package main;
import java.util.ArrayList;
public class Test {
    public static void main(String[] args) {
        Othello test = new Othello("abc", "def");

        String[] str = test.printBoard();
        for(int i = 0; i < str.length; i++){
            System.out.println(str[i]);
        }
        test.placePiecePlayer(3,5);
        test.placePiecePlayer(4,5);
        test.placePiecePlayer(5,5);
        test.placePiecePlayer(5,4);
        str = test.printBoard();
        for(int i = 0; i < str.length; i++){
            System.out.println(str[i]);
        }
        ArrayList<int[]> moveList = test.allLegalMoves(true);
        int[] showyamoves = new int[2];
        String movesya = "";
        for(int i = 0; i < moveList.size(); i++)
        {
            showyamoves = moveList.get(i);
            movesya += "["+showyamoves[0]+ "," + showyamoves[1]+"]"+" , ";
        }
        System.out.println(movesya);
        int[][] arr = test.getLegal(true,3,6);
        System.out.println("Legal moves from 1,4");
        for(int i = 0; i < arr.length; i++){
            System.out.println("("+arr[i][0]+","+arr[i][1]+")");

        }

        System.out.println(test.placePieceCPU());

        str = test.printBoard();
        for(int i = 0; i < str.length; i++){
            System.out.println(str[i]);
        }
        System.out.println(test.checkWin());

    }
}
