/**
 * Generates one or more solutions for the 8queens problem.
 * http://en.wikipedia.org/wiki/Eight_queens_puzzle
 */
public class Queens {
  /** An empty cell */
  private static final int EMPTY = 0;
  /** A cell with a queen */
  private static final int QUEEN   = 1;
  /** Blocking operation.  A blocking operation may be invoked
    on a cell more than once. */
  private static final int BLOCK = -1;
  /** Unblocking operation.  An unblocking operation may be invoked
    on a cell more than once. */
  private static final int UNBLOCK = 1;
  /** Default board size */
  private static final int DEFAULT_BOARD_SIZE = 8;
  /** Actual board size */
  private int size;
  /** Chess board */
  private int[][] board;
	/** Command line option to toggle debug mode on */
	private static final String OPT_DEBUG = "--debug";
	/** Flag that indicates that we are in debug mode */
  public boolean debug = false;
  /**
   * Creates a problem solver with the {@link DEFAULT_BOARD_SIZE} board.
   */
  public Queens() {
    this(DEFAULT_BOARD_SIZE);
  }
  /**
   * Creates a problem solver of the specified size board.
   */
  public Queens(int size) {
		this.size = size;
    board = new int[size][size];
  }
  public void getAllSolutions() {
	  for(int i = 0; i < size; i++) {
		  board = new int[size][size];
			placeQueen(0, i);
			build(1);
			print();
			System.out.println();
	  }
  }
  /**
   * Generates a solution.
   */
  public void solve() {
    build(0);
  }
  /**
   * Determines the position of a queen for the specified <tt>row</tt>.
   * This method is typically invoked recursively to position the queen
   * on <b>all</b> rows.
   * @param row The row for which to determine the queen's position.
   * @return <tt>true</tt> if successful in finding a spot for the queen and <tt>false</tt>
     otherwise.
   */
  private boolean build(int row) {
    // If we have reached the last row we're done
    if(row == board.length) {
      return true;
    }
		
    // Find the queen position for this row
    // Determine the first unused column for this row.
    for(int c = 0; c < board.length; c++) {
      if(debug) {
        System.out.printf("  b:%d Column %d\n", row, c);
        print();
      }
      
      // Cannot use a cell that has already been blocked.
      if(isBlocked(row, c)) continue;
      
      /*
       * Determine if this column is occupied on any prior row.
       */
      boolean skipToNextColumn = false;
      for(int r = 0; r < row; r++) {
        /*
          * If this cell is taken we don't need to check
          * this column for any more rows.  Time to skip
          * to the next column.
          */
        if(board[r][c] == QUEEN) {
          skipToNextColumn = true;
          break;
        }
      }
      // If cell [row][c] is unavailable try the next column.
      if(skipToNextColumn) {
        continue;
      }
      
      // Yay! Can place queen here.
      placeQueen(row, c);
      
      // Find the queen position for the next row.
      boolean retval = build(row+1);
      
      // If queen could not be placed on the next row, the placement
      // on the current row is invalid and must be undone.
      if(retval) {
        return retval;
      } else {
        removeQueen(row, c);
      }
    }
    return false;
  }
  /**
   * Indicates whether the cell identified by the parameters is already blocked.
   * @param row The row.
   * @param row The column.
   */
  private boolean isBlocked(final int row, final int column) {
    return board[row][column] <= BLOCK;
  }
  private void placeQueen(int row, int column) {
    board[row][column] = QUEEN;
    set(row, column, BLOCK);
  }
  private void removeQueen(int row, int column) {
    set(row, column, UNBLOCK);
    board[row][column] = EMPTY;
  }
  /**
   * Sets the value of all cells visible to a queen at <tt>row</tt>, <tt>column</tt>
   * to the specified <tt>value</tt>.
   */
  private void set(int row, int column, int value) {
    // set row
    for(int c = 0; c < board.length; c++) {
      if(c == column) continue;
      board[row][c] += value;
    }
    // set column
    for(int r = 0; r < board.length; r++) {
      if(r == row) continue;
      board[r][column] += value;
    }
    // set sw
    for(int r = row + 1, c = column - 1; r < board.length && c > -1; r++, c--) {
      board[r][c] += value;
    }
    // set ne
    for(int r = row - 1, c = column + 1; r > -1 && c < board.length; r--, c++) {
      board[r][c] += value;
    }
    // set nw
    for(int r = row - 1, c = column - 1; r > -1 && c > -1; r--, c--) {
      board[r][c] += value;
    }
    // set se
    for(int r = row + 1, c = column + 1; r < board.length && c < board.length; r++, c++) {
      board[r][c] += value;
    }
  }
  /**
   * Prints the board to stdout.
   */
  public void print() {
    for(int[] row:board) {
      for(int cell:row) {
        String s = null;
        switch (cell) {
          case QUEEN: s = "Q"; break;
          case EMPTY: s = "."; break;
          default: s = debug?"x":"."; break;
        }
        System.out.printf("%2s  ", s);
      }
      System.out.println();
    }
    public static void printUsage() {
			System.out.println("Usage:");
			System.out.println("  'program-name'         -- Computes one solution");
			System.out.println("  'program-name all'     -- Computes all solutions");
			System.out.println("  'program-name --debug' -- Displays intermediate steps");
    }
  }
	
	/**
		* Driver
		*/
  public static void main(String[] args) {
		
    Queens q = new Queens();
		boolean all = false; // solve for all solutions?
    
    // Process command line arguments
		for(String arg:args) {
			if(OPT_DEBUG.equals(arg)) {
				q.debug = true;
      } else if("all".equals(arg)) {
				all = true;
      } else if(arg.)
      } else if("-h".equals(arg) || "--help".equals(arg) || "--usage".equals(arg)) {
        Queens.printUsage();
				System.exit(0);
      } else {
        System.out.println("Invalid flag:" + arg);
        printUsage();
      }
		}
    
    // Run the task
		if(all) {
			q.getAllSolutions();
		} else {
			q.solve();
			q.print();
		}
  }
}
