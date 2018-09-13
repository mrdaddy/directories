package com.rw.directories.services;

import com.rw.directories.dao.DirectoryUpdateDao;
import com.rw.directories.dto.DirectoryUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DirectoryUpdateService {
    @Autowired
    private DirectoryUpdateDao directoryUpdateDao;

    public List<DirectoryUpdate> getDirectoryUpdates() {
        List<DirectoryUpdate >directory = directoryUpdateDao.getDirectoryUpdates();
        return  directory;
    }
}
