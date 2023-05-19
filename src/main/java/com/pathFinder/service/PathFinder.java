package com.pathFinder.service;
//this class contain initialoze graph function to initialize the graph 


import java.util.ArrayList;
import java.util.List;

import com.pathFinder.domain.Node;
import com.pathFinder.domain.PathSearchStateJson;

public abstract class PathFinder {
	//class member
	
	static Integer m = 14, n = 16;
	Node grid[][] = new Node[m][n];
	static String startNodeId,  stopNodeId;
	List<Node> path = new ArrayList<>();
	
	void initializeGraph() {
		String type;
		Integer g;
		Double h, f;
		for(int row = 0; row < m; row++) {
			for(int column = 0; column < n; column++) {
				grid[row][column] = new Node(String.valueOf(row)+ "-" + String.valueOf(column));
				if(grid[row][column].getId().equals(startNodeId)) {
					type = "start";
					g = 0;
					h = Double.MAX_VALUE;
					f =  Double.MAX_VALUE;
				} 
				else if(grid[row][column].getId().equals(getStopNodeId())) {
					type = "goal";
					h = 0.0;
					g = Integer.MAX_VALUE;
					f = (double) Integer.MAX_VALUE;
				}
				else {
					type = "unVisited";
					g = Integer.MAX_VALUE;
					h = Double.MAX_VALUE;
					f = Double.MAX_VALUE;
				}
				grid[row][column].setG(g);
				grid[row][column].setHeuristics(h);
				grid[row][column].setType(type);
				grid[row][column].setF(f);
			}
		}
	}
	
	//abstraxt method of algorith 
	
	abstract PathSearchStateJson algorithm(PathSearchStateJson state);
	
	public static String getStopNodeId() {
		return stopNodeId;
	}

	public static void setStopNodeId(String stopNode) {
		PathFinder.stopNodeId = stopNode;
	}
	
	public static String getstartNodeId() {
		return startNodeId;
	}

	public static void setstartNodeId(String startNode) {
		PathFinder.startNodeId = startNode;
	}

	public static Integer getM() {
		return m;
	}

	public static void setM(Integer m) {
		PathFinder.m = m;
	}

	public static Integer getN() {
		return n;
	}

	public static void setN(Integer n) {
		PathFinder.n = n;
	}
	
}
