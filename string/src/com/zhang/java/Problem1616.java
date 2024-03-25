package com.zhang.java;

/**
 * @Date 2024/3/16 08:26
 * @Author zsy
 * @Description 分割两个字符串得到回文串 回文类比
 * 给你两个字符串 a 和 b ，它们长度相同。
 * 请你选择一个下标，将两个字符串都在 相同的下标 分割开。
 * 由 a 可以得到两个字符串： aprefix 和 asuffix ，满足 a = aprefix + asuffix ，
 * 同理，由 b 可以得到两个字符串 bprefix 和 bsuffix ，满足 b = bprefix + bsuffix 。
 * 请你判断 aprefix + bsuffix 或者 bprefix + asuffix 能否构成回文串。
 * 当你将一个字符串 s 分割成 sprefix 和 ssuffix 时， ssuffix 或者 sprefix 可以为空。
 * 比方说， s = "abc" 那么 "" + "abc" ， "a" + "bc" ， "ab" + "c" 和 "abc" + "" 都是合法分割。
 * 如果 能构成回文字符串 ，那么请返回 true，否则返回 false 。
 * 注意， x + y 表示连接字符串 x 和 y 。
 * <p>
 * 输入：a = "x", b = "y"
 * 输出：true
 * 解释：如果 a 或者 b 是回文串，那么答案一定为 true ，因为你可以如下分割：
 * aprefix = "", asuffix = "x"
 * bprefix = "", bsuffix = "y"
 * 那么 aprefix + bsuffix = "" + "y" = "y" 是回文串。
 * <p>
 * 输入：a = "abdef", b = "fecab"
 * 输出：true
 * <p>
 * 输入：a = "ulacfd", b = "jizalu"
 * 输出：true
 * 解释：在下标为 3 处分割：
 * aprefix = "ula", asuffix = "cfd"
 * bprefix = "jiz", bsuffix = "alu"
 * 那么 aprefix + bsuffix = "ula" + "alu" = "ulaalu" 是回文串。
 * <p>
 * 1 <= a.length, b.length <= 10^5
 * a.length == b.length
 * a 和 b 都只包含小写英文字母
 */
public class Problem1616 {
    public static void main(String[] args) {
        Problem1616 problem1616 = new Problem1616();
        //"ula"+"cfd"
        String a = "ulacfd";
        //"jiz"+"alu"
        String b = "jizalu";
        System.out.println(problem1616.checkPalindromeFormation(a, b));
        System.out.println(problem1616.checkPalindromeFormation2(a, b));
    }

    /**
     * 暴力
     * 时间复杂度O(n^2)，空间复杂度O(n)
     *
     * @param a
     * @param b
     * @return
     */
    public boolean checkPalindromeFormation(String a, String b) {
        for (int i = 0; i <= a.length(); i++) {
            //a[0]-a[i-1]拼接b[i]-b[n-1]得到的字符串
            String s1 = a.substring(0, i) + b.substring(i);
            //b[0]-b[i-1]拼接a[i]-a[n-1]得到的字符串
            String s2 = b.substring(0, i) + a.substring(i);

            if (isPalindrome(s1, 0, s1.length() - 1) || isPalindrome(s2, 0, s2.length() - 1)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 贪心
     * 找a的最长前缀和b的最长后缀匹配，并且a的中间剩余部分或b的中间剩余部分为回文，
     * 则a的最长前缀+a的中间剩余部分回文或b的中间剩余部分回文+b的最长后缀匹配构成回文
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param a
     * @param b
     * @return
     */
    public boolean checkPalindromeFormation2(String a, String b) {
        //a的前缀和b的后缀能否构成回文，b的前缀和a的后缀能否构成回文，只要有一种情况满足要求则返回true
        return check(a, b) || check(b, a);
    }

    /**
     * 判断字符串a的前缀和字符串b的后缀能否构成回文
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param a
     * @param b
     * @return
     */
    private boolean check(String a, String b) {
        int left = 0;
        int right = b.length() - 1;

        //找a的最长前缀和b的最长后缀匹配
        while (left < right) {
            if (a.charAt(left) == b.charAt(right)) {
                left++;
                right--;
            } else {
                break;
            }
        }

        //a的最长前缀超过字符串的一半，则已经找到a的前缀和b的后缀构成回文，直接返回true
        if (left > right) {
            return true;
        }

        //a[left]-a[right]是回文，或者b[left]-b[right]是回文，
        //则a[0]-a[left-1]+a[left]-a[right]/b[left]-b[right]+b[right-1]-b[n-1]构成回文
        return isPalindrome(a, left, right) || isPalindrome(b, left, right);
    }

    private boolean isPalindrome(String s, int i, int j) {
        int left = i;
        int right = j;

        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) {
                return false;
            } else {
                left++;
                right--;
            }
        }

        return true;
    }
}
