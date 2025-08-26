package com.zhang.java;

/**
 * @Date 2025/8/25 09:46
 * @Author zsy
 * @Description 邻位交换的最小次数 类比Problem31、Problem556、Problem670、Problem738、Problem1323、Problem1328、Problem1842、Problem2231
 * 给你一个表示大整数的字符串 num ，和一个整数 k 。
 * 如果某个整数是 num 中各位数字的一个 排列 且它的 值大于 num ，则称这个整数为 妙数 。
 * 可能存在很多妙数，但是只需要关注 值最小 的那些。
 * 例如，num = "5489355142" ：
 * 第 1 个最小妙数是 "5489355214"
 * 第 2 个最小妙数是 "5489355241"
 * 第 3 个最小妙数是 "5489355412"
 * 第 4 个最小妙数是 "5489355421"
 * 返回要得到第 k 个 最小妙数 需要对 num 执行的 相邻位数字交换的最小次数 。
 * 测试用例是按存在第 k 个最小妙数而生成的。
 * <p>
 * 输入：num = "5489355142", k = 4
 * 输出：2
 * 解释：第 4 个最小妙数是 "5489355421" ，要想得到这个数字：
 * - 交换下标 7 和下标 8 对应的位："5489355142" -> "5489355412"
 * - 交换下标 8 和下标 9 对应的位："5489355412" -> "5489355421"
 * <p>
 * 输入：num = "11112", k = 4
 * 输出：4
 * 解释：第 4 个最小妙数是 "21111" ，要想得到这个数字：
 * - 交换下标 3 和下标 4 对应的位："11112" -> "11121"
 * - 交换下标 2 和下标 3 对应的位："11121" -> "11211"
 * - 交换下标 1 和下标 2 对应的位："11211" -> "12111"
 * - 交换下标 0 和下标 1 对应的位："12111" -> "21111"
 * <p>
 * 输入：num = "00123", k = 1
 * 输出：1
 * 解释：第 1 个最小妙数是 "00132" ，要想得到这个数字：
 * - 交换下标 3 和下标 4 对应的位："00123" -> "00132"
 * <p>
 * 2 <= num.length <= 1000
 * 1 <= k <= 1000
 * num 仅由数字组成
 */
public class Problem1850 {
    public static void main(String[] args) {
        Problem1850 problem1850 = new Problem1850();
//        String num = "5489355142";
//        int k = 4;
        String num = "1332";
        int k = 2;
        System.out.println(problem1850.getMinSwaps(num, k));
    }

    /**
     * 模拟
     * 1、根据31题执行k次，得到num的第k个下一个排列，即为第k个妙数
     * 2、num和num的第k个下一个排列的每一位依次比较，如果当前位不相等，则num往后找相等的值，相邻位交换
     * 时间复杂度O(nk+n^2)，空间复杂度O(n)
     *
     * @param num
     * @param k
     * @return
     */
    public int getMinSwaps(String num, int k) {
        //初始num数组
        int[] originArr = new int[num.length()];
        //执行k次，得到第k个妙数数组
        int[] kthArr = new int[num.length()];

        for (int i = 0; i < num.length(); i++) {
            char c = num.charAt(i);
            originArr[i] = c - '0';
            kthArr[i] = c - '0';
        }

        //1、根据31题执行k次，得到num的第k个下一个排列，即为第k个妙数
        for (int i = 0; i < k; i++) {
            nextPermutation(kthArr);
        }

        int count = 0;

        //2、num和num的第k个下一个排列的每一位依次比较，如果当前位不相等，则num往后找相等的值，相邻位交换
        for (int i = 0; i < num.length(); i++) {
            if (originArr[i] == kthArr[i]) {
                continue;
            }

            int j = i;

            //往后找originArr中和kthArr[i]相等的值
            while (originArr[j] != kthArr[i]) {
                j++;
            }

            //originArr相邻位交换
            for (int m = j; m > i; m--) {
                swap(originArr, m, m - 1);
                count++;
            }
        }

        return count;
    }

    /**
     * 得到nums的下一个排列，同31题
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     */
    private void nextPermutation(int[] nums) {
        //最长递减数组的下标索引
        int i = nums.length - 1;

        //从后往前找最长递减数组
        //注意：最长递减数组包含相邻元素相等的情况，即为大于等于
        while (i > 0 && nums[i - 1] >= nums[i]) {
            i--;
        }

        //递减数组nums[i]-nums[nums.length-1]反转，变为递增数组
        reverse(nums, i, nums.length - 1);

        //nums整体为递减数组，则不存在比nums大的下一个排列，反转整个数组得到递增数组为最小排列，直接返回
        if (i == 0) {
            return;
        }

        int j = i - 1;

        //从前往后找第一个大于nums[j]的元素nums[i]，两者进行交换，得到下个一排列
        while (i < nums.length && nums[j] >= nums[i]) {
            i++;
        }

        swap(nums, i, j);
    }

    private void reverse(int[] nums, int i, int j) {
        while (i < j) {
            swap(nums, i, j);
            i++;
            j--;
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
