package ca2;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "bankaccount")
@XmlAccessorType (XmlAccessType.FIELD)
@XmlType(propOrder = {"branch_code", "account_number", "cust_name", "cust_address", "cust_rating", "balance"})
public class BankAccount {
	
    private String branch_code;
    private String account_number;
    private String cust_name;
    private String cust_address;
    private int cust_rating;
    private int balance;
// private List<Link> link;


    public BankAccount(String branch_code, String account_number, String cust_name, String cust_address, int cust_rating, int balance) {
        this.branch_code = branch_code;
        this.account_number = account_number;
        this.cust_name = cust_name;
        this.cust_address = cust_address;
        this.cust_rating = cust_rating;
        this.balance = balance;
    }
    
    public BankAccount() {
    }

    public String getBranch_code() {
        return branch_code;
    }

    public void setBranch_code(String branch_code) {
        this.branch_code = branch_code;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public String getCust_name() {
        return cust_name;
    }

    public void setCust_name(String cust_name) {
        this.cust_name = cust_name;
    }

    public String getCust_address() {
        return cust_address;
    }

    public void setCust_address(String cust_address) {
        this.cust_address = cust_address;
    }

    public int getCust_rating() {
        return cust_rating;
    }

    public void setCust_rating(int cust_rating) {
        this.cust_rating = cust_rating;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
