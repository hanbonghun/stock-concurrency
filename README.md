# 재고 시스템으로 알아보는 동시성 제어
다양한 동시성 제어 방식을 학습하고 비교/분석하기 위한 프로젝트입니다.

## 학습 내용
재고 감소 로직을 통해 동시성 이슈를 재현하고, 다양한 방식으로 해결해보며 각 해결책의 장단점을 분석했습니다.

### 구현한 동시성 제어 방식
1. Application Level
   - synchronized를 활용한 단일 서버 동시성 제어
   - Optimistic Lock을 활용한 충돌 감지
   - Pessimistic Lock을 활용한 동시성 제어

2. Database Level
   - Named Lock을 활용한 동시성 제어

3. Distributed Lock
   - Redisson을 활용한 분산 락 구현
   - Lettuce를 활용한 분산 락 구현

## 기술 스택
- Spring Boot
- MySQL
- Redis
- Redisson
- Lettuce

각 방식의 특징과 결과를 분석해서 추가해보겠습니다:

## 테스트 결과 및 분석

### 1. Application Level

#### synchronized
- **장점**
  - 구현이 간단하고 직관적
  - 추가 인프라 요구사항 없음
- **단점**
  - 서버가 여러 대인 경우 동시성 제어 불가능
  - 락의 범위가 넓어 성능 저하 가능성 있음
- **적합한 사용 케이스**
  - 단일 서버 환경
  - 간단한 동시성 제어가 필요한 경우

#### Optimistic Lock
- **장점**
  - 별도의 락 획득이 필요 없어 성능상 이점
  - 충돌이 적은 상황에서 효율적
- **단점**
  - 충돌 시 재시도 로직 구현 필요 
  - 충돌이 빈번한 경우 성능 저하
- **적합한 사용 케이스**
  - 충돌이 적은 비즈니스 환경
  - 읽기가 많고 쓰기가 적은 경우

#### Pessimistic Lock
- **장점**
  - 충돌이 빈번한 경우 Optimistic Lock보다 효율적
  - 데이터 정합성 보장이 강력함
- **단점**
  - 성능 저하 가능성
  - 데드락 주의 필요
- **적합한 사용 케이스**
  - 충돌이 빈번한 환경
  - 데이터 정합성이 매우 중요한 경우

### 2. Database Level (Named Lock)
- **장점**
  - 분산 환경에서 동시성 제어 가능
  - 트랜잭션 종료 시 자동 락 해제
- **단점**
  - 트랜잭션 종료 시까지 락이 유지되어 성능 영향
  - 데이터베이스 부하 증가
- **적합한 사용 케이스**
  - 분산 환경에서의 동시성 제어
  - 데이터베이스를 활용한 락 관리가 필요한 경우

### 3. Distributed Lock

#### Redisson
- **장점**
  - pub/sub 방식으로 효율적인 락 관리
  - 락 획득 재시도를 자동으로 제공
  - 구현이 상대적으로 간단
- **단점**
  - Redis 서버 필요
- **적합한 사용 케이스**
  - 분산 환경에서 높은 신뢰성이 필요한 경우
  - 실시간성이 중요한 경우

#### Lettuce
- **장점**
  - 스핀락 방식으로 구현 간단
  - Redis 기본 클라이언트로 추가 의존성 없음
- **단점**
  - 스핀락으로 인한 Redis 서버 부하
  - 재시도 로직을 직접 구현해야 함
- **적합한 사용 케이스**
  - 간단한 분산 락이 필요한 경우
  - Redis를 이미 사용 중인 환경

### 결론
- 단일 서버 환경: synchronized나 Pessimistic Lock이 적합
- 분산 환경 & 충돌이 적은 경우: Optimistic Lock 고려
- 분산 환경 & 충돌이 많은 경우: Redisson을 활용한 분산 락 권장
- 데이터베이스 의존적인 경우: Named Lock 고려