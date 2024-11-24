package chess;

public class Rook extends ChessPiece {

    // Конструктор, принимающий цвет фигуры
    public Rook(String color) {
        super(color);
    }

    // Метод возвращает цвет фигуры
    @Override
    public String getColor() {
        return this.color;
    }

    // Метод возвращает символ фигуры
    @Override
    public String getSymbol() {
        return "R";
    }
    // Реализация метода canMoveToPosition
    @Override
    public boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {
        if (!isValidMove(chessBoard, line, column, toLine, toColumn)) {
            return false;
        }

        if (line != toLine && column != toColumn) {
            return false;
        }

        // Проверяем свободность пути
        if (line == toLine) { // Горизонтальное движение
            int step = (toColumn > column) ? 1 : -1;
            for (int currentColumn = column + step; currentColumn != toColumn; currentColumn += step) {
                if (chessBoard.board[line][currentColumn] != null) {
                    return false;
                }
            }
        } else { // Вертикальное движение
            int step = (toLine > line) ? 1 : -1;
            for (int currentLine = line + step; currentLine != toLine; currentLine += step) {
                if (chessBoard.board[currentLine][column] != null) {
                    return false;
                }
            }
        }

        // Проверяем, что в конечной позиции нет фигуры того же цвета
        ChessPiece targetPiece = chessBoard.board[toLine][toColumn];
        return targetPiece == null || !targetPiece.getColor().equals(this.color);
    }

}
