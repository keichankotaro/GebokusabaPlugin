# GebokusabaPlugin
下僕鯖(マインクラフトサーバー)に使われている独自プラグインです。
メインプログラムは[こちら](https://github.com/keichankotaro/GebokusabaPlugin/blob/main/src/main/java/com/keichankotaro/gebokusaba/gebokusabaplugin/GebokusabaPlugin.java)

## 使い方
`/tpb`・・・鉄2個消費して拠点にテレポート
その他のコマンド(同じアクション):`/tpbase`, `/gobase`, `/gob`

`/tpbe`・・・鉄4個消費して5x5x5の範囲にいる一番近くのエンティティ(ボート・トロッコ・ドロップアイテム等を含む)と一緒に拠点にテレポート。
その他のコマンド(同じアクション):`/tpbasewithentity`, `/tpbwe`, `/gobasewithentity`, `/gobwe`, `/gobe`

`/seed`・・・ワールドのシード値の表示。

`/rlg`・・・config.ymlの再読み込み。

## config.ymlの設定
config.ymlには拠点の座標を入れます。
```
base:
  x: 拠点のx座標
  y: 拠点のy座標
  z: 拠点のz座標
  yaw: テレポートしたとき向きたい向き(上下)
  pitch: テレポートしたとき向きたい向き(左右)
  world: 拠点があるワールド(world, world_nether, world_the_end等)
items:
  tpalone:
    item: 拠点tpする時に使用するアイテム
    num: 上のアイテムの必要数
  tpwithentity:
    item: エンティティと一緒に拠点tpする時に使用するアイテム
    num: 上のアイテムの必要数
```
アイテムを設定するときは、[こちら](https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Material.html)を参照してください。
初期値は次のようになっています。
```
base:
  x: 0
  y: 64
  z: 0
  yaw: 0
  pitch: 0
  world: world
items:
  tpalone:
    item: IRON_INGOT
    num: 2
  tpwithentity:
    item: IRON_INGOT
    num: 4
```
