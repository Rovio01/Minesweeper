package com.rovio.minesweeper.ai;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

import com.rovio.minesweeper.game.Board;
import com.rovio.minesweeper.game.Game;

public class Solver {

	
	private static Board currentGame;
	private static int x,y;


	public static void startSolver() {
		
		currentGame=Game.getVisible();
		x=Game.getWidth();
		y=Game.getWidth();
	}

	private static void attemptFlagMine() {
		for (int a=0;a<x;a++) {
			for (int b=0;b<y;b++) {
				updateBoard();
				
				if(onScreen(a,b) >= 1){
			          int curNum = onScreen(a,b);

			          // Flag necessary squares
			          if(curNum == countFreeSquaresAround(a,b)){
			        	  //System.out.println("Operating on "+a+","+b);
			            for(int ii=0; ii<x; ii++){
			              for(int jj=0; jj<y; jj++){
			                if(Math.abs(ii-a)<=1 && Math.abs(jj-b)<=1){
			                	//System.out.println("Space "+(ii)+","+(jj)+" is in range");
			                  if(onScreen(ii,jj) == -2 && !Game.getFlags()[ii][jj]){
			                	  //System.out.println("Flagging a mine at "+ii+","+jj);
			                    Game.setFlag(ii, jj,true);
			                    updateBoard();
			                  }
			                }
			              }
			            }
			          }


			        }
				/*
				if (countFreeSquaresAround(a,b)-countFlagsAround(a,b)==currentGame.getSpace(a, b)-countFlagsAround(a,b)) {
					//TODO Flag all spaces around if not visible
					if(onScreen(a-1,b)==-2&&!Game.isFlagged(a-1,b)){
						Game.flag(a-1,b);
						System.out.println("Flagging "+(a-1)+","+b);
						}
					if(onScreen(a+1,b)==-2&&!Game.isFlagged(a+1,b)) {
						Game.flag(a+1,b);
						System.out.println("Flagging "+(a+1)+","+(b));
					}
					if(onScreen(a,b-1)==-2&&!Game.isFlagged(a,b-1)) {
						Game.flag(a,b-1);
						System.out.println("Flagging "+(a)+","+(b-1));
					}
					if(onScreen(a,b+1)==-2&&!Game.isFlagged(a,b+1)) {
						Game.flag(a,b+1);
						System.out.println("Flagging "+(a)+","+(b+1));
					}
					if(onScreen(a-1,b-1)==-2&&!Game.isFlagged(a-1,b-1)) {
						Game.flag(a-1,b-1);
						System.out.println("Flagging "+(a-1)+","+(b-1));
					}
					if(onScreen(a-1,b+1)==-2&&!Game.isFlagged(a-1,b+1)) {
						Game.flag(a-1,b+1);
						System.out.println("Flagging "+(a-1)+","+(b+1));
					}
					if(onScreen(a+1,b-1)==-2&&!Game.isFlagged(a+1,b-1)) {
						Game.flag(a+1,b-1);
						System.out.println("Flagging "+(a+1)+","+(b-1));
					}
					if(onScreen(a+1,b+1)==-2&&!Game.isFlagged(a+1,b+1)) {
						Game.flag(a+1,b+1);
						System.out.println("Flagging "+(a+1)+","+(b+1));
					}
					solved++;
					updateBoard();
				}
				*/
			}
		}
	}
	
	private static boolean success;
	
	private static void attemptMove() {
		success=false;
		
		for(int i=0; i<x; i++){
		      for(int j=0; j<y; j++){
		        
		        if(onScreen(i,j) >= 1){

		          // Count how many mines around it
		          int curNum = currentGame.getBoard()[i][j];
		          int mines = countFlagsAround(Game.getFlags(),i,j);
		          int freeSquares = countFreeSquaresAround(i,j);


		          // Click on the deduced non-mine squares
		          if(curNum == mines && freeSquares > mines){
		            success = true;

		            // Use the chord or the classical algorithm
		            if(freeSquares - mines > 1){
		              middleClick(i,j);
		              continue;
		            }

		            // Old algorithm: don't chord
		            for(int ii=0; ii<x; ii++){
		              for(int jj=0; jj<y; jj++){
		                if(Math.abs(ii-i)<=1 && Math.abs(jj-j)<=1){
		                  if(onScreen(ii,jj) == -2 && !Game.getFlags()[ii][jj]){
		                    Game.click(ii,jj);
		                    
		                  }
		                }
		              }
		            }

		          }
		        }
		      }
		    }
		/*
		for (int a=0;a<x;a++) {
			for (int b=0;b<y;b++) {
				middleClick(a,b);
			}
		}
		*/
		if (success) return;
		
		System.out.println("Beginning Tank Algorithm");
		updateBoard();
		tankSolver();
	}
	
	public static int iterations=0;
	private static boolean notLost=true;
	public static void run() {
		startSolver();
		Scanner scan=new Scanner(System.in);
		//System.out.println("Starting the Solver");
		while (Game.isSolved()==0&&notLost) {
			iterations++;
			//TODO Solve the board
			if (Game.isSolved()!=0) return;
			attemptFlagMine();
			attemptMove();
			currentGame=Game.getVisible();
			System.out.println(currentGame);
			System.out.println(""+Game.isSolved());
			updateBoard();
			String useless=scan.nextLine();
		}
		/*
		for(int a=0;a<x;a++) {
			for (int b=0;b<x;b++) {
				out+=temp[a][b];
				if (temp[a][b]>=0) out+=" ";
			}
			out+="\n";
		}
		System.out.println(out);
		*/
	}

	private static int countFlagsAround(int x, int y) {
		int mines = 0;

		// See if we're on the edge of the board
		boolean oU = false, oD = false, oL = false, oR = false;
		if(x == 0) oU = true;
		if(y == 0) oL = true;
		if(x == Solver.x-1) oD = true;
		if(y == Solver.y-1) oR = true;

		if(!oU && currentGame.getSpace(x-1,y)==-3) mines++;
		if(!oL && currentGame.getSpace(x,y-1)==-3) mines++;
		if(!oD && currentGame.getSpace(x+1,y)==-3) mines++;
		if(!oR && currentGame.getSpace(x,y+1)==-3) mines++;
		if(!oU && !oL && currentGame.getSpace(x-1,y-1)==-3) mines++;
		if(!oU && !oR && currentGame.getSpace(x-1,y+1)==-3) mines++;
		if(!oD && !oL && currentGame.getSpace(x+1,y-1)==-3) mines++;
		if(!oD && !oR && currentGame.getSpace(x+1,y+1)==-3) mines++;

		return mines;
	}

	private static int countFlagsAround(boolean[][] array, int i, int j){
	    int mines = 0;

	    // See if we're on the edge of the board
	    boolean oU = false, oD = false, oL = false, oR = false;
	    if(i == 0) oU = true;
	    if(j == 0) oL = true;
	    if(i == array.length-1) oD = true;
	    if(j == array[0].length-1) oR = true;

	    if(!oU && array[i-1][j]) mines++;
	    if(!oL && array[i][j-1]) mines++;
	    if(!oD && array[i+1][j]) mines++;
	    if(!oR && array[i][j+1]) mines++;
	    if(!oU && !oL && array[i-1][j-1]) mines++;
	    if(!oU && !oR && array[i-1][j+1]) mines++;
	    if(!oD && !oL && array[i+1][j-1]) mines++;
	    if(!oD && !oR && array[i+1][j+1]) mines++;

	    return mines;
	  }
	
	// How many unopened squares around this square?
	public static int countFreeSquaresAround(int x, int y) {
		updateBoard();
		int freeSquares = 0;

		if(onScreen(x-1,y)==-2||onScreen(x-1,y)==-3) freeSquares++;
		if(onScreen(x+1,y)==-2||onScreen(x+1,y)==-3) freeSquares++;
		if(onScreen(x,y-1)==-2||onScreen(x,y-1)==-3) freeSquares++;
		if(onScreen(x,y+1)==-2||onScreen(x,y+1)==-3) freeSquares++;
		if(onScreen(x-1,y-1)==-2||onScreen(x-1,y-1)==-3) freeSquares++;
		if(onScreen(x-1,y+1)==-2||onScreen(x-1,y+1)==-3) freeSquares++;
		if(onScreen(x+1,y-1)==-2||onScreen(x+1,y-1)==-3) freeSquares++;
		if(onScreen(x+1,y+1)==-2||onScreen(x+1,y+1)==-3) freeSquares++;
		//System.out.println("Space "+y+","+y+" has "+freeSquares+" free squares");
		return freeSquares;
	}

	private static int onScreen(int x, int y) {
		if(x < 0 || y < 0 || x > Solver.x-1 || y > Solver.y-1)
			return -10;
		return currentGame.getSpace(x, y);
	}
	
	private static void updateBoard() {
		currentGame=Game.getVisible();
	}
	
	private static void middleClick(int x, int y) {
		if (currentGame.getSpace(x,y)>=0&&currentGame.getSpace(x, y)==countFlagsAround(x,y)) {
			try{
				//System.out.println("Trying "+(x-1)+","+(y-1));
				Game.click(x-1, y-1);
				//System.out.println("Clicked");
				}catch(Exception e){}
			try{
				//System.out.println("Trying "+(x)+","+(y-1));
				Game.click(x, y-1);
				//System.out.println("Clicked");
				}catch(Exception e){}
			try{
				//System.out.println("Trying "+(x-1)+","+(y));
				Game.click(x-1, y);
				//System.out.println("Clicked");
				}catch(Exception e){}
			try{
				//System.out.println("Trying "+(x+1)+","+(y-1));
				Game.click(x+1, y-1);
				//System.out.println("Clicked");
				}catch(Exception e){}
			try{
				//System.out.println("Trying "+(x-1)+","+(y+1));
				Game.click(x-1, y+1);
				//System.out.println("Clicked");
				}catch(Exception e){}
			try{
				//System.out.println("Trying "+(x)+","+(y+1));
				Game.click(x, y+1);
				//System.out.println("Clicked");
				}catch(Exception e){}
			try{
				//System.out.println("Trying "+(x+1)+","+(y));
				Game.click(x+1, y);
				//System.out.println("Clicked");
			}catch(Exception e){}
			try{//System.out.println("Trying "+(x+1)+","+(y+1));
				Game.click(x+1, y+1);
				//System.out.println("Clicked");
			}catch(Exception e){}
		}
		updateBoard();
	}
	
	private static boolean[][] knownMines=null;
	private static boolean[][] knownEmpty=null;
	private static int[][] tankBoard=null;
	private static boolean borderOptimization;
	private static int bruteForceLimit=8;
	private static ArrayList<boolean[]> tankSolutions;
	
	//Adapted from https://github.com/luckytoilet/MSolver
	private static void tankSolver() {
		//Let's do this
		
		long tankTime = System.currentTimeMillis();
		
		ArrayList<Pair> borderTiles=new ArrayList<Pair>();
		ArrayList<Pair> allEmptyTiles=new ArrayList<Pair>();
		
		borderOptimization=false;
		for (int a=0;a<x;a++) 
			for (int b=0;b<y;b++) 
				if(onScreen(a,b)==-2&&!Game.isFlagged(a, b))
					allEmptyTiles.add(new Pair(a,b));
		
		for (int a=0;a<x;a++) 
			for (int b=0;b<y;b++) 
				if(isBoundary(currentGame,a,b)&&!Game.isFlagged(a, b))
					borderTiles.add(new Pair(a,b));
		
		int squaresOutsideRange=allEmptyTiles.size()-borderTiles.size();
		if (squaresOutsideRange>bruteForceLimit)
			borderOptimization=true;
		else borderTiles=allEmptyTiles;
		
		if (borderTiles.size()==0) return;
		
		ArrayList<ArrayList<Pair>> segregated;
		if (!borderOptimization) {
			segregated=new ArrayList<ArrayList<Pair>>();
			segregated.add(borderTiles);
		}
		else segregated=tankSegregate(borderTiles);
		
		int totalMultCases = 1;
	    boolean success = false;
	    double prob_best = -1; // Store information about the best probability
	    int prob_besttile = -1;
	    int prob_best_s = -1;
	    for(int s = 0; s < segregated.size(); s++){

	      // Copy everything into temporary constructs
	      tankSolutions = new ArrayList<boolean[]>();
	      tankBoard = currentGame.getBoard().clone();
	      knownMines = Game.getFlags().clone();

	      knownEmpty = new boolean[x][y];
	      for(int i=0; i<x; i++)
	        for(int j=0; j<y; j++)
	          if(tankBoard[i][j] >= 0)
	            knownEmpty[i][j] = true;
	          else knownEmpty[i][j] = false;


	      // Compute solutions -- here's the time consuming step
	      tankRecurse(segregated.get(s),0);

	      // Something screwed up
	      if(tankSolutions.size() == 0) return;


	      // Check for solved squares
	      for(int i=0; i<segregated.get(s).size(); i++){
	        boolean allMine = true;
	        boolean allEmpty = true;
	        for(boolean[] sln : tankSolutions){
	          if(!sln[i]) allMine = false;
	          if(sln[i]) allEmpty = false;
	        }

	        
	        Pair q = segregated.get(s).get(i);
	        int qi = q.getFirst();
	        int qj = q.getSecond();

	        // Muahaha
	        if(allMine){
	          Game.setFlag(qi, qj, true);
	        }
	        if(allEmpty){
	          success = true;
	          System.out.println("Clicking on "+qi+","+qj);
	          Game.click(qi, qj);
	          if (Game.getSpace(qi, qj)==-1) notLost=false;
	        }
	      }

	      totalMultCases *= tankSolutions.size();

	      
	      // Calculate probabilities, in case we need it
	      if(success) continue;
	      int maxEmpty = -10000;
	      int iEmpty = -1;
	      for(int i=0; i<segregated.get(s).size(); i++){
	        int nEmpty = 0;
	        for(boolean[] sln : tankSolutions){
	          if(!sln[i]) nEmpty++;
	        }
	        if(nEmpty > maxEmpty){
	          maxEmpty = nEmpty;
	          iEmpty = i;
	        }
	      }
	      double probability = (double)maxEmpty / (double)tankSolutions.size();

	      if(probability > prob_best){
	        prob_best = probability;
	        prob_besttile = iEmpty;
	        prob_best_s = s;
	      }

	    }

	    // But wait! If there's any hope, bruteforce harder (by a factor of 32x)!
	    if(bruteForceLimit == 8 && squaresOutsideRange > 8 && squaresOutsideRange <= 13){
	      System.out.println("Extending bruteforce horizon...");
	      bruteForceLimit = 13;
	      tankSolver();
	      bruteForceLimit = 8;
	      return;
	    }

	    tankTime = System.currentTimeMillis() - tankTime;
	    if(success){
	    	System.out.printf(
	    	          "TANK Solver successfully invoked at step %d (%dms, %d cases)%s\n",
	    	          Game.bombs(), tankTime, totalMultCases, (borderOptimization?"":"*"));
	      return;
	    }


	    // Take the guess, since we can't deduce anything useful
	    System.out.printf(
	            "TANK Solver guessing with probability %1.2f at step %d (%dms, %d cases)%s\n",
	            prob_best, Game.bombs(), tankTime, totalMultCases,
	            (borderOptimization?"":"*"));
	    System.out.println(""+segregated);
	    Pair q = segregated.get(prob_best_s)
	    		.get(prob_besttile);
	    int qi = q.getFirst();
	    int qj = q.getSecond();
	    System.out.println("Clicking on "+qi+","+qj);
	    Game.click(qi, qj);
	}
	
	//Adapted from https://github.com/luckytoilet/MSolver
	private static ArrayList<ArrayList<Pair>> tankSegregate(ArrayList<Pair> borderTiles) {
		ArrayList<ArrayList<Pair>> allRegions = new ArrayList<ArrayList<Pair>>();
	    ArrayList<Pair> covered = new ArrayList<Pair>();

	    while(true){

	      LinkedList<Pair> queue = new LinkedList<Pair>();
	      ArrayList<Pair> finishedRegion = new ArrayList<Pair>();

	      // Find a suitable starting point
	      for(Pair firstT : borderTiles){
	        if(!covered.contains(firstT)){
	          queue.add(firstT);
	          break;
	        }
	      }

	      if(queue.isEmpty())
	        break;

	      while(!queue.isEmpty()){

	        Pair curTile = queue.poll();
	        int ci = curTile.getFirst();
	        int cj = curTile.getSecond();

	        finishedRegion.add(curTile);
	        covered.add(curTile);

	        // Find all connecting tiles
	        for(Pair tile : borderTiles){
	          int ti = tile.getFirst();
	          int tj = tile.getSecond();

	          boolean isConnected = false;

	          if(finishedRegion.contains(tile))
	            continue;
	          
	          if(Math.abs(ci-ti)>2 || Math.abs(cj-tj) > 2)
	            isConnected = false;

	          else{
	            // Perform a search on all the tiles
	            tilesearch:
	            for(int i=0; i<x; i++){
	              for(int j=0; j<y; j++){
	                if(onScreen(i,j) > 0){
	                  if(Math.abs(ci-i) <= 1 && Math.abs(cj-j) <= 1 &&
	                      Math.abs(ti-i) <= 1 && Math.abs(tj-j) <= 1){
	                    isConnected = true;
	                    break tilesearch;
	                  }
	                }
	              }
	            }
	          }
	          
	          if(!isConnected) continue;

	          if(!queue.contains(tile))
	            queue.add(tile);

	        }
	      }

	      allRegions.add(finishedRegion);

	    }

	    return allRegions;

	}
	
	//Adapted from https://github.com/luckytoilet/MSolver
	private static void tankRecurse(ArrayList<Pair> borderTiles, int k) {
		System.out.println("Recursing "+borderTiles);
	    // Return if at this point, it's already inconsistent
	    int flagCount = 0;
	    for(int i=0; i<x; i++)
	      for(int j=0; j<y; j++){

	        // Count flags for endgame cases
	        if(knownMines[i][j])
	          flagCount++;

	        int num = tankBoard[i][j];
	        if(num < 0) continue;

	        // Total bordering squares
	        int surround = 0;
	        if((i==0&&j==0) || (i==x-1 && j==y-1))
	          surround = 3;
	        else if(i==0 || j==0 || i==x-1 || j==y-1)
	          surround = 5;
	        else surround = 8;

	        int numFlags = countFlagsAround(knownMines, i,j);
	        int numFree = countFlagsAround(knownEmpty, i,j);
	        
	        // Scenario 1: too many mines
	        if(numFlags > num) {
	        	System.out.println("Returning because too many mines");
	        	System.out.println("");
	        	return;
	        }

	        // Scenario 2: too many empty
	        if(surround - numFree < num) {
	        	System.out.println("Returning because too many empty");
	        	return;
	        }
	      }

	    // We have too many flags
	    if(flagCount > Game.bombs())
	      return;


	    // Solution found!
	    if(k == borderTiles.size()){

	      // We don't have the exact mine count, so no
	      if(!borderOptimization && flagCount < Game.bombs())
	        return;

	      boolean[] solution = new boolean[borderTiles.size()];
	      for(int i=0; i<borderTiles.size(); i++){
	        Pair s = borderTiles.get(i);
	        int si = s.getFirst();
	        int sj = s.getSecond();
	        solution[i] = knownMines[si][sj];
	      }
	      tankSolutions.add(solution);
	      System.out.println("Possible solution found");
	      return;
	    }

	    Pair q = borderTiles.get(k);
	    int qi = q.getFirst();
	    int qj = q.getSecond();

	    // Recurse two positions: mine and no mine
	    knownMines[qi][qj] = true;
	    tankRecurse(borderTiles, k+1);
	    knownMines[qi][qj] = false;

	    knownEmpty[qi][qj] = true;
	    tankRecurse(borderTiles, k+1);
	    knownEmpty[qi][qj] = false;
	}
	
	private static boolean isBoundary (Board board, int i, int j) {
		if(board.getSpace(i, j) != -2) return false;

	    boolean oU = false, oD = false, oL = false, oR = false;
	    if(i == 0) oU = true;
	    if(j == 0) oL = true;
	    if(i == x-1) oD = true;
	    if(j == y-1) oR = true;
	    boolean isBoundry = false;

	    if(!oU && board.getSpace(i-1, j)>=0) isBoundry = true;
	    if(!oL && board.getSpace(i,j-1)>=0) isBoundry = true;
	    if(!oD && board.getSpace(i+1,j)>=0) isBoundry = true;
	    if(!oR && board.getSpace(i,j+1)>=0) isBoundry = true;
	    if(!oU && !oL && board.getSpace(i-1,j-1)>=0) isBoundry = true;
	    if(!oU && !oR && board.getSpace(i-1,j+1)>=0) isBoundry = true;
	    if(!oD && !oL && board.getSpace(i+1,j-1)>=0) isBoundry = true;
	    if(!oD && !oR && board.getSpace(i+1,j+1)>=0) isBoundry = true;

	    return isBoundry;
	}
}

//Copied from http://stackoverflow.com/questions/156275/ and modified to make only ints
class Pair {
 private int first;
 private int second;

 public Pair(int first, int second) {
     super();
     this.first = first;
     this.second = second;
 }

 

 public boolean equals(Pair other) {
     return first==other.getFirst()&&second==other.getSecond();
 }

 public String toString()
 { 
        return "(" + first + ", " + second + ")"; 
 }

 public int getFirst() {
     return first;
 }

 public void setFirst(int first) {
     this.first = first;
 }

 public int getSecond() {
     return second;
 }

 public void setSecond(int second) {
     this.second = second;
 }
}