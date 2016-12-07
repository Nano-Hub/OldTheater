package model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "User")
@NamedQueries({
		@NamedQuery(name = "Seat.createSeat", query = "INSERT INTO seat (seat_category, user_id, event_id) VALUES ( :category , :user_id , :event_id);"), })

public class Seat
{
	private char category;
	private int id;
	private int number;

	@ManyToOne
	@JoinColumn(name = "id_event")
	private TheaterEvent event;

	@ManyToOne
	@JoinColumn(name = "id_user")
	private User user;

	public Seat(String infoSeat)
	{
		this.category = infoSeat.charAt(0);
		this.number = Integer.parseInt(infoSeat.substring(1));
	}

	public Seat(char category, int id, TheaterEvent event, User user)
	{
		super();
		this.category = category;
		this.id = id;
		this.event = event;
		this.user = user;
	}

	public char getCategory()
	{
		return category;
	}

	public void setCategory(char category)
	{
		this.category = category;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public void setUser(User user)
	{
		this.user = user;
	}

	public void setEvent(TheaterEvent event)
	{
		this.event = event;
	}
}
