package com.akgs.algo.pathfinder.data;

import lombok.Getter;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Cell {
    @Getter
    final int x;
    @Getter
    final int y;
    final Lock lock;

    public Cell(int x, int y){
        this.x = x;
        this.y = y;
        lock = new ReentrantLock();
    }

    /**
     * Check if this cell is to left of other cell
     * @param other
     * @return
     */
    boolean isLeft(Cell other){
        return x == other.x - 1 && y == other.y;
    }

    /**
     * Check if this cell is to right of other cell
     * @param other
     * @return
     */
    boolean isRight(Cell other){
        return x == other.x + 1 && y == other.y;
    }

    /**
     * Check if this cell is to above other cell
     * @param other
     * @return
     */
    boolean isUp(Cell other){ return x == other.x && y == other.y + 1; }

    /**
     * Check if this cell is below other cell
     * @param other
     * @return
     */
    boolean isDown(Cell other){
        return x == other.x && y == other.y - 1;
    }

    boolean isNeighbor(Cell other){
        return isLeft(other) || isRight(other) || isUp(other) || isDown(other);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Cell){
            Cell other = (Cell)obj;
            if(this.x == other.x && this.y == other.y){
                return true;
            }
            else{
                return false;
            }
        }
        return false;
    }

    public Cell left() {
        return new Cell(x,y-1);
    }

    public Cell right() {
        return new Cell(x,y+1);
    }

    public Cell up() {
        return new Cell(x+1,y);
    }

    public Cell down() {
        return new Cell(x-1,y);
    }

    @Override
    public String toString() {
        return "("+x+", "+y+")";
    }
}
