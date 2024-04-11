package com.score.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.score.model.Trainer;

public interface TrainerRepository extends JpaRepository<Trainer, Long> {

	Trainer findByEmailAndPassword(String email, String password);

	boolean existsByEmail(String email);
}