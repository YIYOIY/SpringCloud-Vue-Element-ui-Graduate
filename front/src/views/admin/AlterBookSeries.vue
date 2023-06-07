<template>
  <p v-show="false">{{ series.series.seriesName }}</p>
  <div style="margin: 20% 30%;text-align: center;align-content: center">
    <div style="width: 50%;float: left">
		
    <el-form
        size="default"
        label-position="left"
        ref="form"
        label-width="auto"
        :model="series"
    >
	
      <el-form-item label="系列">
        <el-input v-model="series.series.seriesName" :model-value="series.series.seriesName"></el-input>
      </el-form-item>
	  
      <el-form-item>
        <el-button type="danger" @click="alter()">修改</el-button>
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

const prop = defineProps(['seriesId'])
let seriesId=toRef(prop,'seriesId')
console.log(seriesId.value+"!!!!!!!!!!!!!!!!!!!!!!")

const series = reactive({
  series: {
    seriesName: "",
  }
})


onBeforeMount(async () => {
  await axios.get(`api/bookSeriesBySeriesId?seriesId=${seriesId.value}`).then(Response => {
    series.series = Response.data
    console.log(series.series.seriesName+"@@@@@@@@@@@@@@@@@@@@@@@@@@@")
  })
})


let alter = (() => {
  let alterSeries = JSON.stringify(series.series)

  axios.put('api/bookSeries', alterSeries, {headers: {'Content-Type': 'application/json'}}).then(Response => {
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
    name: 'adminBookSeries'
  })
})
</script>

<style scoped>
.el-radio-group {
  margin-right: 12px;
}
.el-input{
  width: 100%;
}
.control{
  float: left;
  width: 30%;
}
.el-form{
  margin-left: 10%;
  float: right;
 width: 60%;
}
</style>
