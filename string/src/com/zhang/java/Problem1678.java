package com.zhang.java;

/**
 * @Date 2024/9/28 08:45
 * @Author zsy
 * @Description 设计 Goal 解析器
 * 请你设计一个可以解释字符串 command 的 Goal 解析器 。
 * command 由 "G"、"()" 和/或 "(al)" 按某种顺序组成。
 * Goal 解析器会将 "G" 解释为字符串 "G"、"()" 解释为字符串 "o" ，"(al)" 解释为字符串 "al" 。
 * 然后，按原顺序将经解释得到的字符串连接成一个字符串。
 * 给你字符串 command ，返回 Goal 解析器 对 command 的解释结果。
 * <p>
 * 输入：command = "G()(al)"
 * 输出："Goal"
 * 解释：Goal 解析器解释命令的步骤如下所示：
 * G -> G
 * () -> o
 * (al) -> al
 * 最后连接得到的结果是 "Goal"
 * <p>
 * 输入：command = "G()()()()(al)"
 * 输出："Gooooal"
 * <p>
 * 输入：command = "(al)G(al)()()G"
 * 输出："alGalooG"
 * <p>
 * 1 <= command.length <= 100
 * command 由 "G"、"()" 和/或 "(al)" 按某种顺序组成
 */
public class Problem1678 {
    public static void main(String[] args) {
        Problem1678 problem1678 = new Problem1678();
        String command = "(al)G(al)()()G";
        System.out.println(problem1678.interpret(command));
    }

    /**
     * 模拟
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param command
     * @return
     */
    public String interpret(String command) {
        int index = 0;
        StringBuilder sb = new StringBuilder();

        while (index < command.length()) {
            char c = command.charAt(index);

            //G -> G
            if (c == 'G') {
                sb.append("G");
                index++;
            } else {
                //() -> o
                if (command.charAt(index + 1) == ')') {
                    sb.append("o");
                    index = index + 2;
                } else {
                    //(al) -> al
                    sb.append("al");
                    index = index + 4;
                }
            }
        }

        return sb.toString();
    }
}
