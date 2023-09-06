package org.example;



import org.junit.Test;


import java.util.Date;

import static org.junit.Assert.*;

public class LoanTest {

    @Test
    public void testPaymentDateBeforeDueDate() throws Exception {
        Loan loan = new Loan("L1", "C1", "LEN1", 10000, new Date(123, 5, 6), 1, new Date(123, 6, 5), 0.01);
        // The payment date is before the due date, so this should not throw an exception.
        loan.setPaymentDate(new Date(123, 6, 4));
        assertEquals(new Date(123, 6, 4), loan.getPaymentDate());
    }

    @Test(expected = Exception.class)
    public void testPaymentDateAfterDueDate() throws Exception {
        Loan loan = new Loan("L2", "C1", "LEN1", 20000, new Date(123, 5, 6), 1, new Date(123, 5, 5), 0.01);
        // The payment date is after the due date, so this should throw an exception.
        loan.setPaymentDate(new Date(123, 5, 6));
    }

    @Test
    public void testRemainingAmount() {
        Loan loan = new Loan("L3", "C2", "LEN2", 50000, new Date(123, 4, 4), 2, new Date(123, 4, 5), 0.02);
        // Initially, remaining amount should be equal to the loan amount.
        assertEquals(50000, loan.getRemainingAmount(), 0.01);
        // Make a payment and verify that remaining amount is updated.
        loan.makePayment(20000);
        assertEquals(30000, loan.getRemainingAmount(), 0.01);
    }

    @Test
    public void testCancelLoan() {
        Loan loan = new Loan("L4", "C3", "LEN2", 50000, new Date(123, 4, 4), 2, new Date(123, 4, 5), 0.02);
        assertFalse(loan.isCanceled());
        loan.cancelLoan();
        assertTrue(loan.isCanceled());
    }


}
