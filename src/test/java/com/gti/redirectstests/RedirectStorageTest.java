package com.gti.redirectstests;

import com.gti.redirects.Redirects.RedirectStorage;
import com.gti.redirects.StorageI;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

/**
 * Created by xach on 5/3/17.
 */
public class RedirectStorageTest {

    @Test
    public void fileIsCreated() {
        String filePath = System.getProperty("user.dir") + "/src/test/mocks/tmp/test.json";
        StorageI storage = new RedirectStorage(filePath);
        File file = new File(filePath);
        Assert.assertTrue(file.exists());
    }
}
