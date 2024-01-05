<template>
    <div class="detailPage">
        <t-card :bordered="false" title="菜单信息">
            <t-skeleton :loading="loading" animation="gradient">
                <descriptions :desc="desc">
                    <template #icon="scope">
                        <t-icon v-if="scope.item.iconType" :name="scope.scope" />
                        <img class="icon-options" v-else @error="scope.item.iconType = true" :src="scope.scope" alt="icon">
                    </template>
                </descriptions>
            </t-skeleton>
        </t-card>
    </div>
</template>
<script setup lang="tsx">
import { getMenu } from '@/api/system/menu';
import descriptions, { DescItem } from '@/components/descriptions/index.vue'
import i18n from '@/i18n';
import { computed, ref } from 'vue';
import { useRoute } from 'vue-router';
const route = useRoute();
const desc = ref<DescItem[]>([
    {
        label: computed(() => i18n.global.t('menu.label.id')),
        value: '',
        code: 'menuId',
        column: 3
    },
    {
        label: computed(() => i18n.global.t('menu.label.title')),
        value: '',
        code: 'label',
        column: 3,
        formatter: (value: string) => {
            return i18n.global.t(value)
        }
    },
    {
        label: computed(() => i18n.global.t('menu.label.icon')),
        value: '',
        code: 'icon',
        column: 3,
        slotName:'icon'
    },
    {
        label: computed(() => i18n.global.t('menu.label.type')),
        value: '',
        code: 'type',
        column: 3,
        formatter: (value: number) => {
            return i18n.global.t('menu.label.type.' + value)
        }
    },
    {
        label: computed(() => i18n.global.t('menu.label.name')),
        value: '',
        code: 'name',
        column: 3
    },
    {
        label: computed(() => i18n.global.t('menu.label.compoment')),
        value: '',
        code: 'component',
        column: 3
    },
    {
        label: computed(() => i18n.global.t('menu.label.router')),
        value: '',
        code: 'path',
        column: 3
    },
    {
        label: computed(() => i18n.global.t('menu.label.query')),
        value: '',
        code: 'query',
        column: 3
    },
    {
        label: computed(() => i18n.global.t('menu.label.title.openType')),
        value: '',
        code: 'openType',
        column: 3,
        formatter: (value: number) => {
            return i18n.global.t('menu.label.title.openType.' + value)
        }
    },
    {
        label: computed(() => i18n.global.t('menu.label.auth')),
        value: '',
        code: 'auth',
        column: 3
    },
    {
        label: computed(() => i18n.global.t('menu.label.isDisplay')),
        value: '',
        code: 'isDisplay',
        column: 3,
        formatter: (value: number) => {
            return i18n.global.t('menu.label.isDisplay.' + value)
        }
    },
    {
        label: computed(() => i18n.global.t('menu.label.isCache')),
        value: '',
        code: 'isCache',
        column: 3,
        formatter: (value: number) => {
            return i18n.global.t('menu.label.isCache.' + value)
        }
    },
    {
        label: computed(() => i18n.global.t('menu.label.isOutside')),
        value: '',
        code: 'isOutside',
        column: 3,
        formatter: (value: number) => {
            return i18n.global.t('menu.label.isOutside.' + value)
        }
    },
])
/**
 * 查询菜单详情回显字段
 * 
 */
const loading = ref(true)
const queryMenuDetail = async () => {
    const menuId = route.query.menuId as string
    if (!menuId) return
    loading.value = true
    const { code, result } = await getMenu(menuId);
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
        img.icon-options {
            width: 14px;
            height: 14px;
            vertical-align: middle;
        }
    }
}
</style>