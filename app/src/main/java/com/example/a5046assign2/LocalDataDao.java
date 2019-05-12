package com.example.a5046assign2;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;
@Dao
public interface LocalDataDao {
    @Query("SELECT * FROM localdata")
    List<LocalData> getAll();
    @Query("SELECT * FROM localdata WHERE dailyStepId LIKE :first AND " +
            "steps LIKE :last LIMIT 1")
    LocalData findByFirstandLastName(String first, String last);
    @Query("SELECT * FROM localdata WHERE dailyStepId = :dailyStepId LIMIT 1")
    LocalData findByID(int dailyStepId);
    @Insert
    void insertAll(LocalData... localdatas);
    @Insert
    long insert(LocalData localdata);
    @Delete
    void delete(LocalData localdata);
    @Update(onConflict = REPLACE)
    public void updateUsers(LocalData... localdatas);
    @Query("DELETE FROM localdata")
    void deleteAll();
}
