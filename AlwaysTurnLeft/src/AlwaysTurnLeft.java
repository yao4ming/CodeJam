import java.io.*;
import java.util.*;

/**
 * Created by yaoming on 3/8/16.
 */
public class AlwaysTurnLeft {

    static int row, col;

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

    static Direction dir = Direction.SOUTH;

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
            //System.out.println("new hex " + hex);
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
                        String pos = row + "&" + col;
                        //System.out.println(pos + " " + hex);
                        storeCellInfo(pos, hex);
                    }

                    move(dir);
                    break;
                }
                case 'L': {
                    dir = turnLeft(dir);
                    break;
                }
                case 'R': {
                    //store cell info (wall in front and left)
                    int hex = 15 - (dir.getVal() | turnLeft(dir).getVal());
                    String pos = row + "&" + col;
                    //System.out.println(pos + " " + hex);
                    storeCellInfo(pos, hex);

                    dir = turnRight(dir);
                    break;
                }
            }
            prevMove = move;
        }
    }

    public static void printMaze(PrintWriter writer, int caseNum) {
        if (caseNum > 1) writer.println();
        writer.println("Case #" + caseNum + ":");
        String prevRow = "";
        for (Map.Entry<String, Integer> cell : cells.entrySet()) {

            //print new row
            if (!prevRow.equals("") && !cell.getKey().split("&")[0].equals(prevRow)) {
                writer.println();
            }

            prevRow = cell.getKey().split("&")[0];

            String value = Integer.toHexString(cell.getValue());
            writer.print(value);
        }
    }

    public static void main(String[] args) {

        File file = new File("B-small-practice.in");
        try (Scanner in = new Scanner(file); PrintWriter writer = new PrintWriter("output.txt")) {
            int testCases = Integer.parseInt(in.nextLine());

            for (int i = 0; i < testCases; i++) {

                String line = in.nextLine();

                String fromStart = line.split(" ")[0];
                String fromFinish = line.split(" ")[1];

                row = 0; col = 0;

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
