<template>
  <div style="margin: 5% 20%">
    <div style="width: 100%;float: left">
      <el-form size="default" label-position="left" ref="form" width="100%" :model="series">
        <el-form-item label="系列">
          <el-input v-model="series.series.seriesName" :model-value="series.series.seriesName"></el-input>
        </el-form-item>
        <el-form-item>
          <el-row style="width:100%" justify="space-evenly">
            <el-col span="24">
              <el-button type="danger" plain round @click="alter()">修改</el-button>
            </el-col>
            <el-col span="24">
              <el-button type="success" plain round @click="back()">返回</el-button>
            </el-col>
          </el-row>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from "vue-router";
import {reactive} from "vue";
import {alterSeries, getSeriesByID} from "@/api/CategoryApi";
import {ElMessage} from "element-plus";
import {inject} from "vue";
let emit = defineEmits(['finish', 'cancel'])
let router = useRouter()

let seriesId = inject("seriesId")
const series = reactive({
  series: {
    seriesName: "",
    seriesId: seriesId.value
  }
})

getSeriesByID(seriesId.value).then(Response => {
  series.series = Response.data
})

let alter = (() => {
  let Series = JSON.stringify(series.series)
  alterSeries(Series).then(Response => {
      ElMessage.success(Response.message)
      emit('finish')
    series.seriesName=''
  }).catch(Error => {
    ElMessage(Error.data.message)
  })
})

let back = (() => {
  emit('cancel')
  location.reload()
  router.go(0)
})
</script>

