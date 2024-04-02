package main;

public class Othello {
    private String name;
    private String pass;
    private String[][] board;
    private int[] grid;

    public Othello(String name, String pass){
        this.name = name;
        this.pass = pass;
        board = new String[9][9];
        for(int i = 1; i < board.length; i++) {
            for(int j = 1; j < board[i].length; j++) {
                board[i][j] = "-";
            }
        }
        grid = new int[]{1,2,3,4,5,6,7,8};
        for(int i = 1; i < board.length;i++){
            board[i][0] = Integer.toString(grid[i-1]);
        }
        for(int i = 1; i <board[0].length;i++){
            board[0][i] = Integer.toString(grid[i-1]);
        }
        board[0][0] = " ";
    }
    public void printBoard(){
        for(int i = 0; i < board.length; i++){
            System.out.println();
            for(int j = 0; j < board[i].length; j++){
                System.out.print(board[i][j] + " ");
            }
        }
    }

    public void placePiece(boolean player, int x, int y){
        if(player){  //Player
            board[x][y] = "X";
        }else{
            board[x][y] = "O";
        }
        printBoard();
    }


    public String getName(){
        return name;
    }
    public String getPass(){
        return pass;
    }
}