public class Ship{
  private final List<Cell> cells;

  public Ship(List<Cell> cells){
    this.cells = cells;
  }

  public boolean isSunk(){
    for(Cell cell : cells){
      if(!cell.isHit()){
        return false;
      }
    }
    return true;
  }
}

public class Cell{
  private Ship ship;
  private boolean isHit;

  public void placeShip(Ship ship){
    this.ship = ship;
  }

  public boolean hasShip(){
    return ship != null;
  }

  public boolean isHit(){
    return isHit;
  }

  public boolean hit(){
    isHit = true;
    if(ship != null){
      return true;
    }
    return false;
  }

  public Ship getShip(){
    return ship;
  }
}

public class Board{
  private dinal int SIZE = 10;
  private final Cell[][] grid = new Cell[SIZE][SIZE];

  public Board(){
    for(int i=0;i<SIZE;i++){
      for(int j=0;j<SIZE;j++){
        grid[i][j] = new Cell();
      }
    }
  }

  public boolean placeShip(int row, int col, int length, boolean isHorizontal, Ship ship){
    for (int i=0;i<length;i++){
      int r = row+(isHorizontal ? 0:i);
      int c = col+(isHorizontal? i:0);
      grid[r][c].placeShip(ship);
    }
    return true;
  }

  public boolean canPlace(int row, int col, int length, boolean isHorizontal){
    for(int i=0;i<length;i++){
      int r = row+(isHorizontal ? 0:i);
      int c = col+(isHorizontal? i:0);
      if(r >= 10 || c>=10 || grid[r][c].hasShip()) return false;
    }
    return true;
  }

  public boolean hit(int row, int col){
    return grid[row][col].hit();
  }
  public Cell getCell(int row, int col){
    return grid[row][col];
  }
}

public class Player{
  private final Board primaryBoard = new Board();
  private final Board trackingBoard = new Board();
  private final List<Ship> ships = new ArrayList<>();

  public boolean placeShip(int row, int col, int length, boolean isHorizontal){
    List<Cell> shipCells = new ArrayList<>();
    for(int i=0;i<length;i++){
      int r = row+(isHorizontal ? 0:i);
      int c = col + (isHorizontal ? i:0);
      shipCells.add(primaryBoard.getCell(r,c));
    }
    Ship ship = new Ship(shipcells);
    boolean placed = primaryBoard.placeShip(row, col, length, isHorizontal, ship);
    if(placed) ships.add(ship);
    return placed;
  }

  public boolean hit(Player opponent, int row, int col){
    boolean isHit = opponent.primaryBoard.hit(row, col);
    trackingBoard.getCell(row, col).hit();
    return isHit;
  }

  public boolean hasLost(){
    for(Ship ship: ships){
      if(!ship.isSunk()) return false;
    }
    return true;
  }
}

public class Game{
  private Player player1;
  private Player player2;
  private Player currentPlayer;
  public Game(Player player1, Plater player2){
    this.player1 = player1;
    this.player2=player2;
    this.currentPlayer = player1;
  }

  public boolean playerTurn(int row, int col){
    Player opponent = (currentPlayer == player1)? player2: player1;
    boolean isHit = currentPlayer.hit(opponent, row, col);
    if (opponent.hasLost()){
      print
    }
    currentPlayer = opponent
    return isHit();
  }
}
