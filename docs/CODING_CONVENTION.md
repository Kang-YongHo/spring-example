# CODING-CONVENTION

Code내 주석은 작성하지 않는다. Clean Code 철학에 맞게 읽으면 이해가 되는 코드를 작성하자

## 🚩 Table of Contents

- [Package](#-package)
- [Class layout](-#class-layout)
- [Dependency Injection](-#dependency-Injection)
- [Naming](#-naming)
- [Exception](#-exception)
- [Return](#-return)
- [Control Statement](#-control-statement)
- [Reqeust & Response](#-rr)

### 

## Package

```
├── domain
│   └── user
│       ├── controller
│       ├── service
│       ├── exception
│       └── repository
├── global
│   ├── common
│   ├── config
│   ├── entity
│   ├── util
│   └── properties
```
### `domain` : 도메인 관련 패키지
- controller : rest api 엔드포인트 매핑 및 request validation, request VO
- service : 비즈니스 로직
- exception : 해당 도메인이 발생시키는 예외
- repository : 해당 도메인에 CURD, response VO

### `global` : 전체 설정 및 전역 객체
- common : 공통으로 사용되는 Value 객체. 페이징 처리를 위한 Request, 공통된 응답을 위한 제네릭 Response 등
- config : 스프링 각종 설정.
- entity : Jpa Entities
- error : 예외 핸들링 및 공통 예외
- util : 유틸

## Class layout

### 우선순위

1. Member variable and Initializer
2. Constructor
3. Method

Member variable이 Spring Bean일 경우 final로 선언.

## Dependency Injection

Constructor Injection 방식을 기본으로 사용

```java
    class UserController {
        private final UserService userService;

        public UserController(UserService userService) {
            this.userService = userService;
        }
    }
```

## Naming

Camelcase로 작성, 용도가 명확한 명사로 작성

예약어 사용 불가

DB column name(Entity member variable name)과 반환하는 Object member variable을 가능하면 동일하게 하지 않는다.

### Variable

상수는 영문 대문자 Snake case를 사용

URL, HTML같은 범용적인 대문자 약어는 대문자 그대로 사용한다.

#### 우선순위

1. public
2. protected
3. private

### Method

사용하고자 하는 API(Interface)가 있을경우 동일하게 하고 이외, 기능을 인지할 수 있는 명확한 동사로 작성.

```java
    public List<Uesr> findAll() {
        return this.userEntity.findAll();
    }
```

## Exception

필요한 경우가 아닐 경우 함수 내에서 try catch 지양

필요한 경우가 아닐 경우 특정 Exception throws 지양 throws Exception 사용 지향

## Return

함수 내에서 반환은 한 번만 사용되는걸 권장하나 필요에 의해 다중 반환을 이용하여도 된다.\
특정 값을 반환해야 하는 경우, 함수 맨 마지막에서 한 번만 반환한다.

return문 바로 위는 한칸 비워 놓는다.

## Control Statement

짧은 분기문의 경우 중괄호 없이 조건과 동일한 라인에 둔다.\
```java
    if(str == null) return true;
```

## Reqeust & Response <a name="rr"></a>

[Try not to call your objects DTOs](https://stackoverflow.com/a/35341664) <br>
DTO는 레이어간의 통신시에 주로 객체의 postfix로 사용되나 위 사유로 상세한 명칭을 사용하도록 한다.

일반적인 웹 서비스에 요청과 응답에서는 {Name}Request,{Name}Response 사용.\
DAO(Repository) query 응답결과를 매핑할 땐 {Name}QueryResult 등\
레이어간 톻신에서 어떤 용도의 오브젝트인지 명시할 수 있도록 한다.

Server to Server Mq 통신시에는 {Name}Message, {Name}Representation 

```java
@Getmapping("/order/{id}")
public ResponseEntity<OrderQueryResult> findOrder(@PathVariable String id) {
    return ResponseEntity.ok(orderService.findOne(id));
}
```
