package org.easy.boot.log.log.core.model;

import org.easy.boot.log.log.core.annotation.Param;
import org.easy.boot.log.log.core.annotation.Search;

/**
* 公司模型  
* @author : KangNing Hu
*/
public class Company {

	private String id;


	@Param("companyName")
	private String name;


	@Search
	private Address address;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
}
