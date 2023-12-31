package com.zhouenyu;

import java.util.PriorityQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.logging.Logger;

/**
 * @author 魂之子
 * @since 2023-12-30 11:53
 * program: javacourse
 * description: 战机类
 */
public class Aircraft extends Thread {
    public static final Object lock = new Object(); // 锁对象

    private volatile int maxAttackTimes;
    private volatile boolean isDestroyed;
    private volatile int attackTimes;
    private PriorityBlockingQueue<Coordinate> attackOrder;
    private Coordinate coordinate;

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }


    public Aircraft(int maxAttackTimes, PriorityBlockingQueue<Coordinate> attackOrder) {
        this.maxAttackTimes = maxAttackTimes;
        this.attackOrder = attackOrder;
    }


    public int getMaxAttackTimes() {
        return maxAttackTimes;
    }

    public void setMaxAttackTimes(int maxAttackTimes) {
        this.maxAttackTimes = maxAttackTimes;
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    public void setDestroyed(boolean destroyed) {
        isDestroyed = destroyed;
    }


    public int getAttackTimes() {
        return attackTimes;
    }

    public void setAttackTimes(int attackTimes) {
        this.attackTimes = attackTimes;
    }

    @Override
    public void run() {
        //首先判断该飞机是否可以进行攻击
        synchronized (lock) {
            if (this.attackTimes == this.maxAttackTimes || this.isDestroyed) {
                this.isDestroyed = true;
                System.out.println("one craft from " + this.getCoordinate() +  Thread.currentThread().getName()+  " was exhausted its ammunition");
                return;
            }
        }

        //1. 找一个攻击目标
        //2. 攻击
        long distance = attack(this.attackOrder);

        if (distance == -1) {
            return;
        }
        int defend = Target.defend();
        //defend == 1 表示防御成功，该飞机报废，线程可以停止了
        //defend == 0 说明飞机还能够进行下一轮的攻击
        if (defend == 1) {
            // 递归出口
            System.out.println("the craft form "+this.getCoordinate()+ Thread.currentThread().getName()+" was shot down ");
            synchronized (lock) {
                this.isDestroyed = true;
            }
            return;
        }
        //3. 根据距离进行攻击，需要进行飞行，飞行时间用sleep进行表示
        try {
            Thread.sleep(distance * 500);
            if (defend == 0) {
                run();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    //找到一个可攻击的 target 并攻击一次 返回距离
    public long attack(PriorityBlockingQueue<Coordinate> attackOrder) {
        synchronized (lock) {
            Coordinate peek = attackOrder.peek();
            if(peek == null){
//                System.out.println(Thread.currentThread().getName() + " 107 peek == null");
                return -1;
            }
            //检测是否该位置被destroy掉
            //如果这个peek 的target 被摧毁，我要将这个target 跳过
            while (Battlefield.targetTable.get(peek).isDestroyed() &&
                    Battlefield.targetTable.get(peek).getMaxBeAttackedTimes() == Battlefield.targetTable.get(peek).getBeAttackedTimes().get()) {
                 attackOrder.poll();
                 peek = attackOrder.peek();
//                System.out.println("114 poll:"+ Thread.currentThread().getName() + peek);
                if(attackOrder.peek() == null) {
//                    System.out.println("116");
                    return -1;
                }
            }
            peek = attackOrder.peek();

//            if (peek == null) {
//                //-1 表示攻击结束，进攻方胜利
//                return -1;
//            }
            // 有可攻击的目标，进行一次攻击
            System.out.println("aircraft form " + this.getCoordinate()+Thread.currentThread().getName() + " is attacking " + peek);
            this.setAttackTimes(this.getAttackTimes() + 1);
            //根据peek的坐标找到对应的 target
            Target target = Battlefield.targetTable.get(peek);
            if (!target.isDestroyed()) {
                target.beAttacked();
                if (target.isDestroyed()) {
                    Coordinate poll = attackOrder.poll();
//                    System.out.println("139 poll "+ Thread.currentThread().getName()+ poll);
                    System.out.println(
                            target.getCoordinate() + "is crashed"
                    );

                    //检查是否还有攻击目标
                    if (attackOrder.isEmpty()) {
                        System.out.println("飞机击落所有目标，进攻方胜利");
                        System.exit(0);
                        return -1;
                    }

                }
            } else {
                System.out.println("执行");
            }
            double distance = Coordinate.calculateDistance(this.coordinate, target.getCoordinate());
            return (long) distance;
        }
    }
}
