package com.score.conntroller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.score.model.Candidate;
import com.score.repository.CandidateRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class CandidateController {

@Autowired
private CandidateRepository candidateRepository;

@GetMapping("/candidate/register")
public String showCandidateRegistrationForm(Model model) {
   model.addAttribute("candidate", new Candidate());
   return "candidate_registration";
}

@PostMapping("/candidate/register")
public String processCandidateRegistration(@ModelAttribute("candidate") Candidate candidate, Model model) {
    
    Candidate existingCandidate = candidateRepository.findByEmail(candidate.getEmail());
    if (existingCandidate != null) {
        
        model.addAttribute("warning", "Email already exists. Please use a different email.");
        return "candidate_registration"; 
    }
    
    
    candidateRepository.save(candidate);
    
    return "redirect:/";
}


@GetMapping("/candidate/login")
public String showCandidateLoginForm() {
   return "candidate_login";
}

@PostMapping("/candidate/login")
public String processCandidateLogin(@RequestParam("email") String email,
                                    @RequestParam("password") String password,
                                    HttpSession session,
                                    Model model) {
    Candidate candidate = candidateRepository.findByEmailAndPassword(email, password);
    if (candidate != null) {
        session.setAttribute("candidateId", candidate.getId());
        model.addAttribute("candidate", candidate);
        return "candidate_dashboard";
    } else {
        model.addAttribute("error", "Invalid email or password");
        return "candidate_login";
    }
}

@PostMapping("/logout")
public String logout(HttpSession session) {
    session.invalidate();
    return "redirect:/";
}
@GetMapping("/editCandidate")
public String editCandidate(@RequestParam Long id, Model model) {
    Optional<Candidate> optionalCandidate = candidateRepository.findById(id);
    if (optionalCandidate.isPresent()) {
        Candidate candidate = optionalCandidate.get();
        model.addAttribute("candidate", candidate);
        return "edit_candidates"; 
    } else {
        
        model.addAttribute("errorMessage", "Candidate with ID " + id + " not found");
        return "error"; 
    }
}
}



