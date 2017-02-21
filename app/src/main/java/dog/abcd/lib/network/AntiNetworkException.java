package dog.abcd.lib.network;

/**
 * <b>网络错误</b><br>
 * 包括http错误与错误信息
 *
 * @author Michael Lee<br>
 *         <b> create at </b>2017/2/20 下午 14:33
 * @Company RZQC
 * @Mender Michael Lee<br>
 * <b> change at </b>2017/2/20 下午 14:33
 */
public class AntiNetworkException extends Exception {

    public AntiNetworkException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private int code;

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}
