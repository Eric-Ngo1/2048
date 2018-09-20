// Eric Ngo
// February 23, 2018

/* Gui2048 is a class that will carry out the GUI for the game 2048. It will
 * utilize the back end code from our Board.java class. This class creates
 * a window where the user can play the game. The game board is a set of 
 * tiles that move based off us input from the keyboard and it displays the
 * score on the top right hand corner of the window.
 */
import javafx.application.*;
import javafx.scene.control.*;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.event.*;
import javafx.scene.input.*;
import javafx.scene.text.*;
import javafx.geometry.*;
import java.util.*;

import java.io.*;

public class Gui2048 extends Application
{
    private String outputBoard; // The filename for where to save the Board
    private Board board; // The 2048 Game Board
    private GridPane pane;
    private Rectangle[][] grid;
    private StackPane stack;
    private BorderPane borderPane;
    private Scene scene;



    @Override
    public void start(Stage primaryStage)
    {
        // Process Arguments and Initialize the Game Board
        processArgs(getParameters().getRaw().toArray(new String[0]));

        //initializes the stackPane that will hold the gridPane
        stack = new StackPane();
        stack.setAlignment(Pos.CENTER);
        stack.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
        stack.setStyle("-fx-background-color: rgb(187, 173, 160)");

        //initializes the Pane that will hold the grid with the tiles
        pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
        pane.setStyle("-fx-background-color: rgb(187, 173, 160)");
        // Set the spacing between the Tiles
        pane.setHgap(15); 
        pane.setVgap(15);


        //initializes the borderPane that will hold the pane, title, and
        //score
        this.borderPane = new BorderPane(); 
        this.borderPane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
       this.borderPane.setStyle("-fx-background-color: rgb(187, 173, 160)");

        //creates the title and beginning score
        makeHeader();         
        //initializes the grid that will represent the tiles
        this.grid = new Rectangle[board.GRID_SIZE][board.GRID_SIZE];
        //creates a new pane which contains a fresh board with nothing on it
        makeNewPane();

        
        scene = new Scene(stack); 
        //adds two random tiles to the empty board
        drawBoard(getPane(), getBoard());
        //reads user input
        scene.setOnKeyPressed(new myKeyHandler());
     
        //creates the window, with the title Gui2048 and shows everything
        primaryStage.setTitle("Gui2048");
        primaryStage.setScene(scene);
        primaryStage.setHeight(800);
        primaryStage.setWidth(800);
        primaryStage.show();
    }

    //Returns the value of the specified (x,y) index
    //@param: int x - the x coordinate
    //        int y - the y coordinate
    //@return: the value at the current coordinates
    private int getValue(int x, int y)
    {
     int[][] grid = this.board.getGrid();
     return grid[x][y];
    }
    
    //Creates the header for the game(title and score)
    //@param: none
    //@return: none
    private void makeHeader()
    {
     //creates text that is the title for the game
     Text textTitle = new Text();
     textTitle.setText("2048");
     textTitle.setFont(Font.font("Times New Roman",FontWeight.BOLD, 30));        textTitle.setFill(Constants2048.COLOR_VALUE_DARK);

     //creates text that is the score for the game
     int score = board.getScore();
     Text scoreTitle = new Text();
     scoreTitle.setText("Score: " + Integer.toString(score));
     scoreTitle.setFont(Font.font("Times New Roman",FontWeight.BOLD,30));
     scoreTitle.setFill(Constants2048.COLOR_VALUE_DARK);

     //creates HBox that holds the title and score
     HBox hBox = new HBox();
     hBox.setPadding(new Insets(15, 12, 15, 12));
     hBox.setSpacing(100);
     hBox.setStyle("-fx-background-color: rgb(187, 173, 160)");
     hBox.getChildren().addAll(textTitle, scoreTitle);
     hBox.setAlignment(Pos.CENTER);
     hBox.setPrefWidth(150);

     //adds the HBox to the top of the borderPane, the stackPane is then
     //cleared and the updated borderPane is added to the stackPane
     getBorder().setTop(hBox);
     getStack().getChildren().clear();
     getStack().getChildren().add(getBorder());
    }

    //returns the instance variable borderPane
    //@param: none
    //@return: this.borderPane - instance variable BorderPane borderPane
    private BorderPane getBorder()
    {
     return this.borderPane;
    }

    //creates an empty grid of any size board onto the pane, and the pane
    //is then added onto the borderPane, which is added onto the stackPane
    //@param: none
    //@return: none
    private void makeNewPane()
    {
      //first clears anything on the pane in order to compeltely update it
      this.pane.getChildren().clear();
      for(int row = 0; row < board.GRID_SIZE; row++)
        {
         for(int column = 0; column < board.GRID_SIZE; column++)
         {
          //each index is initialized to be a rectangle object, that is 
          //created at a proportional size to the window size
          getGrid()[column][row] = new Rectangle();
          //the grid can be constructed from any size board, and will 
          //adjust its sizing and tile sizes based on the size of the 
          //window, the size of the tiles is binded to the width and 
          //height of the window 
          getGrid()[column][row].setFill(Constants2048.COLOR_EMPTY);
          //the width of the tile is binded to the width of the window, 
          //however the width of the tile is set to be proportional to the
          //size of the window based off the calculations below. First, I 
          //calculate a proportion of 1 divided by the grid size and then 
          //add that to a constant 0.15, combined, this value is multiplied
          //by the width of the window and the result is the width of the 
          //tile, this same process is repeated for the height
          getGrid()[column][row].widthProperty().bind(getStack().widthProperty().multiply(((double)(1/board.GRID_SIZE)) + 0.15));
          getGrid()[column][row].heightProperty().bind(getStack().heightProperty().multiply(((double)(1/board.GRID_SIZE)) + 0.15));
          //each new rectangle is then added to the pane at a given index,
          //the pane is then added into the gridPane
          this.pane.add(this.grid[column][row], row, column);
          getBorder().setCenter(this.pane);
         }
        }
       //the stackPane is then cleared in order to completely update it
       //then the borderPane is added onto the stackPane
       getStack().getChildren().clear();
       getStack().getChildren().add(getBorder());
    }

    //returns the instance variable grid
    //@param: none
    //@return: this.grid - instance variable for the Rectangle[][] grid 
    private Rectangle[][] getGrid()
    {
     return this.grid;
    }

    //returns the instance variable stack
    //@param: none
    //@return: this.stack - instance variable StackPane stack
    private StackPane getStack()
    {
     return this.stack;
    }
    
    //returns the instance variable pane
    //@param: none
    //@return: this.pane - instance variable GridPane pane
    private GridPane getPane()
    {
     return this.pane;
    }
    
    //returns the instance variable board
    //@param: none
    //@return: this.board - instance variable Board board
    private Board getBoard()
    {
     return this.board;
    }

    //creates the GUI for the game board based on the information from the 
    //board instance variable
    //@param: GridPane pane - the pane that the board will be painted on
    //        Board board - the board which we will retrieve the 
    //                      information from to put into graphics
    public void drawBoard(GridPane pane, Board board)
    {
      for(int row = 0; row < board.GRID_SIZE; row++)
         {
          for(int column = 0; column < board.GRID_SIZE; column++)
           {
            //constructs a tile onto the pane whenever it is greater than 0
            if(getValue(column, row) > 0)
             {
              Rectangle tile = new Rectangle();
              //each tile to be created is made with the same algorithm as
              //before, the width of the tile is binded to the width of 
              //the window, but is multiplied by (1 / grid size) + 0.15
              //to create the correct sized tile that is proportional to 
              //the size of the window, this process is reapeated for the 
              //height of the tile as well 
              tile.widthProperty().bind(getStack().widthProperty().multiply(              ((double)(1/board.GRID_SIZE)) + 0.15));
             tile.heightProperty().bind(getStack().heightProperty().multiply             (((double)(1/board.GRID_SIZE))+ 0.15));
              //the following chunk of code assigns a color to the tile 
              //based on its value
              if(getValue(column, row) == 2)
               tile.setFill(Constants2048.COLOR_2);
              else if(getValue(column, row) == 4)
               tile.setFill(Constants2048.COLOR_4);
              else if(getValue(column, row) == 8)
               tile.setFill(Constants2048.COLOR_8);
              else if(getValue(column, row) == 16)
               tile.setFill(Constants2048.COLOR_16);
              else if(getValue(column, row) == 32)
               tile.setFill(Constants2048.COLOR_32);
              else if(getValue(column, row) == 64)
               tile.setFill(Constants2048.COLOR_64);
              else if(getValue(column, row) == 128)
               tile.setFill(Constants2048.COLOR_128);
              else if(getValue(column, row) == 256)
               tile.setFill(Constants2048.COLOR_256);
              else if(getValue(column, row) == 512)
               tile.setFill(Constants2048.COLOR_512);
              else if(getValue(column, row) == 1024)
               tile.setFill(Constants2048.COLOR_1024);
              else if (getValue(column, row) == 2048)
               tile.setFill(Constants2048.COLOR_2048);
              else if(getValue(column, row) > 2048)
                tile.setFill(Constants2048.COLOR_OTHER);
              
              //creates the text that will go on top of the tile, the text
              //will be the value itself
              Text tileValue = new Text();
              int value = getValue(column, row);
              String tileNumber = Integer.toString(value);
              tileValue.setText(tileNumber);
              //if the tile is smaller than 128 it will be given this 
              //constant size for the font 
              if( (value > 0 ) && (value < 128))
              { 
              tileValue.setFont(Font.font("Times New Roman", 
              FontWeight.BOLD, Constants2048.TEXT_SIZE_LOW));
              }
              //if it is between 128 and 1024, it will be given this 
              //constant size for the font
              else if( (value >= 128) && (value <  1024))
              {
               tileValue.setFont(Font.font("Times New Roman", 
               FontWeight.BOLD, Constants2048.TEXT_SIZE_MID));
              }
              //if it is greater than 1024, it will given this constant
              //size for the font
              else if( (value >= 1024) )
              {
               tileValue.setFont(Font.font("Times New Roman", 
               FontWeight.BOLD, Constants2048.TEXT_SIZE_HIGH));
              }
        
              //if the value is greater than 8, the text will be assigned
              //to this constant color
              if(value >= 8 )
               {  
                tileValue.setFill(Constants2048.COLOR_VALUE_LIGHT);
               }
              //else it will be filled with this constant color
              else
               tileValue.setFill(Constants2048.COLOR_VALUE_DARK);
              //the tile and tileValue are added to the pane at the given
              //coordinates
              pane.add(tile, row, column );
              pane.add(tileValue, row, column);
              //the text is aligned to be centered inside the tile
              GridPane.setHalignment(tileValue, HPos.CENTER);
              //the pane is then added to the borderPane
              getBorder().setCenter(pane);
              //the old stackPane is cleared and then the borderPane with
              //the updated tiles is added to the stackPane
              getStack().getChildren().clear();
              getStack().getChildren().add(getBorder());
             }
           }
         }
    }

    private class myKeyHandler implements EventHandler<KeyEvent>{

    //handles any user input from the keyboard, and performs movements and
    //actions depending on the input or if the game is over
    //@param: KeyEvent e - the key that is pressed by the user
    //@return: none
    @Override
    public void handle(KeyEvent e)
    {
     //if the game is not over, the user will be able to press keys and put
     //in input
     if(getBoard().isGameOver() == false)
     {
     //if the key pressed is the up arrow, this chunk of code will run
     if(e.getCode()==KeyCode.UP)
     {
      //checks if you can move in the up direction 
      if(getBoard().canMove(Direction.UP))
      {
      System.out.println("Moving Up");
      //moves the board and adds a random tile
      getBoard().move(Direction.UP);
      getBoard().addRandomTile();
      //creates a fresh, new pane so that the new board can be drawn
      makeNewPane();
      //draws the new board after the move
      drawBoard(getPane(), getBoard());
      //creates a new header with title and score to update the score after
      //the move
      makeHeader();
      //the updated pane is then added to the borderPane
      getBorder().setCenter(getPane());
      //the updated borderPane is then added to a fresh stackPane
      getStack().getChildren().clear();
      getStack().getChildren().add(getBorder());
      }
     }

     //if the key pressed is down, this chunk of code will run
     else if(e.getCode()==KeyCode.DOWN)
     {
      //checks if you can move in the down direction
      if(getBoard().canMove(Direction.DOWN))
      {
      System.out.println("Moving Down");
      //moves the board and adds a random tile
      getBoard().move(Direction.DOWN);
      getBoard().addRandomTile();
      //creates a fresh pane so that the new board can be drawn
      makeNewPane();
      //draws the new board after the move
      drawBoard(getPane(), getBoard());
      //makes a new header with a title and score to draw the updated score
      makeHeader();
      //adds the updated pane to the borderPane
      getBorder().setCenter(getPane());
      //the updated borderPane is then added to a fresh stackpane
      getStack().getChildren().clear();
      getStack().getChildren().add(getBorder());
      }
     }
    
     //if the key pressed is right, this chunk of code will run
     else if(e.getCode()==KeyCode.RIGHT)
     {
      //checks if you can move in the right direction
      if(getBoard().canMove(Direction.RIGHT))
      {
      System.out.println("Moving Right");
      //moves the board and adds a random tile
      getBoard().move(Direction.RIGHT);
      getBoard().addRandomTile();
      //creates a fresh pane so that the new board can be drawn
      makeNewPane();
      //draws the new board after the move
      drawBoard(getPane(), getBoard());
      //makes a new header with a title and score to draw the updated score
      makeHeader();
      //adds the updated pane to the borderPane
      getBorder().setCenter(getPane());
      //updated borderPane is added to a fresh stackPane
      getStack().getChildren().clear();
      getStack().getChildren().add(getBorder());
      }
     }

     //if the key pressed is left, this chunk of code will run
     else if(e.getCode()==KeyCode.LEFT)
     {
      //checks if you can move in the left direction
      if(getBoard().canMove(Direction.LEFT))
      {
      System.out.println("Moving Left");
      //moves the board and adds a random tile
      getBoard().move(Direction.LEFT);
      getBoard().addRandomTile();
      //creates a fresh pane so that the new baord can be drawn
      makeNewPane();
      //draws the new board after the move
      drawBoard(getPane(), getBoard());
      //makes a new header with a title and the updated score
      makeHeader();
      //adds the updated pane to the borderPane
      getBorder().setCenter(getPane());
      //adds the updated borderPane to a fresh stackPane
      getStack().getChildren().clear();
      getStack().getChildren().add(getBorder());
      }
     }
    
     //if the key pressed is s, this chunk of code will run
     else if(e.getCode() == KeyCode.S)
     {
      String outputFileName = "outputFileName";
      //will try to save the board
      try
      {
       getBoard().saveBoard(outputFileName);
      }
      //if it cant save board, it will catch this exception
      catch (IOException s)
      {
       System.out.println("saveBoard threw an exception");
      }
       //if the board is saved then this line will print
       System.out.println("Saving Board to " + outputFileName);
     }
    
    }

    //if the game is over, this chunk of code will run
    else if( getBoard().isGameOver() == true)
    {
     //new rectangle is created to be used to blur out the background when
     //the game is over
     Rectangle blur = new Rectangle();
     //the rectangle is binded to the size of the window, and is set to the
     //constant color when the game is over
     blur.widthProperty().bind(getStack().widthProperty());
     blur.heightProperty().bind(getStack().heightProperty());
     blur.setFill(Constants2048.COLOR_GAME_OVER);
     blur.setOpacity(0.5);
     //the text Game Over! will be printed on top of the blurry rectangle
     Text gameOver = new Text();
     gameOver.setText("Game Over!");
     gameOver.setFont(Font.font("Times New Roman", FontWeight.BOLD, 70));
     gameOver.setFill(Color.BLACK);
     getBorder().setCenter(getPane());
     //the stackPane layers everything together, putting the borderPane on
     //the bottom, the blurry rectangle on top of that, and the text game 
     //over on the very top
     getStack().getChildren().clear();
     getStack().getChildren().add(getBorder());
     getStack().getChildren().add(blur);
     getStack().getChildren().add(gameOver);
      
    } 
}
}


    /** DO NOT EDIT BELOW */

    // The method used to process the command line arguments
    private void processArgs(String[] args)
    {
        String inputBoard = null;   // The filename for where to load the Board
        int boardSize = 0;          // The Size of the Board

        // Arguments must come in pairs
        if((args.length % 2) != 0)
        {
            printUsage();
            System.exit(-1);
        }

        // Process all the arguments 
        for(int i = 0; i < args.length; i += 2)
        {
            if(args[i].equals("-i"))
            {   // We are processing the argument that specifies
                // the input file to be used to set the board
                inputBoard = args[i + 1];
            }
            else if(args[i].equals("-o"))
            {   // We are processing the argument that specifies
                // the output file to be used to save the board
                outputBoard = args[i + 1];
            }
            else if(args[i].equals("-s"))
            {   // We are processing the argument that specifies
                // the size of the Board
                boardSize = Integer.parseInt(args[i + 1]);
            }
            else
            {   // Incorrect Argument 
                printUsage();
                System.exit(-1);
            }
        }

        // Set the default output file if none specified
        if(outputBoard == null)
            outputBoard = "2048.board";
        // Set the default Board size if none specified or less than 2
        if(boardSize < 2)
            boardSize = 4;

        // Initialize the Game Board
        try{
            if(inputBoard != null)
                board = new Board(new Random(), inputBoard);
            else
                board = new Board(new Random(), boardSize);
        }
        catch (Exception e)
        {
            System.out.println(e.getClass().getName() + 
                               " was thrown while creating a " +
                               "Board from file " + inputBoard);
            System.out.println("Either your Board(String, Random) " +
                               "Constructor is broken or the file isn't " +
                               "formated correctly");
            System.exit(-1);
        }
    }

    // Print the Usage Message 
    private static void printUsage()
    {
        System.out.println("Gui2048");
        System.out.println("Usage:  Gui2048 [-i|o file ...]");
        System.out.println();
        System.out.println("  Command line arguments come in pairs of the "+ 
                           "form: <command> <argument>");
        System.out.println();
        System.out.println("  -i [file]  -> Specifies a 2048 board that " + 
                           "should be loaded");
        System.out.println();
        System.out.println("  -o [file]  -> Specifies a file that should be " + 
                           "used to save the 2048 board");
        System.out.println("                If none specified then the " + 
                           "default \"2048.board\" file will be used");  
        System.out.println("  -s [size]  -> Specifies the size of the 2048" + 
                           "board if an input file hasn't been"); 
        System.out.println("                specified.  If both -s and -i" + 
                           "are used, then the size of the board"); 
        System.out.println("                will be determined by the input" +
                           " file. The default size is 4.");
    }


  }
 


 
