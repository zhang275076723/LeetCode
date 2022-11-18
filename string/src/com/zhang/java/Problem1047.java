package com.zhang.java;

/**
 * @Date 2022/11/17 10:51
 * @Author zsy
 * @Description 删除字符串中的所有相邻重复项
 * 给出由小写字母组成的字符串 S，重复项删除操作会选择两个相邻且相同的字母，并删除它们。
 * 在 S 上反复执行重复项删除操作，直到无法继续删除。
 * 在完成所有重复项删除操作后返回最终的字符串。答案保证唯一。
 * <p>
 * 输入："abbaca"
 * 输出："ca"
 * 解释：
 * 例如，在 "abbaca" 中，我们可以删除 "bb" 由于两字母相邻且相同，这是此时唯一可以执行删除操作的重复项。
 * 之后我们得到字符串 "aaca"，其中又只有 "aa" 可以执行重复项删除操作，所以最后的字符串为 "ca"。
 * <p>
 * 1 <= S.length <= 20000
 * S 仅由小写英文字母组成。
 */
public class Problem1047 {
    public static void main(String[] args) {
        Problem1047 problem1047 = new Problem1047();
        String s = "abbaca";
        System.out.println(problem1047.removeDuplicates(s));
    }

    /**
     * 栈
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param s
     * @return
     */
    public String removeDuplicates(String s) {
        if (s == null || s.length() < 2) {
            return s;
        }

        StringBuilder sb = new StringBuilder();

        for (char c : s.toCharArray()) {
            //当前元素和sb末尾元素相等时，即可以去除相同元素
            if (sb.length() != 0 && sb.charAt(sb.length() - 1) == c) {
                sb.delete(sb.length() - 1, sb.length());
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }
}
