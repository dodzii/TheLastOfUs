package engine;

import java.util.*;

import exceptions.InvalidTargetException;
import exceptions.NotEnoughActionsException;

import java.awt.Point;
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
//		map[0][0]=new CharacterCell(h);
		int i=0;
		for(i=0;i<15;i++) {
			for(int j=0;j<15;j++) {
					map[i][j]=new CharacterCell(null);
			}
		}
		heroes.add(h);
		map[0][0]=new CharacterCell(h);
		((CharacterCell)map[0][0]).setCharacter(h);
		h.setLocation(new Point(0,0));
		availableHeroes.remove(h);
		i=0;
		while(i<5) {
			int x = (int)(Math.random()*15);
			int y = (int)(Math.random()*15);
			if((map[x][y] instanceof CharacterCell) && (((CharacterCell)map[x][y]).getCharacter() == null)) {
				map[x][y]=new CollectibleCell(new Vaccine());
				i++;
			}
		}
		i=0;
		while(i<5) {
			int x = (int)(Math.random()*15);
			int y = (int)(Math.random()*15);
			if((map[x][y] instanceof CharacterCell) && (((CharacterCell)map[x][y]).getCharacter() == null)) {
				map[x][y]=new CollectibleCell(new Supply());
				i++;	
			}
		}
		i=0;
		while(i<5) {
			int x = (int)(Math.random()*15);
			int y = (int)(Math.random()*15);
			if((map[x][y] instanceof CharacterCell) && (((CharacterCell)map[x][y]).getCharacter() == null)) {
				map[x][y]=new TrapCell();
				i++;				
			}
		}
		i=0;
		while(i<10) {
			int x = (int)(Math.random()*15);
			int y = (int)(Math.random()*15);
			if((map[x][y] instanceof CharacterCell) && (((CharacterCell)map[x][y]).getCharacter() == null)) {
				Zombie z = new Zombie();
				map[x][y]=new CharacterCell(z);
				zombies.add(z);
				z.setLocation(new Point(x,y));
				i++;				 
			}
		}
		
//		map[0][0]=new CharacterCell(h);
//		((CharacterCell)map[0][0]).setCharacter(h);
//		h.setLocation(new Point(0,0));
		map[0][0].setVisible(true);
		map[1][1].setVisible(true);
		map[0][1].setVisible(true);
		map[1][0].setVisible(true);
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
	public static void endTurn()  {
		setAllInvisible();
		for(Hero x : heroes) {
			x.setActionsAvailable(x.getMaxActions());
			x.setTarget(null);
			x.setSpecialAction(false);
			x.assignVisibilityAround();
		}
		for(Zombie z : zombies) {
			int x = z.getLocation().x;
			int y = z.getLocation().y;
			int l = x-1;
			int r = x+1;
			int u = y+1;
			int d = y-1;
			if((u>=0&&u<=14)&& Game.map[x][u] instanceof CharacterCell && ((CharacterCell)Game.map[x][u]).getCharacter()!=null && ((CharacterCell)Game.map[x][u]).getCharacter() instanceof Hero) {
				Hero h = (Hero)((CharacterCell)Game.map[x][u]).getCharacter();
				z.setTarget(h);
			}
			else if((d>=0&&d<=14)&& Game.map[x][d] instanceof CharacterCell && ((CharacterCell)Game.map[x][d]).getCharacter()!=null && ((CharacterCell)Game.map[x][d]).getCharacter() instanceof Hero) {
				Hero h = (Hero)((CharacterCell)Game.map[x][d]).getCharacter();
				z.setTarget(h);					
			}
			else if((l>=0&&l<=14)&& Game.map[l][y] instanceof CharacterCell && ((CharacterCell)Game.map[l][y]).getCharacter()!=null && ((CharacterCell)Game.map[l][y]).getCharacter() instanceof Hero) {
				Hero h = (Hero)((CharacterCell)Game.map[l][y]).getCharacter();
				z.setTarget(h);
									
			}
			else if((r>=0&&r<=14)&& Game.map[r][y] instanceof CharacterCell && ((CharacterCell)Game.map[r][y]).getCharacter()!=null &&((CharacterCell)Game.map[r][y]).getCharacter() instanceof Hero) {
				Hero h = (Hero)((CharacterCell)Game.map[r][y]).getCharacter();
				z.setTarget(h);
										
			}
			else if((u>=0&&u<=14)&&(l>=0&&l<=14)&& Game.map[l][u] instanceof CharacterCell && ((CharacterCell)Game.map[l][u]).getCharacter()!=null && ((CharacterCell)Game.map[l][u]).getCharacter() instanceof Hero) {
				Hero h = (Hero)((CharacterCell)Game.map[l][u]).getCharacter();
				z.setTarget(h);						
			}
			else if((u>=0&&u<=14)&&(r>=0&&r<=14)&& Game.map[r][u] instanceof CharacterCell && ((CharacterCell)Game.map[r][u]).getCharacter()!=null && ((CharacterCell)Game.map[r][u]).getCharacter() instanceof Hero) {
				Hero h = (Hero)((CharacterCell)Game.map[r][u]).getCharacter();
				z.setTarget(h);
									
			}
			else if((d>=0&&d<=14)&&(l>=0&&l<=14)&& Game.map[l][d] instanceof CharacterCell && ((CharacterCell)Game.map[l][d]).getCharacter()!=null &&((CharacterCell)Game.map[l][d]).getCharacter() instanceof Hero) {
				Hero h = (Hero)((CharacterCell)Game.map[l][d]).getCharacter();
				z.setTarget(h);
								
			}
			else if((d>=0&&d<=14)&&(r>=0&&r<=14)&& Game.map[r][d] instanceof CharacterCell && ((CharacterCell)Game.map[r][d]).getCharacter() != null && ((CharacterCell)Game.map[r][d]).getCharacter() instanceof Hero) {
				Hero h = (Hero)((CharacterCell)Game.map[r][d]).getCharacter();
				z.setTarget(h);
				
			}
			if(z.getTarget()!=null) {
			try {
				z.attack();
			} catch (InvalidTargetException e) {
				e.printStackTrace();
			} catch (NotEnoughActionsException e) {
				e.printStackTrace();
			}
		}
		}
		
		respawnZombie();
		for(Zombie z:zombies) {	
		z.setTarget(null);
		}
	}
	public static void setAllInvisible(){
		for(int i=0;i<15;i++) {
			for(int j=0;j<15;j++) {
				Cell cell = map[i][j];
				cell.setVisible(false);
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
				z.setLocation(new Point(x,y));
				zombies.add(z);
				break;
			}
		}
	}
}

