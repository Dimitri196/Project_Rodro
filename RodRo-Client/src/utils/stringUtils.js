import { remove as removeDiacritics } from "diacritics";

export function normalizeString(str) {
  if (!str) return "";
  return removeDiacritics(str).toLowerCase().trim();
}