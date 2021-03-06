package main.transactionsubsys;
import java.util.*;
import java.util.Date;
import java.util.Scanner;
import java.io.*;
import java.util.Random;
import main.repositorysys.Repository;
import main.repositorysys.Bill;
import main.repositorysys.Transaction;
import main.repositorysys.Account;
import main.repositorysys.Loan;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;



public class BankDataInterface {
   private static String bankFileDirectory = "C:/Users/black/Desktop/BankTest/BudgetBuddy/main/transactionsubsys/Bank.txt";
   private static String dataDirectory = "C:/Users/black/Desktop/BankTest/BudgetBuddy/main/transactionsubsys/";


   public BankDataInterface() {
       parseAccounts();
       parseLoans();
   } // BankDataInterface()


   // this is a helper method for parseBills() and parseAcccounts()
   // inputs: filepath of csv file
   // outputs: 2d list of data in csv file
   public static ArrayList<String[]> parseGenericCSV(String filepath) {
       String line = "";
       ArrayList<String[]> result = new ArrayList<String[]>();

       try (BufferedReader bra = new BufferedReader(new FileReader(filepath))) {
           while ((line = bra.readLine()) != null) {
               result.add(line.split(","));
           } // while
       } catch (IOException e) {
           e.printStackTrace();
       } // catch

       return result;
   } // parseGenericCSV


   public static void parseLoans() {
       ArrayList<String[]> parsedData = parseGenericCSV("./main/data/bank/loans.csv");

       for (String[] dat : parsedData) {
           try {
               DateFormat format = new SimpleDateFormat("MM-dd-yyyy");
               Date date = format.parse(dat[4]);
              Repository.createLoan(dat[0], Double.parseDouble(dat[1]), Double.parseDouble(dat[2]), Double.parseDouble(dat[3]), date);
            } catch (ParseException e) {
                System.out.println("Incorrect date format");
            } // catch
        } // for

    } // getAccounts()


   public static void parseAccounts() {
       ArrayList<String[]> parsedData = parseGenericCSV("./main/data/bank/accounts.csv");

       for (String[] dat : parsedData) {
           Account a = Repository.createAccount(dat[3], dat[0], Double.parseDouble(dat[1]), Double.parseDouble(dat[2]));
           parseAccountTransactions(a);
       } // for

   } // getAccounts()


   public static void parseAccountTransactions(Account account) {
       String accountFilePath = "./main/data/bank/accounts/" + account.getName() + ".csv";
       ArrayList<String[]>  parsedData = parseGenericCSV(accountFilePath);

       for (String[] dat : parsedData) {
           Transaction t = account.createTransaction(dat[0], Double.parseDouble(dat[1]), dat[2]);
       } // for

   } // parseAccountTransactions()


   public static boolean accountExists(String acctName)throws FileNotFoundException{
      File bankFile = new File(bankFileDirectory);
      if(bankFile.exists() != true){
         return false;
      }
      if(bankFile.canRead() != true){
         return false;
      }

      Scanner bankScan = new Scanner(bankFile);
      String line = bankScan.nextLine();
      System.out.println(acctName);
      System.out.println(line);
      while(bankScan.hasNextLine()){
         if(line.equals(acctName)){
            return true;
         }
         line = bankScan.nextLine();
      }
      return false;
   }

   public static boolean loanExists(String acctName, String bankFileName, String loanName)throws FileNotFoundException{
      if(accountExists(acctName) != true){
         return false;
      }
      File acctFile = new File(dataDirectory + loanName + ".txt");
      Scanner loanScan = new Scanner(acctFile);
      String line = loanScan.next();
      while(loanScan.hasNextLine()){
         if(line == loanName){
            return true;
         }
      }
      return false;
   }

   public static String[] getTransactions(String acctName)throws FileNotFoundException{
      if(accountExists(acctName) != true){
         throw new FileNotFoundException("Account Not Found.");
      }
      File acctTransFile = new File(dataDirectory + acctName + "Transactions.txt");
      Scanner transScan = new Scanner(acctTransFile);
      int num = getNumOfTransactions(acctName);

      String line = null;
      String[] transactions = new String[num];
      int index = 0;
      while(transScan.hasNextLine()){
         transactions[index] = line;
         line = transScan.nextLine();
         index++;
      }

      return transactions;
   }

   public static int getNumOfTransactions(String acctName){
      int numOfTransactions = 0;
      File acctTransFile1 = new File(dataDirectory + acctName + "Transactions.txt");
      Scanner transScan1 = null;
      try {
         transScan1 = new Scanner(acctTransFile1);
         }
      catch(FileNotFoundException e){
         System.out.println("Transactions file not found.");
      }
      String line = null;
      while(transScan1.hasNextLine()){
         numOfTransactions++;
         line = transScan1.nextLine();
      }
      return numOfTransactions;
   }

    //RecordTransactionController rtc = new RecordTransactionController();

    // Generate Transactions from bank
    public void generateTransactions(int numOfTransactions) {
        for(int i = 0; i <= numOfTransactions; i++) {

            //Generate Random Type
            String randString = getRandomWord(5); //Replace with categories

            //Generate Random Value
            double upper = 10000;
            double lower = -10000;
            double randValue = Math.round( (Math.random() * (upper - lower) + lower) * 100.0) / 100.0;


            //Generate Random Date
            Random rand = new Random();
            int dateHighm = 12;
            int dateLowm = 1;
            int randDatd_month = rand.nextInt((dateHighm - dateLowm) + 1) + dateLowm;

            int dateHighd = 30;
            int dateLowd = 1;
            int randDatd_day = rand.nextInt((dateHighd - dateLowd) + 1) + dateLowd;

            int dateHighy = 18;
            int dateLowy = 10;
            int randDatd_year = rand.nextInt((dateHighy - dateLowy) + 1) + dateLowy;


            Repository.getAccount("cash").createTransaction(randString, randValue,
                    randDatd_month + "-" + randDatd_day + "-20" + randDatd_year);
        }
        TransactionSystem.saveTransactions(); //Save the transactions

    }

   private String getRandomWord(int length) {
        String[] list = {"Food", "Services", "Games", "Item", "Groceries", "Bill"
                , "Gift", "Automotive", "Fuel", "Medical", "Insurance"
                , "Office", "Supplies", "Home", "Pet Care", "Mortgage"
                , "Rent", "Loan", "Personal", "Cable", "Phone", "Reward"
                , "Entertainment", "Recreation"}; //23
        Random rand = new Random();
        int high = 12;
        int low = 1;
        int randN = rand.nextInt((high - low) + 1) + low;
        return list[randN];
    }
}
