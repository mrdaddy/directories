package com.rw.directories.controllers;


import com.rw.directories.dto.DirectoryUpdate;
import com.rw.directories.services.DirectoryUpdateService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DirectoryUpdateControllerTest {

@Mock private DirectoryUpdateService directoryUpdateService;

@InjectMocks
    private DirectoryUpdateController directoryUpdateController;
    private List<DirectoryUpdate> directoryUpdatesTrue;
    private List<DirectoryUpdate> directoryUpdatesFake;
    private Date date;
    private String mock;

@Before
    public void setUp(){

        date = new Date();
        mock = new String("test");
        directoryUpdatesTrue = new ArrayList<>();
        directoryUpdatesTrue.add(new DirectoryUpdate("test", date));
        directoryUpdatesTrue.add(new DirectoryUpdate("test2", date));

    }

@Test
    public void getDirectoryUpdates(){

        when(directoryUpdateService.getDirectoryUpdates()).thenReturn(directoryUpdatesTrue);
        directoryUpdatesFake = directoryUpdateController.getDirectoryUpdates(mock);
        assertTrue(directoryUpdatesFake.equals(directoryUpdatesTrue));

    }
}
