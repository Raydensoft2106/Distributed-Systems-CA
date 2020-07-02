package ca2;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
 
@XmlRootElement(name = "bankaccounts")
@XmlAccessorType (XmlAccessType.FIELD)
public class BankAccounts {
    @XmlElement(name = "bankaccount")
    private List<BankAccount> bankaccounts = null;
 
    public List<BankAccount> getBankAccounts() {
        return bankaccounts;
    }
 
    public void setBankAccounts(List<BankAccount> bankaccounts) {
        this.bankaccounts = bankaccounts;
    }
}