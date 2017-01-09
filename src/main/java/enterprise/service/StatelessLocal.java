package enterprise.service;

import java.util.ArrayList;

import javax.ejb.Local;

import model.TheaterEvent;
import model.User;

@Local
public interface StatelessLocal
{
	//USER
	public User getUser(int id_user);
	public String createUser(String name, String email, String password);
	public User login(String email, String password); //TODO return int idUser
	
	//EVENT  and   EVENT CATEGORY
	public ArrayList<TheaterEvent> showActiveEvents();
	public TheaterEvent getEvent(int id_event);
	
	//RESERVATION   AND   SEAT CATEGORY
	public String lockSeats(int id_event, String seat, int idUser);
	public String bookSeats(int id_event, String seat, int idUser);
	public String getBookedSeats(int id_event);
}