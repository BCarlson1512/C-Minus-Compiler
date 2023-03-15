import absyn.Dec;

public class NodeType {
  private String name;
  private Dec def;
  private int level;

  public NodeType(String name, Dec def, int level) {
    this.name = name;
    this.def = def;
    this.level = level;
  }
}