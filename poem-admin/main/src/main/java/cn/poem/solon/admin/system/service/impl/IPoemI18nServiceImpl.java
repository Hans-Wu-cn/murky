package cn.poem.solon.admin.system.service.impl;

import cn.poem.solon.admin.core.exception.ServiceException;
import cn.poem.solon.admin.system.domain.dto.PoemI18nFromDTO;
import cn.poem.solon.admin.system.domain.dto.PoemI18nDTO;
import cn.poem.solon.admin.system.domain.entity.PoemDictData;
import cn.poem.solon.admin.system.domain.entity.PoemI18n;
import cn.poem.solon.admin.system.domain.query.PoemI18nPageQuery;
import cn.poem.solon.admin.system.domain.vo.PoemI18nVo;
import cn.poem.solon.admin.system.mapper.PoemI18nMapper;
import cn.poem.solon.admin.system.service.IPoemDictDataService;
import cn.poem.solon.admin.system.service.IPoemI18nService;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.solon.service.impl.ServiceImpl;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;
import org.noear.solon.data.annotation.Tran;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * i18n Service
 *
 * @author hans
 */
@Component
public class IPoemI18nServiceImpl extends ServiceImpl<PoemI18nMapper, PoemI18n> implements IPoemI18nService {
    @Inject
    private IPoemDictDataService iPoemDictDataService;

    /**
     * 根据字典和tag数据节后的动态分页
     *
     * @param poemI18nDTO
     * @return 返回的是根据字典内容指定的Map
     */
    @Override
    public Page<Map> page(PoemI18nDTO poemI18nDTO) {
        List<String> i18nDict = iPoemDictDataService.getI18nDict().stream().map(PoemDictData::getDictValue).toList();
        PoemI18nPageQuery poemI18nPageQuery = new PoemI18nPageQuery().setPoemI18nDTO(poemI18nDTO).setI18nKeys(i18nDict);
        return mapper.page(poemI18nPageQuery);
    }

    /**
     * 根据i18nKey查询某一个国际化编码的详情信息
     * @return 返回的是根据字典内容指定的字段已经详细信息
     */
    @Override
    public PoemI18nVo info(PoemI18nDTO poemI18nDTO) {
        List<String> i18nDict = iPoemDictDataService.getI18nDict().stream().map(PoemDictData::getDictValue).toList();
        PoemI18nPageQuery poemI18nPageQuery = new PoemI18nPageQuery().setPoemI18nDTO(poemI18nDTO).setI18nKeys(i18nDict);
        return mapper.info(poemI18nPageQuery);
    }

    /**
     * 重写新增方法
     *
     * @return 保存状态
     */
    @Tran
    @Override
    public boolean save(PoemI18nFromDTO poemI18nFromDTO) {
        List<PoemDictData> i18nDict = iPoemDictDataService.getI18nDict();
        PoemDictData defaultI18n = i18nDict.get(0);
        List<PoemI18n> poemI18nList = new ArrayList<>();
        for (PoemI18nFromDTO.I18nInput i18nInput : poemI18nFromDTO.getI18nInputs()) {
            if (i18nInput.getI18n().equals(defaultI18n.getDictValue())) {
                Optional.ofNullable(i18nInput.getI18nValue()).orElseThrow(() -> new ServiceException("1"));
            }
            PoemI18n poemI18n = new PoemI18n()
                    .setI18nTag(poemI18nFromDTO.getI18nTag())
                    .setI18nKey(poemI18nFromDTO.getI18nKey())
                    .setI18nValue(i18nInput.getI18nValue())
                    .setI18n(i18nInput.getI18n());
            poemI18nList.add(poemI18n);
        }
        int i = mapper.insertBatch(poemI18nList);
        return i > 0;
    }

    /**
     * 重写修改方法
     *
     * @return 保存状态
     */
    @Tran
    @Override
    public boolean edit(PoemI18nFromDTO poemI18nFromDTO) {
        List<PoemDictData> i18nDict = iPoemDictDataService.getI18nDict();
        PoemDictData defaultI18n = i18nDict.get(0);
        int count = 0;
        for (PoemI18nFromDTO.I18nInput i18nInput : poemI18nFromDTO.getI18nInputs()) {
            if (i18nInput.getI18n().equals(defaultI18n.getDictValue())) {
                Optional.ofNullable(i18nInput.getI18nValue()).orElseThrow(() -> new ServiceException("1"));
            }
            PoemI18n poemI18n = new PoemI18n()
                    .setI18nTag(poemI18nFromDTO.getI18nTag())
                    .setI18nKey(poemI18nFromDTO.getI18nKey())
                    .setI18nValue(i18nInput.getI18nValue())
                    .setI18n(i18nInput.getI18n());
            count += mapper.updateI18nValue(poemI18n);
        }
        return count > 0;
    }

    /**
     * 重写删除方法
     * @return 保存状态
     */
    @Override
    public boolean remove(String i18nKey) {
        return mapper.deleteByI18nKey(i18nKey) > 0;
    }
}
