/* RunLengthEncoding.java */

/**
 *  The RunLengthEncoding class defines an object that run-length encodes an
 *  Ocean object.  Descriptions of the methods you must implement appear below.
 *  They include constructors of the form
 *
 *      public RunLengthEncoding(int i, int j, int starveTime);
 *      public RunLengthEncoding(int i, int j, int starveTime,
 *                               int[] runTypes, int[] runLengths) {
 *      public RunLengthEncoding(Ocean ocean) {
 *
 *  that create a run-length encoding of an Ocean having width i and height j,
 *  in which sharks starve after starveTime timesteps.
 *
 *  The first constructor creates a run-length encoding of an Ocean in which
 *  every cell is empty.  The second constructor creates a run-length encoding
 *  for which the runs are provided as parameters.  The third constructor
 *  converts an Ocean object into a run-length encoding of that object.
 *
 *  See the README file accompanying this project for additional details.
 */

public class RunLengthEncoding {

  /**
   *  Define any variables associated with a RunLengthEncoding object here.
   *  These variables MUST be private.
   */

    private int starveTime;
    private DList oceanRLE = new DList();
    private DListNode current = new DListNode();
    private int counts = 0;
    private int counters = 0;
    private int points = 0;
    private int widths;
    private int heights;
    private DListNode pointer = new DListNode();
    private DListNode pointer2 = new DListNode();
    private DListNode pointer3 = new DListNode();
    private int checkcount = 0;
    private int numSame=1;
    private int totalRunLengths=0;
    private DList rle = new DList();
    private int seaW;
    private int seaH;

  /**
   *  The following methods are required for Part II.
   */

    private DListNode shark(int runLength){
	DListNode shark = new DListNode(Ocean.SHARK, runLength);
	return shark;
    }

    private DListNode fish(int runLength){
	DListNode fish = new DListNode(Ocean.FISH, runLength);
	return fish;
    }

    private DListNode empty(int runLength){
	DListNode empty = new DListNode(Ocean.EMPTY, runLength);
	return empty;
    }


  /**
   *  RunLengthEncoding() (with three parameters) is a constructor that creates
   *  a run-length encoding of an empty ocean having width i and height j,
   *  in which sharks starve after starveTime timesteps.
   *  @param i is the width of the ocean.
   *  @param j is the height of the ocean.
   *  @param starveTime is the number of timesteps sharks survive without food.
   */

  public RunLengthEncoding(int i, int j, int starveTime) {
      DList emptyRun = new DList(Ocean.EMPTY,i*j);
      this.starveTime = starveTime;
  }

  /**
   *  RunLengthEncoding() (with five parameters) is a constructor that creates
   *  a run-length encoding of an ocean having width i and height j, in which
   *  sharks starve after starveTime timesteps.  The runs of the run-length
   *  encoding are taken from two input arrays.  Run i has length runLengths[i]
   *  and species runTypes[i].
   *  @param i is the width of the ocean.
   *  @param j is the height of the ocean.
   *  @param starveTime is the number of timesteps sharks survive without food.
   *  @param runTypes is an array that represents the species represented by
   *         each run.  Each element of runTypes is Ocean.EMPTY, Ocean.FISH,
   *         or Ocean.SHARK.  Any run of sharks is treated as a run of newborn
   *         sharks (which are equivalent to sharks that have just eaten).
   *  @param runLengths is an array that represents the length of each run.
   *         The sum of all elements of the runLengths array should be i * j.
   */

  public RunLengthEncoding(int i, int j, int starveTime,
                           int[] runTypes, int[] runLengths) {
      this.starveTime = starveTime;
      DList oceanRLE = new DList();
      for(int m=0;m<runTypes.length;m++){
	  oceanRLE.insertEnd(runTypes[m],runLengths[m]);
	  counts++;
      }
      current = oceanRLE.head;
      pointer = oceanRLE.head;
      pointer2 = oceanRLE.head;
      Ocean sea = new Ocean(i,j,starveTime);
      widths = sea.width();
      heights = sea.height();
  }



  

  /**
   *  restartRuns() and nextRun() are two methods that work together to return
   *  all the runs in the run-length encoding, one by one.  Each time
   *  nextRun() is invoked, it returns a different run (represented as an
   *  array of two ints), until every run has been returned.  The first time
   *  nextRun() is invoked, it returns the first run in the encoding, which
   *  contains cell (0, 0).  After every run has been returned, nextRun()
   *  returns null, which lets the calling program know that there are no more
   *  runs in the encoding.
   *
   *  The restartRuns() method resets the enumeration, so that nextRun() will
   *  once again enumerate all the runs as if nextRun() were being invoked for
   *  the first time.
   *
   *  (Note:  Don't worry about what might happen if nextRun() is interleaved
   *  with addFish() or addShark(); it won't happen.)
   */

  /**
   *  restartRuns() resets the enumeration as described above, so that
   *  nextRun() will enumerate all the runs from the beginning.
   */

  public void restartRuns() {
      // STARTS AT SENTINEL.
      current = oceanRLE.head;
  }

  /**
   *  nextRun() returns the next run in the enumeration, as described above.
   *  If the runs have been exhausted, it returns null.  The return value is
   *  an array of two ints (constructed here), representing the type and the
   *  size of the run, in that order.
   *  @return the next run in the enumeration, represented by an array of
   *          two ints.  The int at index zero indicates the run type
   *          (Ocean.EMPTY, Ocean.SHARK, or Ocean.FISH).  The int at index one
   *          indicates the run length (which must be at least 1).
   */

  public int[] nextRun() {
      current = current.next;
      if(counters<counts){
	  counters++;
	  return current.item;
      } else {
	  return null;
      }
  }

  /**
   *  toOcean() converts a run-length encoding of an ocean into an Ocean
   *  object.  You will need to implement the three-parameter addShark method
   *  in the Ocean class for this method's use.
   *  @return the Ocean represented by a run-length encoding.
   */

  public Ocean toOcean() {
      Ocean rleocean = new Ocean(widths, heights, starveTime);
      int x=0;
      int y=0;
      pointer = pointer.next;
      while(points<counts){
	  for(int m=0; m<pointer.item[1]; m++){
	      if(x==widths){
		  y++;
		  x=0;
		  if(pointer.item[0]==Ocean.FISH){
		      rleocean.addFish(x,y);
		  } else if(pointer.item[0]==Ocean.SHARK){
		      rleocean.addShark(x,y,pointer.hunger);
		  } else{}
	      } else{
		  if(pointer.item[0]==Ocean.FISH){
		      rleocean.addFish(x,y);
		  } else if(pointer.item[0]==Ocean.SHARK){
		      rleocean.addShark(x,y,pointer.hunger);
		  } else{}
	      }
	      x++;
	  }
	  points++;
	  pointer = pointer.next;
      }     
      return rleocean;
  }

  /**
   *  The following method is required for Part III.
   */

  /**
   *  RunLengthEncoding() (with one parameter) is a constructor that creates
   *  a run-length encoding of an input Ocean.  You will need to implement
   *  the sharkFeeding method in the Ocean class for this constructor's use.
   *  @param sea is the ocean to encode.
   */

  public RunLengthEncoding(Ocean sea) {
      widths = sea.width();
      heights = sea.height();
      seaW = sea.width();
      seaH = sea.height();
      int currentHunger=0;
      int currentLength=0;
      int currentType = sea.cellContents(0,0);
      currentHunger=sea.sharkFeeding(0,0);
      pointer = rle.head;
      current = rle.head;

      for(int j=0;j<seaH;j++){
	  for(int i=0;i<seaW;i++){
	      if(currentType != sea.cellContents(i,j) || currentHunger !=sea.sharkFeeding(i,j)){
		  //CREATE THE RUN/DUMP THE CONTENTS
		DListNode x = new DListNode(currentType,currentLength);
		x.hunger = currentHunger;
		rle.insertEnd(x);
		counts++;
		//RESET THE COUNTER
		currentType = sea.cellContents(i,j);
		currentLength=0;
		currentHunger = sea.sharkFeeding(i,j);
	      }
	      //ADD LENGTH TO THE COUNTER
	  currentLength++;

	  }
      }
      //FOR DUMPING THE LAST RUN
		DListNode x = new DListNode(currentType,currentLength);
		x.hunger = currentHunger;
		rle.insertEnd(x);
		counts++;
    check();
      }

  /**
   *  The following methods are required for Part IV.
   */

  /**
   *  addFish() places a fish in cell (x, y) if the cell is empty.  If the
   *  cell is already occupied, leave the cell as it is.  The final run-length
   *  encoding should be compressed as much as possible; there should not be
   *  two consecutive runs of sharks with the same degree of hunger.
   *  @param x is the x-coordinate of the cell to place a fish in.
   *  @param y is the y-coordinate of the cell to place a fish in.
   */

  public void addFish(int x, int y) {
    // Your solution here, but you should probably leave the following line
    //   at the end.
    check();
  }

  /**
   *  addShark() (with two parameters) places a newborn shark in cell (x, y) if
   *  the cell is empty.  A "newborn" shark is equivalent to a shark that has
   *  just eaten.  If the cell is already occupied, leave the cell as it is.
   *  The final run-length encoding should be compressed as much as possible;
   *  there should not be two consecutive runs of sharks with the same degree
   *  of hunger.
   *  @param x is the x-coordinate of the cell to place a shark in.
   *  @param y is the y-coordinate of the cell to place a shark in.
   */

  public void addShark(int x, int y) {
    // Your solution here, but you should probably leave the following line
    //   at the end.
    check();
  }

  /**
   *  check() walks through the run-length encoding and prints an error message
   *  if two consecutive runs have the same contents, or if the sum of all run
   *  lengths does not equal the number of cells in the ocean.
   */
    
    private void check() {
	
      DListNode pointer3 = rle.head;
      while(checkcount<rle.size){
      pointer3 = pointer3.next;
      if(pointer3.item[1]==0){
	  System.out.println("Run length less than 1 detected. Abort.");
      }
      if(pointer3.item[0]==pointer3.next.item[0]){
	  if(pointer3.item[0]==Ocean.SHARK){
	      if(pointer3.hunger==pointer3.next.hunger){
		  System.out.println("ERROR: These shark nodes must be combined.");
	      } else{}
	  } else{
	  System.out.println("ERROR: These nodes must be combined.");
	  }
      } else{}
      totalRunLengths+=pointer3.item[1];
	 checkcount++;
      }
	if(totalRunLengths !=seaW*seaH){
	    System.out.println("ERROR: Size mismatch!");
	} 
    }
	

}


