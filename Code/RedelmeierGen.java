import java.util.*;
import java.awt.Color;
import java.math.*;
//This class implements the Redelmeier algorithm to generate fixed/free Polyominos
//We constructed this class with two attributes, liste_polyos helps us with the generation of the fixed polyominos,
//because it keeps a static list updated with new polyominos as long as the Redelmeier algorithms is being executed
//We also added an attribute "key" that will help us generate the free Polyominos. The idea is that once the fixed polyominos have been generated
//we will hash those fixed Polyominos using "Polyomino.HashInt(.,key)", i.e "Key" is the hashing number used by our hash methods.
public class RedelmeierGen {
	public static LinkedList<Polyomino> liste_polyos;
	static int key = 1000;
	
	
//This method generates the list of free polyominos of size n
	public static LinkedList<Polyomino> Redelmeier_GeneratorFree(int n)
	{
		LinkedList<Polyomino> fixed = Redelmeier_GeneratorFixed(n);
		HashMap<List<List<Integer>>,Polyomino> hashtab = new HashMap<List<List<Integer>>,Polyomino>();
		for (Polyomino p:fixed)
		{
			hashtab.put(Hash(p,key), p);
		}
		Collection<Polyomino> values = hashtab.values();
		LinkedList<Polyomino> free = new LinkedList<Polyomino>();
		
		for (Polyomino p:values)
		{
			free.add(p);
		}
		return free;
	}
	

//This method launchs the Redelmeier algorithm and adds all the fixed polyominos of size n to liste_polyos
	public static LinkedList<Polyomino> Redelmeier_GeneratorFixed(int n)
	{
		liste_polyos = new LinkedList<Polyomino>();
		Tableau t = new Tableau(-n+1,n,0,n);
		LinkedList<Point> untriedSet = new LinkedList<Point>();
		LinkedList<Point> triedSet   = new LinkedList<Point>();
		untriedSet.add(new Point(1,0));
		untriedSet.add(new Point(0,1));
		t.set(new Point(0,0), true);
		auxFixed(t,triedSet,untriedSet,1,n);
		return liste_polyos;
	}
	
	
//This method is the auxiliary recursive generative function for fixed polyominos of size n, p is incremented with recursion (keeps track of the depth of the recursion)
	public static void auxFixed(Tableau t, LinkedList<Point> triedSet, LinkedList<Point> untriedSet, int p, int n)
	{
		int size_t0 = triedSet.size();
		while(!untriedSet.isEmpty())
		{
			Point P = untriedSet.pop();
			t.set(P, true);
			if (n==1)
			{
				liste_polyos.add(t.getPolyomino());
			}
			
			if (p+1==n)
			{
				Polyomino polyo = t.getPolyomino();
				liste_polyos.add(polyo);
			}
			if (p+1<n)
			{
				LinkedList<Point> copieUntried = copy(untriedSet);
				for (Point neighbor :P.Neighbors())
				{
					if ((neighbor.x>0 && neighbor.y>=0 || neighbor.y>0)
							&&!IsIn(neighbor,untriedSet)
							&& !IsIn(neighbor,triedSet)
							&& !t.get(neighbor))
					{
						copieUntried.add(neighbor);
					}
						
				}
				auxFixed(t,triedSet,copieUntried,p+1,n);
			}
			triedSet.add(P);
			t.set(P, false);
		}
		while(triedSet.size() > size_t0) triedSet.pollLast();
	}
	
	

	
//This method implements a total order over lists of Point objects, the ordering used is the lexicographic order on the y-coordinates then x-coordinates
//Since comparisons using the latter order are equivalent to comparing the Hash codes we implemented, we use them (and thus the reason why we added the argument n)
	public static List<Point> sort(List<Point> l, int n)
	{
		List<Integer> liste = new ArrayList<Integer>();
		for ( Point p:l)
		{
			liste.add(Point.HashInt(p, n));
		}
		Collections.sort(liste, Collections.reverseOrder());
		List<Point> sorted = new ArrayList<Point>();
		
		for (int code :liste)
		{
			sorted.add(Point.DeHashInt(code, n));
		}
		return sorted;
	}
	

//This method compares two points p1,p2 and return true if p1<=p2 , false otherwise 
	public static boolean leq(Point p1, Point p2, int n)
	{
		return (Point.HashInt(p1, n) <= Point.HashInt(p2, n));
	}
	
	public static boolean leq_strict(Point p1, Point p2, int n)
	{
		return (Point.HashInt(p1, n) < Point.HashInt(p2, n));
	}
	

//This method compares two polyominos and returns true is polyo1<=polyo2 , false otherwise
	public static boolean leq(Polyomino polyo1, Polyomino polyo2, int n)
	{
		polyo1.squares = sort(polyo1.squares, n);
		polyo2.squares = sort(polyo2.squares, n);
		int n1 = polyo1.squares.size();
		int n2 = polyo2.squares.size();
		
		if (!(n1==n2))
		{
			return (n2 >= n1);
		}
		else
		{
			boolean bo = true;
			//keep becomes false if we encounter a point p2 > p1
			boolean keep = true;
			int i = 0;
			
			while (bo && keep && i<n1)
			{
				if ( leq(polyo1.squares.get(i),polyo2.squares.get(i),n) && 
						!(Point.equals(polyo1.squares.get(i), polyo2.squares.get(i))) )
				{
					keep=false;
				}
				if (Point.equals(polyo1.squares.get(i), polyo2.squares.get(i)))
				{
					i=i+1;
				}
				else
				{
					bo = false;
				}
			}
			return (bo || !keep);
		}
		
	}
	

	
// In order to generate the free polyominos we need to a hashing procedure that would transform any two polyominos representing the same free polyomino
// into the same hash value.
// The approach used here is quite simple, we first implement a Hash function that transforms a polyomino into a list of lists of size 2
// For example the Polyomino of size 2 with unit squares starting at (0,0) and (1,0) will be hashed as [[0,0],[1,0]].
// Next we associate a cononical free representation in the following way:
//    For a polyomino polyo, we look at its orbit (i.e symetries)
//    We find the least (in the sense of the ordering implemented above) polyomino amongst those in the orbit, call it Petit_Polyo
//    We then then consider the canonical representative of polyo as the Hash (in terms of lists of lists of pairs) of Petit_Polyo.

//Attention : in order for all of this to work we must only work with the canonical fixed representative otherwise the ordering implemented fails
	public static List<List<Integer>> Hash(Polyomino polyo, int n)
	{
		List<Polyomino> orbit =Polyomino.FormsFree(polyo);
		Polyomino rep = Polyomino.copy(polyo);
		for ( Polyomino q: orbit)
		{
			if (leq(q,rep, n))
			{
				rep = q;
			}
		}
		List<List<Integer>> liste = new ArrayList<List<Integer>>();
		for (Point p :rep.squares)
		{
			List<Integer> s = new ArrayList<Integer>();
			s.add(p.x);
			s.add(p.y);
			liste.add(s);
		}
		return liste;
	}
	
//Does what you think it does
	public static LinkedList<Point> copy(LinkedList<Point> l)
	{
		LinkedList<Point> ret = new LinkedList<Point>();
		for (Point P:l)
		{
			ret.add(new Point(P.x,P.y));
		}
		return ret;
	}
	
//Checks if a point P is in the linked list l
	public static boolean IsIn(Point P,LinkedList<Point> l)
	{
		boolean bo = false;
		for (Point q:l)
		{
			if (Point.equals(q,P))
			{
				bo = true;
				break;
			}
		}
		return bo;
	}
	
	
}
