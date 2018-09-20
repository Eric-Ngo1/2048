//Eric Ngo
//February 4, 2018

/* This class is the back end of the game 2048. It creates a grid that 
 * consists of int's and it performs all of the basic moves necessary in the
 * game. This file can also save instances of the game into files.
 */



import java.util.*;
import java.io.*;

public class Board {
    public final int NUM_START_TILES = 2; 
    public final int TWO_PROBABILITY = 90;
    public final int GRID_SIZE;


    private final Random random; // a reference to the Random object, passed in 
                                 // as a parameter in Boards constructors
    private int[][] grid;  // a 2D int array, its size being boardSize*boardSize
    private int score;     // the current score, incremented as tiles merge 


    
    // Constructs a fresh board with random tiles
    //@param Random random - generates random value
    //	     int boardSize - size of the board
    public Board(Random random, int boardSize) {
	GRID_SIZE = boardSize;
        this.grid = new int[GRID_SIZE][GRID_SIZE];
        this.random = random;
	addRandomTile();
	addRandomTile();
	
         
    }

    
    // Construct a board based off of an input file
    // assume board is valid
    // @param Random random - random object needed to generate a random 
    //                        value
    //        String inputBoard - name of the file already with a board
    public Board(Random random, String inputBoard) throws IOException {
        File myFile = new File(inputBoard);
	Scanner myScanner = new Scanner(myFile);
        this.random = random;
        GRID_SIZE = Integer.parseInt(myScanner.next());
	this.score = Integer.parseInt(myScanner.next());
	this.grid = new int[GRID_SIZE][GRID_SIZE];
	for ( int i = 0; i < GRID_SIZE; i++)
	 {
	  for ( int j = 0; j < GRID_SIZE; j++)
	   {
	    this.grid[i][j] = Integer.parseInt(myScanner.next());
 	   }
 	 }
    }

    
    // Saves the current board to a file
    // @param: String outputBoard - the file containg the 
    //                             board that will be saved
    // @return: none
    public void saveBoard(String outputBoard) throws IOException {
	File myFile = new File(outputBoard);
	PrintWriter myWriter = new PrintWriter(myFile);

	int gridSize = this.GRID_SIZE;
	myWriter.print(gridSize);
	myWriter.println();
	int score = this.score;
	myWriter.print(score);
	myWriter.println();
	for(int x = 0; x < this.GRID_SIZE; x++)
	 {
	  for(int y = 0; y < this.GRID_SIZE; y++)
	   {
	    myWriter.print(this.grid[x][y]);
	    myWriter.print(" ");
	   }
	  myWriter.println();
	 }

	myWriter.close();
	
        
    }

  
    // Adds a random tile (of value 2 or 4) to a
    // random empty space on the board
    // @param: none
    // @return: none
    public void addRandomTile() {
	int count = 0;
	//checks for the empty spaces on the board and counts them up
	for (int x = 0; x < GRID_SIZE; x++)
	 {
 	  for (int y = 0; y < GRID_SIZE; y++)
	   {
	    if( this.grid[x][y] == 0 )
	     {
	      count++;
	     } 
 	   }
	  } 

	//if there are no empty spaces, it wont add a random tile
	if(count == 0)
	return;
	
	int location = random.nextInt(count);
	int value = random.nextInt(100);
	int counter = 0;	

	//loops through grid again, placing a random tile in a random
	//location
	for(int x = 0; x < GRID_SIZE; x++)
	 {
	  for(int y = 0; y < GRID_SIZE; y++)
	   {
	    if( this.grid[x][y] == 0)
	     {
	      if( counter == location )
	       {
		//if the random value is less than 90, it will put 2 as the
		//random value, else it will put 4
	        if( value < TWO_PROBABILITY )
	         {
	          this.grid[x][y] = 2;
	         }
	        else
	        this.grid[x][y] = 4;
	       }
	      counter++;
	     }
	   }
	 }	
	

    }

    //Helper method for the canMove method, it tests if any move 
    //in the left direction is possible
    //@param: none
    //@return: boolean value, true if its possible to move left.
    //        false if it cannot move left
    private boolean canMoveLeft()
    {
	for(int x = 0; x < this.GRID_SIZE; x++)
	 {
	  for(int y = 0; y < this.GRID_SIZE-1; y++)
	   {
	    if( (this.grid[x][y+1] != 0 && this.grid[x][y] == 0)|| 
	        ((this.grid[x][y] == this.grid[x][y+1])
		 && this.grid[x][y] > 0) )
	     {
	      return true;
	     }
	   }
	 }
	return false;
    }

   //Helper method for the canMove method, it tests if any move
   //in the left direction is possible
   //@param: none
   //@return: boolean value, true if its possible to move right.
   //	     false if it cannot move right
   private boolean canMoveRight()
    {
	for(int x = 0; x < this.GRID_SIZE; x++)
	 {
	  for(int y = this.GRID_SIZE-1; y >= 1; y--)
	   {
	    if((this.grid[x][y-1] != 0 && this.grid[x][y] == 0) ||
	       ((this.grid[x][y] == this.grid[x][y-1]) && 
		 this.grid[x][y] > 0))
	     {
	      return true;
	     }
	   } 
	 }
	return false;
    }
  
   //Helper method for the canMove method, it tests if any move
   //in the down direction is possible
   //@param: none
   //@return: boolean value, true if its possible to move down.
   //        false if it cannot move down
   private boolean canMoveDown()
    {
	for(int y = 0; y < this.GRID_SIZE; y++)
	 {
	  for(int x = this.GRID_SIZE-1; x >= 1; x--)
	   {
	    if((this.grid[x-1][y] != 0 && this.grid[x][y] == 0) ||
	       ((this.grid[x][y] == this.grid[x-1][y]) 
 		 && this.grid[x][y] > 0))
	     {
	      return true;
	     }
	   } 
	 }
	return false;
    }

   //Helper method for the canMove method, it tests if any move 
   //in the up direction is possible
   //@param: none
   //@return: boolean value, true if its possible to move up.
   //	     false if it cannot move up
   private boolean canMoveUp() 
    {
	for(int y = 0; y < this.GRID_SIZE; y++)
	 {
	  for(int x = 0; x < this.GRID_SIZE -1; x++)
	   {
	    if((this.grid[x+1][y] != 0 && this.grid[x][y] == 0)||
	       ((this.grid[x][y] == this.grid[x+1][y])
	    	 && this.grid[x][y] > 0))
	    {
	     return true;
	    }
	   }
	 }
	return false;
    }

    
    //Determines whether the board can move in a certain direction
    //@param: Direction direction - any direction to be tested
    //@return: true if such a move is possible, false if it isnt possible
    public boolean canMove(Direction direction){

	if(direction.equals(Direction.UP))
	{
	 if(canMoveUp())
	  {
	   return true;
	  }
	}

	if(direction.equals(Direction.DOWN))
	 {
	  if(canMoveDown())
	   {
	    return true;
	   }
	 }
	
	if(direction.equals(Direction.RIGHT))
	 {
	  if(canMoveRight())
	   {
	    return true;
	   }
	 }

	if(direction.equals(Direction.LEFT))
	 {
	  if(canMoveLeft())
	   {
	    return true;
	   }
	 }
        return false; 
    }

     //Helper method for the move method, moves tiles to the right
     //@param: none
     //@return: true to show the move was successful
     private boolean moveRight()
	{

	 // if there are any 0's in the row, it will be cleared and all
	 // values greater than 0 will be pushed to the right
	 for(int maxMoves = 0; maxMoves < this.GRID_SIZE; maxMoves++)
	  {
	   for(int column = 0; column < this.GRID_SIZE; column++)
	    {
	     for(int row = this.GRID_SIZE-1; row >= 1; row--)
	      {
	       if(this.grid[column][row-1]!=0 && this.grid[column][row]==0)
		{
		 this.grid[column][row] = this.grid[column][row-1];
		 this.grid[column][row-1] = 0;
		}
	      }
	    }
	  }  


	  //if there are any tiles next to eachother that are the same
	  //value, and they are both greater than 0, they will be 
	  //combined and their score will be accounted for
	  for(int column = 0; column < this.GRID_SIZE; column++)
	   {
	    for(int row = this.GRID_SIZE-1; row >= 1; row--)
	     {
	      if((this.grid[column][row] == this.grid[column][row-1]) 
		 && this.grid[column][row]>0)
	       {
	        this.grid[column][row] = this.grid[column][row] +
	        this.grid[column][row-1];
	        this.grid[column][row-1] = 0;
	        this.score += this.grid[column][row];
	       }
	     }
	   }
            
 
         //if there are any more 0's in between tiles with values after the
         //shifting and combining of the two previous chunks of code, they 
         //will move and all values greater than 0 will be shifted to 
         //the right
	 for(int maxMoves = 0; maxMoves < this.GRID_SIZE; maxMoves++)
	  {
	   for(int column = 0; column < this.GRID_SIZE; column++)
	    {
	     for(int row = this.GRID_SIZE-1; row >= 1; row--)
	      {
	       if(this.grid[column][row-1]!=0 && this.grid[column][row]==0)
		{
		 this.grid[column][row] = this.grid[column][row-1];
		 this.grid[column][row-1] = 0;
		}
	      }
	    }
	  }  


	 return true;
	}


   //Helper method for the move method, moves tiles to the left
   //@param: none
   //@return: true to show that the move was successful
   private boolean moveLeft()
	{
         // if there are any 0's in the row, it will be cleared and all
	 // values greater than 0 will be pushed to the left
	 for(int maxMoves = 0; maxMoves < this.GRID_SIZE; maxMoves++)
	  {
	   for(int column = 0; column < this.GRID_SIZE; column++)
	    {
	     for(int row = 0; row < this.GRID_SIZE -1; row++)
	      {
	       if(this.grid[column][row+1]!=0 && this.grid[column][row]==0)
		{
		 this.grid[column][row] = this.grid[column][row+1];
		 this.grid[column][row+1] = 0;
		}
	      }
	    }
	  }  


	  //if there are any tiles next to eachother that are the same
	  //value, and they are both greater than 0, they will be 
	  //combined and their score will be accounted for
	  for(int column = 0; column < this.GRID_SIZE; column++)
	   {
	    for(int row = 0; row < this.GRID_SIZE - 1; row++)
	     {
	      if((this.grid[column][row] == this.grid[column][row+1]) 
		 && this.grid[column][row]>0)
	       {
	        this.grid[column][row] = this.grid[column][row] +
	        this.grid[column][row+1];
	        this.grid[column][row+1] = 0;
	        this.score += this.grid[column][row];
	       }
	     }
	   }
            

	 //if there are any more 0's in between tiles with values after the
         //shifting and combining of the two previous chunks of code, they 
         //will move and all values greater than 0 will be shifted to 
         //the left
	 for(int maxMoves = 0; maxMoves < this.GRID_SIZE; maxMoves++)
	  {
	   for(int column = 0; column < this.GRID_SIZE; column++)
	    {
	     for(int row = 0; row < this.GRID_SIZE -1; row++)
	      {
	       if(this.grid[column][row+1]!=0 && this.grid[column][row]==0)
		{
		 this.grid[column][row] = this.grid[column][row+1];
		 this.grid[column][row+1] = 0;
		}
	      }
	    }
	  }  

	 
	 return true;
        }


    //Helper method for the move method, moves tiles up
    //@param: none
    //@return: true to show that the move was successful
    private boolean moveUp()
	{

	// if there are any 0's in the column, it will be cleared and all
	 // values greater than 0 will be pushed up
	for(int maxMoves = 0; maxMoves < this.GRID_SIZE; maxMoves++)
	  {
	   for(int row = 0; row < this.GRID_SIZE; row++)
	    {
	     for(int column = 0; column < this.GRID_SIZE -1; column++)
	      {
	       if(this.grid[column+1][row]!=0 && this.grid[column][row]==0)
		{
		 this.grid[column][row] = this.grid[column+1][row];
		 this.grid[column+1][row] = 0;
		}
	      }
	    }
	  }  


	  //if there are any tiles next to eachother that are the same
	  //value, and they are both greater than 0, they will be 
	  //combined and their score will be accounted for
	  for(int row = 0; row < this.GRID_SIZE; row++)
	   {
	    for(int column = 0; column < this.GRID_SIZE-1; column++)
	     {
	      if((this.grid[column][row] == this.grid[column+1][row]) 
		 && this.grid[column][row]>0)
	       {
	        this.grid[column][row] = this.grid[column][row] +
	        this.grid[column+1][row];
	        this.grid[column+1][row] = 0;
	        this.score += this.grid[column][row];
	       }
	     }
	   }
            

	//if there are any more 0's in between tiles with values after the
         //shifting and combining of the two previous chunks of code, they 
         //will move and all values greater than 0 will be shifted up
	for(int maxMoves = 0; maxMoves < this.GRID_SIZE; maxMoves++)
	  {
	   for(int row = 0; row < this.GRID_SIZE; row++)
	    {
	     for(int column = 0; column < this.GRID_SIZE -1; column++)
	      {
	       if(this.grid[column+1][row]!=0 && this.grid[column][row]==0)
		{
		 this.grid[column][row] = this.grid[column+1][row];
		 this.grid[column+1][row] = 0;
		}
	      }
	    }
	  }  


	 return true;
	}


     //Helper method for the move method, moves the tiles down
     //@param: none
     //@return: true to show that the move was successful
     private boolean moveDown()
	{

	 // if there are any 0's in the column, it will be cleared and all
	 // values greater than 0 will be pushed down
	 for(int maxMoves = 0; maxMoves < this.GRID_SIZE; maxMoves++)
	  {
	   for(int row = 0; row < this.GRID_SIZE; row++)
	    {
	     for(int column = this.GRID_SIZE-1; column >= 1; column--)
	      {
	       if(this.grid[column-1][row]!=0 && this.grid[column][row]==0)
		{
		 this.grid[column][row] = this.grid[column-1][row];
		 this.grid[column-1][row] = 0;
		}
	      }
	    }
	  }  


	  //if there are any tiles next to eachother that are the same
	  //value, and they are both greater than 0, they will be 
	  //combined and their score will be accounted for
	  for(int row = 0; row < this.GRID_SIZE; row++)
	   {
	    for(int column = this.GRID_SIZE-1; column >= 1; column--)
	     {
	      if((this.grid[column][row] == this.grid[column-1][row]) 
		 && this.grid[column][row]>0)
	       {
	        this.grid[column][row] = this.grid[column][row] +
	        this.grid[column-1][row];
	        this.grid[column-1][row] = 0;
	        this.score += this.grid[column][row];
	       }
	     }
	   }
            

	//if there are any more 0's in between tiles with values after the
         //shifting and combining of the two previous chunks of code, they 
         //will move and all values greater than 0 will be shifted down
	for(int maxMoves = 0; maxMoves < this.GRID_SIZE; maxMoves++)
	  {
	   for(int row = 0; row < this.GRID_SIZE; row++)
	    {
	     for(int column = this.GRID_SIZE-1; column >= 1; column--)
	      {
	       if(this.grid[column-1][row]!=0 && this.grid[column][row]==0)
		{
		 this.grid[column][row] = this.grid[column-1][row];
		 this.grid[column-1][row] = 0;
		}
	      }
	    }
	  }  


	    return true;
        }
     
    
    //Move the board in a certain direction
    //@param: Direction direction - specified direction to move
    //@return: true if such a move is successful
    public boolean move(Direction direction) {
	if(canMove(direction))	
	 {

	  if(direction.equals(direction.RIGHT))	  
	   {
	    moveRight();
	    return true;
	   }
	
	  if(direction.equals(direction.LEFT))
	   {
	    moveLeft();
	    return true;
	   }

	  if(direction.equals(direction.UP))
	   {
	    moveUp();
	    return true;
	   }

	  if(direction.equals(direction.DOWN))
	   {
	    moveDown();
	    return true;
	   } 
	 }
        return false;
    }

    
    //Check to see if we have a game over
    //@param: none
    //@return: false if game is not over
    //	      true if game is over
    public boolean isGameOver() {
	if(canMove(Direction.UP))
	 {return false;}

	if(canMove(Direction.DOWN))
	 {return false;}

	if(canMove(Direction.RIGHT))
	 {return false;}

	if(canMove(Direction.LEFT))
	 {return false;}

        return true;
    }

    // Return the reference to the 2048 Grid
    public int[][] getGrid() {
        return grid;
    }

    // Return the score
    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        StringBuilder outputString = new StringBuilder();
        outputString.append(String.format("Score: %d\n", score));
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int column = 0; column < GRID_SIZE; column++)
                outputString.append(grid[row][column] == 0 ? "    -" :
                        String.format("%5d", grid[row][column]));

            outputString.append("\n");
        }
        return outputString.toString();
    }
}
