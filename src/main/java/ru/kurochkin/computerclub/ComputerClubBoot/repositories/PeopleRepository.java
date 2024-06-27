package ru.kurochkin.computerclub.ComputerClubBoot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kurochkin.computerclub.ComputerClubBoot.models.Person;

import java.util.Optional;

/**
 * @author Oleg Kurochkin
 */
@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
    Optional<Person> findByName(String name);
}
