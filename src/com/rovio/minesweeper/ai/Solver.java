package com.rovio.minesweeper.ai;

import com.rovio.minesweeper.game.Board;
import com.rovio.minesweeper.game.Game;

public class Solver {
	
	private Game game;
	private Board currentGame;
	
	public Solver(Game game) {
		this.game=game;
		currentGame=game.getVisible();
	}
	
	public void newGame(Game game) {
		this.game=game;
		currentGame=game.getVisible();
	}
	
	public void iterate() {
		
	}
}
