package com.zhang.java;

import java.util.Random;
import java.util.Stack;

/**
 * @Date 2024/11/13 08:42
 * @Author zsy
 * @Description 奇偶跳 类比Problem406、Problem1340 跳跃问题类比Problem45、Problem55、Problem403、Problem1306、Problem1340、Problem1345、Problem1377、Problem1654、Problem1696、Problem1871、Problem2297、Problem2498、Problem2770、LCP09 单调栈类比
 * 给定一个整数数组 A，你可以从某一起始索引出发，跳跃一定次数。
 * 在你跳跃的过程中，第 1、3、5... 次跳跃称为奇数跳跃，而第 2、4、6... 次跳跃称为偶数跳跃。
 * 你可以按以下方式从索引 i 向后跳转到索引 j（其中 i < j）：
 * 在进行奇数跳跃时（如，第 1，3，5... 次跳跃），你将会跳到索引 j，使得 A[i] <= A[j]，A[j] 是可能的最小值。
 * 如果存在多个这样的索引 j，你只能跳到满足要求的最小索引 j 上。
 * 在进行偶数跳跃时（如，第 2，4，6... 次跳跃），你将会跳到索引 j，使得 A[i] >= A[j]，A[j] 是可能的最大值。
 * 如果存在多个这样的索引 j，你只能跳到满足要求的最小索引 j 上。
 * （对于某些索引 i，可能无法进行合乎要求的跳跃。）
 * 如果从某一索引开始跳跃一定次数（可能是 0 次或多次），就可以到达数组的末尾（索引 A.length - 1），
 * 那么该索引就会被认为是好的起始索引。
 * 返回好的起始索引的数量。
 * <p>
 * 输入：[10,13,12,14,15]
 * 输出：2
 * 解释：
 * 从起始索引 i = 0 出发，我们可以跳到 i = 2，（因为 A[2] 是 A[1]，A[2]，A[3]，A[4] 中大于或等于 A[0] 的最小值），
 * 然后我们就无法继续跳下去了。
 * 从起始索引 i = 1 和 i = 2 出发，我们可以跳到 i = 3，然后我们就无法继续跳下去了。
 * 从起始索引 i = 3 出发，我们可以跳到 i = 4，到达数组末尾。
 * 从起始索引 i = 4 出发，我们已经到达数组末尾。
 * 总之，我们可以从 2 个不同的起始索引（i = 3, i = 4）出发，通过一定数量的跳跃到达数组末尾。
 * <p>
 * 输入：[2,3,1,1,4]
 * 输出：3
 * 解释：
 * 从起始索引 i=0 出发，我们依次可以跳到 i = 1，i = 2，i = 3：
 * 在我们的第一次跳跃（奇数）中，我们先跳到 i = 1，因为 A[1] 是（A[1]，A[2]，A[3]，A[4]）中大于或等于 A[0] 的最小值。
 * 在我们的第二次跳跃（偶数）中，我们从 i = 1 跳到 i = 2，因为 A[2] 是（A[2]，A[3]，A[4]）中小于或等于 A[1] 的最大值。
 * A[3] 也是最大的值，但 2 是一个较小的索引，所以我们只能跳到 i = 2，而不能跳到 i = 3。
 * 在我们的第三次跳跃（奇数）中，我们从 i = 2 跳到 i = 3，因为 A[3] 是（A[3]，A[4]）中大于或等于 A[2] 的最小值。
 * 我们不能从 i = 3 跳到 i = 4，所以起始索引 i = 0 不是好的起始索引。
 * 类似地，我们可以推断：
 * 从起始索引 i = 1 出发， 我们跳到 i = 4，这样我们就到达数组末尾。
 * 从起始索引 i = 2 出发， 我们跳到 i = 3，然后我们就不能再跳了。
 * 从起始索引 i = 3 出发， 我们跳到 i = 4，这样我们就到达数组末尾。
 * 从起始索引 i = 4 出发，我们已经到达数组末尾。
 * 总之，我们可以从 3 个不同的起始索引（i = 1, i = 3, i = 4）出发，通过一定数量的跳跃到达数组末尾。
 * <p>
 * 输入：[5,1,3,4,2]
 * 输出：3
 * 解释：
 * 我们可以从起始索引 1，2，4 出发到达数组末尾。
 * <p>
 * 1 <= A.length <= 20000
 * 0 <= A[i] < 100000
 */
public class Problem975 {
    public static void main(String[] args) {
        Problem975 problem975 = new Problem975();
        int[] arr = {10, 13, 12, 14, 15};
        System.out.println(problem975.oddEvenJumps(arr));
    }

    /**
     * 排序+单调栈+动态规划
     * odd[i]：从下标索引i以"奇-偶-奇"方式跳跃，能否跳跃到末尾下标索引n-1
     * even[i]：从下标索引i以"偶-奇-偶"方式跳跃，能否跳跃到末尾下标索引n-1
     * odd[i] = even[j] (i < j，arr[j]为arr[i]右边大于等于arr[i]的最小值)
     * even[i] = odd[j] (i < j，arr[j]为arr[i]右边小于等于arr[i]的最大值)
     * 一维数组为arr[i]，二维数组为arr[i]的下标索引i，
     * 一维由小到大排序，二维由小到大排序，对下标索引i通过单调递减栈获取arr[i]右边大于等于arr[i]的最小值；
     * 一维由大到小排序，二维由小到大排序，对下标索引i通过单调递减栈获取arr[i]右边小于等于arr[i]的最大值
     * 时间复杂度O(nlogn)，空间复杂度O(n)
     *
     * @param arr
     * @return
     */
    public int oddEvenJumps(int[] arr) {
        //一维数组为arr[i]，二维数组为arr[i]的下标索引i
        int[][] tempArr = new int[arr.length][2];
        //arr[i]右边大于等于arr[i]的最小值在arr中的下标索引数组
        int[] biggerArr = new int[arr.length];
        //arr[i]右边小于等于arr[i]的最大值在arr中的下标索引数组
        int[] smallerArr = new int[arr.length];

        for (int i = 0; i < arr.length; i++) {
            tempArr[i] = new int[]{arr[i], i};
            //初始化为-1，表示arr[i]右边不存在大于等于arr[i]的最小值在arr中的下标索引
            biggerArr[i] = -1;
            //初始化为-1，表示arr[i]右边不存在小于等于arr[i]的最大值在arr中的下标索引
            smallerArr[i] = -1;
        }

        //一维由小到大排序，二维由小到大排序
        quickSort(tempArr, 0, tempArr.length - 1);

        //单调递减栈
        Stack<Integer> stack = new Stack<>();

        //对下标索引i通过单调递减栈获取arr[i]右边大于等于arr[i]的最小值
        for (int i = 0; i < tempArr.length; i++) {
            while (!stack.isEmpty() && stack.peek() < tempArr[i][1]) {
                int index = stack.pop();
                biggerArr[index] = tempArr[i][1];
            }

            stack.push(tempArr[i][1]);
        }

        //一维由大到小排序，二维由小到大排序
        mergeSort(tempArr, 0, tempArr.length - 1, new int[tempArr.length][2]);

        //单调递减栈清空，用于求smallerArr
        stack.clear();

        //对下标索引i通过单调递减栈获取arr[i]右边小于等于arr[i]的最大值
        for (int i = 0; i < tempArr.length; i++) {
            while (!stack.isEmpty() && stack.peek() < tempArr[i][1]) {
                int index = stack.pop();
                smallerArr[index] = tempArr[i][1];
            }

            stack.push(tempArr[i][1]);
        }

        boolean[] odd = new boolean[arr.length];
        boolean[] even = new boolean[arr.length];
        //dp初始化
        odd[arr.length - 1] = true;
        even[arr.length - 1] = true;

        for (int i = arr.length - 2; i >= 0; i--) {
            //biggerArr[i]为arr[i]右边大于等于arr[i]的最小值在arr中的下标索引
            if (biggerArr[i] != -1) {
                odd[i] = even[biggerArr[i]];
            }

            //smallerArr[i]为arr[i]右边小于等于arr[i]的最大值在arr中的下标索引
            if (smallerArr[i] != -1) {
                even[i] = odd[smallerArr[i]];
            }
        }

        int count = 0;

        //从下标索引i以"奇-偶-奇"方式跳跃，能跳跃到末尾下标索引n-1，则为好的起始索引
        for (int i = 0; i < arr.length; i++) {
            if (odd[i]) {
                count++;
            }
        }

        return count;
    }

    /**
     * 一维由小到大排序，二维由小到大排序
     *
     * @param arr
     * @param left
     * @param right
     */
    private void quickSort(int[][] arr, int left, int right) {
        if (left >= right) {
            return;
        }

        int pivot = partition(arr, left, right);
        quickSort(arr, left, pivot - 1);
        quickSort(arr, pivot + 1, right);
    }

    /**
     * 一维由大到小排序，二维由小到大排序
     *
     * @param arr
     * @param left
     * @param right
     * @param tempArr
     */
    private void mergeSort(int[][] arr, int left, int right, int[][] tempArr) {
        if (left >= right) {
            return;
        }

        int mid = left + ((right - left) >> 1);

        mergeSort(arr, left, mid, tempArr);
        mergeSort(arr, mid + 1, right, tempArr);
        merge(arr, left, mid, right, tempArr);
    }

    private int partition(int[][] arr, int left, int right) {
        int randomIndex = new Random().nextInt(right - left + 1) + left;

        int[] value = arr[left];
        arr[left] = arr[randomIndex];
        arr[randomIndex] = value;

        int[] temp = arr[left];

        while (left < right) {
            while (left < right && (arr[right][0] > temp[0] || (arr[right][0] == temp[0] && arr[right][1] >= temp[1]))) {
                right--;
            }

            arr[left] = arr[right];

            while (left < right && (arr[left][0] < temp[0] || (arr[left][0] == temp[0] && arr[left][1] <= temp[1]))) {
                left++;
            }

            arr[right] = arr[left];
        }

        arr[left] = temp;

        return left;
    }

    private void merge(int[][] arr, int left, int mid, int right, int[][] tempArr) {
        int i = left;
        int j = mid + 1;
        int k = left;

        while (i <= mid && j <= right) {
            if (arr[i][0] > arr[j][0] || (arr[i][0] == arr[j][0] && arr[i][1] < arr[j][1])) {
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
    }
}
