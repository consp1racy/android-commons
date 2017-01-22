package net.xpece.android.net;

import com.facebook.network.connectionclass.DeviceBandwidthSampler;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * https://hackernoon.com/so-you-want-to-develop-for-the-next-billion-9eb072c26bc8#.ysd0cnwmc
 */
public class SamplingInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        DeviceBandwidthSampler.getInstance().startSampling();
        Response response = chain.proceed(request);
        DeviceBandwidthSampler.getInstance().stopSampling();
        return response;
    }
}
