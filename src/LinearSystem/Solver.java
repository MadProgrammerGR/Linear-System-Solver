package LinearSystem;

public class Solver {
	
	private float[][] E;
	private float[] solution;
	private String[] solutions;
	
	public Result solve(){
		int rows = E.length;
		int columns = E[0].length;
		//n = minimum dimension
		int n = rows < columns ? rows : columns;
		
		//gauss elimination method
		for(int k=0;k<n;k++){
			
			for(int i=0;i<rows;i++){
				if(E[i][k] != 0){
					R3(k, i);
					break;
				}
			}
			
			float pivot = E[k][k];
			R1(k, 1/pivot);
			
			for(int row=0;row<n;row++){
				if(row != k){
					float c =  E[row][k];
					R2(row, k, -c);
				}
			}
			
			//check for impossible solution case
			for(int row=rows-1;row>=0;row--){
				boolean isZero = true; 
				for(int col=0;col<columns-1;col++){
					if(E[row][col] != 0) {
						isZero = false;
						break;
					}
				}
				if(isZero && E[row][columns-1] != 0)
					return Result.IMPOSSIBLE_SOLUTION;
			}
		}
			
		//check for unique solution case
		boolean equalToIn = true;
		outer:for(int row=0;row<n;row++){
			for(int col=0;col<columns;col++){
				int value = row == col ? 1 : 0;
				if(E[row][col] != value){
					equalToIn = false;
					break outer;
				}
			}
		}
		if(equalToIn){
			solution = new float[columns-1];
			for(int i=0;i<solution.length;i++){
				solution[i] = E[i][columns-1];
			}
			return Result.UNIQUE_SOLUTION;
		}
		
		//check for infinity solutions
		int row = 0;
		outer:for(int i=rows-1;i>=0;i++){
			for(int j=0;j<columns;j++){
				if(E[i][j] != 0){
					row = i;
					break outer;
				}
			}
		}
		solutions = new String[row+1];
		for(int i=0;i<solutions.length;i++){
			solutions[i] = "x"+(i+1)+" = "+E[i][columns-1];
			for(int j=i;j<columns-1;j++){
				if( E[i][j] != 0 && i !=j)
					solutions[i] += (E[i][j] > 0 ? " ":" +")+ -E[i][j]+"x"+(j+1);
			}
		}
		return Result.INFINITY_SOLUTIONS;
	}
	
	// Ri -> c*Ri
	private void R1(int i, float c){
		int columns = E[0].length;
		for(int col=0;col<columns;col++){
			E[i][col] *= c;
		}
	}
	
	// Ri -> Ri + c*Rj
	private void R2(int i, int j, float c){
		int columns = E[0].length;
		for(int col=0;col<columns;col++){
			E[i][col] += c*E[j][col];
		}
	}
	
	// Ri <-> Rj
	private void R3(int i, int j){
		int columns = E[0].length;
		for(int col=0;col<columns;col++){
			float temp = E[i][col];
			E[i][col] = E[j][col];
			E[j][col] = temp;
		}
	}
	
	public float[] getSolution(){
		return solution;
	}
	
	public String[] getSolutions(){
		return solutions;
	}
	
	public void setMatrix(float[][] array){
		E = array;
	}
	
	public float[][] getMatrix(){ 
		return E;
	}
	
}
