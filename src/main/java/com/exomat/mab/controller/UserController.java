package com.exomat.mab.controller;

import com.exomat.mab.model.User;
import com.exomat.mab.service.GroupService;
import com.exomat.mab.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class UserController {
    private final UserService userService;
    private final GroupService groupService;


    public UserController(UserService userService, GroupService groupService) {
        this.userService = userService;
        this.groupService = groupService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("groups", groupService.findAll());
        return "index";
    }

    @GetMapping("/signup")
    public String showSignUpForm(User user, Model model) {
        model.addAttribute("groups", groupService.findAll());
        return "add-user";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        model.addAttribute("groups", groupService.findAll());
        return "update-user";
    }


    @PostMapping("/adduser")
    public String addUser(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-user";
        }

        userService.save(user);
        model.addAttribute("users", userService.findAll());
        return "redirect:/";
    }


    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, @Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            user.setId(id);
            return "update-user";
        }

        userService.save(user);
        model.addAttribute("users", userService.findAll());
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        User user = userService.findById(id);
        userService.deleteUser(user.getUsername());
        model.addAttribute("users", userService.findAll());
        return "index";
    }

}
