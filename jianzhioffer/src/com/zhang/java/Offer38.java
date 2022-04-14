package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/3/24 12:09
 * @Author zsy
 * @Description 输入一个字符串，打印出该字符串中字符的所有排列。
 * 你可以以任意顺序返回这个字符串数组，但里面不能有重复元素。
 * <p>
 * 输入：s = "aab"
 * 输出：["aba","aab","baa"]
 */
public class Offer38 {
    //使用Set去重
    Set<String> set = new HashSet<>();

    public static void main(String[] args) {
        Offer38 offer38 = new Offer38();
        String s = "aab";
        String[] strings = offer38.permutation(s);
        System.out.println(Arrays.toString(strings));
    }

    /**
     * 回溯，时间复杂度O(n*n!)，空间复杂度O(n)
     * 全排列有O(n!)个，每个需要O(n)时间生成，所以时间复杂度为O(n*n!)
     * 需要O(n)的栈进行回溯，所以空间复杂度为O(n)
     *
     * @param s
     * @return
     */
    public String[] permutation(String s) {
        backtrack(s, new StringBuilder(), new boolean[s.length()], 0);

        String[] strings = new String[set.size()];
        int i = 0;
        for (String str : set) {
            strings[i] = str;
            i++;
        }
        return strings;
    }

    /**
     * @param s       要全排列的字符串s
     * @param sb      用sb表示每个结果
     * @param visited 访问数组
     * @param t       对第t位访问
     */
    public void backtrack(String s, StringBuilder sb, boolean[] visited, int t) {
        if (t >= s.length()) {
            set.add(sb.toString());
            return;
        }

        for (int i = 0; i < s.length(); i++) {
            if (!visited[i]) {
                visited[i] = true;
                sb.append(s.charAt(i));
                backtrack(s, sb, visited, t + 1);
                sb.deleteCharAt(t);
                visited[i] = false;
            }
        }
    }
}
