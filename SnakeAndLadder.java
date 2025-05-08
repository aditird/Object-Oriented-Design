import java.util.concurrent.ThreadLocalRandom;
public class Dice{
  int diceCount;
  int min = 1;
  int max = 6;
  public Dice(int diceCount){
    this.diceCount = diceCount;
  }

  public int rollDice(){
    int totalSum =0;
    int diceUsed = 0;

    while(diceUsed < diceCount){
      totalSum += ThreadLocalRandom.current().nextInt(min, max+1);
      diceUsed++;
    }

    return totalSum;
  }
}


public class Player{
  String id;
  int currentPosition;
  public Player(String id, currentPosition){
    this.id = id;
    this.currentPosition = currentPosition;
  }
}

//snake or ladder jump
public class Jump{
  int start;
  int end;
  public Jump();
}

public class Cell{
  Jump jump;
}

public class Board{
  Cells[][] cells;

  Board(int numOfSnakes, int numOfLadders, int boardSize){
    initializeCells(cells, numOfSnakes, numOfLadders);
    addSnakesLadders(Cells[][] cells, int numOfSnakes, int numOfLadders);
  }

  private void initializeCells(Cells[][] cells, int numOfSnakes, int numOfLadders){
    cells = new Cell[boardSize][boardSize];

    for(int i=0;i<boardSize;i++){
      for(int j=0;j<boardSize;j++){
        Cell cellObj = new Cell();
        cells[i][j] = cellObj;
      }
    }
  }

  private void addSnakesLadders(cells, numOfSnakes, numOfLadders){
    while(numOfSnakes > 0){
    int snakeHead = ThreadLocalRandom.current().nextInt(1,cells.length*cells.length+1);
    int snakeTail = ThreadLocalRandom.current().nextInt(1,cells.length*cells.length+1);

    Jump snakeObj = new Jump();

    snakeObj.start = snakeHead;
    snakeObj.end = snakeTail;

    Cell cell = getCell(snakeHead);
    cell.jump = snakeObj;

    numOfSnakes--;
  }
  //do the same for num of numOfLadders
  //addLadders
}



  private Cell getCell(int position){
    int row = position/cells.length;
    int col = position%cells.length;

    return cells[row][col];
  }

}

public class Game{
  Board board;
  Dice dice;
  Deque<Player> playersList = new LinkedList<>();
  Player winner;
  public Game(){
    initializeGame();
  }

  private void initializeGame(){
    board = new Board(10,4,10);
    dice = new Dice(1);
    winner = null;
    addPlayers();
  }

  private void addPlayers(){
    Player p1 = new Player("p1",0);
    Player p2 = new Player("p2",0);
    playersList.add(p1);
    playersList.add(p2);
  }

  public void startGame(){
    while(winner == null){
      Player playerTurn = findPlayerTurn();

      int diceNumbers = dice.rollDice();

      int playerNewPos = playerTurn.currentPosition+diceNumbers;

      playerNewPos = jumpCheck(playerNewPos);

      playerTurn.currentPosition = playerNewPos;

      //check winner
      if(playerNewPos >= board.length*board.length-1){
        winner = playerTurn;
      }
    }

    System.out.printn("WINNER IS "+winner.id+" CONGRATSSSSS!!!!!");
  }

  private Player findPlayerTurn(){
    Player playerTurn  = playersList.removeFirst();
    playersList.addLast(playerTurn);
    return playerTurn;
  }

  private int jumpCheck(int playerNewPos){
    if(playerNewPos > board.cells.length * board.cells.length-1){
      return playerNewPos;
    }

    Cell cell = board.getCell(playerNewPos);
    if(cell.jump != null && cell.jump.start == playerNewPos){
      return cell.jump.end; //snake or ladder
    }

    return playerNewPos;
  }

}


/*
CHATGPT


import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

// Player class
class Player {
    private String id;
    private int currentPos;

    public Player(String id) {
        this.id = id;
        this.currentPos = 0;
    }

    public String getId() {
        return id;
    }

    public int getCurrentPos() {
        return currentPos;
    }

    public void setCurrentPos(int newPos) {
        this.currentPos = newPos;
    }
}

// Dice interface and implementation for Strategy Pattern
interface DiceStrategy {
    int roll();
}

class StandardDice implements DiceStrategy {
    private int numOfDice;
    private static final int MIN = 1;
    private static final int MAX = 6;

    public StandardDice(int numOfDice) {
        this.numOfDice = numOfDice;
    }

    @Override
    public int roll() {
        int total = 0;
        for (int i = 0; i < numOfDice; i++) {
            total += ThreadLocalRandom.current().nextInt(MIN, MAX + 1);
        }
        return total;
    }
}

// Jump class (for Snakes and Ladders)
class Jump {
    private int start;
    private int end;

    public Jump(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }
}

// Cell class
class Cell {
    private Jump jump;

    public void setJump(Jump jump) {
        this.jump = jump;
    }

    public Jump getJump() {
        return jump;
    }
}

// Board class
class Board {
    private Cell[][] cells;
    private int size;

    public Board(int size, int numOfSnakes, int numOfLadders) {
        this.size = size;
        this.cells = new Cell[size][size];
        initializeBoard();
        addJumps(numOfSnakes, numOfLadders);
    }

    private void initializeBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cells[i][j] = new Cell();
            }
        }
    }

    private void addJumps(int numOfSnakes, int numOfLadders) {
        addSpecificJumps(numOfSnakes, false); // Add Snakes
        addSpecificJumps(numOfLadders, true);  // Add Ladders
    }

    private void addSpecificJumps(int count, boolean isLadder) {
        while (count > 0) {
            int start = ThreadLocalRandom.current().nextInt(1, size * size - 1);
            int end = ThreadLocalRandom.current().nextInt(1, size * size - 1);

            if ((isLadder && end > start) || (!isLadder && end < start)) {
                getCell(start).setJump(new Jump(start, end));
                count--;
            }
        }
    }

    public Cell getCell(int position) {
        int row = position / size;
        int col = position % size;
        return cells[row][col];
    }

    public int getSize() {
        return size;
    }
}

// Game class
public class Game {
    private Board board;
    private DiceStrategy dice;
    private Queue<Player> players;
    private Player winner;

    public Game(int boardSize, int numOfSnakes, int numOfLadders, int numOfDice) {
        this.board = new Board(boardSize, numOfSnakes, numOfLadders);
        this.dice = new StandardDice(numOfDice);
        this.players = new LinkedList<>();
        initializePlayers();
    }

    private void initializePlayers() {
        players.add(new Player("P1"));
        players.add(new Player("P2"));
    }

    public void startGame() {
        while (winner == null) {
            Player currentPlayer = players.poll();
            int diceRoll = dice.roll();
            int newPos = currentPlayer.getCurrentPos() + diceRoll;

            if (newPos >= board.getSize() * board.getSize() - 1) {
                winner = currentPlayer;
                break;
            }

            newPos = applyJumps(newPos);
            currentPlayer.setCurrentPos(newPos);
            players.add(currentPlayer);
        }
        System.out.println("Winner is: " + winner.getId());
    }

    private int applyJumps(int position) {
        Cell cell = board.getCell(position);
        if (cell.getJump() != null) {
            return cell.getJump().getEnd();
        }
        return position;
    }

    public static void main(String[] args) {
        Game game = new Game(10, 3, 4, 2);
        game.startGame();
    }
}




*/
