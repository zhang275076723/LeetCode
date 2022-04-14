package com.zhang.java;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;

/**
 * @Date 2021/12/1 17:53
 * @Author zsy
 * @Description
 */
public class Problem732 {
    public static void main(String[] args) {
        MyCalendarThree m = new MyCalendarThree();
        m.book(10,20);
        m.book(50,60);
        m.book(10,40);
        m.book(5,15);
    }
}

class MyCalendarThree {
    private TreeMap<Integer, Integer> calendar;

    public MyCalendarThree() {
        calendar = new TreeMap<>();
    }

    public int book(int start, int end) {

        // 添加至日程中
        calendar.put(start, calendar.getOrDefault(start, 0) + 1);
        calendar.put(end, calendar.getOrDefault(end, 0) - 1);

        // 记录最大活跃的日程数
        int max = 0;
        // 记录活跃的日程数
        int active = 0;

        for (Integer d : calendar.values()) {
            // 以时间线统计日程
            active += d;

            // 找到活跃事件数量最多的时刻，记录下来。
            if (active > max) {
                max = active;
            }
        }

        return max;
    }
}