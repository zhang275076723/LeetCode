package com.zhang.java;

/**
 * @Date 2023/9/2 09:42
 * @Author zsy
 * @Description 青蛙过河 II 类比Problem561、Problem717 跳跃问题类比Problem45、Problem55、Problem403、Problem1306、Problem1340、Problem1345、Problem1654、Problem1696、Problem1871 二分查找类比Problem4、Problem287、Problem373、Problem378、Problem410、Problem644、Problem658、Problem668、Problem719、Problem786、Problem878、Problem1201、Problem1482、Problem1723、Problem2305、CutWood、FindMaxArrayMinAfterKMinus
 * 给你一个下标从 0 开始的整数数组 stones ，数组中的元素 严格递增 ，表示一条河中石头的位置。
 * 一只青蛙一开始在第一块石头上，它想到达最后一块石头，然后回到第一块石头。同时每块石头 至多 到达 一次。
 * 一次跳跃的 长度 是青蛙跳跃前和跳跃后所在两块石头之间的距离。
 * 更正式的，如果青蛙从 stones[i] 跳到 stones[j] ，跳跃的长度为 |stones[i] - stones[j]| 。
 * 一条路径的 代价 是这条路径里的 最大跳跃长度 。
 * 请你返回这只青蛙的 最小代价 。
 * <p>
 * 输入：stones = [0,2,5,6,7]
 * 输出：5
 * 解释：0-5-7-6-2-0
 * 这条路径的代价是 5 ，是这条路径中的最大跳跃长度。
 * 无法得到一条代价小于 5 的路径，我们返回 5 。
 * <p>
 * 输入：stones = [0,3,9]
 * 输出：9
 * 解释：
 * 青蛙可以直接跳到最后一块石头，然后跳回第一块石头。
 * 在这条路径中，每次跳跃长度都是 9 。所以路径代价是 max(9, 9) = 9 。
 * 这是可行路径中的最小代价。
 * <p>
 * 2 <= stones.length <= 10^5
 * 0 <= stones[i] <= 10^9
 * stones[0] == 0
 * stones 中的元素严格递增。
 */
public class Problem2498 {
    public static void main(String[] args) {
        Problem2498 problem2498 = new Problem2498();
        int[] stones = {0, 2, 5, 6, 7};
        System.out.println(problem2498.maxJump(stones));
        System.out.println(problem2498.maxJump2(stones));
    }

    /**
     * 贪心
     * 要得到每次跳跃的最大距离的最小值，必须尽可能多的跳到每块石头，每块石头最多只能跳到1次，所以不能每个石头挨着跳，
     * 每次只能间隔1个石头跳跃，即从stones[0]-stones[2]-...-stones[n-3]-stones[n-1]-stones[n-2]-...-stones[1]-stones[0]，
     * stones[n-3]到stones[n-1]的距离大于stones[n-1]到stones[n-2]的距离，stones[0]到stones[2]的距离大于stones[1]到stones[0]的距离，
     * 所以只需要考虑每间隔1个石头的最大距离，即为每次跳跃的最大距离的最小值
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param stones
     * @return
     */
    public int maxJump(int[] stones) {
        if (stones == null || stones.length == 0) {
            return 0;
        }

        //只有2个石头，无法间隔1个石头跳跃，直接可以跳到最后一个石头
        if (stones.length == 2) {
            return stones[1] - stones[0];
        }

        int max = 0;

        for (int i = 2; i < stones.length; i++) {
            //间隔1个石头跳跃
            max = Math.max(max, stones[i] - stones[i - 2]);
        }

        return max;
    }

    /**
     * 二分查找，使...最大值尽可能小，就要想到二分查找
     * 对[left,right]进行二分查找，left为1，right为最后一个石头和第一个石头的距离，
     * 判断能否从第一块石头跳跃到达最后一块石头，再回到第一块石头，并且每次跳跃距离小于等于mid，每块石头最多只能跳到1次，
     * 如果存在mid可以跳到，则right=mid，继续往左边找；
     * 如果不存在mid可以跳到，则left=mid+1，继续往右边找
     * 时间复杂度O(n*log(right-left))=O(n)，空间复杂度O(1) (left和right为int范围内的数，log(right-left)<32)
     *
     * @param stones
     * @return
     */
    public int maxJump2(int[] stones) {
        if (stones == null || stones.length == 0) {
            return 0;
        }

        //二分查找左边界，初始化为1
        int left = 1;
        //二分查找右边界，初始化为最后一个石头和第一个石头的距离
        int right = stones[stones.length - 1] - stones[0];
        int mid;

        while (left < right) {
            //mid作为每次跳跃的最大距离
            mid = left + ((right - left) >> 1);

            //判断能否从第一块石头跳跃到达最后一块石头，再回到第一块石头，并且每次跳跃距离小于等于mid，每块石头最多只能跳到1次
            if (isLessEqualThanStepJump(stones, mid)) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }

        return left;
    }

    /**
     * 能否从第一块石头跳跃到达最后一块石头，再回到第一块石头，并且每次跳跃距离小于等于step，每块石头最多只能跳到1次
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param stones
     * @param step
     * @return
     */
    private boolean isLessEqualThanStepJump(int[] stones, int step) {
        //访问数组，用于判断当前石头是否被访问过
        boolean[] visited = new boolean[stones.length];
        //当前跳跃到石头的下标索引
        int curIndex = 0;

        //从前往后每次跳跃距离都要尽可能大，尽可能接近step
        while (curIndex < stones.length - 1) {
            //从curIndex能够跳到的最远石头的下标索引
            int nextIndex = curIndex;

            while (nextIndex + 1 < stones.length && stones[nextIndex + 1] - stones[curIndex] <= step) {
                nextIndex++;
            }

            //当前石头无法跳到下一个石头，即curIndex和下一个石头距离大于step，无法跳跃，返回false
            if (curIndex == nextIndex) {
                return false;
            }

            curIndex = nextIndex;
            visited[nextIndex] = true;
        }

        //从后往前每次跳跃距离都要尽可能小，不遗漏未访问的石头，保证每次跳跃距离都小于等于step
        while (curIndex > 0) {
            //从curIndex能够跳到的最近石头的下标索引
            int nextIndex = curIndex;

            while (nextIndex >= 0 && visited[nextIndex]) {
                nextIndex--;
            }

            //当前石头和下一个石头距离大于step，无法跳跃，返回false
            if (stones[curIndex] - stones[nextIndex] > step) {
                return false;
            }

            curIndex = nextIndex;
            visited[nextIndex] = true;
        }

        return true;
    }
}
