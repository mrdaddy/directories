package com.rw.directories;

import com.rw.directories.dto.Directory;
import lombok.SneakyThrows;
import java.lang.reflect.Constructor;
import java.sql.ResultSet;

public class DirectoryFactory<T extends Directory> {
    @SneakyThrows
    public static <T extends Directory> T getDirectory(Class<T> clazz, ResultSet rs) {

        Class name = Class.forName( clazz.getName());
        Constructor<T> constructor = name.getDeclaredConstructor( );
        constructor.setAccessible(true);

        T dir =  constructor.newInstance();

        dir.setId(rs.getInt("ID"));
        dir.setUpdatedOn(rs.getTimestamp("UPDATED_ON"));

        return dir;
    }
}
