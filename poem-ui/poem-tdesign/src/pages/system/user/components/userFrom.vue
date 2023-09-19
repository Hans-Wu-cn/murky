<template>
    <div>
        <t-form ref="form" colon reset-type="initial" :rules="FORM_RULES" :data="formData" @reset="onReset"
            @submit="onSubmit">
            <t-form-item label="用户名" name="userName">
                <t-input v-model="formData.userName" placeholder="请输入用户名"></t-input>
            </t-form-item>
            <t-form-item label="账号" name="account">
                <t-input v-model="formData.account" placeholder="请输入账号"></t-input>
            </t-form-item>
            <t-form-item label="密码" name="password">
                <t-input v-model="formData.password" type="password" placeholder="请输入密码"></t-input>
            </t-form-item>
            <t-form-item label="性别" name="sex">
                <t-radio-group v-model="formData.sex">
                    <t-radio :value="0">男</t-radio>
                    <t-radio :value="1">女</t-radio>
                    <t-radio :value="2">其他</t-radio>
                </t-radio-group>
            </t-form-item>
            <t-form-item label="邮箱" name="email">
                <t-input v-model="formData.email" placeholder="请输入邮箱"></t-input>
            </t-form-item>
            <t-form-item label="部门" name="deptId">
                <t-tree-select v-model="formData.deptId" :data="deptTree" :tree-props="deptTreeKeys" clearable filterable
                    placeholder="请选择" />
            </t-form-item>
            <t-form-item label="角色" name="roleIds">
                <t-select v-model="formData.roleIds" placeholder="请选择角色" multiple clearable :min-collapsed-num="3">
                    <t-option label="全选" :check-all="true" />
                    <t-option v-for="item in roleData" :key="item.roleId" :value="item.roleId"
                        :label="item.roleName"></t-option>
                </t-select>
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
import { FormRules, MessagePlugin, SubmitContext, TreeNodeModel, TreeNodeValue, } from 'tdesign-vue-next';
import { addUser, queryUserInfo } from '@/api/user';
import { ResultEnum } from '@/enums/httpEnum';
import { PoemUser } from '@/api/user/types';
import { getDeptList } from '@/api/dept';
import { PoemDeptTree } from '@/api/dept/types';
import { PoemRole } from '@/api/role/types';
import { roleList } from '@/api/role';

const emit = defineEmits(['submit-hook'])
const FORM_RULES = ref<FormRules>({
    userName: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
    account: [{ required: true, message: '请输入账号', trigger: 'blur' }],
    password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
    sex: [{ required: true, message: '请输入性别', trigger: 'change' }],
    email: [{ email: { ignore_max_length: true }, message: '请输入正确的邮箱地址' }]
})
// 表单对象
const formData = ref<PoemUser>({
    sex: 0
});

const roleFromId = ref('');
const loading = ref(false);
/**
 * 重置表单
 */
const onReset = () => {
    if (roleFromId.value) {
        initFromData(roleFromId.value)
    }

};

/**
 * 初始化表单
 * @param roleId 角色id
 */
const resetValue = ref({})// 记录重置表单数据
const initFromData = async (roleId: string) => {
    if (!roleId) {
        formData.value = {}
        roleFromId.value = undefined
        return
    }
    roleFromId.value = roleId;
    loading.value = true
    const { code, result } = await queryUserInfo(roleId)
    if (ResultEnum.SUCCESS === code) {
        formData.value = result
        resetValue.value = JSON.parse(JSON.stringify(result))
    }
    loading.value = false
}

/**
 * 表单提交事件
 * @param param0 表单验证
 */
const onSubmit = async ({ validateResult }: SubmitContext<PoemUser>) => {
    if (validateResult === true) {
        loading.value = true
        console.log(formData.value)
        const res = await addUser(formData.value);
        if (res.code === ResultEnum.SUCCESS) {
            MessagePlugin.success('提交成功');
            emit('submit-hook');
        } else {
            MessagePlugin.error(res.message);
        }
        loading.value = false
    }
};
//部门数据
const deptTree = ref<PoemDeptTree[]>();
const deptTreeKeys = { keys: { value: 'deptId', label: 'deptName', children: 'children' } }
const getdeptTreeData = async () => {
    const { code, result } = await getDeptList();
    if (ResultEnum.SUCCESS === code) {
        deptTree.value = result
    }
}

// 角色
const roleData = ref<PoemRole[]>();
const getroleTreeData = async () => {
    const { code, result, message } = await roleList()
    if (code === ResultEnum.SUCCESS) {
        roleData.value = result
    } else {
        MessagePlugin.error(message);
    }
}
onMounted(() => {
    getdeptTreeData();
    getroleTreeData();
})
defineExpose({
    initFromData
})



</script>
<style lang="less"></style>