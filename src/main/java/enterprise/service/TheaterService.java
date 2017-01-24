package enterprise.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import model.SeatCategory;
import model.TheaterEvent;
import model.User;

@Stateless
@WebService
public class TheaterService
{
	@EJB(beanName = "TheaterBean")
	private StatelessLocal metier;

	/*//USERS
	@WebMethod
	public String getUsernameFromId(@WebParam(name = "idUser") int id_user)
	{
		User user = metier.getUser(id_user);
		if(user != null)
			return user.getUsername();
		else
			return "NONE";
	}*/
	
	@WebMethod
	public User login(@WebParam(name = "idUser") int id_user)
	{
		return metier.getUser(id_user);
		
	}
	
	@WebMethod 
	public String createUser(@WebParam(name = "nom") String nom, @WebParam(name = "password") String password,
			@WebParam(name = "email") String email)
	{
		return metier.createUser(nom, email, password);
	}
	
	@WebMethod
	public String login(@WebParam(name = "email") String email, @WebParam(name = "password") String password)
	{
		return metier.login(email, password);
	}

	//THEATER EVENTS
	@WebMethod
	public List<TheaterEvent> getAllActiveEvents()
	{
		return metier.showActiveEvents();
	}
		
	@WebMethod
	public List<SeatCategory> getSeatsInfos()
	{
		return metier.getSeatsInfos();
	}

	//RESERVATIONS AND SEATS
	@WebMethod
	public String lockSeats(@WebParam(name = "idEvent") int id_event, @WebParam(name = "seat") String seat,
			@WebParam(name = "id_user") int id_user)
	{
		try
		{
			return metier.lockSeats(id_event, seat, id_user);
		}
		catch (Exception e)
		{
			return e.getMessage();
		}
	}

	/*@WebMethod//TODO MANAGE EXCEPTIONS
	public String bookSeats(@WebParam(name = "idEvent") int id_event, @WebParam(name = "seat") String seat,
			@WebParam(name = "id_user") int id_user)
	{
		try
		{
			return metier.bookSeats(id_event, seat, id_user);
		}
		catch (Exception e)
		{
			return e.getMessage();
		}
	}*/
	
	@WebMethod
	public String getEventBookedSeats(@WebParam(name = "idEvent") int id_event, @WebParam(name = "idUser") int id_user)
	{
		return metier.getBookedSeats(id_event, id_user);
	}
	
	@WebMethod
	public float getTotalEarningFromTheBeginningOfTheUniverse()
	{
		return metier.getTotalEarningFromTheBeginningOfTheUniverse();
	}
}
