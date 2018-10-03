package com.rw.directories.services;

import com.rw.directories.dao.EripFacilitiesDao;
import com.rw.directories.dto.EripFacilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import java.util.List;

@Service
@Validated
public class EripFacilitiesService {

    @Autowired
    private EripFacilitiesDao eripFacilitiesDao;

    public List<EripFacilities> getEripFacilities() {
        return eripFacilitiesDao.getEripFacilities();
    }

}
