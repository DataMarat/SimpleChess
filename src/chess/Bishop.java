package chess;

/**
 * Класс Bishop представляет шахматную фигуру слона.
 */
public class Bishop extends ChessPiece {
    public Bishop(String color) {
        super(color);
    }

    @Override
    public String getSymbol() {
        return "B";
    }


    @Override
    public boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {
        if (!isValidMove(chessBoard, line, column, toLine, toColumn)) {
            return false;
        }

        int deltaX = Math.abs(toLine - line);
        int deltaY = Math.abs(toColumn - column);

        if (deltaX == deltaY) { // Проверка диагонального движения
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
