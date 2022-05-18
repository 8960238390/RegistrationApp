package in.ashokit.util;

public class RandomPasswordGenerator {
	
	// function to generate a random string of length 8
    public static String getAlphaNumericString()
    {
  
        // chose a Character random from this String
        String characterString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                                    + "0123456789"
                                    + "abcdefghijklmnopqrstuvxyz";
                                
  
        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(8);
  
        for (int i = 0; i < 8; i++) {
  
            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                = (int)(characterString.length()* Math.random());
  
            // add Character one by one in end of sb
            sb.append(characterString.charAt(index));
        }
  
        return sb.toString();
    }
  
}
