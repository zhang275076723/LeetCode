package com.zhang.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Date 2024/9/12 08:27
 * @Author zsy
 * @Description 设计日志存储系统 类比Problem539、Problem1154、Problem1360、Problem2224、Problem2409
 * 你将获得多条日志，每条日志都有唯一的 id 和 timestamp ，timestamp 是形如 Year:Month:Day:Hour:Minute:Second 的字符串，
 * 2017:01:01:23:59:59 ，所有值域都是零填充的十进制数。
 * 实现 LogSystem 类：
 * LogSystem() 初始化 LogSystem 对象
 * void put(int id, string timestamp) 给定日志的 id 和 timestamp ，将这个日志存入你的存储系统中。
 * int[] retrieve(string start, string end, string granularity) 返回在给定时间区间 [start, end]（包含两端）内的所有日志的 id 。
 * start 、end 和 timestamp 的格式相同，granularity 表示考虑的时间粒度（例如，精确到 Day、Minute 等）。
 * 例如 start = "2017:01:01:23:59:59"、end = "2017:01:02:23:59:59" 且 granularity = "Day"
 * 意味着需要查找从 Jan. 1st 2017 到 Jan. 2nd 2017 范围内的日志，可以忽略日志的 Hour、Minute 和 Second 。
 * <p>
 * 输入：
 * ["LogSystem", "put", "put", "put", "retrieve", "retrieve"]
 * [[], [1, "2017:01:01:23:59:59"], [2, "2017:01:01:22:59:59"], [3, "2016:01:01:00:00:00"], ["2016:01:01:01:01:01", "2017:01:01:23:00:00", "Year"], ["2016:01:01:01:01:01", "2017:01:01:23:00:00", "Hour"]]
 * 输出：
 * [null, null, null, null, [3, 2, 1], [2, 1]]
 * 解释：
 * LogSystem logSystem = new LogSystem();
 * logSystem.put(1, "2017:01:01:23:59:59");
 * logSystem.put(2, "2017:01:01:22:59:59");
 * logSystem.put(3, "2016:01:01:00:00:00");
 * // 返回 [3,2,1]，返回从 2016 年到 2017 年所有的日志。
 * logSystem.retrieve("2016:01:01:01:01:01", "2017:01:01:23:00:00", "Year");
 * // 返回 [2,1]，返回从 Jan. 1, 2016 01:XX:XX 到 Jan. 1, 2017 23:XX:XX 之间的所有日志
 * // 不返回日志 3 因为记录时间 Jan. 1, 2016 00:00:00 超过范围的起始时间
 * logSystem.retrieve("2016:01:01:01:01:01", "2017:01:01:23:00:00", "Hour");
 * <p>
 * 1 <= id <= 500
 * 2000 <= Year <= 2017
 * 1 <= Month <= 12
 * 1 <= Day <= 31
 * 0 <= Hour <= 23
 * 0 <= Minute, Second <= 59
 * granularity 是这些值 ["Year", "Month", "Day", "Hour", "Minute", "Second"] 之一
 * 最多调用 500 次 put 和 retrieve
 */
public class Problem635 {
    public static void main(String[] args) {
        LogSystem logSystem = new LogSystem();
        logSystem.put(1, "2017:01:01:23:59:59");
        logSystem.put(2, "2017:01:01:22:59:59");
        logSystem.put(3, "2016:01:01:00:00:00");
        // 返回 [3,2,1]，返回从 2016 年到 2017 年所有的日志。
        System.out.println(logSystem.retrieve("2016:01:01:01:01:01", "2017:01:01:23:00:00", "Year"));
        // 返回 [2,1]，返回从 Jan. 1, 2016 01:XX:XX 到 Jan. 1, 2017 23:XX:XX 之间的所有日志
        // 不返回日志 3 因为记录时间 Jan. 1, 2016 00:00:00 超过范围的起始时间
        System.out.println(logSystem.retrieve("2016:01:01:01:01:01", "2017:01:01:23:00:00", "Hour"));
    }

    /**
     * 模拟
     * 将字符串类型时间转换为long类型的秒存储，判断在范围内的日志id
     */
    static class LogSystem {
        private final List<Log> list;
        //key：时间类型，value：当前时间类型对应的秒
        private final Map<String, Integer> map;

        public LogSystem() {
            list = new ArrayList<>();
            map = new HashMap<>();

            map.put("Year", 12 * 31 * 24 * 3600);
            //假设每个月都有31天，这样不用考虑每月天数不同的影响
            map.put("Month", 31 * 24 * 3600);
            map.put("Day", 24 * 3600);
            map.put("Hour", 3600);
            map.put("Minute", 60);
            map.put("Second", 1);
        }

        public void put(int id, String timestamp) {
            int year = Integer.parseInt(timestamp.substring(0, 4));
            int month = Integer.parseInt(timestamp.substring(5, 7));
            int day = Integer.parseInt(timestamp.substring(8, 10));
            int hour = Integer.parseInt(timestamp.substring(11, 13));
            int minute = Integer.parseInt(timestamp.substring(14, 16));
            int second = Integer.parseInt(timestamp.substring(17));

            //注意：月和天都是从1开始的，需要减1
            long time = (long) year * map.get("Year") + (long) (month - 1) * map.get("Month") +
                    (long) (day - 1) * map.get("Day") + (long) hour * map.get("Hour") +
                    (long) minute * map.get("Minute") + (long) second * map.get("Second");

            //转换后的日志加入list中
            list.add(new Log(id, time));
        }

        public List<Integer> retrieve(String start, String end, String granularity) {
            //start根据时间粒度转换为秒的时间
            long startTime = 0;
            //end根据时间粒度转换为秒的时间，结尾时间需要加上当前粒度对应的秒
            long endTime = 0;

            int year1 = Integer.parseInt(start.substring(0, 4));
            int year2 = Integer.parseInt(end.substring(0, 4));
            int month1 = Integer.parseInt(start.substring(5, 7));
            int month2 = Integer.parseInt(end.substring(5, 7));
            int day1 = Integer.parseInt(start.substring(8, 10));
            int day2 = Integer.parseInt(end.substring(8, 10));
            int hour1 = Integer.parseInt(start.substring(11, 13));
            int hour2 = Integer.parseInt(end.substring(11, 13));
            int minute1 = Integer.parseInt(start.substring(14, 16));
            int minute2 = Integer.parseInt(end.substring(14, 16));
            int second1 = Integer.parseInt(start.substring(17));
            int second2 = Integer.parseInt(end.substring(17));

            //注意：月和天都是从1开始的，需要减1
            if ("Year".equals(granularity)) {
                startTime = (long) year1 * map.get("Year");
                //结尾时间需要加上当前粒度对应的秒
                endTime = (long) year2 * map.get("Year") + map.get("Year");
            } else if ("Month".equals(granularity)) {
                startTime = (long) year1 * map.get("Year") + (long) (month1 - 1) * map.get("Month");
                //结尾时间需要加上当前粒度对应的秒
                endTime = (long) year2 * map.get("Year") + (long) (month2 - 1) * map.get("Month") + map.get("Month");
            } else if ("Day".equals(granularity)) {
                startTime = (long) year1 * map.get("Year") + (long) (month1 - 1) * map.get("Month") +
                        (long) (day1 - 1) * map.get("Day");
                //结尾时间需要加上当前粒度对应的秒
                endTime = (long) year2 * map.get("Year") + (long) (month2 - 1) * map.get("Month") +
                        (long) (day2 - 1) * map.get("Day") + map.get("Day");
            } else if ("Hour".equals(granularity)) {
                startTime = (long) year1 * map.get("Year") + (long) (month1 - 1) * map.get("Month") +
                        (long) (day1 - 1) * map.get("Day") + (long) hour1 * map.get("Hour");
                //结尾时间需要加上当前粒度对应的秒
                endTime = (long) year2 * map.get("Year") + (long) (month2 - 1) * map.get("Month") +
                        (long) (day2 - 1) * map.get("Day") + (long) hour2 * map.get("Hour") + map.get("Hour");
            } else if ("Minute".equals(granularity)) {
                startTime = (long) year1 * map.get("Year") + (long) (month1 - 1) * map.get("Month") +
                        (long) (day1 - 1) * map.get("Day") + (long) hour1 * map.get("Hour") +
                        (long) minute1 * map.get("Minute");
                //结尾时间需要加上当前粒度对应的秒
                endTime = (long) year2 * map.get("Year") + (long) (month2 - 1) * map.get("Month") +
                        (long) (day2 - 1) * map.get("Day") + (long) hour2 * map.get("Hour") +
                        (long) minute2 * map.get("Minute") + map.get("Minute");
            } else if ("Second".equals(granularity)) {
                startTime = (long) year1 * map.get("Year") + (long) (month1 - 1) * map.get("Month") +
                        (long) (day1 - 1) * map.get("Day") + (long) hour1 * map.get("Hour") +
                        (long) minute1 * map.get("Minute") + (long) second1 * map.get("Second");
                //结尾时间需要加上当前粒度对应的秒
                endTime = (long) year2 * map.get("Year") + (long) (month2 - 1) * map.get("Month") +
                        (long) (day2 - 1) * map.get("Day") + (long) hour2 * map.get("Hour") +
                        (long) minute2 * map.get("Minute") + (long) second2 * map.get("Second") + map.get("Second");
            }

            List<Integer> result = new ArrayList<>();

            for (Log log : list) {
                //在范围的日志时间戳范围为[startTime,endTime)
                //注意：是小于endTime，而不是小于等于endTime
                if (startTime <= log.timestamp && log.timestamp < endTime) {
                    result.add(log.id);
                }
            }

            return result;
        }

        /**
         * 日志节点
         */
        private static class Log {
            //日志id
            private int id;
            //long类型的日志时间戳，单位为秒
            private long timestamp;

            public Log(int id, long timestamp) {
                this.id = id;
                this.timestamp = timestamp;
            }
        }
    }
}
