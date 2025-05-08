import java.util.*;

public enum PieceType {
    X,
    O;
}

public class PlayingPiece {
    PieceType type;
    public PlayingPiece(PieceType type) {
        this.type = type;
    }
}

public class Board {
    public int size;
    public PlayingPiece[][] board;

    public Board(int size) {
        this.size = size;
        board = new PlayingPiece[size][size];
    }

    public List<Pair<Integer>> getFreeCells() {
        List<Pair<Integer>> freeCells = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == null) {
                    Pair<Integer, Integer> rowColumn = new Pair<>(i, j);
                    freeCells.add(rowColumn);
                }
            }
        }
        return freeCells;
    }

    public boolean addPiece(int row, int col, PlayingPiece piece) {
        if (board[row][col] == null) {
            board[row][col] = piece;
            return true;
        }
        return false;
    }
}

public class Player {
    public String name;
    public PlayingPiece playingPiece;

    public Player(String name, PlayingPiece playingPiece) {
        this.name = name;
        this.playingPiece = playingPiece;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PlayingPiece getPlayingPiece() {
        return playingPiece;
    }

    public void setPlayingPiece(PlayingPiece piece) {
        this.playingPiece = piece;
    }
}

public class TicTacToe {
    Deque<Player> players;
    Board gameBoard;

    public TicTacToe(Player player1, PlayingPiece pp1, Player player2, PlayingPiece pp2, int size) {
        initializeGame(player1, pp1, player2, pp2, size);
    }

    public void initializeGame(Player player1, PlayingPiece pp1, Player player2, PlayingPiece pp2, int size) {
        players = new LinkedList<>();
        players.add(player1);
        players.add(player2);
        gameBoard = new Board(size);
    }

    public String startGame() {
        boolean noWinner = true;

        while (noWinner) {
            Player playerTurn = players.removeFirst();
            List<Pair<Integer>> freeSpaces = gameBoard.getFreeCells();
            if (freeSpaces.isEmpty()) {
                noWinner = false;
                continue;
            }

            System.out.println("enter row col");

            // Assuming you take input for row and column
            int inputRow = 0; // Placeholder
            int inputCol = 0; // Placeholder

            boolean isPieceAdded = gameBoard.addPiece(inputRow, inputCol, playerTurn.playingPiece);
            if (!isPieceAdded) {
                System.out.println("Incorrect move, please try again");
                players.addFirst(playerTurn);
            } else {
                players.add(playerTurn);
                boolean winner = isThereWinner(inputRow, inputCol, playerTurn.playingPiece.type);
                if (winner) {
                    return playerTurn.name;
                }
            }
        }
        return "tie";
    }

    public boolean isThereWinner(int row, int col, PieceType type) {
        boolean rowMatch = true;
        boolean colMatch = true;
        boolean diagMatch = true;
        boolean antiDiagMatch = true;

        // Check row
        for (int i = 0; i < gameBoard.size; i++) {
            if (gameBoard.board[row][i] == null || gameBoard.board[row][i].type != type) {
                rowMatch = false;
            }
        }

        // Check column
        for (int i = 0; i < gameBoard.size; i++) {
            if (gameBoard.board[i][col] == null || gameBoard.board[i][col].type != type) {
                colMatch = false;
            }
        }

        // Check main diagonal
        for (int i = 0; i < gameBoard.size; i++) {
            if (gameBoard.board[i][i] == null || gameBoard.board[i][i].type != type) {
                diagMatch = false;
            }
        }

        // Check anti-diagonal
        for (int i = 0, j = gameBoard.size - 1; i < gameBoard.size; i++, j--) {
            if (gameBoard.board[i][j] == null || gameBoard.board[i][j].type != type) {
                antiDiagMatch = false;
            }
        }

        return rowMatch || colMatch || diagMatch || antiDiagMatch;
    }
}

// Pair class (if needed)
class Pair<T> {
    T first, second;

    public Pair(T first, T second) {
        this.first = first;
        this.second = second;
    }
}
