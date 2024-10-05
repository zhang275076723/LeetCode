package com.zhang.java;

/**
 * @Date 2022/9/4 21:27
 * @Author zsy
 * @Description 找数组执行k次减x之后的尽可能小的最大值 网易机试题 二分查找类比
 * 一个数组中选一个数减去x，执行k次之后，返回数组中尽可能小的最大值。
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
//        System.out.println(findMaxArrayMinAfterKMinus.find(arr, k, x));
        System.out.println(findMaxArrayMinAfterKMinus.find2(arr, k, x));
    }

    /**
     * 手动实现大根堆
     * 取出堆顶元素，减去x，再入堆，执行k次，堆顶元素即为尽可能小的最大值
     * 时间复杂度O(nlogn+klogn)，空间复杂度O(n) (n = arr.length)
     *
     * @param arr
     * @param k
     * @param x
     * @return
     */
    public int find(int[] arr, int k, int x) {
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
     * 对[left,right]进行二分查找，left为arr最小值-kx，right为arr最大值，统计数组中元素都小于等于mid，减去x的次数count，
     * 如果count大于k，则数组中元素减去x，执行k次之后的最大值在mid右边，left=mid+1；
     * 如果count小于等于k，则数组中元素减去x，执行k次之后的最大值在mid或mid左边，right=mid
     * 时间复杂度O(n*log(max(arr[i])-(min(arr[i])-kx)))=O(n)，空间复杂度O(1)
     *
     * @param arr
     * @param k
     * @param x
     * @return
     */
    public int find2(int[] arr, int k, int x) {
        int min = arr[0];
        int max = arr[0];

        for (int num : arr) {
            min = Math.min(min, num);
            max = Math.max(max, num);
        }

        int left = min - k * x;
        int right = max;
        int mid;

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

    private void heapify(int[] arr, int i, int heapSize) {
        int index = i;
        int leftIndex = i * 2 + 1;
        int rightIndex = i * 2 + 2;

        if (leftIndex < heapSize && arr[leftIndex] > arr[index]) {
            index = leftIndex;
        }

        if (rightIndex < heapSize && arr[rightIndex] > arr[index]) {
            index = rightIndex;
        }

        if (index != i) {
            int temp = arr[index];
            arr[index] = arr[i];
            arr[i] = temp;

            heapify(arr, index, heapSize);
        }
    }
}
