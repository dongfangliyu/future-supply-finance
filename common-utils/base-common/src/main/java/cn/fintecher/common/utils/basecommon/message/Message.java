package cn.fintecher.common.utils.basecommon.message;

import lombok.Data;

@Data
public class Message {

    private Integer code = null;

    private String type = null;

    private Object data = null;

    public Message(Integer code, String type, Object data) {
        this.code = code;
        this.type = type;
        this.data = data;
    }

    public Message(){

    }
}
