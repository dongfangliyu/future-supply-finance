package cn.fintecher.common.utils.basecommon.message;

import lombok.Data;

@Data
public class Message<T> {

    private Integer code = null;

    private String type = null;

    private T message = null;

    public Message(Integer code, String type, T message) {
        this.code = code;
        this.type = type;
        this.message = message;
    }

    public Message(){

    }
}
