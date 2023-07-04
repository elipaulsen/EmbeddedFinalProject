import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that holds the logic with gui actions
 */
public class GUIController {
    /**
     * cashier that will do the calculations
     */
    private final Cashier cashier = new Cashier();

    /**
     * map with keys: account names, and values: accounts
     */
    private final Map<String, Account> accountMap = new HashMap<>();

    /**
     * account that is currently selected
     */
    private Account selectedAccount;

    /**
     * label that represents exchange rate
     */
    @FXML
    private Label exchangeRateText;

    /**
     * text field to enter new exchange rate
     */
    @FXML
    private TextField newRate;

    /**
     * text field to enter acct name
     */
    @FXML
    private TextField acctName;

    /**
     * dropdown menu to selece account
     */
    @FXML
    private ChoiceBox<String> accountChoiceBox;

    /**
     * button to delete accounts
     */
    @FXML
    private ChoiceBox<String> deleteBox;

    /**
     * button to select account
     */
    @FXML
    private Button selectAcct;

    /**
     * button to delete account
     */
    @FXML
    private Button deletedAcct;

    /**
     * label with current balance
     */
    @FXML
    private Label balance;

    /**
     * text field to enter amount to set balance to
     */
    @FXML
    private TextField depositUSDtext;

    /**
     * button to set balance
     */
    @FXML
    private Button depUSD;

    /**
     * text field to enter amount wanted
     */
    @FXML
    private TextField withdrawalUSDtext;

    /**
     * button to withdrawl usd
     */
    @FXML
    private Button withdrawalUSD;

    /**
     * text field to enter amount wanted
     */
    @FXML
    private TextField withdrawalSWDtext;

    /**
     * button to withdrawal SWD
     */
    @FXML
    private Button withdrawalSWD;

    /**
     * text area to show change and feedback
     */
    @FXML
    private TextArea changeGiver;


    /**
     * Method that changes the exchange rate
     */
    @FXML
    private void changeRate() {
        try {
            cashier.setExchangeRate(new BigDecimal(newRate.getText()));
            newRate.setText("");
            exchangeRateText.setText(String.valueOf(cashier.getExchangeRate().setScale(2, RoundingMode.HALF_EVEN)));
        }
        catch (Exception ignored) {
        }
    }

    /**
     * method that creates a new account
     */
    @FXML
    private void newAccount() {
        selectAcct.setDisable(false);
        accountChoiceBox.setDisable(false);
        String name = acctName.getText().trim();
        accountMap.put(name, new Account());
        accountChoiceBox.getItems().add(name);
        deleteBox.getItems().add(name);
        accountChoiceBox.show();
        acctName.setText("");
    }

    /**
     * method that selects an account
     */
    @FXML
    private void accountChose() {
        selectedAccount = accountMap.get(accountChoiceBox.getValue());
        balance.setText('$'+String.valueOf(selectedAccount.getBalance()));
        changeGiver.setText("\n>>> JFX ExchangeComp");

        if(withdrawalUSD.isDisabled()) {
            withdrawalUSD.setDisable(false);
            withdrawalSWD.setDisable(false);
            withdrawalSWDtext.setDisable(false);
            withdrawalUSDtext.setDisable(false);
            depUSD.setDisable(false);
            depositUSDtext.setDisable(false);
        }

    }

    /**
     * method that sets account balance
     */
    @FXML
    private void initializeBalance() {
        try {
            selectedAccount.setBalance(new BigDecimal(depositUSDtext.getText().trim()));
            balance.setText('$'+String.valueOf(selectedAccount.getBalance()));
            changeGiver.setText(changeGiver.getText()+"\n>>> set balance to "+depositUSDtext.getText());
            depositUSDtext.setText("");
        }
        catch (Exception e) {
            changeGiver.setText(changeGiver.getText()+"\n>>> improper input");
        }
    }

    /**
     * method that withdrawals usd
     */
    @FXML
    private void withDrawalUsd() {
        try {
            String result = cashier.withdrawalUsd(selectedAccount, new BigDecimal(withdrawalUSDtext.getText().trim()));
            balance.setText('$'+String.valueOf(selectedAccount.getBalance()));
            changeGiver.setText(changeGiver.getText()+"\n>>> "+result);
            withdrawalUSDtext.setText("");
        }
        catch (Exception e) {
            changeGiver.setText(changeGiver.getText()+"\n>>> improper input");
        }
    }

    /**
     * method that withdrawals swd
     */
    @FXML
    private void withDrawalSwd() {
        try {
            String result = cashier.withdrawalSwd(selectedAccount, new BigDecimal(withdrawalSWDtext.getText().trim()));
            balance.setText('$'+String.valueOf(selectedAccount.getBalance()));
            changeGiver.setText(changeGiver.getText()+"\n>>> "+result);
            withdrawalSWDtext.setText("");
        }
        catch (Exception e) {
            changeGiver.setText(changeGiver.getText()+"\n>>> improper input");
        }
    }

    /**
     * method that deletes account
     */
    @FXML
    private void deleteAccount() {
        accountChoiceBox.getItems().remove(deleteBox.getValue());
        changeGiver.setText("");
    }


}
