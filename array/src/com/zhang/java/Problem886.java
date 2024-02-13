package com.zhang.java;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @Date 2023/10/31 08:30
 * @Author zsy
 * @Description 可能的二分法 类比Problem785 并查集类比Problem130、Problem200、Problem261、Problem305、Problem323、Problem399、Problem547、Problem684、Problem685、Problem695、Problem765、Problem785、Problem827、Problem952、Problem1135、Problem1254、Problem1319、Problem1361、Problem1489、Problem1568、Problem1584、Problem1627、Problem1905、Problem1998、Problem2685
 * 给定一组 n 人（编号为 1, 2, ..., n）， 我们想把每个人分进任意大小的两组。
 * 每个人都可能不喜欢其他人，那么他们不应该属于同一组。
 * 给定整数 n 和数组 dislikes ，其中 dislikes[i] = [ai, bi] ，表示不允许将编号为 ai 和  bi的人归入同一组。
 * 当可以用这种方法将所有人分进两组时，返回 true；否则返回 false。
 * <p>
 * 输入：n = 4, dislikes = [[1,2],[1,3],[2,4]]
 * 输出：true
 * 解释：group1 [1,4], group2 [2,3]
 * <p>
 * 输入：n = 3, dislikes = [[1,2],[1,3],[2,3]]
 * 输出：false
 * <p>
 * 输入：n = 5, dislikes = [[1,2],[2,3],[3,4],[4,5],[1,5]]
 * 输出：false
 * <p>
 * 1 <= n <= 2000
 * 0 <= dislikes.length <= 10^4
 * dislikes[i].length == 2
 * 1 <= dislikes[i][j] <= n
 * ai < bi
 * dislikes 中每一组都 不同
 */
public class Problem886 {
    /**
     * dfs中图是否能二分
     */
    private boolean flag = true;

    public static void main(String[] args) {
        Problem886 problem886 = new Problem886();
//        int n = 4;
//        int[][] dislike = {{1, 2}, {1, 3}, {2, 4}};
        int n = 5;
        int[][] dislike = {{1, 2}, {2, 3}, {3, 4}, {4, 5}, {1, 5}};
        System.out.println(problem886.possibleBipartition(n, dislike));
        System.out.println(problem886.possibleBipartition2(n, dislike));
        System.out.println(problem886.possibleBipartition3(n, dislike));
    }

    /**
     * dfs
     * 根据dislikes建图，图中节点的邻接节点为当前节点不喜欢的节点，
     * 当前节点所有的邻接节点都在一个集合中，并且当前节点和所有的邻接节点都不在一个集合中，才能二分
     * 标记当前节点的访问方式和前一个节点的访问方式不同，
     * 如果当前节点已被访问，并且和前一个节点的访问方式相同，则一条边上两个节点的访问方式相同，当前边的两个节点在一个集合中，无法二分
     * 时间复杂度O(m+n)，空间复杂度O(m+n) (m：图中边的个数，n：图中节点的个数，如果使用邻接矩阵，空间复杂度O(n^2))
     *
     * @param n
     * @param dislikes
     * @return
     */
    public boolean possibleBipartition(int n, int[][] dislikes) {
        //邻接表，图中节点的邻接节点为当前节点不喜欢的节点
        List<List<Integer>> edges = new ArrayList<>();

        //注意节点是从1开始，所以需要多加1
        for (int i = 0; i <= n; i++) {
            edges.add(new ArrayList<>());
        }

        for (int i = 0; i < dislikes.length; i++) {
            int u = dislikes[i][0];
            int v = dislikes[i][1];

            edges.get(u).add(v);
            edges.get(v).add(u);
        }

        //节点访问数组，节点是从1开始，所以需要多加1
        //visited[i]为0：节点i未访问；visited[i]为1：节点i的访问方式为1；visited[i]为2：节点i的访问方式为2
        //一条边上两个顶点的访问方式不同，则当前边可以二分
        int[] visited = new int[n + 1];

        //一条边上两个节点的访问方式相同，则当前边的两个节点属于同一个集合，无法二分
        //注意节点是从1开始
        for (int i = 1; i <= n; i++) {
            //未访问的节点进行dfs
            if (visited[i] == 0) {
                //lastVisited：节点i的上一个节点的访问方式
                //这里lastVisited不仅可以为0，也可以为1或2
                dfs(i, 0, visited, edges);
            }

            //dfs过程中不能二分，返回false
            if (!flag) {
                return false;
            }
        }

        //dfs遍历结束，则说明所有边都可以二分，返回true
        return true;
    }

    /**
     * bfs
     * 根据dislikes建图，图中节点的邻接节点为当前节点不喜欢的节点，
     * 当前节点所有的邻接节点都在一个集合中，并且当前节点和所有的邻接节点都不在一个集合中，才能二分
     * 标记当前节点的访问方式和前一个节点的访问方式不同，
     * 如果当前节点已被访问，并且和前一个节点的访问方式相同，则一条边上两个节点的访问方式相同，当前边的两个节点在一个集合中，无法二分
     * 时间复杂度O(m+n)，空间复杂度O(m+n) (m：图中边的个数，n：图中节点的个数，如果使用邻接矩阵，空间复杂度O(n^2))
     *
     * @param n
     * @param dislikes
     * @return
     */
    public boolean possibleBipartition2(int n, int[][] dislikes) {
        //邻接表，图中节点的邻接节点为当前节点不喜欢的节点
        List<List<Integer>> edges = new ArrayList<>();

        //注意节点是从1开始，所以需要多加1
        for (int i = 0; i <= n; i++) {
            edges.add(new ArrayList<>());
        }

        for (int i = 0; i < dislikes.length; i++) {
            int u = dislikes[i][0];
            int v = dislikes[i][1];

            edges.get(u).add(v);
            edges.get(v).add(u);
        }

        //节点访问数组，节点是从1开始，所以需要多加1
        //visited[i]为0：节点i未访问；visited[i]为1：节点i的访问方式为1；visited[i]为2：节点i的访问方式为2
        //一条边上两个顶点的访问方式不同，则当前边可以二分
        int[] visited = new int[n + 1];

        //一条边上两个节点的访问方式相同，则当前边的两个节点属于同一个集合，无法二分
        //注意节点是从1开始
        for (int i = 1; i <= n; i++) {
            //未访问的节点进行bfs
            if (visited[i] == 0) {
                //bfs过程中不能二分，返回false
                if (!bfs(i, visited, edges)) {
                    return false;
                }
            }
        }

        //bfs遍历结束，则说明所有边都可以二分，返回true
        return true;
    }

    /**
     * 并查集
     * 根据dislikes建图，图中节点的邻接节点为当前节点不喜欢的节点，
     * 当前节点所有的邻接节点都在同一个连通分量中，并且当前节点和所有的邻接节点都不在同一个连通分量中，才能二分
     * 时间复杂度O(m+n)，空间复杂度O(m+n) (m：图中边的个数，n：图中节点的个数，如果使用邻接矩阵，空间复杂度O(n^2))
     *
     * @param n
     * @param dislikes
     * @return
     */
    public boolean possibleBipartition3(int n, int[][] dislikes) {
        //邻接表，图中节点的邻接节点为当前节点不喜欢的节点
        List<List<Integer>> edges = new ArrayList<>();

        //注意节点是从1开始，所以需要多加1
        for (int i = 0; i <= n; i++) {
            edges.add(new ArrayList<>());
        }

        for (int i = 0; i < dislikes.length; i++) {
            int u = dislikes[i][0];
            int v = dislikes[i][1];

            edges.get(u).add(v);
            edges.get(v).add(u);
        }

        //节点是从1开始，所以需要多加1
        UnionFind unionFind = new UnionFind(n + 1);

        //当前节点u
        for (int u = 0; u < n; u++) {
            //节点u的邻接节点v
            for (int v : edges.get(u)) {
                //当前节点u和当前节点的邻接节点v连通，则无法二分，返回false
                if (unionFind.isConnected(u, v)) {
                    return false;
                } else {
                    //当前节点u和当前节点的邻接节点v不连通，则节点v和节点u的邻接节点edges.get(u).get(0)相连
                    unionFind.union(edges.get(u).get(0), v);
                }
            }
        }

        //遍历结束，则说明所有边都可以二分，返回true
        return true;
    }

    private void dfs(int i, int lastVisited, int[] visited, List<List<Integer>> edges) {
        if (!flag) {
            return;
        }

        //当前节点已访问
        if (visited[i] != 0) {
            //当前节点的访问方式和上一个节点的访问方式相同，则当前边的两个节点属于同一个集合，
            //无法二分，flag设置为false
            if (visited[i] == lastVisited) {
                flag = false;
            }
        } else {
            //当前节点未访问，设置当前节点的访问方式不同于上一个节点的访问方式
            if (lastVisited == 1) {
                visited[i] = 2;
            } else {
                visited[i] = 1;
            }

            //dfs遍历当前节点i的邻接节点u
            for (int u : edges.get(i)) {
                dfs(u, visited[i], visited, edges);
            }
        }
    }

    private boolean bfs(int i, int[] visited, List<List<Integer>> edges) {
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(i);
        //设置当前节点i的访问方式为1，也可以设置为2
        visited[i] = 1;

        while (!queue.isEmpty()) {
            //当前节点u
            int u = queue.poll();

            //当前节点u的邻接节点v
            for (int v : edges.get(u)) {
                //邻接节点v已访问，当前节点u的访问方式和邻接节点v的访问方式相同，则当前边的两个节点属于同一个集合，
                //无法二分，返回false
                if (visited[v] != 0) {
                    if (visited[u] == visited[v]) {
                        return false;
                    }
                } else {
                    //邻接节点v未访问，设置邻接节点v的访问方式不同于当前节点u的访问方式
                    if (visited[u] == 1) {
                        visited[v] = 2;
                    } else {
                        visited[v] = 1;
                    }

                    queue.offer(v);
                }
            }
        }

        //bfs遍历结束，则说明所有边都可以二分，返回true
        return true;
    }

    /**
     * 并查集(不相交数据集)类
     * 用数组的形式表示图
     */
    private static class UnionFind {
        private int count;
        private final int[] parent;
        private final int[] weight;

        public UnionFind(int n) {
            count = n;
            parent = new int[n];
            weight = new int[n];

            for (int i = 0; i < n; i++) {
                parent[i] = i;
                weight[i] = 1;
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
