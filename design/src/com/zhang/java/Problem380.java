package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/10/29 08:45
 * @Author zsy
 * @Description O(1) 时间插入、删除和获取随机元素 类比problem381
 * 实现RandomizedSet 类：
 * RandomizedSet() 初始化 RandomizedSet 对象
 * bool insert(int val) 当元素 val 不存在时，向集合中插入该项，并返回 true ；否则，返回 false 。
 * bool remove(int val) 当元素 val 存在时，从集合中移除该项，并返回 true ；否则，返回 false 。
 * int getRandom() 随机返回现有集合中的一项（测试用例保证调用此方法时集合中至少存在一个元素）。每个元素应该有 相同的概率 被返回。
 * 你必须实现类的所有函数，并满足每个函数的 平均 时间复杂度为 O(1) 。
 * <p>
 * 输入
 * ["RandomizedSet", "insert", "remove", "insert", "getRandom", "remove", "insert", "getRandom"]
 * [[], [1], [2], [2], [], [1], [2], []]
 * 输出
 * [null, true, false, true, 2, true, false, 2]
 * 解释
 * RandomizedSet randomizedSet = new RandomizedSet();
 * randomizedSet.insert(1); // 向集合中插入 1 。返回 true 表示 1 被成功地插入。
 * randomizedSet.remove(2); // 返回 false ，表示集合中不存在 2 。
 * randomizedSet.insert(2); // 向集合中插入 2 。返回 true 。集合现在包含 [1,2] 。
 * randomizedSet.getRandom(); // getRandom 应随机返回 1 或 2 。
 * randomizedSet.remove(1); // 从集合中移除 1 ，返回 true 。集合现在包含 [2] 。
 * randomizedSet.insert(2); // 2 已在集合中，所以返回 false 。
 * randomizedSet.getRandom(); // 由于 2 是集合中唯一的数字，getRandom 总是返回 2 。
 * <p>
 * -2^31 <= val <= 2^31 - 1
 * 最多调用 insert、remove 和 getRandom 函数 2 * 10^5 次
 * 在调用 getRandom 方法时，数据结构中 至少存在一个 元素。
 */
public class Problem380 {
    public static void main(String[] args) {
        RandomizedSet randomizedSet = new RandomizedSet();
        System.out.println(randomizedSet.insert(1));
        System.out.println(randomizedSet.remove(2));
        System.out.println(randomizedSet.insert(2));
        System.out.println(randomizedSet.getRandom());
        System.out.println(randomizedSet.remove(1));
        System.out.println(randomizedSet.insert(2));
        System.out.println(randomizedSet.getRandom());
    }

    /**
     * list集合+哈希表
     * 移除元素的时候，用list集合中最后一个元素替换当前元素，再删除最后一个元素
     * 时间复杂度O(1)，空间复杂度O(n)
     */
    static class RandomizedSet {
        //存储元素的list集合
        private final List<Integer> list;

        //key：list中的元素，value：key在list中的下标索引
        private final Map<Integer, Integer> map;

        //获取随机值，用于随机访问
        private final Random random;

        public RandomizedSet() {
            list = new ArrayList<>();
            map = new HashMap<>();
            random = new Random();
        }

        public boolean insert(int val) {
            //map中已经存在val，则不能插入，直接返回false
            if (map.containsKey(val)) {
                return false;
            }

            int index = list.size();
            list.add(val);
            map.put(val, index);

            return true;
        }

        public boolean remove(int val) {
            //map中没有val，则不能删除，直接返回false
            if (!map.containsKey(val)) {
                return false;
            }

            //要删除元素在list集合中的下标索引
            int index = map.get(val);
            //list集合中最后一个元素下标索引
            int lastIndex = list.size() - 1;
            //list集合中最后一个元素
            int lastValue = list.get(lastIndex);

            //用list集合中最后一个元素替换当前元素，再删除最后一个元素
            //注意：要先set再remove
            list.set(index, lastValue);
            list.remove(lastIndex);

            //map中移除当前元素，更新map中list集合中最后一个元素在list集合中的下标索引
            //注意：要先put再remove
            map.put(lastValue, index);
            map.remove(val);

            return true;
        }

        public int getRandom() {
            //[0,list.size()-1]的随机数
            int index = random.nextInt(list.size());

            return list.get(index);
        }
    }
}
