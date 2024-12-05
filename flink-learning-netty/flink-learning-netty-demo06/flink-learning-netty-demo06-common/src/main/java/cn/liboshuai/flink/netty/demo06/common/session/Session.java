package cn.liboshuai.flink.netty.demo06.common.session;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Session implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户名称
     */
    private String username;

    // 其他用户信息字段省略

}
