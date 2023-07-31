package com.keichankotaro.matasaba.matasabaplugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBManager {
	public static Connection connection;

    public static void connect() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:plugins/MatasabaPlugin/database.db");
            System.out.println("Connected to database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void disconnect() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Disconnected from database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ここにデータベース操作のメソッドを追加します（テーブル作成、データの読み込み・書き込みなど）
    // 例えば、テーブルを作成するメソッドを以下に示します：
    public static void createTable() {
        try {
            String query_block = "CREATE TABLE IF NOT EXISTS [block] ([ID] TEXT, [UUID] TEXT, [X] INTEGER, [Y] INTEGER, [Z] INTEGER, [Yaw] REAL, [Pitch] REAL, [World] TEXT, [FUNC] TEXT, [ITEM] TEXT, [NUM] INTEGER, [TIME] INTEGER);";
            connection.createStatement().execute(query_block);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void AddDB_BLOCK(String id, String uuid, int x, int y, int z, float yaw, float pitch, String world, String func, String item, int num, int time) {
    	try {
    		String query = "INSERT INTO block VALUES (\""+id+"\", \""+uuid+"\", "+x+", "+y+","+z+", "+yaw+", "+pitch+", \""+world+"\", \""+func+"\", \""+item+"\", "+num+", "+time+")";
    		connection.createStatement().execute(query);
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
    }
    
    public static ResultSet RUN_DB(String query, Connection connection) {
    	try {
    		return connection.createStatement().executeQuery(query);
    	} catch (SQLException e) {
    		e.printStackTrace();
    		return null;
    	}
    }
}