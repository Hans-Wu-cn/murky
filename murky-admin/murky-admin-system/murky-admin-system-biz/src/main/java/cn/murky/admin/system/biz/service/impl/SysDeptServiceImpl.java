package cn.murky.admin.system.biz.service.impl;

import cn.murky.admin.system.biz.domain.entity.SysDeptAncestors;
import cn.murky.security.utils.SecurityUtils;
import cn.murky.admin.system.biz.domain.dto.SysDeptFromDTO;
import cn.murky.admin.system.biz.domain.vo.SysDeptTreeVO;
import cn.murky.admin.system.biz.service.ISysDeptService;
import cn.murky.admin.system.biz.domain.convert.SysDeptConvert;
import cn.murky.admin.system.biz.domain.entity.SysDept;
import cn.murky.admin.system.biz.mapper.SysDeptMapper;
import cn.murky.admin.system.biz.mapper.SysUserMapper;
import cn.murky.admin.system.biz.mapper.SysDeptAncestorsMapper;
import cn.murky.core.exception.ServiceException;
import com.mybatisflex.solon.service.impl.ServiceImpl;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;
import org.noear.solon.data.annotation.Tran;

import java.util.ArrayList;
import java.util.List;

@Component
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements ISysDeptService {

    @Inject
    private SysDeptAncestorsMapper SysDeptAncestorsMapper;

    @Inject
    private SysUserMapper sysUserMapper;

    @Inject
    private ISysDeptService iSysDeptService;

    /**
     * 获取部门树
     * @return 部门树视图对象
     */
    @Override
    public List<SysDeptTreeVO> treeDept() {
        List<SysDept> allSysDept = mapper.getSelectByCreate(SecurityUtils.getUserInfo());
        List<SysDeptTreeVO> SysDeptTreeVOList = SysDeptConvert.INSTANCES.toVOs(allSysDept);
        long minPid = SysDeptTreeVOList.stream().mapToLong(SysDeptTreeVO::getParentId).min().getAsLong();
        List<SysDeptTreeVO> list = SysDeptTreeVOList.stream().filter(item -> item.getParentId() == minPid).toList();
        buildTreeSysDept(list,SysDeptTreeVOList);
        return list;
    }

    /**
     * 保存部门
     * 1.保存部门信息
     * 2.保存部门层级关系信息
     * @param sysDeptFromDTO
     * @return
     */
    @Override
    @Tran
    public Boolean save(SysDeptFromDTO sysDeptFromDTO)  {
        SysDept entity = sysDeptFromDTO.toEntity();
        int insert = mapper.insert(entity);
        if(insert>0){
            List<SysDeptAncestors> SysDeptAncestors=new ArrayList<>();
            SysDeptAncestors.add(new SysDeptAncestors()
                    .setFkDeptId(entity.getId())
                    .setAncestors(entity.getParentId()));
            List<SysDeptAncestors> list = SysDeptAncestorsMapper.getListByDeptId(entity.getParentId());
            for (SysDeptAncestors deptAncestors : list) {
                SysDeptAncestors.add(new SysDeptAncestors()
                        .setFkDeptId(entity.getId())
                        .setAncestors(deptAncestors.getAncestors()));
            }
            int i = SysDeptAncestorsMapper.insertBatch(SysDeptAncestors);
            if(i == SysDeptAncestors.size()){
                return true;
            }
            throw new ServiceException("新增部门失败");
        }
        return false;
    }


    /**
     * 修改部门
     * 1.修改部门信息
     * 2.删除部门层级关系信息
     * 3.保存部门层级关系信息
     * @param sysDeptFromDTO 部门表单对象
     * @return 新增状态
     */
    @Override
    @Tran
    public Boolean edit(SysDeptFromDTO sysDeptFromDTO) {
        SysDept entity = sysDeptFromDTO.toEntity();
        int update = mapper.update(entity);
        if(update>0){
            //组装部门关系
            List<SysDeptAncestors> SysDeptAncestors=new ArrayList<>();
            SysDeptAncestors.add(new SysDeptAncestors()
                    .setFkDeptId(entity.getId())
                    .setAncestors(entity.getParentId()));
            List<SysDeptAncestors> list = SysDeptAncestorsMapper.getListByDeptId(entity.getParentId());
            for (SysDeptAncestors deptAncestors : list) {
                SysDeptAncestors.add(new SysDeptAncestors()
                        .setFkDeptId(entity.getId())
                        .setAncestors(deptAncestors.getAncestors()));
            }
            //删除部门关系
            SysDeptAncestorsMapper.deleteByDeptId(entity.getId());
            //保存部门关系
            int i = SysDeptAncestorsMapper.insertBatch(SysDeptAncestors);
            if(i == SysDeptAncestors.size()){
                return true;
            }
            throw new ServiceException("修改部门失败");
        }
        return false;
    }

    /**
     * 删除部门
     * @param deptId 部门id
     * @return 删除状态
     */
    @Override
    @Tran
    public Boolean remove(Long deptId) {
        Long countByAncestors = SysDeptAncestorsMapper.getCountByAncestors(deptId);
        if(countByAncestors>0){
            throw new ServiceException("该部门下仍有子部门无法删除");
        }
        Long countByDeptId = sysUserMapper.getCountByDeptId(deptId);
        if(countByDeptId>0){
            throw new ServiceException("该部门正在被使用,无法删除");
        }
        SysDeptAncestorsMapper.deleteByDeptId(deptId);
        mapper.deleteById(deptId);
        return true;
    }

    @Override
    @Tran
    public Boolean drop(List<Long> deptIds) {
        List<SysDept> SysDepts=new ArrayList<>();
        for (int i = 0; i < deptIds.size(); i++) {
            Long deptId = deptIds.get(i);
            SysDepts.add(new SysDept().setId(deptId).setSort(i));
        }
        return iSysDeptService.updateBatch(SysDepts);
    }

    /**
     * 构建部门树
     *
     * @param parentDeptList 父级部门
     * @param SysDeptList   部门资源池
     */
    private void buildTreeSysDept(List<SysDeptTreeVO> parentDeptList, List<SysDeptTreeVO> SysDeptList) {
        for (SysDeptTreeVO SysDeptTreeVO : parentDeptList) {
            List<SysDeptTreeVO> SysDeptTree = new ArrayList<>();
            for (SysDeptTreeVO deptTreeVO : SysDeptList) {
                if (deptTreeVO.getParentId().equals(SysDeptTreeVO.getId())) {
                    SysDeptTree.add(deptTreeVO);
                }
            }
            buildTreeSysDept(SysDeptTree, SysDeptList);
            SysDeptTreeVO.setChildren(SysDeptTree);
        }
    }
}
