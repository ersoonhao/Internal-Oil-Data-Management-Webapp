package com.oop.project.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;

@Entity
@Table(name = "newzealand_data")
public class NewZealandData {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @Column(name = "productGroup")
    private String productGroup;
    // 

    @Column(name = "type")
    private String type;
    // type: import, export, etc

    @Column(name = "quantity")
    private double quantity;

    @Column(name = "month")
    private Integer month;

    @Column(name = "year")
    private Integer year;

    public NewZealandData() {

    }

    public NewZealandData(String productGroup, String type, double quantity, Integer month, Integer year) {
        this.productGroup = productGroup;
        this.type = type;
        this.quantity = quantity;
        this.month = month;
        this.year = year;
    }

    public String getProductGroup() {
        return productGroup;
    }

    public void setProductGroup(String productGroup) {
        this.productGroup = productGroup;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    
}

