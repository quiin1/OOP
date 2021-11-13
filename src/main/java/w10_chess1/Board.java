package w10_chess1;

import java.util.ArrayList;

public class Board {
    public static final int WIDTH = 8;
    public static final int HEIGHT = 8;
    private ArrayList<Piece> pieces;

    public Board() {
        pieces = new ArrayList<>();
    }

    /**
     * add piece into board if valid and not duplicate.
     *
     * @param piece .
     */
    public void addPiece(Piece piece) {
        for (Piece p : pieces) {
            if (piece.checkPosition(p) && piece.getColor() == p.getColor()) {
                return;
            }
        }
        pieces.add(piece);
    }

    /**
     * is position valid ?.
     *
     * @param x .
     * @param y .
     * @return .
     */
    public boolean validate(int x, int y) {
        return 1 <= x && x <= 8 && 1 <= y && y <= 8;
    }

    /**
     * remove piece at (x, y).
     *
     * @param x .
     * @param y .
     */
    public void removeAt(int x, int y) {
        for (int i = 0; i < pieces.size(); i++) {
            if (pieces.get(i).getCoordinatesX() == x
                    && pieces.get(i).getCoordinatesY() == y) {
                pieces.remove(i);
            }
        }
    }

    /**
     * get piece at (x, y).
     *
     * @param x .
     * @param y .
     * @return .
     */
    public Piece getAt(int x, int y) {
        for (Piece piece : pieces) {
            if (piece.getCoordinatesX() == x && piece.getCoordinatesY() == y) {
                return piece;
            }
        }
        return null;
    }

    public ArrayList<Piece> getPieces() {
        return pieces;
    }

    public void setPieces(ArrayList<Piece> pieces) {
        this.pieces = pieces;
    }
}
