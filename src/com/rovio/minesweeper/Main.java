package com.rovio.minesweeper;

import java.util.Scanner;

import com.rovio.minesweeper.game.Board;
import com.rovio.minesweeper.game.Game;

public class Main {

	public static void main(String[] args) {
		Scanner scan=new Scanner(System.in);
		System.out.println("What gamemode would you like to play?");
		System.out.println("Beginner(1), Intermediate(2), or Expert(3)");
		int gamemode=scan.nextInt()-1;
		int[] defaultMines=new int[]{10,40,99};
		int[] defaultWidth=new int[]{8,16,16};
		int[] defaultHeight=new int[]{8,16,30};
		Game game=new Game(defaultMines[gamemode],defaultHeight[gamemode],defaultWidth[gamemode]);
		//Uncomment to cheat
		//System.out.println(game);
		System.out.println(game.getVisible());
		boolean repeat=true;
		boolean win=false;
		while (repeat) {
			System.out.println("Flag (false) or click (true)?");
			boolean flag=scan.next().startsWith("t");
			System.out.println("Select a space");
			int x=scan.nextInt();
			int y=scan.nextInt();
			if (flag) {
				Board temp=game.click(x, y);
				if (temp.getSpace(x, y)==-1) {
					repeat=false;
					win=false;
				}
				if (temp.getVisibleSpaces()==defaultWidth[gamemode]*defaultHeight[gamemode]-defaultMines[gamemode]) {
					repeat=false;
					win=true;
				}
			}
			else {
				game.flag(x, y);
			}
			System.out.println(game.getVisible());
		}
		if (win)
			System.out.println("Congratulations, you won!");
		else
			System.out.println("Sorry, you lost.");
		System.out.println(game);
	}

}
