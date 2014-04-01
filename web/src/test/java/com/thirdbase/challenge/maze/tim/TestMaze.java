package com.thirdbase.challenge.maze.tim;

import org.junit.Test;

import com.thirdbase.challenge.util.SolutionTester;

/**
 * Maze is an NxM squares maze where each square has the cordinate n,m
 * @author Tim Mickelson
 *
 */
public class TestMaze {
	
	@Test
	public void maze() throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		String[] args = {"path=com", "class=com.thirdbase.challenge.maze.tim.SolverImpl", "size=150", "seed=5", "mirror=no", "type=sparse", "visualize=yes"};
		SolutionTester.main(args);
		//SolutionTester solutionTest = new SolutionTester(new SolverImpl(), 50, 100L, false, false, true);
		//solutionTest.solve();
	}
	
} // end class TestMaze