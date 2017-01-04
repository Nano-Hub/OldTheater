package enterprise.service;

import javax.ejb.Local;

import model.TheaterEvent;
import model.User;

@Local
public interface StatelessLocal
{
	//	
	//	public BankCustomer getUser(String user);
	//
	//	public String transferFunds( String fromAccountNo, String toAccountNo,
	//			BigDecimal amount) throws Exception;

	//USER
	public User getUser(int id_user);
	public User createUser(String name, String email, String password);
	public User login(String email, String password);
	
	//EVENT  and   EVENT CATEGORY
	public TheaterEvent getEvent(int id_event);
	
	//RESERVATION   AND   SEAT CATEGORY
	public String lockSeats(int id_event, String seat, int idUser);
	public String bookSeats(int id_event, String seat, int idUser);


}