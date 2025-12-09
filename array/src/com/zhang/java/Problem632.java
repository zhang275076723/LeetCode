package com.zhang.java;

import java.util.*;

/**
 * @Date 2024/1/14 09:05
 * @Author zsy
 * @Description 最小区间 南京大学机试题 排序+滑动窗口类比Problem532、Problem1838、Problem2009 区间类比Problem56、Problem57、Problem163、Problem228、Problem252、Problem253、Problem352、Problem406、Problem435、Problem436、Problem763、Problem855、Problem986、Problem1288、Problem2402 优先队列类比
 * 你有 k 个 非递减排列 的整数列表。找到一个 最小 区间，使得 k 个列表中的每个列表至少有一个数包含在其中。
 * 我们定义如果 b-a < d-c 或者在 b-a == d-c 时 a < c，则区间 [a,b] 比 [c,d] 小。
 * <p>
 * 输入：nums = [[4,10,15,24,26], [0,9,12,20], [5,18,22,30]]
 * 输出：[20,24]
 * 解释：
 * 列表 1：[4, 10, 15, 24, 26]，24 在区间 [20,24] 中。
 * 列表 2：[0, 9, 12, 20]，20 在区间 [20,24] 中。
 * 列表 3：[5, 18, 22, 30]，22 在区间 [20,24] 中。
 * <p>
 * 输入：nums = [[1,2,3],[1,2,3],[1,2,3]]
 * 输出：[1,1]
 * <p>
 * nums.length == k
 * 1 <= k <= 3500
 * 1 <= nums[i].length <= 50
 * -10^5 <= nums[i][j] <= 10^5
 * nums[i] 按非递减顺序排列
 */
public class Problem632 {
    public static void main(String[] args) {
        Problem632 problem632 = new Problem632();
        List<List<Integer>> nums = new ArrayList<List<Integer>>() {{
            add(new ArrayList<Integer>() {{
                add(4);
                add(10);
                add(15);
                add(24);
                add(26);
            }});
            add(new ArrayList<Integer>() {{
                add(0);
                add(9);
                add(12);
                add(20);
            }});
            add(new ArrayList<Integer>() {{
                add(5);
                add(18);
                add(22);
                add(30);
            }});
        }};
        System.out.println(Arrays.toString(problem632.smallestRange(nums)));
        System.out.println(Arrays.toString(problem632.smallestRange2(nums)));
    }

    /**
     * 优先队列，小根堆，多路归并排序
     * nums每行第一个元素入小根堆，同时记录小根堆中的最大元素，小根堆堆顶元素和小根堆中的最大元素作为新区间更新最小区间，
     * 如果堆顶元素出堆后，当前元素所在行存在下一个元素，则当前行下一个元素入堆，同时更新小根堆中的最大元素
     * 时间复杂度O(nlogk)，空间复杂度O(k) (k=nums.size()，n：nums中所有元素的个数)
     *
     * @param nums
     * @return
     */
    public int[] smallestRange(List<List<Integer>> nums) {
        //优先队列，小根堆，int[0]：当前元素，int[1]：当前元素所在行的下标索引，int[2]：当前元素所在列的下标索引
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] arr1, int[] arr2) {
                return arr1[0] - arr2[0];
            }
        });

        //小根堆中的最大元素，堆顶元素和小根堆中的最大元素作为新区间更新最小区间
        int max = Integer.MIN_VALUE;

        //每行第一个元素入小根堆
        for (int i = 0; i < nums.size(); i++) {
            priorityQueue.offer(new int[]{nums.get(i).get(0), i, 0});
            max = Math.max(max, nums.get(i).get(0));
        }

        //最小区间的左边界
        //避免minRightBound取int最大值，minLeftBound取最小值，minRightBound-minLeftBound在int范围内溢出
        int minLeftBound = Integer.MIN_VALUE / 2;
        //最小区间的右边界
        int minRightBound = Integer.MAX_VALUE / 2;

        //确保小根堆中包含nums中每行的元素
        while (priorityQueue.size() == nums.size()) {
            int[] arr = priorityQueue.poll();

            //更新最小区间
            if (max - arr[0] < minRightBound - minLeftBound) {
                minLeftBound = arr[0];
                minRightBound = max;
            }

            //当前元素所在行存在下一个元素，则当前行下一个元素入堆，同时更新当前小根堆中的最大元素
            if (arr[2] + 1 < nums.get(arr[1]).size()) {
                priorityQueue.offer(new int[]{nums.get(arr[1]).get(arr[2] + 1), arr[1], arr[2] + 1});
                max = Math.max(max, nums.get(arr[1]).get(arr[2] + 1));
            }
        }

        return new int[]{minLeftBound, minRightBound};
    }

    /**
     * 排序+滑动窗口
     * arr[0]：nums中元素，arr[1]：当前nums元素所在行的下标索引，二维数组arr按照nums中元素arr[0]由小到大排序，
     * 当left和right形成的窗口不满足每行至少包含一个nums元素时，right右移；
     * 当left和right形成的窗口满足每行至少包含一个nums元素时，更新最小区间，left左移
     * 时间复杂度O(nlogn)，空间复杂度O(n) (k=nums.size()，n：nums中所有元素的个数)
     *
     * @param nums
     * @return
     */
    public int[] smallestRange2(List<List<Integer>> nums) {
        //nums中元素的个数
        int count = 0;

        for (int i = 0; i < nums.size(); i++) {
            count = count + nums.get(i).size();
        }

        //arr[0]：nums中元素，arr[1]：当前nums元素所在行的下标索引
        int[][] arr = new int[count][2];
        int index = 0;

        for (int i = 0; i < nums.size(); i++) {
            for (int j = 0; j < nums.get(i).size(); j++) {
                arr[index] = new int[]{nums.get(i).get(j), i};
                index++;
            }
        }

        //二维数组arr按照nums中元素arr[0]由小到大排序
        Arrays.sort(arr, new Comparator<int[]>() {
            @Override
            public int compare(int[] arr1, int[] arr2) {
                return arr1[0] - arr2[0];
            }
        });

        //滑动窗口左右指针
        int left = 0;
        int right = 0;
        //最小区间的左边界
        //避免minRightBound取int最大值，minLeftBound取最小值，minRightBound-minLeftBound在int范围内溢出
        int minLeftBound = Integer.MIN_VALUE / 2;
        //最小区间的右边界
        int minRightBound = Integer.MAX_VALUE / 2;
        //key：滑动窗口中元素在nums所在行的下标索引，value：滑动窗口中当前行key中nums元素个数
        Map<Integer, Integer> map = new HashMap<>();

        while (right < arr.length) {
            map.put(arr[right][1], map.getOrDefault(arr[right][1], 0) + 1);

            //滑动窗口满足每行至少包含一个nums元素时，更新最小区间，left左移
            while (map.size() == nums.size()) {
                //更新最小区间
                if (arr[right][0] - arr[left][0] < minRightBound - minLeftBound) {
                    minLeftBound = arr[left][0];
                    minRightBound = arr[right][0];
                }

                //left指针所指元素从map中移除
                map.put(arr[left][1], map.get(arr[left][1]) - 1);

                //注意：arr[left][1]必须从map中移除，因为是通过map.size()判断滑动窗口中是否每个列表中都包含元素
                if (map.get(arr[left][1]) == 0) {
                    map.remove(arr[left][1]);
                }

                left++;
            }

            right++;
        }

        return new int[]{minLeftBound, minRightBound};
    }
}
