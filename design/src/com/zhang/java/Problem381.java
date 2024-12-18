package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/10/29 11:32
 * @Author zsy
 * @Description O(1) 时间插入、删除和获取随机元素 - 允许重复 类比Problem380
 * RandomizedCollection 是一种包含数字集合(可能是重复的)的数据结构。它应该支持插入和删除特定元素，以及删除随机元素。
 * 实现 RandomizedCollection 类:
 * RandomizedCollection()初始化空的 RandomizedCollection 对象。
 * bool insert(int val) 将一个 val 项插入到集合中，即使该项已经存在。如果该项不存在，则返回 true ，否则返回 false 。
 * bool remove(int val) 如果存在，从集合中移除一个 val 项。
 * 如果该项存在，则返回 true ，否则返回 false 。注意，如果 val 在集合中出现多次，我们只删除其中一个。
 * int getRandom() 从当前的多个元素集合中返回一个随机元素。每个元素被返回的概率与集合中包含的相同值的数量 线性相关 。
 * 您必须实现类的函数，使每个函数的 平均 时间复杂度为 O(1) 。
 * 注意：生成测试用例时，只有在 RandomizedCollection 中 至少有一项 时，才会调用 getRandom 。
 * <p>
 * 输入
 * ["RandomizedCollection", "insert", "insert", "insert", "getRandom", "remove", "getRandom"]
 * [[], [1], [1], [2], [], [1], []]
 * 输出
 * [null, true, false, true, 2, true, 1]
 * 解释
 * RandomizedCollection collection = new RandomizedCollection();// 初始化一个空的集合。
 * collection.insert(1);// 向集合中插入 1 。返回 true 表示集合不包含 1 。
 * collection.insert(1);// 向集合中插入另一个 1 。返回 false 表示集合包含 1 。集合现在包含 [1,1] 。
 * collection.insert(2);// 向集合中插入 2 ，返回 true 。集合现在包含 [1,1,2] 。
 * collection.getRandom();// getRandom 应当有 2/3 的概率返回 1 ，1/3 的概率返回 2 。
 * collection.remove(1);// 从集合中删除 1 ，返回 true 。集合现在包含 [1,2] 。
 * collection.getRandom();// getRandom 应有相同概率返回 1 和 2 。
 * <p>
 * -2^31 <= val <= 2^31 - 1
 * insert, remove 和 getRandom 最多 总共 被调用 2 * 10^5 次
 * 当调用 getRandom 时，数据结构中 至少有一个 元素
 */
public class Problem381 {
    public static void main(String[] args) {
        RandomizedCollection collection = new RandomizedCollection();
        System.out.println(collection.insert(1));
        System.out.println(collection.insert(1));
        System.out.println(collection.insert(2));
        System.out.println(collection.getRandom());
        System.out.println(collection.remove(1));
        System.out.println(collection.getRandom());
    }

    /**
     * list集合+哈希表
     * 移除元素的时候，用list集合中最后一个元素替换当前元素，再删除最后一个元素
     * 时间复杂度O(1)，空间复杂度O(n)
     */
    static class RandomizedCollection {
        //存储元素的list集合
        private final List<Integer> list;

        //key：list中的元素，value：相同元素key在list中的下标索引set集合
        //注意：不能使用list存放相同元素的下标索引，只能使用set存放相同元素的下标索引，因为set才能O(1)获取末尾元素在list中的下标索引
        private final Map<Integer, Set<Integer>> map;

        //获取随机值，用于随机访问
        private final Random random;

        public RandomizedCollection() {
            list = new ArrayList<>();
            map = new HashMap<>();
            random = new Random();
        }

        public boolean insert(int val) {
            //map中已经存在val，返回false
            if (map.containsKey(val)) {
                int index = list.size() - 1;
                Set<Integer> set = map.get(val);
                list.add(val);
                set.add(index);

                return false;
            }

            map.put(val, new HashSet<>());
            Set<Integer> set = map.get(val);
            int index = list.size();
            list.add(val);
            set.add(index);

            return true;
        }

        public boolean remove(int val) {
            if (!map.containsKey(val)) {
                return false;
            }

            //存储list集合中相同val元素的下标索引set集合
            Set<Integer> set = map.get(val);
            //要移除val的下标索引
            int index = set.iterator().next();
            //list集合中最后一个元素的下标索引
            int lastValueIndex = list.size() - 1;
            //list集合中最后一个元素的值
            int lastValue = list.get(lastValueIndex);
            //相同元素lastValue在list中的下标索引set集合
            Set<Integer> lastValueSet = map.get(lastValue);

            //list集合中最后一个元素替换当前元素，再删除最后一个元素
            //注意：要先set再remove
            list.set(index, lastValue);
            list.remove(lastValueIndex);

            //set中删除index
            //注意：要先在set中remove，再从lastValueSet中add和remove
            set.remove(index);
            //index加入到lastValueSet中，list中最后一个元素lastValue的下标索引lastValueIndex从lastValueSet中删除
            //注意：要先add再remove
            lastValueSet.add(index);
            lastValueSet.remove(lastValueIndex);

            //要删除的val对应set为空，则map中删除set
            if (set.isEmpty()) {
                map.remove(val);
            }

            return true;
        }

        public int getRandom() {
            //[0,list.size()-1]的随机数
            int index = random.nextInt(list.size());

            return list.get(index);
        }
    }
}
