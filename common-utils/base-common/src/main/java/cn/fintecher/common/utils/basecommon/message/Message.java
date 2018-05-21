package cn.fintecher.common.utils.basecommon.message;

import lombok.Data;

@Data
public class Message {

    private Integer code = null;

    private String type = null;

    private Object message = null;

    public Message(Integer code, String type, Object message) {
        this.code = code;
        this.type = type;
        this.message = message;
    }
}
