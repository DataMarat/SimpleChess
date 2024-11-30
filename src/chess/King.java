package chess;

/**
 * Класс King представляет шахматную фигуру короля.
 */
public class King extends ChessPiece {
    public King(String color) {
        super(color);
    }

    @Override
    public String getSymbol() {
        return "K";
    }

    @Override
    public boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {
        if (!isValidMove(chessBoard, line, column, toLine, toColumn)) {
            return false;
        }

        int deltaX = Math.abs(toLine - line);
        int deltaY = Math.abs(toColumn - column);

        // Король может двигаться на 1 клетку в любом направлении
        if (deltaX <= 1 && deltaY <= 1) {
            // Проверяем, не окажется ли король под шахом
            if (chessBoard.isUnderAttack(toLine, toColumn, this.color)) {
                return false;
            }
            return true;
        }
        return false;
    }
}
