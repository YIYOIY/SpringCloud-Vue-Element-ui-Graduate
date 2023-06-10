<template>
  <div style="margin: 15% 30%">

    <div style="width: 60%;float: left">

      <el-form size="default" label-position="left" ref="form" width="100%" :model="series">
        <el-form-item label="系列">
          <el-input v-model="series.series.seriesName" :model-value="series.series.seriesName"></el-input>
        </el-form-item>

        <el-form-item>
          <el-row style="width:100%" justify="space-evenly">
            <el-col span="24">
              <el-button type="danger" @click="alter()">修改</el-button>
            </el-col>
            <el-col span="24">
              <el-button type="success" @click="back()">返回</el-button>
            </el-col>
          </el-row>
        </el-form-item>

      </el-form>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from "vue-router";
import { reactive, onBeforeMount, toRef } from "vue";
import axios from "axios";
let router = useRouter()

const prop = defineProps(['seriesId'])
let seriesId = toRef(prop, 'seriesId')
console.log(seriesId.value + "!!!!!!!!!!!!!!!!!!!!!!")

const series = reactive({
  series: {
    seriesName: "",
    seriesId: seriesId.value
  }
})


onBeforeMount(async () => {
  await axios.get(`api/bookSeriesBySeriesId?seriesId=${seriesId.value}`).then(Response => {
    series.series = Response.data
    console.log(series.series.seriesName + "@@@@@@@@@@@@@@@@@@@@@@@@@@@")
  })
})


let alter = (() => {
  let alterSeries = JSON.stringify(series.series)

  axios.put('api/bookSeries', alterSeries, { headers: { 'Content-Type': 'application/json' } }).then(Response => {
    let message = Response.data
    if (confirm(message + "!  是否跳转到系列页")) {
      router.push({
        name: 'category',
      })
    }
  }).catch(Error => {
    alert(Error.message)
  })
})

let back = (() => {
  router.push({
    name: 'category'
  })
})
</script>

<style scoped></style>
