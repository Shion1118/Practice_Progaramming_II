package pr2calc;

import java.util.function.Function;

public class NonlinearEquation {

	public static final double EPSILON = 0.001;
	public static final int MAXIMUM_IT = 100;

	private double initialValue_;
	private double answer_;
	private double _alpha;

	private double NEGATIVE_MAX;
	private double POSITIVE_MAX;

	public NonlinearEquation() {
		this.answer_ = 0.0;
		this._alpha = 0.0;
		this.NEGATIVE_MAX = 0.0;
		this.POSITIVE_MAX = 0.0;
	}

	/* LinearIteration */
	public NonlinearEquation(double value) {
		this.initialValue_ = (value % 3) * 2;
		this.answer_ = 0.0;
		this._alpha = value % 10;
	}

	/* BisectionMethod */
	public NonlinearEquation(int value, double negative) {
		this.answer_ = 0.0;
		this._alpha = (value % 3) + 1;
		this.NEGATIVE_MAX = negative;
		this.POSITIVE_MAX = _alpha * (int)(5.0 / _alpha);
	}

	/* RegulaFalsi */
	public NonlinearEquation(int value) {
		this.answer_ = 0.0;
		this._alpha = 1.0;
		this.NEGATIVE_MAX = 0.0;
		this.POSITIVE_MAX = _alpha * (int)(5.0 / _alpha);
	}

	/* Newton */
	public NonlinearEquation(double value, double x) {
		this.answer_ = 0.0;
		this._alpha = value;
		this.initialValue_ = x;
	}

	private boolean _solveNLEByLinearIteration() {
		double value, pastValue = 0.0;
		int cnt = 0;

		pastValue = this.initialValue_;
		value = Math.sqrt(10 + this._alpha +pastValue);

		System.out.println("value = " + value + " , pastValue = " + pastValue);
		while (Math.abs(value - pastValue) >= EPSILON) {
			if (cnt > MAXIMUM_IT) return false;
			pastValue = value;
			value = Math.sqrt(10 + this._alpha + value);
			System.out.println("value = " + value + ", pastValue = " + pastValue);
			cnt++;
		}

		System.out.println("X = " + value + " at interation " + cnt + "." + "\n");
		this.answer_ = value;
		return true;
	}

	private boolean _solveNLEByBisectionMethod() {
		double value;
		double negative = this.NEGATIVE_MAX;
		double positive = this.POSITIVE_MAX;
		double mid, pastMid = 0;
		int cnt = 0;
		mid = (positive + negative) / 2;
		value = Math.sin(mid + this._alpha) / (mid + this._alpha);

		System.out.println("xMid = " + mid + ", f(xMid) = " + value + ", xPastMid = " + pastMid);
		while (Math.abs(mid - pastMid) >= EPSILON) {
			if (cnt > MAXIMUM_IT) return false;
			if (value > 0) {
				negative = mid;
			} else {
				positive = mid;
			}

			pastMid = mid;
			mid = (positive + negative) / 2;

			value = Math.sin(mid + this._alpha) / (mid + this._alpha);
			if(value == 0) break;

			System.out.println("xMid = " + mid + ", f(xMid) = " + value + ", xPastMid = " + pastMid);
			cnt++;
		}

		System.out.println("X = " + mid + " at interation " + cnt + "." + "\n");
		this.answer_ = pastMid;
		return true;
	}

	private boolean _solveNLEByRegulaFalsi() {
		double xNext, xPastNext = 0.0;
		double valueNegative, valuePositive, valueF;
		double negative = this.NEGATIVE_MAX;
		double positive = this.POSITIVE_MAX;
		int cnt = 1;

		Function<Double, Double> func = x -> (x + this._alpha == 0) ? 1.0 : Math.sin(x + this._alpha) / (x + this._alpha);

		valueNegative = func.apply(negative);
		valuePositive = func.apply(positive);
		xNext = (negative * valuePositive - positive * valueNegative) / (valuePositive - valueNegative);
		valueF = func.apply(xNext);

		System.out.println("xNext = " + xNext + ", f(xNext) = " + valueF + ", xPastNext = " +xPastNext);
		while (Math.abs(xNext - xPastNext) >= EPSILON && valueF != 0) {
			if (valueF > 0) {
				negative = xNext;
			} else {
				positive = xNext;
			}
			xPastNext = xNext;
			valueNegative = func.apply(negative);
			valuePositive = func.apply(positive);
			xNext = (negative * valuePositive - positive * valueNegative) / (valuePositive - valueNegative);
			valueF = func.apply(xNext);

			System.out.println("xNext = " + xNext + ", f(xNext) = " + valueF + ", xPastNext = " +xPastNext);
			cnt++;
		}

		System.out.println("X = " + xNext + " at interation " + cnt + "." + "\n");
		this.answer_ = xNext;
		return true;
	}

	private boolean _solveNLEByNewton() {
		double xNext = this.initialValue_;
		double xPastNext = 0.0;
		int cnt = 1;

		double h = 1e-5;
		Function<Double, Double> func = x -> Math.exp(x) - this._alpha * x;
		/*  中心差分による微分 */
		Function<Double, Double> funcDiff = x -> (func.apply(x + h) - func.apply(x - h)) / (h * 2);

		while (Math.abs(xNext - xPastNext) >= EPSILON) {
			xPastNext = xNext;
			xNext = xPastNext - func.apply(xPastNext) / funcDiff.apply(xPastNext);

			System.out.println("xNext = " + xNext + ", f(xNext) = " + func.apply(xNext));
			cnt++;
		}

		System.out.println("X = " + xNext + " at interation " + cnt + "." + "\n");
		this.answer_ = xNext;
		return true;
	}

	public static void main(String[] args) {
		NonlinearEquation eq = new NonlinearEquation(26);
	    if (!eq._solveNLEByLinearIteration()) System.out.println("ERROR");

		NonlinearEquation eq2 = new NonlinearEquation(26, 0.0);
		if (!eq2._solveNLEByBisectionMethod()) System.out.println("ERROR");

		NonlinearEquation eq3 = new NonlinearEquation(26);
		if (!eq3._solveNLEByRegulaFalsi()) System.out.println("ERROR");

		NonlinearEquation eq4 = new NonlinearEquation(3.05, 1.19);
		if (!eq4._solveNLEByNewton()) System.out.println("ERROR");
	}

}
