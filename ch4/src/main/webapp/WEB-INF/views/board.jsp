<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>fastcampus</title>
        <link rel="stylesheet" href="<c:url value='/css/menu.css'/>">
        <script src="https://code.jquery.com/jquery-1.11.3.js"></script>
    </head>
    <script>
        let msg = ${msg};
        if(msg == "Write_Error"){
            alert("등록에 실패했습니다.");
        }
    </script>
    <body>
        <div id="menu">
            <ul>
                <li id="logo">fastcampus</li>
                <li><a href="<c:url value='/'/>">Home</a></li>
                <li><a href="<c:url value='/board/list'/>">Board</a></li>
                <li><a href="<c:url value='/login/login'/>">login</a></li>
                <li><a href="<c:url value='/register/add'/>">Sign in</a></li>
                <li><a href=""><i class="fas fa-search small"></i></a></li>
            </ul>
        </div>
        <div style="text-align:center">
            <h2>게시물 ${mode == "new" ? "글쓰기" : "읽기"}</h2>
            <form action="" id="form">
                <input type="hidden" name="bno" value="${boardDto.bno}">
                <input type="text" name="title" value="${boardDto.title}" ${mode == "new" ? "" : "readonly='readonly'"}>
                <textarea name="content" cols="30" rows="10" ${mode == "new" ? "" : "readonly='readonly'"}>${boardDto.content}</textarea>
                <button type="button" id="writeBtn" class="btn">글쓰기</button>
                <button type="button" id="modifyBtn" class="btn">수정</button>
                <button type="button" id="removeBtn" class="btn">삭제</button>
                <button type="button" id="listBtn" class="btn">목록</button>
            </form>
        </div>
        <script>
            $(document).ready(function (){
                $("#listBtn").on("click", function (){
                    location.href = "<c:url value='/board/list'/>?page=${page}&pageSize=${pageSize}";
                })

                $("#writeBtn").on("click", function (){
                    let form = $("#form");
                    form.attr("action", "<c:url value='/board/write'/>");
                    form.attr("method", "post");
                    form.submit();
                });

                $("#modifyBtn").on("click", function (){
                    //1. 읽기 상태이면 수정상태로 변경
                    let form = $("#form");
                    let isReadOnly = $("input[name=title]").attr("readonly"); //input태그의 name이 title인 태그의 속성이 readonly이냐???

                    if (isReadOnly == "readonly") {
                        $("input[name=title]").attr("readonly", false); //참이면 input태그의 name이 title인 태그의 readonly속성을 없애버린다(false)!
                        $("textarea").attr("readonly", false); //textarea태그도 readonly속성 false로.
                        $("#modifyBtn").html("등록"); //id가 modifyBtn인 버튼태그도 (수정에서) 등록으로 변경
                        $("h2").html("게시물 수정"); //h2태그의 내용도 게시물 수정으로 변경
                        return; //위 내용들만 바꾸고 빠져 나가기
                    }

                    //2. 수정 상태로 바꼈다면, 수정된 내용을 서버로 전송
                    form.attr("action", "<c:url value='/board/modify'/>?page=${page}&pageSize=${pageSize}");
                    form.attr("method", "post");
                    form.submit();
                });

                $("#removeBtn").on("click", function (){
                    if (!confirm("정말로 삭제하시겠습니까?")) {
                        return;
                    }

                    let form = $("#form");
                    form.attr("action", "<c:url value='/board/remove?page=${page}&pageSize=${pageSize}'/>");
                    form.attr("method", "post");
                    form.submit();
                });
            });
        </script>
    </body>
</html>