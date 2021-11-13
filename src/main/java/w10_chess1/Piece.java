package w10_chess1;

public abstract class Piece {
    private int coordinatesX;
    private int coordinatesY;
    private String color;

    /**
     * Constructor 1.
     * @param coordinatesX [1,8].
     * @param coordinatesY [1,8].
     */
    public Piece(int coordinatesX, int coordinatesY) {
        if (1 <= coordinatesX && coordinatesX <= 8) {
            this.coordinatesX = coordinatesX;
        }
        if (1 <= coordinatesY && coordinatesY <= 8) {
            this.coordinatesY = coordinatesY;
        }
        this.color = Game.WHITE;
    }

    /**
     * Constructor 2.
     * @param coordinatesX [1,8].
     * @param coordinatesY [1,8].
     * @param color BLACK or WHITE.
     */
    public Piece(int coordinatesX, int coordinatesY, String color) {
        if (1 <= coordinatesX && coordinatesX <= 8) {
            this.coordinatesX = coordinatesX;
        }
        if (1 <= coordinatesY && coordinatesY <= 8) {
            this.coordinatesY = coordinatesY;
        }
        if (color.equals(Game.BLACK) || color.equals(Game.WHITE)) {
            this.color = color;
        }
    }

    /**
     * get symbol.
     * @return international symbol of piece.
     */
    public abstract String getSymbol();

    /**
     * can move to (x,y)?.
     * @param board .
     * @param x .
     * @param y .
     * @return .
     */
    public abstract boolean canMove(Board board, int x, int y);

    public int getCoordinatesX() {
        return coordinatesX;
    }

    public void setCoordinatesX(int coordinatesX) {
        this.coordinatesX = coordinatesX;
    }

    public int getCoordinatesY() {
        return coordinatesY;
    }

    public void setCoordinatesY(int coordinatesY) {
        this.coordinatesY = coordinatesY;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    /**
     * is this piece at the same position with that piece.
     * @param piece .
     * @return .
     */
    public boolean checkPosition(Piece piece) {
        return this.coordinatesX == piece.getCoordinatesX()
                && this.coordinatesY == piece.getCoordinatesY();
    }
}
