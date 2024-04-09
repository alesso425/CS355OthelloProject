package main;
import java.util.ArrayList;
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
        test.placePiece(true, 5, 2);
        test.placePiece(true, 2, 5);
        int[][] arr = test.getLegal(true, 4, 4);
        for(int i = 0; i < arr.length; i++){
            System.out.println();
            for(int j = 0; j < arr[i].length; j++){
                System.out.print(arr[i][j]);
            }
        }
        ArrayList<int[]> list = new ArrayList<int[]>();
        list = test.allLegalMoves(true);
        int[] x = new int[2];
        for(int i = 0; i < list.size(); i++) {
            x = list.get(i);
            System.out.println();
            System.out.print(x[0]);
            System.out.print(" " + x[1]);
        }

    }
}
