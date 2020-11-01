package com.akgs.algo.pathfinder.service;

import com.akgs.algo.pathfinder.data.Cell;
import com.akgs.algo.pathfinder.data.Grid;
import com.akgs.algo.pathfinder.data.Robot;

import java.util.List;

public interface Algorithm {
    void traverse(Grid grid, Robot robot, Cell target, List<Cell> path);
}