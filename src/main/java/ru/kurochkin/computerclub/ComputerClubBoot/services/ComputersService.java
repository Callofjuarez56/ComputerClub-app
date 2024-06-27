package ru.kurochkin.computerclub.ComputerClubBoot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kurochkin.computerclub.ComputerClubBoot.models.Computers;
import ru.kurochkin.computerclub.ComputerClubBoot.models.Person;
import ru.kurochkin.computerclub.ComputerClubBoot.repositories.ComputersRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Oleg Kurochkin
 */
@Service
@Transactional(readOnly = true)
public class ComputersService {
    private final ComputersRepository computersRepository;

    @Autowired
    public ComputersService(ComputersRepository computersRepository) {
        this.computersRepository = computersRepository;
    }

    public List<Computers> findAll() {
        if (true)
            return computersRepository.findAll(Sort.by("computerNumber"));
        else
            return computersRepository.findAll();
    }


    public Computers findOne(int id) {
        Optional<Computers> foundComputer = computersRepository.findById(id);
        return foundComputer.orElse(null);
    }


    @Transactional
    public void save(Computers computers) {
        computers.setCreatedAt(new Date());
        computersRepository.save(computers);
    }

    @Transactional
    public void update(int id, Computers updatedComputer) {
        Computers computersToBeUpdated = computersRepository.findById(id).get();

        // добавляем новый компьютер (которая не находится в Persistence context), поэтому нужен save()
        updatedComputer.setId(id);
        updatedComputer.setPerson(computersToBeUpdated.getPerson()); // чтобы не терялась связь при обновлении
        updatedComputer.setCreatedAt(new Date());

        computersRepository.save(updatedComputer);
    }

    @Transactional
    public void delete(int id) {
        computersRepository.deleteById(id);
    }

    // Returns null if computers has no owner
    public Person getComputerOwner(int id) {
        // Здесь Hibernate.initialize() не нужен, так как владелец (сторона One) загружается не лениво
        return computersRepository.findById(id).map(Computers::getPerson).orElse(null);
    }

    // Освбождает computer (этот метод вызывается, когда человек освобождает компьютер)
    @Transactional
    public void release(int id) {
        computersRepository.findById(id).ifPresent(
                computers -> {
                    computers.setPerson(null);
                    computers.setBusy(false);
                });
    }

    // Назначает компьютер человеку (этот метод вызывается, когда человек забирает книгу из библиотеки)
    @Transactional
    public void assign(int id, Person selectedPerson) {
        computersRepository.findById(id).ifPresent(
                computers -> {
                    computers.setPerson(selectedPerson);
                    computers.setCreatedAt(new Date()); // текущее время
                }
        );
    }
}
