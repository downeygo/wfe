package xyz.imlent.wfe.api.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("oauth_role_details")
@EqualsAndHashCode(callSuper = true)
public class Role extends BaseEntity{
    @TableId(value = "id", type = IdType.AUTO)
    private String id;
    private String name;
    private String description;
    private String expression;

    @Tolerate
    public Role() {
    }
}
