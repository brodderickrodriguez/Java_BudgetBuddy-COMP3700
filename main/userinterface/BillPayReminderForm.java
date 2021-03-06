package main.userinterface;

import main.repositorysys.BillPayReminder;
import main.transactionsubsys.BillPayReminderController;
import main.transactionsubsys.TransactionSystem;

import java.awt.event.ActionEvent;

public class BillPayReminderForm extends Form {


    public BillPayReminderForm(Window parent) {
        super(parent);

        setGridLayout(4, 2);

        addLabel("Add Bill Reminder");
        addPlaceholder();
        addTextField("reminder_name", "Bill Name");
        addTextField("reminder_amount", "Bill Amount (numbers only)");
        addTextField("reminder_date", "Bill Date (mm-dd-yyyy)");
        addButton("submit", "Add Bill Reminder");
        addButton("back", "Back");

        TransactionSystem.createBillPayReminderController(this);
    }

    public void actionPerformed(ActionEvent event) {
        String name = buttonPressed(event);
        switch (name) {
            case "submit":
                String reminder_name = getTextFromInput("reminder_name");
                double reminder_amount = Double.parseDouble(getTextFromInput("reminder_amount"));
                String reminder_date = getTextFromInput("reminder_date");

                //BillPayReminder temprmd = new BillPayReminder(reminder_name, reminder_amount, reminder_date);
                //BillPayReminderController bpr = new BillPayReminderController();
                //bpr.loadBillReminders();
                //bpr.addBillPayReminder(temprmd);
                //bpr.saveBillReminders();
                //bpr.checkSingleDate(temprmd);

                TransactionSystem.getBillPayReminderController().addBillPayReminder(reminder_name, reminder_amount, reminder_date);
                TransactionSystem.getBillPayReminderController().checkSingleDate();
                break;
            case "back":
                goBack();
                break;

        }
    }
}
