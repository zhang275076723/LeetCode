package com.zhang.java;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Date 2022/10/13 08:04
 * @Author zsy
 * @Description 交替打印 FooBar
 * 给你一个类：
 * class FooBar {
 * * public void foo() {
 * *   for (int i = 0; i < n; i++) {
 * *     print("foo");
 * *   }
 * * }
 * <p>
 * * public void bar() {
 * *   for (int i = 0; i < n; i++) {
 * *     print("bar");
 * *   }
 * * }
 * }
 * <p>
 * 两个不同的线程将会共用一个 FooBar 实例：
 * 线程 A 将会调用 foo() 方法，而
 * 线程 B 将会调用 bar() 方法
 * 请设计修改程序，以确保 "foobar" 被输出 n 次。
 * <p>
 * 输入：n = 1
 * 输出："foobar"
 * 解释：这里有两个线程被异步启动。其中一个调用 foo() 方法, 另一个调用 bar() 方法，"foobar" 将被输出一次。
 * <p>
 * 输入：n = 2
 * 输出："foobarfoobar"
 * 解释："foobar" 将被输出两次。
 * <p>
 * 1 <= n <= 1000
 */
public class Problem1115 {
    public static void main(String[] args) {
        FooBar fooBar = new FooBar(3);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    fooBar.bar(new Runnable() {
                        @Override
                        public void run() {
                            System.out.print("bar");
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    fooBar.foo(new Runnable() {
                        @Override
                        public void run() {
                            System.out.print("foo");
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 使用lock+condition实现
     */
    static class FooBar {
        private int n;
        private int flag;
        private final Lock lock;
        private final Condition condition1;
        private final Condition condition2;

        public FooBar(int n) {
            this.n = n;
            flag = 1;
            lock = new ReentrantLock();
            condition1 = lock.newCondition();
            condition2 = lock.newCondition();
        }

        public void foo(Runnable printFoo) throws InterruptedException {
            for (int i = 0; i < n; i++) {
                lock.lock();
                try {
                    while (flag != 1) {
                        condition1.await();
                    }
                    printFoo.run();
                    flag = 2;
                    condition2.signal();
                } finally {
                    lock.unlock();
                }
            }
        }

        public void bar(Runnable printBar) throws InterruptedException {
            for (int i = 0; i < n; i++) {
                lock.lock();
                try {
                    while (flag != 2) {
                        condition2.await();
                    }
                    printBar.run();
                    flag = 1;
                    condition1.signal();
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    /**
     * 使用synchronized实现
     */
    static class FooBar2 {
        private int n;
        private int flag;
        private final Object object;

        public FooBar2(int n) {
            this.n = n;
            flag = 1;
            object = new Object();
        }

        public void foo(Runnable printFoo) throws InterruptedException {
            for (int i = 0; i < n; i++) {
                synchronized (object) {
                    while (flag != 1) {
                        object.wait();
                    }
                    printFoo.run();
                    flag = 2;
                    object.notifyAll();
                }
            }
        }

        public void bar(Runnable printBar) throws InterruptedException {
            for (int i = 0; i < n; i++) {
                synchronized (object) {
                    while (flag != 2) {
                        object.wait();
                    }
                    printBar.run();
                    flag = 1;
                    object.notifyAll();
                }
            }
        }
    }
}
