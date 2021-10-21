# Online Shop

#### 1) 프로젝트 소개

* 스프링 부트, JPA를 이용한 쇼핑몰 프로젝트

  ![image 1](images/img1.png)
  
    * 회원가입, 로그인, 로그아웃
    
    * 상품 등록 및 관리
    
    * 상품 입고 및 주문에 의한 재고 관리
    
    * 장바구니 담기 기능
    
    * 등록, 수정, 삭제를 위한 HTTP API 제공

    * JUnit을 이용한 테스트 코드 작성

#### 2) 사용 기술

* **[Back-end]**

    * `Java 11`

    * `Gradle`

    * `Spring Boot 2.3.10`

    * `Spring Web`

    * `Spring Data JPA`

    * `Spring Security`

    * `H2 1.4.200`

    * `PostgreSQL`

* **[Front-end]**

    * `Bootstrap 4`

    * `JQuery`

    * `Thymeleaf`

#### 3) 실행 방법

* ① Repository를 git clone을 해서 로컬 환경에 다운로드 받습니다.

  * `git clone https://github.com/kevinntech/online-shop.git`

* ② 아래 디렉토리로 이동합니다.

  * `cd online-shop/project`

* ③ 프로젝트를 빌드합니다.

  * `./gradlew clean build`

* ④ 아래 디렉토리로 이동합니다.

  * `cd build/libs` 

* ⑤ Jar 파일을 실행합니다.

  * `java -jar online-shop-0.0.1-SNAPSHOT.jar` 

* ⑥ 웹 브라우저의 주소창에 아래 내용을 입력합니다.

  * `http://localhost:8080/`

* **[참고]** 
  
  * 현재 프로젝트는 인메모리 데이터베이스로 동작합니다.

  * 추가적인 데이터베이스 설정이 필요 할 수도 있습니다.

#### 4) 프로젝트 전체 흐름

![image 2](images/img2.png)

* ① 클라이언트가 회원가입 및 로그인을 합니다.

* ② (관리자인 경우) 상품을 등록한 다음, 상품에 대한 공급을 하면 재고는 증가합니다. 

* ③ 상품 주문은 재고가 존재하는 경우에만 가능합니다.

  * 상품 주문을 완료하면 재고는 감소합니다.
  
#### 5) 도메인 모델

![image 3](images/img3.png)

  * `User` : 회원

  * `Order` : 주문

  * `OrderProduct` : 주문 상품
  
  * `Product` : 상품

  * `Stock` : 재고

  * `Warehousing` : 상품 공급(입고)

#### 6) 에러 발생 및 해결방법

* Gradle 빌드 시, LOMBOK 관련 에러 : `variable userService not initialized in the default constructor`

  * Gradle 5에서는 애노테이션 프로세서를 별도로 나열해야 합니다.

    ```
    dependencies {
        compileOnly 'org.projectlombok:lombok:1.18.20'
        annotationProcessor 'org.projectlombok:lombok:1.18.20'
        
        testCompileOnly 'org.projectlombok:lombok:1.18.20'
        testAnnotationProcessor 'org.projectlombok:lombok:1.18.20'
    }
    ```

    * [참고] https://stackoverflow.com/questions/54768504/upgrading-from-java-10-to-java-11-and-gradle-4-10-to-gradle-5-2-variable-not

* `@Valid` 애노테이션이 import 되지 않는 문제

  * 스프링 부트 버전 2.3 부터 `starter-web` 의존성에 `spring-boot-starter-validation`가 제외되었으므로 별도로 의존성을 추가해야 합니다.
  
    * `implementation 'org.springframework.boot:spring-boot-starter-validation'`
  
  