
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Test
{
	public static String getReservationsString()
	{
		List<Boolean> A = new ArrayList<Boolean>();
		for (int i = 0; i < 25; i++)
			A.add(new Random().nextInt() % 100 > 50 ? true : false);

		List<Boolean> B = new ArrayList<Boolean>();
		for (int i = 0; i < 45; i++)
			B.add(new Random().nextInt() % 100 > 50 ? true : false);

		List<Boolean> C = new ArrayList<Boolean>();
		for (int i = 0; i < 100; i++)
			C.add(new Random().nextInt() % 100 > 50 ? true : false);

		List<Boolean> D = new ArrayList<Boolean>();
		for (int i = 0; i < 500; i++)
			D.add(new Random().nextInt() % 100 > 50 ? true : false);

		String res = "";
		for (int i = 1; i <= A.size(); i++)
		{
			if (A.get(i - 1))
				res += i + "-";
		}
		res += "/";
		for (int i = 1; i <= B.size(); i++)
		{
			if (B.get(i - 1))
				res += i + "-";
		}
		res += "/";
		for (int i = 1; i <= C.size(); i++)
		{
			if (C.get(i - 1))
				res += i + "-";
		}
		res += "/";
		for (int i = 1; i <= D.size(); i++)
		{
			if (D.get(i - 1))
				res += i + "-";
		}
		return res;
	}

	public static void main(String[] args)
	{
		System.out.println(Test.getReservationsString());
	}
}