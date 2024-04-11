package com.score.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.score.model.Candidate;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {

//	List<Candidate> findByTrainerId(Long id);

	Candidate findByEmailAndPassword(String email, String password);

	List<Candidate> findByTrainerEmail(String email);

	Candidate findByEmail(String email);
}