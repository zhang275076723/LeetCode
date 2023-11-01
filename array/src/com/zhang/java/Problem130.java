package com.zhang.java;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @Date 2022/11/29 15:53
 * @Author zsy
 * @Description 被围绕的区域 标志位类比Problem73、Problem289 dfs和bfs类比Problem79、Problem200、Problem212、Problem463、Problem547、Problem694、Problem695、Problem711、Problem733、Problem827、Problem994、Problem1034、Problem1162、Problem1254、Problem1568、Problem1905、Offer12 并查集类比Problem200、Problem261、Problem305、Problem323、Problem399、Problem547、Problem684、Problem685、Problem695、Problem765、Problem785、Problem827、Problem886、Problem952、Problem1135、Problem1254、Problem1319、Problem1489、Problem1568、Problem1584、Problem1627、Problem1905、Problem1998、Problem2685
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
     * 注意：不使用访问数组，原数组置为'#'表示已访问
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
     * 注意：不使用访问数组，原数组置为'#'表示已访问
     * 时间复杂度O(mn)，空间复杂度O(mn)
     *
     * @param board
     */
    public void solve2(char[][] board) {
        //边界'O'bfs，将相连的'O'置为'#'
        for (int i = 0; i < board.length; i++) {
            if (board[i][0] == 'O') {
                bfs(i, 0, board);
            }

            if (board[i][board[0].length - 1] == 'O') {
                bfs(i, board[0].length - 1, board);
            }
        }

        //边界'O'bfs，将相连的'O'置为'#'
        for (int j = 1; j < board[0].length - 1; j++) {
            if (board[0][j] == 'O') {
                bfs(0, j, board);
            }

            if (board[board.length - 1][j] == 'O') {
                bfs(board.length - 1, j, board);
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
     * 并查集
     * 边界'O'和dummyIndex相连，遍历除边界外为'O'的节点，和相邻为'O'节点相连，遍历除边界外为'O'且不和dummyIndex相连的节点，
     * 将当前为'O'的节点置为'X'
     * 时间复杂度O(mn*α(mn))=O(mn)，空间复杂度O(mn) (find()和union()的时间复杂度为O(α(mn))，可视为常数O(1))
     *
     * @param board
     */
    public void solve3(char[][] board) {
        UnionFind unionFind = new UnionFind(board);

        //边界'O'和dummyIndex相连，dummyIndex作为根节点
        for (int i = 0; i < board.length; i++) {
            if (board[i][0] == 'O') {
                unionFind.unionDummy(i * board[0].length);
            }

            if (board[i][board[0].length - 1] == 'O') {
                unionFind.unionDummy(i * board[0].length + board[0].length - 1);
            }
        }

        //边界'O'和dummyIndex相连，dummyIndex作为根节点
        for (int j = 1; j < board[0].length - 1; j++) {
            if (board[0][j] == 'O') {
                unionFind.unionDummy(j);
            }

            if (board[board.length - 1][j] == 'O') {
                unionFind.unionDummy((board.length - 1) * board[0].length + j);
            }
        }

        //当前节点的上下左右四个位置
        int[][] direction = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        //遍历除边界外为'O'的节点，和相邻为'O'节点相连
        for (int i = 1; i < board.length - 1; i++) {
            for (int j = 1; j < board[0].length - 1; j++) {
                //当前节点为'O'时，和当前位置的上下左右'O'进行合并，成为一个连通分量
                if (board[i][j] == 'O') {
                    for (int k = 0; k < direction.length; k++) {
                        int x = i + direction[k][0];
                        int y = j + direction[k][1];

                        //(i,j)和(x,y)合并为一个连通分量
                        if (board[x][y] == 'O') {
                            unionFind.union(i * board[0].length + j, x * board[0].length + y);
                        }
                    }
                }
            }
        }

        //遍历除边界外为'O'且不和dummyIndex相连的节点，将当前为'O'的节点置为'X'
        for (int i = 1; i < board.length - 1; i++) {
            for (int j = 1; j < board[0].length - 1; j++) {
                if (board[i][j] == 'O' && !unionFind.isConnected(unionFind.dummyIndex, i * board[0].length + j)) {
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

    private void bfs(int i, int j, char[][] board) {
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{i, j});

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
    }

    /**
     * 并查集(不相交数据集)类
     * 用数组的形式表示图
     */
    private static class UnionFind {
        //并查集中连通分量的个数
        private int count;
        //虚拟节点，board中所有边界为'O'的节点的连通分量根节点都指向虚拟节点
        private final int dummyIndex;
        //节点的父节点索引下标数组，二维数组按照由左到右由上到下的顺序，从0开始存储
        private final int[] parent;
        //节点的权值数组(节点的高度)，只有一个节点的权值为1
        private final int[] weight;

        public UnionFind(char[][] board) {
            count = 0;
            //多申请一个长度，末尾节点作为为虚拟节点
            parent = new int[board.length * board[0].length + 1];
            weight = new int[board.length * board[0].length + 1];
            dummyIndex = board.length * board[0].length;
            parent[dummyIndex] = dummyIndex;
            weight[dummyIndex] = 1;

            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    //当前节点为'O'时，才加入并查集中
                    if (board[i][j] == 'O') {
                        parent[board[0].length * i + j] = board[0].length * i + j;
                        weight[board[0].length * i + j] = 1;
                        count++;
                    }
                }
            }
        }

        public void union(int i, int j) {
            int rootI = find(i);
            int rootJ = find(j);

            if (rootI != rootJ) {
                if (weight[rootI] < weight[rootJ]) {
                    parent[rootI] = rootJ;
                } else if (weight[rootI] > weight[rootJ]) {
                    parent[rootJ] = rootI;
                } else {
                    parent[rootJ] = rootI;
                    weight[rootI]++;
                }

                //两个连通分量合并，并查集中连通分量的个数减1
                count--;
            }
        }

        public void unionDummy(int i) {
            int rootI = find(i);
            parent[rootI] = dummyIndex;
            weight[dummyIndex] = Math.max(weight[dummyIndex], weight[rootI]);
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
