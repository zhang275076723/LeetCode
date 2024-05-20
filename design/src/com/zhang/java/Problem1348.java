package com.zhang.java;

import java.util.*;

/**
 * @Date 2024/5/11 08:47
 * @Author zsy
 * @Description 推文计数 类比Problem355 有序集合类比Problem220、Problem352、Problem363、Problem855
 * 一家社交媒体公司正试图通过分析特定时间段内出现的推文数量来监控其网站上的活动。
 * 这些时间段可以根据特定的频率（ 每分钟 、每小时 或 每一天 ）划分为更小的 时间段 。
 * 例如，周期 [10,10000] （以 秒 为单位）将被划分为以下频率的 时间块 :
 * 每 分钟 (60秒 块)： [10,69], [70,129], [130,189], ..., [9970,10000]
 * 每 小时 (3600秒 块)：[10,3609], [3610,7209], [7210,10000]
 * 每 天 (86400秒 块)： [10,10000]
 * 注意，最后一个块可能比指定频率的块大小更短，并且总是以时间段的结束时间结束(在上面的示例中为 10000 )。
 * 设计和实现一个API来帮助公司进行分析。
 * 实现 TweetCounts 类:
 * TweetCounts() 初始化 TweetCounts 对象。
 * 存储记录时间的 tweetName (以秒为单位)。
 * List<integer> getTweetCountsPerFrequency(String freq, String tweetName, int startTime, int endTime) 返回一个整数列表，
 * 表示给定时间 [startTime, endTime] （单位秒）和频率频率中，每个 时间块 中带有 tweetName 的 tweet 的数量。
 * freq 是 “minute” 、 “hour” 或 “day” 中的一个，分别表示 每分钟 、 每小时 或 每一天 的频率。
 * <p>
 * 输入：
 * ["TweetCounts","recordTweet","recordTweet","recordTweet","getTweetCountsPerFrequency","getTweetCountsPerFrequency","recordTweet","getTweetCountsPerFrequency"]
 * [[],["tweet3",0],["tweet3",60],["tweet3",10],["minute","tweet3",0,59],["minute","tweet3",0,60],["tweet3",120],["hour","tweet3",0,210]]
 * <p>
 * 输出：
 * [null,null,null,null,[2],[2,1],null,[4]]
 * 解释：
 * TweetCounts tweetCounts = new TweetCounts();
 * tweetCounts.recordTweet("tweet3", 0);
 * tweetCounts.recordTweet("tweet3", 60);
 * tweetCounts.recordTweet("tweet3", 10);                             // "tweet3" 发布推文的时间分别是 0, 10 和 60 。
 * tweetCounts.getTweetCountsPerFrequency("minute", "tweet3", 0, 59); // 返回 [2]。统计频率是每分钟（60 秒），因此只有一个有效时间间隔 [0,60> - > 2 条推文。
 * tweetCounts.getTweetCountsPerFrequency("minute", "tweet3", 0, 60); // 返回 [2,1]。统计频率是每分钟（60 秒），因此有两个有效时间间隔 1) [0,60> - > 2 条推文，和 2) [60,61> - > 1 条推文。
 * tweetCounts.recordTweet("tweet3", 120);                            // "tweet3" 发布推文的时间分别是 0, 10, 60 和 120 。
 * tweetCounts.getTweetCountsPerFrequency("hour", "tweet3", 0, 210);  // 返回 [4]。统计频率是每小时（3600 秒），因此只有一个有效时间间隔 [0,211> - > 4 条推文。
 * <p>
 * 0 <= time, startTime, endTime <= 10^9
 * 0 <= endTime - startTime <= 10^4
 * recordTweet 和 getTweetCountsPerFrequency，最多有 10^4 次操作。
 */
public class Problem1348 {
    public static void main(String[] args) {
        TweetCounts tweetCounts = new TweetCounts();
        tweetCounts.recordTweet("tweet3", 0);
        tweetCounts.recordTweet("tweet3", 60);
        // "tweet3" 发布推文的时间分别是 0, 10 和 60 。
        tweetCounts.recordTweet("tweet3", 10);
        // 返回 [2]。统计频率是每分钟（60 秒），因此只有一个有效时间间隔 [0,60> - > 2 条推文。
        System.out.println(tweetCounts.getTweetCountsPerFrequency("minute", "tweet3", 0, 59));
        // 返回 [2,1]。统计频率是每分钟（60 秒），因此有两个有效时间间隔 1) [0,60> - > 2 条推文，和 2) [60,61> - > 1 条推文。
        System.out.println(tweetCounts.getTweetCountsPerFrequency("minute", "tweet3", 0, 60));
        // "tweet3" 发布推文的时间分别是 0, 10, 60 和 120 。
        tweetCounts.recordTweet("tweet3", 120);
        // 返回 [4]。统计频率是每小时（3600 秒），因此只有一个有效时间间隔 [0,211> - > 4 条推文。
        System.out.println(tweetCounts.getTweetCountsPerFrequency("hour", "tweet3", 0, 210));
    }

    /**
     * 有序集合
     */
    static class TweetCounts {
        //key：推文发布者tweetName，value：有序集合，由小到大存储推文发布者的推文发布时间(key)和当前时间发布的推文数量(value)
        private final Map<String, TreeMap<Integer, Integer>> tweeterMap;

        public TweetCounts() {
            tweeterMap = new HashMap<>();
        }

        public void recordTweet(String tweetName, int time) {
            //tweeterMap中不存在推文发布者tweetName，则tweetName加入tweeterMap
            if (!tweeterMap.containsKey(tweetName)) {
                //有序集合，由小到大存储推文发布者的推文发布时间(key)和当前时间发布的推文数量(value)
                tweeterMap.put(tweetName, new TreeMap<>(new Comparator<Integer>() {
                    @Override
                    public int compare(Integer a, Integer b) {
                        return a - b;
                    }
                }));
            }

            //有序集合，由小到大存储推文发布者的推文发布时间(key)和当前时间发布的推文数量(value)
            TreeMap<Integer, Integer> tweetMap = tweeterMap.get(tweetName);
            //当前时间发布的推文加入tweetMap
            tweetMap.put(time, tweetMap.getOrDefault(time, 0) + 1);
        }

        public List<Integer> getTweetCountsPerFrequency(String freq, String tweetName, int startTime, int endTime) {
            //tweeterMap中不存在推文发布者tweetName，直接返回[0]
            if (!tweeterMap.containsKey(tweetName)) {
                return new ArrayList<Integer>() {{
                    add(0);
                }};
            }

            //以秒为单位的频率
            int frequency = 1;

            if ("minute".equals(freq)) {
                frequency = 60;
            } else if ("hour".equals(freq)) {
                frequency = 3600;
            } else if ("day".equals(freq)) {
                frequency = 86400;
            }

            List<Integer> list = new ArrayList<>();
            //有序集合，由小到大存储推文发布者的推文发布时间(key)和当前时间发布的推文数量(value)
            TreeMap<Integer, Integer> tweetMap = tweeterMap.get(tweetName);
            //当前间隔的起始时间
            int curTime = startTime;

            while (curTime <= endTime) {
                //下一个间隔的起始时间
                int nextTime = Math.min(curTime + frequency, endTime + 1);
                //当前间隔的推文数量
                int count = 0;
                //O(logn)找到tweetMap中key大于等于curTime的最小entry
                Map.Entry<Integer, Integer> entry = tweetMap.ceilingEntry(curTime);

                //统计当前间隔中的所有推文数量
                while (entry != null && entry.getKey() < nextTime) {
                    count = count + entry.getValue();
                    //O(logn)找到tweetMap中key大于entry.getKey()的最小entry，即找当前entry的下一个entry
                    entry = tweetMap.higherEntry(entry.getKey());
                }

                list.add(count);
                //更新curTime
                curTime = nextTime;
            }

            return list;
        }
    }
}
