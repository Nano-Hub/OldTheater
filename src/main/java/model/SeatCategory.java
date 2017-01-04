package model;

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
		@NamedQuery(name = "SeatCategory.FindByName", query = "SELECT sc FROM SeatCategory sc WHERE sc.name = :name ") 
	}
)

public class SeatCategory
{
	@Id
	@Column(name = "id_seat_category")
	private int id;

	@Column(name = "name")
	private char name;

	@Column(name = "multiplier")
	private float multiplier;

	@Column(name = "number")
	private int nbSeats;

	//bi-directional many-to-one association to seat
	@OneToMany(mappedBy = "seatCategory")
	private List<Reservation> reservations;

	public SeatCategory(/*int id, char name, float multiplier, int nbSeats*/)
	{
		/*this.id = id;
		this.name = name;
		this.multiplier = multiplier;
		this.nbSeats = nbSeats;*/
	}
	
	public SeatCategory(char seatInfo)
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

	public char getName()
	{
		return name;
	}

	public void setName(char name)
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
