<template>
    <div class="searchParams">
        <t-form colon reset-type="initial" :layout="'inline'" @reset="onReset" @submit="onSubmit">
            <t-form-item v-for="item in props.options" :label="item.label" :name="item.name" v-bind="item">
                <InputContent v-model:value="item.value" :type="item.type" :radio-options="item.radioOptions"
                    :placeholder="item.placeholder" :dictOptions="item.dictOptions">
                </InputContent>
            </t-form-item>
            <t-button theme="primary" type="submit">{{ $t('common.button.query') }}</t-button>
            <t-button theme="primary" type="reset">{{ $t('common.button.reset') }}</t-button>
        </t-form>
    </div>
</template>
<script setup lang="ts">
import { SubmitContext } from 'tdesign-vue-next';
import InputContent from './components/inputContent.vue';
import { PoemDictData } from '@/api/system/dict/types';
import { ComputedRef, onMounted, watch } from 'vue';
import { dictFunction } from '@/hooks/dict'
export interface SearchOption {
    name: string,
    label: string | ComputedRef<string> | any,
    value?: string,
    type: string,// 输入框类型
    placeholder?: string | ComputedRef<string> | any,
    radioOptions?: { [key: string]: string },// 单选项搜索
    dictOptions?: Array<PoemDictData>,// 单选项字典
    dictType?: string,// 单选项字典类型
    labelWidth?: string | number
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

const initDictOptions = () => {
    console.log(11111)
    props.options.forEach(async item => {
        console.log(item)
        if (item.type === 'dict' && item.dictType) {
            const dict = await dictFunction(item.dictType);
            console.log('dict', dict)
            if (dict) {
                item.dictOptions = dict
                item.value = dict[0].dictValue
            }
        }
    })
}

watch(() => props.options, function (value, oldvalue) {
    initDictOptions()
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
    initDictOptions()
    emit('reset');
}

onMounted(() => {
    initDictOptions()
})


</script>
<style scoped lang="less">
.searchParams {
    background: var(--td-bg-color-container);
    border-radius: var(--td-radius-medium);
    padding: 20px 0;
    margin-bottom: 10px;
}
</style>