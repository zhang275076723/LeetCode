package com.zhang.java;

/**
 * @Date 2024/9/21 09:01
 * @Author zsy
 * @Description 转化时间需要的最少操作数 类比Problem2241 类比Problem401、Problem539、Problem635
 * 给你两个字符串 current 和 correct ，表示两个 24 小时制时间 。
 * 24 小时制时间 按 "HH:MM" 进行格式化，其中 HH 在 00 和 23 之间，而 MM 在 00 和 59 之间。
 * 最早的 24 小时制时间为 00:00 ，最晚的是 23:59 。
 * 在一步操作中，你可以将 current 这个时间增加 1、5、15 或 60 分钟。
 * 你可以执行这一操作 任意 次数。
 * 返回将 current 转化为 correct 需要的 最少操作数 。
 * <p>
 * 输入：current = "02:30", correct = "04:35"
 * 输出：3
 * 解释：
 * 可以按下述 3 步操作将 current 转换为 correct ：
 * - 为 current 加 60 分钟，current 变为 "03:30" 。
 * - 为 current 加 60 分钟，current 变为 "04:30" 。
 * - 为 current 加 5 分钟，current 变为 "04:35" 。
 * 可以证明，无法用少于 3 步操作将 current 转化为 correct 。
 * <p>
 * 输入：current = "11:00", correct = "11:01"
 * 输出：1
 * 解释：只需要为 current 加一分钟，所以最小操作数是 1 。
 * <p>
 * current 和 correct 都符合 "HH:MM" 格式
 * current <= correct
 */
public class Problem2224 {
    public static void main(String[] args) {
        Problem2224 problem2224 = new Problem2224();
        String current = "02:30";
        String correct = "04:35";
        System.out.println(problem2224.convertTime(current, correct));
    }

    /**
     * 模拟
     * 统一转换为分钟，优先选择较大的时间来构成时间
     * 时间复杂度O(1)，空间复杂度O(1)
     *
     * @param current
     * @param correct
     * @return
     */
    public int convertTime(String current, String correct) {
        int time1 = Integer.parseInt(current.substring(0, 2)) * 60 + Integer.parseInt(current.substring(3, 5));
        int time2 = Integer.parseInt(correct.substring(0, 2)) * 60 + Integer.parseInt(correct.substring(3, 5));
        //time1始终小于time2，所以不需要加绝对值
        int time = time2 - time1;
        //time变为0的最小操作次数
        int count = 0;

        int[] arr = {1, 5, 15, 60};

        //优先选择较大的时间
        for (int i = arr.length - 1; i >= 0; i--) {
            count = count + time / arr[i];
            time = time - time / arr[i] * arr[i];
        }

        return count;
    }
}
