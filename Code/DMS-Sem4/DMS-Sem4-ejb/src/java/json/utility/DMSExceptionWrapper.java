/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package json.utility;

import additional.DMSException;
import com.google.gson.Gson;
import json.entity.JsonError;
import json.entity.JsonResponse;

/**
 *
 * @author TrungHTH
 */
public class DMSExceptionWrapper {
    public static String wrapException(DMSException exc){
        return (new Gson()).toJson( new JsonResponse(ResponseConstants.ERROR, new JsonError(exc.getMessage(), exc.getErrorCode()), null));
    }
}
