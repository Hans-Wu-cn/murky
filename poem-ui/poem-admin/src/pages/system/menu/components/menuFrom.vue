<template>
    <div class="menuFormBox">
        <t-card :bordered="false">
            <t-loading :loading="loading">
                <t-form ref="form" :rules="FORM_RULES" :data="menuFormData" :colon="true" @submit="onSubmit">
                    <t-form-item :label="$t('menu.label.type')" name="type">
                        <t-select v-model="menuFormData.type">
                            <t-option :label="$t('menu.label.type.0')" :value="0" />
                            <t-option :label="$t('menu.label.type.1')" :value="1" />
                            <t-option :label="$t('menu.label.type.2')" :value="2" />
                        </t-select>
                    </t-form-item>
                    <t-form-item :label="$t('menu.label.title')" name="label">
                        <t-input :placeholder="$t('menu.label.pl.title')" v-model="menuFormData.label" />
                    </t-form-item>
                    <t-form-item v-if="menuFormData.type !== 2" :label="$t('menu.label.name')" name="name">
                        <t-input :placeholder="$t('menu.label.pl.name')" v-model="menuFormData.name" />
                    </t-form-item>
                    <t-form-item v-if="menuFormData.type !== 2" :label="$t('menu.label.icon')" name="icon">
                        <!-- <t-input placeholder="请选择菜单图标" v-model="menuFormData.icon" /> -->
                        <!-- 图标选择组件 -->
                        <IconSelect v-model:value="menuFormData.icon"></IconSelect>
                    </t-form-item>
                    <t-form-item v-if="menuFormData.type !== 2" :label="$t('menu.label.router')" name="path">
                        <t-input :placeholder="$t('menu.label.pl.router')" v-model="menuFormData.path" />
                    </t-form-item>
                    <t-form-item v-if="menuFormData.type === 1" :label="$t('menu.label.compoment')" name="component">
                        <t-input :placeholder="$t('menu.label.pl.compoment')" v-model="menuFormData.component" />
                    </t-form-item>
                    <t-form-item v-if="menuFormData.type !== 0" :label="$t('menu.label.from.auth')" name="auth">
                        <t-input :placeholder="$t('menu.label.from.pl.auth')" v-model="menuFormData.auth" />
                    </t-form-item>
                    <t-form-item v-if="menuFormData.type === 1" :label="$t('menu.label.query')" name="query">
                        <t-input :placeholder="$t('menu.label.pl.query')" v-model="menuFormData.query" />
                    </t-form-item>
                    <t-form-item v-if="menuFormData.type === 1" :label="$t('menu.label.title.openType')" name="openType">
                        <t-radio-group v-model="menuFormData.openType">
                            <t-radio :value="1" allow-uncheck>{{ $t('menu.label.title.openType.1') }}</t-radio>
                            <t-radio :value="2" allow-uncheck>{{ $t('menu.label.title.openType.2') }}</t-radio>
                        </t-radio-group>
                    </t-form-item>
                    <t-form-item v-if="menuFormData.type !== 2" :label="$t('menu.label.isDisplay')" name="isDisplay">
                        <t-radio-group v-model="menuFormData.isDisplay">
                            <t-radio :value="0" allow-uncheck>{{ $t('menu.label.isDisplay.0') }}</t-radio>
                            <t-radio :value="1" allow-uncheck>{{ $t('menu.label.isDisplay.1') }}</t-radio>
                        </t-radio-group>
                    </t-form-item>
                    <t-form-item v-if="menuFormData.type !== 2" :label="$t('menu.label.isOutside')" name="isOutside">
                        <t-radio-group v-model="menuFormData.isOutside">
                            <t-radio :value="1" allow-uncheck>{{ $t('menu.label.isOutside.1') }}</t-radio>
                            <t-radio :value="0" allow-uncheck>{{ $t('menu.label.isOutside.0') }}</t-radio>
                        </t-radio-group>
                    </t-form-item>
                    <t-form-item v-if="menuFormData.type !== 2" :label="$t('menu.label.isCache')" name="isCache">
                        <t-switch v-model="menuFormData.isCache" :customValue="[1, 0, undefined]"></t-switch>
                    </t-form-item>
                    <t-form-item :label="$t('common.attribute.sort')" name="sort">
                        <t-input-number :placeholder="$t('common.attribute.pl.sort')" v-model="menuFormData.sort" />
                    </t-form-item>
                    <t-form-item>
                        <t-space size="small">
                            <t-button theme="primary" type="submit">{{ $t('common.button.submit') }}</t-button>
                            <t-button theme="default" variant="base" @click="onReset">{{ $t('common.button.reset1')
                            }}</t-button>
                        </t-space>
                    </t-form-item>
                </t-form>
            </t-loading>

        </t-card>
    </div>
</template>
<script setup lang="tsx">
import { addMenu, getMenu, updateMenu } from '@/api/system/menu';
import { PoemMenu } from '@/api/system/menu/types';
import { ResultEnum } from '@/enums/httpEnum';
import { FormRules, MessagePlugin, SubmitContext } from 'tdesign-vue-next';
import { ComputedRef, computed, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import IconSelect from '@/components/iconSelect/index.vue'
import i18n from '@/i18n';
const route = useRoute();
const router = useRouter()
const props = defineProps<{
    poemId?: string,
    parentMenuId?: string
}>()
const emit = defineEmits<{
    (e: 'submit'): void
}>()
const menuFormData = ref<PoemMenu>({ openType: 1, isDisplay: 0, component: '', isOutside: 0, isCache: 0, sort: 0, icon: '' })
const FORM_RULES = ref<FormRules>({
    // name: [{ required: true, message: '请输入菜单名' }, { pattern: /^[a-zA-Z]{1,}$/, message: '只支持大小写英文字母' },],
    name: [{ required: true, message: i18n.global.t('menu.label.pl.name') }],
    label: [{ required: true, message: i18n.global.t('menu.label.pl.title') }],
    type: [{ required: true, message: i18n.global.t('menu.label.pl.type') }],
    path: [{ required: true, message: i18n.global.t('menu.label.pl.router') }],
});
/**
 * 重置表单
 */
const onReset = () => {
    if (poemId.value) {
        menuFormData.value = historyPoemMenu.value
    } else {
        menuFormData.value = { openType: 1, isDisplay: 0, component: '', isOutside: 0, isCache: 0, sort: 0, icon: '' }
    }
    MessagePlugin.success(i18n.global.t('common.button.reset.success'));
};

/**
 * 表单提交方法
 */
const onSubmit = async ({ validateResult, firstError }: SubmitContext<PoemMenu>) => {
    if (validateResult === true) {
        const api = menuFormData.value.menuId ? updateMenu : addMenu
        menuFormData.value.icon = menuFormData.value.icon || ''
        const res = await api(menuFormData.value);
        if (res.code === ResultEnum.SUCCESS) {
            MessagePlugin.success(i18n.global.t('common.message.submitSuccess'));
            emit('submit');
            // router.go(-1);
        } else {
            MessagePlugin.error(res.message);
        }
    }
};

/**
 * 表单初始化事件
 */
const poemId = ref(route.query.poemId as string || props.poemId);
const historyPoemMenu = ref({})
const loading = ref(false);
const onInitFrom = async () => {
    const parentMenuId = route.query.parentMenuId as string || props.parentMenuId
    if (parentMenuId) {
        menuFormData.value.parentMenuId = parentMenuId;
    }
    if (!poemId.value) return
    loading.value = true;
    const res = await getMenu(poemId.value)
    loading.value = false;
    menuFormData.value = res.result
    historyPoemMenu.value = JSON.parse(JSON.stringify(res.result))
}
onInitFrom()


</script>
<style>
.overlay-options {
    display: inline-block;
}
</style>