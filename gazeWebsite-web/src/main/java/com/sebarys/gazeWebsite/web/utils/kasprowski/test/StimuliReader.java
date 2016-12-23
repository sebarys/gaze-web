package com.sebarys.gazeWebsite.web.utils.kasprowski.test;

import com.sebarys.gazeWebsite.web.utils.kasprowski.mect.stimuli.Stimuli;
import com.sebarys.gazeWebsite.web.utils.kasprowski.mect.stimuli.Stimulus;

import javax.xml.bind.JAXB;
import java.io.File;

public class StimuliReader {
public static void main(String[] args) {
	Stimuli stimuli = JAXB.unmarshal(new File("c:/Users/Sebastian/Desktop/gaze/stm/implanty/stm.xml"), Stimuli.class);
	for(Stimulus st:stimuli.getStimulus()) {
		System.out.println("==============TYPE: "+st.getType());
		if(st.getType().equals("P")) {
			System.out.println(st.getQuestion()+" > "+st.getAnswer());
		}
		if(st.getType().equals("I") || st.getType().equals("M")) {
			System.out.println(st.getFileName()+" "+st.getDuration());
		}
	}
	
}
}
