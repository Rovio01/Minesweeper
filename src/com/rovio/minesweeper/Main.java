package com.rovio.minesweeper;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Main {

	//Console main
	/*
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
		scan.next();
		scan.close();
	}
	*/
	
	//GUI main
	public static void main(String[] args) {
		new Main().execute();
	}
	
	//Faces
	public static final Icon coolFace=new ImageIcon(Main.class.getResource("assets/faces/coolface.png"));
	public static final Icon deadFace=new ImageIcon(Main.class.getResource("assets/faces/deadface.png"));
	public static final Icon faceClicked=new ImageIcon(Main.class.getResource("assets/faces/faceclicked.png"));
	public static final Icon happyFace=new ImageIcon(Main.class.getResource("assets/faces/happyface.png"));
	public static final Icon scaredFace=new ImageIcon(Main.class.getResource("assets/faces/scaredface.png"));
	
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
	
	
	
	//May or may not end up being static, haven't decided
	public void execute() {
		
		JFrame frame=new JFrame("Minesweeper");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridBagLayout());
		frame.setIconImage(((ImageIcon)mine).getImage());
		
		JPanel minefield=new JPanel(new GridBagLayout());
		GridBagConstraints c=new GridBagConstraints();
		
		JLabel tempLabel=new JLabel(mine);
		c.gridx=0;
		c.gridy=0;
		frame.add(tempLabel, c);
		
		JButton face=new JButton(happyFace);
		face.setContentAreaFilled(false);
		face.setBorderPainted(false);
		
		face.setIcon(happyFace);
		c.gridx=1;
		c.gridy=0;
		frame.add(face, c);
		
		c.gridx=0;
		c.gridy=1;
		c.gridwidth=3;
		frame.add(minefield,c);
		frame.pack();
		frame.setVisible(true);
	}
	
	public static void addComponentsToPane(Container pane) {
		pane.setLayout(new GridBagLayout());
		
	}
}
