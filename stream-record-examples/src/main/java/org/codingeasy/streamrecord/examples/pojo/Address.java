package org.codingeasy.streamrecord.examples.pojo;


import org.codingeasy.streamrecord.core.annotation.Param;
import org.codingeasy.streamrecord.core.annotation.Search;

/**
* 地址模型  
* @author : KangNing Hu
*/
public class Address {



	private String addressId;


	@Param
	@Search
	private String addr;


	@Param
	private String cityCode;


	public String getAddressId() {
		return addressId;
	}

	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
}
