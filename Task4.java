import java.util.*;
import java.util.List;

public class Task4 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//Fist instance performance
		System.out.println(" First exact cover instance ");
		
		for (int n=1;n<8;n++)
		{
			System.out.println(" ");
			System.out.print("For n = "+n + " there are ");
			List<Integer> X = Point.Entiers(n);
			List<List<Integer>>  C = Point.Subsets(n);
			long startTime = System.nanoTime();
			List<List<List<Integer>>> sols = Point.exactCover_Heuristic(X, C);
			System.out.println(sols.size() + " possible solutions.");
			long stopTime = System.nanoTime();
			System.out.print("Execution time :");
			System.out.println((stopTime - startTime)/1000000000.+" seconds");
		}
		
		System.out.println("------------------------------");
		//Second instance performance
		System.out.println(" Second exact cover instance ");
			
		for (int n=2;n<10;n++)
		{
			System.out.println(" ");
			System.out.print("For n = "+n + " there are ");
			List<Integer> X = Point.Entiers(n);
			List<List<Integer>>  C = Point.Subsets(n,2);
			long startTime = System.nanoTime();
			List<List<List<Integer>>> sols = Point.exactCover_Heuristic(X, C);
			System.out.println(sols.size() + " possible solutions.");
			long stopTime = System.nanoTime();
			System.out.print("Execution time :");
			System.out.println((stopTime - startTime)/1000000000.+" seconds");
		}
	}

}
