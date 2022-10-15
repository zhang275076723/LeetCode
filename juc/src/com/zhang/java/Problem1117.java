package com.zhang.java;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Date 2022/10/14 19:29
 * @Author zsy
 * @Description H2O 生成
 * 现在有两种线程，氧 oxygen 和氢 hydrogen，你的目标是组织这两种线程来产生水分子。
 * 存在一个屏障（barrier）使得每个线程必须等候直到一个完整水分子能够被产生出来。
 * 氢和氧线程会被分别给予 releaseHydrogen 和 releaseOxygen 方法来允许它们突破屏障。
 * 这些线程应该三三成组突破屏障并能立即组合产生一个水分子。
 * 你必须保证产生一个水分子所需线程的结合必须发生在下一个水分子产生之前。
 * 换句话说:
 * 如果一个氧线程到达屏障时没有氢线程到达，它必须等候直到两个氢线程到达。
 * 如果一个氢线程到达屏障时没有其它线程到达，它必须等候直到一个氧线程和另一个氢线程到达。
 * 书写满足这些限制条件的氢、氧线程同步代码。
 * <p>
 * 输入: water = "HOH"
 * 输出: "HHO"
 * 解释: "HOH" 和 "OHH" 依然都是有效解。
 * <p>
 * 输入: water = "OOHHHH"
 * 输出: "HHOHHO"
 * 解释: "HOHHHO", "OHHHHO", "HHOHOH", "HOHHOH", "OHHHOH", "HHOOHH", "HOHOHH" 和 "OHHOHH" 依然都是有效解。
 * <p>
 * 3 * n == water.length
 * 1 <= n <= 20
 * water[i] == 'O' or 'H'
 * 输入字符串 water 中的 'H' 总数将会是 2 * n 。
 * 输入字符串 water 中的 'O' 总数将会是 n 。
 */
public class Problem1117 {
    public static void main(String[] args) {
        H2O h2o = new H2O();
        String water = "OOHHHH";
        for (int i = 0; i < water.length(); i++) {
            if (water.charAt(i) == 'H') {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            h2o.hydrogen(new Runnable() {
                                @Override
                                public void run() {
                                    System.out.print("H");
                                }
                            });
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            } else {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            h2o.oxygen(new Runnable() {
                                @Override
                                public void run() {
                                    System.out.print("O");
                                }
                            });
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        }
    }

    /**
     * 使用lock+condition实现
     */
    static class H2O {
        private final Lock lock;
        private final Condition condition1;
        private final Condition condition2;
        private int flag;

        public H2O() {
            lock = new ReentrantLock();
            condition1 = lock.newCondition();
            condition2 = lock.newCondition();
            flag = 0;
        }

        public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
            lock.lock();
            try {
                while (flag == 2) {
                    condition1.await();
                }
                releaseHydrogen.run();
                flag++;
                condition1.signal();
                condition2.signal();
            } finally {
                lock.unlock();
            }
        }

        public void oxygen(Runnable releaseOxygen) throws InterruptedException {
            lock.lock();
            try {
                while (flag != 2) {
                    condition2.await();
                }
                releaseOxygen.run();
                flag = 0;
                condition1.signal();
            } finally {
                lock.unlock();
            }
        }
    }

    /**
     * 使用synchronized实现
     */
    static class H2O2 {
        private final Object object;
        private int flag;

        public H2O2() {
            object = new Object();
            flag = 0;
        }

        public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
            synchronized (object) {
                while (flag == 2) {
                    object.wait();
                }
                releaseHydrogen.run();
                flag++;
                object.notifyAll();
            }
        }

        public void oxygen(Runnable releaseOxygen) throws InterruptedException {
            synchronized (object) {
                while (flag != 2) {
                    object.wait();
                }
                releaseOxygen.run();
                flag = 0;
                object.notifyAll();
            }
        }
    }
}
