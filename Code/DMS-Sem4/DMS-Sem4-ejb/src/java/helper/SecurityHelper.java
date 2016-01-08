/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package helper;

import java.security.MessageDigest;

/**
 *
 * @author TrungHTH
 */
public class SecurityHelper {
    
    public static String encryptString(String data){
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(data.getBytes());
            
            byte byteData[] = md.digest();
            
            StringBuffer hexString = new StringBuffer();
            for (int i=0;i<byteData.length;i++) {
                    String hex=Integer.toHexString(0xff & byteData[i]);
                    if(hex.length()==1) hexString.append('0');
                    hexString.append(hex);
            }
            result = hexString.toString();
        } catch (Exception e) {
            
        }
        return result;
    }
    
}
