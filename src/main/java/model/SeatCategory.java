package model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "seat_category")
//TODO Remove this cmoment
@NamedQueries
(
	{
		@NamedQuery(name = "SeatCategory.FindByName", query = "SELECT sc FROM SeatCategory sc WHERE sc.name = :name "),
		@NamedQuery(name = "SeatCategory.FindAll", query = "SELECT sc FROM SeatCategory sc ")
	}
)

public class SeatCategory implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_seat_category")
	private int id;

	@Column(name = "name")
	private String name;

	@Column(name = "multiplier")
	private float multiplier;

	@Column(name = "number")
	private int nbSeats;

	//bi-directional many-to-one association to seat
	@OneToMany(mappedBy = "seatCategory")
	private List<Reservation> reservations;

	public SeatCategory()
	{
		
	}
	
	public SeatCategory(String seatInfo)
	{
		this.name = seatInfo;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public float getMultiplier()
	{
		return multiplier;
	}

	public void setMultiplier(float multiplier)
	{
		this.multiplier = multiplier;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getNbSeats()
	{
		return nbSeats;
	}

	public void setNbSeats(int nbSeats)
	{
		this.nbSeats = nbSeats;
	}
}
