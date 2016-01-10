/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package datatest;

import entity.DocumentDetail;
import entity.Role;
import entity.Users;
import javax.ejb.EJB;
import manager.UsersFacadeLocal;

/**
 *
 * @author TrungHTH
 */
public class TestClass {
    @EJB
    UsersFacadeLocal userLocal;
    public Users getTestUserLogin(){
         Users userSession = new Users();
            //user Hunglq 
            String userId = "0BEE30CF-CA7D-4E9A-B504-38D4CEAE3327";
            userSession = userLocal.getUserById(userId);     
            return userSession;
    }
    /*
    public DocumentDetail getTestDocumentDetail(){
        
    
    }*/
}
