package chess;

/**
 * Абстрактный класс ChessPiece представляет общую функциональность шахматных фигур.
 */
public abstract class ChessPiece {
    protected String color; // Цвет фигуры: "White" или "Black"
    protected boolean check = true; // Указывает, двигалась ли фигура

    public ChessPiece(String color) {
        this.color = color;
    }

    /**
     * Возвращает цвет фигуры.
     *
     * @return строка с цветом фигуры.
     */
    public String getColor() {
        return this.color;
    }

    /**
     * Проверяет, является ли ход базово допустимым.
     *
     * @param chessBoard объект шахматной доски.
     * @param line начальная строка.
     * @param column начальный столбец.
     * @param toLine конечная строка.
     * @param toColumn конечный столбец.
     * @return true, если ход базово допустим; false в противном случае.
     */
    public boolean isValidMove(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {
        if (!chessBoard.checkPos(line) || !chessBoard.checkPos(column) ||
                !chessBoard.checkPos(toLine) || !chessBoard.checkPos(toColumn)) {
            return false;
        }
        return !(line == toLine && column == toColumn);
    }

    /**
     * Определяет, может ли фигура двигаться в указанную позицию.
     *
     * @param chessBoard объект шахматной доски.
     * @param line начальная строка.
     * @param column начальный столбец.
     * @param toLine конечная строка.
     * @param toColumn конечный столбец.
     * @return true, если ход допустим; false в противном случае.
     */
    public abstract boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn);

    /**
     * Проверяет, находится ли фигура под атакой.
     *
     * @param chessBoard объект шахматной доски.
     * @param line строка фигуры.
     * @param column столбец фигуры.
     * @return true, если фигура под атакой; false в противном случае.
     */
    public boolean isUnderAttack(ChessBoard chessBoard, int line, int column) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPiece attacker = chessBoard.board[i][j];
                if (attacker != null && !attacker.getColor().equals(this.color)) {
                    if (attacker.canMoveToPosition(chessBoard, i, j, line, column)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Возвращает символ фигуры.
     *
     * @return строка с символом фигуры.
     */
    public abstract String getSymbol();
}
