package com.example.myapplication;

import com.example.myapplication.db.AppDAO;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    AppDAO appDAO;

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

}