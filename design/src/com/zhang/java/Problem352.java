package com.zhang.java;

import java.util.*;

/**
 * @Date 2023/9/4 08:24
 * @Author zsy
 * @Description 将数据流变为多个不相交区间 类比Problem986、Problem2502、Problem2983 区间类比Problem56、Problem57、Problem163、Problem228、Problem252、Problem253、Problem406、Problem435、Problem436、Problem632、Problem763、Problem855、Problem986、Problem1288、Problem2402 有序集合类比Problem220、Problem363、Problem855、Problem981、Problem1146、Problem1348、Problem1912、Problem2034、Problem2071、Problem2349、Problem2353、Problem2502、Problem2590
 * 给你一个由非负整数 a1, a2, ..., an 组成的数据流输入，请你将到目前为止看到的数字总结为不相交的区间列表。
 * 实现 SummaryRanges 类：
 * SummaryRanges() 使用一个空数据流初始化对象。
 * void addNum(int val) 向数据流中加入整数 val 。
 * int[][] getIntervals() 以不相交区间 [starti, endi] 的列表形式返回对数据流中整数的总结。
 * <p>
 * 输入：
 * ["SummaryRanges", "addNum", "getIntervals", "addNum", "getIntervals", "addNum", "getIntervals", "addNum", "getIntervals", "addNum", "getIntervals"]
 * [[], [1], [], [3], [], [7], [], [2], [], [6], []]
 * 输出：
 * [null, null, [[1, 1]], null, [[1, 1], [3, 3]], null, [[1, 1], [3, 3], [7, 7]], null, [[1, 3], [7, 7]], null, [[1, 3], [6, 7]]]
 * 解释：
 * SummaryRanges summaryRanges = new SummaryRanges();
 * summaryRanges.addNum(1);      // arr = [1]
 * summaryRanges.getIntervals(); // 返回 [[1, 1]]
 * summaryRanges.addNum(3);      // arr = [1, 3]
 * summaryRanges.getIntervals(); // 返回 [[1, 1], [3, 3]]
 * summaryRanges.addNum(7);      // arr = [1, 3, 7]
 * summaryRanges.getIntervals(); // 返回 [[1, 1], [3, 3], [7, 7]]
 * summaryRanges.addNum(2);      // arr = [1, 2, 3, 7]
 * summaryRanges.getIntervals(); // 返回 [[1, 3], [7, 7]]
 * summaryRanges.addNum(6);      // arr = [1, 2, 3, 6, 7]
 * summaryRanges.getIntervals(); // 返回 [[1, 3], [6, 7]]
 * <p>
 * 0 <= val <= 10^4
 * 最多调用 addNum 和 getIntervals 方法 3 * 10^4 次
 */
public class Problem352 {
    public static void main(String[] args) {
//        SummaryRanges summaryRanges = new SummaryRanges();
        SummaryRanges2 summaryRanges = new SummaryRanges2();
        // arr = [1]
        summaryRanges.addNum(1);
        // 返回 [[1, 1]]
        System.out.println(Arrays.deepToString(summaryRanges.getIntervals()));
        // arr = [1, 3]
        summaryRanges.addNum(3);
        // 返回 [[1, 1], [3, 3]]
        System.out.println(Arrays.deepToString(summaryRanges.getIntervals()));
        // arr = [1, 3, 7]
        summaryRanges.addNum(7);
        // 返回 [[1, 1], [3, 3], [7, 7]]
        System.out.println(Arrays.deepToString(summaryRanges.getIntervals()));
        // arr = [1, 2, 3, 7]
        summaryRanges.addNum(2);
        // 返回 [[1, 3], [7, 7]]
        System.out.println(Arrays.deepToString(summaryRanges.getIntervals()));
        // arr = [1, 2, 3, 6, 7]
        summaryRanges.addNum(6);
        // 返回 [[1, 3], [6, 7]]
        System.out.println(Arrays.deepToString(summaryRanges.getIntervals()));
    }

    /**
     * 二分查找
     */
    static class SummaryRanges {
        //按照区间左边界由小到大存储不相交的区间
        private final List<int[]> list;

        public SummaryRanges() {
            list = new ArrayList<>();
        }

        /**
         * 二分查找list中区间左边界小于等于value的最大区间，作为value的左边区间，value的左边区间的下一个区间作为value的右边区间，
         * 然后根据value的左边和右边区间进行插入和合并
         * 时间复杂度O(logn+n)，空间复杂度O(1) (n=list.size())
         *
         * @param value
         */
        public void addNum(int value) {
            if (list.isEmpty()) {
                list.add(new int[]{value, value});
                return;
            }

            //list中左边界小于等于value的最大区间在list中的下标索引
            int leftIndex = -1;
            //list中leftIndex的下一个区间下标索引
            int rightIndex = -1;
            int left = 0;
            int right = list.size() - 1;
            int mid;

            //二分查找区间左边界小于等于value的最大区间
            while (left <= right) {
                mid = left + ((right - left) >> 1);

                if (list.get(mid)[0] <= value) {
                    leftIndex = mid;
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }

            if (leftIndex != list.size() - 1) {
                rightIndex = leftIndex + 1;
            }

            //list中左边界小于等于value的最大区间
            int[] leftArr = leftIndex == -1 ? null : list.get(leftIndex);
            //list中leftArr的下一个区间
            int[] rightArr = rightIndex == -1 ? null : list.get(rightIndex);

            //方法一开始就考虑了当前情况，即当前情况不会发生，则不用操作
            if (leftArr == null && rightArr == null) {

            } else if (leftArr == null) {
                if (value + 1 < rightArr[0]) {
                    list.add(rightIndex, new int[]{value, value});
                } else if (value + 1 == rightArr[0]) {
                    rightArr[0] = value;
                } else {
                    //value在rightArr中，则不用操作
                }
            } else if (rightArr == null) {
                if (leftArr[1] + 1 < value) {
                    list.add(leftIndex + 1, new int[]{value, value});
                } else if (leftArr[1] + 1 == value) {
                    leftArr[1] = value;
                } else {
                    //value在leftArr中，则不用操作
                }
            } else {
                //value在leftArr中，则不用操作
                if (leftArr[1] >= value) {

                } else {
                    if (leftArr[1] + 1 == value && value + 1 == rightArr[0]) {
                        leftArr[1] = rightArr[1];
                        list.remove(rightIndex);
                    } else if (leftArr[1] + 1 == value && value + 1 != rightArr[0]) {
                        leftArr[1] = value;
                    } else if (leftArr[1] + 1 != value && value + 1 == rightArr[0]) {
                        rightArr[0] = value;
                    } else {
                        list.add(rightIndex, new int[]{value, value});
                    }
                }
            }
        }

        public int[][] getIntervals() {
            return list.toArray(new int[list.size()][2]);
        }
    }

    /**
     * 有序集合
     */
    static class SummaryRanges2 {
        //有序集合，按照区间左边界由小到大存储不相交的区间
        private final TreeSet<int[]> set;

        public SummaryRanges2() {
            //按照区间左边界由小到大排序
            set = new TreeSet<>(new Comparator<int[]>() {
                @Override
                public int compare(int[] arr1, int[] arr2) {
                    return arr1[0] - arr2[0];
                }
            });
        }

        /**
         * 通过有序集合得到set中区间左边界小于等于value的最大区间，作为value的左边区间，value的左边区间的下一个区间作为value的右边区间，
         * 然后根据value的左边和右边区间进行插入和合并
         * 时间复杂度O(logn)，空间复杂度O(1) (n=set.size())
         *
         * @param value
         */
        public void addNum(int value) {
            if (set.isEmpty()) {
                set.add(new int[]{value, value});
                return;
            }

            //[value,value]作为一个新区间
            int[] arr = {value, value};
            //set中左边界小于等于value的最大区间
            int[] leftArr = set.floor(arr);
            //set中leftArr的下一个区间
            int[] rightArr;

            if (leftArr == null) {
                rightArr = set.first();
            } else {
                rightArr = set.higher(leftArr);
            }

            //方法一开始就考虑了当前情况，即当前情况不会发生，则不用操作
            if (leftArr == null && rightArr == null) {

            } else if (leftArr == null) {
                if (value + 1 < rightArr[0]) {
                    set.add(arr);
                } else if (value + 1 == rightArr[0]) {
                    rightArr[0] = value;
                } else {
                    //value在rightArr中，则不用操作
                }
            } else if (rightArr == null) {
                if (leftArr[1] + 1 < value) {
                    set.add(arr);
                } else if (leftArr[1] + 1 == value) {
                    leftArr[1] = value;
                } else {
                    //value在leftArr中，则不用操作
                }
            } else {
                if (leftArr[1] >= value) {
                    //value在leftArr中，则不用操作
                } else {
                    if (leftArr[1] + 1 == value && value + 1 == rightArr[0]) {
                        leftArr[1] = rightArr[1];
                        set.remove(rightArr);
                    } else if (leftArr[1] + 1 == value && value + 1 != rightArr[0]) {
                        leftArr[1] = value;
                    } else if (leftArr[1] + 1 != value && value + 1 == rightArr[0]) {
                        rightArr[0] = value;
                    } else {
                        set.add(arr);
                    }
                }
            }
        }

        public int[][] getIntervals() {
            return set.toArray(new int[set.size()][2]);
        }
    }
}
