package com.score.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.score.model.Candidate;
import com.score.repository.CandidateRepository;

@Service
public class CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;

    public List<Candidate> getCandidatesByTrainerEmail(String trainerEmail) {
        return candidateRepository.findByTrainerEmail(trainerEmail);
    }

    public void updateCandidateScore(Long candidateId, int score) {
        Optional<Candidate> candidateOptional = candidateRepository.findById(candidateId);
        candidateOptional.ifPresent(candidate -> {
            candidate.setScore(score);
            candidateRepository.save(candidate);
        });
    }

	public List<Candidate> getCandidatesForLoggedInTrainer() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Candidate> getCandidatesByUserEmail(String userEmail) {
		// TODO Auto-generated method stub
		return null;
	}
}
