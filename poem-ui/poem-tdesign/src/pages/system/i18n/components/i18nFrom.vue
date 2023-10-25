<template>
  <div>

  </div>
</template>
<script setup lang="tsx">
import { onMounted, ref } from 'vue'
import { FormRules, MessagePlugin, SubmitContext } from 'tdesign-vue-next';
import { fromPairs } from 'lodash';

const emit = defineEmits(['submit-hook'])
// const props = defineProps<{
//   dictType: string
// }>()

const FORM_I18N = ref<FormRules>({
  dictLabel: [{ required: true, message: '请输入字典标签', trigger: 'blur' }],
  dictValue: [{ required: true, message: '请输入字典值', trigger: 'blur' }],
})

// 记录重置表单数据
const resetValue = ref({})
const dictDataFromId = ref('');
const dictDataFromType = ref('');
const loading = ref(false);



onMounted(async () => {
});

defineExpose({
})



</script>
<style lang="less">
.treeBox {
  border: 1px solid #ddd;
  overflow: auto;
  height: 300px;
  width: 100%;
}
</style>