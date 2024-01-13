package com.zhang.java;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2023/12/28 08:38
 * @Author zsy
 * @Description 两数之和 III - 数据结构设计 类比Problem1、Problem15、Problem16、Problem18、Problem167、Problem454、Problem653、Problem1099、Offer57
 * 设计一个接收整数流的数据结构，该数据结构支持检查是否存在两数之和等于特定值。
 * 实现 TwoSum 类：
 * TwoSum() 使用空数组初始化 TwoSum 对象
 * void add(int number) 向数据结构添加一个数 number
 * boolean find(int value) 寻找数据结构中是否存在一对整数，使得两数之和与给定的值相等。如果存在，返回 true ；否则，返回 false 。
 * <p>
 * 输入：
 * ["TwoSum", "add", "add", "add", "find", "find"]
 * [[], [1], [3], [5], [4], [7]]
 * 输出：
 * [null, null, null, null, true, false]
 * 解释：
 * TwoSum twoSum = new TwoSum();
 * twoSum.add(1);   // [] --> [1]
 * twoSum.add(3);   // [1] --> [1,3]
 * twoSum.add(5);   // [1,3] --> [1,3,5]
 * twoSum.find(4);  // 1 + 3 = 4，返回 true
 * twoSum.find(7);  // 没有两个整数加起来等于 7 ，返回 false
 * <p>
 * -10^5 <= number <= 10^5
 * -2^31 <= value <= 2^31 - 1
 * 最多调用 10^4 次 add 和 find
 */
public class Problem170 {
    public static void main(String[] args) {
        TwoSum twoSum = new TwoSum();
        // [] --> [1]
        twoSum.add(1);
        // [1] --> [1,3]
        twoSum.add(3);
        // [1,3] --> [1,3,5]
        twoSum.add(5);
        // 1 + 3 = 4，返回 true
        System.out.println(twoSum.find(4));
        // 没有两个整数加起来等于 7 ，返回 false
        System.out.println(twoSum.find(7));
    }

    /**
     * 哈希表
     */
    static class TwoSum {
        //key：当前元素，value：当前元素出现的次数
        private final Map<Integer, Integer> map;

        public TwoSum() {
            map = new HashMap<>();
        }

        public void add(int number) {
            map.put(number, map.getOrDefault(number, 0) + 1);
        }

        /**
         * 时间复杂度O(n)，空间复杂度O(1)
         *
         * @param value
         * @return
         */
        public boolean find(int value) {
            for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                //当前元素x
                int x = entry.getKey();
                //当前元素x出现的次数
                int count = entry.getValue();
                //和x相对应，相加为value的数y
                int y = value - x;

                //map中存在y，并且x和y不相等，或者x和y相等，x出现的次数大于1，则map中存在x+y等于value的情况，返回true
                if (map.containsKey(y) && (x != y || count > 1)) {
                    return true;
                }
            }

            //map遍历结束，不存在两数之和等于value的情况，返回false
            return false;
        }
    }
}
