<template>
  <div style="margin: 5% 20%;height:60%;width: 100%">
    <el-form size="large" label-position="left" ref="form" :model="series" style="width: 100%">
      <el-form-item label="系列名">
        <el-input v-model="series.series.seriesName" :model-value="series.series.seriesName" style="max-width: 60%"></el-input>
      </el-form-item>
        <el-row style="width:70%;margin-top: 8%" justify="space-around">
          <el-col span="24">
            <el-button type="success" size="large" plain round @click="add()" style="width: 120px;">添加</el-button>
          </el-col>
          <el-col span="24">
            <el-button type="primary" size="large" plain round  @click="back()" style="width: 120px;">返回</el-button>
          </el-col>
        </el-row>
    </el-form>
  </div>
</template>

<script setup>
import {reactive} from "vue";
import {addSeries} from "@/api/SeriesApi";
import {ElMessage} from "element-plus";
let emit = defineEmits(['finish', 'cancel'])

let series = reactive({
  series: {
    seriesName: ""
  }
})

let add = (() => {
  let Series = JSON.stringify(series.series)
  addSeries(Series).then(Response => {
    ElMessage.success(Response.message)
    emit('finish')
  }).catch(Error => {
    alert(Error.data.message)
  })
})

let back = (() => {
  emit('cancel')
})
</script>

<style scoped>
</style>
