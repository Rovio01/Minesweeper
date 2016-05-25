package com.rovio.minesweeper;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.rovio.minesweeper.ai.Solver;
import com.rovio.minesweeper.game.Board;
import com.rovio.minesweeper.game.Game;

public class Main {

	//Console-based game, works
	/*
	public static void main(String[] args) {
		Scanner scan=new Scanner(System.in);
		System.out.println("What gamemode would you like to play?");
		System.out.println("Beginner(1), Intermediate(2), or Expert(3)");
		int gamemode=scan.nextInt()-1;
		int[] defaultMines=new int[]{10,40,99};
		int[] defaultWidth=new int[]{8,16,16};
		int[] defaultHeight=new int[]{8,16,30};
		Game.newGame(defaultMines[gamemode],defaultHeight[gamemode],defaultWidth[gamemode]);
		//Uncomment to cheat
		//System.out.println(game);
		System.out.println(Game.getVisible());
		boolean repeat=true;
		boolean win=false;
		while (repeat) {
			System.out.println("Flag (false) or click (true)?");
			boolean flag=scan.next().startsWith("t");
			System.out.println("Select a space");
			int x=scan.nextInt();
			int y=scan.nextInt();
			if (flag) {
				Game.click(x, y);
				Board temp=Game.getVisible();
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
				Game.flag(x, y);
			}
			System.out.println(Game.getVisible());
		}
		if (win)
			System.out.println("Congratulations, you won!");
		else
			System.out.println("Sorry, you lost.");
		System.out.println(Game.getString());
		scan.next();
		scan.close();
	}
	*/
	
	//AI Testing main
	public static void main(String[] args) {
		long start=System.currentTimeMillis();
		Game.newGame(40, 16, 16);
		Game.click(0, 0);
		System.out.println(Game.getVisible());
		Solver.run();
		System.out.println(Game.getString());
		if (Game.isSolved()==-1) {
			System.out.println("Loss in "+Solver.iterations+" iterations over "+((System.currentTimeMillis()-start)/1000.0)+" seconds");
			return;
		}
		System.out.println("Solved in "+Solver.iterations+" iterations over "+((System.currentTimeMillis()-start)/1000.0)+" seconds");
	}
	
	//GUI-based game (not finished)
	/*
	public static void main(String[] args) {
		/*
		Game game=new Game(10, 10, 10);
		Solver solver=new Solver(game);
		System.out.println(Game.getString(););
		game.click(0, 0);
		System.out.println(solver.countFreeSquaresAround(0, 0));
		game.flag(0, 1);
		System.out.println(solver.countFreeSquaresAround(0, 0));
		* /
		//Remove above space if uncommenting this method
		execute();
	}
	*/
	
	//Icons
	//I wish there was a way to condense this in Eclipse, but putting brackets around it doesn't work
	//Faces
	public static final Icon coolFace=new ImageIcon(Main.class.getResource("assets/faces/coolface.png"));
	public static final Icon deadFace=new ImageIcon(Main.class.getResource("assets/faces/deadface.png"));
	public static final Icon faceClicked=new ImageIcon(Main.class.getResource("assets/faces/faceclicked.png"));
	public static final Icon happyFace=new ImageIcon(Main.class.getResource("assets/faces/happyface.png"));
	public static final Icon scaredFace=new ImageIcon(Main.class.getResource("assets/faces/scaredface.png"));
	public static final Icon robot=new ImageIcon(Main.class.getResource("assets/faces/robot.png"));
	public static final Icon robotClicked=new ImageIcon(Main.class.getResource("assets/faces/robotclicked.png"));
	
	//Numbers
	public static final Icon dash=new ImageIcon(Main.class.getResource("assets/numbers/-.png"));
	public static final Icon zero=new ImageIcon(Main.class.getResource("assets/numbers/0.png"));
	public static final Icon one=new ImageIcon(Main.class.getResource("assets/numbers/1.png"));
	public static final Icon two=new ImageIcon(Main.class.getResource("assets/numbers/2.png"));
	public static final Icon three=new ImageIcon(Main.class.getResource("assets/numbers/3.png"));
	public static final Icon four=new ImageIcon(Main.class.getResource("assets/numbers/4.png"));
	public static final Icon five=new ImageIcon(Main.class.getResource("assets/numbers/5.png"));
	public static final Icon six=new ImageIcon(Main.class.getResource("assets/numbers/6.png"));
	public static final Icon seven=new ImageIcon(Main.class.getResource("assets/numbers/7.png"));
	public static final Icon eight=new ImageIcon(Main.class.getResource("assets/numbers/8.png"));
	public static final Icon nine=new ImageIcon(Main.class.getResource("assets/numbers/9.png"));
	
	//Tiles
	public static final Icon covered=new ImageIcon(Main.class.getResource("assets/tiles/covered.png"));
	public static final Icon empty=new ImageIcon(Main.class.getResource("assets/tiles/empty.png"));
	public static final Icon flag=new ImageIcon(Main.class.getResource("assets/tiles/flag.png"));
	public static final Icon markedWrong=new ImageIcon(Main.class.getResource("assets/tiles/markedwrong.png"));
	public static final Icon mine=new ImageIcon(Main.class.getResource("assets/tiles/mine.png"));
	public static final Icon mineClicked=new ImageIcon(Main.class.getResource("assets/tiles/mineclicked.png"));
	public static final Icon tile1=new ImageIcon(Main.class.getResource("assets/tiles/tile1.png"));
	public static final Icon tile2=new ImageIcon(Main.class.getResource("assets/tiles/tile2.png"));
	public static final Icon tile3=new ImageIcon(Main.class.getResource("assets/tiles/tile3.png"));
	public static final Icon tile4=new ImageIcon(Main.class.getResource("assets/tiles/tile4.png"));
	public static final Icon tile5=new ImageIcon(Main.class.getResource("assets/tiles/tile5.png"));
	public static final Icon tile6=new ImageIcon(Main.class.getResource("assets/tiles/tile6.png"));
	public static final Icon tile7=new ImageIcon(Main.class.getResource("assets/tiles/tile7.png"));
	public static final Icon tile8=new ImageIcon(Main.class.getResource("assets/tiles/tile8.png"));
	
	static int seconds=0;
	static Timer timer;
	
	//May or may not end up being static, haven't decided
	public static void execute() {
		//TODO finish the GUI
		JFrame frame=new JFrame("Minesweeper");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridBagLayout());
		frame.setIconImage(((ImageIcon)mine).getImage());
		frame.setResizable(false);
		
		JPanel minefield=new JPanel(new GridBagLayout());
		GridBagConstraints c=new GridBagConstraints();
		
		JLabel face=new JLabel(happyFace);
		
		c.gridx=1;
		c.gridy=0;
		frame.add(face, c);
		
		//Timer
		JPanel timerPanel=new JPanel(new GridBagLayout());
		c=new GridBagConstraints();
		c.gridx=0;
		c.gridy=0;
		JLabel seconds1=new JLabel();
		timerPanel.add(seconds1, c);
		c=new GridBagConstraints();
		c.gridx=1;
		JLabel seconds2=new JLabel();
		timerPanel.add(seconds2, c);
		JLabel seconds3=new JLabel();
		c=new GridBagConstraints();
		c.gridx=2;
		timerPanel.add(seconds3, c);
		
		timer=new Timer();
		timer.scheduleAtFixedRate(new TimerTask(){
			@Override
			public void run() {
				setNumberPanel(seconds%10,seconds3);
				setNumberPanel((seconds/10)%10,seconds2);
				setNumberPanel((seconds/100)%100,seconds1);
				seconds++;
			}
		}, 0, 1000);
		
		c=new GridBagConstraints();
		c.gridx=2;
		c.gridy=0;
		frame.add(timerPanel, c);
		
		
		JPanel minesPanel=new JPanel(new GridBagLayout());
		JLabel mines1=new JLabel();
		
		
		c=new GridBagConstraints();
		c.gridx=0;
		c.gridy=1;
		c.gridwidth=3;
		frame.add(minefield,c);
		frame.pack();
		frame.setVisible(true);
	}

	
	//Sets the given JLabel to the specified integer between 0 and 9
	public static void setNumberPanel (int val, JLabel panel) {
		switch (val) {
		case 0: {
			panel.setIcon(zero);
			break;
		}
		case 1: {
			panel.setIcon(one);
			break;
		}
		case 2: {
			panel.setIcon(two);
			break;
		}
		case 3: {
			panel.setIcon(three);
			break;
		}
		case 4: {
			panel.setIcon(four);
			break;
		}
		case 5: {
			panel.setIcon(five);
			break;
		}
		case 6: {
			panel.setIcon(six);
			break;
		}
		case 7: {
			panel.setIcon(seven);
			break;
		}
		case 8: {
			panel.setIcon(eight);
			break;
		}
		case 9: {
			panel.setIcon(nine);
			break;
		}
		}
	}
	
	static void resetTimer() {
		seconds=0;
		//TODO resync the timer
	}
	
	public static void setTileImage(JLabel tile, int num) {
		//TODO
	}
}
