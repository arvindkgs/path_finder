package com.akgs.algo.pathfinder.service;

import com.akgs.algo.pathfinder.data.Cell;
import com.akgs.algo.pathfinder.data.Grid;

public interface Constraint {
    boolean isValid(Cell cell, Grid grid);
}
