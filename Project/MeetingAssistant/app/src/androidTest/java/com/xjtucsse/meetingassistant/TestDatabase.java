package com.xjtucsse.meetingassistant;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.runner.RunWith;


public class TestDatabase {
    public void testCreate(){

    }
    public void testInsert(){
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        DatabaseDAO dao = new DatabaseDAO(appContext);
        dao.insert("1011","Happy","2020","2021","This is note");
    }
    public void testDelete(){
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        DatabaseDAO dao = new DatabaseDAO(appContext);
        dao.delete("1011");
    }
    public void testUpdate(){
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        DatabaseDAO dao = new DatabaseDAO(appContext);
        dao.update("1011","Also note");
    }
    public void testQuery(){
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        DatabaseDAO dao = new DatabaseDAO(appContext);
        dao.query();
    }
}
