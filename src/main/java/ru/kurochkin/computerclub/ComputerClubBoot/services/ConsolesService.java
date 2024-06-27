package ru.kurochkin.computerclub.ComputerClubBoot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kurochkin.computerclub.ComputerClubBoot.models.Consoles;
import ru.kurochkin.computerclub.ComputerClubBoot.models.Person;
import ru.kurochkin.computerclub.ComputerClubBoot.repositories.ConsolesRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Oleg Kurochkin
 */
@Service
@Transactional(readOnly = true)
public class ConsolesService {
    private final ConsolesRepository consolesRepository;

    @Autowired
    public ConsolesService(ConsolesRepository consolesRepository) {
        this.consolesRepository = consolesRepository;
    }

    public List<Consoles> findAll() {
        if (true)
            return consolesRepository.findAll(Sort.by("consoleNumber"));
        else
            return consolesRepository.findAll();
    }


    public Consoles findOne(int id) {
        Optional<Consoles> foundConsole = consolesRepository.findById(id);
        return foundConsole.orElse(null);
    }


    @Transactional
    public void save(Consoles consoles) {
        consoles.setCreatedAt(new Date());
        consolesRepository.save(consoles);
    }

    @Transactional
    public void update(int id, Consoles updatedConsole) {
        Consoles consolesToBeUpdated = consolesRepository.findById(id).get();

        // добавляем новую консоль (которая не находится в Persistence context), поэтому нужен save()
        updatedConsole.setId(id);
        updatedConsole.setPerson(consolesToBeUpdated.getPerson()); // чтобы не терялась связь при обновлении
        updatedConsole.setCreatedAt(new Date());

        consolesRepository.save(updatedConsole);
    }

    @Transactional
    public void delete(int id) {
        consolesRepository.deleteById(id);
    }

    // Returns null if consoles has no owner
    public Person getConsoleOwner(int id) {
        // Здесь Hibernate.initialize() не нужен, так как владелец (сторона One) загружается не лениво
        return consolesRepository.findById(id).map(Consoles::getPerson).orElse(null);
    }

    // Освбождает консоль (этот метод вызывается, когда человек освобождает компьютер)
    @Transactional
    public void release(int id) {
        consolesRepository.findById(id).ifPresent(
                consoles -> {
                    consoles.setPerson(null);
                    consoles.setBusy(false);
                });
    }

    // Назначает консоль человеку (этот метод вызывается, когда человек забирает книгу из библиотеки)
    @Transactional
    public void assign(int id, Person selectedPerson) {
        consolesRepository.findById(id).ifPresent(
                consoles -> {
                    consoles.setPerson(selectedPerson);
                    consoles.setCreatedAt(new Date()); // текущее время
                }
        );
    }
}
