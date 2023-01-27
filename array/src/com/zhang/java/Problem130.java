package com.zhang.java;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @Date 2022/11/29 15:53
 * @Author zsy
 * @Description 被围绕的区域 标志位类比Problem73、Problem289 并查集类比Problem200、Problem399
 * 给你一个 m x n 的矩阵 board ，由若干字符 'X' 和 'O' ，
 * 找到所有被 'X' 围绕的区域，并将这些区域里所有的 'O' 用 'X' 填充。
 * <p>
 * 输入：board = [
 * ["X","X","X","X"],
 * ["X","O","O","X"],
 * ["X","X","O","X"],
 * ["X","O","X","X"]
 * ]
 * 输出：[
 * ["X","X","X","X"],
 * ["X","X","X","X"],
 * ["X","X","X","X"],
 * ["X","O","X","X"]
 * ]
 * 解释：被围绕的区间不会存在于边界上，换句话说，任何边界上的 'O' 都不会被填充为 'X'。
 * 任何不在边界上，或不与边界上的 'O' 相连的 'O' 最终都会被填充为 'X'。
 * 如果两个元素在水平或垂直方向相邻，则称它们是“相连”的。
 * <p>
 * 输入：board = [["X"]]
 * 输出：[["X"]]
 * <p>
 * m == board.length
 * n == board[i].length
 * 1 <= m, n <= 200
 * board[i][j] 为 'X' 或 'O'
 */
public class Problem130 {
    public static void main(String[] args) {
        Problem130 problem130 = new Problem130();
        char[][] board = {
                {'X', 'X', 'X', 'X'},
                {'X', 'O', 'O', 'X'},
                {'X', 'X', 'O', 'X'},
                {'X', 'O', 'X', 'X'}
        };
//        char[][] board = {
//                {'X', 'O', 'O', 'X', 'X', 'X', 'O', 'X', 'O', 'O'},
//                {'X', 'O', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X'},
//                {'X', 'X', 'X', 'X', 'O', 'X', 'X', 'X', 'X', 'X'},
//                {'X', 'O', 'X', 'X', 'X', 'O', 'X', 'X', 'X', 'O'},
//                {'O', 'X', 'X', 'X', 'O', 'X', 'O', 'X', 'O', 'X'},
//                {'X', 'X', 'O', 'X', 'X', 'O', 'O', 'X', 'X', 'X'},
//                {'O', 'X', 'X', 'O', 'O', 'X', 'O', 'X', 'X', 'O'},
//                {'O', 'X', 'X', 'X', 'X', 'X', 'O', 'X', 'X', 'X'},
//                {'X', 'O', 'O', 'X', 'X', 'O', 'X', 'X', 'O', 'O'},
//                {'X', 'X', 'X', 'O', 'O', 'X', 'O', 'X', 'X', 'O'}
//        };
//        problem130.solve(board);
//        problem130.solve2(board);
        problem130.solve3(board);
        System.out.println(Arrays.deepToString(board));
    }

    /**
     * dfs
     * 对边界'O'dfs，将相连的'O'置为'#'，表示当前的'O'不是被'X'围绕的区域，剩下的'O'都是被'X'围绕的区域，需要置为'X'，
     * 遍历矩阵元素，将未被置为'#'的'O'置为'X'，此时的'O'是被'X'围绕的区域，将被置为'#'的'O'重新置为'O'
     * 时间复杂度O(mn)，空间复杂度O(mn)
     *
     * @param board
     */
    public void solve(char[][] board) {
        //边界'O'dfs，将相连的'O'置为'#'
        for (int i = 0; i < board.length; i++) {
            if (board[i][0] == 'O') {
                dfs(i, 0, board);
            }

            if (board[i][board[0].length - 1] == 'O') {
                dfs(i, board[0].length - 1, board);
            }
        }

        //边界'O'dfs，将相连的'O'置为'#'
        for (int j = 1; j < board[0].length - 1; j++) {
            if (board[0][j] == 'O') {
                dfs(0, j, board);
            }

            if (board[board.length - 1][j] == 'O') {
                dfs(board.length - 1, j, board);
            }
        }

        //遍历矩阵元素，将未被置为'#'的'O'置为'X'，此时的'O'是被'X'围绕的区域，将被置为'#'的'O'重新置为'O'
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == '#') {
                    board[i][j] = 'O';
                } else if (board[i][j] == 'O') {
                    board[i][j] = 'X';
                }
            }
        }
    }

    /**
     * bfs
     * 边界'O'加入队列，队列中出队一个'O'，将相连的'O'置为'#'，表示当前的'O'不是被'X'围绕的区域，剩下的'O'都是需要置为'X'，
     * 遍历矩阵元素，将未被置为'#'的'O'置为'X'，此时的'O'是被'X'围绕的区域，将被置为'#'的'O'重新置为'O'
     * 时间复杂度O(mn)，空间复杂度O(mn)
     *
     * @param board
     */
    public void solve2(char[][] board) {
        Queue<int[]> queue = new LinkedList<>();

        //边界'O'加入队列
        for (int i = 0; i < board.length; i++) {
            if (board[i][0] == 'O') {
                queue.offer(new int[]{i, 0});
            }

            if (board[i][board[0].length - 1] == 'O') {
                queue.offer(new int[]{i, board[0].length - 1});
            }
        }

        //边界'O'加入队列
        for (int j = 1; j < board[0].length - 1; j++) {
            if (board[0][j] == 'O') {
                queue.offer(new int[]{0, j});
            }

            if (board[board.length - 1][j] == 'O') {
                queue.offer(new int[]{board.length - 1, j});
            }
        }

        //队列中'O'进行bfs，将相连的'O'置为'#'，表示当前的'O'不是被'X'围绕的区域
        while (!queue.isEmpty()) {
            int[] arr = queue.poll();

            if (arr[0] < 0 || arr[0] >= board.length || arr[1] < 0 || arr[1] >= board[0].length ||
                    board[arr[0]][arr[1]] != 'O') {
                continue;
            }

            board[arr[0]][arr[1]] = '#';

            queue.offer(new int[]{arr[0] - 1, arr[1]});
            queue.offer(new int[]{arr[0] + 1, arr[1]});
            queue.offer(new int[]{arr[0], arr[1] - 1});
            queue.offer(new int[]{arr[0], arr[1] + 1});
        }

        //遍历矩阵元素，将未被置为'#'的'O'置为'X'，此时的'O'是被'X'围绕的区域，将被置为'#'的'O'重新置为'O'
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == '#') {
                    board[i][j] = 'O';
                } else if (board[i][j] == 'O') {
                    board[i][j] = 'X';
                }
            }
        }
    }

    /**
     * 并查集
     * 时间复杂度O(mn*α(mn))=O(mn)，空间复杂度O(mn) (find()和union()的时间复杂度为O(α(mn))，可视为常数O(1))
     *
     * @param board
     */
    public void solve3(char[][] board) {
        UnionFind unionFind = new UnionFind(board);

        //边界'O'和dummyIndex合并为一个集合，dummyIndex作为根节点
        for (int i = 0; i < board.length; i++) {
            if (board[i][0] == 'O') {
                unionFind.unionDummy(board[0].length * i);
            }

            if (board[i][board[0].length - 1] == 'O') {
                unionFind.unionDummy(board[0].length * i + board[0].length - 1);
            }
        }

        //边界'O'和dummyIndex合并为一个集合，dummyIndex作为根节点
        for (int j = 1; j < board[0].length - 1; j++) {
            if (board[0][j] == 'O') {
                unionFind.unionDummy(j);
            }

            if (board[board.length - 1][j] == 'O') {
                unionFind.unionDummy(board[0].length * (board.length - 1) + j);
            }
        }

        //遍历除矩阵最外圈的'O'，和相连的上下左右'O'进行合并，成为一个集合
        for (int i = 1; i < board.length - 1; i++) {
            for (int j = 1; j < board[0].length - 1; j++) {
                if (board[i][j] == 'O') {
                    if (board[i - 1][j] == 'O') {
                        unionFind.union(board[0].length * i + j, board[0].length * (i - 1) + j);
                    }

                    if (board[i + 1][j] == 'O') {
                        unionFind.union(board[0].length * i + j, board[0].length * (i + 1) + j);
                    }

                    if (board[i][j - 1] == 'O') {
                        unionFind.union(board[0].length * i + j, board[0].length * i + j - 1);
                    }

                    if (board[i][j + 1] == 'O') {
                        unionFind.union(board[0].length * i + j, board[0].length * i + j + 1);
                    }
                }
            }
        }

        //将除矩阵外圈所有与dummyIndex不连通的'O'均置为'X'，即被'X'围绕的区域
        for (int i = 1; i < board.length - 1; i++) {
            for (int j = 1; j < board[0].length - 1; j++) {
                if (board[i][j] == 'O' && !unionFind.isConnected(unionFind.dummyIndex, board[0].length * i + j)) {
                    board[i][j] = 'X';
                }
            }
        }
    }

    private void dfs(int i, int j, char[][] board) {
        if (i < 0 || i >= board.length || j < 0 || j >= board[0].length || board[i][j] != 'O') {
            return;
        }

        board[i][j] = '#';

        //往上下左右找
        dfs(i - 1, j, board);
        dfs(i + 1, j, board);
        dfs(i, j - 1, board);
        dfs(i, j + 1, board);
    }

    /**
     * 并查集(不相交数据集)类
     * 用数组的形式表示图
     */
    private static class UnionFind {
        //并查集个数
        private int count;
        //虚拟节点，board中所有边界'O'相连的集合根节点都指向虚拟节点
        private final int dummyIndex;
        private final int[] parent;
        private final int[] rank;

        public UnionFind(char[][] board) {
            //数组长度多申请1个，末尾节点作为为虚拟节点
            parent = new int[board.length * board[0].length + 1];
            rank = new int[board.length * board[0].length + 1];
            dummyIndex = board.length * board[0].length;
            parent[dummyIndex] = dummyIndex;

            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    //当前节点为'O'时，才加入并查集中
                    if (board[i][j] == 'O') {
                        parent[board[0].length * i + j] = board[0].length * i + j;
                        count++;
                    }
                }
            }
        }

        public void union(int i, int j) {
            int rootI = find(i);
            int rootJ = find(j);

            if (rootI != rootJ) {
                if (rank[rootI] < rank[rootJ]) {
                    parent[rootI] = rootJ;
                } else if (rank[rootI] > rank[rootJ]) {
                    parent[rootJ] = rootI;
                } else {
                    parent[rootJ] = rootI;
                    rank[rootI]++;
                }

                count--;
            }
        }

        public void unionDummy(int i) {
            int rootI = find(i);
            parent[rootI] = dummyIndex;
            rank[dummyIndex] = Math.max(rank[dummyIndex], rank[rootI]);
        }

        public int find(int i) {
            if (parent[i] != i) {
                parent[i] = find(parent[i]);
            }

            return parent[i];
        }

        public boolean isConnected(int i, int j) {
            int rootI = find(i);
            int rootJ = find(j);

            return rootI == rootJ;
        }
    }
}
