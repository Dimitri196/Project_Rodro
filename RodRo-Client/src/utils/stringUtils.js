export const normalizeString = (str = "") =>
  str
    .toLowerCase()
    .normalize("NFD")
    .replace(/[\u0300-\u036f]/g, "")
    .replace(/ł/g, "l")
    .replace(/ń/g, "n")
    .replace(/ś/g, "s")
    .replace(/ź/g, "z")
    .replace(/ż/g, "z")
    .replace(/ć/g, "c")
    .replace(/ą/g, "a")
    .replace(/ę/g, "e")
    .replace(/ó/g, "o");