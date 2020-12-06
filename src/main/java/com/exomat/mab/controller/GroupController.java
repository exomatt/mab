package com.exomat.mab.controller;

import com.exomat.mab.model.Group;
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
public class GroupController {
    private final UserService userService;
    private final GroupService groupService;


    public GroupController(UserService userService, GroupService groupService) {
        this.userService = userService;
        this.groupService = groupService;
    }


    @GetMapping("/createGroup")
    public String showSignUpForm(Group group) {
        return "add-group";
    }

    @PostMapping("/addgroup")
    public String addGroup(@Valid Group group, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-group";
        }

        groupService.save(group);
        return "redirect:/";
    }

    @GetMapping("/editGroup/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Group group = groupService.findById(id);
        model.addAttribute("group", group);
        return "update-group";
    }


    @PostMapping("/updateGroup/{id}")
    public String updateGroup(@PathVariable("id") long id, @Valid Group group, BindingResult result, Model model) {
        if (result.hasErrors()) {
            group.setId(id);
            return "update-group";
        }

        groupService.save(group);
        model.addAttribute("users", userService.findAll());
        model.addAttribute("groups", groupService.findAll());
        return "redirect:/";
    }
}
