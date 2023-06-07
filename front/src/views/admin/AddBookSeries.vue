<template>
  <p v-show="false">{{ series.series.seriesName }}</p>
  <div style="margin: 3% 2%;text-align: center;align-content: center">
    <div style="width: 30%;float: left">
		
      <el-form
          size="default"
          label-position="left"
          ref="form"
          label-width="large"
          :model="series"
      >
	  
        <el-form-item label="系列名">
          <el-input v-model="series.series.seriesName" :model-value="series.series.seriesName"></el-input>
        </el-form-item>
		
        <el-form-item>
          <el-button type="danger" @click="add()">添加</el-button>
          <el-button type="sucess" @click="back()">返回</el-button>
        </el-form-item>
		
      </el-form>
	  
    </div>
  </div>
  
</template>

<script setup>
import {useRouter} from "vue-router";
import {reactive, onBeforeMount, toRef} from "vue";
import axios from "axios";
import { UploadFilled } from '@element-plus/icons-vue'
let router=useRouter()

const series = reactive({
  series: {
    seriesName: ""
  }
})


let add = (() => {
  let addSeries = JSON.stringify(series.series)
  axios.post('api/bookSeries', addSeries, {headers: {'Content-Type': 'application/json'}}).then(Response => {
    let message = Response.data
    if (confirm(message+"!  是否跳转到系列页")){
      router.push({
        name: 'adminBookSeries',
      })}
  }).catch(Error=>{
    alert(Error.message)
  })
})


let back = (() => {
  router.push({
    name: 'adminBookSeroes'
  })
})
</script>

<style scoped>
.el-radio-group {
  margin-right: 12px;
}
.el-input{
  width: 80%;
}
.el-form{
  margin-left: 70%;
  float: right;
  width: 100%;
}
</style>
