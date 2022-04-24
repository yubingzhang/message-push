package com.study.chat.common.context;

import lombok.Builder;
import lombok.Data;

/**
 * 用户会话信息
 *
 * @author zhangyubing
 */
@Data
@Builder
public class Session {
    private String userId;
    private String userName;
}
