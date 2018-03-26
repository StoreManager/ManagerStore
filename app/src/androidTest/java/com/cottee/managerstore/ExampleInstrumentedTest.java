package com.cottee.managerstore;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.cottee.managerstore.httputils.HttpUtils;
import com.cottee.managerstore.utils.OssBean;
import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals( "com.cottee.managerstore", appContext.getPackageName() );
    }
    public final static String endpoint = "http://oss-cn-shenzhen.aliyuncs" + ".com";

    @Test
    public void ossTest() throws Exception {
        final Context appContext = InstrumentationRegistry.getTargetContext();

        HttpUtils.sendOkHttpRequest("https://thethreestooges.cn:5210/identity/oss/token.php", new Callback() {


            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                Gson gson = new Gson();
                OssBean ossBean = gson.fromJson(response.body().string
                        (), OssBean.class);
                String KEY_ID = ossBean.getCredentials().getAccessKeyId();
                String TOKEN = ossBean.getCredentials().getSecurityToken();
                String SECRET_KEY_ID = ossBean.getCredentials().getAccessKeySecret();
                OSSCredentialProvider credentialProvider = new
                        OSSStsTokenCredentialProvider(KEY_ID, SECRET_KEY_ID, TOKEN);
                ClientConfiguration conf = new ClientConfiguration();
                conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
                conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
                conf.setMaxConcurrentRequest(5); // 最大并发请求数，默认5个
                conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
                OSSClient ossClient = new OSSClient(appContext, endpoint,
                        credentialProvider);
                try {
                    String thethreestooges = ossClient
                            .presignConstrainedObjectURL("thethreestooges"
                            , "sweet/circle/chinn96@163.com/20180213143508/0.png" +
                                            "", 30);
                    System.out.println(thethreestooges);
                } catch (ClientException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
