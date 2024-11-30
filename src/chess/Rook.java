package chess;

/**
 * Класс Rook представляет шахматную фигуру ладью.
 */
public class Rook extends ChessPiece {
    public Rook(String color) {
        super(color);
    }

    @Override
    public String getSymbol() {
        return "R";
    }

    @Override
    public boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {
        if (!isValidMove(chessBoard, line, column, toLine, toColumn)) {
            return false;
        }

        if (line == toLine || column == toColumn) {
            int stepX = Integer.signum(toLine - line);
            int stepY = Integer.signum(toColumn - column);

            int currentX = line + stepX, currentY = column + stepY;
            while (currentX != toLine || currentY != toColumn) {
                if (chessBoard.board[currentX][currentY] != null) {
                    return false; // Путь заблокирован
                }
                currentX += stepX;
                currentY += stepY;
            }
            return true;
        }
        return false;
    }
}
