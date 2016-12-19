package pr2calc;

public class Matrix {
    
    double[][] m;
    int numOfRow;
    int numOfColumn;
	
	public Matrix(){
		numOfRow = 0;
		numOfColumn = 0;
	}

	public Matrix(double[][] input){
		this.numOfRow = input.length; 
		this.numOfColumn = input[0].length;
		this.m = new double[this.numOfRow][this.numOfColumn];
		this.m = input;
	}

	public Matrix(double[] input){
		this.numOfRow = 1;
		this.numOfColumn = input.length;
		this.m = new double[this.numOfRow][this.numOfColumn];
		this.m[0] = input;;
	}

	public int getNumOfRow(){
		return this.numOfRow;
    }
        
	public int getNumOfColumn(){
		return this.numOfColumn;
    }

	public double getComponentOf(int rowIndex, int columnIndex){
			if(rowIndex > this.numOfRow || columnIndex > this.numOfColumn){
				System.out.println("Specified element does not exist.");
				System.exit(0);
			}
			return this.m[rowIndex][columnIndex];
        }
        
	public void display(){
		int row = getNumOfRow();
		int column = getNumOfColumn();

		for (int i = 0; i < row; ++i) {
			System.out.print("[ ");
			for (int j = 0; j < column; ++j) {
				System.out.print(this.m[i][j] + " ");
			}	
			System.out.println("]");
		}
		System.out.println();
	}

	public double getInnerProduct(Matrix mat){
		double sum = 0;	

		if(this.getNumOfRow() != 1){
			System.out.println("It's column vector.");
			System.exit(0);
		}


		if(this.getNumOfColumn() == mat.getNumOfColumn() && mat.getNumOfRow() == 1){
			for (int i = 0; i < this.getNumOfColumn(); ++i) {
				sum += this.m[0][i] * mat.m[0][i];
			}
			return sum;
		}

		if(this.getNumOfColumn() == mat.getNumOfRow() && mat.getNumOfColumn() == 1){
			for (int i = 0; i < this.getNumOfColumn(); ++i) {
				sum += this.m[0][i] * mat.m[i][0];
			}
			return sum;
		}

		System.out.println("Can't Calculate.\n");
		System.exit(0);
		return sum;
	}

	public Matrix multiplyMatrix(Matrix mat){
		Matrix result = new Matrix(new double[this.getNumOfRow()][mat.getNumOfColumn()]);
		double sum = 0;

		for (int i = 0; i < this.getNumOfRow(); ++i) {
			for (int j = 0; j < mat.getNumOfColumn(); ++j) {
				for (int k = 0; k < this.getNumOfColumn(); ++k) {
					sum += this.m[i][k] * mat.m[k][j];
				}
				result.m[i][j] = sum;
				sum = 0;
			}
		}

    	return result;
	}

	public boolean multipliable(Matrix mat){
		if(this.getNumOfColumn() == mat.getNumOfRow()){
			return true;
		}
		System.out.println("Can't Calculate.\n");
		return false;
	}

	public Matrix transpose(){
		Matrix result = new Matrix(new double[this.getNumOfColumn()][this.getNumOfRow()]);

		for(int i = 0; i < this.getNumOfColumn(); ++i){
			for (int j = 0; j < this.getNumOfRow(); ++j) {
				result.m[i][j] = this.m[j][i];
			}
		}

		return result;
	}

	public Matrix rotate(double theta){
		Matrix result = new Matrix(new double[this.getNumOfRow()][this.getNumOfColumn()]);

		theta = convertIntoRadian(theta);
		double[][] rotation ={
			{Math.cos(theta),-Math.sin(theta)},
			{Math.sin(theta), Math.cos(theta)}};
		Matrix rmat = new Matrix(rotation);

		if(this.getNumOfColumn() != 1 && this.getNumOfRow()== 2){
			System.out.println("Can't Calculate.\n");
			System.exit(0);
		}

		result = rmat.multiplyMatrix(this);

		return result;
	}

	public static double convertIntoRadian(double theta){
		return theta * Math.PI / 180.0;
	}

	public static void main(String[] args){

		Matrix mat0, mat1, mat2;

		double[][] 
			m0 = {
				{ 1.0, 8.0, 9.0},
				{ 7.0, 3.0,-4.0}},
			m1 = {
				{-5.0},
				{-5.0}},
			m2 = {
				{ 3.0},
				{ 5.196}};
                
                
		mat0 = new Matrix(m0);
		mat1 = new Matrix(m1);
		mat2 = new Matrix(m2);

		System.out.println("Original matrix:");	mat0.display();
		System.out.println("Transposed matrix:"); mat0.transpose().display();

		System.out.println("Coordinate point (1) before rotation"); mat1.display();
		System.out.println("Coordinate point (1) after rotating 45 degree"); mat1.rotate(45).display();

		System.out.println("Coordinate point (2) before rotation"); mat2.display();
		System.out.println("Coordinate point (2) after rotating -60 degree"); mat2.rotate(-60).display();
	}
}