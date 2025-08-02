export function normalizeString(str) {
  if (!str) return "";
  return str
    .normalize("NFD")                     // Decompose Unicode characters
    .replace(/[\u0300-\u036f]/g, "")     // Remove diacritic marks
    .toLowerCase();                      // Case-insensitive search
}