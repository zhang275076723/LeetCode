package com.zhang.java;

/**
 * @Date 2022/11/4 10:26
 * @Author zsy
 * @Description 反转字符串 类比Problem58、Problem151、Problem186、Problem345、Problem541、Problem557、Offer58、Offer58_2
 * 编写一个函数，其作用是将输入的字符串反转过来。
 * 输入字符串以字符数组 s 的形式给出。
 * 不要给另外的数组分配额外的空间，你必须原地修改输入数组、使用 O(1) 的额外空间解决这一问题。
 * <p>
 * 输入：s = ["h","e","l","l","o"]
 * 输出：["o","l","l","e","h"]
 * <p>
 * 输入：s = ["H","a","n","n","a","h"]
 * 输出：["h","a","n","n","a","H"]
 * <p>
 * 1 <= s.length <= 10^5
 * s[i] 都是 ASCII 码表中的可打印字符
 */
public class Problem344 {
    public static void main(String[] args) {
        Problem344 problem344 = new Problem344();
        char[] s = {'h', 'e', 'l', 'l', 'o'};
        problem344.reverseString(s);
        System.out.println(s);
    }

    /**
     * 双指针
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param s
     */
    public void reverseString(char[] s) {
        if (s == null) {
            return;
        }

        int left = 0;
        int right = s.length - 1;

        while (left < right) {
            //左右指针元素交换
            char temp = s[left];
            s[left] = s[right];
            s[right] = temp;

            left++;
            right--;
        }
    }
}
