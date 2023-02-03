package com.zhang.java;

/**
 * @Date 2022/9/4 21:27
 * @Author zsy
 * @Description 找数组执行k次减x之后的尽可能小的最大值 网易机试题 二分查找类比Problem4、Problem287、Problem378、Problem410、Problem658、Problem1482
 * 一个数组中选一个数减去x，执行k次之后，返回数组中尽可能小的最大值
 * <p>
 * 输入：arr = [1,0,7], k = 2, x = 3
 * 输出：1
 * <p>
 * 1 <= arr.length <= 10^5
 * 1 <= arr[i], k, x <= 10^9
 */
public class FindMaxArrayMinAfterKMinus {
    public static void main(String[] args) {
        FindMaxArrayMinAfterKMinus findMaxArrayMinAfterKMinus = new FindMaxArrayMinAfterKMinus();
        int[] arr = {1, 0, 7};
        int k = 2;
        int x = 3;
//        System.out.println(findMaxArrayMinAfterKMinus.findMaxArrayMinAfterKMinus(arr, k, x));
        System.out.println(findMaxArrayMinAfterKMinus.findMaxArrayMinAfterKMinus2(arr, k, x));
    }

    /**
     * 手动实现大根堆
     * 取出堆顶元素，减去x，再入堆，执行k次，堆顶元素即为尽可能小的最大值
     * 时间复杂度O(klogn)，空间复杂度O(n) (n = arr.length)
     *
     * @param arr
     * @param k
     * @param x
     * @return
     */
    public int findMaxArrayMinAfterKMinus(int[] arr, int k, int x) {
        //建堆，大根堆
        for (int i = arr.length / 2 - 1; i >= 0; i--) {
            heapify(arr, i, arr.length);
        }

        //堆顶元素减x，再整堆，执行k次
        for (int i = 0; i < k; i++) {
            arr[0] = arr[0] - x;
            heapify(arr, 0, arr.length);
        }

        //大根堆堆顶元素即为减去k次x之后的最大值
        return arr[0];
    }

    /**
     * 二分查找变形，使...最大值尽可能小，就要想到二分查找
     * 对[left,right]进行二分查找，left为arr最小值减去kx，right为arr最大值，统计数组中元素都小于等于mid，减去x的次数count，
     * 如果count大于k，则数组中元素减去x，执行k次之后的最大值在mid右边，left=mid+1；
     * 如果count小于等于mid，则数组中元素减去x，执行k次之后的最大值在mid或mid左边，right=mid
     * 时间复杂度O(n*log(right-left))=O(n)，空间复杂度O(1) (n = arr.length，二分查找的范围为int范围，log(right-left)为常数)
     *
     * @param arr
     * @param k
     * @param x
     * @return
     */
    public int findMaxArrayMinAfterKMinus2(int[] arr, int k, int x) {
        //数组中最小值减去kx
        int left = Integer.MAX_VALUE;
        //数组中最大值作为右边界
        int right = arr[0];
        int mid;

        for (int num : arr) {
            left = Math.min(left, num);
            right = Math.max(right, num);
        }

        left = left - k * x;

        while (left < right) {
            mid = left + ((right - left) >> 1);

            //数组中元素都小于等于mid，需要减去x的次数
            int count = 0;

            for (int num : arr) {
                while (num > mid) {
                    num = num - x;
                    count++;
                }
            }

            if (count > k) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        return left;
    }

    private void heapify(int[] arr, int index, int heapSize) {
        int maxIndex = index;

        if (index * 2 + 1 < heapSize && arr[index * 2 + 1] > arr[maxIndex]) {
            maxIndex = index * 2 + 1;
        }

        if (index * 2 + 2 < heapSize && arr[index * 2 + 2] > arr[maxIndex]) {
            maxIndex = index * 2 + 2;
        }

        if (index != maxIndex) {
            int temp = arr[index];
            arr[index] = arr[maxIndex];
            arr[maxIndex] = temp;

            heapify(arr, maxIndex, heapSize);
        }
    }
}
