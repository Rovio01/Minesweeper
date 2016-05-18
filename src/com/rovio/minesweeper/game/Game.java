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
		System.out.println("Initialization successful");
		for(int a=0;a<x;a++) {
			for(int b=0;b<y;b++) {
				board[a][b]=new Space(0);
			}
		}
		System.out.println("Zero in every space");
		for(int i=0;i<bombs;i++) {
			int bx=(int)(Math.random()*x);
			int by=(int)(Math.random()*y);
			while(board[bx][by].getValue()!=0) {
				bx=(int)(Math.random()*x);
				by=(int)(Math.random()*y);
			}
			board[bx][by]=new Space(-1);
		}
		System.out.println("Bombs Placed");
		
		/*for(int a=0;a<x;a++) {
			for(int b=0;b<y;b++) {
				try{
					System.out.println(""+a+" "+b+" "+board[a][b].getValue());
				} catch (NullPointerException n) {
					System.out.println(""+a+" "+b+" null");
				}
			}
		}
		*/
		
		for(int a=0;a<x;a++) {
			for(int b=0;b<y;b++) {
				if (board[a][b].getValue()==0) {
					//Count bombs
					int surrounding=0;
					
					//This is the worst way to code this
					if (a==0){
						if (b==0) {
							surrounding=
									toInt(board[a+1][b].getValue()==-1)+
									toInt(board[a+1][b+1].getValue()==-1)+
									toInt(board[a][b+1].getValue()==-1);
						}
						else if (b==y-1) {
							surrounding=
									toInt(board[a+1][b].getValue()==-1)+
									toInt(board[a+1][b-1].getValue()==-1)+
									toInt(board[a][b-1].getValue()==-1);
						}
						else {
							surrounding=
									toInt(board[a][b-1].getValue()==-1)+
									toInt(board[a+1][b-1].getValue()==-1)+
									toInt(board[a+1][b].getValue()==-1)+
									toInt(board[a+1][b+1].getValue()==-1)+
									toInt(board[a][b+1].getValue()==-1);
						}
					}
					else if (a==x-1) {
						if (b==0) {
							surrounding=
									toInt(board[a-1][b].getValue()==-1)+
									toInt(board[a-1][b+1].getValue()==-1)+
									toInt(board[a][b+1].getValue()==-1);
						}
						else if (b==y-1) {
							surrounding=
									toInt(board[a-1][b].getValue()==-1)+
									toInt(board[a-1][b-1].getValue()==-1)+
									toInt(board[a][b-1].getValue()==-1);
						}
						else {
							surrounding=
									toInt(board[a][b-1].getValue()==-1)+
									toInt(board[a-1][b-1].getValue()==-1)+
									toInt(board[a-1][b].getValue()==-1)+
									toInt(board[a-1][b+1].getValue()==-1)+
									toInt(board[a][b+1].getValue()==-1);
						}
					}
					else {
						if (b==0) {
							surrounding=
									toInt(board[a-1][b].getValue()==-1)+
									toInt(board[a-1][b+1].getValue()==-1)+
									toInt(board[a][b+1].getValue()==-1)+
									toInt(board[a+1][b+1].getValue()==-1)+
									toInt(board[a+1][b].getValue()==-1);
						}
						else if (b==y-1) {
							surrounding=
									toInt(board[a-1][b].getValue()==-1)+
									toInt(board[a-1][b-1].getValue()==-1)+
									toInt(board[a][b-1].getValue()==-1)+
									toInt(board[a+1][b-1].getValue()==-1)+
									toInt(board[a+1][b].getValue()==-1);
						}
						else {
							surrounding=
									toInt(board[a-1][b-1].getValue()==-1)+
									toInt(board[a-1][b].getValue()==-1)+
									toInt(board[a-1][b+1].getValue()==-1)+
									toInt(board[a][b-1].getValue()==-1)+
									toInt(board[a][b+1].getValue()==-1)+
									toInt(board[a+1][b-1].getValue()==-1)+
									toInt(board[a+1][b].getValue()==-1)+
									toInt(board[a+1][b+1].getValue()==-1);
						}
					}

					//Place the tile
					board[a][b]=new Space(surrounding);
					
				}
				//Try block shouldn't be necessary anymore
				try{
					System.out.println(""+a+" "+b+" "+board[a][b].getValue());
				} catch (NullPointerException n) {
					System.out.println(""+a+" "+b+" null");
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
	
	public static int toInt(boolean b) {
		return b?1:0;
	}
}
