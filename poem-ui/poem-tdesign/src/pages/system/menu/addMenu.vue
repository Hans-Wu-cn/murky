<template>
    <div class="menuFormBox">
        <t-card :bordered="false">
            <t-form ref="form" :rules="FORM_RULES" :data="menuFormData" :colon="true" @reset="onReset" @submit="onSubmit">
                <t-form-item label="菜单标题" name="label">
                    <t-input placeholder="请输入内容" v-model="menuFormData.label"/>
                </t-form-item>
                <t-form-item label="菜单名(唯一)" name="name">
                    <t-input placeholder="请输入内容" v-model="menuFormData.name"/>
                </t-form-item>
                <t-form-item label="菜单类型" name="type">
                    <t-select v-model="menuFormData.type">
                        <t-option label="目录" :value="0" />
                        <t-option label="侧边菜单" :value="1" />
                        <t-option label="按钮" :value="2" />
                    </t-select>
                </t-form-item>
                <t-form-item label="菜单图标" name="tel">
                    <t-input placeholder="请输入内容" v-model="menuFormData.icon"/>
                </t-form-item>
                <t-form-item label="路由地址" name="path">
                    <t-input placeholder="请输入内容" v-model="menuFormData.path"/>
                </t-form-item>
                <t-form-item label="组件路径" name="tel">
                    <t-input placeholder="请输入内容" v-model="menuFormData.component"/>
                </t-form-item>
                <t-form-item label="打开方式" name="tel">
                    <t-select v-model="menuFormData.openType">
                        <t-option label="当前窗口" :value="1" />
                        <t-option label="新窗口" :value="2" />
                    </t-select>
                </t-form-item>
                <t-form-item label="菜单权限" name="tel">
                    <t-input placeholder="请输入内容" v-model="menuFormData.auth"/>
                </t-form-item>
                <t-form-item label="路由参数" name="tel">
                    <t-input placeholder="请输入内容" v-model="menuFormData.query"/>
                </t-form-item>
                <t-form-item label="是否显示" name="tel">
                    <t-radio-group v-model="menuFormData.isDisplay">
                        <t-radio :value="0" allow-uncheck>是</t-radio>
                        <t-radio :value="1" allow-uncheck>否</t-radio>
                    </t-radio-group>
                </t-form-item>
                <t-form-item label="是否外链" name="tel">
                    <t-radio-group v-model="menuFormData.isOutside">
                        <t-radio :value="1" allow-uncheck>是</t-radio>
                        <t-radio :value="0" allow-uncheck>否</t-radio>
                    </t-radio-group>
                </t-form-item>
                <t-form-item label="是否缓存" name="tel">
                    <t-switch v-model="menuFormData.isCache" :customValue="[1,0,undefined]"></t-switch>
                </t-form-item>
                <t-form-item label="排序" name="tel">
                    <t-input-number placeholder="请输入内容" v-model="menuFormData.sort"/>
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
import { FormRules, MessagePlugin, SubmitContext } from 'tdesign-vue-next';
import { ComputedRef, computed, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
const route = useRoute();
const router = useRouter()
const menuFormData = ref<PoemMenu>({isDisplay:1,component:''})
const typeValidate = computed(()=>menuFormData.value.type !== 2)
const FORM_RULES = ref<FormRules>({
    name: [{ required: true, message: '菜单名必填' },{ pattern: /^[a-zA-Z]{1,}$/, message: '只支持大小写英文字母' },],
    label: [{ required: true, message: '菜单标题必填' }],
    type: [{ required: true, message: '菜单类型必填' }],
    path: [{ required: !!typeValidate, message: '路由地址必填' }],
});
const onReset = () => {
  MessagePlugin.success('重置成功');
};
// 更新菜单接口调用
const updateMenuFun = async (menuData: PoemMenu) => {
    return await updateMenu(menuData)
}
//提交方法
const onSubmit = async({ validateResult, firstError }: SubmitContext<PoemMenu>) => {
  if (validateResult === true) {
    const api = menuFormData.value.menuId?updateMenuFun:addMenu
    const res = await api(menuFormData.value);
    if(res.code === 200){
        MessagePlugin.success('提交成功');
        router.go(-1);
    }else{
        MessagePlugin.warning(res.message);
    }
  } else {
    console.log('Validate Errors: ', firstError, validateResult);
    MessagePlugin.warning(firstError);
  }
};
/**
 * 查询菜单详情回显字段
 * 
 */
const queryMenuDetail = async()=>{
    const poemId = route.query.poemId as string
    if(!poemId)return
    const res = await getMenu(poemId)
    menuFormData.value = res.result
}
queryMenuDetail()
</script>
<style>

</style>