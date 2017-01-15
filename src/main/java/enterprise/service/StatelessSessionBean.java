/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 1997-2013 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

package enterprise.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.apache.tools.ant.taskdefs.SubAnt;

import model.Reservation;
import model.SeatCategory;
import model.TheaterEvent;
import model.User;

@Stateless(name = "TheaterBean")
@TransactionManagement(TransactionManagementType.BEAN)
public class StatelessSessionBean implements StatelessLocal
{

	@Resource
	private EJBContext context;
	@PersistenceContext(type = PersistenceContextType.TRANSACTION)
	private EntityManager em;

	@Override
	public User getUser(int id_user)
	{
		Query query = em.createNamedQuery("User.findUserById");
		query.setParameter("idUser", id_user);

		try{
			User user = (User) query.getSingleResult();

			System.out.println(user);

			return user;
		} catch (NoResultException e) { System.out.println("no result"); };
		return null;

	}

	@Override
	public String getUserStr(int id_user)
	{
		Query query = em.createNamedQuery("User.findUserById");
		query.setParameter("idUser", id_user);

		try{
			User user = (User) query.getSingleResult();

			System.out.println(user);

			return user.toString();
		} catch (NoResultException e) { System.out.println("no result"); };
		return "no result";

	}

	@Override
	public String createUser(String name, String email, String password)
	{


		Query query = em.createNamedQuery("User.findUserByMail");
		query.setParameter("email", email);

		try
		{
			User user = (User) query.getSingleResult();
			//EMAIL EXISTS
			return "Email already exists";
		}
		catch (NoResultException e) //EMAIL DOES NOT EXIST
		{
			try 
			{
				UserTransaction utx = context.getUserTransaction();
				utx.begin();
				User user = new User(name, password, email);
				em.persist(user);
				utx.commit();

				return "Creation OK"; //TODO exception

			} catch (NotSupportedException | SystemException | SecurityException |
					IllegalStateException | RollbackException | HeuristicMixedException | HeuristicRollbackException e2) {
				e2.printStackTrace();

				return "Creation failed"; //TODO exception
			}
		}
	}

	@Override
	public String login(String email, String password)
	{
		//Does this email exist ?
		Query query = em.createNamedQuery("User.findUserByMail");
		query.setParameter("email", email);

		try
		{
			User user = (User) query.getSingleResult();

			if(password.equals(user.getPassword())) //password OK
			{
				return String.valueOf(user.getIdUser());
			}
			else
			{
				return "Wrong password";
			}
		}
		catch (NoResultException e) //Wrong password
		{
			return "Email does not exist";
		}

	}

	@Override
	public TheaterEvent getEvent(int id_event)
	{
		Query query = em.createNamedQuery("TheaterEvent.findEventById");
		query.setParameter("idEvent", id_event);	

		try
		{
			TheaterEvent te = (TheaterEvent) query.getSingleResult();
			return te;
		}
		catch(NoResultException e) { System.out.println("No events");}
		return null;

	}

	@Override
	public List<TheaterEvent> showActiveEvents()
	{
		Query query = em.createNamedQuery("TheaterEvent.showAllActiveEvents");
		try
		{
			List<TheaterEvent> listTE = (List<TheaterEvent>) query.getResultList();
			return listTE;
		}
		catch(NoResultException e)
		{
			System.err.println("NO EVENT IN THE FUTUR");
			System.err.println(e);
			return null;
		}
	}

	@Override
	public List<SeatCategory> getSeatsInfos()
	{
		Query query = em.createNamedQuery("SeatCategory.FindAll");
		try
		{
			List<SeatCategory> listSeatCat = (List<SeatCategory>) query.getResultList();
			if(listSeatCat != null && listSeatCat.size() > 0)
			{
				return listSeatCat;
			}
			else
			{
				return null;
			}
		}
		catch(NoResultException e)
		{
			System.err.println("NO EVENT IN THE FUTUR");
			System.err.println(e);
			return null;
		}
	}

	@Override
	public String lockSeats(int id_event, String infoSeat, int idUser) //TODO : remove seats 
	{
		UserTransaction utx = context.getUserTransaction();		
		try {
			Query query = em.createNamedQuery("SeatCategory.FindByName");
			query.setParameter("name", infoSeat.charAt(0));
			SeatCategory seatCat = (SeatCategory) query.getSingleResult();
			if(Integer.parseInt(infoSeat.substring(1)) < seatCat.getNbSeats())
			{
				try {
					query = em.createNamedQuery("TheaterEvent.findEventById");
					query.setParameter("idEvent", id_event);
					TheaterEvent theaterEvent = (TheaterEvent) query.getSingleResult();
					try {
						query = em.createNamedQuery("User.findUserById");
						query.setParameter("idUser", idUser);
						User user = (User) query.getSingleResult();
						//Everything ok
						try {
							utx.begin();

							Reservation reservation = new Reservation(seatCat, theaterEvent, user, Integer.parseInt(infoSeat.substring(1)));
							em.persist(reservation);

							utx.commit();
							return "locking seat OK";

						} catch (NotSupportedException | SystemException | SecurityException |
								IllegalStateException | RollbackException | HeuristicMixedException | HeuristicRollbackException e) {
							System.out.println("Locking seat failed");
							return "Locking seat failed";
						}
					}
					catch(NoResultException e){System.out.println("User does not exist"); return "User does not exist";}
				}
				catch(NoResultException e){System.out.println("Event does not exist"); return "Event does not exist";}
			}
			else
			{
				System.out.println("This seat doen not exist"); 
				return "This seat doen not exist";
			}
		}
		catch(NoResultException e){System.err.println("Seat does not exist"); return "Seat does not exist";}
	}

	@Override //TODO CHECK EXCEPTIONS
	public String bookSeats(int id_event, String infoSeat, int idUser)
	{
		UserTransaction utx = context.getUserTransaction();
		try
		{
			utx.begin();

			Query query = em.createNamedQuery("SeatCategory.FindByName");
			query.setParameter("name", infoSeat.charAt(0));
			SeatCategory seatCat = (SeatCategory) query.getSingleResult();

			query = em.createNamedQuery("Reservation.getFromCatUserEventNumber");

			query.setParameter("number", Integer.parseInt(infoSeat.substring(1)));
			query.setParameter("eventId", (long) id_event);
			query.setParameter("userId", (long) idUser);

			query.setParameter("category", seatCat);

			Reservation reservation = (Reservation) query.getSingleResult();

			reservation.setState(1);
			em.persist(reservation);
			utx.commit();
		}
		catch (SecurityException | IllegalStateException | RollbackException | HeuristicMixedException
				| HeuristicRollbackException | NotSupportedException | SystemException e)
		{
			e.printStackTrace();
		}
		return "TODO";
	}

	@Override
	public String getBookedSeats(int id_event)
	{
		try
		{
			Query query = em.createNamedQuery("TheaterEvent.findEventById");
			query.setParameter("idEvent", id_event);
			TheaterEvent event = (TheaterEvent) query.getSingleResult();
			query = em.createNamedQuery("Reservation.getBookedSeat");
			query.setParameter("event_id", event);
			List<Reservation> seats = (List<Reservation>) query.getResultList();
			if(seats != null && seats.size() > 0)
			{
				String seatsStr = "";
				for (Reservation r : seats)
				{
					seatsStr += String.valueOf(r.getCategory().getName());
					seatsStr += r.getNumber();
					seatsStr += "-";
				}
				seatsStr = seatsStr.substring(0,  seatsStr.length()-1);
				return seatsStr;
			}
			else
			{
				return "NONE";
			}

		}
		catch(NoResultException e) { System.out.println("Event not found"); return "Event not found";}
	}

	@Override
	public float getTotalEarningFromTheBeginningOfTheUniverse()
	{
		//get all reservation where state = 1;
		//regular price * multiplier 
		float totalEarningFromTheBeginningOfTheUniverse = 0;

		Query query = em.createNamedQuery("Reservation.showAllFinished");
		List<Reservation> listReservation = (List<Reservation>) query.getResultList();
		if(listReservation != null && listReservation.size() > 0)
		{
			for(Reservation r : listReservation)
			{
				float price = r.getEvent().getCategory().getPrice() * r.getCategory().getMultiplier();
				totalEarningFromTheBeginningOfTheUniverse += price;
			}
		}

		return totalEarningFromTheBeginningOfTheUniverse;
	}
}

//	@Override
//	public String transferFunds(String fromAccountNo, String toAccountNo, BigDecimal amount) throws Exception
//	{
//		try
//		{
//			UserTransaction utx = context.getUserTransaction();
//
//			utx.begin();
//			// Check for amount greater than 0
//			// if ( amount.doubleValue() <= 0 )
//			// {
//			// throw new Exception( "Invalid transfer amount" );
//			// }
//
//			// Get source bank account entity
//			Query query = em.createNamedQuery("BankAccountEntity.findByAccountNo");
//			query.setParameter("accountNo", fromAccountNo);
//			BankAccount fromBankAccountEntity = null;
//			fromBankAccountEntity = (BankAccount) query.getSingleResult();
//			System.out.println("--- THe first account is --- " + fromBankAccountEntity.getAccountNo());
//
//			query.setParameter("accountNo", toAccountNo);
//			BankAccount toBankAccountEntity = (BankAccount) query.getSingleResult();
//			System.out.println("--- THe secound account is --- " + toBankAccountEntity.getAccountNo());
//			// Check if there are enough funds in the source account for the
//			// transfer
//			BigDecimal sourceBalance = fromBankAccountEntity.getBalance();
//			System.out.println("Balance source = " + sourceBalance);
//			System.out.println("Amount " + amount);
//			BigDecimal bankCharge = new BigDecimal(2);
//
//			// Perform the transfer
//			sourceBalance = sourceBalance.subtract(amount).subtract(bankCharge);
//			fromBankAccountEntity.setBalance(sourceBalance);
//			System.out.println(fromBankAccountEntity);
//			BigDecimal targetBalance = toBankAccountEntity.getBalance();
//			toBankAccountEntity.setBalance(targetBalance.add(amount));
//			System.out.println(toBankAccountEntity);
//			System.out.println("Transfer Completed");
//			// Update all the accounts
//			// em.merge(toBankAccountEntity);
//			//
//			// em.merge(fromBankAccountEntity);
//			utx.commit();
//			return "Done - Balances after operation \r\n " + fromBankAccountEntity.getAccountNo() + ": "
//					+ fromBankAccountEntity.getBalance() + " \r\n" + toBankAccountEntity.getAccountNo() + ": "
//					+ toBankAccountEntity.getBalance();
//		}
//		catch (Exception e)
//		{
//			System.out.println(e.getMessage());
//			e.printStackTrace();
//			return e.getLocalizedMessage();
//		}
//
//	}
