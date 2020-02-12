import java.util.*;
import java.io.*;

public class WordCounter {
  // Array of LLNodeHash that makes up the hash table
  private LLNodeHash[] table;
  // The number of indeces that are being used in the array
  private int itemsInMap;
  // the ratio of the used indeces ot the total indeces
  private double loadFactor;
  // The total number of indeces in the array
  private int tableSize;
  
  
  // Method to make the hash table
  public void makeTable(){
    table = new LLNodeHash[2];
  }
  
  
  // Method to check the Load Factor
  public boolean checkLf() {
    itemsInMap = 0;
    tableSize = table.length;
    // Looping through the table to see if there are entries
    for(int i = 0; i < tableSize; i++){
      // Every entry,
      if (table[i] != null) {
        // incremement the number of items in the map
        itemsInMap++; 
      }
    }
    // Load factor definition
    loadFactor = (double)itemsInMap / (double)table.length;
    // As directed, rehash if the load factor is greater than 0.75 (as instructed)
    if (loadFactor > 0.75) {
      rehash();
      return false;
    }
    return true;
  }
  
  // Method to increase our table by x2 and rehash the values
  public void rehash(){
    // Temporary table to hold the place so we can expand our current one
    LLNodeHash[] tempTable = table;
    int len = tempTable.length;
    // Making the table twice as large (as directed)
    table = new LLNodeHash[2 * table.length];
    
    for(int i = 0; i < len; i++) {
      // If there is an entry at the position
      if (tempTable[i] != null) {
        // Temporary variable to hold the place of the head node from our temporary table
        LLNodeHash temp = tempTable[i];
        // While there are more nodes in the list
        while (temp.getNext() != null){
          // get the node at that index
          LLNodeHash node = new LLNodeHash(temp.getKey(), temp.getValue(), null);
          // Add it to our new map
          putInMap(node);
          // Increment
          temp = temp.getNext();
        }
        // Repeat if the next value is null (as it is the end
        LLNodeHash node = new LLNodeHash(temp.getKey(), temp.getValue(),null);
        putInMap(node);
      }
    }
  }
  
  // Method to check the number of collisions in the hash table
  public double avgCollisions(){
    // Counter to count the number of collisions
    int counter = 0;
    for(int i = 0;i< tableSize;i++){
      if (table[i] != null) {
        // Set a temporary variable for the head of our Linked List
        LLNodeHash temp = table[i];
        // If there is an entry, increase the counter (head of the list counts)
        if (temp != null){
          counter++;
        }
        // iterate through the linked list (get the size)
        while(temp.getNext() != null){
          counter++;
          temp = temp.getNext();
        }
      }
    }
    // As instructed, formula for collisions
    return ((double)counter/ (double)itemsInMap);
  }
  
  // Method to look through the map to see if there are collisions
  public boolean wordInMap(LLNodeHash node){
    // Our index we are looking at
    int key = Math.abs(node.getKey().hashCode()) % table.length;
    // temporary variable as the head of the linked list
    LLNodeHash temp = table[key];
    // Looping through the linked list
    while (temp != null){
      // If there is a repeat of the key
      if (temp.getKey().equals(node.getKey())) {
        // Increment the value (frequency) of that key
        temp.setValue(temp.getValue() + node.getValue());
        return true;
      }
      // Loop through
      temp = temp.getNext();
    }
    return false;
  }
  
  // Method to put an entry into our table
  public void put(LLNodeHash node){
    // Again, we want to find the index of our word
    int key = Math.abs(node.getKey().hashCode()) % table.length;
    // If there is no entry in that index
    if (table[key] == null) {
      // Create a new linked list at that entry
      table[key] = new LLNodeHash(node.getKey(), node.getValue(), null);
    }
    
    else{
      LLNodeHash temp = table[key];
      // Otherwise loop through the list that is already at that entry
      while (temp.getNext() != null){
        temp = temp.getNext();
      }
      // At the end, add the new entry
      temp.setNext(new LLNodeHash(node.getKey(), node.getValue(), null));
    }
    checkLf();
  }
  
  // Method to put the node in the map if it is not
  public void putInMap(LLNodeHash node) {
    // If the word is not in the map
    if(!(wordInMap(node))){
      // Add it to the map
      put(node);
    }
  }
  
  // Extra Method I added to print things into my interaction pane to check
  public void print(){
    for(int i = 0;i< table.length;i++){
      // If entry is filled
      if (table[i] != null) {
        // Iterate through the linked list and print the frequency of words
        LLNodeHash temp = table[i];
        while(temp.getNext() != null){
          System.out.print("("+temp.getKey() +"="+temp.getValue()+"), ");
          // Increment while loop variable
          temp = temp.getNext();
        }
        // Also print out the end
        System.out.print("("+temp.getKey() +"="+temp.getValue()+"), ");
      }
    }
  }

  // Method to print out the file
  public void printStream(File outputFile){
    try {
      // Creating a new fileWriter to write our output (as we did last project)
      BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
      // Writing all the things onto our new file
      writer.write("Table Size: " + table.length + "\n");
      writer.write("Open spaces: " + (tableSize - itemsInMap) + "\n");
      writer.write("Load Factor: " + loadFactor + "\n");
      writer.write("Avg Collision Length: " + avgCollisions() + "\n");
      for (int i = 0; i < table.length; i++){
        // If there is an entry in the row
        if (table[i] != null) {
          // Iterate through the linked list and print out everything as instructed
          LLNodeHash temp = table[i];
          while (temp.getNext() != null){
            // Print out the word and frequency
            writer.write("("+temp.getKey() +"="+temp.getValue()+"), ");
            // Iterate through
            temp = temp.getNext();
          }
          // Outside the linked list, also print out word and frequency
          writer.write("("+temp.getKey() +"="+temp.getValue()+"), ");
        }
      }
      // Needed line because writer must be closed
      writer.close();
    }
    // If there is an error
    catch(IOException e){
      System.err.println("Error");
    }
  }
  
  // Helper method to read the file that I am inputting
  public void readInput(File inputFile){
    makeTable();
    try{
      // Use a scanner to take in inputs (found Delimiter online)
      Scanner scanner =  new Scanner(inputFile).useDelimiter("\\W+|\\n|\\r|\\t|, "); 
      // While there are more inputs in our file
      while (scanner.hasNext()){
        // Set a permanent variable as the "next" (as a lowerCase)
        String word = scanner.next().toLowerCase();
        // Temporary variable to hold the word
        LLNodeHash node = new LLNodeHash(word, 1, null);
        // add into map
        putInMap(node);
      }
    }
    // Again, if the file is not found then throw and exception
    catch(FileNotFoundException e){
      System.err.print("Error: Not Found");
    }
    // Printing out the output into the interactions pane to check
    System.err.println("TableSize is " + table.length);
    System.err.println("There are " + (tableSize-itemsInMap) + " open spaces");
    System.err.println("The Load Factor is " + loadFactor);
    System.err.println("The average collision length is " + avgCollisions());
    print();
  }
  
  // Method to call that will read the file input and do all of the methods
  public static String wordCount(String inputFileName, String outputFileName){
    // Creating two files (input and output) for the project
    File inputFile = new File(inputFileName);
    File outputFile = new File(outputFileName);
    // Creating an instance of the class to call
    WordCounter instance = new WordCounter();
    // Calling the two methods on
    instance.readInput(inputFile);
    instance.printStream(outputFile);
    
    return "";  
  }
  
  // Main method that allows us to run the function (Same as last project)
  public static void main(String[] args){
    wordCount(args[0],args[1]);
  }
}
