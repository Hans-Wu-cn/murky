package cn.murky.admin.system.biz.service.impl;

import cn.murky.admin.system.api.domian.bo.SysDictDataBO;
import cn.murky.admin.system.biz.domain.dto.SysI18nFromDTO;
import cn.murky.admin.system.biz.domain.vo.SysI18nVo;
import cn.murky.admin.system.biz.service.ISys18nService;
import cn.murky.admin.system.biz.service.ISysDictDataService;
import cn.murky.admin.system.biz.domain.dto.SysI18nDTO;
import cn.murky.admin.system.biz.domain.entity.SysDictData;
import cn.murky.admin.system.biz.domain.entity.SysI18n;
import cn.murky.admin.system.biz.domain.query.SysI18nPageQuery;
import cn.murky.admin.system.biz.mapper.SysI18nMapper;
import cn.murky.core.exception.ServiceException;
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

import static cn.murky.admin.system.api.constant.ErrorConstant.*;

/**
 * i18n Service
 *
 * @author hans
 */
@Component
public class SysI18nServiceImpl extends ServiceImpl<SysI18nMapper, SysI18n> implements ISys18nService {
    @Inject
    private ISysDictDataService iSysDictDataService;
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
        List<String> i18nDict = iSysDictDataService.getI18nDict().stream()
                .map(SysDictDataBO::getDictValue).toList();
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
        List<SysDictDataBO> i18nDict = iSysDictDataService.getI18nDict();
        List<String> i18nDictStrList = i18nDict.stream().map(SysDictDataBO::getDictValue).toList();
        SysI18nPageQuery sysI18nPageQuery = new SysI18nPageQuery().setSysI18nDTO(sysI18nDTO).setI18nKeys(i18nDictStrList);
        SysI18nVo info = mapper.info(sysI18nPageQuery);
        for (SysDictDataBO sysDictData : i18nDict) {
            boolean have = false;
            for (SysI18nVo.I18nInput i18nInput : info.getI18nInputs()) {
                if (i18nInput.getLanguage().equals(sysDictData.getDictValue())) {
                    have = true;
                    break;
                }
            }
            if (!have) {
                info.pushI18nInputs(sysDictData);
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
            throw new ServiceException(I18N_KEY_ALREADY);
        }
        List<SysDictDataBO> i18nDict = iSysDictDataService.getI18nDict();
        Map<String, String> i18nMap = i18nDict.stream()
                .collect(Collectors.toMap(SysDictDataBO::getDictValue,
                        SysDictDataBO::getDictValue));
        SysDictDataBO defaultI18n = i18nDict.get(0);
        List<SysI18n> sysI18ns = new ArrayList<>();
        for (SysI18nFromDTO.I18nInput i18nInput : sysI18nFromDTO.getI18nInputs()) {
            Optional.ofNullable(i18nMap.get(i18nInput.getLanguage())).orElseThrow(() -> new ServiceException(ILLEGAL_LANGUAGE));
            if (i18nInput.getI18nValue().equals(defaultI18n.getDictValue())) {
                Optional.ofNullable(i18nInput.getI18nValue()).orElseThrow(() -> new ServiceException(DEFAULT_LANGUAGE_NOT_SET));
            }
            SysI18n sysI18n = new SysI18n()
                    .setI18nTag(sysI18nFromDTO.getI18nTag())
                    .setI18nKey(sysI18nFromDTO.getI18nKey())
                    .setI18nValue(i18nInput.getI18nValue())
                    .setLanguage(i18nInput.getLanguage());
            sysI18ns.add(sysI18n);
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
        List<SysDictDataBO> i18nDict = iSysDictDataService.getI18nDict();
        Map<String, String> i18nMap = i18nDict.stream().collect(Collectors.
                toMap(SysDictDataBO::getDictValue, SysDictDataBO::getDictValue));
        SysDictDataBO defaultI18n = i18nDict.get(0);
        List<SysI18n> list = new ArrayList<>();
        for (SysI18nFromDTO.I18nInput i18nInput : sysI18nFromDTO.getI18nInputs()) {
            Optional.ofNullable(i18nMap.get(i18nInput.getLanguage())).orElseThrow(() -> new ServiceException(ILLEGAL_LANGUAGE));
            if (i18nInput.getLanguage().equals(defaultI18n.getDictValue())) {
                Optional.ofNullable(i18nInput.getI18nValue()).orElseThrow(() -> new ServiceException(DEFAULT_LANGUAGE_NOT_SET));
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
