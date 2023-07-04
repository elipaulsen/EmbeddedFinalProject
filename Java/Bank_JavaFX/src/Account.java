import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * The Account class that is used with the Cashier class to keep track of multiple account balances on same register/cashier
 *
 * @author elipaulsen
 */
public class Account {
    /**
     * The current balance in the account in USD
     */
    private BigDecimal balance;

    /**
     * A no argument constructor that initializes balance to default value of 0.00
     */
    public Account() {
        balance = new BigDecimal("0").setScale(2,RoundingMode.HALF_EVEN);
    }

    /**
     * Returns the account's balance
     * @return account balance
     */
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * Sets account balance to a positive nonzero BigDecimal input
     * @param balance the new balance
     */
    public void setBalance(BigDecimal balance) {
        if(balance.compareTo(BigDecimal.valueOf(0)) >= 0){              //if parameter in negative nothing changes
            this.balance = balance.setScale(2, RoundingMode.HALF_EVEN);
        }
    }
}
