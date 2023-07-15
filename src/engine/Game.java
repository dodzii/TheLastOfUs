package engine;

import java.util.*;

import exceptions.InvalidTargetException;
import exceptions.NotEnoughActionsException;

import java.awt.Point;
import java.io.*;

import model.characters.*;
import model.collectibles.*;
import model.world.*;

public class Game{

	public static ArrayList<Hero> availableHeroes = new ArrayList<Hero>();
	public static ArrayList<Hero> heroes = new ArrayList<Hero>();
	public static ArrayList<Zombie> zombies = new ArrayList<Zombie>();
	public static Cell[][] map = new Cell[15][15];

	public static void loadHeroes(String filePath) throws Exception {
		String currentLine = "";
		FileReader fileReader = new FileReader(filePath);
		BufferedReader br = new BufferedReader(fileReader);
		while ((currentLine = br.readLine()) != null) {
			String[] h = currentLine.split(",");
			if (Integer.parseInt(h[2]) <= 0)
				continue;
			if ((!h[1].equals("FIGH")) && (!h[1].equals("MED")) && (!h[1].equals("EXP")))
				continue;
			if (h[1].equals("FIGH")) {
				Fighter o = new Fighter(h[0], Integer.parseInt(h[2]), Integer.parseInt(h[4]), Integer.parseInt(h[3]));
				availableHeroes.add(o);
			} else if (h[1].equals("MED")) {
				Medic o = new Medic(h[0], Integer.parseInt(h[2]), Integer.parseInt(h[4]), Integer.parseInt(h[3]));
				availableHeroes.add(o);
			} else {
				Explorer o = new Explorer(h[0], Integer.parseInt(h[2]), Integer.parseInt(h[4]), Integer.parseInt(h[3]));
				availableHeroes.add(o);
			}
		}
		br.close();
	}

	public static void startGame(Hero h) {
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {
				map[i][j] = new CharacterCell(null);
			}
		}
		heroes.add(h);
		map[0][0] = new CharacterCell(h);
		((CharacterCell) map[0][0]).setCharacter(h);
		h.setLocation(new Point(0, 0));
		availableHeroes.remove(h);
		addZombies();
		addVaccines();
		addSupplies();
		generateTrapCells();
		h.assignVisibilityAround();
	}

	public static boolean checkGameOver() {
		if (heroes.size() == 0){
			return true;
		}
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {
				if (map[i][j] instanceof CharacterCell) {
					CharacterCell cell = (CharacterCell) map[i][j];
					if (cell.getCharacter() instanceof Hero) {
						Hero h = (Hero) cell.getCharacter();
						if (h.getVaccineInventory().size() != 0) {
							return false;
						}
					}
				}
				if (map[i][j] instanceof CollectibleCell) {
					CollectibleCell cell = (CollectibleCell) map[i][j];
					if (cell.getCollectible() instanceof Vaccine) {
						return false;
					}

				}
			}
		}
		return true;
	}

	public static boolean checkWin() {
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {
				Cell temp = map[i][j];
				if (temp instanceof CollectibleCell && ((CollectibleCell) temp).getCollectible() instanceof Vaccine) {
					return false;
				}
			}
		}
		for (Hero x : heroes) {
			if (!x.getVaccineInventory().isEmpty()) {
				return false;
			}
		}
		return ((heroes.size() >= 5));
	}

	public static void endTurn() throws InvalidTargetException, NotEnoughActionsException {
		setAllInvisible();

		ArrayList<Zombie>zombiesTmp=new ArrayList<Zombie>();
		for(int i=0;i<zombies.size();i++) {
			zombiesTmp.add(zombies.get(i));
		}

		for (Zombie z : zombiesTmp) {
			z.attack();
		}

		respawnZombie();
		for (Zombie z : zombies) {
			z.setTarget(null);
		}
		for (Hero x : heroes) {
			x.setActionsAvailable(x.getMaxActions());
			x.setTarget(null);
			x.setSpecialAction(false);
			x.assignVisibilityAround();
		}
	}

	public static void addZombies() {
		for (int i = 0; i < 10; i++) {
			respawnZombie();
		}
	}

	public static void addVaccines() {
		for (int i = 0; i < 5;) {
			int x = (int) (Math.random() * 15);
			int y = (int) (Math.random() * 15);
			if ((map[x][y] instanceof CharacterCell) && (((CharacterCell) map[x][y]).getCharacter() == null)) {
				map[x][y] = new CollectibleCell(new Vaccine());
				i++;
			}
		}
	}

	public static void addSupplies() {
		for (int i = 0; i < 5;) {
			int x = (int) (Math.random() * 15);
			int y = (int) (Math.random() * 15);
			if ((map[x][y] instanceof CharacterCell) && (((CharacterCell) map[x][y]).getCharacter() == null)) {
				map[x][y] = new CollectibleCell(new Supply());
				i++;
			}
		}
	}

	public static void generateTrapCells() {
		for (int i = 0; i < 5;) {
			int x = (int) (Math.random() * 15);
			int y = (int) (Math.random() * 15);
			if ((map[x][y] instanceof CharacterCell) && (((CharacterCell) map[x][y]).getCharacter() == null)) {
				map[x][y] = new TrapCell();
				i++;
			}
		}
	}

	public static void setAllInvisible() {
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {
				Cell cell = map[i][j];
				cell.setVisible(false);
			}
		}
	}

	public static void respawnZombie() {
		if (!isMapFull()) {
			while (true) {
				int x = (int) (Math.random() * 15);
				int y = (int) (Math.random() * 15);
				if (map[x][y] instanceof CharacterCell && ((CharacterCell) map[x][y]).getCharacter() == null) {
					Zombie z = new Zombie();
					((CharacterCell) map[x][y]).setCharacter(z);
					z.setLocation(new Point(x, y));
					zombies.add(z);
					break;
				}
			}
		}
	}
	
	public static void respawnZombie(Point locationDead) {
		if (!isMapFull()) {
			if(isThereAnotherPlace(locationDead)) {
				while (true) {
					int x = (int) (Math.random() * 15);
					int y = (int) (Math.random() * 15);
					if (map[x][y] instanceof CharacterCell && ((CharacterCell) map[x][y]).getCharacter() == null
							&& (x != locationDead.x && y != locationDead.y)) {
						Zombie z = new Zombie();
						((CharacterCell) map[x][y]).setCharacter(z);
						z.setLocation(new Point(x, y));
						zombies.add(z);
						break;
					}
				}
			}
			else {
				Zombie z = new Zombie();
				((CharacterCell) map[locationDead.x][locationDead.y]).setCharacter(z);
				z.setLocation(new Point(locationDead.x, locationDead.y));
				zombies.add(z);
			}
		}
	}
	public static boolean isMapFull() {
		for(int i=0;i<15;i++) {
			for(int j=0;j<15;j++) {
				if(map[i][j] instanceof CharacterCell && ((CharacterCell)map[i][j]).getCharacter()==null) {
					return false;
				}
			}
		}
		return true;
	}
	public static boolean isThereAnotherPlace(Point locationDead) {
		for(int i=0;i<15;i++) {
			for(int j=0;j<15;j++) {
				if(i==locationDead.x&&j==locationDead.y)
					continue;
				if(map[i][j] instanceof CharacterCell && ((CharacterCell)map[i][j]).getCharacter()==null) {
					return true;
				}	
			}
		}
		return false;
	}

}
