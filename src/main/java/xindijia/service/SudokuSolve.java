package xindijia.service;


/**
 * @author xia
 * @since 2024/1/6/14:18
 */
public class SudokuSolve {
    private static final int SIZE = 9;

    private int[][] board;

    public SudokuSolve() {
        board = new int[SIZE][SIZE];
    }

    public int[][] getBoard() {
        return board;
    }


    public void backtrack(int count, int[][] map) {
        if (count == 81) {//到达最后一个位置
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    this.board[i][j] = map[i][j];
                }
            }
            return;
        }
        int row = count / SIZE;
        int col = count % SIZE;
        if (map[row][col] == 0) {//待填空格
            for (int i = 1; i <= SIZE; i++) {
                map[row][col] = i;
                if (isPlace(count, map)) {
                    backtrack(count + 1, map);
                }
            }
            map[row][col] = 0;//恢复状态
        } else {//已经存在数字，进行下一个数字填补
            backtrack(count + 1, map);
        }
    }

    public boolean isPlace(int count, int[][] map) {
        int row = count / SIZE;
        int col = count % SIZE;
        //判断同一行是否有一样的元素
        for (int j = 0; j < SIZE; j++) {
            if (map[row][col] == map[row][j] && j != col) {
                return false;
            }
        }
        //判断同一列是否有相同的元素
        for (int i = 0; i < SIZE; i++) {
            if (map[row][col] == map[i][col] && i != row) {
                return false;
            }
        }
        //判断同一块中是否有相同的元素
        int beginRow = row / 3 * 3;
        int beginCol = col / 3 * 3;
        for (int i = beginRow; i < beginRow + 3; i++) {
            for (int j = beginCol; j < beginCol + 3; j++) {
                if (map[row][col] == map[i][j] && i != row && j != col) {
                    return false;
                }
            }
        }
        return true;
    }

}
