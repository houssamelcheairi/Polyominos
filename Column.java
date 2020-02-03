
class Column extends Data{

	
	int S;
	//The names are the indices of the sets : "S_i" --> name = i
	int N;
	
	public void Column(Data U,Data D,Data L, Data R, Column C, int S, int N)
	{
		this.U = U;
		this.D = D;
		this.L = D;
		this.R = R;
		this.C = C;
		this.S = S;
		this.N = N;
	}
	//A constructor that gives a new column with size 0 and name N
	public void Column(int S, int N)
	{
		this.S=S;
		this.N=N;
	}
	public void Column()
	{
		this.S = 0;
	}
	
	//Creates a copy of the colum object
	public static Column Copy(Column C)
	{
		Column C_copy = new Column();
		C_copy.U = C.U;
		C_copy.D = C.D;
		C_copy.L = C.L;
		C_copy.R = C.R;
		
		C_copy.S = C.S;
		C_copy.N = C.N;
		
		return C_copy;
		
	}

}
