package cn.fintecher.lock.lockservicer.dto;

import lombok.Data;

/**
 * Created by lijinlong on 2017/05/27.
 */
@Data
public class LockResultDto {
    private String key;
    private Long timeout;
    private String result; // true,false,failed
    private String message;
}
