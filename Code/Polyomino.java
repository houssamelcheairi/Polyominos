import java.util.*;
import java.awt.*;
import java.util.List;
import java.math.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

//This class implements the Polyomino objects, the representation we chose to use is quite natural and helps us to draw these objects quite easily. We represent a Polyomino
//as list of Point objects, each of these points represents a unit square within the Polyomino, we also add a Color attribut to Polyomino object to track their color.

public class Polyomino {
	public java.util.List<Point> squares;
	public Color color;
	public double code;
	
	public Polyomino(java.util.List<Point> squares,Color color)
	{
		this.squares = squares;
		this.color = color;
		
	}
	
//Read a txt file representing polyominos (the unit used here is 10 pt) then draws them with distance ladder between themselves.
	public static void ReadDraw(String path)
	{
		Image2d ig  = new Image2d(400,200);
		List<Polyomino> image = new ArrayList<Polyomino>();
		
		try 
		{
			File doc = new File(path);
			Scanner myReader = new Scanner(doc);
			while (myReader.hasNextLine())
			{
				String data = myReader.nextLine();
				//System.out.println(data);
				Polyomino polyo  = StringtoPolyo(data, Color.red);
				polyo.Dilatation(10);
				polyo.Updown(100);
				image.add(polyo);
			}
			myReader.close();
			
			ig.Drawable(image, 10, 50);
			Image2dViewer im = new Image2dViewer(ig);
		}
		catch (FileNotFoundException e)
		{
			System.out.println("An error occured");
		}
	}
	
//The following 2 methods compute a canonical representation of a fixde Polyomino in the following way:
//We first search the lowest most left unit square in our Polyomino, then we apply a translation that sens
//the latter unit square to the origin
//The order of preference here is : order wtr to Y values then wtr to X values. 

//This method computes the lowest most left unit square within the Polyomino polyo
	public static Point DownLeft(Polyomino polyo)
	{
		List<Point> s = polyo.squares;
		if (s.size()==1)
		{
			return s.get(0);
		}
		else
		{
			Polyomino polyo_0 = new Polyomino(s.subList(1, s.size()) , polyo.color);
			Point p0 = s.get(0);
			Point p1 = LeftDown(polyo_0);
			if (p0.y < p1.y)
			{
				return p0;
			}
			else
			{
				if (p0.y == p1.y)
				{
					if (p0.x <= p1.x)
					{
						return p0;
					}
					else
					{
						return p1;
					}
				}
				else
				{
					return p1;
				}
			}
		}
	}

//This method applies the translation descrided above
	public void Translate_DownLeft()
	{
		Point p = DownLeft(this);
		this.Translation(-1*p.x, -1*p.y);
	}
	
//Creates a copy of a Polyomino
	public static Polyomino copy(Polyomino polyo)
	{
		List<Point> copie = new ArrayList<Point> ();
		for (Point p:polyo.squares)
		{
			copie.add(Point.Copy(p));
		}
		Polyomino p = new Polyomino(new ArrayList<Point>(copie),polyo.color);
		return p;
	}
	
//Checks if two Polyominos are equal (in graphical sense, not the object sense)
	public static boolean equals(Polyomino polyo1, Polyomino polyo2)
	{
		List<Point> l1 = polyo1.squares;
		List<Point> l2 = polyo2.squares;
		return Point.equals(l1,l2);
		
	}
	
	
//Turns the polyomino upside down wtr to the line y=height to match our visual of it in a normal (x,y) frame
	public void Updown(int height)
	{
		for(Point p:this.squares)
		{
			p.Updown(height);
		}
	}
	
	

//Computes the image of a Polyomino after applying a dilatation but expects us to change the value of the unit used.	
	public void Dilatation(int coeff)
	{
	for (Point u: this.squares)
		{
		u.Dilatation(coeff);
		}
	}
	

//This method computes the image of a Polyomino after applying a dilatation wtr to the origin
	public static Polyomino Dilatation(Polyomino polyo, int coeff )
	{
		List<Point> squares = new ArrayList<Point>();
		for (Point p:polyo.squares)
		{
			squares.addAll(Point.Dilatation(p,coeff));
		}
		Polyomino po =new Polyomino(squares, Color.black);
		return po;
	}
	

//This method computes the image of a Polyomino after applying a translation using the vector (coeffx,coeffy)
	public void Translation(int coeffx, int coeffy)
	{
		for (Point u: this.squares)
		{
			u.Translation(coeffx, coeffy);
		}
	}
	

//This method computes the image of a Polyomino after applying a 90-degrees rotation (in the direct sens) wtr to the Point omega  
	public void Rotation(Point omega)
	{
		for(Point p:this.squares)
		{
			p.Rotation(omega);
		}
	}
	
//This method computes the reflection of a Polyomino wtr to the line y=cst
	public void yReflection(int cst)
	{
		for (Point p: this.squares)
		{
			p.yreflection(cst);
		}
	}
		
//This method computes the reflection of a Polyomino wtr to the line x=cst 
	public void xReflection(int cst)
	{
		for (Point p: this.squares)
		{
			p.xreflection(cst);
		}
	}
	
	
	
	
	

//This method takes a Sting object with the format "[(a,b),(c,d)]" and returns the Polyomino corresponding to this list of Points
		public static Polyomino StringtoPolyo(String st, Color c)
	{
		//getting the (a,b),(c,d) part
		String s = st.substring(1, st.length()-1); 
		// list of "(a,b)" "(c,d)"
		String[] l = s.split(",");

		int n = l.length;
		//
		for (int i=0; i<n; i++)
		{
			String q = l[i];
			if (q.substring(0,1).equals(new String(" ")))
			{
				l[i]=q.substring(1);
			}
		}
		//
		String[] ll = new String[n/2];
		
		for (int i=0; i<n/2; i++)
		{
			String q1 = l[2*i];
			String q2 = l[2*i+1];
			ll[i] = (q1.concat(",")).concat(q2);
		}
		
		
		
		List<Point> lp = new ArrayList<Point> ();
		for (String q : ll)
		{
			lp.add(Point.StringtoPoint(q));
		}
		
		Polyomino polyo  = new Polyomino(lp,c);
		return polyo;
	}
	

	
//This method checks if the Point P (i.e the unit square it represents) is a part of the Polyomino polyo or not
	public static boolean IsInPolyo(Point p,Polyomino polyo)
	{
		boolean b= false;
		for (Point point: polyo.squares)
		{
			if (point.x == p.x & point.y == p.y)
			{
				b=true;
				break;
			}
		}
		return b;
	}
		
//This method takes on a Polyomino then adds to it a unit square whenever it is possible to form a new Polyomino and returns the list of all possible such Polyominos
	public static List<Polyomino> addSquare(Polyomino polyo)
	{
		List<Polyomino> liste = new ArrayList<Polyomino>();
		for (Point p: polyo.squares)
		{
			
			Point W = new Point(p.x-1,p.y  );
			Point S = new Point(p.x  ,p.y-1);
			Point E = new Point(p.x+1,p.y  );
			Point N = new Point(p.x  ,p.y+1);
			if (!IsInPolyo(W,polyo))
			{
				Polyomino polyoW = copy(polyo);
				polyoW.squares.add(W);
				liste.add(polyoW);
			}
			if (!IsInPolyo(S,polyo))
			{
				Polyomino polyoS = copy(polyo);
				polyoS.squares.add(S);
				liste.add(polyoS);
			}
			if (!IsInPolyo(E,polyo))
			{
				Polyomino polyoE = copy(polyo);
				polyoE.squares.add(E);
				liste.add(polyoE);
			}
			if (!IsInPolyo(N,polyo))
			{
				Polyomino polyoN = copy(polyo);
				polyoN.squares.add(N);
				liste.add(polyoN);
			}
		}
		return liste;
	}
	
//This method takes a list of Polyominos and creates new ones by adding squares whenever possible
	public static List<Polyomino> addSquareList(List<Polyomino> l)
	{
		List<Polyomino> liste = new ArrayList<Polyomino> ();
		for(Polyomino polyo: l)
		{
			List<Polyomino> polyo_added = addSquare(polyo);
			liste.addAll(polyo_added);
		}
		return liste;
	}
	
//This method takes on a polyomino and gives back its point that is the most left lowest (it is different from DownLeft method since the order of priority here is different)
	public static Point LeftDown(Polyomino polyo)
	{
		List<Point> s = polyo.squares;
		if (s.size()==1)
		{
			return s.get(0);
		}
		else
		{
			Polyomino polyo_0 = new Polyomino(s.subList(1, s.size()) , polyo.color);
			Point p0 = s.get(0);
			Point p1 = LeftDown(polyo_0);
			if (p0.x < p1.x)
			{
				return p0;
			}
			else
			{
				if (p0.x == p1.x)
				{
					if (p0.y <= p1.y)
					{
						return p0;
					}
					else
					{
						return p1;
					}
				}
				else
				{
					return p1;
				}
			}
		}
	}
	
	
	
	
//This method takes a Polyomino and translates it so that its bottom left square is O,O (left is more valuable than bottom)
	public void Translate_LeftDown()
	{
		Point p = LeftDown(this);
		this.Translation(-1*p.x, -1*p.y);
	}
	
	
//This method takes two Polyominos and says if the two are only different by a translation
	public static Boolean Translation_different(Polyomino polyo1, Polyomino polyo2)
	{
		polyo1.Translate_LeftDown();
		polyo2.Translate_LeftDown();
		return equals(polyo1,polyo2);
	}
	
//This method takes a list of Polyominos with possible repetitions and gives back the quotient list with only the fixed ones appearing once
	public static List<Polyomino> NoRep(List<Polyomino> liste_rep)
	{
		for (Polyomino polyo:liste_rep)
		{
			polyo.Translate_LeftDown();
		}
		List<Polyomino> liste = new ArrayList<Polyomino>();
		if (liste_rep.size()<=1)
		{
			return liste_rep;
		}
		else
		{
			liste.add(liste_rep.get(0));
			for (Polyomino polyo:liste_rep)
			{
				//checks if the same polyo is already in the list
				Boolean seen = false;
				for (Polyomino polyo_seen:liste)
				{
					if (equals(polyo_seen,polyo))
					{
						seen = true;
					}
				}
				if (!seen)
				{
					liste.add(polyo);
				}
				
			}
		}
		return liste;
	}
	
//This method takes a list of Polyominos and gives back the list of the free ones (differ in a rotation)
	public static List<Polyomino> NoRepFree(List<Polyomino> liste_rep)
	{
		for (Polyomino polyo:liste_rep)
		{
			polyo.Translate_LeftDown();
		}
		List<Polyomino> liste = new ArrayList<Polyomino>();
		if (liste_rep.size()<=1)
		{
			return liste_rep;
		}
		else
		{
			liste.add(liste_rep.get(0));
			for (Polyomino polyo:liste_rep)
			{
				//checks if the same polyo is already in the liste
				Boolean seen = false;
				for (Polyomino polyo_seen:liste)
				{
					
					for (int k=0; k<4; k++)
					{
						Point omega = new Point(0,0);
						polyo.Rotation(omega);
						polyo.Translate_LeftDown();
						if (equals(polyo_seen,polyo))
						{
							seen = true;
						}
					}
					//now the same but after a Y-axis reflection
					polyo.yReflection(0);
					for (int k=0; k<4; k++)
					{
						Point omega = new Point(0,0);
						polyo.Rotation(omega);
						polyo.Translate_LeftDown();
						if (equals(polyo_seen,polyo))
						{
							seen = true;
						}
					}
				}
				if (!seen)
				{
					liste.add(polyo);
				}
				
			}
		}
		return liste;
	}
	
	
//Generates all fixed Polyominos of length k recursively
	public static List<Polyomino> Generate_fixed(int k, Color c)
	{
		if (k==1)
		{
			List<Polyomino> liste = new ArrayList<Polyomino>();
			Point p0 = new Point(0,0);
			List<Point> sq = new ArrayList<Point>();
			sq.add(p0);
			Polyomino polyo = new Polyomino(sq,c);
			liste.add(polyo);
			return liste;
		}
		else
		{
			//the recursive list doesn t contain repetitions
			List<Polyomino> liste_rec = Generate_fixed(k-1,c);
			List<Polyomino> liste_rep = addSquareList(liste_rec);
			//we need to eleminate repetitions here
			List<Polyomino> liste = new ArrayList<Polyomino>();
			return NoRep(liste_rep);
			
		}
		
	}
	
//Generates all free Polyominos of length k recursively
		public static List<Polyomino> Generate_free(int k, Color c)
		{
			if (k==1)
			{
				List<Polyomino> liste = new ArrayList<Polyomino>();
				Point p0 = new Point(0,0);
				List<Point> sq = new ArrayList<Point>();
				sq.add(p0);
				Polyomino polyo = new Polyomino(sq,c);
				liste.add(polyo);
				return liste;
			}
			else
			{
				//the recursive list doesn contain repetitions
				List<Polyomino> liste_rec = Generate_free(k-1,c);
				List<Polyomino> liste_rep = addSquareList(liste_rec);
				//we need to eleminate repetitions here
				List<Polyomino> liste = new ArrayList<Polyomino>();
				return NoRepFree(liste_rep);
				
			}
			
		}
		
		

//This method implements a canonical representation of a free Polyomino. We proceed as follows:
//First we translate the Polyomino to the origin using Translate_DownLeft
//Second we generate all of the Polyomino symetries and translate them to the origin aswell
//Third we compute for each of these symetries their Hash code using the method Hash
//Finally we compare the Hash codes of the symetries (lexicographic order (x,y)) and take the least of them
//as the canonical represented (in the free sense)
		public static List<Integer> HashInt(Polyomino polyo, int n)
		{
			List<Integer> liste = new ArrayList<Integer>();
			for (Point P: polyo.squares)
			{
				liste.add(Point.HashInt(P,n));
			}
			return liste;
		}
		
//Decodes a list of integers into the Polyomino it represents (color taken black by default)
		public static Polyomino DeHashInt(List<Integer> code, int n)
		{
			List<Point> squares = new ArrayList<Point>();
			for (int m: code)
			{
				squares.add(Point.DeHashInt(m,n));
			}
			Polyomino polyo = new Polyomino(squares, Color.black);
			return polyo;
		}
		
		
//Transforms a Polyomino tiling problem into an exact cover problem X,S
		public static ExactCover TilingToExact(Polyomino GroundSet, List<Polyomino> Tiles, int n)
		{
			ExactCover EC = new ExactCover();
			List<Integer> X = new ArrayList<Integer>();
			List<List<Integer>> C = new ArrayList<List<Integer>>();
			X = HashInt(GroundSet, n);
			for (Polyomino polyo : Tiles)
			{
				C.add(HashInt(polyo, n));
			}
			EC.X = X;
			EC.C = C;
			
			return EC;
			
		}
		
		
		
//Takes a tiling problem, converts it to an exactCover problem, solves it with DancingLinks, and gives back the list of solutions as lists of polyominos
	public static List<List<Polyomino>> TilingSolver(Polyomino GroundSet, List<Polyomino> Tiles, int n)
	{
		ExactCover EC = TilingToExact(GroundSet, Tiles, n);
		
		DancingLinks DL = new DancingLinks();
		DL.exactCoverToDL(EC.X, EC.C);
		
		List<List<Data>> Partitions = DancingLinks.exactCover(DL);
		
		List<List<List<Integer>>> liste = new ArrayList<List<List<Integer>>>();
		for (List<Data> sol: Partitions)
		{
			liste.add(DancingLinks.DataToSubset(sol, EC.C));
		}
		
		List<List<Polyomino>> liste_polyos = new ArrayList<List<Polyomino>>();
		
		for (List<List<Integer>> l:liste)
		{
			List<Polyomino> l_inter = new ArrayList<Polyomino>();
			for (List<Integer> l_sub : l)
			{
				l_inter.add( DeHashInt(l_sub, n));
			}
			liste_polyos.add(l_inter);
		}
		
		return liste_polyos;
	}
	
//This method takes a polyomino and returns the list of its possible symetries
	public static List<Polyomino> FormsFree(Polyomino polyo)
	{
		List<Polyomino> forms = new ArrayList<Polyomino>();
		Polyomino copy = copy(polyo);
		for (int i=0;i<4; i++)
		{
			Polyomino copy2 = copy(copy);
			//copy2.Rotation(new Point(0,0));
			copy2.Translate_LeftDown();
			forms.add(copy2);
			copy.Rotation(new Point(0,0));
		}
		copy.Translate_LeftDown();
		copy.yReflection(0);
		for (int i=0;i<4; i++)
		{
			Polyomino copy2 = copy(copy);
			//copy2.Rotation(new Point(0,0));
			copy2.Translate_LeftDown();
			forms.add(copy2);
			copy.Rotation(new Point(0,0));
		}
		List<Polyomino> forms_clean = new ArrayList<Polyomino>();
		for (Polyomino p:forms)
		{
			boolean seen = false;
			for (Polyomino q:forms_clean)
			{
				if (equals(p,q))
				seen = true;
			}
			if (!seen)
			{
				forms_clean.add(p);
			}
		}
		return forms_clean;
	}
	
//takes a free polyomino and gives the list of its possibles different forms (doesn t work)
	public static List<Polyomino> FormsOfFree(Polyomino polyo)
	{
		List<Polyomino> forms = new ArrayList<Polyomino>();
		polyo.Translate_LeftDown();
		Polyomino copy = copy(polyo);
		forms.add(copy(polyo));
		
		copy.Rotation(new Point(0,0));
		copy.Translate_LeftDown();
		while (!(equals(copy,polyo)))
		{
			System.out.println(1);
			forms.add(copy(copy));
			copy.Rotation(new Point(0,0));
			copy.Translate_LeftDown();
		}
		
		copy.yReflection(0);
		copy.Translate_LeftDown();
		Polyomino copy2 = copy(polyo);
		copy2.Rotation(new Point(0,0));
		copy2.Rotation(new Point(0,0));
		copy2.Translate_LeftDown();
		Polyomino copy3 = copy(polyo);
		copy3.Rotation(new Point(0,0));
		copy3.Translate_LeftDown();
		if (!(equals(copy,polyo)) && !(equals(copy2,polyo)) && !(equals(copy3,polyo)))
		{	
			forms.add(copy(copy));
			polyo.yReflection(0);
			polyo.Translate_LeftDown();
			copy.Rotation(new Point(0,0));
			copy.Translate_LeftDown();
			
			while(!equals(copy,polyo))
			{
				System.out.println(2);
				forms.add(copy(copy));
				copy.Rotation(new Point(0,0));
				copy.Translate_LeftDown();
			}
		}
		return forms;
	}
//this method also solves tiling problems but adds the constraint of using each tile exactly once
//we take m>>n, and use m to Hash the fictional elements added to represent the constraints
//Tiles_base is the quotient group of Tiles, consisting of a representative of each class	
	
//About the hash of the constraint : The idea is to take a very big integer m and represent the constraints as integers
// m, m+1, m+2, ..., m+k. Once the solution found we can easily distinguish the integers representing unit squares and
//the integers representing the constraint (the latter are way bigger than the integers hashing unit squares) 

	public static List<List<Polyomino>> TilingSolverOnce(Polyomino GroundSet,List<Polyomino> Tiles_base ,int n, int m)
	{
		List<List<Polyomino>> ListOfExact = new ArrayList<List<Polyomino>>();
		for (Polyomino polyo: Tiles_base)
		{
			List<Polyomino> forms = FormsFree(polyo);
			List<Polyomino> l = new ArrayList<Polyomino>();
			for (Polyomino q:forms)
			{
				l.addAll(Translations_Inside(GroundSet,q));
			}
			
			ListOfExact.add(l);
		}
		//System.out.println(ListOfExact.size());
		List<Integer> X = HashInt(GroundSet, n);
		List<List<Integer>> C = new ArrayList<List<Integer>>();
		for (int i=0; i<Tiles_base.size(); i++)
		{
			X.add(m+i);
			
			for (Polyomino polyo: ListOfExact.get(i))
			{
				//System.out.println(ListOfExact.get(i).size());
				List<Integer> l = HashInt(polyo, n);
				l.add(m+i);
				C.add(l);
			}
		}
		ExactCover EC = new ExactCover();
		EC.X = X;
		EC.C = C;
		
		
		//constructing the solutions
		DancingLinks DL = new DancingLinks();
		DL.exactCoverToDL(EC.X, EC.C);
		
		List<List<Data>> Partitions = DancingLinks.exactCover(DL);
		
		List<List<List<Integer>>> liste = new ArrayList<List<List<Integer>>>();
		for (List<Data> sol: Partitions)
		{
			liste.add(DancingLinks.DataToSubset(sol, EC.C));
		}
		
		List<List<Polyomino>> liste_polyos = new ArrayList<List<Polyomino>>();
		
		for (List<List<Integer>> l:liste)
		{
			List<Polyomino> l_inter = new ArrayList<Polyomino>();
			for (List<Integer> l_sub : l)
			{
				List<Integer> cleaned = removeBig(l_sub,m);
				l_inter.add( DeHashInt(l_sub, n));
			}
			liste_polyos.add(l_inter);
		}
		
		return liste_polyos;
	}
	
//This methods looks at a list, and takes away all of its elements that are bigger than m
	public static List<Integer> removeBig(List<Integer> l, int m)
	{
		List<Integer> liste = new ArrayList<Integer>();
		for ( int x:l)
		{
			if (x<m)
			{
				liste.add(x);
			}
		}
		return liste;
	}
	
	
//Task 8, 1st polyomino,
	public static Polyomino Figure5_1()
	{
		String s1 = "[(0,5),(0,6),(0,7),(0,8),(0,9),";
		String s2 = "(1,4),(1,5),(1,6),(1,7),(1,8),(1,9),";
		String s3 = "(2,4),(2,5),(2,6),(2,7),(2,8),";
		String s4 = "(3,3),(3,4),(3,5),(3,6),(3,7),(3,8),";
		String s5 = "(4,3),(4,4),(4,5),(4,6),(4,7),";
		String s6 = "(5,2),(5,3),(5,4),(5,5),(5,6),(5,7),";
		String s7 = "(6,2),(6,3),(6,4),(6,5),(6,6),";
		String s8 = "(7,1),(7,2),(7,3),(7,4),(7,5),(7,6),";
		String s9 = "(8,1),(8,2),(8,3),(8,4),(8,5),";
		String s10= "(9,0),(9,1),(9,2),(9,3),(9,4),(9,5),";
		String s11= "(10,0),(10,1),(10,2),(10,3),(10,4)]";
		
		String s = "";
		s =s.concat(s1);
		s =s.concat(s2);
		s =s.concat(s3);
		s =s.concat(s4);
		s =s.concat(s5);
		s =s.concat(s6);
		s =s.concat(s7);
		s =s.concat(s8);
		s =s.concat(s9);
		s =s.concat(s10);
		s =s.concat(s11);
		Polyomino fig5a = Polyomino.StringtoPolyo(s, Color.red);
		return fig5a;
	}
//Task 8, 2nd polyomino, the one in the middle
//for the figure 5.2  take n = 5 (number of levels in the drawing)
	public static Polyomino Figure5_2(int n)
	{
		List<Point> squares = new ArrayList<Point>();
		if (n==1)
		{
			squares.add(new Point(0,0));
			squares.add(new Point(1,0));
			squares.add(new Point(0,1));
			squares.add(new Point(1,1));
			return (new Polyomino(squares, Color.black));
		}
		else
		{
			Polyomino p_rec = Figure5_2(n-1);
			p_rec.Translation(1, 2);
			//creation of the new base
			List<Point> base = new ArrayList<Point>();
			for (int i=0; i<2*n; i++)
			{
				base.add(new Point(i,0));
				base.add(new Point(i,1));
			}
			for (Point p:base)
			{
				p_rec.squares.add(p);
			}
			return p_rec;
		}
	}
	
//Generates the 3rd figure of Task8 (the star shaped one)
	public static Polyomino Figure5_3()
	{
		List<Point> squares = new ArrayList<Point>();
		for (int y=0; y<5; y++)
		{
			for (int x=0; x<5-y; x++)
			{
				squares.add(new Point(x   ,  y));
				squares.add(new Point(-1*x-1,   y));
				squares.add(new Point(x   ,-1*y-1));
				squares.add(new Point(-1*x-1,-1*y-1));
				
			}
		}
		Polyomino p = new Polyomino(squares, Color.black);
		p.Translate_LeftDown();
		return p;
	}
//Says if a polyomino is entirely covered by some ground set (i.e is a smaller polyo of this groundset)
	public static boolean IsInside(Polyomino GroundSet, Polyomino polyo)
	{
		boolean bo = true;
		for (Point p : polyo.squares)
		{
			if (!IsInPolyo(p,GroundSet))
			{
				bo = false;
				//break;
			}
		}
		return bo;
	}
	
//This method takes a polyomino GroundSet (starting at 0,0), and a smaller polyomino Polyo,
//it gives back the list of translations of Polyo that are inside GroundSet
	public static List<Polyomino> Translations_Inside(Polyomino GroundSet, Polyomino polyo)
	{
		//first, setting the bounds for the squares of GroundSet
		int xMin=0;
		int xMax=0;
		int yMin=0;
		int yMax=0;
		for (Point p:GroundSet.squares)
		{
			if (p.x>xMax)
			{
				xMax = p.x;
			}
			if (p.x<xMin)
			{
				xMin = p.x;
			}
			if (p.y>yMax)
			{
				yMax = p.y;
			}
			if (p.y<yMin)
			{
				yMin = p.y;
			}		
		}
		List<Polyomino> translations = new ArrayList<Polyomino>();
		for (int x=xMin; x<xMax+1; x++)
		{
			for (int y=yMin; y<yMax+1; y++)
			{
				polyo.Translation(x, y);
				if (IsInside(GroundSet, polyo))
				{
					Polyomino polyo_copy = copy(polyo);
					translations.add(polyo_copy);
				}
				polyo.Translation(-1*x,-1*y);
			}
		}
		return translations;
		
	}
	
//Same method but does the job for a list of polyominos
	public static List<Polyomino> Translations_Inside(Polyomino GroundSet, List<Polyomino> polyos)
	{
		List<Polyomino> all_trans = new ArrayList<Polyomino>();
		for (Polyomino polyo: polyos)
		{
			List<Polyomino> trans = Translations_Inside(GroundSet, polyo);
			for (Polyomino p : trans)
			{
				all_trans.add(p);
			}
		}
		return all_trans;
	}
	
//Gives back the polyomino code of a n*m rectangle, n is horizontal, m vertical
	public static Polyomino Rectangle(int n, int m)
	{
		List<Point> squares = new ArrayList<Point>();
		for (int x=0; x<n; x++)
		{
			for (int y=0; y<m; y++)
			{
				Point p = new Point(x,y);
				squares.add(p);
			}
		}
		Polyomino polyo = new Polyomino(squares, Color.black);
		return polyo;
	}
	
//Task 8, takes a polyomino P, and an integer k and gives back the list of possible tilings of k*P by Ps
	public static List<List<Polyomino>> Can_Cover_Own(Polyomino polyo, int k)
	{
		Polyomino Ground = Dilatation(polyo, k);
		List<Polyomino> Tiles = new ArrayList<Polyomino>();
		List<Polyomino> forms = FormsFree(polyo);
		for (Polyomino p:forms)
		{
			Tiles.addAll(Translations_Inside(Ground, p));
		}
		//List<Polyomino> candidates = Translations_Inside(Ground, polyo);
		List<List<Polyomino>> sols = TilingSolver(Ground, Tiles, 100);
		return sols;
	}
}
