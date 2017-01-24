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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;

/** The persistent class for the BANK_CUSTOMERS database table. */
@Entity
@Table(name = "event")
@NamedQueries
(
		{
			@NamedQuery
			(
					name = "TheaterEvent.showAllActiveEvents",
					query = "SELECT e FROM TheaterEvent e WHERE  (e.date > CURRENT_TIME) "
					),
			@NamedQuery
			(
					name = "TheaterEvent.getAllEvents", 
					query = "SELECT e FROM TheaterEvent e "
					), 
			@NamedQuery
			(
					name = "TheaterEvent.findEventById", 
					query = "SELECT e FROM TheaterEvent e WHERE e.idEvent = :idEvent "
					)
		}
)

public class TheaterEvent implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_event")
	private int idEvent;
	@Column(name = "artist_name")
	private String artistName;
	@Column(name = "date")
	private java.util.Calendar date;

	@OneToOne //-> category event
	@JoinColumn(name = "category_id")
	private EventCategory category;

	@OneToMany(mappedBy = "event")
	private List<Reservation> seats;

	public TheaterEvent()
	{

	}

	public int getIdEvent()
	{
		return this.idEvent;
	}

	public void setIdEvent(int idEvent)
	{
		this.idEvent = idEvent;
	}

	public java.util.Calendar getDate() {
		return date;
	}

	public void setDate(java.util.Calendar date) {
		this.date = date;
	}

	public String getArtistName() {
		return artistName;
	}

	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}

	public EventCategory getCategory()
	{
		return this.category;
	}

	public void setCategory(EventCategory categoryId)
	{
		this.category = categoryId;
	}

	@XmlTransient
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

	@Override
	public String toString() {
		return "TheaterEvent [idEvent=" + idEvent + ", artistName=" + artistName + ", date=" + date + ", category="
				+ category + ", seats=" + seats + "]";
	}
}