package pl.kasprowski.test;

import pl.kasprowski.tet.data.Reading;
import pl.kasprowski.tet.data.Results;

import java.util.HashMap;
import java.util.Map;

public class ReadQA {

	public static Map<String,String> readQA(Results results) {
		Map<String,String> qa = new HashMap<String,String>();
		String question = "";
		for(Reading r:results.getReadings()) {
			if(r.getType().equals("PS")) {
				question = (String)r.getData();
			}
			if(r.getType().equals("PE")) {
				String answer = (String)r.getData();
				if(question.equals("")) throw new RuntimeException("null question!\n"+qa);
				qa.put(question, answer);
				question = "";
			}
		}
		return qa;
	}
}
