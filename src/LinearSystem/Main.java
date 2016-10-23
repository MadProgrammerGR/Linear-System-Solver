package LinearSystem;

import java.util.Scanner;

public class Main {

		static Scanner input;
		
		public static void main(String[] args) {
			input = new Scanner(System.in);
			Solver solver = new Solver();
			
			System.out.println("=== Linear System solver ===");
			float[][] E = readSystem();
			input.close();
			
			System.out.println("You gave the matrix:");
			printArray(E);
			
			
			solver.setMatrix(E);
			Result result = solver.solve();
			
			switch(result){
			case IMPOSSIBLE_SOLUTION:
				System.out.println("Impossible system.");
				break;
			case INFINITY_SOLUTIONS:
				System.out.println("Undefined with infinite solutions: ");
				printArray(solver.getSolutions());
				break;
			case UNIQUE_SOLUTION:
				System.out.println("Unique solution: ");
				printSolution(solver.getSolution());
				break;
			}
		}

		private static float[][] readSystem() {
			System.out.print("Number of Equations: ");
			int equations = readInt();
			
			System.out.print("Number of variables: ");
			int variables = readInt();
			
			//read coefficients into matrix E
			float[][] E = new float[equations][variables+1];
			
			System.out.println("\nInsert equation's coefficients and constant.(space seperated)");
			System.out.println("7*x1 - 2*x2 + 0*x3 + 6,3*x4 = 10 ----> 7 -2 0 6,3 10\n");

			for(int i=0;i<equations;i++){
				System.out.println("Equation "+(i+1));
				for(int j=0;j<variables;j++){
					System.out.print("\tx"+(j+1)+" coefficient: ");
					E[i][j] = readFloat();
				}
				System.out.print("\tConstant:");
				E[i][variables] = readFloat();
			}
			
			return E;
		}

		private static int readInt() {
			int number = 0;
			while(true){
				while(!input.hasNextInt()){
					input.next();
					System.out.println("Thats not an integer.");
				}
				number = input.nextInt();
				
				if(number <= 0){
					System.out.println("Input a positive value.");
				}else if(number > 500){
					System.out.println("Input a value in range [1-500].");
				}else{
					break;
				}
			}
			return number;
		}
		
		private static float readFloat(){
			while(!input.hasNextFloat()) {
				input.next();
				System.out.println("Thats not a number.");
			}
			return input.nextFloat();
		}
		
		public static void printSolution(float[] solution){
			String output = "\n";
			for(int i=0;i<solution.length;i++){
				output += "x"+(i+1)+" = "+solution[i]+"\n";
			}
			System.out.println(output);
		}

		public static void printArray(String[] array){
			String output = "\n";
			for(int i=0;i<array.length;i++){
				output += array[i]+"\n";
			}
			System.out.println(output);
		}
		
		public static void printArray(float[][] array){
			String output = "\n";
			for(int i=0;i<array.length;i++){
				for(int j=0;j<array[i].length;j++){
					output += array[i][j] + "\t";
				}
				output += "\n";
			}
			System.out.println(output);
		}
}
