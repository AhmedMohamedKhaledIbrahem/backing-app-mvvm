package com.ahmedelfeky.backingapp.Model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class IngredientsItem implements Serializable , Parcelable {

	@SerializedName("quantity")
	private double quantity;

	@SerializedName("measure")
	private String measure;

	@SerializedName("ingredient")
	private String ingredient;

	protected IngredientsItem(Parcel in) {
		quantity = in.readDouble();
		measure = in.readString();
		ingredient = in.readString();
	}

	public static final Creator<IngredientsItem> CREATOR = new Creator<IngredientsItem>() {
		@Override
		public IngredientsItem createFromParcel(Parcel in) {
			return new IngredientsItem(in);
		}

		@Override
		public IngredientsItem[] newArray(int size) {
			return new IngredientsItem[size];
		}
	};

	public void setQuantity(double quantity){
		this.quantity = quantity;
	}

	public double getQuantity(){
		return quantity;
	}

	public void setMeasure(String measure){
		this.measure = measure;
	}

	public String getMeasure(){
		return measure;
	}

	public void setIngredient(String ingredient){
		this.ingredient = ingredient;
	}

	public String getIngredient(){
		return ingredient;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeDouble(quantity);
		dest.writeString(measure);
		dest.writeString(ingredient);
	}
}