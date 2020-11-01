package com.akgs.algo.pathfinder;

import com.akgs.algo.pathfinder.data.Cell;
import com.akgs.algo.pathfinder.data.Grid;
import com.akgs.algo.pathfinder.data.Robot;
import com.akgs.algo.pathfinder.service.impl.BacktrackingAlgorithm;
import com.akgs.algo.pathfinder.service.impl.LowerLimitConstraint;
import com.akgs.algo.pathfinder.service.impl.UpperLimitConstraint;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

public class PathFinderTest {
    @Test
    public void singleThreadedPositiveBottomUpTest(){
        Robot tron = new Robot("Tron");
        Grid grid = new Grid.Builder().setMax(new Cell(3,3)).addConstraint(new UpperLimitConstraint()).addConstraint(new LowerLimitConstraint()).addRobot(tron).build();
        
        //Test from bottom to top
        List<Cell> tronPath = new ArrayList<>(); 
        BacktrackingAlgorithm btAlgo = new BacktrackingAlgorithm();
        btAlgo.traverse(grid, tron, new Cell(3,3), tronPath);
        //(1, 1) -> (1, 2) -> (1, 3) -> (2, 3) -> (3, 3)
        List<Cell> expected = new ArrayList<>();
        expected.add(new Cell(3,3));
        expected.add(new Cell(2,3));
        expected.add(new Cell(1,3));
        expected.add(new Cell(1,2));
        expected.add(new Cell(1,1));
        assertIterableEquals(expected, tronPath);
    }
    
    @Test
    public void singleThreadedPositiveTopDownTest(){
        //Test from top to bottom
        Robot mesa = new Robot("Mesa");
        Grid grid = new Grid.Builder().setMax(new Cell(3,3)).addConstraint(new UpperLimitConstraint()).addConstraint(new LowerLimitConstraint()).addRobot(mesa, new Cell(3,3)).build();
        List<Cell> mesaPath = new ArrayList<>();
        List<Cell> expected = new ArrayList<>();
        BacktrackingAlgorithm btAlgo = new BacktrackingAlgorithm();
        btAlgo.traverse(grid, mesa, new Cell(1,1), mesaPath);
        //(3, 3) -> (2, 3) -> (1, 3) -> (1, 2) -> (2, 2) -> (3, 2) -> (3, 1) -> (2, 1) -> (1, 1)
        expected.add(new Cell(1,1));
        expected.add(new Cell(2,1));
        expected.add(new Cell(3,1));
        expected.add(new Cell(3,2));
        expected.add(new Cell(2,2));
        expected.add(new Cell(1,2));
        expected.add(new Cell(1,3));
        expected.add(new Cell(2,3));
        expected.add(new Cell(3,3));
        assertIterableEquals(expected, mesaPath);
    }
    
    @Test
    public void singleThreadedNegativeTest(){
        Robot tron = new Robot("Tron");
        Robot mesa = new Robot("mesa");
        Grid grid = new Grid.Builder().setMax(new Cell(3,3)).addConstraint(new UpperLimitConstraint()).addConstraint(new LowerLimitConstraint()).addRobot(mesa).build();

        //Test from bottom to top
        List<Cell> tronPath = new ArrayList<>();
        BacktrackingAlgorithm btAlgo = new BacktrackingAlgorithm();
        btAlgo.traverse(grid, tron, new Cell(3,3), tronPath);
        //(1, 1) -> (1, 2) -> (1, 3) -> (2, 3) -> (3, 3)
        List<Cell> expected = new ArrayList<>();
        assertIterableEquals(expected, tronPath);
    }
    
    @Test
    public void singleThreadedAddRobotExistingGridTest(){
        Robot tron = new Robot("Tron");
        Grid grid = new Grid.Builder().setMax(new Cell(3,3)).addConstraint(new UpperLimitConstraint()).addConstraint(new LowerLimitConstraint()).addRobot(tron).build();

        //Test from bottom to top
        List<Cell> tronPath = new ArrayList<>();
        BacktrackingAlgorithm btAlgo = new BacktrackingAlgorithm();
        btAlgo.traverse(grid, tron, new Cell(3,3), tronPath);
        List<Cell> expected = new ArrayList<>();
        expected.add(new Cell(3,3));
        expected.add(new Cell(2,3));
        expected.add(new Cell(1,3));
        expected.add(new Cell(1,2));
        expected.add(new Cell(1,1));
        assertIterableEquals(expected, tronPath);

        //Test from top to bottom
        grid.removeRobot(tron);
        Robot mesa = new Robot("Mesa");
        grid.addRobot(mesa, new Cell(3,3));
        expected.clear();
        List<Cell> mesaPath = new ArrayList<>();
        btAlgo.traverse(grid, mesa, new Cell(1,1), mesaPath);
        //(3, 3) -> (2, 3) -> (1, 3) -> (1, 2) -> (2, 2) -> (3, 2) -> (3, 1) -> (2, 1) -> (1, 1)
        expected.add(new Cell(1,1));
        expected.add(new Cell(2,1));
        expected.add(new Cell(3,1));
        expected.add(new Cell(3,2));
        expected.add(new Cell(2,2));
        expected.add(new Cell(1,2));
        expected.add(new Cell(1,3));
        expected.add(new Cell(2,3));
        expected.add(new Cell(3,3));
        assertIterableEquals(expected, mesaPath);
    }
    
    @Test
    public void multiThreadedPositiveTest_noSingleCellConstraint(){
        try {
            Robot tron = new Robot("Tron");
            Robot mesa = new Robot("mesa");
            Grid grid = new Grid.Builder().setMax(new Cell(3,3)).addConstraint(new UpperLimitConstraint()).addConstraint(new LowerLimitConstraint()).addRobot(tron, new Cell(1,1)).addRobot(mesa, new Cell(1,3)).build();
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
            List<Cell> expectedMesa = new ArrayList<>();
            expectedMesa.add(new Cell(1,1));
            expectedMesa.add(new Cell(1,2));
            expectedMesa.add(new Cell(2,2));
            expectedMesa.add(new Cell(3,2));
            expectedMesa.add(new Cell(3,3));
            expectedMesa.add(new Cell(2,3));
            expectedMesa.add(new Cell(1,3));
            assertIterableEquals(expectedMesa, mesaPath);
            List<Cell> expectedTron = new ArrayList<>();
            expectedTron.add(new Cell(3,3));
            expectedTron.add(new Cell(2,3));
            expectedTron.add(new Cell(1,3));
            expectedTron.add(new Cell(1,2));
            expectedTron.add(new Cell(1,1));
            assertIterableEquals(expectedTron, tronPath);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void multiThreadedPositiveTest_withSingleCellContraint(){
        try {
            Robot tron = new Robot("Tron");
            Grid grid = new Grid.Builder().setMax(new Cell(3,3)).addConstraint(new UpperLimitConstraint()).addConstraint(new LowerLimitConstraint()).addRobot(tron, new Cell(1,1)).build();
            final List<Cell> tronPath = new ArrayList<>();
            Thread runTronThread = new Thread(() -> {
                new BacktrackingAlgorithm().traverse(grid, tron, new Cell(3,3), tronPath);
            });
            runTronThread.start();
            runTronThread.join();
            List<Cell> expectedTron = new ArrayList<>();
            expectedTron.add(new Cell(3,3));
            expectedTron.add(new Cell(2,3));
            expectedTron.add(new Cell(1,3));
            expectedTron.add(new Cell(1,2));
            expectedTron.add(new Cell(1,1));
            assertIterableEquals(expectedTron, tronPath);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
