import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Sudoku {
	private SudokuBoard sudokuBoard;
	

	public ColorImage boardImage;

	
	
//1. Um objeto SudokuBoard com o estado atual do jogo;
	
//2. Uma imagem (ColorImage) que representa o �ecr� do jogo, 
//que vai sendo alterada � medida que s�o feitas as jogadas.	
		
	public Sudoku(String file, double difficulty) {
		int[][] m =load(file);
		SudokuAux.randomGameMatrix(m,difficulty);
		sudokuBoard = new SudokuBoard(m);
		boardImage = SudokuAux.sudokuDraw(m);
		//SudokuAux.redSquare(boardImage,1,1);
	
	}
	
	void refresh(int y, int x){
		boolean col = SudokuAux.isValidLine(sudokuBoard.getCurrent()[y]);
		
		boolean line = SudokuAux.isValidLine(new int[] { sudokuBoard.getCurrent()[0][x], sudokuBoard.getCurrent()[3][x], sudokuBoard.getCurrent()[6][x], sudokuBoard.getCurrent()[1][x], sudokuBoard.getCurrent()[4][x], sudokuBoard.getCurrent()[7][x], sudokuBoard.getCurrent()[2][x], sudokuBoard.getCurrent()[5][x], sudokuBoard.getCurrent()[8][x] });
	
		int seg = SudokuAux.getSeg(y,x);
		boolean square = SudokuAux.isValidSudokuSegment(SudokuAux.getSegment(sudokuBoard.getCurrent(),seg));
		//System.out.println(seg + " is " + square);
		if(!line){
			SudokuAux.redLine(boardImage,x);
		}
		if(!col){
			SudokuAux.redCol(boardImage,y);
		}
		if(!square){
			int[] coords = SudokuAux.getCoordOfSeg(seg);
			System.out.println(seg + " " + coords[0] + " " + coords[1]);

			SudokuAux.redSquare(boardImage,coords[0],coords[1]);
		}
	}

//	1. Efetuar e anular jogadas	
	
	//efetuar
	void move (int x, int y, int n){
		try{
		sudokuBoard.makeMove(x,y,n);
		SudokuAux.switchNumber(boardImage,x,y,n);
		refresh(x,y);
		}catch (Exception e) {
//			e.printStackTrace();
		}
	}
	//random jogada a respeitar apenas segmentos
	void randomPlay (){
		
	int[]o=sudokuBoard.makeRandomMove();
	
	int x = o[0];
	int y = o[1];
	int n = o[2];
	SudokuAux.switchNumber(boardImage,x,y,n);
	refresh(x,y);
	}
	
	//anular
	void undo (){
		
		int[] undid = sudokuBoard.undo();
		if (undid == null) {
			System.out.println("undo invalid");
			return;
		}
		SudokuAux.switchNumber(boardImage,undid[0],undid[1],undid[2]);
	}

//2. Gravar o estado do jogo para um ficheiro (*.sudgame).
	
	public void save (String game){
	
		try{
			PrintWriter writer = new PrintWriter(game);
		writer.println(SudokuAux.stringMatrix(sudokuBoard.getInicial()));
		writer.println(SudokuAux.stringMatrix(sudokuBoard.getCurrent()));
		writer.close();
		}
		catch(FileNotFoundException e){
			System.err.println("erro escrita ficheiro");
		}
	}

	   
	
//3. Carregar um jogo fornecendo o nome do ficheiro previamente gravado
//	(*.sudgame). a
	
		public int[][] load(String sudFile) {

		    int[][] sud = new int[9][9];

		    try {
//		        String s;
		        File file = new File(sudFile);
		        Scanner sc = new Scanner(file); 
		        for (int x = 0; sc.hasNextLine() && x < 9; x++) {
		            for (int i = 0; sc.hasNext() && i < 9; i++) {
		                sud[x][i] = sc.nextInt();
		            }
		        }
		        sc.close();
		        return sud;
		    } catch (FileNotFoundException e) {
		        System.out.println("File not found");
		    }

		    return null;
		}	

	
}