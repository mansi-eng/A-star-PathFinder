package com.pathFinder.domain;
//path state class of json format


public class PathSearchStateJson {
	//class member in string format 
	String[] open, closed;
	String currentNode;
	
	public PathSearchStateJson() {
		super();
	}

	public PathSearchStateJson(String[] open, String[] closed, String currentNode) {
		super();
		this.open = open;
		this.closed = closed;
		this.currentNode = currentNode;
	}

	public String[] getOpen() {
		return open;
	}
	
	public void setOpen(String[] open) {
		this.open = open;
	}
	
	public String[] getClosed() {
		return closed;
	}
	
	public void setClosed(String[] closed) {
		this.closed = closed;
	}

	public String getCurrentNode() {
		return currentNode;
	}
	 
	public void setCurrentNode(String currentNode) {
		this.currentNode = currentNode;
	}
}
