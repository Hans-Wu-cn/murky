<template>
    <t-select v-model="value" :placeholder="placeholder || $t('user.label.pl.role')" clearable
        :min-collapsed-num="3">
        <t-option :label="$t('common.label.allSelect')" :check-all="true" />
        <t-option v-for="item in roleData" :key="item.id" :value="item.id" :label="item.roleName"></t-option>
    </t-select>
</template>
<script setup lang="ts">
import { roleList } from '@/api/system/role';
import { Role } from '@/api/system/role/types';
import { ResultEnum } from '@/enums/httpEnum';
import { useVModels } from '@vueuse/core';
import { MessagePlugin } from 'tdesign-vue-next';
import { onMounted, ref } from 'vue';

const props = defineProps<{
    value: string[] | string | undefined,
    placeholder?: string
}>()
const emit = defineEmits<{
    (e: 'update:value', value: string[]): void
}>()
const { value } = useVModels(props, emit);
// 角色
const roleData = ref<Role[]>();
const getroleTreeData = async () => {
    const { code, result, message } = await roleList()
    if (code === ResultEnum.SUCCESS) {
        roleData.value = result
    } else {
        MessagePlugin.error(message);
    }
}
onMounted(() => {
    getroleTreeData();
})
</script>
<style scoped lang="less"></style>