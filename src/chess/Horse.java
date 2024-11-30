package chess;

/**
 * Класс Horse представляет шахматную фигуру коня.
 */
public class Horse extends ChessPiece {
    public Horse(String color) {
        super(color);
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

        int deltaX = Math.abs(toLine - line);
        int deltaY = Math.abs(toColumn - column);

        // Конь движется буквой "Г" (2 клетки в одном направлении и 1 в другом)
        return (deltaX == 2 && deltaY == 1) || (deltaX == 1 && deltaY == 2);
    }
}
