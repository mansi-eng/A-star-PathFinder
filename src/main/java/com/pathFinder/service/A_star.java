package com.pathFinder.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.pathFinder.domain.Node;
import com.pathFinder.domain.PathSearchState;
import com.pathFinder.domain.PathSearchStateJson;

//this class contai the astar algorith 

public class A_star extends PathFinder {
	
	
	private List<Node> open = new ArrayList<Node>(), closed = new ArrayList<Node>();
	private Node currentNode;
	
	//neighbours logic 
	
	private boolean canMoveUp(int x, int y)    {	return y - 1 >= 0;  }
	private boolean canMoveDown(int x, int y)  {	return y + 1 <= n;	}
	private boolean canMoveLeft(int x, int y)  {	return x - 1 >= 0;	}
	private boolean canMoveRight(int x, int y) {	return x + 1 <= m;  	}
	//############################3
	private List<Node> getNeighbours() {
		int posX = Integer.parseInt(currentNode.getId().split("-", 2)[1]), posY = Integer.parseInt(currentNode.getId().split("-", 2)[0]);
		List<Node> neighbours = new ArrayList<Node>();
System.out.println("pos: X" + posX + "pos Y: " + posY);
		if(canMoveUp(posX, posY)) neighbours.add(grid[posY - 1][posX]);
		if(canMoveDown(posX, posY)) neighbours.add(grid[posY + 1][posX]);
		if(canMoveLeft(posX, posY)) neighbours.add(grid[posY][posX - 1]);
		if(canMoveRight(posX, posY)) neighbours.add(grid[posY ][posX + 1]);
		if(canMoveUp(posX, posY) && canMoveRight(posX, posY)) neighbours.add(grid[posY - 1][posX + 1]);
		if(canMoveUp(posX, posY) && canMoveLeft(posX, posY)) neighbours.add(grid[posY - 1][posX - 1]);
		if(canMoveDown(posX, posY) && canMoveRight(posX, posY)) neighbours.add(grid[posY + 1][posX + 1]);
		if(canMoveDown(posX, posY) && canMoveLeft(posX, posY)) neighbours.add(grid[posY + 1][posX - 1]);
		System.out.println("___________________________________");
		System.out.println("open : " + open);
		System.out.println("closed : " +closed);
		System.out.println("current : " + currentNode);
		
		//System.out.print("neighbours : " +neighbours);
		
		return neighbours;
	}
	//--------------------------------
	//to decode the recived data from json string to list format
	
	PathSearchState decodeRecievedData(PathSearchStateJson recievedState) {
		List<Node> openNodes = new ArrayList<>(), closedNodes = new ArrayList<>();
		
		Integer rowId, columnId;
		for(String visitedNode : recievedState.getOpen()) {
			rowId = Integer.parseInt(visitedNode.split("-", 2)[0]);
			columnId = Integer.parseInt(visitedNode.split("-", 2)[1]);
			openNodes.add(grid[rowId][columnId]);
		}
		
		for(String fixedNode : recievedState.getClosed()) {
			rowId = Integer.parseInt(fixedNode.split("-", 2)[0]);
			columnId = Integer.parseInt(fixedNode.split("-", 2)[1]);
			closedNodes.add(grid[rowId][columnId]);
		}
		
		return new PathSearchState(openNodes, closedNodes, grid[Integer.parseInt(recievedState.getCurrentNode().split("-", 2)[0])][Integer.parseInt(recievedState.getCurrentNode().split("-", 2)[1])]);
	}
	//-----------------------------------------------
	//to sent data to server we will encode data from list of int to json tring format
	
	PathSearchStateJson encodeDataToBeSent(PathSearchState state) {
		String[] openNodes = new String[open.size()], closedNodes = new String[closed.size()];
		
		for(int i = 0; i < openNodes.length; i++) {
			openNodes[i] = open.get(i).getId();
		}
		
		for(int i = 0; i < closedNodes.length; i++) {
			closedNodes[i] = closed.get(i).getId();
		}
		
		return new PathSearchStateJson(openNodes, closedNodes, state.getCurrentNode().getId());
	}
	//--------------------------------ALGORTIHM
	
	//INITIAlize graph
	//recive data for json
	//set the open ,clkosed and current state
	//get the neigbours of current node
	//update the open closed and current node
	//send the updated data to front end
	
	@Override
	PathSearchStateJson algorithm(PathSearchStateJson recievedState) {
		System.out.println("Before setting grid: \nm: " + m + " n: " + n);
		
		if(closed.size()==1) {
			initializeGraph();
			System.out.println("____________size of closed list" + closed.size());
		}
		
		
		PathSearchState currentState = decodeRecievedData(recievedState);
		
		open = currentState.getOpen();
		currentNode = currentState.getCurrentNode();
		closed = currentState.getClosed();
		
		
		List<Node> neighbours = getNeighbours();
		for(Node neighbour : neighbours) {
			System.out.println("neigh: "+ neighbours);
			if(!closed.contains(neighbour) && !open.contains(neighbour) ) { 
				neighbour.setParentNodeId(currentNode.getId());
				neighbour.setType("visited");
				neighbour.calculateCost(currentNode);
				open.add(neighbour);
			} else if (neighbour.getF() > (currentNode.getG() + 1 + neighbour.getHeuristics())) {
				neighbour.setParentNodeId(currentNode.getId());
				neighbour.calculateCost(currentNode);
				
				if (closed.contains(neighbour)) {
					closed.remove(neighbour);
					open.add(neighbour);
				}
			}
		}
		open.remove(currentNode);
		closed.add(currentNode);
		
		Collections.sort(open);
		
		System.out.println("\n" + open.get(0).getId());
		return encodeDataToBeSent(new PathSearchState(open, closed, open.get(0)));
	}

}
