package com.rovio.minesweeper.game;

public class Game {
	private Space[][] board;
	private int bombs;
	private int x;
	private int y;
	
	public Game(int bombs, int x, int y) {
		this.bombs=bombs;
		board=new Space[x][y];
		this.x=x;
		this.y=y;
		for(int i=0;i<bombs;i++) {
			int bx=(int)(Math.random()*x);
			int by=(int)(Math.random()*y);
			while(board[bx][by]!=null) {
				bx=(int)(Math.random()*x);
				by=(int)(Math.random()*y);
			}
			board[bx][by]=new Space(-1);
		}
		for(int a=0;a<x;a++) {
			for(int b=0;b<y;b++) {
				
			}
		}
	}
}
