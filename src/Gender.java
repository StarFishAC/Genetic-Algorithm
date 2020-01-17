
public enum Gender {
	
	MALE, FEMALE, UNKNOWN;

	public static Gender parseGender(String string) {
		string = string.toLowerCase();
		
		if (string.matches(".+female.+")) {
			return Gender.FEMALE;
		}
		if (string.matches(".+male.+")) {
			return Gender.MALE;
		}
		return Gender.UNKNOWN;
	}

}
