<template>
    <div class="detailPage">
        <t-card :bordered="false" title="菜单信息">
            <t-skeleton :loading="loading" animation="gradient">
                <descriptions :desc="desc"></descriptions>
            </t-skeleton>
        </t-card>
    </div>
</template>
<script setup lang="tsx">
import { getMenu } from '@/api/saas/menu';
import descriptions from '@/components/descriptions/index.vue'
import { ref } from 'vue';
import { useRoute } from 'vue-router';
const route = useRoute();
const desc = ref([
    {
        label: '菜单ID',
        value: '',
        code: 'saasMenuId',
        column: 3
    },
    {
        label: '菜单标题',
        value: '',
        code: 'label',
        column: 3
    },
    {
        label: '图标',
        value: '',
        code: 'icon',
        column: 3
    },
    {
        label: '菜单类型',
        value: '',
        code: 'type',
        column: 3
    },
    {
        label: '菜单名称',
        value: '',
        code: 'name',
        column: 3
    },
    {
        label: '组件路径',
        value: '',
        code: 'component',
        column: 3
    },
    {
        label: '路由地址',
        value: '',
        code: 'path',
        column: 3
    },
    {
        label: '路由参数',
        value: '',
        code: 'query',
        column: 3
    },
    {
        label: '打开方式',
        value: '',
        code: 'openType',
        column: 3
    },
    {
        label: '权限码',
        value: '',
        code: 'auth',
        column: 3
    },
    {
        label: '是否显示在菜单',
        value: '',
        code: 'isDisplay',
        column: 3
    },
    {
        label: '是否开启缓存',
        value: '',
        code: 'isCache',
        column: 3
    },
    {
        label: '是否使用外链',
        value: '',
        code: 'isOutside',
        column: 3
    },
])
/**
 * 查询菜单详情回显字段
 * 
 */
const loading = ref(true)
const queryMenuDetail = async () => {
    const poemId = route.query.poemId as string
    if (!poemId) return
    loading.value = true
    const { code, result } = await getMenu(poemId);
    loading.value = false
    code === 200 ? desc.value.forEach(val => {
        val.value = result[val.code]
    }) : ''
}
queryMenuDetail()
</script>
<style scoped lang="less">
.detailPage {
    .t-card {
        padding: var(--td-comp-paddingTB-xxl) var(--td-comp-paddingLR-xxl);

        :deep(.t-card__header) {
            padding: 0;
        }

        :deep(.t-card__body) {
            padding: 0;
        }

        :deep(.t-skeleton) {
            margin-top: 20px;
        }
    }
}
</style>