package com.zhang.java;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Date 2022/10/12 08:22
 * @Author zsy
 * @Description 按序打印
 * 给你一个类：
 * * public class Foo {
 * *   public void first() { print("first"); }
 * *   public void second() { print("second"); }
 * *   public void third() { print("third"); }
 * * }
 * <p>
 * 三个不同的线程 A、B、C 将会共用一个 Foo 实例。
 * 线程 A 将会调用 first() 方法
 * 线程 B 将会调用 second() 方法
 * 线程 C 将会调用 third() 方法
 * 请设计修改程序，以确保 second() 方法在 first() 方法之后被执行，third() 方法在 second() 方法之后被执行。
 * <p>
 * 提示：
 * 尽管输入中的数字似乎暗示了顺序，但是我们并不保证线程在操作系统中的调度顺序。
 * 你看到的输入格式主要是为了确保测试的全面性。
 * <p>
 * 输入：nums = [1,2,3]
 * 输出："firstsecondthird"
 * 解释：
 * 有三个线程会被异步启动。输入 [1,2,3] 表示线程 A 将会调用 first() 方法，线程 B 将会调用 second() 方法，
 * 线程 C 将会调用 third() 方法。正确的输出是 "firstsecondthird"。
 * <p>
 * 输入：nums = [1,3,2]
 * 输出："firstsecondthird"
 * 解释：
 * 输入 [1,3,2] 表示线程 A 将会调用 first() 方法，线程 B 将会调用 third() 方法，线程 C 将会调用 second() 方法。
 * 正确的输出是 "firstsecondthird"。
 * <p>
 * nums 是 [1, 2, 3] 的一组排列
 */
public class Problem1114 {
    public static void main(String[] args) {
        Foo foo = new Foo();

        new Thread(() -> {
            try {
                foo.second(() -> {
                    System.out.println("second");
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "thread2").start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            try {
                foo.third(() -> {
                    System.out.println("third");
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "thread3").start();

        new Thread(() -> {
            try {
                foo.first(() -> {
                    System.out.println("first");
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "thread1").start();
    }

    /**
     * 使用lock+condition实现
     */
    static class Foo {
        private int flag;
        private final Lock lock;
        private final Condition condition1;
        private final Condition condition2;
        private final Condition condition3;

        public Foo() {
            flag = 1;
            lock = new ReentrantLock();
            condition1 = lock.newCondition();
            condition2 = lock.newCondition();
            condition3 = lock.newCondition();
        }

        public void first(Runnable printFirst) throws InterruptedException {
            lock.lock();
            try {
                while (flag != 1) {
                    condition1.await();
                }
                printFirst.run();
                flag = 2;
                condition2.signal();
            } finally {
                lock.unlock();
            }
        }

        public void second(Runnable printSecond) throws InterruptedException {
            lock.lock();
            try {
                while (flag != 2) {
                    condition2.await();
                }
                printSecond.run();
                flag = 3;
                condition3.signal();
            } finally {
                lock.unlock();
            }
        }

        public void third(Runnable printThird) throws InterruptedException {
            lock.lock();
            try {
                while (flag != 3) {
                    condition3.await();
                }
                printThird.run();
                flag = 1;
                condition1.signal();
            } finally {
                lock.unlock();
            }
        }
    }

    /**
     * 使用synchronized实现
     */
    static class Foo2 {
        private int flag;
        private final Object object;

        public Foo2() {
            flag = 1;
            object = new Object();
        }

        public void first(Runnable printFirst) throws InterruptedException {
            synchronized (object) {
                while (flag != 1) {
                    object.wait();
                }
                printFirst.run();
                flag = 2;
                object.notifyAll();
            }
        }

        public void second(Runnable printSecond) throws InterruptedException {
            synchronized (object) {
                while (flag != 2) {
                    object.wait();
                }
                printSecond.run();
                flag = 3;
                object.notifyAll();
            }
        }

        public void third(Runnable printThird) throws InterruptedException {
            synchronized (object) {
                while (flag != 3) {
                    object.wait();
                }
                printThird.run();
                flag = 1;
                object.notifyAll();
            }
        }
    }
}
