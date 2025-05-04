# PortForU - Team Ping-gu

![Image](https://github.com/user-attachments/assets/919b7463-f245-4bd4-89cc-6101848486fe)

---


## 목차

- [👀프로젝트 소개](#프로젝트-소개)
- [✨주요 기능](#주요-기능)
- [️️⚙️시스템 설계](#시스템-설계)
- [📚STACKS](#stacks)
- [🤔기술적 의사결정](#기술적-의사결정)
- [⚒️트러블 슈팅 및 성능 개선](#트러블-슈팅-및-성능-개선)


____



# 👀프로젝트 소개


점점 어려워지는 취업 , 꽁꽁 얼어붙은 취업시장 위를 걸어다니는 개발자들..🫂

수많은 이력서, 끝없는 서류 탈락, 코딩테스트는 마치 징검다리 없는 강을 건너는 기분이죠.

>  🙋‍♂️ “취업, 도대체 어떻게 해야 돼?”

>  🙋‍♀️ “포트폴리오... 남의 거라도 보고 싶다...”

>  🙇‍♂️ “채용 정보, 왜 이렇게 찾기 어렵냐...”

그 순간—❗

펭귄처럼 귀엽지만, 실력은 날카로운 **우리 팀 핑구(PortForU)가 나타났습니다!**


![Image](https://github.com/user-attachments/assets/50c8999a-9873-4372-8dcc-b6d6d956d873)

## 🚒 "포트폴리오를 구해줄게!"

<Br>

***PortForU*는 포트폴리오 및 채용정보 공유 서비스로**

IT기업 합격 **포트폴리오를 공유**하고,

관심사에 맞는 기업 별 **채용공고 공유** 서비스를 제공합니다.

>“개발자 인생, 혼자 하지 마세요.”


**PortForU가 당신의 구직 여정을 함께 걷겠습니다.**


-------


# ✨주요 기능



<details>
  <summary> 🏢 기업별 채용공고 및 스크랩 </summary>

- IT기업 중심의 채용공고(잡코리아, 사람인)
- 인재상, 스킬 및 채용 우대사항 제공
- 직무/지역/경력/기술스택 필터링 및 검색 및 추천 공고 플로팅
- 공고 상세
  - 공고 내용,지원 자격, 우대 기술 , 지원 링크 제공
  - 관심 공고 마감 임박 알림(이메일)

</details>

<details>
  <summary> 💳 멤버십 </summary>

- 멤버십 생성(관리자)
- 구독기능(toss)

</details>

<details>
  <summary> 💿 포트폴리오 </summary>

- 사용자 포트폴리오 업로드 기능
- 포트폴리오 조회 기능
- 댓글/피드백 기능(멤버십만)

</details>

<details>
  <summary> ✅ 사용자 인증 및 관리 </summary>

- 사용자 인증 및 관리
  - SpringSecurity&JWT를 이용한 회원가입/로그인
  - 일반회원가입
  - 소셜로그인
    - 카카오
    - 네이버
    - 구글
  - 사용자 프로필 관리

</details>

<details>
  <summary> 🎸 기타 기능 </summary>

- 관리자 페이지
  - 결제(PG) 연동
  - 알림 기능

</details>



------



# ⚙️시스템 설계

- 📆 개발 일정 : 2025.04.01 ~ 2025.05.04
- 🐧 개발 주체 : [Team Ping-gu](#팀원-소개)



### ☁️ Cloud Architecture
![Image](https://github.com/user-attachments/assets/98294961-8805-41c7-8795-3d6715bf7e8c)

### 🔗 CI/CD
![Image](https://github.com/user-attachments/assets/547e8f68-4313-4489-97b8-fee04347e446)

### ⛓️ ERD
<img width="1187" alt="Image" src="https://github.com/user-attachments/assets/38f930c8-b435-410f-a32d-b167df5312c4" />

### 📈 FLOW CHART
![Image](https://github.com/user-attachments/assets/8d9e0217-0803-43f2-a7ff-01a0c9eb8819)

### 🖋️ WIRE FRAME
<img width="893" alt="Image" src="https://github.com/user-attachments/assets/7524acf6-bc86-4143-8022-0a2b290a8ba1" />

### 🧾 API
[API 명세서](https://www.notion.so/teamsparta/API-1e62dc3ef514804bb67fcb6131e49add?pvs=4)

------

# 📚STACKS



### 🧩 Backend - Language & Framework

| Java | Kotlin | Spring Boot | Spring Security |
|:--:|:--:|:--:|:--:|
| ![Java](https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white) | ![Kotlin](https://img.shields.io/badge/kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white) | ![Spring Boot](https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white) | ![Spring Security](https://img.shields.io/badge/springsecurity-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white) |

| Spring | Prometheus | Spring Boot Web | Spring Validation |
|:--:|:--:|:--:|:--:|
| ![Spring](https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white) | ![Prometheus](https://img.shields.io/badge/prometheus-E6522C?style=for-the-badge&logo=prometheus&logoColor=white) | ![Web](https://img.shields.io/badge/Spring%20Boot%20Web-6DB33F?style=for-the-badge&logo=spring&logoColor=white) | ![Validation](https://img.shields.io/badge/Spring%20Validation-6DB33F?style=for-the-badge&logo=spring&logoColor=white) |

| Spring Data JPA | Lombok | JWT |  
|:--:|:--:|:--:|
| ![JPA](https://img.shields.io/badge/Spring%20Data%20JPA-6DB33F?style=for-the-badge&logo=spring&logoColor=white) | ![Lombok](https://img.shields.io/badge/lombok-CA2C92?style=for-the-badge&logo=lombok&logoColor=white) | ![JWT](https://img.shields.io/badge/JWT-000000?style=for-the)


---

### 🗄️ Database & Cache

| MySQL | Redis | Elasticsearch |
|:--:|:--:|:--:|
| ![MySQL](https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white) | ![Redis](https://img.shields.io/badge/redis-FF4438?style=for-the-badge&logo=redis&logoColor=white) | ![Elasticsearch](https://img.shields.io/badge/elasticsearch-005571?style=for-the-badge&logo=elasticsearch&logoColor=white) |

---

### ☁️ Infra & CI/CD

| Docker | Compose | GitHub Actions | EC2 |
|:--:|:--:|:--:|:--:|
| ![Docker](https://img.shields.io/badge/docker-2496ED?style=for-the-badge&logo=docker&logoColor=white) | `docker-compose` | ![GitHub Actions](https://img.shields.io/badge/githubactions-2088FF?style=for-the-badge&logo=githubactions&logoColor=white) | ![EC2](https://img.shields.io/badge/amazonec2-FF9900?style=for-the-badge&logo=amazonec2&logoColor=white) |

| S3 | Route 53 | RDS | ELB |
|:--:|:--:|:--:|:--:|
| ![S3](https://img.shields.io/badge/amazons3-569A31?style=for-the-badge&logo=amazons3&logoColor=white) | ![Route 53](https://img.shields.io/badge/amazonroute53-8C4FFF?style=for-the-badge&logo=amazonroute53&logoColor=white) | ![RDS](https://img.shields.io/badge/amazonrds-527FFF?style=for-the-badge&logo=amazonrds&logoColor=white) | ![ELB](https://img.shields.io/badge/awselasticloadbalancing-8C4FFF?style=for-the-badge&logo=awselasticloadbalancing&logoColor=white) |

| IAM |
|:--:|
| ![IAM](https://img.shields.io/badge/amazoniam-DD344C?style=for-the-badge&logo=amazoniam&logoColor=white) |

---

### 🛠️ Tools

| IntelliJ | ERD Cloud | Swagger | Postman |
|:--:|:--:|:--:|:--:|
| ![IntelliJ](https://img.shields.io/badge/IntelliJ%20IDEA-000000?style=for-the-badge&logo=intellijidea&logoColor=white) | `ERD Cloud` | ![Swagger](https://img.shields.io/badge/swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=black) | ![Postman](https://img.shields.io/badge/postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white) |

| Grafana | Selenium | Jsoup | Kotlin Coroutines |
|:--:|:--:|:--:|:--:|
| ![Grafana](https://img.shields.io/badge/grafana-F46800?style=for-the-badge&logo=grafana&logoColor=white) | ![Selenium](https://img.shields.io/badge/selenium-43B02A?style=for-the-badge&logo=selenium&logoColor=white) | `Jsoup` | `Kotlin Coroutines` |

| OAuth | Xvfb | RabbitMQ |
|:--:|:--:|:--:|
| `OAuth` | `Xvfb` | [![RabbitMQ](https://img.shields.io/badge/RabbitMQ-FF6600?style=for-the-badge&logo=rabbitmq&logoColor=white)](https://www.rabbitmq.com/)


---

### 🤝 Collaboration

| Git | GitHub | Slack | Notion |
|:--:|:--:|:--:|:--:|
| ![Git](https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white) | ![GitHub](https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white) | ![Slack](https://img.shields.io/badge/slack-4A154B?style=for-the-badge&logo=slack&logoColor=white) | ![Notion](https://img.shields.io/badge/notion-000000?style=for-the-badge&logo=notion&logoColor=white) |

| Zep | Figma |
|:--:|:--:|
| `Zep` | ![Figma](https://img.shields.io/badge/figma-F24E1E?style=for-the-badge&logo=figma&logoColor=white) |


------


# 🤔기술적 의사결정


<details>
  <summary> 🧠 멤버십 구독 결제 시스템 구현 - TossPG 연동 </summary>

### 1. 결제 서비스 선택 – Toss Payments 도입

### 선택 배경

- Stripe와 Toss Payments 두 결제 플랫폼을 비교하던 중, **국내 사용자 대상 서비스**라는 점을 고려해 Toss를 선택함.
- **Stripe의 한계**
  - 한국 공식 지원 미비
  - 결제 통화가 USD 고정 → 정산 및 사용자 혼란 가능성
- **Toss의 장점**
  - 카카오페이, 토스 등 **국내 간편결제 수단 지원**
  - 한화 정산 및 고객 대응 용이

### 고려된 대안

- Stripe를 도입하고 자체 UI로 결제 구현
- 외부 PG 연동 대신 자체 가상 결제 시스템 구현 (검증 및 보안 부담 커서 폐기)

---

### 2. 결제 연동 방식 결정 – 리다이렉트 방식 선택

### 선택 배경

- Toss Payments는 React 기반 SDK만을 제공하지만, 우리 프로젝트는 **Thymeleaf 기반의 SSR 구조**를 사용하고 있음.
- 현재 **백엔드를 중심으로 학습 중인 상황**이기 때문에, 프론트엔드 구현이 요구되는 SDK 방식은 지양하고, **백엔드만으로 처리 가능한 결제 링크 생성 후 리다이렉트 방식**을 도입함.

### 시도했던 접근

- 백엔드에서 직접 결제 요청 생성 시도 → Toss 문서 기준, 해당 API는 **가맹점 전용 인증 기반**이라 사용 불가 확인
- React SDK 사용 시도 → SSR 환경과 충돌 발생, 기술 스택과의 부적합성으로 폐기

### 최종 구조

- 백엔드에서 결제 정보를 생성 → Toss 결제창 URL로 리다이렉트
- 사용자가 결제를 완료하면 `paymentKey`를 받아 백엔드에서 검증 처리

---

### 3. 결제 상태 관리 설계 – Enum 확장

### 선택 배경

- Toss Payments는 성공/실패/취소 외에도 다양한 결과를 반환함.
- 실제 서비스 운영 중 다음과 같은 케이스들이 발생 가능:
  - 브라우저 강제 종료
  - 10분 이내 결제 미완료
  - 중복 결제 시도 등

### Enum 상태

```java
public enum PaymentStatus {
    PENDING,
    COMPLETED,
    FAILED,
    CANCELLED,
    EXPIRED
}
```

### 장점

- 서비스 로직의 **명시적 상태 추적 가능**
- 예외 상황 대응이 구조적으로 명확해짐

---

### 4. 구독 취소 정책 – Soft Delete 및 정책 보존

### 선택 배경

- 연간 단위 구독제의 특성상, 사용자가 구독을 취소해도 **그 해에는 혜택이 유지되어야 함**
- DB에서 기록을 완전히 삭제하지 않고 **Soft Delete 방식**을 채택

### 기술적 이점

- 이력 관리 가능
- 구독 재가입/중복 결제 방지에 활용
- 회계 및 통계 데이터 유지에 유리

---

### 5. 정원 초과 제한 및 미결제 만료 처리

### 정원 초과

- 인기 멤버십은 수용 인원 제한 존재
- 결제 시점 기준으로 정원을 초과한 경우, **구독 거부 예외 처리** 추가

### 미결제 만료

- Toss 결제는 결제 승인까지 일정 시간 지연 가능 →**20분 내 미승인 시 상태를 `EXPIRED`로 자동 변경**
- Spring Scheduler를 활용해 주기적으로 상태 갱신 처리

---

### 결론

이 결제 시스템은 단순히 결제 API를 붙이는 수준이 아니라,

실제 사용자 행동과 서비스 정책을 반영한 **도메인 중심 설계**와

**예외 관리가 가능한 구조**를 만드는 데 중점을 두었습니다.

기술 스택의 제약(SDK 미지원, SSR 구조), 비즈니스 정책(연간 구독 유지, 정원 제한) 등을 반영하여

**기술적 대안들을 검토하고 최적화된 설계로 결정**한 과정이 핵심이었습니다.

</details>

<details>
  <summary> 🧠 Executor+redis ttl & Keyspace+redis ttl 응답속도 차이 확인과 채택 과정 </summary>

## 1. 실험 목적

> Redis TTL 만료 처리 방식 중 PUBSUB(`Keyspace Notification`)방식과 EXECUTOR(`ScheduledExecutor`) 방식을 비교하여,
>
>
> TTL 만료 후 실제 작업이 실행되기까지의 지연(Delay) 차이를 측정하고
>
> 서비스 특성에 더 적합한 방식을 판단하기 위한 실험을 진행했습니다.
>

## 2. 실험 환경

- 총 테스트 키: 1,000개 (`subscribeId` 기준 1~1000)
- TTL 설정: 모든 키에 대해 10초로 통일
- 출력 데이터: `subscribeId`, `mode`, `expectedTime`, `actualTime`, `delay(ms)`
- 지연 시간 비교 결과는 CSV로 저장해 시각화

## 3. 실험 예상

| 항목 | Executor + TTL | TTL + Keyspace Notification |
| --- | --- | --- |
| 구조 복잡도 | 보통 | 복잡함 (설정 + 리스너) |
| TTL 유실 대비 | 복구 가능 | TTL 날아가면 트리거 없음 |
| 처리 지연 | 수 밀리초 ~ 초 단위 | 거의 실시간 (Pub/Sub) |
| 서버 재시작 시 | Executor 예약 유실 | TTL 이벤트는 살아있음 |
| 확장성 | Redis Lock 필요 | Pub/Sub 병렬성 이슈 고려 필요 |
| Spring 호환성 | 안정적 | 일부 설정 주의 필요 |

## 4. 실험 결과

| 방식 | 평균 지연 시간 | 최소~최대 지연 |
| --- | --- | --- |
| **PUBSUB** | 약 129.34ms | 2ms ~ 371ms |
| **EXECUTOR** | 약 6.814ms | -1ms ~ 15ms |

### 예시 샘플

**PUBSUB**

```
subscribeId=203, delay=2ms
subscribeId=42, delay=371ms
```

**EXECUTOR**

```
subscribeId=673, delay=-1ms
subscribeId=439, delay=15ms
```

[pubsub과 executor 반응 차이.csv](attachment:a906dd87-9778-41a4-8ac9-e1417b0af6ec:pubsub과_executor_반응_차이.csv)

## 5.기술적 분석 비교

| 항목 | PUBSUB 방식 | EXECUTOR 방식 |
| --- | --- | --- |
| 동작 원리 | Redis TTL 만료 → Keyspace Notification 이벤트 발생 → PubSub 전파 | Java에서 TTL 시간에 맞춰 스케줄링된 작업 실행 |
| 정확성 | TTL 만료 후 Redis에서 PubSub로 전파까지 지연 발생 | JVM 내 스케줄링 → 정확한 타이밍 보장 |
| 장점 | Redis 기반 → 분산 환경에서 이벤트 감지 가능 | 정밀한 예약 실행, 지연 거의 없음 |
| 단점 | 전파 지연, 메시지 누락 가능성 있음 | 서버 재시작 시 스케줄 유실 가능성 |
| 적합 용도 | 실시간 알림, 이벤트 브로드캐스팅 | 결제 만료, 정원 복구 등 정밀 예약 처리 |

## 6.결론

- Pub/Sub 방식은 "이론상 실시간"이나 실제로는 **Redis 내부 전파 지연**으로 인해 **평균 129ms** 이상의 딜레이가 존재함
- 반면, ScheduledExecutor는 TTL 만료와 **거의 동시에 작업이 실행**되어 평균 지연 **6.8ms 수준**으로 매우 안정적임
- 또한, **누락 사례 없이 정확한 동작**을 보였고, 구현도 단순하여 Spring과의 통합성도 뛰어남

> 따라서 정밀도와 신뢰성이 중요한 결제 만료나 정원 복구와 같은 예약 로직에는
>
>
> `ScheduledExecutor + TTL + 복구 로직 조합`이 더 적합하다고 판단하여 실제 적용하였습니다.
>

## 7.예상 결과 비교표
| 항목         | 예상(이론 기반)                                    | 결과(실험 기반)                             |
|------------|----------------------------------------------|---------------------------------------|
| 구조 복잡도     | Executor: 보통 / PubSub: 설정 + 리스너로 복잡함         | 그대로 일치                                |
| TTL 유실 대비  | Executor: 복구 가능 / PubSub: 트리거 없음             | 그대로 일치                                |
| 처리 지연      | Executor: 수 ms ~ 수십 ms / PubSub: 거의 실시간      | Executor 평균 6.8ms / PubSub 평균 129.3ms |
| 정확도        | Executor: 정밀 (JVM 스케줄링) / PubSub: 낮음 (전파 지연) | 실험에서도 정확히 반영됨                         |
| 서버 재시작 시   | Executor: 예약 유실 / PubSub: TTL 이벤트는 유지됨       | 그대로 일치                                |
| 확장성        | Executor: Redis Lock 필요 / PubSub: 병렬성 이슈 고려  | 실제 테스트에선 큰 이슈 없었지만, 이론과 일치함           |
| Spring 호환성 | Executor: 안정적 / PubSub: 설정 주의 필요   | 설정 이슈 존재했음 (예: Redis 설정 누락 등)         |
| 지연 분포      |                                              | PubSub: 2ms ~ 371ms / Executor: -1ms ~ 15ms |
| 누락가능성      | PubSub는 누락 가능성 존재                          | 실험에서도 일부 subscribeId 누락 확인됨  |

</details>

<details>
  <summary> 🧠 Redis를 활용한 동시성 제어 기술 의사결정 과정 </summary>

### 1.배경

- 멤버십 결제 및 정원 관리 기능은 동시에 여러 사용자가 요청할 수 있는 민감한 영역
- 실제 서비스에서는 동일한 `subscribeId`에 대해 **동시에 결제 요청**이 들어올 수 있고,
- 이때 적절한 동시성 제어가 없으면 **중복 결제 처리** 또는 **정원 초과** 문제가 발생할 수 있음

---

### 2. 기술 검토

| 방식 | 설명 | 장점 | 단점 |
| --- | --- | --- | --- |
| **DB 트랜잭션 제어 (Pessimistic Lock)** | DB 레벨에서 row 락 걸기 | 구현 간단, DB 내에서 해결 | 성능 저하, 확장성 낮음 |
| **Java 동기화 (Synchronized, ReentrantLock)** | JVM 내 스레드 동기화 | 구조 단순, 빠름 | 분산 환경에서 무용지물 |
| **Redis 분산 락 (Redisson, SET NX)** | Redis 기반 key 락 | 분산 환경 대응 가능, 빠름 | 락 유실/중복 획득 주의 필요 |
| **Kafka 큐 기반 직렬 처리** | 요청을 큐에 밀어넣고 직렬화 | 데이터 순서 보장 | 시스템 구조 복잡, 지연 가능성 |

---

### 3. 적용 결정

- 우리 프로젝트는 **멀티 인스턴스 / WAS 확장 가능성**을 고려하여,
- Java 내 락보다는 **Redis 기반의 분산 락**을 선택함
- `RedissonClient.getLock("subscribeId")` 방식으로 락을 걸고,
  - 성공한 요청만 결제 승인
  - 나머지 요청은 즉시 실패 처리

---

### 4. 채택 이유 요약

- **실시간 처리와 분산 환경**을 동시에 만족해야 했기 때문에 Java 락보다는 Redis 락이 적합
- DB 락은 스케일 문제와 성능 이슈로 배제
- Redisson은 락 자동 해제, TTL 설정, `tryLock()` 지원 등 기능이 풍부해 유지 보수성이 높음

---

### 5. 회고 및 추가 고려

- Redisson 사용 시 **TTL 누락** 또는 **락 소유자 식별 문제** 발생 가능성 있음 → 되도록 `tryLock()` + timeout 설정을 통해 안정성 보완
- 장기적으로는 **결제 요청 자체를 큐잉 처리**(ex. Kafka)하는 구조로 확장도 고려할 수 있음

</details>

<details>
  <summary> 🧠 Authentication/Authorization → ☑️ Spring Security + JWT </summary>

### 배경 및 요구사항

- 다양한 클라이언트에서 안전하게 API에 접근할 수 있는 인증/인가 시스템 필요
- 기존 세션 기반 인증의 한계: 서버 확장성 제약, 다양한 클라이언트 지원 어려움
- 핵심 요구사항: Stateless 인증, 권한 기반 접근 제어, 보안성 및 확장성 확보

---

### 결정 과정

**Spring Security + JWT 선택 이유**

- **Spring Security**: 강력한 인증/권한 기능, 필터 체인 기반 유연한 설정, Spring 생태계 통합
- **JWT**: Stateless 방식으로 서버 확장성 향상, 토큰에 정보 포함, 다양한 클라이언트 지원
- **소셜 로그인 확장성**: 초기 설계 단계에서 소셜 로그인 도입 고려, Spring Security의 OAuth2 지원으로 통합 인증 체계 구축 가능

**JWT vs 다른 인증 방식**

| **특성** | **JWT** | **세션 기반** | **OAuth 2.0** | **SAML** |
| --- | --- | --- | --- | --- |
| **상태 관리** | Stateless | Stateful | 혼합 | Stateless |
| **서버 확장성** | 높음 | 낮음 | 높음 | 높음 |
| **구현 복잡도** | 중간 | 낮음 | 높음 | 매우 높음 |
| **클라이언트 지원** | 다양함 | 제한적 | 다양함 | 제한적 |
| **토큰 무효화** | 어려움 | 쉬움 | 가능 | 가능 |

**JWT 장점 및 한계**

- **장점**: 서버 확장성, 다양한 클라이언트 지원, 정보 포함, 소셜 로그인 통합
- **한계 및 대응**: 토큰 크기(필요 정보만 포함), 토큰 무효화(짧은 유효기간 설정), 클라이언트 보안(HTTPS 사용)

다양함

---

---

### 적용

**JWT 인증 흐름**

```flow
클라이언트 → 로그인 요청 → 서버
클라이언트 ← JWT 토큰 발급 ← 서버
클라이언트 → JWT 포함 API 요청 → JWT 인증필터 → SecurityContext
```

**주요 구현**

- **Security 설정**: CSRF 비활성화, Stateless 세션 정책, JWT 필터 추가, URL별 권한 설정
- **토큰 관리**: Access Token(60분), Refresh Token(7일) 분리, 토큰에 사용자 정보 포함
- **인증 필터**: 모든 요청에 JWT 검증, 예외처리, SecurityContext에 인증 정보 설정
- **결과**: 확장성 향상, 사용자 경험 개선, 유지보수 효율성, 보안성 강화

---

### 회고

- **보안과 사용자 경험 균형**: Access/Refresh Token 분리로 두 가지 모두 만족
- **확장성 고려 설계**: 소셜 로그인 도입 과정에서 확장 가능한 인증 시스템의 중요성 인식
- **유지보수 관점**: 철저한 예외 처리와 로깅의 중요성

**개선**

- 토큰 블랙리스트 도입으로 로그아웃/회원탈퇴 시 토큰 무효화
- 클라이언트 측 토큰 자동 갱신 로직 추가
- HTTPS 강제 및 보안 헤더 설정
- 중요 작업에 다중 인증(MFA) 도입 검토

프로젝트를 통해 보안, 사용자 경험, 확장성, 유지보수성의 균형을 고려하는 법을 배움

기술 선택 시 트렌드보다 프로젝트 요구사항에 맞는 최적의 선택이 중요하다는 점을 깨닫게 됨

</details>

<details>
  <summary> 🧠 System Monitoring → ☑️ Spring Actuator + Prometheus + Grafana </summary>

### 개요

- **목표** : 프로젝트의 잠재적 성능 문제를 미리 파악하고, 향후 서비스의 최적화 기반을 마련하기 위해 모니터링 시스템 도입 계획
- **선택** : Spring Boot Actuator + Prometheus + Grafana 의 조합
- **기대** : 주요 애플리케이션 매트릭을 실시간으로 수집, 저장, 시각화해 시스템 상태 가시성 확보 및 데이터 기반 성능 분석과 개선의 토대 마련
  - 매트릭 → JVM, HTTP 요청, DB 커넥션 등의 정보

---

### 문제 정의

<aside>

🤔 현재 프로젝트는 개발 초기 단계로, 배포 및 운영 시에 발생 가능한 성능 이슈를 사전에 감지하거나 발생 시 원인을 신속히 파악할 객관적 데이터가 없음

- 이슈 : 느린 응답, 과도한 리소스 사용, 특정 기능 병목 등
</aside>

- 문제
  - 가설에 의한 최적화 시도 → 효과 검증 불가, 비효율적
  - 장애 발생 시 원인 분석 시간 지연 → 사용자 경험 저하, 신뢰도 하락
  - 시스템의 현재 상태 파악이 어려움 → 잠재적 위험 예측 불가
- 핵심 : “측정할 수 없으면 개선할 수 없음” → 성능 개선의 첫걸음은 정확한 측정과 분석

---

### 해결방안 - 기술 결정

**☑️ `Spring Boot Actuator + Prometheus + Grafana`**

- Spring Boot Actuator : Spring Boot 애플리케이션 상태 및 매트릭 노출 표준, 쉬운 통합, 필수 기본 매트릭 제공, Micrometer 통한 확장성
- Prometheus : 매트릭 수집/저장 특화 시계열 DB. Pull 방식 아키텍처(애플리케이션 부하 적음), 강력한 쿼리 언어(PromQL), 활발한 오픈소스 생태계, 업계 표준
- Grafana : 데이터 시각화 및 대시보드 솔루션. Prometheus 등 다양한 데이터 소스 지원, 강력하고 유연한 시각화 옵션, 사용자 친화적 인터페이스, 커뮤니티 대시보드 활용 가능

**🤔 고민했던 구현 방법**

- Actuator 단독 사용 : 쉬운 구현, 데이터 저장/시각화 부재 → 추이 분석 및 병목 식별 어려움
- 오픈소스 APM (Pinpoint/Scouter) : 코드 레벨 상세 추적 등 강력한 기능 제공하나, 설치/설정 복잡성 높고 시스템 부하 큼, 현재 프로젝트 규모 및 학습 목표 고려시 과하다고 판단
- Actuator + Prometheus + Grafana : 구현 난이도(Docker 사용시 관리 용이), 기능성(매트릭 중심 모니터링), 시각화, 학습 가치, 실무 연관성 등 모든 측면에서 가장 균형 잡힌 선택

---

### 기술 구현

- 데이터 흐름(요청 → 처리 → 응답)

```flow
graph LR
    A[Portforu App (Actuator)] -- Generates Metrics --> B(Actuator Endpoint /actuator/prometheus);
    C(Prometheus Server) -- Scrapes (Pulls) --> B;
    C -- Stores --> D[(Time Series DB)];
    E(Grafana Server) -- Queries (PromQL) --> C;
    C -- Returns Data --> E;
    F[User Browser] -- Views Dashboard --> E;
    E -- Renders Graphs --> F;
```

- 설정(로컬/AWS 공통 기반)
  - Spring Boot
    - `build.gradle` : actuator, prometheus 의존성 추가
    - `application.yml` : prometheus 엔드포인트 추가 및 설정
  - 모니터링 서버(Docker Compose):
    - `docker-compose.yml` : prometheus, grafana 서비스 정의(이미지, 볼륨 마운트-설정/데이터 영속화, 포트 매핑-9090, 3000, 네트워크 설정)
    - `prometheus.yml` : `scrape_configs` 정의
      - `job_name` : 식별 가능한 이름
      - `metrics_path` : `/actuator/prometheus`
      - 등….
  - Grafana 설정:
    - 웹 UI 접속(localhost:3000 || `<EC2 Public IP>:3000`)
    - Data Source 추가 : Prometheus 선택, URL: `http://prometheus:9000`
    - Dashboard Import: [Grafana.com](http://grafana.com/) ID (e.g., 4701 JVM, 11378 Spring Boot) 활용 || 직접 생성
  - AWS 환경 추가 고려사항
    - Prometheus/Grafana용 EC2 인스턴스 생성 및 Docker/Docker Compose 설치
    - 보안그룹 설정:
      - Prometheus EC2 인스턴스 보안 그룹 -> EB 인스턴스 8080 포드 인바운드 허용
      - 내ㅐ IP -> Prometheus EC2 9090, 3000 포트 인바운드 허용

---

### 결과

- 실시간으로 애플리케이션의 주요 매트릭이 수집되어 Prometheus에 저장됨
- Grafana 대시보드를 통해 이 매트릭들이 시각화되어 시스템 상태를 한눈에 파악 가능

**기대 효과**

- 성능 병목 조기 식별(문제 미리 발견) : 특정 API의 응답 시간이 평소보다 급증하거나, DB 커넥션 풀이 고갈 임계치에 가까워지는 등의 이상 징후를 그래프 변화로 감지 가능
- 최적화 효과 정량적 검증 : 예를 들어, 회원 목록 조회 API에 인덱싱 적용 후 해당 API의 평균 응답 시간이 500ms에서 100ms로 감소했음을 Grafana 그래프로 확인하는 것과 같이 수치 기반으로 개선 효과 증명 가능
- 리소스 사용량 기반 용량 계획(서버가 얼마나 필요한지) : 시간에 따른 CPU, 메모리 사용량 추이를 분석해 향후 필요한 서버 증설 시점 예측에 활용 가능
- 데이터 기반 의사결정 : 어떤 기능을 개선해야 하는가?에 대한 답을 실제 사용 데이터를 통해 얻을 수 있음

---

### 회고

**배운 점**

- 단순 기능 구현을 넘어 성능 및 안정성이라는 중요 측면을 고려하게 됨
- 다양한 모니터링 도구를 비교 분석하고, 프로젝트 상황과 목표에 맞는 기술을 스스로 결정해보는 경험
- 실무에서 널리 사용되는 모니터링 시스템을 직접 구축하고 사용해보며 새로운 기술 습득
- Docker Compose를 활용해 여러 프로그램을 묶어서 관리하는게 편리하다는 것을 배움

**아쉬운 점**

- 네트워킹?: Docker 컨테이너와 호스트 또는 다른 컨테이너 간, 특히 AWS 환경의 네트워크 설정을 알아보는 것이 어려웠음
- PromQL : 데이터를 원하는 형태로 조회, 가공하기 위한 PromQL을 새로 학습해야 하는 어려움
- 매트릭 : Actuator가 제공하는 매트릭이 너무 많아 어느것을 중점적으로 모니터링하고 해석해야 할지에 대한 판단 기준을 정해야 헸음
- 실제 구현 및 운영 과정에서 발생할 수 있는 예상치 못한 문제들에 대한 우려

**향후 계획(개선)**

- Alerting(알림 기능) : Alertmanager를 Prometheus와 연동해 주요 매트릭이 임계치를 초과하면 자동알림 기능 구현 가능
- Logging : 로그 데이터까지 통합 모니터링 환경 구축 가능
  - ELK Stack(Elasticsearch, Logstash, Kibana) 또는 Loki 등 도입
- Tracing(요청 추적) : MSA로 확장될 경우, 분산 트레이싱 도구(Jaeger, Zipkin) 도입해 요청 흐름 추적 가능
- Custom Dashboard : 프로젝트 특화된 맞춤형 Grafana 대시보드를 직접 설계하고 구성 가능
- 실제 최적화 적용 : 구축된 모니터링 환경 기반으로 실제 성능 개선 작업(코드 최적화, DB 튜닝 등)을 진행하고 효과 측정 및 공유 가능

</details>

<details>
  <summary> 🧠 Optimization → 1️⃣ Indexing </summary>

### 목표

데이터베이스의 성능 개선을 위해 인덱싱 적용 (JPA의 `@Index` 활용), 각 엔티티별 쿼리 패턴을 분석해 단일 필드 인덱스와 복합 인덱스를 적절히 조합

> 인덱싱(Indexing)이란?
>
> - 데이터베이스에서 데이터 검색 속도를 향상시키기 위한 데이터 구조
> - 책의 색인과 유사, 이는 특정 컬럼의 값과 해당 레코드의 위치를 매핑해 빠르게 찾을수 있게 함.
> - 검색 성능 향상, 정렬 정능 향상, 조인 성능 향상을 위해 사용

---

### 고려사항

- **추가 저장 공간** : 인덱스는 별도의 데이터 구조로 저장되므로 추가 저장 공간이 필요
- **쓰기 작업 오버헤드** : INSERT, UPDATE, DELETE 작업 시 인덱스도 함께 업데이트해야 하므로 쓰기 작업의 성능이 저하될 수 있음
- **불필요한 인덱스** : 사용되지 않는 인덱스는 오히려 성능을 저하시킬 수 있음
  - 단일 외래키, 자주 변경되는 필드, 조회 빈도가 낮은 필드, 복잡한 복합 인덱스

**최적회된 접근 방식**

- 자주 조회되는 필드 : WHERE절에 자주 사용되는 필드
- 조인 조건에 사용되는 외래 키 필드
- ORDER BY 절(정렬)에 자주 사용되는 필드
- 중복을 방지해야 하는 유니크 제약조건이 필요한 필드

---

### 적용

<details>
  <summary> Scrap </summary>

  - `member_id` : 사용자별 스크랩 목록 조회 성능 향상
  - `job_posting_id` : 특정 채용 공고에 대한 스크랩 정보 조회 성능 향상
  - `member_id + job_posting_id(복합)` : findMemberByIdAndJobPostingId 메소드 성능 최적화
  - `member_id + is_deleted(복합)` : finaAllByMemberIdAndIsDeletedFalse 메소드

    ```java
    // 단일 필드 인덱스 + 복합 인덱스 조합
    @Table(
         name = "scraps",
         indexes = {
         	@Index(name = "idx_scrap_member_id", columnList = "member_id"),
         	@Index(name = "idx_scrap_job_posting_id", columnList = "job_posting_id"),
         	@Index(name = "idx_scrap_member_job_posting", columnList = "member_id,job_posting_id"),
         	@Index(name = "idx_scrap_member_is_deleted", columnList = "member_id,is_deleted")
    		}
    )
    ```

  ⚒️ 최적화 적용

  - 제거 : member_id, job_posting_id 단일 인덱스
  - member_id, job_posting_id의 조합으로 스크랩 여부를 자주 확인

    ```java
    @Table(
       	name = "scraps",
       	indexes = {
       		@Index(name = "idx_scrap_member_job_posting", columnList = "member_id, job_posting_id")
    		}
    )
    ```
</details>

<details>
  <summary> Portfolio </summary>

  - `member_id` : findAllByMemberId 메소드 성능 최적화, 사용자별 포트폴리오 목록 조회 성능 향상
  - `views` : 조회수 기준 정렬 또는 필터링 성능 향상

    ```java
    // 자주 조회되는 필드에 대해 단일 필드 인덱스 적용
    @Table(
       	name = "portfolios",
       	indexes = {
       		@Index(name = "idx_portfolio_member_id", columnList = "member_id"),
       		@Index(name = "idx_portfolio_views", columnList = "views")
    	  }
    )
    ```

  ⚒️ 최적화 적용

  - 사용자별 포트폴리오 목록 조회가 자주 일어날 것으로 판단

    ```java
    @Table(
       	name = "portfolios",
       	indexes = {
       		@Index(name = "idx_portfolio_member_id", columnList = "member_id")
    		}
    )
    ```
</details>

<details>
  <summary> Subscribe </summary>

  - `member_id` : 사용자별 구독 정보 조회 성능 향상
  - `membership_id` : 특정 멤버십에 대한 구독 정보 조회 성능 향상
  - `member_id + membership_id(복합)` : hasValidSubscription 쿼리 성능 최적화
  - `status` : 구독 상태별 필터링 성능 향상
  - `end_date` : 만료일 기준 조회 성능 향상
  - `status + end_date + is_deleted(복합)` : bulkExpireSubscriptions 쿼리 성능 최적화

    ```java
    // 단일 필드 인덱스 + 복합 인덱스 조합, 특히 스케줄러에서 실행되는 만료 처리 쿼리 성능 고려
    @Table(
       	name = "subscribes",
       	indexes = {
       		@Index(name = "idx_subscribe_member_id", columnList = "member_id"),
       		@Index(name = "idx_subscribe_membership_id", columnList = "membership_id"),
       		@Index(name = "idx_subscribe_member_membership", columnList = "member_id, membership_id"),
       		@Index(name = "idx_subscribe_status", columnList = "status"),
       		@Index(name = "idx_subscribe_end_date", columnList = "end_date"),
       		@Index(name = "idx_subscribe_status_end_date_is_deleted", columnList = "status, end_date, is_deleted")
    	  }
    )
    ```

  ⚒️ 최적화 적용

  - 제거 : member_id, membership_id 단일 인덱스
  - status, end_date, is_deleted -> status, end_date로 충분할 것으로 판단

    ```java
    @Table(
       	name = "subscribes",
       	indexes = {
       		@Index(name = "idx_subscribe_member_membership", columnList = "member_id, membership_id"),
       		@Index(name = "idx_subscribe_status_end_date", columnList = "status, end_date")
       	}
    )
    ```

  → member_id, membership_id로 구독 정보 조회가 자주 일어날 것으로 예상
  → status, end_date로 만료된 구독을 찾는 스케줄러 작업이 존재

</details>

<details>
  <summary> JobPosting </summary>

  - `link` 유니크 인덱스 : findByLink 메소드 성능 최적화, 링크 중복 방지
  - `company` : 회사명 기준 검색 성능 향상
  - `hiringEndAt` : 채용 마감일 기준 검색 및 정렬 성능 향상

    ```java
    // 자주 조회되는 필드, 검색 조건으로 사용되는 필드에 인덱스 적용
    @Table(
       	name = "job_postings",
       	indexes = {
       		@Index(name = "idx_job_posting_link", columnList = "link", unique = true),
       		@Index(name = "idx_job_posting_company", columnList = "company"),
       		@Index(name = "idx_job_posting_hiring_end_at", columnList = "hiringEndAt")
       	}
    )
    ```

  ⚒️ 최적화 적용

  - 제거 : company, hiringEndAt(필터링 빈도가 낮다고 판단)

    ```java
    @Table(
       	name = "job_postings",
       	indexes = {
       		@Index(name = "idx_job_posting_link", columnList = "link", unique = true)
       	}
    )
    ```

  → link는 채용 공고 조회에 사용되며, 유니크 값임

</details>

<details>
  <summary> UploadedFile </summary>

  - `fileUrl` 유니크 인덱스 : findByFileUrl 메소드 성능 최적화, 파일 URL 중복 방지
  - `used + createdAt(복합)` : findUnusedFilesOlderThan 쿼리

    ```java
    // 스케줄러에서 실행되는 미사용 파일 정리 쿼리의 성능을 고려한 복합 인덱스 적용
    @Table(
       	name = "uploaded_files",
       	indexes = {
       		@Index(name = "idx_uploaded_file_url", columnList = "fileUrl", unique = true),
       		@Index(name = "idx_uploaded_file_used_created_at", columnList = "used, createdAt")
       	}
    )
    ```

  ⚒️ 최적화 적용

    ```java
    @Table(
       	name = "uploaded_files",
       	indexes = {
       		@Index(name = "idx_uploaded_file_url", columnList = "fileUrl", unique = true)
       	}
    )
    ```

  → fileUrl로 파일을 조회하며, 이는 유니크 값

</details>

<details>
  <summary> Payment </summary>

  - `subscribe_id` : 구독별 결제 정보 조회 성능 향상, existsPayment 쿼리 성능 최적화
  - `status` : 결제 상태별 필터링 성능 향상
  - `created_at` : 결제 일자 기준 조회 및 정렬 성능 향상

    ```java
    // 자주 조회되는 필드와 필터링 조건으로 사용되는 필드에 인덱스 적용
    @Table(
       	name = "payments",
       	indexes = {
       		@Index(name = "idx_payment_subscribe_id", columnList = "subscribe_id"),
       		@Index(name = "idx_payment_status", columnList = "status"),
       		@Index(name = "idx_payment_created_at", columnList = "created_at")
       	}
    )
    ```

  ⚒️ 최적화 적용

  - 제거 : created_at(날짜별 조회는 자주 일어나지 않을 것으로 판단)

    ```java
    @Table(
       	name = "payments",
       	indexes = {
       		@Index(name = "idx_payment_subscribe_id_status", columnList = "subscribe_id, status")
       	}
    )
    ```

  → 특정 구독의 결제 상태를 확인하는 쿼리가 빈번할 것으로 예상

</details>

<details>
  <summary> Member </summary>

  ⚒️ 최적화 적용

  - `email` 유니크 인덱스 : findByEmail 메소드의 성능 최적화 - 이메일 중복 방지

    ```java
    // 로그인 및 사용자 조회에 자주 사용되는 이메일 필드에 유니크 인덱스 적용
    @Table(
       	name = "members",
       	indexes = {
       		@Index(name = "idx_member_email", columnList = "email", unique = true)
       	}
    )
    ```
</details>

---

### 기대 효과

- 성능 향상
  - 사용자별 데이터 조회
  - 특정 조건에 맞는 데이터 필터링
  - 복합 조건 검색
  - 스케줄러 작업
  - 구독 만료 처리 작업
  - 미사용 파일 정리 작업
- 데이터 무결성 강화
  - 유니크 인덱스를 통한 중복 데이터 방지

</details>

<details>
  <summary> 🧠 Optimization → 2️⃣ Query Optimization </summary>

### 목표

쿼리의 성능 향상 → 같은 결과를 더 빠르고 효율적으로 얻어내기

- 응답 시간 개선
- 서버 부하 감소
- 확장성 확보
- 사용자 경험 향상

---

### JPA, Spring Data JPA에서 자주 발생하는 성능 이슈

- N+1 문제 : 가장 흔한, 연관 관계가 있는 엔티티 조회 시 처음 엔티티 목록을 가져오는 쿼리 1번과 각 엔티티의 연관 엔티티를 가져오는 쿼리 N번이 추가로 발생하는 문제
  - EntityGraph 사용
  - JPQL fetch join 사용
- 페이징 처리 : 페이징 처리 시 데이터 수를 계산하는 카운트 쿼리가 비효율적으로 실행될 수도 있음
  - 카운트 쿼리 분리
- 불필요한 데이터 조회 : 필요 데이터만 조회하지 않고 엔티티 전체를 조회할 시 불필요한 데이터 전송 발생
  - 쿼리에서 새로운 dto 생성??

---

### 최적화 방안

- `@EntityGraph` : JPA에서 연관 엔티티를 함께 로딩하는 가장 간단한 방법

    ```java
    @EntityGraph(attributePaths = {"연관 엔티티"})
    List<Cat> findById(Long id)
    
    ```

  - 기존 메소드를 그대로 사용하면서 연관 엔티티 로딩 방식만 변경할 수 있음 → 간단한 구현
  - 복잡한 조건에는 제한적, 너무 많은 연관 엔티티를 지정하면 오히려 성능 저하를 초래할 수 있음
- Fetch Join : JPQL의 연관 엔티티를 함께 로딩하는 강력한(?) 방법

    ```java
    @Query("SELECT c FROM CAT c JOIN FETCH c.name WHERE c.age = :age")
    뭐 대충 이런 식이긴 해
    
    ```

  - 명시적인 제어 가능 -> 복잡한 조건 지원
  - 코드가 복잡해질 수도 있기에 페이징과 함께 사용할때 주의(?)
- 읽기 전용 트랜잭션 사용 : 조회 전용 메소드에 적용하면 변경 감지(Dirty Checking)을 비활성화해 성능 향상

    ```java
    @Transactional(readOnly = true)
    서비스 메소드() { }
    
    ```

  - 간단한 구현 → 메모리 사용량 감소
  - 트랜잭션 내에서 엔티티 수정이 필요한 경우에는 사용 불가 → 읽기 전용(!!)
- 필요한 데이터만 조회 (DTO 프로젝션) : 선택적으로 필요 데이터만 조회해 불필요한 데이터 전송 방지

    ```java
    @Query("SELECT new 디티오_전체_경로(a.추출할 컬럼-데이터, ...)" +
    	"FROM 테이블 a JOIN a.뭐시기 b")
    
    ```

  - 필요 데이터만 전송해 네트워크 트래픽 감소 -> 메모리 사용량 감소
  - DTO 클래스 추가해야함 -> 엔티티의 기능 사용불가

---

### 고려사항

- 조기 최적화 지양
  ”조기 최적화는 모든 악의 근원이다.” - 도널드 크누스 ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ
  → 실제로 성능 문제가 발생한 부분을 찾아 최적화하는 것이 효율적임
- 측정 기반 최적화
  (요거 써봐야겟다)
  - 최적화 전후의 성능을 측정해 실제 개선효과를 확인하는 것이 중요 → 가정x 데이터 기반o

    ```java
    long startTime = System.currentTimeMillis();
    실행 메소드
    long endTime = System.currentTimeMillis();
    System.out.println("실행 시간: " + (endTime - startTime) + "ms");
    
    ```

- 트레이드오프 고려
  - 예) - 코드 복잡성 vs 성능 향상 - 메모리 사용량 vs 실행 속도 - 개발 시간 vs 성능 개선 효과
- 데이터베이스 로그 확인
  - 실제 어떤 SQL이 실행되는지 확인

    ```java
    spring.jpa.show-sql=true
    spring.jpa.properties.hibernate.format_sql=true
    logging.level.org.hibernate.type.descriptor.sql=trace
    
    ```


---

### 적용 계획

- 성능 이슈 식별
  - 애플리케이션 로그에서 반복적인 쿼리 패턴 찾기
  - 응답 시간이 느린 API 엔트포인트 확인
  - 사용자 불만이 많은 기능 분석
- 단계적 최적화 적용
  - 가장 큰 영향을 미치는 문제부터 해결
  - 한번에 하나씩 변경하고 효과 측정
  - 효과가 없으면 롤백하고 다른 방법 시도
- 최적화 결과 문서화하기
  - 팀원들과의 지식 공유
  - 유사 문제 발생 시 참고 자료로 활용
  - 포트폴리오 작성에 활용
- 도구?
  - Spring Boot Actuator(모니터링)
  - p6spy(SQL 실행 시간 로깅)
  - JMeter(부하 테스트)
  - VisualVM(JVM 모니터링)

</details>

<details>
  <summary> 🧠 Optimization → 3️⃣ Caching </summary>

### 캐싱 전략

- **선택적 캐싱** : 모든 엔티티가 아닌 자주 조회되는 데이터, 거의 변경되지 않는 데이터에 우선 적용
- **데이터 특성별 TTL 설정** : 포트폴리오(15분), 멤버십(1시간), 채용공고(30분) 등 특성에 맞게 차등 적용
- **개인화된 데이터 제한적 캐싱** : 회원정보, 구독/스크랩 정보는 짧은 TTL(5-10)분 적용
- **캐시 무효화 죄적화** : 데이터 변경 시 관련 캐시만 선택적으로 무효화

</details>

<details>
  <summary> 🧠 S3로 이미지 업로드 후 수정시 S3에 저장된 이전 파일을 어떻게 처리할까? </summary>

### 배경

포트폴리오 작성시 다운받을수있는 파일 S3에 저장후 DB에 URL을 스트링으로 저장하는 로직과 업데이트 로직 구현

### 고민 & 결정

- 포트폴리오에 파일을 수정하였을때 S3에 이전에 저장되어있던 파일을 삭제하는것에 고민
- 파일을 수정을 하였을때 바로 이전에 파일을 바로 삭제를 한다면 여러가지 문제가 있다
1. 네트워크 지연
   파일을 삭제하고 다시 업로드하는 과정에서 네트워크 지연이 발생할 수 있습니다. 특히 대용량 파일의 경우, 삭제와 업로드 사이에 시간이 소요되어 전체적인 응답 속도가 느려질 수 있습니다.
2. API 호출 증가
   파일을 삭제한 후 다시 업로드하는 과정은 두 번의 API 호출을 필요로 합니다. 이로 인해 API 호출 수가 증가하고, 서버에 부하가 커질 수 있습니다.
3. 일관성 문제
   파일 삭제와 업로드 사이의 시간 동안 다른 사용자들이 파일에 접근하려고 할 경우, 일관성이 깨질 수 있습니다. 이로 인해 사용자 경험이 저하될 수 있습니다.
4. 리소스 낭비
   삭제 후 파일을 업로드하는 과정에서, 임시로 공간을 차지하게 되어 서버나 스토리지의 리소스가 비효율적으로 사용될 수 있습니다. 특히 파일이 크고 잦은 수정이 이루어질 경우, 이러한 현상이 더욱 두드러질 수 있습니다.
5. 오류 처리 복잡성
   파일 삭제 과정에서 오류가 발생할 경우, 이후 파일 업로드가 불가능해질 수 있으며, 이로 인해 복잡한 오류 처리 로직이 필요해질 수 있습니다.
6. 스케일링 문제
   사용자가 많아질 경우, 동시에 파일 수정 요청이 들어올 수 있으며, 이때 삭제와 업로드 과정이 병목 현상을 일으켜 성능 저하를 유발할 수 있습니다.

### 해결

아마존 AWS가 제공하는 LifeCycle을 이용하여 수정되서 쓰지않는 이전의 파일들은 inactive로 옮겨 저장하고 1일후에 삭제되게 설정

</details>

<details>
  <summary> 🧠 채용공고를 어떻게 가져와야 할까? </summary>

## 배경 - 처음부터 순탄치 않다

저희가 개발하려는 서비스에서는 채용공고에 대한 정보를 제공해주는 기능을 포함하고 있습니다.

이에 따라 잡코리아,사람인,고용24에서 제공하는 오픈API를 사용하려고 했으나 개인 사용자가

해당 오픈API를 사용하기에는 제약이 있었습니다.

오픈API를 사용하기 위해 당사에 메일을 수차례 보내보았지만 답변을 받지 못하였습니다.

그래서 직접 웹 크롤링을 통해 데이터를 수집하고 이를 가공하여 저장하는 방안을 채택하였습니다.

크롤링한 데이터를 단순히 메인 서비스에 전달해주기만 하면 되므로 크롤링 기능을

메인 서비스와 분리하여 별도의 프로젝트로 구현했습니다.

채용 공고 데이터 크롤링 기능은 웹 페이지 파싱,데이터 전처리,스크롤 처리 등 반복적인 입출력

중심의 작업이 많아 코틀린을 사용하도록 결정하였습니다.

## 왜 코틀린인가?

1. 코틀린의 코루틴과 같은 기능은 비동기 처리를 보다 쉽게 구현할 수 있어
   멀티스레드 작업이 용이합니다.
2. 크롤링 작업은 네트워크 I/O와 대기 시간이 많은 작업이기 때문에 코루틴을 사용하여 스레드 수를
   최소화 하면서 높은 동시성을 구현할 수 있습니다.
3. 또한 코틀린은 Java에 비해 훨씬 간결한 문법을 제공하여 코드의 양은 줄이고 가독성은 높이는
   개발이 가능합니다.

## 요구사항

주요 요구사항은 다음과 같이 4개로 정의할 수 있습니다.

1. 다양한 채용 사이트로부터 자동 데이터 수집
   현재는 잡코리아와 사람인에서만 데이터를 수집하지만 여러 사이트의 채용 정보를 자동화된
   로직으로 수집할 예정입니다.
2. 신뢰성 있는 데이터 파싱 및 저장
   각 사이트별 HTML구조에 따라 서로 다른 파서 로직을 적용해 올바른 데이터를 추출하고 DB에 안전하게 저장해야 합니다.
3. 동시 실행 및 성능 최적화
   사이트별 크롤링 API를 분리하여 멀티쓰레드로 크롤링 진행하여 크롤링 시간을 단축해야 합니다.
4. 데이터 중복 문제 해결
   다양한 채용 사이트에서 데이터를 수집하기에 같은 채용 공고가 여러개 저장되는 데이터 중복 문제가
   발생할 수 있습니다.
   같은 채용 공고를 저장하지 않도록 데이터 중복 문제를 해결해야 합니다.

## 결정 - 채용공고를 보다 가치있게

신뢰성 있고 유용한 데이터를 가져오기 위해서 사용자 관점에서 생각해보았습니다.

현재 저도 취업 준비생이기에 채용 공고를 볼 때 “어떤 정보가 나에게 필요할까?

나를 필요로 하는 회사를 어떻게 찾을 수 있을까?” 를 생각해보았습니다.

채용 공고는 기본 정보와 핵심 정보로 나눌 수 있을 것이라고 생각했습니다.

기본 정보에는 회사이름,위치,봉급 등 회사에 대한 정보입니다.

핵심 정보는 필수 기술 스택,핵심 역량,우대 사항과 같은 회사가 지원자에게 요구하는 사항들입니다.

사용자는 핵심 정보를 토대로 자신에게 맞는 회사를 찾을 필요성이 있다고 생각이 되었습니다.

이에 따라 핵심 정보는 키워드로 저장을 하여 데이터 정규화 및 필터링에 사용하기로 결정하였습니다.

또한 크롤링 작업은 주기적으로 수행되어야 하지만

크롤링 기능과 메인 서비스를 같은 프로젝트 내에서 실행시키면 크롤링 작업마다

메인 서비스도 같이 실행되므로 이는 서버 부하 , 성능 저하 및 유지 보수의 어려움을 초래할 수 있습니다.

따라서 메인 프로젝트와 크롤링 기능은 별도의 프로젝트로 구현하도록 결정하였습니다.

## 회고

코틀린을 사용하여 크롤링 로직을 모듈화하고 각 사이트별 파서를 분리함으로써

유지보수와 확장이 용이한 구조를 만들 수 있었습니다.

동시에 사이트별 HTML 구조 변경에 유연하게 대응할 수 있어 추후 개선이 쉽도록 하였습니다.

Selenium과 Jsoup에 대해서 잘 알지못하는 상태에서 크롤링을 시도하려니 이해가 안되는 부분도 많았고

크롤링 코드 구조도 잘 알지 못하여 많은 어려움이 있었습니다.

하지만 코드를 하나하나씩 작성해나가며 데이터를 어떻게 처리해야할지 고민해본 결과

많은 공부가 되었습니다.

이로써 HTML 구조 별로 데이터를 처리하는 법과 데이터 정규화와 데이터를 가공하는 법을

배울 수 있었습니다.

</details>

<details>
  <summary> 🧠 크롤링 시에 중복 채용공고 검증 성능 향상시키기 </summary>

## 배경 - 계속 거기까지 가야돼?

기존 시스템에서는 채용공고를 크롤링할 때마다 jopPostingRepositry.findByLink 로

매 URL 마다 DB 조회를 수행하여 중복을 검사했습니다.

이는 대상 URL 수가 많아질수록 매번 발생하는 select 쿼리가 병목이 되어 전체 스케줄링 속도를

저하시켰습니다.

## 문제 사항

매 URL마다 DB I/O를 발생시켜 `SELECT … WHERE link = ?` 쿼리를 실행하고 있었습니다.

크롤링 대상 수가 커질수록 DB 부하가 증가하여 응답 지연 및 스케줄링 전체 속도가 저하되었습니다.

## 고려 사항 -

수많은 채용공고를 크롤링할 때 중복 URL 검증의 성능을 어떻게 향상시킬 수 있을까 찾아보던 도 중

Redis 비트맵을 활용한 BloomFilter라는 것을 알게되었습니다.

BloomFilter를 사용하게 되면 비트맵 조회만으로 중복URL 판별이 가능하였고

DB 접근 없이도 “확실히 신규”인 URL만 저장하도록 분기할 수 있어 전체 I/O를 대폭 절감할 수 있을것이라고

판단하여 BloomFilter를 적용하기로 결정하였습니다.

## 해결방안

핵심 함수 구조: `isNewUrl(url: String): Boolean`

프로젝트에서는 BloomFilter 기능을 UrlBloomFilterService클래스의 isNewUrl()함수에 구현했습니다.

이 함수는 다음과 같은 흐름으로 작동합니다.

### 1️⃣ URL 해시 인덱스 생성

- `url + seed` 형태로 URL에 시드를 더해 5개의 해시 인덱스를 생성합니다.
- 내부적으로는 `MD5` 해시 알고리즘을 사용하고, 오버플로를 방지하기 위해 `Long` 타입으로
  누적 계산합니다.
- 최종적으로는 bitArraySize(예: 10,000,000) 범위 내에서 인덱스를 반환합니다.

```
val indexes = (0 until hashCount).map { hash(url, it) }
```

### 2️⃣ Redis에서 비트 상태 조회 (중복 여부 판단)

- Redis의 비트맵(`bloom:processedUrls`)에서 해당 인덱스의 비트가 모두 1인지 확인합니다.
- Spring RedisTemplate의 `executePipelined`를 사용해 여러 인덱스를 한 번에 처리합니다.

```
val alreadyExists = existsBits.all { it == true }
```

### 3️⃣ 중복 판단 결과에 따라 처리 분기

- **모든 비트가 1이면** → 이미 등록된 URL로 간주하고 `false` 반환합니다 (스킵)
- **하나라도 0이면** → 새 URL이므로 `SETBIT` 명령으로 해당 비트를 모두 1로 설정하고, `true` 반환합니다

```
if (!alreadyExists) {
    // 새 URL → 비트 등록
    redisTemplate.executePipelined { connection ->
        indexes.forEach { i ->
            connection.setBit(redisKey.toByteArray(), i.toLong(), true)
        }
        null
    }
}
```

### 4️⃣ 로그 출력

- 각 URL마다 Bloom Filter 판별 결과를 콘솔에 출력해 디버깅 및 로그 추적에 활용합니다.

```
println("[Bloom] 기존 URL 스킵: $url")
println("[Bloom] 신규 URL 등록: $url")
```

이 방식으로 매번 DB에 접근하지 않고도, 크롤링 대상 URL의 중복 여부를 초고속으로 판별할 수 있습니다.

### 목표: "이 URL, 이미 크롤링한 거 아니야?"

1. Redis 기반으로 BitArray를 구성하고, key는 `bloom:processedUrls`로 지정합니다
2. 해시 함수는 5개로 고정합니다. (`hashCount = 5`)
3. 각 URL을 해시 함수에 넣어 나온 인덱스를 기반으로 Redis의 비트 위치 확인

```
val exists = indexes.all { redis.getBit(key, it) == true }
```

1. 모두 1이면 이미 크롤링된 것으로 간주합니다 → 스킵
2. 하나라도 0이면 새 URL → 저장하고 비트 배열 갱신합니다.

    ```kotlin
    
    // Before
    if (jobPostingRepository.findByLink(link) == null) save(link)
    // After
    if (urlBloomFilterService.isNewUrl(link)) save(link)
    
    ```


## 💡 [해결 완료]

- **DB 조회 없이** Redis 기반 Bloom Filter만으로 중복 여부를 판별하도록 기능 전환했습니다.
- 스케줄러ㆍ컨트롤러ㆍ서비스 전반에서 `isNewUrl()` 호출로 모든 URL을 필터링합니다.
- **실제 로그 예시**

    ```
    
    [Bloom] 신규 URL 등록: https://…rec_idx=50569763
    [Bloom] 기존 URL 스킵: https://…rec_idx=50569665
    ```

- 반복되는 SELECT 쿼리 제거로 **크롤링 처리 속도를** 개선했습니다.

</details>

<details>
  <summary> 🧠 PortForU 어떻게 배포할까? </summary>

저희가 개발한 프로젝트를 실제로 사용자에게 제공하기 위해서는 aws를 이용한 배포가 필수적입니다.

이에 따라 저희는 저희 프로젝트에 맞도록 시스템 아키텍처를 설계하고 배포를 수행하였습니다.

### 1️⃣ **VPC - 기본이 되는 네트워크, 우리만의 VPC 만들기**

먼저 인프라의 기반이 되는 VPC부터 구성했어요. AWS에서 기본으로 제공하는 VPC 말고,

보안을 위해 저희만의 `portforu-vpc`를 만들어서 퍼블릭/프라이빗 서브넷을 나눴습니다.

- **퍼블릭 서브넷**엔 인터넷과 직접 연결되는 **Bastion 서버**와 **NAT Gateway**, 그리고 외부 요청을 받는 **Load Balancer**가 있습니다.
- **프라이빗 서브넷**엔 사용자 요청을 처리하는 **웹 서버**, **크롤러**, 그리고 **데이터베이스**를 넣었습니다.

이후에 장애를 대비하여 서브넷을 두 개 AZ에 걸쳐 분산시켰습니다 .

라우팅 테이블도 각각 서브넷별로 따로 구성해서, 퍼블릭은 IGW를 통해 나가고, 프라이빗은 NAT를 통해

나갈 수 있도록 했습니다.

### **2️⃣ 보안 그룹 - 출입증 있으신가요?**

AWS 인프라에서 출입문 역할을 하는 보안그룹을 세세하게 나눴습니다.

- **API 서버**는 ELB에서 오는 80번 포트만 받고, SSH는 Bastion에서만 가능하게 했습니다.
- **크롤러 서버**는 내부에서만 쓰이는 서비스니까, 외부에 노출하지 않도록 8081 포트는 내부에서만 허용.
- **RDS는 절대 외부 접근 불가**, 오직 API 서버, Crawler, Bastion에서만 접근 가능하게 설정했습니다.
- Bastion만 **외부에서 SSH 접속 가능**, 그것도 특정 IP나 키를 가진 사람만이 접근이 가능합니다.

이렇게 나누니까 서비스별로 어떤 포트가 열려 있는지, 누가 접근 가능한지 한눈에 보여서 관리가 쉬웠습니다.

### 3️⃣ **EC2 인스턴스 - 용도에 맞게, 가볍고 단단하게**

총 3개의 인스턴스를 운영하고 있습니다.

- **Bastion 서버 (퍼블릭)**: SSH를 통해 내부 인스턴스를 제어하는 관리용 서버
- **크롤러 서버 (프라이빗)**: Spring Boot 기반의 Kotlin 크롤링 서비스가 Docker로 실행됨
- **Elastic Beanstalk 인스턴스 (프라이빗)**: Java + Spring 기반의 메인 웹 애플리케이션이 Docker 컨테이너로 운영됨

- **✅ 왜 Ubuntu인가?**

  ### ① **Spring & Spring Boot 기반 애플리케이션 배포에 최적화된 패키지 환경**

  - 저희의 메인 서비스는 Java 기반의 Spring 애플리케이션이고,
    Crawler는 Kotlin + Spring Boot 기반입니다.
  - Ubuntu는 Java, Kotlin, Gradle, Maven, Docker 등 **Spring 프로젝트에 필요한
    모든 툴과 런타임 환경을 안정적으로 지원**합니다.
  - `openjdk-17`, `docker.io`, `chromium-browser`, `chromedriver`, `xvfb` 같은
    의존 패키지들을 설치하고 실행하기 위해선,
    **패키지 버전 호환성과 안정적인 저장소(apt)가 확보된 운영체제**가 필요합니다.
  - 실제로 크롤러 서버에선 headless 크롬 기반 Selenium 크롤링을 실행하는데, Ubuntu는 관련
    라이브러리 설치(`libgtk-3`, `libnss3`, `libgbm1` 등)와 디스플레이 환경 구축이 가장 매끄럽습니다.

    ---

  ### ② **Docker 기반 CI/CD 환경과의 높은 호환성**

  - 저희 CI/CD는 GitHub Actions에서 Docker 이미지를 빌드하고 ECR에 푸시한 뒤,
    EC2 인스턴스에서 컨테이너를 실행하는 방식입니다.
  - 공식 Docker 이미지들이 대부분 **Ubuntu 기반**으로 제공되기 때문에,
    EC2 인스턴스의 운영체제도 동일하게 맞춰주는 게 가장 무난하고
    **런타임 환경 차이로 인한 에러를 줄일 수 있습니다**.
  - 예를 들어, 크롤러 서비스의 Dockerfile은 Selenium + ChromeDriver 기반 이미지 위에
    앱을 얹는 방식인데,
    이때도 Chrome 설치와 chromedriver의 호환이 Ubuntu 기준으로 가장 안정적으로 동작했습니다.

    ---

  ### ③ **Spring 애플리케이션의 리소스 요구를 만족하면서도 관리가 쉬움**

  - EC2 인스턴스는 `t2.micro`, `t3.medium` 정도로 가벼운 사양을 사용하고 있습니다. 이런 환경에서 리소스 사용량이 적고, 서비스 실행에 필요한 패키지만 깔아서 쓸 수 있는 OS가 필요했습니다.
  - Ubuntu는 필요 없는 데몬 없이 **가볍게 커스터마이징 가능**하고, Spring Boot 앱을 실행하기에 딱 알맞은 수준의 리눅스입니다.
  - 특히 로그 관리(`journalctl`, `rsyslog`, `systemd`), 자동 시작 등록, JAR 실행환경 구성 등이 Ubuntu에서 훨씬 **일관되고 편리하게 관리**됩니다.

    ---

  ### ④ **보안 업데이트와 커뮤니티 지원도 믿을 만해서 선택**

  - Ubuntu는 보안 패치도 빠르고, LTS 버전은 최소 5년 지원이라
    **운영 서버로 쓰기에 매우 안정적입니다**.
  - 공식 문서, GitHub, Stack Overflow에서도 대부분 Ubuntu 기준으로 설명되어 있어 문제 해결도
    수월하고, 운영에 대한 불확실성이 적습니다.

### 4️⃣ **ECR + GitHub Actions - 빌드와 배포가 클릭 한번이면 끝난다고!?**

코드를 dev 브랜치에 푸시하면 자동으로 CI/CD가 되도록 구성했습니다.

- GitHub Actions에서 Docker 이미지를 빌드해서 **ECR(Elastic Container Registry)**에 푸시하고,
- Bastion 서버를 거쳐서 EC2에 Docker pull & run 하도록 자동화했습니다.
- `.env` 파일도 같이 복사해서 민감 정보도 따로 관리했습니다.

덕분에 배포가 깃 푸시 한 번이면 끝납니다.  
개발자 입장에서는 손가락 한번 딸깍하면 빌드와 배포가 한번에 자동으로 이루어져 매우 편리해졌습니다.

### 5️⃣ **Elastic Beanstalk - 웹 서버는 자동으로 돌게**

웹 서비스는 **Elastic Beanstalk**로 올렸습니다.

- 스케일링도 자동
- 로드밸런싱(ALB)도 자동
- 헬스체크도 자동

Docker 기반이라서 만든 이미지만 잘 올려주면 알아서 돌아갑니다.
실패한 배포는 롤백도 되고, 운영 부담이 확 줄었습니다.

### 6️⃣ **RDS - DB는 프라이빗하게, 안정적으로**

데이터베이스는 **RDS MySQL**을 사용했습니다.

- 퍼블릭 접근은 막고,
- API 서버, Crawler만 접근 가능하게 했습니다.

### 7️⃣ **Route 53 - 도메인 관리도 AWS에서**

`portforu.online` 도메인을 구입해서 **Route 53**에서 DNS를 설정했습니다.

- `api.portforu.online` 도메인을 ALB로 연결해서 사용자가 서비스에 접속할 수 있게 했고,
- 이메일 관련 인증(TXT, CNAME 등)도 같이 설정했습니다.

이렇게 하니까 인프라가 하나의 AWS 안에서 잘 정돈되고, 연결도 매끄러웠습니다.

## 결론 - 서비스 운영을 위한 최적의 선택

PortforU 프로젝트의 인프라는 **보안**, **확장성**, **자동화**, **유지보수 효율성**을 기준으로 설계되었습니다.

- 퍼블릭/프라이빗 서브넷 분리, Bastion 기반의 SSH 제한, 보안 그룹 간의 명확한 정책 설정으로 **보안성을 확보**했고,
- Elastic Beanstalk + Docker + GitHub Actions + ECR의 조합을 통해 **CI/CD 전체를 자동화**하여 배포 속도와 안정성을 개선했습니다.
- Ubuntu 기반의 EC2 인프라는 Spring/Kotlin 애플리케이션이 요구하는 **패키지 호환성, 리소스 효율성, 관리 편의성**을 충족시켰습니다.
- ALB는 자동 트래픽 분산과 장애 대응으로 **높은 가용성을 보장**하고,
- Route 53을 통해 **도메인, 이메일 인증, API 접속을 모두 통합 관리**하며 인프라 전체를 AWS 안에서 효율적으로 운영하고 있습니다.

이번 인프라 설계와 구축 과정을 통해 단순히 서비스를 띄우는 단계를 넘어,
**지속 가능하고 운영 가능한 구조**로 성장할 수 있는 기반을 마련했습니다.

</details>

<details>
  <summary> 🧠 소셜 로그인 기능 도입을 위한 인증 구조 설계 및 결정 </summary>

- **소셜 로그인 기능 도입을 위한 인증 구조 설계 및 결정**

  ### [배경]

  - 기존에는 이메일/비밀번호 기반의 JWT 로그인만을 제공
  - 사용자 접근성과 편의성 향상을 위해 **Naver, Google, Kakao** 기반의 OAuth2 소셜 로그인 기능을 도입
  - 이미 구축된 Spring Security + JWT 인증 구조에 소셜 로그인을 충돌 없이 통합하는 것이 주요 목표

    ---

  ### [고려사항]

  - **기존 인증 흐름과의 통합성**: JWT 기반 인증 로직과 동일한 방식으로 소셜 로그인 사용자도 처리해야 함
  - **회원 식별 방식의 충돌 방지**: 동일 이메일을 사용한 소셜 로그인/일반 로그인 사용자가 중복되지 않도록 처리
  - **플랫폼 확장성**: 소셜 로그인 제공자(Google, Naver, Kakao)마다 제공하는 사용자 정보 구조가 달라 일관성 있는 처리 구조 필요
  - **자동 회원가입 여부**: 소셜 로그인 최초 시도 시 자동으로 회원가입 처리되어야 함
  - **보안**: 사용자 정보 및 토큰 발급에 있어 안전한 방식 적용 필요

    ---

  ### [결정]

  💡 **인증 구조 및 기술 스택 결정**

  - Spring Security의 OAuth2LoginConfigurer 기반으로 인증 흐름 구성
  - 기본 구현체인 DefaultOAuth2UserService를 상속한 CustomOAuth2UserService를 구현하여 플랫폼별 사용자 정보 파싱 및 자동 회원가입 로직 수행
  - 로그인 성공 시 OAuth2AuthenticationSuccessHandler에서 JWT access/refresh token ****생성 후 JSON 형태로 응답

  💡 **사용자 정보 통합 전략**

  - 각 소셜 로그인 제공자의 사용자 정보를 추상화한 OAuth2UserInfo 인터페이스를 정의하고, `GoogleUserInfo`, `KakaoUserInfo`, `NaverUserInfo` 등으로 세분화하여 구조적 일관성 확보
  - OAuth2UserInfoFactory를 통해 provider 이름에 따라 적절한 구현체 생성

  💡 **사용자 식별 및 저장 방식**

  - DB에서 소셜 로그인 사용자와 일반 로그인 사용자를 구분하기 위해, 이메일에 provider prefix를 붙여 저장

    예: `google_user@example.com`, `naver_user@example.com`

  - 이를 통해 DB unique 제약 조건 위반 방지 및 충돌 없는 사용자 관리 가능

  💡 **인증 주체 통합**

  - 모든 로그인 방식에서 Member 엔티티를 중심으로 인증 정보를 구성하고, CustomOAuth2User를 통해 OAuth2User와 Spring Security의 인증 흐름에 적합한 형식으로 변환
  - JWT 발급은 기존과 동일한 JwtUtil을 사용하여 access/refresh token 생성 후 클라이언트로 반환

    ---

  ### [결론]

  - 최종 소셜 로그인 흐름
    1. 사용자가 소셜 로그인(Google, Kakao, Naver) 요청
    2. Spring Security의 OAuth2LoginConfigurer가 요청을 처리
    3. CustomOAuth2UserService가 provider를 식별하고 사용자 정보를 파싱
    4. 기존 사용자 여부 확인 → 없으면 자동 회원가입 진행
    5. 사용자 정보를 담은 CustomOAuth2User ****생성 후 반환
    6. OAuth2AuthenticationSuccessHandler가 JWT access/refresh token 생성
    7. 클라이언트에 JSON 형태로 토큰 전달
  - 소셜 로그인과 일반 로그인을 동일한 인증 체계 안에서 관리할 수 있게 되었고, 새로운 provider 추가 시에도 구조 확장이 용이하도록 설계됨

    ---

  ### [회고]

  **✅ 잘된 점**

  - Spring Security의 OAuth2 확장 기능을 활용하여 구조적인 통합이 가능했고, 기존 인증 체계와 충돌 없이 적용됨
  - provider별 사용자 정보 구조 차이를 인터페이스 기반으로 추상화하여 유지보수성과 확장성 확보
  - JWT 발급 방식과 인증 흐름을 기존 일반 로그인과 통일하여 인증 일관성 확보
  - 소셜 로그인 최초 시도 시 자동 회원가입 처리로 사용자 UX 향상

  **❗ 향후 개선 포인트**

  - Slack 등 알림 기능과 연계된 로그인 성공/실패 로깅
  - 애플 로그인, 깃허브 로그인 등 신규 provider 확장 시 사용자 정보 필드 구조에 따른 예외 처리 로직 추가
  - OAuth2 로그인 거절/중단 흐름 대응

</details>

<details>
  <summary> 🧠 CI/CD 도입 및 배포 전략 결정: PortForU </summary>

### [배경]

- PortForU 서비스는 Spring Boot 기반 애플리케이션으로, 안정적인 배포를 위해 AWS 기반의 CI/CD 파이프라인이 필요했음
- GitHub Actions를 활용하여 코드 변경 시 자동으로 빌드하고, Docker 이미지 생성 및 ECR 업로드, Elastic Beanstalk를 통한 배포를 자동화하는 구조를 구현

---

### [고려사항]

- **버전 관리 브랜치 전략**: `dev` 브랜치에 푸시될 때만 CI/CD 트리거
- **JAR 빌드 시간 및 비용 최적화**: Gradle 캐시 적용
- **AWS 연동성**: ECR, Beanstalk 등 AWS 서비스들과의 통합 필요
- **배포 대상**: AWS Elastic Beanstalk (Docker 기반 환경)
- **이미지 저장소**: Amazon ECR
- **보안**: AWS 자격증명은 GitHub Secrets를 통해 안전하게 관리

---

### [결정]

💡 **CI/CD 도구로 GitHub Actions 선택**

- 깃허브와 완전 통합: GitHub repository 내에 workflow 파일을 관리함으로써 설정과 코드 변경을 함께 버전 관리할 수 있음
- 서버 필요 없음: 별도 구축형 CI 서버(Jenkins 등) 없이 사용 가능
- 보안 관리: `GitHub Secrets`를 통해 자격 증명 및 환경변수 안전하게 저장 가능

💡 **배포 및 컨테이너 전략**

- Dockerfile 기반 빌드: 경량 Alpine 기반 openjdk:17-jdk-alpine 사용
- Docker image → Amazon ECR에 푸시
- Dockerrun.aws.json 생성 후 Elastic Beanstalk에 배포
- 배포는 `beanstalk-deploy` 액션 사용: 수동 CLI 배포보다 GitHub Actions와의 통합성이 뛰어남

---

### [결론]

- 최종 CI/CD 흐름
  1. `dev` 브랜치에 push 발생 시 워크플로우 트리거
  2. Java 17 환경에서 Gradle로 JAR 빌드
  3. Dockerfile로 이미지 빌드 → Amazon ECR에 푸시
  4. Dockerrun.aws.json 생성 후 ZIP 압축
  5. Elastic Beanstalk 환에 배포 완료
- 수동 배포를 제거하고, 일관된 환경에서 자동화된 안정적인 배포 파이프라인을 구축할 수 있었음

---

### [회고]

**✅ 잘된 점**

- GitHub Actions를 도입함으로써 외부 CI 도구 없이도 단일 환경에서 빌드-테스트-배포까지 처리
- **Elastic Beanstalk + ECR**을 통해 컨테이너 기반의 배포 구조 간단히 구성 가능
- Gradle 캐싱, SHA 기반 이미지 태깅 등 CI/CD 최적화 전략이 반영됨

**❗ 향후 개선 포인트**

- 현재는 테스트(`x test`)가 제외되어 있으므로, 빌드 안정성 검증을 위해 단위 테스트 및 통합 테스트 단계의 도입 필요
- Blue/Green 배포 전략 미적용 → 배포 중 장애 발생 시 롤백 불가능
- `Dockerrun.aws.json` 기반 EB 배포는 단일 컨테이너에 한정됨, 추후 ECS, EKS 고려 가능
- 슬랙/이메일 등의 배포 결과 알림 기능 미구현

</details>

<details>
  <summary> 🧠 CI/CD 도입 및 배포 전략 결정: PortForU-Crawler </summary>

### [배경]

- PortForU-Crawler는 Headless Chrome 및 Chromedriver 환경이 필수적이며, EC2 환경에서 주기적으로 실행되어야
- 배포의 안정성과 효율성을 높이기 위해, GitHub Actions와 AWS(ECR + EC2 via Bastion)를 활용한 자동화된 CI/CD 파이프라인 구축 필요

---

### [고려사항]

- Headless Chrome + Chromedriver 실행 환경을 포함한 Docker 이미지 구성 필요
- build.gradle로 생성된 JAR 파일을 이미지에 포함
- ECR에 이미지 저장 후 EC2 서버에 pull & run
- EC2는 외부 접근이 제한된 private subnet에 존재 → Bastion Host를 통해 접속 필요
- `.env` 파일 기반 환경변수 주입 필요

---

### [결정]

💡 **CI/CD 도구: GitHub Actions**

- GitHub repository와 자연스럽게 통합되며, 추가 구축 없이 Actions만으로 파이프라이 구성 가능
- 다양한 community 액션을 통해 Gradle 빌드, ECR 로그인, EC2 배포 자동화까지 지원

💡 **Docker 이미지 구성**

- 멀티 스테이지 Dockerfile로 구성
  - `builder` 단계에서 `chromedriver`만 다운로드
  - `runtime` 단계에서는 headless Chrome, system 라이브러리, Chromedriver, 그리고 JAR 파일 포함
- ENTRYPOINT에 headless Chrome 실행 옵션 포함 → 크롤러 환경에 최적화됨

💡 **배포 전략**

- `dev` 브랜치에 push → workflow 트리거
- Gradle 빌드 후 JAR 생성
- Docker 이미지 빌드 → Amazon ECR에 push
- `.env` 파일 생성 후 Bastion Host를 통해 EC2에 복사
- ECR 이미지 pull → 기존 컨테이너 제거 → 새 이미지로 재실행

---

### [결론]

- 최종 CI/CD 흐름
  1. `dev` 브랜치 push 시 자동 트리거
  2. JDK 17 + Gradle 환경에서 JAR 빌드 수행
  3. Headless 크롬 환경을 포함한 Docker 이미지 빌드
  4. ECR에 푸시 후, `.env` 파일을 EC2에 전달
  5. Bastion을 통한 SSH로 EC2 접속 → 기존 컨테이너 중지 및 제거
  6. ECR에서 새 이미지 pull 후 실행
- 자동화된 배포가 가능해졌고, 크롤링 서비스 특성상 필요한 Chrome/Chromedriver 의존성도 안정적으로 포함됨

---

### [회고]

**✅ 잘된 점**

- 멀티스테이지 Dockerfile로 이미지 크기를 줄이고 Chrome 실행 환경을 안정적으로 구성
- GitHub Actions와 AWS ECR, EC2 Bastion 연결을 통해 완전 자동화된 배포 성공
- `.env` 파일 기반 설정 → 민감 정보는 GitHub Secrets로 안전하게 관리
- 크롤러 특성에 맞게 `-headless --no-sandbox` 등의 옵션을 ENTRYPOINT에 포함해 안정성 확보

**❗ 향후 개선 포인트**

- Blue/Green 배포 또는 무중단 배포 구조는 아직 없음 → 이후 ECS나 CodeDeploy 활용 검토 필요
- 현재는 단일 환경(dev) 배포만 자동화 → prod 브랜치 구분 및 다중 환경 지원 확장 필요
- 슬랙/이메일 등의 배포 결과 알림 기능 미구현

</details>

<details>
  <summary> 🧠 각 비동기 처리를 요하는 서비스 기능에 필요한 메시지 브로커 정하기 : RabbitMQ </summary>

## 배경/고려사항

이벤트:

- 포트폴리오에 댓글이 달릴 경우 이메일을 통한 알림을 보낼 수 있어야 한다.
- 채용공고가 업데이트 되었을 경우 멤버십 회원에게 업데이트 알림을 이메일로 보낼 수 있어야 한다.
- 채용공고를 스크랩한 회원에게 채용공고 마감 직전에 대한 알림을 이메일로 보낼 수 있어야 한다.

개발 고려사항:

- 이메일 수신기능은 필요하지 않음
- 알림 기능을 계속해서 확장하는데 어려움이 없어야함
- 메일링 시스템을 개발하는 데 있어 유지비용 및 설정 난이도 등이 낮아야 함

## 결정 - 메시지 브로커 

  - 크롤링 완료 후 채용공고가 업데이트 되었을 때, 포트폴리오 게시글에 댓글이 달렸을 때, 채용공고 마감일 검사에 대한 스케쥴러 작동 후에 이메일 발송을 처리하는 메시지 큐를 구현해야 합니다.

### 선택지

**RabbitMQ**

- 장점:
  - 이메일, 알림 등 **즉시 전송이 필요한 트랜잭션성 이벤트**에 적합
  - 소비 실패 시 **재시도, 지연 큐, 데드 레터 큐 등 신뢰성 있는 재처리** 가능
  - 큐별로 라우팅, 필터링 구조 유연하게 설계 가능
  - 알림 수신 여부(ack) 기반 처리로 **사용자당 1회 전송 보장**하기 쉬움
- 단점:
  - 메시지 처리량이 많아질수록 성능 튜닝 필요
  - 스케일 아웃이 상대적으로 복잡함
  - 운영 중 큐 상태 모니터링과 복구 시나리오를 신경 써야 함

**Kafka**

- 장점:
  - 이벤트 발생 이력(로그)을 **장기간 저장 가능** → 재처리, 분석 가능
  - 수많은 알림 이벤트를 **동시에 처리**할 수 있는 **확장성과 속도** 보장
  - **다수의 소비자 그룹이 독립적으로 소비** 가능 (예: 이메일 알림, 슬랙 알림 분리 처리)
- 단점:
  - 메시지 자체에 대한 "한 번만 처리 보장"이 어려움 → **이메일 중복 전송 방지 처리가 직접 필요**
  - 메시지를 실시간 push보다는 polling으로 읽어야 해서 **즉시성 낮을 수 있음**
  - 기본적으로 **스트리밍 시스템**이라 구축/운영 복잡

**Redis Pub/Sub**

- 장점:
  - **즉각적인 반응속도** (채팅이나 알림에 적합)
  - 설정이 매우 간단하고 서버 리소스 거의 소모하지 않음
  - Redis를 이미 사용 중이라면 추가 구성 없이 활용 가능
- 단점:
  - **보낸 시점에 구독자가 없으면 메시지 소실됨** → 이메일 알림 같은 보장성이 필요한 시스템에 부적합
  - 메시지 저장, 재시도 불가 → 실패 시 복구 어려움
  - 확장성, 복잡한 큐 처리 로직이 사실상 불가능

## 결론

### RabbitMQ

- 채용공고 마감, 게시글 댓글 등의 알림은 **사용자마다 1회만 정확히 발송**되어야 하므로, 메시지 손실 없이 처리 가능한 구조가 필요했습니다.
- 설정과 운영 복잡도가 낮고, 메시지 처리 순서 및 라우팅에 유연성이 높아, **비교적 단순하고 예측 가능한 알림 이벤트 처리**에 적합하다고 생각했습니다.

### 회고

- 카프카나 레디스와는 달리 웹페이지로 큐를 보고 관리할 수 있어 좋았지만 
- 채용공고 마감 알림에 등에 있어서는 단말성의 마감알림 형식이 사용자에게 있어서는 스크랩 시점에 따라 알림의 혜택을 보지 못하는 경우의 수가 발생하는 것을 발견했습니다.
- 이를 개선하기 위해 Kafka로 전환할 경우(사용자 수가 많아진다는 전제 하), 마감 대상 공고를 크롤링 시점에 이벤트로 발행하고, 메인 서비스 측에서 별도의 스케줄러를 통해 시간 조건을 평가한 뒤 실제 발송 시점에 맞춰 소비하는 구조로 재구성할 수 있으며, 이를 통해 사용자 행동과 무관한 안정적인 알림 전달하는 방식 또한 있다는 것을 알게 되었습니다.

</details>

<details>
  <summary> 🧠 Google SMTP를 사용한 메일링 서비스 구현 </summary>

## 배경/고려사항

### 이벤트:

- 포트폴리오에 댓글이 달릴 경우 이메일을 통한 알림을 보낼 수 있어야 한다.
- 채용공고가 업데이트 되었을 경우 멤버십 회원에게 업데이트 알림을 이메일로 보낼 수 있어야 한다.
- 채용공고를 스크랩한 회원에게 채용공고 마감 직전에 대한 알림을 이메일로 보낼 수 있어야 한다.

개발 고려사항:

- 이메일 수신기능은 필요하지 않음
- 알림 기능을 계속해서 확장하는데 어려움이 없어야함
- 메일링 시스템을 개발하는 데 있어 유지비용 및 설정 난이도 등이 낮아야 함

## 결정

### 선택지 : 

**Google SMTP**

- 장점:
  - Gmail 계정만 있으면 바로 사용 가능
  - 설정이 비교적 간단하며 테스트 용도로 적합
  - 무료로 소규모 전송에 적당함
- 단점:
  - 반드시 2단계 인증 + 앱 비밀번호 필요
  - 일일 발송량 제한이 큼 (약 100~500건 수준, 계정에 따라 다름)
  - 퍼블릭 이메일 도메인이라 수신 메일함에서 스팸 처리될 위험이 상대적으로 높음
  - 기업용 발신자로 사용하기엔 신뢰도, 확장성 부족

**AWS SES**

- 장점:
  - 매우 저렴한 가격 (대량 발송 시 가장 가성비 좋음)
  - AWS 인프라와의 통합에 유리 (예: Lambda, S3, SNS 등)
  - SMTP와 API 모두 지원하며 안정성 높음
  - 높은 신뢰도 (메일 수신처에서 스팸 분류 확률 낮음)
- 단점:
  - 처음은 샌드박스 모드로 제한됨 → 생산 전환 신청 필요
  - 도메인 인증, IAM 권한 설정 등 초기 설정이 복잡할 수 있음
  - UI가 불친절하고 학습 곡선이 있음

**기타 외부 서비스( Postmark, Mailgun, SendGrid)**

- 장점:
  - 대부분 SMTP와 REST API 모두 지원
  - 발송, 통계, 실패 리포트 등 기능 풍부
  - Postmark: 트랜잭션 메일 속도와 신뢰도 최고 수준
  - SendGrid: 마케팅 + 트랜잭션 모두 가능, 확장성 높음
  - Mailgun: 개발자 친화적이며 가성비 좋음
- 단점:
  - 무료 요금제가 제한적이거나 1회성 체험 위주(비쌈)
  - 도메인 인증 필수인 경우가 많고, 일부 서비스는 퍼블릭 이메일 도메인 사용 불가
  - 마케팅 기능은 보통 유료 플랜에서만 온전히 제공됨

## 결론

### Google SMTP

- Postmark, SES 등은 도메인 인증과 프로덕션 전환 과정이 필요해 초기 적용 시점에서는 부담이 있었습니다.
- **개발 초기 단계의 테스트용 이메일 전송**이 주 목적이었기 때문에, 별도의 도메인이나 인증서 없이도 빠르게 적용 가능한 Google SMTP를 선택했습니다.
- Gmail 계정과 앱 비밀번호만으로 연동 가능하며, Spring Mail과의 통합이 쉬워 **개발/검증을 빠르게 진행할 수 있었습니다.**
- 수신자가 소수이고, 대량 발송이 아니기 때문에 Gmail SMTP의 **일일 발송량 제한과 기본 인증 조건도 수용 가능한 수준**이었습니다.


## 회고

- 개발단계에서는 구글 SMTP등을 사용했지만 서비스가 완성도가 있어진다면 AWS의 SES로의 다시 전환할 필요가 있다고 느낍니다.
- 이 과정에서 도메인 이메일 등을 갖추어 신뢰성있는 메일 서비스를 구현해야함의 필요성을 느꼈습니다.

</details>


---

# ⚒️트러블 슈팅 및 성능 개선


<details>
  <summary> 🩹 멤버십 중복 결제 문제 </summary>

### 1.문제 현상

- 하나의 멤버십 상품에 대해 동시에 여러 명이 결제 요청을 보내는 상황에서,
- DB에는 `COMPLETED` 상태의 결제 정보가 **2건 이상 저장**되는 문제 발생

### 2.원인 분석

- Redis 락을 사용했지만, **락 설정 TTL 누락** + **재진입 락 설정 오류**로 인해
- 동시에 여러 요청이 락을 통과하거나, 락 소유자가 중복 처리하게 되는 경우 발생

### 3.해결 과정

- Redisson 락 설정 시 `.tryLock(5, 3, TimeUnit.SECONDS)` 형태로
  - **최대 대기 시간 5초**, **락 유지 시간 3초**로 명시
- 동시에 **멤버십 정원 검사**도 락 내부에서 진행되도록 코드 이동
- 멀티스레드 환경에서 동작 검증을 위한 `@RepeatedTest` 기반 테스트 작성

### 4.개선 결과

- 동시 요청 10,000건 중 `COMPLETED` 결제는 1건으로 제한
- 멤버십 정원도 정확히 1명만 줄어들어 **정합성 문제 해결**
- 테스트 실행 시간 12.3s → 6.1s 로 개선

</details>

<details>
  <summary> 🩹 Prometheus 응답 포맷 오류 </summary>

### 1.문제 현상

- Prometheus가 `/actuator/prometheus` 엔드포인트를 긁어오지 못하고
  `"unsupported Content-Type 'text/html'"` 에러 발생

### 2.원인 분석

- Spring Boot에서 actuator 엔드포인트 노출이 설정되지 않았거나,
- `/actuator/prometheus` 접근 시 보안 필터에 걸려 리다이렉트됨

### 3.해결 과정

- `application.yml`에 아래 설정 추가

```yaml
management:
  endpoints:
    web:
      exposure:
        include: prometheus
  endpoint:
    prometheus:
      enabled: true
```

- `management.server.port: 8080`으로 주 서버와 통합
- Prometheus `scrape_configs`도 `localhost:8080`으로 수정

### 4.개선 결과

- Prometheus와 Grafana 연결 성공, 실시간 JVM/CPU/요청 수 모니터링 가능
- 서버 상태 확인 → 이슈 발생 시 즉시 인지 가능한 환경 구축 완료

</details>

<details>
  <summary> 🩹 Redis 동시성 제어 테스트시 Toss pg연동 결제 승인 응답 누락 </summary>

### 1.문제 상황

- Redis 기반 분산 락을 이용해 동시 결제 요청 중복을 제어하는 테스트 과정에서, 일부 요청에서 **Toss 결제 승인 응답이 누락**되는 현상이 발생함.
- 테스트 환경에서는 Toss API를 직접 호출하지 않고 **Mock 처리**를 사용 중이었음에도, 특정 요청이 `PENDING` 상태로 남거나 응답 자체가 들어오지 않음.

### 2.원인 분석

- Redis 락 획득 후 Toss 승인 요청을 보내는 시점에 **다른 스레드가 락 획득을 기다리며 병렬로 요청**을 시도.
- Mock 서버 또는 컨트롤러가 **비동기 처리 중 일부 요청을 정상적으로 응답하지 못함**.
- 또한, 테스트 시 응답 시간/순서가 다소 불규칙하게 발생하며, **`@Transactional` 처리 누락 또는 DB 반영 지연**이 함께 관찰됨.

### 3.조치 내용

1. **락 획득 후 승인 요청 & 상태 반영 순서를 명확히 고정**:
  - 락 획득 → Toss 승인 요청 → DB 상태 저장 → 락 해제

    로 흐름을 고정하고 중간 실패 시 바로 `FAIL` 처리.

2. **Mock 응답 처리 로직 단순화 및 Logging 보강**:
  - 결제 성공/실패 응답을 명확하게 구분하고, `subscribeId`, 상태를 로그로 남김.
3. **동시성 테스트에 `CountDownLatch` 및 `@Transactional` 보완**:
  - 테스트 중 트랜잭션 커밋 누락 방지를 위해 명시적 커밋 확인 및 롤백 방지 설정 적용.

### 4.개선 결과

- 총 10,000건의 동시 요청 중 **결제 승인 실패/누락 현상 없음**.
- 모든 요청에서 **단 1건만 `COMPLETED`, 나머지 `FAIL` 혹은 `PENDING` → `FAIL` 처리됨**.
- `subscribeId` 기준으로 **정확한 상태 추적 가능**했고, 멤버십 정원 데이터도 정확히 1 감소.

### 5.회고

- 동시성 테스트는 단순히 락이 걸리는지 보는 것뿐만 아니라, **락 이후 흐름까지 명확히 설계되어야 함**을 체감.
- PG 모의 응답(mock)이 있어도, **정확한 흐름 통제와 테스트 환경의 비동기 응답 처리 방식까지 신경 써야 신뢰도 있는 테스트 가능**.
- 추후에는 **Toss Webhook 연동 방식으로 전환**하거나, **Kafka 기반 이벤트 처리 구조로 분리**하는 것도 하나의 방법이라 생각됨.

</details>

<details>
  <summary> 🩹 Toss PG 연동 </summary>

### 1.문제 상황

- 초기에 PG 연동에 대한 지식이 거의 없는 상태에서 결제 기능을 구현하려고 했음
- 우리는 백엔드 학습반이기 때문에,
  **가능한 모든 처리를 백엔드(Spring) 단에서 해결하는 방향**으로 접근했음
- Toss의 **결제 요청 API를 직접 호출해 결제 링크를 생성하는 방식**을 시도했지만, 공식 문서에서는 해당 API에 대한 명확한 정보가 부족했고, 테스트 요청이 반복해서 실패함
- 결국 Toss Payments의 공식 Discord에 문의했는데, 다음과 같은 답변을 받음:

  > “결제링크 API 라면 링크페이를 말씀하시는 걸까요? 
  > 해당 API는 계약하신 가맹점에만 제공되고 있습니다.

- 이 답변을 통해 해당 API는 **인증된 가맹점에서만 사용 가능하며**,
  **테스트 환경에서는 호출이 거부**된다는 사실을 알게 되었음
- 이로 인해, **백엔드 단독으로 결제 요청을 처리하는 구조는 현실적으로 적용 불가능**하다는
  결론에 도달함

### 2.구조적 해결: SDK → 리다이렉트 방식으로 전환

- Toss에서는 **React 기반 SDK**를 제공하고 있어서, 처음에는 해당 방식을 활용해 결제를 처리해보려고 했음
- 실제로 적용을 시도했으나, **SDK 사용을 위해서는 별도의 프론트엔드 환경 구축(React 등)이
  필요**했고,이는 백엔드 중심으로 구성된 우리 프로젝트 구조와 맞지 않았음
- 그래서 어떻게 해야 할지 고민하던 중, **Thymeleaf 기반 SSR 템플릿 방식**으로 처리할 수 있다는 점을 착안했고, 해당 방식이 우리 구조와 맞는다고 판단해 방향을 전환함
- 최종적으로는 Toss 결제 페이지로 **사용자를 리다이렉트**하고,결제 완료 시
  **`success-url` / `fail-url`로 서버에서 직접 결제 결과를 검증**하는 구조로 변경함

### 3.결제 상태 Enum 확장 (도메인 개선)

- 초기에는 결제 상태가 `PENDING`, `COMPLETED`, `FAILED` 세 가지뿐이었지만,
- 실제 테스트 및 운영 중 다음과 같은 **복잡한 결제 흐름**이 발생했음:
  - 사용자가 결제 중 브라우저를 닫은 경우 → `EXPIRED`
  - 결제 도중 사용자가 직접 취소한 경우 → `CANCELLED`
- 이 상황들을 제대로 반영하기 위해 `PaymentStatus` enum을 확장하고,
  **결제 상태 전이 흐름을 명확히 정의**함으로써 도메인 정합성을 강화함
- 확장된 상태값 덕분에 결제 로그 분석이 명확해졌고,
  **정원 복구, 유효성 검사 등 여러 로직에서도 상태 기반으로 유연하게 분기 처리**할 수 있게 되었음

### 4.결제 수단 분기 처리를 위한 PaymentMethod Enum 확장

- 초기에는 `CARD`, `VIRTUAL_ACCOUNT`, `ACCOUNT_TRANSFER`, `MOBILE_PHONE`, `TOSS_PAY` 등 **기본적인 5개 결제 수단**만 정의되어 있었음
- 하지만 실제 PG 연동 시, Toss는 `method = "간편결제"`, `provider = "삼성페이"`, `"네이버페이"` 등의 **서브 타입 정보를 따로 분리해서 제공**함
- 단순히 `method`만 기준으로 분기하면 **삼성페이로 결제했음에도 TOSS_PAY로 분류되는 문제** 발생
- 이를 해결하기 위해 `PaymentMethod.fromTossMethod(String method, String provider)` 메서드를 정의하고,

  **`provider` 값까지 함께 판단하여 `SAMSUNG_PAY`, `NAVER_PAY`, `LPAY` 등 세부 수단까지 정확히 매핑**되도록 enum 구조를 확장함


### 5.개선 효과

- **결제 연동 흐름의 현실적인 설계 전환**

  초기에 백엔드에서 Toss 결제 요청 API를 직접 호출하려 했으나,

  가맹점 인증된 환경에서만 사용 가능하다는 제약으로 중단하고,

  → Toss 결제 페이지로 리다이렉트하는 구조로 전환하여 **실제 연동 가능성 확보**

- **프론트 SDK 대신 SSR 환경에 적합한 방식 도입**

  Toss가 공식적으로 제공하는 React SDK는 우리 프로젝트(Thymeleaf 기반)와 호환되지 않았음.

  → 별도 프론트 구축 없이도 적용 가능한 **SSR 환경 기반 리다이렉트 방식 채택**으로 **구현 난이도 완화**

- **결제 상태 흐름 개선 및 enum 확장**

  초기에는 `PENDING`, `COMPLETED`, `FAILED`만 존재했지만,

  운영 중 브라우저 종료나 사용자의 취소 등 다양한 케이스에 대응하기 위해

  → `CANCELLED`, `EXPIRED` 상태를 추가하고 **도메인 정합성 강화**

  → 결제 상태 기반의 분기 처리 및 정원 복구 처리 **더 유연하고 명확해짐**

- **결제 수단 식별 정확도 향상**

  Toss에서 `method = 간편결제`, `provider = 삼성페이`처럼 애매한 정보가 들어오는 경우,

  → `PaymentMethod.fromTossMethod()` 로직을 통해 **SAMSUNG_PAY, NAVER_PAY 등으로 세분화 분기 처리**

  → UI 출력, 관리자 통계 등에 **정확한 결제 수단 표시 가능**

</details>

<details>
  <summary> 🩹 Redis TTL  기반 결제 만료 처리 </summary>

### 1.문제 상황

PG 연동 구조상, 사용자가 결제 페이지로 이동한 후 일정 시간 내 결제를 완료하지 않으면
해당 요청은 만료 처리돼야 함.
하지만 **서버가 재시작되거나 장애가 발생한 경우**, 만료 처리를 위한 타이머 로직이 동작하지 않아 일부 결제가 영구히 `PENDING` 상태로 남는 문제가 발생함.

### 2.원인 분석

기존에는 `ScheduledExecutorService`를 활용해 결제 요청 시점에 서버 내에서 타이머를 등록했지만,
이 방식은 **서버가 내려가면 예약 작업이 함께 사라지는 구조**였기 때문에,
서버 복구 이후에도 **만료 처리가 누락된 결제 요청이 계속 남는 구조적 문제가 있었음.**

### 3.해결 방법

- **Redis의 TTL 기능**을 활용해 결제 만료 타이머를 외부 저장소에 저장하도록 구조 변경
- 서버 재시작 시, Redis에 남아 있는 TTL 기반으로 만료 스케줄을 재등록
- TTL이 만료되어 Redis 키가 삭제된 경우를 대비해,
  **DB의 결제 상태를 기반으로 누락된 만료 처리를 보완하는 로직도 추가**

### 4.개선 효과

- 서버 장애 및 재시작 상황에서도 **결제 만료 처리가 안정적으로 유지**
- 구독 상태와 결제 상태 간의 불일치 문제가 사라져 도메인 정합성 향상
- Executor 기반 단발성 예약 로직에서 Redis 기반 분산 TTL 처리 방식으로 전환하며
  **신뢰성과 확장성 모두 개선**

</details>

<details>
  <summary> 🩹 크롤링 시 ubuntu 환경에서의 chormedrive오류 </summary>

### ❗ 문제 상황

저희는 AWS EC2 Ubuntu 인스턴스에서 **Spring Boot 기반의 Kotlin 크롤링 서비스**를 운영하고 있습니다.

이 서비스는 `Selenium + ChromeDriver`를 통해 웹 페이지를 크롤링하는데,  chorme을 실행할 수 없는

에러가 발견되었습니다.

### 🔍 원인 분석

EC2의 Ubuntu 서버는 기본적으로 **GUI(그래픽 환경, X Window System)**가 설치되어 있지 않습니다.

Selenium은 `--headless` 모드에서도 **내부적으로 디스플레이 자원**을 사용하려고 하기 때문에,

**디스플레이 컨텍스트가 없는 환경**에서는 ChromeDriver 실행에 실패하게 됩니다.

### ✅ 해결 방법: xvfb로 가상 디스플레이 환경 구성

이 문제를 해결하기 위해 `xvfb`(X virtual framebuffer)를 사용했습니다.

`xvfb`는 눈에 보이지 않는 가상의 디스플레이를 생성해주는 도구로,

GUI 환경이 없는 서버에서도 마치 화면이 존재하는 것처럼 만들어줍니다.

## 📦 Dockerfile 설정 (크롤러 서비스용)

Dockerfile 내에서 필요한 라이브러리들과 함께 `xvfb`를 설치합니다.

```docker
RUN apt-get install -y --no-install-recommends \
      google-chrome-stable \
      xvfb xauth \
      libglib2.0-0 libnss3 libx11-xcb1 libxcomposite1 libxcursor1 \
      libxdamage1 libxrandr2 libatk1.0-0 libatk-bridge2.0-0 \
      libgtk-3-0 libgbm1 libasound2 libcups2 libdrm2 \
 && rm -rf /var/lib/apt/lists/*
```

## 🧭 실행 흐름 요약

1. 크롤러 서비스는 Docker 컨테이너 안에서 실행됨
2. 컨테이너 내부에 설치된 xvfb 및 관련 GUI 라이브러리들이 환경을 준비
3. Kotlin + Spring Boot 애플리케이션에서 headless Chrome을 실행
4. Selenium이 GUI 없이도 ChromeDriver를 통해 웹페이지를 크롤링
5. 크롤링된 결과는 RDS에 저장되거나 API 응답으로 전달됨

## ✅ 최종 결론

- AWS EC2 Ubuntu 인스턴스는 GUI 환경이 없기 때문에
  **headless Chrome 실행 시 문제가 발생**할 수 있습니다.
- `xvfb`는 실제 디스플레이가 없는 환경에서도 **가상의 X 서버를 제공하여** 이 문제를 해결합니다.
- 저희는 `xvfb`와 관련된 라이브러리들을 Docker 이미지에 포함시켜,
  **실행 중 DISPLAY를 수동 설정하지 않아도 안정적인 실행 환경**을 구성했습니다.
- 그 결과, EC2 인스턴스에서도 **Selenium + ChromeDriver 기반의 크롤링 서비스가 에러 없이
  안정적으로 운영**되고 있습니다.

🔗 References

- [공식 xvfb 문서](https://www.x.org/archive/X11R7.6/doc/man/man1/Xvfb.1.xhtml)
- [Selenium headless chrome with xvfb on Ubuntu](https://stackoverflow.com/questions/50642308)

</details>

<details>
  <summary> 🩹 크롤러 저장 데이터가 ES 인덱스에 반영되지 않는 문제 </summary>

### [문제 상황 및 원인 분석]

- 크롤링으로 저장된 JobPosting이 Elasticsearch에 자동 반영되지 않음

✔️ **서비스 구조 요약**

| 항목 | 내용                                              |
| --- |-------------------------------------------------|
| 🗂️ 프로젝트 1 | Kotlin 기반 크롤러 → 크롤링된 JobPosting 데이터를 DB에 저장     |
| 🗂️ 프로젝트 2 | Spring Boot 기반 메인 서비스 → 검색 기능 및 Elasticsearch 연동 |
| 🗃 DB | 공용 DB 사용 (job_postings 테이블 공유)                  |
| 🔍 Elasticsearch 적용 | 메인 서비스에만 연동되어 있음`JobPostingSearchService` 내 색인/검색 처리 |
| ❓ 문제 | 크롤러가 ES에 직접 저장하지 않기 때문에 ES 색인 누락 발생             |

---

### [해결 방안]

1. **메인 앱에서 일정 주기로 DB → ES 재인덱싱**
  - 메인 앱이 일정 간격으로 DB에서 `JobPosting`을 조회 후 Elasticsearch에 동기화
  - 크롤러는 수정 없이 유지 가능
  - 단점: 실시간 반영은 어려움
2. **크롤러에서 메인 앱에 API 요청 (Push 방식)**
  - 크롤링이 끝난 후, 메인 서비스의 `/es-sync` 등의 엔드포인트에 POST 요청
  - 실시간 색인 가능하지만, 네트워크 연결 및 API 스펙 관리 필요
3. **크롤러에도 Elasticsearch 연동 (Shared Module 방식)**
  - 크롤러와 메인 서비스가 `JobPostingDocument` 및 검색 서비스를 공용 모듈로 공유
  - 양쪽에서 동일한 방식으로 색인 가능
  - 단점: 모듈 복잡도 증가, 종속성 관리 필요

---

### [해결 방법]

✅ **색인 자동화 스케줄러 구현**

- `JobPosting` 엔티티에 `indexed: Boolean` 컬럼을 추가하여 색인 상태를 관리
- Spring의 `@Scheduled` 어노테이션을 활용해 주기적으로 색인되지 않은 데이터 조회 및 Elasticsearch 색인 실행
- 동시 실행 방지를 위해 `AtomicBoolean` 플래그를 도입하여 스케줄러 중복 실행 방지 처리
- 크롤러는 기존대로 DB에 저장만 수행하며, 색인 동기화는 메인 서비스가 책임지는 구조

---

### [결론]

- 크롤링된 JobPosting 데이터가 Elasticsearch에 반영되지 않던 문제는 메인 서비스 내 스케줄러 기반의 재색인 방식으로 해결
  - 크롤러 수정 없이도 Elasticsearch 색인 누락 문제 해결
  - 메인 서비스가 색인 책임을 갖되, 데이터 일관성과 상태 관리(`indexed`)가 가능
  - 동시성 안전성과 실행 주기 조절이 쉬운 구조를 확보
- 실시간 반영은 어렵지만, 안정성과 구현 효율성을 고려할 때 가장 적합한 방식이라 판단

</details>

<details>
  <summary> 🩹 Hibernate Lazy 로딩 오류: Kotlin getter final 문제 </summary>

### [문제 상황 및 원인 분석]

- Spring Boot + Kotlin + Hibernate 프로젝트 실행 시 다음과 같은 오류가 발생

    ```jsx
    org.hibernate.HibernateException: Getter methods of lazy classes cannot be final
    ```

- 이는 Hibernate가 엔티티에 대한 Lazy 프록시 객체를 생성할 수 없을 때 발생하며, 구체적으로는 다음과 같은 조건에서 문제 발생
  - Kotlin은 기본적으로 모든 클래스, 메서드, 프로퍼티가 `final`
  - Hibernate는 Lazy 로딩 프록시 생성을 위해 엔티티 클래스와 그 getter 메서드가 `open`(=non-final) 상태여야 함
  - 그러나 BaseEntity는 @MappedSuperclass로 선언되어 있고, getter 메서드는 `final` 상태였음
  - build.gradle.kts에 allOpen 및 noArg 플러그인이 설정되어 있었으나, 해당 플러그인의 기본 적용 대상은 @Entity에 한정되어 있어 @MappedSuperclass에는 적용되지 않았음

---

### [해결 방안]

1. MappedSuperclass에 `open` 처리 강제 적용

- Gradle allOpen, noArg 플러그인에 jakarta.persistence.MappedSuperclass 어노테이션을 명시적으로 추가
- 이렇게 하면 Kotlin 컴파일러가 @MappedSuperclass가 붙은 클래스도 자동으로 `open` 처리함

2. BaseEntity 클래스 내부의 프로퍼티 및 메서드에 명시적으로 `open` 키워드 추가

- 컴파일 플러그인이 적용되지 않거나 미묘하게 동작하지 않는 상황까지 방지
- Hibernate의 Lazy 로딩 대상이 될 가능성이 있는 필드는 `open`이어야 프록시 생성 가능

---

### [해결 방법]

**build.gradle.kts** 수정

```jsx
allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass") // 추가
}
noArg {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass") // 추가
}

```

---

### [결론]

- Kotlin과 Hibernate를 함께 사용할 때는 클래스 및 메서드가 기본적으로 `final`이라는 Kotlin의 특성과, Hibernate의 Lazy 프록시 생성 요구사항(`open`)이 충돌할 수 있음
- 이는 allOpen/noArg 플러그인을 활용하여 해결할 수 있으며, @MappedSuperclass도 명시적으로 처리 대상에 포함시켜야 함을 유의해야 함

</details>


---------------------------


# ❤️팀원 소개


<table>
  <thead>
    <tr>
      <th align="left">이름</th>
      <th align="left">역할 및 담당 업무</th>
      <th align="left">링크</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>
        <img src="https://github.com/user-attachments/assets/53a379b8-0295-4390-8548-3512cc4e8f2d" width="80"><br>  
        김한이 (팀 리더)
      </td>
      <td>
        🍗 Portfolio & Comment CRUD<br>
        🍗 채용공고 크롤링<br>
        🍗 AWS 인프라 구축
      </td>
      <td>
        <a href="https://github.com/kim-hani">
          <img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white">
        </a>
        <a href="https://velog.io/@h_ani99/posts">
          <img src="https://img.shields.io/badge/velog-20C997?style=for-the-badge&logo=velog&logoColor=white">
        </a>
      </td>
    </tr>
    <tr>
      <td>
        <img src="https://github.com/user-attachments/assets/50c8999a-9873-4372-8dcc-b6d6d956d873" width="80"><br>
        김유란 (부 리더)
      </td>
      <td>
        💎 Jobposting & Scrap CRUD<br>
        💎 소셜 로그인 구현<br>
        💎 CI/CD 구축<br>
        💎 AWS 인프라 구축<br>
        💎 Elasticsearch
      </td>
      <td>
        <a href="https://github.com/yoorkim">
          <img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white">
        </a>
        <a href="https://velog.io/@yyrkk/posts">
          <img src="https://img.shields.io/badge/velog-20C997?style=for-the-badge&logo=velog&logoColor=white">
        </a>
      </td>
    </tr>
    <tr>
      <td>
        <img src="https://github.com/user-attachments/assets/df3f600f-0bcb-4936-ba8c-1279f8cc2de9" width="80"><br>
        정청원 (팀원)
      </td>
      <td>
        ⚾️ Membership CRUD<br>
        ⚾️ 소셜 로그인 구현<br>
        ⚾️ AWS S3 연동<br>
        ⚾️ Elasticsearch<br>
        ⚾️ 프론트 작업
      </td>
      <td>
        <a href="https://github.com/chungwonJ">
          <img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white">
        </a>
        <a href="https://chungwonj.tistory.com">
          <img src="https://img.shields.io/badge/tistory-000000?style=for-the-badge&logo=tistory&logoColor=white">
        </a>
      </td>
    </tr>
    <tr>
      <td>
        <img src="https://github.com/user-attachments/assets/b43d361a-e376-4cd3-9c4b-e853f4c08af3" width="80"><br>
        민혜원 (팀원)
      </td>
      <td>
        👑 Auth & User CRUD<br>
        👑 전체 코드 리팩토링<br>
        👑 PG 연동<br>
        👑 시스템 모니터링 Prometheus+Grafana<br>
        👑 최적화<br>
      </td>
      <td>
        <a href="https://github.com/Heni0717">
          <img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white">
        </a>
        <a href="https://heni0717.tistory.com/">
          <img src="https://img.shields.io/badge/tistory-000000?style=for-the-badge&logo=tistory&logoColor=white">
        </a>
      </td>
    </tr>
    <tr>
      <td>
        <img src="https://github.com/user-attachments/assets/9093608b-19d1-4d21-b3d2-c132a5f8edce" width="80"><br>
        문정호 (팀원)
      </td>
      <td>
        📖 Subscribe & Payment CRUD<br>
        📖 PG 연동<br>
        📖 동시성 제어 및 redis ttl<br>
        📖 시스템 모니터링 Prometheus+Grafana
      </td>
      <td>
        <a href="https://github.com/ansdudn2">
          <img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white">
        </a>
        <a href="https://ansdudn2.tistory.com/">
          <img src="https://img.shields.io/badge/tistory-000000?style=for-the-badge&logo=tistory&logoColor=white">
        </a>
      </td>
    </tr>
    <tr>
      <td>
        <img src="https://github.com/user-attachments/assets/ad742c32-9491-4f7e-8d82-467fa6598197" width="80"><br>
        김지영 (팀원)
      </td>
      <td>
        ☕️ 채용공고 크롤링<br>
        ☕️ 비동기 처리 - RabbitMQ, SMTP
      </td>
      <td>
        <a href="https://github.com/flammaa">
          <img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white">
        </a>
        <a href="https://velog.io/@flamma/posts">
          <img src="https://img.shields.io/badge/velog-20C997?style=for-the-badge&logo=velog&logoColor=white">
        </a>
      </td>
    </tr>
  </tbody>
</table>

