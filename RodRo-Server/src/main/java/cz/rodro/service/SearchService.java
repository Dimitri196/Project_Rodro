package cz.rodro.service;

import cz.rodro.dto.GlobalSearchResultsDTO;

public interface SearchService {
    GlobalSearchResultsDTO globalSearch(String query);
}
