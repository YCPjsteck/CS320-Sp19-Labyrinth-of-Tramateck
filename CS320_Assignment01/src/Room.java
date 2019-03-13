import java.util.ArrayList;

public class Room {
	ArrayList<String> monsters;
	ArrayList<Monster> monsterList;
	String roomDescription;
	int roomID;
	
	// TODO: 
	//		Add the ability for rooms to contain puzzles
	//		Long description vs short description
	
	public Room() {
		monsters = new ArrayList<String>();
		monsterList = new ArrayList<Monster>();
	}
	
	/**
	 * A set of methods for setting and querying the
	 * room's ID number and description
	 */
	public void setID(int ID) {
		roomID = ID;
	}
	public int getID() {
		return roomID;
	}
	public void setDescription(String description) {
		roomDescription = description;
	}
	public String getDescription() {
		// return roomDescription;
		String description = "This room contains: ";
		for(String s : monsters) 
			description += s + " ";
		/*
		for(Monster m : monsterList)
			if(!m.isDead())
				description += m.getName() + " "; 
				*/
		if(description.equals("This room contains: "))
			description += "nothing";
		
		return description;
	}
	
	// TODO: randomly populate the monsters list
	public void populate(ArrayList<String> list) {
		monsters.addAll(list);
		int id = 1;
		for(String s : monsters) {
			Monster monster = new Monster(1);
			monster.setID(id);
			monster.setName(s);
			id++;
		}
	}
	// TODO: return a list of monster objects
	public ArrayList<String> getMonsters() {
		return monsters;
	}
	public Monster getMonster(int id) {
		for(Monster m : monsterList)
			if(m.getID() == id)
				return m;
		
		return null;
	}
	public Monster getMonster(String name) {
		for(Monster m : monsterList)
			if(m.getName().equals(name))
				return m;
		
		return null;
	}
	// TODO: be able to track specific monsters in a room by an ID number,
	// meaning that two monsters can share the same name in a room.
	public void monsterKilled(String monster) {
		for(int i = 0; i < monsters.size(); i++)
			if(monster.equals(monsters.get(i))) {
				monsters.remove(i);
				return;
			}
	}
	
	/**
	 * The room is complete is there are no more monsters left alive.
	 */
	public boolean roomComplete() {
		/*
		for(Monster m : monsterList)
			if(!m.isDead())
				return false;
		return true;
		*/
		return (monsters.size() == 0);
	}
}
