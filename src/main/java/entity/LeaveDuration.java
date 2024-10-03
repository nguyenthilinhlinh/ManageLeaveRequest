package entity;

public enum LeaveDuration {
	FULL_DAY("Full Day", 8),
	HALF_DAY_MORNING("Half Day - Morning", 4),
	HALF_DAY_AFTERNOON("Half Day - Afternoon", 4);
	
	private final int hours;
	private final String description;
	
	private LeaveDuration(String description, int hours) {
		this.hours = hours;
		this.description = description;
	}
	
	public int getHours() {
		return this.hours;
	}
	
	public String getDescription() {
		return this.description;
	}
}
