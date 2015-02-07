
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**	
 * Implementation of generic version of quick and merge sort
 * 1. Merge Sort
 * 2. Quick Sort
 * 3. Java Sort
 * 4. Dual Pivot Quick Sort
 * @author ketan
 */

public class Sort
{
	private static int size = 2000000;
	private static int base = 10;
	private static long startTime;


	public static <T extends Comparable<? super T>> void InsertionSort(T[] A, int p, int r)
	{
		for (int i = p; i <= r; i++)
		{
			for (int j = i; j > p; j--)
			{
				if(A[j].compareTo(A[j - 1]) < 0)
				{
					T t = A[j];
					A[j] = A[j - 1];
					A[j - 1] = t;
				}
				else
					break;
			}
		}
	}

	public static <T extends Comparable<? super T>> void MergeSort(T[] A, int p, int r, T[] B)
	{
		int n = (r - p) + 1;
		int q = (p + r) >>> 1;

		if(n <= base)
		{
			// Base case
			InsertionSort(A, p, r);
			return;
		}
		else
		{
			MergeSort(A, p, q, B);
			MergeSort(A, q + 1, r, B);
		}
		Merge(A, p, q, r, B);
	}

	public static <T extends Comparable<? super T>> void Merge(T[] A, int p, int q, int r, T[] B)
	{
		 for (int i = p; i <= r; i++)
			B[i] = A[i];
		 
		 int i = p;
		 int j = q + 1;
		 for (int k = p; k <= r; k++)
		{
			 if(j > r || (i <= q && (B[i].compareTo(B[j]) <= 0)))
				 A[k] = B[i++];
			 else
				 A[k] = B[j++];
		}
	}

	public static <T extends Comparable<? super T>> void QuickSort(T[] A, int p, int r)
	{
		int n = r - p + 1;

		if(n <= base)
		{
			InsertionSort(A, p, r);
			return;
		}
		else
		{
			int q = Partition(A, p, r);
			if(q > p)
				QuickSort(A, p, q - 1);
			if(q < r - 1)
				QuickSort(A, q + 1, r);
		}
	}

	public static <T extends Comparable<? super T>> int Partition(T[] A, int p, int r)
	{
		Random random = new Random();
		int i = Math.abs(random.nextInt(r - p) + p);

		T temp = A[i];
		A[i] = A[r];
		A[r] = temp;
		T pivot = A[r];
		i = p - 1;

		for (int j = p; j <= r - 1; j++)
		{
			if(A[j].compareTo(pivot) <= 0)
			{
				i++;
				temp = A[i];
				A[i] = A[j];
				A[j] = temp;
			}
		}
		temp = A[i + 1];
		A[i + 1] = A[r];
		A[r] = temp;

		return i + 1;
	}

	public static <T extends Comparable<? super T>> void DualPivotQuickSort(T[] A, int p, int r)
	{
		if(p >= r)
			return;
		int n = r - p + 1;
		if (n <= 1)
			return;
		if(n <= base)
		{
			InsertionSort(A, p, r);
			return;
		}
		int[] indices = DualPartition(A, p, r);
		int q1 = indices[0];
		int q2 = indices[1];
		
		DualPivotQuickSort(A, p, q1 - 1);
		DualPivotQuickSort(A, q2 + 1, r);
		if((A[q1].compareTo(A[q2]) != 0))
			DualPivotQuickSort(A, q1 + 1, q2 - 1);
	}
	
	public static <T extends Comparable<? super T>> int[] DualPartition(T[] A, int p, int r)
	{
		Random random = new Random();
		T temp;
		int pivot1 = Math.abs(random.nextInt(r - p) + p);
		int pivot2 = pivot1;
		while(pivot1 == pivot2)
		{
			pivot2 = Math.abs(random.nextInt(r - p) + p);
		}

		T pivotT1, pivotT2;
		pivotT1 = A[pivot1];
		pivotT2 = A[pivot2];
		if(pivotT1.compareTo(pivotT2) < 0)
		{
			temp = A[pivot1];
			A[pivot1] = A[p];
			A[p] = temp;
			temp = A[pivot2];
			A[pivot2] = A[r];
			A[r] = temp;
		}
		else
		{
			temp = A[pivot1];
			A[pivot1] = A[r];
			A[r] = temp;
			temp = A[pivot2];
			A[pivot2] = A[p];
			A[p] = temp;
		}

		if(pivotT1.compareTo(pivotT2) > 0)
		{
			temp = pivotT1;
			pivotT1 = pivotT2;
			pivotT2 = temp;
		}
		A[p] = pivotT1;
		A[r] = pivotT2;

		T t;
		int i = p;
		int j = r;
		int k = p + 1;
		while (k < j)
		{
			if(A[k].compareTo(pivotT1) < 0)
			{
				i++;
				t = A[k];
				A[k] = A[i];
				A[i] = t;
				k++;
			}
			else if((A[k].compareTo(pivotT1) >= 0) &&  (A[k].compareTo(pivotT2) <= 0))
			{
				k++;
			}
			else if(A[k].compareTo(pivotT2) > 0)
			{
				j--;
				t = A[k];
				A[k] = A[j];
				A[j] = t;
			}

		}
		
		temp = A[p];
		A[p] = A[i];
		A[i] = temp;
		
		temp = A[r];
		A[r] = A[j];
		A[j] = temp;

		return new int[]{i, j};
	}

	public static <T> void Swap(T a, T b)
	{
		T temp = a;
		a = b;
		b = temp;
	}
	
	public static void main(String[] args)
	{
		if(args.length == 1)
		{
			size = Integer.parseInt(args[0]);
		}
		else if(args.length == 0)
		{
			System.out.println("Enter size of array : ");

			Scanner scanner = new Scanner(System.in);
			size = scanner.nextInt();
			scanner.close();
		}
		else
		{
			System.out.println("Invalid number of arguments. Running the code with default array size of 2000000.");
		}

		Integer[] arr = new Integer[size];
		Integer[] aux = new Integer[size];
		Integer[] arrCopy = new Integer[size];
		Random random = new Random();

		for (int i = 0; i < size; i++)
		{
			arr[i] = new Integer(random.nextInt(size * 10));
			arrCopy[i] = new Integer(arr[i]);
			aux[i] = new Integer(0);
		}
		
		System.out.println("MergeSort:");
		StartTimer();
		MergeSort(arr, 0, size - 1, aux);
		StopTimer();
		
		System.out.println("QuickSort:");
		for (int i = 0; i < size; i++)
		{
			arr[i] = arrCopy[i];
		}
		StartTimer();
		QuickSort(arr, 0, size - 1);
		StopTimer();
		
		System.out.println("JavaSort:");
		for (int i = 0; i < size; i++)
		{
			arr[i] = arrCopy[i];
		}
		StartTimer();
		Arrays.sort(arr);
		StopTimer();

		System.out.println("Dual Pivot Quick Sort:");
		for (int i = 0; i < size; i++)
		{
			arr[i] = arrCopy[i];
		}
		StartTimer();
		DualPivotQuickSort(arr, 0, size - 1);
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
