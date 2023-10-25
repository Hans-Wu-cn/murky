<template>
    <div class="searchParams">
        <t-form colon reset-type="initial" :layout="'inline'" @reset="onReset" @submit="onSubmit">
            <t-form-item v-for="item in props.options" :label="item.label" :name="item.name">
                <InputContent v-model:value="item.value" :type="item.type" :radio-options="item.radioOptions"
                    :placeholder="item.placeholder" :dictOptions="item.dictOptions">
                </InputContent>
            </t-form-item>
            <t-button theme="primary" type="submit">查 询</t-button>
            <t-button theme="primary" type="reset">重 置</t-button>
        </t-form>
    </div>
</template>
<script setup lang="ts">
import { SubmitContext } from 'tdesign-vue-next';
import InputContent from './components/inputContent.vue';
import { PoemDictData } from '@/api/dict/types';

export interface SearchOption {
    name: string,
    label: string,
    value: string,
    type: string,// 输入框类型
    placeholder?: string,
    radioOptions?: { [key: string]: string },// 单选项搜索
    dictOptions?: Array<PoemDictData>,// 单选项字典
}

const props = withDefaults(defineProps<{
    options?: SearchOption[]
}>(), {
    options: () => [{
        name: '',
        value: '',
        label: '',
        type: 'input',
        placeholder: '请输入'
    }],
})

const emit = defineEmits<{
    (e: 'submit', value: any): void;
    (e: 'reset'): void;
}>()

/**
 * 表单提交事件
 * @param param0 表单验证
 */
const onSubmit = async ({ validateResult }: SubmitContext) => {
    if (validateResult === true) {
        const params: { [key: string]: any } = {}
        props.options.forEach(val => {
            params[val.name] = val.value
        })
        emit('submit', params);
    }
};

/**
 * 表单重置事件
 */
const onReset = () => {
    props.options.forEach(val => {
        val.value = ''
    })
    emit('reset');
}
</script>
<style scoped lang="less">
.searchParams {
    background: #fff;
    border-radius: var(--td-radius-medium);
    padding: 20px 0;
    margin-bottom: 10px;
}
</style>