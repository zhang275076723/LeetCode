package com.zhang.java;

import java.util.*;

/**
 * @Date 2023/9/20 08:22
 * @Author zsy
 * @Description 不同岛屿的数量 II dfs和bfs类比Problem79、Problem130、Problem200、Problem212、Problem463、Problem547、Problem694、Problem695、Problem733、Problem827、Problem994、Problem1034、Problem1162、Problem1254、Problem1568、Problem1905、Offer12
 * 给定一个 m x n 二进制数组表示的网格 grid ，一个岛屿由 四连通 （上、下、左、右四个方向）的 1 组成（代表陆地）。
 * 你可以认为网格的四周被海水包围。
 * 如果两个岛屿的形状相同，或者通过旋转（顺时针旋转 90°，180°，270°）、翻转（左右翻转、上下翻转）后形状相同，
 * 那么就认为这两个岛屿是相同的。
 * 返回 这个网格中形状 不同 的岛屿的数量 。
 * <p>
 * 输入： grid = [
 * [1,1,0,0,0],
 * [1,0,0,0,0],
 * [0,0,0,0,1],
 * [0,0,0,1,1]
 * ]
 * 输出：1
 * 解释：将左上角的岛屿顺时针旋转 180 度之后将得到和右下角完全相同的岛屿。
 * <p>
 * 输入： grid = [
 * [1,1,1,0,0],
 * [1,0,0,0,1],
 * [0,1,0,0,1],
 * [0,1,1,1,0]
 * ]
 * 输出：2
 * 解释：将左上角的岛屿镜像翻转 180 度之后将得到和右下角完全相同的岛屿。
 * <p>
 * m == grid.length
 * n == grid[i].length
 * 1 <= m, n <= 50
 * grid[i][j] 不是 0 就是 1
 */
public class Problem711 {
    public static void main(String[] args) {
        Problem711 problem711 = new Problem711();
        int[][] grid = {
                {1, 1, 1, 0, 0},
                {1, 0, 0, 0, 1},
                {0, 1, 0, 0, 1},
                {0, 1, 1, 1, 0}
        };
        System.out.println(problem711.numDistinctIslands(grid));
        System.out.println(problem711.numDistinctIslands2(grid));
    }

    /**
     * dfs
     * 核心思想：对每个岛屿需要得到当前岛屿的唯一识别
     * dfs遍历每个岛屿，得到当前岛屿中每个节点，经过旋转或翻转，得到8种不同的情况，
     * 计算每种情况每个节点减去当前情况的最小横纵坐标节点，得到相对节点，由小到大排序8种情况的相对节点，
     * 排序后的第1种情况作为当前岛屿的标记，如果当前岛屿的标记在之前保存的标记中出现过，则存在相同岛屿
     * 原坐标：(x,y)
     * 上下翻转：(x,-y)
     * 左右翻转：(-x,y)
     * 顺时针旋转90°：(y,-x)
     * 顺时针旋转180°：(-x,-y)
     * 顺时针旋转270°：(-y,x)
     * 顺时针旋转90°+上下翻转：(y,x)
     * 顺时针旋转90°+左右翻转：(-y,-x)
     * 时间复杂度O(mnlog(mn))，空间复杂度O(mn) (对mn个节点排序的时间复杂度O(mnlog(mn)))
     *
     * @param grid
     * @return
     */
    public int numDistinctIslands(int[][] grid) {
        //保存不同岛屿标记的set集合，当前岛屿中每个节点经过旋转或翻转，减去每种情况的最小横纵坐标节点，
        //得到相对节点，由小到大排序后的第1种情况作为当前岛屿的标记
        Set<String> set = new HashSet<>();
        boolean[][] visited = new boolean[grid.length][grid[0].length];

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 1 && !visited[i][j]) {
                    //当前岛屿中节点的list集合
                    List<int[]> list = new ArrayList<>();

                    dfs(i, j, grid, visited, list);

                    //当前岛屿的唯一标识
                    String key = getKey(list);
                    set.add(key);
                }
            }
        }

        return set.size();
    }

    /**
     * bfs
     * 核心思想：对每个岛屿需要得到当前岛屿的唯一识别
     * bfs遍历每个岛屿，得到当前岛屿中每个节点，经过旋转或翻转，得到8种不同的情况，
     * 计算每种情况每个节点减去当前情况的最小横纵坐标节点，得到相对节点，由小到大排序8种情况的相对节点，
     * 排序后的第1种情况作为当前岛屿的标记，如果当前岛屿的标记在之前保存的标记中出现过，则存在相同岛屿
     * 原坐标：(x,y)
     * 上下翻转：(x,-y)
     * 左右翻转：(-x,y)
     * 顺时针旋转90°：(y,-x)
     * 顺时针旋转180°：(-x,-y)
     * 顺时针旋转270°：(-y,x)
     * 顺时针旋转90°+上下翻转：(y,x)
     * 顺时针旋转90°+左右翻转：(-y,-x)
     * 时间复杂度O(mnlog(mn))，空间复杂度O(mn) (对mn个节点排序的时间复杂度O(mnlog(mn)))
     *
     * @param grid
     * @return
     */
    public int numDistinctIslands2(int[][] grid) {
        //保存不同岛屿标记的set集合，当前岛屿中每个节点经过旋转或翻转，减去每种情况的最小横纵坐标节点，
        //得到相对节点，由小到大排序后的第1种情况作为当前岛屿的标记
        Set<String> set = new HashSet<>();
        boolean[][] visited = new boolean[grid.length][grid[0].length];

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 1 && !visited[i][j]) {
                    //当前岛屿中节点的list集合
                    List<int[]> list = new ArrayList<>();

                    bfs(i, j, grid, visited, list);

                    //当前岛屿的唯一标识
                    String key = getKey(list);
                    set.add(key);
                }
            }
        }

        return set.size();
    }

    private void dfs(int i, int j, int[][] grid, boolean[][] visited, List<int[]> list) {
        if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length || grid[i][j] != 1 || visited[i][j]) {
            return;
        }

        visited[i][j] = true;
        list.add(new int[]{i, j});

        dfs(i - 1, j, grid, visited, list);
        dfs(i + 1, j, grid, visited, list);
        dfs(i, j - 1, grid, visited, list);
        dfs(i, j + 1, grid, visited, list);
    }

    private void bfs(int i, int j, int[][] grid, boolean[][] visited, List<int[]> list) {
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{i, j});

        while (!queue.isEmpty()) {
            int[] arr = queue.poll();

            if (arr[0] < 0 || arr[0] >= grid.length || arr[1] < 0 || arr[1] >= grid[0].length ||
                    grid[arr[0]][arr[1]] != 1 || visited[arr[0]][arr[1]]) {
                continue;
            }

            visited[arr[0]][arr[1]] = true;
            list.add(arr);

            queue.offer(new int[]{arr[0] - 1, arr[1]});
            queue.offer(new int[]{arr[0] + 1, arr[1]});
            queue.offer(new int[]{arr[0], arr[1] - 1});
            queue.offer(new int[]{arr[0], arr[1] + 1});
        }
    }

    /**
     * 根据当前岛屿中每个节点，经过旋转或翻转，减去每种情况的最小横纵坐标节点，得到相对节点，
     * 由小到大排序后的第1种情况作为当前岛屿的标记
     * 时间复杂度O(mnlog(mn))，空间复杂度O(mn)
     *
     * @param list
     * @return
     */
    private String getKey(List<int[]> list) {
        //当前岛屿中每个节点经过旋转或翻转，减去每种情况的最小横纵坐标节点，得到相对节点的集合数组，只有8种不同情况的节点
        List<int[]>[] listArr = new List[8];

        for (int i = 0; i < 8; i++) {
            listArr[i] = new ArrayList<>();
        }

        //当前岛屿中每个节点经过旋转或翻转之后的节点
        for (int[] arr : list) {
            //不旋转和翻转：(x,y)
            listArr[0].add(new int[]{arr[0], arr[1]});
            //上下翻转：(x,-y)
            listArr[1].add(new int[]{arr[0], -arr[1]});
            //左右翻转：(-x,y)
            listArr[2].add(new int[]{-arr[0], arr[1]});
            //顺时针旋转90°：(y,-x)
            listArr[3].add(new int[]{arr[1], -arr[0]});
            //顺时针旋转180°：(-x,-y)
            listArr[4].add(new int[]{-arr[0], -arr[1]});
            //顺时针旋转270°：(-y,x)
            listArr[5].add(new int[]{-arr[1], arr[0]});
            //顺时针旋转90°+上下翻转：(y,x)
            listArr[6].add(new int[]{arr[1], arr[0]});
            //顺时针旋转90°+左右翻转：(-y,-x)
            listArr[7].add(new int[]{-arr[1], -arr[0]});
        }

        //每种情况按照节点横坐标和纵坐标由小到大排序，减去当前情况最小横纵坐标，得到相对节点
        for (List<int[]> curList : listArr) {
            curList.sort(new Comparator<int[]>() {
                @Override
                public int compare(int[] arr1, int[] arr2) {
                    if (arr1[0] != arr2[0]) {
                        return arr1[0] - arr2[0];
                    } else {
                        return arr1[1] - arr2[1];
                    }
                }
            });

            //当前情况的最小横纵坐标
            int minX = curList.get(0)[0];
            int minY = curList.get(0)[1];

            //减去最小横纵坐标，得到相对节点
            for (int[] arr : curList) {
                arr[0] = arr[0] - minX;
                arr[1] = arr[1] - minY;
            }
        }

        //由小到大排序相对节点的8种情况
        Arrays.sort(listArr, new Comparator<List<int[]>>() {
            @Override
            public int compare(List<int[]> list1, List<int[]> list2) {
                for (int i = 0; i < list1.size(); i++) {
                    if (list1.get(i)[0] != list2.get(i)[0]) {
                        return list1.get(i)[0] - list2.get(i)[0];
                    } else if (list1.get(i)[1] != list2.get(i)[1]) {
                        return list1.get(i)[1] - list2.get(i)[1];
                    }
                }

                return 0;
            }
        });

        StringBuilder sb = new StringBuilder();

        //由小到大排序后第1种情况作为当前岛屿的标记
        for (int[] arr : listArr[0]) {
            sb.append('(').append(arr[0]).append(',').append(arr[1]).append(')');
        }

        return sb.toString();
    }
}
