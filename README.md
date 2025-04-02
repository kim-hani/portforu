# PortForU

---
### Common

- Entity
  - BaseEntity에 생성, 수정, 삭제일 있습니다
  - Entity에 @Setter 사용은 지양했으면 좋겠습니다..
    - 부득이하게 사용해야하는 경우가 있다면 명시적으로 setter가 사용될 메소드 생성하는것이 좋다고 생각합니다 😘


- Response<T> 사용 : 사용은 자유롭게 하십시오. 그냥 `ResponseEntity<dto>` 형식으로 반환해도 에러 안납니다 ㅎ.
  - 페이징 X
    - Controller에서 `ResponseEntity<Response<dto 또는 객체>>` 타입으로 반환합니다.
    - `return ResponseEntity.ok().body(Response.of(dto))`
  - 페이징 O
    - Service에서 pageNum, pageSize 받고 Pageable 객체 생성합니다.
      - 반환타입 : `Page<dto 또는 객체>`
      - return dto
    - Controller에서 PageInfo 받아와서 build 합니당(현재값(?)으로 갱신)
      - @ModellAttribute Pagecond pagecond로 num,size 기본값 설정 가능합니다.
      - 반환타입 : `ResponseEntity<Response<List<dto>>>`
      - return `Response.of(페이징된 dto, pageinfo)`


- Exception
  - CustomExceotion/ServerException 사용하시면 됩니다.
  - 추가적으로 Exception 빌드하실 일 있으시면 `GlobalExceptionHandler.java` 에 형식에 맞춰 추가해서 사용하시면 됩니다.
  - `new CustomException(HttpStatus.CODE, "message");` 형식으로 사용하십시오😉


### application-local
- 전역설정 추가사항 있으시면 application.yml에 추가해주시고
- jwt key, db설정 등 민감한 정보는
  - `application-local.yml` 또는 `application-local.properties`에 추가하시면 됩니다!
  - 혹 local에 적은 값들이 추적되지 않는다면 ❓
    - intellij 설정의 run-> debug -> configurations 설정 -> Active profiles에 local 활성화 시켜주십시오!