package com.pathFinder.web;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pathFinder.domain.PathSearchStateJson;
import com.pathFinder.domain.StartStopNode;
import com.pathFinder.service.PathFinderService;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class PathFinderController {
	
	private PathFinderService pathService = new PathFinderService();
	
	@PutMapping(value = "/api/startNodes/{id}")
	public ResponseEntity<?> getStartNodes(@PathVariable("id") String id, @RequestBody StartStopNode startNodes) {
		System.out.print("Recieved Id: " + id + "\n");
		pathService.setStartNodes(startNodes);
		return ResponseEntity.ok(startNodes);
	}
	
	
	@PutMapping("/api/pathState/{id}")
	public ResponseEntity<?> getNextPath(@PathVariable("id") String id, @RequestBody PathSearchStateJson currentState) {
//		System.out.println("Received Id: " + id);
//		if(currentState.getOpen() != null)
//			System.out.println("Open list: " + currentState.getOpen());
//		if(currentState.getClosed() != null)
//			System.out.println("Closed list: " + currentState.getClosed());
//		if(currentState.getCurrentNode() != null)
//			System.out.println("Current Node: " + currentState.getCurrentNode());
		PathSearchStateJson newState = pathService.getNextState(currentState);
		return ResponseEntity.ok(newState);
	}
}
