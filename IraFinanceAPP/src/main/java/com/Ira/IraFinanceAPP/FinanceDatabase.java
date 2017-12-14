package com.Ira.IraFinanceAPP;

import java.sql.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class FinanceDatabase 
{

		Connection con=null;
		
	
		public FinanceDatabase() 
		{
		
			String url="jdbc:mysql://localhost:3306/irafinance?useUnicode=true&characterEncoding=UTF-8";
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
		    				st1.setString(4, "false");
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
			
			int flag=0,temp=0;
			System.out.println("2.Login");
	 
			JSONObject jo=new JSONObject();
	 
			String log="select *from logincontrol";
	 
				
			String reg="select subid from registration";
			
				try
				{
				Statement st1=con.createStatement();
				ResultSet rs1=st1.executeQuery(reg);
				
					while(rs1.next())
					{
						if(username.equals(rs1.getString(1)))
						{
							temp=1;
						
						}
					}
				
				}
				catch(Exception e)
				{
					System.out.println(e);
				}
				
				
				if(temp==1)
				{
				
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
					
														jo.put("parentuser", rs.getInt(1));
														jo.put("check","success" );
														jo.put("accl",rs.getString(3));
														jo.put("forcep",rs.getString(4));
														jo.put("access", rs.getInt(5));
			 		  
														return jo.toString();
					
													}
	 				
											}
									}
	 	
									if(flag==0)
										{
											jo.put("parentuser", "null");
											jo.put("check", "uincorrect" );
											jo.put("accl",  "null");
											jo.put("forcep",  "null");
											jo.put("access","null");
			  
											return jo.toString();
										}
			 
									if(flag==2)
										{
											jo.put("parentuser", "null");
											jo.put("check", "pincorrect" );
											jo.put("accl",  "null");
											jo.put("forcep",  "null");
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
					}
				
				
				else
				{
					System.out.println("else");
					String login="select *from logincontrol";
					
					String s1="'"+username+"'";
					String subu="select subid from subuser where childUserName="+s1;
					
					int id=0;
					try
					{
					Statement st3=con.createStatement();
					ResultSet rs3=st3.executeQuery(subu);
						rs3.next();
						id=rs3.getInt(1);
					
					}
					catch(Exception e)
					{
						System.out.println(e);
					}
					
					int flag1=0;
					
					try
					{
						Statement st2=con.createStatement();
						ResultSet rs2=st2.executeQuery(login);
						
						while(rs2.next())
						{
							if(username.equals(rs2.getString(1)))
							{
										flag1=1;
									if(password.equals(rs2.getString(2)))
										{
											flag1=2;
										
											jo.put("parentuser", id);
											jo.put("check","success" );
											jo.put("accl",rs2.getString(3));
											jo.put("forcep",rs2.getString(4));
											jo.put("access", rs2.getInt(5));
										
											return jo.toString();
											
	
										}

							}
								
						}
						
						if(flag1==0)
						{
							jo.put("parentuser", "null");
							jo.put("check", "uincorrect" );
							jo.put("accl",  "null");
							jo.put("forcep",  "null");
							jo.put("access","null");
  
							return jo.toString();
						}
						
						if(flag1==2)
						{
							jo.put("parentuser", "null");
							jo.put("check", "pincorrect" );
							jo.put("accl",  "null");
							jo.put("forcep",  "null");
							jo.put("access","null");
  
							return jo.toString();
						}
						
						
					}
					catch(Exception e)
					{
						System.out.println(e);
					}
				}
	  
						return "error2";
	 
		}
 
 
 
 /*3.-------------------FORGET USER NAME--------------------------------------------*/
 
		public String forgetUs(String mobilenumber)
			{
			System.out.println("3.Foreget User Name");
				
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
	
 /*4.-----------------------------FORGET PASSWORD-------------------------------------------*/
 
 
 
		public String forgetPd(String username)
			{
				System.out.println("4.Forget Password");
	 
				String un="select subid,mobilenumber,emailid,acctlocked from registration";
		
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
				System.out.println("5.Temp Password");
			
				int flag=0,temp=0;
				String s="'"+username+"'";
				String rg="update registration set pswd=? where subid=?";
				
				String lc="update logincontrol set pswd=? ,forcechgpwd=? where username="+s;
				
				
				/*-------------------CHECKING USER EXIST OR NOT---------------------------*/
				String main="select subid from registration";
					
					try
					{
					Statement st=con.createStatement();
					ResultSet rs=st.executeQuery(main);
					
						while(rs.next())
						{
							if(username==rs.getString(1))
							{
								temp=1;
							}
						}
						if(temp==0)
							return "unotexist";
					
					}
					catch(Exception e)
					{
						System.out.println(e);
					}
					
					
					
					try
						{
				
				
							//Statement st3=con.createStatement();
							PreparedStatement ps = con.prepareStatement(rg);
							PreparedStatement fcp = con.prepareStatement(lc);
			
							ps.setString(1, password);
							ps.setString(2, username);
			
							fcp.setString(1, password);
							fcp.setString(2, "true");
							//fcp.setString(3, username);
			
							ps.executeUpdate();
							fcp.executeUpdate();
		      
						}
					catch(Exception e)
						{
							flag=1;
							System.out.println(e);
						}
					if(flag==1)
						return "error";
					
						return "success";
		}	
		
		
/*6.---------------------------------RESET PASSWORD---------------------------------*/		
		
 
		public String resetPass(String username,String password)
		{
			System.out.println("6.Reset Password");
			//int flag=0;
				String rege="select subid from registration";
			
					try
						{
							Statement st=con.createStatement();
							ResultSet rs=st.executeQuery(rege);
							
							/*-------CHECKING FOR MAIN USER-----*/ 
								while(rs.next())
									if(rs.getString(1).equals(username))
										{
											int flag1=0;
											String rg="update registration set pswd=? where subid=?";
											String lc="update logincontrol set pswd=? ,forcechgpwd=? where username=?";
		    		
		    		
											try
												{
		    			
		    			
												//Statement st3=con.createStatement();
												PreparedStatement ps = con.prepareStatement(rg);
												PreparedStatement fcp = con.prepareStatement(lc);
		    		
												ps.setString(1, password);
												ps.setString(2, username);
		    		
												fcp.setString(1, password);
												fcp.setString(2, "false");
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
		
		
		
/*7.-----------------------SUB USER REGISTRATION--------------------*/
		
		public String createSuser(String user,String username,String pswd,String access,
				                   String substartdate,String subenddate)
			{ 
				System.out.println("7.User Registration");
				int subid=0 ,flag=0;
				
				String control="select  childUserName from subuser";
				String mu="select  *from registration where subid="+user;
				String su="insert into subuser values(?,?,?,?,?,?,?,?)";
				
				String log="insert into logincontrol values(?,?,?,?,?,?,?)";
			
				RegisterUser r=new RegisterUser();
			
				r.setUsername(username);
				r.setPassword(pswd);
			
				r.setSubstartdate(substartdate);
				r.setSubenddate(subenddate);
			
					try
						{
							Statement st3=con.createStatement();
							ResultSet rs1=st3.executeQuery(control);
		    
								while(rs1.next())
									if(rs1.getString(1).equals(r.getUsername()))
										return "subexist";
						}
					catch(Exception e)
						{   flag=1;
							System.out.println(e);
						}
			
					try {
							Statement st=con.createStatement();
							ResultSet rs=st.executeQuery(mu);
							rs.next();
							subid=rs.getInt(1);
							System.out.println(subid);
		     
						}
					catch(Exception e)
					{
						flag=1;
						System.out.println(e);
					}
			
			
					try {
							PreparedStatement st1=con.prepareStatement(su);
							PreparedStatement st2=con.prepareStatement(log);
				
							st1.setInt(1, subid);
							st1.setString(2, r.getUsername());
							st1.setString(3, r.getPassword());
							st1.setString(4, "false");
							st1.setString(5, r.getSubstartdate());
							st1.setString(6, r.getSubenddate());
							st1.setString(7, "true");
							st1.setString(8, access);
		        
							st2.setString(1, r.getUsername());
							st2.setString(2, r.getPassword());
							st2.setString(3, "false");
							st2.setString(4, "true");
							st2.setString(5, access);
							st2.setString(6, "true");
							st2.setInt(7, subid );
							
							st1.executeUpdate(); 
							st2.executeUpdate();
						}
					catch(Exception e)
						{   flag=1;
							System.out.println(e);
						}
					if(flag==1)
						return "error";
							return "success";
			}		
		
		
		
/*8.-------------------------------GET ALL SUB USER NAME BY MAIN USER NAME-----------------------*/
			
		public String allSubUser(String username)
			{
			
			System.out.println("8.All Sub User Name");
				int id=0, flag=0;
				
				JSONObject jo = new JSONObject();
				JSONArray ja = new JSONArray();
				
					try {
							jo.put("subusername", ja);
						}
					catch (JSONException e1) 
						{
					// TODO Auto-generated catch block
							e1.printStackTrace();
						}
				
				
				/*-------Geting subid of Main User from Registration--*/
				
						String main="select subId from registration";
				
						try
							{
					
								Statement st=con.createStatement();
								ResultSet rs=st.executeQuery(main);
			     
								rs.next();
								id=rs.getInt(1);
								//System.out.println("ASSS");
								//System.out.println(id);
			     
							}
						catch(Exception e)
							{
								flag=1;
								//System.out.println("mainusernotexist");
					
								System.out.println(e);
							}
						if(flag==1)
							return jo.toString();
				
				
				
				
				      /*----Fetching all user name from subuser table using main user subid--*/
				
						String sub = "select subId,childUserName from subuser where subId="+username;
							
						try
							{
					
								Statement st1=con.createStatement();
								ResultSet rs1=st1.executeQuery(sub);
			     	
								while(rs1.next())
									{
										//int s=rs1.getInt(1);
											if(username.equals(rs1.getString(1))) 
												{
			    		
			    		
													ja.put(rs1.getString(2));
			        
			            
													//System.out.println(ja);
												}
			    	
									}
			    	

							}
						catch(Exception e)
							{
								flag=2;
					
								System.out.println(e);
							}
						if(flag==2)
							return jo.toString();
							//System.out.println(jo);

							return jo.toString();
			}
		
		
		

/*9.---------------------------------FOR EDIT USER ACCESS-----------------------------------------*/
		
		public String editSuser(String username,String paswd,String access)
		{
			System.out.println("9.Edit User Access");
			int flag=0,temp=0;
			
			String sub="update subuser set childPassword=?, access=? where childUserName=?";
			String login="update logincontrol set pswd=?,access=? where userName=?";
			
				String subuser="select childUserName from subuser ";
			
			
				try
				{
					Statement st=con.createStatement();
					ResultSet rs=st.executeQuery(subuser);
					
					while(rs.next())
					{
						if(rs.getString(1).equals(username))
						{
							temp=1;
							break;
						}
					}
					
					if(temp==0)
						return "uincorrect";
				}
				catch(Exception e)
				{
					System.out.println(e);
					
				}
			
			try
				{
					PreparedStatement ps = con.prepareStatement(sub);
					PreparedStatement ps1 = con.prepareStatement(login);
			
					ps.setString(1, paswd);
					ps.setString(2, access);
					ps.setString(3, username);
			
					ps1.setString(1, paswd);
					ps1.setString(2, access);
					ps1.setString(3, username);
			
					ps.executeUpdate();
					ps1.executeUpdate();
				}
				catch(Exception e)
					{
						flag=1;
						System.out.println(e);
					}
				if(flag==1)
					return "error";
			
				return "success";
		}		
		

		
		
/*10.--------------------------------GET SUB USER ACCESS  FROM MAIN USER-----------------------------------*/
		public String getSubAccess(String username)
		{
			System.out.println("10.Get Sub User Access");
				int flag=0;
				String suser="select childUserName from subuser";
				String slogin="select *from logincontrol";
			 
			 
				JSONObject jo=new JSONObject();
			 
					try
						{
							Statement st=con.createStatement();
							ResultSet rs=st.executeQuery(suser);
		     
							Statement st1=con.createStatement();
							ResultSet rs1=st1.executeQuery(slogin);
							
							while(rs.next())
								if(rs.getString(1).equals(username))
									{    
										flag=1;
										break;
									}
							
							if(flag==1)
								{
									while(rs1.next())
										if(rs1.getString(1).equals(username))
											if(rs1.getString(3).equals("false"))
												{   flag=2;
		    		
													jo.put("check","suexist");
													jo.put("accl", "false");
													jo.put("access", rs1.getString(5));
		    			 
													return jo.toString();
												}
								}
		     	
		     	
						}
					catch(Exception e)
						{   
							System.out.println(e);
						}
			 
			 
					if(flag==0)
						{
							try
							{
							jo.put("check","udoesnotexist");
							jo.put("accl", "null");
							jo.put("access","null");
				 
				
							return jo.toString();
							}
							catch(Exception e)
							{
								System.out.println(e);
							}
						}
					if(flag==1)
						{
							try
							{
							jo.put("check","uexist");
							jo.put("accl", "true");
							jo.put("access","null"); 
				 
			 
							return jo.toString();
							}
							catch(Exception e)
							{
								System.out.println(e);
							}
						}	
					
					return "error";
		
		}	
		
		
		
	/*----------------------------------------HINDI-------------------------------------------*/
		
		public String createH(String name)
		{
			
			String hin="insert into hindi values(?)";
			
			
			try
			{
			PreparedStatement ps = con.prepareStatement(hin);
			
			ps.setString(1, name);
			ps.executeUpdate();
			}
			catch(Exception e)
			{
				System.out.print(e);
			}
			
			return name;
		}
		

		public String showH()
		{
			String s="select data from hindi";
			JSONObject jo=new JSONObject();
			JSONArray ja=new JSONArray();
			try
			{
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery(s);
			
			while(rs.next())
			{
				ja.put(rs.getString(1));
				
			}
			jo.put("name", ja);
			}
			catch(Exception e)
			{
				System.out.println(e);
			}

			return jo.toString();
		}
}

