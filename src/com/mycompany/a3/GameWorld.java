package com.mycompany.a3;

import com.codename1.charts.util.ColorUtil;

import java.util.ArrayList;
import java.util.Random;
import java.util.Observable;

//Abstraction of the game world.
//Model class Responsible for creating and populating array list of game object.
//Provides methods for the Game class to manipulate game state values and data
//Contains all implementations for actions the user can invoke.
//Provides accessor methods for the Observer classes to update information as needed.
public class GameWorld extends Observable {
	
	private static final int MAXCATS = 30;
	
	private int width;
	private int height;
	private int score = 0;
	private int dogCount = 3;
	private int catCount = 4;
	private int dogsCaught = 0;
	private int catsCaught = 0;
	private Random rand = new Random();
	private GameObjectsCollection objectList;
	
	public GameWorld() {
		objectList = new GameObjectsCollection();
	}
	//ArrayList<GameObjects> objectList = new ArrayList<GameObjects>();
	
	//Populate the arraylist with 1 net, 3 dogs, and 4 cats with the following attributes:
	//All objects will have a random starting location (within game world: Dimensions of 1000x610)
	//Dogs and cats have a random, static size between 40 and 55
	//Dogs have a starting rgb color value of (0, 0, 128), Cats have (0, 128, 255)
	//All Animal objects have a random starting direction, and have a speed of 5
	public void initLayout() {
		add(new Net(rand.nextInt(width+1), rand.nextInt(height+1), ColorUtil.rgb(0, 0, 0), 100, ObjectID.Net));
		for(int i = 0; i < 3; i++) {
			add(new Dog(rand.nextInt(width+1), rand.nextInt(height+1), ColorUtil.rgb(0, 128, 255), 
					       rand.nextInt(16) + 40, rand.nextInt(360), 5, 0, ObjectID.Dog));
		}
		for(int j = 0; j < 4; j++) {
			add(new Cat(rand.nextInt(width+1), rand.nextInt(height+1), ColorUtil.rgb(0, 128, 15),
					       rand.nextInt(16) + 40, rand.nextInt(360), 5, ObjectID.Cat));
		}
	}
		
	public void add(GameObjects newObject) {
		objectList.add(newObject);
	}
	
	public GameObjectsCollection getObjectList() {
		return objectList;
	}
	
//--------------------MAPVIEW/SCOREVIEW ACCESSOR METHODS---------------------------
	
	//Method used by MapView to print updated GameObject Collection information
	public String printMap() {
		StringBuilder out = new StringBuilder();
		IIterator itr = objectList.getIterator();
		out.append("|-----------------------------------------------------------------------|");
		out.append("\n");
		while (itr.hasNext()) {
			out.append(itr.getNext());
			out.append("\n");
		}
		out.append("|-----------------------------------------------------------------------|");
		return out.toString();
	}
	
	//Methods used by ScoreView to update Labels displaying score-related values
	public int getScore() {
		return score;
	}
	
	public int getDogCount() {
		return dogCount;
	}
	
	public int getCatCount() {
		return catCount;
	}
	
	public void incCatCount() {
		catCount++;
	}
	
	public int getDogsCaught() {
		return dogsCaught;
	}
	
	public int getCatsCaught() {
		return catsCaught;
	}
	
//------------------------Extraneous Methods----------------------------	
	

	public Object getObject (int index) {
		return objectList.get(index);
	}
	
	public void removeObject (int index) {
		objectList.removeObject(index);
	}
	
	//Methods used by Game class to send the dimensions of the center container to the Game World
	public void setWidth(int width) {
		this.width = width;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}

//*~~~*~~~*~~~*~~~*~~~* PLAYER EXECUTABLE METHODS *~~~*~~~*~~~*~~~*~~~*	
	
	//Checks to see if an Animal is within the bounds of the Net's size
	//An Animal directly on the edge of the net will NOT be scooped
	//Used by scoop method
	public boolean scoopCheck (Object net, Object animal) {
		if(((Math.abs(((Net)net).getX() - ((Animal)animal).getX())) < (((Net)net).getSize() / 2)) &&
				((Math.abs(((Net)net).getY() - ((Animal)animal).getY())) < (((Net)net).getSize() / 2))) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * ---USER INPUTS 's'---
	 * Check to see if any other GameObjects are within range of the
	 * net to be scooped up. If so, remove them from the game world.	
	 * Does NOT scoop up objects directly on the net's boundaries.
	 * They must be inside.
	 */
	public void scoop() {
		int scoopCount = 0;
		for(int i = objectList.size() - 1; i >= 1; i--) {
			//Check to see if object is within net's boundaries
			if(scoopCheck(objectList.get(0), objectList.get(i)) == true) {
				//If within boundaries, check to see if it's a Dog 
				if(objectList.get(i) instanceof Dog) {
					System.out.println("Dog captured!");
					Dog tempDog = (Dog)getObject(i);
					int tempVal = tempDog.getScratches();
					score = score + 10 - tempVal;
					dogsCaught++;
					dogCount--;
				}
				//If within boundaries, check to see if it's a Cat =^.^=
				if(objectList.get(i) instanceof Cat) {
					System.out.println("Cat captured!");
					score = score - 10;
					catsCaught++;
					catCount--;
				}
				scoopCount++;
				objectList.removeObject(i);
			}
		}
		//My best attempt at changing the above code using an Iterator rather than a for loop
		//Would work for all cases except when the GameObjectsCollection is left with just a net
		//and an Animal object, in which case it would refuse to remove the last Animal.
		
		/*int idx = 0;
		IIterator itr = objectList.getIterator();
		GameObjects o = (GameObjects) itr.getNext();
		while(itr.hasNext()) {
			if((scoopCheck(getObject(0), getObject(idx)) == true) && (getObject(0) != getObject(idx))) {
				if (objectList.get(idx) instanceof Dog) {
					System.out.println("Dog captured!");
					Dog tempDog = (Dog)getObject(idx);
					int tempVal = tempDog.getScratches();
					score = score + 10 - tempVal;
					dogsCaught++;
					dogCount--;
				} else if(objectList.get(idx) instanceof Cat) {
					System.out.println("Cat captured!");
					score = score - 10;
					catsCaught++;
					catCount--;
				}
				itr.remove();
				scoopCount++;
				idx--;
			}
			o = (GameObjects) itr.getNext();
			idx++;
		}*/
		if(scoopCount > 0) {
			setChanged();
			notifyObservers();
		}
		if(scoopCount == 0) {
			System.out.println("There's nothing in the net to capture!");
		}
	}
	
	/**
	 * ---USER INPUTS 'r' or 'l' or 'u' or 'd'---
	 * Commands to move Net right, left, up, or down
	 */
	public void moveNetUp() {
		((Net)getObject(0)).moveUp();
		setChanged();
		notifyObservers();
	}
	
	public void moveNetDown() {
		((Net)getObject(0)).moveDown();
		setChanged();
		notifyObservers();
	}
	
	public void moveNetLeft() {
		((Net)getObject(0)).moveLeft();
		setChanged();
		notifyObservers();
	}

	public void moveNetRight() {
		((Net)getObject(0)).moveRight();
		setChanged();
		notifyObservers();
	}
	
    /**
     * ---USER INPUTS 'e'---
     * Expand Net size by 50. Cannot exceed size of width of the center container
     */
	public void expand() {
		if(((Net)objectList.get(0)).getSize() < width) {
			((Net)objectList.get(0)).setSize(((Net)objectList.get(0)).getSize() + 50);
			setChanged();
			notifyObservers();
		} else {
			System.out.println("Error: the net has reached its MAXIMUM size.");
		}
	}
	
	
	/**
	 * ---USER INPUTS 'c'---
	 * Contract Net size by 50. This value cannot go below 50.
	 */
	public void contract() {
		if(((Net)objectList.get(0)).getSize() > 50) {
			((Net)objectList.get(0)).setSize(((Net)objectList.get(0)).getSize() - 50);
			setChanged();
			notifyObservers();
		} else {
			System.out.println("Error: the net has reached its MINIMUM size of 50.");
		}
	}
	
	/**
	 * ---USER INPUTS 'o'---
	 * Replace Net's x,y coordinates with a random Dog's location
	 */
	public void jumpToDog() {
		if(dogCount < 1) {
			System.out.println("There are no dogs to teleport to!");
		} else {
			boolean workDone1 = false;
			while(workDone1 == false) {
				int tempRand = rand.nextInt(objectList.size() - 1) + 1;
				if (getObject(tempRand) instanceof Dog) {
					((Net)getObject(0)).setX(((Dog)getObject(tempRand)).getX());
					((Net)getObject(0)).setY(((Dog)getObject(tempRand)).getY());
					System.out.println("The net has moved to a random dog's location!");
					workDone1 = true;
					setChanged();
					notifyObservers();
					}
				}
		}
	}
	
	/**
	 * ---USER INPUTS 'a'---
	 * Replace Net's x,y coordinates with a random Cat's location
	 */
	public void jumpToCat() {
		if(catCount < 1) {
			System.out.println("There are no cats to teleport to!");
		} else {
			
			boolean workDone2 = false;
			while(workDone2 == false) {
				int tempRand = rand.nextInt(objectList.size() - 1) + 1;
				if (getObject(tempRand) instanceof Cat) {
					((Net)getObject(0)).setX(((Cat)getObject(tempRand)).getX());
					((Net)getObject(0)).setY(((Cat)getObject(tempRand)).getY());
					System.out.println("The net has moved to a random cat's location!");
					workDone2 = true;
					setChanged();
					notifyObservers();
				}
			}
		}
	}
	
	/**
	 * ---USER INPUTS 'k'---
	 * =^.^= Kitty spawning procedure =^.^=
	 * Spawn a new Cat object if at least two Cats currently exist.
	 * New Cat will spawn in a random location within 100 units of another cat, 
	 * within the boundaries of the game world.
	 */
	public void spawnCat() {
		if(catCount < 2) {
			System.out.println("ERROR: At least two cats are required to perform this action.");
		} else {
			if(catCount < MAXCATS) {
				boolean catspawnDone = false;
				while (catspawnDone == false) {
					//Select random cat to spawn a kitten next to =^.^=
					int catRand = rand.nextInt(objectList.size() - 1) + 1;
					if (getObject(catRand) instanceof Cat) {
						//Generate random location for new cat =^.^=
						//New location will be within 100 range in x,y directions of parent
						double randX = rand.nextInt(200) - 100;
						double randY = rand.nextInt(200) - 100;
						double kittenX = ((Cat)getObject(catRand)).getX() + randX;
						double kittenY = ((Cat)getObject(catRand)).getY() + randY;
						//Ensure that new cat spawns within boundaries of the game world =^.^=
						//If new coordinates go beyond game world, re-randomize them near parent
						//until coordinates are within game world.
						while(kittenX < 0 || kittenX > width || kittenY < 0 || kittenY > height) {
							randX = rand.nextInt(200) - 100;
							randY = rand.nextInt(200) - 100;
							kittenX = ((Cat)getObject(catRand)).getX() + randX;
							kittenY = ((Cat)getObject(catRand)).getY() + randY;
						}
						//=^.^= Spawn a new kitty! =^.^=
						objectList.add(new Cat(kittenX, kittenY, ColorUtil.rgb(0,  128, 15),
							          rand.nextInt(16) + 40, rand.nextInt(360), 5, ObjectID.Cat));
						System.out.println("New cat has joined the game!");
						catCount++;
						catspawnDone = true;
						setChanged();
						notifyObservers();
					}
				}
			}
		}
	}
	
	/**
	 * ----USER INPUTS 'f'---
	 * If at least one Cat exists, a random Dog will be selected to be attacked by a Cat.
	 * This Dog will have its scratch count incremented by 1, and speed
	 * reduced by 1. A Dog cannot exceed 5 scratches or go below a speed of 0.	
	 */
	public void catFight() {
		if(catCount == 0) {
			System.out.println("ERROR: At least one cat is required to perform this action.");
		} else {
			boolean scratchWork = false;
			while(scratchWork == false) {
				//Select random Dog
				int tempRand = rand.nextInt(objectList.size() - 1) + 1;
				if(getObject(tempRand) instanceof Dog) {
					scratchWork = true;
					//Change selected Dog's attributes iff speed is above 0
					//Otherwise exit method and nothing changes
					if(((Animal)getObject(tempRand)).getSpeed() > 0) {
						int tempColor = ((Animal)getObject(tempRand)).getColor(); //Obtain Dog's rgb color value
						int newGreen = ColorUtil.green(tempColor) + 20; //Increase green aspect of rgb by 20
						int newRed = ColorUtil.red(tempColor) + 40;
						//int newBlue = ColorUtil.blue(tempColor) - 20;
						((Animal)getObject(tempRand)).setColor(newRed, newGreen, 255); //Update Dog's rbg color value
						int oldSpeed = ((Animal)getObject(tempRand)).getSpeed(); //Obtain Dog's speed
						int newSpeed = oldSpeed - 1; //Placeholder to decrement speed by 1
						((Animal)getObject(tempRand)).setSpeed(newSpeed); //Update speed
						((Dog)getObject(tempRand)).addScratch(); //Increase scratch count by 1
						System.out.println("A dog has been scratched!");
						setChanged();
						notifyObservers();
					}//if
				}//if
			}//while	
		}
	}
	
	/**
	 * ---USER INPUTS 't'---
	 * Game clock has ticked. All Animal objects move and update locations
	 * Method definition for tick() is located in GameWorld class.
	 * Accesses and manipulates array list of GameObjects in GameWorld class.
	 */
	public void tick() {
		int idx = 0;
	    IIterator itr = objectList.getIterator();
	    GameObjects o;
	    while(itr.hasNext()) {
	    	if(getObject(idx) instanceof Animal) {
				((Animal)objectList.get(idx)).move(width, height);  
	    	}
	    	o = (GameObjects)itr.getNext();
	    	idx++;
	    }
	    setChanged();
	    notifyObservers();
		/*for(int i = 0; i < objectList.size(); i++) {
			if(getObject(i) instanceof Animal) {
				((Animal)objectList.get(i)).move();
			}
		}*/
	}
	
}