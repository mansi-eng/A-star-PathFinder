package com.pathFinder.domain;

import java.util.Objects;
import com.pathFinder.service.A_star;
//node class 
//here we have defined the properties of node 
//it will have the id ,type ,g,h,and f,parent node id  with every node 

public class Node implements Comparable<Node>{

	//classs members of node 
	private	String id, type, parentNodeId;
	private Integer g;
	private Double heuristics, f;
	
	
	//constructor 
	public Node(String id) {
		super();
		this.id = id;
	}

	//constructor
	public Node(String id, String type, String parentNodeId, Integer g, Double heuristics, Double f) {
		super();
		this.id = id;
		this.type = type;
		this.parentNodeId = parentNodeId;
		this.g = g;
		this.heuristics = heuristics;
		this.f = f;
	}
	
	
	//calculating heuristics 
	Double getEuclideanDist() {
		int x1 = Integer.parseInt(this.getId().split("-", 2)[0]), y1 = Integer.parseInt(this.getId().split("-", 2)[1]), x2 = Integer.parseInt(A_star.getStopNodeId().split("-", 2)[0]) , y2 = Integer.parseInt(A_star.getStopNodeId().split("-", 2)[1]);
		return Math.sqrt(Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2));
	}

	//calculating cost fun
	public void calculateCost(Node n) {
		System.out.println("function cost");
		this.g = n.getG() + 1;
		this.heuristics = getEuclideanDist();
		this.f = (double)(this.g) + this.heuristics;
	}
	
	@Override
	public String toString() {
		//return "Node [id=" + id + ", type=" + type + ", g=" + g + ", heuristics=" + heuristics + ", Full Cost =" + f + ", parent=" + parentNodeId + "]";
		return "Node [id=" + id + ", Full Cost =" + f+ "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		return Objects.equals(g, other.g) && Objects.equals(heuristics, other.heuristics)
				&& Objects.equals(id, other.id);
	}
	
	@Override
	public int compareTo(Node node) {
		if (this.f > node.f) return 1;
		else if(this.g < node.g) return -1;
		return this.heuristics.compareTo(node.heuristics);
	}
	
	public String getId() {
		return id;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getG() {
		return g;
	}

	public void setG(Integer g) {
		this.g = g;
	}

	public Double getHeuristics() {
		return heuristics;
	}

	public void setHeuristics(Double heuristics) {
		this.heuristics = heuristics;
	}
	
	public String getParentNodeId() {
		return parentNodeId;
	}

	public void setParentNodeId(String pid) {
		this.parentNodeId = pid;
	}

	
	public Double getF() {
		return f;
	}

	public void setF(Double f) {
		this.f = f;
	}
	
}
