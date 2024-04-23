package main;
import java.util.ArrayList;
public class Test {
    public static void main(String[] args) {
        Othello test = new Othello("abc", "def");
        test.placePiecePlayer(4,4);
        test.placePiecePlayer(4,5);
        test.placePiecePlayer(4,6);
        test.placePiece(5, 5);
        test.placePiecePlayer(5,4);
        test.placePiecePlayer(3,5);
        test.placePieceCPU();
        String str = test.printBoard();
        System.out.println(str);
    }
}
