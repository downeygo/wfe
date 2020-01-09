package xyz.imlent.wfe.api.user.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Tolerate;
import xyz.imlent.wfe.db.base.BaseEntity;

/**
 * @author wfee
 */
@Data
@Builder
@TableName("oauth_user_details")
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer isDeleted;
    private String username;
    private String password;
    private Integer accountLocked;
    private Integer accountExpired;

    @Tolerate
    public User() {
    }
}
