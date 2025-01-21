package com.zhang.java;

/**
 * @Date 2022/5/7 11:29
 * @Author zsy
 * @Description 电梯调度 字节面试题 类比Problem834、Problem1838
 * 有一个特殊的电梯，只能从1楼载人，然后运输至某一楼（记为第N层）。
 * 现有若干乘客，每个乘客有一个自己想去的楼层，以数组表示：下标为乘客的id，数组中的内容为乘客想去的楼层。
 * 乘客在第N层下电梯后，需要爬楼梯到自己所去的楼层。
 * 现已知数组nums，求楼层N，使得乘客在N楼下电梯后，所有人所爬楼梯的总和最小。
 * 若某两个N都可以使得所爬楼层和最小，输出最小的那个楼层数N。
 * <p>
 * 输入样例：[1,2,3,3,2,5]
 * 输出样例：2
 */
public class ElevatorSchedule {
    public static void main(String[] args) {
        ElevatorSchedule elevatorSchedule = new ElevatorSchedule();
        int[] person = {1, 2, 3, 3, 2, 5};
        System.out.println(elevatorSchedule.schedule(person));
        System.out.println(elevatorSchedule.schedule2(person));
    }

    /**
     * 暴力
     * 遍历电梯所停在的楼层，计算所有人达到想到楼层所要爬的楼梯总数
     * 时间复杂度O(n^2)，空间复杂度O(1)
     *
     * @param person
     * @return
     */
    public int schedule(int[] person) {
        if (person == null || person.length == 0) {
            return -1;
        }

        //乘客要去的最小楼层
        int minFloor = Integer.MAX_VALUE;
        //乘客要去的最大楼层
        int maxFloor = Integer.MIN_VALUE;

        for (int floor : person) {
            minFloor = Math.min(minFloor, floor);
            maxFloor = Math.max(maxFloor, floor);
        }

        //电梯停的最佳楼层
        int bestFloor = minFloor;
        //最佳楼层所需爬的楼层数量
        int minCount = Integer.MAX_VALUE;

        for (int i = minFloor; i <= maxFloor; i++) {
            //电梯停在第i层，所需爬的楼层数量
            int tempCount = 0;

            for (int j = 0; j < person.length; j++) {
                tempCount = tempCount + Math.abs(i - person[j]);
            }

            //电梯停在第i层所需爬的楼层数量小于当前最佳楼层所需爬的楼层数量，则更新最佳楼层和最佳楼层所需爬的楼层数量
            if (tempCount < minCount) {
                minCount = tempCount;
                bestFloor = i;
            }
        }

        return bestFloor;
    }

    /**
     * 动态规划
     * 假设电梯停在i楼，有n1个乘客要去i楼以下，n2个乘客在i楼，n3个乘客要去i楼以上，此时总共需要爬Y层
     * 当电梯停在i+1楼时，要到i楼和i楼以下的乘客需要多爬1层，总共需要多爬n1+n2层；
     * 要到i+1楼和i+1楼以上的乘客可以少爬1层，总共可以少爬n3层。
     * 此时总共需要爬Y+n1+n2-n3层，取两者最小值，就知道电梯停在i楼和i+1楼的最佳位置
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param person
     * @return
     */
    public int schedule2(int[] person) {
        if (person == null || person.length == 0) {
            return -1;
        }

        //乘客要去的最小楼层
        int minFloor = Integer.MAX_VALUE;
        //乘客要去的最大楼层
        int maxFloor = Integer.MIN_VALUE;

        for (int floor : person) {
            minFloor = Math.min(minFloor, floor);
            maxFloor = Math.max(maxFloor, floor);
        }

        //floor[i]：要去第i+minFloor层的人数
        int[] floor = new int[maxFloor - minFloor + 1];

        for (int i = 0; i < person.length; i++) {
            floor[person[i] - minFloor]++;
        }

        //电梯停的最佳楼层
        int bestFloor = minFloor;
        //最佳楼层所需爬的楼层数量
        int bestCount;
        //电梯停在某一层后，乘客达到要去楼层所需爬的楼层之和
        int count = 0;
        //要去i楼以下的乘客数量
        int n1 = 0;
        //要去i楼的乘客数量
        int n2 = floor[0];
        //要去i楼以上的乘客数量
        int n3 = 0;

        //电梯停在minFloor楼，乘客需要爬的楼层总数之和，并更新要去i楼以上的乘客数量n3
        for (int i = 1; i < floor.length; i++) {
            count = count + floor[i] * i;
            n3 = n3 + floor[i];
        }

        bestCount = count;

        for (int i = 1; i < floor.length; i++) {
            //电梯停在i+1楼和停在i楼相比，i+1层之下的乘客需要多爬n1+n2层，i+1层及以上的乘客需要少爬n3层
            count = count + n1 + n2 - n3;

            //电梯停在i+1楼比当前最佳楼层所需爬的楼层数量要少，则更新最佳楼层和最佳楼层所需爬的楼层数量
            if (count < bestCount) {
                bestFloor = minFloor + i;
                bestCount = count;
            }

            //每次电梯往上停一层，更新n1、n2、n3
            n1 = n1 + n2;
            n2 = floor[i];
            n3 = n3 - n2;
        }

        return bestFloor;
    }
}
