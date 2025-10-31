package cz.rodro.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GlobalSearchResultsDTO {

        private List<PersonDTO> persons;
        private List<LocationDTO> locations;
        private List<ParishDTO> parishes;

}
