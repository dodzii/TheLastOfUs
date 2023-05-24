package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.Point;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import org.junit.Test;

import engine.Game;
import model.characters.Character;
import model.characters.Hero;
import model.characters.Zombie;
import model.collectibles.Supply;
import model.world.Cell;
import model.world.CharacterCell;
import model.world.TrapCell;

public class M2PrivateTests {

	String characterPath = "model.characters.Character";
	String collectiblePath = "model.collectibles.Collectible";
	String vaccinePath = "model.collectibles.Vaccine";
	String supplyPath = "model.collectibles.Supply";
	String fighterPath = "model.characters.Fighter";

	private String charCell = "model.world.CharacterCell";
	private String cellPath = "model.world.Cell";
	private String collectibleCellPath = "model.world.CollectibleCell";
	private String trapCellPath = "model.world.TrapCell";
	private String enumDirectionPath = "model.characters.Direction";

	String gamePath = "engine.Game";
	String medicPath = "model.characters.Medic";
	String explorerPath = "model.characters.Explorer";
	String heroPath = "model.characters.Hero";

	String gameActionExceptionPath = "exceptions.GameActionException";
	String invalidTargetExceptionPath = "exceptions.InvalidTargetException";
	String movementExceptionPath = "exceptions.MovementException";
	String noAvailableResourcesExceptionPath = "exceptions.NoAvailableResourcesException";
	String notEnoughActionsExceptionPath = "exceptions.NotEnoughActionsException";

	String zombiePath = "model.characters.Zombie";
	

	
	String directionPath = "model.characters.Direction";
	

	//Caroline
	
	@Test(timeout = 3000)
	  public void testSupplyImplementsCollectible() {
	    try {
	      testClassImplementsInterface(
	        Class.forName(supplyPath),
	        Class.forName(collectiblePath)
	      );
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	  }

	  @Test(timeout = 3000)
	  public void testPickUpMethodLogicInSupply() {
	    int random = (int) (Math.random() * 1000);
	    String nameHero = "Fighter " + random;
	    int maxHpHero = (int) (Math.random() * 100);
	    int attackDmgHero = (int) (Math.random() * 100);
	    int maxActionsHero = (int) (Math.random() * 5);
	    try {
	      Constructor<?> constructorHero = Class
	        .forName(medicPath)
	        .getConstructor(String.class, int.class, int.class, int.class);
	      Object hero = constructorHero.newInstance(
	        nameHero,
	        maxHpHero,
	        attackDmgHero,
	        maxActionsHero
	      );
	      Constructor<?> constructorSupply = Class
	        .forName(supplyPath)
	        .getConstructor();
	      Object supply = constructorSupply.newInstance();
	      Method method = Class
	        .forName(supplyPath)
	        .getDeclaredMethod("pickUp", Class.forName(heroPath));
	      method.invoke(supply, hero);
	      // Check supply inventory
	      Class<?> classHero = Class.forName(heroPath);
	      Field supplyInventory = classHero.getDeclaredField("supplyInventory");
	      supplyInventory.setAccessible(true);
	      ArrayList supplyObject = new ArrayList<>();
	      supplyObject.add(supply);
	      assertEquals(
	        "The field supply inventory should not include a supply instance after calling use method",
	        supplyObject,
	        supplyInventory.get(hero)
	      );
	    } catch (NoSuchFieldException e) {
	      assertTrue(
	        "Class hero should have a field called supplyInventory",
	        false
	      );
	      e.printStackTrace();
	    } catch (ClassNotFoundException e) {
	      assertTrue(
	        "Package model.collectibles should contain a class called Supply.",
	        false
	      );
	    } catch (InstantiationException e) {
	      assertTrue("Objects of type hero can not be instantiated.", false);
	    } catch (NoSuchMethodException e) {
	      assertTrue(
	        "Class supply should contain a method called pickUp that takes a hero as a parameter.",
	        false
	      );
	    } catch (Exception e) {
	      assertTrue(
	        e.getClass().getName() + " occurred: " + e.getMessage(),
	        false
	      );
	      e.printStackTrace();
	    }
	  }

	  @Test(timeout = 3000)
	  public void testUseSpecialMethodLogicInMedic2() {
	    //Calling super useSpecial
	    try {
	      Constructor<?> medicsConstructor = Class
	        .forName(medicPath)
	        .getConstructor(String.class, int.class, int.class, int.class);
	      Object createdMedics1 = medicsConstructor.newInstance(
	        "Eminem",
	        1000,
	        50,
	        20
	      );
	      Constructor<?> zombieConstructor = Class
	        .forName(explorerPath)
	        .getConstructor(String.class, int.class, int.class, int.class);
	      Object zombieObject = zombieConstructor.newInstance(
	        "Eminem",
	        1000,
	        50,
	        20
	      );
	      Method setTarget = createdMedics1
	        .getClass()
	        .getMethod("setTarget", Character.class);
	      Method setLocation = createdMedics1
	        .getClass()
	        .getMethod("setLocation", Point.class);
	      setLocation.invoke(createdMedics1, new Point(0, 0));
	      setLocation.invoke(zombieObject, new Point(0, 0));

	      setTarget.invoke(createdMedics1, (Character) zombieObject);
	      Class<?> classHero = Class.forName(heroPath);
	      Field supplyInventory = classHero.getDeclaredField("supplyInventory");
	      supplyInventory.setAccessible(true);
	      Constructor<?> constructorSupply = Class
	        .forName(supplyPath)
	        .getConstructor();
	      Object supply = constructorSupply.newInstance();
	      ArrayList<Supply> supplyArray = new ArrayList<>();
	      supplyArray.add((Supply) supply);
	      supplyInventory.set(createdMedics1, supplyArray);
	      ArrayList<Supply> supplyArrayEmpty = new ArrayList<>();
	      Method useSpecialMethod = Class
	        .forName(medicPath)
	        .getDeclaredMethod("useSpecial");
	      useSpecialMethod.invoke(createdMedics1);
	      assertEquals(
	        "useSpecial method of Hero class has to be used in Explorer class with super keyword",
	        supplyInventory.get(createdMedics1),
	        supplyArrayEmpty
	      );
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	  }

	  //UseSpecial
	  @Test(timeout = 3000)
	  public void testInvalidUseSpecialNoAvailableResources() throws Exception {
	    int random = (int) (Math.random() * 1000);
	    String nameHero = "Fighter " + random;
	    int maxHpHero = (int) (Math.random() * 100);
	    int attackDmgHero = (int) (Math.random() * 100);
	    int maxActionsHero = (int) (Math.random() * 5) + 100;
	    generateMap();
	    Constructor<?> constructor = Class
	      .forName(fighterPath)
	      .getConstructor(String.class, int.class, int.class, int.class);
	    Constructor<?> constructor2 = Class.forName(zombiePath).getConstructor();
	    Object character2 = constructor2.newInstance();
	    Object character = constructor.newInstance(
	      nameHero,
	      maxHpHero,
	      attackDmgHero,
	      maxActionsHero
	    );
	    Method setLocation = character
	      .getClass()
	      .getMethod("setLocation", Point.class);
	    setLocation.invoke(character, new Point(4, 4));
	    Method setTarget = character
	      .getClass()
	      .getMethod("setTarget", Character.class);
	    setTarget.invoke(character, (Character) character2);
	    Game.zombies = new ArrayList<>();
	    Game.zombies.add((Zombie) character2);
	    Method setActions = character
	      .getClass()
	      .getMethod("setActionsAvailable", int.class);
	    setActions.invoke(character, 0);
	    Cell[][] m = engine.Game.map;
	    m[3][4] =
	      new CharacterCell(
	        (Character) constructor.newInstance(
	          nameHero,
	          maxHpHero,
	          attackDmgHero,
	          maxActionsHero
	        )
	      );
	    try {
	      Method m3 = Class.forName(heroPath).getDeclaredMethod("useSpecial");
	      m3.invoke(character);
	      fail(
	        "Trying to use a special action with no available resources, an exception should be thrown"
	      );
	    } catch (NoSuchMethodException e) {
	      fail("Hero class should have useSpecial method");
	    } catch (InvocationTargetException e) {
	      try {
	        if (
	          !(
	            Class
	              .forName(noAvailableResourcesExceptionPath)
	              .equals(e.getCause().getClass())
	          )
	        ) fail(
	          "Trying to use a special action with no available resources, an exception should be thrown"
	        );
	      } catch (ClassNotFoundException e1) {
	        fail("There should be a notEnoughActionsException class");
	      }
	    }
	  }

	  @SuppressWarnings("unchecked")
	  @Test(timeout = 3000)
	  public void testValidUseSpecialUpdateSupplyInventory() throws Exception {
	    int random = (int) (Math.random() * 1000);
	    String nameHero = "Fighter " + random;
	    int maxHpHero = (int) (Math.random() * 100);
	    int attackDmgHero = (int) (Math.random() * 100);
	    int maxActionsHero = (int) (Math.random() * 5) + 100;
	    generateMap();
	    Constructor<?> constructor = Class
	      .forName(fighterPath)
	      .getConstructor(String.class, int.class, int.class, int.class);
	    Object character = constructor.newInstance(
	      nameHero,
	      maxHpHero,
	      attackDmgHero,
	      maxActionsHero
	    );
	    Method setLocation = character
	      .getClass()
	      .getMethod("setLocation", Point.class);
	    setLocation.invoke(character, new Point(4, 4));
	    Constructor<?> constructor2 = Class.forName(zombiePath).getConstructor();
	    Object character2 = constructor2.newInstance();
	    Game.zombies = new ArrayList<>();
	    Game.zombies.add((Zombie) character2);
	    Method setTarget = character
	      .getClass()
	      .getMethod("setTarget", Character.class);
	    setTarget.invoke(character, (Character) character2);
	    setLocation.invoke(character2, new Point(3, 4));
	    Method setActions = character
	      .getClass()
	      .getMethod("setActionsAvailable", int.class);
	    setActions.invoke(character, 51);
	    Cell[][] m = engine.Game.map;
	    m[3][4] =
	      new CharacterCell(
	        (Character) constructor.newInstance(
	          nameHero,
	          maxHpHero,
	          attackDmgHero,
	          maxActionsHero
	        )
	      );

	    try {
	      Method getSupplyInventory = character
	        .getClass()
	        .getMethod("getSupplyInventory");
	      ArrayList<Supply> inventory1 = (ArrayList<Supply>) getSupplyInventory.invoke(
	        character
	      );
	      inventory1.add(new Supply());
	      Method m3 = Class.forName(heroPath).getDeclaredMethod("useSpecial");
	      m3.invoke(character);

	      ArrayList<Supply> inventory2 = (ArrayList<Supply>) getSupplyInventory.invoke(
	        character
	      );

	      assertTrue(
	        "The used supply should be removed from the hero's inventory. Expected " +
	        "empty arraylist but the arraylist size was " +
	        inventory2.size(),
	        0 == inventory2.size()
	      );
	    } catch (NoSuchMethodException e) {
	      fail("Hero class should have useSpecial method");
	    }
	  }

	  @SuppressWarnings("unchecked")
	  @Test(timeout = 3000)
	  public void testValidUseSpecialSetSpecialAction() throws Exception {
	    int random = (int) (Math.random() * 1000);
	    String nameHero = "Fighter " + random;
	    int maxHpHero = (int) (Math.random() * 100);
	    int attackDmgHero = (int) (Math.random() * 100);
	    int maxActionsHero = (int) (Math.random() * 5) + 100;
	    generateMap();
	    Constructor<?> constructor = Class
	      .forName(fighterPath)
	      .getConstructor(String.class, int.class, int.class, int.class);
	    Object character = constructor.newInstance(
	      nameHero,
	      maxHpHero,
	      attackDmgHero,
	      maxActionsHero
	    );
	    Method setLocation = character
	      .getClass()
	      .getMethod("setLocation", Point.class);
	    setLocation.invoke(character, new Point(4, 4));
	    Constructor<?> constructor2 = Class.forName(zombiePath).getConstructor();
	    Object character2 = constructor2.newInstance();
	    Game.zombies = new ArrayList<>();
	    Game.zombies.add((Zombie) character2);
	    Method setTarget = character
	      .getClass()
	      .getMethod("setTarget", Character.class);
	    setTarget.invoke(character, (Character) character2);
	    setLocation.invoke(character2, new Point(3, 4));
	    Method setActions = character
	      .getClass()
	      .getMethod("setActionsAvailable", int.class);
	    setActions.invoke(character, 51);
	    Cell[][] m = engine.Game.map;
	    m[3][4] =
	      new CharacterCell(
	        (Character) constructor.newInstance(
	          nameHero,
	          maxHpHero,
	          attackDmgHero,
	          maxActionsHero
	        )
	      );

	    Method getSupplyInventory = character
	      .getClass()
	      .getMethod("getSupplyInventory");
	    ArrayList<Supply> inventory1 = (ArrayList<Supply>) getSupplyInventory.invoke(
	      character
	    );
	    inventory1.add(new Supply());

	    try {
	      Method setSpecialAction = character
	        .getClass()
	        .getMethod("setSpecialAction", boolean.class);
	      setSpecialAction.invoke(character, false);
	    } catch (NoSuchMethodException e) {
	      fail("Hero class should have setSpecialAction method.");
	    }

	    try {
	      Method m3 = Class.forName(heroPath).getDeclaredMethod("useSpecial");
	      m3.invoke(character);
	    } catch (NoSuchMethodException e) {
	      fail("Hero class should have useSpecial method.");
	    }
	    try {
	      Method isSpecialActionM = character
	        .getClass()
	        .getMethod("isSpecialAction");
	      Boolean isSpecialAction = (Boolean) isSpecialActionM.invoke(character);

	      assertTrue(
	        "The boolean specialAction should be set to true. Expected true but was false",
	        true == isSpecialAction
	      );
	    } catch (NoSuchMethodException e) {
	      fail("Hero class should have isSpecialAction method.");
	    }
	  }

	  //Helpers
	  public ArrayList<Object> setEnvironment() {
	    try {
	      Class<?> classGame = Class.forName(gamePath);
	      //New Character type Fighter
	      int random = (int) (Math.random() * 1000);
	      String nameHero = "Fighter " + random;
	      int maxHpHero = (int) (Math.random() * 100);
	      int attackDmgHero = (int) (Math.random() * 100);
	      int maxActionsHero = (int) (Math.random() * 5);
	      Constructor<?> constructor = Class
	        .forName(fighterPath)
	        .getConstructor(String.class, int.class, int.class, int.class);
	      Object fighter = constructor.newInstance(
	        nameHero,
	        maxHpHero,
	        attackDmgHero,
	        maxActionsHero
	      );
	      //New Character Cell carrying a fighter
	      Constructor<?> constructorCharacterCell = Class
	        .forName(charCell)
	        .getConstructor(Class.forName(characterPath));
	      Object charCell = constructorCharacterCell.newInstance(fighter);

	      //New Zombie wooooooh
	      Constructor<?> constructorZombie = Class
	        .forName(zombiePath)
	        .getConstructor();
	      Object zombieObject = constructorZombie.newInstance();

	      //Fill available Heros
	      ArrayList<Object> medics = new ArrayList<Object>();
	      Constructor<?> medicsConstructor = Class
	        .forName(medicPath)
	        .getConstructor(String.class, int.class, int.class, int.class);
	      Object createdMedics1 = medicsConstructor.newInstance(
	        "ABC",
	        1000,
	        50,
	        20
	      );
	      Object createdMedics2 = medicsConstructor.newInstance(
	        "123",
	        5000,
	        500,
	        100
	      );
	      Object createdMedics3 = medicsConstructor.newInstance("XYZ", 50, 5, 1);
	      medics.add(createdMedics1);
	      medics.add(createdMedics2);
	      medics.add(createdMedics3);

	      Field availableHeros = classGame.getDeclaredField("availableHeroes");
	      availableHeros.setAccessible(true);
	      availableHeros.set(
	        (ArrayList<Object>) availableHeros.get(null),
	        new ArrayList<>()
	      );
	      availableHeros.set((ArrayList<Object>) availableHeros.get(null), medics);
	      //Set target.
	      ((Zombie) zombieObject).setLocation(new Point(1, 0));
	      for (Hero h : Game.availableHeroes) {
	        h.setTarget((Zombie) zombieObject);
	      }
	      // Initialize game map
	      Field gameMap = classGame.getDeclaredField("map");
	      gameMap.setAccessible(true);
	      Cell[][] map = new Cell[5][6];
	      map[0][5] = (Cell) charCell;
	      gameMap.set(gameMap.get(null), map);

	      //Fill Zombie Arraylist in Game class
	      ArrayList<Object> zombiesArray = new ArrayList<Object>();
	      zombiesArray.add(zombieObject);
	      Field zombies = classGame.getDeclaredField("zombies");
	      zombies.setAccessible(true);
	      zombies.set(zombies.get(null), zombiesArray);

	      ArrayList<Object> returns = new ArrayList<Object>();
	      returns.add(medics.get(0));
	      returns.add(zombieObject);
	      returns.add(charCell);
	      return returns;
	    } catch (
	      NoSuchFieldException
	      | SecurityException
	      | ClassNotFoundException
	      | IllegalArgumentException
	      | IllegalAccessException
	      | InvocationTargetException
	      | NoSuchMethodException
	      | InstantiationException e
	    ) {
	      e.printStackTrace();
	    }
	    return null;
	  }

	  //////////// Helper Method to Generate a map ///////
	  private void generateMap() {
	    Constructor<?> gameConstructor;
	    Field f = null;
	    try {
	      gameConstructor = Class.forName(gamePath).getConstructor();
	      Object createdGame = gameConstructor.newInstance();
	      Class<? extends Object> curr = createdGame.getClass();
	      f = curr.getDeclaredField("map");
	      f.setAccessible(true);
	      Cell[][] map = new Cell[15][15];
	      //New Character type Fighter
	      int random = (int) (Math.random() * 1000);
	      String nameHero = "Fighter " + random;
	      int maxHpHero = (int) (Math.random() * 100);
	      int attackDmgHero = (int) (Math.random() * 100);
	      int maxActionsHero = (int) (Math.random() * 5);
	      Constructor<?> constructor = Class
	        .forName(fighterPath)
	        .getConstructor(String.class, int.class, int.class, int.class);
	      Object fighter = constructor.newInstance(
	        nameHero,
	        maxHpHero,
	        attackDmgHero,
	        maxActionsHero
	      );
	      //New Character Cell carrying a fighter
	      Constructor<?> constructorCharacterCell = Class
	        .forName(charCell)
	        .getConstructor(Class.forName(characterPath));
	      constructorCharacterCell.newInstance(fighter);

	      for (int i = 0; i < map.length; i++) {
	        for (int j = 0; j < map[i].length; j++) {
	          map[i][j] = (Cell) constructorCharacterCell.newInstance(fighter);
	        }
	      }

	      f.set(createdGame, map);
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	  }

	  private static boolean containsMethodName(Method[] methods, String name) {
	    for (Method method : methods) {
	      if (method.getName().equals(name)) return true;
	    }
	    return false;
	  }

	  private boolean checkIfMapIsVisible() {
	    for (int i = 0; i < Game.map.length; i++) {
	      for (int j = 0; j < Game.map[i].length; j++) {
	        if (!Game.map[i][j].isVisible()) {
	          return false;
	        }
	      }
	    }
	    return true;
	  }

	  private void testClassImplementsInterface(Class<?> aClass, Class<?> iClass) {
	    assertTrue(
	      "Class \"" +
	      aClass.getSimpleName() +
	      "\" should implement \"" +
	      iClass.getSimpleName() +
	      "\" interface.",
	      iClass.isAssignableFrom(aClass)
	    );
	  }

	  private void testIsInterface(Class<?> aClass) {
	    assertTrue(
	      "\"" + aClass.getSimpleName() + "\" is an interface.",
	      Modifier.isInterface(aClass.getModifiers())
	    );
	  }

	  @SuppressWarnings({ "rawtypes", "unchecked" })
	  private void testMethodExistsInClass(
	    Class aClass,
	    String methodName,
	    boolean implementedMethod,
	    Class returnType,
	    Class... inputTypes
	  ) {
	    Method[] methods = aClass.getDeclaredMethods();
	    if (implementedMethod) {
	      assertTrue(
	        "The " +
	        methodName +
	        " method in class " +
	        aClass.getSimpleName() +
	        " should be implemented.",
	        containsMethodName(methods, methodName)
	      );
	    } else {
	      assertFalse(
	        "The " +
	        methodName +
	        " method in class " +
	        aClass.getSimpleName() +
	        " should not be implemented, only inherited from super class.",
	        containsMethodName(methods, methodName)
	      );
	      return;
	    }
	    Method m = null;
	    boolean found = true;
	    try {
	      m = aClass.getDeclaredMethod(methodName, inputTypes);
	    } catch (Exception e) {
	      found = false;
	    }
	    String inputsList = "";
	    for (Class inputType : inputTypes) {
	      inputsList += inputType.getSimpleName() + ", ";
	    }
	    if (inputsList.equals("")) assertTrue(
	      aClass.getSimpleName() +
	      " class should have " +
	      methodName +
	      " method that takes no parameters.",
	      found
	    ); else {
	      if (inputsList.charAt(inputsList.length() - 1) == ' ') inputsList =
	        inputsList.substring(0, inputsList.length() - 2);
	      assertTrue(
	        aClass.getSimpleName() +
	        " class should have " +
	        methodName +
	        " method that takes " +
	        inputsList +
	        " parameter(s).",
	        found
	      );
	    }
	    assertTrue(
	      "incorrect return type for " +
	      methodName +
	      " method in " +
	      aClass.getSimpleName() +
	      ".",
	      m.getReturnType().equals(returnType)
	    );
	  }
	  
	  //Nada
	  
		@Test(timeout = 3000)
		public void testCureExistsHero() {
			Method m = null;
			try {
				m = Class.forName(heroPath).getDeclaredMethod("cure");
			} catch (NoSuchMethodException e) {
				assertTrue(
						"Class \"Hero\" should contain a method called \"cure\" that takes no parameter.",
						false);
			} catch (SecurityException e) {
				assertTrue(e.getClass().getName() + " occurred: " + e.getMessage(), false);
			} catch (ClassNotFoundException e) {
				assertTrue("Package model.characters should contain a class called Hero.", false);
			}
			assertTrue("Method \"cure\" in class \"Hero\" should be void.", m.getReturnType().equals(void.class));

		}
		

	///////////////// Move Method Test Cases///////////// 
		
		@SuppressWarnings("unchecked")
		@Test(timeout = 3000)
		public void testInvalidBorderMoveUp() throws Exception {
			int random = (int) (Math.random() * 1000);
			String nameHero = "Fighter " + random;
			int maxHpHero = (int) (Math.random() * 100);
			int attackDmgHero = (int) (Math.random() * 100);
			int maxActionsHero = (int) (Math.random() * 5)+100;
			generateMap1();
			Constructor<?> constructor = Class.forName(fighterPath).getConstructor(String.class, int.class, int.class,
					int.class);
			Object character = constructor.newInstance(nameHero, maxHpHero, attackDmgHero, maxActionsHero);
			Method setLocation = character.getClass().getMethod("setLocation", Point.class);
			setLocation.invoke(character, new Point(14, 1));
			Method setActions = character.getClass().getMethod("setActionsAvailable", int.class);
			setActions.invoke(character,maxActionsHero );
			try {
				Method m3 = Class.forName(heroPath).getDeclaredMethod("move", Class.forName(directionPath));
				m3.invoke(character, Enum.valueOf((Class<Enum>) Class.forName(directionPath), "UP"));
				fail("Trying to move UP beyond a border, an exception should be thrown");

			}catch (NoSuchMethodException e) {
				fail("Hero class should have move method");
			} catch (InvocationTargetException e) {
				try {
					if (!(Class.forName(movementExceptionPath).equals(e.getCause().getClass())))
						fail("Trying to move beyond a border, an exception should be thrown");
					

				} catch (ClassNotFoundException e1) {
					 
					fail("There should be a MovementException class");
				}

			}

		}
		

		
		@SuppressWarnings("unchecked")
		@Test(timeout = 3000)
		public void testInvalidMoveLeftCharacterCell() throws Exception {
			int random = (int) (Math.random() * 1000);
			String nameHero = "Fighter " + random;
			int maxHpHero = (int) (Math.random() * 100);
			int attackDmgHero = (int) (Math.random() * 100);
			int maxActionsHero = (int) (Math.random() * 5)+100;
			generateMap1();
			Constructor<?> constructor = Class.forName(fighterPath).getConstructor(String.class, int.class, int.class,
					int.class);
			Object character = constructor.newInstance(nameHero, maxHpHero, attackDmgHero, maxActionsHero);
			Method setLocation = character.getClass().getMethod("setLocation", Point.class);
			setLocation.invoke(character, new Point(4, 4));
			Method setActions = character.getClass().getMethod("setActionsAvailable", int.class);
			setActions.invoke(character,maxActionsHero);
			Cell[][] m = engine.Game.map;
			m[4][3] = new CharacterCell((Character) constructor.newInstance(nameHero, maxHpHero, attackDmgHero, maxActionsHero)); 
			try {
				Method m3 = Class.forName(heroPath).getDeclaredMethod("move", Class.forName(directionPath));
				m3.invoke(character, Enum.valueOf((Class<Enum>) Class.forName(directionPath), "LEFT"));
				fail("Trying to move Left to an occupied cell, an exception should be thrown");

			}catch (NoSuchMethodException e) {
				fail("Hero class should have move method");
			} catch (InvocationTargetException e) {
				try {
					if (!(Class.forName(movementExceptionPath).equals(e.getCause().getClass())))
						fail("Trying to move Left to an occupied cell, an exception should be thrown");

				} catch (ClassNotFoundException e1) {
					 
					fail("There should be a MovementException class");
				}

			}

		}
		
		

		
		@SuppressWarnings("unchecked")
		@Test(timeout = 3000)
		public void testMoveDownTrapCell() throws Exception {
			int random = (int) (Math.random() * 1000);
			String nameHero = "Fighter " + random;
			int maxHpHero = 200;
			int attackDmgHero = (int) (Math.random() * 100);
			int maxActionsHero = (int) (Math.random() * 5)+100;
			generateMap1();
			Constructor<?> constructor = Class.forName(fighterPath).getConstructor(String.class, int.class, int.class,
					int.class);
			Object character = constructor.newInstance(nameHero, maxHpHero, attackDmgHero, maxActionsHero);
			Method setLocation = character.getClass().getMethod("setLocation", Point.class);
			setLocation.invoke(character, new Point(4, 4));
			Method setActions = character.getClass().getMethod("setActionsAvailable", int.class);
			setActions.invoke(character,maxActionsHero);
			Game.heroes = new ArrayList<>();
			Game.heroes.add((Hero) character);
			Cell[][] m = engine.Game.map;
			CharacterCell c = new CharacterCell(null);
			for (int i = -1; i <= 1; i++) {
				int cx = 3 + i;
				for (int j = -1; j <= 1; j++) {
					int cy = 4 + j;
					if (cy >= 0 && cy <= m.length - 1) {
						m[cx][cy]=c;
			}
				}
			}
			m[4][4]= new CharacterCell((Character) character);
			
			try {
				
				TrapCell t= new TrapCell(); 
				 m[3][4] = t;
				int trap = t.getTrapDamage();
				Method getHP = character.getClass().getMethod("getCurrentHp");
				int currentHP1 = (int) getHP.invoke(character);
				int expected = currentHP1-trap;
				
				Method m3 = Class.forName(heroPath).getDeclaredMethod("move", Class.forName(directionPath));
				m3.invoke(character, Enum.valueOf((Class<Enum>) Class.forName(directionPath), "DOWN"));
				
				int currentHP2 = (int) getHP.invoke(character);
				assertTrue("The current HP of the character is not updated correctly after moving to a trap cell. Expected" 
				+ expected  + " but was " + currentHP2 + ".", expected == currentHP2);
						
				
			}catch (NoSuchMethodException e) {
				fail("Hero class should have move method");
			} 

		}
		
		
		

		
		
		@SuppressWarnings("unchecked")
		@Test(timeout = 3000)
		public void testValidMoveDown() throws Exception {
			int random = (int) (Math.random() * 1000);
			String nameHero = "Fighter " + random;
			int maxHpHero = 1000;
			int attackDmgHero = (int) (Math.random() * 100);
			int maxActionsHero = (int) (Math.random() * 5)+100;
			generateMap1();
			Constructor<?> constructor = Class.forName(fighterPath).getConstructor(String.class, int.class, int.class,
					int.class);
			Object character = constructor.newInstance(nameHero, maxHpHero, attackDmgHero, maxActionsHero);
			Method setLocation = character.getClass().getMethod("setLocation", Point.class);
			setLocation.invoke(character, new Point(4, 4));
			Method setActions = character.getClass().getMethod("setActionsAvailable", int.class);
			setActions.invoke(character,maxActionsHero);
			
			try {
				Cell[][] m = engine.Game.map;
				CharacterCell c = new CharacterCell(null);
				for (int i = -1; i <= 1; i++) {
					int cx = 3 + i;
					for (int j = -1; j <= 1; j++) {
						int cy = 4 + j;
						if (cy >= 0 && cy <= m.length - 1) {
							m[cx][cy]=c;
				}
					}
				}
				m[4][4]= new CharacterCell((Character) character);
				
				Method getLocation = character.getClass().getMethod("getLocation");
				Method m3 = Class.forName(heroPath).getDeclaredMethod("move", Class.forName(directionPath));
				m3.invoke(character, Enum.valueOf((Class<Enum>) Class.forName(directionPath), "DOWN"));
				Point location = (Point) getLocation.invoke(character);
				int updatedX = (int) location.getX();
				int updatedY = (int) location.getY();
				
				
		
				assertTrue("The current location of the character is not updated correctly after moving down. Expected X coorditane "
						+  3 + " but was " + updatedX  , 3 == updatedX);
				
				assertTrue("The current location of the character is not updated correctly after moving down. Expected Y coorditane "
						+  4 + " but was " + updatedY  , 4 == updatedY);
						
				
			}catch (NoSuchMethodException e) {
				fail("Hero class should have move method");
			} 

		}
		
		
		
		
		///////////////// Attack Method Test Cases///////////// 
		
		@SuppressWarnings("unchecked")
		@Test(timeout = 3000)
		public void testInvalidAttackNoActionPoints() throws Exception {
			int random = (int) (Math.random() * 1000);
			String nameHero = "Fighter " + random;
			int maxHpHero = (int) (Math.random() * 100);
			int attackDmgHero = (int) (Math.random() * 100);
			int maxActionsHero = (int) (Math.random() * 5)+100;
			generateMap1();
			Constructor<?> constructor = Class.forName(fighterPath).getConstructor(String.class, int.class, int.class,
					int.class);
			Constructor<?> constructor2 = Class.forName(zombiePath).getConstructor();
			Object character2 = constructor2.newInstance();
			Object character = constructor.newInstance(nameHero, maxHpHero, attackDmgHero, maxActionsHero);
			Method setLocation = character.getClass().getMethod("setLocation", Point.class);
			setLocation.invoke(character, new Point(4, 4));
			setLocation.invoke(character2, new Point(3, 4));
			Method setTarget = character.getClass().getMethod("setTarget", Character.class);
			setTarget.invoke(character, (Character)character2);
			Game.zombies = new ArrayList<>();
			Game.zombies.add((Zombie) character2);
			Method setActions = character.getClass().getMethod("setActionsAvailable", int.class);
			setActions.invoke(character,0);
			Cell[][] m = engine.Game.map;
			m[3][4] = new CharacterCell((Character) constructor.newInstance(nameHero, maxHpHero, attackDmgHero, maxActionsHero)); 
			try {
				Method m3 = Class.forName(heroPath).getDeclaredMethod("attack");
				m3.invoke(character);
				fail("Trying to attack with no enough action points, an exception should be thrown");

			}catch (NoSuchMethodException e) {
				fail("Hero class should have attack method");
			} catch (InvocationTargetException e) {
				try {
					if (!(Class.forName(notEnoughActionsExceptionPath).equals(e.getCause().getClass())))
						fail("Trying to attack with no enough action points, an exception should be thrown");

				} catch (ClassNotFoundException e1) {
					 
					fail("There should be a notEnoughActionsException class");
				}

			}

		}
		
		
		
		@SuppressWarnings("unchecked")
		@Test(timeout = 3000)
		public void testValidAttackUpdateActionsAvailable() throws Exception {
			int random = (int) (Math.random() * 1000);
			String nameHero = "Fighter " + random;
			int maxHpHero = (int) (Math.random() * 100);
			int attackDmgHero = (int) (Math.random() * 100);
			int maxActionsHero = (int) (Math.random() * 5)+100;
			generateMap1();
			Constructor<?> constructor = Class.forName(fighterPath).getConstructor(String.class, int.class, int.class,
					int.class);
			Object character = constructor.newInstance(nameHero, maxHpHero, attackDmgHero, maxActionsHero);
			Method setLocation = character.getClass().getMethod("setLocation", Point.class);
			Cell[][] m = engine.Game.map;
			setLocation.invoke(character, new Point(4, 4));
			m[4][4] = new CharacterCell((Character) character);
			Constructor<?> constructor2 = Class.forName(zombiePath).getConstructor();
			Object character2 = constructor2.newInstance();
			Game.zombies = new ArrayList<>();
			Game.zombies.add((Zombie) character2);
			Method setTarget = character.getClass().getMethod("setTarget", Character.class);
			setTarget.invoke(character, (Character)character2);
			setLocation.invoke(character2, new Point(3, 4));
			Method setActions = character.getClass().getMethod("setActionsAvailable", int.class);
			setActions.invoke(character,51);
			m[3][4] = new CharacterCell((Character) constructor.newInstance(nameHero, maxHpHero, attackDmgHero, maxActionsHero)); 
			try {
				Method m3 = Class.forName(heroPath).getDeclaredMethod("attack");
				m3.invoke(character);
				Method getActionsAvailable = character.getClass().getMethod("getActionsAvailable");
				int actions = (int) getActionsAvailable.invoke(character);
				assertTrue("The available actions of the character is not updated correctly after attacking. Expected "
						+  50 + " but was " + actions  , 50 == actions);

			}catch (NoSuchMethodException e) {
				fail("Hero class should have attack method");
			
			}

		}
		
		
		
		////////// useSpecial Method Test Cases////////////
		
		
		@SuppressWarnings("unchecked")
		@Test(timeout = 3000)
		public void testInvalidUseSpecialNoAvailableResources1() throws Exception {
			int random = (int) (Math.random() * 1000);
			String nameHero = "Fighter " + random;
			int maxHpHero = (int) (Math.random() * 100);
			int attackDmgHero = (int) (Math.random() * 100);
			int maxActionsHero = (int) (Math.random() * 5)+100;
			generateMap1();
			Constructor<?> constructor = Class.forName(fighterPath).getConstructor(String.class, int.class, int.class,
					int.class);
			Constructor<?> constructor2 = Class.forName(zombiePath).getConstructor();
			Object character2 = constructor2.newInstance();
			Object character = constructor.newInstance(nameHero, maxHpHero, attackDmgHero, maxActionsHero);
			Method setLocation = character.getClass().getMethod("setLocation", Point.class);
			setLocation.invoke(character, new Point(4, 4));
			Method setTarget = character.getClass().getMethod("setTarget", Character.class);
			setTarget.invoke(character, (Character)character2);
			Game.zombies = new ArrayList<>();
			Game.zombies.add((Zombie) character2);
			Method setActions = character.getClass().getMethod("setActionsAvailable", int.class);
			setActions.invoke(character,maxActionsHero);
			Cell[][] m = engine.Game.map;
			m[3][4] = new CharacterCell((Character) constructor.newInstance(nameHero, maxHpHero, attackDmgHero, maxActionsHero)); 
			try {
				Method m3 = Class.forName(heroPath).getDeclaredMethod("useSpecial");
				m3.invoke(character);
				fail("Trying to use a special action with no supply in your inventory, an exception should be thrown");

			}catch (NoSuchMethodException e) {
				fail("Hero class should have useSpecial method");
			} catch (InvocationTargetException e) {
				try {
					if (!(Class.forName(noAvailableResourcesExceptionPath).equals(e.getCause().getClass())))
						fail("Trying to use a special action with no supply in your inventory, an exception should be thrown");

				} catch (ClassNotFoundException e1) {
					 
					fail("There should be a notEnoughActionsException class");
				}

			}

		}
		
		

		///////// Cure Method Test Cases//////////
		
		@SuppressWarnings("unchecked")
		@Test(timeout = 3000)
		public void testInvalidCureNoVaccineInventory() throws Exception {
			int random = (int) (Math.random() * 1000);
			String nameHero = "Fighter " + random;
			int maxHpHero = (int) (Math.random() * 100);
			int attackDmgHero = (int) (Math.random() * 100);
			int maxActionsHero = (int) (Math.random() * 5)+100;
			generateMap1();
			Constructor<?> constructor = Class.forName(fighterPath).getConstructor(String.class, int.class, int.class,
					int.class);
			Constructor<?> constructor2 = Class.forName(zombiePath).getConstructor();
			Object character2 = constructor2.newInstance();
			Object character = constructor.newInstance(nameHero, maxHpHero, attackDmgHero, maxActionsHero);
			Method setLocation = character.getClass().getMethod("setLocation", Point.class);
			setLocation.invoke(character2, new Point(4, 5));
			setLocation.invoke(character, new Point(4, 4));
			Method setTarget = character.getClass().getMethod("setTarget", Character.class);
			setTarget.invoke(character, (Character)character2);
			Game.zombies = new ArrayList<>();
			Game.zombies.add((Zombie) character2);
			Method setActions = character.getClass().getMethod("setActionsAvailable", int.class);
			setActions.invoke(character,maxActionsHero);
			Cell[][] m = engine.Game.map;
			m[3][4] = new CharacterCell((Character) constructor.newInstance(nameHero, maxHpHero, attackDmgHero, maxActionsHero)); 
			try {
				Method m3 = Class.forName(heroPath).getDeclaredMethod("cure");
				m3.invoke(character);
				fail("Trying to cure a zombie with no vaccine in your inventory, an exception should be thrown");

			}catch (NoSuchMethodException e) {
				fail("Hero class should have cure method");
			} catch (InvocationTargetException e) {
				try {
					if (!(Class.forName(noAvailableResourcesExceptionPath).equals(e.getCause().getClass())))
						fail("Trying to cure a zombie with no vaccine in your inventory, an exception should be thrown");

				} catch (ClassNotFoundException e1) {
					 
					fail("There should be a NoAvailableResourcesException class");
				}

			}

		}
		

		//////////// Helper Method to Generate a map ///////	
		private void generateMap1() {
			Constructor<?> gameConstructor;
			Field f = null;
			try {
				gameConstructor = Class.forName(gamePath).getConstructor();
				Object createdGame = gameConstructor.newInstance();
				Class<? extends Object> curr = createdGame.getClass();
				f = curr.getDeclaredField("map");
				f.setAccessible(true);
				f.set(createdGame, new Cell[15][15]);
				Cell[][] m = engine.Game.map;
				for(int i=0;i<15;i++) {
					for(int j=0;j<15;j++) {
						m[i][j]= new CharacterCell(null);
					}	
				}
				
				
				
		}catch (Exception e) {
			e.printStackTrace();
			
		}

			
		
	}
		
		//Mayar
		
		private void placeRandomObjectOnMap(Constructor<?> cellConstructor,Object obj) throws InstantiationException,
		IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException, SecurityException, ClassNotFoundException, NoSuchMethodException {

			Field fd = Class.forName(gamePath).getDeclaredField("map");
			fd.setAccessible(true);
			Object[][] map=((Object[][]) fd.get(null));

			Method getChar= Class.forName(charCell).getMethod("getCharacter");
			int x, y;
			do {
				x = ((int) (Math.random() * map.length));
				y = ((int) (Math.random() * map[x].length));
			} while (map[x][y]!=null &&
					(((map[x][y].getClass().equals(Class.forName(charCell))&& getChar.invoke(map[x][y])!=null ))
							|| (map[x][y].getClass().equals(Class.forName(collectibleCellPath)))
							|| (map[x][y].getClass().equals(Class.forName(trapCellPath)))));

			map[x][y] = cellConstructor.newInstance(obj);
			if(obj.getClass().equals(Class.forName(explorerPath))||
					obj.getClass().equals(Class.forName(fighterPath)) ||
					obj.getClass().equals(Class.forName(medicPath))||
					obj.getClass().equals(Class.forName(zombiePath))
					) 
			{
				fd = Class.forName(characterPath).getDeclaredField("location");
				fd.setAccessible(true);
				fd.set(obj, new Point(x, y));
			}
		}
		
		private Object createFighter() throws NoSuchMethodException, SecurityException, 
		ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

			Constructor<?> fighterConstructor = Class.forName(fighterPath).getConstructor(String.class, int.class,
					int.class, int.class);

			int maxHp = ((int) (Math.random() * 100) + 10);
			int dmg = ((int) (Math.random() * 4) + 3);
			int actions = ((int) (Math.random() * 5) + 5);
			String name = "Hero_" ;

			return fighterConstructor.newInstance(name, maxHp, dmg, actions);

		}	
		private Object createMedic() throws NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

			Constructor<?> medicsConstructor = Class.forName(medicPath).getConstructor(String.class, int.class, int.class,
					int.class);
			int maxHp = ((int) (Math.random() * 100) + 10);
			int dmg = ((int) (Math.random() * 2) + 10);
			int actions = ((int) (Math.random() * 5) + 5);
			String name = "Hero_" ;

			return medicsConstructor.newInstance(name, maxHp, dmg, actions);

		}
		private Object createExplorer() throws NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
			Constructor<?> explorerConstructor = Class.forName(explorerPath).getConstructor(String.class, int.class,
					int.class, int.class);
			int maxHp = ((int) (Math.random() * 100) + 10);
			int dmg = ((int) (Math.random() * 5) + 5);
			int actions = ((int) (Math.random() * 5) + 5);
			String name = "Hero_" ;
			return explorerConstructor.newInstance(name, maxHp, dmg, actions);

		}

		private Object createZombie() throws NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
			Constructor<?> zombieConstructor = Class.forName(zombiePath).getConstructor();
			return zombieConstructor.newInstance();
		}
		private Object createVaccine() throws NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
			Constructor<?> vaccineConstructor = Class.forName(vaccinePath).getConstructor();
			return vaccineConstructor.newInstance();
		}
		private Object createSupply() throws NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
			Constructor<?> supplyConstructor = Class.forName(supplyPath).getConstructor();
			return supplyConstructor.newInstance();
		}

		private void resetGameStatics() throws Exception{
			Field fd = Class.forName(gamePath).getDeclaredField("zombies");
			fd.set((ArrayList<Object>) fd.get(null), new ArrayList<>());

			fd = Class.forName(gamePath).getDeclaredField("heroes");
			fd.set((ArrayList<Object>) fd.get(null), new ArrayList<>());

			fd = Class.forName(gamePath).getDeclaredField("map");
			fd.setAccessible(true);

			Constructor<?> charCellConst= Class.forName(charCell).getConstructor(Class.forName(characterPath));
			Constructor<?> zombieConst=Class.forName(zombiePath).getConstructor();

			Field fd2 = Class.forName(charCell).getDeclaredField("character");
			fd2.setAccessible(true);

			Object[][] map=((Object[][]) fd.get(null));

			Method setChar= Class.forName(charCell).getMethod("setCharacter", Class.forName(characterPath));
			Object o = null;
			fd = Class.forName(cellPath).getDeclaredField("isVisible");
			fd.setAccessible(true);
			for (int i = 0; i < map.length; i++) {
				for (int j = 0; j < map[i].length; j++) {
					map[i][j]=charCellConst.newInstance(o);
					fd.set(map[i][j], false);
					//				zombieConst.newInstance()
					//				setChar.invoke(map[i][j], o);


				}
			}



			fd2=Class.forName(zombiePath).getDeclaredField("ZOMBIES_COUNT");
			fd2.setAccessible(true);
			fd2.set((int)fd2.get(null), 11);



		}

		private boolean checkRandomPlaces2(String className) throws NoSuchMethodException, SecurityException, ClassNotFoundException,
		InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException {
			boolean check = true;

			int countTrue = 0;

			ArrayList<String> allStrings = new ArrayList<>();
			for (int i = 0; i < 25; i++) {
				allStrings.add(helperBoard(className));
			}
			for (int i = 0; i < allStrings.size(); i++) {
				String s1 = allStrings.get(i);
				for (int j = 0; j < allStrings.size(); j++) {
					String s2 = allStrings.get(j);
					if (arePermutation(s1, s2))
						countTrue++;

				}
			}
			if (countTrue / 670 > 0.4)
				check = false;
			return check;
		}
		private boolean checkRandomPlaces(String className) throws Exception {
			ArrayList<String> allStrings = new ArrayList<>();
			for (int l = 0; l < 10; l++) {
				resetGameStatics();
				Method m = Class.forName(gamePath).getMethod("startGame",Class.forName(heroPath));

				m.invoke(null,createFighter());
				Field fd2 = Class.forName(gamePath).getDeclaredField("map");
				fd2.setAccessible(true);

				Object[][] map = (Object[][]) fd2.get(null);
				String s = "";

				for (int i = 0; i < map.length; i++) {
					for (int j = 0; j < map[i].length; j++) {
						if(map[i][j] != null && 
								map[i][j].getClass().equals(Class.forName(charCell))&& className.equals(zombiePath))
						{	
							Method m2 = Class.forName(charCell).getMethod("getCharacter");
							if (((m2.invoke(map[i][j]) != null)) && 
									(m2.invoke(map[i][j]).getClass().equals(Class.forName(zombiePath)))) {
								s += i + "" + j;
							}
						}
						else {
							if(map[i][j] != null && 
									map[i][j].getClass().equals(Class.forName(trapCellPath))&& className.equals(trapCellPath)) {
								if (((map[i][j] != null)) && 
										(map[i][j].getClass().equals(Class.forName(className)))) {
									s += i + "" + j;
								}
							}
							else {
								if(  map[i][j] != null && 
										map[i][j].getClass().equals(Class.forName(collectibleCellPath)))
								{
									//								System.out.println("enter");
									Method m2 = Class.forName(collectibleCellPath).getMethod("getCollectible");
									if (((m2.invoke(map[i][j]) != null)) && 
											(m2.invoke(map[i][j]).getClass().equals(Class.forName(className)))) {
										s += i + "" + j;
									}
								}
							}
						}

					}
				}

				allStrings.add(s);
			}
			String s1="",s2="";
			int count=0;
			for (int i = 0; i < allStrings.size(); i++) {
				s1=allStrings.get(i);
				//			System.out.println(s1);
				//			if(i%2==0)
				//			else
				for (int j = 0; j < allStrings.size(); j++) {
					if(i!=j) {
						s2=allStrings.get(j);
						if(findSimilarity(s1, s2)>0.5)
							count++;

					}

				}
			}


			return count<10;
		}


		private String helperBoard(String className) throws NoSuchMethodException, SecurityException, ClassNotFoundException,
		InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException {

			Method m = Class.forName(gamePath).getMethod("startGame", Class.forName(heroPath));

			m.invoke(null, createFighter());

			Field fd = Class.forName(gamePath).getDeclaredField("map");
			fd.setAccessible(true);

			Object[][] map = (Object[][]) fd.get(null);
			String s = "";
			for (int i = 0; i < map.length; i++) {
				for (int j = 0; j < map[i].length; j++) {
					if (((map[i][j] != null)) && (map[i][j].getClass().equals(Class.forName(className)))) {
						s += i + "" + j;
					}

				}
			}
			return s;
		}


		private static boolean arePermutation(String str1, String str2) {

			int n1 = str1.length();
			int n2 = str2.length();

			if (n1 != n2)
				return false;
			char ch1[] = str1.toCharArray();
			char ch2[] = str2.toCharArray();

			Arrays.sort(ch1);
			Arrays.sort(ch2);

			for (int i = 0; i < n1; i++)
				if (ch1[i] != ch2[i])
					return false;

			return true;
		}


		private static int getLevenshteinDistance(String X, String Y)
		{
			int m = X.length();
			int n = Y.length();

			int[][] T = new int[m + 1][n + 1];
			for (int i = 1; i <= m; i++) {
				T[i][0] = i;
			}
			for (int j = 1; j <= n; j++) {
				T[0][j] = j;
			}

			int cost;
			for (int i = 1; i <= m; i++) {
				for (int j = 1; j <= n; j++) {
					cost = X.charAt(i - 1) == Y.charAt(j - 1) ? 0: 1;
					T[i][j] = Integer.min(Integer.min(T[i - 1][j] + 1, T[i][j - 1] + 1),
							T[i - 1][j - 1] + cost);
				}
			}

			return T[m][n];
		}

		private static double findSimilarity(String x, String y) {
			if (x == null || y == null) {
				throw new IllegalArgumentException("Strings must not be null");
			}

			double maxLength = Double.max(x.length(), y.length());
			if (maxLength > 0) {
				// optionally ignore case if needed
				return (maxLength - getLevenshteinDistance(x, y)) / maxLength;
			}
			return 1.0;
		}


//		-------------------------------START TEST METHODS--------------------------------

		@Test(timeout = 100000)
		public void testStartGameTrapsRandomlyPlaced() throws Exception {
			resetGameStatics();
			Method m = Class.forName(gamePath).getMethod("startGame", Class.forName(heroPath));

			m.invoke(null, createFighter());

			assertTrue("Traps should be randomly placed on the map",checkRandomPlaces(trapCellPath));
		}

		@Test(timeout = 10000)
		public void testStartGame10Zombies() throws Exception {
			resetGameStatics();
			Method m = Class.forName(gamePath).getMethod("startGame", Class.forName(heroPath));

			m.invoke(null, createFighter());

			Field fd = Class.forName(gamePath).getDeclaredField("map");
			fd.setAccessible(true);

			Object[][] map = (Object[][]) fd.get(null);
			int countZombies=0;
			Method m2 = Class.forName(charCell).getMethod("getCharacter");

			for (int i = 0; i < map.length; i++) {
				for (int j = 0; j < map[i].length; j++) {
					if(map[i][j]!=null && map[i][j].getClass().equals(Class.forName(charCell)))
					{
						if(m2.invoke(map[i][j])!=null && m2.invoke(map[i][j]).getClass().equals(Class.forName(zombiePath)))
							countZombies++;
					}
				}
			}
			assertTrue("The game map should be initalized with the correct number of zombies",countZombies==10);
		}

		@Test(timeout = 100000)
		public void testStartGameZombiesRandomlyPlaced() throws Exception {
			resetGameStatics();
			Method m = Class.forName(gamePath).getMethod("startGame", Class.forName(heroPath));

			m.invoke(null, createFighter());

			assertTrue("The zombies should be randomly placed on the map",checkRandomPlaces(zombiePath));
		}
		@Test(timeout = 10000)
		public void testStartGameAdjacentCellsVisibility2() throws Exception {
			resetGameStatics();
			Method m = Class.forName(gamePath).getMethod("startGame", Class.forName(heroPath));
			Object fighter=createFighter();
			m.invoke(null,fighter );
			
			Field fd = Class.forName(gamePath).getDeclaredField("map");
			fd.setAccessible(true);
			
			Object[][] map = (Object[][]) fd.get(null);
			int count=0;
			fd = Class.forName(cellPath).getDeclaredField("isVisible");
			fd.setAccessible(true);
			
			
			assertTrue("Hero's adjacent cells should be visible when starting the game",(boolean)fd.get(map[1][0]));
			
		}
		@Test(timeout = 10000)
		public void testStartGameSupplyies() throws Exception {
			resetGameStatics();
			Method m = Class.forName(gamePath).getMethod("startGame", Class.forName(heroPath));

			m.invoke(null, createFighter());

			Field fd = Class.forName(gamePath).getDeclaredField("map");
			fd.setAccessible(true);

			Object[][] map = (Object[][]) fd.get(null);
			int count=0;
			Method m2 = Class.forName(collectibleCellPath).getMethod("getCollectible");
			for (int i = 0; i < map.length; i++) {
				for (int j = 0; j < map[i].length; j++) {
					if(map[i][j]!=null && map[i][j].getClass().equals(Class.forName(collectibleCellPath)))
					{
						if(m2.invoke(map[i][j])!=null && m2.invoke(map[i][j]).getClass().equals(Class.forName(supplyPath)))
							count++;
					}
				}
			}
			assertTrue("The game map should be initalized with the correct number of vaccines",count==5);
		}
		@Test(timeout = 10000)
		public void testStartGameHeroAtCorrectLocationInClassHero2() throws Exception {
			resetGameStatics();
			Method m = Class.forName(gamePath).getMethod("startGame", Class.forName(heroPath));
			Object fighter=createFighter();
			m.invoke(null,fighter );
			
			Field fd = Class.forName(gamePath).getDeclaredField("map");
			fd.setAccessible(true);
			
			
			
			Method m2 = Class.forName(charCell).getMethod("getCharacter");
			Object o= m2.invoke(((Object[][]) fd.get(null))[0][0]);
			
			m2 = Class.forName(heroPath).getMethod("getLocation");
			Point loc=(Point) m2.invoke(o);
			try {
				
				assertTrue("The hero location should be updated correctly when starting the game", loc.x==0 || loc.y==0);
			}
			catch(NullPointerException e) {
				fail("Hero Location should not be null!");
			}
		}
		
		@Test(timeout = 10000)
		public void testWinGameUltmiateFail() throws Exception {
			resetGameStatics();
			Field fd = Class.forName(gamePath).getDeclaredField("heroes");
			fd.setAccessible(true);
			ArrayList<Object> heros = (ArrayList<Object>) fd.get(null);
			Object h1=createFighter();

			Field fd2 = Class.forName(heroPath).getDeclaredField("vaccineInventory");
			fd2.setAccessible(true);
			ArrayList<Object> vaccines = (ArrayList<Object>) fd2.get(h1);
			vaccines.add(createVaccine());

			heros.add(h1);
			Object h2=createFighter();
			heros.add(h2);
			Object h3=createMedic();
			heros.add(h3);

			fd = Class.forName(gamePath).getDeclaredField("zombies");
			fd.setAccessible(true);
			ArrayList<Object> zombies = (ArrayList<Object>) fd.get(null);
			zombies.add(createZombie());

			fd = Class.forName(gamePath).getDeclaredField("map");
			fd.setAccessible(true);

			Constructor<?> charCellConstructor = Class.forName(charCell).getConstructor(Class.forName(characterPath));
			Constructor<?> collectibleCellConstructor = Class.forName(collectibleCellPath).getConstructor(Class.forName(collectiblePath));


			placeRandomObjectOnMap(charCellConstructor,h1);
			placeRandomObjectOnMap(charCellConstructor,h2);
			placeRandomObjectOnMap(charCellConstructor,h3);
			placeRandomObjectOnMap(charCellConstructor,zombies.get(0));
			placeRandomObjectOnMap(collectibleCellConstructor,createVaccine());

			Method m = Class.forName(gamePath).getMethod("checkWin");
			assertTrue("Player can not win with zombies still alive", !(boolean)m.invoke(null ));
		}
		
		@Test(timeout = 10000)
		public void testGameOverUnusedandUncollectedVaccines() throws Exception {
			resetGameStatics();
			
			Field fd = Class.forName(gamePath).getDeclaredField("heroes");
			fd.setAccessible(true);
			ArrayList<Object> heros = (ArrayList<Object>) fd.get(null);
			Object h1=createFighter();
			Object h2=createFighter();
			Object h3=createMedic();
			heros.add(h1);
			heros.add(h2);
			heros.add(h3);
			
			Field fd2 = Class.forName(heroPath).getDeclaredField("vaccineInventory");
			fd2.setAccessible(true);
			ArrayList<Object> vaccines = (ArrayList<Object>) fd2.get(h2);
			vaccines.add(createVaccine());
			
			Constructor<?> charCellConstructor = Class.forName(charCell).getConstructor(Class.forName(characterPath));
			Constructor<?> collectibleCellConstructor = Class.forName(collectibleCellPath).getConstructor(Class.forName(collectiblePath));
			
			placeRandomObjectOnMap(charCellConstructor,h1);
			placeRandomObjectOnMap(charCellConstructor,h2);
			placeRandomObjectOnMap(charCellConstructor,h3);
			placeRandomObjectOnMap(collectibleCellConstructor,createVaccine());

			Method m = Class.forName(gamePath).getMethod("checkGameOver");
			assertTrue("The game should not end with uncollected and unused vaccines left with any hero", !(boolean)m.invoke(null ));
		}
		
		@Test(timeout = 10000)
		public void testEndTurnCellVisibilityCorner1() throws Exception {
			resetGameStatics();
			Field fd = Class.forName(gamePath).getDeclaredField("heroes");
			fd.setAccessible(true);
			ArrayList<Object> heros = (ArrayList<Object>) fd.get(null);
			fd = Class.forName(gamePath).getDeclaredField("map");
			fd.setAccessible(true);
			Object[][] map=(Object[][]) fd.get(null);

			Object h1= createMedic();
			heros.add(h1);


			Constructor<?> charCellConstructor = Class.forName(charCell).getConstructor(Class.forName(characterPath));
			int x = 14;
			int y = 14;

			fd = Class.forName(characterPath).getDeclaredField("location");
			fd.setAccessible(true);
			fd.set(h1, new Point(x, y));




			map[x][y]= charCellConstructor.newInstance(h1);

			Method m = Class.forName(gamePath).getMethod("endTurn");

			try {
				
				m.invoke(null);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				fail(e.getCause()+" occured but shouldnt check the console for the error trace");
			}
			



			fd = Class.forName(cellPath).getDeclaredField("isVisible");
			fd.setAccessible(true);

			for (int i = 0; i < map.length; i++) {
				for (int j = 0; j < map[i].length; j++) {
					if((i<=x+1 && j<=y+1)&&(i>=x-1&& j>=y-1)) {

						assertTrue("End turn should update the cells' visibility, heros' cells and their adjacent cells should be visible", (boolean)fd.get(map[i][j]));
					}
					else {

						assertFalse("End turn should update the cells' visibility, ONLY heros' cells and their adjacent cells should be visible", (boolean)fd.get(map[i][j]));

					}
				}
			}
		}
		@Test(timeout = 10000)
		public void testEndTurnResetTargetMany() throws Exception {
			resetGameStatics();
			Field fd = Class.forName(gamePath).getDeclaredField("heroes");
			fd.setAccessible(true);
			ArrayList<Object> heros = (ArrayList<Object>) fd.get(null);
			Constructor<?> charCellConstructor = Class.forName(charCell).getConstructor(Class.forName(characterPath));
			ArrayList<Object> usedHeros= new ArrayList<>();


			for (int i = 0; i < 5; i++) {

				int maxHp = ((int) (Math.random() * 100) + 10);
				int dmg = ((int) (Math.random() * 200) + 10);
				int maxActions = ((int) (Math.random() * 10) + 10);
				String name = "Hero_" ;
				int newActions=((int) (Math.random() * 5) + 5);

				Object h1= createMedic();
				Object z2= createZombie();


				heros.add(h1);

				fd = Class.forName(characterPath).getDeclaredField("target");
				fd.setAccessible(true);
				fd.set(h1,z2);

				usedHeros.add(h1);


				placeRandomObjectOnMap(charCellConstructor,h1);
				placeRandomObjectOnMap(charCellConstructor,z2);
			}






			Method m = Class.forName(gamePath).getMethod("endTurn");

			try {
				
				m.invoke(null);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				fail(e.getCause()+" occured but shouldnt check the console for the error trace");
			}
			


			fd = Class.forName(characterPath).getDeclaredField("target");
			fd.setAccessible(true);
			for (Object hero : usedHeros) {

				assertTrue("After the player ends their turn all heros' targets should be reset", fd.get(hero)==null);
			}
		}
		
		@Test(timeout = 100000)
		public void testEndTurnCellVisibilityMany() throws Exception {
			resetGameStatics();
			Field fd = Class.forName(gamePath).getDeclaredField("heroes");
			fd.setAccessible(true);
			ArrayList<Object> heros = (ArrayList<Object>) fd.get(null);
			fd = Class.forName(gamePath).getDeclaredField("map");
			fd.setAccessible(true);
			Object[][] map=(Object[][]) fd.get(null);
			Constructor<?> charCellConstructor = Class.forName(charCell).getConstructor(Class.forName(characterPath));
			ArrayList<Object> herosToUse= new ArrayList<>();
			ArrayList<Point> visibleLocations= new ArrayList<>();
			ArrayList<Point> points=new ArrayList<>();
			points.add(new Point(5,4));
			points.add(new Point(0, 13));
			points.add(new Point(10, 10));
			for (int i = 0; i < 3; i++) {

				Object h1= createMedic();
				heros.add(h1);

				herosToUse.add(h1);

				fd = Class.forName(characterPath).getDeclaredField("location");
				fd.setAccessible(true);
				Point p = points.get(i);
				fd.set(h1, p);
				map[p.x][p.y]= charCellConstructor.newInstance(h1);
				//			System.out.println("Heros loc "+ p);

				visibleLocations.add(p);
				for (int j = p.x-1; j <=p.x+1 ; j++) {
					for (int j2 = p.y-1; j2 <= p.y+1; j2++) {

						visibleLocations.add(new Point(j,j2));
						//					System.out.println(new Point(j,j2));
					}
				}
			}





			Method m = Class.forName(gamePath).getMethod("endTurn");

			try {
				
				m.invoke(null);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				fail(e.getCause()+" occured but shouldnt check the console for the error trace");
			}
			



			fd = Class.forName(cellPath).getDeclaredField("isVisible");
			fd.setAccessible(true);

			for (int i = 0; i < map.length; i++) {
				for (int j = 0; j < map[i].length; j++) {
					Point p = new Point(i, j);
					if(visibleLocations.contains(p)) {

						//					System.out.println("Should be visible "+p+" "+ (boolean)fd.get(map[i][j]) );
						assertTrue("End turn should update the cells' visibility, heros' cells and their adjacent cells should be visible", (boolean)fd.get(map[i][j]));
					}
					else {
						//					System.out.println("Shouldnot be visible "+p+" "+ (boolean)fd.get(map[i][j]) );
						assertFalse("End turn should update the cells' visibility, ONLY heros' cells and their adjacent cells should be visible", (boolean)fd.get(map[i][j]));

					}
				}
			}
		}
		
		@Test(timeout = 10000)
		public void testEndTurnResetZombieTarget() throws Exception {
			resetGameStatics();
			Field fd = Class.forName(gamePath).getDeclaredField("zombies");
			fd.setAccessible(true);
			ArrayList<Object> zombies = (ArrayList<Object>) fd.get(null);
			Constructor<?> charCellConstructor = Class.forName(charCell).getConstructor(Class.forName(characterPath));

			Object h1= createMedic();
			placeRandomObjectOnMap(charCellConstructor,h1);

			Object z2= createZombie();
			zombies.add(z2);
			placeRandomObjectOnMap(charCellConstructor, z2);

			fd = Class.forName(characterPath).getDeclaredField("target");
			fd.setAccessible(true);
			fd.set(z2,h1);



			Method m = Class.forName(gamePath).getMethod("endTurn");

			try {
				m.invoke(null);
			} catch (Exception  e) {
				e.printStackTrace();
				if(e.getClass().equals(Class.forName(invalidTargetExceptionPath)))
					fail(e.getClass()+" Occured but shouldnt end turn");
				if(e.getClass().equals(Class.forName(notEnoughActionsExceptionPath)))
					fail(e.getClass()+" occured but shouldnt in end turn");
				if(e.getClass().equals(Class.forName(noAvailableResourcesExceptionPath)))
					fail(e.getClass()+" occured but shouldnt in end turn");

				fail("Null pointer exception occured make sure to handle null targets scenario in end turn");
			}
			fd = Class.forName(characterPath).getDeclaredField("target");
			fd.setAccessible(true);
			assertTrue("After the player ends their turn all zombies' targets should be reset", fd.get(z2)==null);
		}
		
		@Test(timeout = 10000)
		public void testEndTurnZombieAttackLeft2() throws Exception {
			resetGameStatics();
			Field fd = Class.forName(gamePath).getDeclaredField("zombies");
			fd.setAccessible(true);
			ArrayList<Object> zombies = (ArrayList<Object>) fd.get(null);

			fd = Class.forName(gamePath).getDeclaredField("heroes");
			fd.setAccessible(true);
			ArrayList<Object> heros = (ArrayList<Object>) fd.get(null);

			Constructor<?> charCellConstructor = Class.forName(charCell).getConstructor(Class.forName(characterPath));
			Constructor<?> medicsConstructor = Class.forName(medicPath).getConstructor(String.class, int.class, int.class,
					int.class);
			int maxHp = ((int) (Math.random() * 100) + 10);
			int dmg = ((int) (Math.random() * 10) + 3);
			int actions = ((int) (Math.random() * 5) + 5);
			String name = "Hero_" ;

			Object h1= medicsConstructor.newInstance(name, maxHp, dmg, actions);


			heros.add(h1);
			Object z2= createZombie();
			zombies.add(z2);

			fd = Class.forName(characterPath).getDeclaredField("target");
			fd.setAccessible(true);
			fd.set(z2,h1);

			fd = Class.forName(gamePath).getDeclaredField("map");
			fd.setAccessible(true);
			Object[][] map=((Object[][]) fd.get(null));

			map[5][5]= charCellConstructor.newInstance(z2);

			fd = Class.forName(characterPath).getDeclaredField("location");
			fd.setAccessible(true);
			fd.set(z2, new Point(5,5));

			map[5][4]= charCellConstructor.newInstance(h1);

			fd = Class.forName(characterPath).getDeclaredField("location");
			fd.setAccessible(true);
			fd.set(h1, new Point(5,4));


			Method m = Class.forName(gamePath).getMethod("endTurn");

			try {

				m.invoke(null);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				fail(e.getCause()+" occured but shouldnt check the console for the error trace");
			}


			fd = Class.forName(characterPath).getDeclaredField("currentHp");
			fd.setAccessible(true);
			assertEquals("Zombies should attack any valid target to their left during ending the turn",(int)(maxHp-10), (int)fd.get(h1));
		}
		@Test(timeout = 10000)
		public void testEndTurnZombieAttackDown() throws Exception {
			resetGameStatics();
			Field fd = Class.forName(gamePath).getDeclaredField("zombies");
			fd.setAccessible(true);
			ArrayList<Object> zombies = (ArrayList<Object>) fd.get(null);

			fd = Class.forName(gamePath).getDeclaredField("heroes");
			fd.setAccessible(true);
			ArrayList<Object> heros = (ArrayList<Object>) fd.get(null);

			Constructor<?> charCellConstructor = Class.forName(charCell).getConstructor(Class.forName(characterPath));
			Constructor<?> medicsConstructor = Class.forName(medicPath).getConstructor(String.class, int.class, int.class,
					int.class);
			int maxHp = ((int) (Math.random() * 100) + 10);
			int dmg = ((int) (Math.random() * 5) + 3);
			int actions = ((int) (Math.random() * 5) + 5);
			String name = "Hero_" ;

			Object h1= medicsConstructor.newInstance(name, maxHp, dmg, actions);


			heros.add(h1);
			Object z2= createZombie();
			zombies.add(z2);

			fd = Class.forName(characterPath).getDeclaredField("target");
			fd.setAccessible(true);
			fd.set(z2,h1);

			fd = Class.forName(gamePath).getDeclaredField("map");
			fd.setAccessible(true);
			Object[][] map=((Object[][]) fd.get(null));

			map[5][5]= charCellConstructor.newInstance(z2);

			fd = Class.forName(characterPath).getDeclaredField("location");
			fd.setAccessible(true);
			fd.set(z2, new Point(5,5));

			map[4][5]= charCellConstructor.newInstance(h1);

			fd = Class.forName(characterPath).getDeclaredField("location");
			fd.setAccessible(true);
			fd.set(h1, new Point(4,5));


			Method m = Class.forName(gamePath).getMethod("endTurn");

			try {
				
				m.invoke(null);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				fail(e.getCause()+" occured but shouldnt check the console for the error trace");
			}
			


			fd = Class.forName(characterPath).getDeclaredField("currentHp");
			fd.setAccessible(true);
			assertEquals("Zombies' HP should be updated after they attack during ending the turn",(int)(40-dmg/2), (int)fd.get(z2));
		}
		
		@Test(timeout = 10000)
		public void testEndTurnZombieAttackOnlyOnce() throws Exception {
			resetGameStatics();
			Field fd = Class.forName(gamePath).getDeclaredField("zombies");
			fd.setAccessible(true);
			ArrayList<Object> zombies = (ArrayList<Object>) fd.get(null);

			fd = Class.forName(gamePath).getDeclaredField("heroes");
			fd.setAccessible(true);
			ArrayList<Object> heros = (ArrayList<Object>) fd.get(null);

			Constructor<?> charCellConstructor = Class.forName(charCell).getConstructor(Class.forName(characterPath));
			Constructor<?> medicsConstructor = Class.forName(medicPath).getConstructor(String.class, int.class, int.class,
					int.class);

			ArrayList<Object> herosInRange=new ArrayList<>();

			int dmg = 5;
			for (int i = 0; i < 3; i++) {

				int maxHp = ((int) (Math.random() * 100) + 10);
				int actions = ((int) (Math.random() * 5) + 5);
				String name = "Hero_" ;

				Object h1= medicsConstructor.newInstance(name, maxHp, dmg, actions);
				heros.add(h1);
				herosInRange.add(h1);

			}
			Object z2= createZombie();
			zombies.add(z2);


			fd = Class.forName(gamePath).getDeclaredField("map");
			fd.setAccessible(true);
			Object[][] map=((Object[][]) fd.get(null));

			map[5][5]= charCellConstructor.newInstance(z2);

			fd = Class.forName(characterPath).getDeclaredField("location");
			fd.setAccessible(true);
			fd.set(z2, new Point(5,5));




			map[4][5]= charCellConstructor.newInstance(herosInRange.get(0));

			fd = Class.forName(characterPath).getDeclaredField("location");
			fd.setAccessible(true);
			fd.set(herosInRange.get(0), new Point(4,5));


			map[6][5]= charCellConstructor.newInstance(herosInRange.get(1));

			fd = Class.forName(characterPath).getDeclaredField("location");
			fd.setAccessible(true);
			fd.set(herosInRange.get(1), new Point(6,5));


			map[5][4]= charCellConstructor.newInstance(herosInRange.get(2));

			fd = Class.forName(characterPath).getDeclaredField("location");
			fd.setAccessible(true);
			fd.set(herosInRange.get(2), new Point(5,4));

			boolean oneAttacked=false;



			Method m = Class.forName(gamePath).getMethod("endTurn");

			try {
				m.invoke(null);
			} catch (Exception  e) {
				e.printStackTrace();
				if(e.getClass().equals(Class.forName(invalidTargetExceptionPath)))
					fail(e.getClass()+" Occured but shouldnt end turn");
				if(e.getClass().equals(Class.forName(notEnoughActionsExceptionPath)))
					fail(e.getClass()+" occured but shouldnt in end turn");
				if(e.getClass().equals(Class.forName(noAvailableResourcesExceptionPath)))
					fail(e.getClass()+" occured but shouldnt in end turn");

				fail("Null pointer exception occured make sure to handle null targets scenario in end turn");
			}

			fd = Class.forName(characterPath).getDeclaredField("currentHp");
			fd.setAccessible(true);

			assertEquals("Zombies'HP should be updated after they attack during ending the turn",(int)(40-dmg/2), (int)fd.get(z2));
			oneAttacked=true;

		}

		@Test(timeout = 10000)
		public void testEndTurnAddZombies3() throws Exception {
			resetGameStatics();
			Constructor<?> charCellConst= Class.forName(charCell).getConstructor(Class.forName(characterPath));
			
			Field fd = Class.forName(gamePath).getDeclaredField("zombies");
			fd.setAccessible(true);
			ArrayList<Object> zombies = (ArrayList<Object>) fd.get(null);
			Object z= createZombie();
			zombies.add(z);
			placeRandomObjectOnMap(charCellConst, z);
			z= createZombie();
			zombies.add(z);
			placeRandomObjectOnMap(charCellConst, z);
			
			Method m = Class.forName(gamePath).getMethod("endTurn");
			
			m.invoke(null);
			
			fd = Class.forName(gamePath).getDeclaredField("map");
			fd.setAccessible(true);
			Object[][] map=((Object[][]) fd.get(null));
			
			
			Method m2 = Class.forName(charCell).getMethod("getCharacter");
			int countZombies=0;
			for (int i = 0; i < map.length; i++) {
				for (int j = 0; j < map[i].length; j++) {
					if(map[i][j]!=null && map[i][j].getClass().equals(Class.forName(charCell)))
					{
						if(m2.invoke(map[i][j])!=null && m2.invoke(map[i][j]).getClass().equals(Class.forName(zombiePath)))
							countZombies++;
					}
				}
			}
			fd = Class.forName(gamePath).getDeclaredField("zombies");
			fd.setAccessible(true);
			zombies = (ArrayList<Object>) fd.get(null);
			assertTrue("The game should add one new zombie to the arraylist of zombies at the end of each turn as long as the game only contains up 10 zombies",
					zombies.size()>1);
			
			assertTrue("The game should add ONLY ONE new zombie to the arraylist of zombies at the end of each turn as long as the game only contains up 10 zombies",
					zombies.size()==3);
			assertEquals("The game should add one new zombie at the end of each turn as long as the game only contains up 10 zombies",
					3,countZombies);
			
		}


		@Test(timeout = 10000)
		public void testEndTurnResetActions() throws Exception {
			resetGameStatics();
			Field fd = Class.forName(gamePath).getDeclaredField("heroes");
			fd.setAccessible(true);
			ArrayList<Object> heros = (ArrayList<Object>) fd.get(null);
			Constructor<?> fighterConstructor = Class.forName(fighterPath).getConstructor(String.class, int.class,
					int.class, int.class);

			int maxHp = ((int) (Math.random() * 100) + 10);
			int dmg = ((int) (Math.random() * 200) + 10);
			int maxActions = ((int) (Math.random() * 10) + 10);
			String name = "Hero_" ;
			int newActions=((int) (Math.random() * 5) + 5);

			Object h1= fighterConstructor.newInstance(name, maxHp, dmg, maxActions);


			heros.add(h1);

			fd = Class.forName(heroPath).getDeclaredField("actionsAvailable");
			fd.setAccessible(true);
			fd.set(h1,newActions);

			Constructor<?> charCellConstructor = Class.forName(charCell).getConstructor(Class.forName(characterPath));

			placeRandomObjectOnMap(charCellConstructor,h1);


			Method m = Class.forName(gamePath).getMethod("endTurn");

			try {
				
				m.invoke(null);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				fail(e.getCause()+" occured but shouldnt check the console for the error trace");
			}
			


			fd = Class.forName(heroPath).getDeclaredField("actionsAvailable");
			fd.setAccessible(true);
			assertTrue("After the player ends their turn all hero's actions should be reset", (int)fd.get(h1)==maxActions);
		}

		@Test(timeout = 10000)
		public void testEndTurnZombieAttackRight() throws Exception {
			resetGameStatics();
			Field fd = Class.forName(gamePath).getDeclaredField("zombies");
			fd.setAccessible(true);
			ArrayList<Object> zombies = (ArrayList<Object>) fd.get(null);

			fd = Class.forName(gamePath).getDeclaredField("heroes");
			fd.setAccessible(true);
			ArrayList<Object> heros = (ArrayList<Object>) fd.get(null);

			Constructor<?> charCellConstructor = Class.forName(charCell).getConstructor(Class.forName(characterPath));
			Constructor<?> medicsConstructor = Class.forName(medicPath).getConstructor(String.class, int.class, int.class,
					int.class);
			int maxHp = ((int) (Math.random() * 100) + 10);
			int dmg = ((int) (Math.random() * 5) + 3);
			int actions = ((int) (Math.random() * 5) + 5);
			String name = "Hero_" ;

			Object h1= medicsConstructor.newInstance(name, maxHp, dmg, actions);


			heros.add(h1);
			Object z2= createZombie();
			zombies.add(z2);

			fd = Class.forName(characterPath).getDeclaredField("target");
			fd.setAccessible(true);
			fd.set(z2,h1);

			fd = Class.forName(gamePath).getDeclaredField("map");
			fd.setAccessible(true);
			Object[][] map=((Object[][]) fd.get(null));

			map[5][5]= charCellConstructor.newInstance(z2);

			fd = Class.forName(characterPath).getDeclaredField("location");
			fd.setAccessible(true);
			fd.set(z2, new Point(5,5));

			map[5][6]= charCellConstructor.newInstance(h1);

			fd = Class.forName(characterPath).getDeclaredField("location");
			fd.setAccessible(true);
			fd.set(h1, new Point(5,6));


			Method m = Class.forName(gamePath).getMethod("endTurn");

			try {
				
				m.invoke(null);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				fail(e.getCause()+" occured but shouldnt check the console for the error trace");
			}
			

			fd = Class.forName(characterPath).getDeclaredField("currentHp");
			fd.setAccessible(true);
			assertEquals("Zombies' HP should be updated after they attack during ending the turn",(int)(40-dmg/2), (int)fd.get(z2));
		}
		
		//kapiel
		
		@SuppressWarnings("unchecked")
		@Test(timeout = 5000)
	    public void testZombieAttackOutOfBoundsMin(){

			//Initialization
	        int maxHp = 1;
	        int attackDamage = 1;
	        int maxActions = 1;
	        
	        int zombieXLocation = 0;
	        int zombieYLocation = 0;

	        ArrayList<Hero> testCharactersList = null;
	        Class<?> characterClass = null;
	        
	        try {
				
	            Class<?> fighterClass = Class.forName(fighterPath);
	            characterClass = Class.forName(characterPath);
	            Constructor<?> constructorFighter = fighterClass.getConstructor(String.class, int.class, int.class, int.class);
	            
	            testCharactersList = new ArrayList<Hero>();
	            
	            for (int i = 0; i < 8; i++) {
	            	testCharactersList.add((Hero) constructorFighter.newInstance("Bob " + i, maxHp, attackDamage, maxActions));
	    		}
	        	
			} catch (Exception e) {
				fail(e.getCause().getClass() + " ccuered while trying to create Fighters. Check the fighter, hero and character constuctors!");
			}


	        Object[] array = testCharactersList.toArray();       
	        Hero[] testCharacters = new Hero[array.length];

	        for (int i = 0; i < 8; i++)
	        	testCharacters[i] = (Hero) array[i];

	        // create zombie instance
	        Object character2 = null;
	        
	        try {
	        	
	        	Class<?> zombieClass = Class.forName(zombiePath);
	            Constructor<?> constructorZombie = zombieClass.getConstructor();
	            character2 = constructorZombie.newInstance();
	            
			} catch (Exception e) {
				fail(e.getCause().getClass() + " ccuered while trying to create Zombies. Check the zombie and character constuctors!");
			}
	        
	           
	        //Set map
	        Class<?> gameClass = null;
	        try {
	        	gameClass = Class.forName(gamePath);   
			} catch (Exception e) {
				fail(e.getCause().getClass() + " ccuered while trying to create a game.");
			}
	         
	        //Start game
			try {
				Method startGame = gameClass.getMethod("startGame", Hero.class);
		        startGame.invoke(gameClass, testCharacters[0]);
			} catch (Exception e) {
				fail(e.getCause().getClass() + " ccuered while trying to start a game");
			}
	        
	        Field mapField = null;
	        Cell[][] tmpMap = null;
	        
	        Field heroField  = null;
	        ArrayList<Hero> heroList = null;
	        Method setLocation = null;
	        
	        Field zombieField = null;
	        ArrayList<Zombie> zombieList = null;
	    
			try {
							
		        setLocation = characterClass.getMethod("setLocation", Point.class);	        
		        heroField = Game.class.getDeclaredField("heroes");	        
		        heroList = ((ArrayList<Hero>)heroField.get(gameClass));
		        
		        mapField = Game.class.getDeclaredField("map");	
		        
		        tmpMap = (Cell[][])mapField.get(gameClass);


		        zombieField = Game.class.getDeclaredField("zombies");
		        zombieList = ((ArrayList<Zombie>)zombieField.get(gameClass));
		        
		        zombieList.add((Zombie)character2);
		        
		        //Put Zombie in cell 0, 0
		        Point location2 = new Point(zombieXLocation, zombieYLocation);
		        setLocation.invoke(character2, location2);
		        tmpMap[zombieXLocation][zombieYLocation] = new CharacterCell((Zombie)character2);
		        
		        
			} catch (Exception e) {
				fail(e.getCause().getClass() + " occurred while trying to get Map variable from the Game Class");
			}
			
	        //Place Heros
				Point location2 = new Point(1, 1);

				try {
					tmpMap[location2.x][location2.y] = new CharacterCell(testCharacters[0]);
					setLocation.invoke(testCharacters[0], location2);
				} catch (Exception e) {
					fail(e.getCause().getClass() + " occurred while trying to set location of Fighters");
				}

			//Attack 8 times
			for (int i = 0; i < 8; i++) {
				
				//Delete Existing Zombies
				Iterator<Zombie> iterator = zombieList.iterator();
				
				while (iterator.hasNext()) {
					
					Zombie z = iterator.next();

					if(!z.equals((Zombie) character2)) {
						Point locationZ = z.getLocation();
		
						tmpMap[locationZ.x][locationZ.y] = new CharacterCell(null);		
						iterator.remove();
					}
				}			

				// call end turn
				try {
					Method endTurn = gameClass.getMethod("endTurn");
					endTurn.invoke(gameClass);
					
					
				} catch (Exception e) {
					fail(e.getCause().getClass() + " Make sure the zombie doesn't attack out of bounds of the map!");
				}			
			}

	        //If reached the end, then no out of bounds attacks
	        assertTrue(true);
	    }	
			
		@Test(timeout = 1000)
	    public void testZombieAttackFighterUpdatesCurrentHP() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException{
	     	
	        int maxHp = (int) (Math.random() * 100) + 10;
	        int attackDamage = (int) (Math.random() * 30) + 1;
	        int maxActions = 1;
	        
	        Point location1 = new Point(3, 3);
	        Point location2 = new Point(4, 3);
	        
	        // create zombie instance
	        Class<?> zombieClass = Class.forName(zombiePath);
	        Constructor<?> constructorZombie = zombieClass.getConstructor();
	        Object character1 = constructorZombie.newInstance();
	        
	        // create fighter instance
	        Class<?> fighterClass = Class.forName(fighterPath);
	        Class<?> characterClass = Class.forName(characterPath);
	        Constructor<?> constructorFighter = fighterClass.getConstructor(String.class, int.class, int.class, int.class);
	        Object character2 = constructorFighter.newInstance("Bob", maxHp, attackDamage, maxActions);
	        
	        //Start game
	        Class<?> gameClass = Class.forName(gamePath);

			try {
				Method startGame = gameClass.getMethod("startGame", Hero.class);
		        startGame.invoke(gameClass, character2);
			} catch (Exception e) {
				fail(e.getCause().getClass() + " ccuered while trying to start a game");
			}
			
	        Field zombieField = null;
	        ArrayList<Zombie> zombieList = null;

	        try {
	        	
	            zombieField = Game.class.getDeclaredField("zombies");	       
	            zombieList = ((ArrayList<Zombie>)zombieField.get(gameClass));  	       

	    	    zombieList.add((Zombie)character1);
	    	    
	        }catch (Exception e) {
	        	fail(e.getCause().getClass() + " ccuered while trying to get zombie list");
			}
	     
	        // set location of characters
	        Method setLocation = characterClass.getMethod("setLocation", Point.class);
	        setLocation.invoke(character1, location1);
	        setLocation.invoke(character2, location2);
	        
	        //Set map   
			try {
				Field myField = Game.class.getDeclaredField("map");
		        Cell[][] tmpMap = (Cell[][]) myField.get(gameClass);
		        
		        tmpMap[location1.x][location1.y] = new CharacterCell((Character)character1);
		        tmpMap[location2.x][location2.y] = new CharacterCell((Character)character2);
		        
			} catch (Exception e) {
				fail(e.getCause().getClass() + " occurred while trying to get Map variable from the Game Class");
			}
			
	        
	        // call attack method on character1
	        Method attackMethod = characterClass.getMethod("attack");
	        try {
	        	attackMethod.invoke(character1);
	        }catch(Exception e) {
	        	fail(e.getCause().getClass() + " occurred while trying to make a Zombie attack, check its attack method");
	        }
	        

	        // check if target's current HP has decreased by the attack damage
	        Method getCurrentHpMethod = characterClass.getMethod("getCurrentHp");
	        int expectedHp = maxHp - 10;
	        int actualHp = (int) getCurrentHpMethod.invoke(character2);
	        assertEquals(expectedHp, actualHp);
	    }
		
		@Test(timeout = 1000)
	    public void testZombieDefenseUpdatesCurrentHP() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException{
	     	
	        int maxHp = (int) (Math.random() * 100) + 10;
	        int attackDamage = (int) (Math.random() * 30) + 1;
	        int maxActions = 1;
	        
	        Point location1 = new Point(3, 3);
	        Point location2 = new Point(4, 3);
	        
	        // create first character instance
	        Class<?> fighterClass = Class.forName(fighterPath);
	        Class<?> characterClass = Class.forName(characterPath);
	        Constructor<?> constructorFighter = fighterClass.getConstructor(String.class, int.class, int.class, int.class);
	        Object character1 = constructorFighter.newInstance("Bob", maxHp, attackDamage, maxActions);

	        // create second character instance
	        Class<?> zombieClass = Class.forName(zombiePath);
	        Constructor<?> constructorZombie = zombieClass.getConstructor();
	        Object character2 = constructorZombie.newInstance();

	        //Start Game
	        Class<?> gameClass = Class.forName(gamePath);

			try {
				Method startGame = gameClass.getMethod("startGame", Hero.class);
		        startGame.invoke(gameClass, character1);
			} catch (Exception e) {
				fail(e.getCause().getClass() + " ccuered while trying to start a game");
			}
			
	        Field zombieField = null;
	        ArrayList<Zombie> zombieList = null;

	        try {
	        	
	            zombieField = Game.class.getDeclaredField("zombies");	       
	            zombieList = ((ArrayList<Zombie>)zombieField.get(gameClass));  	       

	    	    zombieList.add((Zombie)character2);
	    	    
	        }catch (Exception e) {
	        	fail(e.getCause().getClass() + " ccuered while trying to get zombie list");
			}
	        
	        // set location of characters
	        Method setLocation = characterClass.getMethod("setLocation", Point.class);
	        setLocation.invoke(character1, location1);
	        setLocation.invoke(character2, location2);
	        
	        // set character2 as the target of character1
	        Method setTargetMethod = characterClass.getMethod("setTarget", characterClass);
	        setTargetMethod.invoke(character1, character2);
	                
	        //Set map
			try {
				Field myField = Game.class.getDeclaredField("map");
		        Cell[][] tmpMap = (Cell[][]) myField.get(gameClass);
		        
		        tmpMap[location1.x][location1.y] = new CharacterCell((Character)character1);
		        tmpMap[location2.x][location2.y] = new CharacterCell((Character)character2);
		        
			} catch (Exception e) {
				fail(e.getCause().getClass() + " occurred while trying to get Map variable from the Game Class");
			}

	        // call attack method on character1
	        Method attackMethod = characterClass.getMethod("attack");
	        attackMethod.invoke(character1);

	        // check if target's current HP has decreased by the attack damage
	        Method getCurrentHpMethod = characterClass.getMethod("getCurrentHp");
	        int expectedHp = maxHp - 5;
	        int actualHp = (int) getCurrentHpMethod.invoke(character1);
	        assertEquals(expectedHp, actualHp);
	    }
		
		@Test(timeout = 1000)
	    public void testFighterDeathOnAttack() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException{
	     	
	        int maxHp = 1;
	        int attackDamage = 1;
	        int maxActions = 1;
	                
	        // create first character instance
	        Class<?> fighterClass = Class.forName(fighterPath);
	        Class<?> characterClass = Class.forName(characterPath);
	        Constructor<?> constructorFighter = fighterClass.getConstructor(String.class, int.class, int.class, int.class);
	        Object character1 = constructorFighter.newInstance("Bob", maxHp, attackDamage, maxActions);

	        // create second character instance
	        Class<?> zombieClass = Class.forName(zombiePath);
	        Constructor<?> constructorZombie = zombieClass.getConstructor();
	        Object character2 = constructorZombie.newInstance();
	           
	        //Start Game
	        Class<?> gameClass = Class.forName(gamePath);

			try {
				Method startGame = gameClass.getMethod("startGame", Hero.class);
		        startGame.invoke(gameClass, character1);
			} catch (Exception e) {
				fail(e.getCause().getClass() + " ccuered while trying to start a game");
			}
			
	        Field zombieField = null;
	        ArrayList<Zombie> zombieList = null;

	        try {
	        	
	            zombieField = Game.class.getDeclaredField("zombies");	       
	            zombieList = ((ArrayList<Zombie>)zombieField.get(gameClass));  	       

	    	    zombieList.add((Zombie)character2);
	    	    
	        }catch (Exception e) {
	        	fail(e.getCause().getClass() + " ccuered while trying to get zombie list");
			}
	        
	        //Set Map
	        Cell[][] tmpMap = null;
	        Field heroField  = null;
	        
			try {
							
				Field mapField = Game.class.getDeclaredField("map");
		        tmpMap = (Cell[][]) mapField.get(gameClass);
		        Method setLocation = characterClass.getMethod("setLocation", Point.class);	        
		        heroField = Game.class.getDeclaredField("heroes");	        
		        
		        Point location2 = new Point(1, 1);
		        setLocation.invoke(character2, location2);
		        tmpMap[location2.x][location2.y] = new CharacterCell((Zombie)character2);
		        
			} catch (Exception e) {
				fail(e.getCause().getClass() + " occurred while trying to get Map variable from the Game Class");
			}

	        // call end turn
			Method endTurn = gameClass.getMethod("endTurn");
			endTurn.invoke(gameClass);
	        
	        //Check if fighter died
	        boolean isDead = ((CharacterCell)tmpMap[0][0]).getCharacter() == null;
	        isDead =  !((ArrayList<Hero>)heroField.get(gameClass)).contains(character1);

	        // check if target's current HP has decreased by the attack damage
	        assertEquals("The Hero is considered dead if it no longer exists on the map and in the Heros array ", isDead, true);
	    }

		@Test(timeout = 1000)
	    public void testFighterDefenseDoesNotUseActionPoints() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException{
	     	
	        int maxHp = (int) (Math.random() * 100) + 10;
	        int attackDamage = (int) (Math.random() * 30) + 1;
	        int maxActions = (int) (Math.random() * 10) + 1;
	        
	        Point location1 = new Point(3, 3);
	        Point location2 = new Point(4, 3);
	        
	        // create second character instance
	        Class<?> zombieClass = Class.forName(zombiePath);
	        Constructor<?> constructorZombie = zombieClass.getConstructor();
	        Object character1 = constructorZombie.newInstance();
	        
	        // create first character instance
	        Class<?> fighterClass = Class.forName(fighterPath);
	        Class<?> characterClass = Class.forName(characterPath);
	        Constructor<?> constructorFighter = fighterClass.getConstructor(String.class, int.class, int.class, int.class);
	        Object character2 = constructorFighter.newInstance("Bob", maxHp, attackDamage, maxActions);

	        //Start Game
	        Class<?> gameClass = Class.forName(gamePath);

			try {
				Method startGame = gameClass.getMethod("startGame", Hero.class);
		        startGame.invoke(gameClass, character2);
			} catch (Exception e) {
				fail(e.getCause().getClass() + " ccuered while trying to start a game");
			}
			
	        Field zombieField = null;
	        ArrayList<Zombie> zombieList = null;

	        try {
	        	
	            zombieField = Game.class.getDeclaredField("zombies");	       
	            zombieList = ((ArrayList<Zombie>)zombieField.get(gameClass));  	       

	    	    zombieList.add((Zombie)character1);
	    	    
	        }catch (Exception e) {
	        	fail(e.getCause().getClass() + " ccuered while trying to get zombie list");
			}
	        
	        
	        
	        // set location of characters
	        Method setLocation = characterClass.getMethod("setLocation", Point.class);
	        setLocation.invoke(character1, location1);
	        setLocation.invoke(character2, location2);
	        

			try {
				Field myField = Game.class.getDeclaredField("map");
		        Cell[][] tmpMap = (Cell[][]) myField.get(gameClass);
		        
		        tmpMap[location1.x][location1.y] = new CharacterCell((Character)character1);
		        tmpMap[location2.x][location2.y] = new CharacterCell((Character)character2);
		        
			} catch (Exception e) {
				fail(e.getCause().getClass() + " occurred while trying to get Map variable from the Game Class");
			}

	        // call attack method on character1
	        Method attackMethod = characterClass.getMethod("attack");
	        attackMethod.invoke(character1);

	        // check if target's current HP has decreased by the attack damage
	        Method getCurrentApMethod = fighterClass.getMethod("getActionsAvailable");
	        int actualAp = (int) getCurrentApMethod.invoke(character2);
	        assertEquals(maxActions, actualAp);
	    }

		@Test(timeout = 1000)
	    public void testZombieDeathOnDefense() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException{
	     	
	        int maxHp = 1;
	        int attackDamage = 80;
	        int maxActions = 1;
	                
	        // create first character instance
	        Class<?> fighterClass = Class.forName(fighterPath);
	        Class<?> characterClass = Class.forName(characterPath);
	        Constructor<?> constructorFighter = fighterClass.getConstructor(String.class, int.class, int.class, int.class);
	        Object character1 = constructorFighter.newInstance("Bob", maxHp, attackDamage, maxActions);

	        // create second character instance
	        Class<?> zombieClass = Class.forName(zombiePath);
	        Constructor<?> constructorZombie = zombieClass.getConstructor();
	        Object character2 = constructorZombie.newInstance();
	        
	        //Start Game
	        Class<?> gameClass = Class.forName(gamePath);

			try {
				Method startGame = gameClass.getMethod("startGame", Hero.class);
		        startGame.invoke(gameClass, character1);
			} catch (Exception e) {
				fail(e.getCause().getClass() + " ccuered while trying to start a game");
			}
			
	        Field zombieField = null;
	        ArrayList<Zombie> zombieList = null;

	        try {
	        	
	            zombieField = Game.class.getDeclaredField("zombies");	       
	            zombieList = ((ArrayList<Zombie>)zombieField.get(gameClass));  	       

	    	    zombieList.add((Zombie)character2);
	    	    
	        }catch (Exception e) {
	        	fail(e.getCause().getClass() + " ccuered while trying to get zombie list");
			}
	        
	        
	        // set character2 as the target of character1
	        Method setTargetMethod = characterClass.getMethod("setTarget", characterClass);
	        setTargetMethod.invoke(character1, character2);
	           
	        //Set map
	        
	        Cell[][] tmpMap = null;
	        
			try {
							
				Field mapField = Game.class.getDeclaredField("map");
		        tmpMap = (Cell[][]) mapField.get(gameClass);
		        Method setLocation = characterClass.getMethod("setLocation", Point.class);	        
		        
		        Point location2 = new Point(1, 1);
		        setLocation.invoke(character2, location2);
		        tmpMap[location2.x][location2.y] = new CharacterCell((Zombie)character2);
		        
			} catch (Exception e) {
				fail(e.getCause().getClass() + " occurred while trying to get Map variable from the Game Class");
			}

	        // call end turn
	        Method attackMethod = zombieClass.getMethod("attack");
	        attackMethod.invoke(character2);
	        
	        //Check if zombie died
	        boolean isDead = ((CharacterCell)tmpMap[1][1]).getCharacter() == null;
	        isDead = isDead && !((ArrayList<Zombie>)zombieField.get(gameClass)).contains(character2);

	        // check if target's current HP has decreased by the attack damage
	        assertEquals("The Zombie should die if they attack an enemy and it defends dealing more damage than their current HP  ", isDead, true);
	    }
		



}