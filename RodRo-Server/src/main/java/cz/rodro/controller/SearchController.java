package cz.rodro.controller;

import cz.rodro.dto.GlobalSearchResultsDTO;
import cz.rodro.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping("/search")
    public GlobalSearchResultsDTO globalSearch(@RequestParam("q") String query) {
        return searchService.globalSearch(query);
    }
}
