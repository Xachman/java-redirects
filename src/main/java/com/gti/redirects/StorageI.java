package com.gti.redirects;

import java.util.List;

/**
 * Created by xach on 5/2/17.
 */
public interface StorageI {
    List redirects();
    boolean addRedirect(Redirect redirect);

}
