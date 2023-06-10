<template>
  <div style="margin: 0 30%;height:100px">
    <el-form size="large" label-position="left" ref="form" width="100%" :model="series">
      <el-form-item label="系列名">
        <el-input v-model="series.series.seriesName" :model-value="series.series.seriesName"></el-input>
      </el-form-item>
      <el-form-item>
        <el-row style="width:100%" justify="space-evenly">
          <el-col span="24">
            <el-button type="success" @click="add()">添加</el-button>
          </el-col>
          <el-col span="24">
            <el-button type="primary" @click="back()">返回</el-button>
          </el-col>
        </el-row>
      </el-form-item>
    </el-form>
  </div>
</template>

<script setup>
import { useRouter } from "vue-router";
import { reactive, onBeforeMount, toRef } from "vue";
import axios from "axios";
import { SuccessFilled, UploadFilled } from '@element-plus/icons-vue'
let router = useRouter()
let emit = defineEmits(['finish', 'cancel'])

const series = reactive({
  series: {
    seriesName: ""
  }
})


let add = (() => {
  let addSeries = JSON.stringify(series.series)
  axios.post('api/bookSeries', addSeries, { headers: { 'Content-Type': 'application/json' } }).then(Response => {
    let message = Response.data
    alert(message)
    emit('finish')
  }).catch(Error => {
    alert(Error.message)
  })
})


// let back = (() => {
//   router.push({
//     name: 'category'
//   })
// })

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
