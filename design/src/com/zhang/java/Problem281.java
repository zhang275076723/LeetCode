package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2023/11/10 08:50
 * @Author zsy
 * @Description 锯齿迭代器 类比Problem6 迭代器类比Problem173、Problem251、Problem284、Problem341、Problem1586
 * 给出两个一维的向量，请你实现一个迭代器，交替返回它们中间的元素。
 * 输入:
 * v1 = [1,2]
 * v2 = [3,4,5,6]
 * 输出: [1,3,2,4,5,6]
 * 解析: 通过连续调用 next 函数直到 hasNext 函数返回 false，next 函数返回值的次序应依次为: [1,3,2,4,5,6]。
 * 拓展：假如给你 k 个一维向量呢？你的代码在这种情况下的扩展性又会如何呢?
 * 拓展声明：
 * “锯齿” 顺序对于 k > 2 的情况定义可能会有些歧义。所以，假如你觉得 “锯齿” 这个表述不妥，也可以认为这是一种 “循环”。例如：
 * 输入:
 * [1,2,3]
 * [4,5,6,7]
 * [8,9]
 * 输出: [1,4,8,2,5,9,3,6,7].
 */
public class Problem281 {
    public static void main(String[] args) {
        List<Integer> v1 = new ArrayList<Integer>() {{
            add(1);
            add(2);
        }};
        List<Integer> v2 = new ArrayList<Integer>() {{
            add(3);
            add(4);
            add(5);
            add(6);
        }};
        ZigzagIterator zigzagIterator = new ZigzagIterator(v1, v2);
        while (zigzagIterator.hasNext()) {
            System.out.print(zigzagIterator.next() + " ");
        }

        System.out.println();

        List<Integer>[] listArr = new List[3];
        listArr[0] = new ArrayList<Integer>() {{
            add(1);
            add(2);
            add(3);
        }};
        listArr[1] = new ArrayList<Integer>() {{
            add(4);
            add(5);
            add(6);
            add(7);
        }};
        listArr[2] = new ArrayList<Integer>() {{
            add(8);
            add(9);
        }};
        ZigzagIterator2 zigzagIterator2 = new ZigzagIterator2(listArr);
        while (zigzagIterator2.hasNext()) {
            System.out.print(zigzagIterator2.next() + " ");
        }
    }

    /**
     * 双指针
     * 双指针记录v1和v2当前遍历到的元素下标索引，index记录下一个元素应该从哪个list中获取
     */
    static class ZigzagIterator {
        //存储v1元素的集合
        private List<Integer> list1;
        //存储v2元素的集合
        private List<Integer> list2;
        //v1当前遍历到的元素下标索引
        private int i;
        //v2当前遍历到的元素下标索引
        private int j;
        //下一个元素所在list的下标索引，index为0，则下一个元素在list1中；index为1，则下一个元素在list2中
        private int index;

        public ZigzagIterator(List<Integer> v1, List<Integer> v2) {
            list1 = v1;
            list2 = v2;
            i = 0;
            j = 0;
            index = 0;
        }

        public int next() {
            int value = -1;

            if (index == 0) {
                value = list1.get(i);
                i++;
                index = 1;
            } else {
                value = list2.get(j);
                j++;
                index = 0;
            }

            return value;
        }

        /**
         * 是否存在下一个元素，如果存在，index指向下一个元素所在的list，返回true；如果不存在，返回false
         *
         * @return
         */
        public boolean hasNext() {
            if (i == list1.size()) {
                index = 1;
            } else if (j == list2.size()) {
                index = 0;
            }

            //list1中元素没有遍历完，或者list2中元素没有遍历完，则存在下一个元素
            return i != list1.size() || j != list2.size();
        }
    }

    /**
     * 多指针
     * 每个指针分别记录每个list当前遍历到的元素下标索引，index记录下一个元素应该从哪个list中获取
     */
    static class ZigzagIterator2 {
        //存储list的集合
        private List<List<Integer>> result;
        //存储每个list当前遍历到的元素下标索引
        private List<Integer> indexList;
        //下一个元素所在list的下标索引，index为0，则下一个元素在list1中；index为1，则下一个元素在list2中
        private int index;
        //list集合的数量
        private final int size;

        public ZigzagIterator2(List<Integer>[] listArr) {
            result = new ArrayList<>();
            indexList = new ArrayList<>();
            index = 0;
            size = listArr.length;

            for (int i = 0; i < size; i++) {
                result.add(listArr[i]);
                indexList.add(0);
            }
        }

        public int next() {
            //下一个元素所在的list
            List<Integer> list = result.get(index);
            //下一个元素在list中的下标索引
            int i = indexList.get(index);
            //下一个元素
            int value = list.get(i);
            //list中下一个元素下标索引后移一位
            indexList.set(index, i + 1);
            //index指向下一个元素所在的list下标索引
            index = (index + 1) % size;
            return value;
        }

        /**
         * 是否存在下一个元素，如果存在，index指向下一个元素所在的list和list中的下标索引i，返回true；如果不存在，返回false
         *
         * @return
         */
        public boolean hasNext() {
            //下一个元素所在的list下标索引
            int startIndex = index;

            //当前list中元素已经遍历完，判断下一个list中元素是否已经遍历完
            while (result.get(index).size() == indexList.get(index)) {
                index = (index + 1) % size;

                //所有的list中元素都已经遍历完，index又重新回到startIndex，则不存在下一个元素，返回false
                if (index == startIndex) {
                    return false;
                }
            }

            //只要有一个list中元素没有遍历完，则存在下一个元素，返回true
            return true;
        }
    }
}
