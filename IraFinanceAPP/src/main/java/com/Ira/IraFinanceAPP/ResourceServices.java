package com.Ira.IraFinanceAPP;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("register")
public class ResourceServices {
	
	FinanceDatabase db=new FinanceDatabase(); 
	//System.out.println("1.Register");
/*1------------OK----- FOR REGISTER NEW USER----------------------------- */
	
	@GET
	@Path("create")
	//@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_XML)
   
	public String createUser(@QueryParam("pswd") String pswd,
							@QueryParam("shop") String shopname,@QueryParam("add") String address,
							@QueryParam("mnum") String mobilenumber,@QueryParam("email") String emailid,
							@QueryParam("gstn") String gstnumber,@QueryParam("accl") String acctlocked,
			 				@QueryParam("sdate") String substartdate,@QueryParam("edate") String subenddate) 
    {
    	
    	RegisterUser r=new RegisterUser();
    	
    	r.setPassword(pswd);
    	r.setShopname(shopname);
    	r.setAddress(address);
    	r.setMobilenumber(mobilenumber);
    	r.setEmailid(emailid);
    	r.setGstnumber(gstnumber);
    	r.setAcctlocked(acctlocked);
    	r.setSubstartdate(substartdate);
    	r.setSubenddate(subenddate);
    	
    		return db.create(r);
    
	}
    
    
/*2-----------------------FOR LOGIN USER-----------------------------------------------*/	
	
	@GET
	@Path("login")
	
	public String loginU(@QueryParam("user") String username,@QueryParam("pswd") String password)
	{
		return db.loginUser(username,password);
		
		
	}
	
	
/*3.--------------------------FORGET USER NAME-----------------------------------------*/
	
	 
	   @GET
	   @Path("forgetuser")
	   @Produces(MediaType.APPLICATION_JSON)
	   public String forgetUser(@QueryParam("mobile") String mobilenumber)
	   {
		   return db.forgetUs(mobilenumber);
	   }
	
	
/*4.---------------------FORGET PASSWORD-------------------------------------------------*/	
	
	   @GET
	   @Path("forgetpassword")
	   
	   public String forgetPassword(@QueryParam("user") String username)
	   {
		   return db.forgetPd(username);
	
	   }

}
