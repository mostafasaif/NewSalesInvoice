
package salesinvoice.model;

import java.util.ArrayList;


public class InvoiceClass {
    private int Number;
    private String Date;
    private String Customer;
    private ArrayList<LineClass> Line ;

    public InvoiceClass() {
        
    }

    public InvoiceClass(int Number, String Date, String Customer) {
        this.Number = Number;
        this.Date = Date;
        this.Customer = Customer;
    }
    
    public double getTotalOfInvoice()
    {
        double total =0.0;
        for(LineClass line : getLine())
        {
            total +=line.getTotalLine();
        }
        return total;
    }

    public ArrayList<LineClass> getLine() {
        if(Line == null)
        {
            Line=new ArrayList<>();
        }
        return Line;
    }
    

    public int getNumber() {
        return Number;
    }

    public String getDate() {
        return Date;
    }

    public String getCustomer() {
        return Customer;
    }

    public void setNumber(int Number) {
        this.Number = Number;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public void setCustomer(String Customer) {
        this.Customer = Customer;
    }

    @Override
    public String toString() {
        return "InvoiceClass{" + "Number=" + Number + ", Date=" + Date + ", Customer=" + Customer + ", Line=" + Line + '}';
    }
    
    public String getCsvFile()
    {
        return Number + "," + Date + "," + Customer;
    }
}
