package com.fastcampus.ch4.dao;

import com.fastcampus.ch4.domain.BoardDto;

import java.util.List;
import java.util.Map;

public interface BoardDao {
    BoardDto select(Integer bno) throws Exception; // T selectOne(String statement, Object parameter)
    int count() throws Exception; // T selectOne(String statement)
    int insert(BoardDto dto) throws Exception; // int insert(String statement, Object parameter)
    List<BoardDto> selectPage(Map map) throws Exception; // List<E> selectList(String statement, Object parameter)
    List<BoardDto> selectAll() throws Exception; // List<E> selectList(String statement)
    int update(BoardDto dto) throws Exception; // int update(String statement, Object parameter)
    int increaseViewCnt(Integer bno) throws Exception; // int update(String statement, Object parameter)
    int delete(Integer bno, String writer) throws Exception; // int delete(String statement, Object parameter)
    int deleteAll(); // int delete(String statement)
//    int searchResultCnt(SearchCondition sc) throws Exception; // T selectOne(String statement, Object parameter)
//    List<BoardDto> searchSelectPage(SearchCondition sc) throws Exception; // List<E> selectList(String statement, Object parameter)
}
