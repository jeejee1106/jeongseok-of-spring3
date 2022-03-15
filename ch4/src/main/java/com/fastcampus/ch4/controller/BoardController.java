package com.fastcampus.ch4.controller;

import com.fastcampus.ch4.domain.BoardDto;
import com.fastcampus.ch4.domain.PageHandler;
import com.fastcampus.ch4.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    BoardService boardService;

    @PostMapping("modify")
    public String modify(BoardDto boardDto, Integer page, Integer pageSize, Model m, HttpSession session, RedirectAttributes rattr) {
        String writer = (String) session.getAttribute("id"); //getAttribute는 Object를 반환하기 때문에 형변환 필요
        boardDto.setWriter(writer);

        try {
            int rowCnt = boardService.modify(boardDto); //insert이기 때문에 잘 됐으면 1개의 insert가 완료되었다는 int타입의 1을 반환

            if (rowCnt != 1) {
                throw new Exception("Modify failed");
            }

            rattr.addFlashAttribute("msg", "Modify_Success"); //세션을 이용한 1회성 저장방식
            m.addAttribute("page", page);
            m.addAttribute("pageSize", pageSize);

            return "redirect:/board/list?page=" + page + "&pageSize=" + pageSize;
        } catch (Exception e) {
            e.printStackTrace();
            m.addAttribute("boardDto", boardDto); //예외가 발생했을 때 쓰고 있던 글 제목이나 내용 그대로 유지
            rattr.addFlashAttribute("msg", "Modify_Error");
            return "board"; //에러나면 글 쓰고 있던 그 화면 그대로
        }
    }

    @PostMapping("write")
    public String write(BoardDto boardDto, Model m, HttpSession session, RedirectAttributes rattr) {
        String writer = (String) session.getAttribute("id"); //getAttribute는 Object를 반환하기 때문에 형변환 필요
        boardDto.setWriter(writer);

        try {
            int rowCnt = boardService.write(boardDto); //insert이기 때문에 잘 됐으면 1개의 insert가 완료되었다는 int타입의 1을 반환

            if (rowCnt != 1) {
                throw new Exception("Write failed");
            }

            rattr.addFlashAttribute("msg", "Write_Success"); //세션을 이용한 1회성 저장방식

            return "redirect:/board/list";
        } catch (Exception e) {
            e.printStackTrace();
            m.addAttribute("boardDto", boardDto); //예외가 발생했을 때 쓰고 있던 글 제목이나 내용 그대로 유지
            rattr.addFlashAttribute("msg", "Write_Error");
            return "board"; //에러나면 글 쓰고 있던 그 화면 그대로
        }
    }

    @GetMapping("/write")
    public String write(Model m) {
        m.addAttribute("mode", "new");
        return "board"; //board.jsp는 읽기와 쓰기 모두 사용할 것이기 때문에 쓰기에 사용할 때는 mode=new를 넘겨서 구분해준다.
    }

    @PostMapping("/remove")
    public String remove(Integer bno, Integer page, Integer pageSize, Model m, HttpSession session, RedirectAttributes rattr) {
        String writer = (String) session.getAttribute("id");
        try {
            m.addAttribute("page", page);
            m.addAttribute("pageSize", pageSize);

            int rowCnt = boardService.remove(bno, writer);

            if (rowCnt != 1) {
                throw new Exception("board remove error");
            }
            rattr.addFlashAttribute("msg", "Del_Success"); //addFlashAttribute 1회성!!!
        } catch (Exception e) {
            e.printStackTrace();
            rattr.addFlashAttribute("msg", "Del_Error");
        }
        return "redirect:/board/list";
    }

    @GetMapping("/read")
    public String read(Integer bno, Integer page, Integer pageSize, Model m) {
        try {
            BoardDto boardDto = boardService.read(bno);
//            m.addAttribute("boardDto", boardDto); //아래 코드와 동일
            m.addAttribute("boardDto", boardDto); //위처럼 안쓰고 name을 생략할 수 있다. 생략하면 타입에서 첫글자를 소문자로 한 단어가 name이 된다.
            m.addAttribute("page", page);
            m.addAttribute("pageSize", pageSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "board";
    }

    @GetMapping("/list")
    public String list(Integer page, Integer pageSize, Model m, HttpServletRequest request) {
        if(!loginCheck(request)) {
            return "redirect:/login/login?toURL=" + request.getRequestURL();  // 로그인을 안했으면 로그인 화면으로 이동
        }

        if (page == null) page = 1;
        if (pageSize == null) pageSize = 10;

        try {

            int totalCnt = boardService.getCount();
            PageHandler pageHandler = new PageHandler(totalCnt, page, pageSize);

            Map map = new HashMap();
            map.put("offset", (page - 1) * pageSize);
            map.put("pageSize", pageSize);

            List<BoardDto> list = boardService.getPage(map);
            m.addAttribute("list", list);
            m.addAttribute("ph", pageHandler);
            m.addAttribute("page", page);
            m.addAttribute("pageSize", pageSize);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "boardList"; // 로그인을 한 상태이면, 게시판 화면으로 이동
    }

    private boolean loginCheck(HttpServletRequest request) {
        // 1. 세션을 얻어서
        HttpSession session = request.getSession();
        // 2. 세션에 id가 있는지 확인, 있으면 true를 반환
        return session.getAttribute("id")!=null;
    }
}
