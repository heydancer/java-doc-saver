package com.heydancer.controller;

import com.heydancer.dto.MoverDTO;
import com.heydancer.entity.Mover;
import com.heydancer.entity.User;
import com.heydancer.service.MoverService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Log4j
@Controller
@RequestMapping("/movers")
@RequiredArgsConstructor
public class MoverController {
    private final MoverService moverService;

    @GetMapping
    public String search(@RequestParam(required = false, defaultValue = "") String lastName, Model model) {
        List<MoverDTO> movers;

        if (lastName != null && !lastName.isEmpty()) {
            log.info("Getting all movers");

            movers = moverService.getAllByLastName(lastName);
        } else {

            log.info("Getting movers by lastName");
            movers = moverService.getAllConfirmed();
        }

        model.addAttribute("movers", movers);
        model.addAttribute("lastName", lastName);

        return "mover";
    }

    @GetMapping("{mover}")
    public String moverEditForm(@PathVariable Mover mover, Model model) {
        List<Boolean> stats = new ArrayList<>();
        stats.add(true);
        stats.add(false);

        model.addAttribute("mover", mover);
        model.addAttribute("states", stats);

        return "moverEdit";
    }

    @PostMapping
    public String moveSave(@RequestParam String firstName,
                           @RequestParam String lastName,
                           @RequestParam String email,
                           @RequestParam String subdivision,
                           @RequestParam String link,
                           @RequestParam("moverId") Mover mover) {

        moverService.save(mover, firstName, lastName, email, link, subdivision);
        return "redirect:/movers";
    }

    @GetMapping("/request")
    public String requestList(Model model) {
        model.addAttribute("movers", moverService.getMoversInConfirmationStatus());
        return "request";
    }

    @PostMapping("{moverId}/request")
    public String changeRegistrationState(@PathVariable Long moverId, @RequestParam Boolean status) {
        log.info("Update registration state");

        moverService.changeRegistrationState(moverId, status);

        return "redirect:/movers/request";
    }


    @PostMapping("/distribution")
    public String replyAll(@AuthenticationPrincipal User user,
                           @RequestParam String text) {
        moverService.replyToAll(text);
        return "redirect:/movers";

    }
}
