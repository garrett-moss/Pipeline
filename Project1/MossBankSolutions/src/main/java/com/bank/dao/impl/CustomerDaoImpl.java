package com.bank.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.bank.dao.CustomerDAO;
import com.bank.dbutil.BankOracleConnection;
import com.bank.exception.BankException;
import com.bank.main.MainDriver;
import com.bank.model.Customer;
import com.bank.model.Employee;

public class CustomerDaoImpl implements CustomerDAO {

	final static Logger logger = Logger.getLogger(MainDriver.class);
	
	@Override
	public Customer createCustomerAccount(Customer createAccount) throws BankException {

		try (Connection connection= BankOracleConnection.getConnection()){
			String sql="{call CREATECUSTOMER(?,?,?,?,?)}";
			CallableStatement callableStatement = connection.prepareCall(sql);
			callableStatement.setString(1, createAccount.getUsername());
			callableStatement.setString(2, createAccount.getPassword());
			callableStatement.setString(3, createAccount.getAccountBalance());
			callableStatement.setString(5, createAccount.getApproved());
			
			
			callableStatement.registerOutParameter(4, java.sql.Types.VARCHAR);
			callableStatement.execute();
			createAccount.setAccountNumber(callableStatement.getString("accountnumber"));
			
		} catch (ClassNotFoundException | SQLException e) {
			logger.error("Customer DAO Exception");
		}
		
		return createAccount;
	}

@Override
	public Customer viewBalance(String accountNumber) throws BankException {
		Customer newCustomer=null;
		try (Connection connection= BankOracleConnection.getConnection()){
		String sql="Select accountbalance from customer where accountnumber=?";
		PreparedStatement preparedStatement=connection.prepareStatement(sql);
		preparedStatement.setString(1, accountNumber);
		ResultSet resultSet=preparedStatement.executeQuery();
		if(resultSet.next()) {
			newCustomer = new Customer();
			newCustomer.setAccountNumber(accountNumber);
			newCustomer.setAccountBalance(resultSet.getString("accountbalance"));
		} else {
			logger.error("Customer Account Number "+ accountNumber+ " is not valid");
		}
		}catch (ClassNotFoundException | SQLException e) {
			logger.error("Error contact Customer Support");
		}
		return newCustomer;
		
	}

	public Customer withdraw(String accountNumber, String withdraw) throws BankException {
		try (Connection connection= BankOracleConnection.getConnection()){
			String sql="UPDATE customer set accountbalance = (accountbalance - ?)  where accountnumber =?";
			String sql2="INSERT into transactions (transactionamount, accountnumber, transactiontype) values (?,?,?)";
			CallableStatement callableStatement = connection.prepareCall(sql);
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setString(2, accountNumber);
			preparedStatement.setDouble(1, Double.parseDouble(withdraw));
			int resultSet=preparedStatement.executeUpdate();
			CallableStatement callableStatementTransaction = connection.prepareCall(sql2);
			PreparedStatement preparedStatementTransaction=connection.prepareStatement(sql2);
			preparedStatementTransaction.setString(2, accountNumber);
			preparedStatementTransaction.setString(1, withdraw);
			preparedStatementTransaction.setString(3, "Withdrawl");
			int resultSetTransaction=preparedStatementTransaction.executeUpdate();
			
			if (resultSet < 0) {
				logger.error("Internal error occured please contact SYSADMIN");
			}
			
			} catch (ClassNotFoundException | SQLException e) {
				logger.error("Internal error occured please contact SYSADMIN");
			}
		return null;
	}

	public Customer deposit(String deposit, String accountNumber) throws BankException {
		try (Connection connection= BankOracleConnection.getConnection()){
			String sql="UPDATE customer set accountbalance = (accountbalance + ?)  where accountnumber =?";
			String sql2="INSERT into transactions (transactionamount, accountnumber, transactiontype) values (?,?,?)";
			CallableStatement callableStatement = connection.prepareCall(sql);
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setString(2, accountNumber);
			preparedStatement.setDouble(1, Double.parseDouble(deposit));
			int resultSet=preparedStatement.executeUpdate();
			CallableStatement callableStatementTransaction = connection.prepareCall(sql2);
			PreparedStatement preparedStatementTransaction=connection.prepareStatement(sql2);
			preparedStatementTransaction.setString(2, accountNumber);
			preparedStatementTransaction.setString(1, deposit);
			preparedStatementTransaction.setString(3, "Deposit");
			int resultSetTransaction=preparedStatementTransaction.executeUpdate();
			
			if (resultSet < 0) {
				logger.error("Internal error occured please contact SYSADMIN");
			}
			
			} catch (ClassNotFoundException | SQLException e) {
				logger.error("Internal error occured please contact SYSADMIN");
			}
		return null;
	}

	public Customer moneyTransfer(String fromAccountNumber, String transferAmount, String toAccountNumber) throws BankException {
		try (Connection connection= BankOracleConnection.getConnection()){
			String sql="UPDATE customer set accountbalance = (accountbalance - ?)  where accountnumber =?";
			String sql2="UPDATE customer set accountbalance = (accountbalance + ?)  where accountnumber =?";
			String sql3="INSERT into transactions (transactionamount, accountnumber, transactiontype) values (?,?,?)";
			CallableStatement callableStatement = connection.prepareCall(sql);
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setString(2, fromAccountNumber);
			preparedStatement.setDouble(1, Double.parseDouble(transferAmount));
			CallableStatement callableStatement2 = connection.prepareCall(sql2);
			PreparedStatement preparedStatement2=connection.prepareStatement(sql2);
			preparedStatement2.setString(2, toAccountNumber);
			preparedStatement2.setDouble(1, Double.parseDouble(transferAmount));
			int resultSet=preparedStatement.executeUpdate();
			int resultSet2=preparedStatement2.executeUpdate();
			CallableStatement callableStatementTransaction = connection.prepareCall(sql3);
			PreparedStatement preparedStatementTransaction=connection.prepareStatement(sql3);
			preparedStatementTransaction.setString(2, ("From: "+fromAccountNumber+" To: "+toAccountNumber));
			preparedStatementTransaction.setString(1, transferAmount);
			preparedStatementTransaction.setString(3, "Money Transfer");
			int resultSetTransaction=preparedStatementTransaction.executeUpdate();
			
			if (resultSet < 0) {
				logger.error("Internal error occured please contact SYSADMIN");
			}
			
			} catch (ClassNotFoundException | SQLException e) {
				logger.error("Internal error occured please contact SYSADMIN");
			}
		return null;
		
	}

	public Customer acceptTransfer(String fromAccountNumber, String transferAmount, String toAccountNumber) throws BankException {
		try (Connection connection= BankOracleConnection.getConnection()){
			String sql="UPDATE customer set accountbalance = (accountbalance + ?)  where accountnumber =?";
			String sql2="UPDATE customer set accountbalance = (accountbalance - ?)  where accountnumber =?";
			String sql3="INSERT into transactions (transactionamount, accountnumber, transactiontype) values (?,?,?)";
			CallableStatement callableStatement = connection.prepareCall(sql);
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setString(2, fromAccountNumber);
			preparedStatement.setDouble(1, Double.parseDouble(transferAmount));
			CallableStatement callableStatement2 = connection.prepareCall(sql2);
			PreparedStatement preparedStatement2=connection.prepareStatement(sql2);
			preparedStatement2.setString(2, toAccountNumber);
			preparedStatement2.setDouble(1, Double.parseDouble(transferAmount));
			int resultSet=preparedStatement.executeUpdate();
			int resultSet2=preparedStatement2.executeUpdate();
			CallableStatement callableStatementTransaction = connection.prepareCall(sql3);
			PreparedStatement preparedStatementTransaction=connection.prepareStatement(sql3);
			preparedStatementTransaction.setString(2, ("From: "+fromAccountNumber+" To: "+toAccountNumber));
			preparedStatementTransaction.setString(1, transferAmount);
			preparedStatementTransaction.setString(3, "Money Transfer Request");
			int resultSetTransaction=preparedStatementTransaction.executeUpdate();
			
			if (resultSet < 0) {
				logger.error("Internal error occured please contact SYSADMIN");
			}
			
			} catch (ClassNotFoundException | SQLException e) {
				logger.error("Internal error occured please contact SYSADMIN");
			}
		return null;
	
	}

	@Override
	public String deleteCustomer(String accountnumber) throws BankException {
		try (Connection connection= BankOracleConnection.getConnection()){
			String sql="DELETE from customer where accountnumber =?	";
			CallableStatement callableStatement = connection.prepareCall(sql);
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setString(1, accountnumber);
			int resultSet=preparedStatement.executeUpdate();
			
			if (resultSet < 1) {
				logger.error("Internal error occured please contact SYSADMIN");
			}
			
			} catch (ClassNotFoundException | SQLException e) {
				logger.error("Internal error occured please contact SYSADMIN");
			}
		return null;
	}

	@Override
	public Customer loginVerification(String username) throws BankException {
			Customer account = new Customer();
		try (Connection connection= BankOracleConnection.getConnection()){
				String sql="SELECT * from customer where username=?";
				PreparedStatement preparedStatement=connection.prepareStatement(sql);
				preparedStatement.setString(1, username);
				ResultSet resultSet=preparedStatement.executeQuery();
				if(resultSet.next()) {
					account.setUsername(resultSet.getString("username"));
					account.setPassword(resultSet.getString("password"));
					account.setAccountBalance(resultSet.getString("accountBalance"));
					account.setAccountNumber(resultSet.getString("accountNumber"));
					account.setApproved(resultSet.getString("approved"));
				} else {
					logger.error("Customer Credentials "+ username+ " are not valid");
				}
				}catch (ClassNotFoundException | SQLException e) {
					logger.error("Error contact Customer Support");
				}
				return account;
		}
	@Override
	public Customer updateCustomer(String newPassword, String accountNumber) throws BankException {
		try (Connection connection= BankOracleConnection.getConnection()){
			String sql="UPDATE customer set password = ? where accountnumber =?";
			CallableStatement callableStatement = connection.prepareCall(sql);
			callableStatement.setString(1, newPassword);
			callableStatement.setString(2, accountNumber);
			
			callableStatement.execute();
			
		} catch (ClassNotFoundException | SQLException e) {
			logger.error("Internal error occured plase contact SYSADMIN");
		}
		
		return null;
	}
}
