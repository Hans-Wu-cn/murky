<template>
<div class="menuManage">
  <t-card :bordered="false">
    <div>
      <t-button @click="handleAdd">添加菜单</t-button>
      <!-- <t-button theme="default" style="margin-left: 16px" @click="resetData">重置/更新数据</t-button>
      <t-button theme="default" style="margin-left: 16px" @click="onRowToggle">任意节点展开/收起</t-button>
      <t-button theme="default" style="margin-left: 16px" @click="onExpandAllToggle">
        {{ expandAll ? '收起全部' : '展开全部' }}
      </t-button>
      <t-button theme="default" style="margin-left: 16px" @click="getTreeNode">获取全部树形结构</t-button> -->
    </div>
    <br />
    <div>
      <t-checkbox v-model="customTreeExpandAndFoldIcon" style="vertical-align: middle">
        自定义折叠/展开图标
      </t-checkbox>
    </div>
    <br />
    <!-- 第一列展开树结点，缩进为 24px，子节点字段 childrenKey 默认为 children -->
    <!-- !!! 树形结构 EnhancedTable 才支持，普通 Table 不支持 !!! -->
    <t-enhanced-table
      ref="table"
      row-key="menuId"
      drag-sort="row-handler"
      :data="data"
      :columns="columns"
      :tree="treeConfig"
      :tree-expand-and-fold-icon="treeExpandIcon"
      :before-drag-sort="beforeDragSort"
      @abnormal-drag-sort="onAbnormalDragSort"
      @drag-sort="onDragSort"
      @tree-expand-change="onTreeExpandChange"
    />

    <!-- 弹出框 -->
    <t-dialog
      v-model:visible="dialogVisible"
      :header="dialogHeader"
      :confirm-btn="dialogButton"
      :close-btn="true"
    >
      <template #body>slot body</template>
    </t-dialog>
  </t-card>
</div>
</template>
<script setup lang="tsx">
import {
  AddRectangleIcon,
  ChevronDownIcon,
  ChevronRightIcon,
  MinusRectangleIcon,
  MoveIcon,
} from 'tdesign-icons-vue-next';
import { EnhancedTable as TEnhancedTable, Loading, MessagePlugin, PrimaryTableCol } from 'tdesign-vue-next';
import { computed, onMounted, reactive, ref } from 'vue';

import { addMenu, getMenuList } from '@/api/menu';
import { ResultEnum } from '@/enums/httpEnum';

async function getData() {
  const data:any[] = [];
  const { code, result } = await getMenuList();
  if (ResultEnum.SUCCESS === code)
    result.forEach((item) => {
      data.push(item);
    });
  return data;
}

const table = ref();
const data = ref([]);
const lazyLoadingData = ref(null);
const dialogHeader = ref('');
const dialogVisible = ref(false);
const dialogButtonLoading = ref(false);
const treeConfig = reactive({ childrenKey: 'children', treeNodeColumnIndex: 2, indent: 50 });

const resetData = () => {
  // 需要更新数据地址空间
  getData().then((item) => {
    table.value.resetData(item);
  });
};

const onEditClick = (row:any) => {
  const newData = {
    ...row,
    platform: 'New',
    type: 'Symbol',
    default: 'undefined',
  };
  table.value.setData(row.key, newData);
  MessagePlugin.success('数据已更新');
};

const onDeleteConfirm = (row:any) => {
  table.value.remove(row.key);
  MessagePlugin.success('删除成功');
};

const onLookUp = (row:any) => {
  const allRowData = table.value.getData(row.key);
  const message = '当前行全部数据，包含节点路径、父节点、子节点、是否展开、是否禁用等';
  MessagePlugin.success(`打开控制台查看${message}`);
  console.log(`${message}：`, allRowData);
};

const appendTo = (row:any) => {
  const randomKey1 = Math.round(Math.random() * Math.random() * 1000) + 10000;
  table.value.appendTo(row.key, {
    id: randomKey1,
    key: `申请人 ${randomKey1} 号`,
    platform: '电子签署',
    type: 'Number',
  });
  MessagePlugin.success(`已插入子节点申请人 ${randomKey1} 号，请展开查看`);

  // 一次性添加多个子节点。示例代码有效，勿删！!!
  // appendMultipleDataTo(row);
};

function appendMultipleDataTo(row) {
  const randomKey1 = Math.round(Math.random() * Math.random() * 1000) + 10000;
  const randomKey2 = Math.round(Math.random() * Math.random() * 1000) + 10000;
  const randomKey3 = Math.round(Math.random() * Math.random() * 1000) + 10000;
  const newData = [
    {
      id: randomKey1,
      key: `申请人 ${randomKey1} 号`,
      platform: '电子签署',
      type: 'Number',
    },
    {
      id: randomKey2,
      key: `申请人 ${randomKey2} 号`,
      platform: '纸质签署',
      type: 'Number',
      list: true,
    },
    {
      id: randomKey3,
      key: `申请人 ${randomKey3} 号`,
      platform: '纸质签署',
      type: 'Number',
      list: true,
    },
  ];
  table.value.appendTo(row?.key, newData);
  MessagePlugin.success(`已插入子节点申请人 ${randomKey1} 和 ${randomKey2} 号，请展开查看`);
}

// 当前节点之前，新增兄弟节前
const insertBefore = (row) => {
  const randomKey = Math.round(Math.random() * Math.random() * 1000) + 10000;
  table.value.insertBefore(row.key, {
    id: randomKey,
    key: `申请人 ${randomKey} 号`,
    platform: '纸质签署',
    type: 'Number',
  });
  MessagePlugin.success(`已插入子节点申请人 ${randomKey} 号，请展开查看`);
};

// 当前节点之后，新增兄弟节前
const insertAfter = (row) => {
  const randomKey = Math.round(Math.random() * Math.random() * 1000) + 10000;
  table.value.insertAfter(row.key, {
    id: randomKey,
    key: `申请人 ${randomKey} 号`,
    platform: '纸质签署',
    type: 'Number',
  });
  MessagePlugin.success(`已插入子节点申请人 ${randomKey} 号，请展开查看`);
};

const columns:Array<PrimaryTableCol<any>> = [
  {
    // 列拖拽排序必要参数
    colKey: 'drag',
    title: '排序',
    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    cell: (_h) => <MoveIcon />,
    width: 46,
  },
  {
    colKey: 'menuId',
    title: '编号',
    ellipsis: true,
    width: 80,
  },
  {
    width: 180,
    colKey: 'label',
    title: '菜单标题',
    ellipsis: true,
  },
  {
    colKey: 'name',
    title: '菜单名',
    width: 100,
  },
  {
    colKey: 'auth',
    title: '权限码',
    width: 100,
  },
  {
    colKey: 'operate',
    width: 340,
    title: '操作',
    // 增、删、改、查 等操作
    cell: (h, { row }) => (
      <div class="tdesign-table-demo__table-operations">
        <t-space>
          <t-link theme="primary" variant="text" hover="color" onClick={() => appendTo(row)}>
            插入
          </t-link>
          <t-link theme="primary" variant="text" hover="color" onClick={() => insertBefore(row)}>
            前插
          </t-link>
          <t-link theme="primary" variant="text" hover="color" onClick={() => insertAfter(row)}>
            后插
          </t-link>
          <t-link theme="primary" variant="text" hover="color" onClick={() => onEditClick(row)}>
            更新
          </t-link>
          <t-link theme="primary" variant="text" hover="color" onClick={() => onLookUp(row)}>
            查看
          </t-link>
          <t-popconfirm content="确认删除吗" onConfirm={() => onDeleteConfirm(row)}>
            <t-link variant="text" hover="color" theme="danger">
              删除
            </t-link>
          </t-popconfirm>
        </t-space>
      </div>
    ),
  },
];

const expandAll = ref(false);

const onRowToggle = () => {
  const rowIds = ['申请人 1_1 号', '申请人 2_1 号', '申请人 3_1 号', '申请人 4_1 号'];
  rowIds.forEach((id) => {
    // getData 参数为行唯一标识，lodash.get(row, rowKey)
    const rowData = table.value.getData(id);
    table.value.toggleExpandData(rowData);
    // 或者
    // table.value.toggleExpandData({ rowIndex: rowData.rowIndex, row: rowData.row });
  });
};

const customTreeExpandAndFoldIcon = ref(false);

const treeExpandAndFoldIconRender = (h, { type, row }) => {
  if (lazyLoadingData.value && lazyLoadingData.value.id === row?.id) {
    return <Loading size="14px" />;
  }
  return type === 'expand' ? <ChevronRightIcon /> : <ChevronDownIcon />;
};

// 懒加载图标渲染
const lazyLoadingTreeIconRender = (h, params) => {
  const { type, row } = params;
  if (lazyLoadingData.value && lazyLoadingData.value.id === row?.id) {
    return <Loading size="14px" />;
  }
  return type === 'expand' ? <AddRectangleIcon /> : <MinusRectangleIcon />;
};

// 默认展开全部。示例代码有效，勿删
// onMounted(() => {
//   table.value.expandAll();
// });

const getTreeNode = () => {
  const treeData = table.value.getTreeNode();
  console.log(treeData);
  MessagePlugin.success('树形结构获取成功，请打开控制台查看');
};

const onExpandAllToggle = () => {
  expandAll.value = !expandAll.value;
  // expandAll.value ? table.value.expandAll() : table.value.foldAll();
};

const handleAdd = () => {
  dialogVisible.value = true;
  dialogHeader.value = '添加根菜单';
};

const dialogButton = () => {
  return (
    <t-button theme="primary" v-model:loading={dialogButtonLoading}>
      保存
    </t-button>
  );
};

const onAbnormalDragSort = (params) => {
  console.log(params);
  // MessagePlugin.warning(params.reason);
  if (params.code === 1001) {
    MessagePlugin.warning('不同层级的元素，不允许调整顺序');
  }
};

const onTreeExpandChange = (context) => {
  console.log(context.rowState.expanded ? '展开' : '收起', context);
  /**
   * 如果是懒加载，请确认自己完成了以下几个步骤
   * 1. 提前设置 children 值为 true；
   * 2. 在 onTreeExpandChange 事件中处理异步数据；
   * 3. 自定义展开图标渲染 lazyLoadingTreeIconRender
   */
  if (context.row.list === true) {
    lazyLoadingData.value = context.row;
    const timer = setTimeout(() => {
      appendMultipleDataTo(context.row);
      lazyLoadingData.value = null;
      clearTimeout(timer);
    }, 200);
  }
};

const onDragSort = (params) => {
  console.log('onDragSort:', params);
};

// 应用于需要阻止拖拽排序的场景。如：当子节点存在时，则不允许调整顺序。
// 返回值为 true，允许拖拽排序；返回值 为 false，则阻止拖拽排序
const beforeDragSort = (params) => {
  console.log('beforeDragSort:', params);
  return true;
};

const treeExpandIcon = computed(() => {
  // 自定义展开图标
  if (customTreeExpandAndFoldIcon.value) {
    return treeExpandAndFoldIconRender;
  }
  return lazyLoadingTreeIconRender;
});

onMounted(async () => {
  resetData();
});
</script>

<style scoped lang="less">
.tdesign-table-demo__table-operations .t-link {
  padding: 0 8px;
}
.menuManage{
  // background: #fff;
  min-height: 100%;
  display: flex;
}
</style>
