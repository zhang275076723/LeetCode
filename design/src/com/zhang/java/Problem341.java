package com.zhang.java;

import java.util.*;

/**
 * @Date 2023/11/12 08:52
 * @Author zsy
 * @Description 扁平化嵌套列表迭代器 类比Problem385 迭代器类比Problem173、Problem251、Problem281、Problem284、Problem604、Problem900、Problem1286、Problem1586 栈类比Problem20、Problem71、Problem150、Problem224、Problem227、Problem331、Problem394、Problem678、Problem856、Problem946、Problem1003、Problem1047、Problem1096、Offer31、CharacterToInteger
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
//        //[[1,1],2,[1,1]]
//        List<NestedInteger> nestedList = new ArrayList<NestedInteger>(){{
//            add(new Node(new ArrayList<NestedInteger>(){{
//                add(new Node(1));
//                add(new Node(1));
//            }}));
//            add(new Node(2));
//            add(new Node(new ArrayList<NestedInteger>(){{
//                add(new Node(1));
//                add(new Node(1));
//            }}));
//        }};
        //[[1,2],3,[4,[5,6]]]
        List<NestedInteger> nestedList = new ArrayList<NestedInteger>() {{
            add(new Node(new ArrayList<NestedInteger>() {{
                add(new Node(1));
                add(new Node(2));
            }}));
            add(new Node(3));
            add(new Node(new ArrayList<NestedInteger>() {{
                add(new Node(4));
                add(new Node(new ArrayList<NestedInteger>() {{
                    add(new Node(5));
                    add(new Node(6));
                }}));
            }}));
        }};
//        NestedIterator nestedIterator = new NestedIterator(nestedList);
        NestedIterator2 nestedIterator = new NestedIterator2(nestedList);
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

            for (NestedInteger nestedInteger : nestedList) {
                dfs(nestedInteger);
            }

            iterator = list.iterator();
        }

        private void dfs(NestedInteger nestedInteger) {
            //当前节点存储整数，加入list中
            if (nestedInteger.isInteger()) {
                list.add(nestedInteger.getInteger());
                return;
            }

            //当前节点存储列表，继续dfs
            for (NestedInteger nestedInteger2 : nestedInteger.getList()) {
                dfs(nestedInteger2);
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

            for (NestedInteger nestedInteger : nestedList) {
                stack.offerLast(nestedInteger);
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
                //当前元素为整数，则存在下一个整数，直接返回true
                if (stack.peekFirst().isInteger()) {
                    return true;
                }

                //当前元素为列表
                NestedInteger nestedInteger = stack.pollFirst();

                //list中元素从后往前遍历入栈，保证list中前面元素优先出栈，保证迭代器遍历顺序
                for (int i = nestedInteger.getList().size() - 1; i >= 0; i--) {
                    stack.offerFirst(nestedInteger.getList().get(i));
                }
            }

            //栈中元素都遍历完，则不存在下一个整数，直接返回false
            return false;
        }
    }

    /**
     * 自定义NestedInteger的实现类
     * list为空，则表示当前节点存储整数；list不为空，则表示当前节点存储列表
     */
    public static class Node implements NestedInteger {
        //存储的整形必须为Integer，不能是int，通过value是否为空，判断当前节点存储的是整形还是列表
        private Integer value;
        //存储列表，列表中元素可以是整数，也可以是一个列表
        private final List<NestedInteger> list;

        //当前节点存储列表
        public Node(List<NestedInteger> list) {
            value = null;
            this.list = list;
        }

        //当前节点存储整数
        public Node(int value) {
            this.value = value;
            list = new ArrayList<>();
        }

        @Override
        public boolean isInteger() {
            return value != null;
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
         * 当前节点是否是存储整数
         *
         * @return
         */
        public boolean isInteger();

        /**
         * 返回当前节点存储的整数
         *
         * @return
         */
        public Integer getInteger();

        /**
         * 返回当前节点存储的NestedInteger集合
         *
         * @return
         */
        public List<NestedInteger> getList();
    }
}
