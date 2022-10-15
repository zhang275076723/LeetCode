package com.zhang.java;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.IntConsumer;

/**
 * @Date 2022/10/15 08:54
 * @Author zsy
 * @Description 交替打印字符串
 * 编写一个可以从 1 到 n 输出代表这个数字的字符串的程序，但是：
 * 如果这个数字可以被 3 整除，输出 "fizz"。
 * 如果这个数字可以被 5 整除，输出 "buzz"。
 * 如果这个数字可以同时被 3 和 5 整除，输出 "fizzbuzz"。
 * 例如，当 n = 15，输出： 1, 2, fizz, 4, buzz, fizz, 7, 8, fizz, buzz, 11, fizz, 13, 14, fizzbuzz。
 * <p>
 * 假设有这么一个类：
 * * class FizzBuzz {
 * *   public FizzBuzz(int n) { ... }               // constructor
 * *   public void fizz(printFizz) { ... }          // only output "fizz"
 * *   public void buzz(printBuzz) { ... }          // only output "buzz"
 * *   public void fizzbuzz(printFizzBuzz) { ... }  // only output "fizzbuzz"
 * *   public void number(printNumber) { ... }      // only output the numbers
 * * }
 * <p>
 * 请你实现一个有四个线程的多线程版 FizzBuzz，同一个 FizzBuzz 实例会被如下四个线程使用：
 * <p>
 * 线程A将调用 fizz() 来判断是否能被 3 整除，如果可以，则输出 fizz。
 * 线程B将调用 buzz() 来判断是否能被 5 整除，如果可以，则输出 buzz。
 * 线程C将调用 fizzbuzz() 来判断是否同时能被 3 和 5 整除，如果可以，则输出 fizzbuzz。
 * 线程D将调用 number() 来实现输出既不能被 3 整除也不能被 5 整除的数字。
 */
public class Problem1195 {
    public static void main(String[] args) {
        FizzBuzz fizzBuzz = new FizzBuzz(15);
//        FizzBuzz2 fizzBuzz2 = new FizzBuzz2(15);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    fizzBuzz.fizz(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("fizz");
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    fizzBuzz.buzz(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("buzz");
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    fizzBuzz.fizzbuzz(new Runnable() {
                        @Override
                        public void run() {
                            System.out.print("fizzbuzz");
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    fizzBuzz.number();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * lock+condition实现
     */
    static class FizzBuzz {
        private int n;
        private int flag;
        private final Lock lock;
        private final Condition condition;

        public FizzBuzz(int n) {
            this.n = n;
            //0:输出数字，1:输出fizz，2:输出buzz，3:输出fizzbuzz
            flag = 0;
            lock = new ReentrantLock();
            condition = lock.newCondition();
        }

        public void fizz(Runnable printFizz) throws InterruptedException {
            for (int i = 3; i <= n; i = i + 3) {
                if (i % 5 == 0) {
                    continue;
                }

                lock.lock();
                try {
                    while (flag != 1) {
                        condition.await();
                    }
                    printFizz.run();
                    flag = 0;
                    condition.signalAll();
                } finally {
                    lock.unlock();
                }
            }
        }

        public void buzz(Runnable printBuzz) throws InterruptedException {
            for (int i = 5; i <= n; i = i + 5) {
                if (i % 3 == 0) {
                    continue;
                }

                lock.lock();
                try {
                    while (flag != 2) {
                        condition.await();
                    }
                    printBuzz.run();
                    flag = 0;
                    condition.signalAll();
                } finally {
                    lock.unlock();
                }
            }
        }

        public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
            for (int i = 15; i <= n; i = i + 15) {
                lock.lock();
                try {
                    while (flag != 3) {
                        condition.await();
                    }
                    printFizzBuzz.run();
                    flag = 0;
                    condition.signalAll();
                } finally {
                    lock.unlock();
                }
            }
        }

        public void number() throws InterruptedException {
            for (int i = 1; i <= n; i++) {
                lock.lock();
                try {
                    while (flag != 0) {
                        condition.await();
                    }

                    if (i % 3 == 0 || i % 5 == 0) {
                        if (i % 3 == 0 && i % 5 == 0) {
                            flag = 3;
                        } else if (i % 3 == 0) {
                            flag = 1;
                        } else {
                            flag = 2;
                        }
                    } else {
                        System.out.println(i);
                    }

                    condition.signalAll();
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    /**
     * synchronized实现
     */
    static class FizzBuzz2 {
        private int n;
        private int i;
        private final Object object;

        public FizzBuzz2(int n) {
            this.n = n;
            i = 1;
            object = new Object();
        }

        public void fizz(Runnable printFizz) throws InterruptedException {
            while (i <= n) {
                synchronized (object) {
                    if (i % 3 == 0 && i % 5 != 0) {
                        printFizz.run();
                        i++;
                        object.notifyAll();
                    } else {
                        object.wait();
                    }
                }
            }
        }

        public void buzz(Runnable printBuzz) throws InterruptedException {
            while (i <= n) {
                synchronized (object) {
                    if (i % 5 == 0 && i % 3 != 0) {
                        printBuzz.run();
                        i++;
                        object.notifyAll();
                    } else {
                        object.wait();
                    }
                }
            }
        }

        public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
            while (i <= n) {
                synchronized (object) {
                    if (i % 3 == 0 && i % 5 == 0) {
                        printFizzBuzz.run();
                        i++;
                        object.notifyAll();
                    } else {
                        object.wait();
                    }
                }
            }
        }

        public void number() throws InterruptedException {
            while (i <= n) {
                synchronized (object) {
                    if (i % 3 != 0 && i % 5 != 0) {
                        System.out.println(i);
                        i++;
                        object.notifyAll();
                    } else {
                        object.wait();
                    }
                }
            }
        }
    }
}
