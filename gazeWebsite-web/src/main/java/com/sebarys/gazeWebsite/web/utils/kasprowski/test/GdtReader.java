package com.sebarys.gazeWebsite.web.utils.kasprowski.test;

import com.sebarys.gazeWebsite.web.utils.kasprowski.tet.data.Reading;
import com.sebarys.gazeWebsite.web.utils.kasprowski.tet.data.Results;

import java.io.File;
import java.util.List;

public class GdtReader {

	static String indir = "c:/Users/Sebastian/Desktop/gaze/gdt/";

	public static void main(String[] args) {
		File dir = new File(indir);
		System.out.println("Start");
		for(String fname:dir.list()) {
			Results res = Results.deserialize(indir+fname);
			List<Reading> startRd = res.getStartReadings();
			List<Reading> endRd = res.getEndReadings();
			for(int i=0; i<startRd.size(); i++) {
				long ts1 = startRd.get(i).getTimestamp();
				long ts2 = endRd.get(i).getTimestamp();
				if(startRd.get(i).getType().equals("SS"))
					System.out.println("Image "+startRd.get(i).getData()+" observed "+(ts2-ts1)+" ms");
				if(startRd.get(i).getType().equals("PS"))
					System.out.println("Question: "+startRd.get(i).getData()+" answered "+endRd.get(i).getData());
			}
		}


	}
}

