package com.gti.redirects;

import java.util.Map;

/**
 * Created by xach on 5/11/17.
 */
public interface Request<V extends Validable> {

    Answer process(V value, Map<String, String> urlParams, Map<String, String> requestParams, boolean shouldReturnHtml);
}
