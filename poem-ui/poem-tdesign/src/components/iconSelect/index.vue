<template>
    <t-select
        v-model="value"
        placeholder="请选择图标"
        :style="{ width: '400px' }"
        :popup-props="{ overlayInnerStyle: { width: '400px' } }"
        clearable
        :filter="filterIcons"
        >
        <t-option-group label="TDsign icons">
            <t-option v-for="item in options" :key="item.stem" :value="item.stem" class="overlay-options">
                <div>
                    <t-icon :name="item.stem" />
                </div>
            </t-option>
        </t-option-group>
        <t-option-group label="Custom icons">
            <t-option v-for="item in customIcons" :key="item.name" :value="item.path" class="overlay-options">
                <div>
                    <img :src="item.path" alt="icon">
                </div>
            </t-option>
        </t-option-group>
        <template #valueDisplay="scope" v-if="value">
            <img v-if="isCustomIcon" :src="value"  :style="{ marginRight: '8px' }">
            <t-icon v-else :name="value" :style="{ marginRight: '8px' }" />
            {{ value }}
        </template>
    </t-select>
</template>
<script setup lang="ts">
import { manifest } from 'tdesign-icons-vue-next';
import { computed, ref } from 'vue';
import {useVModels} from '@vueuse/core'
import { getIconNames } from './getIconName';
//默认的svg文件存储位置
import { iconConfig } from './config';
import { Icon } from './type';

const props = withDefaults(defineProps<{
    value:string
}>(),{
    value:''
})
const emit = defineEmits<{
    (e:'update:value',value:string):void
}>()
const {value} = useVModels(props,emit)
// 获取TDsign全部图标的列表
const options = ref(manifest);
// 获取自定义图标
const iconNames = getIconNames()
const customIcons = ref<Icon[]>(iconNames);
// 计算定义图标还是TDsign图标
const isCustomIcon = computed(()=>value.value && value.value.indexOf(iconConfig.svgPath)!==-1 )
// 过滤图标
const filterIcons = (filterWords: string, option: any)=>{
    return option.key?option.key.indexOf(filterWords)!==-1:true
}
</script>
<style scoped lang="less">
.overlay-options{
    img{
        width: 14px;
        height: 14px;
        vertical-align: middle;
    }
}
:deep(.t-select img){
    width: 14px;
    height: 14px;
    vertical-align: middle;
}
</style>