package com.zhang.java;

/**
 * @Date 2024/10/29 08:59
 * @Author zsy
 * @Description 最小化两个数组中的最大值 容斥原理类比Problem878、Problem1201、Problem3116 最大公因数和最小公倍数类比 二分查找类比
 * 给你两个数组 arr1 和 arr2 ，它们一开始都是空的。
 * 你需要往它们中添加正整数，使它们满足以下条件：
 * arr1 包含 uniqueCnt1 个 互不相同 的正整数，每个整数都 不能 被 divisor1 整除 。
 * arr2 包含 uniqueCnt2 个 互不相同 的正整数，每个整数都 不能 被 divisor2 整除 。
 * arr1 和 arr2 中的元素 互不相同 。
 * 给你 divisor1 ，divisor2 ，uniqueCnt1 和 uniqueCnt2 ，请你返回两个数组中 最大元素 的 最小值 。
 * <p>
 * 输入：divisor1 = 2, divisor2 = 7, uniqueCnt1 = 1, uniqueCnt2 = 3
 * 输出：4
 * 解释：
 * 我们可以把前 4 个自然数划分到 arr1 和 arr2 中。
 * arr1 = [1] 和 arr2 = [2,3,4] 。
 * 可以看出两个数组都满足条件。
 * 最大值是 4 ，所以返回 4 。
 * <p>
 * 输入：divisor1 = 3, divisor2 = 5, uniqueCnt1 = 2, uniqueCnt2 = 1
 * 输出：3
 * 解释：
 * arr1 = [1,2] 和 arr2 = [3] 满足所有条件。
 * 最大值是 3 ，所以返回 3 。
 * <p>
 * 输入：divisor1 = 2, divisor2 = 4, uniqueCnt1 = 8, uniqueCnt2 = 2
 * 输出：15
 * 解释：
 * 最终数组为 arr1 = [1,3,5,7,9,11,13,15] 和 arr2 = [2,6] 。
 * 上述方案是满足所有条件的最优解。
 * <p>
 * 2 <= divisor1, divisor2 <= 10^5
 * 1 <= uniqueCnt1, uniqueCnt2 < 10^9
 * 2 <= uniqueCnt1 + uniqueCnt2 <= 10^9
 */
public class Problem2513 {
    public static void main(String[] args) {
        Problem2513 problem2513 = new Problem2513();
        int divisor1 = 2;
        int divisor2 = 4;
        int uniqueCnt1 = 8;
        int uniqueCnt2 = 2;
        System.out.println(problem2513.minimizeSet(divisor1, divisor2, uniqueCnt1, uniqueCnt2));
    }

    /**
     * 二分查找+容斥原理
     * 二分查找变形，使...最大值尽可能小，就要想到二分查找
     * 对[left,right]进行二分查找，left为uniqueCnt1+uniqueCnt2，right为int最大值，判断1-mid能否得到满足要求的两个数组，
     * 如果数字1-mid不能得到满足要求的两个数组，则两个数组的最大值的最小值在mid右边，left=mid+1；
     * 如果数字1-mid能够得到满足要求的两个数组，则两个数组的最大值的最小值在mid或mid左边，right=mid
     * 小于等于n能被a整除的数的个数记为A，小于等于n能被b整除的数的个数记为B，其中|A|=n/a，|B|=n/b，
     * 则小于等于n能被a或b整除的数的个数为|A∪B|=|A|+|B|-|A∩B|=n/a+n/b-n/lcm(a,b) (容斥原理)
     * 时间复杂度O(log(2^31-1))=O(1)，空间复杂度O(1)
     *
     * @param divisor1
     * @param divisor2
     * @param uniqueCnt1
     * @param uniqueCnt2
     * @return
     */
    public int minimizeSet(int divisor1, int divisor2, int uniqueCnt1, int uniqueCnt2) {
        int left = uniqueCnt1 + uniqueCnt2;
        int right = Integer.MAX_VALUE;
        int mid;

        while (left < right) {
            mid = left + ((right - left) >> 1);

            //1-mid被divisor1整除的个数
            int divisor1Count = mid / divisor1;
            //1-mid被divisor2整除的个数
            int divisor2Count = mid / divisor2;
            //1-mid不被divisor1整除的个数
            int notDivisor1Count = mid - divisor1Count;
            //1-mid不被divisor2整除的个数
            int notDivisor2Count = mid - divisor2Count;
            //1-mid被divisor1或被divisor2整除的个数(容斥原理)
            int divisor1OrDivisor2Count = (int) (divisor1Count + divisor2Count - mid / lcm(divisor1, divisor2));
            //1-mid不被divisor1并且不被divisor2整除的个数，即这些元素可以放置在arr1或arr2中
            int notDivisor1AndNotDivisor2Count = mid - divisor1OrDivisor2Count;
            //1-mid不被divisor1但被divisor2整除的个数，即这些元素只能放置在arr1中
            int notDivisor1ButDivisor2Count = notDivisor1Count - notDivisor1AndNotDivisor2Count;
            //1-mid不被divisor2但被divisor1整除的个数，即这些元素只能放置在arr2中
            int notDivisor2ButDivisor1Count = notDivisor2Count - notDivisor1AndNotDivisor2Count;

            //1-mid不被divisor1但被divisor2整除的数字优先放置在arr1中，1-mid不被divisor2但被divisor1整除的数字优先放置在arr2中，
            //arr1和arr2中剩余需要放置的数字个数小于等于1-mid不被divisor1并且不被divisor2整除的个数，则1-mid能得到满足要求的两个数组
            if (Math.max(uniqueCnt1 - notDivisor1ButDivisor2Count, 0) + Math.max(uniqueCnt2 - notDivisor2ButDivisor1Count, 0) <= notDivisor1AndNotDivisor2Count) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }

        return left;
    }

    /**
     * 非递归，辗转相除法得到a和b的最大公因数
     * 时间复杂度O(logn)=O(1)，空间复杂度O(1)
     *
     * @param a
     * @param b
     * @return
     */
    private int gcd(int a, int b) {
        //当b为0时，a即为最大公因数
        while (b != 0) {
            int temp = a % b;
            a = b;
            b = temp;
        }

        return a;
    }

    /**
     * 得到a和b的最小公倍数
     * 时间复杂度O(logn)=O(1)，空间复杂度O(1)
     *
     * @param a
     * @param b
     * @return
     */
    private long lcm(int a, int b) {
        return (long) a * b / gcd(a, b);
    }
}
