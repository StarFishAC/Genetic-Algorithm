
public class Function {
	
	double[] coefficients;
	
	public Function(double[] coefficients) {
		this.coefficients = coefficients;
	}
	
	public double getValueFor(double x) {
		double result = 0;
		
		for (int exp = 0; exp < coefficients.length; exp++) {
			result += coefficients[coefficients.length - 1 - exp] * Math.pow(x, exp);
		}
		
		return result;
	}

}
