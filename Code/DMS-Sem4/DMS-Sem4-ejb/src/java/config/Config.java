/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package config;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 *
 * @author TrungHTH
 */
@Singleton
@Startup
@LocalBean
public class Config {
     public static final long MAX_FILE_SIZE = 1024 * 1024 * 10; // 10 MB
}
