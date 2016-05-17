package com.rovio.minesweeper.game;

public class Space {
	//Surrounding values
	//-1=bomb
	//0=empty with none surrounding
	//1-8=empty with its value surrounding
	private int surrounding;
	private boolean marked;
	private boolean clicked;
	
	public Space(int surrounding) {
		this.surrounding=surrounding;
		marked=false;
		clicked=false;
	}
	
	public int click() {
		clicked=true;
		return surrounding;
	}
	
	public void flag() {
		marked=!marked;
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
	
	
}
