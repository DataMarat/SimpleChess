package chess;

/**
 * Класс ChessBoard представляет шахматную доску и управление игрой.
 */
public class ChessBoard {
    public ChessPiece[][] board = new ChessPiece[8][8]; // Шахматная доска
    String nowPlayer; // Текущий игрок: "White" или "Black"

    public ChessBoard(String nowPlayer) {
        this.nowPlayer = nowPlayer;
    }

    /**
     * Возвращает цвет текущего игрока.
     *
     * @return строка с цветом текущего игрока.
     */
    public String nowPlayerColor() {
        return this.nowPlayer;
    }

    /**
     * Перемещает фигуру с начальной позиции на конечную, если это допустимый ход.
     *
     * @param startLine   начальная строка.
     * @param startColumn начальный столбец.
     * @param endLine     конечная строка.
     * @param endColumn   конечный столбец.
     * @return true, если ход выполнен успешно; false, если ход невозможен.
     */
    public boolean moveToPosition(int startLine, int startColumn, int endLine, int endColumn) {
        if (!checkPos(startLine) || !checkPos(startColumn)) {
//            System.out.println("Ошибка: начальная позиция находится за пределами доски.");
            return false;
        }

        if (!checkPos(endLine) || !checkPos(endColumn)) {
//            System.out.println("Ошибка: конечная позиция находится за пределами доски.");
            return false;
        }

        ChessPiece movingPiece = board[startLine][startColumn];
        if (movingPiece == null) {
//            System.out.println("Ошибка: на начальной позиции нет фигуры.");
            return false;
        }

        if (!nowPlayer.equals(movingPiece.getColor())) {
//            System.out.println("Ошибка: фигура принадлежит другому игроку.");
            return false;
        }

        if (!movingPiece.canMoveToPosition(this, startLine, startColumn, endLine, endColumn)) {
//            System.out.println("Ошибка: фигура " + movingPiece.getSymbol() + " (" + movingPiece.getColor() + ") не может двигаться в позицию (" + endLine + ", " + endColumn + ").");
            return false;
        }

        // Выполняем временный ход
        ChessPiece capturedPiece = board[endLine][endColumn];
        board[endLine][endColumn] = movingPiece;
        board[startLine][startColumn] = null;

        // Проверяем, не остаётся ли король под шахом
        if (isKingUnderCheck(nowPlayer)) {
            // Отменяем ход
            board[startLine][startColumn] = movingPiece;
            board[endLine][endColumn] = capturedPiece;
//            System.out.println("Ошибка: ход оставляет короля под шахом.");
            return false;
        }

        // Завершаем ход
        nowPlayer = nowPlayer.equals("White") ? "Black" : "White";
        return true;
    }

    /**
     * Проверяет, находится ли указанная клетка под атакой фигур противника.
     *
     * @param row         строка клетки.
     * @param col         столбец клетки.
     * @param playerColor цвет игрока, для которого проверяется атака.
     * @return true, если клетка под атакой; false в противном случае.
     */
    public boolean isUnderAttack(int row, int col, String playerColor) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPiece piece = board[i][j];
                // Проверяем фигуры противоположного цвета
                if (piece != null && !piece.getColor().equals(playerColor)) {
                    if (piece.canMoveToPosition(this, i, j, row, col)) {
                        return true; // Клетка под атакой
                    }
                }
            }
        }
        return false; // Клетка не под атакой
    }

    /**
     * Проверяет, находится ли король текущего игрока под шахом.
     *
     * @param playerColor цвет игрока ("White" или "Black").
     * @return true, если король под шахом; false, если король в безопасности.
     */
    public boolean isKingUnderCheck(String playerColor) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPiece piece = board[i][j];
                if (piece instanceof King && piece.getColor().equals(playerColor)) {
                    return piece.isUnderAttack(this, i, j);
                }
            }
        }
        return false;
    }

    /**
     * Проверяет, находится ли позиция на доске.
     *
     * @param pos индекс строки или столбца.
     * @return true, если позиция допустима; false в противном случае.
     */
    public boolean checkPos(int pos) {
        return pos >= 0 && pos < 8;
    }

    /**
     * Длинная рокировка для текущего игрока.
     *
     * @return true, если рокировка выполнена успешно; false, если рокировка невозможна.
     */
    public boolean castling0() {
        int line = nowPlayer.equals("White") ? 0 : 7;

        if (board[line][4] instanceof King && board[line][0] instanceof Rook) {
            King king = (King) board[line][4];
            Rook rook = (Rook) board[line][0];

            if (king.check && rook.check) {
                if (board[line][1] == null && board[line][2] == null && board[line][3] == null) {
                    if (!king.isUnderAttack(this, line, 4) && !king.isUnderAttack(this, line, 3) && !king.isUnderAttack(this, line, 2)) {
                        board[line][2] = king;
                        board[line][3] = rook;
                        board[line][4] = null;
                        board[line][0] = null;

                        king.check = false;
                        rook.check = false;

                        nowPlayer = nowPlayer.equals("White") ? "Black" : "White";
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Короткая рокировка для текущего игрока.
     *
     * @return true, если рокировка выполнена успешно; false, если рокировка невозможна.
     */
    public boolean castling7() {
        int line = nowPlayer.equals("White") ? 0 : 7;

        if (board[line][4] instanceof King && board[line][7] instanceof Rook) {
            King king = (King) board[line][4];
            Rook rook = (Rook) board[line][7];

            if (king.check && rook.check) {
                if (board[line][5] == null && board[line][6] == null) {
                    if (!king.isUnderAttack(this, line, 4) && !king.isUnderAttack(this, line, 5) && !king.isUnderAttack(this, line, 6)) {
                        board[line][6] = king;
                        board[line][5] = rook;
                        board[line][4] = null;
                        board[line][7] = null;

                        king.check = false;
                        rook.check = false;

                        nowPlayer = nowPlayer.equals("White") ? "Black" : "White";
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Печатает текущее состояние шахматной доски в консоль.
     * Включает текущего игрока, метки осей и состояние клеток.
     */
    public void printBoard() {
        System.out.println("Turn: " + nowPlayer); // Отображает текущего игрока
        System.out.println();
        System.out.println("Player 2 (Black)");
        System.out.println();
        System.out.println("\t0\t1\t2\t3\t4\t5\t6\t7"); // Метки колонок

        for (int i = 7; i >= 0; i--) { // Строки выводятся сверху вниз
            System.out.print(i + "\t"); // Метка строки
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == null) {
                    System.out.print(".." + "\t"); // Пустая клетка
                } else {
                    // Символ фигуры и первая буква её цвета
                    System.out.print(board[i][j].getSymbol() + board[i][j].getColor().substring(0, 1).toLowerCase() + "\t");
                }
            }
            System.out.println();
            System.out.println();
        }
        System.out.println("Player 1 (White)");
    }
}
