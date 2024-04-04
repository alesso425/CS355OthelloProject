package main;
import java.util.ArrayList;

public class Othello {
    private String name;
    private String pass;
    private String[][] board;
    private int[] grid;

    public Othello(String name, String pass) {
        this.name = name;
        this.pass = pass;
        board = new String[9][9];
        for (int i = 1; i < board.length; i++) {
            for (int j = 1; j < board[i].length; j++) {
                board[i][j] = "-";
            }
        }
        grid = new int[]{1, 2, 3, 4, 5, 6, 7, 8};
        for (int i = 1; i < board.length; i++) {
            board[i][0] = Integer.toString(grid[i - 1]);
        }
        for (int i = 1; i < board[0].length; i++) {
            board[0][i] = Integer.toString(grid[i - 1]);
        }
        board[0][0] = " ";
    }

    public void printBoard() {
        for (int i = 0; i < board.length; i++) {
            System.out.println();
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j] + " ");
            }
        }
    }

    public void placePiece(boolean player, int row, int column) {
        if (player) {  //Player
            /**
            if (x != 0 && y != 0 && x != 1 && y != 1 && x != 9 && y != 9) {
                boolean oppsq;
                boolean plsq;
                for(int i = 0; i < board.length; i++){

                }
         }
             **/
            board[row][column] = "X";

        } else {
            board[row][column] = "O";
        }
    printBoard();
    }

    public void setHoriz(boolean player, int row, int column){
        if(player) {
            for (int i = column+1; i < board.length; i++) {
                if (board[row][i].equals("-")) {
                    break;
                } else if (board[row][i].equals("X")) {
                    for (int j = column + 1; j < i; j++) {
                        board[row][j] = "X";
                    }
                }else if(i == 8){
                    break;
                }
            }
            for (int i = column-1; i > 0; i--){
                if (board[row][i].equals("-")) {
                    break;
                } else if (board[row][i].equals("X")) {

                    for (int j = column - 1; j > i; j--) {
                        board[row][j] = "X";
                    }
                }else if(i == 1){
                    break;
                }
            }
        }else{
            for (int i = column+1; i < board.length; i++) {
                if (board[row][i].equals("-")) {
                    break;
                } else if (board[row][i].equals("O")) {
                    for (int j = column + 1; j < i; j++) {
                        board[row][j] = "O";
                    }
                }else if(i == 8){
                    break;
                }
            }
            for (int i = column-1; i > 0; i--){
                if (board[row][i].equals("-")) {
                    break;
                } else if (board[row][i].equals("O")) {

                    for (int j = column - 1; j > i; j--) {
                        board[row][j] = "O";
                    }
                }else if(i == 1){
                    break;
                }
            }
        }
        printBoard();
    }
    public void setVert(boolean player, int row, int column){
        if(player) {
            for (int i = row+1; i < board.length; i++) {
                if (board[i][column].equals("-")) {
                    break;
                } else if (board[i][column].equals("X")) {
                    for (int j = row + 1; j < i; j++) {
                        board[j][column] = "X";
                    }
                }else if(i == 8){
                    break;
                }
            }
            for (int i = row-1; i > 0; i--){
                if (board[i][column].equals("-")) {
                    break;
                } else if (board[i][column].equals("X")) {
                    for (int j = row - 1; j > i; j--) {
                        board[j][column] = "X";
                    }
                }else if(i == 1){
                    break;
                }
            }
        }else{
            for (int i = row+1; i < board.length; i++) {
                if (board[i][column].equals("-")) {
                    break;
                } else if (board[i][column].equals("O")) {
                    for (int j = row + 1; j < i; j++) {
                        board[j][column] = "O";
                    }
                }else if(i == 8){
                    break;
                }
            }
            for (int i = column-1; i > 0; i--){
                if (board[i][column].equals("-")) {
                    break;
                } else if (board[i][column].equals("O")) {

                    for (int j = row - 1; j > i; j--) {
                        board[i][column] = "O";
                    }
                }else if(i == 1){
                    break;
                }
            }
        }
        printBoard();
    }
    public void setDiag2(boolean player, int row, int column){
        int x = column + 1;
        int y = row - 1;
        int x2 = column+1;
        int y2 = row-1;
        while(x < board.length && y > 0) { //Up-Right;
            if(board[y][x].equals("-")){
                break;
            }else if (board[y][x].equals("X")){
                while(y2 > y && x2 < x){
                    board[y2][x2] = "X";
                    y2--;
                    x2++;
                }
            }
            x++;
            y--;
        }
        printBoard();
    }
    /**
    public void setDiag(boolean player, int row, int column){
        if(player) {
            for (int i = row-1; i > 0; i--) {        //Up-Right
                for (int j = column + 1; j < board[row].length; j++) {
                    if (board[i][j].equals("-")) {
                        break;
                    } else if (board[i][j].equals("X")) {
                        for (int k = row - 1; k > i; k--) {
                            for (int l = column + 1; l < j; l++) {
                                System.out.println("Up-Right");
                                board[k][l] = "X";
                            }
                        }
                    }
                }
            }
            for (int i = row-1; i > 0; i--) {        //Down-Right
                for (int j = column + 1; j < board.length; j++) {
                    if (board[i][j].equals("-")) {
                        break;
                    } else if (board[i][j].equals("X")) {
                        for (int k = row - 1; k > i; k--) {
                            for (int l = column + 1; l < j; l++) {
                                System.out.println("Down-Right");
                                board[k][l] = "X";
                            }
                        }
                    }
                }
            }
            for (int i = row-1; i > 0; i--) {        //Up-Left
                for (int j = column + 1; j < board[row].length; j++) {
                    if (board[i][j].equals("-")) {
                        break;
                    } else if (board[i][j].equals("X")) {
                        for (int k = row + 1; j > i; k++) {
                            for(int l = column + 1; l < j; l++) {
                                System.out.println("Up-Left");
                                board[k][l] ="X";
                            }
                        }
                    } else if (i == 8 || j == 8) {
                        break;
                    }
                }
            }
            for (int i = row + 1; i < board.length; i++) {        //Down-Left
                for (int j = column - 1; j > 0; j--) {
                    if (board[i][j].equals("-")) {
                        break;
                    } else if (board[i][j].equals("X")) {
                        for (int k = row + 1; k < i; k++) {

                            for(int l = column - 1; l > j; l--) {
                                System.out.println("Down-Left");
                                board[k][l] ="X";
                                System.out.println("(" + k + "," + l + ")");
                            }
                        }
                    } else if (i == 8 || j == 8) {
                        break;
                    }
                }
            }
        }else{
            for (int i = row+1; i < board.length; i++) {        //Up-Right
                for (int j = column + 1; j < board[row].length; j++) {
                    if (board[i][j].equals("-")) {
                        break;
                    } else if (board[i][j].equals("O")) {
                        for (int k = row + 1; j < i; j++) {
                            for(int l = column + 1; l < j; k++) {
                                board[k][l] ="O";
                            }
                        }
                    } else if (i == 8 || j == 8) {
                        break;
                    }
                }
            }
            for (int i = row+1; i < board.length; i++) {        //Down-Right
                for (int j = column - 1; j > 0; j--) {
                    if (board[i][j].equals("-")) {
                        break;
                    } else if (board[i][j].equals("O")) {
                        for (int k = row + 1; j < i; k++) {
                            for(int l = column - 1; l > j; l--) {
                                board[k][l] ="O";
                            }
                        }
                    } else if (i == 8 || j == 8) {
                        break;
                    }
                }
            }
            for (int i = row-1; i > 0; i--) {        //Up-Left
                for (int j = column + 1; j < board[row].length; j++) {
                    if (board[i][j].equals("-")) {
                        break;
                    } else if (board[i][j].equals("O")) {
                        for (int k = row + 1; j > i; k++) {
                            for(int l = column + 1; l < j; l++) {
                                board[k][l] ="O";
                            }
                        }
                    } else if (i == 8 || j == 8) {
                        break;
                    }
                }
            }
            for (int i = row-1; i > 0; i--) {        //Down-Left
                for (int j = column - 1; j > 0; j--) {
                    if (board[i][j].equals("-")) {
                        break;
                    } else if (board[i][j].equals("O")) {
                        for (int k = row - 1; j > i; k--) {
                            for(int l = column - 1; l > j; l--) {
                                board[k][l] ="O";
                            }
                        }
                    } else if (i == 8 || j == 8) {
                        break;
                    }
                }
            }
        }
        printBoard();
    }
    /**
    public ArrayList<int[]> allLegalMoves(boolean player){

    }
    **/
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
            for(int i = column - 1; i > 0; i--){
                for(int j = row + 1; j < board[row].length; j++){
                    if(board[i][j].equals("-")){
                        result[1][0] = -1;
                        result[1][1] = -1;
                        break;
                    }else if(board[i][j].equals("X")){
                        result[1][0] = i;
                        result[1][1] = j;
                        break;
                    }else {
                        result[1][0] = -1;
                        result[1][1] = -1;
                    }
                }
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
            for(int i = column + 1; i < board.length; i++){
                for(int j = row + 1; j < board[row].length; j++){
                    if(board[i][j].equals("-")){
                        result[3][0] = -1;
                        result[3][1] = -1;
                        break;
                    }else if(board[i][j].equals("X")){
                        result[3][0] = i;
                        result[3][1] = j;
                        break;
                    }else {
                        result[3][0] = -1;
                        result[3][1] = -1;
                    }
                }
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
            for(int i = column + 1; i < board.length; i++){
                for(int j = row - 1; j > 0; j--){
                    if(board[i][j].equals("-")){
                        result[5][0] = -1;
                        result[5][1] = -1;
                        break;
                    }else if(board[i][j].equals("X")){
                        result[5][0] = i;
                        result[5][1] = j;
                        break;
                    }else {
                        result[5][0] = -1;
                        result[5][1] = -1;
                    }
                }
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
            for(int i = column - 1; i > 0; i--){
                for(int j = row + 1; j < board[row].length; j++){
                    if(board[i][j].equals("-")){
                        result[7][0] = -1;
                        result[7][1] = -1;
                        break;
                    }else if(board[i][j].equals("X")){
                        result[7][0] = i;
                        result[7][1] = j;
                        break;
                    }else {
                        result[7][0] = -1;
                        result[7][1] = -1;
                    }
                }
            }
        }
        return result;
    }
    /**
    public int[] getBestMove(boolean player){
        int[] move = new int[2];
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[i].length; j++){
                if(player){
                    if(i == 0){
                        if()
                    }
                }
            }
        }


        return move;
    }
    **/
    public String getName(){
        return name;
    }
    public String getPass(){
        return pass;
    }
}