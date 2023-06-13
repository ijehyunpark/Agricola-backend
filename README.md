[![Java CI with Gradle](https://github.com/ijehyunpark/Agricola-backend/actions/workflows/gradle.yml/badge.svg?branch=develop)](https://github.com/ijehyunpark/Agricola-backend/actions/workflows/gradle.yml)
# SE MEOSS Agricola-backend

- java 17
- spring boot 3.0.5

### 아키텍쳐 설계 이유

클라이언트와 서버간의 통신을 위한 책임을 분배하는 최적의 핵심 레이어로 구성하였다. 

- Controller 단에서는 클라이언트의 입력을 해석하고 적절한 책임을 가지고 있는 비지니스 단으로 이를 전달한다.
- 비지니스 단에서는 게임의 상태를 전이하고 게임을 진행시키는 핵심 로직을 담당한다. 해당 레이어에서는 실제 게임 모델의 상태와 정보를 수정하며 게임의 진행 사항을 조정한다.
- 데이터 접근 단에서는 실제 게임 정보와 상태를 관리하고 제어한다. 해당 프로젝트에서는 모든 데이터가 서버 메모리 상에서 처리된다.

---

이런 핵심 3단으로 책임을 분산시켜 각 레이어가 자기 자신만의 역할과 책임에만 한정되어 있기에 개발과 유지 보수성에 큰 역할을 이룬다.

# 통신 시스템 설정

## websocket + stomp 사용

- 동시에 4명과 게임 진행사항을 소통하기 위해서 사용
    - REST와 같은 기존 메세지 교환 프로토콜을 사용할 경우 메세지를 주고받은 유저하고만 소통이 가능하다.
    - STOMP를 사용하면 발행/구독을 사용하여 관심이 있는 모든 유저와 함께 메세지를 주고 받는 것이 가능하다.
    - 게임은 참여자 모두가 게임 상태에 관심이 있다.
- 클라이언트와 서버 간에 세션을 사용하여 서버에 추가적인 요청 없이 간단히 GUEST 상태로 게임을 할 수 있도록 구성되어 있다. 이를 통해 추가적인 로직 모듈과 아그리콜라 게임 로직 모듈간의 완전한 분리를 통해 게임 로직에만 집중하여 개발할 수 있다. (**데이터 베이스 사용을 베제한 가장 중요한 이유**)
- 만약 게임 재접속 및 랭킹 시스템을 도입한다고 하면 아그리콜라 게임 로직과 로그인 서버를 연결하여 추가적인 서비스를 간단하게 지원할 수 있다.

## 게임 방 생성

- REST API로 구성되어 있다.
- 수많은 플레이어가 동시에 게임을 할 수 있도록 게임방을 생성, 수정, 삭제를 할 수 있도록 구성되어 있다.

## 클래스다이어 그램 작성이유

- 초기 객체 분석
    
    ![클래스 추출](https://github.com/ijehyunpark/Agricola-backend/assets/43169027/789be0aa-db21-45e1-bc6e-5a0374c3f943)

- 완성된 클래스 다이어그램
    
    ![클래스 다이어그램](https://github.com/ijehyunpark/Agricola-backend/assets/43169027/579b4846-1ebe-4ab7-a5a7-e454f7771cc0)

    
- 초기 단계에서는 요구사항 분석을 통해 예상되는 클래스들을 식별하였습니다. 이 과정은 개발자들이 실제 개발이 이루어지기 전에 시스템 내에 존재할 수 있는 객체들을 조사하고 이해하는 데 도움이 되었습니다. (위 사진)
- 클래스 다이어그램을 작성한 이유
    - 백엔드 개발 팀이 클래스 역할, 구조를 동일하게 인식하고 혼동 없이 작업을 진행할 수 있도록 도와주었습니다.
    - 객체들을 더욱 세부적으로 분류하고 상속 관계를 나타내는 것이 가능하였습니다. 이를 통해 시스템의 복잡성을 관리하고, 재사용성을 높이며, 유지 보수를 용이하게 하는 등의 이점을 얻을 수 있었습니다.

## Action 구현 방식

### 요구사항

- 각 Action은 round를 보유해야 한다.
- 자원을 누적시키는 Action의 경우 해당 자원을 매 round마다 누적시켜야 한다.
- 한면 사용한 Action의 경우 해당 라운드동안에는 사용할 수 없어야 한다.
- 각 Action 별로 구별되는 작업을 수행해야 한다.

### 문제 분석

- Action 별로 공통되는 로직이 존재한다.
    - 플레이어 필드에 건물 건설
    - 울타리 건설
    - 자원 획득
    
    ---
    
    따라서 공통되는 로직을 분리하여 실제 Action 구현체를 조립하기 위한 요소 객체를 만들자
    
- Action의 로직과 상태값을 분리할 수 있다.
    - 각 Action의 로직의 경우 모든 게임에서 고정된 값이므로 중복해서 생성할 필요가 없다.
        - e.g) 방 건설 작업은 어느 게임에서도 동일하다.
    - 반면에 stack 혹은 공개되는 라운드의 경우 매 게임마다 다르고 게임 중간에서 변경된다.
    
    ---
    
    따라서 로직을 Singleton으로 분리하고 해당 로직을 보유하는 Action 구현체(이후 Event라 지칭)를 stack과 라운드와 함께 조립하여 객체를 만들자
    
- Action 로직을 여러개 보유하고 있는 Action들이 있다.
    - Event에서 Action logic을 여러개를 보유하고 DoType으로 묶자
        - DoType: 그리고/또는, 이후의, 또는 .. 등등 연결자
    

### 실제 구현

![액션](https://github.com/ijehyunpark/Agricola-backend/assets/43169027/fd921ecd-81e3-481d-a51f-80507ca893a0)


- 공통되는 Action을 분리하여 제작한 후 ActionFactory에서 Action들을 조립하여 원하는 Action 인스턴스를 생산한다.
- 생산된 Action 인스턴스를 사용하여 ActionImplement를 조립하여 구성한 뒤 게임의 Action의 상태를 표현하기 위해 Event 내부에 구성시켜서 아그리콜라의 Action을 구성하였다.
- Action과 ActionImplement 그리고 Factory들은 **Singleton**으로 처리한다. ****(변하는 상태 값이 없다.)
- 만약 새로운 Action을 추가로 개발하라는 요구사항이 생길 경우 기존의 구현체를 조립하여 쉽게 대처가 가능한 구조이다.
    - 만약 **나무 100개를 일시불로 획득 또는 방 건설**을 수행하는 **무차별 벌목** Action Tile을 만들고 싶을 경우를 가정해보자
        - ActionFactory에서 BasicAction(단순 자원 획득)를 조립한 100개 일시불 Action을 구성한다.
        - 방 건설의 경우 이미 구현되어 있으므로 기존의 Action을 사용한다.
        - ActionImplementFactory에서 새로운 Action 구현체를 위 2개의 Action 요소를 조립하여 생성한다.
        - Event에 만든 구현체를 넣어서 게임 보드에 넣어주면 된다.

## 카드 구현 방식

![카드](https://github.com/ijehyunpark/Agricola-backend/assets/43169027/85ef9a19-45d6-46bc-91d5-869fb8106a69)

- 카드도 액션과 동일한 방식으로 구현된다.
- 모든 카드를 관리하는 CardDictionary에서 **주설비, 보조설비, 직업** 카드의 상태와 소유자를 처리하는 모든 작업의 책임을 가진다.
- 카드도 액션과 동일하게 모든 게임과 게임 중 변하는 값이 없으므로 **Singleton**으로 구성되고 Factory를 통해 **인스턴스를** 생성한다.
- CardDictionary의 경우 모든 게임에서 다른 값을 가져야 한다. 따라서 Singleton이 아닌 **Prototype**으로 처리하여 모든 게임이 다른 인스턴스를 가지도록 구성한다.(게임마다 특정 카드의 소유자가 다르기 때문이다.)
- 같은 domain을 처리하는 Card가 있을 경우 해당 Interface를 생성하여 공통 logic에 대해 간단히 처리할 수 있도록 분리하였다. (다형성)
    - 빵 굽기에 관심이 있는 화로와 화덕이 예시가 될 수 있다.
- 역시 카드 또한 interface내에서 새로운 카드 추가 요청사항에 대해 Action과 동일한 방식으로 간단하게 구현할 수 있다.
- Card라는 인터페이스를 가장 상위클래스로 두는 것으로 주설비, 보조설비, 직업카드등에서 공통적으로 사용되는 인터페이스(다음 특정 라운드카드에 자원을 미리 두는 카드등)의 경우 Card를 부모로 갖는 인터페이스를 작성하여 시스템내에서 처리하기 편하도록 구성하였다.

## 게임 로직 구현 방식

- 동시에 같은 요청이 올 수 있으므로 임계 영역에 대해 **모니터를** 적용하여 **상호 배제**를 수행하였다.
- 게임의 상태를 정의하고 유저의 요청을 받아 **다음 상태로 전의**하는 구조를 가진다.
- 상태: (start) → PlayAction → Harvest(Blocking) | AnimalOverFlow(Blocking) | PlayAction → … → Finish
- 해당 상태에 맞추어 유저의 요청을변환 받고 다음 상태로 전의하는 메소드를 담은 interface를 제작하고 이를 구현하였다.

```java
/**
 * 아그리 콜라 게임 메인 로직
 */
public interface AgricolaService {
    // 게임 시작
    void start(Long gameRoomId);
    // 플레이어 액션 요청 수락
    void playAction(Long gameRoomId, Long eventId, List<AgricolaActionRequest.ActionFormat> acts);
    // 플레이어 교환 요청 수락
    void playExchange(Long gameRoomId, Long improvementId, ResourceType resource, int count);
    void playExchange(Long gameRoomId, Long improvementId, AnimalType resource, int count);
    // 플레이어 재배치 요청 수락
    void playRelocation(Long gameRoomId, Integer y, Integer x, Integer newY, Integer newX, Integer count);
    void playRelocation(Long gameRoomId, AnimalType animalType, Integer newY, Integer newX, Integer count);
    // 게임 종료
    void finish(Long gameRoomId);
    // 플레이어 턴 검
    boolean validatePlayer(Long gameRoomId, Object userId);
}
```

## 부가 기능(점수 계산, 플레이어 객체 구현체)

- 아그리콜라 구현체의 경우 실시간으로 **현재 플레이어의 점수를 계산하는 기능**을 가지고 있다.
- 플레이어 객체에 각 필드의 구성 요소(집, 농지, 외양간)과 울타리에 대해서도 구현이 되어 있다. 이는 적절한 요청이 있을 경우에 한해 요소 건설을 허용하고 있다.

## 테스트 방식

- 테스트는 해당 객체가 지원해야 하는 기능에 관련된 메소드를 모두 커버할 수 있는 목표 하에 제작되었다.
- 간단한 자원 획득에 관심이 있는 기본 자원 획득 테스트의 경우
    - 적절하게 해당 자원에 플레이어 객체에 추가가 되었는지 검증한다.
    - 여러 자원을 동시에 주는 자원 획득 흐름에 대해서도 정상적으로 작동하는지 검증한다.
    - 여러번 자원 획득 메소드를 수행해도 원하는 결과가 지속적으로 도출되는지 검증한다.
    
    ---
    
    이러한 방식으로 테스트가 진행된다.
    
- 테스트는 해당 객체가 지원하는 바를 중점적으로 테스트하기 위해 **Unit Test** 단계에서 수행된다. 테스트시 불필요한 객체일 경우 Builder pattern을 사용하여 빈 객체를 사용하고, 불가피할 경우 **Mock & stub**를 사용하여 해당 객체를 테스트에서 배제하여 수행한다.
