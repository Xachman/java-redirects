package com.gti.redirectstests;

import com.gti.redirects.Util.QueryParamUtil;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by xach on 6/21/17.
 */
public class QueryParamUtilTest {

    @Test
    public void emptyQuery() {
        QueryParamUtil qu = new QueryParamUtil("");

        Assert.assertEquals("{}", qu.toJson());
    }
    @Test
    public void noValQuery() {
        QueryParamUtil qu = new QueryParamUtil("test=val&test2&test3=testval2");

        Assert.assertEquals("{\"test2\":\"\",\"test3\":\"testval2\",\"test\":\"val\"}", qu.toJson());
    }
}
