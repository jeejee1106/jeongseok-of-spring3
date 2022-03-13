package com.fastcampus.ch4.dao;

import com.fastcampus.ch4.domain.BoardDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BoardDaoImpl implements BoardDao {
    @Autowired
    SqlSession session;

    String namespace = "com.fastcampus.ch4.dao.BoardMapper.";

    @Override
    public BoardDto select(int bno) throws Exception{ //여기선 예외 처리를 하지 않고 다 서비스 쪽으로 던진다.
        return session.selectOne(namespace + "select", bno);
    }
}
