package com.fastcampus.ch4.dao;

import com.fastcampus.ch4.domain.*;

public interface UserDao {
    User selectUser(String id) throws Exception;
//    User selectUser2(String id, String pwd) throws Exception; //그냥 statement를 썼을 때 sql인잭션이 일어난다. 를 보여주기 위한 예제 코드
    int deleteUser(String id) throws Exception;
    int insertUser(User user) throws Exception;
    int updateUser(User user) throws Exception;
    int count() throws Exception;
    void deleteAll() throws Exception;
}