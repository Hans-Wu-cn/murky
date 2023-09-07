<template>
  <div>
    <t-form ref="form" colon reset-type="initial" :rules="FORM_RULES" :data="formData" @reset="onReset"
      @submit="onSubmit">
      <t-form-item label="角色名" name="roleName">
        <t-input v-model="formData.roleName" placeholder="请输入角色名"></t-input>
      </t-form-item>
      <t-form-item label="权限码" name="roleCode">
        <t-input v-model="formData.roleCode" placeholder="请输入角色权限码"></t-input>
      </t-form-item>
      <t-form-item label="描述" name="describe">
        <t-textarea v-model="formData.describe" placeholder="请输入描述内容"></t-textarea>
      </t-form-item>
      <t-form-item label="数据范围">
        <t-select v-model="formData.dataScope">
          <t-option v-for="item in dataScopeDict" :key="item.value" :label="item.label" :value="item.value" />
        </t-select>
      </t-form-item>
      <!-- <t-form-item label="所属菜单">
        <t-tree-select
          v-model="formData.menuIds"
          :data="menuOptions"
          clearable
          placeholder="请选择"
        />
      </t-form-item> -->
      <t-form-item label="描述" name="describe">
        <t-textarea v-model="formData.describe" placeholder="请输入描述内容"></t-textarea>
      </t-form-item>
      <t-form-item label="数据权限">
        <t-tree hover expand-all :data="deptTree" :keys="deptTreeKeys" checkable value-mode="all"
          @change="treeOnChange" />
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
import { onMounted, reactive, ref } from 'vue'
import { PoemRole } from '@/api/role/types'
import { FormRules, MessagePlugin, SelectValue, SelectValueChangeTrigger, SubmitContext, TreeNodeModel, TreeNodeValue, } from 'tdesign-vue-next';
import { addPoemRole, updatePoemRole } from '@/api/role';
import { ResultEnum } from '@/enums/httpEnum';
import { dataScopeDict } from '../constants';
import { Dict } from '@/enums';
import { getDeptList } from '@/api/dept';
import { PoemDeptTree } from '@/api/dept/types';

const emit = defineEmits(['submit-hook'])
const deptTree = ref<Array<PoemDeptTree>>();
const deptTreeKeys = { value: 'deptId', label: 'deptName', children: 'children' }
const FORM_RULES = ref<FormRules>({
  roleName: [{ required: true, message: '请输入角色名', trigger: 'blur' }],
  roleCode: [{ required: true, message: '请输入角色权限码', trigger: 'blur' }],
})
// 表单对象
const formData = reactive<PoemRole>({
  roleCode: '',
  roleName: '',
  describe: '',
  dataScope: 0
});

/**
 * 重置表单
 */
const onReset = () => {
  MessagePlugin.success('重置成功');
};

const treeOnChange = (value: Array<TreeNodeValue>, context: { node: TreeNodeModel<PoemDeptTree>; e?: any; trigger: 'node-click' | 'setItem' }) => {
  console.log(value)
  console.log(context)
}

/**
 * 表单提交事件
 * @param param0 表单验证
 */
const onSubmit = async ({ validateResult }: SubmitContext<PoemRole>) => {
  if (validateResult === true) {
    const api = formData.roleId ? updatePoemRole : addPoemRole
    const res = await api(formData);
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
  formData
})



</script>
<style lang="less"></style>