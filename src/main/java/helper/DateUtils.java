package helper;

import java.time.Duration;
import java.util.Date;

public final class DateUtils {
	public static Duration between(java.sql.Date d1, java.sql.Date d2) {
		assert d1 == null || d2 == null : "The input date must not be null";
		Date date1 = new Date(d1.getTime());
		Date date2 = new Date(d2.getTime());
		
		return Duration.between(date1.toInstant(), date2.toInstant());
	}
	
	public static Duration between(Date d1, Date d2) {
		assert d1 == null || d2 == null : "The input date must not be null";
		Date date1 = new Date(d1.getTime());
		Date date2 = new Date(d2.getTime());
		
		return Duration.between(date1.toInstant(), date2.toInstant());
	}
}
