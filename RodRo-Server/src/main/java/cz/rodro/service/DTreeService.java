package cz.rodro.service;

import cz.rodro.dto.DTreeNodeDTO;
import cz.rodro.dto.FamilyDTO;
import cz.rodro.dto.MarriageDTO;
import cz.rodro.dto.PersonDTO;
import cz.rodro.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DTreeService {

    private final PersonService personService;
    private final FamilyService familyService;

    public DTreeService(PersonService personService, FamilyService familyService) {
        this.personService = personService;
        this.familyService = familyService;
    }

    public List<DTreeNodeDTO> generateDTree() {
        List<PersonDTO> persons = personService.getAll();
        List<FamilyDTO> families = familyService.getAllFamilies();
        return buildDTreeStructure(persons, families);
    }

    public List<DTreeNodeDTO> generateDTreeForPerson(Long personId) {
        PersonDTO rootPerson = personService.getPerson(personId);
        if (rootPerson == null) {
            throw new NotFoundException("Person with id " + personId + " not found");
        }

        Set<PersonDTO> relevantPersons = new HashSet<>();
        Set<FamilyDTO> relevantFamilies = new HashSet<>();

        List<PersonDTO> allPersons = personService.getAll();

        collectAncestors(rootPerson, relevantPersons, relevantFamilies, 10);
        collectDescendants(rootPerson, relevantPersons, relevantFamilies, 10, allPersons);
        collectSiblingsAndSpouses(rootPerson, relevantPersons, relevantFamilies);

        return buildDTreeStructure(new ArrayList<>(relevantPersons), new ArrayList<>(relevantFamilies));
    }

    private List<DTreeNodeDTO> buildDTreeStructure(List<PersonDTO> persons, List<FamilyDTO> families) {
        Map<Long, DTreeNodeDTO> nodesById = new HashMap<>();

        for (PersonDTO p : persons) {
            DTreeNodeDTO node = new DTreeNodeDTO();
            node.setId(p.getId().toString());
            node.setName(p.getGivenName() + " " + p.getGivenSurname());
            node.setClassName(p.getGender() != null ? p.getGender().toString().toLowerCase() : "unknown");

            String birth = p.getBirthDate() != null ? p.getBirthDate().toString() : "";
            String death = p.getDeathDate() != null ? p.getDeathDate().toString() : "";
            String extra = "";

            if (!birth.isEmpty()) extra += "b. " + birth;
            if (!death.isEmpty()) extra += (extra.isEmpty() ? "" : ", ") + "d. " + death;

            node.setExtra(extra.isEmpty() ? null : extra);

            nodesById.put(p.getId(), node);
        }

        for (FamilyDTO family : families) {
            String maleId = family.getSpouseMale() != null ? family.getSpouseMale().getId().toString() : null;
            String femaleId = family.getSpouseFemale() != null ? family.getSpouseFemale().getId().toString() : null;

            if (maleId == null || femaleId == null) continue;

            MarriageDTO marriageForMale = new MarriageDTO();
            marriageForMale.setSpouse(femaleId);
            marriageForMale.setChildren(findChildrenIds(family, persons));

            MarriageDTO marriageForFemale = new MarriageDTO();
            marriageForFemale.setSpouse(maleId);
            marriageForFemale.setChildren(findChildrenIds(family, persons));

            DTreeNodeDTO maleNode = nodesById.get(Long.valueOf(maleId));
            if (maleNode != null) {
                maleNode.getMarriages().add(marriageForMale);
            }

            DTreeNodeDTO femaleNode = nodesById.get(Long.valueOf(femaleId));
            if (femaleNode != null) {
                femaleNode.getMarriages().add(marriageForFemale);
            }
        }

        return new ArrayList<>(nodesById.values());
    }

    private List<String> findChildrenIds(FamilyDTO family, List<PersonDTO> allPersons) {
        Long fatherId = family.getSpouseMale() != null ? family.getSpouseMale().getId() : null;
        Long motherId = family.getSpouseFemale() != null ? family.getSpouseFemale().getId() : null;

        if (fatherId == null || motherId == null) return Collections.emptyList();

        return allPersons.stream()
                .filter(p -> p.getFather() != null && p.getMother() != null
                        && p.getFather().getId().equals(fatherId)
                        && p.getMother().getId().equals(motherId))
                .map(p -> p.getId().toString())
                .collect(Collectors.toList());
    }

    private void collectAncestors(PersonDTO person, Set<PersonDTO> persons, Set<FamilyDTO> families, int generationsLeft) {
        if (person == null || generationsLeft == 0 || persons.contains(person)) return;

        persons.add(person);

        if (person.getFather() != null) {
            collectAncestors(person.getFather(), persons, families, generationsLeft - 1);
        }
        if (person.getMother() != null) {
            collectAncestors(person.getMother(), persons, families, generationsLeft - 1);
        }

        families.addAll(familyService.findFamiliesByChild(person.getId()));
    }

    private void collectDescendants(PersonDTO person, Set<PersonDTO> persons, Set<FamilyDTO> families, int generationsLeft, List<PersonDTO> allPersons) {
        if (person == null || generationsLeft == 0) return;

        if (!persons.contains(person)) {
            persons.add(person);
        }

        List<FamilyDTO> familiesOfPerson = familyService.getSpouses(person.getId());
        families.addAll(familiesOfPerson);

        for (FamilyDTO family : familiesOfPerson) {
            List<PersonDTO> children = findChildrenInFamily(family, allPersons);
            for (PersonDTO child : children) {
                collectDescendants(child, persons, families, generationsLeft - 1, allPersons);
            }
        }
    }

    private void collectSiblingsAndSpouses(PersonDTO person, Set<PersonDTO> persons, Set<FamilyDTO> families) {
        if (person == null) return;

        persons.add(person);

        List<PersonDTO> siblings = personService.getSiblings(person.getId());
        persons.addAll(siblings);

        List<FamilyDTO> familiesOfPerson = familyService.getSpouses(person.getId());
        families.addAll(familiesOfPerson);

        for (FamilyDTO family : familiesOfPerson) {
            Long spouseId = null;
            if (family.getSpouseMale() != null && family.getSpouseMale().getId().equals(person.getId())) {
                spouseId = family.getSpouseFemale() != null ? family.getSpouseFemale().getId() : null;
            } else if (family.getSpouseFemale() != null && family.getSpouseFemale().getId().equals(person.getId())) {
                spouseId = family.getSpouseMale() != null ? family.getSpouseMale().getId() : null;
            }
            if (spouseId != null) {
                PersonDTO spouse = personService.getPerson(spouseId);
                if (spouse != null) {
                    persons.add(spouse);
                }
            }
        }
    }

    private List<PersonDTO> findChildrenInFamily(FamilyDTO family, List<PersonDTO> allPersons) {
        List<PersonDTO> children = new ArrayList<>();
        Long fatherId = family.getSpouseMale() != null ? family.getSpouseMale().getId() : null;
        Long motherId = family.getSpouseFemale() != null ? family.getSpouseFemale().getId() : null;

        if (fatherId == null || motherId == null) return children;

        for (PersonDTO p : allPersons) {
            if (p.getFather() != null && p.getMother() != null
                    && p.getFather().getId().equals(fatherId)
                    && p.getMother().getId().equals(motherId)) {
                children.add(p);
            }
        }
        return children;
    }
}
