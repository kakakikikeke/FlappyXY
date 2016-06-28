# FlappyXY
センサーを使った Android 用のゲームアプリです

# インストール方法
以下の URL からインストールしてください。  
https://play.google.com/store/apps/details?id=com.blogspot.kakakikikeke.sensortest

# 遊び方

![FlappyXY-demo](https://lh3.googleusercontent.com/wlIiuwOU0CDDFxyht0B8rRWP9jcpgLwxKy48tA8IPiLw8UjHDzHvmRGmT-jWPRS92mo=h450)

* ゲームがスタートしたら指定の角度にスマートフォンを傾けてください
* クリアするたびにポイントが加算されます
* タイムオーバになるまで永遠に続きます

# 開発者向け

## 開発バージョン
* Android Over 4.0.3
* AndroidStudio 1.3.2
  * Lombok plugin 0.9.6.14
* Java 1.8.0_31
* NCMB 1.5.4

## 開発のためのソースインストール方法
* git clone https://github.com/kakakikikeke/android-sensor-game.git
* AndroidStudio を開く
* Open an existing Android Studio project -> clone したプロジェクトを選択
* Gradle Sync -> OK

### 設定ファイルの編集
* app/src/main/resources/config.properties の編集 (オプション)
  * [NCMB](http://mb.cloud.nifty.com/) を使ってデバイス情報を登録することができます
  * デバイス情報を登録すると NCMB を使ってプッシュ通知を送信することができます

# バグ・要望

Issues または Pull requests で報告してもらえると助かります。
