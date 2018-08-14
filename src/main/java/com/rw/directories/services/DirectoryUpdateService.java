package com.rw.directories.services;

import com.rw.directories.dao.DirectoryUpdateDao;
import com.rw.directories.dto.DirectoryUpdate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class DirectoryUpdateService {
    @Autowired
    private DirectoryUpdateDao directoryUpdateDao;

    List<DirectoryUpdate> getDirectoryUpdates() {
        return directoryUpdateDao.getDirectoryUpdates();
    }
}
