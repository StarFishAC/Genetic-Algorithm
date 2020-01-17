import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class Classification implements Runnable {
	
	ArrayList<Person> data;
	TestSuite[] testSuites;
	
	public Classification() {
		this.data = new ArrayList<>();
		this.testSuites = new TestSuite[20];
	}

	private void loadDataFromFile() {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("weight-height.csv")))) {
			String line = reader.readLine();
			while ((line = reader.readLine()) != null) {
				String[] lineArr = line.split(",");
				
				data.add(new Person(
						Double.parseDouble(lineArr[1]),		// height
						Double.parseDouble(lineArr[2]),		// weight
						Gender.parseGender(lineArr[0])		// gender
						));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void generateTestSuites() {
		for (int i = 0; i < testSuites.length; i++) {
			if (testSuites[i] == null) {
				testSuites[i] = new TestSuite(data);
			} else {
				
			}
		}
	}
	
	private TestSuite getBestPerforming() {
		TestSuite bestPerforming = testSuites[0];
		for (TestSuite testSuite : testSuites) {
			if (testSuite.performance > bestPerforming.performance) {
				bestPerforming = testSuite;
			}
		}
		return bestPerforming;
	}

	@Override
	public void run() {
		this.loadDataFromFile();
		this.generateTestSuites();
		
		for (TestSuite testSuite : testSuites) {
			testSuite.run();
		}
		
		TestSuite bestPerforming = getBestPerforming();
		System.out.println(Arrays.toString(bestPerforming.f.coefficients));
	}

}
