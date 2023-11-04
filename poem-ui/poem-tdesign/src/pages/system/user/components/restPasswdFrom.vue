<template>
    <div>
        <t-form ref="form" colon reset-type="initial" :rules="FORM_RULES" :data="formData" @submit="onSubmit">
            <t-form-item label="密码" name="password">
                <t-input v-model="formData.password" type="password" placeholder="请输入密码"></t-input>
            </t-form-item>
            <t-form-item label="确认密码" name="confirmPassword">
                <t-input v-model="formData.confirmPassword" type="password" placeholder="请重复输入密码"></t-input>
            </t-form-item>

            <t-form-item>
                <t-space size="small">
                    <t-button theme="primary" type="submit" :loading="loading">提交</t-button>
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
import { PoemUser, RestPassword } from '@/api/system/user/types';

const emit = defineEmits(['submit-hook'])
const FORM_RULES = ref<FormRules>({
    password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
    confirmPassword: [{ required: true, message: '请确认密码', trigger: 'blur' }],
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
const onSubmit = async ({ validateResult }: SubmitContext<PoemUser>) => {
    if (validateResult === true) {
        loading.value = true
        console.log(formData.value)
        const res = await restPassword(formData.value);
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