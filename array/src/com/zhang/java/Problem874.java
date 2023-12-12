package com.zhang.java;

import java.util.HashSet;
import java.util.Set;

/**
 * @Date 2023/12/19 08:53
 * @Author zsy
 * @Description 模拟行走机器人 类比Problem62、Problem63、Problem64、Problem980、Offer13
 * 机器人在一个无限大小的 XY 网格平面上行走，从点 (0, 0) 处开始出发，面向北方。
 * 该机器人可以接收以下三种类型的命令 commands ：
 * -2 ：向左转 90 度
 * -1 ：向右转 90 度
 * 1 <= x <= 9 ：向前移动 x 个单位长度
 * 在网格上有一些格子被视为障碍物 obstacles 。第 i 个障碍物位于网格点  obstacles[i] = (xi, yi) 。
 * 机器人无法走到障碍物上，它将会停留在障碍物的前一个网格方块上，并继续执行下一个命令。
 * 返回机器人距离原点的 最大欧式距离 的 平方 。（即，如果距离为 5 ，则返回 25 ）
 * 注意：
 * 北方表示 +Y 方向。
 * 东方表示 +X 方向。
 * 南方表示 -Y 方向。
 * 西方表示 -X 方向。
 * 原点 [0,0] 可能会有障碍物。
 * <p>
 * 输入：commands = [4,-1,3], obstacles = []
 * 输出：25
 * 解释：
 * 机器人开始位于 (0, 0)：
 * 1. 向北移动 4 个单位，到达 (0, 4)
 * 2. 右转
 * 3. 向东移动 3 个单位，到达 (3, 4)
 * 距离原点最远的是 (3, 4) ，距离为 3^2 + 4^2 = 25
 * <p>
 * 输入：commands = [4,-1,4,-2,4], obstacles = [[2,4]]
 * 输出：65
 * 解释：机器人开始位于 (0, 0)：
 * 1. 向北移动 4 个单位，到达 (0, 4)
 * 2. 右转
 * 3. 向东移动 1 个单位，然后被位于 (2, 4) 的障碍物阻挡，机器人停在 (1, 4)
 * 4. 左转
 * 5. 向北走 4 个单位，到达 (1, 8)
 * 距离原点最远的是 (1, 8) ，距离为 1^2 + 8^2 = 65
 * <p>
 * 输入：commands = [6,-1,-1,6], obstacles = []
 * 输出：36
 * 解释：机器人开始位于 (0, 0):
 * 1. 向北移动 6 个单位，到达 (0, 6).
 * 2. 右转
 * 3. 右转
 * 4. 向南移动 6 个单位，到达 (0, 0).
 * 机器人距离原点最远的点是 (0, 6)，其距离的平方是 6^2 = 36 个单位。
 * <p>
 * 1 <= commands.length <= 10^4
 * commands[i] 的值可以取 -2、-1 或者是范围 [1, 9] 内的一个整数。
 * 0 <= obstacles.length <= 10^4
 * -3 * 10^4 <= xi, yi <= 3 * 10^4
 * 答案保证小于 2^31
 */
public class Problem874 {
    public static void main(String[] args) {
        Problem874 problem874 = new Problem874();
//        int[] commands = {4, -1, 4, -2, 4};
//        int[][] obstacle = {{2, 4}};
        int[] commands = {-2, -1, -2, 3, 7};
        int[][] obstacle = {{1, -3}, {2, -3}, {4, 0}, {-2, 5}, {-5, 2}, {0, 0}, {4, -4}, {-2, -5}, {-1, -2}, {0, 2}};
        System.out.println(problem874.robotSim(commands, obstacle));
    }

    /**
     * 哈希表
     * 模拟机器人每一步移动的过程，使用哈希集合记录障碍物的位置
     * 时间复杂度O(Cn+m)，空间复杂度O(m) (n=commands.length，m=obstacles.length，C=commands中最大值)
     *
     * @param commands
     * @param obstacles
     * @return
     */
    public int robotSim(int[] commands, int[][] obstacles) {
        //存储障碍物的哈希集合
        //节点(x,y)为障碍物，则存储的key为"x-y"
        Set<String> set = new HashSet<>();

        for (int i = 0; i < obstacles.length; i++) {
            set.add(obstacles[i][0] + "-" + obstacles[i][1]);
        }

        //direction[0]：向北移动，direction[1]：向东移动，direction[2]：向南移动，direction[3]：向西移动
        //注意：只能这样安排移动方向，因为左转右转和direction对应，向左转90度，d=(d+3)mod4；向右转90度，d=(d+1)mod4
        int[][] direction = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        //初始化移动方向为向北移动，(x,y)下一个移动位置为(x+direction[d][0],y+direction[d][1])
        int d = 0;
        //当前位置(x,y)
        int x = 0;
        int y = 0;
        int result = 0;

        for (int i = 0; i < commands.length; i++) {
            //改变移动方向，向右转90度
            if (commands[i] == -1) {
                d = (d + 1) % 4;
            } else if (commands[i] == -2) {
                //改变移动方向，向左转90度
                d = (d + 3) % 4;
            } else {
                //向下一个位置移动
                for (int j = 0; j < commands[i]; j++) {
                    //下一个位置为障碍物，则不能移动，直接跳出循环
                    if (set.contains((x + direction[d][0]) + "-" + (y + direction[d][1]))) {
                        break;
                    }

                    x = x + direction[d][0];
                    y = y + direction[d][1];
                    //更新距离起始节点的最大欧式距离的平方
                    result = Math.max(result, x * x + y * y);
                }
            }
        }

        return result;
    }
}
