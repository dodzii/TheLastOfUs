package engine;

import java.util.*;

import exceptions.InvalidTargetException;
import exceptions.NotEnoughActionsException;

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
		for(i=0;i<15;i++) {
			for(int j=0;j<15;j++) {
				if(map[i][j]==null) {
					map[i][j]=new CharacterCell(null);
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
	public static void endTurn() throws InvalidTargetException, NotEnoughActionsException {
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
		for(int i=0;i<15;i++) {
			for(int j=0;j<15;j++) {
				Cell cell = map[i][j];
				if(cell instanceof CharacterCell && ((CharacterCell)cell).getCharacter() instanceof Zombie) {
					Zombie z =(Zombie)((CharacterCell)cell).getCharacter();
					int x = z.getLocation().x;
					int y = z.getLocation().y;
					int l = x-1;
					int r = x+1;
					int u = y+1;
					int d = y-1;
					if((u>=0&&u<=14)&& map[x][u] instanceof CharacterCell && ((CharacterCell)map[x][u]).getCharacter() instanceof Hero) {
						Hero h = (Hero)((CharacterCell)map[x][u]).getCharacter();
						z.setTarget(h);
						z.attack();
					}
					else if((d>=0&&d<=14)&& map[x][d] instanceof CharacterCell && ((CharacterCell)map[x][d]).getCharacter() instanceof Hero) {
						Hero h = (Hero)((CharacterCell)map[x][d]).getCharacter();
						z.setTarget(h);
						z.attack();
					}
					else if((l>=0&&l<=14)&& map[l][y] instanceof CharacterCell && ((CharacterCell)map[l][y]).getCharacter() instanceof Hero) {
						Hero h = (Hero)((CharacterCell)map[l][y]).getCharacter();
						z.setTarget(h);
						z.attack();
					}
					else if((r>=0&&r<=14)&& map[r][y] instanceof CharacterCell && ((CharacterCell)map[r][y]).getCharacter() instanceof Hero) {
						Hero h = (Hero)((CharacterCell)map[r][y]).getCharacter();
						z.setTarget(h);
						z.attack();
					}
					else if((u>=0&&u<=14)&&(l>=0&&l<=14)&& map[l][u] instanceof CharacterCell && ((CharacterCell)map[l][u]).getCharacter() instanceof Hero) {
						Hero h = (Hero)((CharacterCell)map[l][u]).getCharacter();
						z.setTarget(h);
						z.attack();
					}
					else if((u>=0&&u<=14)&&(r>=0&&r<=14)&& map[r][u] instanceof CharacterCell && ((CharacterCell)map[r][u]).getCharacter() instanceof Hero) {
						Hero h = (Hero)((CharacterCell)map[r][u]).getCharacter();
						z.setTarget(h);
						z.attack();
					}
					else if((d>=0&&d<=14)&&(l>=0&&l<=14)&& map[l][d] instanceof CharacterCell && ((CharacterCell)map[l][d]).getCharacter() instanceof Hero) {
						Hero h = (Hero)((CharacterCell)map[l][d]).getCharacter();
						z.setTarget(h);
						z.attack();
					}
					else if((d>=0&&d<=14)&&(r>=0&&r<=14)&& map[r][d] instanceof CharacterCell && ((CharacterCell)map[r][d]).getCharacter() instanceof Hero) {
						Hero h = (Hero)((CharacterCell)map[r][d]).getCharacter();
						z.setTarget(h);
						z.attack();
					}
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
				else if(cell!=null){
					cell.setVisible(false);
				}
			}
		}
	}
	public static void respawnZombie() {
		while(true) {
			int x = (int)(Math.random()*15);
			int y = (int)(Math.random()*15);
			if(map[x][y] instanceof CharacterCell && ((CharacterCell)map[x][y]).getCharacter()==null) {
				Zombie z = new Zombie();
				((CharacterCell)map[x][y]).setCharacter(z);
				zombies.add(z);
				break;
			}
		}
	}
}

