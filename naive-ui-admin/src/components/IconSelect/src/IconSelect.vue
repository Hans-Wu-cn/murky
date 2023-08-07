<template>
  <div class="icon-body">
    <n-popover trigger="click" placement="bottom-start" width="trigger" style="max-height: 300px" scrollable>
      <template #trigger>
        <n-input readonly v-model:value="iconName" style="position: relative;" clearable :placeholder="placeholder"
          @clear="clear()">
          <template #prefix>
            <component v-if="selectIcon" :is="selectIcon()"></component>
          </template>
        </n-input>
      </template>
      <div id="icon-list" class="icon-list">
        <div v-for="(v, k) in iconList" :key="k" @click="selectedIcon(k, v)">
          <component :is="v()"></component>
        </div>
      </div>
    </n-popover>

  </div>
</template>

<script lang="ts">
import { defineComponent, ref, watch } from 'vue';
// import { getIconNames } from './getIconName'
import { constantRouterIcon } from '@/router/icons'
export default defineComponent({
  name: 'IconSelect',
  emits: ['selected', 'inputIn', 'update:iconName'],
  props: {
    iconName: String,
    placeholder: {
      type: String,
      default: '请选择图标'
    }
  },
  setup(props, { emit }) {
    const selectIcon = ref()
    const iconList = ref()
    const showPopover = ref(false)
    iconList.value = constantRouterIcon
    

    selectIcon.value = iconList.value[props.iconName as string]
    const inputIcon = ref('')

    watch(props, (newProps) => {
      selectIcon.value = iconList.value[newProps.iconName as string]
    })

    function selectedIcon(k, v) {
      emit('update:iconName', k)
      selectIcon.value = v
      console.log(v);
      console.log(props.iconName);

      emit('selected', k)
    }

    function clear() {
      // selectIcon.value = null
      emit('update:iconName', '')
    }

    return {
      iconList,
      props,
      selectedIcon,
      inputIcon,
      showPopover,
      selectIcon,
      clear
      // selectIconName
    };
  },

});
</script>

<style lang="less">
.icon-body {
  width: 100%;

  .icon-list {
    display: flex;
    justify-content: flex-start;
    flex-wrap: wrap;
    height: 250px;
    overflow-y: scroll;

    div {
      font-size: 25px;
      display: flex;
      align-items: center;
      justify-content: center;
      height: 50px;
      cursor: pointer;
      width: 20%;

      &:hover {
        background-color: #efebeb;
      }
    }

    span {
      display: inline-block;
      fill: currentColor;
      overflow: hidden;
      margin-left: 0.2rem;
    }
  }


}

#icon-list {
  display: flex;
  justify-content: flex-start;
  flex-wrap: wrap;
  height: 250px;
  overflow-y: scroll;

  div {
    font-size: 25px;
    display: flex;
    align-items: center;
    justify-content: center;
    height: 50px;
    cursor: pointer;
    width: 20%;

    &:hover {
      background-color: #efebeb;
    }
  }

  span {
    display: inline-block;
    fill: currentColor;
    overflow: hidden;
    margin-left: 0.2rem;
  }
}
</style>
