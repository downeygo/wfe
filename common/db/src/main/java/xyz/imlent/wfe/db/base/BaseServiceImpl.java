package xyz.imlent.wfe.db.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import xyz.imlent.wfe.core.model.R;
import xyz.imlent.wfe.log.annotation.ApiLog;

/**
 * @author wfee
 */
public class BaseServiceImpl<M extends BaseMapper<T>, T extends BaseEntity> extends ServiceImpl<M, T> implements BaseService<T> {
    @ApiLog
    @Override
    public R getPage(IPage<T> page, T user) {
        return R.success(this.page(page, Wrappers.lambdaQuery(user)));
    }
}
