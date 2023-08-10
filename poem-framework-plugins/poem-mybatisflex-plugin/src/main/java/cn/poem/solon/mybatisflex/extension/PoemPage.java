package cn.poem.solon.mybatisflex.extension;

import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.paginate.Page;
import lombok.Data;
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
