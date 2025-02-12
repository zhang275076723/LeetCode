package com.zhang.java;

import java.util.*;

/**
 * @Date 2025/3/27 08:55
 * @Author zsy
 * @Description 坐上公交的最晚时间 双指针类比2410 二分查找类比
 * 给你一个下标从 0 开始长度为 n 的整数数组 buses ，其中 buses[i] 表示第 i 辆公交车的出发时间。
 * 同时给你一个下标从 0 开始长度为 m 的整数数组 passengers ，其中 passengers[j] 表示第 j 位乘客的到达时间。
 * 所有公交车出发的时间互不相同，所有乘客到达的时间也互不相同。
 * 给你一个整数 capacity ，表示每辆公交车 最多 能容纳的乘客数目。
 * 每位乘客都会排队搭乘下一辆有座位的公交车。
 * 如果你在 y 时刻到达，公交在 x 时刻出发，满足 y <= x  且公交没有满，那么你可以搭乘这一辆公交。
 * 最早 到达的乘客优先上车。
 * 返回你可以搭乘公交车的最晚到达公交站时间。你 不能 跟别的乘客同时刻到达。
 * 注意：数组 buses 和 passengers 不一定是有序的。
 * <p>
 * 输入：buses = [10,20], passengers = [2,17,18,19], capacity = 2
 * 输出：16
 * 解释：
 * 第 1 辆公交车载着第 1 位乘客。
 * 第 2 辆公交车载着你和第 2 位乘客。
 * 注意你不能跟其他乘客同一时间到达，所以你必须在第二位乘客之前到达。
 * <p>
 * 输入：buses = [20,30,10], passengers = [19,13,26,4,25,11,21], capacity = 2
 * 输出：20
 * 解释：
 * 第 1 辆公交车载着第 4 位乘客。
 * 第 2 辆公交车载着第 6 位和第 2 位乘客。
 * 第 3 辆公交车载着第 1 位乘客和你。
 * <p>
 * n == buses.length
 * m == passengers.length
 * 1 <= n, m, capacity <= 10^5
 * 2 <= buses[i], passengers[i] <= 10^9
 * buses 中的元素 互不相同 。
 * passengers 中的元素 互不相同 。
 */
public class Problem2332 {
    public static void main(String[] args) {
        Problem2332 problem2332 = new Problem2332();
//        int[] buses = {20, 30, 10};
//        int[] passengers = {19, 13, 26, 4, 25, 11, 21};
//        int capacity = 2;
//        int[] buses = {3, 5, 7};
//        int[] passengers = {2, 3, 5};
//        int capacity = 2;
        int[] buses = {18, 8, 3, 12, 9, 2, 7, 13, 20, 5};
        int[] passengers = {13, 10, 8, 4, 12, 14, 18, 19, 5, 2, 30, 34};
        int capacity = 1;
        System.out.println(problem2332.latestTimeCatchTheBus(buses, passengers, capacity));
        System.out.println(problem2332.latestTimeCatchTheBus2(buses, passengers, capacity));
    }

    /**
     * 二分查找变形
     * buses和passengers由小到大排序，对[left,right]进行二分查找，left为1，right为buses[buses.length-1]，判断mid时间能否坐上公交车，
     * 如果mid时间能坐上公交车，则最晚到达公交站的时间在mid或mid右边，left=mid；
     * 如果mid时间不能坐上公交车，则最晚到达公交站的时间在mid左边，right=mid-1
     * 注意：最晚到达公交站的时间不能和其他乘客的到达时间相同，如果二分得到的结果在passengers中，
     * 则最晚到达公交站的时间为不在passengers中小于二分得到的结果的最大值
     * 时间复杂度O(mlogm+(nlogn+m+n)*log(buses[buses.length-1]-1)+n)=O(mlogm+nlogn)，空间复杂度O(logm+logn+n) (m=buses.length，n=passengers.length)
     *
     * @param buses
     * @param passengers
     * @param capacity
     * @return
     */
    public int latestTimeCatchTheBus(int[] buses, int[] passengers, int capacity) {
        //buses由小到大排序
        Arrays.sort(buses);

        int left = 1;
        int right = buses[buses.length - 1];
        int mid;

        while (left < right) {
            mid = left + ((right - left) >> 1) + 1;

            List<Integer> passengersList = new ArrayList<>();
            passengersList.add(mid);

            for (int passenger : passengers) {
                passengersList.add(passenger);
            }

            //passengersList由小到大排序
            passengersList.sort(new Comparator<Integer>() {
                @Override
                public int compare(Integer a, Integer b) {
                    return a - b;
                }
            });

            //mid时间能坐上公交车，则最晚到达公交站的时间在mid或mid右边，left=mid
            if (canArrive(buses, passengersList, capacity, mid)) {
                left = mid;
            } else {
                //mid时间不能坐上公交车，则最晚到达公交站的时间在mid左边，right=mid-1
                right = mid - 1;
            }
        }

        Set<Integer> set = new HashSet<>();

        for (int passenger : passengers) {
            set.add(passenger);
        }

        //注意：最晚到达公交站的时间不能和其他乘客的到达时间相同，如果二分得到的结果在passengers中，
        //则最晚到达公交站的时间为不在passengers中小于二分得到的结果的最大值
        while (set.contains(left)) {
            left--;
        }

        return left;
    }

    /**
     * 排序+双指针
     * buses和passengers由小到大排序，遍历排序后的buses，找最后一个上车的乘客，
     * 最后一个上车的乘客从后往前找和其他乘客到达时间不同的时间即为最晚到达公交站的时间
     * 时间复杂度O(mlogm+nlogn)，空间复杂度O(logm+logn) (m=buses.length，n=passengers.length)
     *
     * @param buses
     * @param passengers
     * @param capacity
     * @return
     */
    public int latestTimeCatchTheBus2(int[] buses, int[] passengers, int capacity) {
        //buses和passengers由小到大排序
        Arrays.sort(buses);
        Arrays.sort(passengers);

        //当前公交车上车的乘客
        int count = 0;
        //passengers指针
        int index = 0;

        for (int bus : buses) {
            count = 0;

            //乘客到达时间小于等于当前公交车发车时间，则当前乘客可以上车
            while (count < capacity && index < passengers.length && passengers[index] <= bus) {
                count++;
                index++;
            }
        }

        //减1找到最后一个上车的乘客
        index--;
        //最晚到达公交站的时间
        int lastTime;

        //最后一辆公交车没有坐满，则最晚到达公交站的时间为最后一辆公交车出发时间
        if (count < capacity) {
            lastTime = buses[buses.length - 1];
        } else {
            //最后一辆公交车坐满，则最晚到达公交站的时间为最后一个上车的乘客的到达时间
            lastTime = passengers[index];
        }

        //lastTime不能和其他乘客的到达时间相同，则从后往前找没有其他乘客的到达时间
        while (index >= 0 && lastTime == passengers[index]) {
            lastTime--;
            index--;
        }

        return lastTime;
    }

    /**
     * time时间能否坐上公交车
     * 时间复杂度O(m+n)，空间复杂度O(1) (m=buses.length，n=passengersList.size())
     *
     * @param buses
     * @param passengersList
     * @param capacity
     * @param time
     * @return
     */
    private boolean canArrive(int[] buses, List<Integer> passengersList, int capacity, int time) {
        //当前公交车上车的乘客
        int count;
        //passengersList指针
        int index = 0;

        for (int bus : buses) {
            count = 0;

            //乘客到达时间小于等于当前公交车发车时间，则当前乘客可以上车
            while (count < capacity && index < passengersList.size() && passengersList.get(index) <= bus) {
                //time时间能坐上公交车，返回true
                if (passengersList.get(index) == time) {
                    return true;
                }

                count++;
                index++;
            }
        }

        //遍历结束，则time时间不能坐上公交车，返回false
        return false;
    }
}
