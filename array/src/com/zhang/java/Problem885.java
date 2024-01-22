package com.zhang.java;

import java.util.Arrays;

/**
 * @Date 2024/1/22 08:27
 * @Author zsy
 * @Description 螺旋矩阵 III 改变方向类比Problem874 类比Problem48、Problem54、Problem59、Problem498、Problem1424、Problem2326、Offer29
 * 在 rows x cols 的网格上，你从单元格 (rStart, cStart) 面朝东面开始。
 * 网格的西北角位于第一行第一列，网格的东南角位于最后一行最后一列。
 * 你需要以顺时针按螺旋状行走，访问此网格中的每个位置。
 * 每当移动到网格的边界之外时，需要继续在网格之外行走（但稍后可能会返回到网格边界）。
 * 最终，我们到过网格的所有 rows x cols 个空间。
 * 按照访问顺序返回表示网格位置的坐标列表。
 * <p>
 * 输入：rows = 1, cols = 4, rStart = 0, cStart = 0
 * 输出：[[0,0],[0,1],[0,2],[0,3]]
 * <p>
 * 输入：rows = 5, cols = 6, rStart = 1, cStart = 4
 * 输出：[[1,4],[1,5],[2,5],[2,4],[2,3],[1,3],[0,3],[0,4],[0,5],[3,5],
 * [3,4],[3,3],[3,2],[2,2],[1,2],[0,2],[4,5],[4,4],[4,3],[4,2],[4,1],
 * [3,1],[2,1],[1,1],[0,1],[4,0],[3,0],[2,0],[1,0],[0,0]]
 * <p>
 * 1 <= rows, cols <= 100
 * 0 <= rStart < rows
 * 0 <= cStart < cols
 */
public class Problem885 {
    public static void main(String[] args) {
        Problem885 problem885 = new Problem885();
        int rows = 1;
        int cols = 4;
        int rsStart = 0;
        int cStart = 0;
//        int rows = 5;
//        int cols = 6;
//        int rsStart = 1;
//        int cStart = 4;
        System.out.println(Arrays.deepToString(problem885.spiralMatrixIII(rows, cols, rsStart, cStart)));
    }

    /**
     * 模拟
     * 使用四个指针，分别限定矩阵的上下左右边界，每次遍历完一行或一列之后，指针移动
     * 时间复杂度O(mn)，空间复杂度O(1)
     *
     * @param rows
     * @param cols
     * @param rStart
     * @param cStart
     * @return
     */
    public int[][] spiralMatrixIII(int rows, int cols, int rStart, int cStart) {
        int[][] result = new int[rows * cols][2];
        int index = 0;

        //上下左右四个指针，限定矩阵的上下左右边界
        int left = cStart - 1;
        int right = cStart + 1;
        int top = rStart - 1;
        int bottom = rStart + 1;

        //(0,0)为矩阵左上角，所以向东需要加上(0,1)
        //注意：只能这样安排移动方向，因为direction和d有对应关系，d=0表示向东移动，d=1表示向南移动，d=2表示向西移动，d=3表示向北移动
        int[][] direction = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        //初始化移动方向为向东移动，(x,y)下一个移动位置为(x+direction[d][0],y+direction[d][1])
        int d = 0;
        //当前位置(x,y)
        int x = rStart;
        int y = cStart;

        while (index < rows * cols) {
            //当前位置在矩阵中，加入result
            if (x >= 0 && x < rows && y >= 0 && y < cols) {
                result[index] = new int[]{x, y};
                index++;
            }

            //向东遍历到右边界right，改变方向向南遍历，right++
            if (d == 0 && y == right) {
                d = 1;
                right++;
            } else if (d == 1 && x == bottom) {
                //向南遍历到下边界bottom，改变方向向西遍历，bottom++
                d = 2;
                bottom++;
            } else if (d == 2 && y == left) {
                //向西遍历到左边界left，改变方向向北遍历，left--
                d = 3;
                left--;
            } else if (d == 3 && x == top) {
                //向北遍历到上边界top，改变方向向东遍历，top--
                d = 0;
                top--;
            }

            x = x + direction[d][0];
            y = y + direction[d][1];
        }

        return result;
    }
}
