<template>
    <div class="inputContent">
        <t-input v-if="props.type === 'input'" v-model="value" :placeholder="props.placeholder || '请输入'"></t-input>
        <t-textarea v-else-if="props.type === 'textarea'" v-model="value"
            :placeholder="props.placeholder || '请输入'"></t-textarea>
        <t-select v-else-if="props.type === 'select'" v-model="value">
            <t-option v-for="(value, key) in radioOptions" :key="key" :value="key" :label="value" />
        </t-select>
        <t-select v-else-if="props.type === 'dict'" v-model="value">
            <t-option v-for="(item, index) in dictOptions" :key="item.dictValue" :value="item.dictValue"
                :label="item.dictLabel" />
        </t-select>
        <!-- <t-radio-group v-else-if="props.type === 'radio'" v-model="value">
            <t-radio v-for="(value,key) in radioOptions" :key="key" :value="key">{{ value }}</t-radio>
        </t-radio-group> -->
    </div>
</template>
<script setup lang="ts">
import { PoemDictData } from '@/api/dict/types';
import { useVModels } from '@vueuse/core';

const props = defineProps<{
    type: string,
    value: any,
    placeholder?: string,
    radioOptions?: { [key: string]: string }
    dictOptions: Array<PoemDictData>,// 单选项字典
}>()

const emit = defineEmits<{
    (e: 'update:value', value: string): void
}>()

const { value } = useVModels(props, emit)

</script>
<style scoped lang="less"></style>