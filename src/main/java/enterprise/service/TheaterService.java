package enterprise.service;

import java.math.BigDecimal;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@Stateless
@WebService
public class TheaterService
{
	@EJB(beanName = "TheaterBean")
	private StatelessLocal metier;

	@WebMethod
	public String getUserInfo(@WebParam(name = "idUser") int id_user)
	{
		return metier.getUser(id_user).getPassword();
	}

	@WebMethod
	public String bookSeats(@WebParam(name = "idEvent") int id_event, @WebParam(name = "seat") String seat)
			throws Exception
	{
		//TODO : 
		BigDecimal amount = new BigDecimal(transferAmount);
		try
		{
			return metier.transferFunds(fromAccount, toAccount, amount);
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
