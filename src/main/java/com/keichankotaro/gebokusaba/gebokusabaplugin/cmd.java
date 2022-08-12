package com.keichankotaro.gebokusaba.gebokusabaplugin;

import java.io.IOException;

public class cmd {
 
    @SuppressWarnings("unused")
	public static void run(String string) {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process p  = runtime.exec(string);
            p.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
    }
}