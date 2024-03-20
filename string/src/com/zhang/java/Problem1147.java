package com.zhang.java;

/**
 * @Date 2024/3/7 08:28
 * @Author zsy
 * @Description 段式回文 类比Problem241 回文类比Problem5、Problem9、Problem125、Problem131、Problem132、Problem214、Problem234、Problem266、Problem267、Problem336、Problem409、Problem479、Problem516、Problem647、Problem680、Problem866、Problem1177、Problem1312、Problem1328、Problem1332
 * 你会得到一个字符串 text 。
 * 你应该把它分成 k 个子字符串 (subtext1, subtext2，…， subtextk) ，要求满足:
 * subtexti 是 非空 字符串
 * 所有子字符串的连接等于 text ( 即subtext1 + subtext2 + ... + subtextk == text )
 * 对于所有 i 的有效值( 即 1 <= i <= k ) ，subtexti == subtextk - i + 1 均成立
 * 返回k可能最大值。
 * <p>
 * 输入：text = "ghiabcdefhelloadamhelloabcdefghi"
 * 输出：7
 * 解释：我们可以把字符串拆分成 "(ghi)(abcdef)(hello)(adam)(hello)(abcdef)(ghi)"。
 * <p>
 * 输入：text = "merchant"
 * 输出：1
 * 解释：我们可以把字符串拆分成 "(merchant)"。
 * <p>
 * 输入：text = "antaprezatepzapreanta"
 * 输出：11
 * 解释：我们可以把字符串拆分成 "(a)(nt)(a)(pre)(za)(tep)(za)(pre)(a)(nt)(a)"。
 * <p>
 * 1 <= text.length <= 1000
 * text 仅由小写英文字符组成
 */
public class Problem1147 {
    public static void main(String[] args) {
        Problem1147 problem1147 = new Problem1147();
        //"(ghi)(abcdef)(hello)(adam)(hello)(abcdef)(ghi)"
        String text = "ghiabcdefhelloadamhelloabcdefghi";
        System.out.println(problem1147.longestDecomposition(text));
        System.out.println(problem1147.longestDecomposition2(text));
    }

    /**
     * 递归
     * 从字符串两端开始，找长度最短且相同的前缀和后缀，即前缀和后缀构成段式回文
     * 时间复杂度O(n^2)，空间复杂度O(n)
     *
     * @param text
     * @return
     */
    public int longestDecomposition(String text) {
        //text为空，则无法构成段式回文，返回0
        if (text.length() == 0) {
            return 0;
        }

        for (int i = 0; i < text.length() / 2; i++) {
            //text[0]-text[i]和text[text.length()-1-i]-text[text.length()-1]相同，则前缀和后缀构成段式回文，加2
            if (text.substring(0, i + 1).equals(text.substring(text.length() - 1 - i))) {
                return longestDecomposition(text.substring(i + 1, text.length() - 1 - i)) + 2;
            }
        }

        //text整体作为段式回文，返回1
        return 1;
    }

    /**
     * 迭代
     * 从字符串两端开始，找长度最短且相同的前缀和后缀，即前缀和后缀构成段式回文
     * 时间复杂度O(n^2)，空间复杂度O(1)
     *
     * @param text
     * @return
     */
    public int longestDecomposition2(String text) {
        int count = 0;
        //当前字符串起始下标索引
        int i = 0;
        //当前字符串结尾下标索引
        int j = text.length() - 1;

        while (i <= j) {
            //前缀和后缀的长度
            int length = 1;

            //判断text[i]-text[i+length-1]和text[j-length+1]-text[j]是否相同
            //注意：前缀和后缀不能相交，如果相交，则text[i]-text[j]整体作为段式回文
            while (i + length - 1 < j - length + 1) {
                //前缀和后缀构成段式回文，count加2
                if (equals(text, i, j, length)) {
                    count = count + 2;
                    break;
                }

                length++;
            }

            //text[i]-text[j]整体作为段式回文，count加1
            if (i + length - 1 >= j - length + 1) {
                count++;
                break;
            }

            i = i + length;
            j = j - length;
        }

        return count;
    }

    /**
     * 判断s[i]-s[i+length-1]和s[j-length+1]-s[j]是否相同
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param s
     * @param i
     * @param j
     * @param length
     * @return
     */
    private boolean equals(String s, int i, int j, int length) {
        //前缀起始下标索引
        int left = i;
        //后缀起始下标索引
        int right = j - length + 1;

        while (left <= i + length - 1) {
            if (s.charAt(left) != s.charAt(right)) {
                return false;
            }

            left++;
            right++;
        }

        return true;
    }
}
