package in.ashokit.util;

public class EmailBody {

	public static String emailBody(String fname, String lname,String tempPwd ) {
		
		String body = "Hi "+fname+" "+lname+" :/n/n"+
						"Welcome to IES family, your registration is almost complete./n"+
						"Please unlock your account using below details/n"+
						"Temparory Password : "+tempPwd+"/n"+
						"<a href=''>Link to unlock account</a>";
		
		return body;
	}
}
