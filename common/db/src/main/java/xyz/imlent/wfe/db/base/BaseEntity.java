package xyz.imlent.wfe.db.base;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;
import xyz.imlent.wfe.core.util.DateUtil;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author wfee
 */
@Data
public class BaseEntity implements Serializable {

    /**
     * 添加人
     */
    @TableField(value = "add_user", fill = FieldFill.INSERT)
    private String addUser;

    /**
     * 添加日期
     */
    @JsonFormat(pattern = DateUtil.PATTERN_DATETIME)
    @DateTimeFormat(pattern = DateUtil.PATTERN_DATETIME)
    @TableField(value = "add_time", fill = FieldFill.INSERT)
    private LocalDateTime addTime;

    /**
     * 编辑人
     */
    @TableField(value = "edit_user", fill = FieldFill.INSERT_UPDATE)
    private String editUser;

    /**
     * 编辑日期
     */
    @JsonFormat(pattern = DateUtil.PATTERN_DATETIME)
    @DateTimeFormat(pattern = DateUtil.PATTERN_DATETIME)
    @TableField(value = "edit_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime editTime;

    @TableLogic
    @TableField(value = "is_deleted", fill = FieldFill.INSERT)
    private Integer isDeleted;
}
