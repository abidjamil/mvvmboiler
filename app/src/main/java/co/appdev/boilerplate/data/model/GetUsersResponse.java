package co.appdev.boilerplate.data.model;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class GetUsersResponse{

	@SerializedName("Users")
	private List<User> users;

	public void setUsers(List<User> users){
		this.users = users;
	}

	public List<User> getUsers(){
		return users;
	}

	@Override
 	public String toString(){
		return 
			"User{" + 
			"users = '" + users + '\'' + 
			"}";
		}
}