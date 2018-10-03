package com.rw.directories.controllers;


import com.rw.directories.dto.DirectoryUpdate;
import com.rw.directories.services.DirectoryUpdateService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Timestamp;
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
    private Timestamp timestamp;
    private String mock;

@Before
    public void setUp(){

        timestamp = Timestamp.valueOf("2018-08-25 11:38:38.7640300");
        mock = new String("test");
        directoryUpdatesTrue = new ArrayList<>();
        directoryUpdatesTrue.add(new DirectoryUpdate(DirectoryUpdate.DIRECTORY.valueOf("Parameters"), timestamp));
        directoryUpdatesTrue.add(new DirectoryUpdate(DirectoryUpdate.DIRECTORY.valueOf("Countries"), timestamp));

    }

@Test
    public void getDirectoryUpdates(){

        when(directoryUpdateService.getDirectoryUpdates()).thenReturn(directoryUpdatesTrue);
        directoryUpdatesFake = directoryUpdateController.getDirectoryUpdates(mock);
        assertTrue(directoryUpdatesFake.equals(directoryUpdatesTrue));

    }
}
