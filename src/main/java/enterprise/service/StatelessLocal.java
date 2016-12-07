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

	public TheaterEvent getEvent(int id_event);

	public User getUser(int id_user);

	public String bookSeats(int id_event, String seat, int idUser);

	public User createUser(String name, String email, String password);

}