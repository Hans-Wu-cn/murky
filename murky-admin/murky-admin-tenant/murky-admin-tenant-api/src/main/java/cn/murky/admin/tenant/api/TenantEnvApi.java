package cn.murky.admin.tenant.api;

import cn.murky.admin.system.api.domian.bo.SysDictBO;

import java.util.List;

public interface TenantEnvApi {
    void refreshDict(List<SysDictBO> sysDictBos);

}
