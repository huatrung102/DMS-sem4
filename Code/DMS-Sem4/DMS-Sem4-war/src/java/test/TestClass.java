/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import entity.Role;
import entity.User;

/**
 *
 * @author TrungHTH
 */
public class TestClass {
    public static User getTestUser(){
         User userSession = new User();
            userSession.setUserName("testTrunghth");
            userSession.setUserFullName("Hua Tran Huu Trung");
            userSession.setUserId("6D96CBEC-079C-4C33-BCB3-8DCC7D7054CC");
            //staff
            userSession.setRoleId(new Role("e43a70ba-666a-454b-a34c-66338670b8a1"));
            
            return userSession;
    }
}
