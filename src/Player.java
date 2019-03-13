import java.util.ArrayList;

public class Player {
	ArrayList<Item> inventory, equipment;
	int playerHealth;
	
	public Player() {
		playerHealth = 100;
		inventory = new ArrayList<Item>();
		equipment = new ArrayList<Item>();
	}
	
	public int attack() {
		return 10;
	}
	
	public void changeHealth(int change) {
		playerHealth += change;
	}
	
	public void addItem(Item item) {
		inventory.add(item);
	}
	
	public boolean isDead() {
		return (playerHealth <= 0);
	}
}
