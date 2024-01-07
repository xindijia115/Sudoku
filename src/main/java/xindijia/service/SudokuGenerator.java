package xindijia.service;

import java.util.*;

public class SudokuGenerator {
    private static final int SIZE = 9;
    private int[][] board;
    private int[][] question;

    public SudokuGenerator() {
        board = new int[SIZE][SIZE];
        question = new int[SIZE][SIZE];
    }


    public void generate(int empty) {
        //根据参数生成不同难度的数独, empty 越大 难度越大
        solve(0);
        removeDigits(empty);
    }

    /**
     * 生成一个数独问题，含解
     */
    private boolean solve(int count) {
        if (count == 81) {//已经填满了,找到一个数独问题
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    question[i][j] = board[i][j];
                }
            }
            return true;
        }
        //一维转二维对应的坐标
        int row = count / SIZE;
        int col = count % SIZE;

        List<Integer> numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
        Collections.shuffle(numbers);//打乱顺序

        for (int num : numbers) {
            board[row][col] = num;
            if (isPlace(count, board)) {
                if (solve(count + 1)) {
                    return true;//终止
                }
            }
        }
        board[row][col] = 0; // 回溯
        return false;
    }

    //判断是否可填
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

    /**
     * 取出若干数便成了待填数组
     */
    private void removeDigits(int empty) {
        Random random = new Random();
        //int empty = 40; // 控制空白单元格的数量
        while (empty > 0) {
            int row = random.nextInt(SIZE);//在0-8之间生成坐标
            int col = random.nextInt(SIZE);
            if (question[row][col] != 0) {
                question[row][col] = 0;
                empty--;
            }
        }
    }

    //获得问题
    public int[][] getQuestion() {
        return question;
    }

    //获得问题的解
    public int[][] getBoard() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        return board;
    }

}
