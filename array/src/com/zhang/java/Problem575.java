package com.zhang.java;

import java.util.HashSet;
import java.util.Set;

/**
 * @Date 2022/5/13 9:23
 * @Author zsy
 * @Description 分糖果 类比Problem135
 * Alice 有 n 枚糖，其中第 i 枚糖的类型为 candyType[i] 。
 * Alice 注意到她的体重正在增长，所以前去拜访了一位医生。
 * 医生建议 Alice 要少摄入糖分，只吃掉她所有糖的 n / 2 即可（n 是一个偶数）。
 * Alice 非常喜欢这些糖，她想要在遵循医生建议的情况下，尽可能吃到最多不同种类的糖。
 * 给你一个长度为 n 的整数数组 candyType ，返回： Alice 在仅吃掉 n / 2 枚糖的情况下，可以吃到糖的 最多 种类数。
 * <p>
 * 输入：candyType = [1,1,2,2,3,3]
 * 输出：3
 * 解释：Alice 只能吃 6 / 2 = 3 枚糖，由于只有 3 种糖，她可以每种吃一枚。
 * <p>
 * 输入：candyType = [1,1,2,3]
 * 输出：2
 * 解释：Alice 只能吃 4 / 2 = 2 枚糖，不管她选择吃的种类是 [1,2]、[1,3] 还是 [2,3]，她只能吃到两种不同
 * <p>
 * 输入：candyType = [6,6,6,6]
 * 输出：1
 * 解释：Alice 只能吃 4 / 2 = 2 枚糖，尽管她能吃 2 枚，但只能吃到 1 种糖。
 * <p>
 * n == candyType.length
 * 2 <= n <= 10^4
 * n 是一个偶数
 * -10^5 <= candyType[i] <= 10^5
 */
public class Problem575 {
    public static void main(String[] args) {
        Problem575 problem575 = new Problem575();
        int[] candyType = {1, 1, 2, 2, 3, 3};
        System.out.println(problem575.distributeCandies(candyType));
    }

    /**
     * 哈希表
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param candyType
     * @return
     */
    public int distributeCandies(int[] candyType) {
        if (candyType == null || candyType.length == 0) {
            return 0;
        }

        Set<Integer> set = new HashSet<>();

        for (int candy : candyType) {
            set.add(candy);
        }

        return Math.min(set.size(), candyType.length / 2);
    }
}
