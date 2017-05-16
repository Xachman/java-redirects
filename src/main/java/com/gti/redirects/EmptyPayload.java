package com.gti.redirects;

/**
 * Created by xach on 5/13/17.
 */

import com.gti.redirects.Validable;

/**
 * Created by federico on 24/07/15.
 */
public class EmptyPayload implements Validable {
    @Override
    public boolean isValid() {
        return true;
    }
}