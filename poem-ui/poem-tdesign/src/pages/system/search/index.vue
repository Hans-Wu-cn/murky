<template>
    <div>
        
    </div>
</template>
<script setup lang="ts">
const props = withDefaults(defineProps<{
    options:{
        name:string,
        value:string,
        key:string,
        type:string
    }[]
}>(),{
    options:()=>[{
        name:'',
        value:'',
        key:'',
        type:'input'
    }]
})
</script>
<style scoped lang="less">

</style>