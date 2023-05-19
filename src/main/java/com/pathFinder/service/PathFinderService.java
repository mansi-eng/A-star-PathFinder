package com.pathFinder.service;

import com.pathFinder.domain.PathSearchStateJson;
import com.pathFinder.domain.StartStopNode;

public class PathFinderService {
	
	private A_star aStar = new A_star();

	public PathSearchStateJson getNextState(PathSearchStateJson currentState) {
		PathSearchStateJson newState = aStar.algorithm(currentState);
		return newState;
	}

	public void setStartNodes(StartStopNode startNodes) {
		System.out.println(startNodes.getStart() + " " + startNodes.getStop());
		A_star.setstartNodeId(startNodes.getStart());
		A_star.setStopNodeId(startNodes.getStop());
		System.out.println("M: " + (Integer.parseInt(startNodes.getStop().split("-")[0]) + 1));
		System.out.println("N: " + (Integer.parseInt(startNodes.getStop().split("-")[1]) + 1));
		
		//A_star.setM(Integer.parseInt(startNodes.getStop().split("-")[0]) + 1);
	
	//   A_star.setN(Integer.parseInt(startNodes.getStop().split("-")[1]) + 1);
		System.out.println("Done setting .... ");
	}
	
}
