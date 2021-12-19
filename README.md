# Spectator Theater
プレイヤーのスペクテイターモードを管理するプラグイン

## 使い方

### スペクテイターモードの開始

`/spectatortheater start`を実行でスペクテイターモードに入ります。

### スペクテイターモードの終了

スペクテイターモードには時間制限があり、`config.yml`で指定した`time-limit`の秒数が経過すると自動的にスペクテイターモードが終了します。
また、`/spectatortheater end`を実行することで制限時間前に終了することも可能です。

そのほかにも、スペクテイターモードは以下の場合で終了します。
- ワールド間の移動を行った場合(スペクテイターモードによるテレポートでワールドをまたいだ場合でも終了します。テレポート先のプレイヤーがどのワールドに居るか確認してからテレポートしてください。)
- プレイヤーのゲームモードが変更された場合
- プレイヤーがゲームを退出した場合
- SpectatorTheaterが無効化された場合

### クールタイム

スペクテイターモードにはクールタイムがあり、最後にスペクテイターモードを終了した時間から`config.yml`で指定した`cool-time`秒は新たにスペクテイターモードを開始することはできません。

### ※サーバーの再起動について

SpectatorTheaterはサーバーの再起動を超えたデータの保存を行いません。
よって、クールタイムはサーバーの再起動によって初期化されます。

---

## Command

`/spectatortheater`  

SpectatorTheaterの機能を使用するためのコマンドです。  
エイリアス: `/spectator`  
必要権限: `spectatortheater.command.spectatortheater`

### Sub Commands

`/spectatortheater start`  
スペクテイターモードを開始します。

`/spectatortheater end`  
スペクテイターモードを終了します

---

## Permission

`spectatortheater.command.spectatortheater`

`/spectatortheater`コマンドを実行するために必要な権限です。  
初期状態で全プレイヤーが所持しています。

---

## 開発環境
- Spigot 1.12.2
- Kotlin 1.6.10

## プラグインのビルド

`shadowJar`タスクを実行してください。