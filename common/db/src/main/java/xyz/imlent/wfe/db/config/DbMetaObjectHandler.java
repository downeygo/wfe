package xyz.imlent.wfe.db.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;

/**
 * @author wfee
 */
public class DbMetaObjectHandler implements MetaObjectHandler {
    private static final String ADD_TIME = "addTime";
    private static final String EDIT_TIME = "editTime";
    private static final String ADD_USER = "addUser";
    private static final String EDIT_USER = "editUser";
    private static final String IS_DELETED = "isDeleted";

    @Override
    public void insertFill(MetaObject metaObject) {
        Object addTime = getFieldValByName(ADD_TIME, metaObject);
        Object addUser = getFieldValByName(ADD_USER, metaObject);
        Object editTime = getFieldValByName(EDIT_TIME, metaObject);
        Object editUser = getFieldValByName(EDIT_USER, metaObject);
        Object isDeleted = getFieldValByName(IS_DELETED, metaObject);
        if (addTime == null) {
            setFieldValByName(ADD_TIME, LocalDateTime.now(), metaObject);
        }
        addTime = getFieldValByName(ADD_TIME, metaObject);
        if (editTime == null) {
            setFieldValByName(EDIT_TIME, LocalDateTime.now(), metaObject);
        }
        if (addUser == null) {
            setFieldValByName(ADD_USER, getUsername(), metaObject);
        }
        addUser = getFieldValByName(ADD_USER, metaObject);
        if (editUser == null) {
            setFieldValByName(EDIT_USER, getUsername(), metaObject);
        }
        if (isDeleted == null) {
            setFieldValByName(IS_DELETED, 0, metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Object editTime = getFieldValByName(EDIT_TIME, metaObject);
        Object editUser = getFieldValByName(EDIT_USER, metaObject);
        if (editTime == null) {
            setFieldValByName(EDIT_TIME, LocalDateTime.now(), metaObject);
        }
        if (editUser == null) {
            setFieldValByName(EDIT_USER, getUsername(), metaObject);
        }
    }

    private static String getUsername() {
        return "root";
    }
}
