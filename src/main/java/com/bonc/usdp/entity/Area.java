package com.bonc.usdp.entity;

import lombok.*;

/**
 * created on 2017/9/26
 *
 * @author liyanjun@bonc.com.cn
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Area {

    private String province;
    private String city;
    private String county;
    private double longitude;
    private double latitude;

}
