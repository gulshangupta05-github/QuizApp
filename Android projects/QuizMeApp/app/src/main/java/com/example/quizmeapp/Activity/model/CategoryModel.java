package com.example.quizmeapp.Activity.model;

public class CategoryModel {
    private  String categoryId, CategoryName,categoryImage;

    public CategoryModel(String categoryId, String categoryName, String categoryImage) {
        this.categoryId = categoryId;
        CategoryName = categoryName;
        this.categoryImage = categoryImage;
    }

    public  CategoryModel(){

    }
    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public String getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }


}
