package com.rw.directories.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Directory {
    @ApiModelProperty(example = "1", required = true, value = "Уникальный идентификатор записи", dataType = "int")
    protected Integer id;

    @ApiModelProperty(example = "2018-08-13T11:44:31.881+0300", required = true, value = "Дата обновления, часовой пояс Europe/Minsk", dataType = "datetime")
    protected Date updatedOn;

}
