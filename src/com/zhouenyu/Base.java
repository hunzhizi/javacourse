package com.zhouenyu;

import java.util.*;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * @author 魂之子
 * @since 2023-12-30 12:01
 * program: javacourse
 * description:
 */
public class Base {
    private List<Aircraft> aircraft;
    private boolean allAircraftCrashed;
    private Coordinate coordinate;  //基地的坐标
    private volatile PriorityBlockingQueue<Coordinate> attackOrder;

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public List<Aircraft> getAircraft() {
        return aircraft;
    }

    public void setAircraft(List<Aircraft> aircraft) {
        this.aircraft = aircraft;
    }

    public boolean isAllAircraftCrashed() {
        return allAircraftCrashed;
    }

    public void setAllAircraftCrashed(boolean allAircraftCrashed) {
        this.allAircraftCrashed = allAircraftCrashed;
    }

    public Base(int aircraftNum, int maxAttackTimes, Coordinate coordinate) {
        this.aircraft = new LinkedList<>();
        this.coordinate = coordinate;
        //根据map生成攻击序列
        generateAttackOrder(Battlefield.map);
        for (int i = 0; i < aircraftNum; i++) {
            Aircraft aircraft = new Aircraft(maxAttackTimes, this.attackOrder);
            aircraft.setCoordinate(this.coordinate);
            this.aircraft.add(aircraft);
        }

    }

    //根据距离生成攻击序列
    private void generateAttackOrder(int[][] map) {
        int x = this.coordinate.getX();
        int y = this.coordinate.getY();
        int length = map.length;

//        this.attackOrder = new PriorityBlockingQueue<>();
        this.attackOrder = new PriorityBlockingQueue<>(30,
                new Comparator<Coordinate>() {
                    @Override
                    public int compare(Coordinate o1, Coordinate o2) {
                        return (int) (Coordinate.calculateDistance(o1, coordinate) - Coordinate.calculateDistance(o2, coordinate));
                    }
                }
        );
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == 2) {
                    attackOrder.add(new Coordinate(i, j));

                }
            }
        }
    }


}
