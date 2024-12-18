package com.zhang.java;

import java.util.*;

/**
 * @Date 2025/2/9 08:47
 * @Author zsy
 * @Description 黑名单中的随机数 类比Problem384、Problem519、Random1_100
 * 给定一个整数 n 和一个 无重复 黑名单整数数组 blacklist 。
 * 设计一种算法，从 [0, n - 1] 范围内的任意整数中选取一个 未加入 黑名单 blacklist 的整数。
 * 任何在上述范围内且不在黑名单 blacklist 中的整数都应该有 同等的可能性 被返回。
 * 优化你的算法，使它最小化调用语言 内置 随机函数的次数。
 * 实现 Solution 类:
 * Solution(int n, int[] blacklist) 初始化整数 n 和被加入黑名单 blacklist 的整数
 * int pick() 返回一个范围为 [0, n - 1] 且不在黑名单 blacklist 中的随机整数
 * <p>
 * 输入
 * ["Solution", "pick", "pick", "pick", "pick", "pick", "pick", "pick"]
 * [[7, [2, 3, 5]], [], [], [], [], [], [], []]
 * 输出
 * [null, 0, 4, 1, 6, 1, 0, 4]
 * 解释
 * Solution solution = new Solution(7, [2, 3, 5]);
 * solution.pick(); // 返回0，任何[0,1,4,6]的整数都可以。注意，对于每一个pick的调用，
 * <                // 0、1、4和6的返回概率必须相等(即概率为1/4)。
 * solution.pick(); // 返回 4
 * solution.pick(); // 返回 1
 * solution.pick(); // 返回 6
 * solution.pick(); // 返回 1
 * solution.pick(); // 返回 0
 * solution.pick(); // 返回 4
 * <p>
 * 1 <= n <= 10^9
 * 0 <= blacklist.length <= min(10^5, n - 1)
 * 0 <= blacklist[i] < n
 * blacklist 中所有值都 不同
 * pick 最多被调用 2 * 10^4 次
 */
public class Problem710 {
    public static void main(String[] args) {
        int n = 7;
        int[] blacklist = {2, 3, 5};
        Solution solution = new Solution(n, blacklist);
        // 返回0，任何[0,1,4,6]的整数都可以。注意，对于每一个pick的调用，0、1、4和6的返回概率必须相等(即概率为1/4)。
        System.out.println(solution.pick());
        // 返回 4
        System.out.println(solution.pick());
        // 返回 1
        System.out.println(solution.pick());
        // 返回 6
        System.out.println(solution.pick());
        // 返回 1
        System.out.println(solution.pick());
        // 返回 0
        System.out.println(solution.pick());
        // 返回 4
        System.out.println(solution.pick());
    }

    /**
     * 哈希表
     * 核心思想：删除nums[index]，则当前元素nums[index]和末尾元素nums[n-1]交换，相当于删除末尾元素nums[n-1]，
     * 保证下次在下标索引[0,n-2]连续的范围内选择要删除的元素，但实际上并不交换，而是通过map标记交换
     * 注意：不能使用数组，只能使用哈希表，因为m*n过大，使用数组会空间溢出
     * <p>
     * 例如：n=7，blacklist=[2,3,5]，total=7-3=4，即[0,3]存放未删除的元素，[4,6]存放删除的元素
     * 1、blackNum=2<total，curNum=4不是blacklist中元素，则blackNum=2映射到4，map={[2:4]}
     * 2、blackNum=3<total，curNum=5是blacklist中元素，curNum加1为6，则blackNum=3映射到6，map={[2:4],[3:6]}
     * 3、blackNum=5>=total，直接进行下次循环，map={[2:4],[3:6]}
     */
    static class Solution {
        //key：当前范围[0,total-1]已拉黑元素，value：key在范围[total,n-1]映射的未拉黑元素
        private final Map<Integer, Integer> map;
        private final Random random;
        private final int total;

        public Solution(int n, int[] blacklist) {
            map = new HashMap<>();
            random = new Random();
            total = n - blacklist.length;

            //存储黑名单元素，在O(1)判断当前元素是否在黑名单
            Set<Integer> set = new HashSet<>();

            for (int blackNum : blacklist) {
                set.add(blackNum);
            }

            //当前遍历到的元素，指向[total,n-1]中未拉黑的元素
            int curNum = total;

            //从total开始往后遍历，在范围[total,n-1]找未拉黑的元素，和范围[0,total-1]已拉黑的元素blackNum交换
            for (int blackNum : blacklist) {
                //只需要找[0,total-1]中拉黑的元素
                if (blackNum < total) {
                    while (set.contains(curNum)) {
                        curNum++;
                    }

                    map.put(blackNum, curNum);
                    curNum++;
                }
            }
        }

        public int pick() {
            //[0,total-1]的随机值
            int randomNum = random.nextInt(total);

            //当前元素未拉黑，则直接直接返回randomNum；当前元素已拉黑，则通过map在范围[total,n-1]中得到未拉黑的元素
            return map.getOrDefault(randomNum, randomNum);
        }
    }
}
