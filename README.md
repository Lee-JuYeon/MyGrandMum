# MyGrandMum

* 난관
EncryptedSharedPreferences 
    minSDK 버전이 23인데 비해, EncryptedSharedPreferences을 사용하기 위한 일부 라이브러리가 SDK 24이상을 요구해 EncryptedSharedPreferences을 사용할 수 없게 되었다. 그래서 암복호화를 자체적으로 구현하고, 암호화된 데이터를 SharedPreferences에 CRUD을 적용시켰다.




1. 보호자 위치추적 (설정칸)
2. '눈길', '설리번'같은 서비스 구현
3. '설정칸'에 실종 api연동
