package pr2calc;

import java.util.*;
import java.io.*;

public class SourceExample{  

    public int a;
    public int[][] b;
    public String str;       

    public SourceExample(){ 
        int h,i;

        this.a = -1;
        this.b = new int[3][3];

        for(h=0;h<this.b.length;h++){
            for(i=0;i<this.b[0].length;i++)
                this.b[h][i] = -1;
        }
        this.str = "";
    }

    public SourceExample(int[][] value){ 
        setA(3);
        setB(value);
        setStr("Hello World.");
    }

    public SourceExample(String fileName){
    	try{
        	this.loadData(fileName);
        }catch(IOException e){
        	System.out.println(e.getMessage());
    		System.out.println("ファイルからの入力に失敗しました。");
    	}
    }

    private boolean loadData(String fileName) throws IOException{
		int h,i;
		int row, column;
		BufferedReader fin = new BufferedReader(new FileReader(fileName));
		String inputData;
		String[] inputValue;

		inputData = fin.readLine();
		inputValue = inputData.split("\\s");

		if(inputValue.length != 1) {
			fin.close();
			return false;
		}else{
			setA(Integer.parseInt(inputValue[0]));
			inputData = fin.readLine();
			inputValue = inputData.split("\\s");
			if(inputValue.length != 2){	
				fin.close();
				return false;
			}else{
				row = Integer.parseInt(inputValue[0]);
				column = Integer.parseInt(inputValue[1]);
				this.b = new int[row][column];

				for(h = 0; h < row; ++h){
					inputData = fin.readLine();
					inputValue = inputData.split("\\s");
					for(i = 0; i < column; ++i){
						this.b[h][i] = Integer.parseInt(inputValue[i]);
					}
				}

				this.str = fin.readLine();
			}
		}
		fin.close(); 
		return true;
	}

	public void saveData(String saveFileName){
		try{
            int h, i;
            int row, column;
        	BufferedWriter fin = new BufferedWriter(new FileWriter(saveFileName));

        	fin.write(Integer.toString(this.a));
            fin.newLine();
            row = this.b.length;
            column = this.b[0].length;
        	fin.write(row + " " + column);
            fin.newLine();
            for(h = 0; h < row; ++h){
                for(i = 0; i < column; ++i){
                    if(i != column - 1){
                        fin.write(Integer.toString(this.b[h][i]) + " ");
                    }else{
                        fin.write(Integer.toString(this.b[h][i]));
                    }
                }
                fin.newLine();
            }
            fin.write(this.str);
        	fin.close();
        }catch(IOException e){
        	System.out.println(e.getMessage());
    		System.out.println("ファイルの保存に失敗しました。");
    	}
	}

    public void setA(int value){
        this.a = value;
    }

    public int getA(){
        return this.a;
    }

    public void setB(int[][] value){
        this.b = value;
    }

    public int[][] getB(){
        return this.b;
    }

    public void setStr(String value){
        this.str = value;
    }

    public String getStr(){
        return this.str;
    }

    public void showAllContentsOfB(){
        for(int h = 0; h < this.b.length; h++){
            for(int i = 0; i < this.b[0].length; i++){ 
                if(i != this.b[0].length - 1){
                    System.out.print(this.b[h][i] + " , ");
                } else {
                    System.out.println(this.b[h][i]);
                }
            }
        }   
    }

    public static void main(String[] args){
        SourceExample ex;
        String fn = null;

        //引数があっていてもファイルが存在しないのに実行する場合がある。
        //その場合showAllContentsOfB()メソッドにてbがnullでエラーがでる。
        //それを避けるために存在するファイル名を入力するまで入力を繰り返すようにした。
        if(args.length != 1){
        	System.out.println("ファイルを指定してください");
        	fn = new Scanner(System.in).next();
        }else{
        	fn = args[0];
        }

        while(!(new File(fn).exists())){
        	System.out.println("ファイルが存在しません。データ入力用のファイル名を再度指定して下さい。");
        	fn = new Scanner(System.in).next();
        }

        ex = new SourceExample(fn);

		System.out.println("オブジェクトのaフィールドの値は"+ex.getA()+"です");
		System.out.println("");
		ex.showAllContentsOfB();
		System.out.println("");
		System.out.println(ex.getStr());

		ex.saveData("savedSample.dat");

    }

}