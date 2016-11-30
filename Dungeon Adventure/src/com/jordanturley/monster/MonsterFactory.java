package com.jordanturley.monster;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.ImageIcon;

/**
 * The <code>MonsterFactory</code> class is used to generate new Monster objects. Before you can generate a
 * monster of a given type, you have to add the monster type using createMonsterType, which will store
 * the monster type's min and max health, min and max damage, etc... Then when you create a monster, it will
 * have a set health.
 * 
 * @author Jordan Turley
 */
public class MonsterFactory {
	private static final Random RANDOM = new Random();
	
	private static final Map<String, MonsterType> MONSTER_TYPES = new HashMap<String, MonsterType>();
	
	/**
	 * Creates a new type of monster for use with the MonsterFactory.
	 * @param name The name of the monster
	 * @param healthStr The range of health of the monster ("5-10")
	 * @param damageStr The range of damage of the monster ("5-10")
	 * @param randomPercentage The percentage for the monster to appear in a room when there
	 * is a random monster in a room (1-100)
	 */
	public static void createMonsterType(String name, String healthStr, String damageStr, String randomPercentage, String xpStr) { 
		if (!MONSTER_TYPES.containsKey(name)) { //If this type of monster isn't already in the map
			//Parse the min and max health
			String[] healthStrParts = healthStr.split("-");
			int minHealth = Integer.parseInt(healthStrParts[0]);
			int maxHealth = Integer.parseInt(healthStrParts[1]);
			
			//Parse the min and max damage
			String[] damageStrParts = damageStr.split("-");
			int minDamage = Integer.parseInt(damageStrParts[0]);
			int maxDamage = Integer.parseInt(damageStrParts[1]);
			
			//Parse the random chance
			int randomChance = Integer.parseInt(randomPercentage);
			
			ImageIcon[] images = new ImageIcon[Monster.NUM_ANIMATION_FRAMES];
			for (int i1 = 0; i1 < Monster.NUM_ANIMATION_FRAMES; i1++) {
				String path = "images" + File.separator + "monsters" + File.separator + name + "_" + (i1 + 1) + ".png";
				ImageIcon image = new ImageIcon(path);
				images[i1] = image;
			}

			int xp = Integer.parseInt(xpStr);
			
			MonsterType mt = new MonsterType(name, minHealth, maxHealth, xp, minDamage, maxDamage, randomChance, images);
			MONSTER_TYPES.put(name, mt);
		}
	}
	
	/**
	 * Generates a Monster object
	 * @param monsterName The name of the monster to generate
	 * @return A new Monster object
	 */
	public static Monster getMonster(String monsterName) {
		if (monsterName.equals("random")) { //Get a random monster for the room, based on the monster's percentages
			int rand = (int) (Math.random() * 100);
			List<MonsterType> monsterTypesList = new ArrayList<MonsterType>(MONSTER_TYPES.values());
			int total = 0;
			MonsterType chosenMonster = null;
			for (MonsterType mt : monsterTypesList) {
				total += mt.getRandomChance();
				if (rand < total) {
					chosenMonster = mt;
					break;
				}
			}
			
			return getMonsterFromMonsterType(chosenMonster);
		} else if (!monsterName.equals("none")) { //Just get the monster for the room
			MonsterType mt = MONSTER_TYPES.get(monsterName);
			return getMonsterFromMonsterType(mt);
		} else {
			//The type of monster was not recognized
			throw new IllegalArgumentException("The monster '" + monsterName + "' could not be found");
		}
	}
	
	/**
	 * Generates a Monster object from the given MonsterType
	 * @param mt The MonsterType (type of the monster)
	 * @return The Monster object
	 */
	private static Monster getMonsterFromMonsterType(MonsterType mt) {
		int lowHealth = mt.getLowerHealth();
		int highHealth = mt.getHigherHealth();
		
		int health = RANDOM.nextInt(highHealth - lowHealth + 1) + lowHealth;
		
		Monster monster = new Monster(mt.getName(), health, mt.getXpToGive(), mt.getLowerDamage(), mt.getHigherDamage(), mt.getImages());
		return monster;
	}
}
