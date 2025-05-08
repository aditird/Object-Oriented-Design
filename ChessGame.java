public abstract class Piece{
  protected final Color color;
  protected int row;
  protected int col;

  public Piece(Color color, int row, int col){
    this.color = color;
    this.row=row;
    this.col=col;
  }

  public abstract boolean canMove(Board board, int destRow, destCol);

  //getColor, SetColor, getCol, getRow,
}

public class Bishop extends Piece{
  public Bishop(Color color, int row, int col){
    super(color, row, col);
  }
  @Override
  public boolean canMove(Board board, int destRow, int destCol){
    //impl
  }
}

//knight, Queen, King, Rook, Pawn

public enum Color{
  WHITE,
  BLACK;
}

public class Player{
  private final Color color;
  public Player(Color color){
    this.color=color;
  }

  public void makeMove(Board board, Move move){
    if(board.isValidMove(piece, destRow, destCol)){

    }else{
      throw new InvalidMoveException("INvalid Move!");
    }
  }
}

public class Move{
  private final Piece piece;
  private final int destRow;
  private final int destCol;

  //constructor

  //getPiece, getDestRow, getDestCol
}

public class Board{
  private final Piece[][] board;

  public Board(){
    board = new piece[8][8];
  }

  private void initializeBoard(){

  }

  //getPiece, setPiece,

  public boolean isValidMove(Piece piece, int destRow, int destCol){

  }

  public boolean isCheckMate(){

  }

  public boolean isStalemate(){

  }
}

public class ChessGame{
  private final Board board;
  private final Player[] players;
  private int currentPlayer;

  //

  public void Start(){
    while(!isGameOver()){

    }
  }

  public boolean isGameOver(){

  }

  private Move getPlayerMove(){

  }
  
}
