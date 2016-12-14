package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "reservation")
@NamedQueries
(
	{
		@NamedQuery
		(
			name = "Reservation.book", 
			query = "INSERT INTO reservation (seat_category_id, user_id, event_id) VALUES ( :category , :user_id , :event_id);"
		),
		
		@NamedQuery
		(
			name = "Reservation.showReservation", 
			query = "SELECT * INTO reservation where user_id = :user_id and event_id = :event_id;"
		),
		
		@NamedQuery
		(
			name = "Reservation.showUser", 
			query = "SELECT * INTO reservation where user_id = :user_id;"
		),
		
		@NamedQuery
		(
			name = "Reservation.showAll", 
			query = "SELECT * INTO reservation"
		), 
	}
)

public class Reservation
{
	@Id
	@Column(name = "id_seat")
	private int id;
	@Column(name = "number")
	private int number;
	@Column(name = "state")
	private int state;
	
	@OneToOne
	@JoinColumn(name = "seat_category_id")
	private int seatCategory;
	
	@ManyToOne
	@JoinColumn(name = "event_id")
	private TheaterEvent event;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	public Reservation(String infoSeat)
	{
		this.seatCategory = infoSeat.charAt(0);
		this.number = Integer.parseInt(infoSeat.substring(1));
	}

	public Reservation(int seatCategory, int id, TheaterEvent event, User user)
	{
		super();
		this.seatCategory = seatCategory;
		this.id = id;
		this.event = event;
		this.user = user;
	}
	
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}

	public int getCategory()
	{
		return seatCategory;
	}
	public void setCategory(int seatCategory)
	{
		this.seatCategory = seatCategory;
	}

	public int getNumber()
	{
		return number;
	}
	public void setNumber(int number)
	{
		this.number = number;
	}

	public User getUser()
	{
		return user;
	}
	public void setUser(User user)
	{
		this.user = user;
	}

	public TheaterEvent getEvent()
	{
		return event;
	}
	public void setEvent(TheaterEvent event)
	{
		this.event = event;
	}
}
