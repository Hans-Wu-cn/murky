<template>
    <div class="menuFormBox">
        <t-card :bordered="false">
            <t-form ref="form" :rules="FORM_RULES" :data="menuFormData" :colon="true" @reset="onReset" @submit="onSubmit">
                <t-form-item label="菜单类型" name="type">
                    <t-select v-model="menuFormData.type">
                        <t-option label="目录" :value="0" />
                        <t-option label="侧边菜单" :value="1" />
                        <t-option label="按钮" :value="2" />
                    </t-select>
                </t-form-item>
                <t-form-item label="菜单标题" name="label">
                    <t-input placeholder="请输入菜单标题" v-model="menuFormData.label" />
                </t-form-item>
                <t-form-item v-if="menuFormData.type !== 2" label="菜单名" name="name">
                    <t-input placeholder="请输入菜单名" v-model="menuFormData.name" />
                </t-form-item>

                <t-form-item v-if="menuFormData.type !== 2" label="菜单图标" name="icon">
                    <t-input placeholder="请选择菜单图标" v-model="menuFormData.icon" />
                </t-form-item>
                <t-form-item v-if="menuFormData.type !== 2" label="路由地址" name="path">
                    <t-input placeholder="请输入路由地址" v-model="menuFormData.path" />
                </t-form-item>
                <t-form-item v-if="menuFormData.type === 1" label="组件路径" name="component">
                    <t-input placeholder="请输入view下组件相对路径例如: system/menu/index.vue" v-model="menuFormData.component" />
                </t-form-item>
                <t-form-item v-if="menuFormData.type !== 0" label="菜单权限" name="auth">
                    <t-input placeholder="权限码要求唯一,如果是按钮权限码建议使用父级菜单:权限 例如 menu:add" v-model="menuFormData.auth" />
                </t-form-item>
                <t-form-item v-if="menuFormData.type === 1" label="路由参数" name="query">
                    <t-input placeholder="请输入路由参数,固定的参数 例如: {'name':'hans'}" v-model="menuFormData.query" />
                </t-form-item>
                <t-form-item v-if="menuFormData.type === 1" label="打开方式" name="openType">
                    <t-radio-group v-model="menuFormData.openType">
                        <t-radio :value="1" allow-uncheck>当前窗口</t-radio>
                        <t-radio :value="2" allow-uncheck>新窗口</t-radio>
                    </t-radio-group>
                </t-form-item>
                <t-form-item v-if="menuFormData.type !== 2" label="是否显示" name="isDisplay">
                    <t-radio-group v-model="menuFormData.isDisplay">
                        <t-radio :value="0" allow-uncheck>是</t-radio>
                        <t-radio :value="1" allow-uncheck>否</t-radio>
                    </t-radio-group>
                </t-form-item>
                <t-form-item v-if="menuFormData.type !== 2" label="是否外链" name="isOutside">
                    <t-radio-group v-model="menuFormData.isOutside">
                        <t-radio :value="1" allow-uncheck>是</t-radio>
                        <t-radio :value="0" allow-uncheck>否</t-radio>
                    </t-radio-group>
                </t-form-item>
                <t-form-item v-if="menuFormData.type !== 2" label="是否缓存" name="isCache">
                    <t-switch v-model="menuFormData.isCache" :customValue="[1, 0, undefined]"></t-switch>
                </t-form-item>
                <t-form-item label="排序" name="sort">
                    <t-input-number placeholder="请输入内容" v-model="menuFormData.sort" />
                </t-form-item>
                <t-form-item>
                    <t-space size="small">
                        <t-button theme="primary" type="submit">提交</t-button>
                        <t-button theme="default" variant="base" type="reset">重置</t-button>
                    </t-space>
                </t-form-item>
            </t-form>
        </t-card>
    </div>
</template>
<script setup lang="tsx">
import { addMenu, getMenu, updateMenu } from '@/api/menu';
import { PoemMenu } from '@/api/menu/types';
import { ResultEnum } from '@/enums/httpEnum';
import { FormRules, MessagePlugin, SubmitContext } from 'tdesign-vue-next';
import { ComputedRef, computed, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
const route = useRoute();
const router = useRouter()
const menuFormData = ref<PoemMenu>({ openType: 1, isDisplay: 0, component: '', isOutside: 0, isCache: 0, sort: 0 })
const FORM_RULES = ref<FormRules>({
    name: [{ required: true, message: '请输入菜单名' }, { pattern: /^[a-zA-Z]{1,}$/, message: '只支持大小写英文字母' },],
    label: [{ required: true, message: '请输入菜单标题' }],
    type: [{ required: true, message: '请选择菜单类型' }],
    path: [{ required: true, message: '请输入路由地址' }],
});
/**
 * 重置表单
 */
const onReset = () => {
    MessagePlugin.success('重置成功');
};

/**
 * 表单提交方法
 */
const onSubmit = async ({ validateResult, firstError }: SubmitContext<PoemMenu>) => {
    if (validateResult) {
        const api = menuFormData.value.menuId ? updateMenu : addMenu
        const res = await api(menuFormData.value);
        if (res.code === ResultEnum.SUCCESS) {
            MessagePlugin.success('提交成功');
            router.go(-1);
        } else {
            MessagePlugin.error(res.message);
        }
    }
};

/**
 * 表单初始化事件
 */
const onInitFrom = async () => {
    const poemId = route.query.poemId as string
    const parentMenuId = route.query.parentMenuId as string
    if (parentMenuId) {
        menuFormData.value.parentMenuId = parentMenuId;
    }
    if (!poemId) return
    const res = await getMenu(poemId)
    menuFormData.value = res.result
}
onInitFrom()
</script>
<style></style>