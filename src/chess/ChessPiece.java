package chess;

public abstract class ChessPiece {
    protected String color;
    protected boolean check = true;

    public ChessPiece(String color) {
        this.color = color;
    }

    public String getColor() {
        return this.color;
    }

    public abstract boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn);

    public abstract String getSymbol();

    // Новый метод для проверки корректности позиций
    protected boolean isValidMove(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {
        // Проверяем, чтобы позиции находились на доске
        if (!chessBoard.checkPos(line) || !chessBoard.checkPos(column)
                || !chessBoard.checkPos(toLine) || !chessBoard.checkPos(toColumn)) {
            return false;
        }

        // Проверяем, чтобы начальная и конечная позиции не совпадали
        return !(line == toLine && column == toColumn);
    }
}
