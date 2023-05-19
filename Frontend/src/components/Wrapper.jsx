import React, { useState } from "react";
import Node from "./Node";

const Wrapper = () => {
  const [nodeCounter, setNodeCounter] = useState(0);
  const [startNode, setStartNode] = useState("");
  const [stopNode, setStopNode] = useState("");
  const [path, setPath] = useState({ open: [], currentNode: "", closed: [] });

  const handleId = async (e) => {
    if (nodeCounter < 2) {
      if (nodeCounter === 0) {
        setStartNode(e.target.id);
      } else {
        setStopNode(e.target.id);
        setPath({
          open: [startNode],
          currentNode: startNode,
          closed: [startNode],
        });
      }
    } else if (nodeCounter === 2) {
      const response = await fetch(
        `http://localhost:8080/api/startNodes/${nodeCounter}`,
        {
          method: "PUT",
          headers: {
            "Content-Type": "application/json;charset=UTF-8",
          },
          body: JSON.stringify({
            start: startNode,
            stop: stopNode,
          }),
        }
      );

      console.log("respondeee", response);
    }
    console.log(
      e.target.id +
        " button clicked " +
        startNode +
        " -- " +
        stopNode +
        "  |" +
        " count: " +
        nodeCounter
    );
    setNodeCounter((prev) => prev + 1);
  };

  const getPath = async () => {
    try {
      console.log("Start node: " + startNode + "  Stop node: " + stopNode);
      console.log("Open: " + path.open + "  Current node: " + path.currentNode);

      let idCounter = 0;

      while (path.open.length !== 0) {
        // console.log("1-1 in open", path.open.indexOf("1-1") > -1);
        if (path.currentNode === stopNode) {
          console.log("Found stop node. Stoping now.....");
          break;
        } else if (path.open.length === 0) {
          console.log("All options exhausted......");
          break;
        } else {
          // call the backend to get the next state to be shown
          const response = await fetch(
            `http://localhost:8080/api/pathState/${idCounter}`,
            {
              method: "PUT",
              headers: { "Content-Type": "application/json;charset=UTF-8" },
              body: JSON.stringify(path),
            }
          );

          const data = await response.json();
          console.log(`Received next path state ${idCounter}: `, data);
          setPath(data);
          idCounter++;
        }
      }
      if (path.open.length === 0) console.log("No path found");
    } catch (err) {
      console.log("Error: ", err);
    }
  };

  let gridelement = [];
  for (let i = 0; i < 14; i++) {
    for (let j = 0; j < 16; j++) {
      const nodeVal = `${i.toString()}-${j.toString()}`;

      gridelement.push(
        <Node
          key={nodeVal}
          id={nodeVal}
          nodeType={
            nodeVal === startNode
              ? "key-start"
              : nodeVal === stopNode
              ? "key-stop"
              : path.open.indexOf(nodeVal) > -1
              ? "key-visited"
              : path.closed.indexOf(nodeVal) > -1
              ? "key-final-path"
              : "key"
          }
          onClick={handleId}
        />
      );
    }
  }

  return (
    <div className="container">
      <div className="key-container">
        {gridelement}
        <button id="find_path" onClick={getPath} className="key-path ">
          Find Path
        </button>
      </div>
    </div>
  );
};

export default Wrapper;
