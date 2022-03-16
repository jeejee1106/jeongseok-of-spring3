package com.fastcampus.ch4.dao;

import com.fastcampus.ch4.domain.*;
import org.apache.ibatis.session.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public class BoardDaoImpl implements BoardDao {
    @Autowired
    private SqlSession session;
    private static String namespace = "com.fastcampus.ch4.dao.BoardMapper.";

    /** SqlSession 메서드 정리
     * selectOne : 하나의 객체를 리턴하는 조회 구문 수행 메서드
     * selectList : 한 개 이상의 객체를 리턴하는 조회 구무 수행 메서드
     * selectMap : 결과 데이터를 Map으로 변환하기 위해 제공되는 메서드
     * selectCursor : 결과 데이터를 Cursor객체로 반환하는 메서드
     * insert, update, delete : 이 메서드들은 Object parameter없이 호출될 수 있도록 오버라이딩 되어있다. 반환데이터는 SQL구문에 의해 영향을 받은 row수를 나타낸다.
     */

    @Override
    public BoardDto select(Integer bno) throws Exception {
        return session.selectOne(namespace + "select", bno);
    } // T selectOne(String statement, Object parameter)

    @Override
    public int count() throws Exception {
        return session.selectOne(namespace+"count");
    } // T selectOne(String statement)

    @Override
    public int insert(BoardDto dto) throws Exception {
        return session.insert(namespace+"insert", dto);
    } // int insert(String statement, Object parameter)

    @Override
    public List<BoardDto> selectPage(Map map) throws Exception {
        return session.selectList(namespace+"selectPage", map);
    } // List<E> selectList(String statement, Object parameter)

    @Override
    public List<BoardDto> selectAll() throws Exception {
        return session.selectList(namespace+"selectAll");
    } // List<E> selectList(String statement)

    @Override
    public int update(BoardDto dto) throws Exception {
        return session.update(namespace+"update", dto);
    } // int update(String statement, Object parameter)

    @Override
    public int increaseViewCnt(Integer bno) throws Exception {
        return session.update(namespace+"increaseViewCnt", bno);
    } // int update(String statement, Object parameter)

    @Override
    public int delete(Integer bno, String writer) throws Exception {
        Map map = new HashMap();
        map.put("bno", bno);
        map.put("writer", writer);
        return session.delete(namespace+"delete", map);
    } // int delete(String statement, Object parameter)

    @Override
    public int deleteAll() {
        return session.delete(namespace+"deleteAll");
    } // int delete(String statement)

//    @Override
//    public int searchResultCnt(SearchCondition sc) throws Exception {
//        System.out.println("sc in searchResultCnt() = " + sc);
//        System.out.println("session = " + session);
//        return session.selectOne(namespace+"searchResultCnt", sc);
//    } // T selectOne(String statement, Object parameter)
//
//    @Override
//    public List<BoardDto> searchSelectPage(SearchCondition sc) throws Exception {
//        return session.selectList(namespace+"searchSelectPage", sc);
//    } // List<E> selectList(String statement, Object parameter)
}