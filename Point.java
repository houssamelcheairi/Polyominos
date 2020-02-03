import java.util.*;
import java.util.List;

//This class implements the representation of a point in the 2D plane with its x-coordinate and y-coordinate
//This class helps us represent the unit squares of the plane, the conventional representation we used consists of 
//representing each unit square with vertices (x,y),(x+1,y),(x,y+1),(x+1,y+1) with its left-down vertice : (x,y)

public class Point {
	public int x; //x coordinate
	public int y; //y coordinate
	
	public Point(int x,int y)
	{
		this.x =x;
		this.y=y;
	}
	
//This method takes a string with the following format : "(x,y)" and gives back the Point object it represents	
	public static Point StringtoPoint(String st)
	{
		Point p = new Point(0,0);
		int ind = st.indexOf(",");
		String x = st.substring(1, ind);
		String y = st.substring(ind+1, st.length() -1);
		int a = Integer.parseInt(x);
		int b = Integer.parseInt(y);
		p.x = a;
		p.y = b;
		return p;
	}

	

//This method gives back the 4 direct lattice neighbors of a Point (x,y)	
	public List<Point> Neighbors()
	{
		List<Point> liste = new ArrayList<Point>();
		liste.add(new Point(x+1,y  ));
		liste.add(new Point(x  ,y+1));
		liste.add(new Point(x-1,y  ));
		liste.add(new Point(x  ,y-1));
		return liste;
		
	}
	
//Codes a point (x,y) as n*n+n*(y+n/2)+(x+n/2) for a large enough n. 
	public static int HashInt(Point P, int n)
	{
		return (n*(P.y+n/2)+P.x+(n/2));
	}
	
//Decodes an integer n*n+n*y+x as Point(x,y)
	public static Point DeHashInt(int m, int n)
	{
		int x = (m)%n -n/2;
		int y =  ((m-x-n/2)/n)%n-n/2 ;
		return (new Point(x,y));
	}
	
//Creates a copy of a Point p
	public static Point Copy(Point p)
	{
		Point q = new Point(p.x,p.y);
		return q;
	}
//Checks if two points are equal
	public static boolean equals(Point p1,Point p2)
	{
		if (p1.x==p2.x & p1.y==p2.y)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
//Checks if a list of point contains all of the elements of the 2nd list, i.e if l2 is included in l1
	public static boolean CheckContain(List<Point>l1,List<Point>l2)
	{
		boolean bo = true;
		for (Point p:l2)
		{
			boolean bo_sub = false;
			for (Point q:l1)
			{
				if (equals(q,p))
				{
					bo_sub =true;
				}
			}
			if (bo_sub==false)
			{
				bo = false;
			}
		}
		return bo;
	}
	
	
//Checks if two lists of Points are the same no matter what order
	public static boolean equals(List<Point>l1,List<Point>l2)
	{
		if (CheckContain(l1,l2) & CheckContain(l2,l1))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	

//Computes the image of a Point after applying a dilatation with coefficent coeff with respect to the origin
	public void Dilatation( int coeff)
	{
		this.x = this.x *coeff;
		this.y = this.y *coeff;
	}
	
//This method gives the list of unit squares obtained after the dilatation of 1 unit square represented by the Point p
	public static List<Point> Dilatation(Point p,int coeff)
	{
		List<Point> squares = new ArrayList<Point>();
		int newx = p.x*coeff;
		int newy = p.y*coeff;
		for (int i=0; i<coeff; i++)
		{
			for (int j=0; j<coeff; j++)
			{
				squares.add(new Point(newx+i,newy+j));
			}
		}
		return squares;	
	}
	
	public void Translation(int coeffx, int coeffy)
	{
		this.x = this.x +coeffx;
		this.y = this.y +coeffy;
	}
	
//Computes the image of a Point after applying a rotation of 90 degrees with respect to omega (center of the rotation)
	public void Rotation(Point omega)
	{
		int a = omega.x + omega.y - this.y;
		int b = omega.y + this.x - omega.y;
		this.x =a;
		this.y =b;
	}
	
	
//Computes the image of a Point after applying a reflection with respect to the line of equation : y=cst

	public void yreflection(int cst)
	{
		this.y = this.y + (cst-this.y)*2 -1;
	}
	
//Computes the image of a Point after applying a reflection with respect to the line of equation : x=cst
	public void xreflection(int cst)
	{
		this.x = this.x + (cst-this.x)*2 -1;
	}
	
	

//This method computes the turns a Point upside down in the visual sense (what it actually does is a reflection wtr to the equation y=height)
//The reason behind coding this method is to get a correct visual of the Polyominos once drawn with the Class Image2d, indeed, the Java JFrame Class
//considers that the y-axis is directed downwards whereas in the mathematical conventions and in our analysis the y-axis is directed upwards 
	public void Updown(int height)
	{
		this.y = height-this.y;
	}
	

//This method checks if the integer n is included in liste	
	public static Boolean IsIn(List<Integer>liste, int n)
	{
		boolean bo = false;
		for (int m:liste)
		{
			if (m==n)
			{
				bo = true;
				break;
			}
		}
		return bo;
	}
	

//This method takes a integer list X, and a list of integer lists C and solves the exact cover problem (X,C) (if there is no solution this method returns a empty list)
//The algorithm used is the one described in Task 3.
	public static List<List<List<Integer>>> exactCover(List<Integer> X, List<List<Integer>> C)
	{
		List<List<List<Integer>>> Pset = new ArrayList<List<List<Integer>>>();
		if (X.isEmpty())
		{
			List<List<List<Integer>>> l = new ArrayList<List<List<Integer>>>();
			return l;
		}
		else
		{
			int x = X.get(0);

			
			for (List<Integer> S:C)
			{

				if (IsIn(S,x))
				{
					List<Integer> X_0 = new ArrayList<Integer>(X);
					List<List<Integer>> C_0 = new ArrayList<List<Integer>> (C);
					for (int y:S)
					{
						X_0.remove(X_0.indexOf(y));
						for(List<Integer> T:C)
						{
							if (IsIn(T,y))
							{
								C_0.remove(T);
							}
						}
					}
					if (exactCover(X_0,C_0).isEmpty())
					{
						List<List<Integer>> P = new ArrayList<List<Integer>>();
						if (X_0.isEmpty())
						{
							P.add(S);
							Pset.add(P);
						}
						
					}
					else
					{
						for (List<List<Integer>> P:exactCover(X_0,C_0))
						{
							P.add(S);
							Pset.add(P);
						}
					}
					
				}
			
			}
			
			
		}
		return Pset;
	}
	
	
//This method takes a non empty set X, some subsets C and return an element of X that appears the least in the sets of C
//It helps us implementing the heuristic version of the exact cover algorithm
	public static int HeuristicEC(List<Integer> X, List<List<Integer>> C)
	{
		HashMap<Integer,Integer> Occurences = new HashMap<Integer,Integer>();
		for (List<Integer> l: C)
		{
			for (int x:l)
			{
				Occurences.put(x,1);
			}
		}
		for (List<Integer> l: C)
		{
			for (int x:l)
			{
				int y = Occurences.get(x);
				Occurences.put(x,y+1);
			}
		}
		int min = 0;
		Collection<Integer> col = Occurences.values();
		for (int x : col)
		{
			if (x < min)
			{
				min = x;
			}
		}
		for (int x:X)
		{
			try
			{
			if (Occurences.get(x)==min)
			{
				return x;
			}
			}
			catch (NullPointerException e)
			{
				
			}
		}
		return X.get(0);
	}
	
//an Implementation of the exact cover algorithm with the least frequent element heuristic
	public static List<List<List<Integer>>> exactCover_Heuristic(List<Integer> X, List<List<Integer>> C)
	{
		List<List<List<Integer>>> Pset = new ArrayList<List<List<Integer>>>();
		if (X.isEmpty())
		{
			List<List<List<Integer>>> l = new ArrayList<List<List<Integer>>>();
			return l;
		}
		else
		{
			int x = HeuristicEC(X,C);

			
			for (List<Integer> S:C)
			{

				if (IsIn(S,x))
				{
					List<Integer> X_0 = new ArrayList<Integer>(X);
					List<List<Integer>> C_0 = new ArrayList<List<Integer>> (C);
					for (int y:S)
					{
						X_0.remove(X_0.indexOf(y));
						for(List<Integer> T:C)
						{
							if (IsIn(T,y))
							{
								C_0.remove(T);
							}
						}
					}
					if (exactCover(X_0,C_0).isEmpty())
					{
						List<List<Integer>> P = new ArrayList<List<Integer>>();
						if (X_0.isEmpty())
						{
							P.add(S);
							Pset.add(P);
						}
						
					}
					else
					{
						for (List<List<Integer>> P:exactCover(X_0,C_0))
						{
							P.add(S);
							Pset.add(P);
						}
					}
					
				}
			
			}
			
			
		}
		return Pset;
	}
	
//Returns all the integer subsets (seen as lists) of [1,2,...,n]
	public static List<List<Integer>> Subsets(int n)
	{
		if (n==1)
		{
			List<Integer> l = new ArrayList<Integer>();
			l.add(1);
			List<List<Integer>> ll = new ArrayList<List<Integer>>();
			List<Integer> l2 = new ArrayList<Integer>(); 
			ll.add(l);
			ll.add(l2);
			return ll;
		}
		else
		{
			List<List<Integer>> l_rec = Subsets(n-1);
			List<List<Integer>> liste = new ArrayList<List<Integer>> ();
			for (List<Integer> sub : l_rec)
			{
				liste.add(sub);
				List<Integer> sub_clone = new ArrayList<Integer>(sub);
				sub_clone.add(n);
				liste.add(sub_clone);
			}
			return liste;
		}
	}
	
//Returns the list of all integers between 1 and n : [1,2,...,n]
	public static List<Integer> Entiers(int n)
	{
		if (n==1)
		{
			List<Integer> l = new ArrayList<Integer>();
			l.add(1);
			return l;
		}
		else
		{
			List<Integer> l_rec = Entiers(n-1);
			l_rec.add(n);
			return l_rec;
		}
	}
	
//Returns all the integer subsets (seen as lists) of size k of the list [1,2,...,n]
	public static List<List<Integer>> Subsets(int n, int k)
	{
		if (n==k)
		{
			List<List<Integer>> l = new ArrayList<List<Integer>>();
			l.add(Entiers(n));
			return l;
		}
		if (k>1)
		{
			List<List<Integer>> l_rec1 = Subsets(n-1,k-1);
			List<List<Integer>> l_rec2 = Subsets(n-1,k);
			List<List<Integer>> l = new ArrayList<List<Integer>>();
			for (List<Integer> liste : l_rec1)
			{
				liste.add(n);
				l.add(liste);
			}
			l.addAll(l_rec2);
			return l;
		}
		else
		{
			List<List<Integer>> l = new ArrayList<List<Integer>>();
			for (int i=1;i<=n; i++)
			{
				List<Integer> s = new ArrayList<Integer>();
				s.add(i);
				l.add(s);
			}
			return l;
		}
	}
}