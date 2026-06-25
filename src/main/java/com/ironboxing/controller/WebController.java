package com.ironboxing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.ironboxing.service.AtletaService;
import com.ironboxing.service.TreinadorService;
import com.ironboxing.service.PlanoService;
import com.ironboxing.service.TurmaService;

@Controller
public class WebController {

    @Autowired
    private AtletaService atletaService;

    @Autowired
    private TreinadorService treinadorService;

    @Autowired
    private PlanoService planoService;

    @Autowired
    private TurmaService turmaService;

    private boolean isAuthenticated() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken);
    }

    private String getUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            return auth.getName();
        }
        return "Usuário";
    }

    @GetMapping("/")
    public String index() {
        if (isAuthenticated()) {
            return "redirect:/dashboard";
        }
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        if (isAuthenticated()) {
            return "redirect:/dashboard";
        }
        return "login";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        if (!isAuthenticated()) {
            return "redirect:/login";
        }
        
        model.addAttribute("username", getUsername());
        model.addAttribute("totalAtletas", atletaService.listarAtletas().size());
        model.addAttribute("totalTreinadores", treinadorService.listarTreinadores().size());
        model.addAttribute("totalPlanos", planoService.listarPlanos().size());
        model.addAttribute("totalTurmas", turmaService.listarTurmas().size());
        
        return "dashboard";
    }

    @GetMapping("/atletas")
    public String atletas(Model model) {
        if (!isAuthenticated()) {
            return "redirect:/login";
        }
        
        model.addAttribute("username", getUsername());
        model.addAttribute("atletas", atletaService.listarAtletas());
        
        return "atletas";
    }
}
