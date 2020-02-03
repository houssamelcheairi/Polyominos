import java.awt.*;

public class ColoredPolygon {
	public Color color;
	public Polygon polygon;

	
	public ColoredPolygon(int[] xcoords, int[] ycoords, Color color) {
		polygon = new Polygon(xcoords, ycoords, xcoords.length);
		this.color   = color;
	}
	
	
	public static ColoredPolygon addColoredPolygon(ColoredPolygon cp, int[] xcoords, int[] ycoords) {
		int[] x = new int[cp.polygon.npoints + xcoords.length];
		int[] y = new int[cp.polygon.npoints + ycoords.length];
		
		for (int i=0; i<cp.polygon.npoints + xcoords.length; i++) 
		{
			if (i<cp.polygon.npoints)
			{
				x[i] = cp.polygon.xpoints[i];
				y[i] = cp.polygon.ypoints[i];
			}
			else
			{
				x[i] = xcoords[i - cp.polygon.npoints];
				y[i] = xcoords[i - cp.polygon.npoints];
			}
		}
		ColoredPolygon c = new ColoredPolygon(x,y,cp.color);
		return c;
	}
	

	
}
