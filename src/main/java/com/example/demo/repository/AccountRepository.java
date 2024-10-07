package com.example.demo.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Account;


@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findByLastUsedBefore(LocalDateTime dateTime);
    void deleteByLastUsedBefore(LocalDateTime dateTime);
}