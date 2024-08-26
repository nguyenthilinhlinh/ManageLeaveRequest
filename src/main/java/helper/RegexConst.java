package helper;

public class RegexConst {
	public final static String NUM = "^-?\\d+$";
	public final static String LETTER = "^[a-zA-Z]{5,25}$";
	public final static String STATUS = "[0-1]";
	public final static String DATE = "^\\d{1,2}[-|/]\\d{1,2}[-|/]\\d{4}";
	public final static String DOUBLE =  "-?\\d+(\\.\\d+)?";
	public final static String EMAIL =  "^[a-zA-Z0-9._%+-]+@(gmail\\\\.com|outlook\\\\.com|icloud\\\\.com|zoho\\\\.com|yahoo\\\\.com|aol\\\\.com|gmx\\\\.com|example\\\\.com)$";
}

