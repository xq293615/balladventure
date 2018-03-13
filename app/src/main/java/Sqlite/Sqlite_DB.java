package Sqlite;

import android.database.Cursor;

import com.example.balladventure.BeginActivity;


public class Sqlite_DB {

	public static void isInsert() {
		Cursor cursor = BeginActivity.db.query("game_information", null, null, null, null,
				null, null, null);
		if (cursor.getCount() == 1) {

		} else {
			BeginActivity.db.execSQL("insert into game_information values(?,?,?)",
					new Object[] { null, 1, 4});
		}
	}
	public static String find_Data(int i){
		Cursor cursor = BeginActivity.db.query("game_information", null, null, null, null,
				null, null, null);
		cursor.moveToFirst();
		return cursor.getString(i);
	}
	public static void update_Data(String attr,String text){
		BeginActivity.db.execSQL("update game_information set " + attr + "=? where _id=1",
				new Object[] { text });
	}
}
