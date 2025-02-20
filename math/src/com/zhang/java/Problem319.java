package com.zhang.java;

/**
 * @Date 2025/4/10 08:15
 * @Author zsy
 * @Description 灯泡开关 类比Problem672
 * 初始时有 n 个灯泡处于关闭状态。
 * 第一轮，你将会打开所有灯泡。
 * 接下来的第二轮，你将会每两个灯泡关闭第二个。
 * 第三轮，你每三个灯泡就切换第三个灯泡的开关（即，打开变关闭，关闭变打开）。
 * 第 i 轮，你每 i 个灯泡就切换第 i 个灯泡的开关。直到第 n 轮，你只需要切换最后一个灯泡的开关。
 * 找出并返回 n 轮后有多少个亮着的灯泡。
 * <p>
 * 输入：n = 3
 * 输出：1
 * 解释：
 * 初始时, 灯泡状态 [关闭, 关闭, 关闭].
 * 第一轮后, 灯泡状态 [开启, 开启, 开启].
 * 第二轮后, 灯泡状态 [开启, 关闭, 开启].
 * 第三轮后, 灯泡状态 [开启, 关闭, 关闭].
 * 你应该返回 1，因为只有一个灯泡还亮着。
 * <p>
 * 输入：n = 0
 * 输出：0
 * <p>
 * 输入：n = 1
 * 输出：1
 * <p>
 * 0 <= n <= 10^9
 */
public class Problem319 {
    public static void main(String[] args) {
        Problem319 problem319 = new Problem319();
        int n = 3;
        System.out.println(problem319.bulbSwitch(n));
    }

    /**
     * 数学
     * 第k个灯泡，如果第x轮，x为k的因数，则第k个灯泡被切换，则第k个灯泡被切换的次数为k的因数的个数，
     * 如果k有偶数个因数，则第k个灯泡最终为暗；如果k有奇数个因数，则第k个灯泡最终为亮
     * 时间复杂度O(1)，空间复杂度O(1)
     *
     * @param n
     * @return
     */
    public int bulbSwitch(int n) {
        //只有完全平方数才有奇数个因子，最终才为亮，则统计1-n中完全平方数的个数
        return (int) Math.sqrt(n);
    }
}
