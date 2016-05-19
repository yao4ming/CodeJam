public enum Direction {
    NORTH(1), SOUTH(2), WEST(4), EAST(8);

    Direction(int val) {
        this.val = val;
    }

    int val;
    public int getVal() {return val;}
}