package model;

import java.util.Calendar;

import annotations.NotNull;

public class Coupon {

	@NotNull
	private int id;
	@NotNull
	private int companyId;
	@NotNull
	private Category category;
	private String title;
	private String description;
	@NotNull
	private Calendar startDate;
	@NotNull
	private Calendar endDate;
	@NotNull
	private int amount;
	@NotNull
	private double price;
	private String image;

	/**
	 * note: that this Constructor pre-sets startDate and endDate's
	 * hour,minute,second,and millisecond to 00-00-00-00 before defining them.
	 */
	public Coupon(int copamyId, Category category, String title, String description, Calendar startDate,
			Calendar endDate, int amount, double price, String image) {
		this.companyId = copamyId;
		this.category = category;
		this.title = title;
		this.description = description;
		startDate = setTime(startDate);
		this.startDate = startDate;
		endDate = setTime(endDate);
		this.endDate = endDate;
		this.amount = amount;
		this.price = price;
		this.image = image;
	}

	/**
	 * this function receives a time parameter and then sets its'
	 * HOUR,MINUTE,SECOND and MILLISECOND values to 0;
	 * 
	 * @param time - Calendar-typed.
	 * @return {@link java.util.Calendar #set(Calendar)}
	 */
	private Calendar setTime(Calendar time) {
		time.set(Calendar.HOUR, 0);
		time.set(Calendar.MINUTE, 0);
		time.set(Calendar.SECOND, 0);
		time.set(Calendar.MILLISECOND, 0);
		return time;
	}

	public Coupon(int id, int copamyId, Category category, String title, String description, Calendar startDate,
			Calendar endDate, int amount, double price, String image) {
		this(copamyId, category, title, description, startDate, endDate, amount, price, image);
		this.id = id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Calendar getStartDate() {
		return startDate;
	}

	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}

	public Calendar getEndDate() {
		return endDate;
	}

	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getId() {
		return id;
	}

	public int getCompanyId() {
		return companyId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((category == null) ? 0 : category.hashCode());
		result = prime * result + companyId;
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + id;
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coupon other = (Coupon) obj;
		if (category != other.category)
			return false;
		if (companyId != other.companyId)
			return false;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (id != other.id)
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "coupone [id=" + id + ", copamyId=" + companyId + ", category=" + category + ", title=" + title
				+ ", description=" + description + ", startDate=" + getTime(startDate) + ", endDate=" + getTime(endDate)
				+ ", amount=" + amount + ", price=" + price + ", image=" + image + "]";
	}

	/**
	 * this function receives a time parameter and gets the time object's YEAR,MONTH
	 * AND DAY;
	 * 
	 * @param time - Calendar-typed.
	 * @return a String representation of time's year, month and day.
	 * @see {@link #getFullNumber(int)}
	 * 
	 */
	private String getTime(Calendar time) {
		return getFullNumber(time.get(Calendar.DAY_OF_MONTH)) + "/" + getFullNumber(time.get(Calendar.MONTH)) + "/"
				+ getFullNumber(time.get(Calendar.YEAR));
	}

	/**
	 * this function checks if the received num parameter is smaller than 9, and if it IS, it
	 * presents it as a Double-digited number.
	 * 
	 * @param num - int-typed.
	 * @return a number that is double-digited if its smaller than 9 as a String
	 *         (for example 3 will be presented as "03") .
	 */
	private String getFullNumber(int num) {
		return num > 9 ? num + "" : "0" + num;
	}

}
