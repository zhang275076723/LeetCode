package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/11/12 11:37
 * @Author zsy
 * @Description 排序数组 十大排序算法 类比Problem164
 * 给你一个整数数组 nums，请你将该数组升序排列。
 * <p>
 * 输入：nums = [5,2,3,1]
 * 输出：[1,2,3,5]
 * <p>
 * 输入：nums = [5,1,1,2,0,0]
 * 输出：[0,0,1,1,2,5]
 * <p>
 * 1 <= nums.length <= 5 * 10^4
 * -5 * 10^4 <= nums[i] <= 5 * 10^4
 */
public class Problem912 {
    public static void main(String[] args) {
        Problem912 problem912 = new Problem912();
//        int[] nums = {5, 1, 1, 2, 0, 0};
        int[] nums = {13, 42, 32, 44, 14, 16, 66, 40, 48, 38, 49, 3, -3, -9};
//        System.out.println(Arrays.toString(problem912.sortArray(nums)));
//        System.out.println(Arrays.toString(problem912.sortArray2(nums)));
//        System.out.println(Arrays.toString(problem912.sortArray3(nums)));
//        System.out.println(Arrays.toString(problem912.sortArray4(nums)));
//        System.out.println(Arrays.toString(problem912.sortArray5(nums)));
//        System.out.println(Arrays.toString(problem912.sortArray6(nums)));
//        System.out.println(Arrays.toString(problem912.sortArray7(nums)));
//        System.out.println(Arrays.toString(problem912.sortArray8(nums)));
//        System.out.println(Arrays.toString(problem912.sortArray9(nums)));
        System.out.println(Arrays.toString(problem912.sortArray10(nums)));
    }

    /**
     * 插入排序 稳定
     * 时间复杂度O(n^2)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public int[] sortArray(int[] nums) {
        for (int i = 1; i < nums.length; i++) {
            int temp = nums[i];
            int j = i - 1;

            //找到temp要插入的位置
            while (j >= 0 && nums[j] > temp) {
                nums[j + 1] = nums[j];
                j--;
            }

            nums[j + 1] = temp;
        }

        return nums;
    }

    /**
     * 冒泡排序 稳定
     * 时间复杂度O(n^2)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public int[] sortArray2(int[] nums) {
        for (int i = 0; i < nums.length - 1; i++) {
            //本轮是否进行交换的标志位
            boolean flag = false;

            for (int j = 0; j < nums.length - 1 - i; j++) {
                if (nums[j] > nums[j + 1]) {
                    int temp = nums[j];
                    nums[j] = nums[j + 1];
                    nums[j + 1] = temp;
                    flag = true;
                }
            }

            //本轮没有进行交换，则说明nums已经有序，直接返回
            if (!flag) {
                return nums;
            }
        }

        return nums;
    }

    /**
     * 选择排序 不稳定
     * 时间复杂度O(n^2)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public int[] sortArray3(int[] nums) {
        for (int i = 0; i < nums.length - 1; i++) {
            //当前最小元素下标索引
            int index = i;

            //找nums[i]后面比nums[i]小的最小值nums[index]
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[j] < nums[index]) {
                    index = j;
                }
            }

            //找到比nums[i]小的最小值nums[index]，两者进行交换
            if (index != i) {
                int temp = nums[index];
                nums[index] = nums[i];
                nums[i] = temp;
            }
        }

        return nums;
    }

    /**
     * 快速排序 不稳定
     * 时间复杂度O(nlogn)，空间复杂度O(logn)
     *
     * @param nums
     * @return
     */
    public int[] sortArray4(int[] nums) {
        quickSort(nums, 0, nums.length - 1);

        return nums;
    }

    /**
     * 堆排序 不稳定
     * 时间复杂度O(nlogn)，空间复杂度O(logn) (因为采用递归整堆，栈深度为O(logn))
     *
     * @param nums
     * @return
     */
    public int[] sortArray5(int[] nums) {
        //建堆
        for (int i = nums.length / 2 - 1; i >= 0; i--) {
            heapify(nums, i, nums.length);
        }

        //堆顶元素和末尾元素交换
        for (int i = nums.length - 1; i > 0; i--) {
            int temp = nums[0];
            nums[0] = nums[i];
            nums[i] = temp;

            //继续整堆，保持大根堆
            heapify(nums, 0, i);
        }

        return nums;
    }

    /**
     * 归并排序 稳定
     * 时间复杂度O(nlogn)，空间复杂度O(n)
     *
     * @param nums
     * @return
     */
    public int[] sortArray6(int[] nums) {
        mergeSort(nums, 0, nums.length - 1, new int[nums.length]);

        return nums;
    }

    /**
     * 希尔排序 不稳定
     * 时间复杂度O(n^1.3)-O(n^2)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public int[] sortArray7(int[] nums) {
        //步长i
        for (int i = nums.length / 2; i > 0; i = i / 2) {
            //当前步长的每组元素的起始元素下标索引j
            for (int j = 0; j < i; j++) {
                //对每组元素(nums[j]、nums[j+i]、nums[j+i*2]...)进行插入排序
                for (int m = j + i; m < nums.length; m = m + i) {
                    int temp = nums[m];
                    int n = m - i;

                    //找到temp要插入的位置
                    while (n >= j && nums[n] > temp) {
                        nums[n + i] = nums[n];
                        n = n - i;
                    }

                    nums[n + i] = temp;
                }
            }
        }

        return nums;
    }

    /**
     * 计数排序(只能对整数进行排序) 稳定
     * 时间复杂度O(n)，空间复杂度O(max(nums)-min(nums))
     *
     * @param nums
     * @return
     */
    public int[] sortArray8(int[] nums) {
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

        return result;
    }

    /**
     * 桶排序 稳定或不稳定，根据对每个桶使用的排序算法确定是否稳定
     * 根据数组中元素范围分为多个桶，将数组中元素放到对应的桶中，对每个桶进行排序，最后把桶中数据拼接，得到排好序的数组
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param nums
     * @return
     */
    public int[] sortArray9(int[] nums) {
        int max = nums[0];
        int min = nums[0];

        for (int num : nums) {
            max = Math.max(max, num);
            min = Math.min(min, num);
        }

        //每个桶的大小，例如第一个桶存放[min,min+bucketSize)的元素
        int bucketSize = 10;
        //桶的数量
        int bucketCount = (max - min) / bucketSize + 1;
        //共bucketCount个桶
        List<List<Integer>> buckets = new ArrayList<>(bucketCount);

        for (int i = 0; i < bucketCount; i++) {
            buckets.add(new ArrayList<>());
        }

        //nums中元素放入对应桶中
        for (int num : nums) {
            //当前元素所在的桶索引下标
            int index = (num - min) / bucketSize;
            buckets.get(index).add(num);
        }

        //每个桶中元素使用某种排序算法进行排序
        for (int i = 0; i < bucketCount; i++) {
            buckets.get(i).sort(new Comparator<Integer>() {
                @Override
                public int compare(Integer a, Integer b) {
                    return a - b;
                }
            });
        }

        int[] result = new int[nums.length];
        int index = 0;

        for (List<Integer> bucket : buckets) {
            for (int num : bucket) {
                result[index] = num;
                index++;
            }
        }

        return result;
    }

    /**
     * 基数排序 稳定
     * 时间复杂度O(kn)=O((log32)n)=O(n)，空间复杂度O(n) (k：数组中最长元素的位数，int范围最多有32位)
     *
     * @param nums
     * @return
     */
    public int[] sortArray10(int[] nums) {
        //nums中绝对值最大的元素
        int max = nums[0];

        //找数组中最长元素的位数
        for (int num : nums) {
            //因为存在负数，所以需要取绝对值
            max = Math.max(max, Math.abs(num));
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
            //存在负数，共19个桶，从小到大分别为-9、-8...-1、0、1...8、9
            List<List<Integer>> radix = new ArrayList<>(19);

            for (int j = 0; j < 19; j++) {
                radix.add(new ArrayList<>());
            }

            for (int num : nums) {
                //得到num当前位所在桶的下标索引，因为存在负数，所以index要加9
                int index = (num / bitCount) % 10 + 9;
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

            //bitCount乘10，表示对高1位进行排序
            bitCount = bitCount * 10;
        }

        return nums;
    }

    private void quickSort(int[] nums, int left, int right) {
        if (left < right) {
            int pivot = partition(nums, left, right);
            quickSort(nums, left, pivot - 1);
            quickSort(nums, pivot + 1, right);
        }
    }

    private int partition(int[] nums, int left, int right) {
        //随机取一个元素作为划分基准，避免性能倒退为O(n^2)
        int randomIndex = new Random().nextInt(right - left + 1) + left;

        int value = nums[left];
        nums[left] = nums[randomIndex];
        nums[randomIndex] = value;

        int temp = nums[left];

        while (left < right) {
            while (left < right && nums[right] >= temp) {
                right--;
            }

            nums[left] = nums[right];

            while (left < right && nums[left] <= temp) {
                left++;
            }

            nums[right] = nums[left];
        }

        nums[left] = temp;

        return left;
    }

    private void heapify(int[] nums, int i, int heapSize) {
        int index = i;
        int leftIndex = i * 2 + 1;
        int rightIndex = i * 2 + 2;

        if (leftIndex < heapSize && nums[leftIndex] >= nums[index]) {
            index = leftIndex;
        }

        if (rightIndex < heapSize && nums[rightIndex] >= nums[index]) {
            index = rightIndex;
        }

        if (i != index) {
            int temp = nums[i];
            nums[i] = nums[index];
            nums[index] = temp;

            heapify(nums, index, heapSize);
        }
    }

    private void mergeSort(int[] nums, int left, int right, int[] tempArr) {
        if (left < right) {
            int mid = left + ((right - left) >> 1);
            mergeSort(nums, left, mid, tempArr);
            mergeSort(nums, mid + 1, right, tempArr);
            merge(nums, left, mid, right, tempArr);
        }
    }

    private void merge(int[] nums, int left, int mid, int right, int[] tempArr) {
        int i = left;
        int j = mid + 1;
        int k = left;

        while (i <= mid && j <= right) {
            if (nums[i] <= nums[j]) {
                tempArr[k] = nums[i];
                i++;
                k++;
            } else {
                tempArr[k] = nums[j];
                j++;
                k++;
            }
        }

        while (i <= mid) {
            tempArr[k] = nums[i];
            i++;
            k++;
        }

        while (j <= right) {
            tempArr[k] = nums[j];
            j++;
            k++;
        }

        for (k = left; k <= right; k++) {
            nums[k] = tempArr[k];
        }
    }
}
