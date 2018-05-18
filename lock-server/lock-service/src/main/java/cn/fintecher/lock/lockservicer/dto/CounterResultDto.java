package cn.fintecher.lock.lockservicer.dto;

import lombok.Data;

/**
 * Created by lijinlong on 2017/05/27.
 */
@Data
public class CounterResultDto {
    private String key;
    private Long timeout;
    private Long value;
    private Boolean success;
    private String message;
}
