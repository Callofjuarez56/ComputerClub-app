package ru.kurochkin.computerclub.ComputerClubBoot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kurochkin.computerclub.ComputerClubBoot.models.Computers;

/**
 * @author Oleg Kurochkin
 */
@Repository
public interface ComputersRepository extends JpaRepository<Computers, Integer>{
}
