//package cn.poem.solon.admin.system.domain.entity;
//
//import cn.poem.solon.admin.domin.BaseEntity;
//import cn.poem.solon.admin.system.enums.Sex;
//import com.mybatisflex.annotation.Id;
//import com.mybatisflex.annotation.Table;
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
//import lombok.Data;
//import lombok.experimental.Accessors;
//
///**
// * 用户实体类
// *
// * @author hans
// */
//@Data
//@Accessors(chain = true)
//@ApiModel("用户实体类")
//@Table("poem_user")
//public class PoemUser extends BaseEntity {
//
//    @ApiModelProperty("用户id")
//    @Id
//    private Long userId;
//
//    @ApiModelProperty("用户名")
//    private String userName;
//
//    @ApiModelProperty("账号")
//    private String account;
//
//    @ApiModelProperty("密码")
//    private String password;
//
//    @ApiModelProperty("性别 0:男性 1:女性 2:其他")
//    private Sex sex;
//
//    @ApiModelProperty("邮箱")
//    private String email;
//
//    @ApiModelProperty("部门id")
//    private Long deptId;
//}
