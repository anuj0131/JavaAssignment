package org.example;

import java.util.Date;

public class Loan {
    private String loanId;
    private String customerId;
    private String lenderId;
    private double amount;
    private double remainingAmount;
    private Date paymentDate;
    private double interestPerDay;
    private Date dueDate;
    private double penaltyPerDay;
    private boolean isCanceled;

    public Loan(String loanId, String customerId, String lenderId, double amount,
                Date paymentDate, double interestPerDay, Date dueDate, double penaltyPerDay) {
        this.loanId = loanId;
        this.customerId = customerId;
        this.lenderId = lenderId;
        this.amount = amount;
        this.remainingAmount = amount;
        this.paymentDate = paymentDate;
        this.interestPerDay = interestPerDay;
        this.dueDate = dueDate;
        this.penaltyPerDay = penaltyPerDay;
        this.isCanceled = false;
    }

    public String getLoanId() {
        return loanId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getLenderId() {
        return lenderId;
    }

    public double getAmount() {
        return amount;
    }

    public double getRemainingAmount() {
        return remainingAmount;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) throws Exception {
        if (paymentDate.after(dueDate)) {
            throw new Exception("Payment date cannot be after the due date.");
        }
        this.paymentDate = paymentDate;
    }

    public double getInterestPerDay() {
        return interestPerDay;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public double getPenaltyPerDay() {
        return penaltyPerDay;
    }

    public boolean isCanceled() {
        return isCanceled;
    }

    public void makePayment(double amountPaid) {
        if (!isCanceled && amountPaid > 0) {
            double remaining = remainingAmount - amountPaid;
            if (remaining >= 0) {
                remainingAmount = remaining;
            } else {

                remainingAmount = 0;
            }
        }
    }

    public void cancelLoan() {
        isCanceled = true;
    }
}
