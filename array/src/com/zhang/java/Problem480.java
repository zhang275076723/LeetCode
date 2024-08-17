package com.zhang.java;

import java.util.*;

/**
 * @Date 2023/7/4 08:45
 * @Author zsy
 * @Description 滑动窗口中位数 招商银行机试题 美团机试题 类比Problem4、Problem239 类比Problem295、Problem346、Problem703、Offer41 延迟删除类比Problem2034、Problem2349、Problem2353
 * 中位数是有序序列最中间的那个数。如果序列的长度是偶数，则没有最中间的数；此时中位数是最中间的两个数的平均数。
 * 例如：
 * [2,3,4]，中位数是 3
 * [2,3]，中位数是 (2 + 3) / 2 = 2.5
 * 给你一个数组 nums，有一个长度为 k 的窗口从最左端滑动到最右端。
 * 窗口中有 k 个数，每次窗口向右移1位。
 * 你的任务是找出每次窗口移动后得到的新窗口中元素的中位数，并输出由它们组成的数组。
 * <p>
 * 给出 nums = [1,3,-1,-3,5,3,6,7]，以及 k = 3。
 * 窗口位置                      中位数
 * ---------------               -----
 * [1  3  -1] -3  5  3  6  7       1
 * 1 [3  -1  -3] 5  3  6  7       -1
 * 1  3 [-1  -3  5] 3  6  7       -1
 * 1  3  -1 [-3  5  3] 6  7        3
 * 1  3  -1  -3 [5  3  6] 7        5
 * 1  3  -1  -3  5 [3  6  7]       6
 * 因此，返回该滑动窗口的中位数数组 [1,-1,-1,3,5,6]。
 * <p>
 * 你可以假设 k 始终有效，即：k 始终小于等于输入的非空数组的元素个数。
 * 与真实值误差在 10 ^ -5 以内的答案将被视作正确答案。
 */
public class Problem480 {
    public static void main(String[] args) {
        Problem480 problem480 = new Problem480();
        int[] nums = {1, 3, -1, -3, 5, 3, 6, 7};
        int k = 3;
        System.out.println(Arrays.toString(problem480.medianSlidingWindow(nums, k)));
        System.out.println(Arrays.toString(problem480.medianSlidingWindow2(nums, k)));
    }

    /**
     * 暴力
     * 时间复杂度O((n-k)*k*logk)，空间复杂度O(k)
     *
     * @param nums
     * @param k
     * @return
     */
    public double[] medianSlidingWindow(int[] nums, int k) {
        //结果数组
        double[] result = new double[nums.length - k + 1];
        //存放当前滑动窗口k个元素的数组
        int[] arr = new int[k];

        for (int i = 0; i < nums.length - k + 1; i++) {
            //当前滑动窗口中k个元素放入arr中，排序取中位数
            for (int j = 0; j < k; j++) {
                arr[j] = nums[i + j];
            }

            quickSort(arr, 0, arr.length - 1);

            if (k % 2 == 0) {
                //使用long，避免int相加溢出
                result[i] = ((long) arr[k / 2 - 1] + arr[k / 2]) / 2.0;
            } else {
                result[i] = arr[k / 2];
            }
        }

        return result;
    }

    /**
     * 大根堆+小根堆(对顶堆)
     * 1、大根堆为空，或者当前元素小于大根堆堆顶元素，则当前元素入大根堆，此时如果大根堆元素数量大于小根堆元素数量加1，
     * 则大根堆堆顶元素出堆，入小根堆；
     * 2、否则，当前元素入小根堆，此时如果大根堆元素数量小于小根堆元素数量，则小根堆堆顶元素出堆，入大根堆
     * 注意：因为大根堆和小根堆只能移除堆顶元素，所以对于非堆顶元素的删除使用延迟删除，
     * 如果要删除的元素不是堆顶元素，则记录要删除的元素，直到堆顶元素是要删除的元素时，才删除当前元素
     * 注意：始终保持大根堆元素数量 >= 小根堆元素数量
     * 时间复杂度O(nlogn)，空间复杂度O(n)
     *
     * @param nums
     * @param k
     * @return
     */
    public double[] medianSlidingWindow2(int[] nums, int k) {
        DualHeap dualHeap = new DualHeap(k);
        double[] result = new double[nums.length - k + 1];

        for (int i = 0; i < k; i++) {
            dualHeap.insert(nums[i]);
        }

        result[0] = dualHeap.getMedian();

        for (int i = k; i < nums.length; i++) {
            dualHeap.insert(nums[i]);
            dualHeap.delete(nums[i - k]);
            result[i - k + 1] = dualHeap.getMedian();
        }

        return result;
    }

    /**
     * 对顶堆(大根堆+小根堆)+延迟删除
     * 大根堆，维护所有元素中较小的一半
     * 小根堆，维护所有元素中较大的一半
     * 1、大根堆为空，或者当前元素小于大根堆堆顶元素，则当前元素入大根堆，此时如果大根堆元素数量大于小根堆元素数量加1，
     * 则大根堆堆顶元素出堆，入小根堆；
     * 2、否则，当前元素入小根堆，此时如果大根堆元素数量小于小根堆元素数量，则小根堆堆顶元素出堆，入大根堆
     * 注意：因为大根堆和小根堆只能删除堆顶元素，如果要删除的元素不是堆顶元素，则标记为延迟删除，当堆顶元素是要删除的元素时，
     * 才删除当前元素
     * 注意：始终保持大根堆元素数量 >= 小根堆元素数量
     * 注意：堆中元素每次出堆时，都需要调整堆，保证堆顶元素不是延迟删除的元素
     */
    public static class DualHeap {
        //大根堆，维护所有元素中较小的一半
        private final Queue<Integer> maxQueue;
        //小根堆，维护所有元素中较大的一半
        private final Queue<Integer> minQueue;
        //延迟删除map，因为大根堆和小根堆只能移除堆顶元素，使用延迟删除记录要删除的元素，当堆顶元素是要删除的元素时，才删除当前元素
        //key：延迟删除的元素，value：当前元素需要删除的次数
        private final Map<Integer, Integer> delayMap;
        //大根堆大小
        private int maxQueueSize;
        //小根堆大小
        private int minQueueSize;
        //滑动窗口大小
        private final int k;

        public DualHeap(int k) {
            maxQueue = new PriorityQueue<>(new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    //不能使用o2-o1，避免int相减溢出
                    return Integer.compare(o2, o1);
                }
            });
            minQueue = new PriorityQueue<>(new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    return Integer.compare(o1, o2);
                }
            });
            delayMap = new HashMap<>();
            maxQueueSize = 0;
            minQueueSize = 0;
            this.k = k;
        }

        public void insert(int num) {
            //num入大根堆
            if (maxQueueSize == 0 || num <= maxQueue.peek()) {
                maxQueue.offer(num);
                maxQueueSize++;

                //大根堆元素数量大于小根堆元素数量加1，大根堆堆顶元素出堆，入小根堆，并调整大根堆
                if (maxQueueSize > minQueueSize + 1) {
                    minQueue.offer(maxQueue.poll());
                    maxQueueSize--;
                    minQueueSize++;
                    //大根堆堆顶元素出堆，入小根堆，则需要调整大根堆，删除堆顶是延迟删除的元素
                    adjust(maxQueue);
                }
            } else {
                //num入小根堆
                minQueue.offer(num);
                minQueueSize++;

                //大根堆元素数量小于小根堆元素数量，小根堆堆顶元素出堆，入大根堆，并调整小根堆
                if (maxQueueSize < minQueueSize) {
                    maxQueue.offer(minQueue.poll());
                    maxQueueSize++;
                    minQueueSize--;
                    //小根堆堆顶元素出堆，入大根堆，则需要调整小根堆，删除堆顶是延迟删除的元素
                    adjust(minQueue);
                }
            }
        }

        public void delete(int num) {
            //延迟删除的元素num加入delayMap
            delayMap.put(num, delayMap.getOrDefault(num, 0) + 1);

            //延迟删除的num在大根堆中
            if (num <= maxQueue.peek()) {
                maxQueueSize--;
                adjust(maxQueue);

                //大根堆元素数量小于小根堆元素数量，小根堆堆顶元素出堆，入大根堆，并调整小根堆
                if (maxQueueSize < minQueueSize) {
                    maxQueue.offer(minQueue.poll());
                    maxQueueSize++;
                    minQueueSize--;
                    //小根堆堆顶元素出堆，入大根堆，则需要调整小根堆，删除堆顶是延迟删除的元素
                    adjust(minQueue);
                }
            } else {
                //延迟删除的num在小根堆中
                minQueueSize--;
                adjust(minQueue);

                //大根堆元素数量大于小根堆元素数量加1，大根堆堆顶元素出堆，入小根堆，并调整大根堆
                if (maxQueueSize > minQueueSize + 1) {
                    minQueue.offer(maxQueue.poll());
                    maxQueueSize--;
                    minQueueSize++;
                    //大根堆堆顶元素出堆，入小根堆，则需要调整大根堆，删除堆顶是延迟删除的元素
                    adjust(maxQueue);
                }
            }
        }

        /**
         * 调整堆，删除堆顶是延迟删除的元素，确保堆顶元素不是延迟删除的元素
         * 时间复杂度O(nlogn)，空间复杂度O(1)
         */
        public void adjust(Queue<Integer> queue) {
            //堆顶元素是延迟删除的元素，则直接出堆，delayMap中个数减1
            while (!queue.isEmpty() && delayMap.containsKey(queue.peek())) {
                int num = queue.poll();
                delayMap.put(num, delayMap.get(num) - 1);
                if (delayMap.get(num) == 0) {
                    delayMap.remove(num);
                }
            }
        }

        public double getMedian() {
            if (k % 2 == 0) {
                //使用long，避免int相加溢出
                return ((long) maxQueue.peek() + minQueue.peek()) / 2.0;
            } else {
                return maxQueue.peek();
            }
        }
    }

    private void quickSort(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }

        int pivot = partition(arr, left, right);
        quickSort(arr, left, pivot - 1);
        quickSort(arr, pivot + 1, right);
    }

    private int partition(int[] arr, int left, int right) {
        int randomIndex = new Random().nextInt(right - left + 1) + left;

        int value = arr[left];
        arr[left] = arr[randomIndex];
        arr[randomIndex] = value;

        int temp = arr[left];

        while (left < right) {
            while (left < right && arr[right] >= temp) {
                right--;
            }
            arr[left] = arr[right];

            while (left < right && arr[left] <= temp) {
                left++;
            }
            arr[right] = arr[left];
        }

        arr[left] = temp;

        return left;
    }
}
