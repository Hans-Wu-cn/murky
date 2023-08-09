<template>
  <div>
    <n-grid class="mt-4" cols="1 s:1 m:1 l:3 xl:3 2xl:3" responsive="screen" :x-gap="12">
      <n-gi span="1">
        <n-card :segmented="{ content: true }" :bordered="false" size="small">
          <template #header>
            <n-space>
              <n-dropdown trigger="hover" @select="selectAddMenu" :options="addMenuOptions">
                <n-button type="info" ghost icon-placement="right">
                  添加菜单
                  <template #icon>
                    <div class="flex items-center">
                      <n-icon size="14">
                        <DownOutlined />
                      </n-icon>
                    </div>
                  </template>
                </n-button>
              </n-dropdown>
              <n-button type="info" ghost icon-placement="left" @click="packHandle">
                全部{{ expandedKeys.length ? '收起' : '展开' }}
                <template #icon>
                  <div class="flex items-center">
                    <n-icon size="14">
                      <AlignLeftOutlined />
                    </n-icon>
                  </div>
                </template>
              </n-button>
              <n-button @click="refresh" strong secondary circle :dashed="true" type="default">
                <template #icon>
                  <n-icon>
                    <Refresh />
                  </n-icon>
                </template>
              </n-button>
            </n-space>
          </template>
          <div class="w-full menu">
            <n-input type="text" v-model:value="pattern" placeholder="输入菜单名称搜索">
              <template #suffix>
                <n-icon size="18" class="cursor-pointer">
                  <SearchOutlined />
                </n-icon>
              </template>
            </n-input>
            <div class="py-3 menu-list">
              <template v-if="loading">
                <div class="flex items-center justify-center py-4">
                  <n-spin size="medium" />
                </div>
              </template>
              <template v-else>
                <n-tree draggable block-line cascade checkable key-field="menuId" :virtual-scroll="true"
                  :pattern="pattern" :data="treeData" :expandedKeys="expandedKeys"
                  style="max-height: 650px; overflow: hidden" :render-prefix="renderPrefix" @drop="handleDrop"
                  @update:selected-keys="selectedTree" @update:expanded-keys="onExpandedKeys" />
              </template>
            </div>
          </div>
        </n-card>
      </n-gi>
      <n-gi span="2">
        <n-card :segmented="{ content: true }" :bordered="false" size="small">
          <template #header>
            <n-space>
              <n-icon size="18">
                <FormOutlined />
              </n-icon>
              <span>编辑菜单{{ treeItemTitle ? `：${treeItemTitle}` : '' }}</span>
            </n-space>
          </template>
          <n-alert type="info" closable> 从菜单列表选择一项后，进行编辑</n-alert>
          <n-form :model="formParams" :rules="rules" ref="formRef" label-placement="left" :label-width="100"
            v-if="isEditMenu" class="py-4">
            <n-form-item label="类型" path="type">
              <n-radio-group v-model:value="formParams.type" name="openType">
                <n-space>
                  <n-radio :value="0">目录</n-radio>
                  <n-radio :value="1">菜单</n-radio>
                  <n-radio :value="2">按钮</n-radio>
                </n-space>
              </n-radio-group>
            </n-form-item>
            <n-form-item label="标题" path="label">
              <n-input placeholder="请输入标题" v-model:value="formParams.label" />
            </n-form-item>
            <n-form-item v-if="formParams.type !== 2" label="菜单图标" path="icon">
              <IconSelect v-model:iconName="formParams.icon"></IconSelect>
            </n-form-item>
            <n-form-item v-if="formParams.type !== 2" label="副标题" path="subtitle">
              <n-input placeholder="请输入副标题" v-model:value="formParams.subtitle" />
            </n-form-item>
            <n-form-item v-if="formParams.type !== 2" label="路由地址" path="path">
              <n-input placeholder="请输入路径" v-model:value="formParams.path" />
            </n-form-item>
            <n-form-item v-if="formParams.type === 1" label="组件路径" path="component">
              <n-input placeholder="请输入组件路径" v-model:value="formParams.component" />
            </n-form-item>
            <n-form-item v-if="formParams.type != 0" label="菜单权限" path="auth">
              <n-input placeholder="请输入权限" v-model:value="formParams.auth" />
            </n-form-item>
            <n-form-item v-if="formParams.type == 1" label="路由参数" path="query">
              <n-input placeholder="请输入路由参数" v-model:value="formParams.query" />
            </n-form-item>
            <n-grid v-if="formParams.type === 1" class="mt-4" cols="4">
              <n-gi span="2">
                <n-form-item label="打开方式" path="openType">
                  <n-radio-group v-model:value="formParams.openType" name="openType">
                    <n-space>
                      <n-radio :value="1">当前窗口</n-radio>
                      <n-radio :value="2">新窗口</n-radio>
                    </n-space>
                  </n-radio-group>
                </n-form-item>
              </n-gi>
              <n-gi span="2">
                <n-form-item label="是否显示" path="isDisplay">
                  <n-radio-group v-model:value="formParams.isDisplay" name="isDisplay">
                    <n-space>
                      <n-radio :value="0">是</n-radio>
                      <n-radio :value="1">否</n-radio>
                    </n-space>
                  </n-radio-group>
                </n-form-item>
              </n-gi>
            </n-grid>

            <n-grid v-if="formParams.type != 2" class="mt-4" cols="4">
              <n-gi span="2">
                <n-form-item label="是否外链" path="isOutside">
                  <n-radio-group v-model:value="formParams.isOutside" name="isOutside">
                    <n-space>
                      <n-radio :value="0">否</n-radio>
                      <n-radio :value="1">是</n-radio>
                    </n-space>
                  </n-radio-group>
                </n-form-item>
              </n-gi>
              <n-gi span="2">
                <n-form-item label="是否缓存" path="isCache">
                  <n-radio-group v-model:value="formParams.isCache" name="isCache">
                    <n-space>
                      <n-radio :value="0">否</n-radio>
                      <n-radio :value="1">是</n-radio>
                    </n-space>
                  </n-radio-group>
                </n-form-item>
              </n-gi>
            </n-grid>
            <n-form-item label="排序" path="sort">
                  <n-input-number placeholder="请输入排序" :min="-10000" :max="10000" :default-value="0"
                    v-model:value="formParams.sort" />
                </n-form-item>
            <n-form-item path="auth" style="margin-left: 100px">
              <n-space>
                <n-button type="primary" :loading="subLoading" @click="formSubmit">保存修改</n-button>
                <n-button @click="handleReset">重置</n-button>
                <n-button @click="handleDel">删除</n-button>
              </n-space>
            </n-form-item>
          </n-form>
        </n-card>
      </n-gi>
    </n-grid>
    <CreateDrawer ref="createDrawerRef" :type="drawerType" :title="drawerTitle" :parentMenuId="drawerParentMenuId"
      @refresh="refresh" />
  </div>
</template>

<script lang="ts" setup>
import { ref, unref, reactive, onMounted, computed } from 'vue';
import { TreeOption, useDialog, useMessage } from 'naive-ui';
import { IconSelect } from '@/components/IconSelect'
import { DownOutlined, AlignLeftOutlined, SearchOutlined, FormOutlined } from '@vicons/antd';
import { getMenuList, removeMenu, editMenu, dropMenu } from '@/api/system/menu';
import { PoemMenu, PoemMenuDrop } from '@/api/system/menu/types';
import { getTreeItem } from '@/utils';
import CreateDrawer from './CreateDrawer.vue';
import { Refresh } from '@vicons/ionicons5'
import { constantRouterIcon } from '@/router/icons'

const rules = {
  label: {
    required: true,
    message: '请输入标题',
    trigger: 'blur',
  },
  path: {
    required: true,
    message: '请输入路径',
    trigger: 'blur',
  },
  component: {
    required: true,
    message: '请输入组件地址',
    trigger: 'blur',
  }
};


const formRef: any = ref(null);
const createDrawerRef = ref();
const message = useMessage();
const dialog = useDialog();

let treeItemKey = ref([]);

let expandedKeys = ref([]);

const treeData = ref([]);

const loading = ref(true);
const subLoading = ref(false);
const isEditMenu = ref(false);
const treeItemTitle = ref('');
const pattern = ref('');
const drawerTitle = ref('');
let drawerParentMenuId = ref('0');
let drawerType = ref(0);
const isAddSon = computed(() => {
  return !treeItemKey.value.length;
});

const addMenuOptions = ref([
  {
    label: '添加顶级菜单',
    key: 'home',
    disabled: false,
  },
  {
    label: '添加子菜单',
    key: 'son',
    disabled: isAddSon,
  },
]);

const formParams: PoemMenu = reactive({
  type: 1,
  label: '',
  subtitle: '',
  path: '',
  auth: '',
  openType: 1,
  sort: 0,
  component: '',
  icon: '',
  isCache: 0,
  isDisplay: 0,
  isOutside: 0,
  query:''
});

function renderPrefix({ option }: { option: TreeOption }) {
  const prefix = constantRouterIcon[option.icon as string];
  if (prefix) {
    return prefix()
  }
  return null;
}


function handleDrop({ node, dragNode, dropPosition }) {
  const [dragNodeSiblings, dragNodeIndex] = findSiblingsAndIndex(
    dragNode,
    treeData.value
  )
  if (dragNodeSiblings === null || dragNodeIndex === null) return
  let data: PoemMenuDrop = {
    menuIds: []
  };
  dragNodeSiblings.splice(dragNodeIndex, 1)
  //拖动到树节点内部
  if (dropPosition === 'inside') {
    let menus = node.children;
    menus.push(dragNode);
    data.menuIds = menus.map((item) => item.menuId);
    data.parentMenuId = node.menuId;
  } else if (dropPosition === 'before') {
    //拖动到树节点前面
    const [nodeSiblings, nodeIndex] = findSiblingsAndIndex(
      node,
      treeData.value
    )
    if (nodeSiblings === null || nodeIndex === null) return
    nodeSiblings.splice(nodeIndex, 0, dragNode)
    data.menuIds = nodeSiblings.map((item) => item.menuId as string);
    data.parentMenuId = node.parentMenuId;
  } else if (dropPosition === 'after') {
    //拖动到树节点前面
    const [nodeSiblings, nodeIndex] = findSiblingsAndIndex(
      node,
      treeData.value
    )
    if (nodeSiblings === null || nodeIndex === null) return
    nodeSiblings.splice(nodeIndex + 1, 0, dragNode)
    data.menuIds = nodeSiblings.map((item) => item.menuId as string);
    data.parentMenuId = node.parentMenuId;
  }
  drop(data);
}

function findSiblingsAndIndex(
  node: TreeOption,
  nodes?: TreeOption[]
): [TreeOption[], number] | [null, null] {
  if (!nodes) return [null, null]
  for (let i = 0; i < nodes.length; ++i) {
    const siblingNode = nodes[i]
    if (siblingNode.menuId === node.menuId) return [nodes, i]
    const [siblings, index] = findSiblingsAndIndex(node, siblingNode.children)
    if (siblings && index !== null) return [siblings, index]
  }
  return [null, null]
}

async function drop(data: PoemMenuDrop) {
  const { code } = await dropMenu(data);
  if (code !== 200) {
    message.error("修改失败")
  }
  refresh()
}

function selectAddMenu(key: string) {
  if (key === 'home') {
    drawerTitle.value = '添加顶栏菜单';
    drawerParentMenuId.value = '0';
    drawerType.value = 0;
  } else {
    drawerTitle.value = `添加子菜单：${treeItemTitle.value}`;
    drawerType.value = 1;

  }
  openCreateDrawer();
}

function openCreateDrawer() {
  const { openDrawer } = createDrawerRef.value;
  openDrawer();
}

function selectedTree(keys) {
  debugger
  if (keys.length) {
    const treeItem = getTreeItem(unref(treeData), keys[0]);
    drawerParentMenuId.value = keys[0];
    treeItemKey.value = keys;
    treeItemTitle.value = treeItem.label;
    Object.assign(formParams, treeItem);
    if (!treeItem.component) {
      formParams.component = '';
    }
    if (!treeItem.query) {
      formParams.query = '';

    }
    isEditMenu.value = true;
  } else {
    isEditMenu.value = false;
    treeItemKey.value = [];
    treeItemTitle.value = '';
    Object.assign(formParams, null);
  }
}

function handleDel() {
  dialog.info({
    title: '提示',
    content: `您确定想删除此权限吗?`,
    positiveText: '确定',
    negativeText: '取消',
    onPositiveClick: async () => {
      loading.value = true;
      const { code } = await removeMenu(formParams.menuId as string);
      console.log(code)
      if (code === 200) {
        message.success('删除成功');
        getMenu();
        Object.assign(formParams, null);
      }
      loading.value = false;
    },
    onNegativeClick: () => {
      message.error('已取消');
    },
  });
}

function handleReset() {
  const treeItem = getTreeItem(unref(treeData), treeItemKey.value[0]);
  Object.assign(formParams, treeItem);
}

function formSubmit() {
  if (0 === formParams.type && formParams.parentMenuId === '0') {
    formParams.component = 'LAYOUT';
  }
  if (0 === formParams.type && formParams.parentMenuId !== '0') {
    formParams.component = 'PARENTLAYOUT';
  }
  if (formParams.isOutside === 1) {
    formParams.component = 'IFRAME';
  }
  formRef.value.validate(async (errors: boolean) => {
    if (!errors) {
      subLoading.value = true;
      const { code } = await editMenu(formParams);
      if (code === 200) {
        message.success('修改成功');
        refresh()
      }
      subLoading.value = false;
    } else {
      message.error('请填写完整信息');
    }
  });
}

function packHandle() {
  if (expandedKeys.value.length) {
    expandedKeys.value = [];
  } else {
    expandedKeys.value = unref(treeData).map((item: any) => item.key as string) as [];
  }
}

onMounted(async () => {
  refresh()
});

function refresh() {
  loading.value = true;
  getMenu().then(()=>{
    loading.value = false
  });
}

async function getMenu() {
  const { code, result } = await getMenuList();
  if (code === 200) {
    treeData.value = result;
  }

}

function onExpandedKeys(keys) {
  expandedKeys.value = keys;
}

//选择图标
// function selected(data) {
//   formParams.icon = data
// }

</script>