package com.zhang.java;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Date 2023/1/4 19:51
 * @Author zsy
 * @Description 顶端迭代器 迭代器类比Problem173、Problem251、Problem281、Problem341、Problem604、Problem900、Problem1286、Problem1586
 * 请你在设计一个迭代器，在集成现有迭代器拥有的 hasNext 和 next 操作的基础上，还额外支持 peek 操作。
 * 实现 PeekingIterator 类：
 * PeekingIterator(Iterator<int> nums) 使用指定整数迭代器 nums 初始化迭代器。
 * int next() 返回数组中的下一个元素，并将指针移动到下个元素处。
 * bool hasNext() 如果数组中存在下一个元素，返回 true ；否则，返回 false 。
 * int peek() 返回数组中的下一个元素，但 不 移动指针。
 * 注意：每种语言可能有不同的构造函数和迭代器 Iterator，但均支持 int next() 和 boolean hasNext() 函数。
 * <p>
 * 输入：
 * ["PeekingIterator", "next", "peek", "next", "next", "hasNext"]
 * [[[1, 2, 3]], [], [], [], [], []]
 * 输出：
 * [null, 1, 2, 2, 3, false]
 * 解释：
 * PeekingIterator peekingIterator = new PeekingIterator([1, 2, 3]); // [1,2,3]
 * peekingIterator.next();    // 返回 1 ，指针移动到下一个元素 [1,2,3]
 * peekingIterator.peek();    // 返回 2 ，指针未发生移动 [1,2,3]
 * peekingIterator.next();    // 返回 2 ，指针移动到下一个元素 [1,2,3]
 * peekingIterator.next();    // 返回 3 ，指针移动到下一个元素 [1,2,3]
 * peekingIterator.hasNext(); // 返回 False
 * <p>
 * 1 <= nums.length <= 1000
 * 1 <= nums[i] <= 1000
 * 对 next 和 peek 的调用均有效
 * next、hasNext 和 peek 最多调用 1000 次
 */
public class Problem284 {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        PeekingIterator peekingIterator = new PeekingIterator(list.iterator());
        // 返回 1 ，指针移动到下一个元素 [1,2,3]
        System.out.println(peekingIterator.next());
        // 返回 2 ，指针未发生移动 [1,2,3]
        System.out.println(peekingIterator.peek());
        // 返回 2 ，指针移动到下一个元素 [1,2,3]
        System.out.println(peekingIterator.next());
        // 返回 3 ，指针移动到下一个元素 [1,2,3]
        System.out.println(peekingIterator.next());
        // 返回 False
        System.out.println(peekingIterator.hasNext());
    }

    /**
     * 使用Integer保存Iterator的下一个元素，则可以获取peek，并且不移动指针
     * 时间复杂度O(1)，空间复杂度O(1)
     */
    static class PeekingIterator implements Iterator<Integer> {
        //当前迭代器
        private final Iterator<Integer> iterator;
        //当前迭代器指向的下一个元素，则可以获取peek，并且不移动指针
        //注意：只能使用Integer，不能使用int，通过Integer是否为null，判断是否还存在下一个元素
        private Integer nextValue;

        public PeekingIterator(Iterator<Integer> iterator) {
            this.iterator = iterator;
            nextValue = iterator.next();
        }

        public Integer peek() {
            return nextValue;
        }

        @Override
        public Integer next() {
            Integer value = nextValue;

            //更新下一个元素
            if (iterator.hasNext()) {
                nextValue = iterator.next();
            } else {
                //Integer为null，表示已经遍历到了末尾，不存在下一个元素
                nextValue = null;
            }

            return value;
        }

        @Override
        public boolean hasNext() {
            return nextValue != null;
        }
    }
}
