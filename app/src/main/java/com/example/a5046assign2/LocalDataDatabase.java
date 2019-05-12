package com.example.a5046assign2;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {LocalData.class}, version = 2, exportSchema = false)
public abstract class LocalDataDatabase extends RoomDatabase {
    public abstract LocalDataDao localDataDao();

    private static volatile LocalDataDatabase INSTANCE;
    static LocalDataDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (LocalDataDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE =
                            Room.databaseBuilder(context.getApplicationContext(),
                                    LocalDataDatabase.class, "LocalDataDatabase")
                                    .build();
                }
            }
        }
        return INSTANCE;
    }
}
