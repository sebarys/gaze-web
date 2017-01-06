package pl.kasprowski.fixation;

import java.io.Serializable;
import java.text.DecimalFormat;

public class Fixation implements Serializable{
	private static final long serialVersionUID = 1L;
	public FPoint p;
	long start;
	long length;

	public Fixation() {	}
	
	public Fixation(FPoint p, long start, long length) {
		super();
		this.p = p;
		this.start = start;
		this.length = length;
	}
	public void setP(FPoint p) {
		this.p = p;
	}
	public FPoint getP() {
		return p;
	}
	public long getStart() {
		return start;
	}
	public long getLength() {
		return length;
	}
	
public void setLength(int length) {
		this.length = length;
	}


public void setStart(int start) {
	this.start = start;
}
//@Override
//public String toString() {
//	return "S:"+start+" L:"+length+" ["+p.x+","+p.y+"]";
//}
@Override
public String toString() {
	return "S:"+start+" L:"+length+" ["+new DecimalFormat("#.###").format(p.x)+";"+new DecimalFormat("#.###").format(p.y)+"]";
}


}
