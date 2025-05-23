package com.iftm.client.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iftm.client.entities.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByNameIgnoreCase(String nome);
    List<Client> findByNameContainingIgnoreCase(String nome);
    List<Client> findByIncomeGreaterThan(Double income);
    List<Client> findByChildrenLessThan(Integer children);
    List<Client> findByBirthDateBetween(java.time.Instant start, java.time.Instant end);
    Optional<Client> findByNameIgnoreCaseAndCpf(String name, String cpf);
}
