package w11_chess2;

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
            Move move;
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

    // /*
    public static void main(String[] args) {
        Board board = new Board();
        Game game = new Game(board);

        Bishop B1 = new Bishop(2, 2);
        Bishop B2 = new Bishop(7, 7, BLACK);
        board.addPiece(B1);
        board.addPiece(B2);
        System.out.println(B1.canMove(board, 7, 7));
        game.movePiece(B1, 4, 5);
        ArrayList<Move> his = game.getMoveHistory();
        for (int i = 0; i < his.size(); i++) {
            System.out.println(his.get(i));
        }
    }
    // */
}
