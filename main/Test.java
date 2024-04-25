package main;
import java.util.ArrayList;
public class Test {
    public static void main(String[] args) {
        Othello test = new Othello("abc", "def");

        String[] str = test.printBoard();
        for(int i = 0; i < str.length; i++){
            System.out.println(str[i]);
        }
        test.placePiecePlayer(3,3);
        test.placePiecePlayer(3,5);
        test.placePiecePlayer(4,3);
        test.placePiecePlayer(4,4);
        test.placePiecePlayer(4,5);
        test.placePiecePlayer(5,3);
        test.placePiecePlayer(5,5);
        test.placePiece(5,4);
        test.placePiece(6,4);
        test.placePiece(3,4);
        test.placePiece(2,4);
        str = test.printBoard();
        for(int i = 0; i < str.length; i++){
            System.out.println(str[i]);
        }
        int[][] arr = test.getLegal(false,1,4);
        System.out.println("Legal moves from 1,4");
        for(int i = 0; i < arr.length; i++){
            System.out.println("("+arr[i][0]+","+arr[i][1]+")");

        }
        test.placePieceCPU();
        str = test.printBoard();
        for(int i = 0; i < str.length; i++){
            System.out.println(str[i]);
        }
        System.out.println(test.checkWin());
    }
}
