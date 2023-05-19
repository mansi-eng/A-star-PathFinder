import React, { Component } from "react";
import "./App.css";
import Wrapper from "./components/Wrapper";

class App extends Component {
  render() {
    return (
      <div className="App">
        <h2 className="app-header">A-Star path Visualiser</h2>
        <Wrapper />
      </div>
    );
  }
}

export default App;
