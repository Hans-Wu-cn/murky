package cn.poem.solon.system.service.impl;

import cn.poem.solon.system.domain.convert.PoemDeptConvert;
import cn.poem.solon.system.domain.entity.PoemDept;
import cn.poem.solon.system.domain.vo.PoemDeptTreeVO;
import cn.poem.solon.system.mapper.PoemDeptMapper;
import cn.poem.solon.system.service.IPoemDeptService;
import cn.poem.solon.expand.SystemSecurityCache;
import cn.poem.solon.utils.SecurityUtils;
import com.mybatisflex.solon.service.impl.ServiceImpl;
import org.noear.solon.annotation.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class IPoemDeptServiceImpl extends ServiceImpl<PoemDeptMapper, PoemDept> implements IPoemDeptService {

    /**
     * 获取部门树
     * @return 部门树视图对象
     */
    @Override
    public List<PoemDeptTreeVO> treeDept() {
        List<PoemDept> allPoemDept = mapper.getSelectByCreate(SecurityUtils.getUserId());
        List<PoemDeptTreeVO> poemDeptTreeVOList = PoemDeptConvert.INSTANCES.toEntity(allPoemDept);
        List<PoemDeptTreeVO> list = poemDeptTreeVOList.stream().filter(item -> item.getParentDept() == 0).toList();
        buildTreePoemMenu(list,poemDeptTreeVOList);
        return list;
    }

    /**
     * 构建部门树
     *
     * @param parentDeptList 父级部门
     * @param poemDeptList   部门资源池
     */
    private void buildTreePoemMenu(List<PoemDeptTreeVO> parentDeptList, List<PoemDeptTreeVO> poemDeptList) {
        for (PoemDeptTreeVO poemDeptTreeVO : parentDeptList) {
            List<PoemDeptTreeVO> PoemDeptTree = new ArrayList<>();
            for (PoemDeptTreeVO deptTreeVO : poemDeptList) {
                if (deptTreeVO.getParentDept().equals(poemDeptTreeVO.getDeptId())) {
                    PoemDeptTree.add(deptTreeVO);
                }
            }
            buildTreePoemMenu(PoemDeptTree, poemDeptList);
            poemDeptTreeVO.setChildren(PoemDeptTree);
        }
    }
}
