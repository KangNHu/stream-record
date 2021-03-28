package org.dream.tool.auditlog.test.model;

import org.dream.tool.auditlog.annotation.Param;
import org.dream.tool.auditlog.annotation.Search;

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
