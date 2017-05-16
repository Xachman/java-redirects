package com.gti.redirects;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.gti.redirects.Util.QueryParamUtil;
import spark.Request;
import spark.Response;
import spark.Route;

import java.io.IOException;
import java.util.Map;
/**
 * Created by xach on 5/11/17.
 */
public abstract class AbstractRequest<V extends Validable> implements com.gti.redirects.Request<V>, Route {

    private Class<V> valueClass;
    protected Model model;

    private static final int HTTP_BAD_REQUEST = 400;

    public AbstractRequest(Class<V> valueClass, Model model){
        this.valueClass = valueClass;
        this.model = model;
    }

    private static boolean shouldReturnHtml(Request request) {
        String accept = request.headers("Accept");
        return accept != null && accept.contains("text/html");
    }

    public static String dataToJson(Object data) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            return mapper.writeValueAsString(data);
        } catch (IOException e){
            throw new RuntimeException("IOException from a StringWriter?");
        }
    }

    public final Answer process(V value, Map<String, String> queryParams, boolean shouldReturnHtml) {
        if (value != null && !value.isValid()) {
            return new Answer(HTTP_BAD_REQUEST);
        } else {
            return processImpl(value, queryParams, shouldReturnHtml);
        }
    }

    protected abstract Answer processImpl(V value, Map<String, String> queryParams, boolean shouldReturnHtml);


    @Override
    public Object handle(Request request, Response response) throws Exception {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            QueryParamUtil qUtil = new QueryParamUtil(request.body());
            V value = null;
            System.out.println(request.body());
            if (valueClass != EmptyPayload.class) {
                value = objectMapper.readValue(qUtil.toJson(), valueClass);
            }
            Map<String, String> urlParams = request.params();
            Answer answer = process(value, urlParams, shouldReturnHtml(request));
            response.status(answer.getCode());
            if (shouldReturnHtml(request)) {
                response.type("text/html");
            } else {
                response.type("application/json");
            }
            response.body(answer.getBody());
            return answer.getBody();
        } catch(JsonMappingException e) {
            response.status(400);
            response.body(e.getMessage());
            e.printStackTrace();
            return e.getMessage();
        }
    }
}