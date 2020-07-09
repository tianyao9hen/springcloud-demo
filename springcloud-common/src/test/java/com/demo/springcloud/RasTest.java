package com.demo.springcloud;

import com.demo.springcloud.constant.Constant;
import com.demo.springcloud.utils.RasUtils;
import org.junit.Test;

/**
 * RasTest
 *
 * @author pxf
 * @version v1.0
 * @Date 2020-07-09
 */
public class RasTest {

    @Test
    public void testGen() throws Exception {
        RasUtils.generateKey(Constant.PUB_KEY_PATH,Constant.PRI_KEY_PATH,"234");
    }
}