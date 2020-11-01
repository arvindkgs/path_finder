package com.akgs.algo.pathfinder.service.impl;

import com.akgs.algo.pathfinder.data.Cell;
import com.akgs.algo.pathfinder.data.Grid;
import com.akgs.algo.pathfinder.service.Constraint;

public class SingleRobotInCellConstraint implements Constraint {

    /**
     * Checks if target cell to move to is already occupied by another robot
     * @param target
     * @param grid
     * @return
     */
    @Override
    public boolean isValid(Cell target, Grid grid) {
        for(Cell cell : grid.getOccupiedCells()){
            if(target.equals(cell)){
                return false;
            }
        }
        return true;
    }
}
