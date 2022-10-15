package com.zhang.java;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Date 2022/10/15 16:09
 * @Author zsy
 * @Description 三个线程按顺序打印ABC，打印100个字符
 */
public class PrintABC {
    public static void main(String[] args) {
//        Resource resource = new Resource();
        Resource2 resource = new Resource2();

        new Thread(new Runnable() {
            @Override
            public void run() {
                resource.printA();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                resource.printB();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                resource.printC();
            }
        }).start();
    }

    /**
     * 使用lock+condition实现
     */
    static class Resource {
        private final Lock lock;
        private final Condition conditionA;
        private final Condition conditionB;
        private final Condition conditionC;
        private int flag;

        Resource() {
            lock = new ReentrantLock();
            conditionA = lock.newCondition();
            conditionB = lock.newCondition();
            conditionC = lock.newCondition();
            flag = 1;
        }

        public void printA() {
            for (int i = 1; i <= 100; i = i + 3) {
                lock.lock();
                try {
                    while (flag != 1) {
                        try {
                            conditionA.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("A");
                    flag = 2;
                    conditionB.signal();
                } finally {
                    lock.unlock();
                }
            }
        }

        public void printB() {
            for (int i = 2; i <= 100; i = i + 3) {
                lock.lock();
                try {
                    while (flag != 2) {
                        try {
                            conditionB.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("B");
                    flag = 3;
                    conditionC.signal();
                } finally {
                    lock.unlock();
                }
            }
        }

        public void printC() {
            for (int i = 3; i <= 100; i = i + 3) {
                lock.lock();
                try {
                    while (flag != 3) {
                        try {
                            conditionC.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("C");
                    flag = 1;
                    conditionA.signal();
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    /**
     * 使用synchronized实现，count可以保证原子性和可见性
     */
    static class Resource2 {
        private final Object object;
        private int flag;
        private int count;

        Resource2() {
            object = new Object();
            flag = 1;
            count = 1;
        }

        public void printA() {
            synchronized (object) {
                while (count <= 100) {
                    if (flag == 1) {
                        System.out.println("A");
                        flag = 2;
                        count++;
                        object.notifyAll();
                    } else {
                        try {
                            object.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        public void printB() {
            synchronized (object) {
                while (count <= 100) {
                    if (flag == 2) {
                        System.out.println("B");
                        flag = 3;
                        count++;
                        object.notifyAll();
                    } else {
                        try {
                            object.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        public void printC() {
            synchronized (object) {
                while (count <= 100) {
                    if (flag == 3) {
                        System.out.println("C");
                        flag = 1;
                        count++;
                        object.notifyAll();
                    } else {
                        try {
                            object.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
