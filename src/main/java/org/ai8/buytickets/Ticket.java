package org.ai8.buytickets;


import org.springframework.data.jpa.repository.Query;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.List;

@Entity
public class Ticket {
    @Id
    @GeneratedValue
    public Integer id; //id

    public String train_no; //车次
    public String station; //车站
    public String time; //时间
    public String duration; //历时
    public String zy_num; //一等
    public String ze_num; //二等
    public String rw_num; //软卧
    public String yw_num; //硬卧
    public String yz_num; //硬座
    public String wz_num; //无座
    public BigDecimal price; //价格
}
