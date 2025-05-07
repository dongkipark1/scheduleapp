

# 🗓️ ScheduleApp

Spring Boot 기반의 일정 관리 애플리케이션입니다. CRUD 기능과 검색, 필터, 생성일 표시, 삭제 확인 기능을 포함하며, 단위 테스트와 통합 테스트도 함께 제공합니다.

---

🧑‍💻 작성자

이름: 박동기
이메일: qkrehdrl4417@gmail.com
Github: http://github.com/dongkipark1

---

🙋 프로젝트 목적

해당 프로젝트는 Spring Boot 및 MVC 아키텍처 이해를 기반으로 한 개인 포트폴리오 프로젝트입니다. 프론트엔드는 Mustache 템플릿 엔진을 이용해 간단한 UI로 구성되어 있으며, 전체적인 백엔드 로직 구현에 중점을 두었습니다.

---

## 📌 주요 기능

- 일정 등록, 조회, 수정, 삭제
- 제목 검색
- 완료/미완료 필터링
- 생성일, 마감일 표시
- 삭제 확인창
- Bean Validation 및 예외 처리
- 단위 테스트 및 통합 테스트 수행

---

## ✅ 기술 스택

- Java 21
- Spring Boot 3
- Spring MVC
- Mustache Template Engine
- H2 Database (in-memory)
- Spring Validation
- JUnit 5, Mockito (단위 테스트)
- Spring Boot Test (통합 테스트)
- Gradle

---

✅ 기술 스택
<p> <img src="https://img.shields.io/badge/Java-21-007396?style=for-the-badge&logo=java&logoColor=white" /> <br> <img src="https://img.shields.io/badge/Spring Boot-3.2.x-6DB33F?style=for-the-badge&logo=springboot&logoColor=white" /> <br> <img src="https://img.shields.io/badge/Mustache-Template-orange?style=for-the-badge" /> <br> <img src="https://img.shields.io/badge/H2-Database-004A99?style=for-the-badge&logo=h2&logoColor=white" /> <br> <img src="https://img.shields.io/badge/JUnit5-Test-red?style=for-the-badge&logo=junit5&logoColor=white" /> </p>

## 🚀 git 페이지
https://github.com/dongkipark1/scheduleapp.git

📌 API 명세 (간단 정리)

| HTTP Method | URL Path                 | 설명            |
| ----------- | ------------------------ | ------------- |
| GET         | `/schedules`             | 일정 전체 목록 조회   |
| GET         | `/schedules/new`         | 일정 등록 폼       |
| POST        | `/schedules`             | 일정 저장 (등록/수정) |
| GET         | `/schedules/{id}/update` | 일정 수정 폼       |
| POST        | `/schedules/{id}/delete` | 일정 삭제         |
