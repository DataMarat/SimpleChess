package chess;

public class Horse extends ChessPiece {

    public Horse(String color) {
        super(color);
    }

    @Override
    public String getColor() {
        return this.color;
    }

    @Override
    public String getSymbol() {
        return "H";
    }
    @Override
    public boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {
        if (!isValidMove(chessBoard, line, column, toLine, toColumn)) {
            return false;
        }

        int[][] moves = {
                {2, 1}, {2, -1}, {-2, 1}, {-2, -1},
                {1, 2}, {1, -2}, {-1, 2}, {-1, -2}
        };

        for (int[] move : moves) {
            int newLine = line + move[0];
            int newColumn = column + move[1];
            if (newLine == toLine && newColumn == toColumn) {
                ChessPiece targetPiece = chessBoard.board[toLine][toColumn];
                return targetPiece == null || !targetPiece.getColor().equals(this.color);
            }
        }

        return false;
    }


}
