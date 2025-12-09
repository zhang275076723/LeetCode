package com.zhang.java;

import java.util.*;

/**
 * @Date 2024/11/23 08:37
 * @Author zsy
 * @Description 基于时间的键值存储 MVCC类比Problem1146 有序集合类比Problem220、Problem352、Problem363、Problem855、Problem1146、Problem1348、Problem1912、Problem2034、Problem2071、Problem2349、Problem2353、Problem2502、Problem2590
 * 设计一个基于时间的键值数据结构，该结构可以在不同时间戳存储对应同一个键的多个值，并针对特定时间戳检索键对应的值。
 * 实现 TimeMap 类：
 * TimeMap() 初始化数据结构对象
 * void set(String key, String value, int timestamp) 存储给定时间戳 timestamp 时的键 key 和值 value。
 * String get(String key, int timestamp) 返回一个值，该值在之前调用了 set，其中 timestamp_prev <= timestamp 。
 * 如果有多个这样的值，它将返回与最大  timestamp_prev 关联的值。如果没有值，则返回空字符串（""）。
 * <p>
 * 输入：
 * ["TimeMap", "set", "get", "get", "set", "get", "get"]
 * [[], ["foo", "bar", 1], ["foo", 1], ["foo", 3], ["foo", "bar2", 4], ["foo", 4], ["foo", 5]]
 * 输出：
 * [null, null, "bar", "bar", null, "bar2", "bar2"]
 * 解释：
 * TimeMap timeMap = new TimeMap();
 * timeMap.set("foo", "bar", 1);  // 存储键 "foo" 和值 "bar" ，时间戳 timestamp = 1
 * timeMap.get("foo", 1);         // 返回 "bar"
 * timeMap.get("foo", 3);         // 返回 "bar", 因为在时间戳 3 和时间戳 2 处没有对应 "foo" 的值，所以唯一的值位于时间戳 1 处（即 "bar"） 。
 * timeMap.set("foo", "bar2", 4); // 存储键 "foo" 和值 "bar2" ，时间戳 timestamp = 4
 * timeMap.get("foo", 4);         // 返回 "bar2"
 * timeMap.get("foo", 5);         // 返回 "bar2"
 * <p>
 * 1 <= key.length, value.length <= 100
 * key 和 value 由小写英文字母和数字组成
 * 1 <= timestamp <= 10^7
 * set 操作中的时间戳 timestamp 都是严格递增的
 * 最多调用 set 和 get 操作 2 * 10^5 次
 */
public class Problem981 {
    public static void main(String[] args) {
//        TimeMap timeMap = new TimeMap();
        TimeMap2 timeMap = new TimeMap2();
        // 存储键 "foo" 和值 "bar" ，时间戳 timestamp = 1
        timeMap.set("foo", "bar", 1);
        // 返回 "bar"
        System.out.println(timeMap.get("foo", 1));
        // 返回 "bar", 因为在时间戳 3 和时间戳 2 处没有对应 "foo" 的值，所以唯一的值位于时间戳 1 处（即 "bar"） 。
        System.out.println(timeMap.get("foo", 3));
        // 存储键 "foo" 和值 "bar2" ，时间戳 timestamp = 4
        timeMap.set("foo", "bar2", 4);
        // 返回 "bar2"
        System.out.println(timeMap.get("foo", 4));
        // 返回 "bar2"
        System.out.println(timeMap.get("foo", 5));
    }

    /**
     * 二分查找 (MVCC)
     * 记录每一个key对应的时间戳和value，通过二分查找查找小于等于当前时间戳的key对应的最大时间戳，即为当前时间戳key对应的value
     */
    static class TimeMap {
        private final Map<String, List<Pair>> map;

        public TimeMap() {
            map = new HashMap<>();
        }

        public void set(String key, String value, int timestamp) {
            if (!map.containsKey(key)) {
                map.put(key, new ArrayList<>());
            }

            map.get(key).add(new Pair(value, timestamp));
        }

        public String get(String key, int timestamp) {
            //不存在当前key，则返回""
            if (!map.containsKey(key)) {
                return "";
            }

            List<Pair> list = map.get(key);

            //小于等于timestamp的key对应最大时间戳在list中的下标索引
            //初始化为-1，表示不存在小于等于timestamp的key对应时间戳
            int result = -1;
            int left = 0;
            int right = list.size() - 1;
            int mid;

            //通过二分查找查找小于等于timestamp的key对应的最大时间戳
            while (left <= right) {
                mid = left + ((right - left) >> 1);

                if (list.get(mid).timestamp <= timestamp) {
                    result = mid;
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }

            return result == -1 ? "" : list.get(result).value;
        }

        private static class Pair {
            private String value;
            //当前键值对的时间戳
            private int timestamp;

            public Pair(String value, int timestamp) {
                this.value = value;
                this.timestamp = timestamp;
            }
        }
    }

    /**
     * 有序集合 (MVCC)
     * 记录每一个key对应的时间戳和value，通过有序集合查找小于等于当前时间戳的key对应的最大时间戳，即为当前时间戳key对应的value
     */
    static class TimeMap2 {
        //key：键，value：按照时间戳由小到大存储相同键的有序集合
        //注意：基于树的集合，例如TreeSet和TreeMap不需要重写equals()和hashCode()
        private final Map<String, TreeSet<Pair>> map;

        public TimeMap2() {
            map = new HashMap<>();
        }

        public void set(String key, String value, int timestamp) {
            if (!map.containsKey(key)) {
                map.put(key, new TreeSet<>(new Comparator<Pair>() {
                    @Override
                    public int compare(Pair pair1, Pair pair2) {
                        return pair1.timestamp - pair2.timestamp;
                    }
                }));
            }

            TreeSet<Pair> treeSet = map.get(key);
            treeSet.add(new Pair(value, timestamp));
        }

        public String get(String key, int timestamp) {
            //不存在当前key，则返回""
            if (!map.containsKey(key)) {
                return "";
            }

            TreeSet<Pair> treeSet = map.get(key);

            Pair pair = new Pair(null, timestamp);
            //treeSet中小于等于timestamp的最大时间戳的Pair
            Pair prePair = treeSet.floor(pair);

            return prePair == null ? "" : prePair.value;
        }

        private static class Pair {
            private String value;
            //当前键值对的时间戳
            private int timestamp;

            public Pair(String value, int timestamp) {
                this.value = value;
                this.timestamp = timestamp;
            }
        }
    }
}
