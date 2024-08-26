package com.zhang.java;

/**
 * @Date 2024/12/4 08:17
 * @Author zsy
 * @Description 重新排列字符串 原地哈希类比Problem41、Problem268、Problem287、Problem442、Problem448、Problem645、Offer3
 * 给你一个字符串 s 和一个 长度相同 的整数数组 indices 。
 * 请你重新排列字符串 s ，其中第 i 个字符需要移动到 indices[i] 指示的位置。
 * 返回重新排列后的字符串。
 * <p>
 * 输入：s = "codeleet", indices = [4,5,6,7,0,2,1,3]
 * 输出："leetcode"
 * 解释：如图所示，"codeleet" 重新排列后变为 "leetcode" 。
 * <p>
 * 输入：s = "abc", indices = [0,1,2]
 * 输出："abc"
 * 解释：重新排列后，每个字符都还留在原来的位置上。
 * <p>
 * s.length == indices.length == n
 * 1 <= n <= 100
 * s 仅包含小写英文字母
 * 0 <= indices[i] < n
 * indices 的所有的值都是 唯一 的
 */
public class Problem1528 {
    public static void main(String[] args) {
        Problem1528 problem1528 = new Problem1528();
        String s = "codeleet";
        int[] indices = {4, 5, 6, 7, 0, 2, 1, 3};
        System.out.println(problem1528.restoreString(s, indices));
        System.out.println(problem1528.restoreString2(s, indices));
    }

    /**
     * 模拟
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param s
     * @param indices
     * @return
     */
    public String restoreString(String s, int[] indices) {
        char[] arr = new char[s.length()];

        for (int i = 0; i < s.length(); i++) {
            arr[indices[i]] = s.charAt(i);
        }

        return new String(arr);
    }

    /**
     * 原地哈希，s的字符数组arr作为哈希表，下标索引i处放置的indices[i]等于i
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param s
     * @param indices
     * @return
     */
    public String restoreString2(String s, int[] indices) {
        char[] arr = s.toCharArray();

        for (int i = 0; i < arr.length; i++) {
            //indices[i]和indices[indices[i]]不相等时，arr[i]和arr[indices[i]]进行交换，indices[i]和indices[indices[i]]进行交换
            while (indices[i] != indices[indices[i]]) {
                //注意：要先交换arr，再交换indices
                swap(arr, i, indices[i]);
                swap(indices, i, indices[i]);
            }
        }

        return new String(arr);
    }

    private void swap(char[] arr, int i, int j) {
        char temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
