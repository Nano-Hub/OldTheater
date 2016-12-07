package model;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/** The persistent class for the BANK_CUSTOMERS database table. */
@Entity
@Table(name = "TheaterEvent")
@NamedQueries({ @NamedQuery(name = "TheaterEvent", query = "SELECT * FROM event;"),

		@NamedQuery(name = "TheaterEvent.findEvent", query = "SELECT * FROM event WHERE id_event = :idEvent;"),

		@NamedQuery(name = "TheaterEvent.displayAvailableSeats", query = "SELECT id_seat, seat_category_id FROM seat  WHERE state = 1 OR state = 0 and event_id=:idEvent;") })

public class TheaterEvent implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_EVENT")
	private long idEvent;

	private String artistName;
	private Date date;
	private int categoryId;

	//bi-directional many-to-one association to Event
	@OneToMany(mappedBy = "seat")
	private List<Seat> seats;

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

	public int getCategoryId()
	{
		return this.categoryId;
	}

	public void setCategoryId(int categoryId)
	{
		this.categoryId = categoryId;
	}

	public List<Seat> getSeats()
	{
		return this.seats;
	}

	public void setSeats(List<Seat> seats)
	{
		this.seats = seats;
	}

	public Seat addSeat(Seat seat)
	{
		getSeats().add(seat);
		seat.setEvent(this);
		return seat;
	}

	public Seat removeSeat(Seat seat)
	{
		getSeats().remove(seat);
		seat.setEvent(null);
		return seat;
	}

}