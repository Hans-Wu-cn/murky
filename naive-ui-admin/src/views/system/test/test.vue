<template>
    <n-popover placement="bottom-start" :show="showPopover" width="trigger">
        <template #trigger>
            <n-input readonly v-model:value="iconName" @focus="showPopover = true" style="width: 500px;">
                <template #prefix>
                    <svg-icon :name="iconName" style="height: 30px;width: 16px;" />
                </template>
            </n-input>
        </template>
        <IconSelect @inputIn="inputIn"  @selected="selected"></IconSelect>
    </n-popover>
</template>
  
<script lang="ts" setup>
import { ref } from 'vue'
import { IconSelect } from '@/components/IconSelect'


const iconName = ref('')
const showPopover = ref(false)

// 选择完成拿到选择的图标名字iconName,并且隐藏popover
function selected(data) {
    iconName.value = data
    showPopover.value = false
}

//输入图标名字时 Popover显示
function inputIn() {
    showPopover.value = true
}

</script>
  
<style lang="less" scoped></style>
  