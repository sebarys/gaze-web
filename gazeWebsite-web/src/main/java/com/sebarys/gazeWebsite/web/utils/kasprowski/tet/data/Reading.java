package com.sebarys.gazeWebsite.web.utils.kasprowski.tet.data;

import com.sebarys.gazeWebsite.web.utils.client.data.GazeData;
import com.sebarys.gazeWebsite.web.utils.client.data.Point2D;

import java.awt.*;
import java.io.Serializable;

public class Reading implements Comparable<Reading>,Serializable{
	private static final long serialVersionUID = 1L;
	private long timestamp;
	private String type;
	private Object data;
	public String getType() {
		return type;
	}

//	public void setType(String type) {
//		this.type = type;
//	}

	public long getTimestamp() {
		return timestamp;
	}
	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	public Reading(long timestamp, String type, Object data) {
		super();
		this.timestamp = timestamp;
		this.type = type;
		this.data = data;
	}
	//	public Class getType() {
	//		return data.getClass();
	//	}
	@Override
	public int compareTo(Reading r) {
		return (int)(timestamp - r.timestamp);
	}

	@Override
	public String toString() {
		if(data instanceof Point) {
			Point p = (Point)getData();
			return getTimestamp()+"\t"+getType()+"\t"+p.x+"\t"+p.y + "\tPoint";
		}
		if(data instanceof Point2D) {
			Point2D p = (Point2D)getData();
			return getTimestamp()+"\t"+getType()+"\t"+p.x+"\t"+p.y + "\tPoint2D";
		}
		if(data instanceof GazeData) {
			Point2D p = ((GazeData)getData()).smoothedCoordinates;
			return getTimestamp()+"\t"+getType()+"\t"+p.x+"\t"+p.y + "\tGazeData";
		}

		return getTimestamp()+"\t"+getType()+"\t"+getData();
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public Point2D p() {
		//System.out.println(this);
		if(!getType().equals("G")) throw new RuntimeException("p() may be called only for G!");
		Object o = getData();
		Point2D p;
		if(o instanceof GazeData) {
			GazeData g = (GazeData)o;
			p = g.smoothedCoordinates;
		}
		else
			p=new Point2D(((Point2D)o).x,((Point2D)o).y);
		return p;
	}
}
