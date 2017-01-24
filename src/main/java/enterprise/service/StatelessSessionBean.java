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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

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

		try //If the user exists
		{
			User user = (User) query.getSingleResult();
			return user;
		} catch (NoResultException e) { System.out.println("no result"); };
		return null;
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
			return "email";
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
				return "OK";
			} catch (NotSupportedException | SystemException | SecurityException |
					IllegalStateException | RollbackException | HeuristicMixedException | HeuristicRollbackException e2) {
				e2.printStackTrace();

				return "failed";
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
		String seats[] = infoSeat.split("-");
		UserTransaction utx = context.getUserTransaction();		
		try//Event exists ? 
		{
			Query query = em.createNamedQuery("TheaterEvent.findEventById");
			query.setParameter("idEvent", id_event);
			TheaterEvent theaterEvent = (TheaterEvent) query.getSingleResult();
			try// User exists ?
			{
				query = em.createNamedQuery("User.findUserById");
				query.setParameter("idUser", idUser);
				User user = (User) query.getSingleResult();
				try 
				{
					query = em.createNamedQuery("Reservation.getBookedSeatUser");
					query.setParameter("event_id", theaterEvent);
					query.setParameter("userId", idUser);
					List<Reservation> seatsUser = (List<Reservation>) query.getResultList();
					if(seatsUser != null && (seatsUser.size() + seats.length) <= 4) //If the sum of its already booked seats and the ones he wants to book now doesn't exceed 4
					{
						try 
						{
							for(String seat : seats)
							{
								query = em.createNamedQuery("SeatCategory.FindByName");
								query.setParameter("name", ""+seat.charAt(0));
								SeatCategory seatCat = (SeatCategory) query.getSingleResult();
								if(Integer.parseInt(seat.substring(1)) <= seatCat.getNbSeats())
								{
									utx.begin();
									Reservation reservation = new Reservation(seatCat, theaterEvent, user, Integer.parseInt(seat.substring(1)));
									em.persist(reservation);
									utx.commit();
								}
								else
								{
									System.out.println("This seat doen not exist"); 
									return "This seat does not exist";
								}
							}
						}
						catch(NoResultException e){System.err.println("Seat does not exist"); return "Seat does not exist";}
					}
					else
					{
						System.out.println("Sum of already booked + this one exceed 4");
						return "You are going to book too much seats,you can't :/";
					}
				}
				catch (NotSupportedException | SystemException | SecurityException |
						IllegalStateException | RollbackException | HeuristicMixedException | HeuristicRollbackException e) {
					System.out.println("Locking seat failed");
					return "Locking seat failed";
				}
			}
			catch(NoResultException e){System.out.println("User does not exist"); return "User does not exist";}
		}
		catch(NoResultException e){System.out.println("Event does not exist"); return "Event does not exist";}

		Query query = em.createNamedQuery("Reservation.getFromEventUserLocked");
		query.setParameter("eventId", (long) id_event);
		query.setParameter("userId", (long) idUser);
		List<Reservation> reservations = (List<Reservation>) query.getResultList();
		float totalPrice = 0;
		for(Reservation r : reservations)
		{
			totalPrice += r.getEvent().getCategory().getPrice() * r.getCategory().getMultiplier();
		}
		return String.valueOf(totalPrice);
	}

	@Override
	public String bookSeats(int id_event, int idUser)
	{
		UserTransaction utx = context.getUserTransaction();
		try
		{
			utx.begin();
			Query query = em.createNamedQuery("Reservation.getFromEventUser");
			query.setParameter("eventId", (long) id_event);
			query.setParameter("userId", (long) idUser);
			List<Reservation> reservations = (List<Reservation>) query.getResultList();
			for(Reservation r : reservations)
			{
				r.setState(1);
				em.persist(r);
			}
			utx.commit();
		}
		catch (SecurityException | IllegalStateException | RollbackException | HeuristicMixedException
				| HeuristicRollbackException | NotSupportedException | SystemException e)
		{
			e.printStackTrace();
			return "ko";
		}
		return "ok";
	}

	@Override
	public String getBookedSeats(int id_event, int id_user)
	{
		String seatsStr = "";
		try
		{
			//All the seats booked
			Query query = em.createNamedQuery("TheaterEvent.findEventById");
			query.setParameter("idEvent", id_event);
			TheaterEvent event = (TheaterEvent) query.getSingleResult();
			query = em.createNamedQuery("Reservation.getBookedSeat");
			query.setParameter("event_id", event);
			List<Reservation> seats = (List<Reservation>) query.getResultList();
			if(seats != null && seats.size() > 0)
			{
				for (Reservation r : seats)
				{
					seatsStr += String.valueOf(r.getCategory().getName());
					seatsStr += r.getNumber();
					seatsStr += "-";
				}
				seatsStr = seatsStr.substring(0,  seatsStr.length()-1);
			}
			seatsStr += "/";
			//Number of seats booked by the user
			query = em.createNamedQuery("Reservation.getBookedSeatUser");
			query.setParameter("event_id", event);
			query.setParameter("userId", id_user);
			List<Reservation> seatsUser = (List<Reservation>) query.getResultList();
			if(seatsUser != null && seatsUser.size() > 0)
			{
				seatsStr += seatsUser.size();
			}

			return seatsStr;
		}
		catch(NoResultException e) { System.out.println("Event not found"); return "Event not found";}
	}

	@Override
	public float getTotalEarningFromTheBeginningOfTheUniverse()
	{
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
	
	@Override
	public String getEventsInfosAdmin()
	{
		Query query = em.createNamedQuery("TheaterEvent.getAllEvents");
		try
		{
			String result = "";
			List<TheaterEvent> listTE = (List<TheaterEvent>) query.getResultList();
			
			for(TheaterEvent event : listTE) //For each event
			{
				query = em.createNamedQuery("Reservation.getBookedSeat");
				query.setParameter("event_id", event);

				double recette = 0;
				String name = event.getArtistName();
				String date = calendarToNiceStr(event.getDate());
				int nbCatA = 0;
				int nbCatB = 0;
				int nbCatC = 0;
				int nbCatD = 0;
				
				try
				{
					List<Reservation> listReservation = (List<Reservation>) query.getResultList();
					for(Reservation reserv : listReservation) //For each reservation
					{
						if(reserv.getCategory().getName().equals("A"))
						{
							nbCatA++;
						}
						else if(reserv.getCategory().getName().equals("B"))
						{
							nbCatB++;
						}
						else if(reserv.getCategory().getName().equals("C"))
						{
							nbCatC++;
						}
						else if(reserv.getCategory().getName().equals("D"))
						{
							nbCatD++;
						}
						recette += reserv.getEvent().getCategory().getPrice() * reserv.getCategory().getMultiplier();
					}
					
				}
				catch(NoResultException e)
				{
					System.err.println("NO EVENT IN THE FUTUR");
					System.err.println(e);
					return null;
				}
				
				result += name +"," + date + "," + recette + "," + nbCatA + "," + nbCatB + "," + nbCatC + "," + nbCatD + "/";
			}
			
			return result.substring(0, result.length()-1);
		}
		catch(NoResultException e)
		{
			System.err.println("NO EVENTS");
			System.err.println(e);
			return null;
		}
	}
	
	public static String calendarToNiceStr(Calendar dateCalendar)
	{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEEEEEE dd MMMMMMM yyyy à hh:mm", Locale.FRANCE);
		String dateString = simpleDateFormat.format(dateCalendar.getTime());
		return dateString;
	}
}