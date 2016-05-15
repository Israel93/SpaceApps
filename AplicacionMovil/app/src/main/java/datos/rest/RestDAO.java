package datos.rest;

import android.content.Context;

import java.io.IOException;

import javax.net.ssl.SSLSocketFactory;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * Created by gabrielguevara on 28/3/16.
 */
public abstract class RestDAO {

    public final String endPointWS = "http://172.17.239.180:8088/";
    public final String endPointWSPortal = "http://172.17.239.180:8088/";
    protected Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(endPointWS)
            //.addConverterFactory(SimpleXmlConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create());
    private Retrofit retrofit;
    protected Context context;
    public static int PRIMERREGISTRO = 0;

    private OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    private static SSLSocketFactory sslSocketFactory;

    public <T> T createService(Class<T> serviceClass){
        return createService(serviceClass, null);
    }

    public <T> T createService(Class<T> serviceClass, final String token) {
        if (token != null) {
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();

                    Request.Builder requestBuilder = original.newBuilder()
                            .header("Authorization", token)
                            .method(original.method(), original.body());

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });
        }
        if( sslSocketFactory != null) {
            httpClient.socketFactory(sslSocketFactory);
        }
        OkHttpClient client = httpClient.build();
        retrofit = builder.client(client).build();
        return retrofit.create(serviceClass);
    }

    public void forzarXmlParser(String endPoint) {
        builder = new Retrofit.Builder()
                .baseUrl(endPoint)
                .addConverterFactory(SimpleXmlConverterFactory.create());
    }

    public void forzarJsonParser(String endPoint){
        builder = new Retrofit.Builder()
                .baseUrl(endPoint)
                .addConverterFactory(GsonConverterFactory.create());
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
