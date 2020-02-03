import java.util.ArrayList;
import java.util.List;

public class Task9 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//Sudoku tests
		
		//Fist Sudoku problem [Easy difficulty] ( https://dingo.sbs.arizona.edu/~sandiway/sudoku/examples.html)
		System.out.println("        Easy level Sudoku problem :");
		List<Sudoku> partiels = new ArrayList<Sudoku>();
		Sudoku s1 = new Sudoku(0,3,1);
		Sudoku s2 = new Sudoku(0,4,5);
		Sudoku s3 = new Sudoku(0,6,6);
		Sudoku s4 = new Sudoku(0,8,0);
		Sudoku s5 = new Sudoku(1,0,5);
		Sudoku s6 = new Sudoku(1,1,7);
		Sudoku s7 = new Sudoku(1,4,6);
		Sudoku s8 = new Sudoku(1,7,8);
		Sudoku s9 = new Sudoku(2,0,0);
		Sudoku s10 = new Sudoku(2,1,8);
		Sudoku s11 = new Sudoku(2,5,3);
		Sudoku s12 = new Sudoku(2,6,4);
		Sudoku s13 = new Sudoku(3,0,7);
		Sudoku s14 = new Sudoku(3,1,1);
		Sudoku s15 = new Sudoku(3,3,0);
		Sudoku s16 = new Sudoku(3,7,3);
		Sudoku s17 = new Sudoku(4,2,3);
		Sudoku s18 = new Sudoku(4,3,5);
		Sudoku s19 = new Sudoku(4,5,1);
		Sudoku s20 = new Sudoku(4,6,8);
		Sudoku s21 = new Sudoku(5,1,4);
		Sudoku s22 = new Sudoku(5,5,2);
		Sudoku s23 = new Sudoku(5,7,1);
		Sudoku s24 = new Sudoku(5,8,7);
		Sudoku s25 = new Sudoku(6,2,8);
		Sudoku s26 = new Sudoku(6,3,2);
		Sudoku s27 = new Sudoku(6,7,6);
		Sudoku s28 = new Sudoku(6,8,3);
		Sudoku s29 = new Sudoku(7,1,3);
		Sudoku s30 = new Sudoku(7,4,4);
		Sudoku s31 = new Sudoku(7,7,2);
		Sudoku s32 = new Sudoku(7,8,5);
		Sudoku s33 = new Sudoku(8,0,6);
		Sudoku s34 = new Sudoku(8,2,2);
		Sudoku s35 = new Sudoku(8,4,0);
		Sudoku s36 = new Sudoku(8,5,7);

				
		partiels.add(s1);
		partiels.add(s2);
		partiels.add(s3);
		partiels.add(s4);
		partiels.add(s5);
		partiels.add(s6);
		partiels.add(s7);
		partiels.add(s8);
		partiels.add(s7);
		partiels.add(s9);
		partiels.add(s10);
		partiels.add(s11);
		partiels.add(s12);
		partiels.add(s13);
		partiels.add(s14);
		partiels.add(s15);
		partiels.add(s16);
		partiels.add(s17);
		partiels.add(s18);
		partiels.add(s19);
		partiels.add(s20);
		partiels.add(s21);
		partiels.add(s22);
		partiels.add(s23);
		partiels.add(s24);
		partiels.add(s25);
		partiels.add(s26);
		partiels.add(s27);
		partiels.add(s28);
		partiels.add(s27);
		partiels.add(s29);
		partiels.add(s30);
		partiels.add(s31);
		partiels.add(s32);
		partiels.add(s33);
		partiels.add(s34);
		partiels.add(s35);
		partiels.add(s36);
		
		Sudoku.SudokuPrinter(partiels);
		
		//Second Sudoku problem [Hard difficulty] ( https://dingo.sbs.arizona.edu/~sandiway/sudoku/examples.html)
		System.out.println("        Hard level Sudoko probem:");
		Sudoku q1 = new Sudoku(0,1,1);
		Sudoku q2 = new Sudoku(1,3,5);
		Sudoku q3 = new Sudoku(1,8,2);
		Sudoku q4 = new Sudoku(2,1,6);
		Sudoku q5 = new Sudoku(2,2,3);
		Sudoku q6 = new Sudoku(2,4,7);
		Sudoku q7 = new Sudoku(3,5,2);
		Sudoku q8 = new Sudoku(3,8,1);
		Sudoku q9 = new Sudoku(4,1,7);
		Sudoku q10 = new Sudoku(4,4,3);
		Sudoku q11 = new Sudoku(4,7,0);
		Sudoku q12 = new Sudoku(5,0,5);
		Sudoku q13 = new Sudoku(5,3,4);
		Sudoku q14 = new Sudoku(6,4,0);
		Sudoku q15 = new Sudoku(6,6,6);
		Sudoku q16 = new Sudoku(6,7,7);
		Sudoku q17 = new Sudoku(7,0,4);
		Sudoku q18 = new Sudoku(7,5,8);
		Sudoku q19 = new Sudoku(8,7,3);
		List<Sudoku> v = new ArrayList<Sudoku>();
		v.add(q1);
		v.add(q2);
		v.add(q3);
		v.add(q4);
		v.add(q5);
		v.add(q6);
		v.add(q7);
		v.add(q8);
		v.add(q9);
		v.add(q10);
		v.add(q11);
		v.add(q12);
		v.add(q13);
		v.add(q14);
		v.add(q15);
		v.add(q16);
		v.add(q17);
		v.add(q18);
		v.add(q19);
		
		Sudoku.SudokuPrinter(v);
	}

}
