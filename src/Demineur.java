import java.util.Scanner;
import java.util.Random;

public class Demineur {
	public static  Random gen = new Random();
	public static  Scanner kbr = new Scanner(System.in);

	 static int[][] board = new int[10][10];
	 static boolean[][] boardView = new boolean[10][10];
	 int[][] mines = new int[12][2];
	
	 final static int bomb = -1;
	 static int gameOver;

	public static void main(String[] args) {
		while (true) {
			gameOver = 0;
			boardReset();
			boardInsert(getMines(),bomb);
			boardFill();
			do{
				drawBoard(false);
				boardClick(getInput());
				boardCheckDone();
			}while (gameOver == 0);
			sayWinner(gameOver);
			drawBoard(true);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	static void boardCheckDone() {
		for (int y=0; y<10; y++)
			for (int x=0; x<10; x++)
				if (board[x][y]!=bomb && !boardView[x][y]){
					return;
				}
		gameOver=2;
	}            

	static void boardFill() {
		for (int y=0; y<10; y++)
			for (int x=0; x<10; x++)
				if (!(board[x][y]==bomb))
					for (int y1=-1; y1<=1; y1++)
						for (int x1=-1; x1<=1; x1++)
							if (0<=x+x1 && x+x1<10 && 0<=y+y1 && y+y1<10 && board[x+x1][y+y1]==bomb)
								board[x][y]++;
	}

	static void boardClick(int[] input) {
		int x = input[0];
		int y = input[1];
		if (!boardView[x][y]){
			boardView[x][y]=true;
			if (board[x][y] == bomb)
					gameOver = 1;
			else if (board[x][y] == 0){
				if (y>0)
					boardClick(new int[] {x, y-1});
				if (y<9)
					boardClick(new int[] {x, y+1});
				if (x>0){
					boardClick(new int[] {x-1, y});
					if (y>0)
						boardClick(new int[] {x-1, y-1});
					if (y<9)
						boardClick(new int[] {x-1, y+1});
				}
				if (x<9){
					boardClick(new int[] {x+1, y});
					if (y>0)
						boardClick(new int[] {x+1, y-1});
					if (y<9)
						boardClick(new int[] {x+1, y+1});
				}
			}
		}
	}

	static void drawBoard(boolean showAll){
		System.out.print("\t");
		for (int i=0; i<10; i++){
			System.out.print(i+" ");
		}
		System.out.print("\n\n");
		for (int y=0; y<10; y++){
			System.out.print(y+"\t");
			for (int x=0; x<10; x++){
				if (boardView[x][y]||showAll)
					System.out.print(translateBoard(board[x][y],showAll)+" ");
				else
					System.out.print("_ ");
			}
			System.out.print("\n");
		}
	}

	static char translateBoard(int i, boolean showAll) {
		switch(i){
		case -1: return (showAll)?'_':'X';
		case 0: return ' ';
		default: return (""+i).charAt(0);
		}
	}

	static void boardInsert(int[][] array, int num) {
		for (int i = 0; i < array.length; i++)
			board[array[i][0]][array[i][1]] = num;
	}
	
	 static int[] getInput() {
		String in;
		do {
			System.out.print("\nEntrer Ligne espace Colonne> ");
			in = kbr.nextLine();
		} while (!in.matches("(\\d)([ ])(\\d)"));
		return new int[] {Integer.parseInt(Character.toString(in.charAt(0))),
				Integer.parseInt(Character.toString(in.charAt(2))) };
	}

	 static int[][] getMines() {
		int[][] input = new int[12][2];
		for (int i = 0; i < 12; i =  (i + 1)) {
			do {
				input[i][0] = (gen.nextInt(10));
				input[i][1] = (gen.nextInt(10));
			} while (checkDupes(input, i));
		}
		return input;
	}

	 static boolean checkDupes(int[][] array, int i) {
		for (int j = 0; j < i; j++) {
			if ((array[j][0] == array[i][0]) && (array[j][1] == array[i][1])) {
				return true;
			}
		}
		return false;
	}

	 static void boardReset() {
		 board = new int[10][10];
		 boardView = new boolean[10][10];
	}

	 static void sayWinner(int a) {
		switch(a){
		case 2: System.out.print("Vous avez Gagner!\n");return;
		default: System.out.print("Vous avez perdu!\n");return;
		}
	}
}
