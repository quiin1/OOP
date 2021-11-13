package w10_chess1;

import java.util.ArrayList;

public class Game {
    private Board board;
    private ArrayList<Move> moveHistory;
    public static final String BLACK = "black";
    public static final String WHITE = "white";

    public Game(Board board) {
        this.board = board;
        moveHistory = new ArrayList<>();
    }

    /**
     * move piece to (x, y).
     *
     * @param piece .
     * @param x     .
     * @param y     .
     */
    public void movePiece(Piece piece, int x, int y) {
        int oldX = piece.getCoordinatesX();
        int oldY = piece.getCoordinatesY();

        if (piece.canMove(board, x, y)) {
            Move move = null;
            piece.setCoordinatesX(x);
            piece.setCoordinatesY(y);
            if (board.getAt(x, y) == null) {
                move = new Move(oldX, x, oldY, y, piece);
            } else {
                move = new Move(oldX, x, oldY, y, piece, board.getAt(x, y));
                board.removeAt(x, y);
            }
            moveHistory.add(move);
        }
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public ArrayList<Move> getMoveHistory() {
        return moveHistory;
    }

    /*
    public static void main(String[] args) {
        Piece R1 = new Rook(2, 5);
        Piece R2 = new Rook(1, 8);
        Piece R3 = new Rook(1, 8);
        Piece R = new Rook(2, 6, BLACK);
        Board board = new Board();
        board.addPiece(R1);
        System.out.println(board.getPieces().size());
        board.addPiece(R2);
        System.out.println(board.getPieces().size());
        board.addPiece(R3);
        System.out.println(board.getPieces().size());
        board.addPiece(R);
        System.out.println(R1.canMove(board, 2, 6));

        Game game = new Game(board);
        game.movePiece(R1, 2, 6);
    }
    */
}
