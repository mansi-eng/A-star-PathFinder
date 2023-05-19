package com.pathFinder.domain;

import java.util.ArrayList;
import java.util.List;
//this is the path state class 
//contain the open list ,closed and current node


public class PathSearchState {
	//class member
	private List<Node> open = new ArrayList<Node>(), closed = new ArrayList<Node>();
	private Node currentNode;
	
	public PathSearchState() {
		super();
	}
	//constructor
	public PathSearchState(List<Node> open, List<Node> closed, Node currentNode) {
		super();
		this.open = open;
		this.closed = closed;
		this.currentNode = currentNode;
	}
	
	//member function
	public List<Node> getOpen() {
		return open;
	}
	
	public void setOpen(List<Node> open) {
		this.open = open;
	}
	
	public List<Node> getClosed() {
		return closed;
	}
	
	public void setClosed(List<Node> closed) {
		this.closed = closed;
	}
	
	public Node getCurrentNode() {
		return currentNode;
	}
	
	public void setCurrentNode(Node currentNode) {
		this.currentNode = currentNode;
	}
}
