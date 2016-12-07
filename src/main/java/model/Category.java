package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Category
{

	private char id;
	private float price;
	private List<Boolean> sieges;

	public Category(char id, float price, int nbSieges)
	{
		super();
		this.id = id;
		this.price = price;
		sieges = new ArrayList<Boolean>(nbSieges); // Sieges -> ind 0 = siege 1 , ind n = siege n+1 
		Collections.fill(sieges, Boolean.FALSE); // Rempli la liste sieges à false
	}

	public char getId()
	{
		return id;
	}

	public float getPrice()
	{
		return price;
	}

	public List<Boolean> getSieges()
	{
		return sieges;
	}

	public void setSieges(List<Boolean> sieges)
	{
		this.sieges = sieges;
	}

}
