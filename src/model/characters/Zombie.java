package model.characters;

import engine.Game;
import exceptions.InvalidTargetException;
import exceptions.NotEnoughActionsException;
import model.world.Cell;
import model.world.CharacterCell;

public class Zombie extends Character{
	private static int ZOMBIES_COUNT =1;
	
	//Constructors
	public Zombie() {
		super(("Zombie "+ZOMBIES_COUNT),40,10);
		ZOMBIES_COUNT++;
	}

	//Getters & Setters
	public static int getZOMBIES_COUNT() {
		return ZOMBIES_COUNT;
	}
	
	
	
	@Override
	public void attack() throws InvalidTargetException, NotEnoughActionsException {
//		for(int i=0;i<15;i++) {
//			for(int j=0;j<15;j++) {
//				Cell cell = Game.map[i][j];
//				if(cell instanceof CharacterCell && ((CharacterCell)cell).getCharacter() instanceof Zombie) {
//					Zombie z =(Zombie)((CharacterCell)cell).getCharacter();
		
		Zombie z =this;		
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
//				}
//			}
//		}
		if(this.getTarget() instanceof Hero) {
			super.attack();
			}
			else {
				throw new InvalidTargetException();
			}
	}

	public void onCharacterDeath() {
		super.onCharacterDeath();
		Game.zombies.remove(this);
		Game.respawnZombie();
	}
}
