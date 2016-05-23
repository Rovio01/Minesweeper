package com.rovio.minesweeper.ai;

import com.rovio.minesweeper.game.Board;
import com.rovio.minesweeper.game.Game;

public class Solver extends Thread {

	private Game game;
	private Board currentGame;
	private int x,y;


	public Solver(Game game) {
		this.game=game;
		currentGame=game.getVisible();
		x=game.getWidth();
		y=game.getWidth();
	}

	public void iterate() {
		//Strategies section
		for (int a=0;a<x;a++) {
			for (int b=0;b<y;b++) {
				//If it has enough bombs around it, open all other surrounding spaces

			}
		}

		//Update instance variables
		currentGame=game.getVisible();
	}

	public void run() {
		while (!game.isSolved()) {
			//TODO Solve the board
		}
	}

	private int getSurroundingBombs(int a, int b) {
		int out=0;

		return out;
	}

	private int countFlagsAround(boolean[][] array, int x, int y) {
		int mines = 0;

		// See if we're on the edge of the board
		boolean oU = false, oD = false, oL = false, oR = false;
		if(x == 0) oU = true;
		if(y == 0) oL = true;
		if(x == this.x-1) oD = true;
		if(y == this.y-1) oR = true;

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

	// How many unopened squares around this square?
	public int countFreeSquaresAround(int x, int y) {
		updateBoard();
		int freeSquares = 0;

		if(onScreen(x-1,y)==-2) freeSquares++;
		if(onScreen(x+1,y)==-2) freeSquares++;
		if(onScreen(x,y-1)==-2) freeSquares++;
		if(onScreen(x,y+1)==-2) freeSquares++;
		if(onScreen(x-1,y-1)==-2) freeSquares++;
		if(onScreen(x-1,y+1)==-2) freeSquares++;
		if(onScreen(x+1,y-1)==-2) freeSquares++;
		if(onScreen(x+1,y+1)==-2) freeSquares++;

		return freeSquares;
	}

	private int onScreen(int x, int y) {
		if(x < 0 || y < 0 || x > this.x-1 || y > this.y-1)
			return -10;
		return currentGame.getSpace(x, y);
	}
	
	private void updateBoard() {
		currentGame=game.getVisible();
	}
}
