package com.zhang.java;

import java.util.*;

/**
 * @Date 2024/2/13 08:37
 * @Author zsy
 * @Description 考场就座 类比Problem605、Problem849、Problem1437 延迟删除类比Problem480、Problem2034、Problem2349、Problem2353 有序集合类比Problem220、Problem352、Problem363、Problem981、Problem1146、Problem1348、Problem1912、Problem2034、Problem2071、Problem2349、Problem2353、Problem2502、Problem2590 区间类比Problem56、Problem57、Problem163、Problem228、Problem252、Problem253、Problem352、Problem406、Problem435、Problem436、Problem632、Problem763、Problem986、Problem1288、Problem2402
 * 在考场里，有 n 个座位排成一行，编号为 0 到 n - 1。
 * 当学生进入考场后，他必须坐在离最近的人最远的座位上。
 * 如果有多个这样的座位，他会坐在编号最小的座位上。(另外，如果考场里没有人，那么学生就坐在 0 号座位上。)
 * 设计一个模拟所述考场的类。
 * 实现 ExamRoom 类：
 * ExamRoom(int n) 用座位的数量 n 初始化考场对象。
 * int seat() 返回下一个学生将会入座的座位编号。
 * void leave(int p) 指定坐在座位 p 的学生将离开教室。保证座位 p 上会有一位学生。
 * <p>
 * 输入：
 * ["ExamRoom", "seat", "seat", "seat", "seat", "leave", "seat"]
 * [[10], [], [], [], [], [4], []]
 * 输出：
 * [null, 0, 9, 4, 2, null, 5]
 * 解释：
 * ExamRoom examRoom = new ExamRoom(10);
 * examRoom.seat(); // 返回 0，房间里没有人，学生坐在 0 号座位。
 * examRoom.seat(); // 返回 9，学生最后坐在 9 号座位。
 * examRoom.seat(); // 返回 4，学生最后坐在 4 号座位。
 * examRoom.seat(); // 返回 2，学生最后坐在 2 号座位。
 * examRoom.leave(4);
 * examRoom.seat(); // 返回 5，学生最后坐在 5 号座位。
 * <p>
 * 1 <= n <= 10^9
 * 保证有学生正坐在座位 p 上。
 * seat 和 leave 最多被调用 10^4 次。
 */
public class Problem855 {
    public static void main(String[] args) {
        ExamRoom examRoom = new ExamRoom(10);
//        ExamRoom2 examRoom = new ExamRoom2(10);
        // 返回 0，房间里没有人，学生坐在 0 号座位。
        System.out.println(examRoom.seat());
        // 返回 9，学生最后坐在 9 号座位。
        System.out.println(examRoom.seat());
        // 返回 4，学生最后坐在 4 号座位。
        System.out.println(examRoom.seat());
        // 返回 2，学生最后坐在 2 号座位。
        System.out.println(examRoom.seat());
        examRoom.leave(4);
        // 返回 5，学生最后坐在 5 号座位。
        System.out.println(examRoom.seat());
    }

    /**
     * 有序集合
     */
    static class ExamRoom {
        //按照座位下标索引由小到大排序的有序集合
        private final TreeSet<Integer> treeSet;
        private final int n;

        public ExamRoom(int n) {
            treeSet = new TreeSet<>(new Comparator<Integer>() {
                @Override
                public int compare(Integer a, Integer b) {
                    return a - b;
                }
            });

            this.n = n;
        }

        public int seat() {
            //treeSet为空，则学生坐在下标索引为0的座位
            if (treeSet.isEmpty()) {
                treeSet.add(0);
                return 0;
            }

            //当前学生入座的距离最近的人最远的座位下标索引
            int result = 0;
            //result距离最近的人最远距离
            int max = treeSet.first();
            //treeSet中index的前一个座位下标索引
            int preIndex = treeSet.first();

            //由小到大遍历treeSet中的座位index
            for (int index : treeSet) {
                if ((index - preIndex) / 2 > max) {
                    result = preIndex + (index - preIndex) / 2;
                    max = (index - preIndex) / 2;
                }

                preIndex = index;
            }

            //考虑treeSet中最后一个座位到n-1的情况
            if (preIndex != n - 1 && n - 1 - treeSet.last() > max) {
                result = n - 1;
            }

            treeSet.add(result);

            return result;
        }

        public void leave(int p) {
            treeSet.remove(p);
        }
    }

    /**
     * 有序集合+优先队列，大根堆+延迟删除
     */
    static class ExamRoom2 {
        //按照座位下标索引由小到大排序的有序集合
        private final TreeSet<Integer> treeSet;
        //优先队列，大根堆，存放treeSet中相邻两个座位下标索引构成的区间
        //注意：堆中存储有不合法的区间，通过延迟删除堆顶不合法区间
        private final PriorityQueue<int[]> priorityQueue;
        private final int n;

        public ExamRoom2(int n) {
            treeSet = new TreeSet<>(new Comparator<Integer>() {
                @Override
                public int compare(Integer a, Integer b) {
                    return a - b;
                }
            });

            priorityQueue = new PriorityQueue<>(new Comparator<int[]>() {
                @Override
                public int compare(int[] arr1, int[] arr2) {
                    //按照区间中座位到最近座位的最远距离由大到小排序，再按照区间左边界由小到大排序
                    if ((arr1[1] - arr1[0]) / 2 != (arr2[1] - arr2[0]) / 2) {
                        return (arr2[1] - arr2[0]) / 2 - (arr1[1] - arr1[0]) / 2;
                    } else {
                        return arr1[0] - arr2[0];
                    }
                }
            });

            this.n = n;
        }

        public int seat() {
            //treeSet为空，则学生坐在下标索引为0的座位
            if (treeSet.isEmpty()) {
                treeSet.add(0);
                return 0;
            }

            //坐在下标索引为0的座位和treeSet中最小下标索引座位之间的距离
            int left = treeSet.first();
            //坐下标索引为n-1的座位和treeSet中最大下标索引座位之间的距离
            int right = n - 1 - treeSet.last();

            while (treeSet.size() >= 2) {
                int[] arr = priorityQueue.peek();

                //treeSet中不存在当前座位，或者当前区间左边界和右边界不是treeSet中相邻座位，则延迟删除堆顶区间
                if (!treeSet.contains(arr[0]) || !treeSet.contains(arr[1]) || treeSet.higher(arr[0]) != arr[1]) {
                    priorityQueue.poll();
                    continue;
                }

                //当前座位的中间座位不是到最近座位的最大距离，即到最近座位的最大距离在0或n-1，直接跳出循环
                //注意：left是大于等于，right是大于，要保证存在多个相等的最近座位的最大距离时，返回下标索引小的座位
                if (left >= (arr[1] - arr[0]) / 2 || right > (arr[1] - arr[0]) / 2) {
                    break;
                }

                priorityQueue.poll();
                priorityQueue.offer(new int[]{arr[0], arr[0] + (arr[1] - arr[0]) / 2});
                priorityQueue.offer(new int[]{arr[0] + (arr[1] - arr[0]) / 2, arr[1]});
                treeSet.add(arr[0] + (arr[1] - arr[0]) / 2);

                return arr[0] + (arr[1] - arr[0]) / 2;
            }

            //到最近座位的最大距离座位在0
            if (left >= right) {
                priorityQueue.offer(new int[]{0, treeSet.first()});
                treeSet.add(0);
                return 0;
            } else {
                //到最近座位的最大距离座位在n-1
                priorityQueue.offer(new int[]{treeSet.last(), n - 1});
                treeSet.add(n - 1);
                return n - 1;
            }
        }

        public void leave(int p) {
            //座位p有相邻的前后座位，则相邻的前后座位构成的新区间入堆，
            //座位p和相邻的前面座位、座位p和相邻的后面座位构成的老区间，在seat()中延迟删除
            if (treeSet.lower(p) != null && treeSet.higher(p) != null) {
                priorityQueue.offer(new int[]{treeSet.lower(p), treeSet.higher(p)});
            }

            treeSet.remove(p);
        }
    }
}
