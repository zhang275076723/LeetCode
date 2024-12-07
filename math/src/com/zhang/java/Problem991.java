package com.zhang.java;

/**
 * @Date 2025/2/4 08:34
 * @Author zsy
 * @Description 坏了的计算器 小米面试题 类比Problem397、Problem453、Problem1342、Problem1404、Problem2139、Problem2571
 * 在显示着数字 startValue 的坏计算器上，我们可以执行以下两种操作：
 * 双倍（Double）：将显示屏上的数字乘 2；
 * 递减（Decrement）：将显示屏上的数字减 1 。
 * 给定两个整数 startValue 和 target 。
 * 返回显示数字 target 所需的最小操作数。
 * <p>
 * 输入：startValue = 2, target = 3
 * 输出：2
 * 解释：先进行双倍运算，然后再进行递减运算 {2 -> 4 -> 3}.
 * <p>
 * 输入：startValue = 5, target = 8
 * 输出：2
 * 解释：先递减，再双倍 {5 -> 4 -> 8}.
 * <p>
 * 输入：startValue = 3, target = 10
 * 输出：3
 * 解释：先双倍，然后递减，再双倍 {3 -> 6 -> 5 -> 10}.
 * <p>
 * 1 <= startValue, target <= 10^9
 */
public class Problem991 {
    public static void main(String[] args) {
        Problem991 problem991 = new Problem991();
        int startValue = 3;
        int target = 10;
        System.out.println(problem991.brokenCalc(startValue, target));
    }

    /**
     * 贪心
     * 逆向思维：startValue变为target只能乘2，或者减1，则target变为startValue可以除2，或者加1
     * 当target大于startValue，如果target为偶数，优先除2，target为奇数，优先加1；
     * 当target小于等于startValue，target只能加1变为startValue
     * 时间复杂度O(log(target))，空间复杂度O(1)
     *
     * @param startValue
     * @param target
     * @return
     */
    public int brokenCalc(int startValue, int target) {
        int count = 0;

        //当target大于startValue，如果target为偶数，优先除2，target为奇数，优先加1
        while (target > startValue) {
            if (target % 2 == 0) {
                target = target / 2;
            } else {
                target++;
            }

            count++;
        }

        //当target小于等于startValue，target只能加1变为startValue
        return count + startValue - target;
    }
}
