/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eebv.org.tools;

import com.fasterxml.jackson.core.JsonProcessingException;
import eebv.org.models.*;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;
import org.json.*;
import com.fasterxml.jackson.databind.*;

/**
 *
 * @author JoseUrdaneta
 */
public class JSONParser {

    public static JSONObject toJSON(Object o) throws JsonProcessingException, JSONException{
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        JSONObject json = new JSONObject(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(o));
        return json;
    }
    
    public static JSONArray toJSONArray(Object o) throws JsonProcessingException, JSONException{
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        JSONArray json = new JSONArray(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(o));
        return json;
    }
}
