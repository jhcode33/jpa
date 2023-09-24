자바 ORM 표준 JPA 프로그래밍 -[김영한]
==

<br>
- 위 저장소는 김영한님의 JPA 프로그래밍 책을 따라가면서 학습한 공간임을 알립니다.<br>
- 학습 환경은 2023년에 맞추어 변경하였습니다.<br>
- 양관관계 매핑에 대해 학습할 때는 프로젝트를 모듈로 나누어 각 관계를 확습했습니다.<br>

- 원본 github : https://github.com/holyeye/jpabook
- jh가 정리한 블로그 : https://jhcode33.tistory.com/category/%F0%9F%8D%80Spring/%F0%9F%93%96jpa
<br>

## ch12 -> jpabookstore

- spring boot로 변경, war -> jar
- QueryDSL 제거, 기존에 QueryDSL로 사용하던 메소드들은 JPQL을 사용
- view : jsp -> thymeleaf로 변경
<br>

## version
- java : Oracle Open JDK 17
- hibernate : 5.6.15.Final
- h2 DB : 2.2.2221