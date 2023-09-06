package org.example;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class LoanStore {
    private List<Loan> loans;
    private Logger logger;

    public LoanStore() {
        this.loans = new ArrayList<>();
        this.logger = Logger.getLogger("LoanStore");
    }

    public void addLoan(Loan loan) throws Exception {
        if (loan.getPaymentDate().after(loan.getDueDate())) {
            throw new Exception("Payment date cannot be greater than the due date for Loan " + loan.getLoanId());
        }
        loans.add(loan);
    }

    // To implement aggregations by Lender, Interest, and Customer ID
    public Map<String, Double> aggregateByLender() {
        Map<String, Double> aggregation = new HashMap<>();
        for (Loan loan : loans) {
            String lenderId = loan.getLenderId();
            double totalAmount = aggregation.getOrDefault(lenderId, 0.0);
            totalAmount += loan.getRemainingAmount();
            aggregation.put(lenderId, totalAmount);
        }
        return aggregation;
    }

    public Map<Double, Double> aggregateByInterest() {
        Map<Double, Double> aggregation = new HashMap<>();
        for (Loan loan : loans) {
            double interestRate = loan.getInterestPerDay();
            double totalAmount = aggregation.getOrDefault(interestRate, 0.0);
            totalAmount += loan.getRemainingAmount();
            aggregation.put(interestRate, totalAmount);
        }
        return aggregation;
    }

    public Map<String, Double> aggregateByCustomerId() {
        Map<String, Double> aggregation = new HashMap<>();
        for (Loan loan : loans) {
            String customerId = loan.getCustomerId();
            double totalAmount = aggregation.getOrDefault(customerId, 0.0);
            totalAmount += loan.getRemainingAmount();
            aggregation.put(customerId, totalAmount);
        }
        return aggregation;
    }

    // Check and log if loans have crossed the due date
    public void checkAndLogDueDateAlerts() {
        Date currentDate = new Date();
        for (Loan loan : loans) {
            if (!loan.isCanceled() && currentDate.after(loan.getDueDate())) {
                logger.warning("Loan " + loan.getLoanId() + " has crossed the due date.");
            }
        }
    }
}
