import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class Function {
	
	double[] coefficients;
	double offsetX, offsetY;
	
	DecimalFormat df;
	
	public Function(double[] coefficients) {
		this(coefficients, 0, 0);
	}
	
	public Function(double[] coefficients, double offsetX, double offsetY) {
		this.coefficients = coefficients;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		
		NumberFormat nf = NumberFormat.getInstance(Locale.US);
		this.df = (DecimalFormat) nf;
		this.df.applyPattern("####.####");
	}
	
	public double getValueFor(double x) {
		double result = 0;
		
		x -= offsetX;
		for (int exp = 1; exp <= coefficients.length; exp++) {
			result += (coefficients[coefficients.length - exp] * Math.pow(x, exp));
		}
		result += offsetY;
		
		return result;
	}
	
	public String toString() {
		String ans = "";
		String modifiedX = "x";
		
		if (offsetX > 0) {
			modifiedX = "(x-" + df.format(offsetX) + ")";
		} else if (offsetX < 0) {
			modifiedX = "(x+" + df.format(Math.abs(offsetX)) + ")";
		}
		
		for (int exp = coefficients.length; exp > 0; exp--) {
			double co = coefficients[coefficients.length - exp];
			
			if (co > 0) {
				ans += "+" + df.format(co) + modifiedX + "^" + exp;
			} else if (co < 0) {
				ans += df.format(co) + modifiedX + "^" + exp;
			}
		}
		if (offsetY > 0) {
			ans += "+" + df.format(offsetY);
		} else if (offsetY < 0) {
			ans += df.format(offsetY);
		}
		
		ans = ans.replace("^1", "");
		ans = ans.replace("^2", "\u00B2");
		ans = ans.replace("^3", "\u00B3");
		ans = ans.replace("^4", "\u2074");
		ans = ans.replace("^5", "\u2075");
		ans = ans.replace("^6", "\u2076");
		ans = ans.replace("^7", "\u2077");
		ans = ans.replace("^8", "\u2078");
		ans = ans.replace("^9", "\u2079");
		
		return ans;
	}

}
