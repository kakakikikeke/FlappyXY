# android-sensor-game
Android のセンサーを使ったゲームです

# 開発バージョン
* Android Over 4.0.3
* AndroidStudio 1.3.2
  * Lombok plugin 0.9.6.14
* Java 1.8.0_31
* NCMB 1.5.4

# 開発のためのソースインストール方法
* git clone https://github.com/kakakikikeke/android-sensor-game.git
* AndroidStudio を開く
* Open an existing Android Studio project -> clone したプロジェクトを選択
* Gradle Sync -> OK

## 設定ファイルの編集
* app/src/main/resources/config.properties の編集 (オプション)
  * [NCMB](http://mb.cloud.nifty.com/) を使ってデバイス情報を登録することができます
  * デバイス情報を登録すると NCMB を使ってプッシュ通知を送信することができます
