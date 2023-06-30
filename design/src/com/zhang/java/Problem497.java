package com.zhang.java;

import java.util.Arrays;
import java.util.Random;

/**
 * @Date 2023/6/30 09:38
 * @Author zsy
 * @Description 非重叠矩形中的随机点 蓄水池抽样类比Problem382、Problem398、Problem528 前缀和+二分查找类比Problem528
 * 给定一个由非重叠的轴对齐矩形的数组 rects ，其中 rects[i] = [ai, bi, xi, yi] 表示 (ai, bi) 是第 i 个矩形的左下角点，
 * (xi, yi) 是第 i 个矩形的右上角点。
 * 设计一个算法来随机挑选一个被某一矩形覆盖的整数点。
 * 矩形周长上的点也算做是被矩形覆盖。
 * 所有满足要求的点必须等概率被返回。
 * 在给定的矩形覆盖的空间内的任何整数点都有可能被返回。
 * 请注意 ，整数点是具有整数坐标的点。
 * 实现 Solution 类:
 * Solution(int[][] rects) 用给定的矩形数组 rects 初始化对象。
 * int[] pick() 返回一个随机的整数点 [u, v] 在给定的矩形所覆盖的空间内。
 * <p>
 * 输入:
 * ["Solution", "pick", "pick", "pick", "pick", "pick"]
 * [[[[-2, -2, 1, 1], [2, 2, 4, 6]]], [], [], [], [], []]
 * 输出:
 * [null, [1, -2], [1, -1], [-1, -2], [-2, -2], [0, 0]]
 * <p>
 * 解释：
 * Solution solution = new Solution([[-2, -2, 1, 1], [2, 2, 4, 6]]);
 * solution.pick(); // 返回 [1, -2]
 * solution.pick(); // 返回 [1, -1]
 * solution.pick(); // 返回 [-1, -2]
 * solution.pick(); // 返回 [-2, -2]
 * solution.pick(); // 返回 [0, 0]
 * <p>
 * 1 <= rects.length <= 100
 * rects[i].length == 4
 * -10^9 <= ai < xi <= 10^9
 * -10^9 <= bi < yi <= 10^9
 * xi - ai <= 2000
 * yi - bi <= 2000
 * 所有的矩形不重叠。
 * pick 最多被调用 10^4 次。
 */
public class Problem497 {
    public static void main(String[] args) {
//        int[][] rects = {{-2, -2, 1, 1}, {2, 2, 4, 6}};
        int[][] rects = {{82918473, -57180867, 82918476, -57180863}, {83793579, 18088559, 83793580, 18088560},
                {66574245, 26243152, 66574246, 26243153}, {72983930, 11921716, 72983934, 11921720}};
//        Solution solution = new Solution(rects);
        Solution2 solution = new Solution2(rects);
        // 返回 [1, -2]
        System.out.println(Arrays.toString(solution.pick()));
        // 返回 [1, -1]
        System.out.println(Arrays.toString(solution.pick()));
        // 返回 [-1, -2]
        System.out.println(Arrays.toString(solution.pick()));
        // 返回 [-2, -2]
        System.out.println(Arrays.toString(solution.pick()));
        // 返回 [0, 0]
        System.out.println(Arrays.toString(solution.pick()));
    }


    /**
     * 蓄水池抽样，从n个元素中随机等概率的抽取k个元素，n未知
     * 时间换空间，适用于nums数组很大，无法将nums元素下标索引全部保存到内存中的情况
     */
    static class Solution {
        private final int[][] rects;
        private final Random random;

        public Solution(int[][] rects) {
            this.rects = rects;
            random = new Random();
        }

        /**
         * 遍历到当前第k个矩形的面积之和为sum(k)，第k个矩形面积为area(k)，总共有n个矩形
         * 选择当前矩形的概率为area(k)/sum(n) = (area(k)/sum(k))*(sum(k)/sum(k+1))*(sum(k+1)/sum(k+2))*...*(sum(n-1)/sum(n))
         * (area(k)/sum(k)：选择第k个矩形，sum(k)/sum(k+1)：不选第k+1个矩形，...，sum(n-1)/sum(n):不选第n个矩形，
         * 即选择第k个矩形的概率为area(k)/total
         * 时间复杂度O(n)，空间复杂度O(1)
         *
         * @return
         */
        public int[] pick() {
            //随机选到的矩形下标索引
            int index = 0;
            //当前矩形面积之和
            int sum = 0;

            for (int i = 0; i < rects.length; i++) {
                //当前矩形面积
                int area = (rects[i][2] - rects[i][0] + 1) * (rects[i][3] - rects[i][1] + 1);
                sum = sum + area;

                //[0,sum-1]的随机值小于当前矩形面积，则选择当前矩形的下标索引
                if (random.nextInt(sum) < area) {
                    index = i;
                }
            }

            //(x1,y1)左下角节点，(x2,y2)右上角节点
            int x1 = rects[index][0];
            int y1 = rects[index][1];
            int x2 = rects[index][2];
            int y2 = rects[index][3];

            //返回当前矩阵中的随机节点
            return new int[]{random.nextInt(x2 - x1 + 1) + x1, random.nextInt(y2 - y1 + 1) + y1};
        }
    }

    /**
     * 前缀和+二分查找
     */
    static class Solution2 {
        private final int[][] rects;
        //前缀和权值数组，preSum[i]：rects[0]-rects[i-1]面积之和
        private final int[] preSum;
        //权值面积总和，即preSum[rects.length]
        private final int n;
        private final Random random;


        public Solution2(int[][] rects) {
            this.rects = rects;
            preSum = new int[rects.length + 1];
            random = new Random();

            for (int i = 1; i <= rects.length; i++) {
                preSum[i] = preSum[i - 1] +
                        (rects[i - 1][2] - rects[i - 1][0] + 1) * (rects[i - 1][3] - rects[i - 1][1] + 1);
            }

            n = preSum[rects.length];
        }

        /**
         * 二分查找确定1-n的随机数在preSum中的下标索引
         * 时间复杂度O(logn)，空间复杂度O(1)
         *
         * @return
         */
        public int[] pick() {
            //生成1-n的随机数
            int randomArea = random.nextInt(n) + 1;
            //preSum[0]为0，所以得到下标索引需要减1
            int index = binarySearch(preSum, randomArea) - 1;

            //(x1,y1)左下角节点，(x2,y2)右上角节点
            int x1 = rects[index][0];
            int y1 = rects[index][1];
            int x2 = rects[index][2];
            int y2 = rects[index][3];

            //返回当前矩阵中的随机节点
            return new int[]{random.nextInt(x2 - x1 + 1) + x1, random.nextInt(y2 - y1 + 1) + y1};
        }

        private int binarySearch(int[] preSum, int target) {
            int left = 0;
            int right = preSum.length - 1;
            int mid;

            while (left <= right) {
                mid = left + ((right - left) >> 1);

                if (preSum[mid] == target) {
                    return mid;
                } else if (preSum[mid] < target) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }

            return left;
        }
    }
}
