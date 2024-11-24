package chess;

public class Queen extends ChessPiece {

    // Конструктор
    public Queen(String color) {
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
        return "Q";
    }
    // Реализация метода canMoveToPosition
    @Override
    public boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {
        if (!isValidMove(chessBoard, line, column, toLine, toColumn)) {
            return false;
        }

        Rook rook = new Rook(this.color);
        Bishop bishop = new Bishop(this.color);

        // Проверяем, если движение ферзя совпадает с движением ладьи или слона
        return rook.canMoveToPosition(chessBoard, line, column, toLine, toColumn)
                || bishop.canMoveToPosition(chessBoard, line, column, toLine, toColumn);
    }


}
