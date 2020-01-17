
public class Function {
	
	double[] coefficients;
	double offsetX, offsetY;
	
	public Function(double[] coefficients) {
		this(coefficients, 0, 0);
	}
	
	public Function(double[] coefficients, double offsetX, double offsetY) {
		this.coefficients = coefficients;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
	}
	
	public double getValueFor(double x) {
		double result = 0;
		
		for (int exp = 1; exp <= coefficients.length; exp++) {
			result += coefficients[coefficients.length - exp] * Math.pow(x + offsetX, exp);
		}
		result += offsetY;
		
		return result;
	}
	
	public String toString() {
		String ans = "";
		String modifiedX = "x";
		
		if (offsetX > 0) {
			modifiedX = "(x+" + offsetX + ")";
		} else if (offsetX < 0) {
			modifiedX = "(x" + offsetX + ")";
		}
		
		for (int exp = 1; exp <= coefficients.length; exp++) {
			double co = coefficients[coefficients.length - exp];
			
			if (co > 0) {
				ans += "+" + co + modifiedX + "^" + exp;
			} else if (co < 0) {
				ans += co + modifiedX + "^" + exp;
			}
		}
		if (offsetY > 0) {
			ans += "+" + offsetY;
		} else if (offsetY < 0) {
			ans += offsetY;
		}
		
		return ans;
	}

}
