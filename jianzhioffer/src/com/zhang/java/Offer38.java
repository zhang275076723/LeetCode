package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/3/24 12:09
 * @Author zsy
 * @Description 字符串的排列 类比Problem39、Problem40、Problem301
 * 输入一个字符串，打印出该字符串中字符的所有排列。
 * 你可以以任意顺序返回这个字符串数组，但里面不能有重复元素。
 * <p>
 * 输入：s = "aab"
 * 输出：["aba","aab","baa"]
 * <p>
 * 1 <= s 的长度 <= 8
 */
public class Offer38 {

    public static void main(String[] args) {
        Offer38 offer38 = new Offer38();
        String s = "aab";
        String[] strings = offer38.permutation(s);
        System.out.println(Arrays.toString(strings));
    }

    /**
     * 回溯+剪枝，难点在于如何去重，不建议使用set去重
     * 时间复杂度O(n*n!)，空间复杂度O(n) (全排列有n!个，每个字符串需要O(n)时间复制到结果集合中)
     *
     * @param s
     * @return
     */
    public String[] permutation(String s) {
        if (s.length() == 1) {
            return new String[]{s};
        }

        //将字符串从小到大排序，便于剪枝去重
        char[] c = s.toCharArray();
        mergeSort(c, 0, c.length - 1, new char[c.length]);

        List<String> result = new ArrayList<>();

        backtrack(c, 0, new StringBuilder(), new boolean[s.length()], result);

        String[] strResult = new String[result.size()];

        for (int i = 0; i < strResult.length; i++) {
            strResult[i] = result.get(i);
        }

        return strResult;
    }

    /**
     * @param c       要全排列的字符串s
     * @param t       当前访问c的索引下标
     * @param sb      用sb表示每个结果
     * @param visited 访问数组
     * @param result  结果集合
     */
    private void backtrack(char[] c, int t, StringBuilder sb, boolean[] visited, List<String> result) {
        if (t == c.length) {
            result.add(sb.toString());
            return;
        }

        for (int i = 0; i < c.length; i++) {
            if (visited[i]) {
                continue;
            }

            //去重，当前字符和前一个字符相同，并且前一个字符没有被访问，说明本次和上次情况相同，直接进行下次循环
            if (i > 0 && !visited[i - 1] && c[i] == c[i - 1]) {
                continue;
            }

            visited[i] = true;
            sb.append(c[i]);

            backtrack(c, t + 1, sb, visited, result);

            sb.deleteCharAt(sb.length() - 1);
            visited[i] = false;
        }
    }

    private void mergeSort(char[] c, int left, int right, char[] tempArr) {
        if (left < right) {
            int mid = left + ((right - left) >> 1);
            mergeSort(c, left, mid, tempArr);
            mergeSort(c, mid + 1, right, tempArr);
            merge(c, left, mid, right, tempArr);
        }
    }

    private void merge(char[] c, int left, int mid, int right, char[] tempArr) {
        int i = left;
        int j = mid + 1;
        int index = left;

        while (i <= mid && j <= right) {
            if (c[i] < c[j]) {
                tempArr[index] = c[i];
                i++;
            } else {
                tempArr[index] = c[j];
                j++;
            }
            index++;
        }

        while (i <= mid) {
            tempArr[index] = c[i];
            i++;
            index++;
        }

        while (j <= right) {
            tempArr[index] = c[j];
            j++;
            index++;
        }

        for (index = left; index <= right; index++) {
            c[index] = tempArr[index];
        }
    }
}
