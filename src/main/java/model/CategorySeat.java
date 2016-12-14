package model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "seat_category")
@NamedQueries({ @NamedQuery(name = "SeatCategory.FindSeat", query = "SELECT * FROM seat_category WHERE id_seat_category = :id_category ;"),
		})

public class CategorySeat
{
	@Column(name = "id_seat_category")
	private int id;
	@Column(name = "name")
	private String name;
	@Column(name = "multiplier")
	private float multiplier;
	@Column(name = "number")
	private int nbSeats;
	
	//bi-directional many-to-one association to seat
	@OneToMany(mappedBy = "seat")
	private List<Reservation> seats;

	public CategorySeat(int id, String name, float multiplier, int nbSeats)
	{
		this.id = id;
		this.name = name;
		this.multiplier = multiplier;
		this.nbSeats= nbSeats;
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
