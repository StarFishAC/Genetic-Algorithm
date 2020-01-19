import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class View extends JLabel {
	
	Classification classification;
	BufferedImage frame;
	BufferedImage dataImg;
	
	public View(Classification classification) {
		this.classification = classification;
		this.dataImg = new BufferedImage(1280, 720, BufferedImage.TYPE_INT_ARGB);
		
		Graphics2D g = dataImg.createGraphics();
		for (Person person : classification.data) {
			int x = (int) (5 * person.height);
			int y = (int) (dataImg.getHeight() - 5 * person.weigth);
			
			int rgb = dataImg.getRGB(x, y);
			if (person.gender == Gender.MALE) {
				rgb += new Color(0, 0, 255, 32).getRGB();
			} else {
				rgb += new Color(255, 0, 0, 32).getRGB();
			}
			g.setColor(new Color(rgb, true));
			g.fillRect(x - 1, y - 1, 3, 3);
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.drawImage(frame, 0, 0, null);
		g.drawString("Performance: " + classification.bestPerforming.performance, 10, 20);
		g.drawString("Generation: " + classification.generation, 10, 30);
		if (classification.done) {
			g.drawString("Calculations done!", 10, 50);
			g.drawString("Best solution: " + classification.bestPerforming.f, 10, 60);
		}
		
		g.setColor(Color.LIGHT_GRAY);
		for (int x = 0; x < getWidth() / 5; x += 50) {
			g.drawString("" + x, x * 5, 720 - 20);
			g.drawLine(x * 5, 0, x * 5, getHeight());
		}
		for (int y = 0; y < 720 / 5; y += 50) {
			g.drawString("" + y, 10, 720 - y * 5);
			g.drawLine(0, 720 - y * 5, getWidth(), 720 - y * 5);
		}
		for (int y = -50; y > (720 - getHeight()) / 5; y -= 50) {
			g.drawString("" + y, 10, 720 - y * 5);
			g.drawLine(0, 720 - y * 5, getWidth(), 720 - y * 5);
		}
	}
	
	public void plotFunctions() {
		frame = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = frame.createGraphics();
		
		g.drawImage(dataImg, 0, 0, null);
		
		for (TestSuite testSuite : classification.testSuites) {
			g.setColor(testSuite.equals(classification.bestPerforming) ? new Color(0, 0, 0, 16) : new Color(0, 0, 0, 0));
			
			if (testSuite.equals(classification.bestPerforming)) {
				Function f = testSuite.f;
				for (double x = 0; x < getWidth() / 5; x+=0.01) {
					double y = f.getValueFor(x);
					
					g.fillRect((int) (5 * x) - 1, (int) (720 - 5 * y) - 1, 3, 3);
				}
			}
		}
	}

}
