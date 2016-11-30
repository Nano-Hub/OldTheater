package enterprise.service;

import java.math.BigDecimal;

import javax.ejb.Local;

import model.BankCustomer;

@Local
public interface StatelessLocal {

	public BankCustomer getUser(String user);

	public String transferFunds( String fromAccountNo, String toAccountNo,
			BigDecimal amount) throws Exception;

}
