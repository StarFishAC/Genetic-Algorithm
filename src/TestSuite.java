import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Callable;

public class TestSuite implements Callable<Void> {
	
	Function f;
	Random rnd;
	double performance;
	ArrayList<Person> data;
	
	public TestSuite(ArrayList<Person> data, double averageHeight, double averageWeigth) {
		this.data = data;
		this.rnd = new Random();
		
		double[] coefficients = new double[3];
		for (int i = 0; i < coefficients.length; i++) {
			coefficients[i] = (rnd.nextDouble() - 0.5);
		}
		
		this.f = new Function(coefficients, averageHeight, averageWeigth);
	}
	
	public void mutateBy(Function function) {
		for (int i = 0; i < f.coefficients.length; i++) {
			f.coefficients[i] = function.coefficients[i] + (0.05 * (rnd.nextDouble() - 0.5));
		}
		f.offsetX = function.offsetX + 5 * (rnd.nextDouble() - 0.5);
		f.offsetY = function.offsetY + 5 * (rnd.nextDouble() - 0.5);
	}
	
	public void mutateRandom() {
		for (int i = 0; i < f.coefficients.length; i++) {
			f.coefficients[i] = (rnd.nextDouble() - 0.5);
		}
		f.offsetX += (2 * (rnd.nextDouble() - 0.5));
		f.offsetY += (2 * (rnd.nextDouble() - 0.5));
	}

	@Override
	public Void call() throws Exception {
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
		
		return null;
	}

}
