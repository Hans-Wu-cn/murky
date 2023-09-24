package cn.poem.solon.admin.system.service.impl;

import cn.poem.solon.admin.core.exception.ServiceException;
import cn.poem.solon.admin.domin.PoemDeptAncestors;
import cn.poem.solon.admin.system.domain.convert.PoemDeptConvert;
import cn.poem.solon.admin.system.domain.dto.PoemDeptFromDTO;
import cn.poem.solon.admin.system.domain.entity.PoemDept;
import cn.poem.solon.admin.system.domain.vo.PoemDeptTreeVO;
import cn.poem.solon.admin.system.mapper.PoemDeptMapper;
import cn.poem.solon.admin.system.mapper.PoemUserMapper;
import cn.poem.solon.admin.system.service.IPoemDeptService;
import cn.poem.solon.admin.system.mapper.PoemDeptAncestorsMapper;
import cn.poem.solon.admin.utils.SecurityUtils;
import com.mybatisflex.solon.service.impl.ServiceImpl;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;
import org.noear.solon.data.annotation.Tran;

import java.util.ArrayList;
import java.util.List;

@Component
public class IPoemDeptServiceImpl extends ServiceImpl<PoemDeptMapper, PoemDept> implements IPoemDeptService {

    @Inject
    private PoemDeptAncestorsMapper poemDeptAncestorsMapper;

    @Inject
    private PoemUserMapper poemUserMapper;

    @Inject
    private IPoemDeptService iPoemDeptService;

    /**
     * 获取部门树
     * @return 部门树视图对象
     */
    @Override
    public List<PoemDeptTreeVO> treeDept() {
        List<PoemDept> allPoemDept = mapper.getSelectByCreate(SecurityUtils.getUserInfo());
        List<PoemDeptTreeVO> poemDeptTreeVOList = PoemDeptConvert.INSTANCES.toEntity(allPoemDept);
        List<PoemDeptTreeVO> list = poemDeptTreeVOList.stream().filter(item -> item.getParentDept() == 0).toList();
        buildTreePoemDept(list,poemDeptTreeVOList);
        return list;
    }

    /**
     * 保存部门
     * 1.保存部门信息
     * 2.保存部门层级关系信息
     * @param poemDeptFromDTO
     * @return
     */
    @Override
    @Tran
    public Boolean save(PoemDeptFromDTO poemDeptFromDTO)  {
        PoemDept entity = poemDeptFromDTO.toEntity();
        int insert = mapper.insert(entity);
        if(insert>0){
            List<PoemDeptAncestors> poemDeptAncestors=new ArrayList<>();
            poemDeptAncestors.add(new PoemDeptAncestors()
                    .setDeptId(entity.getDeptId())
                    .setAncestors(entity.getParentDept()));
            List<PoemDeptAncestors> list = poemDeptAncestorsMapper.getListByDeptId(entity.getParentDept());
            for (PoemDeptAncestors deptAncestors : list) {
                poemDeptAncestors.add(new PoemDeptAncestors()
                        .setDeptId(entity.getDeptId())
                        .setAncestors(deptAncestors.getAncestors()));
            }
            int i = poemDeptAncestorsMapper.insertBatch(poemDeptAncestors);
            if(i == poemDeptAncestors.size()){
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
     * @param poemDeptFromDTO 部门表单对象
     * @return 新增状态
     */
    @Override
    @Tran
    public Boolean edit(PoemDeptFromDTO poemDeptFromDTO) {
        PoemDept entity = poemDeptFromDTO.toEntity();
        int update = mapper.update(entity);
        if(update>0){
            //组装部门关系
            List<PoemDeptAncestors> poemDeptAncestors=new ArrayList<>();
            poemDeptAncestors.add(new PoemDeptAncestors()
                    .setDeptId(entity.getDeptId())
                    .setAncestors(entity.getParentDept()));
            List<PoemDeptAncestors> list = poemDeptAncestorsMapper.getListByDeptId(entity.getParentDept());
            for (PoemDeptAncestors deptAncestors : list) {
                poemDeptAncestors.add(new PoemDeptAncestors()
                        .setDeptId(entity.getDeptId())
                        .setAncestors(deptAncestors.getAncestors()));
            }
            //删除部门关系
            poemDeptAncestorsMapper.deleteByDeptId(entity.getDeptId());
            //保存部门关系
            int i = poemDeptAncestorsMapper.insertBatch(poemDeptAncestors);
            if(i == poemDeptAncestors.size()){
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
        Long countByAncestors = poemDeptAncestorsMapper.getCountByAncestors(deptId);
        if(countByAncestors>0){
            throw new ServiceException("该部门下仍有子部门无法删除");
        }
        Long countByDeptId = poemUserMapper.getCountByDeptId(deptId);
        if(countByDeptId>0){
            throw new ServiceException("该部门正在被使用,无法删除");
        }
        poemDeptAncestorsMapper.deleteByDeptId(deptId);
        mapper.deleteById(deptId);
        return true;
    }

    @Override
    @Tran
    public Boolean drop(List<Long> deptIds) {
        List<PoemDept> poemDepts=new ArrayList<>();
        for (int i = 0; i < deptIds.size(); i++) {
            Long deptId = deptIds.get(i);
            poemDepts.add(new PoemDept().setDeptId(deptId).setSort(i));
        }
        return iPoemDeptService.updateBatch(poemDepts);
    }

    /**
     * 构建部门树
     *
     * @param parentDeptList 父级部门
     * @param poemDeptList   部门资源池
     */
    private void buildTreePoemDept(List<PoemDeptTreeVO> parentDeptList, List<PoemDeptTreeVO> poemDeptList) {
        for (PoemDeptTreeVO poemDeptTreeVO : parentDeptList) {
            List<PoemDeptTreeVO> PoemDeptTree = new ArrayList<>();
            for (PoemDeptTreeVO deptTreeVO : poemDeptList) {
                if (deptTreeVO.getParentDept().equals(poemDeptTreeVO.getDeptId())) {
                    PoemDeptTree.add(deptTreeVO);
                }
            }
            buildTreePoemDept(PoemDeptTree, poemDeptList);
            poemDeptTreeVO.setChildren(PoemDeptTree);
        }
    }
}
