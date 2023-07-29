package cn.poem.solon.system.domain.dto;

import cn.poem.solon.system.domain.entity.PoemRole;
import cn.poem.solon.system.domain.entity.PoemUser;
import com.mybatisflex.core.paginate.Page;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 用户分页查询类
 *
 * @author hans
 */
@Data
@Accessors(chain = true)
@ApiModel("用户分页查询类")
public class PoemUserPageDTO extends Page<PoemUser> {
}
