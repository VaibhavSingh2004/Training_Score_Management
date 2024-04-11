package com.score.conntroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.score.model.Candidate;
import com.score.model.Trainer;
import com.score.repository.CandidateRepository;
import com.score.repository.TrainerRepository;
import com.score.service.CandidateService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class TrainerController {
	
	private final CandidateService candidateService = new CandidateService();
    
    @Autowired
    private TrainerRepository trainerRepository;
    
    @Autowired
    private CandidateRepository candidateRepository;
    
    @GetMapping("/trainer/register")
    public String showTrainerRegistrationForm(Model model) {
        model.addAttribute("trainer", new Trainer());
        return "trainer_registration";
    }

    @PostMapping("/trainer/register")
    public String processTrainerRegistration(@ModelAttribute("trainer") Trainer trainer, Model model) {
        if (trainerRepository.existsByEmail(trainer.getEmail())) {
            model.addAttribute("error", "Email is already registered");
            return "trainer_registration";
        }

        trainerRepository.save(trainer);
        return "redirect:/trainer/login"; 
    }
    @GetMapping("/trainer/login")
    public String showTrainerLoginForm(HttpSession session, Model model) {
        
        String trainerEmail = (String) session.getAttribute("trainer_email");
        if (trainerEmail != null) {
            
            List<Candidate> candidates = candidateRepository.findByTrainerEmail(trainerEmail);
            
            model.addAttribute("candidates", candidates);
            return "trainer_dashboard"; 
        } else {
            
            return "trainer_login";
        }
    }
    @PostMapping("/trainer/login")
    public String processTrainerLogin(@RequestParam("email") String email,
                                      @RequestParam("password") String password,
                                      HttpSession session,
                                      Model model) {
        
        Trainer trainer = trainerRepository.findByEmailAndPassword(email, password);
        if (trainer != null) {
           
            session.setAttribute("trainer_email", trainer.getEmail());
            
           
            List<Candidate> candidates = candidateRepository.findByTrainerEmail(email);
            
            model.addAttribute("candidates", candidates);
            
            return "trainer_dashboard"; 
        } else {
            
            model.addAttribute("error", "Invalid email or password");
            return "trainer_login";
        }
    }
    
    @PostMapping("/t_logout")
    public String logout(HttpSession session) {
        
        session.invalidate();
        
        
        return "redirect:/trainer/login"; 
    }
    @PostMapping("/update-score")
    public String updateCandidateScore(@RequestParam("candidateId") Long candidateId,
                                       @RequestParam("score") int score,
                                       HttpServletRequest request,
                                       HttpSession session) {
        
        Candidate candidate = candidateRepository.findById(candidateId).orElse(null);

        
        if (candidate != null) {
            candidate.setScore(score);
            candidateRepository.save(candidate);
        }

        return "redirect:/trainer/login";
    }

    }
    
    

