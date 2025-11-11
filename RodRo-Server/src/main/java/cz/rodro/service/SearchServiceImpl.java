package cz.rodro.service;

import cz.rodro.dto.GlobalSearchResultsDTO;
import cz.rodro.dto.mapper.LocationMapper;
import cz.rodro.dto.mapper.ParishMapper;
import cz.rodro.dto.mapper.PersonMapper;
import cz.rodro.entity.LocationEntity;
import cz.rodro.entity.ParishEntity;
import cz.rodro.entity.PersonEntity;
import cz.rodro.entity.repository.LocationRepository;
import cz.rodro.entity.repository.ParishRepository;
import cz.rodro.entity.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private ParishRepository parishRepository;

    @Autowired
    private PersonMapper personMapper;

    @Autowired
    private LocationMapper locationMapper;

    @Autowired
    private ParishMapper parishMapper;

    @Override
    public GlobalSearchResultsDTO globalSearch(String query) {
        if (query == null || query.isBlank()) {
            return new GlobalSearchResultsDTO();
        }

        String normalizedQuery = normalizeQuery(query);

        List<PersonEntity> persons = personRepository.findByNormalizedSearchTerm(normalizedQuery);
        List<LocationEntity> locations = locationRepository.findByNormalizedLocationName(normalizedQuery);
        List<ParishEntity> parishes = parishRepository.findByNormalizedName(normalizedQuery);

         GlobalSearchResultsDTO results = new GlobalSearchResultsDTO();
        results.setPersons(persons.stream().map(personMapper::toDTO).collect(Collectors.toList()));
        results.setLocations(locations.stream().map(locationMapper::toLocationDTO).collect(Collectors.toList()));
        results.setParishes(parishes.stream().map(parishMapper::toParishDTO).collect(Collectors.toList()));

        return results;
    }

    /**
     * Normalize query for universal search: lowercase + replace common diacritics.
     * Add more replacements as needed for your target languages.
     */
    private String normalizeQuery(String query) {
        if (query == null) return "";
        return query.toLowerCase()
                .replace('ł', 'l')
                .replace('Ł', 'L')
                .replace('é', 'e')
                .replace('è', 'e')
                .replace('ë', 'e')
                .replace('á', 'a')
                .replace('à', 'a')
                .replace('ä', 'a')
                .replace('ó', 'o')
                .replace('ò', 'o')
                .replace('ö', 'o')
                .replace('í', 'i')
                .replace('ì', 'i')
                .replace('ï', 'i')
                .replace('ú', 'u')
                .replace('ù', 'u')
                .replace('ü', 'u')
                .trim();
    }
}
