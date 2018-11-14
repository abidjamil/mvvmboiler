package co.appdev.boilerplate.data.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class User{

	@SerializedName("name")
	private String name;

	@SerializedName("age")
	private int age;

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setAge(int age){
		this.age = age;
	}

	public int getAge(){
		return age;
	}

	@Override
 	public String toString(){
		return 
			"UsersItem{" + 
			"name = '" + name + '\'' + 
			",age = '" + age + '\'' + 
			"}";
		}
}