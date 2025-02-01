package com.zhang.java;

import java.util.PriorityQueue;

/**
 * @Date 2024/9/25 08:22
 * @Author zsy
 * @Description 座位预约管理系统 类比Problem379、Problem1500、Problem2254、Problem2336
 * 请你设计一个管理 n 个座位预约的系统，座位编号从 1 到 n 。
 * 请你实现 SeatManager 类：
 * SeatManager(int n) 初始化一个 SeatManager 对象，它管理从 1 到 n 编号的 n 个座位。所有座位初始都是可预约的。
 * int reserve() 返回可以预约座位的 最小编号 ，此座位变为不可预约。
 * void unreserve(int seatNumber) 将给定编号 seatNumber 对应的座位变成可以预约。
 * <p>
 * 输入：
 * ["SeatManager", "reserve", "reserve", "unreserve", "reserve", "reserve", "reserve", "reserve", "unreserve"]
 * [[5], [], [], [2], [], [], [], [], [5]]
 * 输出：
 * [null, 1, 2, null, 2, 3, 4, 5, null]
 * 解释：
 * SeatManager seatManager = new SeatManager(5); // 初始化 SeatManager ，有 5 个座位。
 * seatManager.reserve();    // 所有座位都可以预约，所以返回最小编号的座位，也就是 1 。
 * seatManager.reserve();    // 可以预约的座位为 [2,3,4,5] ，返回最小编号的座位，也就是 2 。
 * seatManager.unreserve(2); // 将座位 2 变为可以预约，现在可预约的座位为 [2,3,4,5] 。
 * seatManager.reserve();    // 可以预约的座位为 [2,3,4,5] ，返回最小编号的座位，也就是 2 。
 * seatManager.reserve();    // 可以预约的座位为 [3,4,5] ，返回最小编号的座位，也就是 3 。
 * seatManager.reserve();    // 可以预约的座位为 [4,5] ，返回最小编号的座位，也就是 4 。
 * seatManager.reserve();    // 唯一可以预约的是座位 5 ，所以返回 5 。
 * seatManager.unreserve(5); // 将座位 5 变为可以预约，现在可预约的座位为 [5] 。
 * <p>
 * 1 <= n <= 10^5
 * 1 <= seatNumber <= n
 * 每一次对 reserve 的调用，题目保证至少存在一个可以预约的座位。
 * 每一次对 unreserve 的调用，题目保证 seatNumber 在调用函数前都是被预约状态。
 * 对 reserve 和 unreserve 的调用 总共 不超过 10^5 次。
 */
public class Problem1845 {
    public static void main(String[] args) {
        // 初始化 SeatManager ，有 5 个座位。
        SeatManager seatManager = new SeatManager(5);
        // 所有座位都可以预约，所以返回最小编号的座位，也就是 1 。
        System.out.println(seatManager.reserve());
        // 可以预约的座位为 [2,3,4,5] ，返回最小编号的座位，也就是 2 。
        System.out.println(seatManager.reserve());
        // 将座位 2 变为可以预约，现在可预约的座位为 [2,3,4,5] 。
        seatManager.unreserve(2);
        // 可以预约的座位为 [2,3,4,5] ，返回最小编号的座位，也就是 2 。
        System.out.println(seatManager.reserve());
        // 可以预约的座位为 [3,4,5] ，返回最小编号的座位，也就是 3 。
        System.out.println(seatManager.reserve());
        // 可以预约的座位为 [4,5] ，返回最小编号的座位，也就是 4 。
        System.out.println(seatManager.reserve());
        // 唯一可以预约的是座位 5 ，所以返回 5 。
        System.out.println(seatManager.reserve());
        // 将座位 5 变为可以预约，现在可预约的座位为 [5] 。
        seatManager.unreserve(5);
    }

    /**
     * 优先队列，小根堆
     */
    static class SeatManager {
        //优先队列，小根堆，存放可以重复使用的座位
        //小根堆不为空，则优先使用小根堆堆顶的座位
        private final PriorityQueue<Integer> priorityQueue;
        //下一个座位
        private int nextSeat;

        public SeatManager(int n) {
            priorityQueue = new PriorityQueue<>();
            //初始化最小座位为1
            nextSeat = 1;
        }

        public int reserve() {
            //小根堆为空，则优先使用nextSeat的座位
            if (priorityQueue.isEmpty()) {
                int result = nextSeat;
                nextSeat++;
                return result;
            }

            //小根堆不为空，则优先使用小根堆堆顶的座位
            return priorityQueue.poll();
        }

        public void unreserve(int seatNumber) {
            //seatNumber加入小根堆，作为可以重复使用的座位
            priorityQueue.offer(seatNumber);
        }
    }
}
