package main;

public class Test {
    public static void main(String[] args){
        Othello test = new Othello("abc", "def");
        test.placePiece(true, 4, 4);
        test.placePiece(true, 4, 1);
        test.placePiece(true, 1, 4);
        test.placePiece(true, 4, 8);
        test.placePiece(true, 8, 4);
        test.placePiece(false, 2, 4);
        test.placePiece(false, 3, 4);
        test.placePiece(false, 5, 4);
        test.placePiece(false, 6, 4);
        test.placePiece(false, 7, 4);
        test.placePiece(false,4, 2);
        test.placePiece(false, 4, 3);
        test.placePiece(false, 4, 5);
        test.placePiece(false, 4, 6);
        test.placePiece(false,4, 7);
        test.setHoriz(true, 4, 4);
        test.setVert(true, 4, 4);

    }
}
