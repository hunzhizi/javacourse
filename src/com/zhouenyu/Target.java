package com.zhouenyu;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 魂之子
 * @since 2023-12-30 12:15
 * program: javacourse
 * description: 防守方的目标
 */
public class Target {

    public static final Object lock = new Object(); // 锁对象

    private volatile int maxBeAttackedTimes;
    private volatile AtomicInteger beAttackedTimes;
    private volatile boolean isDestroyed;
    private Coordinate coordinate;

    public Target(int maxBeAttackedTimes, Coordinate coordinate) {
        this.maxBeAttackedTimes = maxBeAttackedTimes;
        this.coordinate = coordinate;
        this.beAttackedTimes = new AtomicInteger();
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public int getMaxBeAttackedTimes() {
        return maxBeAttackedTimes;
    }

    public void setMaxBeAttackedTimes(int maxBeAttackedTimes) {
        this.maxBeAttackedTimes = maxBeAttackedTimes;
    }

    public AtomicInteger getBeAttackedTimes() {
        return beAttackedTimes;
    }

    public void setBeAttackedTimes(AtomicInteger beAttackedTimes) {
        this.beAttackedTimes = beAttackedTimes;
    }

    public synchronized boolean isDestroyed() {
        return isDestroyed;
    }

    public void setDestroyed(boolean destroyed) {
        isDestroyed = destroyed;
    }

    //被攻击的同时会进行防御，返回值为1 表示飞机被击落，返回值为0 表示不会被击落
    public void beAttacked() {
        synchronized (lock) {
            int totalTimes = this.beAttackedTimes.incrementAndGet();
            System.out.println(this.getCoordinate() + "'s totalTimes = " + totalTimes);
//        System.out.println("this.maxBeAttackedTimes is " + this.maxBeAttackedTimes);
            if (totalTimes == this.maxBeAttackedTimes) {
                this.isDestroyed = true;
            }
        }


    }

    public static int defend() {
        Random random = new Random(/*System.currentTimeMillis()*/);
        int probability = Config.Target.DEFEND_PROBABILITY; // 生成 0 的概率为 probability%，生成 1 的概率为 (100 - probability)%
        int bound = 100; // 设置范围为 [0, 100)
        int randomNumber = random.nextInt(bound); // 生成一个范围在 [0, bound) 的随机整数
//        System.out.println("randomNumber is " + randomNumber);
        int result;
        if (randomNumber < probability) {
            result = 1;
        } else {
            result = 0;
        }


        return result; // 生成一个范围在 [0, 2) 的随机整数
    }

    @Override
    public String toString() {
        return "Target{" +
                "maxBeAttackedTimes=" + maxBeAttackedTimes +
                ", beAttackedTimes=" + beAttackedTimes +
                ", isDestroyed=" + isDestroyed +
                ", coordinate=" + coordinate +
                '}';
    }
}
