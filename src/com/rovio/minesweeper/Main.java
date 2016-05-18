package com.rovio.minesweeper;

import java.util.Scanner;

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
		System.out.println(new Game(defaultMines[gamemode],defaultHeight[gamemode],defaultWidth[gamemode]));
	}

}
