package com.zhang.java;

import java.util.Random;

/**
 * @Date 2022/12/6 11:34
 * @Author zsy
 * @Description 第一个错误的版本 类比problem374
 * 你是产品经理，目前正在带领一个团队开发新的产品。
 * 不幸的是，你的产品的最新版本没有通过质量检测。
 * 由于每个版本都是基于之前的版本开发的，所以错误的版本之后的所有版本都是错的。
 * 假设你有 n 个版本 [1, 2, ..., n]，你想找出导致之后所有版本出错的第一个错误的版本。
 * 你可以通过调用 bool isBadVersion(version) 接口来判断版本号 version 是否在单元测试中出错。
 * 实现一个函数来查找第一个错误的版本。
 * 你应该尽量减少对调用 API 的次数。
 * <p>
 * 输入：n = 5, bad = 4
 * 输出：4
 * 解释：
 * 调用 isBadVersion(3) -> false
 * 调用 isBadVersion(5) -> true
 * 调用 isBadVersion(4) -> true
 * 所以，4 是第一个错误的版本。
 * <p>
 * 输入：n = 1, bad = 1
 * 输出：1
 * <p>
 * 1 <= bad <= n <= 2^31 - 1
 */
public class Problem278 {
    public static void main(String[] args) {
        Problem278 problem278 = new Problem278();
        System.out.println(problem278.firstBadVersion(8));
    }

    /**
     * 二分查找
     * 时间复杂度O(logn)，空间复杂度O(1)
     *
     * @param n
     * @return
     */
    public int firstBadVersion(int n) {
        int firstBadVersion = new Random().nextInt(n + 1);

        int left = 1;
        int right = n;
        int mid;

        while (left < right) {
            //防止溢出
            mid = left + ((right - left) >> 1);

            if (mid < firstBadVersion) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        return left;
    }
}
