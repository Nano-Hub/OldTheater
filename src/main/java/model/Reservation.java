package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
		/*@NamedQuery
		(
			name = "Reservation.lock", 
			query = "INSERT INTO reservation (seat_category_id, user_id, event_id, number) VALUES ( :category , :user_id , :event_id, :number );"
		),
		
		@NamedQuery
		(
			name = "Reservation.book", 
			query = "INSERT INTO reservation (seat_category_id, user_id, event_id) VALUES ( :category , :user_id , :event_id );"
		),*/
		
		@NamedQuery
		(
			name = "Reservation.showReservation", 
			query = "SELECT r FROM Reservation r WHERE r.user = :user_id AND r.event = :event_id "
		),
		
		@NamedQuery
		(
			name = "Reservation.getBookedSeat", 
			query = "SELECT r FROM Reservation r WHERE r.event = :event_id "
		),
		
		@NamedQuery
		(
			name = "Reservation.showUser", 
			query = "SELECT r FROM Reservation r WHERE r.user = :user_id "
		),
		
		@NamedQuery
		(
			name = "Reservation.showAll", 
			query = "SELECT r FROM Reservation r"
		), 
		
		@NamedQuery
		(
			name = "Reservation.getFromCatUserEventNumber", 
			query = "SELECT r FROM Reservation r WHERE r.user.idUser = :userId AND r.event.idEvent = :eventId AND r.number = :number AND r.seatCategory = :category "
		), 
	}
)

public class Reservation
{
	@Id
	@Column(name = "id_reservation")
	private int id;
	@Column(name = "number")
	private int number;
	@Column(name = "state")
	private int state;
	
	@OneToOne
	@JoinColumn(name = "seat_category_id")
	private SeatCategory seatCategory;
	
	@ManyToOne
	@JoinColumn(name = "event_id")
	private TheaterEvent event;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;



	public Reservation(SeatCategory seatCategory, TheaterEvent event, User user, int number)
	{
		this.seatCategory = seatCategory;
		this.event = event;
		this.user = user;
		this.number = number;
	}
	
	public Reservation()
	{
		
	}
	
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}

	public SeatCategory getCategory()
	{
		return seatCategory;
	}
	public void setCategory(SeatCategory seatCategory)
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
	public int getState()
	{
		return state;
	}
	public void setState(int p_state)
	{
		this.state = p_state;
	}
	
}
