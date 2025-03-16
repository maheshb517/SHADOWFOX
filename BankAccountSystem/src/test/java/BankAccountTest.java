import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BankAccountTest {

    @Test
    void testDeposit() {
        BankAccount account = new BankAccount(1000);
        account.deposit(500);
        assertEquals(1500, account.getBalance());
    }

    @Test
    void testWithdraw() {
        BankAccount account = new BankAccount(1000);
        boolean success = account.withdraw(200);
        assertTrue(success);
        assertEquals(800, account.getBalance());
    }

    @Test
    void testOverWithdraw() {
        BankAccount account = new BankAccount(500);
        boolean success = account.withdraw(600);
        assertFalse(success);
        assertEquals(500, account.getBalance());
    }
}
