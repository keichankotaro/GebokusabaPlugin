package com.keichankotaro.matasaba.matasabaplugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
	        query_block = "CREATE TABLE IF NOT EXISTS [EULA] ([ID] TEXT, [UUID] TEXT, [EULA] BOOLEAN)";
	        connection.createStatement().execute(query_block);
	        query_block = "CREATE TABLE IF NOT EXISTS [LAST_PLACE] ([ID] TEXT, [UUID] TEXT, [X] REAL, [Y] REAL, [Z] REAL, [Yaw] REAL, [Pitch] REAL, [World] TEXT)";
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
    
    public static void AddDB_EULA(String id, String uuid) {
    	try {
    		String query = "INSERT INTO EULA VALUES (\""+id+"\", \""+uuid+"\", true)";
    		connection.createStatement().execute(query);
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
    }
    
    public static void AddDB_LastPlace(String id, String uuid, double x, double y, double z, double yaw, double pitch, String world) {
    	try {
            // 既存のUUIDが存在するか確認
            String checkQuery = "SELECT COUNT(*) FROM LAST_PLACE WHERE UUID='" + uuid + "'";
            ResultSet resultSet = connection.createStatement().executeQuery(checkQuery);
            resultSet.next();
            int count = resultSet.getInt(1);

            if (count > 0) {
                // 既存のUUIDが存在する場合は更新
                String updateQuery = "UPDATE LAST_PLACE SET X=" + x + ", Y=" + y + ", Z=" + z + ", Yaw=" + yaw + ", Pitch=" + pitch + ", World='" + world + "' WHERE UUID='" + uuid + "'";
                connection.createStatement().execute(updateQuery);
            } else {
                // 存在しない場合は挿入
                String insertQuery = "INSERT INTO LAST_PLACE (ID, UUID, X, Y, Z, Yaw, Pitch, World) VALUES ('" + id + "', '" + uuid + "', " + x + ", " + y + ", " + z + ", " + yaw + ", " + pitch + ", '" + world + "')";
                connection.createStatement().execute(insertQuery);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
	public static boolean Check_EULA(String uuid) {
		try {
	        String query = "SELECT EULA FROM EULA WHERE UUID = '" + uuid + "'";
	        ResultSet resultSet = connection.createStatement().executeQuery(query);
	        if(resultSet.next()) {
	            if (resultSet.getBoolean("EULA")) {
	            	return true;
	            } else {
	            	return false;
	            }
	        } else {
	            return false;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	public static List<Object> GetDB_LastPlace(String uuid) {
        List<Object> lastPlaceInfo = new ArrayList<>();
        try {
            String query = "SELECT X, Y, Z, Yaw, Pitch, World FROM LAST_PLACE WHERE UUID = '" + uuid + "' ORDER BY ID DESC LIMIT 1";
            ResultSet resultSet = connection.createStatement().executeQuery(query);
            if(resultSet.next()) {
                double x = resultSet.getDouble("X");
                double y = resultSet.getDouble("Y");
                double z = resultSet.getDouble("Z");
                double yaw = resultSet.getDouble("Yaw");
                double pitch = resultSet.getDouble("Pitch");
                String world = resultSet.getString("World");
                lastPlaceInfo.add(x);
                lastPlaceInfo.add(y);
                lastPlaceInfo.add(z);
                lastPlaceInfo.add(yaw);
                lastPlaceInfo.add(pitch);
                lastPlaceInfo.add(world);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lastPlaceInfo;
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