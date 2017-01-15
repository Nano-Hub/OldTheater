package enterprise.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import model.Reservation;
import model.SeatCategory;
import model.TheaterEvent;
import model.User;

@Stateless
@Path("test")
@WebService
public class TheaterService
{
	@EJB(beanName = "TheaterBean")
	private StatelessLocal metier;

	//USERS
	@WebMethod
	public String getUsernameFromId(@WebParam(name = "idUser") int id_user)
	{
		User user = metier.getUser(id_user);
		if(user != null)
			return user.getUsername();
		else
			return "NONE";
	}
	
	public User getUser(@WebParam(name = "idUser") int id_user)
	{
		User user = metier.getUser(id_user);
		if(user != null)
			return user;
		else
			return null;
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
	@GET
	@Path("test")
	@WebMethod
	public List<TheaterEvent> getAllActiveEvents()
	{
		return metier.showActiveEvents();
	}
	
	@WebMethod
	public TheaterEvent getEvent(@WebParam(name = "idEvent") int idEvent)
	{
		return metier.getEvent(idEvent);
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

	@WebMethod//TODO MANAGE EXCEPTIONS
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
	}
	
	@WebMethod
	public String getEventBookedSeats(@WebParam(name = "idEvent") int id_event)
	{
		return metier.getBookedSeats(id_event);
	}
	
	@WebMethod
	public float getTotalEarningFromTheBeginningOfTheUniverse()
	{
		return metier.getTotalEarningFromTheBeginningOfTheUniverse();
	}
}


/*@WebMethod
public String selectSeats(@WebParam(name = "idSeats") String id) //String seats = "A1-A2-A3-A4";
{
	String[] allId = id.split("-");
	for (int i = 0; i < allId.length; i++)
	{
		//traitement
	}
	return null;
}*/

/*@WebMethod
public String getEventArtist(@WebParam(name = "idEvent") int id_event)
{
	return metier.getEvent(id_event).getArtistName();
}

@WebMethod
public String getEventDate(@WebParam(name = "idEvent") int id_event)
{
	return String.valueOf(metier.getEvent(id_event).getDate());
}

@WebMethod
public String getEventCategory(@WebParam(name = "idEvent") int id_event)
{
	return metier.getEvent(id_event).getCategory().getName();
}

@WebMethod
public float getEventRegularPrice(@WebParam(name = "idEvent") int id_event)
{
	return metier.getEvent(id_event).getCategory().getPrice();
}*/
