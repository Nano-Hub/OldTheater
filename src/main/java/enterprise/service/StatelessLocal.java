package enterprise.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Local;

import model.SeatCategory;
import model.TheaterEvent;
import model.User;

@Local
public interface StatelessLocal
{
	//USER
	public User getUser(int id_user); 
	public String createUser(String name, String email, String password);
	public String login(String email, String password);
	
	//EVENT  and   EVENT CATEGORY
	public List<TheaterEvent> showActiveEvents();
	public TheaterEvent getEvent(int id_event);
	public List<SeatCategory> getSeatsInfos();
	
	//RESERVATION   AND   SEAT CATEGORY
	public String lockSeats(int id_event, String seat, int idUser);
	public String bookSeats(int id_event, String seat, int idUser);
	public String getBookedSeats(int id_event);
	
	public float getTotalEarningFromTheBeginningOfTheUniverse();
}