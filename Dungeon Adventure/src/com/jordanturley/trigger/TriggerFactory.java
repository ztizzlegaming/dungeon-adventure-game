package com.jordanturley.trigger;

public class TriggerFactory {
	public static Trigger getTrigger(
			String activated, //Activated or Not Activated
			String activationMethod, //Enter Text, Items In Inventory, or Items In Rooms
			String whatActivates, //The text to enter, or items in inventory or rooms
			String effectType, //Add Item To Game, Delete Item From Game, Win Game, Lose Game, or Remove Barrier
			String roomAffected, //The room that is affected, or room affected and direction opened, or None
			String textAddToRoom, //Text to add to a room or None
			String itemsToAdd, //Items to add to the room or None
			String itemsToDelete, //Items to delete from the room or None
			String triggerText //The text that is printed when trigger is activated or None
	) {
		return null;
	}
}
