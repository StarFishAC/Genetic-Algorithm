
import java.awt.Dimension;

import javax.swing.JFrame;

public class Controller implements Runnable {
	
	private Classification classification;
	private JFrame window;
	private View view;
	
	public Controller() {
		this.classification = new Classification(this);
		
		this.classification.loadDataFromFile();
		this.classification.generateTestSuites();
		
		this.window = new JFrame();
		this.view = new View(classification);
		buildWindow();
	}
	
	private void buildWindow() {
		window.add(view);
		
		view.setPreferredSize(new Dimension(1280, 720));
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.pack();
		
		window.setVisible(true);
	}

	@Override
	public void run() {
		final long PREFFERED_FRAME_TIME = Math.round(1_000_000_000.0 / 60);
		long lastFrameTime = System.nanoTime();
		
		new Thread(classification).start();
		
		while (true) {
			if (System.nanoTime() - lastFrameTime > PREFFERED_FRAME_TIME) {
				lastFrameTime = System.nanoTime();
				
				view.repaint();
				
				long sleepTime = (lastFrameTime - System.nanoTime() + PREFFERED_FRAME_TIME) / 1_000_000;
				if (sleepTime > 0) {
					try {
						Thread.sleep(sleepTime);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public void update() {
		view.plotFunctions();
	}

}
