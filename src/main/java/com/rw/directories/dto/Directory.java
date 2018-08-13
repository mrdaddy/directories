package com.rw.directories.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
public abstract class Directory {
    @ApiModelProperty(example = "2018-08-13T11:44:31.881+0300", required = true, value = "Дата обновления, часовой пояс Europe/Minsk", dataType = "datetime")
    private Date updatedOn;

}
