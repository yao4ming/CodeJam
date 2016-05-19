import javax.swing.*;
import java.io.*;
import java.util.*;

public class AlwaysTurnLeft {

    static Maze maze;

    static int row, col, maxRow, maxCol, minRow, minCol;

    static Map<String, Integer> cells = new HashMap<>();

    static Direction dir;

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

        //first move is always W
        char prevMove = 'W';
        for (int i = 1; i < moves.length(); i++) {
            char move = moves.charAt(i);
            String pos = row + "&" + col;
            switch (move) {
                case 'W': {
                    //store cell info (able to move forward, backwards, rightwards) using bitwise OR
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
                    //store cell info (able to move backwards and rightwards) using bitwise OR
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

        //print maze from top-left
        for (int i = maxRow; i >= minRow; i--) {
            for (int j = minCol; j <= maxCol; j++) {
                String key = i + "&" + j;
                //only room without key is f
                if (!cells.containsKey(key)) writer.print("f");
                else writer.print(Integer.toHexString(cells.get(key)));
            }
            writer.println();
        }
    }

    public static void drawMaze() {
        int height = maxRow-minRow+1;
        int width = maxCol-minCol+1;
        maze = new Maze(height, width);
        JFrame frame = new JFrame("AlwaysTurnLeft");
        frame.add(maze);
        frame.setSize(width*100, height*100);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);

        for (int i = maxRow; i >= minRow; i--) {
            for (int j = minCol; j <= maxCol; j++) {
                String key = i + "&" + j;
                if (cells.get(key) != null) {
                    maze.drawCell(-i, j-minCol, cells.get(key));
                } else {
                    maze.drawCell(-i, j-minCol, 15);
                }
            }
        }

        try {
            Thread.sleep(3000);
            frame.dispose();
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public static void main(String[] args) {

        File file = new File("input.in");
        try (Scanner in = new Scanner(file); PrintWriter writer = new PrintWriter("output.out")) {
            int testCases = Integer.parseInt(in.nextLine());

            for (int i = 0; i < testCases; i++) {

                String line = in.nextLine();

                String fromStart = line.split(" ")[0];
                String fromFinish = line.split(" ")[1];

                //start at 0,0 facing southWall
                row = 0; col = 0; maxRow = 0; maxCol = 0; minRow = 0; minCol = 0;
                dir = Direction.SOUTH;
                traverseMaze(fromStart);

                //turn 180 degrees and move one space forward
                dir = turnRight(turnRight(dir));
                move(dir);
                traverseMaze(fromFinish);

                printMaze(writer, i+1);
                drawMaze();

                cells.clear();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
