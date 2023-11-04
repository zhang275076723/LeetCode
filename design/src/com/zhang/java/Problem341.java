package com.zhang.java;

import java.util.*;

/**
 * @Date 2023/11/12 08:52
 * @Author zsy
 * @Description 扁平化嵌套列表迭代器 迭代器类比Problem173、Problem251、Problem281、Problem284、Problem1586
 * 给你一个嵌套的整数列表 nestedList 。每个元素要么是一个整数，要么是一个列表；该列表的元素也可能是整数或者是其他列表。
 * 请你实现一个迭代器将其扁平化，使之能够遍历这个列表中的所有整数。
 * 实现扁平迭代器类 NestedIterator ：
 * NestedIterator(List<NestedInteger> nestedList) 用嵌套列表 nestedList 初始化迭代器。
 * int next() 返回嵌套列表的下一个整数。
 * boolean hasNext() 如果仍然存在待迭代的整数，返回 true ；否则，返回 false 。
 * 你的代码将会用下述伪代码检测：
 * initialize iterator with nestedList
 * res = []
 * while iterator.hasNext()
 * append iterator.next() to the end of res
 * return res
 * 如果 res 与预期的扁平化列表匹配，那么你的代码将会被判为正确。
 * <p>
 * 输入：nestedList = [[1,1],2,[1,1]]
 * 输出：[1,1,2,1,1]
 * 解释：通过重复调用 next 直到 hasNext 返回 false，next 返回的元素的顺序应该是: [1,1,2,1,1]。
 * <p>
 * 输入：nestedList = [1,[4,[6]]]
 * 输出：[1,4,6]
 * 解释：通过重复调用 next 直到 hasNext 返回 false，next 返回的元素的顺序应该是: [1,4,6]。
 * <p>
 * 1 <= nestedList.length <= 500
 * 嵌套列表中的整数值在范围 [-10^6, 10^6] 内
 */
public class Problem341 {
    public static void main(String[] args) {
        List<NestedInteger> nestedList = new ArrayList<>();
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);
        Node node6 = new Node(6);
        Node node7 = new Node();
        Node node8 = new Node();
        Node node9 = new Node();
        node7.list.add(node1);
        node7.list.add(node2);
        node8.list.add(node5);
        node8.list.add(node6);
        node9.list.add(node4);
        node9.list.add(node8);
        nestedList.add(node7);
        nestedList.add(node3);
        nestedList.add(node9);
//        NestedIterator nestedIterator = new NestedIterator(nestedList);
        NestedIterator2 nestedIterator = new NestedIterator2(nestedList);
        //[[1,2],3,[4,[5,6]]]
        while (nestedIterator.hasNext()) {
            System.out.println(nestedIterator.next());
        }
    }

    /**
     * dfs
     * 通过dfs将nestedList中元素保存到list中
     * 时间复杂度O(n)，空间复杂度O(n)
     */
    static class NestedIterator implements Iterator<Integer> {
        //存储nestedList中元素的集合
        private final List<Integer> list;
        //list的迭代器
        private final Iterator<Integer> iterator;

        public NestedIterator(List<NestedInteger> nestedList) {
            list = new ArrayList<>();
            dfs(nestedList, list);
            iterator = list.iterator();
        }

        private void dfs(List<NestedInteger> nestedList, List<Integer> list) {
            for (NestedInteger nestedInteger : nestedList) {
                //当前节点存储整数，加入list中
                if (nestedInteger.isInteger()) {
                    list.add(nestedInteger.getInteger());
                } else {
                    //当前节点存储列表，继续dfs
                    dfs(nestedInteger.getList(), list);
                }
            }
        }

        @Override
        public Integer next() {
            return iterator.next();
        }

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }
    }

    /**
     * 栈
     * nestedList中元素逆序入栈，保证nestedList中前面元素优先出栈，保证迭代器遍历顺序
     * 时间复杂度O(n)，空间复杂度O(n)
     */
    static class NestedIterator2 implements Iterator<Integer> {
        //存储nestedList中元素的栈
        private final Deque<NestedInteger> stack;

        public NestedIterator2(List<NestedInteger> nestedList) {
            stack = new LinkedList<>();

            //nestedList中元素逆序入栈，保证nestedList中前面元素优先出栈，保证迭代器遍历顺序
            for (int i = nestedList.size() - 1; i >= 0; i--) {
                NestedInteger nestedInteger = nestedList.get(i);
                stack.offerFirst(nestedInteger);
            }
        }

        @Override
        public Integer next() {
            //直接返回栈顶元素，每次调用next()之前都先调用hasNext()，保证栈顶元素肯定是整数
            return stack.pollFirst().getInteger();
        }

        @Override
        public boolean hasNext() {
            while (!stack.isEmpty()) {
                //栈顶元素
                NestedInteger nestedInteger = stack.peekFirst();

                //当前元素为整数，则存在下一个整数，直接返回true
                if (nestedInteger.isInteger()) {
                    return true;
                }

                //当前元素为列表
                nestedInteger = stack.pollFirst();
                List<NestedInteger> list = nestedInteger.getList();

                //list中元素逆序入栈，保证list中前面元素优先出栈，保证迭代器遍历顺序
                for (int i = list.size() - 1; i >= 0; i--) {
                    stack.offerFirst(list.get(i));
                }
            }

            //栈中元素都遍历完，则不存在下一个整数，直接返回true
            return false;
        }
    }

    /**
     * 自定义NestedInteger的实现类
     * list为空，则表示当前节点存储整数；list不为空，则表示当前节点存储列表
     */
    public static class Node implements NestedInteger {
        //存储整数
        private int value;
        //存储列表，列表中元素可以是整数，也可以是一个列表
        private final List<NestedInteger> list;

        //当前节点存储列表
        public Node() {
            list = new ArrayList<>();
        }

        //当前节点存储整数
        public Node(int value) {
            this.value = value;
            list = new ArrayList<>();
        }

        @Override
        public boolean isInteger() {
            return list.isEmpty();
        }

        @Override
        public Integer getInteger() {
            return value;
        }

        @Override
        public List<NestedInteger> getList() {
            return list;
        }
    }

    public interface NestedInteger {
        /**
         * 当前节点是否是一个整数
         *
         * @return
         */
        public boolean isInteger();

        /**
         * 返回当前节点整数
         *
         * @return
         */
        public Integer getInteger();

        /**
         * 返回当前节点的NestedInteger集合
         *
         * @return
         */
        public List<NestedInteger> getList();
    }
}
