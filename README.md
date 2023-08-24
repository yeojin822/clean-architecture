# 클린 아키텍처 적용해보기

책 [로버트 마틴 - 만들면서 배우는 클린 아키텍처] 를 읽고나서
책의 예시가 아닌 구현했던 도메인에 클린아키텍처를 적용해보고 싶었고,
포인트 도메인을 선택해서 구현하게 되었습니다.

### 포인트 적립
- 구현 기능
  - [X] 회원별 적립금 합계 조회
  - [X] 회원별 적립금 적립/사용 내역 조회(페이징처리 포함)
  - [X] 회원별 적립금 적립
  - [X] 회원별 적립금 사용 - 적립금 사용시 우선순위는 먼저 적립된 순서로 사용(FIFO)
  - [X] 적립금의 유효기간 구현 (특정기간 이후 배치를 통한 삭제 처리) 

- 조건
  - [X] 동시성 이슈가 발생할 수 있는 부분을 염두에 둔 개발
  - [X] JPA 사용

### api 문서
http://localhost:8080/swagger-ui/index.html

### 개발환경
Java11, Spring Boot 2.7.15, Spring Data JPA, H2

```bash
├── presentation
│   ├── controller
│   └── payload
├── usecases
│─ port
│   ├── domain
│   ├───── user
│   ├───── points
│   ├───── point
│   ├── service
│   ├───── PointHistoryEventHandler
│   └── schedule
├── persistence
│─ adapter
│   ├── entity
│   └── repository
└── common
```
