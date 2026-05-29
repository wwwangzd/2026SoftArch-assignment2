package edu.softarch.hotelpricingagent.agent;

public enum AddIteration {

	OVERALL_SYSTEM_STRUCTURE(1, "Establishing an Overall System Structure"),

	PRIMARY_FUNCTIONALITY(2, "Identifying Structures to Support Primary Functionality"),

	RELIABILITY_AND_AVAILABILITY(3, "Addressing Reliability and Availability Quality Attributes"),

	DEVELOPMENT_AND_OPERATIONS(4, "Addressing Development and Operations");

	private final int number;

	private final String title;

	AddIteration(int number, String title) {
		this.number = number;
		this.title = title;
	}

	public int number() {
		return number;
	}

	public String title() {
		return title;
	}

	public String label() {
		return "Iteration " + number + ": " + title;
	}
}
