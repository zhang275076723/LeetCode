package com.zhang.java;

import java.util.*;

/**
 * @Date 2024/4/2 09:10
 * @Author zsy
 * @Description 摆动排序 II 类比Problem280 类比Problem164、Problem912 三指针类比Problem75、Problem264、Problem313、Problem1201、Offer49 快排划分类比Problem215、Problem347、Problem462、Problem973、Offer40
 * 给你一个整数数组 nums，将它重新排列成 nums[0] < nums[1] > nums[2] < nums[3]... 的顺序。
 * 你可以假设所有输入数组都可以得到满足题目要求的结果。
 * 进阶：你能用 O(n) 时间复杂度和 / 或原地 O(1) 额外空间来实现吗？
 * <p>
 * 输入：nums = [1,5,1,1,6,4]
 * 输出：[1,6,1,5,1,4]
 * 解释：[1,4,1,5,1,6] 同样是符合题目要求的结果，可以被判题程序接受。
 * <p>
 * 输入：nums = [1,3,2,2,3,1]
 * 输出：[2,3,1,3,1,2]
 * <p>
 * 1 <= nums.length <= 5 * 10^4
 * 0 <= nums[i] <= 5000
 * 题目数据保证，对于给定的输入 nums ，总能产生满足题目要求的结果
 */
public class Problem324 {
    public static void main(String[] args) {
        Problem324 problem324 = new Problem324();
        int[] nums = {1, 5, 1, 1, 6, 4};
        problem324.wiggleSort(nums);
        System.out.println(Arrays.toString(nums));
        //需要对原数组重新赋值
        nums = new int[]{1, 5, 1, 1, 6, 4};
        problem324.wiggleSort2(nums);
        System.out.println(Arrays.toString(nums));
        //需要对原数组重新赋值
        nums = new int[]{1, 5, 1, 1, 6, 4};
        problem324.wiggleSort3(nums);
        System.out.println(Arrays.toString(nums));
        //需要对原数组重新赋值
        nums = new int[]{1, 5, 1, 1, 6, 4};
        problem324.wiggleSort4(nums);
        System.out.println(Arrays.toString(nums));
        //需要对原数组重新赋值
        nums = new int[]{1, 5, 1, 1, 6, 4};
        problem324.wiggleSort5(nums);
        System.out.println(Arrays.toString(nums));
    }

    /**
     * 排序+模拟
     * nums由小到大排序，前一半元素(数组长度为奇数，则前一半元素包含中间元素)按逆序放在偶数下标索引中，后一半元素按逆序放在奇数下标索引中
     * 注意：前一半元素(数组长度为奇数，则前一半元素包含中间元素)按顺序放在偶数下标索引中，后一半元素按顺序放在奇数下标索引中不正确
     * 例如：nums = [1,2,4,4,4,6]
     * 前一半元素和后一半元素如果都按顺序放置，则得到[1,4,2,4,4,6]，不正确
     * 前一半元素和后一半元素如果都按逆序放置，则得到[4,6,2,4,1,4]，正确
     * 时间复杂度O(nlogn)，空间复杂度O(n) (堆排的空间复杂度为O(logn)，需要O(n)的额外数组)
     *
     * @param nums
     */
    public void wiggleSort(int[] nums) {
        //nums的拷贝数组
        int[] arr = Arrays.copyOfRange(nums, 0, nums.length);

        //arr由小到大排序
        heapSort(arr);

        //偶数下标索引
        int index1 = 0;
        //奇数下标索引
        int index2 = 1;

        //前一半元素(数组长度为奇数，则前一半元素包含中间元素)按逆序放在偶数下标索引中
        for (int i = (arr.length % 2 == 0 ? arr.length / 2 - 1 : arr.length / 2); i >= 0; i--) {
            nums[index1] = arr[i];
            index1 = index1 + 2;
        }

        //后一半元素按逆序放在奇数下标索引中
        for (int i = arr.length - 1; i >= (arr.length % 2 == 0 ? arr.length / 2 : arr.length / 2 + 1); i--) {
            nums[index2] = arr[i];
            index2 = index2 + 2;
        }
    }

    /**
     * 计数排序+模拟
     * nums由小到大排序，前一半元素(数组长度为奇数，则前一半元素包含中间元素)按逆序放在偶数下标索引中，后一半元素按逆序放在奇数下标索引中
     * 注意：前一半元素(数组长度为奇数，则前一半元素包含中间元素)按顺序放在偶数下标索引中，后一半元素按顺序放在奇数下标索引中不正确
     * 例如：nums = [1,2,4,4,4,6]
     * 前一半元素和后一半元素如果都按顺序放置，则得到[1,4,2,4,4,6]，不正确
     * 前一半元素和后一半元素如果都按逆序放置，则得到[4,6,2,4,1,4]，正确
     * 时间复杂度O(n)，空间复杂度O(max(nums)-min(nums))
     *
     * @param nums
     */
    public void wiggleSort2(int[] nums) {
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

        for (int i = 1; i < countArr.length; i++) {
            countArr[i] = countArr[i] + countArr[i - 1];
        }

        //计数排序后的数组
        int[] result = new int[nums.length];

        for (int i = nums.length - 1; i >= 0; i--) {
            int index = countArr[nums[i] - min] - 1;
            result[index] = nums[i];
            countArr[nums[i] - min]--;
        }

        //偶数下标索引
        int index1 = 0;
        //奇数下标索引
        int index2 = 1;

        //前一半元素(数组长度为奇数，则前一半元素包含中间元素)按逆序放在偶数下标索引中
        for (int i = (result.length % 2 == 0 ? result.length / 2 - 1 : result.length / 2); i >= 0; i--) {
            nums[index1] = result[i];
            index1 = index1 + 2;
        }

        //后一半元素按逆序放在奇数下标索引中
        for (int i = result.length - 1; i >= (result.length % 2 == 0 ? result.length / 2 : result.length / 2 + 1); i--) {
            nums[index2] = result[i];
            index2 = index2 + 2;
        }
    }

    /**
     * 桶排序+模拟
     * nums由小到大排序，前一半元素(数组长度为奇数，则前一半元素包含中间元素)按逆序放在偶数下标索引中，后一半元素按逆序放在奇数下标索引中
     * 注意：前一半元素(数组长度为奇数，则前一半元素包含中间元素)按顺序放在偶数下标索引中，后一半元素按顺序放在奇数下标索引中不正确
     * 例如：nums = [1,2,4,4,4,6]
     * 前一半元素和后一半元素如果都按顺序放置，则得到[1,4,2,4,4,6]，不正确
     * 前一半元素和后一半元素如果都按逆序放置，则得到[4,6,2,4,1,4]，正确
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param nums
     */
    public void wiggleSort3(int[] nums) {
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

        //每个桶中元素由小到大排序
        for (int i = 0; i < bucketCount; i++) {
            buckets.get(i).sort(new Comparator<Integer>() {
                @Override
                public int compare(Integer a, Integer b) {
                    return a - b;
                }
            });
        }

        //桶排序后的数组
        int[] result = new int[nums.length];
        int index = 0;

        for (List<Integer> bucket : buckets) {
            for (int num : bucket) {
                result[index] = num;
                index++;
            }
        }

        //偶数下标索引
        int index1 = 0;
        //奇数下标索引
        int index2 = 1;

        //前一半元素(数组长度为奇数，则前一半元素包含中间元素)按逆序放在偶数下标索引中
        for (int i = (result.length % 2 == 0 ? result.length / 2 - 1 : result.length / 2); i >= 0; i--) {
            nums[index1] = result[i];
            index1 = index1 + 2;
        }

        //后一半元素按逆序放在奇数下标索引中
        for (int i = result.length - 1; i >= (result.length % 2 == 0 ? result.length / 2 : result.length / 2 + 1); i--) {
            nums[index2] = result[i];
            index2 = index2 + 2;
        }
    }

    /**
     * 基数排序+模拟
     * nums由小到大排序，前一半元素(数组长度为奇数，则前一半元素包含中间元素)按逆序放在偶数下标索引中，后一半元素按逆序放在奇数下标索引中
     * 注意：前一半元素(数组长度为奇数，则前一半元素包含中间元素)按顺序放在偶数下标索引中，后一半元素按顺序放在奇数下标索引中不正确
     * 例如：nums = [1,2,4,4,4,6]
     * 前一半元素和后一半元素如果都按顺序放置，则得到[1,4,2,4,4,6]，不正确
     * 前一半元素和后一半元素如果都按逆序放置，则得到[4,6,2,4,1,4]，正确
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param nums
     */
    public void wiggleSort4(int[] nums) {
        //nums的拷贝数组
        int[] arr = Arrays.copyOfRange(nums, 0, nums.length);

        int max = arr[0];

        for (int num : arr) {
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

        for (int i = 0; i < maxLen; i++) {
            List<List<Integer>> radix = new ArrayList<>();

            for (int j = 0; j < 10; j++) {
                radix.add(new ArrayList<>());
            }

            for (int num : arr) {
                int index = num / bitCount % 10;
                radix.get(index).add(num);
            }

            int index = 0;

            for (List<Integer> bucket : radix) {
                for (int num : bucket) {
                    arr[index] = num;
                    index++;
                }
            }

            bitCount = bitCount * 10;
        }

        //偶数下标索引
        int index1 = 0;
        //奇数下标索引
        int index2 = 1;

        //前一半元素(数组长度为奇数，则前一半元素包含中间元素)按逆序放在偶数下标索引中
        for (int i = (arr.length % 2 == 0 ? arr.length / 2 - 1 : arr.length / 2); i >= 0; i--) {
            nums[index1] = arr[i];
            index1 = index1 + 2;
        }

        //后一半元素按逆序放在奇数下标索引中
        for (int i = arr.length - 1; i >= (arr.length % 2 == 0 ? arr.length / 2 : arr.length / 2 + 1); i--) {
            nums[index2] = arr[i];
            index2 = index2 + 2;
        }
    }

    /**
     * 快排划分+三指针+模拟
     * 1、快排划分确定中间元素midValue，保证中间元素左边的元素都小于等于midValue，中间元素右边的元素都大于等于midValue
     * 2、三指针将数组中元素划分为三部分，即小于midValue的元素放在左边，等于midValue的元素放在中间，大于midValue的元素放在右边
     * 3、前一半元素(数组长度为奇数，则前一半元素包含中间元素)按逆序放在偶数下标索引中，后一半元素按逆序放在奇数下标索引中
     * 注意：前一半元素(数组长度为奇数，则前一半元素包含中间元素)按顺序放在偶数下标索引中，后一半元素按顺序放在奇数下标索引中不正确
     * 例如：nums = [1,2,4,4,4,6]
     * 前一半元素和后一半元素如果都按顺序放置，则得到[1,4,2,4,4,6]，不正确
     * 前一半元素和后一半元素如果都按逆序放置，则得到[4,6,2,4,1,4]，正确
     * 时间复杂度O(n)，空间复杂度O(n) (需要O(n)的额外数组)
     *
     * @param nums
     */
    public void wiggleSort5(int[] nums) {
        //nums的拷贝数组
        int[] arr = Arrays.copyOfRange(nums, 0, nums.length);

        //1、快排划分确定中间元素midValue，保证中间元素左边的元素都小于等于midValue，中间元素右边的元素都大于等于midValue
        //arr中间元素，即由小到大排序后arr的第arr.length/2+1大元素
        int midValue = findKthLargest(arr, arr.length / 2 + 1);

        //2、三指针将数组中元素划分为三部分，即小于midValue的元素放在左边，等于midValue的元素放在中间，大于midValue的元素放在右边
        threeWayPartition(arr, midValue);

        //3、前一半元素(数组长度为奇数，则前一半元素包含中间元素)按逆序放在偶数下标索引中，后一半元素按逆序放在奇数下标索引中

        //偶数下标索引
        int index1 = 0;
        //奇数下标索引
        int index2 = 1;

        //前一半元素(数组长度为奇数，则前一半元素包含中间元素)按逆序放在偶数下标索引中
        for (int i = (arr.length % 2 == 0 ? arr.length / 2 - 1 : arr.length / 2); i >= 0; i--) {
            nums[index1] = arr[i];
            index1 = index1 + 2;
        }

        //后一半元素按逆序放在奇数下标索引中
        for (int i = arr.length - 1; i >= (arr.length % 2 == 0 ? arr.length / 2 : arr.length / 2 + 1); i--) {
            nums[index2] = arr[i];
            index2 = index2 + 2;
        }
    }

    /**
     * 返回nums中第k大元素，同时保证第k大元素左边的元素都小于等于k，第k大元素右边的元素都大于等于k
     * 时间复杂度O(n)，空间复杂度O(logn)
     *
     * @param nums
     * @param k
     * @return
     */
    private int findKthLargest(int[] nums, int k) {
        int left = 0;
        int right = nums.length - 1;
        int pivot = partition(nums, left, right);

        //当前基准不是第k大元素
        while (pivot != nums.length - k) {
            if (pivot < nums.length - k) {
                left = pivot + 1;
                pivot = partition(nums, left, right);
            } else {
                right = pivot - 1;
                pivot = partition(nums, left, right);
            }
        }

        return nums[pivot];
    }

    /**
     * 三指针，三向切分
     * 将数组中元素划分为三部分，即小于midValue的元素放在左边，等于midValue的元素放在中间，大于midValue的元素放在右边
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param arr
     * @param midValue
     */
    private void threeWayPartition(int[] arr, int midValue) {
        //要交换小于midValue的下标索引指针
        int i = 0;
        //要交换大于midValue的下标索引指针
        int j = arr.length - 1;
        //当前遍历到的下标索引指针
        int k = 0;

        while (k <= j) {
            //当前元素小于midValue，arr[i]和arr[k]交换，i、k右移
            if (arr[k] < midValue) {
                swap(arr, i, k);
                i++;
                k++;
            } else if (arr[k] > midValue) {
                //当前元素大于midValue，arr[j]和arr[k]交换，j左移
                swap(arr, j, k);
                j--;
            } else {
                //当前元素等于midValue，k右移
                k++;
            }
        }
    }

    private int partition(int[] nums, int left, int right) {
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

    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private void heapSort(int[] arr) {
        for (int i = arr.length / 2 - 1; i >= 0; i--) {
            heapify(arr, i, arr.length);
        }

        for (int i = arr.length - 1; i > 0; i--) {
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            heapify(arr, 0, i);
        }
    }

    private void heapify(int[] arr, int i, int heapSize) {
        int index = i;
        int leftIndex = i * 2 + 1;
        int rightIndex = i * 2 + 2;

        if (leftIndex < heapSize && arr[index] < arr[leftIndex]) {
            index = leftIndex;
        }

        if (rightIndex < heapSize && arr[index] < arr[rightIndex]) {
            index = rightIndex;
        }

        if (index != i) {
            int temp = arr[i];
            arr[i] = arr[index];
            arr[index] = temp;

            heapify(arr, index, heapSize);
        }
    }
}
