package com.zhang.java;

import java.util.*;

/**
 * @Date 2023/9/4 08:24
 * @Author zsy
 * @Description 将数据流变为多个不相交区间 类比Problem362、Problem1146 类比Problem986、Problem2983 有序集合类比Problem220、Problem363、Problem855、Problem1348 区间类比Problem56、Problem57、Problem163、Problem228、Problem252、Problem253、Problem406、Problem435、Problem436、Problem632、Problem763、Problem855、Problem986、Problem1288、Problem2402
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
        //由小到大存储不相交的区间
        private final List<int[]> list;

        public SummaryRanges() {
            list = new ArrayList<>();
        }

        /**
         * 二分查找区间左边界小于等于value的最大区间，作为value的左边区间，value的左边区间的下一个区间作为value的右边区间，
         * 如果不存在左边界小于等于value的最大区间，则二分查找返回左边界大于value的最小区间
         * 二分查找区间左边界小于等于value的最大区间之后，有以下7种情况：
         * 1、value在左边区间左边，且左边区间左边界-1不等于value，则value作为单独区间插入左边区间左侧
         * 2、value在左边区间左边，且左边区间左边界-1等于value，则value和左边区间合并
         * 3、value在左边区间内，则不需要合并和插入，直接返回
         * 4、value在左边区间右边，且左边区间右边界+1等于value，右边区间左边界-1不等于value，则value和左边区间合并
         * 5、value在左边区间右边，且左边区间右边界+1等于value，右边区间左边界-1等于value，则value和左边区间、右边区间合并
         * 6、value在左边区间右边，且左边区间右边界+1不等于value，右边区间左边界-1等于value，则value和右边区间合并
         * 7、value在左边区间右边，且左边区间右边界+1不等于value，右边区间左边界-1不等于value，则value作为单独区间插入左边区间和右边区间之间
         * 时间复杂度O(logn+n)，空间复杂度O(1) (n：list中不相交区间的个数)
         *
         * @param value
         */
        public void addNum(int value) {
            if (list.isEmpty()) {
                list.add(new int[]{value, value});
                return;
            }

            int left = 0;
            int right = list.size() - 1;
            int mid;

            //二分查找区间左边界小于等于value的最大区间
            while (left < right) {
                //mid往右偏移，因为转移条件是right=mid-1，避免无法跳出循环
                mid = left + ((right - left) >> 1) + 1;

                if (list.get(mid)[0] <= value) {
                    left = mid;
                } else {
                    right = mid - 1;
                }
            }

            //左边界小于等于value的最大区间的下标索引，即value左边区间下标索引
            int leftIndex = left;
            //value右边区间下标索引
            int rightIndex;

            //value左边区间为末尾区间，则value右边区间只能为末尾区间
            if (leftIndex == list.size() - 1) {
                rightIndex = leftIndex;
            } else {
                rightIndex = leftIndex + 1;
            }

            //value左边区间，即区间左边界小于等于value的最大区间
            //如果不存在左边界小于等于value的最大区间，则二分查找返回左边界大于value的最小区间
            int[] leftArr = list.get(leftIndex);
            //value右边区间
            int[] rightArr = list.get(rightIndex);

            //情况1、2，value在左边区间左边
            if (value < leftArr[0]) {
                //情况1
                if (value + 1 != leftArr[0]) {
                    list.add(leftIndex, new int[]{value, value});
                } else {
                    //情况2
                    leftArr[0] = value;
                }
            } else if (value <= leftArr[1]) {
                //情况3，value在左边区间内
            } else {
                //情况4、5、6、7，value在左边区间右边

                //情况4
                if (leftArr[1] + 1 == value && value + 1 != rightArr[0]) {
                    leftArr[1] = value;
                } else if (leftArr[1] + 1 == value && value + 1 == rightArr[0]) {
                    //情况5
                    leftArr[1] = rightArr[1];
                    list.remove(rightIndex);
                } else if (leftArr[1] + 1 != value && value + 1 == rightArr[0]) {
                    //情况6
                    rightArr[0] = value;
                } else {
                    //情况7
                    //注意：不能写成list.add(rightIndex, new int[]{value, value});
                    //避免leftIndex等于rightIndex，导致插入区间[value,value]之后list无序
                    list.add(leftIndex + 1, new int[]{value, value});
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
        //有序集合，由小到大存储不相交的区间
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
         * 通过有序集合得到区间左边界小于等于value的最大区间，作为value的左边区间，如果不存在该区间，则返回区间左边界大于等于value的最小区间，
         * 通过有序集合得到区间左边界大于等于value的最小区间，作为value的右边区间，如果不存在该区间，则返回区间左边界小于等于value的最大区间，
         * 通过有序集合得到value的的左右边区间之后，有以下7种情况：
         * 1、value在左边区间左边，且左边区间左边界-1不等于value，则value作为单独区间插入左边区间左侧
         * 2、value在左边区间左边，且左边区间左边界-1等于value，则value和左边区间合并
         * 3、value在左边区间内，则不需要合并和插入，直接返回
         * 4、value在左边区间右边，且左边区间右边界+1等于value，右边区间左边界-1不等于value，则value和左边区间合并
         * 5、value在左边区间右边，且左边区间右边界+1等于value，右边区间左边界-1等于value，则value和左边区间、右边区间合并
         * 6、value在左边区间右边，且左边区间右边界+1不等于value，右边区间左边界-1等于value，则value和右边区间合并
         * 7、value在左边区间右边，且左边区间右边界+1不等于value，右边区间左边界-1不等于value，则value作为单独区间插入左边区间和右边区间之间
         * 时间复杂度O(logn)，空间复杂度O(n) (n：set中不相交区间的个数)
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
            //value左边区间，即区间左边界小于等于value的最大区间，如果不存在该区间，则返回null
            int[] leftArr = set.floor(arr);
            //value右边区间，即区间左边界大于等于value的最小区间，如果不存在该区间，则返回null
            int[] rightArr = set.ceiling(arr);

            //不存在区间左边界小于等于value的最大区间，则leftArr为区间左边界大于等于value的最小区间
            if (leftArr == null) {
                leftArr = set.ceiling(arr);
            }

            //不存在区间左边界大于等于value的最小区间，则rightArr为区间左边界小于等于value的最大区间
            if (rightArr == null) {
                rightArr = set.floor(arr);
            }

            //情况1、2，value在左边区间左边
            if (value < leftArr[0]) {
                //情况1
                if (value + 1 != leftArr[0]) {
                    set.add(arr);
                } else {
                    //情况2
                    leftArr[0] = value;
                }
            } else if (value <= leftArr[1]) {
                //情况3，value在左边区间内
            } else {
                //情况4、5、6、7，value在左边区间右边

                //情况4
                if (leftArr[1] + 1 == value && value + 1 != rightArr[0]) {
                    leftArr[1] = value;
                } else if (leftArr[1] + 1 == value && value + 1 == rightArr[0]) {
                    //情况5
                    leftArr[1] = rightArr[1];
                    set.remove(rightArr);
                } else if (leftArr[1] + 1 != value && value + 1 == rightArr[0]) {
                    //情况6
                    rightArr[0] = value;
                } else {
                    //情况7
                    set.add(arr);
                }
            }
        }

        public int[][] getIntervals() {
            return set.toArray(new int[set.size()][2]);
        }
    }
}
