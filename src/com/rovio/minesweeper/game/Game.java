package com.rovio.minesweeper.game;

public class Game {
	//-1=bomb
	//0=Empty with no bombs surrounding
	//1-8=Empty with that many bombs surrounding
	private static int[][] board;
	//Same size as board, if true, space is flagged
	private static boolean[][] flags;
	//Same size as board, if false, space is not visible
	private static boolean[][] visible;
	private static int bombs;
	private static int x;
	private static int y;

	public static void newGame(int bombs, int x, int y) {
		Game.bombs=bombs;
		board=new int[x][y];
		flags=new boolean[x][y];
		visible=new boolean[x][y];

		Game.x=x;
		Game.y=y;
		//System.out.println("Initialization successful");
		
		//Actually unnecessary after removing Space class
		/*
		for(int a=0;a<x;a++) {
			for(int b=0;b<y;b++) {
				board[a][b]=0;
			}
		}
		//System.out.println("Zero in every space");
		*/
		
		//Place bombs
		for(int i=0;i<bombs;i++) {
			int bx=(int)(Math.random()*x);
			int by=(int)(Math.random()*y);
			while(board[bx][by]!=0||bx==0&&by==0) {
				bx=(int)(Math.random()*x);
				by=(int)(Math.random()*y);
			}
			board[bx][by]=-1;
		}
		//System.out.println("Bombs Placed");
		
		//Test, prints out all values, not updated to remove Space class
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
		
		//Counts bombs around each space and assigns that space the correct number
		for(int a=0;a<x;a++) {
			for(int b=0;b<y;b++) {
				if (board[a][b]==0) {
					//Count bombs
					int surrounding=0;

					//This is the worst way to code this
					if (a==0){
						if (b==0) {
							surrounding=
									toInt(board[a+1][b]==-1)+
									toInt(board[a+1][b+1]==-1)+
									toInt(board[a][b+1]==-1);
						}
						else if (b==y-1) {
							surrounding=
									toInt(board[a+1][b]==-1)+
									toInt(board[a+1][b-1]==-1)+
									toInt(board[a][b-1]==-1);
						}
						else {
							surrounding=
									toInt(board[a][b-1]==-1)+
									toInt(board[a+1][b-1]==-1)+
									toInt(board[a+1][b]==-1)+
									toInt(board[a+1][b+1]==-1)+
									toInt(board[a][b+1]==-1);
						}
					}
					else if (a==x-1) {
						if (b==0) {
							surrounding=
									toInt(board[a-1][b]==-1)+
									toInt(board[a-1][b+1]==-1)+
									toInt(board[a][b+1]==-1);
						}
						else if (b==y-1) {
							surrounding=
									toInt(board[a-1][b]==-1)+
									toInt(board[a-1][b-1]==-1)+
									toInt(board[a][b-1]==-1);
						}
						else {
							surrounding=
									toInt(board[a][b-1]==-1)+
									toInt(board[a-1][b-1]==-1)+
									toInt(board[a-1][b]==-1)+
									toInt(board[a-1][b+1]==-1)+
									toInt(board[a][b+1]==-1);
						}
					}
					else {
						if (b==0) {
							surrounding=
									toInt(board[a-1][b]==-1)+
									toInt(board[a-1][b+1]==-1)+
									toInt(board[a][b+1]==-1)+
									toInt(board[a+1][b+1]==-1)+
									toInt(board[a+1][b]==-1);
						}
						else if (b==y-1) {
							surrounding=
									toInt(board[a-1][b]==-1)+
									toInt(board[a-1][b-1]==-1)+
									toInt(board[a][b-1]==-1)+
									toInt(board[a+1][b-1]==-1)+
									toInt(board[a+1][b]==-1);
						}
						else {
							surrounding=
									toInt(board[a-1][b-1]==-1)+
									toInt(board[a-1][b]==-1)+
									toInt(board[a-1][b+1]==-1)+
									toInt(board[a][b-1]==-1)+
									toInt(board[a][b+1]==-1)+
									toInt(board[a+1][b-1]==-1)+
									toInt(board[a+1][b]==-1)+
									toInt(board[a+1][b+1]==-1);
						}
					}

					//Assign the value
					board[a][b]=surrounding;

				}
				/*
				//Try block shouldn't be necessary anymore
				try{
					System.out.println(""+a+" "+b+" "+board[a][b].getValue());
				} catch (NullPointerException n) {
					System.out.println(""+a+" "+b+" null");
				}
				 */
			}
		}

	}
	//toString can't be static, so it needs to be called explicitly, otherwise it won't work
	public static String getString() {
		String out="";
		for (int a=0;a<x;a++) {
			for (int b=0;b<y;b++) {
				if (board[a][b]==-1) 
					out+='@';
				else if (board[a][b]==0)
					out+='_';
				else
					out+=board[a][b];
			}
			out+="\n";
		}
		return out;
	}

	public static int toInt(boolean b) {
		return b?1:0;
	}

	public static Board getVisible() {
		int[][] temp=new int[board.length][board[0].length];
		for (int a=0;a<x;a++) {
			for (int b=0;b<y;b++) {
				if (flags[a][b])
					temp[a][b]=-3;
				else if (!visible[a][b])
					temp[a][b]=-2;
				else
					temp[a][b]=board[a][b];
			}
		}
		return new Board(temp);
	}

	public static void click(int x, int y) {
		if (!visible[x][y]&&!flags[x][y]) {
			visible[x][y]=true;
			if (board[x][y]==0) {
				try {
					click(x-1,y-1);
				} catch (Exception e) {}
				try {
					click(x-1,y);
				} catch (Exception e) {}
				try {
					click(x,y-1);
				} catch (Exception e) {}
				try {
					click(x-1,y+1);
				} catch (Exception e) {}
				try {
					click(x+1,y-1);
				} catch (Exception e) {}
				try {
					click(x,y+1);
				} catch (Exception e) {}
				try {
					click(x+1,y);
				} catch (Exception e) {}
				try {
					click(x+1,y+1);
				} catch (Exception e) {}
			}
		}
	}
	
	public static void flag(int x, int y) {
		if (!visible[x][y]) {
			flags[x][y]=!flags[x][y];
		}
		if (board[x][y]==-1)
			System.exit(0);
	}
	
	public static int bombs() {
		return bombs;
	}
	
	public static int flags() {
		int out=0;
		for(int a=0;a<x;a++) {
			for(int b=0;b<y;b++) {
				if(flags[a][b]) {
					out++;
				}
			}
		}
		return out;
	}
	
	public static int getWidth() {
		return x;
	}
	
	public static int getHeight() {
		return y;
	}
	
	public static int isSolved() {
		
		for (int a=0;a<x;a++) {
			for (int b=0;b<x;b++) {
				if (board[a][b]==-1&&visible[a][b]) {
					System.out.println("-1");
					return -1;
				}
				if ((board[a][b]!=-1&&!visible[a][b])) {
					System.out.println(0);
					return 0;
				}
			}
		}
		return 1;
	}
	
	public static int getSpace(int x, int y) {
		if (flags[x][y]) {
			return -3;
		}
		if (!visible[x][y]) {
			return -2;
		}
		
		return board[x][y];
	}
	
	public static boolean isFlagged(int x, int y) {
		return flags[x][y];
	}
	
	public static boolean[][] getFlags() {
		return flags;
	}
	
	public static void setFlag(int x, int y, boolean value) {
		flags[x][y]=value;
	}
}
