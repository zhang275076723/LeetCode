package com.zhang.java;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Date 2024/1/27 08:40
 * @Author zsy
 * @Description 验证二叉树 入度出度类比Problem207、Problem210、Problem331、Problem685 并查集类比Problem130、Problem200、Problem261、Problem305、Problem323、Problem399、Problem547、Problem684、Problem685、Problem695、Problem765、Problem785、Problem827、Problem886、Problem952、Problem1135、Problem1254、Problem1319、Problem1489、Problem1568、Problem1584、Problem1627、Problem1905、Problem1998、Problem2685
 * 二叉树上有 n 个节点，按从 0 到 n - 1 编号，其中节点 i 的两个子节点分别是 leftChild[i] 和 rightChild[i]。
 * 只有 所有 节点能够形成且 只 形成 一颗 有效的二叉树时，返回 true；否则返回 false。
 * 如果节点 i 没有左子节点，那么 leftChild[i] 就等于 -1。右子节点也符合该规则。
 * 注意：节点没有值，本问题中仅仅使用节点编号。
 * <p>
 * 输入：n = 4, leftChild = [1,-1,3,-1], rightChild = [2,-1,-1,-1]
 * 输出：true
 * <p>
 * 输入：n = 4, leftChild = [1,-1,3,-1], rightChild = [2,3,-1,-1]
 * 输出：false
 * <p>
 * 输入：n = 2, leftChild = [1,0], rightChild = [-1,-1]
 * 输出：false
 * <p>
 * 输入：n = 6, leftChild = [1,-1,-1,4,-1,-1], rightChild = [2,-1,-1,5,-1,-1]
 * 输出：false
 * <p>
 * 1 <= n <= 10^4
 * leftChild.length == rightChild.length == n
 * -1 <= leftChild[i], rightChild[i] <= n - 1
 */
public class Problem1361 {
    /**
     * dfs能够访问到的节点个数
     */
    private int count = 0;

    public static void main(String[] args) {
        Problem1361 problem1361 = new Problem1361();
        int n = 6;
        int[] leftChild = {-1, 2, 0, 4, -1, -1};
        int[] rightChild = {1, -1, -1, 5, -1, -1};
//        int n = 5;
//        int[] leftChild = {0, -1, 3, 1, 3};
//        int[] rightChild = {4, 3, 0, 1, -1};
        System.out.println(problem1361.validateBinaryTreeNodes(n, leftChild, rightChild));
        System.out.println(problem1361.validateBinaryTreeNodes2(n, leftChild, rightChild));
        System.out.println(problem1361.validateBinaryTreeNodes3(n, leftChild, rightChild));
    }

    /**
     * dfs
     * 计算每个节点的入度，只有根节点的入度为0，其他节点入度为都为1，
     * 如果入度为0的节点个数大于1，或者不存在入度为0的节点，或者存在入度大于1的节点，则不是有效二叉树；
     * 从入度为0的节点，即根节点开始dfs，如果不能访问到所有节点，则不是有效二叉树
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param n
     * @param leftChild
     * @param rightChild
     * @return
     */
    public boolean validateBinaryTreeNodes(int n, int[] leftChild, int[] rightChild) {
        int[] inDegree = new int[n];

        for (int i = 0; i < n; i++) {
            if (leftChild[i] != -1) {
                inDegree[leftChild[i]]++;
                //存在入度大于1的节点，则不是有效二叉树，返回false
                if (inDegree[leftChild[i]] > 1) {
                    return false;
                }
            }

            if (rightChild[i] != -1) {
                inDegree[rightChild[i]]++;
                //存在入度大于1的节点，则不是有效二叉树，返回false
                if (inDegree[rightChild[i]] > 1) {
                    return false;
                }
            }
        }

        //根节点
        int root = -1;

        for (int i = 0; i < n; i++) {
            if (inDegree[i] == 0) {
                if (root == -1) {
                    root = i;
                } else {
                    //入度为0的节点个数大于1，则不是有效二叉树，返回false
                    return false;
                }
            }
        }

        //不存在入度为0的节点，则不是有效二叉树，返回false
        if (root == -1) {
            return false;
        }

        //节点访问数组
        boolean[] visited = new boolean[n];

        dfs(root, leftChild, rightChild, visited);

        //从入度为0的节点，即根节点开始dfs，能访问到所有节点，则是有效二叉树
        return count == n;
    }

    /**
     * bfs
     * 计算每个节点的入度，只有根节点的入度为0，其他节点入度为都为1，
     * 如果入度为0的节点个数大于1，或者不存在入度为0的节点，或者存在入度大于1的节点，则不是有效二叉树；
     * 从入度为0的节点，即根节点开始bfs，如果不能访问到所有节点，则不是有效二叉树
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param n
     * @param leftChild
     * @param rightChild
     * @return
     */
    public boolean validateBinaryTreeNodes2(int n, int[] leftChild, int[] rightChild) {
        int[] inDegree = new int[n];

        for (int i = 0; i < n; i++) {
            if (leftChild[i] != -1) {
                inDegree[leftChild[i]]++;
                //存在入度大于1的节点，则不是有效二叉树，返回false
                if (inDegree[leftChild[i]] > 1) {
                    return false;
                }
            }

            if (rightChild[i] != -1) {
                inDegree[rightChild[i]]++;
                //存在入度大于1的节点，则不是有效二叉树，返回false
                if (inDegree[rightChild[i]] > 1) {
                    return false;
                }
            }
        }

        //根节点
        int root = -1;

        for (int i = 0; i < n; i++) {
            if (inDegree[i] == 0) {
                if (root == -1) {
                    root = i;
                } else {
                    //入度为0的节点个数大于1，则不是有效二叉树，返回false
                    return false;
                }
            }
        }

        //不存在入度为0的节点，则不是有效二叉树，返回false
        if (root == -1) {
            return false;
        }

        //bfs能够访问到的节点个数
        int count = 0;
        //节点访问数组
        boolean[] visited = new boolean[n];
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int u = queue.poll();

            if (u == -1) {
                continue;
            }

            //当前节点u已经访问，存在环，则不是有效二叉树，返回false
            if (visited[u]) {
                return false;
            }

            count++;
            visited[u] = true;

            queue.offer(leftChild[u]);
            queue.offer(rightChild[u]);
        }

        //从入度为0的节点，即根节点开始bfs，能访问到所有节点，则是有效二叉树
        return count == n;
    }

    /**
     * 并查集
     * 遍历过程中，父节点和子节点已经连通，或者当前子节点有多个父节点，则不是有效二叉树；
     * 遍历结束，连通分量的个数超过1个，则不是有效二叉树
     * 注意：父节点和子节点合并的先后顺序，即谁指向谁
     * 时间复杂度O(n*α(mn))，空间复杂度O(n) (find()和union()的时间复杂度为O(α(n))，可视为常数O(1))
     *
     * @param n
     * @param leftChild
     * @param rightChild
     * @return
     */
    public boolean validateBinaryTreeNodes3(int n, int[] leftChild, int[] rightChild) {
        UnionFind unionFind = new UnionFind(n);

        for (int i = 0; i < n; i++) {
            if (leftChild[i] != -1) {
                //父节点i和子节点leftChild[i]已经连通，或者当前子节点leftChild[i]有多个父节点，则不是有效二叉树，返回false
                if (unionFind.isConnected(i, leftChild[i]) || unionFind.find(leftChild[i]) != leftChild[i]) {
                    return false;
                } else {
                    //注意：合并之后只能是子节点leftChild[i]的根节点指向父节点i的根节点，不能写反
                    unionFind.union(i, leftChild[i]);
                }
            }

            if (rightChild[i] != -1) {
                //父节点i和子节点rightChild[i]已经连通，或者当前子节点rightChild[i]有多个父节点，则不是有效二叉树，返回false
                if (unionFind.isConnected(i, rightChild[i]) || unionFind.find(rightChild[i]) != rightChild[i]) {
                    return false;
                } else {
                    //注意：合并之后只能是子节点rightChild[i]的根节点指向父节点i的根节点，不能写反
                    unionFind.union(i, rightChild[i]);
                }
            }
        }

        //遍历结束，并查集中连通分量的个数超过1个，则不是有效二叉树
        return unionFind.count == 1;
    }

    private void dfs(int u, int[] leftChild, int[] rightChild, boolean[] visited) {
        if (u == -1 || visited[u]) {
            return;
        }

        visited[u] = true;
        count++;

        dfs(leftChild[u], leftChild, rightChild, visited);
        dfs(rightChild[u], leftChild, rightChild, visited);
    }

    /**
     * 并查集
     */
    private static class UnionFind {
        private int count;
        private final int[] parent;

        public UnionFind(int n) {
            count = n;
            parent = new int[n];

            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
        }

        /**
         * i为父节点，j为子节点，合并之后只能是子节点j的根节点指向父节点i的根节点
         * 注意：当前并查集不能按秩合并，因为要保留父子节点的指向关系，避免两个父节点指向同一个子节点的不合法情况
         *
         * @param i
         * @param j
         */
        public void union(int i, int j) {
            int rootI = find(i);
            int rootJ = find(j);

            if (rootI != rootJ) {
                //子节点j的根节点指向父节点i的根节点
                parent[rootJ] = rootI;
                count--;
            }
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
