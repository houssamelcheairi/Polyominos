import java.awt.Color;
import java.util.*;
public class Task2 {

	public static void main(String[] args) {

		System.out.println(" A naive generative approach :");
		//Creating the table performance
		//Fixed polyominos :
		for (int i=1;i<7;i++)
		{
			
			long startTime = System.nanoTime();
			System.out.println(" ");
			System.out.print("There are ");
			List<Polyomino> l = Polyomino.Generate_fixed(i, Color.red);
			System.out.print(l.size());
			System.out.println(" fixed polyominos of size "+i);
			long stopTime = System.nanoTime();
			System.out.print("Execution time :");
			System.out.println((stopTime - startTime)/1000000000.  +" seconds");
		
		
		}
		System.out.println("-----------------------------------------");
		
		//Creating the table performance
		//Free polyominos :
		for (int i=1;i<7;i++)
		{
			
			long startTime = System.nanoTime();
			System.out.println(" ");
			System.out.print("There are ");
			List<Polyomino> l = Polyomino.Generate_free(i, Color.red);
			System.out.print(l.size());
			System.out.println(" free polyominos of size "+i);
			long stopTime = System.nanoTime();
			System.out.print("Execution time :");
			System.out.println((stopTime - startTime)/1000000000.  +" seconds");
				
				
		}
	}

}
