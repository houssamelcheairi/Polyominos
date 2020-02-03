
import java.awt.Color;
import java.util.*;
import java.util.List;

//This class implements grid objects used by the Redelmeier algorithm
public class Tableau {
	boolean[][] matrix;
	int xMin;
	int xMax;
	int yMin;
	int yMax;
	
	public Tableau(boolean[][] matrix, int xMin, int xMax, int yMin,int yMax)
	{
		this.matrix = matrix;
		this.xMin = xMin;
		this.xMax = xMax;
		this.yMin = yMin;
		this.yMax = yMax;
	}
	
	//creation de tableau avec une matrice vide
	public Tableau(int xMin,int xMax,int yMin,int yMax)
	{
		boolean[][] matrix = new boolean[xMax-xMin+1][yMax-yMin+1];
		for (int i=0; i<xMax-xMin+1;i++)
		{
			for (int j=0; j<yMax-yMin+1; j++)
			{
				matrix[i][j]=false;
			}
		}
		this.matrix = matrix;
		this.xMin = xMin;
		this.xMax = xMax;
		this.yMin = yMin;
		this.yMax = yMax;
	}
	
//Takes a point with possibly negative coordinates and gives back the corresponding point as coded in the matrix
	public Point Convert(Point P)
	{
		return (new Point(P.x - this.xMin,P.y-this.yMin));
	}
	
	public void set(Point P,boolean bo)
	{
		Point q = Convert(P);
		this.matrix[q.x][q.y]=bo;
	}
	
	public boolean get(Point P)
	{
		Point q = Convert(P);
		return this.matrix[q.x][q.y];
	}
	

//Returns the polyomino coded by the grid (black colored)
	public Polyomino getPolyomino()
	{
		List<Point> squares = new ArrayList<Point>();
		int lx = this.xMax-this.xMin+1;
		int ly = this.yMax-this.yMin+1;
		for (int i=0;i<lx;i++)
		{
			for (int j=0;j<ly;j++)
			{
				if (matrix[i][j])
				{
					Point P = new Point(i+xMin,j+yMin);
					squares.add(P);
				}
			}
		}
		return new Polyomino(squares,Color.black);
	
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
