package model;

public class eventTest
{
	private int id;
	private String nomArtiste;
	private long date;
	private float priceUnit;
	private CategorySeat[] categories;

	public eventTest()
	{
		initCategories();
	}

	private void initCategories()
	{
		categories = new CategorySeat[4];
		categories[0] = new CategorySeat('A', 3 * priceUnit, 25);
		categories[1] = new CategorySeat('B', (float) 2.5 * priceUnit, 45);
		categories[2] = new CategorySeat('C', 2 * priceUnit, 100);
		categories[3] = new CategorySeat('D', priceUnit, 500);
	}
}
