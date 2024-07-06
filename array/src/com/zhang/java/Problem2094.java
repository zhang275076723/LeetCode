package com.zhang.java;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @Date 2024/8/12 08:21
 * @Author zsy
 * @Description 找出 3 位偶数
 * 给你一个整数数组 digits ，其中每个元素是一个数字（0 - 9）。
 * 数组中可能存在重复元素。
 * 你需要找出 所有 满足下述条件且 互不相同 的整数：
 * 该整数由 digits 中的三个元素按 任意 顺序 依次连接 组成。
 * 该整数不含 前导零
 * 该整数是一个 偶数
 * 例如，给定的 digits 是 [1, 2, 3] ，整数 132 和 312 满足上面列出的全部条件。
 * 将找出的所有互不相同的整数按 递增顺序 排列，并以数组形式返回。
 * <p>
 * 输入：digits = [2,1,3,0]
 * 输出：[102,120,130,132,210,230,302,310,312,320]
 * 解释：
 * 所有满足题目条件的整数都在输出数组中列出。
 * 注意，答案数组中不含有 奇数 或带 前导零 的整数。
 * <p>
 * 输入：digits = [2,2,8,8,2]
 * 输出：[222,228,282,288,822,828,882]
 * 解释：
 * 同样的数字（0 - 9）在构造整数时可以重复多次，重复次数最多与其在 digits 中出现的次数一样。
 * 在这个例子中，数字 8 在构造 288、828 和 882 时都重复了两次。
 * <p>
 * 输入：digits = [3,7,5]
 * 输出：[]
 * 解释：
 * 使用给定的 digits 无法构造偶数。
 * <p>
 * 3 <= digits.length <= 100
 * 0 <= digits[i] <= 9
 */
public class Problem2094 {
    public static void main(String[] args) {
        Problem2094 problem2094 = new Problem2094();
        int[] digits = {2, 1, 3, 0};
        System.out.println(Arrays.toString(problem2094.findEvenNumbers(digits)));
    }

    /**
     * 哈希表
     * 时间复杂度O(n^3+mlogm)，空间复杂度O(m) (n=digits.length，m=set.size())
     *
     * @param digits
     * @return
     */
    public int[] findEvenNumbers(int[] digits) {
        //去重
        Set<Integer> set = new HashSet<>();

        for (int i = 0; i < digits.length; i++) {
            for (int j = 0; j < digits.length; j++) {
                for (int k = 0; k < digits.length; k++) {
                    //digit中某一个元素被重复使用，或者存在前导0，或者digits[k]不是偶数，则不是3位偶数，直接进行下次循环
                    if (i == j || i == k || j == k || digits[i] == 0 || digits[k] % 2 != 0) {
                        continue;
                    }

                    int num = digits[i] * 100 + digits[j] * 10 + digits[k];
                    set.add(num);
                }
            }
        }

        int[] result = new int[set.size()];
        int index = 0;

        for (int num : set) {
            result[index] = num;
            index++;
        }

        //由小到大排序
        heapSort(result);

        return result;
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
        int leftIndex = 2 * i + 1;
        int rightIndex = 2 * i + 2;

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
