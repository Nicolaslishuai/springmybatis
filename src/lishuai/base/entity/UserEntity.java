package lishuai.base.entity;

import org.apache.ibatis.type.Alias;

@Alias("User")
public class UserEntity  {

	private String id;
	
	private String username;
	
	private int age;
	
	private String address;
	
	private String password;
	
	private String enabled;
	
	

	public UserEntity() {
		super();
	}

	public UserEntity(String id, String username, int age, String address) {
		super();
		this.id = id;
		this.username = username;
		this.age = age;
		this.address = address;
	}



	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	@Override
	public String toString() {
		return "id:"+id+"|username:"+username+"|age:"+age+"|address:"+address;
	}
	
	

}
