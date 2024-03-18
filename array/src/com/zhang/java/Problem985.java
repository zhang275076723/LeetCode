package com.zhang.java;

import java.util.Arrays;

/**
 * @Date 2023/9/24 08:10
 * @Author zsy
 * @Description 查询后的偶数和
 * 给出一个整数数组 A 和一个查询数组 queries。
 * 对于第 i 次查询，有 val = queries[i][0], index = queries[i][1]，我们会把 val 加到 A[index] 上。
 * 然后，第 i 次查询的答案是 A 中偶数值的和。
 * （此处给定的 index = queries[i][1] 是从 0 开始的索引，每次查询都会永久修改数组 A。）
 * 返回所有查询的答案。
 * 你的答案应当以数组 answer 给出，answer[i] 为第 i 次查询的答案。
 * <p>
 * 输入：A = [1,2,3,4], queries = [[1,0],[-3,1],[-4,0],[2,3]]
 * 输出：[8,6,2,4]
 * 解释：
 * 开始时，数组为 [1,2,3,4]。
 * 将 1 加到 A[0] 上之后，数组为 [2,2,3,4]，偶数值之和为 2 + 2 + 4 = 8。
 * 将 -3 加到 A[1] 上之后，数组为 [2,-1,3,4]，偶数值之和为 2 + 4 = 6。
 * 将 -4 加到 A[0] 上之后，数组为 [-2,-1,3,4]，偶数值之和为 -2 + 4 = 2。
 * 将 2 加到 A[3] 上之后，数组为 [-2,-1,3,6]，偶数值之和为 -2 + 6 = 4。
 * <p>
 * 1 <= A.length <= 10000
 * -10000 <= A[i] <= 10000
 * 1 <= queries.length <= 10000
 * -10000 <= queries[i][0] <= 10000
 * 0 <= queries[i][1] < A.length
 */
public class Problem985 {
    public static void main(String[] args) {
        Problem985 problem985 = new Problem985();
        int[] nums = {1, 2, 3, 4};
        int[][] queries = {{1, 0}, {-3, 1}, {-4, 0}, {2, 3}};
        System.out.println(Arrays.toString(problem985.sumEvenAfterQueries(nums, queries)));
    }

    /**
     * 模拟
     * 先计算nums中偶元素之和，修改nums[queries[i][1]]的值加上queries[i][0]，有以下4种情况：
     * 1、nums[queries[i][1]]修改之前为偶数，修改之后nums[queries[i][1]]为偶数，则偶元素之和加上queries[i][0]；
     * 2、nums[queries[i][1]]修改之前为偶数，修改之后nums[queries[i][1]]为奇数，则偶元素之和减去修改之前的nums[queries[i][1]]；
     * 3、nums[queries[i][1]]修改之前为奇数，修改之后nums[queries[i][1]]为偶数，则偶元素之和加上修改之后的nums[queries[i][1]]；
     * 4、nums[queries[i][1]]修改之前为奇数，修改之后nums[queries[i][1]]为奇数，则偶元素之和不变；
     * 时间复杂度O(n+queries.length)，空间复杂度O(1)
     *
     * @param nums
     * @param queries
     * @return
     */
    public int[] sumEvenAfterQueries(int[] nums, int[][] queries) {
        //偶元素之和
        int evenSum = 0;

        for (int num : nums) {
            if (num % 2 == 0) {
                evenSum = evenSum + num;
            }
        }

        int[] result = new int[queries.length];

        for (int i = 0; i < queries.length; i++) {
            int val = queries[i][0];
            int index = queries[i][1];

            //情况1、情况2，nums[index]修改之前为偶数
            if (nums[index] % 2 == 0) {
                //情况1，nums[index]修改之后为偶数
                if ((nums[index] + val) % 2 == 0) {
                    evenSum = evenSum + val;
                    nums[index] = nums[index] + val;
                    result[i] = evenSum;
                } else {
                    //情况2，nums[index]修改之后为奇数
                    evenSum = evenSum - nums[index];
                    nums[index] = nums[index] + val;
                    result[i] = evenSum;
                }
            } else {
                //情况3、情况4，nums[queries[i][1]]修改之前为奇数

                //情况3，nums[index]修改之后为偶数
                if ((nums[index] + val) % 2 == 0) {
                    nums[index] = nums[index] + val;
                    evenSum = evenSum + nums[index];
                    result[i] = evenSum;
                } else {
                    //情况4，nums[index]修改之后为奇数
                    nums[index] = nums[index] + val;
                    result[i] = evenSum;
                }
            }
        }

        return result;
    }
}
