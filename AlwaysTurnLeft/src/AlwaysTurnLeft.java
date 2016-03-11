import java.io.*;
import java.util.*;

/**
 * Created by yaoming on 3/8/16.
 */
public class AlwaysTurnLeft {

    static int row, col, maxRow, maxCol, minRow, minCol;

    static SortedMap<String, Integer> cells = new TreeMap<>(new Comparator<String>() {
        @Override
        public int compare(String s1, String s2) {
            char row = s1.charAt(0);
            char col = s1.charAt(1);
            char row2 = s1.charAt(0);
            char col2 = s1.charAt(1);

            if (row > row2) {
                return 1;
            } else if (row == row2){
                if (col < col2) {
                    return 1;
                }
                else if (col == col2) {
                    return 0;
                }
                else {
                    return -1;
                }
            } else {
                return -1;
            }

        }
    });

    static Direction dir = Direction.SOUTH;

    public enum Direction {
        NORTH(1), SOUTH(2), WEST(4), EAST(8);

        int val;
        Direction(int val) {
            this.val = val;
        }

        public int getVal() {return val;}

    }

    public static class Cell {
        public int row;
        public int col;
        public int val;

        public Cell(int row, int col, int val) {
            this.row = row;
            this.col = col;
            this.val = val;
        }
    }

    public static void storeCellInfo(String pos, int hex) {
        if (cells.containsKey(pos)) {
            hex = cells.get(pos) & hex;
            cells.put(pos, hex);
            System.out.println("new hex " + hex);
        } else {
            cells.put(pos, hex);
        }
    }

    public static Direction turnRight(Direction direction) {

        switch (direction) {
            case NORTH:
                return Direction.EAST;
            case SOUTH:
                return Direction.WEST;
            case WEST:
                return Direction.NORTH;
            case EAST:
                return Direction.SOUTH;
            default:
                System.out.println("WRONG DIRECTION");
                return null;
        }
    }

    public static Direction turnLeft(Direction direction) {
        return turnRight(turnRight(turnRight(direction)));
    }

    public static void move(Direction dir) {

        //move in current direction
        if (dir == Direction.SOUTH) row--;
        if (dir == Direction.WEST) col--;
        if (dir == Direction.NORTH) row++;
        if (dir == Direction.EAST) col++;
    }

    public static void traverseMaze(String moves) {

        char prevMove = 'W';

        for (int i = 1; i < moves.length(); i++) {

            char move = moves.charAt(i);
            switch (move) {
                case 'W': {
                    //store cell info (unless prevMove was left, wall on left)
                    if (prevMove != 'L') {
                        int hex = 15 - (turnLeft(dir).getVal());
                        String pos = "" + row + col;
                        System.out.println(pos + " " + hex);

                        storeCellInfo(pos, hex);
                    }

                    move(dir);

                    //update maze info
                    maxRow = (maxRow > row ? maxRow : row);
                    maxCol = (maxCol > col ? maxCol : col);
                    minRow = (minRow < row ? minRow : row);
                    minCol = (minCol < col ? minCol : col);
                    break;
                }
                case 'L': {
                    dir = turnLeft(dir);
                    break;
                }
                case 'R': {
                    //store cell info (wall in front and left)
                    int hex = 15 - (dir.getVal() | turnLeft(dir).getVal());
                    String pos = "" + row + col;
                    System.out.println(pos + " " + hex);
                    storeCellInfo(pos, hex);

                    dir = turnRight(dir);
                    break;
                }
            }
            prevMove = move;
        }
    }

    public static void main(String[] args) {

        File file = new File("input.txt");
        try (Scanner in = new Scanner(file)) {
            int testCases = Integer.parseInt(in.nextLine());

            for (int i = 0; i < testCases; i++) {

                String line = in.nextLine();

                String fromStart = line.split(" ")[0];
                String fromFinish = line.split(" ")[1];

                row = 0; col = 0; maxRow = 0; maxCol = 0; minRow = 0; minCol = 0;

                traverseMaze(fromStart);

                //turn 180 degrees and move one space forward
                dir = turnRight(turnRight(dir));
                move(dir);

                traverseMaze(fromFinish);

                int numRows = maxRow-minRow;
                int numCols = maxCol-minCol;
                int[][] maze = new int[numRows][numCols];

                Set<String> keys = cells.keySet();
                for (String key : keys) {
                    System.out.println(key + " ==> "+ cells.get(key));
                }

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
