package com.zhang.java;

/**
 * @Date 2024/4/10 08:36
 * @Author zsy
 * @Description 最大回文数字 类比Problem266、Problem267、Problem409、Problem2131、Problem3035 回文类比
 * 给你一个仅由数字（0 - 9）组成的字符串 num 。
 * 请你找出能够使用 num 中数字形成的 最大回文 整数，并以字符串形式返回。
 * 该整数不含 前导零 。
 * 注意：
 * 你 无需 使用 num 中的所有数字，但你必须使用 至少 一个数字。
 * 数字可以重新排序。
 * <p>
 * 输入：num = "444947137"
 * 输出："7449447"
 * 解释：
 * 从 "444947137" 中选用数字 "4449477"，可以形成回文整数 "7449447" 。
 * 可以证明 "7449447" 是能够形成的最大回文整数。
 * <p>
 * 输入：num = "00009"
 * 输出："9"
 * 解释：
 * 可以证明 "9" 能够形成的最大回文整数。
 * 注意返回的整数不应含前导零。
 * <p>
 * 1 <= num.length <= 10^5
 * num 由数字（0 - 9）组成
 */
public class Problem2384 {
    public static void main(String[] args) {
        Problem2384 problem2384 = new Problem2384();
        String num = "444947137";
        System.out.println(problem2384.largestPalindromic(num));
    }

    /**
     * 哈希表
     * 统计num中0-9数字出现的次数，统计完之后，按照数字大小由大到小拼接出现次数大于1的数字，作为最大回文数的前一半，
     * 拼接之后，按照数字大小由大到小拼接出现次数等于1的数字，作为最大回文数的中间数字，最后拼接后一半得到最大回文数
     * 时间复杂度O(n)，空间复杂度O(max(n,|Σ|)) (n=num.length()，|Σ|=10，只包含0-9的数字)
     *
     * @param num
     * @return
     */
    public String largestPalindromic(String num) {
        int[] count = new int[10];

        for (char c : num.toCharArray()) {
            count[c - '0']++;
        }

        StringBuilder sb = new StringBuilder();

        //按照数字大小由大到小拼接出现次数大于1的数字，作为最大回文数的前一半
        for (int i = 9; i >= 0; i--) {
            if (count[i] > 1) {
                //最大回文数不能包含前导0
                if (i == 0 && sb.length() == 0) {
                    break;
                }

                for (int j = 0; j < count[i] / 2; j++) {
                    sb.append(i);
                }

                //左右个拼接count[i]/2个i，如果剩余1个i，可以作为最大回文数的中间数字
                count[i] = count[i] - count[i] / 2 * 2;
            }
        }

        //最大回文数的后一半
        StringBuilder sb2 = new StringBuilder(sb).reverse();

        //拼接之后，按照数字大小由大到小拼接出现次数等于1的数字，作为最大回文数的中间数字
        for (int i = 9; i >= 0; i--) {
            if (count[i] == 1) {
                sb.append(i);
                break;
            }
        }

        //最大回文数拼接后一半
        sb.append(sb2);

        //考虑sb长度为0的特殊情况
        return sb.length() == 0 ? "0" : sb.toString();
    }
}
