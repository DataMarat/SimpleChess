package chess;

public class Bishop extends ChessPiece {

    // Конструктор, принимающий цвет фигуры
    public Bishop(String color) {
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
        return "B";
    }

    // Реализация метода canMoveToPosition
    @Override
    public boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {
        if (!isValidMove(chessBoard, line, column, toLine, toColumn)) {
            return false;
        }

        if (Math.abs(line - toLine) != Math.abs(column - toColumn)) {
            return false;
        }

        // Проверяем свободность пути
        int lineStep = (toLine > line) ? 1 : -1;
        int columnStep = (toColumn > column) ? 1 : -1;
        int currentLine = line + lineStep;
        int currentColumn = column + columnStep;

        while (currentLine != toLine && currentColumn != toColumn) {
            if (chessBoard.board[currentLine][currentColumn] != null) {
                return false;
            }
            currentLine += lineStep;
            currentColumn += columnStep;
        }

        // Проверяем, что в конечной позиции нет фигуры того же цвета
        ChessPiece targetPiece = chessBoard.board[toLine][toColumn];
        return targetPiece == null || !targetPiece.getColor().equals(this.color);
    }


}

