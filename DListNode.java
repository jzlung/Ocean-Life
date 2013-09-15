/* DListNode.java */

/**
 *  A DListNode is a node in a DList (doubly-linked list).
 */

//THIS IMPLEMENTATION WORKS WITH INT[] ARRAYS FOR PJ1.

public class DListNode {

  /**
   *  item references the item stored in the current node.
   *  prev references the previous node in the DList.
   *  next references the next node in the DList.
   *
   *  DO NOT CHANGE THE FOLLOWING FIELD DECLARATIONS.
   */

  public int[] item = new int[2];
  public DListNode prev;
  public DListNode next;
  public int hunger = 0;

  /**
   *  DListNode() constructor.
   */

  DListNode() {
      //item[0]=Integer.MIN_VALUE;
      //item[1]=Integer.MIN_VALUE;
    prev = null;
    next = null;
  }
    

    DListNode(int runType, int runLength) {
    item[0] = runType;
    item[1] = runLength;
    prev = null;
    next = null;
  }
}
