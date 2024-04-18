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
        int[] x = new int[2];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == "O"){
                    x[0]++;
                }else if(board[i][j] == "X"){
                    x[1]++;
                }
            }
        }
        return x;
    }

    public int checkWin(){
        int[] x = countPieces();
        ArrayList<int[]> playerMoves = allLegalMoves(true);
        ArrayList<int[]> CPUMoves = allLegalMoves(false);
        if(x[0] + x[1] >= 64){
            if(x[0] > x[1]){
                return 1;           //Player win.
            }else if(x[0] < x[1]){
                return 2;           //CPU win.
            }else{
                return 3;           //Draw.
            }
        }else if(playerMoves.isEmpty() && CPUMoves.isEmpty()){
            if(x[0] > x[1]){
                return 1;           //Player win.
            }else if(x[0] < x[1]){
                return 2;           //CPU win.
            }else{
                return 3;           //Draw.
            }
        }

        return -1; //No win yet.
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
        int[] bestMove = getBestMove(false);
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
            //traverse to the right of the coordinates and change each piece to X until the end off your pieces or board
            for (int i = column+1; i < board.length; i++) {
                if (board[row][i].equals("-")) {
                    break;
                } else if (board[row][i].equals("X")) {
                    for (int j = column + 1; j < i; j++) {
                        board[row][j] = "X";
                    }
                    break;
                }
            }
            //traverse to the left of the coordinates and change each piece to X until the end of your pieces or board
            for (int i = column-1; i > 0; i--){
                if (board[row][i].equals("-")) {
                    break;
                } else if (board[row][i].equals("X")) {
                    for (int j = column - 1; j > i; j--) {
                        board[row][j] = "X";
                    }
                    break;
                }
            }
        //if it is the computer's turn
        }else{
            //traverse to the right of the coordinates and change each piece to O until the end of the CPU's pieces or board
            for (int i = column+1; i < board.length; i++) {
                if (board[row][i].equals("-")) {
                    break;
                } else if (board[row][i].equals("O")) {
                    for (int j = column + 1; j < i; j++) {
                        board[row][j] = "O";
                    }
                    break;
                }
            }
            //traverse to the left of the coordinates and change each piece to O until the end of the CPU's pieces or board
            for (int i = column-1; i > 0; i--){
                if (board[row][i].equals("-")) {
                    break;
                } else if (board[row][i].equals("O")) {
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
            //traverse up from the coordinates and change each piece to X until the end off your pieces or board
            for (int i = row+1; i < board.length; i++) {
                if (board[i][column].equals("-")) {
                    break;
                } else if (board[i][column].equals("X")) {
                    for (int j = row + 1; j < i; j++) {
                        board[j][column] = "X";
                    }
                    break;
                }
            }
            //traverse down from the coordinates and change each piece to X until the end of your pieces or board
            for (int i = row-1; i > 0; i--){
                if (board[i][column].equals("-")) {
                    break;
                } else if (board[i][column].equals("X")) {
                    for (int j = row - 1; j > i; j--) {
                        board[j][column] = "X";
                    }
                    break;
                }
            }
        //if it is the computer's turn
        }else{
            //traverse up from the coordinates and change each piece to O until the end off the CPU's pieces or board
            for (int i = row+1; i < board.length; i++) {
                if (board[i][column].equals("-")) {
                    break;
                } else if (board[i][column].equals("O")) {
                    for (int j = row + 1; j < i; j++) {
                        board[j][column] = "O";
                    }
                    break;
                }
            }
            //traverse down from the coordinates and change each piece to O until the end off the CPU's pieces or board
            for (int i = column-1; i > 0; i--){
                if (board[i][column].equals("-")) {
                    break;
                } else if (board[i][column].equals("O")) {
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
            int x = column + 1;
            int y = row - 1;
            int x2 = column+1;
            int y2 = row-1;
            while(x < board.length && y > 0) { //Up-Right;
                if(board[y][x].equals("-")){
                    break;
                }else if (board[y][x].equals("X")){
                    while(x2 < x && y2 > y){
                        board[y2][x2] = "X";
                        x2++;
                        y2--;
                    }
                    break;
                }
                x++;
                y--;
            }
            x = column + 1;
            x2 = column + 1;
            y = row + 1;
            y2 = row + 1;
            while(x < board.length && y < board.length) { //Down-Right
                if(board[y][x].equals("-")){
                    break;
                }else if(board[y][x].equals("X")){
                    while(x2 < x && y2 < y){
                        board[y2][x2] = "X";
                        x2++;
                        y2++;
                    }
                    break;
                }
                x++;
                y++;
            }
            x = column - 1;
            x2 = column - 1;
            y = row + 1;
            y2 = row + 1;
            while(x > 0 && y < board.length) { //Down-Left
                if(board[y][x].equals("-")){
                    break;
                }else if(board[y][x].equals("X")){
                    while(x2 > x && y2 < y){
                        board[y2][x2] = "X";
                        x2--;
                        y2++;
                    }
                    break;
                }
                x--;
                y++;
            }
            x = column - 1;
            x2 = column - 1;
            y = row - 1;
            y2 = row - 1;
            while(x > 0 && y > 0) { //Up-Left
                if (board[y][x].equals("-")){
                    break;
                }else if(board[y][x].equals("X")){
                    while(x2 > x && y2 > y){
                        board[y2][x2] = "X";
                        x2--;
                        y2--;
                    }
                    break;
                }
                x--;
                y--;
            }
        //if it is the computer's turn
        }else{
            int x = column + 1;
            int y = row - 1;
            int x2 = column+1;
            int y2 = row-1;
            while(x < board.length && y > 0) { //Up-Right;
                if(board[y][x].equals("-")){
                    break;
                }else if (board[y][x].equals("O")){
                    while(x2 < x && y2 > y){
                        board[y2][x2] = "O";
                        x2++;
                        y2--;
                    }
                    break;
                }
                x++;
                y--;
            }
            x = column + 1;
            x2 = column + 1;
            y = row + 1;
            y2 = row + 1;
            while(x < board.length && y < board.length) { //Down-Right
                if(board[y][x].equals("-")){
                    break;
                }else if(board[y][x].equals("O")){
                    while(x2 < x && y2 < y) {
                        board[y2][x2] = "O";
                        x2++;
                        y2++;
                    }
                    break;
                }
                x++;
                y++;
            }
            x = column - 1;
            x2 = column - 1;
            y = row + 1;
            y2 = row + 1;
            while(x > 0 && y < board.length) { //Down-Left
                if(board[y][x].equals("-")){
                    break;
                }else if(board[y][x].equals("O")){
                    while(x2 > x && y2 < y){
                        board[y2][x2] = "O";
                        x2--;
                        y2++;
                    }
                    break;
                }
                x--;
                y++;
            }
            x = column - 1;
            x2 = column - 1;
            y = row - 1;
            y2 = row - 1;
            while(x > 0 && y > 0) { //Up-Left
                if (board[y][x].equals("-")){
                    break;
                }else if(board[y][x].equals("O")){
                    while(x2 > x && y2 > y){
                        board[y2][x2] = "O";
                        x2--;
                        y2--;
                    }
                    break;
                }
                x--;
                y--;
            }
        }
    }

    public ArrayList<int[]> allLegalMoves(boolean player)
    {
        ArrayList<int[]> list = new ArrayList<int[]>();
        int[] x;
        if (player)
        {
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
        else
        {
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
    public boolean isLegal(boolean player, int row, int column)
    {
        boolean con = false;
        int[][] arr = getLegal(player, row, column);
        for(int i = 0; i < arr.length; i++){
           if(arr[i][0] > 0){
               con = true;
               break;
           }
        }

        return con;
    }

    public int[][] getLegal(boolean player, int row, int column){ //Makes a 2D-array for the location of legal moves left, up-left, up, up-right, right, down-right, down, down-left
        int[][] result = new int[8][2];
        if(player) {
            //left
            for (int i = column - 1; i > 0; i--) {
                if (board[row][i].equals("-")) {
                    result[0][0] = -1;
                    result[0][1] = -1;
                    break;
                } else if (board[row][i].equals("X")) {
                    result[0][0] = row;
                    result[0][1] = i;
                    break;
                } else {
                    result[0][0] = -1;
                    result[0][1] = -1;
                }
            }
            //up-left
            int x = column - 1;
            int y = row - 1;
            while(x > 0 && y > 0) {
                if (board[y][x].equals("-")){
                    result[1][0] = -1;
                    result[1][1] = -1;
                    break;
                }else if(board[y][x].equals("X")){
                    result[1][0] = y;
                    result[1][1] = x;
                    break;
                }else{
                    result[1][0] = -1;
                    result[1][1] = -1;
                }
                x--;
                y--;
            }
            //up
            for (int i = row - 1; i > 0; i--) {
                if (board[i][column].equals("-")) {
                    result[2][0] = -1;
                    result[2][1] = -1;
                    break;
                } else if (board[i][column].equals("X")) {
                    result[2][0] = i;
                    result[2][1] = column;
                    break;
                } else {
                    result[2][0] = -1;
                    result[2][1] = -1;
                }
            }
            //up-right
            x = column + 1;
            y = row - 1;
            while(x < board.length && y > 0) { //Up-Right;
                if(board[y][x].equals("-")){
                    result[3][0] = -1;
                    result[3][1] = -1;
                    break;
                }else if (board[y][x].equals("X")){
                    result[3][0] = y;
                    result[3][1] = x;
                    break;
                }else{
                    result[3][0] = -1;
                    result[3][1] = -1;
                }
                x++;
                y--;
            }
            //right
            for (int i = column + 1; i < board.length; i++) {
                if (board[row][i].equals("-")) {
                    result[4][0] = -1;
                    result[4][1] = -1;
                    break;
                } else if (board[row][i].equals("X")) {
                    result[4][0] = row;
                    result[4][1] = i;
                    break;
                } else {
                    result[4][0] = -1;
                    result[4][1] = -1;
                }
            }
            //down-right
            x = column + 1;
            y = row + 1;
            while(x < board.length && y < board.length) { //Down-Right
                if(board[y][x].equals("-")){
                    result[5][0] = -1;
                    result[5][1] = -1;
                    break;
                }else if(board[y][x].equals("X")){
                    result[5][0] = y;
                    result[5][1] = x;
                    break;
                }else{
                    result[5][0] = -1;
                    result[5][1] = -1;
                }
                x++;
                y++;
            }
            //down
            for (int i = row + 1; i < board[row].length; i++) {
                if (board[i][column].equals("-")) {
                    result[6][0] = -1;
                    result[6][1] = -1;
                    break;
                } else if (board[i][column].equals("X")) {
                    result[6][0] = i;
                    result[6][1] = column;
                    break;
                } else {
                    result[6][0] = -1;
                    result[6][1] = -1;
                }
            }
            //down-left
            x = column - 1;
            y = row + 1;
            while(x > 0 && y < board.length) { //Down-Left
                if(board[y][x].equals("-")){
                    result[7][0] = -1;
                    result[7][1] = -1;
                    break;
                }else if(board[y][x].equals("X")){
                    result[7][0] = y;
                    result[7][1] = x;
                    break;
                }else{
                    result[7][0] = -1;
                    result[7][1] = -1;
                }
                x--;
                y++;
            }
        }else {
            for (int i = column - 1; i > 0; i--) {
                if (board[row][i].equals("-")) {
                    result[0][0] = -1;
                    result[0][1] = -1;
                    break;
                } else if (board[row][i].equals("O")) {
                    result[0][0] = row;
                    result[0][1] = i;
                    break;
                } else {
                    result[0][0] = -1;
                    result[0][1] = -1;
                }
            }
            //up-left
            int x = column - 1;
            int y = row - 1;
            while (x > 0 && y > 0) {
                if (board[y][x].equals("-")) {
                    result[1][0] = -1;
                    result[1][1] = -1;
                    break;
                } else if (board[y][x].equals("O")) {
                    result[1][0] = y;
                    result[1][1] = x;
                    break;
                } else {
                    result[1][0] = -1;
                    result[1][1] = -1;
                }
                x--;
                y--;
            }
            //up
            for (int i = row - 1; i > 0; i--) {
                if (board[i][column].equals("-")) {
                    result[2][0] = -1;
                    result[2][1] = -1;
                    break;
                } else if (board[i][column].equals("O")) {
                    result[2][0] = i;
                    result[2][1] = column;
                    break;
                } else {
                    result[2][0] = -1;
                    result[2][1] = -1;
                }
            }
            //up-right
            x = column + 1;
            y = row - 1;
            while (x < board.length && y > 0) { //Up-Right;
                if (board[y][x].equals("-")) {
                    result[3][0] = -1;
                    result[3][1] = -1;
                    break;
                } else if (board[y][x].equals("O")) {
                    result[3][0] = y;
                    result[3][1] = x;
                    break;
                } else {
                    result[3][0] = -1;
                    result[3][1] = -1;
                }
                x++;
                y--;
            }
            //right
            for (int i = column + 1; i < board.length; i++) {
                if (board[row][i].equals("-")) {
                    result[4][0] = -1;
                    result[4][1] = -1;
                    break;
                } else if (board[row][i].equals("O")) {
                    result[4][0] = row;
                    result[4][1] = i;
                    break;
                } else {
                    result[4][0] = -1;
                    result[4][1] = -1;
                }
            }
            //down-right
            x = column + 1;
            y = row + 1;
            while (x < board.length && y < board.length) { //Down-Right
                if (board[y][x].equals("-")) {
                    result[5][0] = -1;
                    result[5][1] = -1;
                    break;
                } else if (board[y][x].equals("O")) {
                    result[5][0] = y;
                    result[5][1] = x;
                    break;
                } else {
                    result[5][0] = -1;
                    result[5][1] = -1;
                }
                x++;
                y++;
            }
            //down
            for (int i = row + 1; i < board[row].length; i++) {
                if (board[i][column].equals("-")) {
                    result[6][0] = -1;
                    result[6][1] = -1;
                    break;
                } else if (board[i][column].equals("O")) {
                    result[6][0] = i;
                    result[6][1] = column;
                    break;
                } else {
                    result[6][0] = -1;
                    result[6][1] = -1;
                }
            }
            //down-left
            x = column - 1;
            y = row + 1;
            while (x > 0 && y < board.length) { //Down-Left
                if (board[y][x].equals("-")) {
                    result[7][0] = -1;
                    result[7][1] = -1;
                    break;
                } else if (board[y][x].equals("O")) {
                    result[7][0] = y;
                    result[7][1] = x;
                    break;
                } else {
                    result[7][0] = -1;
                    result[7][1] = -1;
                }
                x--;
                y++;
            }
        }
        return result;
    }

    public int[] getBestMove(boolean player){
        int[] move = new int[2];
        int sum = 0;
        int maxsum = 0;
        ArrayList<int[]> moveList;
        moveList = allLegalMoves(player);
        int[][] legalList;
        int[] coordinates = new int[2];
        for(int i = 0; i < moveList.size(); i++){
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
            if(sum > maxsum){
                maxsum = sum;
                move = moveList.get(i);
            }
            sum = 0;
        }
        return move;
    }
    public String getName(){
        return name;
    }
    public String getPass(){
        return pass;
    }
}
