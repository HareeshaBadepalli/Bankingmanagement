package com.example.demo.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.User;
import com.example.demo.service.BankingService;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/banking")
public class BankingController {


    @Autowired
    private BankingService bankingService;

    // User Registration
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestParam String email,
                                               @RequestParam String fullName,
                                               @RequestParam String password) {
        User registeredUser =  bankingService.registerUser(email, fullName, password);
        if (registeredUser != null) {
            return ResponseEntity.ok("Registration successful for user: " + registeredUser.getFullName());
        } else {
            return ResponseEntity.status(409).body("User already exists"); // Conflict
        }
    }

    // User Login
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestParam String email,
                                            @RequestParam String password) {
        return bankingService.loginUser(email, password)
                .map(user -> ResponseEntity.ok("Login successful for user: " + user.getFullName()))
                .orElse(ResponseEntity.status(401).body("Invalid email or password")); // Unauthorized
    }

    // Create Account
    @PostMapping("/create-account")
    public ResponseEntity<String> createAccount(@RequestParam String email,
                                                @RequestParam String fullName,
                                                @RequestParam String securityPin) {
        String result = bankingService.createAccount(email, fullName, securityPin);
        if ("Account creation successful".equals(result)) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(400).body(result); // Bad Request
        }
    }

    // Debit Money
    @PostMapping("/debit")
    public ResponseEntity<String> debitMoney(@RequestParam Long accountNumber,
                                             @RequestParam BigDecimal amount,
                                             @RequestParam String securityPin) {
        String result = bankingService.debitMoney(accountNumber, amount, securityPin);
        if ("Debit successful".equals(result)) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(400).body(result); // Bad Request
        }
    }

    // Credit Money
    @PostMapping("/credit")
    public ResponseEntity<String> creditMoney(@RequestParam Long accountNumber,
                                              @RequestParam BigDecimal amount,
                                              @RequestParam String securityPin) {
        String result = bankingService.creditMoney(accountNumber, amount, securityPin);
        if ("Credit successful".equals(result)) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(400).body(result); // Bad Request
        }
    }

    // Transfer Money
    @PostMapping("/transfer")
    public ResponseEntity<String> transferMoney(@RequestParam Long senderAccountNumber,
                                                @RequestParam Long receiverAccountNumber,
                                                @RequestParam BigDecimal amount,
                                                @RequestParam String senderPin) {
        String result = bankingService.transferMoney(senderAccountNumber, receiverAccountNumber, amount, senderPin);
        if ("Transfer successful".equals(result)) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(400).body(result); // Bad Request
        }
    }

    // Check Balance
    @GetMapping("/balance")
    public ResponseEntity<BigDecimal> checkBalance(@RequestParam Long accountNumber,
                                                   @RequestParam String securityPin) {
        BigDecimal balance = bankingService.checkBalance(accountNumber, securityPin);
        if (balance.compareTo(BigDecimal.ZERO) > 0) {
            return ResponseEntity.ok(balance);
        } else {
            return ResponseEntity.status(400).body(balance); // Bad Request
        }
    }

    // Delete Inactive Accounts
    @DeleteMapping("/delete-inactive-accounts")
    public ResponseEntity<String> deleteInactiveAccounts() {
        bankingService.deleteInactiveAccounts();
        return ResponseEntity.ok("Inactive accounts deleted successfully");
    }
   
}