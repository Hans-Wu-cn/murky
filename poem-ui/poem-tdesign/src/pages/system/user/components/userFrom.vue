<template>
    <div>
        <t-form ref="form" colon reset-type="initial" :rules="FORM_RULES" :data="formData" @reset="onReset"
            @submit="onSubmit">
            <t-form-item label="用户名" name="userName">
                <t-input v-model="formData.userName" placeholder="请输入角色名"></t-input>
            </t-form-item>
            <t-form-item label="账号" name="account">
                <t-input v-model="formData.account" placeholder="请输入角色权限码"></t-input>
            </t-form-item>
            <t-form-item label="密码" name="password">
                <t-textarea v-model="formData.password" placeholder="请输入描述内容"></t-textarea>
            </t-form-item>
            <t-form-item label="性别" name="sex">
                <t-radio-group v-model="formData.sex">
                    <t-radio value="0">男</t-radio>
                    <t-radio value="1">女</t-radio>
                    <t-radio value="2">其他</t-radio>
                </t-radio-group>
            </t-form-item>
            <t-form-item label="邮箱" name="email">
                <t-input v-model="formData.email" placeholder="请输入角色权限码"></t-input>
            </t-form-item>
            <t-form-item>
                <t-space size="small">
                    <t-button theme="primary" type="submit" :loading="loading">提交</t-button>
                    <t-button theme="default" variant="base" type="reset" :loading="loading">重置</t-button>
                </t-space>
            </t-form-item>
        </t-form>
    </div>
</template>
<script setup lang="tsx">
import { onMounted, ref } from 'vue'
import { PoemRole } from '@/api/role/types'
import { FormRules, MessagePlugin, SubmitContext, TreeNodeModel, TreeNodeValue, } from 'tdesign-vue-next';
import { addPoemRole, updatePoemRole, roleInfo } from '@/api/role';
import { ResultEnum } from '@/enums/httpEnum';
import { PoemMenu } from '@/api/menu/types';
import { getMenuList } from '@/api/menu';

const emit = defineEmits(['submit-hook'])
const menuTree = ref<Array<PoemMenu>>();
const deptTreeKeys = { value: 'menuId', label: 'label', children: 'children' }
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
    menuIds: []
});

const roleFromId = ref('');
const loading = ref(false);
/**
 * 重置表单
 */
const onReset = () => {
    loading.value = true
    if (roleFromId) {
        formData.value = resetValue.value as PoemRole
    }
    loading.value = false

};

/**
 * 树组件勾选事件
 * @param value 
 * @param context 
 */
const treeOnChange = (value: Array<TreeNodeValue>, context: { node: TreeNodeModel<PoemMenu>; e?: any; trigger: 'node-click' | 'setItem' }) => {
    const menuIds = Array<string>();
    value.forEach(item => menuIds.push(item as string))
    formData.value.menuIds = menuIds
}

/**
 * 初始化表单
 * @param roleId 角色id
 */
const resetValue = ref({})// 记录重置表单数据
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
        resetValue.value = JSON.parse(JSON.stringify(result))
    }
}

/**
 * 表单提交事件
 * @param param0 表单验证
 */
const onSubmit = async ({ validateResult }: SubmitContext<PoemRole>) => {
    if (validateResult === true) {
        loading.value = true
        const api = formData.value.roleId ? updatePoemRole : addPoemRole
        const res = await api(formData.value);
        if (res.code === ResultEnum.SUCCESS) {
            MessagePlugin.success('提交成功');
            emit('submit-hook');
        } else {
            MessagePlugin.error(res.message);
        }
        loading.value = false
    }
};

onMounted(async () => {
    const { code, result } = await getMenuList();
    if (code === ResultEnum.SUCCESS) {
        menuTree.value = result
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