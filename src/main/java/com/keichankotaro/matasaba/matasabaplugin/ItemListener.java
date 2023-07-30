package com.keichankotaro.matasaba.matasabaplugin;

import org.bukkit.event.Listener;

public class ItemListener implements Listener {

	/*
    // プレイヤーごとに直近のアクションを記録するマップ
    private final Map<UUID, Long> lastActionTimestamps = new HashMap<>();
    // プレイヤーごとにアイテムを入れる前の個数を記録するマップ
    private final Map<UUID, Integer> previousAmounts = new HashMap<>();

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        // プレイヤーがチェストやシュルカーボックスを開いたかどうかを確認
        if (event.getClickedInventory() == null) {
            return;
        }

        // プレイヤーがメインインベントリ（プレイヤーの持ち物）を開いた場合は無視
        if (event.getClickedInventory().equals(event.getWhoClicked().getInventory())) {
            return;
        }

        // アクションがチェストやシュルカーボックスのアイテムを取り出し・入れた場合のみ処理
        if (event.getAction().name().contains("PICKUP") || event.getAction().name().contains("PLACE")) {
            ItemStack itemStack = event.getCurrentItem();
            if (itemStack != null) {
                int amount = itemStack.getAmount();
                String action = event.getAction().name().contains("PICKUP") ? "取り出し" : "入れた";
                String itemName = itemStack.getType().name();
                UUID playerUUID = event.getWhoClicked().getUniqueId();

                if (action.equals("入れた")) {
                    // アイテムを入れる前の個数を記録
                    previousAmounts.put(playerUUID, amount);
                } else if (previousAmounts.containsKey(playerUUID)) {
                    // アイテムを入れる前の個数が記録されている場合は、それを取得してログに出力
                    int previousAmount = previousAmounts.get(playerUUID);
                    System.out.println(event.getWhoClicked().getName() + "が" + previousAmount + "個の" + itemName + "を" + action);
                    previousAmounts.remove(playerUUID); // ログを出力したら個数の記録を削除
                } else {
                    // ログを出力する必要がない場合は、直近のアクションから一定時間が経過しているかどうかだけを更新
                    updateLastActionTimestamp(playerUUID);
                }
            }
        }
    }

    private void updateLastActionTimestamp(UUID playerUUID) {
        // 現在のタイムスタンプを記録
        lastActionTimestamps.put(playerUUID, System.currentTimeMillis());
    }
    */
}
