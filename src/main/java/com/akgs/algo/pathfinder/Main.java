package com.akgs.algo.pathfinder;

import com.akgs.algo.pathfinder.data.Cell;
import com.akgs.algo.pathfinder.data.Grid;
import com.akgs.algo.pathfinder.data.Robot;
import com.akgs.algo.pathfinder.service.impl.LowerLimitConstraint;
import com.akgs.algo.pathfinder.service.impl.UpperLimitConstraint;
import com.akgs.algo.pathfinder.service.impl.BacktrackingAlgorithm;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
        Robot tron = new Robot("Tron");
        Robot mesa = new Robot("Mesa");
        Grid grid = new Grid.Builder().
                setMax(new Cell(3,3)).
                addConstraint(new UpperLimitConstraint()).
                addConstraint(new LowerLimitConstraint()).
                addRobot(tron, new Cell(1,1)).
                addRobot(mesa, new Cell(1,3)).
                build();
        final List<Cell> tronPath = new ArrayList<>();
        Thread runTronThread = new Thread(() -> { 
            new BacktrackingAlgorithm().traverse(grid, tron, new Cell(3,3), tronPath);
        });
        final List<Cell> mesaPath = new ArrayList<>();
        Thread runMesaThread = new Thread(() -> {
            new BacktrackingAlgorithm().traverse(grid, mesa, new Cell(1,1), mesaPath);
        });
        runMesaThread.start();
        runTronThread.start();
        runMesaThread.join();
        runTronThread.join();
        printPath(tron, tronPath);
        printPath(mesa, mesaPath);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void printPath(Robot robot, List<Cell> path) {
        System.out.println(robot);
        for (int i = path.size() - 1; i >= 0; i--) {
            if (i != 0) {
                System.out.print(path.get(i) + " -> ");
            } else {
                System.out.println(path.get(i));
            }
        }
    }
}
