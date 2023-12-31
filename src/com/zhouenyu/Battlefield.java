package com.zhouenyu;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 魂之子
 * @since 2023-12-30 12:30
 * program: javacourse
 * description: 用于战场模拟
 */
public class Battlefield {
    public static Battlefield battlefield = new Battlefield();
    private Battlefield() {
        this.x = Config.Battlefield.X;
        this.y = Config.Battlefield.Y;
        this.numBases = Config.Battlefield.NUM_ATTACKER;
        this.numDefender = Config.Battlefield.NUM_DEFENDER;
        this.bases = new LinkedList<>();
        this.targets = new LinkedList<>();
        generateMap();
    }
    public static Battlefield getInstance(){
        return battlefield;
    }
    // 假设战场是一个矩形
    private final int x; //表示长
    private final int y;  //表示宽
    public static int[][] map;  // 1表示 attack 2表示defender
    private final int numBases;
    private final int numDefender;
    private List<Base> bases;
    private List<Target> targets;
    public static volatile ConcurrentHashMap<Coordinate,Target> targetTable = new ConcurrentHashMap <>();



    //要确定 attacker 和 defender 的数量
    private void generateMap(){
        map = new int[x][y];
        randomizePositions(map,1,this.numBases);
        randomizePositions(map,2,this.numDefender);
    }

    //随机将count个变量变成 value
    private void randomizePositions(int[][] array, int value, int count) {
        int rows = array.length;
        int cols = array[0].length;
        Random random = new Random(System.currentTimeMillis());

        while (count > 0) {
            int row = random.nextInt(rows);
            int col = random.nextInt(cols);

            if (array[row][col] == 0) {
                array[row][col] = value;
                count--;
            }
        }
    }

    public void generateAtkNDfd(){
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if(map[i][j] == 1){
                    bases.add(new Base(Config.Base.AIRCRAFT_NUM,Config.Aircraft.MAX_ATTACK_TIMES,new Coordinate(i,j)));
                }
                if(map[i][j] == 2){
                    Coordinate coordinate = new Coordinate(i, j);
                    Target target = new Target(Config.Target.MAX_BE_ATTACKED_TIMES, coordinate);
                    targets.add(target);
                    targetTable.put(coordinate,target);
                }
            }
        }
    }

    public ConcurrentHashMap <Coordinate, Target> getTargetTable() {
        return targetTable;
    }



    public List<Base> getBases() {
        return bases;
    }

    public void setBases(List<Base> bases) {
        this.bases = bases;
    }

    public List<Target> getTargets() {
        return targets;
    }

    public void setTargets(List<Target> targets) {
        this.targets = targets;
    }
}
