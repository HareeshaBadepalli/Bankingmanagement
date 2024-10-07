package com.example.demo.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;

	@Entity
	@Table(name = "accounts")
	public class Account {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long accountNumber; // Unique identifier for the account

	    @Column(nullable = false)
	    private String fullName; // Account holder's full name

	    @Column(nullable = false)
	    private String email; // Account holder's email

	    @Column(nullable = false)
	    private BigDecimal balance; // Account balance

	    @Column(nullable = false)
	    private String securityPin; // Security PIN for the account

	    @Column(nullable = false)
	    private LocalDateTime createdAt; // Timestamp when the account was created

	    @Column(nullable = false)
	    private LocalDateTime lastUsed; // Timestamp of the last account activity

	    // Getters and Setters
	    public Long getAccountNumber() {
	        return accountNumber;
	    }

	    public void setAccountNumber(Long accountNumber) {
	        this.accountNumber = accountNumber;
	    }

	    public String getFullName() {
	        return fullName;
	    }

	    public void setFullName(String fullName) {
	        this.fullName = fullName;
	    }

	    public String getEmail() {
	        return email;
	    }

	    public void setEmail(String email) {
	        this.email = email;
	    }

	    public BigDecimal getBalance() {
	        return balance;
	    }

	    public void setBalance(BigDecimal balance) {
	        this.balance = balance;
	    }

	    public String getSecurityPin() {
	        return securityPin;
	    }

	    public void setSecurityPin(String securityPin) {
	        this.securityPin = securityPin;
	    }

	    public LocalDateTime getCreatedAt() {
	        return createdAt;
	    }

	    public void setCreatedAt(LocalDateTime createdAt) {
	        this.createdAt = createdAt;
	    }

	    public LocalDateTime getLastUsed() {
	        return lastUsed;
	    }

	    public void setLastUsed(LocalDateTime lastUsed) {
	        this.lastUsed = lastUsed;
	    }
	}