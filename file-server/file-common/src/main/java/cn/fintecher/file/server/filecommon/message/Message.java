package cn.fintecher.file.server.filecommon.message;

/**
 * Created by lijinlong on 2017/02/24.
 */
public class Message {
    private String status;
    private String error;
    private Object data;

    public Message(String status){
        this.status = status;
        this.error = "";
        this.data = null;
    }

    public Message(String status, Object data){
        this.status = status;
        this.error = "";
        this.data = data;
    }

    public Message(String status, String error){
        this.status = status;
        this.error = error;
        this.data = null;
    }


    public Message(String status, String error, Object data){
        this.status = status;
        this.error = error;
        this.data = data;
    }

    public Message() {
        super();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
