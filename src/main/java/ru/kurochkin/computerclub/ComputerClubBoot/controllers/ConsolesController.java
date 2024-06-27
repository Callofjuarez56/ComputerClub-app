package ru.kurochkin.computerclub.ComputerClubBoot.controllers;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kurochkin.computerclub.ComputerClubBoot.models.Consoles;
import ru.kurochkin.computerclub.ComputerClubBoot.models.Person;
import ru.kurochkin.computerclub.ComputerClubBoot.services.ConsolesService;
import ru.kurochkin.computerclub.ComputerClubBoot.services.PeopleService;

/**
 * @author Oleg Kurochkin
 */
@Controller
@RequestMapping("/consoles")
public class ConsolesController {

    private final PeopleService peopleService;
    private final ConsolesService consolesService;

    public ConsolesController( PeopleService peopleService, ConsolesService consolesService){
        this.peopleService = peopleService;
        this.consolesService = consolesService;
    }

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("consoles", consolesService.findAll());
        return "consoles/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("consoles", consolesService.findOne(id));

        Person consoleOwner = consolesService.getConsoleOwner(id);

        if (consoleOwner != null)
            model.addAttribute("person", consoleOwner);
        else
            model.addAttribute("people", peopleService.findAll());

        return "consoles/show";
    }

    @GetMapping("/new")
    public String newConsole(@ModelAttribute("console") Consoles consoles) {
        return "consoles/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("console") @Valid Consoles consoles,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "consoles/new";

        consolesService.save(consoles);
        return "redirect:/consoles/index";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("console", consolesService.findOne(id));
        return "consoles/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("console") @Valid Consoles consoles, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "consoles/edit";

        consolesService.update(id, consoles);
        return "redirect:/consoles/index";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        consolesService.delete(id);
        return "redirect:/consoles/index";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id) {
        consolesService.release(id);
        return "redirect:/consoles/" + id;
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("person") Person selectedPerson) {
        // У selectedPerson назначено только поле id, остальные поля - null
        consolesService.assign(id, selectedPerson);
        return "redirect:/consoles/" + id;
    }
}
