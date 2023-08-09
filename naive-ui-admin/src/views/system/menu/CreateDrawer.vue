<template>
  <n-drawer v-model:show="isDrawer" :width="width" :placement="placement">
    <n-drawer-content :title="title" closable>
      <n-form :model="formParams" :rules="rules" ref="formRef" label-placement="left" :label-width="100">
        <n-form-item label="类型" path="type">
          <n-radio-group v-model:value="formParams.type" name="openType">
            <n-space>
              <n-radio :value="0">目录</n-radio>
              <n-radio :value="1">菜单</n-radio>
              <n-radio v-if="type !== 0" :value="2">按钮</n-radio>
            </n-space>
          </n-radio-group>
        </n-form-item>
        <n-form-item label="标题" path="label">
          <n-input placeholder="请输入标题" v-model:value="formParams.label" />
        </n-form-item>
        <n-form-item v-if="formParams.type != 2" label="菜单图标" path="icon">
          <IconSelect v-model:iconName="formParams.icon"></IconSelect>
        </n-form-item>
        <n-form-item v-if="formParams.type != 2" label="副标题" path="subtitle">
          <n-input placeholder="请输入副标题" v-model:value="formParams.subtitle" />
        </n-form-item>
        <n-form-item v-if="formParams.type != 2" label="路由地址" path="path">
          <n-input placeholder="请输入路由地址" v-model:value="formParams.path" />
        </n-form-item>
        <n-form-item v-if="formParams.type === 1" label="组件路径" path="component">
          <n-input placeholder="请输入组件路径" v-model:value="formParams.component" />
        </n-form-item>
        <n-form-item v-if="formParams.type === 1" label="打开方式" path="openType">
          <n-radio-group v-model:value="formParams.openType" name="openType">
            <n-space>
              <n-radio :value="1">当前窗口</n-radio>
              <n-radio :value="2">新窗口</n-radio>
            </n-space>
          </n-radio-group>
        </n-form-item>

        <n-form-item label="菜单权限" path="auth">
          <n-input placeholder="请输入权限，多个权限用，分割" v-model:value="formParams.auth" />
        </n-form-item>
        <n-form-item v-if="formParams.type == 1" label="路由参数" path="query">
          <n-input placeholder="请输入路由参数" v-model:value="formParams.query" />
        </n-form-item>
        <n-form-item v-if="formParams.type !== 2" label="是否显示" path="isDisplay">
          <n-radio-group v-model:value="formParams.isDisplay" name="isDisplay">
            <n-space>
              <n-radio :value="0">是</n-radio>
              <n-radio :value="1">否</n-radio>
            </n-space>
          </n-radio-group>
        </n-form-item>
        <n-form-item v-if="formParams.type !== 2" label="是否外链" path="isOutside">
          <n-radio-group v-model:value="formParams.isOutside" name="isOutside">
            <n-space>
              <n-radio :value="0">否</n-radio>
              <n-radio :value="1">是</n-radio>
            </n-space>
          </n-radio-group>
        </n-form-item>
        <n-form-item v-if="formParams.type !== 2" label="是否缓存" path="isCache">
          <n-radio-group v-model:value="formParams.isCache" name="isCache">
            <n-space>
              <n-radio :value="0">否</n-radio>
              <n-radio :value="1">是</n-radio>
            </n-space>
          </n-radio-group>
        </n-form-item>

        <n-form-item v-if="formParams.type !== 2" label="排序" path="sort">
          <n-input-number placeholder="请输入排序" :min="-10000" :max="10000" :default-value="0"
            v-model:value="formParams.sort" />
        </n-form-item>
      </n-form>

      <template #footer>
        <n-space>
          <n-button type="primary" :loading="subLoading" @click="formSubmit">提交</n-button>
          <n-button @click="handleReset">重置</n-button>
        </n-space>
      </template>
    </n-drawer-content>

  </n-drawer>
</template>

<script lang="ts">
import { defineComponent, reactive, ref, toRefs } from 'vue';
import { useMessage } from 'naive-ui';
import { addMenu } from '@/api/system/menu';
import { PoemMenu } from '@/api/system/menu/types';

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
};
export default defineComponent({
  name: 'CreateDrawer',
  components: {},
  emits: ['refresh'],
  props: {
    title: {
      type: String,
      default: '添加顶级菜单',
    },
    parentMenuId: {
      type: String,
      default: '0',
    },
    type: {
      type: Number
    },
    width: {
      type: Number,
      default: 450,
    },
  },
  setup(props, { emit }) {
    gridCollapsed: ref(false);
    gridCollapsedRows: ref(1);
    gridItemCount: ref(4);
    showSuffix: ref(true);
    const message = useMessage();
    const formRef: any = ref(null);
    const defaultValueRef: PoemMenu = {
      label: '',
      type: 1,
      subtitle: '',
      openType: 1,
      auth: '',
      path: '',
      icon: '',
      component: '',
      // parentMenuId: 0,
      sort: 0,
      isCache: 0,
      isDisplay: 0,
      isOutside: 0
    };

    const state = reactive({
      isDrawer: false,
      subLoading: false,
      formParams: defaultValueRef,
      placement: 'right' as const,
      alertText:
        '该功能主要实时预览各种布局效果，更多完整配置在 projectSetting.ts 中设置，建议在生产环境关闭该布局预览功能。',
    });

    function openDrawer() {
      state.isDrawer = true;
      console.log(props.parentMenuId);
    }

    function closeDrawer() {
      state.isDrawer = false;
      emit('refresh');
    }

    function formSubmit() {
      if (0 === state.formParams.type && state.formParams.parentMenuId === '0') {
        state.formParams.component = 'LAYOUT';
      }
      if (0 === state.formParams.type && state.formParams.parentMenuId !== '0') {
        state.formParams.component = 'PARENTLAYOUT';
      }
      if (state.formParams.isOutside === 1) {
        state.formParams.component = 'IFRAME';
      }
      formRef.value.validate((errors) => {
        if (!errors) {
          state.formParams.parentMenuId = props.parentMenuId
          console.log(state.formParams);
          save().then(() => {
            handleReset();
            closeDrawer();
          })
        } else {
          message.error('请填写完整信息');
        }
      });
    }

    function handleReset() {
      formRef.value.restoreValidation();
      let defaultValueRef: PoemMenu = {
        label: '',
        type: 1,
        subtitle: '',
        openType: 1,
        auth: '',
        path: '',
        icon: '',
        component: '',
        // parentMenuId: 0,
        sort: 0,
        isCache: 0,
        isDisplay: 0,
        isOutside: 0
      }
      state.formParams = Object.assign(state.formParams, defaultValueRef);
    }

    async function save() {
      const { code } = await addMenu(state.formParams);
      if (code === 200) {
        message.success('添加成功');
      }
    }

    return {
      ...toRefs(state),
      formRef,
      rules,
      formSubmit,
      handleReset,
      openDrawer,
      closeDrawer,
    };
  },
});
</script>
<style scoped>
.suffix {
  height: 108px;
  border: 1px solid rgba(0, 128, 0, 0.36);
  box-sizing: border-box;
  display: flex;
  align-items: center;
  justify-content: center;
}

.light-green {
  height: 108px;
  background-color: rgba(0, 128, 0, 0.12);
  box-sizing: border-box;
  display: flex;
  align-items: center;
  justify-content: center;
}

.green {
  height: 108px;
  background-color: rgba(0, 128, 0, 0.24);
  box-sizing: border-box;
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>@/api/system/menu/menu