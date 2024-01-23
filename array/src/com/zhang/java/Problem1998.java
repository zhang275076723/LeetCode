package com.zhang.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @Date 2023/9/15 08:49
 * @Author zsy
 * @Description 数组的最大公因数排序 质数类比Problem204、Problem952、Problem1175、Problem2523、Problem2614 并查集类比Problem130、Problem200、Problem261、Problem305、Problem323、Problem399、Problem547、Problem684、Problem685、Problem695、Problem765、Problem785、Problem827、Problem886、Problem952、Problem1135、Problem1254、Problem1319、Problem1489、Problem1568、Problem1584、Problem1627、Problem1905、Problem2685
 * 给你一个整数数组 nums ，你可以在 nums 上执行下述操作 任意次 ：
 * 如果 gcd(nums[i], nums[j]) > 1 ，交换 nums[i] 和 nums[j] 的位置。
 * 其中 gcd(nums[i], nums[j]) 是 nums[i] 和 nums[j] 的最大公因数。
 * 如果能使用上述交换方式将 nums 按 非递减顺序 排列，返回 true ；否则，返回 false 。
 * <p>
 * 输入：nums = [7,21,3]
 * 输出：true
 * 解释：可以执行下述操作完成对 [7,21,3] 的排序：
 * - 交换 7 和 21 因为 gcd(7,21) = 7 。nums = [21,7,3]
 * - 交换 21 和 3 因为 gcd(21,3) = 3 。nums = [3,7,21]
 * <p>
 * 输入：nums = [5,2,6,2]
 * 输出：false
 * 解释：无法完成排序，因为 5 不能与其他元素交换。
 * <p>
 * 输入：nums = [10,5,9,3,15]
 * 输出：true
 * 解释：
 * 可以执行下述操作完成对 [10,5,9,3,15] 的排序：
 * - 交换 10 和 15 因为 gcd(10,15) = 5 。nums = [15,5,9,3,10]
 * - 交换 15 和 3 因为 gcd(15,3) = 3 。nums = [3,5,9,15,10]
 * - 交换 10 和 15 因为 gcd(10,15) = 5 。nums = [3,5,9,10,15]
 * <p>
 * 1 <= nums.length <= 3 * 10^4
 * 2 <= nums[i] <= 10^5
 */
public class Problem1998 {
    public static void main(String[] args) {
        Problem1998 problem1998 = new Problem1998();
        int[] nums = {5, 2, 6, 2};
//        int[] nums = {2, 6, 2, 11};
        System.out.println(problem1998.gcdSort(nums));
        System.out.println(problem1998.gcdSort2(nums));
        System.out.println(problem1998.gcdSort3(nums));
    }

    /**
     * 并查集+因子
     * nums[i]中的每个大于1的因子和nums[i]属于同一个连通分量
     * nums数组遍历完连通分量连接之后，nums数组从小到大排序得到新数组，和原数组比较，
     * 如果两个元素不在同一个连通分量中，则nums无法交换之后按照从小到大排序，返回false
     * 时间复杂度O(n^(2/3)*α(n))，空间复杂度O(max(nums[i])) (find()和union()的时间复杂度为O(α(n))，可视为常数O(1))
     *
     * @param nums
     * @return
     */
    public boolean gcdSort(int[] nums) {
        if (nums == null || nums.length == 0) {
            return false;
        }

        if (nums.length == 1) {
            return true;
        }

        //数组中的最大值
        int maxNum = nums[0];

        for (int num : nums) {
            maxNum = Math.max(maxNum, num);
        }

        //并查集中元素的因子小于等于maxNum，因为是从0开始，所以需要多加1
        UnionFind unionFind = new UnionFind(maxNum + 1);

        //nums[i]中的每个大于1的因子和nums[i]属于同一个连通分量
        for (int i = 0; i < nums.length; i++) {
            for (int j = 2; j * j <= nums[i]; j++) {
                if (nums[i] % j == 0) {
                    //j和nums[i]/j都是nums[i]的因子
                    unionFind.union(j, nums[i]);
                    unionFind.union(nums[i] / j, nums[i]);
                }
            }
        }

        //原数组nums的拷贝数组
        int[] newArr = Arrays.copyOfRange(nums, 0, nums.length);
        //newArr从小到大排序
        quickSort(newArr, 0, newArr.length - 1);

        for (int i = 0; i < nums.length; i++) {
            //nums[i]和newArr[i]不在同一个连通分量中，则nums无法交换之后按照从小到大排序，返回false
            if (!unionFind.isConnected(nums[i], newArr[i])) {
                return false;
            }
        }

        //遍历结束，则nums可以交换之后按照从小到大排序，返回true
        return true;
    }

    /**
     * 并查集+质因子(埃氏筛)
     * nums[i]中的每个大于1的质因子和nums[i]属于同一个连通分量，因为nums[i]可由质因子相乘得到，当nums[i]可以整除当前质数时，
     * nums[i]不断除以当前质数，找下一个质数，nums[i]最多只存在一个大于(nums[i])^(1/2)的质因子，
     * 因为如果存在两个或两个以上大于(nums[i])^(1/2)的质因子，则这些质因子相乘大于nums[i]，
     * 与nums[i]可由这些质因子相乘得到相矛盾
     * nums数组遍历完连通分量连接之后，nums数组从小到大排序得到新数组，和原数组比较，
     * 如果两个元素不在同一个连通分量中，则nums无法交换之后按照从小到大排序，返回false
     * 时间复杂度O(nlog(logn)+n*α(n))，空间复杂度O(max(nums[i])) (find()和union()的时间复杂度为O(α(n))，可视为常数O(1))
     *
     * @param nums
     * @return
     */
    public boolean gcdSort2(int[] nums) {
        if (nums == null || nums.length == 0) {
            return false;
        }

        if (nums.length == 1) {
            return true;
        }

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

        //nums[i]中的每个大于1的质因子和nums[i]属于同一个连通分量
        for (int i = 0; i < nums.length; i++) {
            //当前数
            int curNum = nums[i];

            for (int j = 0; j < primesList.size(); j++) {
                //当前质数
                int curPrime = primesList.get(j);

                //nums[i]最多只存在一个大于(nums[i])^(1/2)的质因子，因为如果存在两个或两个以上大于(nums[i])^(1/2)的质因子，
                //则这些质因子相乘大于nums[i]，与nums[i]可由这些质因子相乘得到相矛盾
                if (curPrime * curPrime > curNum) {
                    //当前curNum为nums[i]的一个大于1的因子
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

        //原数组nums的拷贝数组
        int[] newArr = Arrays.copyOfRange(nums, 0, nums.length);
        //newArr从小到大排序
        quickSort(newArr, 0, newArr.length - 1);

        for (int i = 0; i < nums.length; i++) {
            //nums[i]和newArr[i]不在同一个连通分量中，则nums无法交换之后按照从小到大排序，返回false
            if (!unionFind.isConnected(nums[i], newArr[i])) {
                return false;
            }
        }

        //遍历结束，则nums可以交换之后按照从小到大排序，返回true
        return true;
    }

    /**
     * 并查集+质因子(欧拉筛，线性筛)
     * nums[i]中的每个大于1的质因子和nums[i]属于同一个连通分量，因为nums[i]可由质因子相乘得到，当nums[i]可以整除当前质数时，
     * nums[i]不断除以当前质数，找下一个质数，nums[i]最多只存在一个大于(nums[i])^(1/2)的质因子，
     * 因为如果存在两个或两个以上大于(nums[i])^(1/2)的质因子，则这些质因子相乘大于nums[i]，
     * 与nums[i]可由这些质因子相乘得到相矛盾
     * nums数组遍历完连通分量连接之后，nums数组从小到大排序得到新数组，和原数组比较，
     * 如果两个元素不在同一个连通分量中，则nums无法交换之后按照从小到大排序，返回false
     * 时间复杂度O(n+n*α(n))，空间复杂度O(max(nums[i])) (find()和union()的时间复杂度为O(α(n))，可视为常数O(1))
     *
     * @param nums
     * @return
     */
    public boolean gcdSort3(int[] nums) {
        if (nums == null || nums.length == 0) {
            return false;
        }

        if (nums.length == 1) {
            return true;
        }

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

            //i和primesList[j]相乘，得到合数
            //注意：每个合数只能由最小质因子得到，当i*primesList[j]的最小质因子不是primesList[j]时，直接跳出循环，避免重复标记合数
            for (int j = 0; j < primesList.size(); j++) {
                //primesList中的当前质数
                int curPrime = primesList.get(j);

                //超过范围maxNum，直接跳出循环
                //使用long，避免int相乘溢出
                if ((long) i * curPrime > maxNum) {
                    break;
                }

                //数i的curPrime倍表示的数不是质数
                dp[i * curPrime] = false;

                //i能整除curPrime，则得到的合数i*curPrime的最小质因子为curPrime，
                //curPrime之后的质数primesList[j+1]和i相乘得到的合数i*primesList[j+1]的最小质因子不是primesList[j+1]，
                //直接跳出循环，避免重复标记合数
                if (i % curPrime == 0) {
                    break;
                }
            }
        }

        //nums[i]中的每个大于1的质因子和nums[i]属于同一个连通分量
        for (int i = 0; i < nums.length; i++) {
            //当前数
            int curNum = nums[i];

            for (int j = 0; j < primesList.size(); j++) {
                //当前质数
                int curPrime = primesList.get(j);

                //nums[i]最多只存在一个大于(nums[i])^(1/2)的质因子，因为如果存在两个或两个以上大于(nums[i])^(1/2)的质因子，
                //则这些质因子相乘大于nums[i]，与nums[i]可由这些质因子相乘得到相矛盾
                if (curPrime * curPrime > curNum) {
                    //当前curNum为nums[i]的一个大于1的因子
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

        //原数组nums的拷贝数组
        int[] newArr = Arrays.copyOfRange(nums, 0, nums.length);
        //newArr从小到大排序
        quickSort(newArr, 0, newArr.length - 1);

        for (int i = 0; i < nums.length; i++) {
            //nums[i]和newArr[i]不在同一个连通分量中，则nums无法交换之后按照从小到大排序，返回false
            if (!unionFind.isConnected(nums[i], newArr[i])) {
                return false;
            }
        }

        //遍历结束，则nums可以交换之后按照从小到大排序，返回true
        return true;
    }

    private void quickSort(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }

        int pivot = partition(arr, left, right);
        quickSort(arr, left, pivot - 1);
        quickSort(arr, pivot + 1, right);
    }

    private int partition(int[] arr, int left, int right) {
        int randomIndex = new Random().nextInt(right - left + 1) + left;

        int value = arr[left];
        arr[left] = arr[randomIndex];
        arr[randomIndex] = value;

        int temp = arr[left];

        while (left < right) {
            while (left < right && arr[right] >= temp) {
                right--;
            }

            arr[left] = arr[right];

            while (left < right && arr[left] <= temp) {
                left++;
            }

            arr[right] = arr[left];
        }

        arr[left] = temp;

        return left;
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
