class SudokuBoard {
	int[][] inicial_matrix;
	int[][] current_matrix;
	int[][] memo;// [x,y,previous_number]
	
//1-criar um jogo dada uma matriz 9x9 a ser validada

	SudokuBoard(int[][] m) {
		if (SudokuAux.isSudoku(m) == true) {
			inicial_matrix = copy(m);
			current_matrix = copy(m);
			int maxUndo = numberOfZeros();
			memo= new int [maxUndo][3];
			bigNum();
		}
	}
	
	int[][] copy(int [][]m){
		int[][] new1 = new int[9][9];
		for (int i = 0; i < new1.length; i++) {
			for (int j = 0; j < new1.length; j++) {
				new1[i][j] = m[i][j];
			}
		}
		return new1;
	}
	

//2. Obter o número que está numa coordenada, ou zero caso não esteja preenchida.
	int getNumberAt(int x, int y) {
		return current_matrix[x-0 ][y -0];
	}

//	3. Efetuar uma jogada dando a coordenada e o valor.
//	Não é permitida uma jogada que se sobreponha a um número da
//	matriz inicial. Por outro lado, a jogada pode resultar
//	num tabuleiro inválido.
	void makeMove(int x, int y, int n) {
		
		int k=0;
		if (inicial_matrix[x][y] == 0) {
			k = current_matrix[x][y]; 
			current_matrix[x][y] = n;
			
		
		}
		savePlays (x, y, k);
	}

//4. Efetuar uma jogada aleatória numa das posições vazias, apenas respeitando a regra
//de não duplicar números num segmento.
	int[] makeRandomMove() {

		int y = (int) (Math.random() * 9);
		int x = (int) (Math.random() * 9);
		while (current_matrix[x][y] != 0) {
			y = (int) (Math.random() * 9);
			x = (int) (Math.random() * 9);
		}
		System.out.println("playing in " + x + " " + y);
		int seg = SudokuAux.getSeg(x, y);
		System.out.println("playing in segment " + seg);
		int n = (int) (Math.random() * 9 + 1);
		current_matrix[y][x] = n;

//
		while (SudokuAux.validSegment(SudokuAux.getSegment(current_matrix, current_matrix[x][y])) == false) {
			n = (int) (Math.random() * 9 + 1);
			current_matrix[y][x] = n;
		}
		System.out.println("played the value " + n);
		int l[] ={x,y,n};
		return l;
	}

//5. Reiniciar o tabuleiro para a configuração inicial.

	void resetBoard() {
		current_matrix = inicial_matrix;

	}

//6. Obter os segmentos que não estão válidos para a solução do Sudoku, 
//ou seja, que contêm números repetidos.

	int[] getWrongSeg (){
	
		int cont = 0;

		for (int i = 0; i < 9; i++) {
			if(SudokuAux.validSegment(SudokuAux.getSegment(current_matrix,i)) == false){
				cont=cont+1;
			}
		}
		int v[]=new int[cont];
		int cc = 0;
		for (int i = 0; i < 9; i++) {
			if(SudokuAux.validSegment(SudokuAux.getSegment(current_matrix,i)) == false){
				v[cc++]=i;
			}
		}
		
	return v;
	}
		
//7-Obter as linhas/colunas que não estão válidas para a solução do 
//Sudoku, ou seja, as que contêm números repetidos.

	int[] getWrongLine (){
	
		int[] invLine= new int[9];
		invLine=SudokuAux.getIndInvLine(current_matrix);
		return invLine;		
	}	
	int[] getWrongCol (){
		
		int[] invCol= new int[9];
		invCol=SudokuAux.getIndInvCol(current_matrix);
		return invCol;		
	}

//8. Saber se o Sudoku já está concluído	

	public boolean isFinishedSudoku(){
		return numberOfZeros()==0 && SudokuAux.isSudoku(current_matrix);
	}
	
	
	private int numberOfZeros() {
	
		int cont=0;
		for (int i = 0; i < current_matrix.length; i++) {
			for (int j = 0; j < current_matrix[i].length; j++) {
				
				if(current_matrix[i][j]==0)
					cont=cont+1;
			}
		
		}
		
		return cont;
		
	}
	
//9. Permitir anular jogadas com um mecanismo de undo, com memória
//	suficiente para o número de posições iniciais vazias

	public void bigNum (){
		
		for (int i = 0; i < memo.length; i++) {
				memo[i][2]=1000;
			}
		}
	
	
	public void savePlays (int x, int y, int n){
		
		for (int i = 0; i < memo.length; i++) {
			if(memo[i][2]==1000){
				memo[i][0]=x;
				memo[i][1]=y;
				memo[i][2]=n;			
				return;
			}
		}
	}
		 
	public int[] undo (){
		int[] undid = null;
		for (int i = memo.length-1;  i>=0; i--) {
			
			if(memo[i][2] != 1000){
				current_matrix[memo[i][0]][memo[i][1]] = memo[i][2];
				undid = new int[]{memo[i][0],memo[i][1], memo[i][2]};
				memo[i][2]=1000;
				return undid;
			}
		}
		return undid;
		
	}
		
	static void test(int[][] m){
		SudokuBoard n = new SudokuBoard(m);
		n.makeMove(0,1,3);
		n.undo();
		return;
	}
		
		
	


//auxiliar matri

	public int [][] getInicial(){
		return inicial_matrix;
	}
	public int[][] getCurrent(){
		return current_matrix;
	}
}