package com.zhouenyu;

import java.util.List;

/**
 * @author 魂之子
 * @since 2023-12-30 17:34
 * program: javacourse
 * description: 主程序运行
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("程序的配置文件如下");
        Config.printConfig();
        //单例模式的 battlefield
        Battlefield battlefield = Battlefield.getInstance();
        battlefield.generateAtkNDfd();
        //找到所有的基地
        List<Base> bases = battlefield.getBases();
        //让所有飞机起飞进行攻击
        for (Base base : bases) {
            List<Aircraft> aircrafts = base.getAircraft();
            for (Aircraft aircraft : aircrafts) {
                aircraft.start();
            }
        }
//        System.out.println("attack is over");
        return;

    }
}
