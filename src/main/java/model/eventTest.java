package model;

public class eventTest
{
	private int id;
	private String nomArtiste;
	private long date;
	private float priceUnit;
	private SeatCategory[] categories;

	public eventTest()
	{
		initCategories();
	}

	private void initCategories()
	{
		categories = new SeatCategory[4];
		categories[0] = new SeatCategory('A', 3 * priceUnit, 25);
		categories[1] = new SeatCategory('B', (float) 2.5 * priceUnit, 45);
		categories[2] = new SeatCategory('C', 2 * priceUnit, 100);
		categories[3] = new SeatCategory('D', priceUnit, 500);
	}
}
