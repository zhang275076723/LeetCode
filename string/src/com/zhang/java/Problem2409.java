package com.zhang.java;

/**
 * @Date 2025/11/3 17:45
 * @Author zsy
 * @Description 统计共同度过的日子数 类比Problem539、Problem635、Problem1154、Problem1360、Problem2224
 * Alice 和 Bob 计划分别去罗马开会。
 * 给你四个字符串 arriveAlice ，leaveAlice ，arriveBob 和 leaveBob 。
 * Alice 会在日期 arriveAlice 到 leaveAlice 之间在城市里（日期为闭区间），而 Bob 在日期 arriveBob 到 leaveBob 之间在城市里（日期为闭区间）。
 * 每个字符串都包含 5 个字符，格式为 "MM-DD" ，对应着一个日期的月和日。
 * 请你返回 Alice和 Bob 同时在罗马的天数。
 * 你可以假设所有日期都在 同一个 自然年，而且 不是 闰年。每个月份的天数分别为：[31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31] 。
 * <p>
 * 输入：arriveAlice = "08-15", leaveAlice = "08-18", arriveBob = "08-16", leaveBob = "08-19"
 * 输出：3
 * 解释：Alice 从 8 月 15 号到 8 月 18 号在罗马。
 * Bob 从 8 月 16 号到 8 月 19 号在罗马，他们同时在罗马的日期为 8 月 16、17 和 18 号。所以答案为 3 。
 * <p>
 * 输入：arriveAlice = "10-01", leaveAlice = "10-31", arriveBob = "11-01", leaveBob = "12-31"
 * 输出：0
 * 解释：Alice 和 Bob 没有同时在罗马的日子，所以我们返回 0 。
 * <p>
 * 所有日期的格式均为 "MM-DD" 。
 * Alice 和 Bob 的到达日期都 早于或等于 他们的离开日期。
 * 题目测试用例所给出的日期均为 非闰年 的有效日期。
 */
public class Problem2409 {
    public static void main(String[] args) {
        Problem2409 problem2409 = new Problem2409();
        String arriveAlice = "08-15";
        String leaveAlice = "08-18";
        String arriveBob = "08-16";
        String leaveBob = "08-19";
        System.out.println(problem2409.countDaysTogether(arriveAlice, leaveAlice, arriveBob, leaveBob));
    }

    /**
     * 模拟
     * 如果Alice和Bob的日期有重叠部分，则计算Alice和Bob距离1月1日的天数，两者之差即为Alice和Bob同时在罗马的天数
     * 时间复杂度O(1)，空间复杂度O(1)
     *
     * @param arriveAlice
     * @param leaveAlice
     * @param arriveBob
     * @param leaveBob
     * @return
     */
    public int countDaysTogether(String arriveAlice, String leaveAlice, String arriveBob, String leaveBob) {
        //arriveAlice距离1月1日的天数
        int arriveAliceDay = getDays(arriveAlice);
        //leaveAlice距离1月1日的天数
        int leaveAliceDay = getDays(leaveAlice);
        //arriveBob距离1月1日的天数
        int arriveBobDay = getDays(arriveBob);
        //leaveBob距离1月1日的天数
        int leaveBobDay = getDays(leaveBob);

        //Alice和Bob的日期不重叠，则Alice和Bob同时在罗马的天数为0
        if (leaveAliceDay < arriveBobDay || arriveAliceDay > leaveBobDay) {
            return 0;
        }

        return Math.min(leaveAliceDay, leaveBobDay) - Math.max(arriveAliceDay, arriveBobDay) + 1;
    }

    /**
     * 返回date距离1月1日的间隔天数
     * 时间复杂度O(1)，空间复杂度O(1)
     *
     * @param date
     * @return
     */
    private int getDays(String date) {
        int month = Integer.parseInt(date.substring(0, 2));
        int day = Integer.parseInt(date.substring(3, 5));

        int count = 0;

        for (int i = 1; i < month; i++) {
            //1、3、5、7、8、10、12月有31天
            if (i == 1 || i == 3 || i == 5 || i == 7 || i == 8 || i == 10 || i == 12) {
                count = count + 31;
            } else if (i == 4 || i == 6 || i == 9 || i == 11) {
                //4、6、9、11月有30天
                count = count + 30;
            } else {
                //不考虑闰年，2月有28天
                count = count + 28;
            }
        }

        //第month个月天数单独计算
        count = count + day;

        return count;
    }
}
