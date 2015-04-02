
public class Box {

	private Wall north = new Wall();
	private Wall south = new Wall();
	private Wall east = new Wall();
	private Wall west = new Wall();
	private Player owner;
	private int boxSize = 100;

	public Box(){
		owner = Player.NONE;
		south = null;
		north = null;
		east = null;
		west = null;
	}

	public Wall regionGetter(int rx, int ry){
		if (rx > ry){
			if (rx < boxSize-ry){
				return north;
			}
			else{
				return east;
			}
		}
		else if (rx < boxSize-ry){
			return west;
		}
		else{
			return south;
		}
	}

	public boolean checkOwned(){
		if (north.getPresent() && south.getPresent() && east.getPresent() && west.getPresent()){
			return true;
		}
		return false;
	}

	public Wall nGetter(){
		return north;
	}
	public Wall sGetter(){
		return south;
	}
	public Wall eGetter(){
		return east;
	}
	public Wall wGetter(){
		return west;
	}
	public Player ownerGetter(){
		return owner;
	}
	public void setBoxOwner(Player a){
		owner = a;
	}
	public void setNorth(Wall wall){
		north = wall;
	}
	public void setSouth(Wall wall){
		south = wall;
	}
	public void setEast(Wall wall){
		east = wall;
	}
	public void setWest(Wall wall){
		west = wall;
	}
}
