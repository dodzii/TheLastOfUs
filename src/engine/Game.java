package engine;

import java.util.*;
import java.io.*;
import model.characters.*;
import model.collectibles.*;
import model.world.*;

public class Game {
	
	public static ArrayList<Hero> availableHeroes = new ArrayList<Hero>();
	public static ArrayList<Hero> heroes = new ArrayList<Hero>();
	public static ArrayList<Zombie> zombies = new ArrayList<Zombie>();
	public static Cell [][] map = new Cell[15][15];
	
	
	public static void loadHeroes(String filePath) throws Exception {
		
		String currentLine = "";
		FileReader fileReader= new FileReader(filePath);
		BufferedReader br = new BufferedReader(fileReader);
		while ((currentLine = br.readLine()) != null) {
			String[]h=currentLine.split(",");
			if(Integer.parseInt(h[2])<=0)
				continue;
			if((!h[1].equals("FIGH"))&&(!h[1].equals("MED"))&&(!h[1].equals("EXP")))
				continue;
			if(h[1].equals("FIGH")) {
				Fighter o = new Fighter(h[0],Integer.parseInt(h[2]),Integer.parseInt(h[4]),Integer.parseInt(h[3]));
				availableHeroes.add(o);
			}
			else if(h[1].equals("MED")) {
				Medic o = new Medic(h[0],Integer.parseInt(h[2]),Integer.parseInt(h[4]),Integer.parseInt(h[3]));
				availableHeroes.add(o);
			}
			else {
				Explorer o = new Explorer(h[0],Integer.parseInt(h[2]),Integer.parseInt(h[4]),Integer.parseInt(h[3]));
				availableHeroes.add(o);
			}
		}
    }

    public static void startGame(Hero h) {
    	
		map[0][0]=new CharacterCell(h);
		availableHeroes.remove(h);
		heroes.add(h);
		int i=0;
		while(i<5) {
			int x = (int)(Math.random()*15);
			int y = (int)(Math.random()*15);
			if(map[x][y]==null) {
				map[x][y]=new CollectibleCell(new Vaccine());
				i++;
				if((x==1&&y==1)||(x==0&&y==1)||(x==1&&y==0)) {
					map[x][y].setVisible(true);
				}
			}
		}
		i=0;
		while(i<5) {
			int x = (int)(Math.random()*15);
			int y = (int)(Math.random()*15);
			if(map[x][y]==null) {
				map[x][y]=new CollectibleCell(new Supply());
				i++;
				if((x==1&&y==1)||(x==0&&y==1)||(x==1&&y==0)) {
					map[x][y].setVisible(true);
				}
			}
		}
		i=0;
		while(i<5) {
			int x = (int)(Math.random()*15);
			int y = (int)(Math.random()*15);
			if(map[x][y]==null) {
				map[x][y]=new TrapCell();
				i++;
				if((x==1&&y==1)||(x==0&&y==1)||(x==1&&y==0)) {
					map[x][y].setVisible(true);
				}
			}
		}
		i=0;
		while(i<10) {
			int x = (int)(Math.random()*15);
			int y = (int)(Math.random()*15);
			if(map[x][y]==null) {
				Zombie z = new Zombie();
				map[x][y]=new CharacterCell(z);
				zombies.add(z);
				i++;
				if((x==1&&y==1)||(x==0&&y==1)||(x==1&&y==0)) {
					map[x][y].setVisible(true);
				} 
			}
		}
    }
	public static boolean checkGameOver() {
		if(heroes.size()==0)
			return true;
		for(int i=0;i<15;i++) {
			for(int j=0;j<15;j++) {
				if(map[i][j] instanceof CharacterCell) {
					CharacterCell cell = (CharacterCell)map[i][j];
					if(cell.getCharacter() instanceof Hero) {
						Hero h = (Hero) cell.getCharacter();
						if(h.getVaccineInventory().size()!=0) {
							return false;
						}
					}
				}
				if(map[i][j] instanceof CollectibleCell) {
					CollectibleCell cell =(CollectibleCell)map[i][j];
					if(cell.getCollectible() instanceof Vaccine) {
						return false;
					}
					
				}
			}
		}
		return true;
	}
	public static boolean checkWin() {
		for(int i=0;i<15;i++) {
			for(int j=0;j<15;j++) {
				Cell temp=map[i][j];
				if(temp instanceof CollectibleCell &&((CollectibleCell)temp).getCollectible() instanceof Vaccine) {
					return false;
				}
			}
		}
		for(Hero x:heroes) {
			if(!x.getVaccineInventory().isEmpty()) {
				return false;
			}
		}
		return((heroes.size()>=5));
	}
	public static void endTurn() {
		setAllInvisible();
		for(int i=0;i<15;i++) {
			for(int j=0;j<15;j++) {
				Cell temp=map[i][j];
				if(temp instanceof CharacterCell && ((CharacterCell)temp).getCharacter() instanceof Hero) {
					Hero h=(Hero) ((CharacterCell)temp).getCharacter();
					h.setActionsAvailable(h.getMaxActions());
					h.setTarget(null);
					h.setSpecialAction(false);;
					h.assignVisibilityAround();
				}
			}
		}
		respawnZombie();
	}
	public static void setAllInvisible(){
		for(int i=0;i<15;i++) {
			for(int j=0;j<15;j++) {
				Cell cell = map[i][j];
				if(cell instanceof CharacterCell) {
						CharacterCell cell2= (CharacterCell)cell;
					if(cell2.getCharacter() instanceof Hero) {
						continue;
					}
				}
				else if(cell!=null) {
					cell.setVisible(false);
				}
			}
		}
	}
	public static void respawnZombie() {
		while(true) {
			int x = (int)(Math.random()*15);
			int y = (int)(Math.random()*15);
			if(map[x][y]==null) {
				Zombie z = new Zombie();
				map[x][y] = new CharacterCell(z);
				zombies.add(z);
				break;
			}
		}
	}
}

