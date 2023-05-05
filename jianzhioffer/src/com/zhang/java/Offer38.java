package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/3/24 12:09
 * @Author zsy
 * @Description 字符串的排列 回溯+剪枝类比Problem17、Problem39、Problem40、Problem46、Problem47、Problem77、Problem78、Problem90、Problem97、Problem216、Problem377、Problem491、Problem679、Offer17
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
     * 回溯+剪枝，难点在于如何去重(不建议使用set去重)
     * 时间复杂度O(n*n!)，空间复杂度O(n) (全排列有n!个，每个字符串需要O(n)时间复制到结果集合中)
     *
     * @param s
     * @return
     */
    public String[] permutation(String s) {
        if (s.length() == 1) {
            return new String[]{s};
        }


        char[] c = s.toCharArray();

        //将字符串从小到大排序，便于剪枝去重
        mergeSort(c, 0, c.length - 1, new char[c.length]);

        List<String> list = new ArrayList<>();

        backtrack(0, c, new StringBuilder(), new boolean[s.length()], list);

        String[] result = new String[list.size()];

        for (int i = 0; i < result.length; i++) {
            result[i] = list.get(i);
        }

        return result;
    }

    /**
     * @param t       当前访问c的索引下标
     * @param c       要全排列的字符串s
     * @param sb      用sb表示每个结果
     * @param visited 访问数组
     * @param result  结果集合
     */
    private void backtrack(int t, char[] c, StringBuilder sb, boolean[] visited, List<String> result) {
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

            backtrack(t + 1, c, sb, visited, result);

            sb.delete(sb.length() - 1, sb.length());
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
        int k = left;

        while (i <= mid && j <= right) {
            if (c[i] < c[j]) {
                tempArr[k] = c[i];
                i++;
                k++;
            } else {
                tempArr[k] = c[j];
                j++;
                k++;
            }
        }

        while (i <= mid) {
            tempArr[k] = c[i];
            i++;
            k++;
        }

        while (j <= right) {
            tempArr[k] = c[j];
            j++;
            k++;
        }

        for (k = left; k <= right; k++) {
            c[k] = tempArr[k];
        }
    }
}
