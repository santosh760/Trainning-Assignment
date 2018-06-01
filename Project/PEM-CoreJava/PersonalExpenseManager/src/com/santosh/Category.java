package com.santosh;

/**
 * This class is used as POJO for Category
 * @author Santosh Pandey
 *
 */
public class Category {
	
	/**
	 * categoryId is id for Category
	 * Auto Generated by System
	 */
	private Long categoryId = System.currentTimeMillis();
	
	/**
	 * Category Name
	 */
	private String name;
	
	public Category(String name) {
		super();
		this.name = name;
	}

	public Category(Long categoryId, String name) {
		super();
		this.categoryId = categoryId;
		this.name = name;
	}
	
	public Category() {
		
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}