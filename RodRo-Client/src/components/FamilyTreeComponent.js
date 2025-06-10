import React, { useEffect, useRef, useState } from "react";
import { useParams } from "react-router-dom";
import { loadFamilyTree } from "../utils/familyTree";
import * as d3 from "d3";

const FamilyTreeComponent = () => {
  const { id } = useParams();
  const treeRef = useRef();
  const [error, setError] = useState(null);

  useEffect(() => {
    loadFamilyTree(id)
      .then(treeData => renderTree(treeData))
      .catch(err => setError(err.message));
  }, [id]);

  const renderTree = (data) => {
    d3.select(treeRef.current).selectAll("*").remove(); // clear previous

    const width = 1000;
    const height = 600;

    const root = d3.hierarchy(data, d => d.parents);
    const treeLayout = d3.tree().size([width, height - 200]);
    treeLayout(root);

    const svg = d3.select(treeRef.current)
      .append("svg")
      .attr("width", width)
      .attr("height", height);

    const g = svg.append("g").attr("transform", "translate(50,50)");

    // links
    g.selectAll(".link")
      .data(root.links())
      .enter()
      .append("line")
      .attr("class", "link")
      .attr("stroke", "#ccc")
      .attr("x1", d => d.source.x)
      .attr("y1", d => d.source.y)
      .attr("x2", d => d.target.x)
      .attr("y2", d => d.target.y);

    // nodes
    const node = g.selectAll(".node")
      .data(root.descendants())
      .enter()
      .append("g")
      .attr("class", "node")
      .attr("transform", d => `translate(${d.x},${d.y})`);

    node.append("circle")
      .attr("r", 20)
      .attr("fill", d => d.data.className === "female" ? "#f99" : "#99f");

    node.append("text")
      .attr("dy", -30)
      .attr("text-anchor", "middle")
      .text(d => d.data.name);
  };

  return (
    <div className="container mt-4">
      <h2>Family Tree</h2>
      {error ? (
        <div className="alert alert-danger">Error: {error}</div>
      ) : (
        <div ref={treeRef}></div>
      )}
    </div>
  );
};

export default FamilyTreeComponent;
