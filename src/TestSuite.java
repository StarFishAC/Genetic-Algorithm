import java.util.ArrayList;
import java.util.Random;

public class TestSuite implements Runnable {
	
	Function f;
	Random rnd;
	double performance;
	ArrayList<Person> data;
	
	public TestSuite(ArrayList<Person> data) {
		this.data = data;
		this.rnd = new Random();
		
		double[] coefficients = new double[3];
		for (int i = 0; i < coefficients.length; i++) {
			coefficients[i] = 20 * (rnd.nextDouble() - 0.5);
		}
		
		this.f = new Function(coefficients);
	}

	@Override
	public void run() {
		int correctGuesses = 0;
		
		for (Person person : data) {
			double value = f.getValueFor(person.height);
			Gender guess = Gender.UNKNOWN;
			
			if (value < person.weigth) {
				guess = Gender.MALE;
			} else if (value > person.weigth) {
				guess = Gender.FEMALE;
			}
			
			if (guess == person.gender) {
				correctGuesses++;
			}
		}
		
		performance = 1.0 * correctGuesses / data.size();
		
		System.out.println("Performance: " + performance);
	}

}
