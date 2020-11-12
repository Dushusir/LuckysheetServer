package com.xc.common.utils.xstream;

import lombok.Data;

import java.util.Date;

@Data
public class TestModel {
    String name;
    String open_id;
    Date date_time;

    public TestModel(){
        name="name";
        open_id="openid__id_1";
        date_time=new Date();
    }
}
