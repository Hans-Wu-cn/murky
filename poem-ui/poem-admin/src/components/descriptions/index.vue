<template>
    <div :class="shadow ? 'descriptions' : ''" class="desc">
        <h3 v-if="title" class="title">{{ title }}</h3>
        <t-row class="descBox">
            <template v-for="item in desc" :key="item">
                <t-col :span="item.column || 8" v-if="!item.disShow">
                    <p class="descLabel">{{ item.label }}</p>
                    <template v-if="!item.slotName">
                        <p class="descContent" v-if="item.descHtml" v-html="formatter(item)"></p>
                        <p class="descContent" v-else>{{ formatter(item) }}</p>
                    </template>
                    <p v-else :class="['descContent', 'slot-' + item.slotName]">
                        <slot :name="item.slotName" :scope="item.value" :item="item" :getValue="formatter(item)"
                            v-if="item.slotName"></slot>
                    </p>
                </t-col>
            </template>
        </t-row>
        <div class="floatBtn">
            <slot></slot>
        </div>
    </div>
</template>
<script setup lang="ts">
import { ComputedRef, Ref } from 'vue';
/**
 * title 标题
 * column 列数
 * desc 数据    label 名  value 值  slotName 插值名称  column flex位数
 */
export interface DescItem {
    label: String|ComputedRef<string>,
    value: String|Ref<string>,
    column?: number,
    formatter?: Function;
    slotName?: String,
    descHtml?: boolean,
    disShow?: boolean,
    [key: string]: any
}

const { title, desc, shadow } = withDefaults(defineProps<{
    title?: String,
    border?: boolean,
    shadow?: boolean,
    desc: DescItem[]
}>(), {
    border: true,
    shadow: true,
})
const formatter = (item: any) => {
    let value = item.value
    if (item.formatter) {
        value = item.formatter(item.value)
    }
    return value
}
</script>
<style scoped lang="less">
.title {
    padding: 15px 20px 0px;
}

.descBox {
    text-align: left;
    font-weight: normal;
    line-height: 23px;
    font-size: 14px;
    border-collapse: collapse;
    width: 100%;
    padding: 10px 20px 0;
    min-height: 20px;

    >.el-col {
        padding-bottom: 20px;
        display: flex;
        padding-right: 20px;
    }

    .descLabel {
        color: var(--td-text-color-placeholder);
        white-space: nowrap;
        min-width: 80px;
        text-align: left;
        text-overflow: ellipsis;
        overflow: hidden;
        margin: var(--td-comp-margin-xxxl) 0 var(--td-comp-margin-l);
        font: var(--td-font-body-medium);
        // font-weight: 500;
        // text-align-last: justify;
    }

    .descContent {
        flex: 1;
        white-space: nowrap;
        text-overflow: ellipsis;
        overflow: hidden;
        font: var(--td-font-body-medium);
        color: var(--td-text-color-primary);
    }
}

.desc {
    position: relative;

    .floatBtn {
        position: absolute;
        top: 10px;
        right: 10px;
    }
}
</style>