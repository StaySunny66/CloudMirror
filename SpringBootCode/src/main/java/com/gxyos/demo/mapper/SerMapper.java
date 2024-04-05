package com.gxyos.demo.mapper;

import com.gxyos.demo.entity.*;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface SerMapper {

    @Select("SELECT * FROM `ser_list` where user_id = #{user_id}")
    List<Server> getSerList(String user_id);

    @Insert("INSERT INTO `tem`.`ser_list` (`ser_UID`,`name`,`ip`,`port`,`user_id`,`type`,`others`) VALUES (#{ser_UID},#{name},#{ip},#{port},#{user_id},#{type},#{others});")
    int serverAdd(Server server);

    @Update("UPDATE `tem`.`ser_list` SET `user_id`=#{user_id} WHERE `ser_UID`=#{ser_UID};")
    int serverRegister(String user_id,String ser_UID);

    @Insert("INSERT INTO `tem`.`ser_per_detail` (`uid`) VALUES (#{uid});")
    int add_ser(String uid);

    @Update("UPDATE `tem`.`ser_per_detail` SET " +
            "`coreNum`=#{coreNum},`cpuUsed`=#{cpuUsed},`ramAll`=#{ramAll},`ramUsed`=#{ramUsed},`ramPercent`=#{ramPercent},`osType`=#{osType},`time`=#{time} " +
            "WHERE `uid`=#{uid};")
    int update(SeverState mState);

    @Select("SELECT * FROM `ser_per_detail` WHERE uid in( SELECT ser_UID from ser_list where user_id = #{user})\n")
    List<SeverState> get_state(String user);

    @Insert("INSERT INTO `tem`.`ser_login_list` (`serUid`,`ip`,`time`,`address`,`state`) VALUES (#{serUid},#{ip},#{time},#{address},#{state})")
    int insert_login(ser_login ser);

    @Select(" SELECT serUid,ip,time,address,state FROM `ser_login_list`  WHERE serUid = #{serUid} ORDER BY iu DESC LIMIT 10")
    List<ser_login> get_login_data(String serUid);

    @Select(" SELECT * FROM ser_per_detail,ser_list WHERE ser_per_detail.uid = ser_list.ser_Uid AND uid = #{serUid}")
    SerDetail get_ser_detail(String serUid);

    @Update("UPDATE `tem`.`ser_list` SET `name`=#{serName},`user_id`=#{userId} WHERE `ser_UID`=#{serUid};")
    public int bangding_ser(String serUid,String userId,String serName);

    @Select("SELECT count(*) from `ser_list` where user_id = #{userid}")
    public int get_ser_count(String userid);

    @Select("SELECT * FROM `cvelist`  where Description Like #{keyWords} order by Name DESC  LIMIT 20;")
    public List<CveData> getCveList(String keyWords);


    @Select("SELECT COUNT(*) FROM `cvelist`  where  Name Like #{year} AND `References` IS NOT NULL AND (`Description` LIKE #{k1} OR `Description` LIKE #{k2}  OR `Description` LIKE #{k3} OR `Description` LIKE #{k4} OR `Description` LIKE #{k5} ) order by Name   DESC")
    public int get_count(String year,String k1,String k2,String k3,String k4,String k5);

    @Select("SELECT * FROM `cvelist`  where  Name Like '%2022%' AND `References` IS NOT NULL AND (`Description` LIKE #{k1} OR `Description` LIKE #{k2}  OR `Description` LIKE #{k3} OR `Description` LIKE #{k4} OR `Description` LIKE #{k5} ) order by Name   DESC    LIMIT 10")
    public List<CveData> get_new(String k1,String k2,String k3,String k4,String k5);


}
