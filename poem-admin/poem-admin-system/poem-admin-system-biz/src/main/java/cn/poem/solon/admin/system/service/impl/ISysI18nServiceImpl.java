package cn.poem.solon.admin.system.service.impl;

import cn.poem.solon.admin.system.domain.dto.SysI18nDTO;
import cn.poem.solon.admin.system.domain.dto.SysI18nFromDTO;
import cn.poem.solon.admin.system.domain.entity.SysDictData;
import cn.poem.solon.admin.system.domain.entity.SysI18n;
import cn.poem.solon.admin.system.domain.query.SysI18nPageQuery;
import cn.poem.solon.admin.system.domain.vo.SysI18nVo;
import cn.poem.solon.admin.system.mapper.SysI18nMapper;
import cn.poem.solon.admin.system.service.ISysDictDataService;
import cn.poem.solon.admin.system.service.ISys18nService;
import cn.poem.solon.exception.ServiceException;
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
public class ISysI18nServiceImpl extends ServiceImpl<SysI18nMapper, SysI18n> implements ISys18nService {
    @Inject
    private ISysDictDataService iPoemDictDataService;
    @Inject
    private ISys18nService iSys18nService;

    /**
     * 根据字典和tag数据节后的动态分页
     *
     * @param sysI18nDTO
     * @return 返回的是根据字典内容指定的Map
     */
    @Override
    public Page<Map> page(SysI18nDTO sysI18nDTO) {
        List<String> i18nDict = iPoemDictDataService.getI18nDict().stream().map(SysDictData::getDictValue).toList();
        SysI18nPageQuery sysI18nPageQuery = new SysI18nPageQuery().setSysI18nDTO(sysI18nDTO).setI18nKeys(i18nDict);
        return mapper.page(sysI18nPageQuery);
    }

    /**
     * 根据i18nKey查询某一个国际化编码的详情信息
     *
     * @return 返回的是根据字典内容指定的字段已经详细信息
     */
    @Override
    public SysI18nVo info(SysI18nDTO sysI18nDTO) {
        List<SysDictData> i18nDict = iPoemDictDataService.getI18nDict();
        List<String> i18nDictStrList = i18nDict.stream().map(SysDictData::getDictValue).toList();
        SysI18nPageQuery sysI18nPageQuery = new SysI18nPageQuery().setSysI18nDTO(sysI18nDTO).setI18nKeys(i18nDictStrList);
        SysI18nVo info = mapper.info(sysI18nPageQuery);
        for (SysDictData poemDictData : i18nDict) {
            boolean have = false;
            for (SysI18nVo.I18nInput i18nInput : info.getI18nInputs()) {
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
    public boolean save(SysI18nFromDTO sysI18nFromDTO) {
        long l = mapper.selectByKeyAndTag(sysI18nFromDTO.getI18nKey(), sysI18nFromDTO.getI18nTag());
        if (l > 0) {
            throw new ServiceException("当前i18nKey已存在");
        }
        List<SysDictData> i18nDict = iPoemDictDataService.getI18nDict();
        Map<String, String> i18nMap = i18nDict.stream().collect(Collectors.toMap(SysDictData::getDictValue, SysDictData::getDictValue));
        SysDictData defaultI18n = i18nDict.get(0);
        List<SysI18n> sysI18ns = new ArrayList<>();
        for (SysI18nFromDTO.I18nInput i18nInput : sysI18nFromDTO.getI18nInputs()) {
            Optional.ofNullable(i18nMap.get(i18nInput.getLanguage())).orElseThrow(() -> new ServiceException("非法的i18n语言"));
            if (i18nInput.getI18nValue().equals(defaultI18n.getDictValue())) {
                Optional.ofNullable(i18nInput.getI18nValue()).orElseThrow(() -> new ServiceException("默认语言必须设置"));
            }
            SysI18n poemI18n = new SysI18n()
                    .setI18nTag(sysI18nFromDTO.getI18nTag())
                    .setI18nKey(sysI18nFromDTO.getI18nKey())
                    .setI18nValue(i18nInput.getI18nValue())
                    .setLanguage(i18nInput.getLanguage());
            sysI18ns.add(poemI18n);
        }
        return iSys18nService.saveBatch(sysI18ns);
    }

    /**
     * 重写修改方法
     *
     * @return 保存状态
     */
    @Tran
    @Override
    public boolean edit(SysI18nFromDTO sysI18nFromDTO) {
        List<SysDictData> i18nDict = iPoemDictDataService.getI18nDict();
        Map<String, String> i18nMap = i18nDict.stream().collect(Collectors.toMap(SysDictData::getDictValue, SysDictData::getDictValue));
        SysDictData defaultI18n = i18nDict.get(0);
        List<SysI18n> list = new ArrayList<>();
        for (SysI18nFromDTO.I18nInput i18nInput : sysI18nFromDTO.getI18nInputs()) {
            Optional.ofNullable(i18nMap.get(i18nInput.getLanguage())).orElseThrow(() -> new ServiceException("非法的i18n语言"));
            if (i18nInput.getLanguage().equals(defaultI18n.getDictValue())) {
                Optional.ofNullable(i18nInput.getI18nValue()).orElseThrow(() -> new ServiceException("默认语言必须设置"));
            }
            SysI18n sysI18n = new SysI18n()
                    .setId(i18nInput.getId())
                    .setI18nTag(sysI18nFromDTO.getI18nTag())
                    .setI18nKey(sysI18nFromDTO.getI18nKey())
                    .setI18nValue(i18nInput.getI18nValue())
                    .setLanguage(i18nInput.getLanguage());
            list.add(sysI18n);
        }
        return iSys18nService.saveOrUpdateBatch(list);
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
        List<SysI18n> sysI18ns = mapper.selectByLanguageAndTag(i18nTag, laguage);
        ONode node = new ONode();
        for (SysI18n sysI18n : sysI18ns) {
            node.set(sysI18n.getI18nKey(), sysI18n.getI18nValue());
        }
        return node.toObject(Map.class);
    }
}
