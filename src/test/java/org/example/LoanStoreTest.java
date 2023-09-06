package org.example;



import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.Map;

import static org.junit.Assert.*;

public class LoanStoreTest {
    private LoanStore loanStore;

    @Before
    public void setUp() {
        loanStore = new LoanStore();
    }

    @Test
    public void testAddValidLoan() throws Exception {
        Loan loan = new Loan("L1", "C1", "LEN1", 10000, new Date(123, 5, 6), 1, new Date(123, 6, 5), 0.01);
        loanStore.addLoan(loan);
        assertEquals(1, loanStore.aggregateByLender().size());
    }

    @Test(expected = Exception.class)
    public void testAddLoanWithPaymentDateAfterDueDate() throws Exception {
        Loan loan = new Loan("L2", "C1", "LEN1", 20000, new Date(123, 5, 6), 1, new Date(123, 5, 5), 0.01);
        loanStore.addLoan(loan);
    }

    @Test
    public void testAggregateByLender() throws Exception {
        Loan loan1 = new Loan("L1", "C1", "LEN1", 10000, new Date(123, 5, 6), 1, new Date(123, 6, 5), 0.01);
        Loan loan2 = new Loan("L2", "C1", "LEN1", 20000, new Date(123, 5, 6), 1, new Date(123, 6, 5), 0.01);
        loanStore.addLoan(loan1);
        loanStore.addLoan(loan2);

        Map<String, Double> lenderAggregation = loanStore.aggregateByLender();
        assertEquals(1, lenderAggregation.size());
        assertEquals(30000.0, lenderAggregation.get("LEN1"), 0.01);
    }

    @Test
    public void testAggregateByInterest() throws Exception {
        Loan loan1 = new Loan("L1", "C1", "LEN1", 10000, new Date(123, 5, 6), 1, new Date(123, 6, 5), 0.01);
        Loan loan2 = new Loan("L2", "C1", "LEN1", 20000, new Date(123, 5, 6), 2, new Date(123, 6, 5), 0.01);
        loanStore.addLoan(loan1);
        loanStore.addLoan(loan2);

        Map<Double, Double> interestAggregation = loanStore.aggregateByInterest();
        assertEquals(2, interestAggregation.size());
        assertEquals(10000.0, interestAggregation.get(1.0), 0.01);
        assertEquals(20000.0, interestAggregation.get(2.0), 0.01);
    }

    @Test
    public void testAggregateByCustomerId() throws Exception {
        Loan loan1 = new Loan("L1", "C1", "LEN1", 10000, new Date(123, 5, 6), 1, new Date(123, 6, 5), 0.01);
        Loan loan2 = new Loan("L2", "C2", "LEN2", 20000, new Date(123, 5, 6), 2, new Date(123, 6, 5), 0.01);
        loanStore.addLoan(loan1);
        loanStore.addLoan(loan2);

        Map<String, Double> customerAggregation = loanStore.aggregateByCustomerId();
        assertEquals(2, customerAggregation.size());
        assertEquals(10000.0, customerAggregation.get("C1"), 0.01);
        assertEquals(20000.0, customerAggregation.get("C2"), 0.01);
    }

    @Test
    public void testDueDateAlerts() throws Exception {
        Loan loan1 = new Loan("L1", "C1", "LEN1", 10000, new Date(123, 4, 1), 1, new Date(123, 5, 1), 0.01);
        Loan loan2 = new Loan("L2", "C2", "LEN2", 20000, new Date(123, 4, 1), 2, new Date(123, 4, 2), 0.01);
        loanStore.addLoan(loan1);
        loanStore.addLoan(loan2);

        loanStore.checkAndLogDueDateAlerts();

    }
}
