package mareczek100.musiccontests.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static mareczek100.musiccontests.api.controller.AdminController.ADMIN_MAIN_PAGE;

@Controller
@RequestMapping(ADMIN_MAIN_PAGE)
@AllArgsConstructor
public class AdminController {

    public static final String ADMIN_MAIN_PAGE = "/admin";

    @GetMapping
    public String adminHomePage(){

        return "admin";
    }
}
