import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.TreeMap;


/**
 * The Cashier class can exchange between USD and SWD, withdrawal money from accounts, and change exchange rate
 *
 * @author elipaulsen
 */
public class Cashier {
    /**
     * exchange rate between USD and SWD. One USD = This variable's value
     */
    private BigDecimal exchangeRate;

    /**
     * A final RoundingMode class variable which rounds half even
     */
    private static final RoundingMode rm = RoundingMode.HALF_EVEN;

    /**
     * A final BigDecimal Array class variable which contains all SWD bill/coin amounts
     */
    private static final BigDecimal[] swdBills = {new BigDecimal(25),new BigDecimal(10),new BigDecimal(5),new BigDecimal(1),new BigDecimal("0.20"),new BigDecimal("0.08"),new BigDecimal("0.05"),new BigDecimal("0.01")};

    /**
     * A final BigDecimal Array class variable which contains all USD bill/coin amounts
     */
    private static final BigDecimal[] usdBills = {new BigDecimal(20),new BigDecimal(10),new BigDecimal(5),new BigDecimal(1),new BigDecimal("0.25"),new BigDecimal("0.10"),new BigDecimal("0.05"),new BigDecimal("0.01")};


    /**
     * A no argument constructor that initializes exchange rate to default value of 1
     */
    public Cashier() {
        exchangeRate = new BigDecimal(1);
    }


    /**
     * Takes given amount in out of given account and displays the number of each USD bill that has been withdrawn
     * if user tries to take out more than they have in their account: account balance will not change and error message will be printed out.
     * @param account account to withdrawal money from
     * @param amount the amount of money to withdrawal from account (USD)
     */
    public String withdrawalUsd(Account account, BigDecimal amount){
        if(amount.compareTo(account.getBalance()) <= 0) {
            account.setBalance(account.getBalance().subtract(amount).setScale(2,rm));
            BigDecimal usdDue = amount.setScale(2,rm);
            int bill = 0;
            Map<BigDecimal,Integer> map = new TreeMap<>();
            while(usdDue.compareTo(BigDecimal.ZERO) > 0 && bill < 8) {
                if(usdBills[bill].compareTo(usdDue) <= 0){
                    usdDue = usdDue.subtract(usdBills[bill]);
                    if(!map.containsKey(usdBills[bill])){
                        map.put(usdBills[bill],1);
                    }
                    else{
                        map.put(usdBills[bill],map.get(usdBills[bill])+1);
                    }
                }
                else{
                    bill++;
                }
            }
            StringBuilder rs= new StringBuilder();
            for(BigDecimal key: map.keySet()){
                rs.append(map.get(key)).append("  $").append(key).append(", ");
            }
           return "Change recieved: \n"+rs.toString();
        }
        return "overdraft account left unchanged";
    }

    /**
     * Takes given amount in USD out of given account and displays the number of each SWD bill that has been withdrawn
     * if user tries to take out more than they have in their account: account balance will not change and error message will be printed out.
     * @param account account to withdrawal money from
     * @param amount the amount of money to withdrawal from account (SWD)
     */
    public String withdrawalSwd(Account account, BigDecimal amount){
        if(amount.divide(exchangeRate,2,rm).compareTo(account.getBalance()) <= 0) {

            account.setBalance(account.getBalance().subtract(amount.divide(exchangeRate,2,rm)));

            BigDecimal swdDue = amount;
            int bill = 0;
            Map<BigDecimal,Integer> map = new TreeMap<>();
            while(swdDue.compareTo(BigDecimal.ZERO) > 0 && bill < 8) {
                if(swdBills[bill].compareTo(swdDue) <= 0){
                    swdDue = swdDue.subtract(swdBills[bill]);
                    if(!map.containsKey(swdBills[bill])){
                        map.put(swdBills[bill],1);
                    }
                    else{
                        map.put(swdBills[bill],map.get(swdBills[bill])+1);
                    }
                }
                else{
                    bill++;
                }
            }
            StringBuilder rs= new StringBuilder();
            for(BigDecimal key: map.keySet()){
                rs.append(map.get(key)).append("  \u0247").append(key).append(", ");
            }
            return "Change recieved: \n" + rs.toString();
        }
        else {
            return "overdraft account left unchanged";
        }
    }

    /**
     * Sets account balance to a positive nonzero BigDecimal input
     * @param exchangeRate the new exchange rate
     */
    public void setExchangeRate(BigDecimal exchangeRate) {
        if(exchangeRate.compareTo(BigDecimal.valueOf(0)) >= 1){
            this.exchangeRate = exchangeRate;
        }
    }

    /**
     * returns the current exchange rate
     * @return the cashiers exchange rate
     */
    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }
}
