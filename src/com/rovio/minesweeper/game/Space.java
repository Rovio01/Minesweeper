package com.rovio.minesweeper.game;

public class Space {
	//Surrounding values
	//-1=bomb
	//0=empty with none surrounding
	//1-8=empty with its value surrounding
	private int surrounding;
	private boolean marked;
	private boolean clicked;
	private int x;
	private int y;
	
	public Space(int surrounding, int x, int y) {
		this.surrounding=surrounding;
		marked=false;
		clicked=false;
		this.x=x;
		this.y=y;
	}
	
	//Returns -1 if flagged
	public int click() {
		if (!marked) {
			clicked=true;
			return surrounding;
		}
		return -1;
	}
	
	public void flag() {
		if (!clicked) {
			marked=!marked;
		}
	}

	public int getValue() {
		return surrounding;
	}

	public boolean isMarked() {
		return marked;
	}

	public boolean isClicked() {
		return clicked;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
}
