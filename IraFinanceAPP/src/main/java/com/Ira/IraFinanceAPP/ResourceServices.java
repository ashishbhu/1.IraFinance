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
	@Consumes(MediaType.APPLICATION_JSON)
   
	public String createUser(@QueryParam("pass") String pswd,
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
	@Consumes(MediaType.APPLICATION_JSON)
	public String loginU(@QueryParam("user") String username,@QueryParam("pass") String password)
	{
		return db.loginUser(username,password);
		
		
	}
	
	
/*3.--------------------------FORGET USER NAME-----------------------------------------*/
	
	 
	   @GET
	   @Path("forgetuser")
	   @Produces(MediaType.APPLICATION_JSON)
	   @Consumes(MediaType.APPLICATION_JSON)
	   public String forgetUser(@QueryParam("mobile") String mobilenumber)
	   {
		   return db.forgetUs(mobilenumber);
	   }
	
	
/*4.---------------------FORGET PASSWORD-------------------------------------------------*/	
	
	   @GET
	   @Path("forgetpassword")
	   @Consumes(MediaType.APPLICATION_JSON)
	   public String forgetPassword(@QueryParam("user") String username)
	   {
		   return db.forgetPd(username);
	
	   }
	   
	   
/*5.-------------------------------TEMP PASSWORD--------------------------------------*/
	   
	   @GET
	   @Path("temppass")
	   @Consumes(MediaType.APPLICATION_JSON)
	   public String tempPassword(@QueryParam("user") String username,@QueryParam("pass") String password )
	   {
		   return db.tempPass(username,password);
	   }  
	   
	   
/*6.---------------------------------RESET PASSWORD---------------------------------*/
	   
	   @GET
	   @Path("resetpass")
	   @Consumes(MediaType.APPLICATION_JSON)
	   public String resetPassword(@QueryParam("user") String username,@QueryParam("pass") String password)
	   {
		   return db.resetPass(username,password);
	   }
	   
	   
	   
/*7.-------------------------------SUB USER REGISTRATION-----------------------------------*/
	   
	   @GET
	   @Path("csuser")
	  
	   @Consumes(MediaType.APPLICATION_JSON)
	   public String createSubUser(@QueryParam("user") String user, @QueryParam("cuname") String username,
			   @QueryParam("cpass") String paswd,@QueryParam("access") String  access,
			   @QueryParam("sdate") String substartdate,@QueryParam("edate") String subenddate)
	   {
		   return db.createSuser(user,username,paswd,access,substartdate,subenddate);
	   }
    	   
	   
/*8.-------------------------OK----------GET ALL SUB USER NAME BY MAIN USER NAME-----------------*/
	   @GET
	   @Path("allsubuser")
	   @Produces(MediaType.APPLICATION_JSON)
	   public String getAllSubUser(@QueryParam("user") String username)
	   {
		   return db.allSubUser(username);
	   }
	   
	   
/*9------------------OK---------FOR EDIT USER ACCESS----------------------------------------------*/
	   
	   @GET
	   @Path("editsubuser")
	   @Consumes(MediaType.APPLICATION_JSON)
	   public String editSubUser(@QueryParam("user") String username,@QueryParam("pass") String paswd,
			   					 @QueryParam("access") String access)
	   {
		  return db.editSuser(username,paswd,access);
	   }
	
	   
/*10.---------------------------------GET SUB USER ACCESS----------------------------------------*/	   
	   
	
	   @GET
	   @Path("getsubuseraccess")
	   @Consumes(MediaType.APPLICATION_JSON)
	  // @Produces(MediaType.APPLICATION_XML)
	   public String getSubUser(@QueryParam("user") String username)
	   {
		   return db.getSubAccess(username);
	   }
	 
	   
	   
	 /*----------------------------HINDI---------------------------*/
	   @GET
	   @Path("hindi")
	   @Consumes(MediaType.APPLICATION_JSON)
	   public String createHindi(@QueryParam("name") String name)
	   {
		   return db.createH(name);
	   }
	   
	   @GET
	   @Path("showhindi")
	   @Produces(MediaType.APPLICATION_JSON)
	   public String showHindi()
	   {
		   return db.showH();
	   }
	   
}
