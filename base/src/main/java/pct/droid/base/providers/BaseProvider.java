package pct.droid.base.providers;

import com.google.gson.Gson;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * BaseProvider.java
 *
 * Base class for providers, has code to enqueue network requests to the OkHttpClient
 */
public abstract class BaseProvider {

    protected OkHttpClient mClient = new OkHttpClient();
    protected Gson mGson = new Gson();
    protected Call mCurrentCall;

    /**
     * Enqueue request without callback
     * @param request Request
     * @return Call
     */
    protected Call enqueue(Request request) {
        return enqueue(request, null);
    }

    /**
     * Enqueue request with callback
     * @param request Request
     * @param requestCallback Callback
     * @return Call
     */
    protected Call enqueue(Request request, com.squareup.okhttp.Callback requestCallback) {
        mCurrentCall = mClient.newCall(request);
        if(requestCallback != null) mCurrentCall.enqueue(requestCallback);
        return mCurrentCall;
    }

    public void cancel() {
        if(mCurrentCall != null) {
            mCurrentCall.cancel();
        }
    }

    /**
     * Build URL encoded query
     * @param valuePairs List with key-value items
     * @return Query string
     */
    protected String buildQuery(List<BasicNameValuePair> valuePairs) {
        StringBuilder stringBuilder = new StringBuilder();

        try {
            for (int i = 0; i < valuePairs.size(); i++) {
                NameValuePair pair = valuePairs.get(i);
                stringBuilder.append(URLEncoder.encode(pair.getName(), "utf-8"));
                stringBuilder.append("=");
                stringBuilder.append(URLEncoder.encode(pair.getValue(), "utf-8"));
                if (i + 1 != valuePairs.size()) stringBuilder.append("&");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        return stringBuilder.toString();
    }

}