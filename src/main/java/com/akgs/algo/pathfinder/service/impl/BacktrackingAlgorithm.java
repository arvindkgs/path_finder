package com.akgs.algo.pathfinder.service.impl;

import com.akgs.algo.pathfinder.data.Cell;
import com.akgs.algo.pathfinder.data.Grid;
import com.akgs.algo.pathfinder.data.Robot;
import com.akgs.algo.pathfinder.service.Algorithm;

import java.util.ArrayList;
import java.util.List;

public class BacktrackingAlgorithm implements Algorithm {
    List<Cell> visited;

    public BacktrackingAlgorithm(){
    }

    /**
     * Implements backtracking algorithm
     *
     * Robot moves one step to right, if constraints allow else,
     *          moves one step to top, if constraints allow else,
     *          moves one step to down, if constraints allow else,
     *          moves one step to left, if constraints allow else,
     * until it has visited all cells or it is visiting an already visited cell (meaning it is in a loop)
     * @param grid Grid to traverse
     * @param robot Robot to move
     * @param target Target cell to reach robot
     * @param path Path taken by robot
     */
    @Override
    public void traverse(Grid grid, Robot robot, Cell target, List<Cell> path) {
        visited = new ArrayList<>();
        recursiveTraversal(grid, robot, target, path);
    }

    private boolean recursiveTraversal(Grid grid, Robot robot, Cell target, List<Cell> path){
        boolean found = false;
        Cell initalPosition = grid.getRobotPosition(robot);
        if(initalPosition == null){
            return false;
        }
        if(visited.contains(grid.getRobotPosition(robot)) || visited.size() >= grid.getMaxX() * grid.getMaxY())
            return false;

        visited.add(grid.getRobotPosition(robot));

        if(grid.getRobotPosition(robot)!=null && grid.getRobotPosition(robot).equals(target)){
            path.add(target);
            return true;
        }

        if(grid.move(robot, initalPosition.right())){
            if(recursiveTraversal(grid, robot, target, path)){
                found = true;
                path.add(initalPosition);
            }
            else{
                grid.move(robot, grid.getRobotPosition(robot).left());
            }
        }

        if(!found && grid.move(robot, initalPosition.up())){
            if(recursiveTraversal(grid, robot, target, path)) {
                found = true;
                path.add(initalPosition);
            }
            else{
                grid.move(robot, grid.getRobotPosition(robot).down());
            }
        }

        if(!found && grid.move(robot, initalPosition.down())){
            if(recursiveTraversal(grid, robot, target, path)) {
                found = true;
                path.add(initalPosition);
            }
            else{
                grid.move(robot, grid.getRobotPosition(robot).up());
            }
        }

        if(!found && grid.move(robot, initalPosition.left())){
            if(recursiveTraversal(grid, robot, target, path)) {
                found = true;
                path.add(initalPosition);
            }
            else{
                grid.move(robot, grid.getRobotPosition(robot).right());
            }
        }

        return found;
    }
}
