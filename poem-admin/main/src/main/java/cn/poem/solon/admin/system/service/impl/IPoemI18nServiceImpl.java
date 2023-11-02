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
import org.noear.snack.ONode;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;
import org.noear.solon.data.annotation.Tran;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * i18n Service
 *
 * @author hans
 */
@Component
public class IPoemI18nServiceImpl extends ServiceImpl<PoemI18nMapper, PoemI18n> implements IPoemI18nService {
    @Inject
    private IPoemDictDataService iPoemDictDataService;
    @Inject
    private IPoemI18nService iPoemI18nService;

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
     *
     * @return 返回的是根据字典内容指定的字段已经详细信息
     */
    @Override
    public PoemI18nVo info(PoemI18nDTO poemI18nDTO) {
        List<PoemDictData> i18nDict = iPoemDictDataService.getI18nDict();
        List<String> i18nDictStrList = i18nDict.stream().map(PoemDictData::getDictValue).toList();
        PoemI18nPageQuery poemI18nPageQuery = new PoemI18nPageQuery().setPoemI18nDTO(poemI18nDTO).setI18nKeys(i18nDictStrList);
        PoemI18nVo info = mapper.info(poemI18nPageQuery);
        for (PoemDictData poemDictData : i18nDict) {
            boolean have = false;
            for (PoemI18nVo.I18nInput i18nInput : info.getI18nInputs()) {
                if (i18nInput.getLanguage().equals(poemDictData.getDictValue())) {
                    have = true;
                    break;
                }
            }
            if (!have) {
                info.pushI18nInputs(poemDictData);
            }
        }
        return info;
    }

    /**
     * 重载保存方法
     *
     * @return 保存状态
     */
    @Tran
    @Override
    public boolean save(PoemI18nFromDTO poemI18nFromDTO) {
        long l = mapper.selectByKeyAndTag(poemI18nFromDTO.getI18nKey(), poemI18nFromDTO.getI18nTag());
        if (l > 0) {
            throw new ServiceException("当前i18nKey已存在");
        }
        List<PoemDictData> i18nDict = iPoemDictDataService.getI18nDict();
        Map<String, String> i18nMap = i18nDict.stream().collect(Collectors.toMap(PoemDictData::getDictValue, PoemDictData::getDictValue));
        PoemDictData defaultI18n = i18nDict.get(0);
        List<PoemI18n> poemI18nList = new ArrayList<>();
        for (PoemI18nFromDTO.I18nInput i18nInput : poemI18nFromDTO.getI18nInputs()) {
            Optional.ofNullable(i18nMap.get(i18nInput.getLanguage())).orElseThrow(() -> new ServiceException("非法的i18n语言"));
            if (i18nInput.getI18nValue().equals(defaultI18n.getDictValue())) {
                Optional.ofNullable(i18nInput.getI18nValue()).orElseThrow(() -> new ServiceException("默认语言必须设置"));
            }
            PoemI18n poemI18n = new PoemI18n()
                    .setI18nTag(poemI18nFromDTO.getI18nTag())
                    .setI18nKey(poemI18nFromDTO.getI18nKey())
                    .setI18nValue(i18nInput.getI18nValue())
                    .setLanguage(i18nInput.getLanguage());
            poemI18nList.add(poemI18n);
        }
        return iPoemI18nService.saveBatch(poemI18nList);
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
        Map<String, String> i18nMap = i18nDict.stream().collect(Collectors.toMap(PoemDictData::getDictValue, PoemDictData::getDictValue));
        PoemDictData defaultI18n = i18nDict.get(0);
        List<PoemI18n> list = new ArrayList<>();
        int count = 0;
        for (PoemI18nFromDTO.I18nInput i18nInput : poemI18nFromDTO.getI18nInputs()) {
            Optional.ofNullable(i18nMap.get(i18nInput.getLanguage())).orElseThrow(() -> new ServiceException("非法的i18n语言"));
            if (i18nInput.getLanguage().equals(defaultI18n.getDictValue())) {
                Optional.ofNullable(i18nInput.getI18nValue()).orElseThrow(() -> new ServiceException("默认语言必须设置"));
            }
//            PoemI18n poemI18nData = mapper.selectByKeyAndTagAndi18n(poemI18nFromDTO.getI18nKey(), poemI18nFromDTO.getI18nTag(), i18nInput.getI18n());
            PoemI18n poemI18n = new PoemI18n()
                    .setId(i18nInput.getId())
                    .setI18nTag(poemI18nFromDTO.getI18nTag())
                    .setI18nKey(poemI18nFromDTO.getI18nKey())
                    .setI18nValue(i18nInput.getI18nValue())
                    .setLanguage(i18nInput.getLanguage());
            list.add(poemI18n);
//            iPoemI18nService.saveOrUpdateBatch()
//            count+=mapper.updateI18nValue(poemI18nOne.getI18nKey(),poemI18n);
        }
        return iPoemI18nService.saveOrUpdateBatch(list);
    }

    /**
     * 重写删除方法
     *
     * @return 保存状态
     */
    @Override
    public boolean remove(String i18nKey) {
        return mapper.deleteByI18nKey(i18nKey) > 0;
    }

    @Override
    public Map<String,String> language(String i18nTag, String laguage) {
        List<PoemI18n> poemI18nList = mapper.selectByLanguageAndTag(i18nTag, laguage);
        ONode node = new ONode();
        for (PoemI18n poemI18n : poemI18nList) {
            node.set(poemI18n.getI18nKey(), poemI18n.getI18nValue());
        }
        return node.toObject(Map.class);
    }
}
