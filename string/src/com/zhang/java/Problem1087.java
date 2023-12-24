package com.zhang.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @Date 2023/10/4 08:10
 * @Author zsy
 * @Description 花括号展开 类比Problem17 括号类比Problem20、Problem22、Problem32、Problem301、Problem678、Problem856、Problem1096 回溯+剪枝类比
 * 给定一个表示单词列表的字符串 s 。
 * 单词中的每个字母都有一个或多个选项。
 * 如果有一个选项，则字母按原样表示。
 * 如果有多个选项，则用大括号分隔选项。
 * 例如,  "{a,b,c}"  表示选项  ["a", "b", "c"]  。
 * 例如，如果  s = "a{b,c}"  ，第一个字符总是 'a' ，但第二个字符可以是 'b' 或 'c' 。
 * 原来的列表是 ["ab", "ac"] 。
 * 请你 按字典顺序 ，返回所有以这种方式形成的单词。
 * <p>
 * 输入：s = "{a,b}c{d,e}f"
 * 输出：["acdf","acef","bcdf","bcef"]
 * <p>
 * 输入：s = "abcd"
 * 输出：["abcd"]
 * <p>
 * 1 <= s.length <= 50
 * s 由括号 '{}' , ',' 和小写英文字母组成。
 * s 保证是一个有效的输入。
 * 没有嵌套的大括号。
 * 在一对连续的左括号和右括号内的所有字符都是不同的。
 */
public class Problem1087 {
    public static void main(String[] args) {
        Problem1087 problem1087 = new Problem1087();
        String s = "{a,b}c{d,e}f";
        System.out.println(Arrays.toString(problem1087.expand(s)));
    }

    /**
     * 回溯+剪枝
     * 通过s得到每个位置能够选择的字母集合，通过该集合进行回溯+剪枝
     * 时间复杂度O()，空间复杂度O()
     *
     * @param s
     * @return
     */
    public String[] expand(String s) {
        //s中每个位置能够选择的字母集合
        List<List<String>> strList = new ArrayList<>();
        int index = 0;

        //将s中每个位置能够选择的字母保存到strList中
        while (index < s.length()) {
            //当前位置只有一种选择
            if (s.charAt(index) != '{') {
                int start = index;
                List<String> list = new ArrayList<>();

                while (index < s.length() && s.charAt(index) != '{') {
                    index++;
                }

                String str = s.substring(start, index);
                list.add(str);
                strList.add(list);
            } else {
                //当前位置有多种选择

                //index右移一位，表示跳过'{'
                index++;
                List<String> list = new ArrayList<>();

                while (index < s.length() && s.charAt(index) != '}') {
                    int start = index;

                    while (index < s.length() && s.charAt(index) != ',' && s.charAt(index) != '}') {
                        index++;
                    }

                    String str = s.substring(start, index);
                    list.add(str);

                    //后面还有，才需要index右移1位，如果是'}'，则不需要右移
                    if (s.charAt(index) == ',') {
                        index++;
                    }
                }

                //index右移一位，表示跳过'}'
                index++;
                strList.add(list);
            }
        }

        List<String> resultList = new ArrayList<>();

        backtrack(0, new StringBuilder(), strList, resultList);

        String[] result = new String[resultList.size()];

        for (int i = 0; i < resultList.size(); i++) {
            result[i] = resultList.get(i);
        }

        //结果按照字典顺序排序，即由小到大排序
        quickSort(result, 0, result.length - 1);

        return result;
    }

    private void backtrack(int t, StringBuilder sb, List<List<String>> strList, List<String> resultList) {
        if (t == strList.size()) {
            resultList.add(sb.toString());
            return;
        }

        List<String> list = strList.get(t);

        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
            backtrack(t + 1, sb, strList, resultList);
            sb.delete(sb.length() - 1, sb.length());
        }
    }

    private void quickSort(String[] arr, int left, int right) {
        if (left >= right) {
            return;
        }

        int pivot = partition(arr, left, right);
        quickSort(arr, left, pivot - 1);
        quickSort(arr, pivot + 1, right);
    }

    private int partition(String[] arr, int left, int right) {
        int randomIndex = new Random().nextInt(right - left + 1) + left;
        String value = arr[left];
        arr[left] = arr[randomIndex];
        arr[randomIndex] = value;

        String temp = arr[left];

        while (left < right) {
            while (left < right && arr[right].compareTo(arr[left]) >= 0) {
                right--;
            }
            arr[left] = arr[right];

            while (left < right && arr[left].compareTo(arr[right]) <= 0) {
                left++;
            }
            arr[right] = arr[left];
        }

        arr[left] = temp;

        return left;
    }
}
