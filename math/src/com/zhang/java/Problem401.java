package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2024/3/11 08:40
 * @Author zsy
 * @Description 二进制手表 位运算类比Problem29、Problem136、Problem137、Problem190、Problem191、Problem201、Problem231、Problem260、Problem271、Problem326、Problem342、Problem371、Problem389、Problem405、Problem461、Problem477、Problem645、Problem898、Problem1290、Offer15、Offer56、Offer56_2、Offer64、Offer65、IPToInt 回溯+剪枝类比Problem17
 * 二进制手表顶部有 4 个 LED 代表 小时（0-11），底部的 6 个 LED 代表 分钟（0-59）。
 * 每个 LED 代表一个 0 或 1，最低位在右侧。
 * 例如，下面的二进制手表读取 "4:51" 。
 * 给你一个整数 turnedOn ，表示当前亮着的 LED 的数量，返回二进制手表可以表示的所有可能时间。
 * 你可以 按任意顺序 返回答案。
 * 小时不会以零开头：
 * 例如，"01:00" 是无效的时间，正确的写法应该是 "1:00" 。
 * 分钟必须由两位数组成，可能会以零开头：
 * 例如，"10:2" 是无效的时间，正确的写法应该是 "10:02" 。
 * <p>
 * 输入：turnedOn = 1
 * 输出：["0:01","0:02","0:04","0:08","0:16","0:32","1:00","2:00","4:00","8:00"]
 * <p>
 * 输入：turnedOn = 9
 * 输出：[]
 * <p>
 * 0 <= turnedOn <= 10
 */
public class Problem401 {
    public static void main(String[] args) {
        Problem401 problem401 = new Problem401();
        int turnedOn = 1;
        System.out.println(problem401.readBinaryWatch(turnedOn));
        System.out.println(problem401.readBinaryWatch2(turnedOn));
    }

    /**
     * 回溯+剪枝
     * 时间复杂度O(10)=O(1)，空间复杂度O(10)=O(1) (小时4位，分钟6位，共10位需要考虑)
     *
     * @param turnedOn
     * @return
     */
    public List<String> readBinaryWatch(int turnedOn) {
        List<String> list = new ArrayList<>();

        //小时4位，分钟6位，即范围上限为10
        backtrack(0, 4 + 6, 0, turnedOn, 0, list);

        return list;
    }

    /**
     * 模拟
     * 统计0-11小时二进制表示的数中1的个数和0-59分钟二进制表示的数中1的个数
     * 时间复杂度O(1)，空间复杂度O(1)
     *
     * @param turnedOn
     * @return
     */
    public List<String> readBinaryWatch2(int turnedOn) {
        List<String> list = new ArrayList<>();

        for (int i = 0; i < 12; i++) {
            //小时i二进制表示的数中1的个数
            int count1 = 0;
            int num = i;

            while (num != 0) {
                count1++;
                num = num & (num - 1);
            }

            if (count1 > turnedOn) {
                continue;
            }

            for (int j = 0; j < 60; j++) {
                //分钟j二进制表示的数中1的个数
                int count2 = 0;
                num = j;

                while (num != 0) {
                    count2++;
                    num = num & (num - 1);
                }

                //小时i二进制表示的数中1的个数和分钟j二进制表示的数中1的个数和为turnedOn，将当前时间加入list
                if (count1 + count2 == turnedOn) {
                    StringBuilder sb = new StringBuilder();

                    //小时不添加前导0
                    sb.append(i).append(":");

                    //分钟添加前导0
                    if (j < 10) {
                        sb.append("0").append(j);
                    } else {
                        sb.append(j);
                    }

                    list.add(sb.toString());
                }
            }
        }

        return list;
    }

    /**
     * @param t           当前遍历到的下标索引
     * @param limit       下标索引的范围，小时4位，分钟6位，即范围上限为10
     * @param curTurnedOn 当前选择的二进制位数
     * @param turnedOn    可以选择的二进制位数
     * @param num         当前选择的二进制表示的数
     * @param list        结果集合
     */
    private void backtrack(int t, int limit, int curTurnedOn, int turnedOn, int num, List<String> list) {
        if (curTurnedOn > turnedOn) {
            return;
        }

        if (t == limit) {
            if (curTurnedOn == turnedOn) {
                String time = convert(num);

                //当前时间合法，则加入list
                if (!"".equals(time)) {
                    list.add(time);
                }
            }

            return;
        }

        //不选当前位
        backtrack(t + 1, limit, curTurnedOn, turnedOn, num << 1, list);

        //选当前位
        backtrack(t + 1, limit, curTurnedOn + 1, turnedOn, (num << 1) | 1, list);
    }

    /**
     * 二进制表示的数num转换为小时和分钟字符串
     * 时间复杂度O(1)，空间复杂度O(5)=O(1) (时间最长为5位)
     *
     * @param num
     * @return
     */
    private String convert(int num) {
        //高4位为小时
        int hour = (num & 0b1111_0000_00) >>> 6;
        //低6位为分钟
        int minute = num & 0b1111_11;

        //小时大于等于12，分钟大于等于60，则当前时间不合法，返回""
        if (hour >= 12 || minute >= 60) {
            return "";
        }

        StringBuilder sb = new StringBuilder();

        //小时不添加前导0
        sb.append(hour).append(":");

        //分钟添加前导0
        if (minute < 10) {
            sb.append("0").append(minute);
        } else {
            sb.append(minute);
        }

        return sb.toString();
    }
}
