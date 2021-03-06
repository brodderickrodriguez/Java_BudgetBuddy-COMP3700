package main.repositorysys;
import java.util.*;
import main.repositorysys.Asset;
import main.repositorysys.Account;
import main.repositorysys.Loan;
import main.repositorysys.Bill;
import main.repositorysys.BillPayReminder;
import main.repositorysys.Budget;
import main.repositorysys.BudgetReport;
import main.repositorysys.FinancialReport;
import main.transactionsubsys.AutomaticBillPayController;
import main.transactionsubsys.TransactionSystem;


public class Repository {

    private static Collection<Account> accountCollection = new ArrayList<Account>();
    private static Collection<Asset> assetCollection = new ArrayList<Asset>();

    //private static Collection<Bill> billCollection = new ArrayList<Bill>();

    private static Collection<BillPayReminder> billPayReminderCollection = new ArrayList<BillPayReminder>();
    private static Collection<BillPayReminder> automaticBillPayReminderCollection = new ArrayList<BillPayReminder>();
    private static Collection<Budget> budgetCollection = new ArrayList<Budget>();
    private static Collection<BudgetReport> budgetReportCollection = new ArrayList<BudgetReport>();
    // Categories are located as a collection within Budget
    private static Collection<FinancialReport> financialReportCollection = new ArrayList<FinancialReport>();
    private static Collection<Loan> loanCollection = new ArrayList<Loan>();
    // Transactions are located as a collection within Account

    public static Collection<Asset> getAssets() {
        return assetCollection;
    }
    
    public static void createAsset(String name, String type, double value, Date date) {
        assetCollection.add(new Asset(name, type, value, date));
    }

    public static Collection<Account> getAccounts() {
        return accountCollection;
    }

    public static Account[] getSavingsAccounts() {
        if (accountCollection.isEmpty()) {
            return null;
        }
        Account[] sAccounts = new Account[accountCollection.size()];
        int i = 0;
        for (Account a : accountCollection) {
            if (a.isSavings()) {
                sAccounts[i++] = a;
            }
        }
        return sAccounts;
    }

    public static Account[] getCreditAccounts() {
        if (accountCollection.isEmpty()) {
            return null;
        }
        Account[] cAccounts = new Account[accountCollection.size()];
        int i = 0;
        for (Account a : accountCollection) {
            if (a.isCredit()) {
                cAccounts[i++] = a;
            }
        }
        return cAccounts;
    }

    public static Collection<Loan> getLoans() {
        return loanCollection;
    }

    public static Loan getLoan(String nameIn) {
        Loan targetLoan = null;
        for (Loan l: loanCollection) {
            if (l.getName().equals(nameIn)) {
                targetLoan = l;
            }
        }
        return targetLoan;
    }

    public static Account createAccount(String inName, String inType, double inBal, double inRate){
        Account zAccount = new Account(inName, inType, inBal, inRate);
        accountCollection.add(zAccount);
        return zAccount;
    }

    public static Budget createBudget(String inName, Date inStart, Date inEnd, double inSpend){
        Budget constructedBudget =  new Budget(inName, inStart, inEnd, inSpend);
        budgetCollection.add(constructedBudget);
        return constructedBudget;
    }

    public static FinancialReport createFinancialReport(String inText){
        FinancialReport returnMe = new FinancialReport(inText);
        financialReportCollection.add(returnMe);
        return returnMe;
    }
    
    public static BudgetReport createBudgetReport(String name, String reportText) {
        BudgetReport report = new BudgetReport(name, reportText);
        budgetReportCollection.add(report);
        return report;
    }
    
    public static BudgetReport getBudgetReport(String nameIn) {
        BudgetReport target = null;
        for (BudgetReport r: budgetReportCollection) {
            if (r.getName().equals(nameIn)) {
                target = r;
            }
        }
        return target;
    }

    public static Account getAccount(String findMe) {
        for (Account a : accountCollection)
            if (findMe.equals(a.getName()))
                return a;
        return null;
    } // getAccount()

    public static Collection<Account> getAccountCollection(){
        return accountCollection;
    }

    public static void printBudgetCollection(){
        if (!budgetCollection.isEmpty()) {
            System.out.println("\n");
            for (Budget budg : budgetCollection) {
                Collection<Category> catsList = budg.getCategories();
                System.out.println(budg.getName() + "\n" + budg.getStartDate() + " - " + budg.getEndDate());
                for (Category cat : catsList) {
                    System.out.println("\t" + cat.getName() + "\t| $" + cat.getGoal());
                }
                System.out.println("\tTotal : $" + budg.getSpendingCap() + "\n");
            }
        }
        else
            System.out.println("You don't have any budgets!");
    }
    
    public static String[] getBudgetNames() {
        if (budgetCollection.isEmpty()) {
            return null;
        }
        String[] names = new String[budgetCollection.size()];
        int i = 0;
        for (Budget b : budgetCollection) {
            names[i++] = b.getName();
        }
        
        return names;
    }
    
    public static Budget getBudget(String name) {
        for (Budget b : budgetCollection) {
            if (b.getName().equals(name)) {
                return b;
            }
        }
        return null;
    }

    public static void createLoan(String nameIn, double amountIn, double interestRateIn, double monthlyPaymentIn, Date startDateIn) {
        loanCollection.add(new Loan(nameIn, amountIn, interestRateIn, monthlyPaymentIn, startDateIn));
    }

    public static BillPayReminder createAutomaticBillPayReminder(String name, double value, String dueDateString) {
        BillPayReminder reminder = new BillPayReminder(name, value, dueDateString);
        automaticBillPayReminderCollection.add(reminder);
        return reminder;
    }

    public static Collection<BillPayReminder> getAutomaticBillPayReminders() {
        return automaticBillPayReminderCollection;
    }

    public static void setBillPayReminders(Collection<BillPayReminder> bill) {
        automaticBillPayReminderCollection = bill;
    }

    public static BillPayReminder createBillPayReminder(String name, double value, String dueDateString) {
        BillPayReminder reminder = new BillPayReminder(name, value, dueDateString);
        billPayReminderCollection.add(reminder);
        return reminder;
    }

    public static void removeBillPayReminder(BillPayReminder bill) {
        billPayReminderCollection.remove(bill);
    }

    public static void removeAutomaticBillPay(BillPayReminder bill) {
        automaticBillPayReminderCollection.remove(bill);
    }

    public static Collection<BillPayReminder> getBillPayReminders() {
        return billPayReminderCollection;
    }

    public static void setAutomaticBillPayReminders(Collection<BillPayReminder> bill) {
        billPayReminderCollection = bill;
    }

}
