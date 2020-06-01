package model;

import java.util.ArrayList;
import java.util.List;

import annotations.NotNull;
/**
 *	this class presents all the information that a Company-typed object has. (attributes,methods, etc.)  
 * @author o_g_i
 *
 */
public class Company {
	@NotNull
	private int id;
	@NotNull
	private String name;
	@NotNull
	private String email;
	@NotNull
	private String password;
	List<Coupon> coupons;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((coupons == null) ? 0 : coupons.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
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
		Company other = (Company) obj;
		if (coupons == null) {
			if (other.coupons != null)
				return false;
		} else if (!coupons.equals(other.coupons))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Company [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + "]";
	}

	public Company(int id, String name, String email, String password) {
		this(name, email, password);
		this.id = id;

	}
	/**
	 * This second Constructor instantiates a coupons list.
	 * @param name - String-typed.
	 * @param email - String-typed.
	 * @param password - String-typed.
	 */
	public Company(String name, String email, String password) {
		coupons = new ArrayList<>();
		this.name = name;
		this.email = email;
		this.password = password;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Coupon> getCoupons() {
		return coupons;
	}
}
