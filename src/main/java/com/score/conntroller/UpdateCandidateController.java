package com.score.conntroller;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.score.model.Candidate;
import com.score.repository.CandidateRepository;

@Controller
@RequestMapping("/updateCandidate")
public class UpdateCandidateController {

    private final CandidateRepository candidateRepository;

    public UpdateCandidateController(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    @PostMapping
    @Transactional
    public String updateCandidate(@RequestParam Long id,
                                  @RequestParam String name,
                                  @RequestParam String email,
                                  @RequestParam int score) {
        try {
            Candidate candidate = candidateRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid candidate ID: " + id));
            
            // Update candidate details
            candidate.setName(name);
            candidate.setEmail(email);
            candidate.setScore(score);
            
            // Save the updated candidate
            candidateRepository.save(candidate);
        } catch (Exception e) {
            // Handle exception
            e.printStackTrace();
            // You can add logging or custom error handling here
            return "error"; // Return an error view if an exception occurs
        }

        return "redirect:/trainer"; // Redirect to the candidates page after updating
    }
}
