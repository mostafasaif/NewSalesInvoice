
package salesinvoice.model;

import java.util.ArrayList;


public class LineClass {
   // private int Number;
    private String Item;
    private int Count;
    private double Price;
    private InvoiceClass Invoice;
   // private ArrayList<LineClass> Lines;

    public LineClass() {
    }

    public LineClass(int Number, String Item, int Count, double Price) {
       // this.Number = Number;
        this.Item = Item;
        this.Count = Count;
        this.Price = Price;
    }
    public double getTotalLine()
    {
        return Price * Count;
    }

    public LineClass( String Item, int Count, double Price, InvoiceClass Invoice) {
       // this.Number = Number;
        this.Item = Item;
        this.Count = Count;
        this.Price = Price;
        this.Invoice = Invoice;
    }

    public InvoiceClass getInvoice() {
        return Invoice;
    }

   
    

   /* public int getNumber() {
        return Number;
    }*/

    public String getItem() {
        return Item;
    }

    public int getCount() {
        return Count;
    }

    public double getPrice() {
        return Price;
    }

   /* public void setNumber(int Number) {
        this.Number = Number;
    }*/

    public void setItem(String Item) {
        this.Item = Item;
    }

    public void setCount(int Count) {
        this.Count = Count;
    }

    public void setPrice(double Price) {
        this.Price = Price;
    }

    @Override
    public String toString() {
        return "LineClass{" + "Number=" + Invoice.getNumber() + ", Item=" + Item + ", Count=" + Count + ", Price=" + Price + '}';
    }
    
    public String getCsvFile()
    {
        return Invoice.getNumber() + "," + Item + "," + Count+ "," + Price;
    }
    
}
