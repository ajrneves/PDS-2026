public enum Direction {
    Up,
    UpRight,
    Right,
    DownRight,
    Down,
    DownLeft,
    Left,
    UpLeft;

    public int[] getCoords() { // x, y
        switch (this) {
            case Up:
                return new int[]{-1, 0};
            case UpRight:
                return new int[]{-1, 1};
            case Right:
                return new int[]{0, 1};
            case DownRight:
                return new int[]{1, 1};
            case Down:
                return new int[]{1, 0};
            case DownLeft:
                return new int[]{1, -1};
            case Left:
                return new int[]{0, -1};
            case UpLeft:
                return new int[]{-1, -1};
            default:
                throw new IllegalArgumentException("Unknown direction: " + this);
        }
    }
}
