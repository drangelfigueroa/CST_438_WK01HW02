package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.myapplication.db.AppDAO;
import com.example.myapplication.db.AppDatabase;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.myapplication", appContext.getPackageName());
    }

    @Test
    public void verifyUser(){
        User user = new User("username", "password");
        assertEquals("username", user.getUsername());
        assertEquals("password", user.getPassword());
        assertNotEquals("userName", user.getUsername());
        assertNotEquals(" pasSword ", user.getPassword());
    }
}