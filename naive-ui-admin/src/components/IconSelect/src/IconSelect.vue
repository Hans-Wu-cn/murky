<template>
  <div class="icon-body">
    <n-input  v-model:value="inputIcon" style="position: relative;" clearable placeholder="请输入图标名称" @input="filterIcons" @clear="filterIcons">
    </n-input>

    <div class="icon-list">
      <div v-for="(item, index) in iconList" :key="index" @click="selectedIcon(item)">
        <svg-icon :name="item" style="height: 30px;width: 16px;" />
        <span>{{ item }}</span>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue';
import { getIconNames } from './getIconName'

export default defineComponent({
  name: 'IconSelect',
  emits: ['selected','inputIn'],
  setup(props, { emit }) {

    const iconList = ref([''])
    iconList.value = getIconNames()

    const inputIcon = ref('')

    //过过滤查询iconNames方法
    function filterIcons() {
      emit('inputIn')
      iconList.value = getIconNames()
      if (inputIcon.value) {
        iconList.value = iconList.value.filter(item => item.includes(inputIcon.value))
      }
    }





    function selectedIcon(item) {
      emit('selected', item)
    }



    return {
      iconList,
      props,
      selectedIcon,
      inputIcon,
      filterIcons
    };
  },
});
</script>

<style lang="less">
.icon-body {
  width: 100%;
  padding: 10px;
  .icon-list {
    height: 250px;
    overflow-y: scroll;

    div {
      height: 30px;
      line-height: 30px;
      margin-bottom: -5px;
      cursor: pointer;
      width: 33%;
      float: left;
      display: inline;

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
</style>
