package com.example.shopmylist;

import java.util.ArrayList;
import java.util.List;

public class list {

	public String receiver;
	public String destination;
	public String shopper;
	public String name;
	public ArrayList<String> lists ; 
	public list(String lis_name, ArrayList<String> l,String rec,String des,String shopper)
	{
		this.lists = l;
		this.destination=des;
		this.receiver=rec;
		this.name=lis_name;
		this.shopper = shopper;
	}
}

