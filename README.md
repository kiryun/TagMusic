## TagMusic(학교 프로젝트)

#### 1. 개발환경 및 역할

- 참여인원: 2명

  - 개발분야:  Android 모바일 어플리케이션 개발

- 제가 담당한 기술 스택
![image-20190916222439030](https://user-images.githubusercontent.com/40315820/75950771-83388180-5eed-11ea-9669-6d1896a35c43.png)

#### 2. 프로젝트 개요

Tag를 이용해 어플리케이션내에 있는 음악을 관리하는 음악 어플리케이션 입니다.

기존 음악 어플리케이션들의 재생목록은 사용자가 Directory를 생성 후 음악을 집어 넣는 형식입니다.

\#TagMusic은 음악에 Tag를 달아두어서 사용자가 보다 탄력적으로 재생목록을 관리할 수 있게 해주는 어플리케이션입니다.

* 기존 문제점

  사용자는 재생목록을 만들 때 가술별, 장르별, 내가 좋아하는 노래별 등으로 만듭니다.

  따라서 재생목록을 만들 때 하나의 노래에 여러가지의 재생목록을 만들어야 자신만의 재생목록을 꾸밀 수 있습니다.

  예를 들면 "볼빨간사춘기 - 싸운날"노래는 볼빨간사춘기, k-pop, 발라드 3가지의 재생목록을 모두 만들어야 합니다.

  또한 재생목록을 수정하려면 직접 재생목록에 있는 음악을 하나하나 수정을 해줘야합니다. 이러한 번거로움을 없애고자 생각해낸게 \#TagMusic 입니다.
  ![image-20190916223848053](https://user-images.githubusercontent.com/40315820/75950774-87fd3580-5eed-11ea-952a-4700b29ca5fc.png)
  ![image-20190916223906762](https://user-images.githubusercontent.com/40315820/75950779-8b90bc80-5eed-11ea-8130-1712abfd189f.png)
  ![image-20190916223922812](https://user-images.githubusercontent.com/40315820/75950783-8f244380-5eed-11ea-9258-1bb5d468a118.png)
  ![image-20190916223946270](https://user-images.githubusercontent.com/40315820/75950788-92b7ca80-5eed-11ea-8075-056805228d71.png)
  ![image-20190916224036995](https://user-images.githubusercontent.com/40315820/75950792-977c7e80-5eed-11ea-94b4-d1b5a5ab38a8.png)

  

  

* 해결

  재생목록의 하위에 음악이 있는 것이 아닌 음악(볼빨간사춘기 - 싸운날)에 #볼빨사 #k-pop #발라드 라는 3개의 tag를 달고 "adele = hello"라는 노래에 #adele #pop #favorite라는 3개의 tag를 달았을 때 #발라드 tag를 재생시키면 #발라드가 tagging된 "볼빨간사춘기 - 싸운날", "adele - hello"가 재생되는 형식입니다.

  또한 노래를 듣다가 볼빨간사춘기 노래만을 듣고 싶다면 #발빨사 tag만 남겨두면 재생목록이 다시 update되고 특정 가수, 장르의 노래를 들을 수 있습니다.

  즉, 재생목록을 단어하나에만 국한 시키지않고 속성을 붙여주는 것입니다. 속성에 음악파일이 들어있는 것이 아니라 음악에 속성을 붙여줌으로서 원하는 음악을 탄력적으로 들을 수 있을 것입니다.
  ![image-20190916224803628](https://user-images.githubusercontent.com/40315820/75950826-b418b680-5eed-11ea-90dc-816f7f9e87de.png)
  ![image-20190916224116605](https://user-images.githubusercontent.com/40315820/75950835-b713a700-5eed-11ea-9bd5-6aa141879fa6.png)

#### 3. 프로젝트를 통해 얻은 점

* Java를 기반으로한 Android 모바일 어플리케이션 개발의 이해

  * Android의 ORM을 통한 SQLite 핸들링 방법의 이해

* Android service에서 동작하는 기능 개발의 이해

  * Android View 구조의 이해
  * **모바일프로그래밍 과목 A+ 취득**

  #### 4. 프로젝트에 기여한 점

* Java를 기반으로한 Android모바일 어플리케이션 개발

  * PagerView를 이용한 UI 제작

    각 View마다 이동이 자유롭게 하기 위해서 PagerView를 이용했습니다. 스와이프만으로도 각 카테고리의 화면을 손쉽게 전환 가능하도록 했습니다.

    ![image-20190916232312339](https://user-images.githubusercontent.com/40315820/75950869-ceeb2b00-5eed-11ea-95f8-60d7fe0e8dbc.png)

    PagerView는 기본적으로 Fragment로 되어있으며 어플리케이션을 구현하기 위해서는 Fragment간에 data이동이 필요합니다.

  * TotalMusicManager를 통한 데이터 관리

    따라서 모든 정보를 관리하는 TotalMusicManager(이하 TMM)을 만들었으며, Singleton 디자인 패턴을 따릅니다. TMM은 SQLite(DB), storage내 음악파일, UI에 업데이트될 정보를 관리합니다.

    <center>[Class구조]</center>

    ![image-20190916233428518](https://user-images.githubusercontent.com/40315820/75950881-d579a280-5eed-11ea-9593-e6d29a49f7e4.png)

  MainAcitivity에서 PagerView를 호출하기 위해 BaseAdaper형태의 pagerAdapder class를 만들었으며 각 카테고리들(music, tag, play)들을 누를 때 마다 그에 따른 fragment가 호출되며, 스와이프로도 화면(frament)이 전환되도록 구현했습니다.

  

```java
      @Override
      public android.support.v4.app.Fragment getItem(int position)
    {
        switch(position){
          case 0:
            throw_args.putSerializable("TotalMusicManager", tmm);
            fragment_mf.setArguments(throw_args);
            return fragment_mf;
          case 1:
            throw_args.putSerializable("TotalMusicManager", tmm);
            fragment_tf.setArguments(throw_args);
            return fragment_tf;
          case 2:
            throw_args.putSerializable("TotalMusicManager", tmm);
            fragment_pf.setArguments(throw_args);
            return fragment_pf;
          default:
            return null;
        }
      }
```

  각 fragment는 서로 data를 공유하기 위해 tmm을 MainActivity에서 생성했으며, pagerAdapter에서 각 fragment를 호출할 때 마다 putSerializable()메소드를 이용해 tmm을 넘겨줍니다.

  * SQLite를 통해 DB관리

  * Android service 에서 동작하는 음악재생 기능 구현

    Android의 MediaPlayer 패키지를 이용해서 구현했습니다.

    음악재생 도중 재생목록가 update되는 것을 알기위해 Thread에서 ProgressUpdate를 해줬습니다.

    음악재생을 위한 UI가 MainActivity에 선언 돼있기 때문에 MainActivity의 각 버튼에 setOnclickListener를 달아두었습니다. 각 버튼을 누를 때 마다 setOnclickListener는 trigger가 되어 MediaPlayer의 pause, play, previous, next를 각각 호출하며 음악 재생 도중 일시정지, 재생, 이전 곡, 다음 곡을 구현했습니다.
