public class Queens {
  private static final int EMPTY = 0;
  private static final int QUEEN   = 1;
  private static final int BLOCKED = -1;
	private static final int UNBLOCKED = 1;
  private int[][] board = new int[8][8];
  private int queenCount = 0;
	private boolean DEBUG = false;
  public Queens() {

  }
  public boolean driver() {
    return build(0);
  }
  public boolean build(int row) {
		// If we have reached the last row we're done
    if(row == board.length) {
      return true;
    }
		
		// Find the queen position for this row
		// Determine the first unused column for this row.
    for(int c = 0; c < board.length; c++) {
			if(DEBUG) {
				System.out.printf("  b:%d Column %d\n", row, c);
				print();
			}
			if(board[row][c] <= BLOCKED) continue;
			/*
			 * We have selected a column.
			 * Now see if this column is occupied for any prior row.
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
      if(skipToNextColumn) {
        continue;
      }
			block(row, c);
			// Find the queen position for the next row.
      boolean retval = build(row+1);
      if(retval) {
        return retval;
      } else {
        unblock(row, c);
      }
    }
    return false;
  }
	private void block(int row, int column) {
		board[row][column] = QUEEN;
		set(row, column, BLOCKED);
	}
	private void unblock(int row, int column) {
		set(row, column, UNBLOCKED);
		board[row][column] = EMPTY;
	}
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
	public void print() {
		for(int[] row:board) {
			for(int cell:row) {
				String s = null;
				switch (cell) {
					case QUEEN: s = "Q"; break;
					case EMPTY: s = "."; break;
					default: s = DEBUG?"x":"."; break;
				}
				System.out.printf("%2s  ", s);
			}
			System.out.println();
		}
	}
  public static void main(String[] x) {
    Queens q = new Queens();
    q.driver();
		q.print();
  }
}
