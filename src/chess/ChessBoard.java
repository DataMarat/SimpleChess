package chess;

public class ChessBoard {
    public ChessPiece[][] board = new ChessPiece[8][8]; // creating a field for game
    String nowPlayer;

    public ChessBoard(String nowPlayer) {
        this.nowPlayer = nowPlayer;
    }

    public String nowPlayerColor() {
        return this.nowPlayer;
    }

    public boolean moveToPosition(int startLine, int startColumn, int endLine, int endColumn) {
        if (checkPos(startLine) && checkPos(startColumn)) {
            ChessPiece movingPiece = board[startLine][startColumn];
            if (movingPiece == null || !nowPlayer.equals(movingPiece.getColor())) {
                return false;
            }

            if (movingPiece.canMoveToPosition(this, startLine, startColumn, endLine, endColumn)) {
                // Перемещаем фигуру
                board[endLine][endColumn] = movingPiece;
                board[startLine][startColumn] = null;

                // Устанавливаем поле check в false, если фигура перемещается впервые
                if (movingPiece instanceof King || movingPiece instanceof Rook) {
                    movingPiece.check = false;
                }

                // Смена игрока
                this.nowPlayer = this.nowPlayerColor().equals("White") ? "Black" : "White";
                return true;
            }
        }
        return false;
    }


    public void printBoard() {  //print board in console
        System.out.println("Turn " + nowPlayer);
        System.out.println();
        System.out.println("Player 2(Black)");
        System.out.println();
        System.out.println("\t0\t1\t2\t3\t4\t5\t6\t7");

        for (int i = 7; i > -1; i--) {
            System.out.print(i + "\t");
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == null) {
                    System.out.print(".." + "\t");
                } else {
                    System.out.print(board[i][j].getSymbol() + board[i][j].getColor().substring(0, 1).toLowerCase() + "\t");
                }
            }
            System.out.println();
            System.out.println();
        }
        System.out.println("Player 1(White)");
    }

    public boolean checkPos(int pos) {
        return pos >= 0 && pos <= 7;
    }
    public boolean castling0() {
        int line = nowPlayer.equals("White") ? 0 : 7;

        // Проверяем, что король и ладья находятся на своих начальных позициях и не двигались
        if (board[line][4] instanceof King && board[line][0] instanceof Rook) {
            King king = (King) board[line][4];
            Rook rook = (Rook) board[line][0];

            if (king.check && rook.check) {
                // Проверяем, что между королем и ладьей все клетки свободны
                if (board[line][1] == null && board[line][2] == null && board[line][3] == null) {
                    // Проверяем, что клетки не находятся под атакой
                    if (!king.isUnderAttack(this, line, 4) && !king.isUnderAttack(this, line, 3) && !king.isUnderAttack(this, line, 2)) {
                        // Совершаем рокировку
                        board[line][2] = king;
                        board[line][3] = rook;
                        board[line][4] = null;
                        board[line][0] = null;

                        // Обновляем check
                        king.check = false;
                        rook.check = false;

                        // Смена игрока
                        this.nowPlayer = this.nowPlayerColor().equals("White") ? "Black" : "White";
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean castling7() {
        int line = nowPlayer.equals("White") ? 0 : 7;

        // Проверяем, что король и ладья находятся на своих начальных позициях и не двигались
        if (board[line][4] instanceof King && board[line][7] instanceof Rook) {
            King king = (King) board[line][4];
            Rook rook = (Rook) board[line][7];

            if (king.check && rook.check) {
                // Проверяем, что между королем и ладьей все клетки свободны
                if (board[line][5] == null && board[line][6] == null) {
                    // Проверяем, что клетки не находятся под атакой
                    if (!king.isUnderAttack(this, line, 4) && !king.isUnderAttack(this, line, 5) && !king.isUnderAttack(this, line, 6)) {
                        // Совершаем рокировку
                        board[line][6] = king;
                        board[line][5] = rook;
                        board[line][4] = null;
                        board[line][7] = null;

                        // Обновляем check
                        king.check = false;
                        rook.check = false;

                        // Смена игрока
                        this.nowPlayer = this.nowPlayerColor().equals("White") ? "Black" : "White";
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
