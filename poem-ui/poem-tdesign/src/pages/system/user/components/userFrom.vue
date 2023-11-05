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
            <t-form-item label="性别" name="sex">
                <t-radio-group v-model="formData.sex">
                    <t-radio v-for="(value, key) in gender" :key="key" :value="key">{{ value }}</t-radio>
                </t-radio-group>
            </t-form-item>
            <t-form-item label="邮箱" name="email">
                <t-input v-model="formData.email" placeholder="请输入邮箱"></t-input>
            </t-form-item>
            <t-form-item label="部门" name="deptId">
                <deptTreeSelect v-model:value="formData.deptId"></deptTreeSelect>
            </t-form-item>
            <t-form-item label="角色" name="roleIds">
                <userSelect v-model:value="formData.roleIds"></userSelect>
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
import { ref } from 'vue'
import { FormRules, MessagePlugin, SubmitContext, TreeNodeModel, TreeNodeValue, } from 'tdesign-vue-next';
import { addUser, editUser, queryUserInfo } from '@/api/system/user';
import { ResultEnum } from '@/enums/httpEnum';
import { PoemUser } from '@/api/system/user/types';

import { gender } from '../constants';
import deptTreeSelect from './deptTreeSelect.vue';
import userSelect from './userSelect.vue';

const emit = defineEmits(['submit-hook'])
const FORM_RULES = ref<FormRules>({
    userName: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
    account: [{ required: true, message: '请输入账号', trigger: 'blur' }],
    sex: [{ required: true, message: '请输入性别', trigger: 'change' }],
    email: [{ email: { ignore_max_length: true }, message: '请输入正确的邮箱地址' }]
})
// 表单对象
const formData = ref<PoemUser>({
    sex: 0,
    deptId: '',
    roleIds: []
});

const userFromId = ref('');
const loading = ref(false);
/**
 * 重置表单
 */
const onReset = () => {
    if (userFromId.value) {
        initFromData(userFromId.value)
    }

};

/**
 * 初始化表单
 * @param userId 用户id
 */
const resetValue = ref({})// 记录重置表单数据
const initFromData = async (userId: string) => {
    if (!userId) {
        formData.value = {}
        userFromId.value = undefined
        return
    }
    userFromId.value = userId;
    loading.value = true
    const { code, result } = await queryUserInfo(userId)
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
        const api = formData.value.userId ? editUser : addUser
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
defineExpose({
    initFromData
})



</script>
<style lang="less"></style>