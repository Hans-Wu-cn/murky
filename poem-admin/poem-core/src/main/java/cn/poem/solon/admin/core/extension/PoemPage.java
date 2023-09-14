package cn.poem.solon.admin.core.extension;

import com.mybatisflex.core.paginate.Page;
import lombok.experimental.Accessors;

//@Data
@Accessors(chain = true)
public abstract class PoemPage<T> {
    /*页数*/
    protected Integer page;

    /*每页数量*/
    protected Integer pageSize;

    public Page<T> toFlexPage(){
        return new Page<T>(page,pageSize);
    }
}
