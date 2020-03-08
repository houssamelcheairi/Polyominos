import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Task8 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Image2d ig1  = new Image2d(1000,150);
		Image2d ig2  = new Image2d(1000,150);
		Image2d ig3  = new Image2d(1000,150);
		Image2d ig4 = new Image2d(1000,1000);
		List<Polyomino> image1 = new ArrayList<Polyomino>();
		List<Polyomino> image2 = new ArrayList<Polyomino>();
		List<Polyomino> image3 = new ArrayList<Polyomino>();
		List<Polyomino> image4 = new ArrayList<Polyomino>();
		
		//Task 8.a
		//Figure 1
		System.out.println("Task 8.a - Figure 1");
		Polyomino fig5a = Polyomino.Figure5_1();
		List<Polyomino> pentaminos = Polyomino.Generate_free(5, Color.red);
		//Hashing keys
		int n = 100;
		int m = 1000000;
		System.out.println(" ");
		System.out.print("There are ");
		long startTime1 = System.nanoTime();
		List<List<Polyomino>> solutions1 = Polyomino.TilingSolverOnce(fig5a, pentaminos, n, m);
		System.out.print(solutions1.size());
		System.out.println(" ways of tiling the first figure.");
		long stopTime1 = System.nanoTime();
		System.out.print("Execution time :");
		System.out.println((stopTime1 - startTime1)/1000000000.+" seconds");
		
		
		//This code gives a visual pf some tilings
		for (int i=0; i<9; i++)
		{
		List<Polyomino> lp = solutions1.get(i+100);
		for (Polyomino q :lp)
		{
			q.Translation(11*i, 0);
			q.Dilatation(10);
			q.Updown(100);
			ig1.addEdges(q, 10);
			Random rand = new Random();
			float re = rand.nextFloat();
			float g = rand.nextFloat();
			float b = rand.nextFloat();
			Color randomColor = new Color(re, g, b);
			q.color = randomColor;
			image1.add(q);
		}
		}
		
		System.out.println("------------------------------");
		//Task 8.a
		//Figure 2
		System.out.println("Task 8.a - Figure 2");
		Polyomino fig5b = Polyomino.Figure5_2(5);
		//Hashing keys
		System.out.println(" ");
		System.out.print("There are ");
		long startTime2 = System.nanoTime();
		List<List<Polyomino>> solutions2 = Polyomino.TilingSolverOnce(fig5b, pentaminos, n, m);
		System.out.print(solutions2.size());
		System.out.println(" ways of tiling the second figure.");
		long stopTime2 = System.nanoTime();
		System.out.print("Execution time :");
		System.out.println((stopTime2 - startTime2)/1000000000.+" seconds");
		
		//This code gives a visual pf some tilings
		for (int i=0; i<9; i++)
		{
		List<Polyomino> lp = solutions2.get(i+100);
		for (Polyomino q :lp)
		{
			

			q.Translation(11*i, 0);
			q.Dilatation(10);
			q.Updown(100);
			ig2.addEdges(q, 10);
			Random rand = new Random();
			float re = rand.nextFloat();
			float g = rand.nextFloat();
			float b = rand.nextFloat();
			Color randomColor = new Color(re, g, b);
			q.color = randomColor;		
			image2.add(q);
		}
		}
		
		System.out.println("------------------------------");
		//Task 8.a
		//Figure 3"
		System.out.println("Task 8.a - Figure 3");
		Polyomino fig5c = Polyomino.Figure5_3();
		//Hashing keys
		System.out.println(" ");
		System.out.print("There are ");
		long startTime3 = System.nanoTime();
		List<List<Polyomino>> solutions3 = Polyomino.TilingSolverOnce(fig5c, pentaminos, n, m);
		System.out.print(solutions3.size());
		System.out.println(" ways of tiling the third figure.");
		long stopTime3 = System.nanoTime();
		System.out.print("Execution time :");
		System.out.println((stopTime3 - startTime3)/1000000000.+" seconds");
		
		
		System.out.println("------------------------------");
		//Task 8.b
		System.out.println("Task 8.b");
		for (int i=1;i<6;i++)
		{
			Polyomino square = Polyomino.Rectangle(i, i);
			List<Polyomino> tiles = Polyomino.Translations_Inside(square,Polyomino.Generate_fixed(i, Color.black));
			System.out.println(" ");
			System.out.print("There are ");
			long startTime4 = System.nanoTime();
			List<List<Polyomino>> solutions4 = Polyomino.TilingSolver(square, tiles, n);
			System.out.print(solutions4.size());
			System.out.println(" ways of tiling a "+ i+"*"+i+" square with fixed polyominos of size "+i);
			long stopTime4 = System.nanoTime();
			System.out.print("Execution time :");
			System.out.println((stopTime4 - startTime4)/1000000000.+" seconds");
		}
		
		System.out.println("------------------------------");
		//Task 8.c
		System.out.println("Task 8.c");
		List<Polyomino> octaminos = Polyomino.Generate_free(8, Color.red);
		int k =4;
		List<Integer> index = new ArrayList<Integer>();
		long startTime5 = System.nanoTime();
		for (int i=0; i<octaminos.size(); i++)
		{
			Polyomino p = octaminos.get(i);
			List<List<Polyomino>> sols = Polyomino.Can_Cover_Own(p, k);
			if ( sols.size() >0)
			{
				index.add(i);
				
			}
		}
		System.out.println(" ");
		System.out.println("There are "+index.size()+ " octominos that can cover their own 4-dilate.");
		long stopTime5 = System.nanoTime();
		System.out.print("Execution time :");
		System.out.println((stopTime5 - startTime5)/1000000000.+" seconds");
		
		for (int j=0; j<index.size(); j++)
		{
			int i = index.get(j);
			List<List<Polyomino>> tilings = Polyomino.Can_Cover_Own(octaminos.get(i), k);
			List<Polyomino> tiling = tilings.get(0);
			
			if (j<4)
			{
				for (Polyomino p:tiling)
				{
					p.Translation(20*(j), 0);
					p.Dilatation(10);
					p.Updown(150);
					Random rand = new Random();
					float r = rand.nextFloat();
					float g = rand.nextFloat();
					float b = rand.nextFloat();
					Color randomColor = new Color(r, g, b);
					p.color = randomColor;
					image4.add(p);
					ig4.addEdges(p, 10);
				}
			}
			
			if (j>3 && j<=6)
			{
				for (Polyomino p:tiling)
				{
					p.Translation(20*(j-3), 0);
					p.Dilatation(10);
					p.Updown(450);
					Random rand = new Random();
					float r = rand.nextFloat();
					float g = rand.nextFloat();
					float b = rand.nextFloat();
					Color randomColor = new Color(r, g, b);
					p.color = randomColor;
					image4.add(p);
					ig4.addEdges(p, 10);
				}
			}
			
			if (j>6)
			{
				for (Polyomino p:tiling)
				{
					p.Translation(30*(j-7), 0);
					p.Dilatation(10);
					p.Updown(650);
					Random rand = new Random();
					float r = rand.nextFloat();
					float g = rand.nextFloat();
					float b = rand.nextFloat();
					Color randomColor = new Color(r, g, b);
					p.color = randomColor;
					image4.add(p);
					ig4.addEdges(p, 10);
				}
			}
		}
		
		
		ig1.Drawable(image1, 10, 0);
//Remove "//" in the next line to have a visual of some tilings	of fig5.1	
		//Image2dViewer im1 = new Image2dViewer(ig1);
		ig2.Drawable(image2, 10, 0);
//Remove "//" in the next line to have a visual of some tilings	of fig5.2	
		//Image2dViewer im2 = new Image2dViewer(ig2);
		ig4.Drawable(image4, 10, 0);
//Remove "//" in the next line to have a visual of some tilings	of Task8.3
		//Image2dViewer im4 = new Image2dViewer(ig4);
	}

}
