package chess;

public class Pawn extends ChessPiece {

    public Pawn(String color) {
        super(color);
    }

    @Override
    public String getColor() {
        return this.color;
    }

    @Override
    public String getSymbol() {
        return "P";
    }

    @Override
    public boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {
        if (!isValidMove(chessBoard, line, column, toLine, toColumn)) {
            return false;
        }

        int direction = this.color.equals("White") ? 1 : -1;

        // Движение вперед
        if (toColumn == column && chessBoard.board[toLine][toColumn] == null) {
            if (toLine == line + direction) {
                return true;
            }
            if ((this.color.equals("White") && line == 1 || this.color.equals("Black") && line == 6)
                    && toLine == line + 2 * direction && chessBoard.board[line + direction][column] == null) {
                return true;
            }
        }

        // Атака по диагонали
        if (Math.abs(toColumn - column) == 1 && toLine == line + direction) {
            ChessPiece targetPiece = chessBoard.board[toLine][toColumn];
            return targetPiece != null && !targetPiece.getColor().equals(this.color);
        }

        return false;
    }

}
