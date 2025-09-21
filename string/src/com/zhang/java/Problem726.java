package com.zhang.java;

import java.util.*;

/**
 * @Date 2025/2/6 08:19
 * @Author zsy
 * @Description 原子的数量 字节面试题 类比Problem394、Problem471 栈类比
 * 给你一个字符串化学式 formula ，返回 每种原子的数量 。
 * 原子总是以一个大写字母开始，接着跟随 0 个或任意个小写字母，表示原子的名字。
 * 如果数量大于 1，原子后会跟着数字表示原子的数量。
 * 如果数量等于 1 则不会跟数字。
 * 例如，"H2O" 和 "H2O2" 是可行的，但 "H1O2" 这个表达是不可行的。
 * 两个化学式连在一起可以构成新的化学式。
 * 例如 "H2O2He3Mg4" 也是化学式。
 * 由括号括起的化学式并佐以数字（可选择性添加）也是化学式。
 * 例如 "(H2O2)" 和 "(H2O2)3" 是化学式。
 * 返回所有原子的数量，格式为：第一个（按字典序）原子的名字，跟着它的数量（如果数量大于 1），
 * 然后是第二个原子的名字（按字典序），跟着它的数量（如果数量大于 1），以此类推。
 * <p>
 * 输入：formula = "H2O"
 * 输出："H2O"
 * 解释：原子的数量是 {'H': 2, 'O': 1}。
 * <p>
 * 输入：formula = "Mg(OH)2"
 * 输出："H2MgO2"
 * 解释：原子的数量是 {'H': 2, 'Mg': 1, 'O': 2}。
 * <p>
 * 输入：formula = "K4(ON(SO3)2)2"
 * 输出："K4N2O14S4"
 * 解释：原子的数量是 {'K': 4, 'N': 2, 'O': 14, 'S': 4}。
 * <p>
 * 1 <= formula.length <= 1000
 * formula 由英文字母、数字、'(' 和 ')' 组成
 * formula 总是有效的化学式
 */
public class Problem726 {
    public static void main(String[] args) {
        Problem726 problem726 = new Problem726();
//        String formula = "K4(ON(SO3)2)2";
        String formula = "Mg(Uub)2";
        System.out.println(problem726.countOfAtoms(formula));
    }

    /**
     * 栈+哈希表
     * 1、当前字符为字母，则找当前原子的字符串和其个数，当前原子和其个数入结果哈希表中
     * 2、当前字符为'('，则结果哈希表入栈，结果哈希表赋值为新的空哈希表
     * 3、当前字符为')'，则栈顶哈希表出栈，结果哈希表中元素加入栈顶哈希表中，结果哈希表赋值为栈顶哈希表
     * 时间复杂度O(n^2+nlogn)=O(n^2)，空间复杂度O(n) (栈的最大深度为O(n)，每次入栈出栈需要O(n)，总共需要O(n^2)时间)
     *
     * @param formula
     * @return
     */
    public String countOfAtoms(String formula) {
        //结果哈希表
        Map<String, Integer> map = new HashMap<>();
        //哈希表栈
        //key：原子名称，value：当前原子的个数
        Stack<Map<String, Integer>> stack = new Stack<>();

        for (int i = 0; i < formula.length(); i++) {
            char c = formula.charAt(i);

            //当前字符为字母，则找当前原子的字符串和其个数，当前原子和其个数入结果哈希表中
            if (c >= 'A' && c <= 'Z') {
                StringBuilder sb = new StringBuilder();
                sb.append(c);

                //注意：原子长度不一定小于等于2，有可能为3，例如Uub，所以要用while，不能用if
                while (i + 1 < formula.length() && formula.charAt(i + 1) >= 'a' && formula.charAt(i + 1) <= 'z') {
                    sb.append(formula.charAt(i + 1));
                    i++;
                }

                //当前原子出现的次数
                int count = 0;

                if (i + 1 < formula.length() && formula.charAt(i + 1) >= '0' && formula.charAt(i + 1) <= '9') {
                    while (i + 1 < formula.length() && formula.charAt(i + 1) >= '0' && formula.charAt(i + 1) <= '9') {
                        count = count * 10 + formula.charAt(i + 1) - '0';
                        i++;
                    }
                } else {
                    //如果后面没有数字，则当前原子出现的次数为1
                    count = 1;
                }

                map.put(sb.toString(), map.getOrDefault(sb.toString(), 0) + count);
            } else if (c == '(') {
                //当前字符为'('，则结果哈希表入栈，结果哈希表赋值为新的空哈希表
                stack.push(map);
                map = new HashMap<>();
            } else {
                //当前字符为')'，则栈顶哈希表出栈，结果哈希表中元素加入栈顶哈希表中，结果哈希表赋值为栈顶哈希表

                //')'后的数字，即map中原子需要乘以的个数
                int count = 0;

                if (i + 1 < formula.length() && formula.charAt(i + 1) >= '0' && formula.charAt(i + 1) <= '9') {
                    while (i + 1 < formula.length() && formula.charAt(i + 1) >= '0' && formula.charAt(i + 1) <= '9') {
                        count = count * 10 + formula.charAt(i + 1) - '0';
                        i++;
                    }
                } else {
                    //如果后面没有数字，则需要乘以的个数为1
                    count = 1;
                }

                //栈顶哈希表
                Map<String, Integer> peekMap = stack.pop();

                for (Map.Entry<String, Integer> entry : map.entrySet()) {
                    peekMap.put(entry.getKey(), peekMap.getOrDefault(entry.getKey(), 0) + entry.getValue() * count);
                }

                map = peekMap;
            }
        }

        PriorityQueue<String> priorityQueue = new PriorityQueue<>(new Comparator<String>() {
            @Override
            public int compare(String str1, String str2) {
                //按照字典序排序
                return str1.compareTo(str2);
            }
        });

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            priorityQueue.offer(entry.getKey());
        }

        StringBuilder sb = new StringBuilder();

        while (!priorityQueue.isEmpty()) {
            String str = priorityQueue.poll();
            sb.append(str);

            if (map.get(str) > 1) {
                sb.append(map.get(str));
            }
        }

        return sb.toString();
    }
}
