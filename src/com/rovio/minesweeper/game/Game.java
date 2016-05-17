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
				if (board[a][b]!=null) {
					//Count bombs
					int surrounding=0;
					if (a!=0&&b!=0&&board[a-1][b-1].getValue()==-1)
						surrounding++;
					if (a!=0&&board[a-1][b].getValue()==-1)
						surrounding++;
					if (b!=0&&board[a][b-1].getValue()==-1)
						surrounding++;
					if (a!=x&&b!=0&&board[a+1][b-1].getValue()==-1)
						surrounding++;
					if (a!=x&&board[a+1][b]!=null)
						surrounding++;
					if (a!=0&&b!=y&&board[a-1][b+1]!=null)
						surrounding++;
					if (b!=y&&board[a][b+1]!=null)
						surrounding++;
					if (a!=x&&b!=y&&board[a+1][b+1]!=null)
						surrounding++;
					//Place the tile
					board[a][b]=new Space(surrounding);
				}
			}
		}
		
	}
	
	public String toString() {
		String out="";
		//TODO still needs to be finished
		for (int a=0;a<x;a++) {
			for (int b=0;b<y;b++) {
				if (board[a][b].getValue()==-1) 
					out+='@';
				else if (board[a][b].getValue()==0)
					out+='_';
				else
					out+=board[a][b].getValue();
			}
			out+="\n";
		}
		return out;
	}
}
