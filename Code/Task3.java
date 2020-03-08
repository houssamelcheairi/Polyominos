import java.awt.Color;
import java.util.List;

public class Task3 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.out.println(" The Redelmeier approach :");
		//Creating the table performance
		//Fixed polyominos :
		for (int i=1;i<11;i++)
		{
			
			long startTime = System.nanoTime();
			System.out.println(" ");
			System.out.print("There are ");
			List<Polyomino> l = RedelmeierGen.Redelmeier_GeneratorFixed(i);
			System.out.print(l.size());
			System.out.println(" fixed polyominos of size "+i);
			long stopTime = System.nanoTime();
			System.out.print("Execution time :");
			System.out.println((stopTime - startTime)/1000000000.  +" seconds");
		
		
		}
		System.out.println("-----------------------------------------");
		
		//Creating the table performance
		//Free polyominos :
		for (int i=1;i<11;i++)
		{
			
			long startTime = System.nanoTime();
			System.out.println(" ");
			System.out.print("There are ");
			List<Polyomino> l = RedelmeierGen.Redelmeier_GeneratorFree(i);
			System.out.print(l.size());
			System.out.println(" free polyominos of size "+i);
			long stopTime = System.nanoTime();
			System.out.print("Execution time :");
			System.out.println((stopTime - startTime)/1000000000.  +" seconds");
				
				
		}
	}

}
