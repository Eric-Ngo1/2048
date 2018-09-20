//Eric Ngo
//February 4, 2018

import java.util.*;
import java.io.*;

public class GameManager {
    // Instance variables
    private Board board; // The actual 2048 board
    private String outputBoard; // File to save the board to when exiting

    /*ec*/
    private String outputRecord; // file to save the record file, format: [size] wasdwasdwasdawsd
    StringBuilder history = new StringBuilder(); // a string of commands history
    /*ce*/


    
    // GameManager Constructor
    // Generate new game
    // input: String outputBoard - the file name of the board that will be
    //                             needed to load the game again
    //        int boardSize - the size of the board to be created
    //        Random random - random object that will be needed to generate
    //                        a random value
    public GameManager(String outputBoard, int boardSize, Random random) {
	this.outputBoard = outputBoard;
	this.board = new Board(random, boardSize);
    }

    
    // GameManager Constructor
    // Load a saved game
    // input: String inputBoard - the file name of the game you want to load
    //        String outputBoard - the new name of the file
    //        Random random - random object that generates a random value
    public GameManager(String inputBoard, String outputBoard, Random random) throws IOException {
	this.outputBoard = outputBoard;
	this.board = new Board(random, inputBoard);

    }

    
    // TODO PSA3
    // Main play loop
    // Takes in input from the user to specify moves to execute
    // valid moves are:
    //      w - Move up
    //      s - Move Down
    //      a - Move Left
    //      d - Move Right
    //      q - Quit and Save Board
    //
    //  If an invalid command is received then print the controls
    //  to remind the user of the valid moves.
    //
    //  Once the player decides to quit or the game is over,
    //  save the game board to a file based on the outputBoard
    //  string that was set in the constructor and then return
    //
    //  If the game is over print "Game Over!" to the terminal
    public void play() throws Exception {
	this.printControls();
	System.out.print(this.board);
	Scanner reader = new Scanner(System.in);

	
	//main loop in play method that checks if the game is over, if the
	//game isn't over then the user will be prompted to do a move 
	while(this.board.isGameOver() == false)
	{
	  String temp = reader.next();

	  //if input is w (up), this will check if the move is valid and
	  //perform the move
	  if(temp.equals("w"))
	   {
	    if(this.board.move(Direction.UP))
	     {
	      this.board.addRandomTile();
	      System.out.print(this.board);
	     }
	    // if it isnt possible to move in this direction then the
	    // user will be prompted to enter another move and will be
	    // reminded of the controls
	    else if(this.board.move(Direction.UP) == false)
	     {
	      System.out.println("Can't move in this direction!");
	      System.out.print(this.board);
	     }
	    }
	   

	  //this chunk of code performs the exact same logic as above 
	  //however it tests if the input is s (down) 
	  else if(temp.equals("s"))
	   {
	    if(this.board.move(Direction.DOWN))
	     {
	      this.board.addRandomTile();
	      System.out.print(this.board);
	     }
	    else if(this.board.move(Direction.DOWN) == false)
	     {
	      System.out.println("Can't move in this direction!");
	      System.out.print(this.board);
	     }
	   }

	  //this chunk of code performs the exact same logic as above
	  //however it tests if the input is d (right)
	  else if(temp.equals("d"))
	   {
	    if(this.board.move(Direction.RIGHT))
	     {
	      this.board.addRandomTile();
	      System.out.print(this.board);
	     }
	    else if(this.board.move(Direction.RIGHT) == false)
	     {
	      System.out.println("Can't move in this direction!");
	      System.out.print(this.board);
	     }
	   }

	  //this chunk of code performs the exact same logic as above 
	  //however it tests if the input is a (left) 	  
	  else if(temp.equals("a"))
	   {
	    if(this.board.move(Direction.LEFT))
	     {
	      this.board.addRandomTile();
	      System.out.print(this.board);
	     }
	    else if(this.board.move(Direction.LEFT) == false)
	     {
	      System.out.println("Can't move in this direction!");
	      System.out.print(this.board);
	     }
	   }

	  //if in put is q, the game will save and exit
	  else if(temp.equals("q"))
	   {
	    this.board.saveBoard(this.outputBoard);
	    return;
	   }
	
	 //if there is any other input, the controls will be displayed 
	 //again to remind the user
	  else
	   {
	    this.printControls();
	   }	  
	 }
	
	//this will print that the game is over once there are no more
	//possible moves
	System.out.println("GAME OVER! MWAHAAHAHAHAH");
	//saves the end board
	this.board.saveBoard(this.outputBoard);
	
    }

    // Print the Controls for the Game
    private void printControls() {
        System.out.println("  Controls:");
        System.out.println("    w - Move Up");
        System.out.println("    s - Move Down");
        System.out.println("    a - Move Left");
        System.out.println("    d - Move Right");
        System.out.println("    q - Quit and Save Board");
        System.out.println();
    }
}
