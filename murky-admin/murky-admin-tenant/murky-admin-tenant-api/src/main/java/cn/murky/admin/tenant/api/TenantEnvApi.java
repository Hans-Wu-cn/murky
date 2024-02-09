package cn.murky.admin.tenant.api;

import cn.murky.common.domain.bo.SysDictBO;

import java.util.List;

public interface TenantEnvApi {
    void refreshDict(List<SysDictBO> sysDictBos);

}
