package com.zhang.java;

/**
 * @Date 2025/2/22 08:11
 * @Author zsy
 * @Description 找到最高海拔
 * 有一个自行车手打算进行一场公路骑行，这条路线总共由 n + 1 个不同海拔的点组成。
 * 自行车手从海拔为 0 的点 0 开始骑行。
 * 给你一个长度为 n 的整数数组 gain ，其中 gain[i] 是点 i 和点 i + 1 的 净海拔高度差（0 <= i < n）。
 * 请你返回 最高点的海拔 。
 * <p>
 * 输入：gain = [-5,1,5,0,-7]
 * 输出：1
 * 解释：海拔高度依次为 [0,-5,-4,1,1,-6] 。最高海拔为 1 。
 * <p>
 * 输入：gain = [-4,-3,-2,-1,4,3,2]
 * 输出：0
 * 解释：海拔高度依次为 [0,-4,-7,-9,-10,-6,-3,-1] 。最高海拔为 0 。
 * <p>
 * n == gain.length
 * 1 <= n <= 100
 * -100 <= gain[i] <= 100
 */
public class Problem1732 {
    public static void main(String[] args) {
        Problem1732 problem1732 = new Problem1732();
        int[] gain = {-5, 1, 5, 0, -7};
        System.out.println(problem1732.largestAltitude(gain));
    }

    /**
     * 模拟
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param gain
     * @return
     */
    public int largestAltitude(int[] gain) {
        //最大海拔
        int maxAltitude = 0;
        //当前遍历到的海拔
        int curAltitude = 0;

        for (int i = 0; i < gain.length; i++) {
            curAltitude = curAltitude + gain[i];
            maxAltitude = Math.max(maxAltitude, curAltitude);
        }

        return maxAltitude;
    }
}
