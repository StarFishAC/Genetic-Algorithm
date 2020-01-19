import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Classification implements Runnable {
	
	ArrayList<Person> data;
	double averageHeight;
	double averageWeigth;
	
	ArrayList<TestSuite> testSuites;
	TestSuite bestPerforming;
	volatile int generation;
	
	Controller ctrl;
	volatile boolean done;
	
	ExecutorService executor;
	
	public Classification(Controller ctrl) {
		this.ctrl = ctrl;
		this.data = new ArrayList<>();
		this.testSuites = new ArrayList<>();
		
		this.executor = Executors.newCachedThreadPool();
	}

	public void loadDataFromFile() {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("weight-height.csv")))) {
			String line = reader.readLine();
			while ((line = reader.readLine()) != null) {
				String[] lineArr = line.split(",");
				double height = Double.parseDouble(lineArr[1]) * 2.54;
				double weight = Double.parseDouble(lineArr[2]) / 2.2046;
				
				averageHeight += height;
				averageWeigth += weight;
				
				data.add(new Person(
						height,								// height
						weight,								// weight
						Gender.parseGender(lineArr[0])		// gender
						));
			}
			
			averageHeight /= 10000;
			averageWeigth /= 10000;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void generateTestSuites() {
		for (int i = 0; i < 20; i++) {
			testSuites.add(new TestSuite(data, averageHeight, averageWeigth));
		}
		bestPerforming = testSuites.get(0);
	}
	
	public TestSuite getBestPerforming() {
		for (TestSuite testSuite : testSuites) {
			if (testSuite.performance > bestPerforming.performance) {
				bestPerforming = testSuite;
				ctrl.update();
			}
		}
		return bestPerforming;
	}

	@Override
	public void run() {
		for (generation = 0; generation < 10000; generation++) {
			
			try {
				executor.invokeAll(testSuites);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			bestPerforming = getBestPerforming();
			
			for (TestSuite testSuite : testSuites) {
				if (!bestPerforming.equals(testSuite)) {
					testSuite.mutateBy(bestPerforming.f);
				}
			}
		}
		
		System.out.println(bestPerforming.f);
		
		done = true;
		ctrl.update();
	}

}
