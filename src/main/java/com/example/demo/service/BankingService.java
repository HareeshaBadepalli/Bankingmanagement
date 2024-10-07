package com.example.demo.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Account;
import com.example.demo.model.User;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.UserRepository;

@Service
	public class BankingService {

	    @Autowired
	    private UserRepository userRepository;

	    @Autowired
	    private AccountRepository accountRepository;

	    // User Registration
	    public User registerUser(String email, String fullName, String password) {
	        if (userRepository.existsByEmail(email)) {
	            return null;
	        }

	        User user = new User();
	        user.setEmail(email);
	        user.setFullName(fullName);
	        user.setPassword(password); // Remember to hash the password
	        return userRepository.save(user);  // Save and return the User object
	    }

	    // User Login
	    public Optional<User> loginUser(String email, String password) {
	        return userRepository.findByEmailAndPassword(email, password);
	    }

	    // Create Account
	    @Transactional
	    public String createAccount(String email, String fullName, String securityPin) {
	        Account account = new Account();
	        account.setFullName(fullName);
	        account.setEmail(email);
	        account.setBalance(BigDecimal.ZERO); // Initial balance is zero
	        account.setSecurityPin(securityPin);
	        account.setCreatedAt(LocalDateTime.now());
	        account.setLastUsed(LocalDateTime.now());

	        accountRepository.save(account);
	        return "Account creation successful";
	    }

	    // Check if Account Exists
	    public boolean accountExists(Long accountNumber) {
	        return accountRepository.existsById(accountNumber);
	    }

	    // Debit Money
	    public String debitMoney(Long accountNumber, BigDecimal amount, String securityPin) {
	        Optional<Account> account = accountRepository.findById(accountNumber);
	        if (account.isPresent() && account.get().getSecurityPin().equals(securityPin)) {
	            Account acc = account.get();
	            if (acc.getBalance().compareTo(amount) >= 0) {
	                acc.setBalance(acc.getBalance().subtract(amount));
	                acc.setLastUsed(LocalDateTime.now()); // Update last used time
	                accountRepository.save(acc);
	                return "Debit successful";
	            } else {
	                return "Insufficient balance";
	            }
	        }
	        return "Invalid account number or security pin";
	    }

	    // Credit Money
	    public String creditMoney(Long accountNumber, BigDecimal amount, String securityPin) {
	        Optional<Account> account = accountRepository.findById(accountNumber);
	        if (account.isPresent() && account.get().getSecurityPin().equals(securityPin)) {
	            Account acc = account.get();
	            acc.setBalance(acc.getBalance().add(amount));
	            acc.setLastUsed(LocalDateTime.now()); // Update last used time
	            accountRepository.save(acc);
	            return "Credit successful";
	        }
	        return "Invalid account number or security pin";
	    }

	    // Transfer Money
	    public String transferMoney(Long senderAccountNumber, Long receiverAccountNumber, BigDecimal amount, String senderPin) {
	        Optional<Account> senderAccount = accountRepository.findById(senderAccountNumber);
	        Optional<Account> receiverAccount = accountRepository.findById(receiverAccountNumber);

	        if (senderAccount.isPresent() && receiverAccount.isPresent() && senderAccount.get().getSecurityPin().equals(senderPin)) {
	            Account sender = senderAccount.get();
	            if (sender.getBalance().compareTo(amount) >= 0) {
	                sender.setBalance(sender.getBalance().subtract(amount));
	                receiverAccount.get().setBalance(receiverAccount.get().getBalance().add(amount));
	                sender.setLastUsed(LocalDateTime.now()); // Update last used time for sender
	                accountRepository.save(sender);
	                accountRepository.save(receiverAccount.get());
	                return "Transfer successful";
	            } else {
	                return "Insufficient balance";
	            }
	        }
	        return "Invalid account number or security pin";
	    }

	    // Check Balance
	    public BigDecimal checkBalance(Long accountNumber, String securityPin) {
	        Optional<Account> account = accountRepository.findById(accountNumber);
	        if (account.isPresent() && account.get().getSecurityPin().equals(securityPin)) {
	            return account.get().getBalance();
	        }
	        return BigDecimal.ZERO; // Return zero if account not found or invalid pin
	    }

	    // Delete Inactive Accounts
	    @Transactional
	    public void deleteInactiveAccounts() {
	        LocalDateTime thresholdDate = LocalDateTime.now().minusDays(30);
	        accountRepository.deleteByLastUsedBefore(thresholdDate);
	    }
	}

