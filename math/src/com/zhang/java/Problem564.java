package com.zhang.java;

/**
 * @Date 2024/4/9 08:35
 * @Author zsy
 * @Description 寻找最近的回文数 字节面试题 tplink面试题 类比Problem479、Problem866、Problem1842、Problem2217、Problem2967 回文类比
 * 给定一个表示整数的字符串 n ，返回与它最近的回文整数（不包括自身）。
 * 如果不止一个，返回较小的那个。
 * “最近的”定义为两个整数差的绝对值最小。
 * <p>
 * 输入: n = "123"
 * 输出: "121"
 * <p>
 * 输入: n = "1"
 * 输出: "0"
 * 解释: 0 和 2是最近的回文，但我们返回最小的，也就是 0。
 * <p>
 * 1 <= n.length <= 18
 * n 只由数字组成
 * n 不含前导 0
 * n 代表在 [1, 10^18 - 1] 范围内的整数
 */
public class Problem564 {
    public static void main(String[] args) {
        Problem564 problem564 = new Problem564();
//        String n = "991";
        String n = "10";
        System.out.println(problem564.nearestPalindromic(n));
    }

    /**
     * 模拟
     * 长度为n的回文只需要考虑前一半，拼接上后半部分得到n位回文
     * 假设n的前一半值为a，只需要考虑a-1、a、a+1作为回文前一半值构成回文的情况，三者距离n最近且不为n的值，即为距离n最近的回文数
     * 注意：需要考虑前一半值为99...99或10...00的特殊情况
     * 时间复杂度O(n.length())，空间复杂度O(n.length())
     *
     * @param n
     * @return
     */
    public String nearestPalindromic(String n) {
        //字符串n长度为1的情况，直接返回回文串n-1
        if (n.length() == 1) {
            return (Integer.parseInt(n) - 1) + "";
        }

        //字符串n转为整形long
        long num = Long.parseLong(n);
        //n的前一半值
        int preHalf;

        //n长度为奇数，前一半值包含中间元素
        if (n.length() % 2 == 1) {
            preHalf = Integer.parseInt(n.substring(0, n.length() / 2 + 1));
        } else {
            //n长度为偶数，前一半值不包含中间元素
            preHalf = Integer.parseInt(n.substring(0, n.length() / 2));
        }

        //前一半值为99...99的特殊情况
        if (preHalf == quickPow(10, (preHalf + "").length()) - 1) {
            int a1 = preHalf - 1;
            int a2 = preHalf;
            int a3 = preHalf + 1;
            StringBuilder sb1 = new StringBuilder().append(a1);
            StringBuilder sb2 = new StringBuilder().append(a2);
            StringBuilder sb3 = new StringBuilder().append(a3);

            //n长度为奇数，拼接后一半时不考虑a1、a2的最后一位，不考虑a3的最后两位
            if (n.length() % 2 == 1) {
                a1 = a1 / 10;
                a2 = a2 / 10;
                a3 = a3 / 100;
            } else {
                //n长度为偶数，拼接后一半时不考虑a3的最后一位
                a3 = a3 / 10;
            }

            while (a1 != 0) {
                sb1.append(a1 % 10);
                a1 = a1 / 10;
            }

            while (a2 != 0) {
                sb2.append(a2 % 10);
                a2 = a2 / 10;
            }

            while (a3 != 0) {
                sb3.append(a3 % 10);
                a3 = a3 / 10;
            }

            //最近接n的3个回文
            //使用long避免溢出
            long palindrome1 = Long.parseLong(sb1.toString());
            long palindrome2 = Long.parseLong(sb2.toString());
            long palindrome3 = Long.parseLong(sb3.toString());
            long abs1 = Math.abs(num - palindrome1);
            long abs2 = Math.abs(num - palindrome2);
            long abs3 = Math.abs(num - palindrome3);

            //三者距离n最近且不为n的值，即为距离n最近的回文数
            if (abs2 != 0) {
                if (abs1 < abs2 && abs1 < abs3) {
                    return sb1.toString();
                } else if (abs2 < abs1 && abs2 < abs3) {
                    return sb2.toString();
                } else if (abs3 < abs1 && abs3 < abs2) {
                    return sb3.toString();
                } else if (abs1 == abs2) {
                    //距离n最近的回文数超过1个，则返回较小的的回文
                    return sb1.toString();
                } else if (abs2 == abs3) {
                    //距离n最近的回文数超过1个，则返回较小的的回文
                    return sb2.toString();
                }
            } else {
                if (abs1 < abs3) {
                    return sb1.toString();
                } else if (abs3 < abs1) {
                    return sb3.toString();
                } else {
                    //距离n最近的回文数超过1个，则返回较小的的回文
                    return sb1.toString();
                }
            }
        }

        //前一半值为10...00的特殊情况
        if (preHalf == quickPow(10, (preHalf + "").length() - 1)) {
            int a1 = preHalf - 1;
            int a2 = preHalf;
            int a3 = preHalf + 1;
            StringBuilder sb1 = new StringBuilder().append(a1);
            StringBuilder sb2 = new StringBuilder().append(a2);
            StringBuilder sb3 = new StringBuilder().append(a3);

            //preHalf-1是否为0的标志位
            //n长度只有2位，并且第一位为1的情况，例如：sb1的回文为9
            boolean flag = false;

            if (a1 == 0) {
                flag = true;
            }

            //n长度为奇数，拼接后一半时不考虑a2、a3的最后一位
            if (n.length() % 2 == 1) {
                a2 = a2 / 10;
                a3 = a3 / 10;
            } else {
                //n长度为偶数，拼接后一半时需要多考虑a1的最后一位一次
                sb1.append(a1 % 10);
            }

            while (a1 != 0) {
                sb1.append(a1 % 10);
                a1 = a1 / 10;
            }

            while (a2 != 0) {
                sb2.append(a2 % 10);
                a2 = a2 / 10;
            }

            while (a3 != 0) {
                sb3.append(a3 % 10);
                a3 = a3 / 10;
            }

            //n长度只有2位，并且第一位为1的情况，例如：sb1的回文为9
            if (flag) {
                sb1 = new StringBuilder().append(9);
            }

            //最近接n的3个回文
            //使用long避免溢出
            long palindrome1 = Long.parseLong(sb1.toString());
            long palindrome2 = Long.parseLong(sb2.toString());
            long palindrome3 = Long.parseLong(sb3.toString());
            long abs1 = Math.abs(num - palindrome1);
            long abs2 = Math.abs(num - palindrome2);
            long abs3 = Math.abs(num - palindrome3);

            //三者距离n最近且不为n的值，即为距离n最近的回文数
            if (abs2 != 0) {
                if (abs1 < abs2 && abs1 < abs3) {
                    return sb1.toString();
                } else if (abs2 < abs1 && abs2 < abs3) {
                    return sb2.toString();
                } else if (abs3 < abs1 && abs3 < abs2) {
                    return sb3.toString();
                } else if (abs1 == abs2) {
                    //距离n最近的回文数超过1个，则返回较小的的回文
                    return sb1.toString();
                } else if (abs2 == abs3) {
                    //距离n最近的回文数超过1个，则返回较小的的回文
                    return sb2.toString();
                }
            } else {
                if (abs1 < abs3) {
                    return sb1.toString();
                } else if (abs3 < abs1) {
                    return sb3.toString();
                } else {
                    //距离n最近的回文数超过1个，则返回较小的的回文
                    return sb1.toString();
                }
            }
        }

        int a1 = preHalf - 1;
        int a2 = preHalf;
        int a3 = preHalf + 1;
        StringBuilder sb1 = new StringBuilder().append(a1);
        StringBuilder sb2 = new StringBuilder().append(a2);
        StringBuilder sb3 = new StringBuilder().append(a3);

        //n长度为奇数，拼接后一半时不考虑a1、a2、a3的最后一位
        if (n.length() % 2 == 1) {
            a1 = a1 / 10;
            a2 = a2 / 10;
            a3 = a3 / 10;
        }

        while (a1 != 0) {
            sb1.append(a1 % 10);
            a1 = a1 / 10;
        }

        while (a2 != 0) {
            sb2.append(a2 % 10);
            a2 = a2 / 10;
        }

        while (a3 != 0) {
            sb3.append(a3 % 10);
            a3 = a3 / 10;
        }

        //最近接n的3个回文
        //使用long避免溢出
        long palindrome1 = Long.parseLong(sb1.toString());
        long palindrome2 = Long.parseLong(sb2.toString());
        long palindrome3 = Long.parseLong(sb3.toString());
        long abs1 = Math.abs(num - palindrome1);
        long abs2 = Math.abs(num - palindrome2);
        long abs3 = Math.abs(num - palindrome3);

        //三者距离n最近且不为n的值，即为距离n最近的回文数
        if (abs2 != 0) {
            if (abs1 < abs2 && abs1 < abs3) {
                return sb1.toString();
            } else if (abs2 < abs1 && abs2 < abs3) {
                return sb2.toString();
            } else if (abs3 < abs1 && abs3 < abs2) {
                return sb3.toString();
            } else if (abs1 == abs2) {
                //距离n最近的回文数超过1个，则返回较小的的回文
                return sb1.toString();
            } else if (abs2 == abs3) {
                //距离n最近的回文数超过1个，则返回较小的的回文
                return sb2.toString();
            }
        } else {
            if (abs1 < abs3) {
                return sb1.toString();
            } else if (abs3 < abs1) {
                return sb3.toString();
            } else {
                //距离n最近的回文数超过1个，则返回较小的的回文
                return sb1.toString();
            }
        }

        return "-1";
    }

    /**
     * 递归快速幂
     * 时间复杂度O(logn)，空间复杂度O(logn)
     *
     * @param a
     * @param n
     * @return
     */
    private int quickPow(int a, int n) {
        if (n == 0) {
            return 1;
        }

        if (n % 2 == 0) {
            int temp = quickPow(a, n / 2);
            return temp * temp;
        } else {
            return quickPow(a, n - 1) * a;
        }
    }
}
