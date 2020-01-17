
public class Person {
	
	double height, weigth;
	Gender gender;
	
	public Person(double height, double weigth) {
		this(height, weigth, Gender.UNKNOWN);
	}
	
	public Person(double height, double weigth, Gender gender) {
		this.height = height;
		this.weigth = weigth;
		this.gender = gender;
	}
	
	public String toString() {
		return gender + ": " + height + "H " + weigth + "W";
	}

}
