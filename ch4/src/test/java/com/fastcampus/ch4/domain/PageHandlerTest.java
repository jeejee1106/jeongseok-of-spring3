package com.fastcampus.ch4.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PageHandlerTest {

    @Test
    public void test() {
        PageHandler ph = new PageHandler(250, 1);
        ph.print();
        System.out.println("ph = " + ph);
        assertTrue(ph.getBeginPage() == 1);
        assertTrue(ph.getEndPage() == 10);
        assertTrue(ph.isShowPrev() == false);
        assertTrue(ph.isShowNext() == true);
    }

    @Test
    public void test2() {
        PageHandler ph = new PageHandler(242, 11);
        ph.print();
        System.out.println("ph = " + ph);
        assertTrue(ph.getBeginPage() == 11);
        assertTrue(ph.getEndPage() == 20);
        assertTrue(ph.getTotalPage() == 25);
        assertTrue(ph.isShowPrev() == true);
        assertTrue(ph.isShowNext() == true);
    }

    @Test
    public void test3() {
        PageHandler ph = new PageHandler(255, 25);
        ph.print();
        System.out.println("ph = " + ph);
        assertTrue(ph.getBeginPage() == 21);
        assertTrue(ph.getEndPage() == 26);
        assertTrue(ph.getTotalPage() == 26);
        assertTrue(ph.isShowPrev() == true);
        assertTrue(ph.isShowNext() == false);
    }
}