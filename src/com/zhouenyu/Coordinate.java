package com.zhouenyu;

import java.util.Objects;

/**
 * @author 魂之子
 * @since 2023-12-30 15:07
 * program: javacourse
 * description: 坐标
 */
public class Coordinate {
    private int x;
    private int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public static double calculateDistance(Coordinate c1, Coordinate c2){
        int abs_x = Math.abs(c1.getX() - c2.getX());
        int abs_y = Math.abs(c1.getY() - c2.getY());
        return Math.pow(
                Math.pow(abs_x, 2) + Math.pow(abs_y, 2)
                , 0.5);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return x == that.x &&
                y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
