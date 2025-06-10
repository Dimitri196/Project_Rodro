// src/utils/familyTree.js
import { apiGet } from "../utils/api";

function buildPersonMap(persons) {
  const map = {};
  persons.forEach(p => {
    map[p.id] = {
      ...p,
      children: [],
      parents: [],
      spouses: [],
    };
  });

  persons.forEach(p => {
    p.marriages.forEach(m => {
      const spouse = map[m.spouse];
      if (spouse && !map[p.id].spouses.includes(spouse)) {
        map[p.id].spouses.push(spouse);
      }

      m.children.forEach(childId => {
        if (map[childId]) {
          map[p.id].children.push(map[childId]);
          map[childId].parents.push(map[p.id]);
        }
      });
    });
  });

  return map;
}

function buildCombinedTree(map, rootId, visited = new Set()) {
  if (!map[rootId] || visited.has(rootId)) return null;
  visited.add(rootId);

  const person = map[rootId];

  return {
    id: person.id,
    name: person.name,
    extra: person.extra,
    className: person.className,
    textClass: person.textClass,
    spouses: person.spouses.map(s => ({ id: s.id, name: s.name, isSpouse: true })),
    children: person.children
      .map(child => buildCombinedTree(map, child.id, visited))
      .filter(Boolean),
    parents: person.parents
      .map(parent => buildCombinedTree(map, parent.id, visited))
      .filter(Boolean),
  };
}

export async function loadFamilyTree(personId) {
  const data = await apiGet(`/api/family-tree/${personId}`);
  const map = buildPersonMap(data);
  return buildCombinedTree(map, personId.toString());
}
