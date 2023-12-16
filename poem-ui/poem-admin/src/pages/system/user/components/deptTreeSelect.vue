<template>
    <t-tree-select v-model="value" :data="deptTree" :tree-props="deptTreeKeys" clearable filterable
        :placeholder="placeholder || $t('user.label.pl.dept')" />
</template>
<script setup lang="ts">
import { getDeptList } from '@/api/system/dept';
import { DeptTree } from '@/api/system/dept/types';
import { ResultEnum } from '@/enums/httpEnum';
import { useVModels } from '@vueuse/core';
import { onMounted, ref } from 'vue';

const props = defineProps<{
    value: string | undefined,
    placeholder?: string
}>()
const emit = defineEmits<{
    (e: 'update:value', value: string): void
}>()
const { value } = useVModels(props, emit);
//部门数据
const deptTree = ref<DeptTree[]>();
const deptTreeKeys = { keys: { value: 'deptId', label: 'deptName', children: 'children' } }
const getdeptTreeData = async () => {
    const { code, result } = await getDeptList();
    if (ResultEnum.SUCCESS === code) {
        deptTree.value = result
    }
}
onMounted(() => {
    getdeptTreeData();
})
</script>
<style scoped lang="less"></style>