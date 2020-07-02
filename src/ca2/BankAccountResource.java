package ca2;

import java.io.IOException;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@Path("/bankaccounts")
public class BankAccountResource {
	@Context
    private UriInfo context;
    
    @GET
    @Produces({MediaType.APPLICATION_XML})
    public List<BankAccount> getBankAccounts(@Context HttpServletRequest request) throws Exception {
        System.out.println("URL: " + request.getRequestURL().toString());
        //System.out.println("String of Dao method: " + BankAccountDao.instance.getBankAccounts());
//
//        List<BankAccount> bankAccounts = new ArrayList<BankAccount>();
//        bankAccounts=BankAccountDao.instance.getBankAccounts();
//        BankAccounts bankaccountsList = new BankAccounts();
//        bankaccountsList.setBankAccounts(bankAccounts);
//		JAXBContext jaxbContext = JAXBContext.newInstance(BankAccounts.class);
//		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
//
//
//		// output pretty printed
//		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//
//		StringWriter sw = new StringWriter();
//		//jaxbMarshaller.marshal(bankaccountsList, System.out);
//		jaxbMarshaller.marshal(bankaccountsList, sw);
//	    String result = sw.toString();
//	    
//		List<BankAccount> BankAccountList = new ParseBankAccounts().doParseBankAccounts(result);
//		int counter = 1;
//		System.out.println("-------------------------");
//		for(BankAccount bankAccount : BankAccountList) {
//			System.out.println("\nAccount: "+counter);
//			System.out.println("Branch Code: " + bankAccount.getBranch_code());
//			System.out.println("Account Number: " + bankAccount.getAccount_number());
//			System.out.println("Cust Name: " + bankAccount.getCust_name());
//			System.out.println("Cust Address: " + bankAccount.getCust_address());
//			System.out.println("Cust Rating: " + bankAccount.getCust_rating());
//			System.out.println("Cust Balance: " + bankAccount.getBalance());
//			counter++;
//		}
        return BankAccountDao.instance.getBankAccounts();
    }
    
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("{branch_code}/{account_number}")
    public BankAccount getAccountDetails(@PathParam("branch_code") String branch_code, @PathParam("account_number") String account_number, @Context Request request) throws JAXBException {
    	//List<BankAccount> bankAccountList = new ParseBankAccounts().doParseBankAccounts(text);
    	BankAccount bankAccount = new BankAccount();
        bankAccount=BankAccountDao.instance.getBankAccount(branch_code, account_number);
    	JAXBContext jaxbContext = JAXBContext.newInstance(BankAccount.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

		// output pretty printed
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		jaxbMarshaller.marshal(bankAccount, System.out);
		StringWriter sw = new StringWriter();
		//jaxbMarshaller.marshal(bankaccountsList, System.out);
		jaxbMarshaller.marshal(bankAccount, sw);
	    String result = sw.toString();
		
//		BankAccount bankAccount = new ParseBankAccount().doParseBankAccount(result);
//		System.out.println("Branch Code: " + bankAccount.getBranch_code());
//		System.out.println("Account Number: " + bankAccount.getAccount_number());
//		System.out.println("Cust Name: " + bankAccount.getCust_name());
//		System.out.println("Cust Address: " + bankAccount.getCust_address());
//		System.out.println("Cust Rating: " + bankAccount.getCust_rating());
//		System.out.println("Cust Balance: " + bankAccount.getBalance());
    	return BankAccountDao.instance.getBankAccount(branch_code, account_number);
    }
    
    @POST
    @Produces(MediaType.APPLICATION_XML)
    @Consumes(MediaType.APPLICATION_XML)
    public BankAccount postBankAccount(BankAccount bankAccount, @Context HttpServletResponse servletResponse) throws IOException, SQLException {
        System.out.println("ACCOUNT NO: "+bankAccount.getAccount_number()+". BRANCH CODE: "+bankAccount.getBranch_code());
        return BankAccountDao.instance.addBankAccount(bankAccount);
    }
    
    @PUT
    @Produces(MediaType.APPLICATION_XML)
    @Consumes(MediaType.APPLICATION_XML)
    @Path("{branch_code}/{account_number}")
    public BankAccount putBankAccount(BankAccount bankAccount, @PathParam("branch_code") String branch_code, @PathParam("account_number") String account_number) throws IOException, SQLException {
        System.out.println("BRANCH CODE: "+branch_code +". ACCOUNTNO: "+account_number);
        return BankAccountDao.instance.updateBankAccount(bankAccount);
    }
    
    @DELETE
    @Produces(MediaType.TEXT_HTML)
    @Path("{branch_code}/{account_number}")
    public String deleteBankAccount(@PathParam("branch_code") String branch_code,@PathParam("account_number") String account_number) throws IOException {
        System.out.println("BRANCH CODE: "+branch_code +". ACCOUNTNO: "+account_number);
        return BankAccountDao.instance.deleteBankAccount(branch_code, account_number);
    }
   
    @DELETE
    @Produces(MediaType.TEXT_HTML)
    public List<BankAccount> deleteAllBankAccounts(@Context HttpServletResponse servletResponse) throws IOException {
        
        return BankAccountDao.instance.deleteAllAccounts();
    }
    
    @HEAD
    public Response doHead() {
        return Response
                .noContent()
                .status(Response.Status.NO_CONTENT)
                .build();
    }
    
    @OPTIONS
    public Response doOptions() {
        Set<String> api = new TreeSet<>();
        api.add("GET");
        api.add("POST");
        api.add("DELETE");
        api.add("HEAD");
        
        return Response
                .noContent()
                .status(Response.Status.NO_CONTENT)
                .build();
    }
    
    @XmlAccessorType (XmlAccessType.FIELD)
    public class Link {
        @XmlAttribute (name="rel")
        private String rel;
        
        @XmlAttribute (name="uri")
        private String uri;
        
        public String getRel() {
            return rel;
        }
        
        public void setRel(String rel) {
            this.rel = rel;
        }
        
        public String getUri() {
            return uri;
        }
        
        public void setUri(String uri) {
            this.uri = uri;
        }
    }
}
