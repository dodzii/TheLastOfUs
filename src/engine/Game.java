package engine;

import java.util.*;
import java.io.*;
import model.characters.*;
import model.world.Cell;

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
	
}

