package com.zhouenyu;

/**
 * @author 魂之子
 * @since 2023-12-30 12:08
 * program: javacourse
 * description:
 */
public final class Config {
    public final static class Aircraft {
        public static int MAX_ATTACK_TIMES = 2;
    }

    public final static class Base {
        public static int AIRCRAFT_NUM = 2;
    }

    public final static class Target {
        public static int MAX_BE_ATTACKED_TIMES = 2;
        public static int DEFEND_PROBABILITY = 10;  //为 0 到 100 的一个数字
    }

    public final static class Battlefield {
        public static int X = 15; //表示战场的长度
        public static int Y = 15;    //表示战场的宽度
        public static int NUM_ATTACKER = 2;
        public static int NUM_DEFENDER = 2;
    }

    public static void printConfig() {

        System.out.println("Aircraft Configuration:");
        System.out.println("  每个飞机的最大攻击次数: " + Aircraft.MAX_ATTACK_TIMES);

        System.out.println("\nBase Configuration:");
        System.out.println("  战机的数量: " + Base.AIRCRAFT_NUM);

        System.out.println("\nTarget Configuration:");
        System.out.println("  目标最大被攻击数量: " + Target.MAX_BE_ATTACKED_TIMES);
        System.out.println("  防御成功的概率: %" + Target.DEFEND_PROBABILITY);

        System.out.println("\nBattlefield Configuration:");
        System.out.println("  战场长度: " + Battlefield.X);
        System.out.println("  战场宽度: " + Battlefield.Y);
        System.out.println("  基地数量: " + Battlefield.NUM_ATTACKER);
        System.out.println("  目标数量: " + Battlefield.NUM_DEFENDER);

    }

}

