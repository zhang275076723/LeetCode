package com.zhang.java;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.IntConsumer;

/**
 * @Date 2022/10/14 08:17
 * @Author zsy
 * @Description 打印零与奇偶数
 * 现有函数 printNumber 可以用一个整数参数调用，并输出该整数到控制台。
 * 例如，调用 printNumber(7) 将会输出 7 到控制台。
 * 给你类 ZeroEvenOdd 的一个实例，该类中有三个函数：zero、even 和 odd 。
 * ZeroEvenOdd 的相同实例将会传递给三个不同线程：
 * 线程 A：调用 zero() ，只输出 0
 * 线程 B：调用 even() ，只输出偶数
 * 线程 C：调用 odd() ，只输出奇数
 * 修改给出的类，以输出序列 "010203040506..." ，其中序列的长度必须为 2n 。
 * <p>
 * 实现 ZeroEvenOdd 类：
 * ZeroEvenOdd(int n) 用数字 n 初始化对象，表示需要输出的数。
 * void zero(printNumber) 调用 printNumber 以输出一个 0 。
 * void even(printNumber) 调用printNumber 以输出偶数。
 * void odd(printNumber) 调用 printNumber 以输出奇数。
 * <p>
 * 输入：n = 2
 * 输出："0102"
 * 解释：三条线程异步执行，其中一个调用 zero()，另一个线程调用 even()，最后一个线程调用odd()。正确的输出为 "0102"。
 * <p>
 * 输入：n = 5
 * 输出："0102030405"
 * <p>
 * 1 <= n <= 1000
 */
public class Problem1116 {
    public static void main(String[] args) {
        ZeroEvenOdd zeroEvenOdd = new ZeroEvenOdd(2);
//        ZeroEvenOdd2 zeroEvenOdd2 = new ZeroEvenOdd2(2);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    zeroEvenOdd.zero();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "zero").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    zeroEvenOdd.odd();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "odd").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    zeroEvenOdd.even();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "even").start();
    }

    /**
     * 使用lock+condition实现
     */
    static class ZeroEvenOdd {
        private int n;
        private int flag;
        private final Lock lock;
        private final Condition condition0;
        private final Condition condition1;
        private final Condition condition2;

        public ZeroEvenOdd(int n) {
            this.n = n;
            flag = 0;
            lock = new ReentrantLock();
            condition0 = lock.newCondition();
            condition1 = lock.newCondition();
            condition2 = lock.newCondition();
        }

        public void zero() throws InterruptedException {
            for (int i = 1; i <= n; i++) {
                lock.lock();
                try {
                    while (flag != 0) {
                        condition0.await();
                    }

                    System.out.print(0);
                    if (i % 2 == 1) {
                        flag = 1;
                    } else {
                        flag = 2;
                    }
                    condition1.signal();
                    condition2.signal();
                } finally {
                    lock.unlock();
                }
            }
        }

        public void odd() throws InterruptedException {
            for (int i = 1; i <= n; i = i + 2) {
                lock.lock();
                try {
                    while (flag != 1) {
                        condition1.await();
                    }

                    System.out.print(i);
                    flag = 0;
                    condition0.signal();
                } finally {
                    lock.unlock();
                }
            }
        }

        public void even() throws InterruptedException {
            for (int i = 2; i <= n; i = i + 2) {
                lock.lock();
                try {
                    while (flag != 2) {
                        condition2.await();
                    }

                    System.out.print(i);
                    flag = 0;
                    condition0.signal();
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    /**
     * 使用synchronized实现
     */
    static class ZeroEvenOdd2 {
        private int n;
        private int flag;
        private volatile int i;
        private final Object object;

        public ZeroEvenOdd2(int n) {
            this.n = n;
            flag = 0;
            i = 1;
            object = new Object();
        }

        public void zero() throws InterruptedException {
            for (int i = 1; i <= n; i++) {
                synchronized (object) {
                    while (flag != 0) {
                        object.wait();
                    }
                    System.out.print(0);
                    if (i % 2 == 1) {
                        flag = 1;
                    } else {
                        flag = 2;
                    }
                    object.notifyAll();
                }
            }
        }

        public void odd() throws InterruptedException {
            while (i <= n) {
                synchronized (object) {
                    if (flag == 1) {
                        System.out.print(i);
                        i++;
                        flag = 0;
                        object.notifyAll();
                    } else {
                        object.wait();
                    }
                }
            }
        }

        public void even() throws InterruptedException {
            while (i <= n) {
                synchronized (object) {
                    if (flag == 2) {
                        System.out.print(i);
                        i++;
                        flag = 0;
                        object.notifyAll();
                    } else {
                        object.wait();
                    }
                }
            }
        }
    }
}
