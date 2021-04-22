package org.easy.boot.log.log.examples.pojo;


import org.dream.tool.auditlog.core.annotation.Param;
import org.dream.tool.auditlog.core.annotation.Search;

/**
* 用户模型  
* @author : KangNing Hu
*/
public class User {

	private String Id;


	@Param
	private String name;



	@Search
	private Company company;


	public void setId(String id) {
		Id = id;
	}

	public String getId() {
		return Id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
}
