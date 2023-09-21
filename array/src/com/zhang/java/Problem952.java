package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2023/9/16 08:14
 * @Author zsy
 * @Description 按公因数计算最大组件大小 质数类比Problem204、Problem1175、Problem1998 并查集类比
 * 给定一个由不同正整数的组成的非空数组 nums ，考虑下面的图：
 * 有 nums.length 个节点，按从 nums[0] 到 nums[nums.length - 1] 标记；
 * 只有当 nums[i] 和 nums[j] 共用一个大于 1 的公因数时，nums[i] 和 nums[j]之间才有一条边。
 * 返回 图中最大连通组件的大小 。
 * <p>
 * 输入：nums = [4,6,15,35]
 * 输出：4
 * 解释：
 * [4,6,15,35]相互连通，4和6的公因数大于1，6和15的公因数大于1，15和35的公因数大于
 * <p>
 * 输入：nums = [20,50,9,63]
 * 输出：2
 * 解释：
 * [20,50]相互连通，20和50的公因数大于1
 * [9,63]相互连通，9和63的公因数大于1
 * <p>
 * 输入：nums = [2,3,6,7,4,12,21,39]
 * 输出：8
 * 解释：
 * [2,3,4,6,7,12,21,39]相互连通，2和4和12的公因数大于1，3和6和12的公因数大于1，7和21的公因数大于1，3和21和39的公因数大于1
 * <p>
 * 1 <= nums.length <= 2 * 10^4
 * 1 <= nums[i] <= 10^5
 * nums 中所有值都 不同
 */
public class Problem952 {
    public static void main(String[] args) {
        Problem952 problem952 = new Problem952();
        int[] nums = {2, 3, 6, 7, 4, 12, 21, 39};
        System.out.println(problem952.largestComponentSize(nums));
        System.out.println(problem952.largestComponentSize2(nums));
        System.out.println(problem952.largestComponentSize3(nums));
    }

    /**
     * 并查集+因子
     * nums[i]中的每个大于1的因子属于同一个连通分量
     * 时间复杂度O(n^(2/3)*α(n))，空间复杂度O(max(nums[i])) (find()和union()的时间复杂度为O(α(n))，可视为常数O(1))
     *
     * @param nums
     * @return
     */
    public int largestComponentSize(int[] nums) {
        //数组中的最大值
        int maxNum = nums[0];

        for (int num : nums) {
            maxNum = Math.max(maxNum, num);
        }

        //并查集中元素的因子小于等于maxNum，因为是从0开始，所以需要多加1
        UnionFind unionFind = new UnionFind(maxNum + 1);

        //nums[i]中的每个大于1的因子属于同一个连通分量
        for (int i = 0; i < nums.length; i++) {
            for (int j = 2; j * j <= nums[i]; j++) {
                if (nums[i] % j == 0) {
                    //j和nums[i]/j都是nums[i]的因子
                    unionFind.union(j, nums[i]);
                    unionFind.union(nums[i] / j, nums[i]);
                }
            }
        }

        //统计数组，nums[i]所属连通分量，即nums[i]根节点都为root的数量count[root]
        int[] count = new int[maxNum + 1];
        //最大连通分量中元素的个数
        int max = 1;

        for (int i = 0; i < nums.length; i++) {
            int root = unionFind.find(nums[i]);
            count[root]++;
            max = Math.max(max, count[root]);
        }

        return max;
    }

    /**
     * 并查集+质因子(埃氏筛)
     * nums[i]中的每个大于1的质因子属于同一个连通分量，因为nums[i]可由质因子相乘得到，当nums[i]可以整除当前质数时，
     * nums[i]不断除以当前质数，找下一个质数，nums[i]最多只存在一个大于(nums[i])^(1/2)的质因子，
     * 因为如果存在两个或两个以上大于(nums[i])^(1/2)的质因子，则这些质因子相乘大于nums[i]，
     * 与nums[i]可由这些质因子相乘得到相矛盾
     * 时间复杂度O(nlog(logn)+n*α(n))，空间复杂度O(max(nums[i])) (find()和union()的时间复杂度为O(α(n))，可视为常数O(1))
     *
     * @param nums
     * @return
     */
    public int largestComponentSize2(int[] nums) {
        //数组中的最大值
        int maxNum = nums[0];

        for (int num : nums) {
            maxNum = Math.max(maxNum, num);
        }

        //dp[i]：数字i是否是质数
        boolean[] dp = new boolean[maxNum + 1];
        //存放当前遍历到的质数集合
        List<Integer> primesList = new ArrayList<>();
        //并查集中元素的质因子小于等于maxNum，因为是从0开始，所以需要多加1
        UnionFind unionFind = new UnionFind(maxNum + 1);

        //dp初始化，初始化[2,maxNum]每个数都是质数
        for (int i = 2; i <= maxNum; i++) {
            dp[i] = true;
        }

        //埃氏筛找小于等于maxNum的质数
        for (int i = 2; i <= maxNum; i++) {
            //如果i是质数，则质数i的正整数倍表示的数都不是质数
            if (dp[i]) {
                primesList.add(i);

                //从i*i开始遍历，因为i*2、i*3...在之前遍历2、3...时已经置为非质数false，使用long，避免int相乘溢出
                for (int j = i; (long) i * j <= maxNum; j++) {
                    //质数i的j倍表示的数都不是质数
                    dp[i * j] = false;
                }
            }
        }

        //nums[i]中的每个大于1的质因子属于同一个连通分量
        for (int i = 0; i < nums.length; i++) {
            //当前数
            int curNum = nums[i];

            for (int j = 0; j < primesList.size(); j++) {
                //当前质数
                int curPrime = primesList.get(j);

                //nums[i]最多只存在一个大于(nums[i])^(1/2)的质因子，因为如果存在两个或两个以上大于(nums[i])^(1/2)的质因子，
                //则这些质因子相乘大于nums[i]，与nums[i]可由这些质因子相乘得到相矛盾
                if (curPrime * curPrime > curNum) {
                    //当前curNum为nums[i]的最大质因子
                    if (curNum > 1) {
                        unionFind.union(curNum, nums[i]);
                    }

                    break;
                }

                //curPrime是curNum的质因子，curNum不断除以curPrime，找下一个质因子
                if (curNum % curPrime == 0) {
                    unionFind.union(curPrime, nums[i]);

                    while (curNum % curPrime == 0) {
                        curNum = curNum / curPrime;
                    }
                }
            }
        }

        //统计数组，nums[i]所属连通分量，即nums[i]根节点都为root的数量count[root]
        int[] count = new int[maxNum + 1];
        //最大连通分量中元素的个数
        int max = 1;

        for (int i = 0; i < nums.length; i++) {
            int root = unionFind.find(nums[i]);
            count[root]++;
            max = Math.max(max, count[root]);
        }

        return max;
    }

    /**
     * 并查集+质因子(欧拉筛，线性筛)
     * nums[i]中的每个大于1的质因子属于同一个连通分量，因为nums[i]可由质因子相乘得到，当nums[i]可以整除当前质数时，
     * nums[i]不断除以当前质数，找下一个质数，nums[i]最多只存在一个大于(nums[i])^(1/2)的质因子，
     * 因为如果存在两个或两个以上大于(nums[i])^(1/2)的质因子，则这些质因子相乘大于nums[i]，
     * 与nums[i]可由这些质因子相乘得到相矛盾
     * 时间复杂度O(n+n*α(n))，空间复杂度O(max(nums[i])) (find()和union()的时间复杂度为O(α(n))，可视为常数O(1))
     *
     * @param nums
     * @return
     */
    public int largestComponentSize3(int[] nums) {
        //数组中的最大值
        int maxNum = nums[0];

        for (int num : nums) {
            maxNum = Math.max(maxNum, num);
        }

        //dp[i]：数字i是否是质数
        boolean[] dp = new boolean[maxNum + 1];
        //存放当前遍历到的质数集合
        List<Integer> primesList = new ArrayList<>();
        //并查集中元素的质因子小于等于maxNum，因为是从0开始，所以需要多加1
        UnionFind unionFind = new UnionFind(maxNum + 1);

        //dp初始化，初始化[2,maxNum]每个数都是质数
        for (int i = 2; i <= maxNum; i++) {
            dp[i] = true;
        }

        //欧拉筛找小于等于maxNum的质数
        for (int i = 2; i <= maxNum; i++) {
            //当前数i是质数，加入质数集合
            if (dp[i]) {
                primesList.add(i);
            }

            //i和质数集合中质数相乘，得到合数，注意：每个合数只能由最小质因子得到，当找到i的最小质因子之后，
            //直接跳出循环，避免了重复计算标记合数
            for (int j = 0; j < primesList.size(); j++) {
                //primesList中的当前质数
                int curPrime = primesList.get(j);

                //超过范围maxNum，直接跳出循环
                if (i * curPrime > maxNum) {
                    break;
                }

                //数i的curPrime倍表示的数不是质数
                dp[i * curPrime] = false;

                //curPrimes为i的最小质因子，则curPrime之后的质数都不是最小质因子，和i相乘得到的会得到重复的合数，直接跳出循环
                if (i % curPrime == 0) {
                    break;
                }
            }
        }

        //nums[i]中的每个大于1的质因子属于同一个连通分量
        for (int i = 0; i < nums.length; i++) {
            //当前数
            int curNum = nums[i];

            for (int j = 0; j < primesList.size(); j++) {
                //当前质数
                int curPrime = primesList.get(j);

                //nums[i]最多只存在一个大于(nums[i])^(1/2)的质因子，因为如果存在两个或两个以上大于(nums[i])^(1/2)的质因子，
                //则这些质因子相乘大于nums[i]，与nums[i]可由这些质因子相乘得到相矛盾
                if (curPrime * curPrime > curNum) {
                    //当前curNum为nums[i]的最大质因子
                    if (curNum > 1) {
                        unionFind.union(curNum, nums[i]);
                    }

                    break;
                }

                //curPrime是curNum的质因子，curNum不断除以curPrime，找下一个质因子
                if (curNum % curPrime == 0) {
                    unionFind.union(curPrime, nums[i]);

                    while (curNum % curPrime == 0) {
                        curNum = curNum / curPrime;
                    }
                }
            }
        }

        //统计数组，nums[i]所属连通分量，即nums[i]根节点都为root的数量count[root]
        int[] count = new int[maxNum + 1];
        //最大连通分量中元素的个数
        int max = 1;

        for (int i = 0; i < nums.length; i++) {
            int root = unionFind.find(nums[i]);
            count[root]++;
            max = Math.max(max, count[root]);
        }

        return max;
    }

    /**
     * 并查集(不相交数据集)类
     * 用数组的形式表示图
     */
    private static class UnionFind {
        //连通分量的个数
        private int count;
        //节点的父节点数组
        private final int[] parent;
        //节点的权值数组
        private final int[] weight;

        public UnionFind(int n) {
            count = n;
            parent = new int[n];
            weight = new int[n];

            for (int i = 0; i < n; i++) {
                parent[i] = i;
                weight[i] = 1;
            }
        }

        public void union(int i, int j) {
            int rootI = find(i);
            int rootJ = find(j);

            if (rootI != rootJ) {
                if (weight[rootI] < weight[rootJ]) {
                    parent[rootI] = rootJ;
                } else if (weight[rootI] > weight[rootJ]) {
                    parent[rootJ] = rootI;
                } else {
                    parent[rootJ] = rootI;
                    weight[rootI]++;
                }

                count--;
            }
        }

        public int find(int i) {
            if (parent[i] != i) {
                parent[i] = find(parent[i]);
            }

            return parent[i];
        }

        public boolean isConnected(int i, int j) {
            int rootI = find(i);
            int rootJ = find(j);

            return rootI == rootJ;
        }
    }
}
