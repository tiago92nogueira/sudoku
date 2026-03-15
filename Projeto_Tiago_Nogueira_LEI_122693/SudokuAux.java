class SudokuAux {

//1 - Validar se uma matriz de inteiros 9x9 representa um Sudoku válido, 
//	sendo que o valor zero representa uma posição não jogada. 
	// FALTA TESTAR!!!

	static boolean isValidLine(int[] v) {

		for (int i = 0; i < v.length; i++) {

			if (v[i] < 0 || v[i] > 9)
				return false;
			for (int j = 0; j < v.length; j++) {

				if (v[i] == v[j] && j != i && v[j] != 0)
					return false;
			}
		}
		return true;
	}

	
		
	static boolean isSudoku(int m[][]) {
		for (int i = 0; i < 9; i = i + 3) {
			for (int j = 0; j < 9; j = j + 3) {
				boolean isValid = isValidLine(new int[] { m[i][j], m[i][j + 1], m[i][j + 2], m[i + 1][j],
						m[i + 1][j + 1], m[i + 1][j + 2], m[i + 2][j], m[i + 2][j + 1], m[i + 2][j + 2] });
				if (isValid == false)
					return false;
			}
		}

		for (int i = 0; i < m.length; i++) {
			if (isValidLine(m[i]) == false)
				return false;
		}

		for (int i = 0; i < 9; i++) {

			boolean isValid10 = isValidLine(
					new int[] { m[0][i], m[3][i], m[6][i], m[1][i], m[4][i], m[7][i], m[2][i], m[5][i], m[8][i] });
			if (isValid10 == false)
				return false;

		}

		return true;
	}

//2 - percentagem de 0 a 1 ou de 0 a 100
	static void randomGameMatrix(int[][] compMatrix, double x) {
		for (int i = 0; i < compMatrix.length; i++) {
			for (int j = 0; j < compMatrix[i].length; j++) {
				double rand_aux = Math.random();
				if (rand_aux <= x) {
					compMatrix[i][j] = 0;
				}
			}
		}
	}

//3 - Produzir uma String com o conteúdo de uma matriz de inteiros. TESTADA

	static String stringMatrix(int[][] m) {
		String s1 = "";

		for (int i = 0; i < m.length; i++) {
			for (int j = 0; j < m[i].length; j++) {

				s1 = s1 + m[i][j];

			}
			s1 = s1 + "\n";
		}
		return s1;
	}

//4-Produzir uma imagem a cores com o desenho do tabuleiro Sudoku a 
//	partir de uma matrix de inteiros 9x9. Poderá ser útil desenvolver
//	procedimentos auxiliares para abordar partes do objetivo 
//	(p.e. desenho de grelha, desenho de números). FEITA E TESTADA	

//aux grid maker	
	static void drawGrid(ColorImage grid1) {

		for (int i = 0; i < grid1.getWidth(); i++) {
			for (int j = 0; j < grid1.getHeight(); j++) {
				if (i % 45 == 0 || j % 45 == 0) {
					grid1.setColor(i, j, Color.BLACK);
				}
			}
		}
	}
//certa

	static ColorImage sudokuDraw(int[][] m) {
		ColorImage c = new ColorImage(406, 406, Color.WHITE);
		drawGrid(c);

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				int x = (i * 45) + 23;
				int y = (j * 45) + 23;
				if (m[i][j] != 0)
					c.drawCenteredText(x, y, String.valueOf(m[i][j]), 20, Color.BLACK);
			}
		}

		return c;
	}

//5. Dada uma imagem resultante de (4), alterar uma posição da imagem 
//	do tabuleiro,fornecendo uma coordenada e o número a colocar	
//a FUNCAO TA CERTA MAS COMO É QUE TIRO O INT M DALI E TORNO-O NUMA
// VARIAVEL DA FUNCAO ANTIGA PARA NAO SER SEMPRE A MESMA
//é void?
	static void switchNumber(ColorImage sudoku, int linha, int coluna, int n) {
		int x = ((linha) * 45) + 23;
		int y = ((coluna) * 45) + 23;
		for (int i = 0 - 20; i < 20; i++) {
			for (int j = 0 - 20; j < 20; j++) {

				sudoku.setColor(x + i, y + j, Color.WHITE);
			}
		}
		if (n != 0) sudoku.drawCenteredText(x, y, String.valueOf(n), 20, Color.BLACK);
	}

//6. Dada uma imagem resultante de (4), marcar uma linha do tabuleiro 
//	com contorno vermelho (para sinalizar que está inválida), 
//	fornecendo o índice da linha

	static void redLine(ColorImage board, int line) {

		int x = ((line) * 45);

		for (int i = 0; i < board.getWidth(); i++) {
			board.setColor(i, x, Color.RED);
			board.setColor(i, x + 1, Color.RED);
			board.setColor(i, x + 44, Color.RED);
			board.setColor(i, x + 45, Color.RED);

		}
		for (int i = x; i < x + 45; i++) {
			board.setColor(0, i, Color.RED);
			board.setColor(board.getWidth() - 1, i, Color.RED);
			board.setColor(1, i, Color.RED);
			board.setColor(board.getWidth() - 2, i, Color.RED);
		}

	}

//7. Análogo a (6), mas para colunas. testado

	static void redCol(ColorImage board, int col) {

		int x = ((col ) * 45);

		for (int i = 0; i < board.getHeight(); i++) {
			board.setColor(x + 44, i, Color.RED);
			board.setColor(x + 45, i, Color.RED);
			board.setColor(x, i, Color.RED);
			board.setColor(x + 1, i, Color.RED);

		}
		for (int i = x; i < x + 45; i++) {
			board.setColor(i, 0, Color.RED);
			board.setColor(i, board.getHeight() - 1, Color.RED);
			board.setColor(i, 1, Color.RED);
			board.setColor(i, board.getWidth() - 2, Color.RED);
		}

	}

//8 - Dada uma imagem resultante de (4), marcar o segmento com um contorno
//	vermelho (para sinalizar que está inválido), fornecendo o índice 
//	do segmento. Um segmento corresponde a um dos nove quadrados 3x3.

	static void redSquare(ColorImage board, int line, int col) {

		int y = ((line) * 135);
		int x = ((col) * 135);

		for (int i = x; i < 135 + x; i++) {
			board.setColor(i, y, Color.RED);
			board.setColor(i, y + 1, Color.RED);
			board.setColor(i, y + 134, Color.RED);
			board.setColor(i, y + 135, Color.RED);

		}
		for (int i = y; i < 135 + y; i++) {
			board.setColor(x, i, Color.RED);
			board.setColor(x + 1, i, Color.RED);
			board.setColor(x + 134, i, Color.RED);
			board.setColor(x + 135, i, Color.RED);
		}

	}

	static int getSeg(int x, int y) {
		int segx;
		int segy;
		if (x < 3) {
			segx = 1;
		} else if (x < 6) {
			segx = 2;
		} else {
			segx = 3;
		}
		if (y < 3) {
			segy = 0;
		} else if (y < 6) {
			segy = 1;
		} else {
			segy = 2;
		}
		int seg = segx + 3 * segy;
		return seg;
	}

	static boolean validSegment(int m[][]) {

		for (int i = 0; i < m.length; i = i + 1) {
			for (int j = 0; j < m[i].length; j = j + 1) {

				if (m[i][j] < 0 || m[i][j] > 9) {
					System.out.println(m[i][j]);
					return false;
				}
			}
		}
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				for (int x = 0; x < 3; x++) {
					for (int y = 0; y < 3; y++) {

						if (i != x || j != y) {
							if (m[x][y] != 0 && (m[i][j] == m[x][y])) {

								return false;
							}
						}
					}
				}
			}
		}
		return true;
	}

	static int[][] getSegment(int[][] global, int n) {

		int[] coord = getCoordOfSeg(n);
		int x = coord[0];
		int y = coord[1];
		int m[][] = new int[3][3];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				m[i][j] = global[y + i][x + j];
			}
		}
		return m;
	}

	 static int[] getCoordOfSeg(int n) {
		int x;
		int y;
		if ((n - 1) % 3 == 0) {
			x = 0;
		} else if ((n - 2) % 3 == 0) {
			x = 1;
		} else {
			x = 2;
		}
		if (n <= 3) {
			y = 0;
		} else if (n <= 6) {
			y = 1;
		} else {
			y = 2;
		}
		int[] coord = new int[2];
		coord[0] = x;
		coord[1] = y;
		return coord;
	}

	static int[] getIndInvLine(int[][] sudoku) {
        
		int[] line = new int[9];
		int n= 0;
		
        for (int i = 0; i < sudoku.length; i++) {
        	
        	if(isValidLine(sudoku[i]) == false)
        		line[n] = 1;
        	n++;
        		
        }

        return line;
    }

	static int[] getIndInvCol(int[][] sudoku) {
       
		int[] col = new int[9];
		int n=0;
		
		
		for(int i=0;i<sudoku[0].length;i++){
			int[] vec = new int[9];
			for (int j = 0; j < sudoku.length; j++) 
				
				vec[j] = sudoku[j][i];
        	if(isValidLine(vec) == false)
        	col[n] = 1;
        n++;
		}
        return col;
    }

	
	
	
	
	
	
	static void testetstets() {
		int m[][] = { { 5, 3, 4, 6, 7, 8, 9, 1, 2 }, { 6, 7, 2, 1, 9, 5, 3, 4, 8 }, { 1, 9, 8, 3, 4, 2, 5, 6, 7 },
				{ 8, 5, 9, 7, 6, 1, 4, 2, 3 }, { 4, 2, 6, 8, 5, 3, 7, 9, 1 }, { 7, 1, 3, 9, 2, 4, 8, 5, 6 },
				{ 9, 6, 1, 5, 3, 7, 2, 8, 4 }, { 2, 8, 7, 4, 1, 9, 6, 3, 5 }, { 3, 4, 5, 2, 8, 6, 1, 7, 9 } };
		randomGameMatrix(m, 0.5);
		SudokuBoard sb = new SudokuBoard(m);
		int[][] c = sb.current_matrix;
		sb.makeRandomMove();
		int[][] c1 = sb.current_matrix;

		return;
	}


    public static boolean isValidSudokuSegment(int[][] segment) {
        // Verificar se cada número na matriz é único em cada linha e coluna
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (segment[i][j] != 0 && !isValid(segment, i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    static boolean isValid(int[][] segment, int row, int col) {
        // Verificar a linha
        for (int j = 0; j < 3; j++) {
            if (j != col && segment[row][j] == segment[row][col]) {
                return false;
            }
        }

        // Verificar a coluna
        for (int i = 0; i < 3; i++) {
            if (i != row && segment[i][col] == segment[row][col]) {
                return false;
            }
        }

        // Verificar o bloco 3x3
        int startRow = row - row % 3;
        int startCol = col - col % 3;
        for (int i = startRow; i < startRow + 3; i++) {
            for (int j = startCol; j < startCol + 3; j++) {
                if ((i != row || j != col) && segment[i][j] == segment[row][col]) {
                    return false;
                }
            }
        }

        return true;
    }
	
	
	
//////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////
	static void test() {
		int m[][] = { { 1, 3, 4, 6, 7, 8, 9, 5, 2 }, { 6, 7, 2, 1, 9, 5, 3, 4, 8 }, { 1, 9, 8, 3, 4, 2, 5, 6, 7 },
				{ 8, 5, 9, 7, 6, 1, 4, 2, 3 }, { 4, 2, 6, 8, 5, 3, 7, 9, 1 }, { 7, 1, 3, 9, 2, 4, 8, 5, 6 },
				{ 9, 6, 1, 5, 3, 7, 2, 8, 4 }, { 2, 8, 7, 4, 1, 9, 6, 3, 5 }, { 3, 4, 5, 2, 8, 6, 1, 7, 9 } };

		//randomGameMatrix(m,0.25);
		SudokuBoard su = new SudokuBoard(m);
	//			su.getWrongLineCol ();
		// ColorImage board = sudokuDraw(m);
		// switchNumber(board,2, 2, 8);
		// redSquare(board,3,3);
		// int[][] seg = getSegment (m, 7);

		// SudokuBoard.makeRandomMove();
		System.out.println("su.getWrongLineCol ()");
	}
}
