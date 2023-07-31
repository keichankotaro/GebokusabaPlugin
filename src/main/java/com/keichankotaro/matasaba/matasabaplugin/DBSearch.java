package com.keichankotaro.matasaba.matasabaplugin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBSearch {
	public static List<List<String>> search(String[] tables, String[] SearchStrings) {
		if (tables.length != SearchStrings.length) {
            throw new IllegalArgumentException("Tables and SearchStrings must have the same length.");
        }
		
		if (tables.length == 1 && SearchStrings.length == 1 && tables[0].equalsIgnoreCase("*") && SearchStrings[0].equalsIgnoreCase("*")) {
			String query = "SELECT * FROM block;";
			ResultSet res = DBManager.RUN_DB(query, DBManager.connection);
			List<List<String>> responce = new ArrayList<>();
			
			try {
				while(res.next()) {
					List<String> temp = new ArrayList<String>();
					String id = res.getString("ID");
					String uuid = res.getString("UUID");
					String x = res.getInt("X") + "";
					String y = res.getInt("Y") + "";
					String z = res.getInt("Z") + "";
					String yaw = res.getFloat("Yaw") + "";
					String pitch = res.getFloat("Pitch") + "";
					String world =  res.getString("World");
					String func = res.getString("FUNC");
					String item = res.getString("ITEM");
					String num = res.getString("NUM");
					String time = res.getInt("TIME") + "";
					//List<String> temp = ArrayList<String> ([id, uuid, x, y, z, yaw, pitch, world, func, item, num]);
					temp.add(id);
					temp.add(uuid);
					temp.add(x);
					temp.add(y);
					temp.add(z);
					temp.add(yaw);
					temp.add(pitch);
					temp.add(world);
					temp.add(func);
					temp.add(item);
					temp.add(num);
					temp.add(time);
					responce.add(temp);
				}
			} catch (SQLException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			return responce;
		} else {
			StringBuilder Search = new StringBuilder();
			
			for (int i = 0; i < tables.length; i++) {
	            if (i > 0) {
	                Search.append(" AND ");
	            }
	            Search.append("\"").append(tables[i]).append("\" = ").append("\"").append(SearchStrings[i]).append("\"");
	        }
			
			String query = "SELECT * FROM block WHERE " + Search.toString() + ";";
			ResultSet res = DBManager.RUN_DB(query, DBManager.connection);
			List<List<String>> responce = new ArrayList<>();
			
			try {
				while(res.next()) {
					List<String> temp = new ArrayList<String>();
					String id = res.getString("ID");
					String uuid = res.getString("UUID");
					String x = res.getInt("X") + "";
					String y = res.getInt("Y") + "";
					String z = res.getInt("Z") + "";
					String yaw = res.getFloat("Yaw") + "";
					String pitch = res.getFloat("Pitch") + "";
					String world =  res.getString("World");
					String func = res.getString("FUNC");
					String item = res.getString("ITEM");
					String num = res.getString("NUM");
					String time = res.getInt("TIME") + "";
					//List<String> temp = ArrayList<String> ([id, uuid, x, y, z, yaw, pitch, world, func, item, num]);
					temp.add(id);
					temp.add(uuid);
					temp.add(x);
					temp.add(y);
					temp.add(z);
					temp.add(yaw);
					temp.add(pitch);
					temp.add(world);
					temp.add(func);
					temp.add(item);
					temp.add(num);
					temp.add(time);
					responce.add(temp);
				}
			} catch (SQLException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			return responce;
		}
	}
}