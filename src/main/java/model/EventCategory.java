package model;

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

@Entity
@Table(name = "seat_category")
/* TODO CHANGE -> */ @NamedQueries({ @NamedQuery(name = "SeatCategory.FindSeat", query = "SELECT * FROM seat_category WHERE id_seat_category = :id_category ;"),
		})

public class EventCategory 
{
	@Id
	@Column(name = "id_category")
	private int idCategory;
	@Column(name = "name")
	private String name;
	@Column(name = "regular_price")
	private int price;
	
	@OneToMany
	@JoinColumn(name = "id_event")
	private List<TheaterEvent> events;

	
	public EventCategory()
	{
		
	}
	
	public int getIdCategory()
	{
		return idCategory;
	}
	public void setIdCategory(int idCategory)
	{
		this.idCategory = idCategory;
	}
	
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	
	public int getPrice()
	{
		return price;
	}
	public void setPrice(int price)
	{
		this.price = price;
	}
}
