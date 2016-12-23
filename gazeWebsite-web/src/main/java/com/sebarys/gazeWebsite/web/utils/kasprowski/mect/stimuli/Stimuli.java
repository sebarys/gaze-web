package com.sebarys.gazeWebsite.web.utils.kasprowski.mect.stimuli;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@XmlRootElement
public class Stimuli {
	String stimId;
	List<Stimulus> stimulus = new ArrayList<Stimulus>();

	public List<Stimulus> getStimulus() {
		return stimulus;
	}

	public void setStimulus(List<Stimulus> stimulus) {
		this.stimulus = stimulus;
	}

	public String getStimId() {
		return stimId;
	}

	public void setStimId(String stimId) {
		this.stimId = stimId;
	}

	public Map<String,ObservableList<String>> getQuestions() {
		Map<String,ObservableList<String>> questions= new LinkedHashMap<String,ObservableList<String>>();
		for(Stimulus s:stimulus) {
			
			if(s.getType().equals("P") && s.getQuestion()!=null && s.getAnswer()!=null) {
				ObservableList<String> answers = FXCollections.observableArrayList(s.getAnswer());
				questions.put(s.getQuestion(),answers);
			}
		}
		return questions;
	}
}
