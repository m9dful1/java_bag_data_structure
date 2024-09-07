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

    // Constants for item names to test the Bag class to reduce redundancy
    private static final String POTION = "Potion";
    private static final String ELIXIR = "Elixir";
    private static final String ANTIDOTE = "Antidote";
    private static final String REVIVE = "Revive";

    // Constructor to initialize the bag and set up the logger
    public Bag() {
        itemInventoryMap = new HashMap<>(); // Initialize the map to store items and their counts

        // Set up the logger with a custom formatter
        Handler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new CustomFormatter());
        logger.setUseParentHandlers(false); // Disable default log handlers to avoid duplicate logs
        logger.addHandler(consoleHandler);
    }

    // Method to add an item to the bag
    public void add(T item) {
        itemInventoryMap.put(item, itemInventoryMap.getOrDefault(item, 0) +1);
    }

    // Method to remove an item from the bag
    public void remove(T item) {
        // Check if the item is in the bag
        if (itemInventoryMap.containsKey(item)) {
            int count = itemInventoryMap.get(item); // Get the count of the item
            if (count > 1) {
                // Decrease the count by 1 if the item exists more than once
                itemInventoryMap.put(item, count -1);
            } else {
                // Remove the item from the bag if it's the last one
                itemInventoryMap.remove(item);
            }
        }
    }

    // Method to check if an item is in the bag
    public boolean contains(T item) {
        // Return true if the item is in the bag, false otherwise
        return itemInventoryMap.containsKey(item);
    }

    // Method to count the number of times an item is in the bag
    public int count(T item) {
        // Return the count of the item in the bag, or 0 if it doesn't exist
        return itemInventoryMap.getOrDefault(item, 0);
    }

    // Method to calculate the total number of items in the bag, including duplicates
    public int size() {
        // Initialize a variable to store the total count
        int totalSize = 0;
        for (int count : itemInventoryMap.values()) {
            totalSize += count;
        }
        return totalSize;
    }

    // Method to merge another bag into the current bag
    public void merge(Bag<T> otherBag) {
        // Iterate over the other bag and add each item to the current bag
        for (Map.Entry<T, Integer> entry : otherBag.itemInventoryMap.entrySet()) {
            T item = entry.getKey();
            int count = entry.getValue();
            itemInventoryMap.put(item, itemInventoryMap.getOrDefault(item, 0) + count);
        }
    }

    // Method to return a new bag with only the distinct elements from the current bag
    public Bag<T> distinct() {
        Bag<T> distinctBag = new Bag<>();
        for (T item : itemInventoryMap.keySet()) {
            distinctBag.add(item); // Add each distinct item once to the new bag
        }
        return distinctBag;
    }

    // Method to print the contents of the bag
    public void printContents() {
        if (logger.isLoggable(java.util.logging.Level.INFO)) {
            logger.info("Player's Inventory: ");
            // Iterate over the map and print the items and their counts
            for (Map.Entry<T, Integer> entry : itemInventoryMap.entrySet()) {
                logger.info(String.format("%s: %d", entry.getKey(), entry.getValue()));
            }
        }
    }

    // Main method to test the Bag class
    public static void main(String[] args) {
        // Create an instance of the Bag class to represent the player's inventory
        Bag<String> playerInventory = new Bag<>();
        Bag<String> newPartyMemberInventory = new Bag<>();

        // Add items to each bag, including duplicates, to test the add method
        playerInventory.add(POTION);
        playerInventory.add(POTION);
        playerInventory.add(POTION);
        playerInventory.add(ELIXIR);
        playerInventory.add(ELIXIR);
        playerInventory.add(ANTIDOTE);

        newPartyMemberInventory.add(REVIVE);
        newPartyMemberInventory.add(POTION);
        newPartyMemberInventory.add(ELIXIR);

        // Print the size of each bag using the size method
        logger.info("Size of Player's Inventory: " + playerInventory.size()); // Expected output: 6
        logger.info("Size of New Party Member's Inventory: " + newPartyMemberInventory.size()); // Expected output: 3

        // Merge the two bags together using the merge method
        playerInventory.merge(newPartyMemberInventory);

        // Print the contents of the bag
        logger.info("Merged Inventory: ");
        playerInventory.printContents(); // Expected output: Potion: 4, Elixir: 3, Antidote: 1, Revive: 1

        // Create a new bag with only the distinct elements from the merged bag
        Bag<String> distinctBag = playerInventory.distinct();

        // Print the contents of the distinct bag
        logger.info("Distinct Inventory: ");
        distinctBag.printContents(); // Expected output: Potion: 1, Elixir: 1, Antidote: 1, Revive: 1
        
    }
}