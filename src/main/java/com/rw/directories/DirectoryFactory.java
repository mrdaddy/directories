package com.rw.directories;

import com.rw.directories.dto.Directory;
import lombok.SneakyThrows;

import java.sql.ResultSet;

public class DirectoryFactory<T extends Directory> {
    @SneakyThrows
    public static <T extends Directory> T getDirectory(Class<T> clazz, ResultSet rs) {
        T dir = clazz.newInstance();
        dir.setId(rs.getInt("ID"));
        dir.setUpdatedOn(rs.getTimestamp("UPDATED_ON"));
        return dir;
    }
}
