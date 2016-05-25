package com.rovio.minesweeper.ai;

import com.rovio.minesweeper.game.Board;
import com.rovio.minesweeper.game.Game;

public class Solver {

	
	private static Board currentGame;
	private static int x,y;


	public static void startSolver() {
		
		currentGame=Game.getVisible();
		x=Game.getWidth();
		y=Game.getWidth();
	}

	private static void attemptFlagMine() {
		for (int a=0;a<x;a++) {
			for (int b=0;b<y;b++) {
				updateBoard();
				
				if(onScreen(a,b) >= 1){
			          int curNum = onScreen(a,b);

			          // Flag necessary squares
			          if(curNum == countFreeSquaresAround(a,b)){
			        	  //System.out.println("Operating on "+a+","+b);
			            for(int ii=0; ii<x; ii++){
			              for(int jj=0; jj<y; jj++){
			                if(Math.abs(ii-a)<=1 && Math.abs(jj-b)<=1){
			                	//System.out.println("Space "+(ii)+","+(jj)+" is in range");
			                  if(onScreen(ii,jj) == -2 && !Game.getFlags()[ii][jj]){
			                	  //System.out.println("Flagging a mine at "+ii+","+jj);
			                    Game.setFlag(ii, jj,true);
			                    updateBoard();
			                  }
			                }
			              }
			            }
			          }


			        }
				/*
				if (countFreeSquaresAround(a,b)-countFlagsAround(a,b)==currentGame.getSpace(a, b)-countFlagsAround(a,b)) {
					//TODO Flag all spaces around if not visible
					if(onScreen(a-1,b)==-2&&!Game.isFlagged(a-1,b)){
						Game.flag(a-1,b);
						System.out.println("Flagging "+(a-1)+","+b);
						}
					if(onScreen(a+1,b)==-2&&!Game.isFlagged(a+1,b)) {
						Game.flag(a+1,b);
						System.out.println("Flagging "+(a+1)+","+(b));
					}
					if(onScreen(a,b-1)==-2&&!Game.isFlagged(a,b-1)) {
						Game.flag(a,b-1);
						System.out.println("Flagging "+(a)+","+(b-1));
					}
					if(onScreen(a,b+1)==-2&&!Game.isFlagged(a,b+1)) {
						Game.flag(a,b+1);
						System.out.println("Flagging "+(a)+","+(b+1));
					}
					if(onScreen(a-1,b-1)==-2&&!Game.isFlagged(a-1,b-1)) {
						Game.flag(a-1,b-1);
						System.out.println("Flagging "+(a-1)+","+(b-1));
					}
					if(onScreen(a-1,b+1)==-2&&!Game.isFlagged(a-1,b+1)) {
						Game.flag(a-1,b+1);
						System.out.println("Flagging "+(a-1)+","+(b+1));
					}
					if(onScreen(a+1,b-1)==-2&&!Game.isFlagged(a+1,b-1)) {
						Game.flag(a+1,b-1);
						System.out.println("Flagging "+(a+1)+","+(b-1));
					}
					if(onScreen(a+1,b+1)==-2&&!Game.isFlagged(a+1,b+1)) {
						Game.flag(a+1,b+1);
						System.out.println("Flagging "+(a+1)+","+(b+1));
					}
					solved++;
					updateBoard();
				}
				*/
			}
		}
	}
	
	private static void attemptMove() {
		for (int a=0;a<x;a++) {
			for (int b=0;b<y;b++) {
				middleClick(a,b);
			}
		}
	}
	
	private static int solved;
	private static Board temp;
	private static Board oldTemp;
	public static int iterations=0;
	
	public static void run() {
		startSolver();
		//System.out.println("Starting the Solver");
		while (Game.isSolved()==0) {
			iterations++;
			temp=currentGame.clone();
			//TODO Solve the board
			solved=0;
			if (Game.isSolved()!=0) return;
			attemptFlagMine();
			attemptMove();
			currentGame=Game.getVisible();
			System.out.println(currentGame);
			
			updateBoard();
			
		}
		int[][] temp=currentGame.getBoard();
		String out="";
		/*
		for(int a=0;a<x;a++) {
			for (int b=0;b<x;b++) {
				out+=temp[a][b];
				if (temp[a][b]>=0) out+=" ";
			}
			out+="\n";
		}
		System.out.println(out);
		*/
	}

	private static int countFlagsAround(int x, int y) {
		int mines = 0;

		// See if we're on the edge of the board
		boolean oU = false, oD = false, oL = false, oR = false;
		if(x == 0) oU = true;
		if(y == 0) oL = true;
		if(x == Solver.x-1) oD = true;
		if(y == Solver.y-1) oR = true;

		if(!oU && currentGame.getSpace(x-1,y)==-3) mines++;
		if(!oL && currentGame.getSpace(x,y-1)==-3) mines++;
		if(!oD && currentGame.getSpace(x+1,y)==-3) mines++;
		if(!oR && currentGame.getSpace(x,y+1)==-3) mines++;
		if(!oU && !oL && currentGame.getSpace(x-1,y-1)==-3) mines++;
		if(!oU && !oR && currentGame.getSpace(x-1,y+1)==-3) mines++;
		if(!oD && !oL && currentGame.getSpace(x+1,y-1)==-3) mines++;
		if(!oD && !oR && currentGame.getSpace(x+1,y+1)==-3) mines++;

		return mines;
	}

	private static int countFlagsAround(boolean[][] array, int i, int j){
	    int mines = 0;

	    // See if we're on the edge of the board
	    boolean oU = false, oD = false, oL = false, oR = false;
	    if(i == 0) oU = true;
	    if(j == 0) oL = true;
	    if(i == array.length-1) oD = true;
	    if(j == array[0].length-1) oR = true;

	    if(!oU && array[i-1][j]) mines++;
	    if(!oL && array[i][j-1]) mines++;
	    if(!oD && array[i+1][j]) mines++;
	    if(!oR && array[i][j+1]) mines++;
	    if(!oU && !oL && array[i-1][j-1]) mines++;
	    if(!oU && !oR && array[i-1][j+1]) mines++;
	    if(!oD && !oL && array[i+1][j-1]) mines++;
	    if(!oD && !oR && array[i+1][j+1]) mines++;

	    return mines;
	  }
	
	// How many unopened squares around this square?
	public static int countFreeSquaresAround(int x, int y) {
		updateBoard();
		int freeSquares = 0;

		if(onScreen(x-1,y)==-2||onScreen(x-1,y)==-3) freeSquares++;
		if(onScreen(x+1,y)==-2||onScreen(x+1,y)==-3) freeSquares++;
		if(onScreen(x,y-1)==-2||onScreen(x,y-1)==-3) freeSquares++;
		if(onScreen(x,y+1)==-2||onScreen(x,y+1)==-3) freeSquares++;
		if(onScreen(x-1,y-1)==-2||onScreen(x-1,y-1)==-3) freeSquares++;
		if(onScreen(x-1,y+1)==-2||onScreen(x-1,y+1)==-3) freeSquares++;
		if(onScreen(x+1,y-1)==-2||onScreen(x+1,y-1)==-3) freeSquares++;
		if(onScreen(x+1,y+1)==-2||onScreen(x+1,y+1)==-3) freeSquares++;
		//System.out.println("Space "+y+","+y+" has "+freeSquares+" free squares");
		return freeSquares;
	}

	private static int onScreen(int x, int y) {
		if(x < 0 || y < 0 || x > Solver.x-1 || y > Solver.y-1)
			return -10;
		return currentGame.getSpace(x, y);
	}
	
	private static void updateBoard() {
		currentGame=Game.getVisible();
	}
	
	private static void middleClick(int x, int y) {
		if (currentGame.getSpace(x,y)>=0&&currentGame.getSpace(x, y)==countFlagsAround(x,y)) {
			try{
				//System.out.println("Trying "+(x-1)+","+(y-1));
				Game.click(x-1, y-1);
				//System.out.println("Clicked");
				}catch(Exception e){}
			try{
				//System.out.println("Trying "+(x)+","+(y-1));
				Game.click(x, y-1);
				//System.out.println("Clicked");
				}catch(Exception e){}
			try{
				//System.out.println("Trying "+(x-1)+","+(y));
				Game.click(x-1, y);
				//System.out.println("Clicked");
				}catch(Exception e){}
			try{
				//System.out.println("Trying "+(x+1)+","+(y-1));
				Game.click(x+1, y-1);
				//System.out.println("Clicked");
				}catch(Exception e){}
			try{
				//System.out.println("Trying "+(x-1)+","+(y+1));
				Game.click(x-1, y+1);
				//System.out.println("Clicked");
				}catch(Exception e){}
			try{
				//System.out.println("Trying "+(x)+","+(y+1));
				Game.click(x, y+1);
				//System.out.println("Clicked");
				}catch(Exception e){}
			try{
				//System.out.println("Trying "+(x+1)+","+(y));
				Game.click(x+1, y);
				//System.out.println("Clicked");
			}catch(Exception e){}
			try{//System.out.println("Trying "+(x+1)+","+(y+1));
				Game.click(x+1, y+1);
				//System.out.println("Clicked");
			}catch(Exception e){}
		}
		updateBoard();
	}
	
	private static boolean[][] knownMines=null;
	private static boolean[][] knownEmpty=null;
	private static int[][] tankBoard=null;
	
	private static void tankRecurse(Board possibleBoard, int k) {
		int countFlags=0;
		for (int a=0;a<x;a++) {
			for (int b=0;b<y;b++) {
				//Increment the flags if there is a flag in this space
				if(knownMines[a][b])
					countFlags++;
				
				
			}
		}
	}
}
