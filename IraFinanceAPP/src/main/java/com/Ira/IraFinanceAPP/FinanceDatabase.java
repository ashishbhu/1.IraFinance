package com.Ira.IraFinanceAPP;

import java.sql.*;

import org.json.JSONObject;


public class FinanceDatabase 
{

		Connection con=null;
		
	
		public FinanceDatabase() 
		{
		
			String url="jdbc:mysql://localhost:3306/irafinance";
			String username="root";
			String password="ashish";
		
				try {
					Class.forName("com.mysql.jdbc.Driver"); 
					con=DriverManager.getConnection(url,username,password);
	    			}
					catch(Exception e)
						{
							System.out.println(e);
						}
	}

		
		
		
/*1------------------------OK----- FOR REGISTER NEW USER------------------------------*/
		   		
		public String create(RegisterUser r2) {
			
			int flag=0,flag1=0;
			
			JSONObject jo = new JSONObject();
			
			System.out.println("1.Register");
		 
			 /*------Checking Mobile or Gst alrady exist-------*/
			String login= "select mobilenumber,gstnumber from registration";
			
			try
			{
				Statement st=con.createStatement();
				ResultSet rs=st.executeQuery(login);
		    
		    		while(rs.next())
		    			if(rs.getString(1).equals(r2.getMobilenumber()))
		    				flag=1;
		    					if(flag==1)
		    					{
		    						jo.put("uname", "null");
		    						jo.put("check", "mexist");
		    						return jo.toString();
		    					}
		    						
		    
		    	Statement st1=con.createStatement();
		    	ResultSet rs1=st1.executeQuery(login);
		    
		    		while(rs1.next())
		    			if(rs1.getString(2).equals(r2.getGstnumber()))
		    				flag=2;
		    					if(flag==2)
		    					{
		    						jo.put("uname", "null");
		    						jo.put("check", "gexist");
		    						return jo.toString();
		    					}
		    						
		    
			} 
			catch(Exception e)
				{	flag=3;
					System.out.println(e);
				
				}
				if(flag==3)
					return "error1";
			
		    	
		    	
               /*-----------Insert Data into Registration Table-------------------*/
		    
		    	
		    String reg="insert into registration(pswd,shopname,"
						+ "address,mobilenumber,emailid,gstnumber,acctlocked,"
						+ "subStartdate,subEnddate)  values(?,?,?,?,?,?,?,?,?)";
			
		    try 
		    {
		    	
		    	PreparedStatement st2=con.prepareStatement(reg);
			
		    	
		    	st2.setString(1, r2.getPassword());
		    	st2.setString(2, r2.getShopname());
		    	st2.setString(3, r2.getAddress());
			
		    	st2.setString(   4,  r2.getMobilenumber());
		    	st2.setString(5, r2.getEmailid());
		    	
		    	st2.setString(6, r2.getGstnumber());
		    	st2.setString(7, r2.getAcctlocked());
		    	
		    	st2.setString(8, r2.getSubstartdate());
		    	st2.setString(9, r2.getSubenddate());
			
		    	st2.executeUpdate();
		
			    flag=4;
			}
			catch(Exception e)
				{
					flag=5;
					System.out.println(e);
				}
		    	if(flag==5)
		    			return "error2";
			
			/*----------Inserting Data into Logincontrol---------*/
		    	
		    	int id=0;
		    	if(flag==4)
		    		{	/*--Getting Subid from registration as username------------*/
		    			 String mo=r2.getMobilenumber();
		    			 
		    		     String regis="select subid from registration where mobilenumber="+mo;
		    		     
		    		     try
		    		     {
		    		    	 Statement st=con.createStatement();
		    		    	 ResultSet rs=st.executeQuery(regis);
		    		    	 
		    		    	 rs.next();
		    		    	 id=rs.getInt(1);
		    		    	 
		    		    	 jo.put("uname", id);
		    		    	 jo.put("check", "success");
		    		     }
		    		     catch(Exception e)
		    		     {   flag=6;
		    		    	 System.out.println(e);
		    		     }
		    		     	if(flag==6)
		    		     		return "error3";
		    		     
		    		     	
		    			String log="insert into logincontrol(username,pswd,acctlocked,forcechgpwd,access,forcelogin)"
							+ "values(?,?,?,?,?,?)";
		    			
		    			try
		    			{
		    				PreparedStatement st1=con.prepareStatement(log);
		    				
		    				st1.setInt(1, id);
		    				st1.setString(2, r2.getPassword());
		    				st1.setString(3, r2.getAcctlocked());
		    				st1.setString(4, "NO");
		    				st1.setInt(5, 15);
		    				st1.setString(6, "true");
		    				
		    				st1.executeUpdate();
		    				
		    				flag1=8;
		    			}
		    			catch(Exception e)
		    			{	flag=7;
		    				System.out.println(e);
		    			}
		    				if(flag==7)
		    					return "error4";
		    		}
		    	else 
		    		return "notregister";
			
		    	
		    	
		    	if(flag==4 && flag1==8)
		    		return jo.toString();
		    	System.out.println(id);
		    	return "error5";
		
	
		}
		
		
 
 
 /*2.------------------------Login User--------------------------------------------*/
 
		public String loginUser(String username,String password)
		{
			System.out.println("2.Login");
	 
			JSONObject jo=new JSONObject();
	 
			String log="select *from logincontrol";
	 
				String log1="update logincontrol set forcelogin=? where username=?";
				int flag=0;
				
				try
					{
						Statement st=con.createStatement();
						ResultSet rs=st.executeQuery(log);
	 	
							while(rs.next())
								{   //flag=1;
	 		
									if(username.equals(rs.getString(1)))
										{     
											flag=2;
											//System.out.println("h"); 
	 		
											if(password.equals(rs.getString(2)))
	 				
												{
													flag=4;
					
													PreparedStatement ps = con.prepareStatement(log1);
					
													ps.setString(1, "false");
													ps.setString(2, username);
					
													ps.executeUpdate();
					
					

													jo.put("check","success" );
													jo.put("accl",rs.getString(3));
													jo.put("fchg",rs.getString(4));
													jo.put("access", rs.getInt(5));
			 		  
													return jo.toString();
					
												}
	 				
										}
								}
	 	
								if(flag==0)
									{
										jo.put("check", "unotexit" );
										jo.put("accl",  "null");
										jo.put("fchg",  "null");
										jo.put("access","null");
			  
										return jo.toString();
									}
			 
								if(flag==2)
									{
										jo.put("check", "pnotexit" );
										jo.put("accl",  "null");
										jo.put("fchg",  "null");
										jo.put("access","null");
			  
										return jo.toString();
									}
	 	
					}
					catch(Exception e)
						{
							flag=3;
							System.out.println(e);
						}
	 
					if(flag==3)
						return "error1";
	  
						return "error2";
	 
		}
 
 
 
 /*2.-------------------FORGET USER NAME--------------------------------------------*/
 
		public String forgetUs(String mobilenumber)
			{
				int flag=0;
				String mo="select subid,mobilenumber,emailid,acctlocked from registration";
		
				JSONObject jo=new JSONObject();
		
					try
						{
							Statement st=con.createStatement();
							ResultSet rs=st.executeQuery(mo);
	     
								while(rs.next())
									{
										if(rs.getString(2).equals(mobilenumber))
											{
												if(rs.getString(4).equals("false"))
													{
														jo.put("check", "myes");
														jo.put("uname", rs.getString(1));
														jo.put("mnum", rs.getString(2));
														jo.put("email", rs.getString(3));
	    			 
														return jo.toString();
													}
												else
													{
														jo.put("check", "ulock");
														jo.put("uname", "null");
														jo.put("mnum", "null");
														jo.put("email", "null");
	 	    		
														return jo.toString();
													}
											}
	    	
									}
	    
						}
						catch (Exception e) 
							{
								flag=1;
								System.out.println(e);
							}
		
							if(flag==1)
								return "error1";
				try
					{
						jo.put("check", "mno");
						jo.put("uname", "null");
						jo.put("mnum", "null");
						jo.put("email", "null");
	     
	     
					}
					catch(Exception e)
						{
							flag=2;
							System.out.println(e);
						}
	
						if(flag==2)
							return "error2";
		
				return jo.toString();
						
			}
	
 /*3.-----------------------------FORGET PASSWORD-------------------------------------------*/
 
 
 
		public String forgetPd(String username)
			{
	 
				String un="select subid,mobilenumber,emailid,acctlocked, from registration";
		
				JSONObject jo=new JSONObject();
		
					try
						{
							Statement st=con.createStatement();
							ResultSet rs=st.executeQuery(un);
	     
								while(rs.next())
									{
										if(rs.getString(1).equals(username))
											{
												if(rs.getString(4).equals("false"))
													{
														jo.put("ckeck", "uyes");
														jo.put("accl", rs.getString(4));
														jo.put("mnum", rs.getString(2));
														jo.put("email", rs.getString(3));
	    			  
	    			  
														return jo.toString();
													}
	    		 
													jo.put("ckeck", "uyes");
													jo.put("accl", "true");
													jo.put("mnum", "null");
													jo.put("email", "null");
    			  
													return jo.toString();
	    		 
											}
	    	  
									}
						}
						catch(Exception e)
							{
								System.out.println(e);
							}
	 	
			try
				{
						jo.put("ckeck", "uno");
						jo.put("accl", "null");
						jo.put("mnum", "null");
						jo.put("email", "null");
	 	
				}
				catch(Exception e)
						{
							System.out.println(e);
						}
				return jo.toString();
	 
			}
 
 /*5.-------------------------------TEMP PASSWORD-------------------------------*/
		
		
		public String tempPass(String  username,String password)
			{
				int flag=0;
				String rg="update registration set pswd=? where username=?";
				String lc="update logincontrol set pswd=? ,forcechgpwd=? where username=?";
			
					try
						{
				
				
							//Statement st3=con.createStatement();
							PreparedStatement ps = con.prepareStatement(rg);
							PreparedStatement fcp = con.prepareStatement(lc);
			
							ps.setString(1, password);
							ps.setString(2, username);
			
							fcp.setString(1, password);
							fcp.setString(2, "YES");
							fcp.setString(3, username);
			
							ps.executeUpdate();
							fcp.executeUpdate();
		      
						}
					catch(Exception e)
						{
							flag=1;
							System.out.println(e);
						}
					if(flag==1)
						return "no";
					
						return "yes";
		}	
		
		
/*6.---------------------------------RESET PASSWORD---------------------------------*/		
		
 
		public String resetPass(String username,String password)
		{
			//int flag=0;
				String rege="select userName from registration";
			
					try
						{
							Statement st=con.createStatement();
							ResultSet rs=st.executeQuery(rege);
							
							/*-------CHECKING FOR MSIN USER-----*/ 
								while(rs.next())
									if(rs.getString(1).equals(username))
										{
											int flag1=0;
											String rg="update registration set pswd=? where username=?";
											String lc="update logincontrol set pswd=? ,forcechgpwd=? where username=?";
		    		
		    		
											try
												{
		    			
		    			
												//Statement st3=con.createStatement();
												PreparedStatement ps = con.prepareStatement(rg);
												PreparedStatement fcp = con.prepareStatement(lc);
		    		
												ps.setString(1, password);
												ps.setString(2, username);
		    		
												fcp.setString(1, password);
												fcp.setString(2, "NO");
												fcp.setString(3, username);
		    		
												ps.executeUpdate();
												fcp.executeUpdate();
		    	      
												}
											catch(Exception e)
												{
													//System.out.println("AKJ");
													flag1=1;
													System.out.println(e);
												}
											if(flag1==1)
												return "nreset";   /*--not reset---*/
												return "reset";
		    		
										}
						}

				    	/*--------Main Exception-------*/
							catch(Exception e)
							{  // flag=1;
								System.out.println(e);
							}
							return "error";
					
		  }

}

