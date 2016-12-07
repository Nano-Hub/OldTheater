package model;

public class eventTest
{
	private int id;
	private String nomArtiste;
	private long date;
	private float priceUnit;
	private Category[] categories;

	public eventTest()
	{
		initCategories();
	}

	private void initCategories()
	{
		categories = new Category[4];
		categories[0] = new Category('A', 3 * priceUnit, 25);
		categories[1] = new Category('B', (float) 2.5 * priceUnit, 45);
		categories[2] = new Category('C', 2 * priceUnit, 100);
		categories[3] = new Category('D', priceUnit, 500);
	}
}
