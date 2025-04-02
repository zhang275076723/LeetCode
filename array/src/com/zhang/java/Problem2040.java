package com.zhang.java;

/**
 * @Date 2024/11/7 08:47
 * @Author zsy
 * @Description 两个有序数组的第 K 小乘积 类比Problem668 类比Problem373、Problem378、Problem644、Problem668、Problem719、Problem786、Problem1439、Problem1508、Problem2386 二分查找类比
 * 给你两个 从小到大排好序 且下标从 0 开始的整数数组 nums1 和 nums2 以及一个整数 k ，
 * 请你返回第 k （从 1 开始编号）小的 nums1[i] * nums2[j] 的乘积，
 * 其中 0 <= i < nums1.length 且 0 <= j < nums2.length 。
 * <p>
 * 输入：nums1 = [2,5], nums2 = [3,4], k = 2
 * 输出：8
 * 解释：第 2 小的乘积计算如下：
 * - nums1[0] * nums2[0] = 2 * 3 = 6
 * - nums1[0] * nums2[1] = 2 * 4 = 8
 * 第 2 小的乘积为 8 。
 * <p>
 * 输入：nums1 = [-4,-2,0,3], nums2 = [2,4], k = 6
 * 输出：0
 * 解释：第 6 小的乘积计算如下：
 * - nums1[0] * nums2[1] = (-4) * 4 = -16
 * - nums1[0] * nums2[0] = (-4) * 2 = -8
 * - nums1[1] * nums2[1] = (-2) * 4 = -8
 * - nums1[1] * nums2[0] = (-2) * 2 = -4
 * - nums1[2] * nums2[0] = 0 * 2 = 0
 * - nums1[2] * nums2[1] = 0 * 4 = 0
 * 第 6 小的乘积为 0 。
 * <p>
 * 输入：nums1 = [-2,-1,0,1,2], nums2 = [-3,-1,2,4,5], k = 3
 * 输出：-6
 * 解释：第 3 小的乘积计算如下：
 * - nums1[0] * nums2[4] = (-2) * 5 = -10
 * - nums1[0] * nums2[3] = (-2) * 4 = -8
 * - nums1[4] * nums2[0] = 2 * (-3) = -6
 * 第 3 小的乘积为 -6 。
 * <p>
 * 1 <= nums1.length, nums2.length <= 5 * 10^4
 * -10^5 <= nums1[i], nums2[j] <= 10^5
 * 1 <= k <= nums1.length * nums2.length
 * nums1 和 nums2 都是从小到大排好序的。
 */
public class Problem2040 {
    public static void main(String[] args) {
        Problem2040 problem2040 = new Problem2040();
//        int[] nums1 = {-4, -2, 0, 3};
//        int[] nums2 = {2, 4};
//        long k = 6;
//        int[] nums1 = {-3, -1, 5, 6};
//        int[] nums2 = {-10, -7, -6, -5, -5, -4, -1, 7, 8};
//        long k = 28;
//        int[] nums1 = {1, 6};
//        int[] nums2 = {-10, -10, -5, -4, -3, -1};
//        long k = 10;
        int[] nums1 = {-8, -8, 3, 7};
        int[] nums2 = {-1};
        long k = 3;
        System.out.println(problem2040.kthSmallestProduct(nums1, nums2, k));
    }

    /**
     * 分类讨论+二分查找+双指针
     * 二分查找确定nums1和nums2中负数的个数negative1和negative2，则negative1和negative2将nums1和nums2构成的二维数组分为四部分，
     * 左上：nums1[0]-nums1[negative1-1]和nums2[0]-nums2[negative2-1]乘积为正数
     * 左下：nums1[negative1]-nums1[m-1]和nums2[0]-nums2[negative2-1]乘积为零和负数
     * 右上：nums1[0]-nums1[negative1-1]和nums2[negative2]-nums2[n-1]乘积为零和负数
     * 右下：nums1[negative1]-nums1[m-1]和nums2[negative2]-nums2[n-1]乘积为零和正数
     * 对[left,right]进行二分查找，left为nums1[i]*nums2[j]的最小值，right为nums1[i]*nums2[j]的最大值，统计nums1[i]*nums2[j]小于等于mid的个数count，
     * 如果count小于k，则第k小乘积在mid右边，left=mid+1；
     * 如果count大于等于k，则第k小乘积在mid或mid左边，right=mid
     * 时间复杂度O(logm+logn+(m+n)log(max(nums1[i]*nums2[j])))=O(logm+logn+m+n)，空间复杂度O(1) (m=nums1.length，n=nums2.length)
     *
     * @param nums1
     * @param nums2
     * @param k
     * @return
     */
    public long kthSmallestProduct(int[] nums1, int[] nums2, long k) {
        //nums1中负数的个数
        int negative1 = 0;
        //nums2中负数的个数
        int negative2 = 0;

        int l = 0;
        int r = nums1.length - 1;
        int m;

        //二分查找确定nums1中负数的个数
        while (l <= r) {
            m = l + ((r - l) >> 1);

            if (nums1[m] < 0) {
                negative1 = m + 1;
                l = m + 1;
            } else {
                r = m - 1;
            }
        }

        l = 0;
        r = nums2.length - 1;

        //二分查找确定nums2中负数的个数
        while (l <= r) {
            m = l + ((r - l) >> 1);

            if (nums2[m] < 0) {
                negative2 = m + 1;
                l = m + 1;
            } else {
                r = m - 1;
            }
        }

        long a = (long) nums1[0] * nums2[0];
        long b = (long) nums1[nums1.length - 1] * nums2[nums2.length - 1];
        long c = (long) nums1[0] * nums2[nums2.length - 1];
        long d = (long) nums1[nums1.length - 1] * nums2[0];

        //nums1[i]*nums2[j]的最小值
        long left = Math.min(Math.min(a, b), Math.min(c, d));
        //nums1[i]*nums2[j]的最大值
        long right = Math.max(Math.max(a, b), Math.max(c, d));
        long mid;

        while (left < right) {
            mid = left + ((right - left) >> 1);

            //nums1[i]*nums2[j]小于等于mid的个数
            //使用long，避免int溢出
            long count = 0;
            //左上部分小于等于mid的个数
            count = count + getLeftUpLessEqualThanNumCount(nums1, nums2, 0, negative1 - 1, 0, negative2 - 1, mid);
            //左下部分小于等于mid的个数
            count = count + getLeftDownLessEqualThanNumCount(nums1, nums2, negative1, nums1.length - 1, 0, negative2 - 1, mid);
            //右上部分小于等于mid的个数
            count = count + getRightUpLessEqualThanNumCount(nums1, nums2, 0, negative1 - 1, negative2, nums2.length - 1, mid);
            //右下部分小于等于mid的个数
            count = count + getRightDownLessEqualThanNumCount(nums1, nums2, negative1, nums1.length - 1, negative2, nums2.length - 1, mid);

            if (count < k) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        return left;
    }

    /**
     * 双指针获取左上部分nums1[nums1Left]-nums1[nums1Right]和nums2[nums2Left]-nums2[nums2Right]中nums1[i]*nums2[j]小于等于num的个数
     * 时间复杂度O(m+n)，空间复杂度O(1) (m=nums1.length，n=nums2.length)
     *
     * @param nums1
     * @param nums2
     * @param nums1Left
     * @param nums1Right
     * @param nums2Left
     * @param nums2Right
     * @param num
     * @return
     */
    private long getLeftUpLessEqualThanNumCount(int[] nums1, int[] nums2, int nums1Left, int nums1Right,
                                                int nums2Left, int nums2Right, long num) {
        //不合法，直接返回0
        if (nums1Left > nums1Right || nums2Left > nums2Right) {
            return 0;
        }

        long count = 0;
        int i = nums1Left;
        int j = nums2Right;

        //从右上到左下遍历
        while (i <= nums1Right && j >= nums2Left) {
            //使用long，避免int相乘溢出
            while (i <= nums1Right && (long) nums1[i] * nums2[j] > num) {
                i++;
            }

            count = count + (nums1Right - i + 1);
            j--;
        }

        return count;
    }

    /**
     * 双指针获取左下部分nums1[nums1Left]-nums1[nums1Right]和nums2[nums2Left]-nums2[nums2Right]中nums1[i]*nums2[j]小于等于num的个数
     * 时间复杂度O(m+n)，空间复杂度O(1) (m=nums1.length，n=nums2.length)
     *
     * @param nums1
     * @param nums2
     * @param nums1Left
     * @param nums1Right
     * @param nums2Left
     * @param nums2Right
     * @param num
     * @return
     */
    private long getLeftDownLessEqualThanNumCount(int[] nums1, int[] nums2, int nums1Left, int nums1Right,
                                                  int nums2Left, int nums2Right, long num) {
        //不合法，直接返回0
        if (nums1Left > nums1Right || nums2Left > nums2Right) {
            return 0;
        }

        long count = 0;
        int i = nums1Left;
        int j = nums2Left;

        //从左上到右下遍历
        while (i <= nums1Right && j <= nums2Right) {
            //使用long，避免int相乘溢出
            while (i <= nums1Right && (long) nums1[i] * nums2[j] > num) {
                i++;
            }

            count = count + (nums1Right - i + 1);
            j++;
        }

        return count;
    }

    /**
     * 双指针获取右上部分nums1[nums1Left]-nums1[nums1Right]和nums2[nums2Left]-nums2[nums2Right]中nums1[i]*nums2[j]小于等于num的个数
     * 时间复杂度O(m+n)，空间复杂度O(1) (m=nums1.length，n=nums2.length)
     *
     * @param nums1
     * @param nums2
     * @param nums1Left
     * @param nums1Right
     * @param nums2Left
     * @param nums2Right
     * @param num
     * @return
     */
    private long getRightUpLessEqualThanNumCount(int[] nums1, int[] nums2, int nums1Left, int nums1Right,
                                                 int nums2Left, int nums2Right, long num) {
        //不合法，直接返回0
        if (nums1Left > nums1Right || nums2Left > nums2Right) {
            return 0;
        }

        long count = 0;
        int i = nums1Right;
        int j = nums2Right;

        //从右下到左上遍历
        while (i >= nums1Left && j >= nums2Left) {
            //使用long，避免int相乘溢出
            while (i >= nums1Left && (long) nums1[i] * nums2[j] > num) {
                i--;
            }

            count = count + (i - nums1Left + 1);
            j--;
        }

        return count;
    }

    /**
     * 双指针获取右下部分nums1[nums1Left]-nums1[nums1Right]和nums2[nums2Left]-nums2[nums2Right]中nums1[i]*nums2[j]小于等于num的个数
     * 时间复杂度O(m+n)，空间复杂度O(1) (m=nums1.length，n=nums2.length)
     *
     * @param nums1
     * @param nums2
     * @param nums1Left
     * @param nums1Right
     * @param nums2Left
     * @param nums2Right
     * @param num
     * @return
     */
    private long getRightDownLessEqualThanNumCount(int[] nums1, int[] nums2, int nums1Left, int nums1Right,
                                                   int nums2Left, int nums2Right, long num) {
        //不合法，直接返回0
        if (nums1Left > nums1Right || nums2Left > nums2Right) {
            return 0;
        }

        long count = 0;
        int i = nums1Right;
        int j = nums2Left;

        //从左下到右上遍历
        while (i >= nums1Left && j <= nums2Right) {
            //使用long，避免int相乘溢出
            while (i >= nums1Left && (long) nums1[i] * nums2[j] > num) {
                i--;
            }

            count = count + (i - nums1Left + 1);
            j++;
        }

        return count;
    }
}
