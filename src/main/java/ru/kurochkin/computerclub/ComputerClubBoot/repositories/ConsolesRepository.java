package ru.kurochkin.computerclub.ComputerClubBoot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kurochkin.computerclub.ComputerClubBoot.models.Consoles;

/**
 * @author Oleg Kurochkin
 */
@Repository
public interface ConsolesRepository extends JpaRepository<Consoles, Integer> {
}
