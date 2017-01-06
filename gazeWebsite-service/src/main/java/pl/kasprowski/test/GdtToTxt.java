package pl.kasprowski.test;

import pl.kasprowski.tet.data.Reading;
import pl.kasprowski.tet.data.Results;

import java.util.List;

public class GdtToTxt {
	public static void main(String[] args) {
		Results r = Results.deserialize("c:/Users/Sebastian/Desktop/gaze/gdt/anC$m$animals$1610162009.gdt");
		r.save("xxx");
        r.getResultsList();
        List<Reading> profile = r.getReadings("PROFILE");
    }
}
