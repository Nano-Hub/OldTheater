package enterprise.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import model.Reservation;
import model.TheaterEvent;

@Stateless
@WebService
public class TheaterService
{
	@EJB(beanName = "TheaterBean")
	private StatelessLocal metier;

	//USER
	@WebMethod
	public String getUsernameFromId(@WebParam(name = "idUser") int id_user)
	{
		return metier.getUser(id_user).getUsername();
	}

	@WebMethod
	public String createUser(@WebParam(name = "nom") String nom, @WebParam(name = "password") String password,
			@WebParam(name = "email") String email)
	{
		return metier.createUser(nom, email, password);
	}

	//THEATER EVENT
	@WebMethod
	public ArrayList<TheaterEvent> getAllEvents()
	{
		return metier.showActiveEvents();
	}

	@WebMethod
	public String getEventArtist(@WebParam(name = "idEvent") int id_event)
	{
		return metier.getEvent(id_event).getArtistName();
	}

	@WebMethod
	public String getEventDate(@WebParam(name = "idEvent") int id_event)
	{
		return metier.getEvent(id_event).getDate().toString();
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
	}

	@WebMethod
	public List<Reservation> getEventAvailableSeats(@WebParam(name = "idEvent") int id_event)
	{
		return metier.getEvent(id_event).getSeats();
	}

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

	@WebMethod
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
	public String selectEvent(@WebParam(name = "idEvent") int id) throws Exception
	{
		return null;
	}

	@WebMethod
	public String selectCategory(@WebParam(name = "idCategory") int id)
	{
		return null;
	}

	@WebMethod
	public String selectSeats(@WebParam(name = "idSeats") String id) //String seats = "A1-A2-A3-A4";
	{
		String[] allId = id.split("-");
		for (int i = 0; i < allId.length; i++)
		{
			//traitement
		}
		return null;
	}
}
