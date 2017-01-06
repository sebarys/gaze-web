package pl.kasprowski.mect.stimuli;

import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.ArrayList;
import java.util.List;

public class Stimulus {
	private int no;
	private String type;
	private String fileName;
	private int duration;
	private String question;
	private boolean clickable = false;
	private boolean extendable = false;
	private List<Aoi> aoi = new ArrayList<Aoi>();

	private List<String> answer = new ArrayList<String>();

	public Stimulus() {}
	public Stimulus(int no, String type, String fileName, int duration,
			String question, List<String> answers) {
		super();
		this.no = no;
		this.type = type;
		this.fileName = fileName;
		this.duration = duration;
		this.question = question;
		this.answer = answers;
	}

	public Stimulus(int no, String type, String fileName, int duration) {
		super();
		this.no = no;
		this.type = type;
		this.fileName = fileName;
		this.duration = duration;
		this.answer = new ArrayList<String>(); 
	}

	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}
	@XmlElementWrapper(name="answers")
	public List<String> getAnswer() {
		return answer;
	}

	public void setAnswer(List<String> answer) {
		this.answer = answer;
	}

	
	
	public boolean isClickable() {
		return clickable;
	}
	public void setClickable(boolean clickable) {
		this.clickable = clickable;
	}
	
	
	
	public boolean isExtendable() {
		return extendable;
	}
	public void setExtendable(boolean extendable) {
		this.extendable = extendable;
	}
	@Override
	public String toString() {
		if(getType().equals("P"))
			return getQuestion()  + " "+getAnswer();
		else
			return getFileName();
	}
	public List<Aoi> getAoi() {
		return aoi;
	}
	public void setAoi(List<Aoi> aoi) {
		this.aoi = aoi;
	}
	
	
}
