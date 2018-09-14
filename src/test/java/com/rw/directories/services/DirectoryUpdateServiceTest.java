package com.rw.directories.services;

import com.rw.directories.dao.DirectoryUpdateDao;
import com.rw.directories.dto.DirectoryUpdate;
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
public class DirectoryUpdateServiceTest {

    @Mock
    private DirectoryUpdateDao directoryUpdateDao;

    @InjectMocks
    private DirectoryUpdateService directoryUpdateService;
    private List<DirectoryUpdate> directoryUpdatesTrue, directoryUpdatesFake;
    private Date date;


    @Before
    public void setUp() {

        date = new Date();
        directoryUpdatesTrue = new ArrayList<>();
        directoryUpdatesTrue.add(new DirectoryUpdate("TEST", date));
        directoryUpdatesTrue.add(new DirectoryUpdate("TEST_2", date));
    }


    @Test
    public void getDirectoryUpdates(){

        when(directoryUpdateDao.getDirectoryUpdates()).thenReturn(directoryUpdatesTrue);
        directoryUpdatesFake = directoryUpdateService.getDirectoryUpdates();
        assertTrue(directoryUpdatesFake.equals(directoryUpdatesTrue));

    }

}