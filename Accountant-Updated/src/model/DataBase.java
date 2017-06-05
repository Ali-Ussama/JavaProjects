package model;

import java.sql.*;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import javafx.scene.control.TextField;

public class DataBase {
	 public static Connection mconnect;
	 public static Statement mstatement;
	 public static ResultSet mResultset;
	 private String Query = null;
	 
	public DataBase(){
		try {
			mconnect = DriverManager.getConnection("jdbc:mysql://localhost:3306/accounting?autoReconnect=true&useSSL=false" , "root" , "");
			mstatement = mconnect.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean CheckBarcode(String StrBarcode) throws SQLException{
		mResultset = SelectRow("product", "barcode", StrBarcode, 0);
		if(!mResultset.next()){
			return false;
		}
		return true;
	}
	public int GetID(String Name,String TableName){
		String Query;
		try {
			Query = "select id from "+TableName+" where name = '"+Name+"'";
			mResultset = mstatement.executeQuery(Query);
			while(mResultset.next())
				return mResultset.getInt("id");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
    public ResultSet searchSSN(String key, String table_Name, String StrInput) throws SQLException {
        if (key.matches("SSN")) {
        	int a = Integer.parseInt(StrInput);
        	if(table_Name.matches("purchase_bill")){
            Query = "select "+table_Name+".ssn,"+table_Name+".total,"+table_Name+".date_time,"+table_Name+".bill_type,"
            		+ "supplier.name from " + table_Name + ", supplier where "+table_Name+".ssn = "+a+ " and "
            		+table_Name+".supplier_id = supplier.id order by ssn";
        	}else{
        		Query = "select * from "+table_Name+" where ssn = "+a;
        	}
                return mResultset = mstatement.executeQuery(Query);

        } else if (key.matches("Date")){
        	if(table_Name.matches("sales_bill"))
            Query = "select * from " + table_Name + " where date_time like '%" + StrInput + "%' order by ssn";
        	else
        	Query = "select "+table_Name+".ssn,"+table_Name+".total,"+table_Name+".date_time,"+table_Name+".bill_type,"
                		+ "supplier.name from " + table_Name + ", supplier where date_time like '%" +StrInput + "%' and "
                		+table_Name+".supplier_id = supplier.id order by ssn";
        	
            return mResultset = mstatement.executeQuery(Query);
        }
        return null;
    }
    public ResultSet SelectBill(String table_Name) throws SQLException {
    	if(table_Name.matches("purchase_bill"))
        	Query = "select "+table_Name+".ssn,"+table_Name+".total,"+table_Name+".date_time,"+table_Name+".bill_type,"
                		+ "supplier.name from " + table_Name + ", supplier where "+table_Name+".supplier_id = supplier.id order by "
                				+ table_Name+".ssn";
    	else
    		Query = "select * from " + table_Name + " order by ssn";
        	
            return mResultset = mstatement.executeQuery(Query);
    }
	public void Delete(int ID,String TableName)throws SQLException{
		Query = "delete from "+TableName+" where id = "+ID;
		mstatement.executeUpdate(Query);
	}
	public ResultSet SelectTable(String TableName) throws SQLException{
		Query = "select * from "+TableName;
		return mResultset = mstatement.executeQuery(Query);
	}
	public ResultSet SelectRow(String Table,String condition,String StrValue,int IntValue) throws SQLException{
		if(StrValue != null)
			Query = "select * from "+Table+" where "+condition+" = '"+StrValue+"'";
		else
			Query = "select * from "+Table+" where "+condition+" = "+IntValue;

		return mResultset = mstatement.executeQuery(Query);
	}
	public ResultSet SelectProduct(String Barcode) throws SQLException{
		Query = "select product.barcode,product.name,product.quantity,product.supplier_price,product.customer_price,major_brand.name,"
				+ "sub_brand.name,unit.name "
				+ "from product,major_brand,sub_brand,unit "
				+ "where product.major_id = major_brand.id and product.sub_id = sub_brand.id and product.unit_id = unit.id "
				+ "and product.barcode = '"+Barcode+"'";
		return mResultset = mstatement.executeQuery(Query);
	}
	public ResultSet SelectAllProducts() throws SQLException{
		Query = "select product.barcode,product.name,product.quantity,product.supplier_price,product.customer_price,major_brand.name,"
				+ "sub_brand.name,unit.name,supplier.name "
				+ "from product,major_brand,sub_brand,unit,supplier "
				+ "where product.major_id = major_brand.id and product.sub_id = sub_brand.id and product.unit_id = unit.id "
				+ "and supplier_id = supplier.id order by product.barcode";
		return mResultset = mstatement.executeQuery(Query);
	}
	public void InsertSupplier(int id,String name,String Phone,String Address) throws SQLException{
		Query = "insert into supplier (id,name,phone,address) "
				+ "values("+id+",'"+name+"','"+Phone+"','"+Address+"')";
		mstatement.executeUpdate(Query);
	}
	public void InsertMajorBrand(String name) throws SQLException{
		mResultset = SelectTable("major_brand");
		while(mResultset.next())
			if(name.matches(mResultset.getString("Name")))
				throw new SQLException();

		Query = "insert into major_brand (name) values ('"+name+"')";
		mstatement.executeUpdate(Query);
	}
	public void InsertSubBrand(int id,String name) throws SQLException{
		Query = "insert into sub_brand (id,name) values ("+ id +",'"+name+"')";
		mstatement.executeUpdate(Query);
	}
	public void InsertUnit(int id,String name)throws SQLException{
		Query = "insert into unit (id,name) values("+id+",'"+name+"')";
		mstatement.executeUpdate(Query);
	}
	public void InsertProduct(String Barcode,String Name,int Quantity,int CustomerPrice,int SupplierPrice,int majorbrand_id,int subbrand_id,int unit_id,int supplier_id) throws SQLException{
    	Query = "insert into product (barcode,name,quantity,customer_price,supplier_price,major_id,sub_id,unit_id,supplier_id) "
    			+ "values ('"+Barcode+"','"+Name+"',"+Quantity+","+CustomerPrice+","+SupplierPrice+","+majorbrand_id+","
    			+ subbrand_id+","+unit_id+","+supplier_id+")";
    	mstatement.executeUpdate(Query);
    }
	public void InsertSalesBill(int Total,ArrayList<String> Barcodes,String Date_Time,String BillType) throws SQLException{
		//Insert Sales Bill
		int Profit = 0,SSN = 1,CustomerPrice = 0,SupplierPrice = 0,Quantity = 0;
		for(int i = 0 ; i < Barcodes.size() ; i++){
			Query = "select customer_price,supplier_price from product where barcode = '"+Barcodes.get(i)+"'";
			mResultset = mstatement.executeQuery(Query);
			while(mResultset.next())
					Profit += (mResultset.getInt("customer_price") - mResultset.getInt("supplier_price"));
		}
		if(BillType != "Cash"){
			Total  *= -1;
			Profit *= -1;
		}
		for(int i = 0 ; i < Barcodes.size(); i++){
				Query = "select quantity from product where barcode = '"+Barcodes.get(i)+"'";
				mResultset = mstatement.executeQuery(Query);
				while(mResultset.next())
							Quantity = mResultset.getInt("quantity");
							if(Quantity==0 && BillType.matches("Cash")){
								JOptionPane.showMessageDialog(null, "Inventory is Empty");
								return;
							}
				if(BillType.matches("Cash"))
				Query = "update product set quantity = "+ --Quantity +" where barcode = '"+Barcodes.get(i)+"'";
				else
					Query = "update product set quantity = "+ ++Quantity +" where barcode = '"+Barcodes.get(i)+"'";
				mstatement.executeUpdate(Query);
		}
		Query = "insert into sales_bill (total,profit,date_time,bill_type) values("+Total+","+Profit+",'"+Date_Time+"','"+BillType+"')";
		mstatement.executeUpdate(Query);
		//Insert Sales Bill Items
		Query = "select ssn from sales_bill where date_time = '"+Date_Time+"'";
		mResultset = mstatement.executeQuery(Query);
		while(mResultset.next())
					SSN = mResultset.getInt("ssn");
		System.out.println("SSN = " +SSN);
		for(int i = 0 ; i < Barcodes.size(); i++){
			Query = "insert into sales_bill_items (product_barcode,sales_bill_ssn) values("+Barcodes.get(i)+" , "+SSN+")";
			mstatement.executeUpdate(Query);
		}
	}
	public void InsertPurchaseBill(int Total,ArrayList<String> Barcodes,String Date_Time,String BillType,String Supplier) throws SQLException{
		//Insert Sales Bill
				int SSN = 1,Supplier_id = 0;
				if(BillType != "Cash"){
					Total  *= -1;
				}
				Query = "select id from supplier where name ='"+Supplier+"'";
				mResultset = mstatement.executeQuery(Query);
				while(mResultset.next())
					Supplier_id = mResultset.getInt("id");
				
				System.out.println(Supplier_id);
				
				Query = "insert into purchase_bill (total,date_time,bill_type,supplier_id) values("+Total+",'"+Date_Time+"','"+BillType+"',"+Supplier_id+")";
				mstatement.executeUpdate(Query);
				//Insert Sales Bill Items
				Query = "select ssn from purchase_bill where date_time = '"+Date_Time+"'";
				mResultset = mstatement.executeQuery(Query);
				while(mResultset.next())
							SSN = mResultset.getInt("ssn");
				System.out.println("SSN = " +SSN);
				for(int i = 0 ; i < Barcodes.size(); i++){
					Query = "insert into purchase_bill_items (product_barcode,purchase_bill_ssn) values("+Barcodes.get(i)+" , "+SSN+")";
					mstatement.executeUpdate(Query);
				}
	}
	public void UpdateSupplier(int id,String name,String Phone,String Address) throws SQLException{
		Query = "update supplier set name = '"+name+"', phone = '"+Phone+"', address = '"+Address+"' where id = "+ id;
		mstatement.executeUpdate(Query);
	}
	public void UpdateMajorBrand(int id,String name) throws SQLException{
		Query = "update major_brand set name = '"+name+"' where id = " + id;
		mstatement.executeUpdate(Query);
	}
	public void UpdateSubBrand(int id,String name) throws SQLException{
		Query = "update sub_brand set name = '"+name+"' where id = " + id;
		mstatement.executeUpdate(Query);
	}
	public void UpdateUnit(int id,String name)throws SQLException{
		Query = "update unit set name ='"+name+"' where id = "+id;
		mstatement.executeUpdate(Query);
	}
    public void UpdateProduct(String Barcode,String Name,int Quantity,int CustomerPrice,int SupplierPrice,int majorbrand_id,int subbrand_id,int unit_id,int supplier_id) throws SQLException{
    	Query = "update product set name = '"+Name+"', quantity = "+Quantity+",customer_price = "+CustomerPrice+
    			",supplier_price = "+SupplierPrice+", major_id ="+majorbrand_id+", sub_id ="+subbrand_id+",unit_id="+unit_id
    			+", supplier_id = "+supplier_id+" where barcode = '"+Barcode+"' ";
    	mstatement.executeUpdate(Query);
    }
    public void DeleteProduct(String barcode) throws SQLException{
    	Query = "delete from product where barcode = '"+barcode+"'";
    	mstatement.executeUpdate(Query);
    }
    public void DeleteMajorBrand(String Name) throws SQLException{
    	Query = "delete from major_brand where name = '"+Name+"' limit 1";
    	mstatement.executeUpdate(Query);
    }
    public void DeleteSubBrand(int id) throws SQLException{
    	Query = "delete from sub_brand where id = "+id;
    	mstatement.executeUpdate(Query);
    }
    public void DeleteUnit(int id) throws SQLException{
    	Query = "delete from unit where id = "+id;
    	mstatement.executeUpdate(Query);
    }
}
