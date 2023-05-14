package com.zhang.java;


/**
 * @Date 2023/5/14 08:49
 * @Author zsy
 * @Description 计算数组的小和 快手面试题 归并排序类比Problem23、Problem148、Problem315、Problem327、Problem493、Offer51
 * 在一个数组中，每一个数左边小于等于当前数的数累加起来，叫做这个数组的小和。求一个数组的小和。
 * 输入：arr = [1,3,5,2,4,6]
 * 输出：27
 * 解释：
 * 1左边小于等于1的数，没有；
 * 3左边小于等于3的数，1；
 * 5左边小于等于5的数，1、3；
 * 2左边小于等于2的数，1；
 * 4左边小于等于4的数，1、3、2；
 * 6左边小于等于6的数，1、3、5、2、4；
 * 所以小和为1+1+3+1+1+3+2+1+3+5+2+4=27
 * <p>
 * 0 < arr.length <= 10^5
 * -100 <= arr[i] <= 100
 */
public class CalculateSmallSum {
    public static void main(String[] args) {
        CalculateSmallSum calculateSmallSum = new CalculateSmallSum();
        int[] arr = {1, 3, 5, 2, 4, 6};
        System.out.println(calculateSmallSum.f(arr));
        System.out.println(calculateSmallSum.f2(arr));
    }

    /**
     * 暴力
     * 时间复杂度O(n^2)，空间复杂度O(1)
     *
     * @param arr
     * @return
     */
    public long f(int[] arr) {
        //使用long，避免int溢出
        long sum = 0;

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < i; j++) {
                if (arr[j] <= arr[i]) {
                    sum = sum + arr[j];
                }
            }
        }

        return sum;
    }

    /**
     * 归并排序
     * 在合并时，如果左边数组当前元素arr[i]小于等于右边数组当前元素arr[j]，
     * 则左边数组当前元素都小于等于右边数组当前元素到末尾元素，
     * 需要加上右边数组当前位置到末尾个arr[i]，作为右边数组当前元素到末尾元素的小和
     * 时间复杂度O(nlogn)，空间复杂度O(n)
     *
     * @param arr
     * @return
     */
    public long f2(int[] arr) {
        return mergeSort(arr, 0, arr.length - 1, new int[arr.length]);
    }

    private long mergeSort(int[] arr, int left, int right, int[] tempArr) {
        if (left >= right) {
            return 0;
        }

        //使用long，避免int溢出
        long sum = 0;
        int mid = left + ((right - left) >> 1);

        sum = sum + mergeSort(arr, left, mid, tempArr);
        sum = sum + mergeSort(arr, mid + 1, right, tempArr);
        sum = sum + merge(arr, left, mid, right, tempArr);

        return sum;
    }

    private long merge(int[] arr, int left, int mid, int right, int[] tempArr) {
        //使用long，避免int溢出
        long sum = 0;
        int i = left;
        int j = mid + 1;
        int k = left;

        while (i <= mid && j <= right) {
            if (arr[i] <= arr[j]) {
                sum = sum + (long) (right - j + 1) * arr[i];
                tempArr[k] = arr[i];
                i++;
                k++;
            } else {
                tempArr[k] = arr[j];
                j++;
                k++;
            }
        }

        while (i <= mid) {
            tempArr[k] = arr[i];
            i++;
            k++;
        }

        while (j <= right) {
            tempArr[k] = arr[j];
            j++;
            k++;
        }

        for (k = left; k <= right; k++) {
            arr[k] = tempArr[k];
        }

        return sum;
    }
}
