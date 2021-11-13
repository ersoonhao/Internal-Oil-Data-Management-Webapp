package com.oop.project.model;

import javax.persistence.*;

@Entity
@Table(name = "china_data")
public class ChinaData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "type")
	private String type;

	@Column(name = "category")
	private String category;

	@Column(name = "year")
	private int year;

	@Column(name = "month")
	private int month;

	@Column(name = "quantity_unit")
	private int quantity_unit;

	@Column(name = "raw_quantity")
	private int raw_quantity;

	@Column(name = "final_quantity")
	private double final_quantity;

	@Column(name = "value")
	private int value;

	@Column(name = "price")
	private double price;

	public ChinaData() {

	}

	public ChinaData(String category, String type, int year, int month, int quantity_unit, int raw_quantity,
			double final_quantity, int value, double price) {
		this.category = category; // e.g. Gasoline
		this.type = type; // Import/Export
		this.year = year;
		this.month = month;
		this.quantity_unit = quantity_unit;
		this.raw_quantity = raw_quantity;
		this.value = value;
		this.final_quantity = final_quantity;
		this.price = price;
	}

	public long getId() {
		return id;
	}

	public String getType() {
		return type;
	}

	public String getCategory() {
		return category;
	}

	public int getMonth() {
		return month;
	}

	public int getYear() {
		return year;
	}

	public int getQuantity_unit() {
		return quantity_unit;
	}

	public int getRaw_quantity() {
		return raw_quantity;
	}

	public double getFinal_quantity() {
		return final_quantity;
	}

	public int getValue() {
		return value;
	}

	public double getPrice() {
		return price;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public void setQuantity_unit(int quantity_unit) {
		this.quantity_unit = quantity_unit;
	}

	public void setRaw_quantity(int raw_quantity) {
		this.raw_quantity = raw_quantity;
	}

	public void setFinal_quantity(double final_quantity) {
		this.final_quantity = final_quantity;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Export [id=" + id + ", type=" + type + ", category=" + category + ", year=" + year + ", month=" + month + ", qty=" + raw_quantity
				+ "]";
	}

}
