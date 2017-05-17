package com.gti.redirects;

/**
 * Created by xach on 5/17/17.
 */
public class AbstractValidable  implements Validable{


    @Override
    public boolean isValid() {
        return false;
    }

    protected boolean validInt(String num) {
        try {
            Integer.parseInt(num);
            return true;
        } catch (NumberFormatException e) {
            // handle error
        }
        return false;
    }
}
