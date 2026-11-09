package it.unibo.bank.impl;

import it.unibo.bank.api.AccountHolder;
import it.unibo.bank.api.BankAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static it.unibo.bank.impl.SimpleBankAccount.MANAGEMENT_FEE;
import static it.unibo.bank.impl.StrictBankAccount.TRANSACTION_FEE;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;//Import per usare assertEquals
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
/**
 * Test class for the {@link StrictBankAccount} class.
 */
class TestStrictBankAccount {
    //Nei test con junit solitamente si usano variabili costanti scritte in capslock
    private final static int INITIAL_AMOUNT=100;


    // Create a new AccountHolder and a StrictBankAccount for it each time tests are executed.
    private AccountHolder mRossi;
    private BankAccount bankAccount;

    /**
     * Prepare the tests.
     */
    @BeforeEach
    public void setUp() {
        this.mRossi = new AccountHolder("Mario", "Rossi", 1);
        this.bankAccount=new StrictBankAccount(mRossi,0.0);
    }

    /**
     * Test the initial state of the StrictBankAccount.
     */
    @Test
    public void testInitialization() {
        assertEquals(0.0,bankAccount.getBalance());//E un metodo in Junit che verifica che due valori siano uguali
        assertEquals(0,bankAccount.getTransactionsCount());
        assertEquals(mRossi,bankAccount.getAccountHolder());
    }

    /**
     * Perform a deposit of 100€, compute the management fees, and check that the balance is correctly reduced.
     */
    @Test
    public void testManagementFees() {
        bankAccount.deposit(1, INITIAL_AMOUNT);
        bankAccount.chargeManagementFees(1);//calcolo delle commisioni di gestione
        assertEquals(INITIAL_AMOUNT-TRANSACTION_FEE-MANAGEMENT_FEE,bankAccount.getBalance());//100-5+1*0.1=100-5.1-->94.9
    }

    /**
     * Test that withdrawing a negative amount causes a failure.
     */
    @Test
    public void testNegativeWithdraw() {
        //bankAccount.deposit(1, 51);
        try{//Quando un test deve falliere facciamo una try per prendere l'exception
            bankAccount.withdraw(1, 50);//error -50
        }catch(final IllegalArgumentException e){
            assertNotNull(e.getMessage());//controlla che il messaggio non sia null
            assertFalse(e.getMessage().isBlank());//il messaggio dentro dice (e.getMessage().isBlank()) con T/F se il messaggio e vuoto e assertfalse fa il controllo sul fatto che sia falso(e che quindi abbia qualcosa)
        }
    }

    /**
     * Test that withdrawing more money than it is in the account is not allowed.
     */
    @Test
    public void testWithdrawingTooMuch() {
        bankAccount.deposit(1, 100);
        
        try{
            bankAccount.withdraw(1, 101);//error
        }catch(final IllegalArgumentException e){
            assertNotNull(e.getMessage());
            assertFalse(e.getMessage().isBlank());
        }
    }
}
