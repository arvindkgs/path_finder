package com.akgs.algo.pathfinder.service.impl;

import com.akgs.algo.pathfinder.data.Cell;
import com.akgs.algo.pathfinder.data.Grid;
import com.akgs.algo.pathfinder.service.Constraint;

public class UpperLimitConstraint implements Constraint {

    @Override
    public boolean isValid(Cell cell, Grid grid) {
        return cell.getX() <= grid.getMaxX() && cell.getY() <= grid.getMaxY();
    }
}
