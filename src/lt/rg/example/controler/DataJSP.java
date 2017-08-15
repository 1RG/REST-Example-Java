package lt.rg.example.controler;

import java.util.LinkedList;

import lt.rg.example.model.DB_Queries;

public class DataJSP {
	public LinkedList<Element> getTable() {
		System.out.println("is Get(jsp) all element");
		
		DB_Queries db = new DB_Queries();
		return db.getAllData();
	}
	
	public LinkedList<Spalva> getSpalvaTable(){
		System.out.println("is Get(jsp) all spalva");
		
		DB_Queries db = new DB_Queries();
		return db.getAllSpalva();
	}
}
