package at.kexxs.game.board;

import java.util.ArrayList;
import java.util.List;

import at.kexxs.game.unit.Unit;

public class Player {

  int id;
  String name;
  boolean active;
  List<Unit> units;

  public Player(int id, String name, boolean active) {
    this.id = id;
    this.name = name;
    this.active = active;
    units = new ArrayList<>();
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public List<Unit> getUnits() {
    return units;
  }

  public void setUnits(List<Unit> units) {
    this.units = units;
  }

}