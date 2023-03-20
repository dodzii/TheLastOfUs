package engine;

import java.util.*;
import java.io.*;
import model.characters.*;
import model.world.Cell;

public class Game {
	
	public static ArrayList<Hero> availableHeroes;
	public static ArrayList<Hero> heroes;
	public static ArrayList<Zombie> zombies;
	public static Cell [][] map;
	
	
	public static void loadHeroes(String filePath) throws Exception {
		
		String currentLine = "";
		FileReader fileReader= new FileReader(filePath);
		BufferedReader br = new BufferedReader(fileReader);
		while ((currentLine = br.readLine()) != null) {
			String[]h=currentLine.split(",");
			if(h[1].equals("FIGH")) {
				Fighter o = new Fighter(h[0],Integer.parseInt(h[2]),Integer.parseInt(h[3]),Integer.parseInt(h[4]));
				availableHeroes.add(o);
			}
			else if(h[1].equals("MED")) {
				Medic o = new Medic(h[0],Integer.parseInt(h[2]),Integer.parseInt(h[3]),Integer.parseInt(h[4]));
				availableHeroes.add(o);
			}
			else {
				Explorer o = new Explorer(h[0],Integer.parseInt(h[2]),Integer.parseInt(h[3]),Integer.parseInt(h[4]));
				availableHeroes.add(o);
			}
		}
    }
	public static void main(String[] args) throws Exception{
		
	}
}

