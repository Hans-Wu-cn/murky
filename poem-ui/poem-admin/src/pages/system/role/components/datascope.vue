<template>
  <div>
    <t-form ref="form" colon reset-type="initial" :rules="FORM_RULES" :data="formData" @reset="onReset"
      @submit="onSubmit">
      <t-form-item label="角色名" name="roleName">
        <t-input disabled v-model="formData.roleName" placeholder="请输入角色名"></t-input>
      </t-form-item>
      <t-form-item label="权限码" name="roleCode">
        <t-input disabled v-model="formData.roleCode" placeholder="请输入角色权限码"></t-input>
      </t-form-item>
      <t-form-item label="数据范围">
        <t-select v-model="formData.dataScope">
          <t-option v-for="item in dataScopeDict" :key="item.value" :label="item.label" :value="item.value" />
        </t-select>
      </t-form-item>
      <t-form-item v-if="formData.dataScope === 1" label="数据权限" name="menuIds">
        <div class="treeBox">
          <t-tree hover expand-all v-model="formData.menuIds" :data="deptTree" :keys="deptTreeKeys" checkable
            value-mode="all" @change="treeOnChange" />
        </div>
      </t-form-item>
      <t-form-item>
        <t-space size="small">
          <t-button theme="primary" type="submit">提交</t-button>
          <t-button theme="default" variant="base" type="reset">重置</t-button>
        </t-space>
      </t-form-item>
    </t-form>
  </div>
</template>
<script setup lang="tsx">
import { onMounted, ref } from 'vue'
import { PoemRole } from '@/api/system/role/types'
import { FormRules, MessagePlugin, SubmitContext, TreeNodeModel, TreeNodeValue, } from 'tdesign-vue-next';
import { addPoemRole, updatePoemRole, roleInfo } from '@/api/system/role';
import { ResultEnum } from '@/enums/httpEnum';
import { PoemDeptTree } from '@/api/system/dept/types';
import { getDeptList } from '@/api/system/dept';
import { dataScopeDict } from '../constants'

const emit = defineEmits(['submit-hook'])
const deptTree = ref<Array<PoemDeptTree>>();
const deptTreeKeys = { value: 'deptId', label: 'deptName', children: 'children' }
const FORM_RULES = ref<FormRules>({
  roleName: [{ required: true, message: '请输入角色名', trigger: 'blur' }],
  roleCode: [{ required: true, message: '请输入角色权限码', trigger: 'blur' }],
})
// 表单对象
const formData = ref<PoemRole>({
  roleCode: '',
  roleName: '',
  describe: '',
  dataScope: 0,
  deptIds: []
});

const roleFromId = ref('');

/**
 * 重置表单
 */
const onReset = () => {
  if (roleFromId) {
    initFromData(roleFromId.value)
  }
};

/**
 * 树组件勾选事件
 * @param value 
 * @param context 
 */
const treeOnChange = (value: Array<TreeNodeValue>, context: { node: TreeNodeModel<PoemDeptTree>; e?: any; trigger: 'node-click' | 'setItem' }) => {
  const menuIds = Array<string>();
  value.forEach(item => menuIds.push(item as string))
  formData.value.menuIds = menuIds
}

/**
 * 初始化表单
 * @param roleId 角色id
 */
const initFromData = async (roleId: string) => {
  if (!roleId) {
    formData.value = {
      roleCode: '',
      roleName: '',
      describe: '',
      dataScope: 0,
      menuIds: []
    }
    roleFromId.value = undefined
    return
  }
  roleFromId.value = roleId;
  const { code, result } = await roleInfo(roleId)
  if (ResultEnum.SUCCESS === code) {
    formData.value = result
  }
}

/**
 * 表单提交事件
 * @param param0 表单验证
 */
const onSubmit = async ({ validateResult }: SubmitContext<PoemRole>) => {
  if (validateResult === true) {
    const api = formData.value.roleId ? updatePoemRole : addPoemRole
    const res = await api(formData.value);
    if (res.code === ResultEnum.SUCCESS) {
      MessagePlugin.success('提交成功');
      emit('submit-hook');
    } else {
      MessagePlugin.error(res.message);
    }
  }
};

onMounted(async () => {
  const { code, result } = await getDeptList();
  if (code === ResultEnum.SUCCESS) {
    deptTree.value = result
  }
});

defineExpose({
  initFromData
})



</script>
<style lang="less">
.treeBox {
  border: 1px solid #ddd;
  overflow: auto;
  height: 300px;
  width: 100%;
}
</style>