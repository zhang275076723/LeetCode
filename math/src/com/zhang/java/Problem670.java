package com.zhang.java;

/**
 * @Date 2023/2/21 10:35
 * @Author zsy
 * @Description 最大交换 字节面试题 类比Problem1328 类比Problem31、Problem556、Problem738、Problem1323
 * 给定一个非负整数，你至多可以交换一次数字中的任意两位。
 * 返回你能得到的最大值。
 * <p>
 * 输入: 2736
 * 输出: 7236
 * 解释: 交换数字2和数字7。
 * <p>
 * 输入: 9973
 * 输出: 9973
 * 解释: 不需要交换。
 * <p>
 * 给定数字的范围是 [0, 10^8]
 */
public class Problem670 {
    public static void main(String[] args) {
        Problem670 problem670 = new Problem670();
        int num = 1332;
        System.out.println(problem670.maximumSwap(num));
    }

    /**
     * 模拟
     * 找比当前元素大且距离当前元素最远的元素，两者进行交换，得到交换一次得到的最大值
     * 类比最小交换，找比当前元素小且距离当前元素最远的元素，两者进行交换，得到交换一次得到的最小值
     * 时间复杂度O((log(num))^2)=O(1)，空间复杂度O(log(num))=O(1)
     *
     * @param num
     * @return
     */
    public int maximumSwap(int num) {
        if (num <= 9) {
            return num;
        }

        //num的char数组
        char[] arr = (num + "").toCharArray();

        for (int i = 0; i < arr.length - 1; i++) {
            int index = i;

            //找比arr[i]大且距离arr[i]最远的元素arr[index]，小于等于保证能够取到最远的最大元素
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[index] <= arr[j]) {
                    index = j;
                }
            }

            //找到比arr[i]大且距离arr[i]最远的元素arr[index]，则arr[i]和arr[index]进行交换，得到交换一次得到的最大值
            if (i != index && arr[i] < arr[index]) {
                char temp = arr[i];
                arr[i] = arr[index];
                arr[index] = temp;

                return Integer.parseInt(new String(arr));
            }
        }

        //num递减，则num就是最大值，不需要交换，直接返回num
        return num;
    }
}
