/**
 * Created by yao on 5/15/2016.
 */
public class Path {

    public int x1, y1, x2, y2;

    public Direction dir;

    public Path(Direction dir, int height, int width, int hex) {
        this.dir = dir;

        if (hex == 3) {
            x1 = width / 2;
            y1 = 0;
            x2 = width / 2;
            y2 = height;
        } else if (hex == 12) {
            x1 = 0;
            x2 = width;
            y1 = height / 2;
            y2 = height / 2;
        }
//        switch (dir) {
//            case SOUTH:
//                x1 = 0;
//                x2 = height / 2;
//                y1 = width / 2;
//                y2 = width / 2;
//                break;
//            case NORTH:
//                x1 = ;
//                x2 = height / 2;
//                y1 = width / 2;
//                y2 = width / 2;
//
//        }
    }
}
