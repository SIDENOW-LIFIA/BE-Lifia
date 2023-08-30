## **🎉 서비스 소개**

### 아파트 주민들의 일상과 삶을 담는 공간
<br />

# 💒LIFIA 라이피아

> ‘LIFIA’는 **아파트 주민들의 일상(Life)을 담은 공간**과 **유토피아(인간이 느낄 수 있는 최선의 상태를 갖춘 사회**)를 합친 단어로, <br />
아파트 주민 간의 **일상의 행복을 나눌 수 있도록 돕는 커뮤니티 웹 서비스**입니다.


<br />



## 🔥팀명: 어?금지

| 분야 | 이름 | 포지션 |
| --- | --- | --- |
| 기획 | 한수민 | **서비스 기획**, **UI/UX 디자인** |
| 개발 | 박민규 | **Front-End 리드** 및 개발 |
| 개발 | 박세영 | **Front-End 개발** |
| 개발 | 유재형 | **Front-End 개발** |
| 개발 | 한종승 | **Back-End 리드** 및 개발 |


<br />


## ❗목적 및 필요성


<br />


## 📊리서치 및 시장조사


<br />


## **🎯 서비스 타겟층**


<br />


## **📌 핵심 기능**


<br />


## **💸비즈니스 모델 설계**


<br />


## 🏛 서비스 구조도


<br />


## 💻 기술 스택

📱 **Front-End :** 
<img src="https://img.shields.io/badge/React-61DAFB?style=flat-square&logo=React&logoColor=black"/> 

📀 **Back-end :** 
<img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=flat-square&logo=SpringBoot&logoColor=white"> 
<img src="https://img.shields.io/badge/SpringDataJpa-6DB33F?style=flat-square&logo=SpringDataJpat&logoColor=white"> 
<img src="https://img.shields.io/badge/Gradle-02303A?style=flat-square&logo=Gradle&logoColor=white">
<img src="https://img.shields.io/badge/Swagger-85EA2D?style=flat-square&logo=Swagger&logoColor=white">
<img src="https://img.shields.io/badge/JWT-black?style=flat-square&logo=JSON%20web%20tokens">
 
💾 **Infra & DB :**
<img src="https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=MySQL&logoColor=white">
<img src="https://img.shields.io/badge/AmazonEC2-FF9900?style=flat-square&logo=AmazonEC2&logoColor=white">
<img src="https://img.shields.io/badge/AmazonRDS-527FFF?style=flat-square&logo=AmazonRDS&logoColor=white">
<img src="https://img.shields.io/badge/AmazonS3-569A31?style=flat-square&logo=AmazonS3&logoColor=white">
<img src="https://img.shields.io/badge/AmazonElastic-005571?style=flat-square&logo=elastic&logoColor=white">
<img src="https://img.shields.io/badge/Redis-DC382D?style=flat-square&logo=Redis&logoColor=white">

🚀 **CI/CD :**
<img src="https://img.shields.io/badge/GithubActions-2088FF?style=flat-square&logo=GithubActions&logoColor=white">
<img src="https://img.shields.io/badge/Docker-2496ED?style=flat-square&logo=Docker&logoColor=white">
<img src="https://img.shields.io/badge/NGINX-009639?style=flat-square&logo=NGINX&logoColor=white">


<br />

## ⚙ 소프트웨어 아키텍쳐
![시스템_아키텍처](https://github.com/SIDENOW-LIFIA/BE-Lifia/assets/60949121/3624ce9b-453f-475c-8a37-7a227aa02a9f)

<br />


## 📑 커밋 컨벤션
  
> Commit Message : [Type][Subject]
  
**📌 Type**
  
|Type|Description|
|--|--|
|**feat**|새로운 기능 추가|
|**fix**|버그 수정|
|**docs**|문서 수정|
|**style**|코드 포맷팅, 세미콜론 누락, 코드 변경이 없는 경우|
|**refactor**|코드 리팩토링|
|**test**|테스트 코드, 리팩토링 테스트 코드 추가|
|**chore**|빌드 업무 수정, 패키지 매니저 수정|

**📌 Subject**
- type과 함께 작성
- 명령문, 현재 시제 사용
- 첫글자 대문자로 시작


<br />

  
### 🌿 Git workflow
  
> Front-end
  
> Back-end

1. `master` → `dev` 브랜치 분기
2. `dev` → `{타입}/{기능이름}+(필요시)/{작업자이름}` 브랜치 분기하여 작업
    - 기본적으로 모든 작업은 `dev` 브랜치에서 이루어지며, 배포 시에 `master` 에 merge
3. commit → push → Pull Request 작성 (관련 Issue  연결)
    - PR 양식 : 변경점, 안내 사항을 알려주며 review 요청
    - Issue 양식 : `1. Description`, `2. Todo`, `3. Etc`
4. PR 작성자가 code review 요청
5. Review 가 끝나면 reviewer 가 `dev` 브랜치에 merge
6. merge 된 작업이 있을 경우 pull

 
 <br />


## 📃 API 명세
  
| 메소드 | URI | 설명 | 현재 구현 여부 ✔️ |
| --- | --- | --- | --- |
| **POST** | /user/signup | 자체 회원가입 |    |
| **POST** | /user/signup/oauth2 | Oauth2 회원가입 |    |
| **GET** | /user/id/duplicate | 아이디 중복 체크 |    |
| **GET** | /user/nickname/duplicate | 닉네임 중복 체크 |    |
| **POST** | /user/email | 인증 이메일 전송 |    |
| **POST** | /user/login | 로그인 |    |
| **POST** | /user/logout | 로그아웃 |    |
| **POST** | /user/re-issue | 토큰 재발급 |    |
| **PUT** | /user/delete | 회원탈퇴 |    |
| **GET** | /user/region | 유저 거주지 조회 |    |
| **GET** | /user/info | 마이페이지 유저 정보 조회 |    |
| **PUT** | /user/password | 유저 비밀번호 수정 |    |
| **PUT** | /user/region | 유저 거주지 수정 |    |
| **PUT** | /user/profile | 유저 프로필 수정 |    |
| **PUT** | /user/nickname | 유저 닉네임 수정 |    |
| **GET** | /user/my-post | 마이페이지 유저 작성글 조회 |    |


  <br/>

  
## 🔑 주요 기능 명세서
  
> 유저 기능
 
 | 기능명 | 상세 |
 | -- | -- |
 | 로그인 | 자체 로그인 |
 | 로그아웃 | 로그아웃 |



> 자유게시판 기능

 | 기능명 | 상세 |
 | -- | -- |



> 같이해요 기능

 | 기능명 | 상세 |
 | -- | -- |



> 고객센터 기능

 | 기능명 | 상세 |
 | -- | -- |



> 마이페이지 기능

 | 기능명 | 상세 |
 | -- | -- |
