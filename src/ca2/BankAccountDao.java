package ca2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public enum BankAccountDao {
	
	instance;

    private Connection con = null;
    
    private Map<Integer, BankAccount> bankAccountMap = new HashMap<Integer, BankAccount>();

    private BankAccountDao() {
    	try{
            System.out.println("Loading db driver...");
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Class.forName("org.hsqldb.jdbcDriver");
            System.out.println("Db driver loaded.");
        
        con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/oneDB", "SA", "Passw0rd");
        
	    } catch (ClassNotFoundException ex) {
            System.err.println("\nClassNotFoundException");
            ex.printStackTrace();
        } catch (SQLException ex) {
            System.out.println("\nSQLException");
            ex.printStackTrace();
        }
    }

    
    public List<BankAccount> getBankAccounts() throws Exception {
        List<BankAccount> bankAccounts = new ArrayList<BankAccount>();
        //Initialize the  list
//        BankAccounts bankaccountsList = new BankAccounts();
//        bankaccountsList.setBankAccounts(bankAccounts);
        try {
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM BANKACCOUNT");
            ResultSet rs = pstmt.executeQuery();


            while (rs.next()) {
            	BankAccount bankAccount = new BankAccount(rs.getString("BRANCH_CODE"), rs.getString("ACCOUNT_NUMBER"), rs.getString("CUST_NAME"), rs.getString("CUST_ADDRESS"), rs.getInt("cust_rating"), rs.getInt("balance"));
            	bankAccounts.add(bankAccount);
            }
            //bankaccountsList.getBankAccounts().addAll(bankAccounts);
            
//    		JAXBContext jaxbContext = JAXBContext.newInstance(BankAccounts.class);
//    		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
//
//
//    		// output pretty printed
//    		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//
//    		StringWriter sw = new StringWriter();
//    		//jaxbMarshaller.marshal(bankaccountsList, System.out);
//    		jaxbMarshaller.marshal(bankaccountsList, sw);
//    	    String result = sw.toString();
    	    //System.out.println(result);
    	    
//			List<BankAccount> BankAccountList = new ParseBankAccounts().doParseBankAccounts(result);
//			int counter = 1;
//			System.out.println("-------------------------");
//			for(BankAccount bankAccount : BankAccountList) {
//				System.out.println("\nAccount: "+counter);
//				System.out.println("Branch Code: " + bankAccount.getBranch_code());
//				System.out.println("Account Number: " + bankAccount.getAccount_number());
//				System.out.println("Cust Name: " + bankAccount.getCust_name());
//				System.out.println("Cust Address: " + bankAccount.getCust_address());
//				System.out.println("Cust Rating: " + bankAccount.getCust_rating());
//				System.out.println("Cust Balance: " + bankAccount.getBalance());
//				counter++;
//			}
			
            //return bankAccounts;
        } catch (SQLException ex) {
            System.out.println("\nSQLException");
            ex.printStackTrace();
        }
        return bankAccounts;
    }

    public BankAccount getBankAccount(String branchcode, String accountnumber) throws JAXBException {
    	BankAccount bankAccounts = new BankAccount("", "", "", "", 0, 0);
        try {
            String str = "SELECT * FROM BANKACCOUNT where BRANCH_CODE='" +branchcode+"' AND ACCOUNT_NUMBER='"+accountnumber+"'";
            System.out.println(str);
            PreparedStatement pstmt = con.prepareStatement(str);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String branch_code =rs.getString("BRANCH_CODE");
                String account_number = rs.getString("ACCOUNT_NUMBER");
                String cust_name = rs.getString("CUST_NAME");
                String cust_address = rs.getString("CUST_ADDRESS");
                int cust_rating = rs.getInt("cust_rating");
                int balance = rs.getInt("balance");
                System.out.println("Account no: "+account_number+" Branch Code: "+branch_code+" Name: "+cust_name+" Address: "+cust_address+" Rating: "+cust_rating+" Balance:"+balance);
                bankAccounts.setBranch_code(branch_code);
                bankAccounts.setAccount_number(account_number);
                bankAccounts.setCust_name(cust_name);
                bankAccounts.setCust_address(cust_address);
                bankAccounts.setCust_rating(cust_rating);
                bankAccounts.setBalance(balance);        		
            }
        } catch (SQLException ex) {
            System.out.println("\nSQLException");
            ex.printStackTrace();
        }
        return bankAccounts;
    }
    
    public BankAccount updateBankAccount(BankAccount bankAccount) throws SQLException
    {
        String str = "UPDATE BANKACCOUNT SET CUST_NAME=?,CUST_ADDRESS=?,cust_rating=?,balance=? WHERE BRANCH_CODE =? AND ACCOUNT_NUMBER =?";
        PreparedStatement pstmt = con.prepareStatement(str);
        pstmt.setString(1, bankAccount.getCust_name());
        pstmt.setString(2, bankAccount.getCust_address());
        pstmt.setInt(3, bankAccount.getCust_rating());
        pstmt.setInt(4, bankAccount.getBalance());
        pstmt.setString(5, bankAccount.getBranch_code());
        pstmt.setString(6, bankAccount.getAccount_number());
        System.out.println(pstmt);
            try{
                pstmt.executeUpdate();  
            }      
            catch (SQLException ex) {
        System.out.println("\nSQLException");
        ex.printStackTrace();
        }
            return bankAccount;
    }

    public BankAccount addBankAccount(BankAccount bankAccount) throws SQLException
    {
        //String str = "INSERT INTO BANKACCOUNT VALUES("+account.getBranch_code()+", "+account.getAccount_number()+", "+account.getCust_name()+", "+account.getCust_address()+", "+account.getCust_rating()+", "+account.getBalance()+")";
        String str = "INSERT INTO BANKACCOUNT VALUES(?,?,?,?,?,?)";
        try {
            PreparedStatement pstmt = con.prepareStatement("INSERT INTO BANKACCOUNT (BRANCH_CODE, ACCOUNT_NUMBER, CUST_NAME, CUST_ADDRESS, CUST_RATING, BALANCE) VALUES (?, ?, ?, ?, ?, ?)");
            pstmt.setString(1, bankAccount.getBranch_code());
            pstmt.setString(2, bankAccount.getAccount_number());
            pstmt.setString(3, bankAccount.getCust_name());
            pstmt.setString(4, bankAccount.getCust_address());
            pstmt.setDouble(5, bankAccount.getCust_rating());
            pstmt.setDouble(6, bankAccount.getBalance());
            System.out.println("STRING: "+pstmt);
            pstmt.executeUpdate();
        }
        catch (Exception ex) {
            System.out.println("\nSQLException");
            ex.printStackTrace();
        }
        return bankAccount;
    }
    
    public int getNextAccountNumber(String branchCode) {
        String account_number = "-1";
        try {
            PreparedStatement pstmt = con.prepareStatement("SELECT ACCOUNT_NUMBER FROM BANKACCOUNT");
            ResultSet rs = pstmt.executeQuery();
            
            while(rs.next()) {
                account_number = rs.getString("account_number");
            }
        }
        catch (Exception ex) {
            System.out.println("\nSQLException");
            ex.printStackTrace();
        }
        return Integer.parseInt(account_number);
    }
    
    public List<BankAccount> deleteAllAccounts() {
    	List<BankAccount> bankAccounts = new ArrayList<BankAccount>();
        try {
            PreparedStatement pstmt = con.prepareStatement("DELETE FROM BANKACCOUNT");
            pstmt.executeUpdate();
        }
        catch (Exception ex) {
            System.out.println("\nSQLException");
            ex.printStackTrace();
        }
		return bankAccounts;
    }

    public String deleteBankAccount(String branchCode, String accountNo) {
        try {
            PreparedStatement pstmt = con.prepareStatement("DELETE FROM BANKACCOUNT WHERE branch_code = ? AND account_number = ?");
            pstmt.setString(1, branchCode);
            pstmt.setString(2, accountNo);
            pstmt.executeUpdate();
        }
        catch (Exception ex) {
            System.out.println("\nSQLException");
            ex.printStackTrace();
        }
        return accountNo;
    }
}
