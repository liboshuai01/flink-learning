package cn.liboshuai.flink.netty.demo06.server.session;

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
     * 用户Id
     */
    private String userId;

    /**
     * 用户名称
     */
    private String username;

}
