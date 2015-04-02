import java.awt.*;


public class Wall {

	private Player player;
	private Boolean present;	
	private Box first;
	private Box second;

	public Wall(){
		player = Player.NONE;
		present = false;
		first = null;
		second = null;

	}

	public boolean getPresent(){
		return present;
	}
	public void setPresent(){
		present = true;
	}

	public Player playerGetter(){
		return player;
	}

	public void setWallOwner(Player a){
		player = a;
	}

}
