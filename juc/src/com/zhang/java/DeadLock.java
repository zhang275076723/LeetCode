package com.zhang.java;

import java.sql.Time;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Date 2022/10/14 19:24
 * @Author zsy
 * @Description
 */
public class DeadLock {
    public static void main(String[] args) {
        DeadLock deadLock = new DeadLock();
        deadLock.deadLock();
//        deadLock.deadLock2();
    }

    /**
     * 使用lock实现死锁
     */
    private void deadLock() {
        Lock lock1 = new ReentrantLock();
        Lock lock2 = new ReentrantLock();

        new Thread(new Runnable() {
            @Override
            public void run() {
                lock1.lock();
                try {
                    System.out.println(Thread.currentThread().getName() + "获取到了lock1，尝试获取lock2");

                    try {
                        //确保当前线程先加锁lock1
                        TimeUnit.MILLISECONDS.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    lock2.lock();
                    try {
                        System.out.println(Thread.currentThread().getName() + "获取到了lock2");
                    } finally {
                        lock2.unlock();
                    }
                } finally {
                    lock1.unlock();
                }
            }
        }, "线程1").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                lock2.lock();
                try {
                    System.out.println(Thread.currentThread().getName() + "获取到了lock2，尝试获取lock1");

                    try {
                        //确保当前线程先加锁lock1
                        TimeUnit.MILLISECONDS.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    lock1.lock();
                    try {
                        System.out.println(Thread.currentThread().getName() + "获取到了lock1");
                    } finally {
                        lock1.unlock();
                    }
                } finally {
                    lock2.unlock();
                }
            }
        }, "线程2").start();
    }

    /**
     * 使用synchronized实现死锁
     */
    private void deadLock2() {
        Object o1 = new Object();
        Object o2 = new Object();

        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (o1) {
                    System.out.println(Thread.currentThread().getName() + "获取到了o1，尝试获取o2");
                    try {
                        //确保当前线程先加锁o1
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (o2) {
                        System.out.println(Thread.currentThread().getName() + "获取到了o2");
                    }
                }
            }
        }, "线程1").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (o2) {
                    System.out.println(Thread.currentThread().getName() + "获取到了o2，尝试获取o1");
                    try {
                        //确保当前线程先加锁o2
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (o1) {
                        System.out.println(Thread.currentThread().getName() + "获取到了o1");
                    }
                }
            }
        }, "线程2").start();
    }
}
