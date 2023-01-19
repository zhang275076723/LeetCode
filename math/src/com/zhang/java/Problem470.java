package com.zhang.java;


import java.util.Random;

/**
 * @Date 2022/6/29 8:35
 * @Author zsy
 * @Description 用 Rand7() 实现 Rand10() 微信面试题、字节面试题
 * 给定方法 rand7 可生成 [1,7] 范围内的均匀随机整数，试写一个方法 rand10 生成 [1,10] 范围内的均匀随机整数。
 * 你只能调用 rand7() 且不能调用其他方法。请不要使用系统的 Math.random() 方法。
 * 每个测试用例将有一个内部参数 n，即你实现的函数 rand10() 在测试时将被调用的次数。
 * 请注意，这不是传递给 rand10() 的参数。
 * <p>
 * 输入: 1
 * 输出: [2]
 * <p>
 * 输入: 2
 * 输出: [2,8]
 * <p>
 * 输入: 3
 * 输出: [3,8,10]
 * <p>
 * 1 <= n <= 10^5
 * <p>
 * rand7()调用次数的 期望值 是多少 ?
 * 你能否尽量少调用 rand7() ?
 */
public class Problem470 {
    public static void main(String[] args) {
        Problem470 problem470 = new Problem470();
        System.out.println(problem470.rand10());
        System.out.println(problem470.rand10_2());
        System.out.println(problem470.rand10_3());
    }

    /**
     * 用rand7()生成等概率的0或1，作为rand10()的每一位，如果生成的rand10()不在[1,10]范围之内，则重新生成
     * 时间复杂度O(1)，空间复杂度O(1)
     *
     * @return
     */
    public int rand10() {
        int random = Integer.MAX_VALUE;

        while (true) {
            //生成的random，小于等于9，直接返回random+1作为随机数
            if (random <= 9) {
                return random + 1;
            } else {
                //生成的random大于9，则需要重新生成
                random = 0;

                for (int i = 1; i <= 4; i++) {
                    random = (random << 1) + rand7Get0Or1();
                }
            }
        }
    }

    /**
     * 用rand7()得到等概率的[1,49]，舍弃[41,49]，将[1,40]等概率映射到[0-9]，再加一得到等概率[1,10]
     * 时间复杂度O(1)，空间复杂度O(1)
     * <p>
     * 总结：
     * 1、rand10生成rand7，则用rand10生成的数如果在1-7之间则直接返回，如果不在，则重新生成rand10
     * 2、rand7生成rand10，则用(rand7-1)*7+rand7扩展等概率范围到[1-49]，找等概率的数，并舍弃一部分数
     *
     * @return
     */
    public int rand10_2() {
        while (true) {
            //生成等概率的[1,49]
            int random = (rand7() - 1) * 7 + rand7();

            //在[1,40]之间，则直接返回；如果超过40，即[41,49]的情况，则重新生成
            if (random <= 40) {
                return random % 10 + 1;
            }
        }
    }

    /**
     * rand10_2()优化
     * 用rand7()得到等概率的[1,49]，对于之前要舍弃[41,49]，减去41乘上7再加上rand7()，得到等概率的[1,63]，
     * 可以将[1,60]等概率映射到[0-9]，再加一得到等概率[1,10]，对于舍弃的[61,63]，
     * 可以进行重复操作，得到等概率[1,21]，舍弃21即可
     * 时间复杂度O(1)，空间复杂度O(1)
     *
     * @return
     */
    public int rand10_3() {
        while (true) {
            //生成等概率的[1,49]
            int random = (rand7() - 1) * 7 + rand7();

            //在[1,40]之间，直接返回
            if (random <= 40) {
                return random % 10 + 1;
            }

            //[41,49]生成等概率的[1,63]
            random = (random - 40 - 1) * 7 + rand7();

            //在[1,60]之间，则直接返回
            if (random <= 60) {
                return random % 10 + 1;
            }

            //[61,63]生成等概率的[1,21]
            random = (random - 60 - 1) * 7 + rand7();

            //在[1,20]之间，则直接返回
            if (random <= 20) {
                return random % 10 + 1;
            }
        }
    }

    /**
     * 用rand7()生成等概率的0或1
     * 1,2,3返回0，5、6、7返回1,4则重新生成
     *
     * @return
     */
    private int rand7Get0Or1() {
        int random = rand7();

        while (true) {
            if (random < 4) {
                return 0;
            } else if (random > 4) {
                return 1;
            } else {
                random = rand7();
            }
        }
    }

    /**
     * 返回1-7的随机整数
     *
     * @return
     */
    private int rand7() {
//        return new Random().nextInt(7) + 1;
        return (int) (Math.random() * 7 + 1);
    }
}
