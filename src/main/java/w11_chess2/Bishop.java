package w11_chess2;

public class Bishop extends Piece {
    public Bishop(int x, int y) {
        super(x, y);
    }

    public Bishop(int coordinatesX, int coordinatesY, String color) {
        super(coordinatesX, coordinatesY, color);
    }

    /**
     * get symbol.
     *
     * @return B - Bishop - quan tuong.
     */
    @Override
    public String getSymbol() {
        return "B";
    }

    /**
     * can move to (x,y)?.
     *
     * @param board .
     * @param x     .
     * @param y     .
     * @return .
     */
    @Override
    public boolean canMove(Board board, int x, int y) {
        // (x,y) validate?
        if (!board.validate(x, y)) {
            return false;
        }

        // stand on
        int xold = this.getCoordinatesX();
        int yold = this.getCoordinatesY();
        if (x == xold && y == yold) {
            return false;
        }

        // (x,y) not null and has the same color -> can't move
        if (board.getAt(x, y) != null
                && board.getAt(x, y).getColor().equals(this.getColor())) {
            return false;
        }
        // (x,y) null or has different color
        // is diagonal line? (co di cheo?)
        if (!isDiagonalLine(xold, yold, x, y)) {
            return false;
        }
        // nothing in the way? (co bi chan duong?)
        int nextX = x - xold > 0 ? 1 : -1;
        int nextY = y - yold > 0 ? 1 : -1;
        for (int i = xold + nextX, j = yold + nextY; i != x && j != y; i += nextX, j += nextY) {
            if (board.getAt(i, j) != null) {
                return false;
            }
        }
        // (x,y) null or (x,y) has different color, go diagonally and nothing in the way :)
        return true;
    }

    private boolean isDiagonalLine(int fromX, int fromY, int toX, int toY) {
        double vectoX = toX - fromX;
        double vectoY = toY - fromY;
        double min = Math.min(vectoX, vectoY);
        vectoX /= min;
        vectoY /= min;
        return vectoX * vectoY == 1 || vectoX * vectoY == -1;
    }
}
