package com.zhang.java;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @Date 2023/2/11 08:18
 * @Author zsy
 * @Description 图像渲染 dfs和bfs类比Problem79、Problem130、Problem200、Problem212、Problem463、Problem490、Problem499、Problem505、Problem547、Problem694、Problem695、Problem711、Problem827、Problem994、Problem1034、Problem1162、Problem1254、Problem1568、Problem1905、Offer12
 * 有一幅以 m x n 的二维整数数组表示的图画 image ，其中 image[i][j] 表示该图画的像素值大小。
 * 你也被给予三个整数 sr , sc 和 newColor 。你应该从像素 image[sr][sc] 开始对图像进行 上色填充 。
 * 为了完成 上色工作 ，从初始像素开始，记录初始坐标的 上下左右四个方向上 像素值与初始坐标相同的相连像素点，
 * 接着再记录这四个方向上符合条件的像素点与他们对应 四个方向上 像素值与初始坐标相同的相连像素点，……，重复该过程。
 * 将所有有记录的像素点的颜色值改为 newColor 。
 * 最后返回 经过上色渲染后的图像 。
 * <p>
 * 输入: image = [[1,1,1],[1,1,0],[1,0,1]]，sr = 1, sc = 1, newColor = 2
 * 输出: [[2,2,2],[2,2,0],[2,0,1]]
 * 解析: 在图像的正中间，(坐标(sr,sc)=(1,1)),在路径上所有符合条件的像素点的颜色都被更改成2。
 * 注意，右下角的像素没有更改为2，因为它不是在上下左右四个方向上与初始点相连的像素点。
 * <p>
 * 输入: image = [[0,0,0],[0,0,0]], sr = 0, sc = 0, newColor = 2
 * 输出: [[2,2,2],[2,2,2]]
 * <p>
 * m == image.length
 * n == image[i].length
 * 1 <= m, n <= 50
 * 0 <= image[i][j], newColor < 2^16
 * 0 <= sr < m
 * 0 <= sc < n
 */
public class Problem733 {
    public static void main(String[] args) {
        Problem733 problem733 = new Problem733();
        int[][] image = {
                {1, 1, 1},
                {1, 1, 0},
                {1, 0, 1}
        };
        int sr = 1;
        int sc = 1;
        int newColor = 2;
//        System.out.println(Arrays.deepToString(problem733.floodFill(image, sr, sc, newColor)));
        System.out.println(Arrays.deepToString(problem733.floodFill2(image, sr, sc, newColor)));
    }

    /**
     * dfs
     * 从节点(sr,sc)dfs遍历，把和节点(sr,sc)值相同的所有相邻元素值修改为newColor
     * 时间复杂度O(mn)，空间复杂度O(mn)
     *
     * @param image
     * @param sr
     * @param sc
     * @param newColor
     * @return
     */
    public int[][] floodFill(int[][] image, int sr, int sc, int newColor) {
        //要修改的颜色和当前颜色相同，则不需要进行修改，直接返回
        if (image[sr][sc] == newColor) {
            return image;
        }

        dfs(sr, sc, image[sr][sc], newColor, image, new boolean[image.length][image[0].length]);

        return image;
    }

    /**
     * bfs
     * 从节点(sr,sc)bfs遍历，把和节点(sr,sc)值相同的所有相邻元素值修改为newColor
     * 时间复杂度O(mn)，空间复杂度O(mn)
     *
     * @param image
     * @param sr
     * @param sc
     * @param newColor
     * @return
     */
    public int[][] floodFill2(int[][] image, int sr, int sc, int newColor) {
        //要修改的颜色和当前颜色相同，则不需要进行修改，直接返回
        if (image[sr][sc] == newColor) {
            return image;
        }

        int originColor = image[sr][sc];
        boolean[][] visited = new boolean[image.length][image[0].length];
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{sr, sc});

        while (!queue.isEmpty()) {
            int[] arr = queue.poll();

            //当前节点(arr[0],arr[1])不在矩阵范围之内，或当前节点(arr[0],arr[1])已被访问，
            //或者当前节点(arr[0],arr[1])不为originColor，直接进行下次循环
            if (arr[0] < 0 || arr[0] >= image.length || arr[1] < 0 || arr[1] >= image[0].length ||
                    visited[arr[0]][arr[1]] || image[arr[0]][arr[1]] != originColor) {
                continue;
            }

            //修改当前节点的值
            image[arr[0]][arr[1]] = newColor;
            //当前节点已被访问
            visited[arr[0]][arr[1]] = true;

            //当前节点的上下左右加入队列
            queue.offer(new int[]{arr[0] - 1, arr[1]});
            queue.offer(new int[]{arr[0] + 1, arr[1]});
            queue.offer(new int[]{arr[0], arr[1] - 1});
            queue.offer(new int[]{arr[0], arr[1] + 1});
        }

        return image;
    }

    private void dfs(int i, int j, int originColor, int newColor, int[][] image, boolean[][] visited) {
        //当前节点(i,j)不在矩阵范围之内，或者当前节点(i,j)已被访问，或者当前节点(i,j)不为originColor，直接返回
        if (i < 0 || i >= image.length || j < 0 || j >= image[0].length || visited[i][j] || image[i][j] != originColor) {
            return;
        }

        //修改当前节点的值
        image[i][j] = newColor;
        //当前节点已被访问
        visited[i][j] = true;

        //往上下左右找
        dfs(i - 1, j, originColor, newColor, image, visited);
        dfs(i + 1, j, originColor, newColor, image, visited);
        dfs(i, j - 1, originColor, newColor, image, visited);
        dfs(i, j + 1, originColor, newColor, image, visited);
    }
}
