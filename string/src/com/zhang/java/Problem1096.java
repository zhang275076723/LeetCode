package com.zhang.java;

import java.util.*;

/**
 * @Date 2023/10/4 08:50
 * @Author zsy
 * @Description 花括号展开 II 括号类比 栈类比 回溯+剪枝类比
 * 如果你熟悉 Shell 编程，那么一定了解过花括号展开，它可以用来生成任意字符串。
 * 花括号展开的表达式可以看作一个由 花括号、逗号 和 小写英文字母 组成的字符串，定义下面几条语法规则：
 * 如果只给出单一的元素 x，那么表达式表示的字符串就只有 "x"。R(x) = {x}
 * 例如，表达式 "a" 表示字符串 "a"。
 * 而表达式 "w" 就表示字符串 "w"。
 * 当两个或多个表达式并列，以逗号分隔，我们取这些表达式中元素的并集。R({e_1,e_2,...}) = R(e_1) ∪ R(e_2) ∪ ...
 * 例如，表达式 "{a,b,c}" 表示字符串 "a","b","c"。
 * 而表达式 "{{a,b},{b,c}}" 也可以表示字符串 "a","b","c"。
 * 要是两个或多个表达式相接，中间没有隔开时，我们从这些表达式中各取一个元素依次连接形成字符串。R(e_1 + e_2) = {a + b for (a, b) in R(e_1) × R(e_2)}
 * 例如，表达式 "{a,b}{c,d}" 表示字符串 "ac","ad","bc","bd"。
 * 表达式之间允许嵌套，单一元素与表达式的连接也是允许的。
 * 例如，表达式 "a{b,c,d}" 表示字符串 "ab","ac","ad"。
 * 例如，表达式 "a{b,c}{d,e}f{g,h}" 可以表示字符串 "abdfg", "abdfh", "abefg", "abefh", "acdfg", "acdfh", "acefg", "acefh"。
 * 给出表示基于给定语法规则的表达式 expression，返回它所表示的所有字符串组成的有序列表。
 * 假如你希望以「集合」的概念了解此题，也可以通过点击 “显示英文描述” 获取详情。
 * <p>
 * 输入：expression = "{a,b}{c,{d,e}}"
 * 输出：["ac","ad","ae","bc","bd","be"]
 * <p>
 * 输入：expression = "{{a,z},a{b,c},{ab,z}}"
 * 输出：["a","ab","ac","z"]
 * 解释：输出中 不应 出现重复的组合结果。
 * <p>
 * 1 <= expression.length <= 60
 * expression[i] 由 '{'，'}'，',' 或小写英文字母组成
 * 给出的表达式 expression 用以表示一组基于题目描述中语法构造的字符串
 */
public class Problem1096 {
    public static void main(String[] args) {
        Problem1096 problem1096 = new Problem1096();
//        //a、b、c
//        String s = "{{a,b},{b,c}}";
//        //ab、ac、bb、bc
//        String s = "{a,b}{b,c}";
        //a、ab、ac、z
        String s = "{{a,z},a{b,c},{ab,z}}";
        System.out.println(problem1096.braceExpansionII(s));
        System.out.println(problem1096.braceExpansionII2(s));
        System.out.println(problem1096.braceExpansionII3(s));
    }

    /**
     * 回溯+剪枝
     * 找第一个'}'(即优先处理最内层花括号)，如果不存在，则当前字符串为不包含花括号的表达式，直接加入set中；
     * 如果存在，找'}'对应的'{'，根据','拆分花括号，得到花括号中每个字符串，拼接前缀、花括号中字符串、后缀，继续分治
     *
     * @param expression
     * @return
     */
    public List<String> braceExpansionII(String expression) {
        //用于去重
        Set<String> set = new HashSet<>();

        dfs(expression, set);

        List<String> list = new ArrayList<>(set);

        //按照字典顺序排序，即由小到大排序
        list.sort(new Comparator<String>() {
            @Override
            public int compare(String str1, String str2) {
                return str1.compareTo(str2);
            }
        });

        return list;
    }

    /**
     * bfs，分治法
     * 出队一个字符串，找第一个'}'(即优先处理最内层花括号)，如果不存在，则当前字符串为不包含花括号的表达式，直接加入set中；
     * 如果存在，找'}'对应的'{'，根据','拆分花括号，得到花括号中每个字符串，拼接前缀、花括号中字符串、后缀，加入队列中
     *
     * @param expression
     * @return
     */
    public List<String> braceExpansionII2(String expression) {
        //用于去重
        Set<String> set = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        queue.offer(expression);

        while (!queue.isEmpty()) {
            String str = queue.poll();

            //在str中找第一个'}'(即优先处理最内层花括号)
            int j = str.indexOf('}');

            //str中不存在'}'，则str为不包含花括号的表达式，直接加入set中
            if (j == -1) {
                set.add(str);
                continue;
            }

            //与下标索引j'}'相对应的'{'下标索引
            int i = j;

            while (i >= 0 && str.charAt(i) != '{') {
                i--;
            }

            //要拆分花括号的前缀
            String prefix = str.substring(0, i);
            //要拆分花括号的后缀
            String suffix = str.substring(j + 1);

            int start = i + 1;

            //根据','拆分花括号中字符串[i+1,j-1]，得到花括号中每个字符串
            while (start <= j - 1) {
                int end = start;

                while (end <= j - 1 && str.charAt(end) != ',') {
                    end++;
                }

                queue.offer(prefix + str.substring(start, end) + suffix);
                start = end + 1;
            }
        }

        List<String> list = new ArrayList<>(set);

        //按照字典顺序排序，即由小到大排序
        list.sort(new Comparator<String>() {
            @Override
            public int compare(String str1, String str2) {
                return str1.compareTo(str2);
            }
        });

        return list;
    }

    /**
     * 双栈，字符串栈和操作符栈
     * ','作为'+'
     * '{'、'}'作为'('、')'
     * aa{}、{}aa、{}{}、aabb之间需要添加'*'，即aa*{}、{}*aa、{}*{}、aa*bb
     * 按照基本计算器双栈的形式进行运算
     *
     * @param expression
     * @return
     */
    public List<String> braceExpansionII3(String expression) {
        //字符串集合栈，使用set用于去重
        Deque<Set<String>> stringSetStack = new LinkedList<>();
        //操作符栈
        Deque<Character> opsStack = new LinkedList<>();

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            //当前字符为字母，则作为基本计算器的数字
            if ('a' <= c && c <= 'z') {
                //当前字符的前一个字符为'{'或字母，则需要添加'*'
                if (i > 0 && (expression.charAt(i - 1) == '}' ||
                        ('a' <= expression.charAt(i - 1) && expression.charAt(i - 1) <= 'z'))) {
                    opsStack.offerLast('*');
                }

                stringSetStack.offerLast(new HashSet<String>() {{
                    add(c + "");
                }});
            } else if (c == '{') {
                //当前字符为'{'，则作为基本计算器的'('

                //判断是否需要添加'*'
                if (i > 0 && (expression.charAt(i - 1) == '}' ||
                        ('a' <= expression.charAt(i - 1) && expression.charAt(i - 1) <= 'z'))) {
                    opsStack.offerLast('*');
                }

                opsStack.offerLast('(');
            } else if (c == '}') {
                //当前字符为'}'，则作为基本计算器的')'

                while (!opsStack.isEmpty() && opsStack.peekLast() != '(') {
                    operation(stringSetStack, opsStack);
                }

                //和')'对应的'('出栈
                opsStack.pollLast();
            } else {
                //当前字符为','，则作为基本计算器的'+'

                //操作符栈顶运算符优先级大于等于当前运算符优先级，则操作符栈顶运算符出栈，字符串集合栈出栈，
                //进行运算，再将运算结果重新入字符串集合栈
                while (!opsStack.isEmpty() && getPriority(opsStack.peekLast()) >= getPriority('+')) {
                    operation(stringSetStack, opsStack);
                }

                opsStack.offerLast('+');
            }
        }

        //操作符栈非空，即存在未运算的运算符，操作符栈顶运算符出栈，字符串集合栈出栈，进行运算，再将运算结果重新入字符串集合栈
        while (!opsStack.isEmpty()) {
            operation(stringSetStack, opsStack);
        }

        //字符串集合栈中剩余的最后一个元素，即为运算结果
        List<String> list = new ArrayList<>(stringSetStack.pollLast());

        //按照字典顺序排序，即由小到大排序
        list.sort(new Comparator<String>() {
            @Override
            public int compare(String str1, String str2) {
                return str1.compareTo(str2);
            }
        });

        return list;
    }

    private void dfs(String expression, Set<String> set) {
        //在expression中找第一个'}'(即优先处理最内层花括号)
        int j = expression.indexOf('}');

        //expression中不存在'}'，则expression为不包含花括号的表达式，直接加入set中
        if (j == -1) {
            set.add(expression);
            return;
        }

        //与下标索引j'}'相对应的'{'下标索引
        int i = j;

        while (i >= 0 && expression.charAt(i) != '{') {
            i--;
        }

        //要拆分花括号的前缀
        String prefix = expression.substring(0, i);
        //要拆分花括号的后缀
        String suffix = expression.substring(j + 1);

        int start = i + 1;

        //根据','拆分花括号中字符串[i+1,j-1]，得到花括号中每个字符串
        while (start <= j - 1) {
            int end = start;

            while (end <= j - 1 && expression.charAt(end) != ',') {
                end++;
            }

            dfs(prefix + expression.substring(start, end) + suffix, set);

            start = end + 1;
        }
    }

    /**
     * 字符串集合栈出栈两个字符串集合，操作符栈出栈运算符，进行运算，得到的结果重新入字符串集合栈
     *
     * @param stringSetStack
     * @param opsStack
     */
    private void operation(Deque<Set<String>> stringSetStack, Deque<Character> opsStack) {
        //先出栈的字符串集合为set2，后出栈的字符串集合为set1
        Set<String> set2 = stringSetStack.pollLast();
        Set<String> set1 = stringSetStack.pollLast();
        char c = opsStack.pollLast();

        if (c == '+') {
            Set<String> set = new HashSet<>();

            for (String str : set1) {
                set.add(str);
            }

            for (String str : set2) {
                set.add(str);
            }

            stringSetStack.offerLast(set);
        } else if (c == '*') {
            Set<String> set = new HashSet<>();

            for (String str1 : set1) {
                for (String str2 : set2) {
                    set.add(str1 + str2);
                }
            }

            stringSetStack.offerLast(set);
        }
    }

    /**
     * 返回当前运算符的优先级
     * '+'、'-'优先级为1
     * '*'、'/'优先级为2
     *
     * @param c
     * @return
     */
    private int getPriority(char c) {
        if (c == '+' || c == '-') {
            return 1;
        } else if (c == '*' || c == '/') {
            return 2;
        } else {
            return -1;
        }
    }
}
