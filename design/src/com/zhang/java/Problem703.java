package com.zhang.java;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @Date 2023/4/4 08:05
 * @Author zsy
 * @Description 数据流中的第 K 大元素 Amazon面试题 类比Problem215、Problem347、Problem451、Problem692、Offer40
 * 设计一个找到数据流中第 k 大元素的类（class）。注意是排序后的第 k 大元素，不是第 k 个不同的元素。
 * 请实现 KthLargest 类：
 * KthLargest(int k, int[] nums) 使用整数 k 和整数流 nums 初始化对象。
 * int add(int val) 将 val 插入数据流 nums 后，返回当前数据流中第 k 大的元素。
 * <p>
 * 输入：
 * ["KthLargest", "add", "add", "add", "add", "add"]
 * [[3, [4, 5, 8, 2]], [3], [5], [10], [9], [4]]
 * 输出：
 * [null, 4, 5, 5, 8, 8]
 * 解释：
 * KthLargest kthLargest = new KthLargest(3, [4, 5, 8, 2]);
 * kthLargest.add(3);   // return 4
 * kthLargest.add(5);   // return 5
 * kthLargest.add(10);  // return 5
 * kthLargest.add(9);   // return 8
 * kthLargest.add(4);   // return 8
 * <p>
 * 1 <= k <= 10^4
 * 0 <= nums.length <= 10^4
 * -10^4 <= nums[i] <= 10^4
 * -10^4 <= val <= 10^4
 * 最多调用 add 方法 10^4 次
 * 题目数据保证，在查找第 k 大元素时，数组中至少有 k 个元素
 */
public class Problem703 {
    public static void main(String[] args) {
        int[] nums = {4, 5, 8, 2};
        int k = 3;
//        KthLargest kthLargest = new KthLargest(k, nums);
        KthLargest2 kthLargest = new KthLargest2(k, nums);
        //4
        System.out.println(kthLargest.add(3));
        //5
        System.out.println(kthLargest.add(5));
        //5
        System.out.println(kthLargest.add(10));
        //8
        System.out.println(kthLargest.add(9));
        //8
        System.out.println(kthLargest.add(4));
    }

    /**
     * 小根堆
     */
    static class KthLargest {
        //小根堆，优先队列
        private final PriorityQueue<Integer> queue;
        //小根堆大小
        private final int k;

        /**
         * 时间复杂度O(nlogk)，空间复杂度O(logk)
         *
         * @param k
         * @param nums
         */
        public KthLargest(int k, int[] nums) {
            this.k = k;
            queue = new PriorityQueue<>(k, new Comparator<Integer>() {
                @Override
                public int compare(Integer a, Integer b) {
                    return a - b;
                }
            });

            for (int i = 0; i < nums.length; i++) {
                queue.offer(nums[i]);
                //小根堆大小超过k时，堆顶元素出堆
                if (queue.size() > k) {
                    queue.poll();
                }
            }
        }

        /**
         * 时间复杂度O(logk)，空间复杂度O(logk)
         *
         * @param val
         * @return
         */
        public int add(int val) {
            queue.offer(val);
            //小根堆大小超过k时，堆顶元素出堆
            if (queue.size() > k) {
                queue.poll();
            }

            return queue.peek();
        }
    }

    /**
     * 手动实现小根堆
     */
    static class KthLargest2 {
        //小根堆数组
        private final int[] arr;
        //小根堆大小
        private final int k;
        //当前小根堆大小
        private int curSize;

        /**
         * 时间复杂度O(nlogk)，空间复杂度O(logk)
         *
         * @param k
         * @param nums
         */
        public KthLargest2(int k, int[] nums) {
            this.k = k;
            curSize = 0;
            arr = new int[k];

            //小根堆arr中放入k和nums数组大小中较小值个元素
            for (int i = 0; i < Math.min(k, nums.length); i++) {
                arr[i] = nums[i];
                curSize++;
            }

            //建立大小为curSize的小根堆
            for (int i = curSize / 2 - 1; i >= 0; i--) {
                heapify(arr, i, curSize);
            }

            //如果当前元素大于小根堆堆顶元素，则堆顶元素出堆，当前元素入堆
            for (int i = k; i < nums.length; i++) {
                if (nums[i] > arr[0]) {
                    arr[0] = nums[i];
                    heapify(arr, 0, k);
                }
            }
        }

        /**
         * 时间复杂度O(logk)，空间复杂度O(logk)
         *
         * @param val
         * @return
         */
        public int add(int val) {
            //当前小根堆大小小于k，则当前元素入堆，再重新建小根堆
            if (curSize < k) {
                arr[curSize] = val;
                curSize++;

                //建立大小为curSize的小根堆
                for (int i = curSize / 2 - 1; i >= 0; i--) {
                    heapify(arr, i, curSize);
                }

                //小根堆堆顶元素即为第k大元素
                return arr[0];
            }

            //如果当前元素大于小根堆堆顶元素，则堆顶元素出堆，当前元素入堆
            if (val > arr[0]) {
                arr[0] = val;
                heapify(arr, 0, k);
            }

            //小根堆堆顶元素即为第k大元素
            return arr[0];
        }

        private void heapify(int[] arr, int i, int heapSize) {
            int index = i;
            int leftIndex = i * 2 + 1;
            int rightIndex = i * 2 + 2;

            if (leftIndex < heapSize && arr[leftIndex] < arr[index]) {
                index = leftIndex;
            }
            if (rightIndex < heapSize && arr[rightIndex] < arr[index]) {
                index = rightIndex;
            }

            //继续向下整堆
            if (index != i) {
                int temp = arr[i];
                arr[i] = arr[index];
                arr[index] = temp;

                heapify(arr, index, heapSize);
            }
        }
    }
}
