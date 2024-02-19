package com.zhang.java;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

/**
 * @Date 2024/2/13 08:37
 * @Author zsy
 * @Description 考场就座 类比Problem849 有序集合类比Problem220、Problem352 区间类比
 * 在考场里，一排有 N 个座位，分别编号为 0, 1, 2, ..., N-1 。
 * 当学生进入考场后，他必须坐在能够使他与离他最近的人之间的距离达到最大化的座位上。
 * 如果有多个这样的座位，他会坐在编号最小的座位上。
 * (另外，如果考场里没有人，那么学生就坐在 0 号座位上。)
 * 返回 ExamRoom(int N) 类，它有两个公开的函数：
 * 其中，函数 ExamRoom.seat() 会返回一个 int （整型数据），代表学生坐的位置；
 * 函数 ExamRoom.leave(int p) 代表坐在座位 p 上的学生现在离开了考场。
 * 每次调用 ExamRoom.leave(p) 时都保证有学生坐在座位 p 上。
 * <p>
 * 输入：["ExamRoom","seat","seat","seat","seat","leave","seat"], [[10],[],[],[],[],[4],[]]
 * 输出：[null,0,9,4,2,null,5]
 * 解释：
 * ExamRoom(10) -> null
 * seat() -> 0，没有人在考场里，那么学生坐在 0 号座位上。
 * seat() -> 9，学生最后坐在 9 号座位上。
 * seat() -> 4，学生最后坐在 4 号座位上。
 * seat() -> 2，学生最后坐在 2 号座位上。
 * leave(4) -> null
 * seat() -> 5，学生最后坐在 5 号座位上。
 * <p>
 * 1 <= N <= 10^9
 * 在所有的测试样例中 ExamRoom.seat() 和 ExamRoom.leave() 最多被调用 10^4 次。
 * 保证在调用 ExamRoom.leave(p) 时有学生正坐在座位 p 上。
 */
public class Problem855 {
    public static void main(String[] args) {
        ExamRoom examRoom = new ExamRoom(10);
        //没有人在考场里，那么学生坐在 0 号座位上。
        System.out.println(examRoom.seat());
        //学生最后坐在 9 号座位上。
        System.out.println(examRoom.seat());
        //学生最后坐在 4 号座位上。
        System.out.println(examRoom.seat());
        //学生最后坐在 2 号座位上。
        System.out.println(examRoom.seat());
        examRoom.leave(4);
        //学生最后坐在 5 号座位上。
        System.out.println(examRoom.seat());
    }

    /**
     * 有序集合+哈希表
     */
    static class ExamRoom {
        //n个座位
        private final int n;
        //有序集合，存储空闲座位的区间，先按照区间大小由大到小排序，再按照区间左边界由小到大排序
        private final TreeSet<int[]> set;
        //key：当前学生下标索引，value：当前学生左边相邻的学生下标索引
        private final Map<Integer, Integer> leftMap;
        //key：当前学生下标索引，value：当前学生右边相邻的学生下标索引
        private final Map<Integer, Integer> rightMap;

        public ExamRoom(int n) {
            this.n = n;

            //先按照区间大小由大到小排序，再按照区间左边界由小到大排序
            set = new TreeSet<>(new Comparator<int[]>() {
                @Override
                public int compare(int[] arr1, int[] arr2) {
                    //arr1中距离arr1左右边界的最近边界的最大距离
                    int distance1;
                    //arr1中距离arr1左右边界的最近边界的最大距离
                    int distance2;

                    //arr1不存在左边界的情况
                    if (arr1[0] == -1) {
                        distance1 = arr1[1];
                    } else if (arr1[1] == n) {
                        //arr1不存在右边界的情况
                        distance1 = n - arr1[0] - 1;
                    } else {
                        distance1 = (arr1[1] - arr1[0]) / 2;
                    }

                    //arr2不存在左边界的情况
                    if (arr2[0] == -1) {
                        distance2 = arr2[1];
                    } else if (arr2[1] == n) {
                        //arr2不存在右边界的情况
                        distance2 = n - arr2[0] - 1;
                    } else {
                        distance2 = (arr2[1] - arr2[0]) / 2;
                    }

                    if (distance1 != distance2) {
                        return distance2 - distance1;
                    } else {
                        return arr1[0] - arr2[0];
                    }
                }
            });

            leftMap = new HashMap<>();
            rightMap = new HashMap<>();

            //初始化set
            set.add(new int[]{-1, n});
        }

        /**
         * 从set中获取区间大小最大，并且左边界最小的区间，取区间中间下标索引作为当前学生选择的座位，
         * 当前区间从set中移除，同时移除leftMap和rightMap，并且两个新区间加入set，同时加入leftMap和rightMap
         * 时间复杂度O(logn)，空间复杂度O(logn)
         *
         * @return
         */
        public int seat() {
            //从set中获取区间大小最大，并且左边界最小的区间
            int[] arr = set.first();

            //当前学生选择的座位下标索引
            int index;

            if (arr[0] == -1) {
                index = 0;
            } else if (arr[1] == n) {
                index = n - 1;
            } else {
                index = (arr[0] + arr[1]) / 2;
            }

            //arr从set中移除，同时移除leftMap和rightMap
            set.remove(arr);
            leftMap.remove(arr[1]);
            rightMap.remove(arr[0]);

            //两个新区间加入set，同时加入leftMap和rightMap
            set.add(new int[]{arr[0], index});
            leftMap.put(index, arr[0]);
            rightMap.put(arr[0], index);

            set.add(new int[]{index, arr[1]});
            leftMap.put(arr[1], index);
            rightMap.put(index, arr[1]);

            return index;
        }

        /**
         * 通过leftMap和rightMap获取当前学生在set中的前一个区间和后一个区间，这两个区间从set中移除，同时移除leftMap和rightMap，
         * 并且新区间加入set，同时加入leftMap和rightMap
         * 时间复杂度O(logn)，空间复杂度O(logn)
         *
         * @param p
         */
        public void leave(int p) {
            //当前学生左边相邻的学生下标索引
            int leftIndex = leftMap.get(p);
            //当前学生右边相邻的学生下标索引
            int rightIndex = rightMap.get(p);

            //这两个区间从set中移除，同时移除leftMap和rightMap
            set.remove(new int[]{leftIndex, p});
            leftMap.remove(p);
            rightMap.remove(leftIndex);

            set.remove(new int[]{p, rightIndex});
            leftMap.remove(rightIndex);
            rightMap.remove(p);

            //新区间加入set，同时加入leftMap和rightMap
            set.add(new int[]{leftIndex, rightIndex});
            leftMap.put(rightIndex, leftIndex);
            rightMap.put(leftIndex, rightIndex);
        }
    }
}
