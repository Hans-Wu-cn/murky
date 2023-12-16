<template>
    <div>
        <t-form ref="form" colon reset-type="initial" :rules="FORM_RULES" :data="formData" @submit="onSubmit"
            labelWidth="width-content">
            <t-form-item :label="$t('user.label.password')" name="password">
                <t-input v-model="formData.password" type="password" :placeholder="$t('user.label.pl.password')"></t-input>
            </t-form-item>
            <t-form-item :label="$t('user.label.confirmPassword')" name="confirmPassword">
                <t-input v-model="formData.confirmPassword" type="password"
                    :placeholder="$t('user.label.pl.confirmPassword')"></t-input>
            </t-form-item>

            <t-form-item>
                <t-space size="small">
                    <t-button theme="primary" type="submit" :loading="loading">{{ $t('common.button.submit') }}</t-button>
                </t-space>
            </t-form-item>
        </t-form>
    </div>
</template>
<script setup lang="tsx">
import { ref } from 'vue'
import { FormRules, MessagePlugin, SubmitContext, TreeNodeModel, TreeNodeValue, } from 'tdesign-vue-next';
import { restPassword } from '@/api/system/user';
import { ResultEnum } from '@/enums/httpEnum';
import { User, RestPassword } from '@/api/system/user/types';
import i18n from '@/i18n';

const emit = defineEmits(['submit-hook'])
const FORM_RULES = ref<FormRules>({
    password: [{ required: true, message: i18n.global.t('user.label.pl.password'), trigger: 'blur' }],
    confirmPassword: [{ required: true, message: i18n.global.t('user.label.pl.confirmPassword'), trigger: 'blur' }],
})
// 表单对象
const formData = ref<RestPassword>({
    userId: '',
    password: '',
    confirmPassword: '',
});

const loading = ref(false);

/**
 * 初始化表单事件
 * @param userId 
 */
const initFromData = (userId: string) => {
    formData.value.userId = userId
}

/**
 * 表单提交事件
 * @param param0 表单验证
 */
const onSubmit = async ({ validateResult }: SubmitContext<User>) => {
    if (validateResult === true) {
        loading.value = true
        console.log(formData.value)
        const res = await restPassword(formData.value);
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