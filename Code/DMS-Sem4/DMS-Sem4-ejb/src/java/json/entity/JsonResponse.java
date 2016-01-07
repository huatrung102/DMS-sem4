/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package json.entity;

/**
 *
 * @author TrungHTH
 */
public class JsonResponse<T> {

    private Integer responseCode;
    private JsonError error;
    private T data;

    public JsonResponse(Integer responseCode, JsonError error, T object) {
        this.responseCode = responseCode;
        this.error = error;
        this.data = object;
    }

    public JsonError getError() {
        return error;
    }

    public void setError(JsonError error) {
        this.error = error;
    }

    public T getObject() {
        return data;
    }

    public void setObject(T object) {
        this.data = object;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }
}
