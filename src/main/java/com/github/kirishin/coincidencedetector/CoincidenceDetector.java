package com.github.kirishin.coincidencedetector;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CoincidenceDetector {

	private List<TimePeriod> periodList;

	private CoincidenceDetector() {
		periodList = new ArrayList<>();
	}

	public CoincidenceDetector with(TimePeriod period) {
		periodList.add(period);
		return this;
	}

	public CoincidenceDetector with(LocalDateTime start, LocalDateTime end) {
		return with(TimePeriod.of(start, end));
	}
}
