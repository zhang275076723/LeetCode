package com.zhang.java;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @Date 2023/7/1 08:59
 * @Author zsy
 * @Description 随机翻转矩阵 类比Problem384、Problem710、Random1_100
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
     * 核心思想：删除nums[index]，则当前元素nums[index]和末尾元素nums[n-1]交换，相当于删除末尾元素nums[n-1]，
     * 保证下次在下标索引[0,n-2]连续的范围内选择要删除的元素，但实际上并不交换，而是通过map标记交换
     * 注意：不能使用数组，只能使用哈希表，因为m*n过大，使用数组会空间溢出
     * <p>
     * 例如：[a,b,c,d,e,f]6个元素中随机选择
     * 1、[0,5]中随机选择下标索引index=3，total=5，map中不存在3、5，即随机选择的元素为nums[3]=d,nums[3]和nums[5]交换，[a,b,c,(d),e,f]，map={[3:5]}
     * 2、[0,4]中随机选择下标索引index=3，total=4，map中3对应下标索引5，map中不存在4，即随机选择的元素为nums[5]=f，nums[5]和nums[4]交换，[a,b,c,(d),e,(f)]，map={[3:4]}
     * 3、[0,3]中随机选择下标索引index=1，total=3，map中不存在1，map中3对应下标索引4，即随机选择的元素为nums[1]=b，nums[1]和nums[4]交换，[a,(b),c,(d),e,(f)]，map={[3:4],[1:4]}
     * 4、[0,2]中随机选择下标索引index=0，total=2，map中不存在0、2，即随机选择的元素为nums[0]=a，nums[0]和nums[2]交换，[(a),(b),c,(d),e,(f)]，map={[3:4],[1:4],[0:2]}
     * 5、[0,1]中随机选择下标索引index=0，total=1，map中0对应下标索引2，map中1对应下标索引4，即随机选择的元素为nums[2]=c，nums[2]和nums[4]交换，[(a),(b),(c),(d),e,(f)]，map={[3:4],[1:4],[0:4]}
     * 6、[0,0]中随机选择下标索引index=0，total=0，map中0对应下标索引4，即随机选择的元素为nums[4]=e，nums[0]和nums[0]交换，[(a),(b),(c),(d),(e),(f)]，map={[3:4],[1:4],[0:4]}
     */
    static class Solution {
        //key：当前范围[0,total-1]已翻转元素的二维坐标转换为的一维下标索引，value：key在范围[total,m*n-1]映射的未翻转元素的二维坐标转换为的一维下标索引
        //例如：key=3，value=5，表示一维下标索引3已翻转，对应未翻转元素的一维下标索引为5
        private final Map<Integer, Integer> map;
        //矩阵行
        private final int m;
        //矩阵列
        private final int n;
        //当前剩余未翻转元素的数量，即矩阵中剩余为0的数量，保证每次从[0,total-1]连续的范围内随机取值
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
         * 随机选择一个元素，当前元素指向末尾元素的下标索引，即删除末尾元素，保证下次随机选择的元素连续
         * 时间复杂度O(1)，空间复杂度O(1)
         *
         * @return
         */
        public int[] flip() {
            //[0,total-1]的随机值
            int randomIndex = random.nextInt(total);
            //当前元素未翻转，则直接得到randomIndex；当前元素已翻转，则通过map在范围[total,m*n-1]中得到未翻转的下标索引
            int index = map.getOrDefault(randomIndex, randomIndex);
            //未翻转的元素数量减1
            total--;

            //当前元素和末尾元素交换，保证范围[0,total-1]连续性，实际上并不交换，而是通过map标记交换
            map.put(randomIndex, map.getOrDefault(total, total));

            //返回一维坐标对应的二维坐标
            return new int[]{index / n, index % n};
        }

        /**
         * 时间复杂度O(C)，空间复杂度O(1) (C：reset之前执行flip的次数，clear()中对每个元素赋值为null，所以时间复杂度O(C)，而不是O(1))
         */
        public void reset() {
            map.clear();
            total = m * n;
        }
    }
}
