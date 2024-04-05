package com.zhang.java;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @Date 2023/8/19 08:32
 * @Author zsy
 * @Description 最大间距 字节面试题 类比Problem912
 * 给定一个无序的数组 nums，返回 数组在排序之后，相邻元素之间最大的差值 。
 * 如果数组元素个数小于 2，则返回 0 。
 * 您必须编写一个在「线性时间」内运行并使用「线性额外空间」的算法。
 * <p>
 * 输入: nums = [3,6,9,1]
 * 输出: 3
 * 解释: 排序后的数组是 [1,3,6,9], 其中相邻元素 (3,6) 和 (6,9) 之间都存在最大差值 3。
 * <p>
 * 输入: nums = [10]
 * 输出: 0
 * 解释: 数组元素个数小于 2，因此返回 0。
 * <p>
 * 1 <= nums.length <= 10^5
 * 0 <= nums[i] <= 10^9
 */
public class Problem164 {
    public static void main(String[] args) {
        Problem164 problem164 = new Problem164();
        int[] nums = {3, 6, 9, 1};
//        //堆排修修改了原数组，需要重新赋值nums，避免影响
//        System.out.println(problem164.maximumGap(nums));
//        System.out.println(problem164.maximumGap2(nums));
//        //基数排序修改了原数组，需要重新赋值nums，避免影响
//        System.out.println(problem164.maximumGap3(nums));
        System.out.println(problem164.maximumGap4(nums));
    }

    /**
     * 暴力
     * 通过比较排序，使数组有序，之后得到相邻元素之间最大的差值
     * 时间复杂度O(nlogn)，空间复杂度O(logn) (递归整堆的空间复杂度为O(logn))
     *
     * @param nums
     * @return
     */
    public int maximumGap(int[] nums) {
        if (nums == null || nums.length < 2) {
            return 0;
        }

        heapSort(nums);

        //排序后相邻元素的最大差值
        int maxDistance = 0;

        for (int i = 1; i < nums.length; i++) {
            maxDistance = Math.max(maxDistance, nums[i] - nums[i - 1]);
        }

        return maxDistance;
    }

    /**
     * 计数排序 (空间溢出)
     * 时间复杂度O(n)，空间复杂度O(max(nums)-min(nums))
     *
     * @param nums
     * @return
     */
    public int maximumGap2(int[] nums) {
        if (nums == null || nums.length < 2) {
            return 0;
        }

        int max = nums[0];
        int min = nums[0];

        for (int num : nums) {
            max = Math.max(max, num);
            min = Math.min(min, num);
        }

        //计数数组
        int[] countArr = new int[max - min + 1];

        for (int num : nums) {
            countArr[num - min]++;
        }

        //计数数组累加，用于从右往左遍历nums找在结果数组中的下标索引，保证稳定性
        //countArr[i]：原数组中小于等于i+min的元素个数
        for (int i = 1; i < countArr.length; i++) {
            countArr[i] = countArr[i] + countArr[i - 1];
        }

        //计数排序后的数组
        int[] result = new int[nums.length];

        //从右往左遍历nums，确定每个nums[i]在result中的下标索引，保证稳定性
        for (int i = nums.length - 1; i >= 0; i--) {
            //当前nums[i]在结果数组result中的下标索引
            int index = countArr[nums[i] - min] - 1;
            result[index] = nums[i];
            //当前nums[i]已排序，nums[i]的元素个数减1
            countArr[nums[i] - min]--;
        }

        //排序后相邻元素的最大差值
        int maxDistance = 0;

        for (int i = 1; i < result.length; i++) {
            maxDistance = Math.max(maxDistance, result[i] - result[i - 1]);
        }

        return maxDistance;
    }

    /**
     * 基数排序
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param nums
     * @return
     */
    public int maximumGap3(int[] nums) {
        if (nums == null || nums.length < 2) {
            return 0;
        }

        int max = nums[0];

        for (int num : nums) {
            max = Math.max(max, num);
        }

        //max的长度，需要根据数组中元素的每一位进行排序
        int maxLen = 0;

        while (max != 0) {
            max = max / 10;
            maxLen++;
        }

        //数组中元素需要除的值，确定当前对数组中元素哪一位进行排序
        int bitCount = 1;

        //由低到高对数组中每一位进行排序，排序之后重新赋值回原数组nums中
        for (int i = 0; i < maxLen; i++) {
            //不存在负数，共10个桶，从小到大分别为0、1...8、9
            List<List<Integer>> radix = new ArrayList<>(10);

            for (int j = 0; j < 10; j++) {
                radix.add(new ArrayList<>());
            }

            for (int num : nums) {
                //得到num当前位所在桶的下标索引
                int index = num / bitCount % 10;
                radix.get(index).add(num);
            }

            int index = 0;

            //由低到高对nums元素第i位排序后，重新赋值回nums，用于第i+1位排序
            for (List<Integer> bucket : radix) {
                for (int num : bucket) {
                    nums[index] = num;
                    index++;
                }
            }

            //bitCount乘10，用于第i+1位排序
            bitCount = bitCount * 10;
        }

        //排序后相邻元素的最大差值
        int maxDistance = 0;

        for (int i = 1; i < nums.length; i++) {
            maxDistance = Math.max(maxDistance, nums[i] - nums[i - 1]);
        }

        return maxDistance;
    }

    /**
     * 桶排序
     * 难点：如果确定每个桶的大小
     * nums数组排序后相邻元素的最大差值大于等于(max-min)/(n-1)，(max：nums中最大值，min：nums中最小值，n：nums长度)
     * 反证法证明，假设nums数组排序后相邻元素的最大差值小于(max-min)/(n-1)，
     * 设nums数组排序后由小到大依次为nums[0]、nums[1]...nums[n-1]，
     * 则nums[n-1]-nums[0] = nums[n-1]-nums[n-2]+...+nums[1]-nums[0] < (n-1)*(max-min)/(n-1) = max-min，
     * 即nums[n-1]-nums[0] = max-min < max-min，产生矛盾
     * 桶排序每个桶大小为(max-min)/(n-1)，即每个桶中相邻元素的差值小于(max-min)/(n-1)，即不是最大差值，
     * 桶排序后，最大差值为当前桶的最小值和上一个桶的最大值的差值的最大值
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param nums
     * @return
     */
    public int maximumGap4(int[] nums) {
        if (nums == null || nums.length < 2) {
            return 0;
        }

        int max = nums[0];
        int min = nums[0];

        for (int num : nums) {
            max = Math.max(max, num);
            min = Math.min(min, num);
        }

        //桶的大小：每个桶存放元素的范围，例如第一个桶存放的范围为[min,min+bucketSize)
        //桶大小为(max-min)/(n-1)，确保每个桶中相邻元素的差值小于(max-min)/(n-1)，即不是最大差值
        //注意：桶的大小至少为1
        int bucketSize = Math.max(1, (max - min) / (nums.length - 1));
        //桶的数量
        int bucketCount = (max - min) / bucketSize + 1;
        //bucketCount个桶
        List<List<Integer>> buckets = new ArrayList<>(bucketCount);

        for (int i = 0; i < bucketCount; i++) {
            buckets.add(new ArrayList<>());
        }

        for (int num : nums) {
            //当前元素所在的桶索引下标
            int index = (num - min) / bucketSize;
            buckets.get(index).add(num);
        }

        //对每一个桶中元素使用某种排序算法进行排序
        for (int i = 0; i < bucketCount; i++) {
            buckets.get(i).sort(new Comparator<Integer>() {
                @Override
                public int compare(Integer a, Integer b) {
                    return a - b;
                }
            });
        }

        //排序后相邻元素的最大差值
        int maxDistance = 0;
        //前一个桶的最大元素，即前一个桶排好序的最后一个元素
        int preBucketMax = buckets.get(0).get(buckets.get(0).size() - 1);

        //最大差值为当前桶的最小值和上一个桶的最大值的差值的最大值
        for (int i = 1; i < bucketCount; i++) {
            //当前桶
            List<Integer> curBucket = buckets.get(i);

            //当前桶中没有元素，直接进行下次循环
            if (curBucket.isEmpty()) {
                continue;
            }

            //当前桶的最小元素，即当前桶排好序的第一个元素
            int curBucketMin = curBucket.get(0);
            maxDistance = Math.max(maxDistance, curBucketMin - preBucketMax);
            //更新前一个桶的最大元素为当前桶的最大值
            preBucketMax = curBucket.get(curBucket.size() - 1);
        }

        return maxDistance;
    }

    private void heapSort(int[] nums) {
        for (int i = nums.length / 2 - 1; i >= 0; i--) {
            heapify(nums, i, nums.length);
        }

        for (int i = nums.length - 1; i > 0; i--) {
            int temp = nums[0];
            nums[0] = nums[i];
            nums[i] = temp;

            heapify(nums, 0, i);
        }
    }

    private void heapify(int[] nums, int i, int heapSize) {
        int index = i;
        int leftIndex = i * 2 + 1;
        int rightIndex = i * 2 + 2;

        if (leftIndex < heapSize && nums[leftIndex] > nums[index]) {
            index = leftIndex;
        }

        if (rightIndex < heapSize && nums[rightIndex] > nums[index]) {
            index = rightIndex;
        }

        if (index != i) {
            int temp = nums[i];
            nums[i] = nums[index];
            nums[index] = temp;

            heapify(nums, index, heapSize);
        }
    }
}
