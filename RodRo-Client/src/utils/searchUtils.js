import { normalizeString } from "./stringUtils";

/**
 * Normalize a query string and return as a URL param-ready string
 */
export function buildSearchQuery(rawQuery) {
  return encodeURIComponent(normalizeString(rawQuery));
}