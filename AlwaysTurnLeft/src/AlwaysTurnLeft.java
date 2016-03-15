import java.io.*;
import java.util.*;

/**
 * Created by yaoming on 3/8/16.
 */
public class AlwaysTurnLeft {

    static int row, col, maxRow, maxCol, minRow, minCol;

    static SortedMap<String, Integer> cells = new TreeMap<>((Comparator<String>) (pos1, pos2) -> {
        int pos1Row = Integer.parseInt(pos1.split("&")[0]);
        int pos1Col = Integer.parseInt(pos1.split("&")[1]);
        int pos2Row = Integer.parseInt(pos2.split("&")[0]);
        int pos2Col = Integer.parseInt(pos2.split("&")[1]);

        if (pos1Row < pos2Row) {
            return 1;
        } else if (pos1Row == pos2Row){
            if (pos1Col > pos2Col) {
                return 1;
            }
            else if (pos1Col == pos2Col) {
                return 0;
            }
            else {
                return -1;
            }
        } else {
            return -1;
        }
    });

    static Direction dir;

    public enum Direction {
        NORTH(1), SOUTH(2), WEST(4), EAST(8);

        Direction(int val) {
            this.val = val;
        }

        int val;
        public int getVal() {return val;}

    }

    public static void storeCellInfo(String pos, int hex) {
        if (cells.containsKey(pos)) {
            hex = cells.get(pos) & hex;
            cells.put(pos, hex);
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
            String pos = row + "&" + col;
            switch (move) {
                case 'W': {
                    //store cell info (able to move forward, backwards, rightwards)
                    if (prevMove != 'L') {
                        int hex = dir.getVal() | turnRight(turnRight(dir)).getVal() | turnRight(dir).getVal();
                        storeCellInfo(pos, hex);
                    }

                    // keep track of grid size
                    maxRow = (row > maxRow ? row : maxRow);
                    minRow = (row < minRow ? row : minRow);
                    maxCol = (col > maxCol ? col : maxCol);
                    minCol = (col < minCol ? col : minCol);

                    move(dir);
                    break;
                }
                case 'L': {
                    dir = turnLeft(dir);
                    break;
                }
                case 'R': {
                    //store cell info (able to move backwards and rightwards)
                    int hex = turnRight(turnRight(dir)).getVal() | turnRight(dir).getVal();
                    storeCellInfo(pos, hex);
                    dir = turnRight(dir);
                    break;
                }
            }
            prevMove = move;
        }
    }

    public static void printMaze(PrintWriter writer, int caseNum) {
        writer.println("Case #" + caseNum + ":");
        for (int i = maxRow; i >= minRow; i--) {
            for (int j = minCol; j <= maxCol; j++) {
                String key = i + "&" + j;
                if (!cells.containsKey(key)) {
                    writer.print("f");
                } else {
                    writer.print(Integer.toHexString(cells.get(key)));
                }

            }
            writer.println();
        }
    }

    public static void main(String[] args) {

        File file = new File("B-small-practice.in");
        try (Scanner in = new Scanner(file); PrintWriter writer = new PrintWriter("B-small-practice.out")) {
            int testCases = Integer.parseInt(in.nextLine());

            for (int i = 0; i < testCases; i++) {

                String line = in.nextLine();

                String fromStart = line.split(" ")[0];
                String fromFinish = line.split(" ")[1];

                //start at 0,0 facing south
                row = 0; col = 0; maxRow = 0; maxCol = 0; minRow = 0; minCol = 0;
                dir = Direction.SOUTH;
                traverseMaze(fromStart);

                //turn 180 degrees and move one space forward
                dir = turnRight(turnRight(dir));
                move(dir);
                traverseMaze(fromFinish);

                printMaze(writer, i+1);
                cells.clear();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
