import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Scanner;


public class SelectionProblem
{
	private static long startTime;
	private static int size = 1000000;
	private static int kIndex = 1000000;

	public static <T extends Comparable<? super T>> T LinearApproach(T[] A, int k)
	{
		Arrays.sort(A);
		return A[k - 1];
	}
	
	public static <T extends Comparable<? super T>> T MinHeapApproach(T[] A, int k)
	{
		PriorityQueue<T> prioQ = new PriorityQueue<T>();
		for (int i = 0; i < size; i++)
		{
			prioQ.add(A[i]);
		}
		
		T item = null;
		for (int i = 0; i < k; i++)
		{
			item = prioQ.poll();
		}
		return item;
	}
	
	public static <T extends Comparable<? super T>> T KNodeMaxHeapApproach(T[] A, int k)
	{
		PriorityQueue<T> prioQ = new PriorityQueue<T>(k, Collections.reverseOrder());
		for (int i = 0; i < k; i++)
		{
			prioQ.add(A[i]);
		}
		
		for (int i = k; i < size; i++)
		{
			if(A[i].compareTo(prioQ.peek()) < 0)
			{
				prioQ.poll();
				prioQ.add(A[i]);
			}
		}
		return prioQ.peek();
	}
	
	public static <T extends Comparable<? super T>> int RandomizedPartition(T[] A, int p, int r)
	{
		if(r-p <= 0)
			return p;
		Random rand = new Random();
		int random = rand.nextInt(r - p) + p;
		
		T temp = A[random];
		A[random] = A[r];
		A[r] = temp;
		int i = p - 1;
		for (int k = p; k <= r; k++)
		{
			if(A[k].compareTo(A[r]) < 0)
			{
				i++;
				temp = A[k];
				A[k] = A[i];
				A[i] = temp; 
			}
		}
		temp = A[i + 1];
		A[i + 1] = A[r];
		A[r] = temp;
		return i + 1;
	}
	
	public static <T extends Comparable<? super T>> T PartitionApproach(T[] A, int p, int r, int k)
	{
		int q = RandomizedPartition(A, p, r);
		if(q - p + 1 == k)
			return A[q];
		else if(q - p + 1 > k)
			return PartitionApproach(A, p, q, k);
		else if(q - p + 1 < k)
			return PartitionApproach(A, q + 1, r, k - (q-p+1));
		return null;
	}

	public static void main(String[] args)
	{
		if(args.length == 2)
		{
			size = Integer.parseInt(args[0]);
			kIndex = Integer.parseInt(args[1]);
		}
		else if(args.length == 0)
		{
			System.out.println("Enter values for size and k : ");

			Scanner scanner = new Scanner(System.in);
			size = scanner.nextInt();
			kIndex = scanner.nextInt();
			scanner.close();
		}
		else
		{
			System.out.println("Invalid number of arguments.");
			return;
		}

		Integer[] arr = new Integer[size];
		Integer[] arrCopy = new Integer[size];
		Random random = new Random();

		for (int i = 0; i < size; i++)
		{
			arr[i] = new Integer(random.nextInt(size * 10));
			arrCopy[i] = new Integer(arr[i]);
		}
		
		System.out.println("K = " + kIndex);
		
		StartTimer();
		System.out.println("Linear Kth smallest : " + LinearApproach(arr, kIndex));
		StopTimer();
		
		for (int i = 0; i < size; i++)
		{
			arr[i] = arrCopy[i];
		}
		StartTimer();
		System.out.println("PriorityQ Kth smallest : " + MinHeapApproach(arr, kIndex));
		StopTimer();
		
		for (int i = 0; i < size; i++)
		{
			arr[i] = arrCopy[i];
		}
		StartTimer();
		System.out.println("K node PQ Kth smallest : " + KNodeMaxHeapApproach(arr, kIndex));
		StopTimer();
		
		for (int i = 0; i < size; i++)
		{
			arr[i] = arrCopy[i];
		}
		StartTimer();
		System.out.println("Partition Kth smallest : " + PartitionApproach(arr, 0, size - 1, kIndex));
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
