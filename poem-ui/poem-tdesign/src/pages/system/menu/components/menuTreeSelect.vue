<template>
    <t-tree-select v-model="value" :data="menuOptions" clearable placeholder="请选择" />
</template>
<script setup lang="tsx">
import { getMenuList } from '@/api/menu';
import { useVModels } from '@vueuse/core'
import { ref } from 'vue';
const props = defineProps<{
    value: string[]
}>()
const emits = defineEmits<{
    (e: 'update:value', value: string[]): void
}>()
const { value } = useVModels(props, emits)
const menuOptions = ref([])
const getMenuOptions = async () => {
    const res = await getMenuList();

}
</script>
<style lang="less" scoped></style>