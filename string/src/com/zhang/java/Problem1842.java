package com.zhang.java;

/**
 * @Date 2024/4/25 09:11
 * @Author zsy
 * @Description 下个由相同数字构成的回文串 类比Problem31、Problem556、Problem670、Problem738、Problem1323、Problem1328、Problem1850、Problem2231 类比Problem479、Problem564、Problem866、Problem906、Problem2217、Problem2967 回文类比
 * 给你一个很长的数字回文串 num ，返回 大于 num、由相同数字重新组合而成的最小 回文串。
 * 如果不存在这样的回文串，则返回空串 ""。
 * 回文串 是正读和反读都一样的字符串。
 * <p>
 * 输入：num = "1221"
 * 输出："2112"
 * 解释：下个比 "1221" 大的回文串是 "2112"。
 * <p>
 * 输入：num = "32123"
 * 输出：""
 * 解释：不存在通过重组 "32123" 的数字可得、比 "32123" 还大的回文串。
 * <p>
 * 输入：num = "45544554"
 * 输出："54455445"
 * 解释：下个比 "45544554" 还要大的回文串是 "54455445"。
 * <p>
 * 1 <= num.length <= 10^5
 * num 是回文串。
 */
public class Problem1842 {
    public static void main(String[] args) {
        Problem1842 problem1842 = new Problem1842();
//        String num = "1221";
//        String num = "32123";
        String num = "45544554";
        System.out.println(problem1842.nextPalindrome(num));
    }

    /**
     * 模拟
     * 只需要考虑num的前半部分，得到比num前半部分大的下一个排列，作为回文的前半部分，
     * 再拼接上回文的后半部分，得到大于num，并且由相同数字重新组合而成的最小回文串
     * 如果不存在比num前半部分大的下一个排列，则返回""
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param num
     * @return
     */
    public String nextPalindrome(String num) {
        //num前半部分字符串
        String preHalf = num.substring(0, num.length() / 2);
        char[] arr = preHalf.toCharArray();

        //最长递减数组的下标索引
        int i = arr.length - 1;

        //从后往前找最长递减数组
        //注意：最长递减数组包含相邻元素相等的情况，即为大于等于
        while (i > 0 && arr[i - 1] >= arr[i]) {
            i--;
        }

        //arr整体为递减数组，则不存在比preHalf大的下一个排列，返回""
        if (i == 0) {
            return "";
        }

        //递减数组arr[i]-arr[arr.length-1]反转，变为递增数组
        reverse(arr, i, arr.length - 1);

        int j = i - 1;

        //从前往后找第一个大于arr[j]的元素arr[i]，两者进行交换，得到比preHalf大的下一个排列
        while (i < arr.length && arr[j] >= arr[i]) {
            i++;
        }

        swap(arr, i, j);

        StringBuilder sb = new StringBuilder().append(arr);

        //num长度为奇数，需要拼接中间一位
        if (num.length() % 2 == 1) {
            sb.append(num.charAt(num.length() / 2));
        }

        //再拼接回文的后半部分，得到大于num，并且由相同数字重新组合而成的最小回文串
        for (int k = arr.length - 1; k >= 0; k--) {
            sb.append(arr[k]);
        }

        return sb.toString();
    }

    private void reverse(char[] arr, int i, int j) {
        while (i < j) {
            swap(arr, i, j);
            i++;
            j--;
        }
    }

    private void swap(char[] arr, int i, int j) {
        char temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
