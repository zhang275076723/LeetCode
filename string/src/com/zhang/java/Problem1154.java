package com.zhang.java;

/**
 * @Date 2025/11/3 11:08
 * @Author zsy
 * @Description 一年中的第几天 类比Problem539、Problem635、Problem1360、Problem2224、Problem2409
 * 给你一个字符串 date ，按 YYYY-MM-DD 格式表示一个 现行公元纪年法 日期。
 * 返回该日期是当年的第几天。
 * <p>
 * 输入：date = "2019-01-09"
 * 输出：9
 * 解释：给定日期是2019年的第九天。
 * <p>
 * 输入：date = "2019-02-10"
 * 输出：41
 * <p>
 * date.length == 10
 * date[4] == date[7] == '-'，其他的 date[i] 都是数字
 * date 表示的范围从 1900 年 1 月 1 日至 2019 年 12 月 31 日
 */
public class Problem1154 {
    public static void main(String[] args) {
        Problem1154 problem1154 = new Problem1154();
//        String date = "2019-02-10";
        String date = "1900-05-02";
        System.out.println(problem1154.dayOfYear(date));
    }

    /**
     * 模拟
     * 1、3、5、7、8、10、12月有31天，4、6、9、11月有30天，
     * 平年2月有28天，闰年2月有29天
     * 时间复杂度O(1)，空间复杂度O(1)
     *
     * @param date
     * @return
     */
    public int dayOfYear(String date) {
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(5, 7));
        int day = Integer.parseInt(date.substring(8, 10));
        //当年是否为闰年
        boolean flag = false;

        //能被4整除，但不能被100整除的是闰年，或者能被400整除的也是闰年
        if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
            flag = true;
        }

        int count = 0;

        //当前月份
        for (int i = 1; i < month; i++) {
            //1、3、5、7、8、10、12月有31天
            if (i == 1 || i == 3 || i == 5 || i == 7 || i == 8 || i == 10 || i == 12) {
                count = count + 31;
            } else if (i == 4 || i == 6 || i == 9 || i == 11) {
                //4、6、9、11月有30天
                count = count + 30;
            } else {
                //闰年2月有29天
                if (flag) {
                    count = count + 29;
                } else {
                    //平年2月有28天
                    count = count + 28;
                }
            }
        }

        //第month月单独计算
        count = count + day;

        return count;
    }
}
