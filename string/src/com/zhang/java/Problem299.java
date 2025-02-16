package com.zhang.java;

/**
 * @Date 2025/4/9 08:36
 * @Author zsy
 * @Description 猜数字游戏 类比Problem438、Problem1347
 * 你在和朋友一起玩 猜数字（Bulls and Cows）游戏，该游戏规则如下：
 * 写出一个秘密数字，并请朋友猜这个数字是多少。朋友每猜测一次，你就会给他一个包含下述信息的提示：
 * 猜测数字中有多少位属于数字和确切位置都猜对了（称为 "Bulls"，公牛），
 * 有多少位属于数字猜对了但是位置不对（称为 "Cows"，奶牛）。
 * 也就是说，这次猜测中有多少位非公牛数字可以通过重新排列转换成公牛数字。
 * 给你一个秘密数字 secret 和朋友猜测的数字 guess ，请你返回对朋友这次猜测的提示。
 * 提示的格式为 "xAyB" ，x 是公牛个数， y 是奶牛个数，A 表示公牛，B 表示奶牛。
 * 请注意秘密数字和朋友猜测的数字都可能含有重复数字。
 * <p>
 * 输入：secret = "1807", guess = "7810"
 * 输出："1A3B"
 * 解释：数字和位置都对（公牛）用 '|' 连接，数字猜对位置不对（奶牛）的采用斜体加粗标识。
 * "1807"
 * < |
 * "7810"
 * <p>
 * 输入：secret = "1123", guess = "0111"
 * 输出："1A1B"
 * 解释：数字和位置都对（公牛）用 '|' 连接，数字猜对位置不对（奶牛）的采用斜体加粗标识。
 * "1123"        "1123"
 * < |      or     |
 * "0111"        "0111"
 * 注意，两个不匹配的 1 中，只有一个会算作奶牛（数字猜对位置不对）。通过重新排列非公牛数字，其中仅有一个 1 可以成为公牛数字。
 * <p>
 * 1 <= secret.length, guess.length <= 1000
 * secret.length == guess.length
 * secret 和 guess 仅由数字组成
 */
public class Problem299 {
    public static void main(String[] args) {
        Problem299 problem299 = new Problem299();
        String secret = "1123";
        String guess = "0111";
        System.out.println(problem299.getHint(secret, guess));
        System.out.println(problem299.getHint2(secret, guess));
    }

    /**
     * 模拟 (2个数组)
     * secret[i]和guess[i]相等，则当前位为公牛
     * secret[i]和guess[i]不相等，统计secret和guess中当前位不同数字出现的次数，0-9出现次数的最小值之和即为奶牛个数
     * 时间复杂度O(n+|Σ|)=O(n)，空间复杂度O(|Σ|)=O(1) (|Σ|=10，只包含0-9的数字)
     *
     * @param secret
     * @param guess
     * @return
     */
    public String getHint(String secret, String guess) {
        int bulls = 0;
        int cows = 0;

        //secret和guess中当前位不同数字出现的次数
        int[] secretArr = new int[10];
        int[] guessArr = new int[10];

        for (int i = 0; i < secret.length(); i++) {
            int num1 = secret.charAt(i) - '0';
            int num2 = guess.charAt(i) - '0';

            if (num1 == num2) {
                bulls++;
            } else {
                secretArr[num1]++;
                guessArr[num2]++;
            }
        }

        //0-9出现次数的最小值之和即为奶牛个数
        for (int i = 0; i < 10; i++) {
            cows = cows + Math.min(secretArr[i], guessArr[i]);
        }

        return new StringBuilder().append(bulls).append('A').append(cows).append('B').toString();
    }

    /**
     * 模拟 (1个数组)
     * 数组存储secret和guess中当前位不同，secret中数字出现的次数减去guess中数字出现的次数
     * secret[i]和guess[i]相等，则当前位为公牛
     * secret[i]和guess[i]不相等，secret当前位数字出现的次数小于0，又因为secret是加1，则说明guess之前存在当前位数字，奶牛数量加1；
     * guess当前位数字出现的次数大于0，又因为guess是减1，则说明secret之前存在当前位数字，奶牛数量加1；
     * 时间复杂度O(n)，空间复杂度O(|Σ|)=O(1) (|Σ|=10，只包含0-9的数字)
     *
     * @param secret
     * @param guess
     * @return
     */
    public String getHint2(String secret, String guess) {
        int bulls = 0;
        int cows = 0;

        //secret和guess中当前位不同，secret中数字出现的次数减去guess中数字出现的次数
        int[] arr = new int[10];

        for (int i = 0; i < secret.length(); i++) {
            int num1 = secret.charAt(i) - '0';
            int num2 = guess.charAt(i) - '0';

            if (num1 == num2) {
                bulls++;
            } else {
                //secret当前位数字出现的次数小于0，又因为secret是加1，则说明guess之前存在当前位数字，奶牛数量加1
                if (arr[num1] < 0) {
                    cows++;
                }

                //guess当前位数字出现的次数大于0，又因为guess是减1，则说明secret之前存在当前位数字，奶牛数量加1
                if (arr[num2] > 0) {
                    cows++;
                }

                arr[num1]++;
                arr[num2]--;
            }
        }

        return new StringBuilder().append(bulls).append('A').append(cows).append('B').toString();
    }
}
