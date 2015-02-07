import java.util.Scanner;

/**
 * Implementation of fibonacci series (mod p) generator
 * @author ketan
 */

public class Fibonacci
{

	private static long n;
	private static int p;
	private static long startTime;

	public static long LinearApproach()
	{
		long term0 = 0, term1 = 1;
		long term2 = -1;
		for (long i = 2; i <= n; i++)
		{
			term2 = (term0 + term1) % p;
			term0 = term1;
			term1 = term2;
		}

		return term2;
	}
	
	public static long RecursiveApproach(long num)
	{
		if(num == 0)
			return 0;
		if(num <= 2)
			return 1;
		return (RecursiveApproach(num - 1) + RecursiveApproach(num - 2)) % p;
	}

	public static long DACApproach()
	{
		long[][] mat = {{1, 1}, {1, 0}};
		//long[][] baseFib = {{1, 0}};
		long[][] finalMat = PowerFunction(n-1, mat);
		//long[][] ans = new long[1][1];
		return finalMat[0][0];
	}

	public static long[][] Multiply(long[][] mat1, long[][] mat2)
	{
		long[][] C= new long[2][2];

		C[0][0] = ((mat1[0][0] * mat2[0][0]) + (mat1[0][1] * mat2[1][0])) % p;
	    C[0][1] = ((mat1[0][0] * mat2[0][1]) + (mat1[0][1] * mat2[1][1])) % p;
	    C[1][0] = ((mat1[1][0] * mat2[0][0]) + (mat1[1][1] * mat2[1][0])) % p;
	    C[1][1] = ((mat1[1][0] * mat2[0][1]) + (mat1[1][1] * mat2[1][1])) % p;

	    return C;

	}
	
	private static long[][] PowerFunction(long power, long[][] mat)
	{
		if(power == 1)
		{
			return mat;
		}
		if(power == 2)
		{
			return Multiply(mat, mat);
		}
		else
		{
			long[][] matt = PowerFunction(power >> 1, Multiply(mat, mat));
			if(power % 2 == 0)
				return matt;
			else
				return Multiply(matt, mat);
		}
	}

	public static void main(String[] args)
	{
		if(args.length == 2)
		{
			n = Long.parseLong(args[0]);
			p = Integer.parseInt(args[1]);
		}
		else if(args.length == 0)
		{
			System.out.println("Enter values for n and p : ");

			Scanner scanner = new Scanner(System.in);
			n = scanner.nextLong();
			p = scanner.nextInt();
			scanner.close();
		}
		else
		{
			System.out.println("Invalid number of arguments");
			return;
		}

		StartTimer();
		System.out.println("Fibonacci Linear Approach : " + LinearApproach());
		StopTimer();

		StartTimer();
		System.out.println("Fibonacci DAC Approach : " + DACApproach());
		StopTimer();		
	}

	private static void StopTimer()
	{
		long stopTime = System.currentTimeMillis();
		System.out.println("Total time : " + (stopTime - startTime) + " msec.");
		MemoryUsage();
	}

	private static void StartTimer()
	{
		startTime = System.currentTimeMillis();
	}
	
	public static void MemoryUsage()
	{
		long memAvailable = Runtime.getRuntime().totalMemory();
		long memUsed = memAvailable - Runtime.getRuntime().freeMemory();
		System.out.println("Memory: " + memUsed / 1000000 + " MB / "
				+ memAvailable / 1000000 + " MB.");
	}
}
