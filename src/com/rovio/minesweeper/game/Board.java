package com.rovio.minesweeper.game;

public class Board {
	//Values
	//-3=flagged
	//-2=not visible
	//-1=bomb
	//0=empty with no adjacent bombs
	//1-8=empty with that many adjacent bombs
	private int[][] visible;
	
	public Board(int[][] visible) {
		this.visible=visible;
	}
	
	public int getSpace(int x, int y) {
		return visible[x][y];
	}
	
	public int[][] getBoard() {
		return visible;
	}
	
	public void setSpace(int x, int y, int val) {
		visible[x][y]=val;
	}
	
	public int getVisibleSpaces() {
		int total=0;
		for (int a=0;a<visible.length;a++) {
			for (int b=0;b<visible[0].length;b++) {
				if (getSpace(a, b)!=-2)
					total++;
			}
		}
		return total;
	}
	
	public String toString() {
		String out="";
		for (int a=0;a<visible.length;a++) {
			for (int b=0;b<visible[0].length;b++) {
				if (getSpace(a, b)==-3)
					out+='F';
				else if (getSpace(a,b)==-1) 
					out+='@';
				else if (getSpace(a,b)==0)
					out+='_';
				else if (getSpace(a,b)==-2)
					out+='X';
				else
					out+=getSpace(a,b);
			}
			out+="\n";
		}
		return out;
	}
}
