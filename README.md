
<div id="top"></div>
<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li><a href="#built-with">Built With</a></li></li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
    <li><a href="#acknowledgments">Acknowledgments</a></li>
  </ol>
</details>

### 멑티 모듈 설계

멀티 모듈 설계는 [이곳](https://techblog.woowahan.com/2637 "멀티모듈 설계 이야기 with Spring, Gradle")을 참조하여 작업 중

#### 모듈별 간략 설명
+ Application
+ Core
+ Domain
  + web-core : spring-boot-parent 를 포함하는 웹 코어 모듈.\
    웹 서비스 구축시 사용될 Base Object(Response, Exception, POJO 등..)를 포함한다.
  + 
+ webclient
  + keycloak : Red hat의 오픈 소스 SSO 서비스의 REST API 를 호출하기위한 Feign Web client infrastructure 모듈.
    
### 버저닝

[유의적 버전](./docs/SEMANTIC_VERSIONING.md) 참조

```text
버전을 주.부.수 숫자로 하고:
1. 기존 버전과 호환되지 않게 API가 바뀌면 “주(主) 버전”을 올리고,
2. 기존 버전과 호환되면서 새로운 기능을 추가할 때는 “부(部) 버전”을 올리고,
3. 기존 버전과 호환되면서 버그를 수정한 것이라면 “수(修) 버전”을 올린다.
```

## Getting Started

This is an example of how you may give instructions on setting up your project locally.
To get a local copy up and running follow these simple example steps.

### Prerequisites

프로젝트 작성 시점 메이븐만 작성되어있음.

* maven
  ```sh
  mvn clean install
  ```

<p align="right">(<a href="#top">back to top</a>)</p>


<!-- ROADMAP -->
## Roadmap

- [ ] Docs
- [ ] 코어, 유틸성 모듈 분리

<p align="right">(<a href="#top">back to top</a>)</p>