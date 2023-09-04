<template>
    <t-select-input
        :value="value"
        placeholder="请选择图标"
        :style="{ width: '400px' }"
        :popup-props="{ overlayInnerStyle: { width: '400px',maxHeight:'300px','overflow-y': 'auto','overscroll-behavior': 'contain' } }"
        clearable
        allow-input
        :popup-visible="popupVisible"
        @popup-visible-change="onPopupVisibleChange"
        @clear="onClear"
        @input-change="onInputChange"
    >
        <template #panel>
            <ul class="tdesign-demo__select-input-ul-single">
                <template v-for="(item,index) in groupOptions" :key="index">
                    <div class="t-select-option-group__header">{{ item.group }}</div>
                    <template v-for="item1 in item.children" :key="item1.value">
                        <li @click="onOptionClick(item1)" v-if="item1.show"
                            :class="['t-select-option','t-size-m','overlay-options',item1.value===value?'t-is-selected':'']">
                            <div>
                                <t-icon v-if="item.group === 'TDsign icons'" :name="item1.label" />
                                <img v-else :src="item1.value" alt="icon">
                            </div>
                        </li>
                    </template>
                </template>
            </ul>
        </template>
        <template #valueDisplay="scope" v-if="value">
            <img v-if="isCustomIcon" :src="value"  :style="{ marginRight: '8px' }">
            <t-icon v-else :name="value" :style="{ marginRight: '8px' }" />
            {{ value }}
        </template>
    </t-select-input>
</template>
<script setup lang="tsx">
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
// 分组图标
const filterOptions = computed(()=>{
    return options.value.map(val=>{
        return{
            label:val.stem,
            value:val.stem,
            show:true,
            // options 自定义下拉选项关键代码
            content: ()=><t-icon name={val.stem}/>,
        }
    })
})
const filterCustom = computed(()=>{
    return customIcons.value.map(val=>{
        return{
            label:val.name,
            value:val.path,
            show:true,
            // options 自定义下拉选项关键代码
            content: ()=><img src={val.path}/>,
        }
    })
})
const groupOptions = ref([
    {
        group:'TDsign icons',
        children:filterOptions
    },{
        group:'Custom icons',
        children:filterCustom
    }
])
const popupVisible = ref(false);
const onOptionClick = (item:{label:string,value:string}) => {
  value.value = item.value;
  // 选中后立即关闭浮层
  popupVisible.value = false;
};
const onClear = () => {
  value.value = '';
};
const onPopupVisibleChange = (val: boolean) => {
//   console.log(context);
  popupVisible.value = val;
};
const onInputChange = (value: string) => {
  // 过滤功能
  popupVisible.value = false
  filterOptions.value.forEach(val=>{
    val.show = val.label.indexOf(value)!==-1
  })
  filterCustom.value.forEach(val=>{
    val.show = val.label.indexOf(value)!==-1
  })
  popupVisible.value = true
};
</script>
<style scoped lang="less">
.overlay-options{
    img{
        width: 14px;
        height: 14px;
        vertical-align: middle;
    }
}
:deep(.t-input__prefix img){
    width: 14px;
    height: 14px;
    vertical-align: middle;
}
</style>