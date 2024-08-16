package com.spiritwisestudiosllc;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

// Define a custom formatter
class CustomFormatter extends SimpleFormatter {
    @Override
    public synchronized String format(LogRecord lr) {
        return lr.getMessage() + System.lineSeparator();
    }
}

// Define the Bag class
public class Bag<T> {
    // Logger to log messages
    private static final Logger logger = Logger.getLogger(Bag.class.getName());

    // Map to store elements and their counts
    private final Map<T, Integer> itemInventoryMap;

    // Constants for item names
    private static final String POTION = "Potion";
    private static final String ELIXIR = "Elixir";
    private static final String ANTIDOTE = "Antidote";
    private static final String REVIVE = "Revive";

    // Constructor to initialize the bag
    public Bag() {
        itemInventoryMap = new HashMap<>();

        // Set up the logger with a custom formatter
        Handler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new CustomFormatter());
        logger.setUseParentHandlers(false);
        logger.addHandler(consoleHandler);
    }

    // Method to add an item to the bag
    public void add(T item) {
        itemInventoryMap.put(item, itemInventoryMap.getOrDefault(item, 0) +1);
    }

    // Method to remove an item from the bag
    public void remove(T item) {
        if (itemInventoryMap.containsKey(item)) {
            int count = itemInventoryMap.get(item);
            if (count > 1) {
                itemInventoryMap.put(item, count -1);
            } else {
                itemInventoryMap.remove(item);
            }
        }
    }

    // Method to check if an item is in the bag
    public boolean contains(T item) {
        return itemInventoryMap.containsKey(item);
    }

    // Method to count the number of times an item is in the bag
    public int count(T item) {
        return itemInventoryMap.getOrDefault(item, 0);
    }

    // Method to print the contents of the bag
    public void printContents() {
        if (logger.isLoggable(java.util.logging.Level.INFO)) {
            logger.info("Player's Inventory: ");
            for (Map.Entry<T, Integer> entry : itemInventoryMap.entrySet()) {
                logger.info(String.format("%s: %d", entry.getKey(), entry.getValue()));
            }
        }
    }

    // Main method to test the Bag class
    public static void main(String[] args) {
        // Create an instance of the Bag class
        Bag<String> playerInventory = new Bag<>();

        // Add items to the bag
        playerInventory.add(POTION);
        playerInventory.add(ELIXIR);
        playerInventory.add(ELIXIR);
        playerInventory.add(POTION);
        playerInventory.add(ANTIDOTE);
        playerInventory.add(POTION);

        // Print the contents of the bag
        playerInventory.printContents();

        // Create variables to hold the results of the contains and count methods
        boolean containsPotion = playerInventory.contains(POTION);
        boolean containsRevive = playerInventory.contains(REVIVE);
        int potionCount = playerInventory.count(POTION);
        int elixirCount = playerInventory.count(ELIXIR);

        // Use conditional statement for efficient logging
        if (logger.isLoggable(java.util.logging.Level.INFO)) {
            // Test the contains method
            if (containsPotion) {
                logger.info("There are potions in the inventory.");
            } else {
                logger.info("There are no potions in the inventory.");
            }

            if (containsRevive) {
                logger.info("There are revives in the inventory.");
            } else {
                logger.info("There are no revives in the inventory.");
            }

            // Test the count method
            logger.info(String.format("There are %d potions in the inventory.", potionCount)); // 3
            logger.info(String.format("There are %d elixirs in the inventory.", elixirCount)); // 1
        
        }

        // Remove items from the bag
        logger.info("Player used a potion.");
        playerInventory.remove(POTION);

        // Print the contents of the bag
        logger.info("Items remaining in inventory: ");
        playerInventory.printContents();

        // Create variables to hold the results of the contains and count methods
        containsPotion = playerInventory.contains(POTION);
        potionCount = playerInventory.count(POTION);
        // Use conditional statement for efficient logging
        if (logger.isLoggable(java.util.logging.Level.INFO)) {
            // Test the contains method for the removed element
            logger.info(String.format("Does Inventory contain potion? %b", containsPotion)); // true

            // Test the count method for the removed element
            logger.info(String.format("Number of potions in Inventory: %d", potionCount)); // 2
        }
    }
}