/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package additional;

import com.google.gson.Gson;
import json.entity.JsonError;
import json.entity.JsonResponse;
import json.utility.ResponseConstants;
/**
 *
 * @author TrungHTH
 */
public class DMSExceptionWrapper {
    public static String wrapException(DMSException exc){
        return (new Gson()).toJson( new JsonResponse(ResponseConstants.ERROR, 
                new JsonError(exc.getMessage(), exc.getErrorCode()), null));
    }
}
