package com.zhang.java;

/**
 * @Date 2023/6/17 10:47
 * @Author zsy
 * @Description 1比特与2比特字符 类比Problem561
 * 有两种特殊字符：
 * 第一种字符可以用一比特 0 表示
 * 第二种字符可以用两比特（10 或 11）表示
 * 给你一个以 0 结尾的二进制数组 bits ，如果最后一个字符必须是一个一比特字符，则返回 true 。
 * <p>
 * 输入: bits = [1, 0, 0]
 * 输出: true
 * 解释: 唯一的解码方式是将其解析为一个两比特字符和一个一比特字符。
 * 所以最后一个字符是一比特字符。
 * <p>
 * 输入：bits = [1,1,1,0]
 * 输出：false
 * 解释：唯一的解码方式是将其解析为两比特字符和两比特字符。
 * 所以最后一个字符不是一比特字符。
 * <p>
 * 1 <= bits.length <= 1000
 * bits[i] 为 0 或 1
 */
public class Problem717 {
    public static void main(String[] args) {
        Problem717 problem717 = new Problem717();
        int[] bits = {1, 1, 1, 0};
        System.out.println(problem717.isOneBitCharacter(bits));
    }

    /**
     * 模拟
     * 从前往后遍历，如果当前值为1，则说明1和它后面一位组成两个比特的字符，向后跳2步；
     * 如果当前值为0，则说明它是单个字符，向后跳1步，
     * 遍历完之后判断是否能够跳跃到数组末尾位置
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param bits
     * @return
     */
    public boolean isOneBitCharacter(int[] bits) {
        if (bits == null || bits.length == 0) {
            return false;
        }

        int index = 0;

        while (index < bits.length - 1) {
            //当前值为1，则说明1和它后面一位组成两个比特的字符，向后跳2步
            if (bits[index] == 1) {
                index = index + 2;
            } else if (bits[index] == 0) {
                //当前值为0，则说明它是单个字符，向后跳1步
                index++;
            }
        }

        //遍历完之后判断是否能够跳跃到数组末尾位置
        return index == bits.length - 1;
    }
}
