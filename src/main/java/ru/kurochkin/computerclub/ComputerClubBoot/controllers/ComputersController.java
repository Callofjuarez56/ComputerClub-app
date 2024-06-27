package ru.kurochkin.computerclub.ComputerClubBoot.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kurochkin.computerclub.ComputerClubBoot.models.Computers;
import ru.kurochkin.computerclub.ComputerClubBoot.models.Person;
import ru.kurochkin.computerclub.ComputerClubBoot.services.ComputersService;
import ru.kurochkin.computerclub.ComputerClubBoot.services.PeopleService;

/**
 * @author Oleg Kurochkin
 */
@Controller
@RequestMapping("/computers")
public class ComputersController {
    //private final BooksService booksService;
    private final PeopleService peopleService;
    private final ComputersService computersService;


    @Autowired
    public ComputersController(ComputersService computersService, PeopleService peopleService) {
        this.computersService = computersService;
        this.peopleService = peopleService;
    }

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("computers", computersService.findAll());
        return "computers/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("computers", computersService.findOne(id));

        Person computerOwner = computersService.getComputerOwner(id);

        if (computerOwner != null)
            model.addAttribute("person", computerOwner);
        else
            model.addAttribute("people", peopleService.findAll());

        return "computers/show";
    }

    @GetMapping("/new")
    public String newComputer(@ModelAttribute("computer") Computers computers) {
        return "computers/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("computer") @Valid Computers Computer,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "computers/new";

        computersService.save(Computer);
        return "redirect:/computers/index";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("computer", computersService.findOne(id));
        return "computers/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("computer") @Valid Computers computers, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "computers/edit";

        computersService.update(id, computers);
        return "redirect:/computers/index";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        computersService.delete(id);
        return "redirect:/computers/index";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id) {
        computersService.release(id);
        return "redirect:/computers/" + id;
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("person") Person selectedPerson) {
        // У selectedPerson назначено только поле id, остальные поля - null
        computersService.assign(id, selectedPerson);
        return "redirect:/computers/" + id;
    }
//
//    @GetMapping("/search")
//    public String searchPage() {
//        return "books/search";
//    }
//
//    @PostMapping("/search")
//    public String makeSearch(Model model, @RequestParam("query") String query) {
//        model.addAttribute("books", booksService.searchByTitle(query));
//        return "books/search";
//    }

}
