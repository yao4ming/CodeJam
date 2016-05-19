import javax.swing.*;
import java.awt.*;

public class Cell extends JPanel {

    public boolean westWall, southWall, northWall, eastWall;
    public int width = 100;
    public int height = 100;
    public int row, col;
    public String hex = "0";

    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        //draw hex value for cell
        g.drawString(hex, width/2, height/2);

        //draw walls for cell
        if(northWall)
            g.drawLine(0, 0, width, 0);

        if(southWall)
            g.drawLine(0, height, width, height);

        if(westWall)
            g.drawLine(0, 0, 0, height);

        if(eastWall)
            g.drawLine(width, 0, width, height);

    }

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
    }
}
