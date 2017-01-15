package enterprise.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("Theater")
public class TheaterWebService 
{
	//@EJB(beanName = "TheaterBean")
	//private StatelessLocal metier;
	
	@GET
	@Path("user")
	@Produces(MediaType.APPLICATION_JSON)
	public String getName()
	{
		/*User user = metier.getUser(1);
		if(user != null)
			return user.getUsername();
		else
			return "NONE";*/
		System.out.println("I'm in ");
		return "it works";
	}
}
