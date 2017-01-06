package pl.kasprowski.mect.stimuli;

import com.theeyetribe.client.data.Point2D;

import java.util.ArrayList;
import java.util.List;

public class Aoi {
	String name;
	List<AoiPoint> points = new ArrayList<AoiPoint>();
	
	public Aoi() {
		
	}
	public Aoi(String name) {
		this.name = name;
	}
	public Aoi(String name, List<AoiPoint> points) {
		this.name = name;
		this.points = points;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public List<AoiPoint> getPoints() {
		return points;
	}
	public void setPoints(List<AoiPoint> points) {
		this.points = points;
	}
	@Override
	public String toString() {
		return getName() + "[" +points.size()+"]";
	}
	
	public Point2D[] getPoints2DforAoi() {
		List<AoiPoint> ap = getPoints();
		Point2D[] p = new Point2D[ap.size()];
		for(int i=0;i<ap.size();i++) {
			p[i] = new Point2D(ap.get(i).getX(),ap.get(i).getY());
		}
		return p;
	}
	
	public boolean isPointInAOI(Point2D p) {
		Point2D[] pp = getPoints2DforAoi();
		return isPointInPolygon(p, pp);
	}
		
	
	/**
	 * Sparwdza czy punkt wewn�trz polygonu
	 * @param p
	 * @param polygon
	 * @return
	 */
	static public boolean isPointInPolygon(Point2D p, Point2D[] polygon) {
		double minX = polygon[0].x;
		double maxX = polygon[0].x;
		double minY = polygon[0].y;
		double maxY = polygon[0].y;
		for (int i = 1; i < polygon.length; i++) {
			Point2D q = polygon[i];
			minX = Math.min(q.x, minX);
			maxX = Math.max(q.x, maxX);
			minY = Math.min(q.y, minY);
			maxY = Math.max(q.y, maxY);
		}
		if (p.x < minX || p.x > maxX || p.y < minY || p.y > maxY) {
			return false;
		}

		// http://www.ecse.rpi.edu/Homepages/wrf/Research/Short_Notes/pnpoly.html
		boolean inside = false;
		for (int i = 0, j = polygon.length - 1; i < polygon.length; j = i++) {
			if ((polygon[i].y > p.y) != (polygon[j].y > p.y)
					&& p.x < (polygon[j].x - polygon[i].x) * (p.y - polygon[i].y) / (polygon[j].y - polygon[i].y)
					+ polygon[i].x) {
				inside = !inside;
			}
		}

		return inside;
	}

	
}
