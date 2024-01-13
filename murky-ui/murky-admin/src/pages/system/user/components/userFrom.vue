<template>
    <div>
        <t-form ref="form" colon reset-type="initial" :rules="FORM_RULES" :data="formData" @reset="onReset"
            @submit="onSubmit">
            <t-form-item :label="$t('user.label.userName')" name="userName">
                <t-input v-model="formData.userName" :placeholder="$t('user.label.pl.userName')"></t-input>
            </t-form-item>
            <t-form-item :label="$t('user.label.account')" name="account">
                <t-input v-model="formData.account" :placeholder="$t('user.label.pl.account')"></t-input>
            </t-form-item>
            <t-form-item :label="$t('user.label.sex')" name="sex">
                <t-radio-group v-model="formData.sex">
                    <t-radio v-for="(value, key) in gender" :key="key" :value="Number(key)">{{ value }}</t-radio>
                </t-radio-group>
            </t-form-item>
            <t-form-item :label="$t('user.label.email')" name="email">
                <t-input v-model="formData.email" :placeholder="$t('user.label.pl.email')"></t-input>
            </t-form-item>
            <t-form-item :label="$t('user.label.dept')" name="fkDeptId">
                <deptTreeSelect v-model:value="formData.fkDeptId"></deptTreeSelect>
            </t-form-item>
            <t-form-item :label="$t('user.label.role')" name="fkRoleIds">
                <userSelect v-model:value="formData.fkRoleIds"></userSelect>
            </t-form-item>
            <t-form-item>
                <t-space size="small">
                    <t-button theme="primary" type="submit" :loading="loading">{{ $t('common.button.submit') }}</t-button>
                    <t-button theme="default" variant="base" type="reset" :loading="loading">{{ $t('common.button.reset1')
                    }}</t-button>
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
import { User } from '@/api/system/user/types';

import { gender } from '../constants';
import deptTreeSelect from './deptTreeSelect.vue';
import userSelect from './userSelect.vue';
import i18n from '@/i18n';

const emit = defineEmits(['submit-hook'])
const FORM_RULES = ref<FormRules>({
    userName: [{ required: true, message: i18n.global.t('user.label.pl.userName'), trigger: 'blur' }],
    account: [{ required: true, message: i18n.global.t('user.label.pl.account'), trigger: 'blur' }],
    sex: [{ required: true, message: i18n.global.t('user.label.pl.sex'), trigger: 'change' }],
    email: [{ email: { ignore_max_length: true }, message: i18n.global.t('user.label.valid.email') }]
})
// 表单对象
const formData = ref<User>({
    sex: 0,
    fkDeptId: '',
    fkRoleIds: []
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
 * @param id 用户id
 */
const resetValue = ref({})// 记录重置表单数据
const initFromData = async (id: string) => {
    if (!id) {
        formData.value = {
            sex: 0,
            fkDeptId: '',
            fkRoleIds: []
        }
        userFromId.value = undefined
        return
    }
    userFromId.value = id;
    loading.value = true
    const { code, result } = await queryUserInfo(id)
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
const onSubmit = async ({ validateResult }: SubmitContext<User>) => {
    if (validateResult === true) {
        loading.value = true
        console.log(formData.value)
        const api = formData.value.id ? editUser : addUser
        const res = await api(formData.value);
        if (res.code === ResultEnum.SUCCESS) {
            MessagePlugin.success(i18n.global.t('common.message.submitSuccess'));
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