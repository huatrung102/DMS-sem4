/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package json.utility;

import com.google.gson.Gson;
import json.entity.JsonResponse;

/**
 *
 * @author TrungHTH
 */
public class ResponseWrapper {
    private static Gson gson = new Gson();
    
    public static String getJsonResponse(JsonResponse resp) {
        return gson.toJson(resp);
    }
    
}
