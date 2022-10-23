# usedTrade System
***

### ** 프로젝트 소개 **
***

### ** 프로젝트 기능 **

<details markdown="1">
<summary>접기/펼치기</summary>

#### 회원(User)
* 회원(User)의 회원가입과 로그인 기능
  * 회원가입 기능 
    * 회원가입 진행시 아이디(이메일), 비밀번호, 주소, 전화번호가 필요하다
    * 회원가입시 이메일 인증을 요구하며 인증이 되지 않은 계정에 대해서는 로그인이 불가능하다
    * 이메일 인증을 이미 한 회원은 다시 이메일 인증을 시도해도 진행이 되지 않는다
  * 로그인 기능
    * 존재하는 계정이여야 하며 비밀번호 불일치시 로그인은 진행이 되지 않는다
    * 정지나 탈퇴 상태로 전환된 계정이 로그인 시도시 에러를 발생시킨다
  * 마이페이지 기능
    * 회원은 자신의 회원정보를(아이디, 주소, 전화번호, 등급) 조회할 수 있다 
    * 회원은 자신의 회원정보(비밀번호-초기화, 주소, 전화번호)를 수정할 수 있다
    * 회원은 해당 사이트에서 탈퇴를 진행 할 수 있다
    * 마이페이지에서 회원 본인이 작성한 게시글(판매내역)들을 볼 수 있다
    * 마이페이지에서 회원 본인이 단 댓글이 존재하는 게시글들의 목록을 볼 수 있다
    * 회원 본인이 관심/찜을 등록한 게시글의 목록을 보여준다


* 회원의 중고거래 게시판 사용 기능
  * 게시판 작성
    * 로그인한 회원에 대해서만 게시판 작성 기능을 제공한다
    * 제목, 내용, 키워드(물품의 키워드), 가격, 첨부파일, 거래여부(거래중, 거래완료)를 게시할 수 있다
  * 게시판 조회
    * 로그인한 회원에 대해서만 중고거래 게시글에 들어갈 수 있다
    * 해당 게시글에서 댓글을 달아 연락을 취할 수 있다
    * 게시글에서 관심/찜을 등록할 수 있다
    * 거래자의 아이디를 클릭할시 거래자의 간략한정보(거래횟수, 게시글목록)을 조회할 수 있다
  * 게시판 수정
    * 회원 본인이 작성한 게시글에 대해서만 수정을 시도할 수 있다
    * 본인이 쓴 게시글이 아닌 게시글에 대해 수정을 시도 하는 경우 경고 알림을 띄우고 목록으로 돌아간다
    * 제목, 내용, 첨부파일, 거래 진행 후 거래여부(거래중, 거래완료)의 내용을 수정할 수 있다
  * 게시판 삭제
    * 회원 본인이 작성한 게시글에 대해서만 삭제를 시도 할 수 있다
    * 본인이 아닌 게시글을 삭제를 시도하는 경우 경고 알림을 띄우고 목록으로 돌아간다
  * 게시판 목록
    * 모든 사용자의 게시글들을 조회할 수 있다
    * 게시글 id, 제목[댓글개수], 작성자 아이디, 작성일자를 보여준다
    * 제목, 내용, 작성자 아이디를 기준으로 검색을 할 수 있다
    * 모든 목록 페이지는 페이징 기능을 제공한다
    * 카테고리(키워드) 별로 게시글을 보여주는 기능을 제공한다
  

* 신고글 기능
  * 신고글 작성
    * 제목, 신고아이디, 내용, 첨부파일을 게시할 수 있다
  * 신고글 조회
    * 모든 회원이 신고글(제목, 신고대상 아이디, 내용, 첨부파일)을 조회할 수 있다
  * 신고글 수정
    * 자신이 작성한 신고글에 대해서 수정을 진행 할 수 있다
    * 자신이 작성한 글이 아닌 글을 수정을 시도할시 에러를 발생한다
  * 신고글 삭제
    * 자신이 작성한 신고글에 대해서 삭제를 진행 할 수 있다
    * 자신이 작성한 글이 아닌 글을 삭제 시도시 에러를 발생한다
  * 신고글 목록
    * 모든 사용자의 신고글 목록을 신고글id, 제목, 신고대상 아이디, 작성자 아이디, 작성일자를 표시한다
    * 신고대상 아이디, 작성자 아이디를 이용해서 검색을 할 수 있다
    * 페이징 기능을 제공한다


#### 관리자(Admin)
* 회원관리
  * 가입되어 있는 회원의 목록을 조회 할 수 있다
  * 가입한 회원의 상태(사용중, 정지, 탈퇴)를 변경할 수 있다
  * 가입한 회원의 비밀번호를 초기화 할 수 있다
* 카테고리/키워드 관리
  * 신규 카테고리, 키워드를 생성 및 삭제 할 수 있다
* 중고거래 게시글 관리
  * 모든 회원이 작성한 거래 게시글을 조회할 수 있다 
  * 회원이 작성한 게시글을 삭제할 수 있다
* 신고글 관리
  * 모든 회원이 작성한 신고글을 조회할 수 있다 
  * 회원이 작성한 신고글을 삭제할 수 있다

</details>

***
### ** 사용 기술 스택 **
* SpringBoot
* MariaDB
* Thymeleaf
