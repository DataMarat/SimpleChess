package chess;

public class King extends ChessPiece {

    // Конструктор
    public King(String color) {
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
        return "K";
    }
    // Реализация метода canMoveToPosition
    @Override
    public boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {
        // Используем метод isValidMove из ChessPiece для базовых проверок
        if (!isValidMove(chessBoard, line, column, toLine, toColumn)) {
            return false;
        }

        // Король может двигаться максимум на 1 клетку в любом направлении
        if (Math.abs(line - toLine) <= 1 && Math.abs(column - toColumn) <= 1) {
            // Проверяем, что конечная позиция не занята фигурой того же цвета
            ChessPiece targetPiece = chessBoard.board[toLine][toColumn];
            if (targetPiece == null || !targetPiece.getColor().equals(this.color)) {
                return true;
            }
        }

        return false;
    }

    // Метод проверяет, находится ли клетка под атакой
    public boolean isUnderAttack(ChessBoard chessBoard, int line, int column) {
        // Проходим по всем фигурам на доске
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPiece piece = chessBoard.board[i][j];
                // Если на клетке есть фигура противоположного цвета
                if (piece != null && !piece.getColor().equals(this.color)) {
                    // Проверяем, может ли эта фигура атаковать указанную клетку
                    if (piece.canMoveToPosition(chessBoard, i, j, line, column)) {
                        return true;
                    }
                }
            }
        }
        return false; // Если ни одна фигура не может атаковать
    }

}
