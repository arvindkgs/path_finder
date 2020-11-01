package com.akgs.algo.pathfinder.data;

import com.akgs.algo.pathfinder.service.Constraint;
import lombok.Getter;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Grid {
    /**
     * Grid type which can be instantiated only using Builder design pattern
     * This allows Grid to be customized and built. For example -
     * 1. Simple grid :  'new Grid.Builder().build'
     * 2. Customized grid : new Grid.Builder().
     *                      setMax(new Cell(3,3)).
     *                      addConstraint(new UpperLimitConstraint()).
     *                      addConstraint(new LowerLimitConstraint()).
     *                      addConstraint(new SingleRobotInCellConstraint()).
     *                      addRobot(tron, new Cell(1,1)).
     *                      addRobot(mesa, new Cell(1,3)).
     *                      build();
     */
    private List<Lock> locks;
    private Map<Robot, Cell> robots;
    @Getter
    private int maxX;
    @Getter
    private int maxY;
    @Getter
    private int minX;
    @Getter
    private int minY;
    private List<Constraint> contraints;

    private Grid() {
        contraints = new ArrayList<>();
        robots = new HashMap<>();
        locks = new ArrayList<>();
        maxX = Integer.MAX_VALUE;
        maxY = Integer.MAX_VALUE;
        minX = 1;
        minY = 1;
    }

    public Optional<Lock> getLock(Cell target){
        Optional<Lock> result = Optional.empty();
        if((target.x * (maxY - minY - 1) + target.y) >= locks.size())
            return result;
        return Optional.of(locks.get(target.x * (maxY - minY - 1) + target.y));
    }
    
    public Collection<Cell> getOccupiedCells(){
        return robots.values();
    }
    
    public Cell getRobotPosition(Robot robot){
        return robots.get(robot);
    }
    
    public void removeRobot(Robot robot){
        robots.remove(robot);
    }
    
    public void addRobot(Robot robot, Cell position){
        robots.put(robot, position);
    }

    /**
     * Moves robot to target cell. Returns true if move is made, false if move is not possible
     * @param robot
     * @param target
     * @return
     */
    public boolean move(Robot robot, Cell target){
        boolean valid = true;
        //check if robot is added to grid
        if(!robots.containsKey(robot)){
            System.out.println("ERROR: Robot not added to grid. First add robot to grid");
            return false;
        }
        Cell curr = robots.get(robot);
        //Check move is up, or down, or left, or right
        if( !curr.isNeighbor(target) ){
            return false;
        }
        for(Constraint constraint : contraints){
            if(!constraint.isValid(target, this)){
                return false;
            }
        }

        getLock(target).ifPresent(lock -> lock.lock());
        try{
            robots.put(robot, target);
        }
        finally {
            getLock(target).ifPresent(lock -> lock.unlock());
        }

        return valid;
    }

    public static class Builder {
        private Grid instance;
        public Builder(){
            instance = new Grid();
        }
        public Builder setMax(Cell max){
            instance.maxX = max.x;
            instance.maxY = max.y;
            return this;
        }
        public Builder setMin(Cell min){
            instance.minX = min.x;
            instance.minY = min.y;
            return this;
        }
        public Builder addConstraint(Constraint constraint){
            instance.contraints.add(constraint);
            return this;
        }
        public Builder addRobot(Robot robot){
            instance.robots.put(robot, new Cell(1,1));
            return this;
        }
        public Builder addRobot(Robot robot, Cell position){
            instance.robots.put(robot, position);
            return this;
        }
        public Grid build(){
            for(int i=instance.minX;i<=instance.maxX;i++){
                for(int j=instance.minY;j<=instance.maxY;j++){
                    instance.locks.add(new ReentrantLock());
                }
            }
            return instance;
        }
    }
}
