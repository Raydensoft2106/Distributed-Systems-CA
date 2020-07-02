package ca2;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class ParseBankAccounts {
	boolean inBankAccounts = false;
	boolean inBankAccount = false;
	boolean inBranch_code = false;
	boolean inAccount_number = false;
	boolean inCust_name = false;
	boolean inCust_address = false;
	boolean inCust_rating = false;
	boolean inBalance = false;
	
	BankAccount currentBankAccount;
	List<BankAccount> currentBankAccountList;
	
	public List<BankAccount> doParseBankAccounts(String s) throws Exception {
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		XmlPullParser pullParser = factory.newPullParser();
		pullParser.setInput(new StringReader(s));
		processDocument(pullParser);
		return currentBankAccountList;
	}
	
	public void processDocument(XmlPullParser pullParser) throws XmlPullParserException, IOException {
		int eventType = pullParser.getEventType();
		do {
			if (eventType == XmlPullParser.START_DOCUMENT) {
				System.out.println("Start Document");
			} 
			else if (eventType == XmlPullParser.END_DOCUMENT) {
				System.out.println("End Document");
			} 
			else if (eventType == XmlPullParser.START_TAG) {
				processStartElement(pullParser);
			} 
			else if (eventType == XmlPullParser.END_TAG) {
				processEndElement(pullParser);
			} 
			else if (eventType == XmlPullParser.TEXT) {
				processText(pullParser);
			}
			eventType = pullParser.next();
		} while (eventType != XmlPullParser.END_DOCUMENT);
	}
	
	public void processStartElement(XmlPullParser event) {
		String name = event.getName();
		if(name.equals("bankAccounts")) {
			inBankAccounts = true;
			currentBankAccountList = new ArrayList<BankAccount>();
		} 
		else if (name.equals("bankaccount")) {
			inBankAccount = true;
			currentBankAccount = new BankAccount();
		} 
		else if (name.equals("branch_code")) {
			inBranch_code = true;
		} 
		else if (name.equals("account_number")) {
			inAccount_number = true;
		} 
		else if (name.equals("cust_name")) {
			inCust_name = true;
		} 
		else if (name.equals("cust_address")) {
			inCust_address = true;
		}
		else if (name.equals("cust_rating")) {
			inCust_rating = true;
		}
		else if (name.equals("balance")) {
			inBalance = true;
		}
	}
	
	public void processEndElement(XmlPullParser event) {
		String name = event.getName();
		if(name.equals("bankAccounts")) {
			inBankAccounts = false;
		} 
		else if (name.equals("bankaccount")) {
			inBankAccount = false;
			currentBankAccountList.add(currentBankAccount);
		} 
		else if (name.equals("branch_code")) {
			inBranch_code = false;
		} 
		else if (name.equals("account_number")) {
			inAccount_number = false;
		} 
		else if (name.equals("cust_name")) {
			inCust_name = false;
		} 
		else if (name.equals("cust_address")) {
			inCust_address = false;
		}
		else if (name.equals("cust_rating")) {
			inCust_rating = false;
		}
		else if (name.equals("balance")) {
			inBalance = false;
		}
	}
	
	public void processText(XmlPullParser event) throws XmlPullParserException {
		if(inBranch_code) {
			String s = event.getText();
			currentBankAccount.setBranch_code(s);
		}
		if(inAccount_number) {
			String s = event.getText();
			currentBankAccount.setAccount_number(s);
		}
		if(inCust_name) {
			String s = event.getText();
			currentBankAccount.setCust_name(s);
		}
		if(inCust_address) {
			String s = event.getText();
			currentBankAccount.setCust_address(s);
		}
		if(inCust_rating) {
			String s = event.getText();
			currentBankAccount.setCust_rating(Integer.parseInt(s));
		}
		if(inBalance) {
			String s = event.getText();
			currentBankAccount.setBalance(Integer.parseInt(s));
		}
	}

}
