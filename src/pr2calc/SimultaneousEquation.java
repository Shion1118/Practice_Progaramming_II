package pr2calc;

public class SimultaneousEquation extends Matrix {

	double[] answers;

	public SimultaneousEquation(double[][] input){
		super(input);
	}

	public void normalize(int row){
		double num = 1 / this.getComponentOf(row, row);

		for (int i = 0; i < this.getNumOfColumn(); ++i){
			this.m[row][i] *= num;
		}
	}

	public void subtractRowFrom(int subRow, int minRow){
		double nor = this.getComponentOf(subRow, subRow);
		double num = this.getComponentOf(minRow, subRow);

		for (int i = 0; i < this.getNumOfColumn(); ++i){
			this.m[minRow][i] -= num * (this.m[subRow][i] / nor);
		}
	}

	protected int selectPivotFromRow(int col){
		double max = 0;
		int maxRow = 0;

		for (int i = col; i < this.getNumOfRow(); ++i) {
			if(Math.abs(this.m[i][col]) < max) continue;
			maxRow = i;
			max = Math.abs(this.m[i][col]);
		}
		return maxRow;
	}

	protected void exchangeRows(int exRow1, int exRow2){
		double tmp;
		for (int i = 0; i < this.getNumOfColumn(); ++i){
			tmp = this.m[exRow1][i];
			this.m[exRow1][i] = this.m[exRow2][i];
			this.m[exRow2][i] = tmp;
		}
	}

	public void solveByGaussJordan(){
		for (int subRow = 0; subRow < this.getNumOfRow(); ++subRow){
			System.out.println((subRow+1) + "行" + (subRow+1) + "列目が1となるように割り、他の行の" + (subRow+1) + "列目が0となるように引く");
			normalize(subRow);
			for (int minRow = 0; minRow < this.getNumOfRow(); ++minRow){
				if(subRow == minRow) continue;
				subtractRowFrom(subRow, minRow);
			}
			this.display();
		}

		this.setAnswers();
		this.displayAnswers();
	}

	public void solveByGauss(){
		for (int subRow = 0; subRow < this.getNumOfRow(); ++subRow){
			for (int minRow = subRow; minRow < this.getNumOfRow(); ++minRow){
				if (minRow == subRow) continue;
				subtractRowFrom(subRow, minRow);
			}
			this.display();
		}

		for (int i = this.getNumOfRow() - 1; i >= 0; --i){
			double ans = this.getComponentOf(i, this.getNumOfColumn() - 1);
			for (int j = i + 1; j < this.getNumOfRow(); j++) {
				ans -= this.getComponentOf(i, j) * this.m[j][this.getNumOfColumn() - 1];
			}
			this.m[i][this.getNumOfColumn() - 1] = ans / this.getComponentOf(i, i);
        }

        this.setAnswers();
        this.displayAnswers();
	}

	public void solveByGaussWithPartialSelection(){
		for (int subRow = 0; subRow < this.getNumOfRow(); ++subRow) {
			int maxRow = this.selectPivotFromRow(subRow);
			this.exchangeRows(subRow, maxRow);
			for (int minRow = subRow; minRow < this.getNumOfRow(); ++minRow){
				if (minRow == subRow) continue;
				subtractRowFrom(subRow, minRow);
			}
			this.display();
        }

        for (int i = this.getNumOfRow() - 1; i >= 0; --i){
			double ans = this.getComponentOf(i, this.getNumOfColumn() - 1);
			for (int j = i + 1; j < this.getNumOfRow(); j++) {
				ans -= this.getComponentOf(i, j) * this.m[j][this.getNumOfColumn() - 1];
			}
			this.m[i][this.getNumOfColumn() - 1] = ans / this.getComponentOf(i, i);
        }

        this.setAnswers();
        this.displayAnswers();
	}

	public void setAnswers(){
		this.answers = new double[this.getNumOfRow()];
		for (int i = 0; i < this.getNumOfRow(); ++i) {
			answers[i] = this.m[i][this.getNumOfColumn() - 1];
		}
	}

	public void displayAnswers(){
		System.out.println("Answer:");
		for (int i = 0; i < this.getNumOfRow(); ++i){
			System.out.printf( "x%d = %f ", i + 1, this.answers[i]);
		}
		System.out.print("\n");
	}
	
	public static void main(String[] args){

		SimultaneousEquation se;

		double[][]
			m =	{
				{ 2, 1, 3, 4, 2},
				{ 3, 2, 5, 2,12},
				{ 3, 4, 1,-1, 4},
				{-1,-3, 1, 3,-1}};

		se = new SimultaneousEquation(m);
		se.display();
		se.solveByGaussWithPartialSelection();
	}
}