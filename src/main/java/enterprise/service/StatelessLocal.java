package enterprise.service;

import java.util.List;

import javax.ejb.Remote;

import model.SeatCategory;
import model.TheaterEvent;
import model.User;

@Remote
public interface StatelessLocal
{
	//USER
	public User getUser(int id_user);
	public String createUser(String name, String email, String password);
	public String login(String email, String password);
	
	//EVENT  and   EVENT CATEGORY
	public List<TheaterEvent> showActiveEvents();
	public List<SeatCategory> getSeatsInfos();
	
	//RESERVATION   AND   SEAT CATEGORY
	public String lockSeats(int id_event, String seat, int idUser);
	public String bookSeats(int id_event, int idUser);
	public String getBookedSeats(int id_event, int id_user);
	
	//ADMIN
	public float getTotalEarningFromTheBeginningOfTheUniverse();
	public String getEventsInfosAdmin();
}