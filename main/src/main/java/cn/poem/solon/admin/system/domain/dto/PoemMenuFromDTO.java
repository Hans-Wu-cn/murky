package cn.poem.solon.admin.system.domain.dto;


import cn.poem.core.validat.Update;
import cn.poem.solon.admin.system.domain.entity.PoemMenu;
import cn.poem.solon.admin.system.enums.MenuType;
import cn.poem.solon.admin.system.enums.OpenType;
import io.github.linpeilie.Converter;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.noear.solon.Solon;
import org.noear.solon.validation.annotation.NotBlank;
import org.noear.solon.validation.annotation.NotNull;

@Data
@Accessors(chain = true)
@ApiModel("菜单实体对象")
@AutoMapper(target = PoemMenu.class)
public class PoemMenuFromDTO{
    @ApiModelProperty("菜单id")
    @NotNull(groups = Update.class)
    private Long menuId;

    @ApiModelProperty("菜单标题")
    @NotBlank
    private String label;

    @ApiModelProperty("菜单副标题")
    private String subtitle;

    @ApiModelProperty("路径")
    private String path;

    @ApiModelProperty("打开方式 1:当前窗口  2:新窗口")
//    @NotNull
    private OpenType openType;

    @ApiModelProperty("权限码")
    private String auth;

    @ApiModelProperty("上级菜单id")
    private Long parentMenuId;

    @ApiModelProperty("菜单类型 1:侧边菜单 2:按钮")
    @NotNull
    private MenuType type;

    @ApiModelProperty("排序")
    private Short order;

    @ApiModelProperty("组件路径")
    private String compoment;

    @ApiModelProperty("图标")
    private String icon;

    public PoemMenu toEntity(){
        Converter bean = Solon.context().getBean(Converter.class);
        return bean.convert(this,PoemMenu.class);
    }
}
