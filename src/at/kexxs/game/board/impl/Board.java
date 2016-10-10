package at.kexxs.game.board.impl;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.Arrays;
import java.util.logging.Logger;

import javax.swing.JPanel;

import at.kexxs.game.Game;
import at.kexxs.game.board.IBoard;
import at.kexxs.game.unit.impl.Soldier;
import at.kexxs.game.unit.impl.Unit;

/**
 * @author Markus
 */
public class Board extends JPanel implements IBoard {
  private static final long serialVersionUID = 1L;
  private final Game game;
  private GameField[][] fields;
  public Unit selectedUnit;

  private static final Logger log = Logger.getLogger(Board.class.getName());

  /** Creates a new instance of ChessBoard */
  public Board(Game game) {
    this.game = game;
    init();
  }

  public GameField[][] getFields() {
    return fields;
  }

  public void setFields(GameField[][] fields) {
    this.fields = fields;
  }

  public Unit getSelectedUnit() {
    return selectedUnit;
  }

  public void setSelectedUnit(Unit selectedUnit) {
    this.selectedUnit = selectedUnit;
  }

  public Game getGame() {
    return game;
  }

  public void init() {

    final GridLayout grid = new GridLayout(Game.WIDTH, Game.HEIGHT, 1, 1);
    setLayout(grid);

    fields = new GameField[Game.WIDTH][Game.HEIGHT];
    for (int row = 0; row < Game.WIDTH; row++) {
      for (int column = 0; column < Game.HEIGHT; column++) {
        fields[row][column] = new GameField(this);
        if ((row + column) % 2 == 0) {
          fields[row][column].setBackground(Color.gray, true);
        } else {
          fields[row][column].setBackground(Color.black, true);
        }

        fields[row][column].setPosY(row);
        fields[row][column].setPosX(column);
        fields[row][column].setName("field_" + row + "_" + column);
        add(fields[row][column]);
      }
    }
  }

  @Override
  public void setUnit(Unit unit, int row, int column) {
    unit.setGameField(getField(row, column));
    fields[row][column].setAndPaintUnit(unit);
  }

  @Override
  public void removeUnit(int row, int column) {
    fields[row][column].removeUnit();
  }

  @Override
  public void clearBackgroundColor() {
    for (int row = 0; row < Game.WIDTH; row++) {
      for (int column = 0; column < Game.HEIGHT; column++) {
        if ((row + column) % 2 == 0) {
          fields[row][column].setBackground(Color.gray);
        } else {
          fields[row][column].setBackground(Color.black);
        }
        fields[row][column].repaint();
      }
    }
  }

  @Override
  public void restart() {
    log.info("Neues Spiel startet");
    removeAll();
    init();
    validate();
  }

  public void checkAvailableFields(GameField field, Unit unit) {
    final int positionX = field.getPosY();
    final int positionY = field.getPosX();
    final int movement = unit.getMovement();

    for (int newPos = positionX + 1; newPos <= (positionX + movement); newPos++) {
      if (newPos < Game.WIDTH) {
        fields[newPos][positionY].setBackground(Color.GREEN);
      }
    }

    for (int newPos = positionX - 1; newPos >= (positionX - movement); newPos--) {
      if (newPos >= 0) {
        fields[newPos][positionY].setBackground(Color.GREEN);
      }
    }

    for (int newPos = positionY + 1; newPos <= (positionY + movement); newPos++) {
      if (newPos < Game.HEIGHT) {
        fields[positionX][newPos].setBackground(Color.GREEN);
      }
    }

    for (int newPos = positionY - 1; newPos >= (positionY - movement); newPos--) {
      if (newPos >= 0) {
        fields[positionX][newPos].setBackground(Color.GREEN);
      }
    }
    repaint();

  }

  public GameField getField(int posY, int posX) {
    return fields[posY][posX];
  }

  @Override
  public void fillBoardWithUnits(Player player1, Player player2) {
    for (int i = 0; i < 10; i++) {
      fields[i][0].setAndPaintUnit(new Unit(player1, Unit.UNIT_RED));
    }
    for (int i = 0; i < 10; i++) {
      fields[i][1].setAndPaintUnit(new Soldier(player1, Soldier.SOLDIER_RED));
    }

    for (int i = 0; i < 10; i++) {
      fields[i][Game.WIDTH - 1].setAndPaintUnit(new Unit(player2, Unit.UNIT_BLUE));
    }

    for (int i = 0; i < 10; i++) {
      fields[i][Game.WIDTH - 2].setAndPaintUnit(new Soldier(player2, Soldier.SOLDIER_BLUE));
    }

  }

  public void nextPlayer() {

    final Player activePlayer = game.changeActivePlayer();
    log.info(activePlayer.getName() + " ist an der Reihe!");
    Game.setText(activePlayer.getName() + " ist an der Reihe!");
    validate();

  }

  @Override
  public GameField findUnitOfPlayer(int playerId) {
    for (int row = 0; row < Game.WIDTH; row++) {
      for (int column = 0; column < Game.HEIGHT; column++) {

        if (fields[row][column].getUnit() != null) {
          if (fields[row][column].getUnit().getPlayer().getId() == playerId) {
            return fields[row][column];
          }
        }
      }
    }
    log.info("No Units found");
    return null;
  }

  @Override
  public String toString() {
    return "Board [game=" + game + ", fields=" + Arrays.toString(fields) + ", selectedUnit=" + selectedUnit + ", toString()=" + super.toString() + "]";
  }

  @Override
  public boolean checkIfUnitisOnField(int row, int column) {
    return (fields[row][column].getUnit() != null);
  }

}