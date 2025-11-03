package com.zhang.java;

/**
 * @Date 2025/11/3 13:12
 * @Author zsy
 * @Description 日期之间隔几天 类比Problem539、Problem635、Problem1154、Problem2224、Problem2409
 * 请你编写一个程序来计算两个日期之间隔了多少天。
 * 日期以字符串形式给出，格式为 YYYY-MM-DD，如示例所示。
 * <p>
 * 输入：date1 = "2019-06-29", date2 = "2019-06-30"
 * 输出：1
 * <p>
 * 输入：date1 = "2020-01-15", date2 = "2019-12-31"
 * 输出：15
 * <p>
 * 给定的日期是 1971 年到 2100 年之间的有效日期。
 */
public class Problem1360 {
    public static void main(String[] args) {
        Problem1360 problem1360 = new Problem1360();
        String date1 = "2020-01-15";
        String date2 = "2019-12-31";
        System.out.println(problem1360.daysBetweenDates(date1, date2));
    }

    /**
     * 模拟
     * 计算date1和date2距离1971年1月1日的间隔天数，两者之差即为date1和date2的间隔天数
     * 时间复杂度O(1)，空间复杂度O(1)
     *
     * @param date1
     * @param date2
     * @return
     */
    public int daysBetweenDates(String date1, String date2) {
        int day1 = getDays(date1);
        int day2 = getDays(date2);

        return Math.abs(day1 - day2);
    }

    /**
     * 返回date距离1971年1月1日的间隔天数
     * 时间复杂度O(1)，空间复杂度O(1)
     *
     * @param date
     * @return
     */
    private int getDays(String date) {
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(5, 7));
        int day = Integer.parseInt(date.substring(8, 10));

        int count = 0;

        //计算第i年每年的天数
        for (int i = 1971; i < year; i++) {
            //能被4整除，但不能被100整除的是闰年，或者能被400整除的也是闰年
            if ((i % 4 == 0 && i % 100 != 0) || (i % 400 == 0)) {
                count = count + 366;
            } else {
                count = count + 365;
            }
        }

        //第year年前month个月数单独计算
        for (int i = 1; i < month; i++) {
            //1、3、5、7、8、10、12月有31天
            if (i == 1 || i == 3 || i == 5 || i == 7 || i == 8 || i == 10 || i == 12) {
                count = count + 31;
            } else if (i == 4 || i == 6 || i == 9 || i == 11) {
                //4、6、9、11月有30天
                count = count + 30;
            } else {
                //闰年2月有29天
                if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
                    count = count + 29;
                } else {
                    //平年2月有28天
                    count = count + 28;
                }
            }
        }

        //第year年第month个月天数单独计算
        count = count + day;

        return count;
    }
}
