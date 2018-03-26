package com.cottee.managerstore.utils;

import android.content.Context;
import android.util.Log;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.GetObjectRequest;
import com.alibaba.sdk.android.oss.model.GetObjectResult;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.cottee.managerstore.httputils.HttpUtils;
import com.google.gson.Gson;
import com.squareup.okhttp.Request;

import java.io.IOException;
import java.io.InputStream;

public class OssUtils {
    public final static String TEST_BUCKET = "thethreestooges";
    public static String KEY_ID = "STS.J4G2KHA54nktUV6g83uVygCjn";
    public static String SECRET_KEY_ID = "2aEFRYXejM3BM7px7sQW3Vkb7fZT4TKM2FZN18VKyUuY";
    public final static String endpoint = "http://oss-cn-shenzhen.aliyuncs" + ".com";
    public static String TOKEN = "CAIS/QF1q6Ft5B2yfSjIq/fyefH8rOoV2amfV3CHgzhmedlViIbBjDz2IHtKe3ZvAekZsfkwlWxT7fwclqp5QZUd0e9GxzM0vPpt6gqET9frma7ctM4p6vCMHWyUFGSIvqv7aPn4S9XwY+qkb0u++AZ43br9c0fJPTXnS+rr76RqddMKRAK1QCNbDdNNXGtYpdQdKGHaOITGUHeooBKJVxAx4Fsk0DMisP3vk5DD0HeE0g2mkN1yjp/qP52pY/NrOJpCSNqv1IR0DPGajnEPtEATq/gr0/0Yomyd4MvuCl1Q8giANPHP7tpsIQl2a643AadYq+Lmkvl1qmkSey1SFdInGoABZWT8M1buzA7KRNqHKKAzFbD3A/Ud7k1us6hSIhvLz7v5hUpdHqaMbAqI7dMN2Ww88KfV1rde7alJL7yjY7PSW20joRDaK/qr+/A5pHoxdePf/76duaw15aL4RCxly6hdK7ZwHICZdunQlbJ+VCurRyTliBTjKga632dwwChOs3U=";
    private static OSS oss;

    private static void initOSS(Context context, OnLoginSuccessful onLoginSuccessful) {
        OSSCredentialProvider credentialProvider = new
                OSSStsTokenCredentialProvider(KEY_ID, SECRET_KEY_ID, TOKEN
        );
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求数，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        oss = new OSSClient(context.getApplicationContext(), endpoint,
                credentialProvider);
        getInfo(onLoginSuccessful);
    }

    private interface OnLoginSuccessful {
        void onKeyUpData();
    }


    public static void updata(Context context, final String objectkey, final byte[] bytes) {
        initOSS(context, new OnLoginSuccessful() {
            @Override
            public void onKeyUpData() {
                PutObjectRequest put = new PutObjectRequest(TEST_BUCKET, objectkey, bytes);
                setServiceCallBack(put);
                // 异步上传时可以设置进度回调
                put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
                    @Override
                    public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                        Log.d("PutObject", "currentSize: " + currentSize + " totalSize: " + totalSize);
                    }
                });
//                OSSAsyncTask task = oss.asyncPutObject(put, completedCallback);
            }
        });
    }

    public static void downImagefromOss(Context context, final String objectKey) {
        initOSS(context, new OnLoginSuccessful() {
            @Override
            public void onKeyUpData() {
                GetObjectRequest get = new GetObjectRequest(TEST_BUCKET, objectKey);
                OSSAsyncTask task = oss.asyncGetObject(get, new OSSCompletedCallback<GetObjectRequest, GetObjectResult>() {
                    @Override
                    public void onSuccess(GetObjectRequest request, GetObjectResult result) {
                        // 请求成功
                        Log.d("Content-Length", "" + result.getContentLength());
                        InputStream inputStream = result.getObjectContent();
                        byte[] buffer = new byte[2048];
                        int len;
                        try {
                            while ((len = inputStream.read(buffer)) != -1) {
                                // 处理下载的数据
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(GetObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                        // 请求异常
                        if (clientExcepion != null) {
                            // 本地异常如网络异常等
                            clientExcepion.printStackTrace();
                        }
                        if (serviceException != null) {
                            // 服务异常
                            Log.e("ErrorCode", serviceException.getErrorCode());
                            Log.e("RequestId", serviceException.getRequestId());
                            Log.e("HostId", serviceException.getHostId());
                            Log.e("RawMessage", serviceException.getRawMessage());
                        }
                    }
                });
            }
        });

    }
    public static String getOSSExtranetPath(String objectKey) {
        String path="";
        try {
            path = MyApplication.getOSSClient().presignConstrainedObjectURL(OssUtils.TEST_BUCKET
                    , objectKey, 30);

        } catch (ClientException e) {
            e.printStackTrace();
        }
        return path;
    }
    private static void setServiceCallBack(PutObjectRequest put) {

        // 异步上传时可以设置进度回调
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                Log.d("PutObject", "currentSize: " + currentSize + " totalSize: " + totalSize);
            }
        });
        OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                Log.d("PutObject", "UploadSuccess");
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                // 请求异常
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace();
                }
                if (serviceException != null) {
                    // 服务异常
                    Log.e("ErrorCode", serviceException.getErrorCode());
                    Log.e("RequestId", serviceException.getRequestId());
                    Log.e("HostId", serviceException.getHostId());
                    Log.e("RawMessage", serviceException.getRawMessage());
                }
            }
        });
    }

    public final static String requestPah = "https://thethreestooges" +
            ".cn:5210/identity/oss/token.php";

    private static void getInfo(final OnLoginSuccessful onLoginSuccessful) {
        HttpUtils.sendOkHttpRequest( requestPah, new com.squareup.okhttp.Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(com.squareup.okhttp.Response response) throws IOException {
                Gson gson = new Gson();
                OssBean ossBean = gson.fromJson(response.body().string
                        (), OssBean.class);
                KEY_ID = ossBean.getCredentials().getAccessKeyId();
                TOKEN = ossBean.getCredentials().getSecurityToken();
                SECRET_KEY_ID = ossBean.getCredentials().getAccessKeySecret();
                onLoginSuccessful.onKeyUpData();
            }
        });
    }
}
