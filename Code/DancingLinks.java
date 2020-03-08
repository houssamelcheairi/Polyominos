import java.util.*;


//This class implements the dancing links data structure. We define a dancing links object by its H column (see task example)
public class DancingLinks {
	public static Column H;

	public void DancingLinks()
	{
		this.H = null;
	}
	

	
//Main method, takes a exact cover problem (X,C) and returns the solutions

	public static List<List<List<Integer>>> ExactCoverSolver(List<Integer> X,List<List<Integer>> C)
	{
		DancingLinks DL = new DancingLinks();
		DL.exactCoverToDL(X, C);
		List<List<Data>> l = DancingLinks.exactCover(DL);
		List<List<List<Integer>>> solutions = new ArrayList<List<List<Integer>>>();
		for (List<Data> a_sol:l)
		{
			solutions.add(DataToSubset(a_sol,C));
		}
		return solutions;
		
	}
//Prints a nice visual of the incidence matrix
	public static void Printer(int[][] M)
	{
		int rows = M.length;
		int cols = M[0].length;
		for (int i=0;i<rows;i++)
		{
			System.out.println(" ");
			for (int j=0; j<cols; j++)
			{
				System.out.print(M[i][j]);
				System.out.print(" ");
			}
		}
	}
	

//Takes a exact cover problem (X,S) and returns its incidence matrix
	public static int[][] matrixOfExact(List<Integer> X, List<List<Integer>> S)
	{
		int n = X.size();
		int m = S.size();
		int[][] matrix = new int[m][n];
		for (int i=0;i<m;i++)
		{
			for (int j=0;j<n;j++)
			{
				matrix[i][j]=0;
			}
		}
		for (int i=0;i<m;i++)
		{
			for (int k : S.get(i))
			{
				matrix[i][X.indexOf(k)]=1;
			}
		}
		return matrix;
	}
	
	

//Converts an exact cover problem incidence matrix into a dancing links data
	public static void Convert(int[][] matrix)
	{
		int m = matrix.length;
		int n = matrix[0].length;
		
		//we add a row so that we can add the columns data type
		Data[][]DLmatrix = new Data[m+1][n];

		//adding in the columns:
		for (int i=0;i<n;i++)
		{
			Column C = new Column();
			C.S = 0;
			C.N = i;
			C.C = C;
			DLmatrix[0][i]=C;
			//creating the column of DLinks coresponding to the column i
			for (int k=1; k<m+1;k++)
			{
				Data d = new Data();
				d.C = C;
				DLmatrix[k][i] = d;
				d.set = k-1;
			}
		}
		
		//completes the links : U,D,L,R for the data objects
		for (int i=0;i<n;i++)
		{
			for (int j=0;j<m+1;j++)
			{
				Data d   = DLmatrix[j][i];
				Data d_U = DLmatrix[Math.floorMod(j-1, m+1)][i];
				Data d_D = DLmatrix[Math.floorMod(j+1,m+1)][i];
				Data d_L = DLmatrix[j][Math.floorMod(i-1,n)];
				Data d_R = DLmatrix[j][Math.floorMod(i+1,n)];
				
				d.U = d_U;
				d.D = d_D;
				d.L = d_L;
				d.R = d_R;
				
			}
		}
		
		//computes the Column.S part for each column object
		for (int k=0;k<n;k++)
		{

			int Compt = 0;
			for (int i=1;i<m+1;i++)
			{
				if (matrix[i-1][k]==1)
				{
					Column C = (Column) DLmatrix[0][k];
					C.S =C.S +1;
				}
				else
				//we delete the data object as shown in figure 3
				{
					Data d_empty = DLmatrix[i][k];
					d_empty.U.D = d_empty.D;
					d_empty.D.U = d_empty.U;
					d_empty.R.L = d_empty.L;
					d_empty.L.R = d_empty.R;
				}
			}
		}
		
		//adding the "Father" column H as in figure 3
		//creating H and adding its links alone 
		H = new Column();
		H.S =-1;
		H.N =-1;
		H.R = DLmatrix[0][0];
		H.L = DLmatrix[0][n-1];
		//adding H to the general data
		H.L.R = H;
		H.R.L = H;
		
	}
	
//converts an exact cover problem (X,C) into it s dancing links data
	public void exactCoverToDL(List<Integer> X, List<List<Integer>> S)
	{
		int[][] matrix = matrixOfExact(X,S);
		Convert(matrix);
	}
//Covers column x
	public static void coverColumn(Column x)
	{
		x.R.L = x.L;
		x.L.R = x.R;
		for (Data t = x.D; !(t == x); t = t.D) {
			for (Data y = t.R; !(y == t); y = y.R) {
				y.D.U = y.U;
				y.U.D = y.D;
				y.C.S--;
			}
		}
	}
//Uncovers column x
	public static void uncoverColumn(Column x)
	{
		x.R.L = x;
		x.L.R = x;
		for (Data t = x.U; !(t == x); t = t.U) {
			for (Data y = t.L; !(y == t); y = y.L) {
				y.D.U = y;
				y.U.D = y;
				y.C.S++;
			}
		}
	}
	


//Gives back a column x with x.S minimal amongst all columns
	public static Data leastFrequent(Data H) {
		int n = Integer.MAX_VALUE;
		Data nul = null;
		for (Data x = H.R; !(x == H); x = x.R)
		{
			if (x.C.S < n) 
			{
				nul = x;
				n = x.C.S;
			}
		}
		if (n == Integer.MAX_VALUE) 
		{
			return H.R;
		}
		return nul;
	}
	
	//the main method that solves the exactCover problem, gives back a list of partitions
	//a partition is a list of data objects, each list should represent one of the S_i
	// we can convert these partitions into natural sets later
	public static List<List<Data>> exactCover(DancingLinks DL)
	{
		Data H = DL.H;
		
		//System.out.println("cas vide :");
		//System.out.println(H.R == H);
		
		if (H.R == H)
		{
			List<List<Data>> P = new ArrayList<List<Data>>();
			P.add(new ArrayList<Data>());
			return P;
		}
		else
		{
			//System.out.println("cas non vide ");
			List<List<Data>> P = new ArrayList<List<Data>>();
			//minimal element x
			Data x = leastFrequent(H);
			//System.out.println("l'element  couvert:");
			//System.out.println("set:");
			//System.out.println(x.set);
			//covering the x
			coverColumn(x.C);
			
			for(Data t=x.U; !(t==x); t=t.U)
			{
				for (Data y=t.L;!(y==t); y=y.L)
				{
					coverColumn(y.C);
				}
				
				for (List<Data> p : exactCover(DL))
				{
					p.add(t);
					P.add(p);
				}
				
				for (Data y=t.R; !(y==t); y=y.R)
				{
					uncoverColumn(y.C);
				}
			}
			
			uncoverColumn(x.C);
			return P;
			
		}
	}
	
//takes a list of Data and gives the subset of C it corresponds to
	public static List<List<Integer>> DataToSubset(List<Data> partition, List<List<Integer>> S)
	{
		List<List<Integer>> liste = new ArrayList<List<Integer>>();
		
		for (Data d: partition)
		{
			liste.add( S.get(d.set) );
		}
		return liste;
	}
	
//similar to DataToSubset but gives back the list of subsets indexes 
	public static List<Integer> DataToSubsetIndexes(List<Data> partition)
	{
		List<Integer> liste = new ArrayList<Integer>();
		for (Data d: partition)
		{
			liste.add( d.set );
		}
		return liste;
	}
	
}
