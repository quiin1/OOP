package w10_chess1;

public class Rook extends Piece {
    public Rook(int x, int y) {
        super(x, y);
    }

    public Rook(int coordinatesX, int coordinatesY, String color) {
        super(coordinatesX, coordinatesY, color);
    }

    /**
     * get symbol.
     * @return R - Rook.
     */
    @Override
    public String getSymbol() {
        return "R";
    }

    /**
     * can move to (x,y); (x,y) can be null or not.
     * @param board .
     * @param x .
     * @param y .
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
        // not go straight
        if (x != xold && y != yold) {
            return false;
        }
        // nothing in the way? (co bi chan duong?)
        if (x == xold) {
            for (int ycur = yold + 1; ycur < y; ycur++) {
                if (board.getAt(xold, ycur) != null) {
                    return false;
                }
            }
            for (int ycur = yold - 1; ycur > y; ycur--) {
                if (board.getAt(xold, ycur) != null) {
                    return false;
                }
            }
        }
        if (y == yold) {
            for (int xcur = xold + 1; xcur < x; xcur++) {
                if (board.getAt(xcur, yold) != null) {
                    return false;
                }
            }
            for (int xcur = xold - 1; xcur > x; xcur--) {
                if (board.getAt(xcur, yold) != null) {
                    return false;
                }
            }
        }
        // (x,y) null or (x,y) has different color, go straight and nothing in the way :)
        return true;
    }
}
