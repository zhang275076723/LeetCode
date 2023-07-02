package com.zhang.java;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @Date 2023/7/1 08:59
 * @Author zsy
 * @Description 随机翻转矩阵 类比Problem384、Random1_100
 * 给你一个 m x n 的二元矩阵 matrix ，且所有值被初始化为 0 。
 * 请你设计一个算法，随机选取一个满足 matrix[i][j] == 0 的下标 (i, j) ，并将它的值变为 1 。
 * 所有满足 matrix[i][j] == 0 的下标 (i, j) 被选取的概率应当均等。
 * 尽量最少调用内置的随机函数，并且优化时间和空间复杂度。
 * 实现 Solution 类：
 * Solution(int m, int n) 使用二元矩阵的大小 m 和 n 初始化该对象
 * int[] flip() 返回一个满足 matrix[i][j] == 0 的随机下标 [i, j] ，并将其对应格子中的值变为 1
 * void reset() 将矩阵中所有的值重置为 0
 * <p>
 * 输入
 * ["Solution", "flip", "flip", "flip", "reset", "flip"]
 * [[3, 1], [], [], [], [], []]
 * 输出
 * [null, [1, 0], [2, 0], [0, 0], null, [2, 0]]
 * 解释
 * Solution solution = new Solution(3, 1);
 * solution.flip();  // 返回 [1, 0]，此时返回 [0,0]、[1,0] 和 [2,0] 的概率应当相同
 * solution.flip();  // 返回 [2, 0]，因为 [1,0] 已经返回过了，此时返回 [2,0] 和 [0,0] 的概率应当相同
 * solution.flip();  // 返回 [0, 0]，根据前面已经返回过的下标，此时只能返回 [0,0]
 * solution.reset(); // 所有值都重置为 0 ，并可以再次选择下标返回
 * solution.flip();  // 返回 [2, 0]，此时返回 [0,0]、[1,0] 和 [2,0] 的概率应当相同
 * <p>
 * 1 <= m, n <= 10^4
 * 每次调用flip 时，矩阵中至少存在一个值为 0 的格子。
 * 最多调用 1000 次 flip 和 reset 方法。
 */
public class Problem519 {
    public static void main(String[] args) {
        Solution solution = new Solution(3, 1);
        // 返回 [1, 0]，此时返回 [0,0]、[1,0] 和 [2,0] 的概率应当相同
        System.out.println(Arrays.toString(solution.flip()));
        // 返回 [2, 0]，因为 [1,0] 已经返回过了，此时返回 [2,0] 和 [0,0] 的概率应当相同
        System.out.println(Arrays.toString(solution.flip()));
        // 返回 [0, 0]，根据前面已经返回过的下标，此时只能返回 [0,0]
        System.out.println(Arrays.toString(solution.flip()));
        // 所有值都重置为 0 ，并可以再次选择下标返回
        solution.reset();
        // 返回 [2, 0]，此时返回 [0,0]、[1,0] 和 [2,0] 的概率应当相同
        System.out.println(Arrays.toString(solution.flip()));
    }

    /**
     * 哈希表
     * 每次交换当前元素和末尾元素的下标索引，保证下次随机选择的元素连续
     * 注意：不能使用下标数组，m*n空间太大，会溢出
     */
    static class Solution {
        //存放元素和元素二维坐标转换为一维坐标下标索引的映射关系
        //如果map中不存在某个元素，则表示当前元素index存放为翻转的元素一维下标索引为index
        //例如：key=1，value=3，表示下标索引1存放未翻转的元素一维下标索引为3
        private final Map<Integer, Integer> map;
        //矩阵行
        private final int m;
        //矩阵列
        private final int n;
        //当前剩余未翻转元素的数量，即为0的数量
        private int total;
        //获取随机值
        private final Random random;

        public Solution(int m, int n) {
            map = new HashMap<>();
            this.m = m;
            this.n = n;
            total = m * n;
            random = new Random();
        }

        /**
         * 随机选择一个元素，交换当前元素和末尾元素的下标索引，保证下次随机选择的元素连续
         * 时间复杂度O(1)，空间复杂度O(1)
         *
         * @return
         */
        public int[] flip() {
            //[0-total-1]的随机值
            int num = random.nextInt(total);
            //当前元素对应的下标索引
            int index = map.getOrDefault(num, num);
            //未翻转的元素数量减1
            total--;

            //交换当前元素和末尾元素的下标索引
            map.put(num, map.getOrDefault(total, total));

            //一维坐标转化为二维坐标
            return new int[]{index / n, index % n};
        }

        /**
         * 时间复杂度O(C)，空间复杂度O(1) (C：reset之前执行flip的次数)
         */
        public void reset() {
            map.clear();
            total = m * n;
        }
    }
}
