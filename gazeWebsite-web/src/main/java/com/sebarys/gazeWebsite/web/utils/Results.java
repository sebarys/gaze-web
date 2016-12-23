package com.sebarys.gazeWebsite.web.utils;

import com.sebarys.gazeWebsite.web.utils.client.data.GazeData;
import com.sebarys.gazeWebsite.web.utils.client.data.Point2D;
import com.sebarys.gazeWebsite.web.utils.kasprowski.fixation.Fixation;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Results implements Serializable{
	private boolean active = true;
	private static final long serialVersionUID = 1L;
	private List<Reading> readings = new ArrayList<Reading>();
	private List<Fixation> fixations = new ArrayList<Fixation>();

	private String fileName;
	public Results() {}
	public Results(String fileName) {
		this.fileName = fileName;
		load(fileName);
	}
	public Results(List<Reading> readings) {
		List<Reading> rt = new ArrayList<Reading>();
		for(Reading r:readings) 
			rt.add(r);
		this.readings = rt;
	}


	public List<Results> getResultsList() {
		List<Results> listResults = new ArrayList<Results>();
		List<Reading> tt = getReadings("TT");
		Reading previous = new Reading(0,"TT",null);
		for(Reading rtt:tt) {
			listResults.add(new Results(getReadingsBetween(previous,rtt)));
			previous = rtt;
		}
		return listResults;
	}



	public List<Reading> getReadings() {
		return readings;
	}

	//	public void detectFixations() {
	//		setFixations(new FixationDetector().detectFixations(getReadings("G")));
	//	}

	public List<Reading> getReadingsBefore(Reading rd) {
		List<Reading> rt = new ArrayList<Reading>();
		for(Reading r:readings) 
			if(r.getTimestamp()<=rd.getTimestamp()) rt.add(r);
		return rt;
	}

	public List<Reading> getReadingsAfter(Reading rd) {
		List<Reading> rt = new ArrayList<Reading>();
		for(Reading r:readings) 
			if(r.getTimestamp()>=rd.getTimestamp()) rt.add(r);
		return rt;
	}

	public List<Reading> getReadingsAfterTimestamp(long timestamp) {
		List<Reading> rt = new ArrayList<Reading>();
		for(Reading r:readings) 
			if(r.getTimestamp()>=timestamp) rt.add(r);
		return rt;
	}

	public List<Reading> getReadingsBetween(Reading rd,Reading ru) {
		return getReadingsBetween(rd.getTimestamp(), ru.getTimestamp());
	}
	public List<Reading> getReadingsBetween(long t1,long t2) {
		List<Reading> rt = new ArrayList<Reading>();

		for(int i=0;i<readings.size();i++) { 
			Reading r = readings.get(i);
			if(r.getTimestamp()>=t1 && r.getTimestamp()<=t2) rt.add(r);
		}
		return rt;
	}

	public List<Reading> getReadings(String type) {
		List<Reading> rt = new ArrayList<Reading>();
		for(Reading r:readings) 
			if(r.getType().equals(type)) rt.add(r);
		return rt;
	}


	/**
	 * Removes gaze points with 0,0
	 * @return
	 */
	public int removeG0() {
		int cnt=0;
		Iterator<Reading> iter = readings.iterator();
		while(iter.hasNext()) {
			Reading r = iter.next();
			//		for(Reading r:readings) {
			if(r.getType().equals("G")) {
				Object data = r.getData();
				Point2D point = new Point2D(0,0);
				if(data instanceof Point) {
					Point p = (Point)r.getData();
					point = new Point2D(p.x,p.y);
				}
				if(data instanceof Point2D) {
					point = (Point2D)data;
				}
				if(data instanceof GazeData) {
					point = ((GazeData)data).smoothedCoordinates;
				}
				if(point.x==0 && point.y==0) {
					iter.remove();
					cnt++;
				}
			}
		}
		return cnt;
	}

	/**
	 * Removes gaze points with values less than 0,0
	 * @return
	 */
	public int removeLessThanZero() {
		int cnt=0;
		Iterator<Reading> iter = readings.iterator();
		while(iter.hasNext()) {
			Reading r = iter.next();
			//		for(Reading r:readings) {
			if(r.getType().equals("G")) {
				Object data = r.getData();
				Point2D point = new Point2D(0,0);
				if(data instanceof Point) {
					Point p = (Point)r.getData();
					point = new Point2D(p.x,p.y);
				}
				if(data instanceof Point2D) {
					point = (Point2D)data;
				}
				if(data instanceof GazeData) {
					point = ((GazeData)data).smoothedCoordinates;
				}
				if(point.x<=0 || point.y<=0) {
					iter.remove();
					cnt++;
				}
			}
		}
		return cnt;
	}

	//	public List<Fixation> getFixations() {
	//		return fixations;
	//	}

	//	public void setFixations(List<Fixation> fixations) {
	//		this.fixations = fixations;
	//	}

	public synchronized void addReading(String type, Object o) {
		if(active)	readings.add(new Reading(System.currentTimeMillis(),type,o));
	}
	//	List<Reading> gazes = new ArrayList<Reading>();
	//	List<Reading> mouses = new ArrayList<Reading>();
	//	List<Reading> keys = new ArrayList<Reading>();
	//	List<Reading> timestamps = new ArrayList<Reading>();
	//
	//	public void addGaze(GazeData gazeData) {
	//		if(active)
	//		gazes.add(new GazeReading(System.currentTimeMillis(),gazeData));
	//	}
	//	public void addMouse(MouseEvent mouseData) {
	//		if(active)
	//		mouses.add(new MouseReading(System.currentTimeMillis(),mouseData));
	//	}
	//	public void addKey(KeyEvent keyData) {
	//		if(active) keys.add(new KeyReading(System.currentTimeMillis(),keyData));
	//	}
	//	public void addTimestamp(Object data) {
	//		if(active) timestamps.add(new Reading(System.currentTimeMillis(),"T",data));
	//	}
	//	public void addTimestamp(String type,Object data) {
	//		if(active) timestamps.add(new Reading(System.currentTimeMillis(),type,data));
	//	}


	public void save(String fname) {
		active=false;
		System.out.println("SIZE="+readings.size());
		//Collections.sort(readings);
		try {

			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(fname),false));
			for(Reading r:readings) {
				bw.write(r.toString()+"\n");
				//			System.out.println(t);
			}
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		active=true;
	}



	public synchronized void serialize(String fileName) {
		active=false;
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
			out.writeObject(this);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		active=true;
	}


	public static Results deserialize(String fileName) {
		Object o=null;
		try{
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
			o = in.readObject();
			in.close();
		}catch(IOException ex) {ex.printStackTrace();}
		catch(ClassNotFoundException ex) {ex.printStackTrace();}
		if(o instanceof Results) {
			((Results)o).setFileName(fileName);
			return (Results)o;
		}
		else return null;
	}

	public void load(String fname) {
		readings = new ArrayList<Reading>();
		try {

			File file = new File(fname);
			if (file.exists() && file.isFile()) {
				BufferedReader br = new BufferedReader(new FileReader(file));
				String line;
				while ((line = br.readLine()) != null) {
					if(line.length()==0) continue;
					String[] elements = line.split("\t");
					//TODO inne timestampy!
					readings.add(new Reading(Long.parseLong(elements[0]), elements[1], new Point2D(Double.parseDouble(elements[2]), Double.parseDouble(elements[3]))));	
				}

				br.close();
			}
			else {throw new IOException("File "+fname+" not found!");}
		}catch (IOException ioe) {ioe.printStackTrace();}
		//System.out.println("file loaded");

	}

	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		String lastType = "";
		Object lastData = null;
		int last = 0;
		for(Reading r:getReadings()) {
			if(r.getType().equals(lastType)) last++;
			else {
				if(last==1)
					sb.append(lastType+"\t"+lastData+"\n");
				else
					sb.append(lastType+"\t"+last+"\n");
				lastType = r.getType();
				lastData = r.getData();
				last = 1;
			}

		}
		return sb.toString();
	}


	
	
	public List<Reading> getStartReadings() {
		List<Reading> newReadings = new ArrayList<Reading>();
		for (Reading r : readings) {
			if (r.getType().equals("PS") || r.getType().equals("SS"))
				newReadings.add(new Reading(r.getTimestamp(), r.getType(), r.getData()));
		}
		return newReadings;
	}

	public List<Reading> getEndReadings() {
		List<Reading> newReadings = new ArrayList<Reading>();
		for (Reading r : readings) {
			if (r.getType().equals("PE") || r.getType().equals("SE"))
				newReadings.add(new Reading(r.getTimestamp(), r.getType(), r.getData()));
		}
		return newReadings;
	}
	public List<Fixation> getFixations() {
		return fixations;
	}
	public void setFixations(List<Fixation> fixations) {
		this.fixations = fixations;
	}


	/**
	 * Filtr medianowy
	 */
	public void medianize(int len) {
		for(Reading r: getReadings()) {
			if(!(r.getData() instanceof GazeData)) System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			//System.out.println(r);
		}

		if(getReadings().size()<len) {
			throw new RuntimeException("Median "+len+" on "+getReadings().size()+" points");
		}


		for(int i=len/2;i<getReadings().size()-len/2;i++) {
			Reading r = getReadings().get(i); 
			double[] tx = new double[len];
			double[] ty = new double[len];
			for(int j=-len/2;j<=len/2;j++) {
				tx[j+len/2] = getReadings().get(i+j).p().x;
				ty[j+len/2] = getReadings().get(i+j).p().y;
			}
			Arrays.sort(tx);
			Arrays.sort(ty);
			if(r.getData() instanceof GazeData) {
				((GazeData)r.getData()).smoothedCoordinates = new Point2D(tx[len/2],ty[len/2]);
			}
			if(r.getData() instanceof Point2D) {
				r.setData(new Point2D(tx[len/2],ty[len/2]));
			}
			

		}

		for(int i=0;i<len/2;i++) { 
			Reading r = getReadings().get(i); 
			r.setData(getReadings().get(len/2).getData());
		}
		//u�rednienie ko�cowych
		for(int i=getReadings().size()-len/2;i<getReadings().size();i++) {
			Reading r = getReadings().get(i); 
			r.setData(getReadings().get(getReadings().size()-len/2).getData());
		}


		//		if(m.size()<len) {
		//			System.err.println("Median "+len+" on "+m.size()+" points");
		//			return;
		//		}
		//		//		System.out.println("Przed:"+m.size());
		//		//		for(int i=0;i<m.size();i++) 
		//		//			System.out.println(m.get(i));
		//
		//		for(int i=len/2;i<m.size()-len/2;i++) {
		//			double[] t = new double[len];
		//			for(int j=-len/2;j<=len/2;j++) {
		//				t[j+len/2] = m.get(i+j);
		//			}
		//			Arrays.sort(t);
		//			m.set(i,t[len/2]);
		//		}
		//
		//		//		System.out.println("Po1:");
		//		//		for(int i=0;i<m.size();i++) 
		//		//			System.out.println(m.get(i));
		//
		//		//u�rednienie pocz�tkowych
		//		for(int i=0;i<len/2;i++) m.set(i,m.get(len/2));
		//		//u�rednienie ko�cowych
		//		for(int i=m.size()-len/2;i<m.size();i++) m.set(i,m.get(m.size()-len/2)); //xmedian[i]=median[size-len/2-1];
		//
		//		//		System.out.println("Po:");
		//		//		for(int i=0;i<m.size();i++) 
		//		//			System.out.println(m.get(i));
		//
		//		//		System.out.println("pa");
	}




}

