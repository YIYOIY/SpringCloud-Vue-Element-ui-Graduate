<template>
  <div style="margin: 0 30%;height:100px">
    <el-form size="large" label-position="left" ref="form" width="100%" :model="series">
      <el-form-item label="系列名">
        <el-input v-model="series.series.seriesName" :model-value="series.series.seriesName"></el-input>
      </el-form-item>
      <el-form-item>
        <el-row style="width:100%" justify="space-evenly">
          <el-col span="24">
            <el-button type="success" plain round @click="add()">添加</el-button>
          </el-col>
          <el-col span="24">
            <el-button type="primary" plain round  @click="back()">返回</el-button>
          </el-col>
        </el-row>
      </el-form-item>
    </el-form>
  </div>
</template>

<script setup>
import { reactive } from "vue";
import {addSeries} from "@/api/CategoryApi";
import {ElMessage} from "element-plus";
let emit = defineEmits(['finish', 'cancel'])

const series = reactive({
  series: {
    seriesName: ""
  }
})

let add = (() => {
  let Series = JSON.stringify(series.series)
  addSeries(Series).then(Response => {
    ElMessage.success(Response.message)
    emit('finish')
    series.series=''
  }).catch(Error => {
    alert(Error.data.message)
  })
})

let back = (() => {
  emit('cancel')
})
</script>

<style scoped>
.el-radio-group {
  margin-right: 12px;
}

.el-input {
  width: 80%;
}

.el-form {
  margin-left: 70%;
  float: right;
  width: 100%;
}
</style>
