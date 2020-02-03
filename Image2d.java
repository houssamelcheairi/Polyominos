import java.awt.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.JFrame;

// Manipulation for images
public class Image2d {
	private int width; // width of the image
	private int height; // height of the image
	private java.util.List<ColoredPolygon> coloredPolygons; // colored polygons in the image
	private java.util.List<Edge> edges; // edges to add to separate polygons

// Constructor that instantiates an image of a specified width and height
	public Image2d(int width, int height) {
		this.width = width;
		this.height = height;
		coloredPolygons = Collections.synchronizedList(new LinkedList<ColoredPolygon>());
		edges = Collections.synchronizedList(new LinkedList<Edge>());
	}

// Return the width of the image
	public int getWidth() {
		return width;
	}

// Return the height of the image
	public int getHeight() {
		return height;
	}

// Return the colored polygons of the image
	public java.util.List<ColoredPolygon> getColoredPolygons() {
		return coloredPolygons;
	}

// Return the edges of the image
	public java.util.List<Edge> getEdges() {
		return edges;
	}

// Create the polygon with xcoords, ycoords and color 
	public void addPolygon(int[] xcoords, int[] ycoords, Color color) {
		coloredPolygons.add(new ColoredPolygon(xcoords, ycoords, color));
	}
	
// Create the edge with coordinates x1, y1, x2, y2
	public void addEdge(int x1, int y1, int x2, int y2, int width) {
		edges.add(new Edge(x1, y1, x2, y2, width));
	}
	
// Clear the picture
	public void clear() {
		coloredPolygons = Collections.synchronizedList(new LinkedList<ColoredPolygon>());
		edges = Collections.synchronizedList(new LinkedList<Edge>());		
	}
	
	// Construct the squares out of the description of a polyomino
// This method helps us draw a polyomino p given the unit used
// for example, if the unit used is 1 then ladder = 1, 
	public static Image2d DrawablePolyo(Polyomino p, int ladder)
	{
		Image2d Ig = new Image2d(1000,1000);
		int n = p.squares.size();
		for (Point pp : p.squares)
		{
			int[] lx = new int[] {pp.x,pp.x,pp.x+ladder,pp.x+ladder};
			int[] ly = new int[] {pp.y,pp.y+ladder,pp.y+ladder,pp.y};
			Ig.addPolygon(lx, ly, p.color);
		}
		
		return Ig;
	}
	
	// method qui ne recrée rien, elle ajoute juste le polyo dans le dessin, ladder represents the length used for the edges of squares
//This method adds a polyomino drawing to an Image2d object, ladder being the unit used
	public void DrawablePolyoM(Polyomino p, int ladder)
	{
		int n = p.squares.size();
		for (Point pp : p.squares)
		{
			int[] lx = new int[] {pp.x,pp.x,pp.x+ladder,pp.x+ladder};
			int[] ly = new int[] {pp.y,pp.y+ladder,pp.y+ladder,pp.y};
			this.addPolygon(lx, ly, p.color);
		}
	}
	
	//ladder 1 is the length of edges, ladder 2 is the space left between two drawings
//this method helps us draw many polyominos with the unit used being ladder1 and the distance between two drawings ladder2
	public void Drawable(List<Polyomino> l,int ladder1, int ladder2)
	{
		int i =0;
		for (Polyomino polyo : l)
		{
			polyo.Translation(ladder2*i,0);
			this.DrawablePolyoM(polyo, ladder1);
			i = i+1;
		}
	}
		
	public static boolean IsIn(Point p, Polyomino polyo)
	{
		boolean bo =false;
		for (Point q : polyo.squares)
		{
			if (q.x == p.x && q.y == p.y)
			{
				bo = true;
			}
		}
		return bo;
	}
//Takes a polyomino and gives back a list of [x1,y1,x2,y2] corresponding to the edges of the polyomino
// k is the ladder (the unit), if the squares are of length 10, then k =10
	public static List<int[]> EdgesOfPolyo(Polyomino polyo, int k)
	{
		List<int[]> l = new ArrayList<int[]>();
		List<Point> squares = polyo.squares;
		
		for (Point p:squares)
		{
			//coté bas
			Point p_D = new Point(p.x,p.y-1*k);
			//coté haut
			Point p_U = new Point(p.x,p.y+1*k);
			//coté droite
			Point p_R = new Point(p.x+1*k,p.y);
			//coté gauche
			Point p_L = new Point(p.x-1*k,p.y);
			
			if (! IsIn(p_D, polyo))
			{
				int[] edge = new int[4];
				edge[0] = p.x;
				edge[1] = p.y;
				edge[2] = p.x+1*k;
				edge[3] = p.y;
				l.add(edge);
			}
			if (! IsIn(p_U, polyo))
			{
				int[] edge = new int[4];
				edge[0] = p.x;
				edge[1] = p.y+1*k;
				edge[2] = p.x+1*k;
				edge[3] = p.y+1*k;
				l.add(edge);
			}
			if (! IsIn(p_R, polyo))
			{
				int[] edge = new int[4];
				edge[0] = p.x+1*k;
				edge[1] = p.y;
				edge[2] = p.x+1*k;
				edge[3] = p.y+1*k;
				l.add(edge);
			}
			if (! IsIn(p_L, polyo))
			{
				int[] edge = new int[4];
				edge[0] = p.x;
				edge[1] = p.y;
				edge[2] = p.x;
				edge[3] = p.y+1*k;
				l.add(edge);
			}
			
		}
		return l;
	}
	
	
//adds the edges of a polyomino to the Image2d object
	public void addEdges(Polyomino polyo, int k)
	{
		List<int[]> eds = EdgesOfPolyo(polyo,k);
		for (int[] line:eds)
		{
			this.addEdge(line[0], line[1], line[2], line[3], 2);
		}
	}
	
//Unused code	
	public static ColoredPolygon homo(int x,int y,Color c, int coeff) {
		int X = coeff*x;
		int Y = coeff*y;
		int[] lX = new int[coeff*coeff];
		int[] lY = new int[coeff*coeff];
		for (int i=0; i<coeff; i++)
		{
			for (int j=0; j<coeff; j++)
			{
				lX[i+coeff*j] = X-j;
				lY[i+coeff*j] = Y-i;
			}
		}
		
		ColoredPolygon cp = new ColoredPolygon(lX,lY, c);
		return cp;

	}
	
	public static ColoredPolygon Homothetie(ColoredPolygon cp, int coeff) {
		Polygon p = cp.polygon;
		int[] x = p.xpoints;
		int[] y = p.ypoints;
		int n = p.npoints;
		Color c = cp.color;
		
		int[] lX = new int[0];
		int[] lY = new int[0];
		
		ColoredPolygon poly = new ColoredPolygon(new int[0],new int[0],cp.color);
		
		
		for (int i=0; i<n; i++)
		{
			int a = x[i];
			int b = y[i];
			ColoredPolygon cp0 = homo(a,b,c,coeff);
			ColoredPolygon poly0 = ColoredPolygon.addColoredPolygon(poly, cp0.polygon.xpoints,cp0.polygon.ypoints);
			poly.polygon = poly0.polygon;
			
			}
		return poly; 
			
		}
	
	public static void main(String[] args) {
		
	}
		
}



