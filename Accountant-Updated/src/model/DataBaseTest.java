package model;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Test;

import controller.PurchaseBillAction;
import javafx.scene.control.TextField;

public class DataBaseTest {
	private DataBase mDataBase;
	private ResultSet mResultSet;
	String []Key = {"SSN","Date"};
	String []TableName={"sales_bill","purchase_bill"};
	private PurchaseBillAction mPurchaseBillAction;
	public static ArrayList<String> Arr ;
	
	public DataBaseTest() {
		mDataBase = new DataBase();
	}
	
	@Test
	public void testSearchSSN() throws SQLException,Exception {
			//SalesBills.
			mResultSet = mDataBase.searchSSN(Key[0], TableName[0], "18");
			while(mResultSet.next()){
						assertEquals(150, mResultSet.getInt("total"));
			}
			mResultSet = mDataBase.searchSSN(Key[0], TableName[0], "15");
			while(mResultSet.next()){
						assertEquals(90, mResultSet.getInt("total"));
			}
			mResultSet = mDataBase.searchSSN(Key[1], TableName[0], "2016/12/25");
			while(mResultSet.next()){
						assertEquals(245, mResultSet.getInt("total"));
			}
			mResultSet = mDataBase.searchSSN(Key[1], TableName[0], "2016/12/22");
			while(mResultSet.next()){
						assertEquals(150, mResultSet.getInt("total"));
			}
			//PurchaseBills.
			mResultSet = mDataBase.searchSSN(Key[0], TableName[1], "1");
			while(mResultSet.next()){
						assertEquals(335, mResultSet.getInt("purchase_bill.total"));
			}
			mResultSet = mDataBase.searchSSN(Key[0], TableName[1], "2");
			while(mResultSet.next()){
						assertEquals("ahmed", mResultSet.getString("supplier.name"));
			}
			mResultSet = mDataBase.searchSSN(Key[1], TableName[1], "2016/12/21");
			while(mResultSet.next()){
				assertEquals(335, mResultSet.getInt("purchase_bill.total"));
			}
			mResultSet = mDataBase.searchSSN(Key[1], TableName[1], "2016/12/23");
			while(mResultSet.next()){
				assertEquals(340, mResultSet.getInt("total"));
			}
		
	}
	
	@Test
	public void testSelectBill() throws SQLException ,Exception{
		int[] SalesBillSSN	  = {15,16,17,18,19,20}, 
			  PurchasBilleSSN = {1,2};
		for(int k = 0 ; k < TableName.length ; k++){
			int index = 0 ;
				mResultSet = mDataBase.SelectBill(TableName[k]);
				  while (mResultSet.next()) {
					  
						if(TableName[k] == "sales_bill" && index < SalesBillSSN.length ){
							assertEquals(SalesBillSSN[index++], mResultSet.getInt("ssn"));
							System.out.println(TableName[k] + " " + index);
						}else if(TableName[k] == "purchase_bill" && index < PurchasBilleSSN.length )
							assertEquals(PurchasBilleSSN[index++], mResultSet.getInt("purchase_bill.ssn"));
				  }
		}
	}

	@Test
	public void testSelectTable() throws SQLException,Exception {
		mResultSet = mDataBase.SelectTable("major_brand");
		  while (mResultSet.next()) {
			  if(mResultSet.getInt("id") == 1)
			  	assertEquals("T-shirt", mResultSet.getString("name"));
			  if(mResultSet.getInt("id") == 2)
				  	assertEquals("short", mResultSet.getString("name"));
		  }
	}

	@Test
	public void testSelectRow() throws SQLException,Exception {
		mResultSet = mDataBase.SelectRow("supplier", "address", "badr", 0);
		while(mResultSet.next()){
			if(mResultSet.getInt("id") == 1)
			  	assertEquals("ali osama", mResultSet.getString("name"));
			else if(mResultSet.getInt("id") == 2)
				  	assertEquals("ahmed", mResultSet.getString("name"));
		}
		
		mResultSet = mDataBase.SelectRow("supplier", "address", null, 2);
		while(mResultSet.next()){
				  	assertEquals("ahmed", mResultSet.getString("name"));
		}
	}

	@Test
	public void testSelectProduct() throws SQLException,Exception{
		mResultSet = mDataBase.SelectProduct("106");
		while(mResultSet.next()){
		  	assertEquals("H&M", mResultSet.getString("name"));
		}
	}

	@Test
	public void testSelectAllProducts()  throws SQLException,Exception{
		mResultSet = mDataBase.SelectAllProducts();
		while(mResultSet.next()){
			if(mResultSet.getString("barcode").matches("106"))
		  	assertEquals("H&M", mResultSet.getString("name"));
			else if(mResultSet.getString("barcode").matches("100"))
		  	assertEquals(90, mResultSet.getInt("customer_price"));
		}
	}
	
	@Test
	public void testGetID() throws SQLException,Exception{
		int ID;
		ID = mDataBase.GetID("T-shirt","major_brand");
		  	assertEquals(1, ID);
		ID = mDataBase.GetID("bagy","sub_brand");
			assertEquals(2, ID);
	}
	
	@Test
	public void testCheckBarcode() throws SQLException,Exception{
		boolean Check;
		Check = mDataBase.CheckBarcode("103");
		assertEquals(true,Check);
		Check = mDataBase.CheckBarcode("107");
		assertEquals(false,Check);
	}
	
	@Test
	public void testFillSupplierList() throws Exception{
		 Arr = new ArrayList<>();
		 mPurchaseBillAction = new PurchaseBillAction();
		 Arr = mPurchaseBillAction.FillSupplierList(Arr);
		assertEquals("ali osama",Arr.get(0));
		assertEquals("ahmed",Arr.get(1));
	}

}
