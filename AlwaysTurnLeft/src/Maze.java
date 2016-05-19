import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Maze extends JPanel {

    int H, W;
    Cell[][] cells;

    public void drawCell(int row, int col, int hex) {

        Cell cell = cells[row][col];

        //set cell hex value
        cell.hex = Integer.toHexString(hex);

        //set cell walls
        switch (hex) {
            case 1:
                cell.southWall = true;
                cell.westWall = true;
                cell.eastWall = true;
                break;
            case 2:
                cell.northWall = true;
                cell.westWall = true;
                cell.eastWall = true;
                break;
            case 3:
                cell.westWall = true;
                cell.eastWall = true;
                break;
            case 4:
                cell.southWall = true;
                cell.northWall = true;
                cell.eastWall = true;
                break;
            case 5:
                cell.southWall = true;
                cell.eastWall = true;
                break;
            case 6:
                cell.northWall = true;
                cell.eastWall = true;
                break;
            case 7:
                cell.eastWall = true;
                break;
            case 8:
                cell.southWall = true;
                cell.westWall = true;
                cell.northWall = true;
                break;
            case 9:
                cell.southWall = true;
                cell.westWall = true;
                break;
            case 10:
                cell.northWall = true;
                cell.westWall = true;
                break;
            case 11:
                cell.westWall = true;
                break;
            case 12:
                cell.southWall = true;
                cell.northWall = true;
                break;
            case 13:
                cell.southWall = true;
                break;
            case 14:
                cell.northWall = true;
                break;
            case 15:
                break;
        }

        try {
            Thread.sleep(1000);
            cell.repaint();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public Maze(int H, int W) {

        this.H = H;
        this.W = W;
        cells = new Cell[H][W];
        setLayout(new GridLayout(H,W));

        //init Maze cells
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                cells[i][j] = new Cell(i, j);
                this.add(cells[i][j]);
            }
        }
    }
}
