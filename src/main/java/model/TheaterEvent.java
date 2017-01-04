package model;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/** The persistent class for the BANK_CUSTOMERS database table. */
@Entity
@Table(name = "event")
@NamedQueries({ 
@NamedQuery(name = "TheaterEvent.showAllEvents", query = "SELECT e.id_event, e.artist_name, e.date, c.name, c.regular_price  FROM event e, event_category c WHERE e.category_id = c.id_category;"),
@NamedQuery(name = "TheaterEvent.findEventById", query = "SELECT * FROM event WHERE id_event = :idEvent;"),
@NamedQuery(name = "TheaterEvent.displayBookedSeats", query = "SELECT id_seat, seat_category_id FROM reservation WHERE event_id = :idEvent;") })

public class TheaterEvent implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_event")
	private long idEvent;
	@Column(name = "artist_name")
	private String artistName;
	@Column(name = "date")
	private Date date;
	
	@OneToOne //-> category event
	@JoinColumn(name = "category_id")
	private EventCategory category;

	@OneToMany(mappedBy = "seat")
	private List<Reservation> seats;
	

	public TheaterEvent(int idEvent)
	{

	}

	public long getIdEvent()
	{
		return this.idEvent;
	}

	public void setIdEvent(int idEvent)
	{
		this.idEvent = idEvent;
	}

	public String getArtistName()
	{
		return this.artistName;
	}

	public void setArtisteName(String artistName)
	{
		this.artistName = artistName;
	}

	public Date getDate()
	{
		return this.date;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}

	public EventCategory getCategory()
	{
		return this.category;
	}

	public void setCategory(EventCategory categoryId)
	{
		this.category = categoryId;
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
		seat.setEvent(this);
		return seat;
	}

	public Reservation removeSeat(Reservation seat)
	{
		getSeats().remove(seat);
		seat.setEvent(null);
		return seat;
	}
}