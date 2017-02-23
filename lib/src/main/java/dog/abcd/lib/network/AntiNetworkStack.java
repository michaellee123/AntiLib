package dog.abcd.lib.network;

import android.content.Context;

import com.android.volley.toolbox.HurlStack;

import okhttp3.OkUrlFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import okhttp3.OkHttpClient;


/**
 * <b>用OKHttp替换Volley底层网络请求</b><br>
 *
 * @author Michael Lee<br>
 *         <b> create at </b>2017/1/22 下午 13:26
 */
class AntiNetworkStack extends HurlStack {

    private OkHttpClient okHttpClient;

    public AntiNetworkStack() {
        this(new OkHttpClient());
    }

    public AntiNetworkStack(OkHttpClient okHttpClient) {
        this(okHttpClient, null, 0);
    }

    /**
     * @param okHttpClient
     * @param context      用于生成证书，传null则不验证https
     * @param certID       raw文件夹中https证书id，传0则不验证https
     */
    public AntiNetworkStack(OkHttpClient okHttpClient, Context context, int certID) {
        super(null, buildSSLSocketFactory(context, certID));
        this.okHttpClient = okHttpClient;
    }

    @Override
    protected HttpURLConnection createConnection(URL url) throws IOException {
        OkUrlFactory okUrlFactory = new OkUrlFactory(okHttpClient);
        return okUrlFactory.open(url);
    }

    private static SSLSocketFactory buildSSLSocketFactory(Context context,
                                                          int certRawResId) {
        if (context == null || certRawResId == 0) {
            return null;
        }
        KeyStore keyStore = null;
        try {
            keyStore = buildKeyStore(context, certRawResId);
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory tmf = null;
        try {
            tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }

        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("TLS");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            sslContext.init(null, tmf.getTrustManagers(), null);
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }

        return sslContext.getSocketFactory();

    }

    private static KeyStore buildKeyStore(Context context, int certRawResId)
            throws KeyStoreException, CertificateException,
            NoSuchAlgorithmException, IOException {
        String keyStoreType = KeyStore.getDefaultType();
        KeyStore keyStore = KeyStore.getInstance(keyStoreType);
        keyStore.load(null, null);

        Certificate cert = readCert(context, certRawResId);
        keyStore.setCertificateEntry("ca", cert);

        return keyStore;
    }

    private static Certificate readCert(Context context, int certResourceID) {
        InputStream inputStream = context.getResources().openRawResource(
                certResourceID);
        Certificate ca = null;

        CertificateFactory cf = null;
        try {
            cf = CertificateFactory.getInstance("X.509");
            ca = cf.generateCertificate(inputStream);

        } catch (CertificateException e) {
            e.printStackTrace();
        }
        return ca;
    }
}
