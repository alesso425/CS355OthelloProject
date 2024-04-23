package main;

//import Java ArrayList and Math functions
import java.util.ArrayList;
import java.lang.Math;

/**
 * The Othello class serves as the foundation of a game of Othello. When a client connects to the server, a new thread
 * launches an instance of Othello and the server and client can interact with each other to play the game.
 */
public class Othello {
    private String name;
    private String pass;
    private String[][] board;
    private int[] grid;

    /**
     * Othello constructor with name and password attributes; Initializes a playable board as a String matrix
     *
     * @param name username of the person
     * @param pass password for the game
     */
    public Othello(String name, String pass) {
        this.name = name;
        this.pass = pass;
        board = new String[9][9]; //declares a String matrix of size 9x9
        for (int i = 1; i < board.length; i++) { //traverses the String matrix and adds a boarder
            for (int j = 1; j < board[i].length; j++) {
                board[i][j] = "-";
            }
        }
        grid = new int[]{1, 2, 3, 4, 5, 6, 7, 8};
        //adds the top row of number labels
        for (int i = 1; i < board.length; i++) {
            board[i][0] = Integer.toString(grid[i - 1]);
        }
        //adds the left side of number labels
        for (int i = 1; i < board[0].length; i++) {
            board[0][i] = Integer.toString(grid[i - 1]);
        }
        //changes the center four squares to two player chips and two computer chips
        board[0][0] = " ";
        board[4][4] = "X"; //player's piece
        board[5][5] = "X";
        board[4][5] = "O"; //computer's piece
        board[5][4] = "O";
    }

    /**
     * The print board method traverse the existing board and prints out the matrix.
     */
    public String printBoard() {
        int[] pieces = countPieces();
        String result = " ";
        for (int i = 0; i < board.length; i++) {
            result += ("\n");
            for (int j = 0; j < board[i].length; j++) {
                result += board[i][j] + " ";
            }
        }
        result += "\n" + "Number of Player Pieces: " + pieces[0] + "\n" + "Number of CPU Pieces: " + pieces[1];
        return result;
    }

    /**
     * The countPieces() method traverses the board matrix and counts the total number of pieces for both the player
     * and the computer, assigning them to two spaces in an integer matrix.
     *
     * @return an integer array of 2 numbers; first one is the total of player's pieces and the other is the total for
     * computer pieces
     */
    public int[] countPieces()
    {
        //integer array for the two scores
        int[] x = new int[2];
        //traverses the entire board counting both the player's and computer's pieces, adding them to the corresponding scores
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == "O"){
                    x[0]++;
                }else if(board[i][j] == "X"){
                    x[1]++;
                }
            }
        }
        //return the integer array of the scores
        return x;
    }

    /**
     * The checkWin() method creates two arrays of all possible legal moves left for both the player and the computer,
     * then checking to see if the game has finished. If the game has finished, meaning the board if filled or no player
     * can make a legal move, then the method calculates the winner by comparing the two score values in the integer
     * array with store values from countPieces().
     *
     * @return an integer corresponding to a win, loss, or draw (or a warning integer to establish that the game needs
     * to be finished)
     */
    public int checkWin(){
        //binds a call of countPieces() to an integer array
        int[] x = countPieces();
        //binds each a call of allLegalMoves for the player and computer to determine if there are any moves to play to
        //an ArrayList, playerMoves and CPUMoves respectively
        ArrayList<int[]> playerMoves = allLegalMoves(true);
        ArrayList<int[]> CPUMoves = allLegalMoves(false);
        //if the total number of pieces played by both players equate to the total number of spaces on the board
        if(x[0] + x[1] >= 64){
            //if the player has more pieces than the computer, the player wins
            if(x[0] > x[1]){
                return 1;
            }else if(x[0] < x[1]){
            //if the computer has more pieces than the player, the computer wins
                return 2;
            //all other cases equate to a draw
            }else{
                return 3;
            }
        }else if(playerMoves.isEmpty() && CPUMoves.isEmpty()){
            //if the player has more pieces than the computer, the player wins
            if(x[0] > x[1]){
                return 1;           //Player win.
            }else if(x[0] < x[1]){
            //if the computer has more pieces than the player, the computer wins
                return 2;
            //all other cases equate to a draw
            }else{
                return 3;
            }
        }
        //if the board has not been filled completely, return -1 to signify the game has to continue
        return -1;
    }

    /**
     * The placePiecePlayer method "places a piece down" for the player where the specified coordinates are on the
     * board. The mmethod takes the coordinates corresponding to a spot on the matrix and changes that piece. Per
     * Othello rules, the method also changes pieces based on what connects your piece to some of your existing ones on
     * the board, therefore taking some pieces away from the computer. Lastly, the method prints the changed
     * Othello board.
     *
     * @param row row coordinate in the board matrix
     * @param column column coordinate in the board matrix
     */
    public void placePiecePlayer(int row, int column) {
        board[row][column] = "X";

        setHoriz(true, row, column);
        setVert(true, row, column);
        setDiag(true, row, column);

    }

    /**
     * In the placePieceCPU() method, there is a call of getBestMove(), which returns coordinates for the best move the
     * CPU can take to an integer array. The piece at those coordinates is changed and then, per Othello rules, the
     * method also changes pieces based on what connects to your piece to some of the computer's existing pieces,
     * taking those pieces away from the player. Lastly, the placePieceCPU() method prints the changed Othello board.
     */
    public void placePieceCPU(){
        int[] bestMove = getBestMoveCPU(false);
        int row = bestMove[0];
        int column = bestMove[1];
        board[row][column] = "O";

        setHoriz(false, row, column);
        setVert(false, row, column);
        setDiag(false, row, column);
    }

    /**
     * The placePiece() method changes the board, at specified coordinates, to the computer's piece.
     *
     * @param row row coordinate in the board matrix
     * @param column column coordinate in the board matrix
     */
    public void placePiece(int row, int column){
        board[row][column] = "O";
    }

    /**
     * The setHoriz() method changes the player's or computer's chips based on what coordinate is given in the
     * horizontal direction. The method takes the boolean parameter to see if the method needs to be run for the player
     * or computer, then takes the coordinates and traverses the board matrix to the left and right of the coordinates,
     * changing the pieces to the player's or computer's pieces.
     *
     * @param player boolean that determines if the method should be run for the player of the computer
     * @param row row coordinate in the board matrix
     * @param column column coordinate in the board matrix
     */
    public void setHoriz(boolean player, int row, int column){
        //if it is the player's turn
        if(player){
            //traverses to the right changing the spaces or computer's pieces to the player's if they
            //are in between the piece the player placed down and another one of their pieces
            for (int i = column+1; i < board.length; i++) {
                //if the space is empty
                if (board[row][i].equals("-")) {
                    break;
                //if the space has the player's piece
                } else if (board[row][i].equals("X")) {
                    //make all spaces/pieces in between the two pieces into the player's
                    for (int j = column + 1; j < i; j++) {
                        board[row][j] = "X";
                    }
                    break;
                }
            }
            //traverses to the left changing the spaces or computer's pieces to the player's if they
            //are in between the piece the player placed down and another one of their pieces
            for (int i = column-1; i > 0; i--){
                //if the space is empty
                if (board[row][i].equals("-")) {
                    break;
                //if the space has the player's piece
                } else if (board[row][i].equals("X")) {
                    //make all spaces/pieces in between the two pieces into the player's
                    for (int j = column - 1; j > i; j--) {
                        board[row][j] = "X";
                    }
                    break;
                }
            }
        //if it is the computer's turn
        }else{
            //traverses to the right changing the spaces or player's pieces to the computer's if they
            //are in between the piece the computer placed down and another one of their pieces
            for (int i = column+1; i < board.length; i++) {
                //if the space is empty
                if (board[row][i].equals("-")) {
                    break;
                } else if (board[row][i].equals("O")) {
                    //make all spaces/pieces in between the two pieces into the computer's
                    for (int j = column + 1; j < i; j++) {
                        board[row][j] = "O";
                    }
                    break;
                }
            }
            //traverses to the changing the spaces or computer's pieces to the player's if they
            //are in between the piece the computer placed down and another one of their pieces
            for (int i = column-1; i > 0; i--){
                //if the space is empty
                if (board[row][i].equals("-")) {
                    break;
                } else if (board[row][i].equals("O")) {
                    //make all spaces/pieces in between the two pieces into the computer's
                    for (int j = column - 1; j > i; j--) {
                        board[row][j] = "O";
                    }
                    break;
                }
            }
        }
    }

    /**
     * The setVert() method changes the player's or computer's chips based on what coordinate is given in the
     * vertical direction. The method takes the boolean parameter to see if the method needs to be run for the player
     * or computer, then takes the coordinates and traverses the board matrix up and down from the coordinates,
     * changing the pieces to the player's or computer's pieces.
     *
     * @param player boolean that determines if the method should be run for the player of the computer
     * @param row row coordinate in the board matrix
     * @param column column coordinate in the board matrix
     */
    public void setVert(boolean player, int row, int column){
        //if it is the player's turn
        if(player) {
            //traverses up the board changing the spaces or computer's pieces to the player's if they
            //are in between the piece the player placed down and another one of their pieces
            for (int i = row+1; i < board.length; i++) {
                //if the space is empty
                if (board[i][column].equals("-")) {
                    break;
                //if the space has the player's piece
                } else if (board[i][column].equals("X")) {
                    //make all spaces/pieces in between the two pieces into the player's
                    for (int j = row + 1; j < i; j++) {
                        board[j][column] = "X";
                    }
                    break;
                }
            }
            //traverses down the board changing the spaces or computer's pieces to the player's if they
            //are in between the piece the player placed down and another one of their pieces
            for (int i = row-1; i > 0; i--){
                //if the space is empty
                if (board[i][column].equals("-")) {
                    break;
                //if the space has the player's piece
                } else if (board[i][column].equals("X")) {
                    //make all spaces/pieces in between the two pieces into the player's
                    for (int j = row - 1; j > i; j--) {
                        board[j][column] = "X";
                    }
                    break;
                }
            }
        //if it is the computer's turn
        }else{
            //traverses up the board changing the spaces or player's pieces to the computer's if they
            //are in between the piece the computer placed down and another one of their pieces
            for (int i = row+1; i < board.length; i++) {
                //if the space is empty
                if (board[i][column].equals("-")) {
                    break;
                ///if the space has the computer's piece
                } else if (board[i][column].equals("O")) {
                    //make all spaces/pieces in between the two pieces into the computer's
                    for (int j = row + 1; j < i; j++) {
                        board[j][column] = "O";
                    }
                    break;
                }
            }
            //traverse down from the coordinates and change each piece to O until the end off the CPU's pieces or board
            for (int i = column-1; i > 0; i--){
                //if the space is empty
                if (board[i][column].equals("-")) {
                    break;
                //if the space has the computer's piece
                } else if (board[i][column].equals("O")) {
                    //make all spaces/pieces in between the two pieces into the computer's
                    for (int j = row - 1; j > i; j--) {
                        board[i][column] = "O";
                    }
                    break;
                }
            }
        }
    }

    /**
     * The setDiag() method changes the player's or computer's chips based on what coordinate is given in the
     * diagonal directions. The method takes the boolean parameter to see if the method needs to be run for the player
     * or computer, then takes the coordinates and traverses the board matrix in all four diagonal directions from the
     * coordinates, changing the pieces to the player's or computer's pieces.
     *
     * @param player boolean that determines if the method should be run for the player of the computer
     * @param row row coordinate in the board matrix
     * @param column column coordinate in the board matrix
     */
    public void setDiag(boolean player, int row, int column){
        //if it is the player's turn
        if(player){
            //traverses in the upper-right direction changing the spaces or computer's pieces to the player's if they
            //are in between the piece the player placed down and another one of their pieces
            //set the comparing coordinates to the space to the upper-right of the given coordinates
            int x = column + 1;
            int y = row - 1;
            int x2 = column+1;
            int y2 = row-1;
            while(x < board.length && y > 0) {
                //if the space is empty
                if(board[y][x].equals("-")){
                    break;
                //if the space has the player's piece
                }else if (board[y][x].equals("X")){
                    //make all spaces/pieces in between the two pieces into the player's
                    while(x2 < x && y2 > y){
                        board[y2][x2] = "X";
                        x2++;
                        y2--;
                    }
                    break;
                }
                //increments to the next spot in the upper-right direction
                x++;
                y--;
            }
            //traverses in the bottom-right direction changing the spaces or computer's pieces to the player's if they
            //are in between the piece the player placed down and another one of their pieces
            //set the comparing coordinates to the space to the bottom-right of the given coordinates
            x = column + 1;
            x2 = column + 1;
            y = row + 1;
            y2 = row + 1;
            while(x < board.length && y < board.length) {
                //if the space is empty
                if(board[y][x].equals("-")){
                    break;
                //if the space has the player's piece
                }else if(board[y][x].equals("X")){
                    //make all spaces/pieces in between the two pieces into the player's
                    while(x2 < x && y2 < y){
                        board[y2][x2] = "X";
                        x2++;
                        y2++;
                    }
                    break;
                }
                //increments to the next spot in the bottom-right direction
                x++;
                y++;
            }
            //traverses in the bottom-left direction changing the spaces or computer's pieces to the player's if they
            //are in between the piece the player placed down and another one of their pieces
            //set the comparing coordinates to the space to the bottom-left of the given coordinates
            x = column - 1;
            x2 = column - 1;
            y = row + 1;
            y2 = row + 1;
            while(x > 0 && y < board.length) {
                //if the space is empty
                if(board[y][x].equals("-")){
                    break;
                //if the space has the player's piece
                }else if(board[y][x].equals("X")){
                    //make all spaces/pieces in between the two pieces into the player's
                    while(x2 > x && y2 < y){
                        board[y2][x2] = "X";
                        x2--;
                        y2++;
                    }
                    break;
                }
                //increments to the next spot in the bottom-left direction
                x--;
                y++;
            }
            //traverses in the upper-left direction changing the spaces or computer's pieces to the player's if they
            //are in between the piece the player placed down and another one of their pieces
            //set the comparing coordinates to the space to the upper-left of the given coordinates
            x = column - 1;
            x2 = column - 1;
            y = row - 1;
            y2 = row - 1;
            while(x > 0 && y > 0) {
                //if the space is empty
                if (board[y][x].equals("-")){
                    break;
                //if the space has the player's piece
                }else if(board[y][x].equals("X")){
                    //make all spaces/pieces in between the two pieces into the player's
                    while(x2 > x && y2 > y){
                        board[y2][x2] = "X";
                        x2--;
                        y2--;
                    }
                    break;
                }
                //increments to the next spot in the upper-left direction
                x--;
                y--;
            }
        //if it is the computer's turn
        }else{
            //traverses in the upper-right direction changing the spaces or player's pieces to the computer's if they
            //are in between the piece the computer placed down and another one of their pieces
            //set the comparing coordinates to the space to the upper-right of the given coordinates
            int x = column + 1;
            int y = row - 1;
            int x2 = column+1;
            int y2 = row-1;
            while(x < board.length && y > 0) {
                //if the space is empty
                if(board[y][x].equals("-")){
                    break;
                //if the space has the computer's piece
                }else if (board[y][x].equals("O")){
                    //make all spaces/pieces in between the two pieces into the player's
                    while(x2 < x && y2 > y){
                        board[y2][x2] = "O";
                        x2++;
                        y2--;
                    }
                    break;
                }
                //increments to the next spot in the upper-right direction
                x++;
                y--;
            }
            //traverses in the bottom-right direction changing the spaces or player's pieces to the computer's if they
            //are in between the piece the computer placed down and another one of their pieces
            //set the comparing coordinates to the space to the bottom-right of the given coordinates
            x = column + 1;
            x2 = column + 1;
            y = row + 1;
            y2 = row + 1;
            while(x < board.length && y < board.length) {
                //if the space is empty
                if(board[y][x].equals("-")){
                    break;
                //if the space has the computer's piece
                }else if(board[y][x].equals("O")){
                    //make all spaces/pieces in between the two pieces into the player's
                    while(x2 < x && y2 < y) {
                        board[y2][x2] = "O";
                        x2++;
                        y2++;
                    }
                    break;
                }
                //increments to the next spot in the bottom-right direction
                x++;
                y++;
            }
            //traverses in the bottom-left direction changing the spaces or player's pieces to the computer's if they
            //are in between the piece the computer placed down and another one of their pieces
            //set the comparing coordinates to the space to the bottom-left of the given coordinates
            x = column - 1;
            x2 = column - 1;
            y = row + 1;
            y2 = row + 1;
            while(x > 0 && y < board.length) {
                //if the space is empty
                if(board[y][x].equals("-")){
                    break;
                //if the space has the computer's piece
                }else if(board[y][x].equals("O")){
                    //make all spaces/pieces in between the two pieces into the player's
                    while(x2 > x && y2 < y){
                        board[y2][x2] = "O";
                        x2--;
                        y2++;
                    }
                    break;
                }
                //increments to the next spot in the bottom-left direction
                x--;
                y++;
            }
            //traverses in the upper-left direction changing the spaces or player's pieces to the computer's if they
            //are in between the piece the computer placed down and another one of their pieces
            //set the comparing coordinates to the space to the upper-left of the given coordinates
            x = column - 1;
            x2 = column - 1;
            y = row - 1;
            y2 = row - 1;
            while(x > 0 && y > 0) {
                //if the space is empty
                if (board[y][x].equals("-")){
                    break;
                //if the space has the computer's piece
                }else if(board[y][x].equals("O")){
                    //make all spaces/pieces in between the two pieces into the player's
                    while(x2 > x && y2 > y){
                        board[y2][x2] = "O";
                        x2--;
                        y2--;
                    }
                    break;
                }
                //increments to the next spot in the upper-left direction
                x--;
                y--;
            }
        }
    }

    /**
     * The allLegalMoves() method traverses the board for the specified player and finds the coordinates where a legal
     * move would be. The method then adds the set of coordinates, which is in an integer array format, to an ArrayList
     * of integer arrays. Lastly, the method returns the list of coordinates.
     *
     * @param player boolean that determines if the method should be run for the player of the computer
     * @return an ArrayList of integer arrays that will represent each set of coordinates for a legal move
     */
    public ArrayList<int[]> allLegalMoves(boolean player)
    {
        //declares an ArrayList of the type integer array
        ArrayList<int[]> list = new ArrayList<int[]>();
        //declare an empty integer array
        int[] x;
        //if it is the player's turn
        if (player)
        {
            //traverses the board finding coordinates that would work for the player and adding them to the list
            for (int i = 0; i < board.length; i++)
            {
                for (int j = 0; j < board[i].length; j++)
                {
                    if (!board[i][j].equals("-") && isLegal(true, i, j))
                    {
                        x = new int[]{i, j};
                        list.add(x);
                    }
                }
            }
        }
        //if it is the computer's turn
        else
        {
            //traverses the board finding coordinates that would work for the computer and adding them to the list
            for (int i = 0; i < board.length; i++)
            {
                for (int j = 0; j < board[i].length; j++)
                {
                    if (board[i][j].equals("-") && isLegal(false, i, j))
                    {
                        x = new int[]{i,j};
                        list.add(x);
                    }
                }
            }
        }
        return list;
    }

    /**
     * The isLegal() method determines if the move, at the specified coordinates and for the specified player, is legal
     * to play. It utilizes the getLegal() method and checks to see whether there is a legal move at the coordinates.
     * If getLegal() returns an integer array with -1's, which is one outcome based on rules of the game, then the
     * move is not legal and the method returns false. The method returns true if there is any other coordinates stored
     * within the returned getLegal() array.
     *
     * @param player boolean that determines if the method should be run for the player of the computer
     * @param row row coordinate in the board matrix
     * @param column column coordinate in the board matrix
     * @return boolean that determines if a move is legal or not
     */
    public boolean isLegal(boolean player, int row, int column)
    {
        boolean con = false;
        int[][] arr = getLegal(player, row, column);
        //checks to see if there is at least one legal move in the array
        for(int i = 0; i < arr.length; i++){
           if(arr[i][0] > 0){
               con = true;
               break;
           }
        }
        return con;
    }

    /**
     * The getLegal() method determines a set of coordinates in an integer array that represent the choices of legal
     * moves that the specified player can choose from. The method takes on the specified coordinates and calculates
     * all the moves the player or computer can make at the time it is called during someone's turn.
     *
     * @param player boolean that determines if the method should be run for the player of the computer
     * @param row row coordinate in the board matrix
     * @param column column coordinate in the board matrix
     * @return integer matrix that represents all legal moves that the player would be able to choose from
     */
    public int[][] getLegal(boolean player, int row, int column){
        int[][] result = new int[8][2];
        //if it is the player's turn
        if(player) {
            //checks to the left of the given coordinates to see if there is an open space or an enemy space
            for (int i = column - 1; i > 0; i--) {
                //if the space is empty, the space at these coordinates is illegal
                if (board[row][i].equals("-")) {
                    result[0][0] = -1;
                    result[0][1] = -1;
                    break; //quit
                //if the space is X, the space at these coordinates is legal, set the coordinates to the integer matrix
                } else if (board[row][i].equals("X")) {
                    result[0][0] = row;
                    result[0][1] = i;
                    break; //quit
                //the space is illegal (catch all)
                } else {
                    result[0][0] = -1;
                    result[0][1] = -1;
                }
            }
            //checks to the top-left of the given coordinates to see if there is an open or an enemy space
            //iterate one up and one left
            int x = column - 1;
            int y = row - 1;
            while(x > 0 && y > 0) {
                //if the space is empty, the space at these coordinates is illegal
                if (board[y][x].equals("-")){
                    result[1][0] = -1;
                    result[1][1] = -1;
                    break;
                //if the space is X, the space at these coordinates is legal, set the coordinates to the integer matrix
                }else if(board[y][x].equals("X")){
                    result[1][0] = y;
                    result[1][1] = x;
                    break;
                //the space is illegal (catch all)
                }else{
                    result[1][0] = -1;
                    result[1][1] = -1;
                }
                //iterate one up and one left
                x--;
                y--;
            }
            //checks up above the given coordinates to see if there is an open space or an enemy space
            for (int i = row - 1; i > 0; i--) {
                //if the space is empty, the space at these coordinates is illegal
                if (board[i][column].equals("-")) {
                    result[2][0] = -1;
                    result[2][1] = -1;
                    break; //quit
                //if the space is X, the space at these coordinates is legal, set the coordinates to the integer matrix
                } else if (board[i][column].equals("X")) {
                    result[2][0] = i;
                    result[2][1] = column;
                    break; //quit
                //the space is illegal (catch all)
                } else {
                    result[2][0] = -1;
                    result[2][1] = -1;
                }
            }
            //checks to the top-right of the given coordinates to see if there is an open or an enemy space
            //iterate one up and one right
            x = column + 1;
            y = row - 1;
            while(x < board.length && y > 0) { //Up-Right;
                //if the space is empty, the space at these coordinates is illegal
                if(board[y][x].equals("-")){
                    result[3][0] = -1;
                    result[3][1] = -1;
                    break;
                //if the space is X, the space at these coordinates is legal, set the coordinates to the integer matrix
                }else if (board[y][x].equals("X")){
                    result[3][0] = y;
                    result[3][1] = x;
                    break;
                //the space is illegal (catch all)
                }else{
                    result[3][0] = -1;
                    result[3][1] = -1;
                }
                //iterate one up and one right
                x++;
                y--;
            }
            //checks to the right of the given coordinates to see if there is an open space or an enemy space
            for (int i = column + 1; i < board.length; i++) {
                //if the space is empty, the space at these coordinates is illegal
                if (board[row][i].equals("-")) {
                    result[4][0] = -1;
                    result[4][1] = -1;
                    break;
                //if the space is X, the space at these coordinates is legal, set the coordinates to the integer matrix
                } else if (board[row][i].equals("X")) {
                    result[4][0] = row;
                    result[4][1] = i;
                    break;
                //the space is illegal (catch all)
                } else {
                    result[4][0] = -1;
                    result[4][1] = -1;
                }
            }
            //checks to the bottom-right of the given coordinates to see if there is an open or an enemy space
            //iterate one down and one right
            x = column + 1;
            y = row + 1;
            while(x < board.length && y < board.length) {
                //if the space is empty, the space at these coordinates is illegal
                if(board[y][x].equals("-")){
                    result[5][0] = -1;
                    result[5][1] = -1;
                    break;
                //if the space is X, the space at these coordinates is legal, set the coordinates to the integer matrix
                }else if(board[y][x].equals("X")){
                    result[5][0] = y;
                    result[5][1] = x;
                    break;
                //the space is illegal (catch all)
                }else{
                    result[5][0] = -1;
                    result[5][1] = -1;
                }
                //iterate one down and one right
                x++;
                y++;
            }
            //checks below the given coordinates to see if there is an open space or an enemy space
            for (int i = row + 1; i < board[row].length; i++) {
                //if the space is empty, the space at these coordinates is illegal
                if (board[i][column].equals("-")) {
                    result[6][0] = -1;
                    result[6][1] = -1;
                    break;
                //if the space is X, the space at these coordinates is legal, set the coordinates to the integer matrix
                } else if (board[i][column].equals("X")) {
                    result[6][0] = i;
                    result[6][1] = column;
                    break;
                //the space is illegal (catch all)
                } else {
                    result[6][0] = -1;
                    result[6][1] = -1;
                }
            }
            //checks to the bottom-left of the given coordinates to see if there is an open or an enemy space
            //iterate one down and one left
            x = column - 1;
            y = row + 1;
            while(x > 0 && y < board.length) {
                //if the space is empty, the space at these coordinates is illegal
                if(board[y][x].equals("-")){
                    result[7][0] = -1;
                    result[7][1] = -1;
                    break;
                //if the space is X, the space at these coordinates is legal, set the coordinates to the integer matrix
                }else if(board[y][x].equals("X")){
                    result[7][0] = y;
                    result[7][1] = x;
                    break;
                //the space is illegal (catch all)
                }else{
                    result[7][0] = -1;
                    result[7][1] = -1;
                }
                //iterate one down and one left
                x--;
                y++;
            }
        //if it is the computer's turn
        }else {
            //checks to the left of the given coordinates to see if there is an open space or an enemy space
            for (int i = column - 1; i > 0; i--) {
                //if the space is empty, the space at these coordinates is illegal
                if (board[row][i].equals("-")) {
                    result[0][0] = -1;
                    result[0][1] = -1;
                    break;
                //if the space is X, the space at these coordinates is legal, set the coordinates to the integer matrix
                } else if (board[row][i].equals("O")) {
                    result[0][0] = row;
                    result[0][1] = i;
                    break;
                //the space is illegal (catch all)
                } else {
                    result[0][0] = -1;
                    result[0][1] = -1;
                }
            }
            //checks to the top-left of the given coordinates to see if there is an open or an enemy space
            //iterate one up and one left
            int x = column - 1;
            int y = row - 1;
            while (x > 0 && y > 0) {
                //if the space is empty, the space at these coordinates is illegal
                if (board[y][x].equals("-")) {
                    result[1][0] = -1;
                    result[1][1] = -1;
                    break;
                //if the space is X, the space at these coordinates is legal, set the coordinates to the integer matrix
                } else if (board[y][x].equals("O")) {
                    result[1][0] = y;
                    result[1][1] = x;
                    break;
                } else {
                //the space is illegal (catch all)
                    result[1][0] = -1;
                    result[1][1] = -1;
                }
                //iterate one up and one left
                x--;
                y--;
            }
            //checks up above the given coordinates to see if there is an open space or an enemy space
            for (int i = row - 1; i > 0; i--) {
                //if the space is empty, the space at these coordinates is illegal
                if (board[i][column].equals("-")) {
                    result[2][0] = -1;
                    result[2][1] = -1;
                    break;
                //if the space is X, the space at these coordinates is legal, set the coordinates to the integer matrix
                } else if (board[i][column].equals("O")) {
                    result[2][0] = i;
                    result[2][1] = column;
                    break;
                //the space is illegal (catch all)
                } else {
                    result[2][0] = -1;
                    result[2][1] = -1;
                }
            }
            //checks to the top-right of the given coordinates to see if there is an open or an enemy space
            //iterate one up and one right
            x = column + 1;
            y = row - 1;
            while (x < board.length && y > 0) {
                //if the space is empty, the space at these coordinates is illegal
                if (board[y][x].equals("-")) {
                    result[3][0] = -1;
                    result[3][1] = -1;
                    break;
                //if the space is X, the space at these coordinates is legal, set the coordinates to the integer matrix
                } else if (board[y][x].equals("O")) {
                    result[3][0] = y;
                    result[3][1] = x;
                    break;
                //the space is illegal (catch all)
                } else {
                    result[3][0] = -1;
                    result[3][1] = -1;
                }
                //iterate one up and one right
                x++;
                y--;
            }
            //checks to the right of the given coordinates to see if there is an open space or an enemy space
            for (int i = column + 1; i < board.length; i++) {
                //if the space is empty, the space at these coordinates is illegal
                if (board[row][i].equals("-")) {
                    result[4][0] = -1;
                    result[4][1] = -1;
                    break;
                //if the space is X, the space at these coordinates is legal, set the coordinates to the integer matrix
                } else if (board[row][i].equals("O")) {
                    result[4][0] = row;
                    result[4][1] = i;
                    break;
                //the space is illegal (catch all)
                } else {
                    result[4][0] = -1;
                    result[4][1] = -1;
                }
            }
            //checks to the bottom-right of the given coordinates to see if there is an open or an enemy space
            //iterate one down and one right
            x = column + 1;
            y = row + 1;
            while (x < board.length && y < board.length) {
                //if the space is empty, the space at these coordinates is illegal
                if (board[y][x].equals("-")) {
                    result[5][0] = -1;
                    result[5][1] = -1;
                    break;
                //if the space is X, the space at these coordinates is legal, set the coordinates to the integer matrix
                } else if (board[y][x].equals("O")) {
                    result[5][0] = y;
                    result[5][1] = x;
                    break;
                //the space is illegal (catch all)
                } else {
                    result[5][0] = -1;
                    result[5][1] = -1;
                }
                //iterate one down and one right
                x++;
                y++;
            }
            //checks below the given coordinates to see if there is an open space or an enemy space
            for (int i = row + 1; i < board[row].length; i++) {
                //if the space is empty, the space at these coordinates is illegal
                if (board[i][column].equals("-")) {
                    result[6][0] = -1;
                    result[6][1] = -1;
                    break;
                //if the space is X, the space at these coordinates is legal, set the coordinates to the integer matrix
                } else if (board[i][column].equals("O")) {
                    result[6][0] = i;
                    result[6][1] = column;
                    break;
                //the space is illegal (catch all)
                } else {
                    result[6][0] = -1;
                    result[6][1] = -1;
                }
            }
            //checks to the bottom-left of the given coordinates to see if there is an open or an enemy space
            //iterate one down and one left
            x = column - 1;
            y = row + 1;
            while (x > 0 && y < board.length) {
                //if the space is empty, the space at these coordinates is illegal
                if (board[y][x].equals("-")) {
                    result[7][0] = -1;
                    result[7][1] = -1;
                    break;
                //if the space is X, the space at these coordinates is legal, set the coordinates to the integer matrix
                } else if (board[y][x].equals("O")) {
                    result[7][0] = y;
                    result[7][1] = x;
                    break;
                //the space is illegal (catch all)
                } else {
                    result[7][0] = -1;
                    result[7][1] = -1;
                }
                //iterate one down and one left
                x--;
                y++;
            }
        }
        //return the resulting array matrix of coordinates
        return result;
    }

    /**
     * The getBestMove() method, utilized in the placePieceCPU() method, creates an integer array of the coordinate of
     * the best move that the computer can make in order to obtain the most pieces on this the turn.
     *
     * @param player boolean that determines if the method should be run for the player of the computer
     * @return integer array representing coordinate of the best move the computer can make
     */
    public int[] getBestMoveCPU(boolean player){
        //integer array for the coordinate, integers for counter sum and comparing sum
        int[] move = new int[2];
        int sum = 0;
        int maxsum = 0;
        //an ArrayList for the legal moves of the player, an integer matrix for the list of coordinates of legal moves
        ArrayList<int[]> moveList = allLegalMoves(player); //will always be called as false in this case
        int[][] legalList;
        //an integer array for any coordinates to compare to
        int[] coordinates = new int[2];

        //traverses the ArrayList of the computer's legal moves to find the best one, comparing each one's total pieces
        //changed to find the greatest change
        for(int i = 0; i < moveList.size(); i++){
            //coordinates becomes the set at index i; the list becomes all moves that the computer is allowed to play
            coordinates = moveList.get(i);
            legalList = getLegal(player, coordinates[0], coordinates[1]);
            for(int j = 0; j < legalList.length; j++){
                if(legalList[j][0] != -1 && legalList[j][1] != -1){
                    if(legalList[j][1] != coordinates[1]){
                        sum = sum + Math.abs(legalList[j][1] - coordinates[1]) - 1;
                    }else{
                        sum = sum + Math.abs(legalList[j][0] - coordinates[0]) - 1;
                    }
                }
            }
            //if the new is greater than the previous maximum sum, then the new sum is the highest and the corresponding
            //move is the best move that the computer can make
            if(sum > maxsum){
                maxsum = sum;
                move = moveList.get(i);
            }
            sum = 0;
        }
        //returns the best coordinate for the computer to play
        return move;
    }

    /**
     * The getNames() method returns the username of the game.
     *
     * @return the username of the game
     */
    public String getName(){
        return name;
    }

    /**
     * THe getPass() method returns the password of the game.
     *
     * @return the password of the game
     */
    public String getPass(){
        return pass;
    }
}
