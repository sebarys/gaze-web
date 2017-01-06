package pl.kasprowski.fixation;

import java.awt.*;
import java.io.Serializable;
import java.text.DecimalFormat;

public class FPoint implements Serializable{
	private static final long serialVersionUID = 1L;
	public double x;
	public double y;
	
	
	
	public FPoint(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}


	public Point getPoint() {
		return new Point((int)x,(int)y);
	}

	@Override
	public String toString() {
		return new DecimalFormat("#.###").format(x)+" "+new DecimalFormat("#.###").format(y);	
		}
}
