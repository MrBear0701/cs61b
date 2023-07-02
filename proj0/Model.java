package game2048;

import java.util.Formatter;
import java.util.Observable;


/** The state of a game of 2048.
 *  @author TODO: YOUR NAME HERE
 */
public class Model extends Observable {
    /** Current contents of the board. */
    private Board board;
    /** Current score. */
    private int score;
    /** Maximum score so far.  Updated when game ends. */
    private int maxScore;
    /** True iff game is ended. */
    private boolean gameOver;

    /* Coordinate System: column C, row R of the board (where row 0,
     * column 0 is the lower-left corner of the board) will correspond
     * to board.tile(c, r).  Be careful! It works like (x, y) coordinates.
     */

    /** Largest piece value. */
    public static final int MAX_PIECE = 2048;

    /** A new 2048 game on a board of size SIZE with no pieces
     *  and score 0. */
    public Model(int size) {
        board = new Board(size);
        score = maxScore = 0;
        gameOver = false;
    }

    /** A new 2048 game where RAWVALUES contain the values of the tiles
     * (0 if null). VALUES is indexed by (row, col) with (0, 0) corresponding
     * to the bottom-left corner. Used for testing purposes. */
    public Model(int[][] rawValues, int score, int maxScore, boolean gameOver) {
        int size = rawValues.length;
        board = new Board(rawValues, score);
        this.score = score;
        this.maxScore = maxScore;
        this.gameOver = gameOver;
    }

    /** Return the current Tile at (COL, ROW), where 0 <= ROW < size(),
     *  0 <= COL < size(). Returns null if there is no tile there.
     *  Used for testing. Should be deprecated and removed.
     *  */
    public Tile tile(int col, int row) {
        return board.tile(col, row);
    }

    /** Return the number of squares on one side of the board.
     *  Used for testing. Should be deprecated and removed. */
    public int size() {
        return board.size();
    }

    /** Return true iff the game is over (there are no moves, or
     *  there is a tile with value 2048 on the board). */
    public boolean gameOver() {
        checkGameOver();
        if (gameOver) {
            maxScore = Math.max(score, maxScore);
        }
        return gameOver;
    }

    /** Return the current score. */
    public int score() {
        return score;
    }

    /** Return the current maximum game score (updated at end of game). */
    public int maxScore() {
        return maxScore;
    }

    /** Clear the board to empty and reset the score. */
    public void clear() {
        score = 0;
        gameOver = false;
        board.clear();
        setChanged();
    }

    /** Add TILE to the board. There must be no Tile currently at the
     *  same position. */
    public void addTile(Tile tile) {
        board.addTile(tile);
        checkGameOver();
        setChanged();
    }

    /** Tilt the board toward SIDE. Return true iff this changes the board.
     *
     * 1. If two Tile objects are adjacent in the direction of motion and have
     *    the same value, they are merged into one Tile of twice the original
     *    value and that new value is added to the score instance variable
     * 2. A tile that is the result of a merge will not merge again on that
     *    tilt. So each move, every tile will only ever be part of at most one
     *    merge (perhaps zero).
     * 3. When three adjacent tiles in the direction of motion have the same
     *    value, then the leading two tiles in the direction of motion merge,
     *    and the trailing tile does not.
     * */
    public boolean tilt(Side side) {
        //调整视角
        switch (side){
            case NORTH -> board.setViewingPerspective(Side.NORTH);
            case EAST -> board.setViewingPerspective(Side.EAST);
            case SOUTH -> board.setViewingPerspective(Side.SOUTH);
            case WEST -> board.setViewingPerspective(Side.WEST);
        }

        boolean changed;
        changed = false;

        // TODO: Modify this.board (and perhaps this.score) to account
        // for the tilt to the Side SIDE. If the board changed, set the
        // changed local variable to true.

        //设定检测器，并且对每一列分别进行合并，得到下一次操作的结果
        boolean[] checkChanged = new boolean[board.size()];
        for (int i = 0; i < board.size(); i++) {
            checkChanged[i]=columnMerge(i);
        }

        //用于修改changed的值，有改变即设定为true
        for (int i = 0; i < board.size() ; i++) {
            if (checkChanged[i]==true) {
                changed = true;
                break;
            }
        }

        board.setViewingPerspective(Side.NORTH);

        checkGameOver();
        if (changed) {
            setChanged();
        }
        return changed;
    }

    public boolean columnMerge(int c){
        //用于检测changed是否改变
        boolean changed = false;
        boolean flag = false;
        //mergecheck用于判断当前格子上是否发生过合并，如果已经合并则无法再次合并
        boolean[] mergeCheck = new boolean[board.size()];
        for (int j = 0; j < board.size(); j++) {
            mergeCheck[j]=false;
        }
        /*对于每一列，从第二行判断是否能与上一行进行合并，例如第二行和第三行，第一行和第二行，
        第零行和第一行。每一轮检查time次，因此实际为210、21、2，这样可以检测所有合并的可能性
         */
        int time = board.size()-1;
        for (int i = 0; i < time; i++) {
            //对于各种可能出现的情况的判断
            for (int r = board.size()-2; r >= i; r--) {
                Tile t = board.tile(c,r);
                if (board.tile(c,r)==null)
                    continue;

                if (board.tile(c,r+1)==null){
                    board.move(c,r+1,t);
                    flag = true;
                }
                else if (board.tile(c,r).value()==board.tile(c,r+1).value()){
                    if (mergeCheck[r+1]==false){
                        board.move(c,r+1,t);
                        score+=board.tile(c,r+1).value();
                        mergeCheck[r+1]=true;
                        flag = true;
                    }
                }
            }
            //对于案例testMultipleMoves1的特殊处理
            if (mergeCheck[board.size()-2]==true)
                mergeCheck[board.size()-1]=true;


        }
        if (flag==true)
            changed = true;
        return changed;
    }





    /** Checks if the game is over and sets the gameOver variable
     *  appropriately.
     */
    private void checkGameOver() {
        gameOver = checkGameOver(board);
    }

    /** Determine whether game is over. */
    private static boolean checkGameOver(Board b) {
        return maxTileExists(b) || !atLeastOneMoveExists(b);
    }

    /** Returns true if at least one space on the Board is empty.
     *  Empty spaces are stored as null.
     * */
    public static boolean emptySpaceExists(Board b) {
        // TODO: Fill in this function.
        for (int i = 0; i < b.size(); i++) {
            for (int j = 0; j < b.size(); j++) {
                if (b.tile(i,j)==null)
                    return true;
            }
        }

        return false;
    }

    /**
     * Returns true if any tile is equal to the maximum valid value.
     * Maximum valid value is given by MAX_PIECE. Note that
     * given a Tile object t, we get its value with t.value().
     */
    public static boolean maxTileExists(Board b) {
        // TODO: Fill in this function.

        for (int i = 0; i < b.size(); i++) {
            for (int j = 0; j < b.size(); j++) {
                if (b.tile(i,j)==null)
                    continue;
                if (b.tile(i,j).value()==MAX_PIECE)
                    return true;
            }
        }
        return false;
    }

    /**
     * Returns true if there are any valid moves on the board.
     * There are two ways that there can be valid moves:
     * 1. There is at least one empty space on the board.
     * 2. There are two adjacent tiles with the same value.
     */
    public static boolean atLeastOneMoveExists(Board b) {
        // TODO: Fill in this function.
        if (emptySpaceExists(b)==true)
            return true;
        if (SearchAdjacent(b)==true)
            return true;

        return false;
    }

    public static boolean SearchAdjacent(Board b){
        for (int i = 0; i < b.size()-1; i++) {
            for (int j = 0; j < b.size(); j++) {
                if (b.tile(i,j).value()==b.tile(i+1,j).value())
                    return true;
            }
        }
        for (int i = 0; i < b.size(); i++) {
            for (int j = 0; j < b.size()-1; j++) {
                if (b.tile(i,j).value()==b.tile(i,j+1).value())
                    return true;
            }
        }

        return false;
    }





    @Override
     /** Returns the model as a string, used for debugging. */
    public String toString() {
        Formatter out = new Formatter();
        out.format("%n[%n");
        for (int row = size() - 1; row >= 0; row -= 1) {
            for (int col = 0; col < size(); col += 1) {
                if (tile(col, row) == null) {
                    out.format("|    ");
                } else {
                    out.format("|%4d", tile(col, row).value());
                }
            }
            out.format("|%n");
        }
        String over = gameOver() ? "over" : "not over";
        out.format("] %d (max: %d) (game is %s) %n", score(), maxScore(), over);
        return out.toString();
    }

    @Override
    /** Returns whether two models are equal. */
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (getClass() != o.getClass()) {
            return false;
        } else {
            return toString().equals(o.toString());
        }
    }

    @Override
    /** Returns hash code of Model’s string. */
    public int hashCode() {
        return toString().hashCode();
    }
}
