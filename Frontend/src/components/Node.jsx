import React from "react";

const Node = ({ id, nodeType, onClick }) => {
  return <button id={id} className={nodeType} onClick={onClick}></button>;
};

export default Node;
