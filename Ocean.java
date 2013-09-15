/* Ocean.java */

/**
 *  The Ocean class defines an object that models an ocean full of sharks and
 *  fish.  Descriptions of the methods you must implement appear below.  They
 *  include a constructor of the form
 *
 *      public Ocean(int i, int j, int starveTime);
 *
 *  that creates an empty ocean having width i and height j, in which sharks
 *  starve after starveTime timesteps.
 *
 *  See the README file accompanying this project for additional details.
 */

public class Ocean {

  /**
   *  Do not rename these constants.  WARNING:  if you change the numbers, you
   *  will need to recompile Test4.java.  Failure to do so will give you a very
   *  hard-to-find bug.
   */

  public final static int EMPTY = 0;
  public final static int SHARK = 1;
  public final static int FISH = 2;

  /**
   *  Define any variables associated with an Ocean object here.  These
   *  variables MUST be private.
   */
    private int width;
    private int height;
    private int starveTime;
    private int[][] ocean;
    private int[][] hunger;
    private int[] neighbors = new int[8];


  /**
   *  The following methods are required for Part I.
   */

  /**
   *  Ocean() is a constructor that creates an empty ocean having width i and
   *  height j, in which sharks starve after starveTime timesteps.
   *  @param i is the width of the ocean.
   *  @param j is the height of the ocean.
   *  @param starveTime is the number of timesteps sharks survive without food.
   */

  public Ocean(int i, int j, int starveTime) {
    ocean = new int[i][j];
    hunger = new int[i][j];
    width = i;
    height = j;
    this.starveTime = starveTime;
  }

  /**
   *  width() returns the width of an Ocean object.
   *  @return the width of the ocean.
   */

  public int width() {
    return width;
  }

  /**
   *  height() returns the height of an Ocean object.
   *  @return the height of the ocean.
   */

  public int height() {
    return height;
  }

  /**
   *  starveTime() returns the number of timesteps sharks survive without food.
   *  @return the number of timesteps sharks survive without food.
   */

  public int starveTime() {
      return starveTime;
  }

  /**
   *  addFish() places a fish in cell (x, y) if the cell is empty.  If the
   *  cell is already occupied, leave the cell as it is.
   *  @param x is the x-coordinate of the cell to place a fish in.
   *  @param y is the y-coordinate of the cell to place a fish in.
   */

    private int wrapX(int x){
	if(x<0){
	    return width + x%width;
}
return x%width;
}



    private int modY(int y){
	if(y>=height){
	    return y=y%height;
	} else{
	    return y=y;
	}
    }

  public void addFish(int x, int y) {
      ocean[moduX(x)][moduY(y)] = FISH;
  }

  /**
   *  addShark() (with two parameters) places a newborn shark in cell (x, y) if
   *  the cell is empty.  A "newborn" shark is equivalent to a shark that has
   *  just eaten.  If the cell is already occupied, leave the cell as it is.
   *  @param x is the x-coordinate of the cell to place a shark in.
   *  @param y is the y-coordinate of the cell to place a shark in.
   */

  public void addShark(int x, int y) {
      ocean[x][y] = SHARK;
      hunger[x][y] = starveTime;
  }

  /**
   *  cellContents() returns EMPTY if cell (x, y) is empty, FISH if it contains
   *  a fish, and SHARK if it contains a shark.
   *  @param x is the x-coordinate of the cell whose contents are queried.
   *  @param y is the y-coordinate of the cell whose contents are queried.
   */

  public int cellContents(int x, int y) {
      return ocean[x][y];

  }

  /**
   *  timeStep() performs a simulation timestep as described in README.
   *  @return an ocean representing the elapse of one timestep.
   */

  public Ocean timeStep() {
      Ocean nextOcean =  new Ocean(width(), height(), starveTime());

      //SET UP 8 NEIGHBORS
      for(int n=0;n<height();n++) {
	  for(int m=0;m<width();m++) {
	      neighbors[0] = top(m,n);
	      neighbors[1] = bottom(m,n);
	      neighbors[2] = left(m,n);
	      neighbors[3] = right(m,n);
	      neighbors[4] = NW(m,n);
	      neighbors[5] = NE(m,n);
	      neighbors[6] = SW(m,n);
	      neighbors[7] = SE(m,n);

	      //IF SHARK AND HAS FISH TO EAT THEN KEEP SHARK AND RESET HUNGER
	      if(cellContents(m,n)==SHARK){
		  if(fishFood()){
		      //nextOcean.ocean[m][n]=SHARK;
		      nextOcean.addShark(m,n);
		      nextOcean.hunger[m][n]=starveTime;
		  } 
		  //IF SHARK BUT NO FISH TO EAT THEN INCREMENT HUNGER
		  else{
		      nextOcean.hunger[m][n]=hunger[m][n]-1;
		      //IF HUNGER IS LESS THAN 0 THEN IT DIES AND RESET HUNGER
		      if(nextOcean.hunger[m][n]<0){
			  nextOcean.ocean[m][n]=EMPTY;
			  nextOcean.hunger[m][n]=0;
			  //IF HUNGER IS NOT MORE THAN STARVETIME THEN KEEP SHARK
		      } else {
			  //nextOcean.ocean[m][n]=SHARK;
			  nextOcean.ocean[m][n]=SHARK;
		      }
		  }
	      }
	      //IF FISH HAS SHARKS AROUND THEN IT DIES
	      if(cellContents(m,n)==FISH){
		  if(areSharksAround()){
		      //IF FISH AND AT LEAST 2 SHARKS AROUND THIS SPACE THEN PUT SHARK THERE
		      if(numSharks()>=2){
		      	  nextOcean.addShark(m,n);
		      } else{
		      nextOcean.ocean[m][n]=EMPTY;
		      }
		  }
		  //IF NO SHARKS AROUND THEN FISH LIVES
		  else{
		      nextOcean.ocean[m][n]=FISH;
		  }
	      }
	      
	      //IF CELL EMPTY
	         if(cellContents(m,n)==EMPTY){
		     //IF THERE ARE AT LEAST 2 FISH BUT AT MOST 1 SHARK, BIRTH FISH
		  if(numFish()>=2 && numSharks()<=1){
		      nextOcean.ocean[m][n]=FISH;
		  } 
		  //IF THERE ARE AT LEAST 2 FISH AND AT LEAST 2 SHARKS, BIRTH SHARK
		  else if(numFish()>=2 && numSharks()>=2){
		      nextOcean.addShark(m,n);
		  } else{}
		  } 
	  }
      }
   return nextOcean;
  }

      private int numFish(){
	int numFish=0;
	for(int p=0;p<neighbors.length;p++){
	    if(neighbors[p]==FISH){
		numFish++;
	    } else{}
	}
	return numFish;
      }

    private boolean fishFood(){
	if(numFish()>0){
	    return true;
	} else {
	    return false;
	}
    }

    private int numSharks() {
	int numSharks=0;
	for(int p=0;p<neighbors.length;p++){
	    if(neighbors[p]==SHARK){
		numSharks++;
	    } else{}
	}
	return numSharks;
    }

    private boolean areSharksAround() {
	if(numSharks()>0){
	    return true;
	} else{
	return false;
	}
    }

    private int abs(int n) {
	if(n<0) {
	    return (n=n*(-1));
	} else {
	    return n;
	}
    }

    private int moduX(int x) {
	/*x = abs(x);
	if(x>=width) {
	    x=x%width;
	} else {
	    x=x;
	}
	return x;
	*/
	if (x<0){
	    return width + x%width;
	}
	else{
	    return x%width;
	}
    }

    private int moduY(int y) {
	/*	y = abs(y);
	if(y>=height) {
	    y=y%height;
	} else {
	    y=y;
	}
	return y;
	*/
	if(y<0){
	    return height + y % height;
	}
	else {
	    return y%height;
	}
    }

 
    private int bottom(int x, int y) {
    // x and y are coordinates of the points for which you want to find the contents of the cell BELOW. 
	if(y==height()-1) {
	    return cellContents(moduX(x),moduY(0));
				} else {
	return cellContents(moduX(x),moduY(y+1));
	    }
    }

    private int top (int x, int y) {
	if(y==0) {
	    return cellContents(moduX(x),moduY(height()-1));
				} else {
	    return cellContents(moduX(x),moduY(y-1));
	    }
      }

    private int left (int x, int y) {
	if(x==0) {
	    return cellContents(moduX(width()-1),moduY(y));
				} else {
      return cellContents(moduX(x-1),moduY(y));
	    }
      }

    private int right (int x, int y) {
	if(x==width()-1) {
	    return cellContents(moduX(0),moduY(y));
	} else {
	    return cellContents(moduX(x+1),moduY(y));
	    }
      }

    private int NW (int x, int y) {
	if(y==0) {
	    if(x==0) {
		return cellContents(moduX(width()-1),moduY(height()-1));
	    } else{
		return cellContents(moduX(x-1),moduY(height()-1));
	    }
	} else if(x==0) {
	    return cellContents(moduX(width()-1),moduY(y-1));
	} else {
      return cellContents(moduX(x-1),moduY(y-1));
      }
    }

    private int NE (int x, int y) {
	if(y==0) {
	    if(x==width()-1){
		return cellContents(moduX(0),moduY(height()-1));
	    } else{
		return cellContents(moduX(x+1),moduY(height()-1));
	    } 
	} else if(x==width()-1){
	    return cellContents(moduX(0),moduY(y-1));
	} else{
      return cellContents(moduX(x+1),moduY(y-1));
      }
    }

    private int SW (int x, int y) {
	if(y==height()-1){
	    if(x==0){
		return cellContents(moduX(width()-1),moduY(0));
		    } else{
		return cellContents(moduX(x-1),moduY(0));
	    }
	} 
	else if(x==0) {
	    return cellContents(moduX(width()-1),moduY(y+1));
	} else {
      return cellContents(moduX(x-1),moduY(y+1));
      }
    }

    private int SE (int x, int y) {
	if(y==height()-1){
	    if(x==width()-1){
		return cellContents(moduX(0),moduY(0));
	    } else {
		return cellContents(moduX(x+1),moduY(0));
	    }
	}
	else if(x==width()-1){
	    return cellContents(moduX(0),moduY(y+1));
	}
	else{
	    return cellContents(moduX(x+1),moduY(y+1));
	}
    }



    

  /**
   *  The following method is required for Part II.
   */

  /**
   *  addShark() (with three parameters) places a shark in cell (x, y) if the
   *  cell is empty.  The shark's hunger is represented by the third parameter.
   *  If the cell is already occupied, leave the cell as it is.  You will need
   *  this method to help convert run-length encodings to Oceans.
   *  @param x is the x-coordinate of the cell to place a shark in.
   *  @param y is the y-coordinate of the cell to place a shark in.
   *  @param feeding is an integer that indicates the shark's hunger.  You may
   *         encode it any way you want; for instance, "feeding" may be the
   *         last timestep the shark was fed, or the amount of time that has
   *         passed since the shark was last fed, or the amount of time left
   *         before the shark will starve.  It's up to you, but be consistent.
   */

  public void addShark(int x, int y, int feeding) {
      if(cellContents(x,y)==EMPTY){
	  this.ocean[x][y]=SHARK;
	  this.hunger[x][y] = feeding;
      } else{}
  }

  /**
   *  The following method is required for Part III.
   */

  /**
   *  sharkFeeding() returns an integer that indicates the hunger of the shark
   *  in cell (x, y), using the same "feeding" representation as the parameter
   *  to addShark() described above.  If cell (x, y) does not contain a shark,
   *  then its return value is undefined--that is, anything you want.
   *  Normally, this method should not be called if cell (x, y) does not
   *  contain a shark.  You will need this method to help convert Oceans to
   *  run-length encodings.
   *  @param x is the x-coordinate of the cell whose contents are queried.
   *  @param y is the y-coordinate of the cell whose contents are queried.
   */

  public int sharkFeeding(int x, int y) {
      if(cellContents(x,y)==EMPTY||cellContents(x,y)==FISH){
	  return 0;
      } else{
	  return this.hunger[x][y];
  }
  }



}
