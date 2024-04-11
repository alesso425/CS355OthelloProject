package main;
import java.util.ArrayList;
import java.lang.Math;

public class Othello {      //Test.
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

    public void placePiecePlayer(int row, int column) {
        board[row][column] = "X";
        setHoriz(true, row, column);
        setVert(true, row, column);
        setDiag(true, row, column);
        board[row][column] = "O";
    printBoard();
    }
    public void placePieceCPU(){
        int[] bestMove = getBestMove(false);
        int row = bestMove[0];
        int column = bestMove[1];
        setHoriz(false, row, column);
        setVert(false, row, column);
        setDiag(false, row, column);
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
                    break;
                }
            }
        }
        printBoard();
    }
    public void setDiag(boolean player, int row, int column){
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
        printBoard();
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
                    if (board[i][j].equals("X") && isLegal(true, i, j))
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
                    if (board[i][j].equals("O") && isLegal(false, i, j))
                    {
                        x = new int[]{i,};
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
           if(arr[i][0] != -1){
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
