# GoStraight - 고려대학교 캠퍼스 내 편의시설 안내 앱
i-print/water purifier/vending machine/ATM map in KU

Developer: 김재호

## 1. 앱 소개
고려대학교를 방문하는 모든 사람들에게 이 앱을 바칩니다.

우리는 학교에 굉장한 등록금을 내지만 학교가 어떻게 생겨먹었으며 편의시설이 어디에 어떻게 있는지 알기는 굉장히 힘듭니다.

특히 프린터, 정수기, 자판기, ATM은 생활 속에서 하루에도 여러번 마주치고 또 필요로 하지만 이들이 정확히 어느 위치에 존재하고 어떤 식으로 위치하며 또 무엇을 제공하는지는 아무도 정리해주지 않습니다.

이를 해결하기 위해 간단한 앱을 하나 개발했습니다. 이름은 GoStraight. 너무나도 간단하기 때문에 쉽게 이용하실 수 있습니다!
    이 앱은 고려대학교 컴퓨터학과 1학년 2학기 전공관련교양 컴퓨터프로그래밍2 기말 개인프로젝트에서 만들어졌습니다.
    고려대학교 컴퓨터학과 재학중인 18학번 김재호가 개발하였습니다.
> 이 프로젝트는 안드로이드 프로젝트입니다.
>
> 소스코드로는
> - GostraightDBCruct.java
> - GostraightDBHelper.java
> - ListIprintFragment.java
> - MainActivity.java
> - MapFragment.java
> - SplashActivity.java
>
> 가 있으며, 레이아웃으로는
> - activity_main.xml
> - fragment_list.xml
> - fragment_map.xml
>
> 으로 구성되어 있습니다.
>
> 이 외에도 values에 arrays.xml, colors.xml, dimens.xml, strings.xml, styles.xml 이 있는 등 안드로이드 앱개발에 필요한 파일들로 구성되어 있습니다.

## 2. 앱 사용 방법
먼저, app/release 디렉토리에 있는 apk 파일을 자신의 안드로이드 스마트폰에 설치합니다. 이는 100%에 가까운 확률로 최신 버전으로 업데이트 적용된 파일이며, 구글 플레이스토어에 등록하기 전까지 이런 식으로 앱을 배포할 예정입니다.

그 다음엔 앱을 실행시킵니다. 처음 앱을 실행시키면 아직 위치접근권한이 꺼져있기 때문에 권한 요청 메시지가 뜹니다. 부디 허용해주시기 바랍니다ㅠㅠ

첫 화면에는 고려대학교 캠퍼스를 배경으로 하는 지도가 뜹니다. 모든 시설물에 대해 마커가 있기 때문에 각 마커를 터치해 어떤 시설물인지 확인할 수 있습니다.

위쪽 버튼을 눌러 목록화면을 볼 수도 있습니다. 지도 버튼과 목록 버튼이 있으므로 보고 싶은 화면이 있다면 해당하는 버튼을 눌러주세요.

목록 화면에서는 시설물들의 종류와 위치하는 건물을 볼 수 있습니다. 자세한 정보(예: 정확한 층수와 구체적인 위치, 아이프린트라면 PC의 개수 등)를 확인하고 싶으시다면 해당하는 목록을 터치해주세요. 이 상태에서 지도로 그 위치를 확인하고 싶다면 '지도로 보기'를 터치해주세요. 해당하는 마커가 선택된 상태로 지도 화면이 뜨게 됩니다.

캠퍼스 안에는 시설물들이 생각보다 많기 때문에 원하는 종류, 원하는 구역에서만 시설물의 위치를 확인하고 싶으실 겁니다.

이럴 때에는 화면 가장 위에 있는 스피너를 터치해 드롭되는 조건들 중 자신이 원하는 것을 선택해주세요. 조건에 해당하는 시설들만 지도 및 목록화면에 뜨게 됩니다. 다시 모든 종류 혹은 모든 구역을 확인하고 싶다면 스피너 맨 위 조건을 선택해주세요.
