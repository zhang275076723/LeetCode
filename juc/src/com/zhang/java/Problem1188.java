package com.zhang.java;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Date 2022/6/1 10:36
 * @Author zsy
 * @Description 设计有限阻塞队列 蔚来面试题
 * 实现一个拥有如下方法的线程安全有限阻塞队列：
 * BoundedBlockingQueue(int capacity) 构造方法初始化队列，其中capacity代表队列长度上限。
 * void enqueue(int element) 在队首增加一个element. 如果队列满，调用线程被阻塞直到队列非满。
 * int dequeue() 返回队尾元素并从队列中将其删除. 如果队列为空，调用线程被阻塞直到队列非空。
 * int size() 返回当前队列元素个数。
 * <p>
 * 你的实现将会被多线程同时访问进行测试。
 * 每一个线程要么是一个只调用enqueue方法的生产者线程，要么是一个只调用dequeue方法的消费者线程。
 * size方法将会在每一个测试用例之后进行调用。
 * 请不要使用内置的有限阻塞队列实现，否则面试将不会通过。
 * <p>
 * 输入:
 * 1
 * 1
 * ["BoundedBlockingQueue","enqueue","dequeue","dequeue","enqueue","enqueue","enqueue","enqueue","dequeue"]
 * [[2],[1],[],[],[0],[2],[3],[4],[]]
 * 输出:
 * [1,0,2,2]
 * 解释:
 * 生产者线程数目 = 1
 * 消费者线程数目 = 1
 * BoundedBlockingQueue queue = new BoundedBlockingQueue(2); // 使用capacity = 2初始化队列。
 * queue.enqueue(1); // 生产者线程将1插入队列。
 * queue.dequeue(); // 消费者线程调用dequeue并返回1。
 * queue.dequeue(); // 由于队列为空，消费者线程被阻塞。
 * queue.enqueue(0); // 生产者线程将0插入队列。消费者线程被解除阻塞同时将0弹出队列并返回。
 * queue.enqueue(2); // 生产者线程将2插入队列。
 * queue.enqueue(3); // 生产者线程将3插入队列。
 * queue.enqueue(4); // 生产者线程由于队列长度已达到上限2而被阻塞。
 * queue.dequeue(); // 消费者线程将2从队列弹出并返回。生产者线程解除阻塞同时将4插入队列。
 * queue.size(); // 队列中还有2个元素。size()方法在每组测试用例最后调用。
 * <p>
 * 输入:
 * 3
 * 4
 * ["BoundedBlockingQueue","enqueue","enqueue","enqueue","dequeue","dequeue","dequeue","enqueue"]
 * [[3],[1],[0],[2],[],[],[],[3]]
 * 输出:
 * [1,0,2,1]
 * 解释:
 * 生产者线程数目 = 3
 * 消费者线程数目 = 4
 * BoundedBlockingQueue queue = new BoundedBlockingQueue(3); // 使用capacity = 3初始化队列。
 * queue.enqueue(1); // 生产者线程P1将1插入队列。
 * queue.enqueue(0); // 生产者线程P2将0插入队列。
 * queue.enqueue(2); // 生产者线程P3将2插入队列。
 * queue.dequeue(); // 消费者线程C1调用dequeue。
 * queue.dequeue(); // 消费者线程C2调用dequeue。
 * queue.dequeue(); // 消费者线程C3调用dequeue。
 * queue.enqueue(3); // 其中一个生产者线程将3插入队列。
 * queue.size(); // 队列中还有1个元素。
 * 由于生产者/消费者线程的数目可能大于1，我们并不知道线程如何被操作系统调度，即使输入看上去隐含了顺序。
 * 因此任意一种输出[1,0,2]或[1,2,0]或[0,1,2]或[0,2,1]或[2,0,1]或[2,1,0]都可被接受。
 */
public class Problem1188 {
    public static void main(String[] args) {
        BoundedBlockingQueue blockingQueue = new BoundedBlockingQueue(3);

        new Thread(() -> {
            for (int i = 0; i < 4; i++) {
                blockingQueue.enqueue(1);
            }
        }, "producer1").start();
        new Thread(() -> {
            for (int i = 0; i < 4; i++) {
                blockingQueue.enqueue(2);
            }
        }, "producer2").start();
        new Thread(() -> {
            for (int i = 0; i < 4; i++) {
                blockingQueue.enqueue(3);
            }
        }, "producer3").start();

        new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                blockingQueue.dequeue();
            }
        }, "consumer1").start();
        new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                blockingQueue.dequeue();
            }
        }, "consumer2").start();
        new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                blockingQueue.dequeue();
            }
        }, "consumer3").start();
        new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                blockingQueue.dequeue();
            }
        }, "consumer4").start();
    }

    static class BoundedBlockingQueue {
        /**
         * 队列最大容量
         */
        private int capacity;

        /**
         * 存放元素的队列
         */
        private LinkedList<Integer> queue;

        /**
         * 队列当前大小，不能使用queue.size()，因为LinkedList的queue.size()非线程安全
         */
        private int size;

        private Lock lock;

        private Condition producer;

        private Condition consumer;

        public BoundedBlockingQueue(int capacity) {
            this.capacity = capacity;
            queue = new LinkedList<>();
            size = 0;
            lock = new ReentrantLock();
            producer = lock.newCondition();
            consumer = lock.newCondition();
        }

        public void enqueue(int element) {
            lock.lock();
            try {
                while (size == capacity) {
                    producer.await();
                }
                queue.offerLast(element);
                size++;
                System.out.println(Thread.currentThread().getName() + ":生产" + element);
                //通知消费者消费
                consumer.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        public int dequeue() {
            int element = 0;
            lock.lock();
            try {
                while (size == 0) {
                    consumer.await();
                }
                element = queue.pollFirst();
                size--;
                System.out.println(Thread.currentThread().getName() + ":消费" + element);
                //通知生产者生产
                producer.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
            return element;
        }

        public int size() {
            return size;
        }
    }
}