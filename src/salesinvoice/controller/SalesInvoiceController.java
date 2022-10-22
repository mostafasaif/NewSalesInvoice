
package salesinvoice.controller;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import salesinvoice.model.InvoiceClass;
import salesinvoice.model.LineClass;
import salesinvoice.model.TableOfInvoiceModel;
import salesinvoice.model.TableOfLinesModel;
import salesinvoice.view.FrameOfInvoice;
import salesinvoice.view.NewInvoiceDialog;
import salesinvoice.view.NewLineDialog;

         
public class SalesInvoiceController implements ActionListener, ListSelectionListener{
    private FrameOfInvoice invframe;
    private NewInvoiceDialog invDialog;
    private NewLineDialog lineDialog;
    public SalesInvoiceController(FrameOfInvoice invframe)
    {
        this.invframe=invframe;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       
       String ActionCommand = e.getActionCommand();
       System.out.println("Please fill the implement " + ActionCommand);
       
       switch (ActionCommand){
           case"Create New Invoice":
               createNewInvoice();
               break;
            case"Delete Invoice":
                deleteInvoice();
               break;
            case"Create New Item":
                createNewItem();
               break;
            case"Delete Item":
                deleteItem();
               break;
            case"Load File":
                loadFile();
               break;
            case"Save File":
                saveFile();
               break;
            case"createNewInvoiceOK":
                createNewInvoiceOK();
                break;
            case"createNewInvoiceCancel":
                createNewInvoiceCancel();
                break;
            case"createNewLineOK":
                createNewLineOK();
                break;
            case"createNewLineCancel":
                createNewLineCancel();
                break;
            
       }
           
    }
    
    @Override
    public void valueChanged(ListSelectionEvent e) {
        int selectIndex= invframe.getTableOfInvoice().getSelectedRow(); 
        if(selectIndex !=-1)
        {
        System.out.println("You have selected row " +invframe.getTableOfInvoice().getSelectedRow() );
        InvoiceClass current = invframe.getInvoices().get(selectIndex);
        invframe.getInvoiceNumLable().setText(""+current.getNumber());
        invframe.getInvoiceDateLable().setText(current.getDate());
        invframe.getCustomerNameLable().setText(current.getCustomer());
        invframe.getTotalOfInvoiceLable().setText(""+current.getTotalOfInvoice());
        TableOfLinesModel tableOfLinesModel = new TableOfLinesModel (current.getLine());
        invframe.getLineTable().setModel(tableOfLinesModel);
        tableOfLinesModel.fireTableDataChanged();
        }
    }

    private void createNewInvoice() {
        
        invDialog =new NewInvoiceDialog(invframe);
        invDialog.setVisible(true);
        
    }

    private void deleteInvoice() {
       int selectRow= invframe.getTableOfInvoice().getSelectedRow();
       if(selectRow !=-1)
       {
         invframe.getInvoices().remove(selectRow);
         invframe.getTableofinvoicemodel().fireTableDataChanged();
       }
        
      
    }

    private void createNewItem() {
       lineDialog = new NewLineDialog(invframe);
       lineDialog.setVisible(true);
        
    }

    private void deleteItem() {
        int selectInvoice=invframe.getTableOfInvoice().getSelectedRow();
        int selectRow= invframe.getLineTable().getSelectedRow();
       if(selectInvoice !=-1 && selectRow !=-1)
       {
           InvoiceClass invoice = invframe.getInvoices().get(selectInvoice);
           invoice.getLine().remove(selectRow);
           TableOfLinesModel tableoflinesmodel = new TableOfLinesModel(invoice.getLine());
           invframe.getLineTable().setModel(tableoflinesmodel);
         
         tableoflinesmodel.fireTableDataChanged();
         invframe.getTableofinvoicemodel().fireTableDataChanged();
       }
    }
    
     

    private void loadFile() {
         
         JFileChooser jfc =new JFileChooser();
         try{
         int action =jfc.showOpenDialog(invframe);
         if(action == JFileChooser.APPROVE_OPTION)
         {
             File hFile =jfc.getSelectedFile();
            Path hpath= Paths.get(hFile.getAbsolutePath());
           List<String> hLines= Files.readAllLines(hpath);
           System.out.println("File Invoices have been read ");
           ArrayList<InvoiceClass> arrOfInvoices= new ArrayList<>();
          
           for( String hLine : hLines )
           {
             String [] hparts =  hLine.split(","); 
             int invoiceNum = Integer.parseInt(hparts[0]);
             String invoiceDate = hparts[1];
             String cumName= hparts[2];
             InvoiceClass invoice = new InvoiceClass(invoiceNum,invoiceDate,cumName);
                arrOfInvoices.add(invoice);
           }
           System.out.println("Check Action");
           action=jfc.showOpenDialog(invframe);
           if(action == JFileChooser.APPROVE_OPTION)
           {
             File lFile =jfc.getSelectedFile();
            Path hline= Paths.get(lFile.getAbsolutePath());
           List<String> invoiceLines= Files.readAllLines(hline);
           for (String invoiceLine : invoiceLines) {
                        String []lineParts = invoiceLine.split(",");
                        int invoiceNum = Integer.parseInt(lineParts[0]);
                        String itemName = lineParts[1];
                        double itemPrice = Double.parseDouble(lineParts[2]);
                        int count = Integer.parseInt(lineParts[3]);
                        InvoiceClass invoices;
                 invoices = null;
                        for (InvoiceClass invoice : arrOfInvoices) {
                            if (invoice.getNumber() == invoiceNum) {
                                invoices = invoice;
                                break;
                            }
                        }

                       // LineClass line= new LineClass (invoiceNum, itemName, itemPrice, count, invoices);
                      /* LineClass line= new LineClass(invoiceNum,itemName,itemPrice,invoices);
                        invoices.getLine().add(line);*/
                      LineClass line=new LineClass( itemName, count, itemPrice, invoices);
                      invoices.getLine().add(line);
                      
                    }
           System.out.println("Check point");
           
           }
           invframe.setInvoices(arrOfInvoices);
           TableOfInvoiceModel tableOfInvoiceModel=new TableOfInvoiceModel(arrOfInvoices);
           invframe.setTableofinvoicemodel(tableOfInvoiceModel);
           invframe.getTableOfInvoice().setModel(tableOfInvoiceModel);
           invframe.getTableofinvoicemodel().fireTableDataChanged();
         }
    } catch(IOException ex)
    {
        ex.printStackTrace();
    }
    }

    private void saveFile() {
        
    ArrayList<InvoiceClass> invoices = invframe.getInvoices();
        String headers = "";
        String lines = "";
        for (InvoiceClass Invo : invoices) {
            String csvInvoice = Invo.getCsvFile();
            headers += csvInvoice;
            headers += "\n";

            for (LineClass line : Invo.getLine()) {
                String csvLine = line.getCsvFile();
                lines += csvLine;
                lines += "\n";
            }
        }
        System.out.println("Check point");
        try {
            JFileChooser jfc = new JFileChooser();
            int result = jfc.showSaveDialog(invframe);
            if (result == JFileChooser.APPROVE_OPTION) {
                File headerFile = jfc.getSelectedFile();
                FileWriter headerFW = new FileWriter(headerFile);
                headerFW.write(headers);
                headerFW.flush();
                headerFW.close();
                result = jfc.showSaveDialog(invframe);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File lineFile = jfc.getSelectedFile();
                    FileWriter lineFW = new FileWriter(lineFile);
                    lineFW.write(lines);
                    lineFW.flush();
                    lineFW.close();
                }
            }
        } catch (Exception ex) {

        }
    }

    private void createNewInvoiceOK() {
       String date= invDialog.getInvDateField().getText();
       String customer= invDialog.getCustNameField().getText();
       int number = invframe.getNextInvoiceNumber();
       InvoiceClass Invoice= new InvoiceClass(number, date,customer);
       invframe.getInvoices().add(Invoice);
       invframe.getTableofinvoicemodel().fireTableDataChanged();
       invDialog.setVisible(false);
       invDialog.dispose();
       invDialog =null;
    }

    private void createNewInvoiceCancel() {
      invDialog.setVisible(false);
      invDialog.dispose();
      invDialog =null;
    }

    private void createNewLineOK() {
        String item = lineDialog.getItemNameField().getText();
        String countString = lineDialog.getItemCountField().getText();
        String priceString = lineDialog.getItemPriceField().getText();
        int count =Integer.parseInt(countString);
        double price = Double.parseDouble(priceString);
        int selectInvoice=invframe.getTableOfInvoice().getSelectedRow();
        if(selectInvoice!=-1){
              InvoiceClass invoice = invframe.getInvoices().get(selectInvoice);
              LineClass line= new LineClass(item, count, price,invoice);
              invoice.getLine().add(line);
              TableOfLinesModel tableOfLinesModel = (TableOfLinesModel) invframe.getLineTable().getModel();
              //tableOfLinesModel.getLines().add(line);
              tableOfLinesModel.fireTableDataChanged();
              invframe.getTableofinvoicemodel().fireTableDataChanged();
        }
       lineDialog.setVisible(false);
        lineDialog.dispose();
        lineDialog = null;
        
      
    }

    private void createNewLineCancel() {
        lineDialog.setVisible(false);
        lineDialog.dispose();
        lineDialog = null;
    }

   

    
}
