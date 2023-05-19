import React from "react";
import Node from "./Node";

class Wrapper extends React.Component {
  constructor() {
    super();
    this.state = {
      nodeCounter: 0,
      startNode: "",
      stopNode: "",
      node_type: "key",
      pathState: { open: [], currentNode: "", closed: [] },
    };
    this.getPath = this.getPath.bind(this);
  }

  handleId = (e) => {
    if (this.state.nodeCounter < 2) {
      if (this.state.nodeCounter === 0) {
        this.setState({ startNode: e.target.id, node_type: "key-start" });
      } else {
        this.setState({
          stopNode: e.target.id,
          node_type: "key-stop",
          pathState: {
            ...this.pathState,
            open: [this.state.startNode],
            currentNode: this.state.startNode,
            closed: [this.state.startNode],
          },
        });
      }
    } else if (this.state.nodeCounter === 2) {
      fetch(`http://localhost:8080/api/startNodes/${this.state.nodeCounter}`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json;charset=UTF-8",
        },
        body: JSON.stringify({
          start: this.state.startNode,
          stop: this.state.stopNode,
        }),
      }).then((response) => console.log(response));
    }
    console.log(
      e.target.id +
        " button clicked " +
        this.state.startNode +
        " -- " +
        this.state.stopNode +
        "  |" +
        " count: " +
        this.state.nodeCounter
    );
    this.setState({ nodeCounter: this.state.nodeCounter + 1 });
  };

  async getPath() {
    try {
      console.log(
        "Start node: " +
          this.state.startNode +
          "  Stop node: " +
          this.state.stopNode
      );
      console.log(
        "Open: " +
          this.state.pathState.open +
          "  Current node: " +
          this.state.pathState.currentNode
      );

      var idCounter = 0;

      while (this.state.pathState.open.length !== 0) {
        // console.log("1-1 in open", this.state.pathState.open.indexOf("1-1") > -1);
        if (this.state.pathState.currentNode === this.state.stopNode) {
          console.log("Found stop node. Stoping now.....");
          break;
        } else if (this.state.pathState.open.length === 0) {
          console.log("All options exhausted......");
          break;
        } else {
          // call the backend to get the next state to be shown
          var response = await fetch(
            `http://localhost:8080/api/pathState/${idCounter}`,
            {
              method: "PUT",
              headers: { "Content-Type": "application/json;charset=UTF-8" },
              body: JSON.stringify(this.state.pathState),
            }
          );

          var data = await response.json();
          console.log(`Recieved next path state ${idCounter}: `, data);
          this.setState({ pathState: data });
          idCounter++;
        }
      }
      if (this.state.pathState.open.length === 0) console.log("No path found");
    } catch (err) {
      console.log("Error: ", err);
    }
  }

  render() {
    let gridelement = [];
    for (let i = 0; i < 14; i++) {
      for (let j = 0; j < 16; j++) {
        gridelement.push(
          <Node
            key={i.toString() + "-" + j.toString()}
            id={i.toString() + "-" + j.toString()}
            nodeType={
              i.toString() + "-" + j.toString() === this.state.startNode
                ? "key-start"
                : i.toString() + "-" + j.toString() === this.state.stopNode
                ? "key-stop"
                : this.state.pathState.open.indexOf(
                    i.toString() + "-" + j.toString()
                  ) > -1
                ? "key-visited"
                : this.state.pathState.closed.indexOf(
                    i.toString() + "-" + j.toString()
                  ) > -1
                ? "key-final-path"
                : "key"
            }
            onClick={this.handleId}
          />
        );
      }
    }

    return (
      <div className="container">
        <div className="key-container">
          {gridelement}
          <button id="find_path" onClick={this.getPath} className="key-path ">
            Find Path
          </button>
        </div>
      </div>
    );
  }
}

export default Wrapper;
