package cn.fintecher.common.utils.basecommon.message;

import lombok.Data;

@Data
public class Message {

    private Integer code = null;

    private String type = null;

    private Object message = null;
}
