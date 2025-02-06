package com.zhang.java;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Date 2025/3/17 08:57
 * @Author zsy
 * @Description 蛇梯棋 类比Problem1654
 * 给你一个大小为 n x n 的整数矩阵 board ，方格按从 1 到 n2 编号，编号遵循 转行交替方式 ，
 * 从左下角开始 （即，从 board[n - 1][0] 开始）的每一行改变方向。
 * 你一开始位于棋盘上的方格  1。每一回合，玩家需要从当前方格 curr 开始出发，按下述要求前进：
 * 选定目标方格 next ，目标方格的编号在范围 [curr + 1, min(curr + 6, n2)] 。
 * 该选择模拟了掷 六面体骰子 的情景，无论棋盘大小如何，玩家最多只能有 6 个目的地。
 * 传送玩家：如果目标方格 next 处存在蛇或梯子，那么玩家会传送到蛇或梯子的目的地。
 * 否则，玩家传送到目标方格 next 。
 * 当玩家到达编号 n2 的方格时，游戏结束。
 * 如果 board[r][c] != -1 ，位于 r 行 c 列的棋盘格中可能存在 “蛇” 或 “梯子”。
 * 那个蛇或梯子的目的地将会是 board[r][c]。
 * 编号为 1 和 n2 的方格不是任何蛇或梯子的起点。
 * 注意，玩家在每次掷骰的前进过程中最多只能爬过蛇或梯子一次：就算目的地是另一条蛇或梯子的起点，玩家也 不能 继续移动。
 * 举个例子，假设棋盘是 [[-1,4],[-1,3]] ，第一次移动，玩家的目标方格是 2 。
 * 那么这个玩家将会顺着梯子到达方格 3 ，但 不能 顺着方格 3 上的梯子前往方格 4 。
 * （简单来说，类似飞行棋，玩家掷出骰子点数后移动对应格数，遇到单向的路径（即梯子或蛇）可以直接跳到路径的终点，但如果多个路径首尾相连，也不能连续跳多个路径）
 * 返回达到编号为 n2 的方格所需的最少掷骰次数，如果不可能，则返回 -1。
 * <p>
 * 输入：board = [
 * [-1,-1,-1,-1,-1,-1],
 * [-1,-1,-1,-1,-1,-1],
 * [-1,-1,-1,-1,-1,-1],
 * [-1,35,-1,-1,13,-1],
 * [-1,-1,-1,-1,-1,-1],
 * [-1,15,-1,-1,-1,-1]
 * ]
 * 输出：4
 * 解释：
 * 首先，从方格 1 [第 5 行，第 0 列] 开始。
 * 先决定移动到方格 2 ，并必须爬过梯子移动到到方格 15 。
 * 然后决定移动到方格 17 [第 3 行，第 4 列]，必须爬过蛇到方格 13 。
 * 接着决定移动到方格 14 ，且必须通过梯子移动到方格 35 。
 * 最后决定移动到方格 36 , 游戏结束。
 * 可以证明需要至少 4 次移动才能到达最后一个方格，所以答案是 4 。
 * <p>
 * 输入：board = [[-1,-1],[-1,3]]
 * 输出：1
 * <p>
 * n == board.length == board[i].length
 * 2 <= n <= 20
 * board[i][j] 的值是 -1 或在范围 [1, n^2] 内
 * 编号为 1 和 n^2 的方格上没有蛇或梯子
 */
public class Problem909 {
    public static void main(String[] args) {
        Problem909 problem909 = new Problem909();
//        int[][] board = {
//                {-1, -1, -1, -1, -1, -1},
//                {-1, -1, -1, -1, -1, -1},
//                {-1, -1, -1, -1, -1, -1},
//                {-1, 35, -1, -1, 13, -1},
//                {-1, -1, -1, -1, -1, -1},
//                {-1, 15, -1, -1, -1, -1}
//        };
        int[][] board = {
                {-1, -1, 16, 6, -1},
                {-1, 9, 25, 8, -1},
                {8, 20, 2, 7, -1},
                {-1, -1, 12, -1, -1},
                {-1, -1, -1, 12, 23}
        };
        //第一步：1->4->12
        //第二步：12->18->25
        System.out.println(problem909.snakesAndLadders(board));
    }

    /**
     * bfs
     * 注意：从当前方格跳转到下一个方格，如果下一个方格board[i][j]不等于-1，则必须跳转到指定的方格，
     * 但如果跳转到的方格board[i][j]也大于0，不能再次进行跳转
     * 注意：如果当前方格board[i][j]不等于-1，只能前进1-6步，不能跳转到指定的方格
     * 时间复杂度O(n^2)，空间复杂度O(n^2) (n=board.length)
     *
     * @param board
     * @return
     */
    public int snakesAndLadders(int[][] board) {
        int n = board.length;

        boolean[] visited = new boolean[n * n + 1];
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(1);
        visited[1] = true;

        //从编号1移动到编号n^2需要的次数
        int count = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                //当前方格编号
                int num = queue.poll();

                //到达编号n^2的方格，返回count
                if (num == n * n) {
                    return count;
                }

                //从当前方格编号可以前进1-6步
                for (int j = 1; j <= 6; j++) {
                    //下一次移动的方格编号
                    int nextNum = num + j;

                    //下一次移动的方格编号不能超过n^2
                    if (nextNum > n * n) {
                        continue;
                    }

                    //下一次移动的方格在board中的下标索引
                    int[] nextArr = convert(nextNum, n);

                    //下一个方格board[nextArr[0]][nextArr[1]]不等于-1，则跳转到指定的方格board[nextArr[0]][nextArr[1]]
                    if (board[nextArr[0]][nextArr[1]] != -1) {
                        if (!visited[board[nextArr[0]][nextArr[1]]]) {
                            queue.offer(board[nextArr[0]][nextArr[1]]);
                            visited[board[nextArr[0]][nextArr[1]]] = true;
                        }
                    } else {
                        //下一个方格board[nextArr[0]][nextArr[1]]等于-1，则跳转到nextNum
                        if (!visited[nextNum]) {
                            queue.offer(nextNum);
                            visited[nextNum] = true;
                        }
                    }
                }
            }

            count++;
        }

        //bfs遍历结束，则无法到达编号n^2的方格，返回-1
        return -1;
    }

    /**
     * 方格编号转换为board横纵坐标
     * 时间复杂度O(1)，空间复杂度O(1)
     *
     * @param num
     * @param n
     * @return
     */
    private int[] convert(int num, int n) {
        //假设num从左上角开始的行下标索引
        //因为num从1开始，所以要减1
        int i = (num - 1) / n;
        //假设num从左上角开始的列下标索引
        int j = (num - 1) % n;

        //num从右下角开始的行下标索引
        int x = n - 1 - i;
        //num从右下角开始的列下标索引
        int y;

        //i为偶数，则当前行编号从左向右
        if (i % 2 == 0) {
            y = j;
        } else {
            //i为奇数，则当前行编号从右向左
            y = n - 1 - j;
        }

        return new int[]{x, y};
    }
}
