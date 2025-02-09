public class WordLocation {
    private String word;
    private int x;
    private int y;
    private Direction direction;

    public WordLocation(String word, int x, int y, Direction direction){   
        this.word = word;
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public String toString(){
        return String.format("%-15s %d\t%d,%d\t%-10s", this.word, this.word.length(), this.x+1, this.y+1, this.direction);
    }

    public String getWord(){
        return this.word;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public Direction getDirection(){
        return this.direction;
    }
}
