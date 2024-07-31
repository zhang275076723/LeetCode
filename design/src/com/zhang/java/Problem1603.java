package com.zhang.java;

/**
 * @Date 2024/10/9 08:07
 * @Author zsy
 * @Description 设计停车系统 状态压缩类比
 * 请你给一个停车场设计一个停车系统。停车场总共有三种不同大小的车位：大，中和小，每种尺寸分别有固定数目的车位。
 * 请你实现 ParkingSystem 类：
 * ParkingSystem(int big, int medium, int small) 初始化 ParkingSystem 类，三个参数分别对应每种停车位的数目。
 * bool addCar(int carType) 检查是否有 carType 对应的停车位。 carType 有三种类型：大，中，小，分别用数字 1， 2 和 3 表示。
 * 一辆车只能停在  carType 对应尺寸的停车位中。如果没有空车位，请返回 false ，否则将该车停入车位并返回 true 。
 * <p>
 * 输入：
 * ["ParkingSystem", "addCar", "addCar", "addCar", "addCar"]
 * [[1, 1, 0], [1], [2], [3], [1]]
 * 输出：
 * [null, true, true, false, false]
 * 解释：
 * ParkingSystem parkingSystem = new ParkingSystem(1, 1, 0);
 * parkingSystem.addCar(1); // 返回 true ，因为有 1 个空的大车位
 * parkingSystem.addCar(2); // 返回 true ，因为有 1 个空的中车位
 * parkingSystem.addCar(3); // 返回 false ，因为没有空的小车位
 * parkingSystem.addCar(1); // 返回 false ，因为没有空的大车位，唯一一个大车位已经被占据了
 * <p>
 * 0 <= big, medium, small <= 1000
 * carType 取值为 1， 2 或 3
 * 最多会调用 addCar 函数 1000 次
 */
public class Problem1603 {
    public static void main(String[] args) {
//        ParkingSystem parkingSystem = new ParkingSystem(1, 1, 0);
        ParkingSystem2 parkingSystem = new ParkingSystem2(1, 1, 0);
        // 返回 true ，因为有 1 个空的大车位
        System.out.println(parkingSystem.addCar(1));
        // 返回 true ，因为有 1 个空的中车位
        System.out.println(parkingSystem.addCar(2));
        // 返回 false ，因为没有空的小车位
        System.out.println(parkingSystem.addCar(3));
        // 返回 false ，因为没有空的大车位，唯一一个大车位已经被占据了
        System.out.println(parkingSystem.addCar(1));
    }

    /**
     * 模拟
     */
    static class ParkingSystem {
        //剩余大车位的数量
        private int big;
        //剩余中车位的数量
        private int medium;
        //剩余小车位的数量
        private int small;

        public ParkingSystem(int big, int medium, int small) {
            this.big = big;
            this.medium = medium;
            this.small = small;
        }

        public boolean addCar(int carType) {
            //当前车位为大车位
            if (carType == 1) {
                if (big <= 0) {
                    return false;
                }

                big--;
                return true;
            } else if (carType == 2) {
                //当前车位为中车位

                if (medium <= 0) {
                    return false;
                }

                medium--;
                return true;
            } else if (carType == 3) {
                //当前车位为小车位

                if (small <= 0) {
                    return false;
                }

                small--;
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * 二进制状态压缩
     * 每种车位的个数不超过1000，则每种车位使用10bit，3种车位只需要30bit，int就可以表示每种车位的个数
     */
    static class ParkingSystem2 {
        //每种车位的个数的二进制状态
        private int state;

        public ParkingSystem2(int big, int medium, int small) {
            state = big;
            state = (state << 10) + medium;
            state = (state << 10) + small;
        }

        public boolean addCar(int carType) {
            //当前车位为大车位
            if (carType == 1) {
                //剩余大车位的数量
                //19-29位存储大车位数量
                int count = state & 0b1111_1111_11_0000_0000_00_0000_0000_00;

                if (count <= 0) {
                    return false;
                }

                state = state - 0b1_0000_0000_00_0000_0000_00;
                return true;
            } else if (carType == 2) {
                //当前车位为中车位

                //剩余中车位的数量
                //9-19位存储中车位数量
                int count = state & 0b1111_1111_11_0000_0000_00;

                if (count <= 0) {
                    return false;
                }

                state = state - 0b1_0000_0000_00;
                return true;
            } else if (carType == 3) {
                //当前车位为小车位

                //剩余小车位的数量
                //0-9位存储小车位数量
                int count = state & 0b1111_1111_11;

                if (count <= 0) {
                    return false;
                }

                state--;
                return true;
            } else {
                return false;
            }
        }
    }
}
