/**
 * The node of a linked list
 * @author Harold Connamacher in EECS 132 (altered by Eddie Xu)
 */
public class LLNodeHash {
  /** the Strings stored in the node */
  private String key;
  
  /** Variable keep track of the frequency*/
  private int freq;
  
  /** a reference to the next node of the list */
  private LLNodeHash next;
  
  /**
   * the constructor
   * @param element  the element to store in the node
   * @param next  a reference to the next node of the list 
   */
  public LLNodeHash(String key, int freq, LLNodeHash next) {
    this.key = key;
    this.freq = freq;
    this.next = next;
  }
  
  /**
   * Returns the element stored in the node
   * @return the element stored in the node
   */
  public String getKey() {
    return this.key;
  }
  
  // Setter method to set the key
  public void setKey(String key) {
    this.key = key;
  }
  
  // Getter method to get the frequency of a word
  public int getValue() {
    return this.freq;
  }
  
  // Setter method to set the frequency of a word
  public void setValue(int freq) {
    this.freq = freq;
  }
  
  /**
   * Returns the next node of the list
   * @return the next node of the list
   */
  public LLNodeHash getNext() {
    return next;
  }
  
  /**
   * Changes the node that comes after this node in the list
   * @param next  the node that should come after this node in the list.  It can be null.
   */
  public void setNext(LLNodeHash next) {
    this.next = next;
  }
}
