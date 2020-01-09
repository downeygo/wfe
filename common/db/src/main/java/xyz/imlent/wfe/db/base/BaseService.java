package xyz.imlent.wfe.db.base;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import xyz.imlent.wfe.core.model.R;

/**
 * @author wfee
 */
public interface BaseService<T extends BaseEntity> extends IService<T> {
    /**
     * 分页查询
     *
     * @param page
     * @param user
     * @return
     */
    R getPage(IPage<T> page, T user);
}
