<template>
  <div class="icon-body">
    <n-popover trigger="click" placement="bottom-start" width="trigger" style="max-height: 300px" scrollable>
      <template #trigger>
        <n-input readonly v-model:iconName="iconName" style="position: relative;" clearable :placeholder="placeholder"
          @input="filterIcons" @clear="filterIcons">
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
import { defineComponent, ref } from 'vue';
// import { getIconNames } from './getIconName'
import { constantRouterIcon } from '@/router/icons'
export default defineComponent({
  name: 'IconSelect',
  emits: ['selected', 'inputIn','update:iconName'],
  props: {
    iconName: String,
    placeholder:{
      type: String,
      default:'请选择图标'
    }
  },
  setup(props, { emit }) {
    const selectIcon = ref()
    const iconList = ref()
    const showPopover = ref(false)
    iconList.value = constantRouterIcon

    // for(let key in constantRouterIcon ) {
    //   console.log(key);
    //   console.log(constantRouterIcon[key]());
    //   iconList.value[key] = constantRouterIcon[key]()
    // }

    const inputIcon = ref('')

    //过过滤查询iconNames方法
    function filterIcons() {
      emit('inputIn')
      iconList.value = constantRouterIcon
      if (inputIcon.value) {
        iconList.value = iconList.value.filter(item => item.includes(inputIcon.value))
      }
    }

    function selectedIcon(k, v) {
      emit('update:iconName', k)
      selectIcon.value = v
      console.log(v);
      console.log(props.iconName);
      
      emit('selected', k)
    }

    return {
      iconList,
      props,
      selectedIcon,
      inputIcon,
      filterIcons,
      showPopover,
      selectIcon,
      // selectIconName
    };
  },
});
</script>

<style lang="less">
.icon-body {
  width: 100%;
  padding: 10px;

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
