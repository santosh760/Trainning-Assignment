package com.santosh;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * This class will provide all service method for PEM
 * @author Santosh Pandey
 *
 */
public class PEMService {
	
	Repositoy repo=Repositoy.getRepository();
	
	ReportService reportService=new ReportService();
	
	private Scanner in=new Scanner(System.in);
	private int choice;
	
	public PEMService() {
		
		prepareSampleData();		
	}
	
	/**
	 * This method will display the Menu
	 */
	public void showMenu(){
		
		while (true){
			printMenu();
			
			switch (choice) {
			
			case 1:
					onAddCategory();
					pressAnyKeyToCountinue();
				break;
			
			case 2:
				onCategoryList();
				pressAnyKeyToCountinue();
			break;
			
			case 3:
				onExpenseEntry();
				pressAnyKeyToCountinue();
			break;
			
			case 4:
				onExpenseList();
				pressAnyKeyToCountinue();
			break;
			
			case 5:
				onReportMonthly();
				pressAnyKeyToCountinue();
			break;
			
			case 6:
				onReportYearly();
				pressAnyKeyToCountinue();
			break;
			
			case 7:
				onReportCategoryWise();
				pressAnyKeyToCountinue();
			break;
			
			case 0:
					System.exit(0);
					break;
					
			default:
					System.out.println("Invalid Entry");
				break;
			}
		}
	}
	
	/**
	 * This method will create Menu item
	 */
	public void printMenu(){
		System.out.println("---------PEM Application---------");
		System.out.println("1. Add Category");
		System.out.println("2. Category List");
		System.out.println("3. Expense Entry");
		System.out.println("4. Expense List");
		System.out.println("5. Report --Monthly");
		System.out.println("6. Report --Yearly");
		System.out.println("7. Report --Category Wise");
		System.out.println("0. Exit");
		System.out.println("----------------------------------");
		System.out.print("Enter Your Choice : ");
		choice=in.nextInt();
	}

	/**
	 * This method will add category into List
	 */
	public void onAddCategory(){
		in.nextLine();
		System.out.print("Enter Category Name :");
		String catName=in.nextLine();
		Category cat=new Category(catName);
		repo.catList.add(cat);
		System.out.println("Category Name Added...");
		
	}
	
	/**
	 * This method will List all the category
	 */
	public void onCategoryList(){
		System.out.println("Category List");
		List<Category> category=repo.catList;
		for(int i=0;i<category.size();i++){
			Category c=category.get(i);
			System.out.println((i+1)+"---"+ c.getName()+"---"+c.getCategoryId());
		}
		
	}
	
	/**
	 * This method will add expense into the category
	 */
	public void onExpenseEntry(){
		System.out.println("Enter Details For Expense Entry :");
		onCategoryList();
		System.out.print("Choose Category :");
		int catChoice=in.nextInt();
		Category selectedCat= repo.catList.get(catChoice-1);
		
		System.out.print("Enter Amouut : ");
		float amount=in.nextFloat();
		
		System.out.print("Enter Remark : ");
		in.nextLine();
		String remark=in.nextLine();
		
		System.out.print("Enter Date in DD/MM/YY format :");
		String dateAsString=in.nextLine();
		Date date=DateUtil.stringToDate(dateAsString);
		
		Expense exp=new Expense();
		exp.setCategoryId(selectedCat.getCategoryId());
		exp.setAmount(amount);
		exp.setRemark(remark);
		exp.setDate(date);
		
		repo.expList.add(exp);
		System.out.println("Expense Added...");
		
	}
	
	/**
	 * This method will list all the Expense
	 */
	public void onExpenseList(){
		System.out.println("Expense List");
		List<Expense> expList=repo.expList;
		for(int i=0;i<expList.size();i++){
			Expense exp=expList.get(i);
			String catName=reportService.getCategoryNameById(exp.getCategoryId());
			String dateString=DateUtil.dateToString(exp.getDate());
			System.out.println((i+1)+"---"+catName+"---"+exp.getAmount()+"---"+dateString);
		}
		
	}
	
	/**
	 * This methos will report expense on monthly basis
	 */
	public void onReportMonthly(){
		System.out.println("Monthly Expense Total..");
		Map<String,Float> resultMap=reportService.calculateMonthlyTotal();
		Set<String> keys=resultMap.keySet();
		for(String yearMoth: keys){
			
			String[] arr=yearMoth.split(",");
			String year=arr[0];
			Integer monthno=new Integer(arr[1]);
			String monthName=DateUtil.getMonthName(monthno);
			System.out.println(year+",  "+monthName+" , "+resultMap.get(yearMoth));
		}
	}
	
	/**
	 * This methos will report expense on yearly basis
	 */
	public void onReportYearly(){
		System.out.println("Yearly Expense Total...");
		Map<Integer, Float> resultMap=reportService.calculateYearlyTotal();
		
		Set<Integer> keys=resultMap.keySet();
		Float total=0.0f;
		for(Integer year: keys){
			Float exp=resultMap.get(year);
			total=total+exp;
			System.out.println(year+" , "+exp);
		}
		System.out.println("----------------");
		System.out.println("Total expense in INR : "+total );
	}
	
	/**
	 * This methos will report expense on Category Wise
	 */
	public void onReportCategoryWise(){
		System.out.println("Category Wise Expense");
		Map<String,Float> resultMap=reportService.calculateCategorizedTotal();
		Set<String> key=resultMap.keySet();
		for(String categoryName:key){
			System.out.println(categoryName+" , "+resultMap.get(categoryName));
		}
		
		
	} 
	
	/**
	 * This method will ask user to press enter to countinue
	 */
	public void pressAnyKeyToCountinue(){
		System.out.println("Press any key to countinue");
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method will prepare some sample data into List
	 */
	private void prepareSampleData() {
		
		Category c1=new Category("Mobile Bill");
		delay();
		Category c2=new Category("Electricity Bill");
		delay();
		Category c3=new Category("Party Bill");
		delay();
		
		repo.catList.add(c1);
		repo.catList.add(c2);
		repo.catList.add(c3);
		
		Expense e1=new Expense(c1.getCategoryId(), 300.0F, DateUtil.stringToDate("2/2/2017"), "Mobile Bill Expense");
		delay();
		Expense e2=new Expense(c2.getCategoryId(), 300.0F, DateUtil.stringToDate("2/12/2017"), "Electric Bill Expense");
		delay();
		Expense e3=new Expense(c1.getCategoryId(), 300.0F, DateUtil.stringToDate("2/2/2018"), "Party Bill Expense");
		delay();
		
		repo.expList.add(e1);
		repo.expList.add(e2);
		repo.expList.add(e3);
		
	}
	
	/**
	 * This method will delay the process of input data so that every category got different id
	 */
	public void delay(){
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
