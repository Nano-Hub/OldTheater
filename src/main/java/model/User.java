package model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/** The persistent class for the BANK_CUSTOMERS database table. */
@Entity
@Table(name = "user")
@NamedQueries({ 
@NamedQuery(name = "User.findUserById", query = "SELECT u FROM User u WHERE u.idUser = :idUser "),
@NamedQuery(name = "User.findUserByMail", query = "SELECT u FROM User u WHERE u.email = :email "),
//@NamedQuery(name = "User.createUser", query = "INSERT INTO `user` (name, password, email) VALUES ( :name , :password , :email);")
//@NamedQuery(name = "User.displayShows", query = "SELECT event.artist_name as  artiste, event.date as date, FROM user JOIN reservation ON user.id_user = reservation.user_id JOIN  event on set.id_event = event.id_event  WHERE user.id_user = :idUser ;")
})

public class User implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_user")
	private long idUser;
	@Column(name = "password")
	private String password;
	@Column(name = "name")
	private String username;
	@Column(name = "email")
	private String email;

	//bi-directional many-to-one association to seat
	@OneToMany(mappedBy = "user")
	/*@OneToMany
	@JoinColumn(name = "id_reservation")*/
	private List<Reservation> seats;

	public User()
	{

	}
	
	public User(String name, String password, String email)
	{
		this.username = name;
		this.password = password;
		this.email = email;
	}

	public long getIdUser()
	{
		return this.idUser;
	}

	public void setIdUser(int idUser)
	{
		this.idUser = idUser;
	}

	public String getPassword()
	{
		return this.password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getUsername()
	{
		return this.username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getEmail()
	{
		return this.email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public List<Reservation> getSeats()
	{
		return this.seats;
	}

	public void setSeats(List<Reservation> seats)
	{
		this.seats = seats;
	}

	public Reservation addSeat(Reservation seat)
	{
		getSeats().add(seat);
		seat.setUser(this);
		return seat;
	}

	public Reservation removeSeat(Reservation seat)
	{
		getSeats().remove(seat);
		seat.setUser(null);
		return seat;
	}

}